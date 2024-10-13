package com.java.services;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.java.entity.Role;
import com.java.entity.Task;
import com.java.entity.User;
import com.java.repositories.TaskRepository;
import com.java.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
    private EmailService emailService;
	
//	public List<Task> getAllTasks() {
//		List<Task> tasks = new ArrayList<Task>();
//		taskRepository.findAll().forEach(task -> tasks.add(task));
//		
//		
//		return tasks;
//
//	}
	
	
    public List<Task> getAllTasks() {
        // Get the authenticated user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        
        System.out.println("username: "+username+ "=====================");
        User user = userRepository.findByUsername(username);
        
        UserDetails userDetails = (UserDetails) principal;
    
        if(userDetails.getAuthorities().stream().anyMatch(authority ->
        authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_MANAGER"))) {
       
        	System.out.println("user is admin or manager");
            // If user is an admin or manager, return all tasks
            return taskRepository.findAll();
        } else {
        	System.out.println("user is employee");
            // Otherwise, return tasks assigned to the user
            return taskRepository.findByUser(user);
        }
    }
	
//	public Task getTaskById(long id) {
//	  return taskRepository.findById(id);
//    }
	
	// Create new task
	public void createOrUpdateTask(Task task, String username) {
		User user = userRepository.findByUsername(username);
	    
	    if (user== null) {
	        throw new IllegalArgumentException("User with username " + username + " not found");
	    }
		
		

	    // Set the user to the task - This will correctly set the foreign key in the `task` table
	    task.setUser(user);

		taskRepository.save(task);
		
		emailService.sendTaskNotificationEmail(user, task);
	}
	

//	public void updateTaskById(long id, Task task) {
//		taskRepository.save(task);
//		
//	}
	
	public Optional<Task> getTaskById(int taskId) {
	    return taskRepository.findById(taskId);
	}
//
//	
//	public void updatePartialTask(Task task) {
//        Task taskobj = taskRepository.findById(task.getId());
//        taskobj.setStatus(task.getStatus());
//        taskRepository.save(taskobj);
//    }
	
	
	
	//delete task based on id.
	@Transactional
	public void deleteTaskById(long id) {
		taskRepository.deleteById(id);
		
	}
	
    public List<Task> getPendingTasksForUser(int userId) {
        return taskRepository.findByUserAndStatus(userRepository.findById(userId).orElse(null), "In Progress");
    }
    
    public Map<User, List<Task>> groupTasksByUser(List<Task> tasks) {
        return tasks.stream().collect(Collectors.groupingBy(Task::getUser));
    }
    
	public List<Task> allUserTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().filter(task -> task.getStatus().equals("PENDING")).collect(Collectors.toList());
    }
}
