package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranscriptInfo {
    private String profilePicture;
    private String masterTrainer;
    private String assistantTrainer;
    private Date startDate;
    private Date endDate;
    private String group;

}
