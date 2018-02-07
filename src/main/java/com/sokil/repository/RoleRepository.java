package com.sokil.repository;

import com.sokil.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
