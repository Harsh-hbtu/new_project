package com.harsh.projectmanagementsystem.repository;

import com.harsh.projectmanagementsystem.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
