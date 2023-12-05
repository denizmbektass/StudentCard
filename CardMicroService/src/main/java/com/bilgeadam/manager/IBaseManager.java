package com.bilgeadam.manager;

import com.bilgeadam.dto.request.SendAbsenceRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "http://localhost:8081/absence",name = "absence-base")
public interface IBaseManager {

    @GetMapping("/find-all-base-absences")
    ResponseEntity<List<SendAbsenceRequestDto>> findAllAbsences();

}
