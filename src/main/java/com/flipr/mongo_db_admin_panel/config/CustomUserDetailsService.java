package com.flipr.mongo_db_admin_panel.config;

import com.flipr.mongo_db_admin_panel.models.User;
import com.flipr.mongo_db_admin_panel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(email);

        if (user==null) {
            throw new UsernameNotFoundException("User Not Found");
        } else {
            return new CustomUser(user);
        }
    }

}
