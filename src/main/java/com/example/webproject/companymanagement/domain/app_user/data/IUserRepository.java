package com.example.webproject.companymanagement.domain.app_user.data;

import com.example.webproject.companymanagement.domain.app_user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

//    @Query("SELECT t FROM user t WHERE t.role.name = EMPLOYEE")
//    Optional<List<User>> findAllEmployees();
}
