package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateInternshipSuccessScoreWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateInternshipSuccsessScoreWeightsRequestDto;
import com.bilgeadam.repository.entity.InternshipSuccessScoreWeights;
import com.bilgeadam.service.InternshipSuccessScoreWeightsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(INTERNSHIP_SUCCESS_SCORE_WEIGHTS)
@RequiredArgsConstructor
public class InternshipSuccessScoreWeightsController {
    private final InternshipSuccessScoreWeightsService internshipSuccessScoreWeightsService;

    @Operation(summary = "Gruplara göre ağırlık alma işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkların getirilmesi. Eğer grup yoksa entity içindeki default değerleri bize döndürecek.")
    @CrossOrigin("*")
    @GetMapping("/{groupName}")
    public InternshipSuccessScoreWeights getWeightsByGroupName(@PathVariable String groupName) {
        return internshipSuccessScoreWeightsService.getWeightsByGroupName(groupName);
    }

    @Operation(summary = "Grup adına ağırlık kaydetme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini kaydeder.")
    @CrossOrigin("*")
    @PostMapping(SAVE_INTERNSHIP_SUCCESS_SCORE_WEIGHTS)
    public boolean saveWeightsByGroupName(@RequestBody CreateInternshipSuccessScoreWeightsRequestDto createInternshipSuccessScoreWeightsRequestDto) {
        return internshipSuccessScoreWeightsService.saveWeights(createInternshipSuccessScoreWeightsRequestDto);
    }

    @Operation(summary = "Grup adına ağırlık güncelleme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini günceller.")
    @CrossOrigin("*")
    @PutMapping(UPDATE_INTERNSHIP_SUCCESS_SCORE_WEIGHTS)
    public boolean updateWeightsByGroupName(@RequestBody UpdateInternshipSuccsessScoreWeightsRequestDto updateInternshipSuccsessScoreWeightsRequestDto) {
        return internshipSuccessScoreWeightsService.updateWeights(updateInternshipSuccsessScoreWeightsRequestDto);
    }

    @Operation(summary = "Bütün Grupların ağırlığını getirme işlemi",
            description = "Bütün gruplara ait ağırlıkların getirilmesi. ")
    @CrossOrigin("*")
    @GetMapping(GET_ALL_INTERNSHIP_SUCCESS_SCORE_WEIGHTS)
    public List<InternshipSuccessScoreWeights> getWeights() {
        return internshipSuccessScoreWeightsService.findAll();
    }
}
