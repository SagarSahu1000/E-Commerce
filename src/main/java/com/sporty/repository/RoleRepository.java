package com.sporty.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sporty.model.Role;
import com.sporty.model.User;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	

}
