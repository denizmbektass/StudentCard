package com.bilgeadam.manager;

import com.bilgeadam.dto.request.ChangePasswordRequestDto;
import com.bilgeadam.dto.response.ChangePasswordResponseDto;
import com.bilgeadam.dto.response.GetAuthInfoForChangePassword;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:4040/api/v1/auth",name = "user-auth")
public interface IAuthManager {

    @GetMapping("get-auth-info-for-user-change-password/{userId}")
    ResponseEntity<GetAuthInfoForChangePassword> getAuthInfoForChangePassword(@PathVariable String userId);

    @PostMapping("change-password-from-user")
    ResponseEntity<Boolean> changePasswordFromUser(@RequestBody ChangePasswordResponseDto dto);
}
