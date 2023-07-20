package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class Interview extends BaseEntity{
    @Id
    private String interviewId;
    private String name;
    private long score;
    private String description;
    private String studentId;
    private String interviewType;
    @Builder.Default()
    private EStatus eStatus = EStatus.ACTIVE;
    //private int trainerId,hrId;

}
