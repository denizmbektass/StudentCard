package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
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
public class Algorithm extends BaseEntity{
    @Id
    private String algorithmId;
    private String studentId;
    private Double firstScore;
    private Double secondScore;
    private Double finalScore;
    private boolean isExempt;
}
