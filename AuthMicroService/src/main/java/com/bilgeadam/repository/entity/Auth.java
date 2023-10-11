package com.bilgeadam.repository.entity;

import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Auth extends BaseEntity {
    @Id
    private String authId;
    private String userId;
    private String name;
    private String surname;
    /**
     * if two users have the same e-mail, DB will be found the first one, second one is never be shown.
     * Because of that we use the unique value for e-mails
     */
    @Indexed(unique = true)
    private String email;
    private String password;
    private List<ERole> role;
    @Builder.Default
    private EStatus status=EStatus.PASSIVE;
}
