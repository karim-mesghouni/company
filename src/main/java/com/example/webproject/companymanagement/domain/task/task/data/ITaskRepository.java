package com.example.webproject.companymanagement.domain.task.task.data;

import com.example.webproject.companymanagement.domain.task.task.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITaskRepository extends JpaRepository<Task,Long> {
    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :assignedTo")
    List<Task> findByUserId(@Param("assignedTo") Long id);

    @Query("SELECT t FROM Task t WHERE t.creator.id = :creator")
    List<Task> findByCreator(@Param("creator") Long id);
}
