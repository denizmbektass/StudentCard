package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateStudentChoiceWeightsRequestDto;
import com.bilgeadam.dto.request.UpdateStudentChoiceWeightsRequestDto;
import com.bilgeadam.repository.entity.StudentChoiceWeights;
import com.bilgeadam.service.StudentChoiceWeightsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constants.ApiUrls.*;

@RestController
@RequestMapping(STUDENT_CHOICE_WEIGHTS)
@RequiredArgsConstructor
public class StudentChoiceWeightsController {
    private final StudentChoiceWeightsService studentChoiceWeightsService;

    @Operation(summary = "Gruplara göre ağırlık alma işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkların getirilmesi. Eğer grup yoksa entity içindeki default değerleri bize döndürecek.")
    @CrossOrigin("*")
    @GetMapping("/{groupName}")
    public StudentChoiceWeights getWeightsByGroupName(@PathVariable String groupName) {
        return studentChoiceWeightsService.getWeightsByGroupName(groupName);
    }

    @Operation(summary = "Grup adına ağırlık kaydetme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini kaydeder.")
    @CrossOrigin("*")
    @PostMapping(SAVE_STUDENT_CHOICE_WEIGHTS)
    public boolean saveWeightsByGroupName(@RequestBody CreateStudentChoiceWeightsRequestDto choiceWeightsRequestDto) {
        return studentChoiceWeightsService.saveWeights(choiceWeightsRequestDto);
    }

    @Operation(summary = "Grup adına ağırlık güncelleme işlemi",
            description = "Grup adı kullanılarak o gruba ait ağırlıkl bilgilerini günceller.")
    @CrossOrigin("*")
    @PutMapping(UPDATE_STUDENT_CHOICE_WEIGHTS)
    public boolean updateWeightsByGroupName(@RequestBody UpdateStudentChoiceWeightsRequestDto choiceWeightsRequestDto) {
        return studentChoiceWeightsService.updateWeights(choiceWeightsRequestDto);
    }

    @Operation(summary = "Bütün Grupların ağırlığını getirme işlemi",
            description = "Bütün gruplara ait ağırlıkların getirilmesi. ")
    @CrossOrigin("*")
    @GetMapping(GET_ALL_STUDENT_CHOICE_WEIGHTS)
    public List<StudentChoiceWeights> getWeights() {
        return studentChoiceWeightsService.findAll();
    }

}