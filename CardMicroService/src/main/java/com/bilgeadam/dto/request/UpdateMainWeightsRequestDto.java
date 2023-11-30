package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMainWeightsRequestDto {
    public String mainWeightsId;
    public String groupName;
    public Double studentChoiceWeight;
    public Double educationWeight;
    public Double internshipSuccessWeight;
    public Double employmentWeight;
}
