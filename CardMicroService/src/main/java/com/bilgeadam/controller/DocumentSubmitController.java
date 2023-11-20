package com.bilgeadam.controller;

import com.bilgeadam.dto.request.SaveDocumentSubmitRequestDto;
import com.bilgeadam.dto.request.UpdateDocumentSubmitRequestDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.repository.entity.DocumentSubmit;
import com.bilgeadam.service.DocumentSubmitService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(DOCUMENT_SUBMIT)
public class DocumentSubmitController {
    private final DocumentSubmitService documentSubmitService;

    public DocumentSubmitController(DocumentSubmitService documentSubmitService){
        this.documentSubmitService=documentSubmitService;
    }
    @Operation(summary = "Evrak teslim puanını kaydetme işlemi", description = "Evrak teslim puanını yüzdeye göre " +
            "oranlar ve kaydeder")
    @PostMapping("save-document-submit")
    @CrossOrigin("*")
    public ResponseEntity<DocumentSubmit> saveDocumentSubmit(@RequestBody @Valid SaveDocumentSubmitRequestDto dto){
     return ResponseEntity.ok(documentSubmitService.saveDocumentSubmit(dto));
    }
    @Operation(summary = "Evrak teslim puanını görüntüleme işlemi", description = "Evrak teslim puanını studentid" +
            "üzerinden görüntüleme isteğine cevap verir")
    @GetMapping("get-document-submit/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<DocumentSubmit> getDocumentSubmitByStudentId(@PathVariable String studentId){
     return ResponseEntity.ok(documentSubmitService.getDocumentSubmitByStudentId(studentId));
    }
    @Operation(summary = "Evrak teslim puanını hesaplama işlemi", description = "Evrak teslim genel averaj puanını " +
            "evrak teslim puanına göre hesaplar")
    @GetMapping("calculate-document-submit/{documentSubmitScore}")
    @CrossOrigin("*")
    public ResponseEntity<Double> calculateDocumentSubmitAverageScore(@PathVariable int documentSubmitScore){
        return ResponseEntity.ok(documentSubmitService.calculateDocumentSubmitAverageScore(documentSubmitScore));
    }
    @Operation(summary = "Evrak teslim puanını günceleme işlemi", description = "Evrak teslim puanını gelen veriye" +
            "göre günceller")
    @PutMapping("update-document-submit")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateDocumentSubmitScore(@RequestBody UpdateDocumentSubmitRequestDto dto){
        return ResponseEntity.ok(documentSubmitService.updateDocumentSubmitScore(dto));
    }
}
