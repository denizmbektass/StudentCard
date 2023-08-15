package com.bilgeadam.controller;

import com.bilgeadam.dto.response.CardResponseDto;
import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(CARD)
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/get-card/{token}")
    @CrossOrigin("*")
    public ResponseEntity<CardResponseDto> getCardByStudent(@PathVariable String token){
        return ResponseEntity.ok(cardService.getCardByStudent(token));
    }
}
