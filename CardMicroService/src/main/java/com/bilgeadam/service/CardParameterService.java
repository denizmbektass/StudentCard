package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateCardParameterRequestDto;
import com.bilgeadam.dto.request.GetDefaultTranscriptInfoByNameRequestDto;
import com.bilgeadam.dto.response.GetDefaultTranscriptInfoByNameResponseDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.ICardParameterMapper;
import com.bilgeadam.repository.ICardParameterRepository;
import com.bilgeadam.repository.entity.CardParameter;
import com.bilgeadam.repository.entity.TranskriptDefault;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardParameterService extends ServiceManager<CardParameter,String> {
    private final ICardParameterRepository cardParameterRepository;
    private final TranskriptDefaultService transkriptDefaultService;

    public CardParameterService(ICardParameterRepository cardParameterRepository,
                                TranskriptDefaultService transkriptDefaultService) {
        super(cardParameterRepository);
        this.cardParameterRepository = cardParameterRepository;
        this.transkriptDefaultService = transkriptDefaultService;
    }


    public CardParameter getCardParameterByGroupName(List<String> groupName){
        return findAll().stream().filter(x-> groupName.contains(x.getGroupName()))
                .max(Comparator.comparingLong(CardParameter::getCreateDate))
                .orElseThrow(()-> new CardServiceException(ErrorType.CARD_PARAMETER_NOT_FOUND));
    }
    public boolean createCardParameter(CreateCardParameterRequestDto dto) {
        Optional<CardParameter> optionalCardParameter = cardParameterRepository.findByGroupName(dto.getGroupName());
        if(optionalCardParameter.isEmpty()) {
            save(ICardParameterMapper.INSTANCE.toCardParameter(dto));
        }else{
            optionalCardParameter.get().setParameters(dto.getParameters());
            update(optionalCardParameter.get());
        }
        return true;
    }

    public GetDefaultTranscriptInfoByNameResponseDto getGroupCardParameterByGroupName(GetDefaultTranscriptInfoByNameRequestDto dto) {
        Optional<CardParameter> cardParameter = cardParameterRepository.findByGroupName(dto.getGroupName());
        GetDefaultTranscriptInfoByNameResponseDto responseDto;
        if(cardParameter.isEmpty()){
            TranskriptDefault transkriptDefault = transkriptDefaultService.findOptionalByMainGroupName(dto.getMainGroupName()).orElseThrow(()->{
                throw new CardServiceException(ErrorType.TRANSCRIPT_NOT_FOUND);
            });
            Map<String,Integer> parameters = new HashMap<>();
            parameters.put("Assignment",transkriptDefault.getHomeworkPercentage());
            parameters.put("Exam",transkriptDefault.getExamPercentage());
            parameters.put("Internship",transkriptDefault.getInternshipSuccessRatePercentage());
            parameters.put("Interview",transkriptDefault.getInterviewPercentage());
            parameters.put("Project",transkriptDefault.getProjectPercentage());
            parameters.put("TrainerAssessment",transkriptDefault.getInstructorPercentageOfOpinion());
            responseDto = GetDefaultTranscriptInfoByNameResponseDto.builder()
                    .parameters(parameters)
                    .groupName(dto.getGroupName())
                    .build();
        }else{
            responseDto = GetDefaultTranscriptInfoByNameResponseDto.builder()
                    .parameters(cardParameter.get().getParameters())
                    .groupName(cardParameter.get().getGroupName())
                    .build();
        }
        return responseDto;
    }
}
