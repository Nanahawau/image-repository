package com.nana.imagerepository.Service;

import com.nana.imagerepository.Entity.User;
import com.nana.imagerepository.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public ResponseEntity<?> register (User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        User userObject = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userObject.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
