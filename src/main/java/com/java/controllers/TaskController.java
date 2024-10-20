package com.java.controllers;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.entity.Task;
import com.java.entity.User;
import com.java.entity.UserManager;
import com.java.repositories.UserRepository;
import com.java.services.TaskService;
import com.java.services.UserDetailsServiceImpl;
@RequestMapping("/api/tasks")
@RestController
public class TaskController {
	
	@Autowired
	TaskService taskService;
	

	
	@Autowired
    private UserDetailsServiceImpl userDetailsService;
	
	// To get all tasks
	@GetMapping("/admin-mangr/getTasks")
	public List<Task> getAllTasks(){
		System.out.println("get all tasks*****************");
		return taskService.getAllTasks();
	}

	@GetMapping("/usr/{userId}/tasks")
	public List<Task> getUserTasks(@PathVariable Long userId) {
	    System.out.println("get tasks for user with ID: " + userId);
	    return taskService.getTasksByUserId(userId);
	}
    //create task 
	@PostMapping("mangr/createTask")
	public void createOrUpdateTask(@RequestBody Map<String, Object> payload) {
		
		Task task = new Task();
		task.setTitle((String) payload.get("title"));
	    task.setDescription((String) payload.get("description"));
	    task.setDueDate(LocalDate.parse((String) payload.get("dueDate")));
	    task.setStatus((String) payload.get("status"));
	    task.setAssignee((String) payload.get("assignee"));

		
		String username = (String) payload.get("username");
	
	
		taskService.createOrUpdateTask(task,username, "Task Created");
	}
	
	//update task
	@PatchMapping("/usr/updateTask")
	public ResponseEntity<String> updateTask(@RequestBody Map<String, Object> payload) {
	    int taskId = (int) payload.get("taskId"); 
	    Optional<Task> existingTask = taskService.getTaskById(taskId);

	    if (!existingTask.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
	    }

	    Task task = existingTask.get();
	    // Update fields
//	    task.setTitle((String) payload.get("title"));
//	    task.setDescription((String) payload.get("description"));
//	    task.setDueDate(LocalDate.parse((String) payload.get("dueDate")));
//	    task.setStatus((String) payload.get("status"));
//	    task.setAssignee((String) payload.get("assignee"));
	    
	    // Update fields dynamically based on the provided keys in the updates map
	    if (payload.containsKey("title")) {
	        task.setTitle((String) payload.get("title"));
	    }
	    if (payload.containsKey("description")) {
	        task.setDescription((String) payload.get("description"));
	    }
	    if (payload.containsKey("dueDate")) {
	        task.setDueDate(LocalDate.parse((String) payload.get("dueDate")));
	    }
	    if (payload.containsKey("status")) {
	        task.setStatus((String) payload.get("status"));
	    }
	    if (payload.containsKey("assignee")) {
	        task.setAssignee((String) payload.get("assignee"));
	    }

	    
	    taskService.createOrUpdateTask(task, task.getUser().getUsername(), "Task Updated");  //getUsername()); // Update the task

	    return ResponseEntity.ok("Task updated successfully");
	}
	
	@DeleteMapping("getTasks/{id}")
	public void deleteTaskById(@PathVariable long id) {
		taskService.deleteTaskById(id);
	}
	
	//************************************************
	
//	@PostMapping("/assign-manager")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> assignManager(@RequestBody Map<String, Object> payload) {
//		        String employee_name = (String) payload.get("employee_name");
//		        String manager_name = (String) payload.get("manager_name");
//		        
//        userDetailsService.assignManager(employee_name, manager_name);
//        return ResponseEntity.ok("Manager assigned successfully");
//    }
//	
	@PostMapping("/assign-manager")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> assignManager(@RequestBody Map<String, Object> payload) {
	    String employeeName = (String) payload.get("employee_name");
	    String managerName = (String) payload.get("manager_name");
	    
	    userDetailsService.assignManager(employeeName, managerName);
//	    
//	    User employee = userService.findByUsername(employeeName)
//	            .orElseThrow(() -> new RuntimeException("Employee not found"));
//	    User manager = userService.findByUsername(managerName)
//	            .orElseThrow(() -> new RuntimeException("Manager not found"));
//	    
//	    UserManager userManager = new UserManager();
//	    userManager.setUser(employee);
//	    userManager.setManager(manager);
//	    
//	    userManagerRepository.save(userManager);  // Assuming you create a repository for the user-manager mapping
	    
	    return ResponseEntity.ok("Manager assigned successfully");
	}

	
	
	
	
//	// to get task by id
//	@GetMapping("/getTasks/{id}")
//	public Task getTaskById(@PathVariable long id){
//		return taskService.getTaskById(id);
//	}
	
	
//	@PatchMapping("/update/{id}")
//	public ResponseEntity<Task> updateTaskById(@PathVariable long id, @RequestBody Task task) {
//		try {
//			Task taskobj = taskService.getTaskById(id);
//			if (taskobj.getTitle() != null)
//				taskobj.setTitle(task.getTitle());
//			if (taskobj.getDescription() != null)
//			 taskobj.setDescription(task.getDescription());
//			if (taskobj.getDueDate() != null)
//			taskobj.setDueDate(task.getDueDate());
//			if (taskobj.getAssignee() != null)
//			taskobj.setAssignee(task.getAssignee());
//			if (taskobj.getStatus() != null)
//			taskobj.setStatus(task.getStatus());
//			taskService.updateTaskById(id, taskobj);
//			return new ResponseEntity<>(taskobj, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		}
//		
//	}
	
//Delete task based on Id
	
	
	
	
}
