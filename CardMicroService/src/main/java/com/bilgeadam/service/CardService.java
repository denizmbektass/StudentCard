package com.bilgeadam.service;


import com.bilgeadam.dto.request.TranscriptInfo;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.exceptions.*;
import com.bilgeadam.manager.IStudentManager;
import com.bilgeadam.rabbitmq.model.GetStudentModel;
import com.bilgeadam.rabbitmq.producer.GetStudentProducer;
import com.bilgeadam.repository.ICardRepository;
import com.bilgeadam.repository.entity.*;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardService extends ServiceManager<Card, String> {
    private final ICardRepository iCardRepository;
    private final JwtTokenManager jwtTokenManager;
    private final CardParameterService cardParameterService;
    private final AssignmentService assignmentService;
    private final OralExamService oralExamService;
    private final ExamService examService;
    private final InternshipSuccessRateService intershipService;
    private final InterviewService interviewService;
    private final AbsenceService absenceService;
    private final ProjectService projectService;
    private final TrainerAssessmentService trainerAssessmentService;
    private final IStudentManager studentManager;
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

    private final EducationWeightsService educationWeightsService;
    private final StudentChoiceWeightsService studentChoiceWeightsService;
    private final InternshipSuccessScoreWeightsService internshipSuccessScoreWeightsService;
    private final EmploymentWeightsService employmentWeightsService;

    private final GetStudentProducer getStudentProducer;

    public CardService(ICardRepository iCardRepository, JwtTokenManager jwtTokenManager,
                       CardParameterService cardParameterService, AssignmentService assignmentService,
                       OralExamService oralExamService, ExamService examService, InternshipSuccessRateService intershipService,
                       InterviewService interviewService, AbsenceService absenceService, ProjectService projectService,
                       TrainerAssessmentService trainerAssessmentService, IStudentManager studentManager, GraduationProjectService graduationProjectService, WrittenExamService writtenExamService, AlgorithmService algorithmService, TechnicalInterviewService technicalInterviewService, TrainerAssessmentCoefficientsService trainerAssessmentCoefficientsService, ProjectBehaviorService projectBehaviorService, EmploymentInterviewService employmentInterviewService, CareerEducationService careerEducationService, ApplicationProcessService applicationProcessService, DocumentSubmitService documentSubmitService, TeamLeadAssessmentService teamLeadAssessmentService, TeamworkService teamworkService, AttendanceService attendanceService, ContributionService contributionService, InternshipTasksService internshipTasksService, PersonalMotivationService personalMotivationService, EducationWeightsService educationWeightsService, StudentChoiceWeightsService studentChoiceWeightsService, InternshipSuccessScoreWeightsService internshipSuccessScoreWeightsService, EmploymentWeightsService employmentWeightsService, GetStudentProducer getStudentProducer) {
        super(iCardRepository);
        this.iCardRepository = iCardRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.cardParameterService = cardParameterService;
        this.assignmentService = assignmentService;
        this.oralExamService = oralExamService;
        this.examService = examService;
        this.intershipService = intershipService;
        this.interviewService = interviewService;
        this.absenceService = absenceService;
        this.projectService = projectService;
        this.trainerAssessmentService = trainerAssessmentService;
        this.studentManager = studentManager;
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
        this.educationWeightsService = educationWeightsService;
        this.studentChoiceWeightsService = studentChoiceWeightsService;
        this.internshipSuccessScoreWeightsService = internshipSuccessScoreWeightsService;
        this.employmentWeightsService = employmentWeightsService;
        this.getStudentProducer = getStudentProducer;
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
        Integer oralExamNote = oralExamService.getOralExamNote(studentId.get());
        Integer examNote = examService.getExamNote(studentId.get());
        Integer internshipNote = intershipService.getInternshipNote(studentId.get());
//        Integer interviewNote = interviewService.getInterviewNote(studentId.get());
        Integer projectNote = projectService.getProjectNote(studentId.get());
        Integer assessmentNote = trainerAssessmentService.getTrainerAssessmentNote(studentId.get());
        Map<String, Integer> newNotes = new HashMap<>();
        newNotes.put("Assignment", assignmentNote);
        newNotes.put("OralExam", oralExamNote);
        newNotes.put("Exam", examNote);
        newNotes.put("Internship", internshipNote);
//        newNotes.put("Interview", interviewNote);
        newNotes.put("Project", projectNote);
        newNotes.put("TrainerAssessment", assessmentNote);
        Integer totalNote = ((assignmentNote * parameters.get("Assignment"))
                + (oralExamNote * parameters.get("OralExam"))
                + (examNote * parameters.get("Exam"))
                + (internshipNote * parameters.get("Internship"))
//                + (interviewNote * parameters.get("Interview"))
                + (projectNote * parameters.get("Project")) + (assessmentNote * parameters.get("TrainerAssessment"))) / 100;
        TranscriptInfo transcriptInfo = studentManager.getTranscriptInfoByStudent(token).getBody();
        ShowStudentAbsenceInformationResponseDto dto = absenceService.showStudentAbsenceInformation(token);
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
        List<String> groupNameForStudent = studentManager.findGroupNameForStudent(studentId.get()).getBody();
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
        List<OralExamResponseDto> oralExamResponseDtos = oralExamService.findAllOralExam(token);
        List<ExamResponseDto> examResponseDtos = examService.findAllExams(token);
        List<TrainerAssessmentForTranscriptResponseDto> trainerAssessmentForTranscriptResponseDto = trainerAssessmentService.findAllTrainerAssessmentForTranscriptResponseDto(token);
        List<InternshipResponseDto> internshipResponseDtos = intershipService.findAllInternshipWithStudent(token);
        List<InterviewForTranscriptResponseDto> interviewForTranscriptResponseDto = interviewService.findAllInterviewsDtos(token);
        ShowStudentAbsenceInformationResponseDto absenceDto = absenceService.showStudentAbsenceInformation(token);
        Double absencePerform = absenceDto != null ? (absenceDto.getGroup1Percentage() + absenceDto.getGroup2Percentage()) / 2 : 0;
        List<StudentProjectListResponseDto> project = projectService.showStudentProjectList(token);
        StudentChoiceResponseDto studentChoiceResponseDto = getStudentChoiceDetails(token);
        TranscriptResponseDto transcriptResponseDto = TranscriptResponseDto.builder().absence(absencePerform).assignment(assignmentResponseDtos).studentChoice(studentChoiceResponseDto).exam(examResponseDtos).intership(internshipResponseDtos).interview(interviewForTranscriptResponseDto).project(project).trainerAssessment(trainerAssessmentForTranscriptResponseDto).build();
        return transcriptResponseDto;
    }

    public EducationScoreDetailsDto getEducationDetails(String token) {
        List<Long> assignmentScoreList;
        List<Long> oralExamScoreList;
        List<Long> examScoreList;
        List<Double> trainerAssessmentScoreList;
        List<Long> projectScoreList;
        // İleride ağırlık oranları değiştiği zaman burayı değiştir!!!

        List<String> groupName = jwtTokenManager.getGroupNameFromToken(token);
        if (groupName.isEmpty()) {
            throw new RuntimeException("Hata");
        }
        EducationWeights educationWeights = educationWeightsService.getEducationWeightsByGroupName(groupName.get(0));
        double examWeight = educationWeights.getExamWeight() / 100;
        double assignmentWeight = educationWeights.getAssignmentWeight() / 100;
        double oralExamWeight = educationWeights.getOralExamWeight() / 100;
        double absenceWeight = educationWeights.getObligationWeight() / 100;
        double projectWeight = educationWeights.getProjectBehaviorWeight() / 100;
        double trainerAssessmentWeight = educationWeights.getAssessmentWeight() / 100;
        double graduationProjectWeight = educationWeights.getGraduationProjectWeight() / 100;
        Double avgAssignmentScore = null;
        Double avgOralExamScore = null;
        Double avgExamScore = null;
        Double avgTrainerAssessmentScore = null;
        Double avgProjectScore = null;
        Double avgAbsencePerformScore = null;
        Double avgGraduationProjectScore = null;
        Double assignmentSuccessScore = null;
        Double oralExamSuccessScore = null;
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

        //Sözlüler için ortalama bilgisi
        avgOralExamScore = getOralExamAverage(token);
        if (avgOralExamScore != null) {
            oralExamSuccessScore = avgOralExamScore * oralExamWeight;
            totalSuccessScore += oralExamSuccessScore;
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
                .averageOralExamScore(avgOralExamScore)
                .averageExamScore(avgExamScore)
                .averageTrainerAssessmentScore(avgTrainerAssessmentScore)
                .averageProjectScore(avgProjectScore)
                .averageAbsencePerformScore(avgAbsencePerformScore)
                .averageGraduationProjectScore(avgGraduationProjectScore)
                .assignmentSuccessScore(assignmentSuccessScore)
                .oralExamSuccessScore(oralExamSuccessScore)
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

    // Sözlüler Ortalama
    public Double getOralExamAverage(String token) {
        List<OralExamResponseDto> oralExamResponseDtos = oralExamService.findAllOralExam(token);
        if (oralExamResponseDtos.isEmpty()) {
            return null;
        } else {
            List<Long> oralExamScoreList = oralExamResponseDtos.stream()
                    .map(OralExamResponseDto::getScore)
                    .collect(Collectors.toList());
            // Ortalama
            return oralExamScoreList.stream()
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
        ShowStudentAbsenceInformationResponseDto absenceDto = absenceService.showStudentAbsenceInformation(token);
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
        List<String> groupName = jwtTokenManager.getGroupNameFromToken(token);
        if (groupName.isEmpty()) {
            throw new RuntimeException("Hata");
        }
        StudentChoiceWeights studentChoiceWeights = studentChoiceWeightsService.getWeightsByGroupName(groupName.get(0));
        Double writtenExamWeight = studentChoiceWeights.getWrittenExamWeight() / 100;
        Double algorithmWeight = studentChoiceWeights.getAlgorithmWeight() / 100;
        Double candidateInterviewWeight = studentChoiceWeights.getCandidateInterviewWeight() / 100;
        Double technicalInterviewWeight = studentChoiceWeights.getTechnicalInterviewWeight() / 100;
        Double writtenExamScore = null;
        Double algorithmScore = null;
        Double candidateInterviewScore = null;
        Double technicalInterviewScore = null;
        Double writtenExamSuccessScore = null;
        Double algorithmSuccessScore = null;
        Double candidateInterviewSuccessScore = null;
        Double technicalInterviewSuccessScore = null;
        Double totalSuccessScore = 0.0;
        Double totalWeight = 1.0;
        int count = 0;
        Boolean isExemptFromAlgorithm = false;
        Boolean isExemptFromTechnicalInterview = false;


        AlgorithmResponseDto algorithm = algorithmService.getAlgorithm(token);
        GetTechnicalInterviewResponseDto technicalInterview = technicalInterviewService.getTechnicalInterview(studentId.get());

        if (technicalInterview != null) {
            if (technicalInterview.isExempt() == true) {
                technicalInterviewWeight = 0.0;
                writtenExamWeight = totalWeight / 3;
                algorithmWeight = totalWeight / 3;
                candidateInterviewWeight = totalWeight / 3;
                count++;
                isExemptFromTechnicalInterview = true;
            }
        }
        if (algorithm != null) {
            if (algorithm.isExempt() == true && count == 1) {
                algorithmWeight = 0.0;
                writtenExamWeight = totalWeight / 2;
                candidateInterviewWeight = totalWeight / 2;
                isExemptFromAlgorithm = true;
            } else if (algorithm.isExempt() == false && count == 1) {
                writtenExamWeight = totalWeight / 3;
                algorithmWeight = totalWeight / 3;
                candidateInterviewWeight = totalWeight / 3;
            } else if (algorithm.isExempt() == true && count == 0) {
                algorithmWeight = 0.0;
                writtenExamWeight = totalWeight / 3;
                technicalInterviewWeight = totalWeight / 3;
                candidateInterviewWeight = totalWeight / 3;
                isExemptFromAlgorithm = true;
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
        List<String> groupName = jwtTokenManager.getGroupNameFromToken(token);
        if (groupName.isEmpty()) {
            throw new RuntimeException("Hata");
        }
        EmploymentWeights employmentWeights = employmentWeightsService.getWeightsByGroupName(groupName.get(0));
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
        careerEducationSuccessScore = getCareerEducationSuccessScore(studentId.get(), employmentWeights);
        if (careerEducationSuccessScore != null) {
            totalSuccessScore += careerEducationSuccessScore;
        }

        // Evrak Teslim
        documentSumbitSuccessScore = getDocumentSubmitSuccessScore(studentId.get());
        if (documentSumbitSuccessScore != null) {
            totalSuccessScore += documentSumbitSuccessScore;
        }

        // Başvuru Süreci
        applicationProcessSuccessScore = getApplicationProcessSuccessScore(studentId.get(), token);
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

    public Double getCareerEducationSuccessScore(String studentId, EmploymentWeights employmentWeights) {
        Double careerEducation = careerEducationService.getCareerEducationAveragePoint(studentId);
        if (careerEducation == null) {
            return null;
        } else {
            Double careerEducationWeight = employmentWeights.getCareerEducationWeight() / 100;
            Double careerEducationSuccessScore = careerEducation * careerEducationWeight;
            return careerEducationSuccessScore;
        }
    }

    public Double getApplicationProcessSuccessScore(String studentId, String token) {
        Double applicationProcess = applicationProcessService.calculateApplicationProcessRate(studentId, token);
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

    public InternshipSuccessResponseDto getInternshipSuccess(String token) {
        List<String> groupName = jwtTokenManager.getGroupNameFromToken(token);
        if (groupName.isEmpty()) {
            throw new RuntimeException("Hata");
        }
        InternshipSuccessScoreWeights internshipSuccessScoreWeights = internshipSuccessScoreWeightsService.getWeightsByGroupName(groupName.get(0));
        Double teamLeadAssessmentSuccessScore = internshipSuccessScoreWeights.getTeamLeaderWeight() / 100;
        Double teamWorkSuccessScore = internshipSuccessScoreWeights.getTeamLeaderWeight() / 100;
        Double contributionSuccessScore = internshipSuccessScoreWeights.getContributionWeight() / 100;
        Double attendanceSuccessScore = internshipSuccessScoreWeights.getAttendanceWeight() / 100;
        Double personalMotivationSuccessScore = internshipSuccessScoreWeights.getPersonalMotivationWeight() / 100;
        Double tasksSuccessScore = internshipSuccessScoreWeights.getTasksWeight() / 100;
        Double teamLeadAssessmentScore = null;
        Double teamWorkScore = null;
        Double contributionScore = null;
        Double attendanceScore = null;
        Double personalMotivationScore = null;
        Double tasksScore = null;
        Double totalSuccessScore = 0.0;
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.STUDENT_NOT_FOUND);
        }
        //Takım Lideri Görüşü
        GetTeamLeadAssessmentDetailsResponseDto getTeamLeadAssessmentDetailsResponseDto = teamLeadAssessmentService.getTeamLeadsAssessmentDetails(studentId.get());
        if (getTeamLeadAssessmentDetailsResponseDto != null) {
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
        if (contributionScore != null) {
            contributionSuccessScore = contributionScore * 0.1;
            totalSuccessScore += contributionSuccessScore;
        }

        //Gorevler
        tasksScore = internshipTasksService.getInternShipTaskSuccessPoint(studentId.get());
        if (tasksScore != null) {
            tasksSuccessScore = tasksScore * 0.20;
            totalSuccessScore += tasksSuccessScore;
        }

        //Kisisel Motivasyonn
        personalMotivationScore = getPersonalMotivation(token);
        if (personalMotivationScore != null) {
            personalMotivationSuccessScore = personalMotivationScore * 0.15;
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
        if (motivationResponseDto == null) {
            return null;
        } else {
            return motivationResponseDto.getAverage();
        }

    }

    private Double getAttendanceScore(String token) {
        GetAttendanceResponseDto getAttendanceResponseDto = attendanceService.getAttendanceInfo(token);
        if (getAttendanceResponseDto == null) {
            return null;
        } else {
            return getAttendanceResponseDto.getAttendanceAverage();
        }
    }


    public void getCreatePdf(HttpServletResponse response, String token) throws IOException, JRException {
        System.out.println("pdf oluşturuluyor");
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        GetStudentModel getStudentModel = GetStudentModel.builder().token(token).build();
        StudentProfileResponseDto studentProfileResponseDto = (StudentProfileResponseDto) getStudentProducer.GetStudent(getStudentModel);

        StudentChoiceResponseDto studentChoice = getStudentChoiceDetails(token);//Öğrenci seçme
        EducationScoreDetailsDto educationDetails = getEducationDetails(token);//Eğitim
        InternshipSuccessResponseDto internshipSuccess = getInternshipSuccess(token);//Staj
        EmploymentScoreDetailsDto employmentScoreDetails = getEmploymentDetails(token);//İstihdam


        List<TranskriptResponseDto> transkriprPdfList = new ArrayList<>();
        transkriprPdfList.add(TranskriptResponseDto.builder()
                                                   .name_surname(studentProfileResponseDto.getName()+ " " + studentProfileResponseDto.getSurname())
                                                   .writtenExamScore(studentChoice.getWrittenExamScore())
                                                   .candidateInterviewScore(studentChoice.getCandidateInterviewScore())
                                                   .algorithmScore(studentChoice.getAlgorithmScore())
                                                   .technicalInterviewScore(studentChoice.getTechnicalInterviewScore())
                                                   .studentChoiceTotalSuccessScore(studentChoice.getTotalSuccessScore())
                                                   .assignmentSuccessScore(educationDetails.getAssignmentSuccessScore())
                                                   .examSuccessScore(educationDetails.getExamSuccessScore())
                                                   .trainerAssessmentSuccessScore(educationDetails.getTrainerAssessmentSuccessScore())
                                                   .projectSuccessScore(educationDetails.getProjectSuccessScore())
                                                   .absencePerformSuccessScore(educationDetails.getAbsencePerformSuccessScore())
                                                   .graduationProjectSuccessScore(educationDetails.getGraduationProjectSuccessScore())
                                                   .educationDetailsTotalSuccessScore(educationDetails.getTotalSuccessScore())
                                                   .teamLeadAssessmentSuccessScore(internshipSuccess.getTeamLeadAssessmentSuccessScore())
                                                   .teamWorkSuccessScore(internshipSuccess.getTeamWorkSuccessScore())
                                                   .contributionSuccessScore(internshipSuccess.getContributionSuccessScore())
                                                   .attendanceSuccessScore(internshipSuccess.getAttendanceSuccessScore())
                                                   .personalMotivationSuccessScore(internshipSuccess.getPersonalMotivationSuccessScore())
                                                   .tasksSuccessScore(internshipSuccess.getTasksSuccessScore())
                                                   .internshipSuccessTotalSuccessScore(internshipSuccess.getTotalSuccessScore())
                                                   .careerEducationSuccessScore(employmentScoreDetails.getCareerEducationSuccessScore())
                                                   .documentSumbitSuccessScore(employmentScoreDetails.getDocumentSumbitSuccessScore())
                                                   .applicationProcessSuccessScore(employmentScoreDetails.getApplicationProcessSuccessScore())
                                                   .employmentInterviewSuccessScore(employmentScoreDetails.getEmploymentInterviewSuccessScore())
                                                   .employmentScoreDetailsTotalSuccessScore(employmentScoreDetails.getTotalSuccessScore())
                                                   .build());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(transkriprPdfList);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("transkriptDataSet",dataSource);

        File file = ResourceUtils.getFile("classpath:transkriptPdf.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(print,response.getOutputStream());
    }

    private static JRPdfExporter getJrPdfExporter(HttpServletResponse response, JasperPrint print) throws IOException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        SimplePdfReportConfiguration reportConfig
                = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig
                = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("bilgeadam");
        exportConfig.setAllowedPermissionsHint("PRINTING");

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);
        return exporter;
    }
}
