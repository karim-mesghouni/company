package com.example.webproject.companymanagement.domain.app_user.models;

import com.example.webproject.companymanagement.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Entity
@Table
@SelectBeforeUpdate
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;


//    @OneToMany(
//            mappedBy = "role",
//            fetch = FetchType.LAZY
//    )
//   private Collection<User> users ;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Collection<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Collection<User> users) {
//        this.users = users;
//    }
}
