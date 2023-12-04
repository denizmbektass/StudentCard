package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateEducationWeightsRequestDto;
import com.bilgeadam.dto.request.CreateMainWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateEducationWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateMainWeightsRequestDto;
import com.bilgeadam.repository.entity.EducationWeights;
import com.bilgeadam.repository.entity.MainWeights;
import com.bilgeadam.service.EducationWeightsService;
import com.bilgeadam.service.MainWeightsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(EDUCATION_WEIGHTS)
@RequiredArgsConstructor
public class EducationWeightsController {
    private final EducationWeightsService educationWeightsService;

    @Operation(summary = "Gruplara göre ağırlık alma işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkların getirilmesi. Eğer grup yoksa entity içindeki default değerleri bize döndürecek.")
    @CrossOrigin("*")
    @GetMapping("/{groupName}")
    public EducationWeights getWeightsByGroupName(@PathVariable String groupName) {
        return educationWeightsService.getEducationWeightsByGroupName(groupName);
    }

    @Operation(summary = "Grup adına ağırlık kaydetme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini kaydeder.")
    @CrossOrigin("*")
    @PostMapping(SAVE_EDUCATION_WEIGHTS)
    public boolean saveWeightsByGroupName(@RequestBody CreateEducationWeightsRequestDto updateEducationWeightsRequestDto) {
        return educationWeightsService.saveWeights(updateEducationWeightsRequestDto);
    }

    @Operation(summary = "Grup adına ağırlık güncelleme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini günceller.")
    @CrossOrigin("*")
    @PutMapping(UPDATE_EDUCATION_WEIGHTS)
    public boolean updateWeightsByGroupName(@RequestBody UpdateEducationWeightsRequestDto updateEducationWeightsRequestDto) {
        return educationWeightsService.updateWeights(updateEducationWeightsRequestDto);
    }

    @Operation(summary = "Bütün Grupların ağırlığını getirme işlemi",
            description = "Bütün gruplara ait ağırlıkların getirilmesi. ")
    @CrossOrigin("*")
    @GetMapping(GET_ALL_EDUCATION_WEIGHTS)
    public List<EducationWeights> getWeights() {
        return educationWeightsService.findAll();
    }
}
