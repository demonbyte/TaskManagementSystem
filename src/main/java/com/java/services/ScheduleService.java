package com.java.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.java.entity.Task;
import com.java.entity.User;

import java.time.LocalDate;
import java.util.*;

@Service
public class ScheduleService {
	
	

	@Autowired
    private TaskService task;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
    private EmailService emailService;
	
	@Autowired
    private UserDetailsServiceImpl UserDetailsServiceImpl;
	
	
	
	
	   // Run every day at 8 AM
    @Scheduled(cron = "0 0 8 * * ?") // 8 am
//	@Scheduled(cron = "0 * * * * ?") 1min
//	@Scheduled(cron = "0 */5 * * * ?") 5 min
    public void sendDailySummaryEmails() {
        List<User> allUsers;
		try {
			allUsers = (List<User>) UserDetailsServiceImpl.getAllUsers();
		
        
        // Loop through each user and send pending task summary
        for (User user : allUsers) {
            List<Task> pendingTasks = taskService.getPendingTasksForUser(user.getId() );
            emailService.sendPendingTasksSummary(user, pendingTasks);
        }

        // Send admin summary
//        sendAdminSummary();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    // Collect all tasks and send summary to admin
    private void sendAdminSummary() {
        List<Task> allTasks = taskService.allUserTasks();
        Map<User, List<Task>> userTaskMap = taskService.groupTasksByUser(allTasks);
        emailService.sendAdminSummary(userTaskMap);
    }
	
}
