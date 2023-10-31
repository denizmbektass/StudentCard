package com.bilgeadam.service;


import com.bilgeadam.dto.request.TranscriptInfo;
import com.bilgeadam.dto.response.*;
import com.bilgeadam.exceptions.*;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.repository.ICardRepository;
import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.repository.entity.CardParameter;
import com.bilgeadam.repository.entity.GraduationProject;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;
import com.bilgeadam.repository.entity.WrittenExam;

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
    private final GameInterviewService gameInterviewService;

    public CardService(ICardRepository iCardRepository, JwtTokenManager jwtTokenManager,
                       CardParameterService cardParameterService, AssignmentService assignmentService,
                       ExamService examService, InternshipSuccessRateService intershipService,
                       InterviewService interviewService, AbsenceService absenceService, ProjectService projectService,
                       TrainerAssessmentService trainerAssessmentService, IUserManager userManager, GraduationProjectService graduationProjectService, WrittenExamService writtenExamService, AlgorithmService algorithmService, GameInterviewService gameInterviewService) {
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
        this.gameInterviewService = gameInterviewService;
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
        StudentChoiceResponseDto studentChoiceResponseDto =getStudentChoiceDetails(token);
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
        double graduationProjectWeight = 0.25;
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
        List<AssignmentResponseDto> assignmentResponseDtos = assignmentService.findAllAssignments(token);
        if (assignmentResponseDtos.isEmpty()) {
            //   throw new AssignmentException(ErrorType.ASSIGNMENT_NOT_FOUND);
        } else {
            assignmentScoreList = assignmentResponseDtos.stream()
                    .map(AssignmentResponseDto::getScore)
                    .collect(Collectors.toList());
            // Ortalama
            avgAssignmentScore = assignmentScoreList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
            // Başarı Puanı
            assignmentSuccessScore = avgAssignmentScore * assignmentWeight;
            totalSuccessScore += assignmentSuccessScore;
        }

        //Sınavlar için ortalama bilgisi
        List<ExamResponseDto> examResponseDtos = examService.findAllExams(token);
        if (!examResponseDtos.isEmpty()) {
            examScoreList = examResponseDtos.stream()
                    .map(ExamResponseDto::getScore)
                    .collect(Collectors.toList());
            // Ortalama
            avgExamScore = examScoreList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
            // Başarı Puanı
            if (avgExamScore != null) {
                examSuccessScore = avgExamScore * examWeight;
                totalSuccessScore += examSuccessScore;
            }

        }

        //Eğitmen Görüşü için ortalam bilgisi
        List<TrainerAssessmentForTranscriptResponseDto> trainerAssessmentForTranscriptResponseDto = trainerAssessmentService.findAllTrainerAssessmentForTranscriptResponseDto(token);
        if (!trainerAssessmentForTranscriptResponseDto.isEmpty()) {
            trainerAssessmentScoreList = trainerAssessmentForTranscriptResponseDto.stream()
                    .map(TrainerAssessmentForTranscriptResponseDto::getTotalTrainerAssessmentScore)
                    .collect(Collectors.toList());
            // Ortalama
            avgTrainerAssessmentScore = trainerAssessmentScoreList.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            // Başarı Puanı
            trainerAssessmentSuccessScore = avgTrainerAssessmentScore * trainerAssessmentWeight;
            totalSuccessScore += trainerAssessmentSuccessScore;

        }

        //Proje için ortalam bilgisi
        List<StudentProjectListResponseDto> project = projectService.showStudentProjectList(token);
        if (!project.isEmpty()) {
            projectScoreList = project.stream()
                    .map(StudentProjectListResponseDto::getProjectScore)
                    .collect(Collectors.toList());
            // Ortalama
            avgProjectScore = projectScoreList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
            // Başarı Puanı
            projectSuccessScore = avgProjectScore * projectWeight;
            totalSuccessScore += projectSuccessScore;
        }
        try{
            //Yoklama için ortalama bilgisi
            ShowUserAbsenceInformationResponseDto absenceDto = absenceService.showUserAbsenceInformation(token);
            if (absenceDto != null) {
                avgAbsencePerformScore = (absenceDto.getGroup1Percentage() + absenceDto.getGroup2Percentage()) / 2;
                absencePerformSuccessScore = avgAbsencePerformScore * absenceWeight;
                totalSuccessScore += absencePerformSuccessScore;
            }
        }catch (Exception e){

        }

        //Bitirme projesi için ortalama bilgisi
        GetGraduationProjectResponseDto graduationProjects = graduationProjectService.findGraduationProject(token);
        if (graduationProjects != null) {
            avgGraduationProjectScore = graduationProjects.getAverageScore();
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

    public StudentChoiceResponseDto getStudentChoiceDetails(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty())
            throw new CardServiceException(ErrorType.INVALID_TOKEN);

        double writtenExamWeight = 0.25;
        double algorithmWeight = 0.25;
        double candidateInterviewWeight = 0.25;
        double gameInterviewWeight = 0.25;


        Double writtenExamScore = null;
        Double algorithmScore = null;
        Double candidateInterviewScore = null;
        Double gameInterviewScore = null;
        Double writtenExamSuccessScore = null;
        Double algorithmSuccessScore =null;
        Double candidateInterviewSuccessScore =null;
        Double gameInterviewSuccessScore =null;
        Double totalSuccessScore= 0.0;


        try {
            WrittenExam writtenExam = writtenExamService.getWrittenExamByStudentId(studentId.get());
            if (writtenExam != null) {
                writtenExamScore = writtenExam.getScore();
                writtenExamSuccessScore = writtenExamScore * writtenExamWeight;
                totalSuccessScore += writtenExamSuccessScore;
            }
        } catch (Exception e) {

        }

        try {
            AlgorithmResponseDto algorithm = algorithmService.getAlgorithm(token);
            if (algorithm != null) {
                algorithmScore = algorithm.getFinalScore();
                algorithmSuccessScore = algorithmScore * algorithmWeight;
                totalSuccessScore += algorithmSuccessScore;
            }
        } catch (Exception e) {

        }
        try{
            Double candidateInterviewAveragePoint = interviewService.getCandidateInterviewAveragePoint(studentId.get());
            if (candidateInterviewAveragePoint != null) {
                candidateInterviewScore = candidateInterviewAveragePoint;
                candidateInterviewSuccessScore = candidateInterviewScore * candidateInterviewWeight;
                totalSuccessScore += candidateInterviewSuccessScore;
            }
        }catch (Exception e){

        }

        Double gameInterviewAveragePoint = gameInterviewService.getGameInterviewAveragePoint(studentId.get());
        if (gameInterviewAveragePoint != null) {
            gameInterviewScore = gameInterviewAveragePoint;
            gameInterviewSuccessScore = gameInterviewScore * gameInterviewWeight;
            totalSuccessScore += gameInterviewSuccessScore;
        }

        return StudentChoiceResponseDto.builder()
                .gameInterviewSuccessScore(gameInterviewSuccessScore)
                .writtenExamSuccessScore(writtenExamSuccessScore)
                .algorithmSuccessScore(algorithmSuccessScore)
                .candidateInterviewSuccessScore(candidateInterviewSuccessScore)
                .algorithmScore(algorithmScore)
                .writtenExamScore(writtenExamScore)
                .gameInterviewScore(gameInterviewScore)
                .candidateInterviewScore(candidateInterviewScore)
                .totalSuccessScore(totalSuccessScore)
                .build();
    }

}
