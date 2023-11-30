package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateMainWeightsRequestDto;
import com.bilgeadam.repository.entity.MainWeights;
import com.bilgeadam.service.MainWeightsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(MAIN_WEIGHTS)
@RequiredArgsConstructor
public class MainWeightsController {
    private final MainWeightsService mainWeightsService;

    @Operation(summary = "Gruplara göre ağırlık alma işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkların getirilmesi. Eğer grup yoksa entity içindeki default değerleri bize döndürecek.")
    @CrossOrigin("*")
    @GetMapping("/{groupName}")
    public MainWeights getWeightsByGroupName(@PathVariable String groupName) {
        return mainWeightsService.getWeightsByGroupName(groupName);
    }

    @Operation(summary = "Grup adına ağırlık kaydetme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini kaydeder.")
    @CrossOrigin("*")
    @PostMapping(SAVE_MAIN_WEIGHTS)
    public boolean saveWeightsByGroupName(@RequestBody CreateMainWeightsRequestDto mainWeightsRequestDto) {
        return mainWeightsService.saveWeights(mainWeightsRequestDto);
    }

    @Operation(summary = "Grup adına ağırlık güncelleme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini günceller.")
    @CrossOrigin("*")
    @PutMapping(UPDATE_MAIN_WEIGHTS)
    public boolean updateWeightsByGroupName(@RequestBody CreateMainWeightsRequestDto mainWeightsRequestDto) {
        return mainWeightsService.saveWeights(mainWeightsRequestDto);
    }

    @Operation(summary = "Bütün Grupların ağırlığını getirme işlemi",
            description = "Bütün gruplara ait ağırlıkların getirilmesi. ")
    @CrossOrigin("*")
    @GetMapping(GET_ALL_MAIN_WEIGHTS)
    public List<MainWeights> getWeights() {
        return mainWeightsService.findAll();
    }
}
