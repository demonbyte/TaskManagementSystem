package com.java.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.entity.Task;
import com.java.repositories.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	TaskRepository taskRepository;
	
	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<Task>();
		taskRepository.findAll().forEach(task -> tasks.add(task));
		return tasks;

	}
	
	
	public Task getTaskById(long id) {
	  return taskRepository.findById(id);
    }
	
	// Create new task
	public void createOrUpdateTask(Task task) {
		taskRepository.save(task);
	}
	
	//update task by id
	public void updateTaskById(long id, Task task) {
		taskRepository.save(task);
		
	}

	
	public void updatePartialTask(Task task) {
        Task taskobj = taskRepository.findById(task.getId());
        taskobj.setStatus(task.getStatus());
        taskRepository.save(taskobj);
    }
	
	
	
	//delete task based on id.
	public void deleteTaskById(long id) {
		taskRepository.deleteById(id);
		
	}
}
