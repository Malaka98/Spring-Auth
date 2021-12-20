package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.security.AppConstant;
import com.example.demo.security.TokenUtils;
import com.example.demo.security.model.AuthenticationRequest;
import com.example.demo.security.model.AuthenticationResponse;
import com.example.demo.security.model.SpringSecurityUser;
import com.example.demo.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(name = "auth_controller", value = "/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        // Perform the authentication
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = this.tokenUtils.generateToken(userDetails);

        // get user
        UserDTO user = userService.getUserByUsername(authenticationRequest.getUsername());

        // Return the token
        return ResponseEntity.ok(new AuthenticationResponse(userDetails.getUsername(), user.getId() ,token));
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
        String token = request.getHeader(AppConstant.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        SpringSecurityUser user = (SpringSecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            UserDTO user1 = userService.getUserByUsername(username);
            return ResponseEntity.ok(new AuthenticationResponse(username,user1.getId()
                    ,refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@NotNull @RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return new ResponseEntity("Your account has been created successfully!",
                HttpStatus.CREATED);
    }
}
