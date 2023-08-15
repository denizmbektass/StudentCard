package com.bilgeadam.service;


import com.bilgeadam.dto.response.CardResponseDto;
import com.bilgeadam.exceptions.AssignmentException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.ICardMapper;
import com.bilgeadam.repository.ICardRepository;
import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CardService extends ServiceManager<Card,String> {
    private final ICardRepository iCardRepository;
    private final JwtTokenManager jwtTokenManager;
    private final CardParameterService cardParameterService;
    private final AssignmentService assignmentService;
    private final ExamService examService;
    private final InternshipSuccessRateService intershipService;
    private final InterviewService interviewService;
    private final ProjectService projectService;
    private final TrainerAssessmentService trainerAssessmentService;

    public CardService(ICardRepository iCardRepository, JwtTokenManager jwtTokenManager,
                       CardParameterService cardParameterService, AssignmentService assignmentService,
                       ExamService examService, InternshipSuccessRateService intershipService,
                       InterviewService interviewService, ProjectService projectService,
                       TrainerAssessmentService trainerAssessmentService) {
        super(iCardRepository);
        this.iCardRepository = iCardRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.cardParameterService = cardParameterService;
        this.assignmentService = assignmentService;
        this.examService = examService;
        this.intershipService = intershipService;
        this.interviewService = interviewService;
        this.projectService = projectService;
        this.trainerAssessmentService = trainerAssessmentService;
    }

    public CardResponseDto getCardByStudent(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        if(studentId.isEmpty())
            throw new AssignmentException(ErrorType.INVALID_TOKEN);
        List<String> groupNames = jwtTokenManager.getGroupNameFromToken(token);
        if(groupNames.isEmpty())
            throw new AssignmentException(ErrorType.INVALID_TOKEN);
        Map<String,Long> parameters = cardParameterService.getCardParameterByGroupName(groupNames).getParameters();
        Card card = Card.builder().studentId(studentId.get()).build();
        Map<String,Long> newNotes = new HashMap<>();
        newNotes.put("Assignment",assignmentService.getAssignmentNote(studentId.get()));
        newNotes.put("Exam",examService.getExamNote(studentId.get()));
        newNotes.put("Internship",intershipService.getInternshipNote(studentId.get()));
        newNotes.put("Interview",interviewService.getInterviewNote(studentId.get()));
        newNotes.put("Project",projectService.getProjectNote(studentId.get()));
        newNotes.put("TrainerAssessment",trainerAssessmentService.getTrainerAssessmentNote(studentId.get()));
        card.setNotes(newNotes);
        return ICardMapper.INSTANCE.toCardResponseDto(save(card));
    }
}
