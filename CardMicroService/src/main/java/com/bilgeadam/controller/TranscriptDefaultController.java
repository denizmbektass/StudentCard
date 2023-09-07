package com.bilgeadam.controller;

import com.bilgeadam.dto.request.TranscriptDefaultRequestDto;
import com.bilgeadam.dto.response.GetDefaultTranscriptResponseDto;
import com.bilgeadam.dto.response.TranscriptDefaultResponseDto;
import com.bilgeadam.repository.view.VwGroupName;
import com.bilgeadam.service.TranskriptDefaultService;
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
    @CrossOrigin("*")
    @GetMapping("/taking-group-names")
    public ResponseEntity<List<VwGroupName>> takingGroupNames(){
        return ResponseEntity.ok(transkriptDefaultService.takingGroupNames());
    }
    @CrossOrigin("*")
    @PostMapping("/creating-transcript-datas")
    public ResponseEntity<Boolean> creatingTranscriptDatas(@RequestBody TranscriptDefaultRequestDto transcriptDefaultRequestDto){
        return ResponseEntity.ok(transkriptDefaultService.creatingTranscriptDatas(transcriptDefaultRequestDto));
    }

    @CrossOrigin("*")
    @GetMapping("/get-default-transcript-info-by-name/{mainGroupName}")
    public ResponseEntity<GetDefaultTranscriptResponseDto> getDefaultTranscriptInfoByName(@PathVariable String mainGroupName){
        return ResponseEntity.ok(transkriptDefaultService.getDefaultTranscriptInfoByName(mainGroupName));
    }

}
