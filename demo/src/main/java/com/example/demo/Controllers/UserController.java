package com.example.demo.Controllers;

import com.example.demo.Configuration.JwtUtil;
import com.example.demo.Models.AuthenticateResponse;
import com.example.demo.Models.AuthenticationRequest;
import com.example.demo.Models.User;
import com.example.demo.Models.UserDto;
import com.example.demo.Services.MyUserDetailsService;
import com.example.demo.Services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.catalina.authenticator.DigestAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @RequestMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws  Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e) { throw new Exception("Incorrect username or password",e); }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final  String jwt =jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticateResponse(jwt));
    }
    @GetMapping("/users/{email}/{password}")
    @CrossOrigin()
    public ResponseEntity<UserDto> login(@PathVariable("email") String email, @PathVariable("password") String password) {

        User user = userService.findByEmailAndId(email, password);
        if (user != null) {

            String token = getToken(user.getEmail());
            UserDto userDto = new UserDto();
            userDto.setEmail(user.getEmail());
            userDto.setToken(token);
            return ResponseEntity.ok().body(userDto);

        } else {

            System.out.println("error");
            return ResponseEntity.notFound().build();

        }
        }


        @GetMapping("/users")
        public ResponseEntity<List<User>> findAll () { return ResponseEntity.ok().body(userService.findAll()); }

        @PostMapping("/users")
        public ResponseEntity<User> save (@RequestBody User user){ return ResponseEntity.ok().body(userService.save(user)); }

        @GetMapping("/users/{id}")
        public ResponseEntity findById ( @PathVariable long id)throws Exception { return ResponseEntity.ok().body(userService.findById(id)); }

        @PutMapping("/users") public ResponseEntity<User> update (@RequestBody User user){ return ResponseEntity.ok().body(userService.update(user)); }

        @DeleteMapping("/users/{id}")
        public ResponseEntity delete ( @PathVariable long id)throws Exception { userService.delete(id);return ResponseEntity.ok().build(); }


    private String getToken( String email){
        String Key = "mysecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        Key.getBytes()).compact();

        return "Bearer " + token;
    }
    }


