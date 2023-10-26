package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class TrainerAssessmentCoefficients extends BaseEntity {

    //@Builder.Default
    public static double BEHAVIOR_IN_CLASS_COEFFICIENT = 0.20;
    public static double COURSE_INTEREST_LEVEL_COEFFICIENT = 0.20;
    public static double CAMERA_OPENING_RATE_COEFFICIENT = 0.10;
    public static double INSTRUCTOR_GRADE_RATE_COEFFICIENT = 0.35;
    public static double DAILY_HOMEWORK_RATE_COEFFICIENT = 0.15;

    @Id
    private String trainerAssessmentCoefficientsId;

    private String groupId;

    @Builder.Default
    private EStatus eStatus=EStatus.ACTIVE;


}
