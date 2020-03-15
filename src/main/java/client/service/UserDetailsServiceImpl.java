package client.service;

import client.model.Role;
import client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Set<Role> roles = new HashSet();
        roles.add(new Role("admin"));

        User user = new User(1, "admin", "admin", "$2a$10$U7Aj7fe.6DAYsn/tIjtCX.6.0DYW.WPoiDSM5ZX9kMlX.YjiQcLEi", roles);

//        UserDetails userDetails = userService.getUserByLogin(username);

//        return userDetails;
        return user;
    }
}
