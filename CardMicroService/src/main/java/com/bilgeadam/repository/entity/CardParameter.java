package com.bilgeadam.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CardParameter extends BaseEntity{

    @Id
    private String cardParameterId;
    private String groupName;
    private Map<String,Integer> parameters;

}
