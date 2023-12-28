package com.bilgeadam.manager;

import com.bilgeadam.dto.request.SendAbsenceRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "schedule-base", url = "${base-api.url}")
public interface IScheduleManager {

  @GetMapping("/absence/find-all-base-absences")
  ResponseEntity<List<SendAbsenceRequestDto>> findAllAbsences();


}