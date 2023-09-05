package com.bilgeadam.controller;

import static com.bilgeadam.constants.ApiUrls.*;

import com.bilgeadam.dto.request.CreateCardParameterRequestDto;
import com.bilgeadam.repository.entity.CardParameter;
import com.bilgeadam.service.CardParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card-parameter")
@RequiredArgsConstructor
public class CardParameterController {
    private final CardParameterService cardParameterService;
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createCardParameter(@RequestBody CreateCardParameterRequestDto dto){
        return ResponseEntity.ok( cardParameterService.createCardParameter(dto));
    }

    @GetMapping(FIND_ALL)
    @CrossOrigin("*")
    public ResponseEntity<List<CardParameter>> findAllCardParameters(){
        return ResponseEntity.ok( cardParameterService.findAll());
    }

}
