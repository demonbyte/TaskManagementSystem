package com.java.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.java.entity.UserManager;

@Repository
public interface UserManagerRepository extends JpaRepository<UserManager, Long> {
}
