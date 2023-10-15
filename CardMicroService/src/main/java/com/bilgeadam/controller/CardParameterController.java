package com.bilgeadam.controller;

import static com.bilgeadam.constants.ApiUrls.*;

import com.bilgeadam.dto.request.CreateCardParameterRequestDto;
import com.bilgeadam.dto.request.GetDefaultTranscriptInfoByNameRequestDto;
import com.bilgeadam.dto.response.GetDefaultTranscriptInfoByNameResponseDto;
import com.bilgeadam.repository.entity.CardParameter;
import com.bilgeadam.service.CardParameterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CARDPARAMETER)
@RequiredArgsConstructor
public class CardParameterController {
    private final CardParameterService cardParameterService;

    @Operation(summary = "Kart parametre oluşturma işlemi",
            description = "Belirtilen DTO kullanılarak bir kart parametre oluşturur.")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> createCardParameter(@RequestBody CreateCardParameterRequestDto dto){
        return ResponseEntity.ok( cardParameterService.createCardParameter(dto));
    }


    @Operation(summary = "Tüm kart parametrelerini alma işlemi",
            description = "Tüm kart parametrelerini alır.")
    @GetMapping(FIND_ALL)
    @CrossOrigin("*")
    public ResponseEntity<List<CardParameter>> findAllCardParameters(){
        return ResponseEntity.ok( cardParameterService.findAll());
    }


    @Operation(summary = "Grup adına göre kart parametrelerini alma işlemi",
            description = "Belirtilen grup adına göre kart parametrelerini alır.")
    @PostMapping("/get-group-card-parameter-by-group-name")
    @CrossOrigin("*")
    public ResponseEntity<GetDefaultTranscriptInfoByNameResponseDto> getGroupCardParameterByGroupName(@RequestBody GetDefaultTranscriptInfoByNameRequestDto dto){
        return ResponseEntity.ok(cardParameterService.getGroupCardParameterByGroupName(dto));
    }



}
