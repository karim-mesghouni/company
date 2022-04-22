package com.example.webproject.companymanagement.domain.task.task.models;

import com.example.webproject.companymanagement.domain.app_user.models.User;
import com.example.webproject.companymanagement.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table
@DynamicUpdate// only generate sql statement for changed columns

public class Task extends BaseEntity {


    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    private String description;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="assignedTo", nullable=false)
    private User assignedTo;



//    @OneToMany(mappedBy = "task",fetch = FetchType.EAGER)
//    private Collection<Situation> situations;
   //private Collection<Situation> situations;


}
