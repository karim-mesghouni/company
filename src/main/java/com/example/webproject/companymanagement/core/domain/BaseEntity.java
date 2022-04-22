package com.example.webproject.companymanagement.core.domain;

import com.example.webproject.companymanagement.domain.app_user.models.User;
import com.example.webproject.companymanagement.core.domain.conserns.HasCreator;
import com.example.webproject.companymanagement.core.domain.conserns.HasId;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="creator")
    private User creator;

    @Override
    public Long getId() {
        return id;
    }


//
////    @Column(nullable = false, updatable = false)
////    @CreatedDate
////    protected LocalDateTime createdDate;
//
////    @CreatedBy
////    @NotFound(action = NotFoundAction.IGNORE)
////    User user;
//
//    @Override
//    public User getCreator() {
//        return getUser();
//    }
//
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        BaseEntity that = (BaseEntity) o;
//        return id != null && Objects.equals(that.id, id);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//
//    /**
//     * @return true if this entity was created by the given user
//     */
//    public boolean createdBy(User user) {
//        return user != null && user.equals(getCreator());
//    }
//
//    /**
//     * @return true if this entity was created by the user who sent this request
//     */
////    public boolean createdByThisAgent() {
////        return thisDBAgent().equals(getCreator());
////    }
//
////    public AppUser thisDBAgent() throws EntityNotFoundException {
////        return agentService.getUser();
////    }

}

