package com.bilgeadam.service;

import com.bilgeadam.manager.IUserManager;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

@Service
public class ScheduleService {


  private final IUserManager userManager;
  private final TaskScheduler taskScheduler;
  private ScheduledFuture<?> scheduledTask;
  private final AbsenceService absenceService;

  private String cronExpression = "0 0 7 * * *";


  public ScheduleService(IUserManager userManager,TaskScheduler taskScheduler,AbsenceService absenceService) {

	this.userManager = userManager;
	this.taskScheduler = taskScheduler;
	this.absenceService = absenceService;

	scheduleTask(cronExpression);
  }

  public void updateScheduledTask(String newCronExpression) {

	String arrangedCronExpression ="0 "+newCronExpression.split(":")[1]+" " + newCronExpression.split(":")[0] + " * * *";

	if (scheduledTask != null) {
	  scheduledTask.cancel(false);
	}

	scheduleTask(arrangedCronExpression);
  }

  private void scheduleTask(String cronExpression) {
	CronTrigger newTrigger = new CronTrigger(cronExpression);

	scheduledTask = taskScheduler.schedule(() -> {
	  absenceService.getAllBaseAbsences();
	  userManager.getAllBaseStudents();
	}, newTrigger);
  }
  public void fetchData(){
	absenceService.getAllBaseAbsences();
	userManager.getAllBaseStudents();
  }
}
