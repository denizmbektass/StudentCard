package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerAssesmentMailReminderRequestDto  {
    private String token;
    private String email;
    private String month;
    private String groupName;
}
