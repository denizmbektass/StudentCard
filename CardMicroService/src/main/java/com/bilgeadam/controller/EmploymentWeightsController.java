package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateEmploymentWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateEmploymentWeightsRequestDto;
import com.bilgeadam.repository.entity.EmploymentWeights;
import com.bilgeadam.service.EmploymentWeightsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(EMPLOYMENT_WEIGHTS)
@RequiredArgsConstructor
public class EmploymentWeightsController {
    private final EmploymentWeightsService employmentWeightsService;

    @Operation(summary = "Gruplara göre ağırlık alma işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkların getirilmesi. Eğer grup yoksa entity içindeki default değerleri bize döndürecek.")
    @CrossOrigin("*")
    @GetMapping("/{groupName}")
    public EmploymentWeights getWeightsByGroupName(@PathVariable String groupName) {
        return employmentWeightsService.getWeightsByGroupName(groupName);
    }

    @Operation(summary = "Grup adına ağırlık kaydetme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini kaydeder.")
    @CrossOrigin("*")
    @PostMapping(SAVE_EMPLOYMENT_WEIGHTS)
    public boolean saveWeightsByGroupName(@RequestBody CreateEmploymentWeightsRequestDto createEmploymentWeightsRequestDto) {
        return employmentWeightsService.saveWeights(createEmploymentWeightsRequestDto);
    }

    @Operation(summary = "Grup adına ağırlık güncelleme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini günceller.")
    @CrossOrigin("*")
    @PutMapping(UPDATE_EMPLOYMENT_WEIGHTS)
    public boolean updateWeightsByGroupName(@RequestBody UpdateEmploymentWeightsRequestDto updateEmploymentWeightsRequestDto) {
        return employmentWeightsService.updateWeights(updateEmploymentWeightsRequestDto);
    }

    @Operation(summary = "Bütün Grupların ağırlığını getirme işlemi",
            description = "Bütün gruplara ait ağırlıkların getirilmesi. ")
    @CrossOrigin("*")
    @GetMapping(GET_ALL_EMPLOYMENT_WEIGHTS)
    public List<EmploymentWeights> getWeights() {
        return employmentWeightsService.findAll();
    }
}
