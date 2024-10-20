package com.java.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import com.java.entity.Task;
import com.java.entity.User;

import jakarta.mail.internet.MimeMessage;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true indicates HTML
            mailSender.send(message);
        } catch (Exception e) {
            // Handle the exception (e.g., logging)
        }
    }

    
    //when a task is createed or updated send email to emp/manager
    public void sendTaskNotificationEmail(User user, Task task, String action) {
        String subject = action + ": " + task.getTitle();
        
        // Prepare the Thymeleaf context
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("task", task);
        context.setVariable("action", action);
        
        // Render the template to a string
        String body = templateEngine.process("task-notification", context);

        sendEmail(user.getEmail(), subject, body);

        // Send email to the manager if exists
      /*  if (user.getManager() != null) { // Assuming User has a getManager() method
            sendEmail(user.getManager().getEmail(), subject, body);
        }*/
    }
    
    
    public void sendPendingTasksSummary(User user, List<Task> tasks) {
        String subject = "Daily Pending Tasks Summary";
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("tasks", tasks);

        String body = templateEngine.process("pending-tasks-summary", context);
        sendEmail(user.getEmail(), subject, body);
    }

    public void sendAdminSummary(Map<User, List<Task>> userTaskMap) {
        String subject = "Daily Task Summary for Admin";
        Context context = new Context();
        context.setVariable("userTaskMap", userTaskMap);

        String body = templateEngine.process("admin-task-summary", context);
        sendEmail("admin@example.com", subject, body);
    }

}

