package com.bilgeadam.controller;

import static com.bilgeadam.constants.ApiUrls.*;

import com.bilgeadam.dto.request.CreateCardParameterRequestDto;
import com.bilgeadam.service.CardParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card-parameter")
@RequiredArgsConstructor
public class CardParameterController {
    private final CardParameterService cardParameterService;

    @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createCardParameter(@RequestBody CreateCardParameterRequestDto dto){
        return ResponseEntity.ok( cardParameterService.createCardParameter(dto));
    }

}
