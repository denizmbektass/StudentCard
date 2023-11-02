package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateGameInterviewRequestDto;
import com.bilgeadam.dto.request.SaveGameInterviewRequestDto;
import com.bilgeadam.dto.request.UpdateGameInterviewRequestDto;
import com.bilgeadam.dto.response.GetGameInterviewResponseDto;
import com.bilgeadam.service.GameInterviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(GAME_INTERVIEW)
@RequiredArgsConstructor
public class GameInterviewController {

    private final GameInterviewService gameInterviewService;

    @Operation(summary = "Oyun mülakatı kaydetme işlemi",
            description = "Öğrencinin oyun mülakat verilerini kaydetmek için kullanılır. İlgili verileri içeren SaveInterviewRequestDto nesnesi, " +
                    "HTTP POST isteği ile gönderilir. Başarılı olursa true, aksi takdirde hata döner.")
    @PostMapping(SAVE)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> saveGameInterview(@RequestBody @Valid SaveGameInterviewRequestDto dto) {
        return ResponseEntity.ok(gameInterviewService.saveGameInterview(dto));
    }

    @Operation(summary = "Oyun mülakatı puanlarını getirir",
            description = "Öğrencinin oyun mülakat verilerini almak için kullanılır. İlgili öğrencinin kimliği," +
                    " URL'de yer alan 'studentId' parametresi ile belirtilir.")
    @GetMapping(GET_GAME_INTERVIEW + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<GetGameInterviewResponseDto> getGameInterview(@PathVariable String studentId) {
        return ResponseEntity.ok(gameInterviewService.getGameInterview(studentId));
    }

    @Operation(summary = "Oyun mülakatını günceller",
            description = "Öğrencinin oyun mülakat verilerini güncellemek için kullanılır. İlgili verileri içeren UpdateGameInterviewRequestDto nesnesi, " +
                    "HTTP PUT isteği ile gönderilir. Başarılı olursa true, aksi takdirde hata döner.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<Boolean> updateGameInterview(@RequestBody @Valid UpdateGameInterviewRequestDto dto) {
        return ResponseEntity.ok(gameInterviewService.updateGameInterview(dto));
    }

    @Operation(summary = "Oyun mülakatı sayısını getirir.",
            description = "Öğrencinin oyun mülakat sayısını almak için kullanılır. " +
                    "Öğrencinin kimliği, URL'de yer alan 'studentId' parametresi ile belirtilir.")
    @GetMapping(GET_GAME_INTERVIEW_NUMBER + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Integer> getGameInterviewNumber(@PathVariable String studentId) {
        return ResponseEntity.ok(gameInterviewService.getGameInterviewNumber(studentId));
    }

    @Operation(summary = "Oyun mülakatı ortalama puanını getirir.",
            description = "Öğrencinin oyun mülakatlarının ortalama puanını almak için kullanılır." +
                    " Öğrencinin kimliği, URL'de yer alan 'studentId' parametresi ile belirtilir.")
    @GetMapping(GET_GAME_INTERVIEW_AVERAGE_POINT + "/{studentId}")
    @CrossOrigin("*")
    public ResponseEntity<Double> getGameInterviewAveragePoint(@PathVariable String studentId) {
        return ResponseEntity.ok(gameInterviewService.getGameInterviewAveragePoint(studentId));
    }

    @Operation(summary = "Oyun mülakatını siler",
            description = "Öğrencinin oyun mülakat verilerini silmek için kullanılır. İlgili oyun mülakat ID'si, " +
                    "HTTP DELETE isteği ile gönderilir. Başarılı olursa true, aksi takdirde hata döner.")
    @DeleteMapping(DELETE)
    public ResponseEntity<Boolean> deleteGameInterview(@PathVariable String gameInterviewId) {
        boolean result = gameInterviewService.deleteGameInterview(gameInterviewId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Oyun mülakatı oluşturur",
            description = "Öğrencinin oyun mülakat verilerini oluşturmak için kullanılır. İlgili verileri içeren CreateGameInterviewRequestDto nesnesi, " +
                    "HTTP POST isteği ile gönderilir. Başarılı olursa true, aksi takdirde hata döner.")
    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createGameInterview(@RequestBody CreateGameInterviewRequestDto dto) {
        boolean result = gameInterviewService.createGameInterview(dto);
        return ResponseEntity.ok(result);


    }
}