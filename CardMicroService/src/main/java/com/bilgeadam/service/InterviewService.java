package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateInterviewRequestDto;
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
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InterviewService extends ServiceManager<Interview,String> {
    private final IInterviewRepository interviewRepository;


    public InterviewService(IInterviewRepository interviewRepository) {
        super(interviewRepository);
        this.interviewRepository = interviewRepository;
    }

    public CreateInterviewResponseDto createInterview(CreateInterviewRequestDto dto) {
        //if check ile böyle bir student var mı eklenebilir user micro service oluşturulduktan sonra.
            Interview interview = IInterviewMapper.INSTANCE.toInterview(dto);
            save(interview);
            return IInterviewMapper.INSTANCE.toCreateInterviewResponseDto(interview);
    }

    public UpdateInterviewResponseDto updateInterview(UpdateInterviewRequestDto dto) {
        Optional<Interview> interview = interviewRepository.findById(dto.getInterviewId());
        if(interview.isEmpty()){
            throw new InterviewServiceException(ErrorType.INTERVIEW_NOT_FOUND);
        }
        interview.get().setDescription(dto.getDescription());
        interview.get().setName(dto.getName());
        interview.get().setScore(dto.getScore());
        interview.get().setStudentId(dto.getStudentId());
        update(interview.get());
        return IInterviewMapper.INSTANCE.toUpdateInterviewResponseDto(interview.get());
    }

    public DeleteInterviewResponseDto deleteInterview(String id) {
        Optional<Interview> interview = interviewRepository.findById(id);
        if(interview.isEmpty()){
            throw new InterviewServiceException(ErrorType.INTERVIEW_NOT_FOUND);
        }
        interview.get().setEStatus(EStatus.DELETED);
        update(interview.get());
        return IInterviewMapper.INSTANCE.toDeleteInterviewResponseDto(interview.get());
    }
}
