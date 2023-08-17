package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateCardParameterRequestDto;
import com.bilgeadam.dto.request.ParameterRequestDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.repository.ICardParameterRepository;
import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.repository.entity.CardParameter;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardParameterService extends ServiceManager<CardParameter,String> {
    private final ICardParameterRepository cardParameterRepository;

    public CardParameterService(ICardParameterRepository cardParameterRepository) {
        super(cardParameterRepository);
        this.cardParameterRepository = cardParameterRepository;
    }


    CardParameter getCardParameterByGroupName(List<String> groupName){
        return findAll().stream().filter(x-> groupName.contains(x.getGroupName()))
                .max(Comparator.comparingLong(CardParameter::getCreateDate))
                .orElseThrow(()-> new CardServiceException(ErrorType.CARD_PARAMETER_NOT_FOUND));
    }
    public void createCardParameter(CreateCardParameterRequestDto dto) {
        CardParameter cardParameter = CardParameter.builder().groupName(dto.getGroupName())
                .parameters(dto.getParameters().stream()
                        .collect(Collectors.toMap(ParameterRequestDto::getName,ParameterRequestDto::getParam))).build();
        save(cardParameter);
    }
}
