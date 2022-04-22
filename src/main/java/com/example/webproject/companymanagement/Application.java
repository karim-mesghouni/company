package com.example.webproject.companymanagement;

import com.example.webproject.companymanagement.domain.app_user.AgentService;
import com.example.webproject.companymanagement.domain.app_user.models.Certification;
import com.example.webproject.companymanagement.domain.app_user.models.Role;
import com.example.webproject.companymanagement.domain.app_user.models.User;
import com.example.webproject.companymanagement.security.config.SecurityAuditorAware;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Application {

    //private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
         SpringApplication.run(Application.class, args);
    }

    /**
     * @return the current spring boot application context (useful for getting beans),
     * until the application boot phase completes this will return null
     */
    //!! @Nullable (before the application boots-up)
//    public static ConfigurableApplicationContext context() {
//        return context;
//    }

    /**
     * @return the bean instance that uniquely matches the given object
     */
//    public static <T> T getBean(Class<T> requiredType) {
//        return context().getBean(requiredType);
//    }

//    @Bean
//    CommandLineRunner run(AgentService agentService) {
//        return args -> {
//            var admin =  agentService.addRole( Role.builder().name("ROLE_ADMIN").build());
//            var employee =  agentService.addRole(Role.builder().name("ROLE_EMPLOYEE").build());
//            User appUser = new User("Abdekkrim Mesghouni",
//					"krim_ms",
//					"123456",
//					"krim.mesghouni@gmail.com",
//					LocalDateTime.of(2000, 05, 27, 6, 45),
//					admin,
//                    new ArrayList<>());
//
//            Certification certification = new Certification("certification title","certification source","certification level",appUser);
//            Certification certification1 = new Certification("certification title2","certification source","certification level",appUser);
//            Certification certification2= new Certification("certification title3","certification source","certification level",appUser);
//            appUser.getCertifications().addAll(Arrays.asList(certification1,certification2));
//            var user = agentService.addUser(appUser);
//            agentService.addCertification(user.getId(),certification);
//
//
//
//
//
//        };
//    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuditorAware<User> auditorAware() {
        return new SecurityAuditorAware();
    }

}
