package com.bilgeadam.controller;

import com.bilgeadam.dto.request.TranscriptDefaultRequestDto;
import com.bilgeadam.dto.response.GetDefaultTranscriptResponseDto;
import com.bilgeadam.dto.response.TranscriptDefaultResponseDto;
import com.bilgeadam.repository.view.VwGroupName;
import com.bilgeadam.service.TranskriptDefaultService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(TRANSCRIPT_DEFAULT)
@RequiredArgsConstructor
public class TranscriptDefaultController {
    private final TranskriptDefaultService transkriptDefaultService;

    @Operation(summary = "Grup isimlerini alma işlemi",
            description = "Sistemde bulunan grupların isimlerini alır.")
    @CrossOrigin("*")
    @GetMapping("/taking-group-names")
    public ResponseEntity<List<VwGroupName>> takingGroupNames(){
        return ResponseEntity.ok(transkriptDefaultService.takingGroupNames());
    }

    @Operation(summary = "Transkript verileri oluşturma işlemi",
            description = "Belirtilen transkript verilerini oluşturur.")
    @CrossOrigin("*")
    @PostMapping("/creating-transcript-datas")
    public ResponseEntity<Boolean> creatingTranscriptDatas(@RequestBody TranscriptDefaultRequestDto transcriptDefaultRequestDto){
        return ResponseEntity.ok(transkriptDefaultService.creatingTranscriptDatas(transcriptDefaultRequestDto));
    }


    @Operation(summary = "Varsayılan transkript bilgilerini isme göre alma işlemi",
            description = "Belirtilen ana grup adına göre varsayılan transkript bilgilerini getirir.")
    @CrossOrigin("*")
    @GetMapping("/get-default-transcript-info-by-name/{mainGroupName}")
    public ResponseEntity<GetDefaultTranscriptResponseDto> getDefaultTranscriptInfoByName(@PathVariable String mainGroupName){
        return ResponseEntity.ok(transkriptDefaultService.getDefaultTranscriptInfoByName(mainGroupName));
    }

}
