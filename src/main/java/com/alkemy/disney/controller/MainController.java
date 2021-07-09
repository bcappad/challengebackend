package com.alkemy.disney.controller;

import com.alkemy.disney.model.*;
import com.alkemy.disney.repository.FilmCharacterRepository;
import com.alkemy.disney.repository.UserRepository;
import com.alkemy.disney.services.MyUserDetailsService;
import com.alkemy.disney.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController

public class MainController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilmCharacterRepository filmCharacterRepository;

    //2. Autenticación de Usuarios
    @PostMapping(path = "/authenticate")//(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("El usuario o contraseña no es válido", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> creatrNewUser(@RequestBody User newUser){
        return null;
    }
}
