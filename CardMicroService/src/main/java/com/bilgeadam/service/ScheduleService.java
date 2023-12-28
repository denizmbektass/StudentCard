package com.bilgeadam.service;

import com.bilgeadam.manager.IScheduleManager;
import com.bilgeadam.manager.IUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

@Service
public class ScheduleService {

  private final IScheduleManager scheduleManager;
  private final IUserManager userManager;
  private final TaskScheduler taskScheduler;
  private ScheduledFuture<?> scheduledTask;

  private String cronExpression = "0 0 7 * * *";


  public ScheduleService(IScheduleManager scheduleManager, IUserManager userManager, TaskScheduler taskScheduler) {
	this.scheduleManager = scheduleManager;
	this.userManager = userManager;
	this.taskScheduler = taskScheduler;


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
	  scheduleManager.findAllAbsences();
	  userManager.getAllBaseStudents();
	}, newTrigger);
  }
}
