package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateInterviewRequestDto;

import com.bilgeadam.dto.request.TokenRequestDto;
import com.bilgeadam.dto.request.UpdateInterviewRequestDto;
import com.bilgeadam.dto.response.CreateInterviewResponseDto;
import com.bilgeadam.dto.response.DeleteInterviewResponseDto;
import com.bilgeadam.dto.response.UpdateInterviewResponseDto;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.exceptions.InterviewServiceException;
import com.bilgeadam.mapper.IInterviewMapper;
import com.bilgeadam.repository.IInterviewRepository;
import com.bilgeadam.repository.entity.Interview;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

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
            throw  new InterviewServiceException(ErrorType.BAD_REQUEST,"Mülakat Türü Boş bırakılamaz");
        if (dto.getScore()<0 || dto.getScore()>100)
            throw new InterviewServiceException(ErrorType.BAD_REQUEST,"Puan 0 ile 100 arasında olmak zorundadır...");
        if(dto.getDescription().isBlank())
            throw new InterviewServiceException(ErrorType.BAD_REQUEST,"Görüş boş bırakılamaz...");
        if(dto.getScore()==null)
            throw new InterviewServiceException(ErrorType.BAD_REQUEST,"Puan boş bırakılamaz...");
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        Interview interview = IInterviewMapper.INSTANCE.toInterview(dto);
        interview.setStudentId(studentId.get());
        save(interview);
        return IInterviewMapper.INSTANCE.toCreateInterviewResponseDto(interview);
    }

    public UpdateInterviewResponseDto updateInterview(UpdateInterviewRequestDto dto) {
        Optional<Interview> interview = interviewRepository.findById(dto.getInterviewId());
        if (interview.isEmpty()) {
            throw new InterviewServiceException(ErrorType.INTERVIEW_NOT_FOUND);
        }
        if(dto.getDescription().isBlank())
            throw new InterviewServiceException(ErrorType.BAD_REQUEST,"Görüş boş bırakılamaz...");
        if(dto.getScore()==null)
            throw new InterviewServiceException(ErrorType.BAD_REQUEST,"Puan boş bırakılamaz...");
        if(dto.getInterviewType().isBlank())
            throw  new InterviewServiceException(ErrorType.BAD_REQUEST,"Mülakat Türü Boş bırakılamaz");
        if(dto.getName().isBlank())
            throw  new InterviewServiceException(ErrorType.BAD_REQUEST,"Mülakat Adı Boş bırakılamaz");
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
            throw new InterviewServiceException(ErrorType.INTERVIEW_NOT_FOUND);
        }
        interview.get().setEStatus(EStatus.DELETED);
        update(interview.get());
        return IInterviewMapper.INSTANCE.toDeleteInterviewResponseDto(interview.get());
    }
    public Long getInterviewNote(String studentId){
        return (long) Math.floor(interviewRepository.findAllByStudentId(studentId).stream()
                .mapToLong(x->x.getScore()).average().getAsDouble());
    }
    public List<Interview> findAllInterviews(TokenRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getToken());
        return findAll().stream().filter(x->x.getEStatus()==EStatus.ACTIVE && x.getStudentId().equals(studentId.get()))
                .collect(Collectors.toList());
    }
}
