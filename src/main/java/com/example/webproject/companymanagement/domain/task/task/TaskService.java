package com.example.webproject.companymanagement.domain.task.task;

import com.example.webproject.companymanagement.core.domain.enums.State;
import com.example.webproject.companymanagement.domain.app_user.data.IUserRepository;
import com.example.webproject.companymanagement.domain.app_user.models.User;
import com.example.webproject.companymanagement.domain.task.task.data.ISituationRepository;
import com.example.webproject.companymanagement.domain.task.task.data.ITaskRepository;
import com.example.webproject.companymanagement.domain.task.task.models.Situation;
import com.example.webproject.companymanagement.domain.task.task.models.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final ITaskRepository repository;

    private final IUserRepository userRepo;
    private final ISituationRepository situationRepository;

    public Task addTask(Task task) {
         Task t = repository.save(task);
         var situation = Situation.builder().task(t).message("").state(State.TODO).build();
         situationRepository.save(situation);
         return t;
    }

    @Transactional
    public Task updateTask(Long id,Task task) throws  EntityNotFoundException{
        Optional<Task> optional = repository.findById(id);
        optional.orElseThrow();
        var user = userRepo.findByUsername(task.getAssignedTo().getUsername());
        user.orElseThrow();
        var t = optional.get();
        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setAssignedTo(user.get());
        return t;
    }

    public Situation createSituation(Long taskId,Situation situationR){
        Task task =  repository.findById(taskId).orElseThrow(EntityNotFoundException::new);
        situationR.setTask(task);
        return situationRepository.save(situationR);
    }

    public List<Task> getEmployeeTasks(String username){
        Optional<User> user = userRepo.findByUsername(username);
        user.orElseThrow();
        return repository.findByUserId(user.get().getId());
    }

    public List<Task> getCurrentUserTasks(){
        var user = User.thisAgent();
        if (user.getRole().getName().equals("ROLE_EMPLOYEE")){
            return repository.findByUserId(user.getId());
        }else if(user.getRole().getName().equals("ROLE_ADMIN")){
            return repository.findByCreator(user.getId());
        }else {
            throw new EntityNotFoundException();
        }
    }

    public List<Situation> getTaskSituation(Long id){
        return situationRepository.findByTask(id);
    }

    public List<Task> getAdminTasks(String username){
        Optional<User> user = userRepo.findByUsername(username);
        user.orElseThrow();
        return repository.findByCreator(user.get().getId());
    }
    public void deleteTask(Long id) throws EntityNotFoundException {
        Optional<Task> task = repository.findById(id);
        task.orElseThrow(EntityNotFoundException::new);
        repository.delete(task.get());
    }

    public Task getTask(Long id){
        Optional<Task> byId = repository.findById(id);
        byId.orElseThrow();
        return byId.get();
    }

}
