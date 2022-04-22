package com.example.webproject.companymanagement.domain.task.task.data;

import com.example.webproject.companymanagement.domain.task.task.models.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISituationRepository extends JpaRepository<Situation,Long> {
    @Query("SELECT t FROM Situation t WHERE t.task.id = :task_id")
    List<Situation> findByTask(@Param("task_id") Long id);
}
