package com.bilgeadam.service;

import com.bilgeadam.converter.CardConverter;
import com.bilgeadam.repository.ICardRepository;
import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CardService extends ServiceManager<Card,String> {
    private final ICardRepository iCardRepository;
    private final CardConverter cardConverter;
    public CardService(ICardRepository iCardRepository, CardConverter cardConverter) {
        super(iCardRepository);
        this.iCardRepository = iCardRepository;
        this.cardConverter = cardConverter;
    }
}
