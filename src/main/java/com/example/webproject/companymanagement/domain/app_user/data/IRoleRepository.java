package com.example.webproject.companymanagement.domain.app_user.data;

import com.example.webproject.companymanagement.domain.app_user.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
