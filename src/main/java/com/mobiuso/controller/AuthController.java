package com.mobiuso.controller;

import com.mobiuso.entity.User;
import com.mobiuso.jwt.JwtTokenProvider;
import com.mobiuso.exception.UserException;
import com.mobiuso.repository.UserRepository;
import com.mobiuso.request.LoginRequest;
import com.mobiuso.request.UserRequest;
import com.mobiuso.response.AuthResponse;
import com.mobiuso.security.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> signUp(@RequestBody UserRequest userRequest) throws UserException {
        String name = userRequest.getName();
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();

        Optional<User> isEmailExist = this.userRepository.findByEmail(email);

        if (isEmailExist.isPresent()) {
            log.warn("--------- exist "+isEmailExist.get().getEmail());

            throw new UserException("Email Is Already Used With Another Account");
        }

        User createdUser= new User();
        createdUser.setName(name);
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));

        this.userRepository.save(createdUser);
        log.info("user got created",createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateJwtToken(authentication);

        AuthResponse authResponse= new AuthResponse();

        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        log.info(username +" ----- "+password);

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String token = jwtTokenProvider.generateJwtToken(authentication);
        AuthResponse authResponse= new AuthResponse();

        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        log.info("sign in userDetails - " + userDetails);

        if (userDetails == null) {
            log.error("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.error("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
