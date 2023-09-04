package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class InternshipGroup extends BaseEntity {
    @Id
    private String internShipGroupId;
    private String mainGroupId;
    private String groupName;
    private Date startingDate;
    private Date endingDate;
}
