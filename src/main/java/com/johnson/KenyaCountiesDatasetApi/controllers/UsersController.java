package com.johnson.KenyaCountiesDatasetApi.controllers;

import com.johnson.KenyaCountiesDatasetApi.models.AuthenticationRequest;
import com.johnson.KenyaCountiesDatasetApi.models.AuthenticationResponse;
import com.johnson.KenyaCountiesDatasetApi.models.User;
import com.johnson.KenyaCountiesDatasetApi.services.MyUserDetailsService;
import com.johnson.KenyaCountiesDatasetApi.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UsersController { @Autowired
private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "AUthenticate the User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "403", description = "FORBIDDEN. Invalid username or password",
                            content = @Content),
                    @ApiResponse(responseCode = "200", description = "SUCCESS. Bearer Token Assigned User",
                            content = { @Content(mediaType = "application/json") })
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("Invalid username or password", e);
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @Operation(
            summary = "Get all the users",
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String username){
        try {
            List<User> users = new ArrayList<User>();
            if (username==null)
                users.addAll(myUserDetailsService.findAll());
            else
                users.addAll(myUserDetailsService.getUserByUsername(username));
            if(users.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get User by ID",
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id){
        Optional<User> userData = myUserDetailsService.getUserById(id);
        return userData.map(users -> new ResponseEntity<>(users, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Add a new User",
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){

        if (!myUserDetailsService.userExists(user.getUsername()))
            try {
                User postUser = myUserDetailsService.saveUser(user);
                return new ResponseEntity<>(postUser, HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    @Operation(
            summary = "Update Details of Existing User",
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user){
        Optional<User> getUser = myUserDetailsService.getUserById(id);
        if (getUser.isPresent())
            try {
                User updateUser = myUserDetailsService.updateUser(user, id);
                return new ResponseEntity<>(updateUser, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Delete User by ID",
            security = {@SecurityRequirement(name = "BearerJWT")}
    )
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id){
        try {
            myUserDetailsService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
