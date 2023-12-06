package com.bilgeadam.manager;

import com.bilgeadam.dto.request.SendStudentsRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "${base-api.url}/student",name = "user-base")
public interface IBaseManager {
    @GetMapping("/find-all-base-students")
    ResponseEntity<List<SendStudentsRequestDto>> findAllBaseStudents();
}
