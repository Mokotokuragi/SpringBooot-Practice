package com.springboot.Springbootpracticerestapi.repository;

import com.springboot.Springbootpracticerestapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
