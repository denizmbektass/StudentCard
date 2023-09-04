package com.bilgeadam.service;


import com.bilgeadam.dto.request.TranscriptInfo;
import com.bilgeadam.dto.response.CardResponseDto;
import com.bilgeadam.dto.response.ShowUserAbsenceInformationResponseDto;
import com.bilgeadam.exceptions.AssignmentException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.manager.IUserManager;
import com.bilgeadam.repository.ICardRepository;
import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.repository.entity.CardParameter;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardService extends ServiceManager<Card,String> {
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

    public CardService(ICardRepository iCardRepository, JwtTokenManager jwtTokenManager,
                       CardParameterService cardParameterService, AssignmentService assignmentService,
                       ExamService examService, InternshipSuccessRateService intershipService,
                       InterviewService interviewService, AbsenceService absenceService, ProjectService projectService,
                       TrainerAssessmentService trainerAssessmentService, IUserManager userManager) {
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
    }

    public CardResponseDto getCardByStudent(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
            throw new AssignmentException(ErrorType.INVALID_TOKEN);
        List<String> groupNames = jwtTokenManager.getGroupNameFromToken(token);
        if(groupNames.isEmpty())
            throw new AssignmentException(ErrorType.INVALID_TOKEN);
        Map<String,Integer> parameters = cardParameterService.getCardParameterByGroupName(groupNames).getParameters();
        Card card = Card.builder().studentId(studentId.get()).build();
        Integer assignmentNote = assignmentService.getAssignmentNote(studentId.get());
        Integer examNote = examService.getExamNote(studentId.get());
        Integer internshipNote = intershipService.getInternshipNote(studentId.get());
        Integer interviewNote = interviewService.getInterviewNote(studentId.get());
        Integer projectNote = projectService.getProjectNote(studentId.get());
        Integer assessmentNote = trainerAssessmentService.getTrainerAssessmentNote(studentId.get());
        Map<String,Integer> newNotes = new HashMap<>();
        newNotes.put("Assignment",assignmentNote);
        newNotes.put("Exam",examNote);
        newNotes.put("Internship",internshipNote);
        newNotes.put("Interview",interviewNote);
        newNotes.put("Project",projectNote);
        newNotes.put("TrainerAssessment",assessmentNote);
        Integer totalNote = ((assignmentNote* parameters.get("Assignment"))+(examNote* parameters.get("Exam"))
                +(internshipNote* parameters.get("Internship"))+(interviewNote* parameters.get("Interview"))
                +(projectNote* parameters.get("Project"))+(assessmentNote* parameters.get("TrainerAssessment")))/100;
        TranscriptInfo transcriptInfo = userManager.getTranscriptInfoByUser(token).getBody();
        ShowUserAbsenceInformationResponseDto dto = absenceService.showUserAbsenceInformation(token);
        Double absence = (dto.getGroup1Percentage()+ dto.getGroup2Percentage())/2;
        card.setNotes(newNotes);
        card.setAbsence(absence) ;
        card.setTotalNote(totalNote);
        save(card);
        return CardResponseDto.builder().profilePicture(transcriptInfo.getProfilePicture()).totalNote(card.getTotalNote())
                .notes(card.getNotes()).absence(card.getAbsence()).assistantTrainer(transcriptInfo.getAssistantTrainer())
                .masterTrainer(transcriptInfo.getMasterTrainer()).groupName(groupNames)
                .startDate(transcriptInfo.getStartDate()).endDate(transcriptInfo.getEndDate()).build();
    }

    public Map<String,Integer> getCardParameterForStudent(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
            throw new AssignmentException(ErrorType.INVALID_TOKEN);
        List<String> groupNameForStudent = userManager.findGroupNameForStudent(studentId.get()).getBody();
        CardParameter cardParameter = cardParameterService.getCardParameterByGroupName(groupNameForStudent);
        Map<String, Integer> parameters = cardParameter.getParameters();
        return parameters;
    }
}
