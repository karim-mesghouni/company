package com.example.webproject.companymanagement.domain.app_user.models;

import com.example.webproject.companymanagement.core.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table
@DynamicUpdate// only generate sql statement for changed columns
public class Certification extends BaseEntity {

    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    private String source;
    @NotBlank
    @NotNull
    private String level;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id")
    private User user;
}
