package com.bilgeadam.service;


import com.bilgeadam.dto.request.TranscriptInfo;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.exceptions.*;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.repository.ICardRepository;
import com.bilgeadam.repository.entity.*;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardService extends ServiceManager<Card, String> {
    private final ICardRepository iCardRepository;
    private final JwtTokenManager jwtTokenManager;
    private final CardParameterService cardParameterService;
    private final AssignmentService assignmentService;
    private final ExamService examService;
    private final InternshipSuccessRateService intershipService;
    private final InterviewService interviewService;
    private final AbsenceService absenceService;
    private final ProjectService projectService;
    private final TrainerAssessmentService trainerAssessmentService;
    private final IUserManager userManager;
    private final GraduationProjectService graduationProjectService;
    private final WrittenExamService writtenExamService;
    private final AlgorithmService algorithmService;
    private final TechnicalInterviewService technicalInterviewService;
    private final TrainerAssessmentCoefficientsService trainerAssessmentCoefficientsService;
    private final ProjectBehaviorService projectBehaviorService;
    private final EmploymentInterviewService employmentInterviewService;
    private final CareerEducationService careerEducationService;
    private final ApplicationProcessService applicationProcessService;
    private final DocumentSubmitService documentSubmitService;
    private final TeamLeadAssessmentService teamLeadAssessmentService;
    private final TeamworkService teamworkService;
    private final AttendanceService attendanceService;
    private final ContributionService contributionService;
    private final InternshipTasksService internshipTasksService;
    private final PersonalMotivationService personalMotivationService;

    public CardService(ICardRepository iCardRepository, JwtTokenManager jwtTokenManager,
                       CardParameterService cardParameterService, AssignmentService assignmentService,
                       ExamService examService, InternshipSuccessRateService intershipService,
                       InterviewService interviewService, AbsenceService absenceService, ProjectService projectService,
                       TrainerAssessmentService trainerAssessmentService, IUserManager userManager, GraduationProjectService graduationProjectService, WrittenExamService writtenExamService, AlgorithmService algorithmService, TechnicalInterviewService technicalInterviewService, TrainerAssessmentCoefficientsService trainerAssessmentCoefficientsService, ProjectBehaviorService projectBehaviorService, EmploymentInterviewService employmentInterviewService, CareerEducationService careerEducationService, ApplicationProcessService applicationProcessService, DocumentSubmitService documentSubmitService, TeamLeadAssessmentService teamLeadAssessmentService, TeamworkService teamworkService, AttendanceService attendanceService, ContributionService contributionService, InternshipTasksService internshipTasksService, PersonalMotivationService personalMotivationService) {
        super(iCardRepository);
        this.iCardRepository = iCardRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.cardParameterService = cardParameterService;
        this.assignmentService = assignmentService;
        this.examService = examService;
        this.intershipService = intershipService;
        this.interviewService = interviewService;
        this.absenceService = absenceService;
        this.projectService = projectService;
        this.trainerAssessmentService = trainerAssessmentService;
        this.userManager = userManager;
        this.graduationProjectService = graduationProjectService;
        this.writtenExamService = writtenExamService;
        this.algorithmService = algorithmService;
        this.technicalInterviewService = technicalInterviewService;
        this.trainerAssessmentCoefficientsService = trainerAssessmentCoefficientsService;
        this.projectBehaviorService = projectBehaviorService;
        this.employmentInterviewService = employmentInterviewService;
        this.careerEducationService = careerEducationService;
        this.applicationProcessService = applicationProcessService;
        this.documentSubmitService = documentSubmitService;
        this.teamLeadAssessmentService = teamLeadAssessmentService;
        this.teamworkService = teamworkService;
        this.attendanceService = attendanceService;
        this.contributionService = contributionService;
        this.internshipTasksService = internshipTasksService;
        this.personalMotivationService = personalMotivationService;
    }

    public CardResponseDto getCardByStudent(String token) {
        getTranscriptByStudent(token);
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        List<String> groupNames = jwtTokenManager.getGroupNameFromToken(token);
        if (groupNames.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        Map<String, Integer> parameters = cardParameterService.getCardParameterByGroupName(groupNames).getParameters();
        Card card = Card.builder().studentId(studentId.get()).build();
        Integer assignmentNote = assignmentService.getAssignmentNote(studentId.get());
        Integer examNote = examService.getExamNote(studentId.get());
        Integer internshipNote = intershipService.getInternshipNote(studentId.get());
//        Integer interviewNote = interviewService.getInterviewNote(studentId.get());
        Integer projectNote = projectService.getProjectNote(studentId.get());
        Integer assessmentNote = trainerAssessmentService.getTrainerAssessmentNote(studentId.get());
        Map<String, Integer> newNotes = new HashMap<>();
        newNotes.put("Assignment", assignmentNote);
        newNotes.put("Exam", examNote);
        newNotes.put("Internship", internshipNote);
//        newNotes.put("Interview", interviewNote);
        newNotes.put("Project", projectNote);
        newNotes.put("TrainerAssessment", assessmentNote);
        Integer totalNote = ((assignmentNote * parameters.get("Assignment")) + (examNote * parameters.get("Exam"))
                + (internshipNote * parameters.get("Internship"))
//                + (interviewNote * parameters.get("Interview"))
                + (projectNote * parameters.get("Project")) + (assessmentNote * parameters.get("TrainerAssessment"))) / 100;
        TranscriptInfo transcriptInfo = userManager.getTranscriptInfoByUser(token).getBody();
        ShowUserAbsenceInformationResponseDto dto = absenceService.showUserAbsenceInformation(token);
        Double absence = (dto.getGroup1Percentage() + dto.getGroup2Percentage()) / 2;
        card.setNotes(newNotes);
        card.setAbsence(absence);
        card.setTotalNote(totalNote);
        save(card);
        return CardResponseDto.builder().profilePicture(transcriptInfo.getProfilePicture()).totalNote(card.getTotalNote())
                .notes(card.getNotes()).absence(card.getAbsence()).assistantTrainer(transcriptInfo.getAssistantTrainer())
                .masterTrainer(transcriptInfo.getMasterTrainer()).groupName(groupNames)
                .startDate(transcriptInfo.getStartDate()).endDate(transcriptInfo.getEndDate()).build();
    }

    public Map<String, Integer> getCardParameterForStudent(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        List<String> groupNameForStudent = userManager.findGroupNameForStudent(studentId.get()).getBody();
        CardParameter cardParameter = cardParameterService.getCardParameterByGroupName(groupNameForStudent);
        Map<String, Integer> parameters = cardParameter.getParameters();
        return parameters;
    }

    public TranscriptResponseDto getTranscriptByStudent(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new AssignmentException(ErrorType.INVALID_TOKEN);
        List<String> groupNames = jwtTokenManager.getGroupNameFromToken(token);
        if (groupNames.isEmpty())
            throw new AssignmentException(ErrorType.INVALID_TOKEN);
        List<AssignmentResponseDto> assignmentResponseDtos = assignmentService.findAllAssignments(token);
        List<ExamResponseDto> examResponseDtos = examService.findAllExams(token);
        List<TrainerAssessmentForTranscriptResponseDto> trainerAssessmentForTranscriptResponseDto = trainerAssessmentService.findAllTrainerAssessmentForTranscriptResponseDto(token);
        List<InternshipResponseDto> internshipResponseDtos = intershipService.findAllInternshipWithUser(token);
        List<InterviewForTranscriptResponseDto> interviewForTranscriptResponseDto = interviewService.findAllInterviewsDtos(token);
        ShowUserAbsenceInformationResponseDto absenceDto = absenceService.showUserAbsenceInformation(token);
        Double absencePerform = (absenceDto.getGroup1Percentage() + absenceDto.getGroup2Percentage()) / 2;
        List<StudentProjectListResponseDto> project = projectService.showStudentProjectList(token);
        StudentChoiceResponseDto studentChoiceResponseDto = getStudentChoiceDetails(token);
        TranscriptResponseDto transcriptResponseDto = TranscriptResponseDto.builder().absence(absencePerform).assignment(assignmentResponseDtos).studentChoice(studentChoiceResponseDto).exam(examResponseDtos).intership(internshipResponseDtos).interview(interviewForTranscriptResponseDto).project(project).trainerAssessment(trainerAssessmentForTranscriptResponseDto).build();
        return transcriptResponseDto;
    }

    public EducationScoreDetailsDto getEducationDetails(String token) {
        List<Long> assignmentScoreList;
        List<Long> examScoreList;
        List<Double> trainerAssessmentScoreList;
        List<Long> projectScoreList;

        // İleride ağırlık oranları değiştiği zaman burayı değiştir!!!
        double examWeight = 0.15;
        double assignmentWeight = 0.15;
        double absenceWeight = 0.1;
        double projectWeight = 0.15;
        double trainerAssessmentWeight = 0.25;
        double graduationProjectWeight = 0.20;
        Double avgAssignmentScore = null;
        Double avgExamScore = null;
        Double avgTrainerAssessmentScore = null;
        Double avgProjectScore = null;
        Double avgAbsencePerformScore = null;
        Double avgGraduationProjectScore = null;
        Double assignmentSuccessScore = null;
        Double examSuccessScore = null;
        Double trainerAssessmentSuccessScore = null;
        Double projectSuccessScore = null;
        Double absencePerformSuccessScore = null;
        Double graduationProjectSuccessScore = null;
        Double totalSuccessScore = 0.0;

        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.STUDENT_NOT_FOUND);
        }

        //Ödevler İçin Ortalama Bilgisi
        avgAssignmentScore = getAssignmentAverage(token);
        if (avgAssignmentScore != null) {
            assignmentSuccessScore = avgAssignmentScore * assignmentWeight;
            totalSuccessScore += assignmentSuccessScore;
        }

        //Sınavlar için ortalama bilgisi
        avgExamScore = getExamAverage(token);
        if (avgExamScore != null) {
            examSuccessScore = avgExamScore * examWeight;
            totalSuccessScore += examSuccessScore;
        }

        //Eğitmen Görüşü için ortalam bilgisi
        avgTrainerAssessmentScore = getTrainerAssesmentAverage(token);
        if (avgTrainerAssessmentScore != null) {
            trainerAssessmentSuccessScore = avgTrainerAssessmentScore * trainerAssessmentWeight;
            totalSuccessScore += trainerAssessmentSuccessScore;
        }

        //Proje için ortalam bilgisi
        avgProjectScore = getProjectBehaviorAverage(token);
        if (avgProjectScore != null) {
            projectSuccessScore = avgProjectScore * projectWeight;
            totalSuccessScore += projectSuccessScore;
        }

        // Yoklama için ortalama bilgisi
        avgAbsencePerformScore = getAbsencePerformAverage(token);
        if (avgAbsencePerformScore != null) {
            absencePerformSuccessScore = avgAbsencePerformScore * absenceWeight;
            totalSuccessScore += absencePerformSuccessScore;
        }

        //Bitirme projesi için ortalama bilgisi
        avgGraduationProjectScore = getGraduationProjectAverage(token);
        if (avgGraduationProjectScore != null) {
            graduationProjectSuccessScore = avgGraduationProjectScore * graduationProjectWeight;
            totalSuccessScore += graduationProjectSuccessScore;
        }

        return EducationScoreDetailsDto.builder()
                .averageAssignmentScore(avgAssignmentScore)
                .averageExamScore(avgExamScore)
                .averageTrainerAssessmentScore(avgTrainerAssessmentScore)
                .averageProjectScore(avgProjectScore)
                .averageAbsencePerformScore(avgAbsencePerformScore)
                .averageGraduationProjectScore(avgGraduationProjectScore)
                .assignmentSuccessScore(assignmentSuccessScore)
                .examSuccessScore(examSuccessScore)
                .trainerAssessmentSuccessScore(trainerAssessmentSuccessScore)
                .projectSuccessScore(projectSuccessScore)
                .absencePerformSuccessScore(absencePerformSuccessScore)
                .graduationProjectSuccessScore(graduationProjectSuccessScore)
                .totalSuccessScore(totalSuccessScore)
                .build();
    }

    // Ödevler Ortalama
    public Double getAssignmentAverage(String token) {
        List<AssignmentResponseDto> assignmentResponseDtos = assignmentService.findAllAssignments(token);
        if (assignmentResponseDtos.isEmpty()) {
            return null;
        } else {
            List<Long> assignmentScoreList = assignmentResponseDtos.stream()
                    .map(AssignmentResponseDto::getScore)
                    .collect(Collectors.toList());
            // Ortalama
            return assignmentScoreList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
        }
    }

    // Eğitmen Görüşü Ortalama
    public Double getTrainerAssesmentAverage(String token) {
        GetTrainerAssessmentCoefficientsResponseDto getTrainerAssessmentCoefficientsResponseDto = trainerAssessmentCoefficientsService.getTrainerAssessmentCoefficientsResponseDto(token);
        if (getTrainerAssessmentCoefficientsResponseDto != null) {
            return getTrainerAssessmentCoefficientsResponseDto.getAverageScore();
        } else {
            return null;
        }
    }

    // Sınav Ortalama
    public Double getExamAverage(String token) {
        List<ExamResponseDto> examResponseDtos = examService.findAllExams(token);
        if (!examResponseDtos.isEmpty()) {
            List<Long> examScoreList = examResponseDtos.stream()
                    .map(ExamResponseDto::getScore)
                    .collect(Collectors.toList());
            // Ortalama
            return examScoreList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
        } else {
            return null;
        }
    }

    // Proje Ortalama
    public Double getProjectBehaviorAverage(String token) {
        GetProjectBehaviorResponseDto getProjectBehaviorResponseDto = projectBehaviorService.findProjectBehavior(token);
        if (getProjectBehaviorResponseDto != null) {
            // Ortalama
            return getProjectBehaviorResponseDto.getAverageScore();

        } else {
            return null;
        }
    }

    // Yoklama Ortalama
    public Double getAbsencePerformAverage(String token) {
        ShowUserAbsenceInformationResponseDto absenceDto = absenceService.showUserAbsenceInformation(token);
        if (absenceDto != null) {
            return (absenceDto.getGroup1Percentage() + absenceDto.getGroup2Percentage()) / 2;
        }
        return null;
    }

    // Bitirme Projesi Ortalama
    public Double getGraduationProjectAverage(String token) {
        GetGraduationProjectResponseDto graduationProjects = graduationProjectService.findGraduationProject(token);
        if (graduationProjects != null) {
            return graduationProjects.getAverageScore();
        } else {
            return null;
        }
    }


    public StudentChoiceResponseDto getStudentChoiceDetails(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);

        Double writtenExamWeight = 0.25;
        Double algorithmWeight= 0.25;
        Double candidateInterviewWeight= 0.25;
        Double technicalInterviewWeight= 0.25;
        Double writtenExamScore = null;
        Double algorithmScore = null;
        Double candidateInterviewScore = null;
        Double technicalInterviewScore = null;
        Double writtenExamSuccessScore = null;
        Double algorithmSuccessScore = null;
        Double candidateInterviewSuccessScore = null;
        Double technicalInterviewSuccessScore = null;
        Double totalSuccessScore = 0.0;
        Double totalWeight=1.0;
        int count = 0;
        Boolean isExemptFromAlgorithm = false;
        Boolean isExemptFromTechnicalInterview = false;


        AlgorithmResponseDto algorithm = algorithmService.getAlgorithm(token);
        GetTechnicalInterviewResponseDto technicalInterview = technicalInterviewService.getTechnicalInterview(studentId.get());

        if(technicalInterview != null ){
            if(technicalInterview.isExempt() == true ) {
                technicalInterviewWeight = 0.0;
                 writtenExamWeight = totalWeight/3;
                 algorithmWeight= totalWeight/3;
                 candidateInterviewWeight= totalWeight/3;
                 count++;
                 isExemptFromTechnicalInterview =true;
            }
        }
        if(algorithm != null){
            if(algorithm.isExempt() == true && count == 1) {
                algorithmWeight =0.0;
                writtenExamWeight= totalWeight/2;
                candidateInterviewWeight= totalWeight/2;
                isExemptFromAlgorithm=true;
            }else if(algorithm.isExempt() == false && count == 1){
                writtenExamWeight = totalWeight/3;
                algorithmWeight= totalWeight/3;
                candidateInterviewWeight= totalWeight/3;
            }
            else if (algorithm.isExempt() == true && count ==0){
                algorithmWeight = 0.0;
                writtenExamWeight = totalWeight/3;
                technicalInterviewWeight= totalWeight/3;
                candidateInterviewWeight= totalWeight/3;
                isExemptFromAlgorithm=true;
            }
        }
        WrittenExam writtenExam = writtenExamService.getWrittenExamByStudentId(studentId.get());
        if (writtenExam != null) {
            writtenExamScore = writtenExam.getScore();
            writtenExamSuccessScore = writtenExamScore * writtenExamWeight;
            totalSuccessScore += writtenExamSuccessScore;
        }


        if (algorithm != null) {
            algorithmScore = algorithm.getFinalScore();
                algorithmSuccessScore = algorithmScore * algorithmWeight;
                totalSuccessScore += algorithmSuccessScore;
        }


        Double candidateInterviewAveragePoint = interviewService.getCandidateInterviewAveragePoint(studentId.get());
        if (candidateInterviewAveragePoint != null) {
            candidateInterviewScore = candidateInterviewAveragePoint;
            candidateInterviewSuccessScore = candidateInterviewScore * candidateInterviewWeight;
            totalSuccessScore += candidateInterviewSuccessScore;
        }

        Double technicalInterviewAveragePoint = technicalInterviewService.getTechnicalInterviewAveragePoint(studentId.get());
        if (technicalInterviewAveragePoint != null) {
            technicalInterviewScore = technicalInterviewAveragePoint;
            technicalInterviewSuccessScore = technicalInterviewScore * technicalInterviewWeight;
            totalSuccessScore += technicalInterviewSuccessScore;
        }
            return StudentChoiceResponseDto.builder()
                    .technicalInterviewSuccessScore(technicalInterviewSuccessScore)
                    .writtenExamSuccessScore(writtenExamSuccessScore)
                    .algorithmSuccessScore(algorithmSuccessScore)
                    .candidateInterviewSuccessScore(candidateInterviewSuccessScore)
                    .algorithmScore(algorithmScore)
                    .writtenExamScore(writtenExamScore)
                    .technicalInterviewScore(technicalInterviewScore)
                    .candidateInterviewScore(candidateInterviewScore)
                    .totalSuccessScore(totalSuccessScore)
                    .isExemptFromAlgorithm(isExemptFromAlgorithm)
                    .isExemptFromTechnicalInterview(isExemptFromTechnicalInterview)
                    .build();
    }


    public EmploymentScoreDetailsDto getEmploymentDetails(String token) {
        Double careerEducationSuccessScore = null;
        Double documentSumbitSuccessScore = null;
        Double applicationProcessSuccessScore = null;
        Double employmentInterviewSuccessScore = null;
        Double totalSuccessScore = 0.0;
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.STUDENT_NOT_FOUND);
        }

        // Kariyer Eğitimi
        careerEducationSuccessScore = getCareerEducationSuccessScore(studentId.get());
        if (careerEducationSuccessScore != null) {
            totalSuccessScore += careerEducationSuccessScore;
        }

        // Evrak Teslim
        documentSumbitSuccessScore = getDocumentSubmitSuccessScore(studentId.get());
        if (documentSumbitSuccessScore != null) {
            totalSuccessScore += documentSumbitSuccessScore;
        }

        // Başvuru Süreci
        applicationProcessSuccessScore = getApplicationProcessSuccessScore(studentId.get());
        if (applicationProcessSuccessScore != null) {
            totalSuccessScore += applicationProcessSuccessScore;
        }

        // Mülakat
        employmentInterviewSuccessScore = getEmploymentInterviewSuccessScore(token);
        if (employmentInterviewSuccessScore != null) {
            totalSuccessScore += employmentInterviewSuccessScore;
        }

        return EmploymentScoreDetailsDto.builder()
                .documentSumbitSuccessScore(documentSumbitSuccessScore)
                .careerEducationSuccessScore(careerEducationSuccessScore)
                .employmentInterviewSuccessScore(employmentInterviewSuccessScore)
                .applicationProcessSuccessScore(applicationProcessSuccessScore)
                .totalSuccessScore(totalSuccessScore)
                .build();
    }

    public Double getDocumentSubmitSuccessScore(String studentId) {
        Double documentSubmitAverage = documentSubmitService.getDocumentSubmitAveragePoint(studentId);
        if (documentSubmitAverage == null) {
            return null;
        } else {
            return documentSubmitAverage;
        }
    }

    public Double getCareerEducationSuccessScore(String studentId) {
        Double careerEducation = careerEducationService.getCareerEducationAveragePoint(studentId);
        if (careerEducation == null) {
            return null;
        } else {
            Double careerEducationWeight = 0.35;
            Double careerEducationSuccessScore = careerEducation * careerEducationWeight;
            return careerEducationSuccessScore;
        }
    }

    public Double getApplicationProcessSuccessScore(String studentId) {
        Double applicationProcess = applicationProcessService.calculateApplicationProcessRate(studentId);
        if (applicationProcess == null) {
            return null;
        } else {
            return applicationProcess;
        }
    }

    public Double getEmploymentInterviewSuccessScore(String token) {
        Double employmentInterview = employmentInterviewService.getEmploymentInterviewAvg(token);
        if (employmentInterview == null) {
            return null;
        } else {
            return employmentInterview;
        }
    }

    public InternshipSuccessResponseDto getInternshipSuccess(String token){
        Double teamLeadAssessmentSuccessScore = null;
        Double teamWorkSuccessScore = null;
        Double contributionSuccessScore = null;
        Double attendanceSuccessScore = null;
        Double personalMotivationSuccessScore = null ;
        Double tasksSuccessScore = null ;
        Double teamLeadAssessmentScore = null;
        Double teamWorkScore = null;
        Double contributionScore = null;
        Double attendanceScore = null;
        Double personalMotivationScore = null ;
        Double tasksScore = null ;
        Double totalSuccessScore = 0.0;
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.STUDENT_NOT_FOUND);
        }
        //Takım Lideri Görüşü
        GetTeamLeadAssessmentDetailsResponseDto getTeamLeadAssessmentDetailsResponseDto = teamLeadAssessmentService.getTeamLeadsAssessmentDetails(studentId.get());
        if(getTeamLeadAssessmentDetailsResponseDto != null) {
            teamLeadAssessmentSuccessScore = getTeamLeadAssessmentDetailsResponseDto.getSuccessScore();
            teamLeadAssessmentScore = getTeamLeadAssessmentDetailsResponseDto.getScore();
            totalSuccessScore += teamLeadAssessmentSuccessScore;

        }

        // Takım Çalışması
        teamWorkScore = teamworkService.getTeamworkSuccessPoint(studentId.get());
        if (teamWorkScore != null) {
            teamWorkSuccessScore = teamWorkScore * 0.20;
            totalSuccessScore += teamWorkSuccessScore;
        }

        // Katılım
        attendanceScore = getAttendanceScore(token);
        if (attendanceScore != null) {
            attendanceSuccessScore = attendanceScore * 0.1;
            totalSuccessScore += attendanceSuccessScore;
        }

        //Katki
        contributionScore = contributionService.calculateAndGetTotalScoreContribution(studentId.get());
        if(contributionScore != null){
            contributionSuccessScore = contributionScore * 0.1;
            totalSuccessScore += contributionSuccessScore;
        }

        //Gorevler
        tasksScore = internshipTasksService.getInternShipTaskSuccessPoint(studentId.get());
        if(tasksScore != null){
            tasksSuccessScore = tasksScore * 0.20 ;
            totalSuccessScore += tasksSuccessScore;
        }

        //Kisisel Motivasyonn
        personalMotivationScore = getPersonalMotivation(token);
        if(personalMotivationScore != null){
            personalMotivationSuccessScore = personalMotivationScore * 0.15 ;
            totalSuccessScore += personalMotivationSuccessScore;
        }

        return InternshipSuccessResponseDto.builder()
                .teamLeadAssessmentScore(teamLeadAssessmentScore)
                .teamLeadAssessmentSuccessScore(teamLeadAssessmentSuccessScore)
                .teamWorkScore(teamWorkScore)
                .teamWorkSuccessScore(teamWorkSuccessScore)
                .attendanceScore(attendanceScore)
                .attendanceSuccessScore(attendanceSuccessScore)
                .contributionScore(contributionScore)
                .contributionSuccessScore(contributionSuccessScore)
                .tasksScore(tasksScore)
                .tasksSuccessScore(tasksSuccessScore)
                .personalMotivationScore(personalMotivationScore)
                .personalMotivationSuccessScore(personalMotivationSuccessScore)
                .totalSuccessScore(totalSuccessScore)
                .build();
    }

    private Double getPersonalMotivation(String token) {
        GetPersonalMotivationResponseDto motivationResponseDto = personalMotivationService.findPersonalMotivation(token);
        if(motivationResponseDto == null){
            return null;
        }else{
            return motivationResponseDto.getAverage();
        }

    }

    private Double getAttendanceScore(String token) {
        GetAttendanceResponseDto getAttendanceResponseDto = attendanceService.getAttendanceInfo(token);
        if (getAttendanceResponseDto == null){
            return null;
        }
        else{
            return getAttendanceResponseDto.getAttendanceAverage();
        }
    }


}
