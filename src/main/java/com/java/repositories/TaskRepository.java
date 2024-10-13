package com.java.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.entity.Task;
import com.java.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
	  
	// To get all tasks
	List<Task> findAll();
	
	// to get task by id
	Optional<Task> findById(long id);
	
	//create or update task
	Task save(Task task);
	
	//Delete task based on Id
	void deleteById(long id);
	
	List<Task> findByUser(User user);
	

    List<Task> findByUserAndStatus(User user, String status);
    
   
	
	}
