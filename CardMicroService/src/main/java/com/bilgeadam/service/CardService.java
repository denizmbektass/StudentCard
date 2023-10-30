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
        Integer interviewNote = interviewService.getInterviewNote(studentId.get());
        Integer projectNote = projectService.getProjectNote(studentId.get());
        Integer assessmentNote = trainerAssessmentService.getTrainerAssessmentNote(studentId.get());
        Map<String, Integer> newNotes = new HashMap<>();
        newNotes.put("Assignment", assignmentNote);
        newNotes.put("Exam", examNote);
        newNotes.put("Internship", internshipNote);
        newNotes.put("Interview", interviewNote);
        newNotes.put("Project", projectNote);
        newNotes.put("TrainerAssessment", assessmentNote);
        Integer totalNote = ((assignmentNote * parameters.get("Assignment")) + (examNote * parameters.get("Exam"))
                + (internshipNote * parameters.get("Internship")) + (interviewNote * parameters.get("Interview"))
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
        System.out.println("girdi");
        TranscriptResponseDto transcriptResponseDto = TranscriptResponseDto.builder().absence(absencePerform).assignment(assignmentResponseDtos).exam(examResponseDtos).intership(internshipResponseDtos).interview(interviewForTranscriptResponseDto).project(project).trainerAssessment(trainerAssessmentForTranscriptResponseDto).build();
        return transcriptResponseDto;
    }

    public EducationScoreDetailsDto getEducationDetails(String token) {
        List<Long> assignmentScoreList;
        List<Long> examScoreList;
        List<Long> trainerAssessmentScoreList;
        List<Long> projectScoreList;

        // İleride ağırlık oranları değiştiği zaman burayı değiştir!!!
        double examWeight = 0.15;
        double assignmentWeight = 0.15;
        double absenceWeight = 0.1;
        double projectWeight = 0.15;
        double trainerAssessmentWeight = 0.25;
        double graduationProjectWeight = 0.25;
        double avgAssignmentScore = 0;
        double avgExamScore = 0;
        double avgTrainerAssessmentScore = 0;
        double avgProjectScore = 0;
        double avgAbsencePerformScore = 0;
        double avgGraduationProjectScore = 0;
        double assignmentSuccessScore = 0;
        double examSuccessScore = 0;
        double trainerAssessmentSuccessScore = 0;
        double projectSuccessScore = 0;
        double absencePerformSuccessScore = 0;
        double graduationProjectSuccessScore = 0;
        double totalSuccessScore = 0;
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.STUDENT_NOT_FOUND);
        }
        //Ödevler İçin Ortalama Bilgisi
        List<AssignmentResponseDto> assignmentResponseDtos = assignmentService.findAllAssignments(token);
        if (assignmentResponseDtos.isEmpty()) {
            throw new AssignmentException(ErrorType.ASSIGNMENT_NOT_FOUND);
        } else {
            assignmentScoreList = assignmentResponseDtos.stream()
                    .map(AssignmentResponseDto::getScore)
                    .collect(Collectors.toList());

            avgAssignmentScore = assignmentScoreList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
        }

        //Sınavlar için ortalama bilgisi
        List<ExamResponseDto> examResponseDtos = examService.findAllExams(token);
        if (examResponseDtos.isEmpty()) {
            throw new ExamException(ErrorType.EXAM_NOT_FOUND);
        } else {
            examScoreList = examResponseDtos.stream()
                    .map(ExamResponseDto::getScore)
                    .collect(Collectors.toList());

            avgExamScore = examScoreList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
        }

        //Eğitmen Görüşü için ortalam bilgisi
        List<TrainerAssessmentForTranscriptResponseDto> trainerAssessmentForTranscriptResponseDto = trainerAssessmentService.findAllTrainerAssessmentForTranscriptResponseDto(token);
        if (trainerAssessmentForTranscriptResponseDto.isEmpty()) {
            throw new TrainerAssessmentException(ErrorType.TRAINER_ASSESSMENT_NOT_FOUND);
        } else {
            trainerAssessmentScoreList = trainerAssessmentForTranscriptResponseDto.stream()
                    .map(TrainerAssessmentForTranscriptResponseDto::getScore)
                    .collect(Collectors.toList());

            avgTrainerAssessmentScore = trainerAssessmentScoreList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
        }

        //Proje için ortalam bilgisi
        List<StudentProjectListResponseDto> project = projectService.showStudentProjectList(token);
        if (project.isEmpty()) {
            throw new ProjectException(ErrorType.PROJECT_NOT_FOUND);
        } else {
            projectScoreList = project.stream()
                    .map(StudentProjectListResponseDto::getProjectScore)
                    .collect(Collectors.toList());

            avgProjectScore = projectScoreList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
        }

        //Yoklama için ortalama bilgisi
        ShowUserAbsenceInformationResponseDto absenceDto = absenceService.showUserAbsenceInformation(token);
        if (absenceDto == null) {
            throw new AbsenceException(ErrorType.ABSENCE_NOT_FOUND);
        } else {
            avgAbsencePerformScore = (absenceDto.getGroup1Percentage() + absenceDto.getGroup2Percentage()) / 2;
        }

        //Bitirme projesi için ortalama bilgisi
        GetGraduationProjectResponseDto graduationProjects = graduationProjectService.findGraduationProject(token);
        if (graduationProjects == null) {
            // Buraya Exceptions sınıfı eklenip yazılacak
            System.out.println("Bitirme projesi bulunmadı.");
        } else {
            avgGraduationProjectScore = graduationProjects.getAverageScore();
        }

        //Başarı puanlarını Hesaplama
        assignmentSuccessScore = avgAssignmentScore * assignmentWeight;
        examSuccessScore = avgExamScore * examWeight;
        trainerAssessmentSuccessScore = avgTrainerAssessmentScore * trainerAssessmentWeight;
        projectSuccessScore = avgProjectScore * projectWeight;
        absencePerformSuccessScore = avgAbsencePerformScore * absenceWeight;
        graduationProjectSuccessScore = avgGraduationProjectScore * graduationProjectWeight;


        totalSuccessScore = assignmentSuccessScore + examSuccessScore+trainerAssessmentSuccessScore+projectSuccessScore+absencePerformSuccessScore+graduationProjectSuccessScore;
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
        double writtenExamScore = 0;
        double algorithmScore = 0;

       WrittenExam writtenExam = writtenExamService.getWrittenExamByStudentId(studentId.get());
       if(writtenExam == null) {
           throw new AssignmentException(ErrorType.WRITTENEXAM_NOT_FOUND);
       }else{
           writtenExamScore = writtenExam.getScore();
       }
        AlgorithmResponseDto algorithm = algorithmService.getAlgorithm(token);
       if (algorithm == null){
           throw new AssignmentException(ErrorType.ALGORITHM_NOT_FOUND);
       }else{
            algorithmScore = algorithm.getFinalScore();
       }
        Double candidateInterviewScore = interviewService.getCandidateInterviewAveragePoint(studentId.get());
       if (candidateInterviewScore == null)
           throw new AssignmentException(ErrorType.CANDIDATE_INTERVIEW_NOT_FOUND);
       Double gameInterviewScore = gameInterviewService.getGameInterviewAveragePoint(studentId.get());
       if(gameInterviewScore == null)
           throw new AssignmentException(ErrorType.GAME_INTERVIEW_NOT_FOUND);


        double writtenExamSuccessScore = writtenExamScore * writtenExamWeight;
        double algorithmSuccessScore = algorithmScore * algorithmWeight;
        double candidateInterviewSuccessScore = candidateInterviewScore * candidateInterviewWeight;
        double gameInterviewSuccessScore= gameInterviewScore * gameInterviewWeight;

        double totalSuccessScore = writtenExamSuccessScore + algorithmSuccessScore + candidateInterviewSuccessScore + gameInterviewSuccessScore ;

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
