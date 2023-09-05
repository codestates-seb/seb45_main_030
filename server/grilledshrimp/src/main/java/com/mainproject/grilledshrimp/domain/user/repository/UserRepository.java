package com.mainproject.grilledshrimp.domain.user.repository;

import com.mainproject.grilledshrimp.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String user_name);
    Users findByEmailAndPassword(String email, String password);
}
