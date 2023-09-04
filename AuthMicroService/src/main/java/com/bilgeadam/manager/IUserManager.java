package com.bilgeadam.manager;

import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.GetAuthInfoForChangePassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:4041/api/v1/user",name = "auth-user")
public interface IUserManager {
    @PostMapping("save-manager-for-user-service")
    ResponseEntity<String> registerManagerForUser(RegisterRequestDto dto);
}
