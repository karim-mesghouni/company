package com.example.webproject.companymanagement.core.data_base.seed;

import com.example.webproject.companymanagement.domain.app_user.AgentService;
import com.example.webproject.companymanagement.domain.app_user.data.IRoleRepository;
import com.example.webproject.companymanagement.domain.app_user.data.IUserRepository;
import com.example.webproject.companymanagement.domain.app_user.models.Role;
import com.example.webproject.companymanagement.domain.app_user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j(topic = "DBSeeder")
public class DataBaseSeeder implements ApplicationRunner {
    private final IUserRepository userRepository;

    private final AgentService agentService;
    private final IRoleRepository roleRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        log.info("Seed into database....");
        // create roles
        if (roleRepository.count() == 0){
            Role admin = Role.builder().name("ROLE_ADMIN").build();
            Role employee = Role.builder().name("ROLE_EMPLOYEE").build();
            Role manager = Role.builder().name("ROLE_MANAGER").build();
            agentService.addRole(admin);
            agentService.addRole(employee);
            agentService.addRole(manager);
        }
        // create user that has "ROLE_ADMIN" role
        if (userRepository.count() == 0) {
            Role role = roleRepository.findByName("ROLE_MANAGER");
            User user = User
                    .builder()
                    .name("Manager")
                    .username("manager")
                    .password("2022")
                    .dateOfBirth(LocalDateTime.now())
                    .email("manager@emai.com")
                    .role(role)
                    .build();
            agentService.addUser(user);
        }
    }
}

