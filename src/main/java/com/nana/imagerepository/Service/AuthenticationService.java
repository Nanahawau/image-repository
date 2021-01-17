package com.nana.imagerepository.Service;

import com.nana.imagerepository.Entity.User;
import com.nana.imagerepository.Model.JwtResponse;
import com.nana.imagerepository.Repository.UserRepository;
import com.nana.imagerepository.Security.jwt.JwtUtils;
import com.nana.imagerepository.Security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;



    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public ResponseEntity<JwtResponse> authenticate(User user) throws Exception {

        authenticate(user.getUsername(), user.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        final String token = jwtUtils.generateJwtToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));


    }




    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
