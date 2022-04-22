package com.example.webproject.companymanagement.presentation;

import com.example.webproject.companymanagement.domain.task.task.TaskService;
import com.example.webproject.companymanagement.domain.task.task.models.Situation;
import com.example.webproject.companymanagement.domain.task.task.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

//    @RolesAllowed(value = {"ROLE_ADMIN"})
//    @GetMapping("{username}/admin")
//    public ResponseEntity<List<Task>> allEmployeeTasks(@PathVariable("username") String username){
//        return ResponseEntity.ok().body(service.getAdminTasks(username));
//    }

//    @RolesAllowed(value = {"ROLE_EMPLOYEE"})
//    @GetMapping("{username}/employee")
//    public ResponseEntity<List<Task>> allAdminTasks(@PathVariable("username") String username){
//        return ResponseEntity.ok().body(service.getEmployeeTasks(username));
//    }
    @RolesAllowed(value = {"ROLE_EMPLOYEE","ROLE_ADMIN"})
    @GetMapping("/")
    public ResponseEntity<List<Task>> tasks(){
        return ResponseEntity.ok().body(service.getCurrentUserTasks());
    }

    @RolesAllowed(value = {"ROLE_ADMIN","ROLE_EMPLOYEE"})
    @GetMapping("{id}/task")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok().body(service.getTask(id));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/")
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        return ResponseEntity.ok().body(service.addTask(task));
    }

    @RolesAllowed(value = {"ROLE_ADMIN","ROLE_EMPLOYEE"})
    @PostMapping("{id}/situation")
    public ResponseEntity<Situation> addSituation(@PathVariable("id") Long taskId ,@RequestBody Situation situation){
            try {
                return  ResponseEntity.ok().body(service.createSituation(taskId, situation));

            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }
    @RolesAllowed(value = {"ROLE_ADMIN","ROLE_EMPLOYEE"})
    @GetMapping("{id}/situations")
    public ResponseEntity<List<Situation>> situation(@PathVariable("id") Long taskId ){
        return ResponseEntity.ok().body(service.getTaskSituation(taskId));
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id,@RequestBody Task task){
        try {
            return ResponseEntity.ok().body(service.updateTask(id,task));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id){
        try {
            service.deleteTask(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
