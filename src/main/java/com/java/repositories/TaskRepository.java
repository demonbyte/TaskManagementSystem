package com.java.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	  
	// To get all tasks
	List<Task> findAll();
	
	// to get task by id
	Task findById(long id);
	
	//create or update task
	Task save(Task task);
	
	//Delete task based on Id
	void deleteById(long id);
	
	
	}
