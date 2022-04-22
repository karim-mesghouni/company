package com.example.webproject.companymanagement.security.sevicre;

import com.example.webproject.companymanagement.domain.app_user.data.IUserRepository;
import com.example.webproject.companymanagement.domain.app_user.models.User;
import com.example.webproject.companymanagement.security.detials.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    IUserRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> agent = agentRepository.findByUsername(username);
        agent.orElseThrow(() -> new UsernameNotFoundException("User not fount"));
        return agent.map(UserDetailsImpl::new).get();

    }
}
