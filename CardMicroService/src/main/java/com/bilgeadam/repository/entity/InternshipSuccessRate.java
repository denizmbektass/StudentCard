package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class InternshipSuccessRate extends BaseEntity {
    @Id
    private String internshipSuccessRateId;
    private Long score; // Participation Rate
    private String comment;
    private String userId;
}
