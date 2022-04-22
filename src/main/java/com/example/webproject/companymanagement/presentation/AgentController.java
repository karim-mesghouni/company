package com.example.webproject.companymanagement.presentation;

import com.example.webproject.companymanagement.domain.app_user.AgentService;
import com.example.webproject.companymanagement.domain.app_user.models.User;
import com.example.webproject.companymanagement.domain.app_user.models.Certification;
import com.example.webproject.companymanagement.domain.app_user.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class AgentController {
    private final AgentService agentService;

    @RolesAllowed(value = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_USER"})
    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
      return ResponseEntity.ok().body(agentService.getUsers());
    }

    @RolesAllowed(value = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_USER"})
    @GetMapping("{username}/user")
    public ResponseEntity<User> user(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(agentService.getUser(username));
    }

    @RolesAllowed(value = {"ROLE_ADMIN","ROLE_MANAGER","ROLE_USER"})
    @GetMapping("/user")
    public ResponseEntity<User> CurrentUser() {
        return ResponseEntity.ok().body(agentService.getCurrentUser());}
    @RolesAllowed(value = {"ROLE_MANAGER"})
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> allRoles() {
        return ResponseEntity.ok().body(agentService.getRoles());
    }

    @RolesAllowed(value = {"ROLE_MANAGER"})
    @GetMapping("/certifications")
    public ResponseEntity<List<Certification>> allCertifications() {
        return ResponseEntity.ok().body(agentService.getCertifications());
    }
    @RolesAllowed(value = {"ROLE_MANAGER"})
    @PostMapping("/")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok().body(agentService.addUser(user));
    }

//    @PostMapping("/roles")
//    public ResponseEntity<Role> addRole(@RequestBody Role role) {
//        return ResponseEntity.ok().body(agentService.addRole(role));
//    }

    @RolesAllowed(value = {"ROLE_MANAGER"})
    @PostMapping("{username}/certifications")
    public ResponseEntity<Certification> addCertification(@PathVariable("username") String username,@RequestBody Certification certification) {
       try {
           return ResponseEntity.ok().body(agentService.addCertification(username,certification));
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
    }

    @RolesAllowed(value = {"ROLE_MANAGER"})
    @PutMapping("{username}")
    public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody User user) {
        try {
            return ResponseEntity.ok().body(agentService.updateUser(username,user));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).header("error_message",e.getMessage()).build();
        }
    }
    @RolesAllowed(value = {"ROLE_MANAGER"})
    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        try {
            agentService.deleteUser(username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).header("error_message",e.getMessage()).build();
        }
    }
}
