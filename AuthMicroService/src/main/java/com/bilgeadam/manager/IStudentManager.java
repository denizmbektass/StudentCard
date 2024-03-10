package com.bilgeadam.manager;

import com.bilgeadam.dto.request.RegisterRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:4041/api/v1/student",name = "auth-student")
public interface IStudentManager {

    @PostMapping("save-manager-for-student-service")
    ResponseEntity<String> registerManagerForStudent(RegisterRequestDto dto);

}
