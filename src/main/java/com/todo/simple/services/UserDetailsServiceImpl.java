package com.todo.simple.services;

import com.todo.simple.models.User;
import com.todo.simple.repositories.UserRepository;
import com.todo.simple.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

// Service para o modelo: UserSpringSecurity (UserDetails)

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new UserSpringSecurity(user.getId(), user.getName(), user.getPassword(), user.getProfiles());
    }
}
