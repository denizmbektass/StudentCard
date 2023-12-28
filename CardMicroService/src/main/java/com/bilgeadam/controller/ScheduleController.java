package com.bilgeadam.controller;

import com.bilgeadam.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
	// Perform validation on scheduledTime if needed
	scheduleService.updateScheduledTask(scheduledTime);
	return "Scheduled time updated successfully";
  }
}
