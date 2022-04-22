package com.example.webproject.companymanagement.domain.app_user.models;


import com.example.webproject.companymanagement.core.domain.BaseEntity;


import com.example.webproject.companymanagement.domain.app_user.data.IUserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@SuperBuilder
@NoArgsConstructor
@Entity
@Table
@SelectBeforeUpdate
public class User extends BaseEntity {



    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

//    @OneToMany(
//            mappedBy = "user",
//            orphanRemoval = true, // to remove all certifications when remove this user,
//            cascade = CascadeType.ALL
//    )
//    private Collection<Certification> certifications;

//    public Collection<Certification> getCertifications() {
//        return certifications;
//    }
//
//    public void setCertifications(Collection<Certification> certifications) {
//        this.certifications = certifications;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * return the current request agent, the agent returned is detached and might not have any relations connected
     * if you want the database agent then use {@link User#thisDBUser() }
     *
     * @return the current logged-in agent extracted from the JWT
     * @see User#thisDBUser()
     */
    public static User thisAgent() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * @return the current request-token-agent retrieved from the database
     * @throws EntityNotFoundException if the corresponding agent_id in the request token does not exist in the database
     */
    public static void thisDBUser() throws EntityNotFoundException {

    }


}
