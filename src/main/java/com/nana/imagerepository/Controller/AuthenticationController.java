package com.nana.imagerepository.Controller;

import com.nana.imagerepository.Entity.User;
import com.nana.imagerepository.Model.JwtResponse;
import com.nana.imagerepository.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("auth/login")
    public ResponseEntity<JwtResponse> login (@RequestBody User user) throws Exception {
        return authenticationService.authenticate(user);
    }
}
