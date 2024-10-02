package com.java.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.entity.Task;
import com.java.services.TaskService;

@RestController
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	// To get all tasks
	@GetMapping("/getTasks")
	public List<Task> getAllTasks(){
		return taskService.getAllTasks();
	}
	
	// to get task by id
	@GetMapping("/getTasks/{id}")
	public Task getTaskById(@PathVariable("id") long id){
		return taskService.getTaskById(id);
	}

	
	//create or update task 
	@PostMapping("/create-updateTask")
	public void createOrUpdateTask(@RequestBody Task task) {
		taskService.createOrUpdateTask(task);
	}
	
	
	
	
	
	
	
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<Task> updateTaskById(@PathVariable long id, @RequestBody Task task) {
		try {
			Task taskobj = taskService.getTaskById(id);
			if (taskobj.getTitle() != null)
				taskobj.setTitle(task.getTitle());
			if (taskobj.getDescription() != null)
			 taskobj.setDescription(task.getDescription());
			if (taskobj.getDueDate() != null)
			taskobj.setDueDate(task.getDueDate());
			if (taskobj.getAssignee() != null)
			taskobj.setAssignee(task.getAssignee());
			if (taskobj.getStatus() != null)
			taskobj.setStatus(task.getStatus());
			taskService.updateTaskById(id, taskobj);
			return new ResponseEntity<>(taskobj, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}
	
//Delete task based on Id
	@DeleteMapping("getTasks/{id}")
	public void deleteTaskById(long id) {
		taskService.deleteTaskById(id);
	}
	
	
	
}
