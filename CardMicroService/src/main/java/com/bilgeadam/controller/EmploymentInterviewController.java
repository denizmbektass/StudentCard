package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateEmploymentInterviewRequestDto;
import com.bilgeadam.dto.request.UpdateEmploymentInterviewRequestDto;
import com.bilgeadam.dto.response.GetEmploymentInterviewResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.service.EmploymentInterviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.bilgeadam.constants.ApiUrls.*;


@RestController
@RequestMapping(EMPLOYMENT_INTERVIEW)
@RequiredArgsConstructor
public class EmploymentInterviewController {
    private final EmploymentInterviewService employmentInterviewService;

    @Operation(summary = "Mülakat Olusturma işlemi",
            description = "Belirtilen DTO kullanılarak bir Mülakat olusturur.")
    @PostMapping(CREATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> createEmploymentInterview(@RequestBody @Valid CreateEmploymentInterviewRequestDto dto){
        return ResponseEntity.ok(employmentInterviewService.createEmploymentInterview(dto));
    }
    @Operation(summary = "Mülakat güncelleme işlemi",
            description = "Belirtilen DTO kullanılarak  Mülakat güncellenir.")
    @PutMapping(UPDATE)
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> updateEmploymentInterview(@RequestBody @Valid UpdateEmploymentInterviewRequestDto dto){
        return  ResponseEntity.ok(employmentInterviewService.updateEmploymentInterview(dto));
    }
    @Operation(summary = "Mülakat silme işlemi",
            description = "Belirtilen token kullanılarak  var olan mülakat silinir.")
    @DeleteMapping(DELETE+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<MessageResponse> deleteEmploymentInterview(@PathVariable String token){
        return ResponseEntity.ok(employmentInterviewService.deleteEmploymentInterview(token));
    }
    @Operation(summary = "Mülakatlari getirme işlemi",
            description = "Belirtilen token kullanılarak  var olan mülakatlari getirir.")
    @GetMapping(GET_EMPLOYMENT_INTERVIEW+"/{token}")
    @CrossOrigin("*")
    public ResponseEntity<GetEmploymentInterviewResponseDto> getEmploymentInterview(@PathVariable String token){
        return ResponseEntity.ok(employmentInterviewService.getEmploymentInterview(token));
    }

}
