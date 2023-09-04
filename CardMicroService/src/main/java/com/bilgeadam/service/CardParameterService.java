package com.bilgeadam.service;

import com.bilgeadam.dto.request.CreateCardParameterRequestDto;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.ICardParameterMapper;
import com.bilgeadam.repository.ICardParameterRepository;
import com.bilgeadam.repository.entity.CardParameter;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CardParameterService extends ServiceManager<CardParameter,String> {
    private final ICardParameterRepository cardParameterRepository;

    public CardParameterService(ICardParameterRepository cardParameterRepository) {
        super(cardParameterRepository);
        this.cardParameterRepository = cardParameterRepository;
    }


    public CardParameter getCardParameterByGroupName(List<String> groupName){
        return findAll().stream().filter(x-> groupName.contains(x.getGroupName()))
                .max(Comparator.comparingLong(CardParameter::getCreateDate))
                .orElseThrow(()-> new CardServiceException(ErrorType.CARD_PARAMETER_NOT_FOUND));
    }
    public boolean createCardParameter(CreateCardParameterRequestDto dto) {
        save(ICardParameterMapper.INSTANCE.toCardParameter(dto));
        return true;
    }
}
