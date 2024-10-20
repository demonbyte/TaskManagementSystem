//package com.java.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.*;
//
//import java.util.*;
//
//import jakarta.persistence.*;
//
//@Entity
//	@Table(name = "users",
//	        uniqueConstraints = {
//	                @UniqueConstraint(columnNames = "username"),
//	                @UniqueConstraint(columnNames = "email")
//	        })
//	public class User {
//	    @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    private Integer id;
//
//	    @NotBlank
//	    @Size(max = 20)
//	    private String username;
//
//	    @NotBlank
//	    @Size(max = 50)
//	    @Email
//	    private String email;
//
//	    @NotBlank
//	    @Size(max = 120)
//	    private String password;
//
//	    @ManyToMany(fetch = FetchType.LAZY)
//	    @JoinTable(name = "user_roles",
//	            joinColumns = @JoinColumn(name = "user_id"),
//	            inverseJoinColumns = @JoinColumn(name = "role_id"))
//	    private Set<Role> roles = new HashSet<>();
//
//	    public User() {
//	    }
//
//	    public User(String username, String email, String password) {
//	        this.username = username;
//	        this.email = email;
//	        this.password = password;
//	    }
//
//	    public Integer getId() {
//	        return id;
//	    }
//
//	    public void setId(Integer id) {
//	        this.id = id;
//	    }
//
//	    public String getUsername() {
//	        return username;
//	    }
//
//	    public void setUsername(String username) {
//	        this.username = username;
//	    }
//
//	    public String getEmail() {
//	        return email;
//	    }
//
//	    public void setEmail(String email) {
//	        this.email = email;
//	    }
//
//	    public String getPassword() {
//	        return password;
//	    }
//
//	    public void setPassword(String password) {
//	        this.password = password;
//	    }
//
//	    public Set<Role> getRoles() {
//	        return roles;
//	    }
//
//	    public void setRoles(Set<Role> roles) {
//	        this.roles = roles;
//	    }
//
//}

package com.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
  
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	private String email;


    private String username;

    
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Task> tasks; 

    // Eager as we want to get the roles when we get the user.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "username"), 
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    public User() {}
    
    public User(String username, String email, String password) {
    	
        this.username = username;
        this.email = email;
        this.password = password;
    }
//////    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "manager_id")
//    private User manager;  // Manager of this user
//
//    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<User> employees = new HashSet<>();  // Employees under this manager

    // Other fields and getters/setters
//
//    public User getManager() {
//        return manager;
//    }
//
//    public void setManager(User manager) {
//        this.manager = manager;
//    }
//
//    public Set<User> getEmployees() {
//        return employees;
//    }
//
//    public void setEmployees(Set<User> employees) {
//        this.employees = employees;
//    }
    
    // Getters and setters

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

