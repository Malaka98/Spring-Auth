package com.example.demo.security.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.model.SpringSecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> appUserOptional = userRepository.findUserByUsername(username);

        if (!appUserOptional.isPresent()) {
            throw new UsernameNotFoundException(String.format("No appUser found with username '%s'.", username));
        } else {
            User appUser = appUserOptional.get();
            return new SpringSecurityUser(
                    appUser.getUsername(),
                    appUser.getPassword(),
                    "ADMIN"
            );
        }
    }
}
