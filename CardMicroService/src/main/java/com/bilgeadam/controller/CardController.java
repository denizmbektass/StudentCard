package com.bilgeadam.controller;

import com.bilgeadam.dto.response.CardResponseDto;
import com.bilgeadam.repository.entity.Card;
import com.bilgeadam.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(CARD)
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @CrossOrigin("*")
    @GetMapping("/get-card/{token}")
    public ResponseEntity<CardResponseDto> getCardByStudent(@PathVariable String token){
        return ResponseEntity.ok(cardService.getCardByStudent(token));
    }

    @CrossOrigin("*")
    @GetMapping("/get-card-parameter-for-student/{token}")
    public ResponseEntity<Map<String,Integer>> getCardParameterForStudent(@PathVariable String token) {
        return ResponseEntity.ok(cardService.getCardParameterForStudent(token));
    }
}
