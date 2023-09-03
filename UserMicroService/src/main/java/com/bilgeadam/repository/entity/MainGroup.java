package com.bilgeadam.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class MainGroup extends BaseEntity{
    @Id
    private String mainGroupId;
    private String mainGroupName;
    private Set<String> groupNameList = new HashSet<>();

}
