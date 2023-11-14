package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationProcessRequestDto {
    @NotEmpty
    private String studentToken;
    @NotNull(message = "Bu alan boş bırakılamaz, lütfen 0-100 aralığında bir değer giriniz")
    private int jobApplicationScore;
    @NotNull(message = "Bu alan boş bırakılamaz, lütfen 0-100 aralığında bir değer giriniz")
    private int informationSharingScore;
    @NotNull(message = "Bu alan boş bırakılamaz, lütfen 0-100 aralığında bir değer giriniz")
    private int referralParticipationScore;
    @NotNull(message = "Bu alan boş bırakılamaz, lütfen 0-100 aralığında bir değer giriniz")
    private int companyFitScore;
    @NotNull(message = "Bu alan boş bırakılamaz, lütfen 0-100 aralığında bir değer giriniz")
    private int careerTeamAssessment;
}
