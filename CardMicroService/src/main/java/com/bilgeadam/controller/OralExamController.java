package com.bilgeadam.controller;

import com.bilgeadam.dto.request.OralExamRequestDto;
import com.bilgeadam.dto.request.UpdateOralExamRequestDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.dto.response.OralExamResponseDto;
import com.bilgeadam.service.OralExamService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static com.bilgeadam.constants.ApiUrls.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(ORALEXAM)
public class OralExamController {
    private final OralExamService oralExamService;
    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")


    @Operation(summary = "Sözlü oluşturma işlemi",
            description = "Belirtilen DTO kullanılarak bir sözlü oluşturur.")
    @PostMapping(CREATE)
    @CrossOrigin("*")
    @PreAuthorize("hasAnyAuthority('master_trainer:write','assistant_trainer:write')")
    public ResponseEntity<MessageResponse> createOralExam(@RequestBody @Valid OralExamRequestDto dto){
        oralExamService.createOralExam(dto);
        return ResponseEntity.ok(new MessageResponse("Sözlü başarıyla kaydedildi.."));
    }

    @Operation(summary = "Tüm sözlüleri listeleme işlemi",
            description = "Belirtilen token kullanılarak tüm sözlüleri listeler.")
    @GetMapping(FIND_ALL+"/{token}")
    @CrossOrigin("*")
    @PreAuthorize("hasAnyAuthority('read')")
    public ResponseEntity<List<OralExamResponseDto>> findAllOralExam(@PathVariable String token){
        return ResponseEntity.ok( oralExamService.findAllOralExam(token));
    }


    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @Operation(summary = "Sözlü güncelleme işlemi",
            description = "Belirtilen DTO kullanılarak bir sözlüyü günceller.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    @PreAuthorize("hasAnyAuthority('master_trainer:write','assistant_trainer:write')")
    public ResponseEntity<MessageResponse> updateOralExam(@RequestBody UpdateOralExamRequestDto dto){
        oralExamService.updateOralExam(dto);
        return ResponseEntity.ok(new MessageResponse("Sözlü başarıyla güncellendi.."));
    }


    //@PreAuthorize("hasAnyAuthority('ADMIN','ASSISTANT_TRAINER','MASTER_TRAINER')")
    @Operation(summary = "Sözlü silme işlemi",
            description = "Belirtilen DTO kullanılarak bir sözlüyü siler.")
    @DeleteMapping(DELETE+"/{oralExamId}")
    @CrossOrigin("*")
    @PreAuthorize("hasAnyAuthority('master_trainer:write','assistant_trainer:write')")
    public ResponseEntity<MessageResponse> deleteOralExam(@PathVariable String oralExamId){
        oralExamService.deleteOralExam(oralExamId);
        return ResponseEntity.ok(new MessageResponse("Sözlü başarıyla silindi.."));
    }


    @Operation(summary = "Tüm başlıkları alma işlemi",
            description = "Belirtilen token kullanılarak tüm sözlü başlıklarını alır.")
    @GetMapping(FIND_ALL+"/title/{token}")
    @CrossOrigin("*")
    @PreAuthorize("hasAnyAuthority('read')")
    public ResponseEntity<Set<String>> getAllTitles(@PathVariable String token){
        return ResponseEntity.ok(oralExamService.getAllTitles(token));
    }
    @Operation(summary = "Sözlülerin not ortalamasını bulma işlemi",
            description = "studentId ile tüm notlara erişilir ve ortalaması alınır. ")
    @GetMapping(ORAL_EXAM_AVERAGE + "/{studentId}")
    @CrossOrigin("*")
    @PreAuthorize("hasAnyAuthority('read')")
    public ResponseEntity<Double> getOralExamAverage(@PathVariable String studentId){
        return ResponseEntity.ok(oralExamService.getOralExamAverage(studentId));
    }
}
