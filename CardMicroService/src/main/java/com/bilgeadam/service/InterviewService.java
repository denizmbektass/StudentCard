package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateInterviewRequestDto;

import com.bilgeadam.dto.request.TokenRequestDto;
import com.bilgeadam.dto.request.UpdateInterviewRequestDto;
import com.bilgeadam.dto.response.CreateInterviewResponseDto;
import com.bilgeadam.dto.response.DeleteInterviewResponseDto;
import com.bilgeadam.dto.response.InterviewForTranscriptResponseDto;
import com.bilgeadam.dto.response.UpdateInterviewResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.InterviewServiceException;
import com.bilgeadam.mapper.IInterviewMapper;
import com.bilgeadam.repository.IInterviewRepository;
import com.bilgeadam.repository.entity.Interview;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InterviewService extends ServiceManager<Interview, String> {
    private final IInterviewRepository interviewRepository;
    private final JwtTokenManager jwtTokenManager;


    public InterviewService(IInterviewRepository interviewRepository, JwtTokenManager jwtTokenManager) {
        super(interviewRepository);
        this.interviewRepository = interviewRepository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public CreateInterviewResponseDto createInterview(CreateInterviewRequestDto dto) {
        //if check ile böyle bir student var mı eklenebilir user micro service oluşturulduktan sonra.
        if(dto.getInterviewType().isBlank())
            throw  new CardServiceException(ErrorType.INTERVIEW_TYPE_EMPTY);
        if (dto.getScore()<0 || dto.getScore()>100)
            throw new CardServiceException(ErrorType.INTERVIEW_SCORE_NUMBER_RANGE);
        if(dto.getDescription().isBlank())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);
        if(dto.getScore()==null)
            throw new InterviewServiceException(ErrorType.POINT_EMPTY);
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        Interview interview = IInterviewMapper.INSTANCE.toInterview(dto);
        interview.setStudentId(studentId.get());
        save(interview);
        return IInterviewMapper.INSTANCE.toCreateInterviewResponseDto(interview);
    }

    public UpdateInterviewResponseDto updateInterview(UpdateInterviewRequestDto dto) {
        Optional<Interview> interview = interviewRepository.findById(dto.getInterviewId());
        if (interview.isEmpty()) {
            throw new CardServiceException(ErrorType.INTERVIEW_NOT_FOUND);
        }
        if(dto.getDescription().isBlank())
            throw new CardServiceException(ErrorType.TRAINER_ASSESSMENT_EMPTY);
        if(dto.getScore()==null)
            throw new CardServiceException(ErrorType.POINT_EMPTY);
        if(dto.getInterviewType().isBlank())
            throw  new CardServiceException(ErrorType.INTERVIEW_TYPE_EMPTY);
        if(dto.getName().isBlank())
            throw  new CardServiceException(ErrorType.INTERVIEW_NAME_EMPTY);
        Interview toUpdateInterview = interview.get();
        toUpdateInterview.setDescription(dto.getDescription());
        toUpdateInterview.setName(dto.getName());
        toUpdateInterview.setScore(dto.getScore());
        toUpdateInterview.setInterviewType((dto.getInterviewType()));
        update(toUpdateInterview);
        return IInterviewMapper.INSTANCE.toUpdateInterviewResponseDto(interview.get());
    }

    public DeleteInterviewResponseDto deleteInterview(String id) {
        Optional<Interview> interview = interviewRepository.findById(id);
        if (interview.isEmpty()) {
            throw new CardServiceException(ErrorType.INTERVIEW_NOT_FOUND);
        }
        interview.get().setEStatus(EStatus.DELETED);
        update(interview.get());
        return IInterviewMapper.INSTANCE.toDeleteInterviewResponseDto(interview.get());
    }
    public Integer getInterviewNote(String studentId){
        return (int) Math.floor(interviewRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x->x.getScore()).average().orElse(0));
    }
    public List<Interview> findAllInterviews(TokenRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        return findAll().stream().filter(x->x.getEStatus()==EStatus.ACTIVE && x.getStudentId().equals(studentId.get()))
                .collect(Collectors.toList());
    }
    public List<InterviewForTranscriptResponseDto> findAllInterviewsDtos(String token) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(token);
        List<Interview> interviews = findAll().stream().filter(x->x.getEStatus()==EStatus.ACTIVE && x.getStudentId().equals(studentId.get()))
                .collect(Collectors.toList());
        List<InterviewForTranscriptResponseDto> interviewForTranscriptResponseDtos = new ArrayList<>();
        interviews.forEach(x->{
            InterviewForTranscriptResponseDto dto = IInterviewMapper.INSTANCE.toInterviewForTranscriptResponseDto(x);
            interviewForTranscriptResponseDtos.add(dto);
        });
        return interviewForTranscriptResponseDtos;
    }
}
