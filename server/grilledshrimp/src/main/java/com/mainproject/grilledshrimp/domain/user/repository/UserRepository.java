package com.mainproject.grilledshrimp.domain.user.repository;

import com.mainproject.grilledshrimp.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
    Users findByUsername(String user_name);
    Users findByEmailAndPassword(String email, String password);
}
