package com.example.webproject.companymanagement.domain.app_user;

import com.example.webproject.companymanagement.domain.app_user.data.CertificationRepository;
import com.example.webproject.companymanagement.domain.app_user.data.IRoleRepository;
import com.example.webproject.companymanagement.domain.app_user.data.IUserRepository;
import com.example.webproject.companymanagement.domain.app_user.models.User;
import com.example.webproject.companymanagement.domain.app_user.models.Certification;
import com.example.webproject.companymanagement.domain.app_user.models.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j(topic = "AgentService")
public class AgentService {
    private final IUserRepository userRepo;
    private final IRoleRepository roleRepo;
    private final CertificationRepository certificationRepo;


    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        List<User> list = userRepo.findAll();
        list.forEach(user -> user.setPassword(""));
        return list;
    }


    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    public List<Certification> getCertifications() {
        return certificationRepo.findAll();
    }

    public User getUser(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
           // user.get().setPassword("");
            return user.get();
        }
        throw new UsernameNotFoundException("Can not find user by the given username");
    }

    public User getCurrentUser() {
       // System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = User.thisAgent();
        log.info("Current user {}", user);
        Optional<User> optional = userRepo.findByUsername(user.getUsername());
        optional.orElseThrow(EntityNotFoundException::new);
      //  optional.get().setPassword("");
        return optional.get();
    }

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public void deleteUser(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        user.orElseThrow(EntityNotFoundException::new);
        userRepo.delete(user.get());
    }

    @Transactional
    public User updateUser(String username, @NotNull User user) {
        Optional<User> optional = userRepo.findByUsername(username);
        Optional<Role> role = roleRepo.findById(user.getRole().getId());

        User u = optional.get();
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        u.setEmail(user.getEmail());
        u.setUsername(user.getUsername());
        u.setRole(role.get());
        u.setDateOfBirth(user.getDateOfBirth());
        return u;
    }


    public Role addRole(Role role) {
        return roleRepo.save(role);
    }


    public void deleteRole(Role role) {
        roleRepo.delete(role);
    }

    public Certification addCertification(String username, Certification certification) {
        User user = userRepo.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        certification.setUser(user);
        return certificationRepo.save(certification);
    }


    @Transactional
    public void addRoleToUser(String username, String roleName) {
        Optional<User> user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);

            user.get().setRole(role);

    }

//   public void addCertificationToUser(String username, Long certificationId) {
//      var user = userRepo.findByUsername(username);
//      var certification = certificationRepo.findById(certificationId);
//      user.ifPresentOrElse((u) -> {
//         u.getCertifications().add(certification.get());
//      }, () -> {
//         throw new UsernameNotFoundException("Can not find user by the given username");
//      });
//   }
}
