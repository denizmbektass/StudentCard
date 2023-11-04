package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateApplicationProcessRequestDto;
import com.bilgeadam.dto.request.UpdateApplicationProcessRequestDto;
import com.bilgeadam.dto.response.GetApplicationProcessResponseDto;
import com.bilgeadam.service.ApplicationProcessService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(APPLICATION_PROCESS)
public class ApplicationProcessController {
    private final ApplicationProcessService applicationProcessService;

    @Operation(summary = "Başvuru süreci kaydetme işlemi", description = "Dto'dan alınan bilgilerle yeni bir başvuru süreci oluşturulur.")
    @PostMapping(SAVE)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> save(@RequestBody CreateApplicationProcessRequestDto dto) {
        return ResponseEntity.ok(applicationProcessService.save(dto));
    }
    @Operation(summary = "Başvuru süreci bulma işlemi", description = "Mevcut başvuru sürecinı bulma işlemi.")
    @GetMapping(FIND_BY_ID+ "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<GetApplicationProcessResponseDto> findApplicationProcessById(@PathVariable String studentId){
        return ResponseEntity.ok(applicationProcessService.findApplicationProcessById(studentId));
    }
    @Operation(summary = "Başvuru süreci güncelleme işlemi", description = "Dto'dan alınan yeni verilerle, eski verilerin güncellenmesi işlemi yapılır.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> update(@RequestBody UpdateApplicationProcessRequestDto dto) {
        return ResponseEntity.ok(applicationProcessService.update(dto));
    }

    @Operation(summary = "Başvuru süreci silme işlemi", description = "Token'dan elde edilen id bilgisi ile verilerin silinmesi işlemi yapılır.")
    @DeleteMapping(DELETE + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Boolean> delete(@PathVariable String studentId) {
        return ResponseEntity.ok(applicationProcessService.delete(studentId));
    }

    @Operation(summary = "Başvuru süreci toplam skoru hesaplama işlemi",
            description = "studentId ile tüm degerlere erişilir ve toplam skor bulunur. ")
    @GetMapping(APPLICATION_PROCESS_TOTAL_SCORE + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Double> calculateApplicationProcessRate(@PathVariable String studentId) {
        return ResponseEntity.ok(applicationProcessService.calculateApplicationProcessRate(studentId));
    }
}
