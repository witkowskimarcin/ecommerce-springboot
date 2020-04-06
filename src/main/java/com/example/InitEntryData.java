package com.example;

import com.example.entity.Role;
import com.example.entity.RoleName;
import com.example.entity.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class InitEntryData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder encoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if(!roleRepository.findByName(RoleName.ROLE_ADMIN).isPresent()) {
            Role adminRole = new Role();
            adminRole.setName(RoleName.ROLE_ADMIN);

            Role userRole = new Role();
            userRole.setName(RoleName.ROLE_USER);

            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));

            User admin = new User();
            admin.setEmail("admin@email.pl");
            admin.setUsername("admin");
            admin.setActive(1);
            admin.setPassword(encoder.encode("qwerty"));
            admin.setRoles(new HashSet<Role>(Arrays.asList(adminRole)));

            User user = new User();
            user.setEmail("user@email.pl");
            user.setUsername("user");
            user.setActive(1);
            user.setPassword(encoder.encode("qwerty"));
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

            userRepository.save(admin);
            userRepository.save(user);
        }

    }
}
