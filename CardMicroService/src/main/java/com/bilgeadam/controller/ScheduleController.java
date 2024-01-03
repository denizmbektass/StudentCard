package com.bilgeadam.controller;

import com.bilgeadam.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constants.ApiUrls.SCHEDULE;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(SCHEDULE)
public class ScheduleController {

  private final ScheduleService scheduleService;

  @PostMapping("/setScheduledTime")
  public String setScheduledTime(@RequestParam String scheduledTime) {

	scheduleService.updateScheduledTask(scheduledTime);
	return "Scheduled time updated successfully";
  }

  @GetMapping("/fetch-data")
  public ResponseEntity<Void> fetchData() {
	scheduleService.fetchData();
	return ResponseEntity.ok().build();
  }
}
