package com.java.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.entity.User;
import com.java.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username);
              
    	if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        return UserDetailsImpl.build(user);
    }
    
    //Get list of all users
    
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	//*****************************************************************************
    
	// New method to assign a manager to an employee
    public void assignManager(String employee_name, String manager_name) {
        // Fetch employee and manager from the repository
        User employee = userRepository.findByUsername(employee_name);
                
       
         if (employee == null) {
             throw new RuntimeException("Employee not found");
         }
        
        User manager = userRepository.findByUsername(manager_name);
//                .orElseThrow(() -> new RuntimeException("Manager not found"));

        // Set the manager for the employee
        employee.setManager(manager);

        // Save the employee with the new manager
        userRepository.save(employee);
    }
	
	
}
