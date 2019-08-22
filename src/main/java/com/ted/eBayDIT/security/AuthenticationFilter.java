package com.ted.eBayDIT.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ted.eBayDIT.SpringApplicationContext;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.ui.model.request.UserLoginRequestModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


//    @Autowired
//    SecurityService securityService;
//
//    @Autowired
//    UserService userService;
//



    private final AuthenticationManager authenticationManager; //it will be used to authenticate user

    public AuthenticationFilter(AuthenticationManager authenticationManagerl) {
        this.authenticationManager = authenticationManagerl;
    }



//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
//            throws AuthenticationException, IOException, ServletException {
//
//        AccountCredentials creds = new ObjectMapper()
//                .readValue(req.getInputStream(), AccountCredentials.class);
//        UserEntity currUser = userRepo.findByEmail(creds.getEmail());
//        Set<GrantedAuthority> authorities = new HashSet<>();
//        if (currUser != null) {
//            authorities.add(new SimpleGrantedAuthority(currUser.getRole().getName()));
//        }
//        return getAuthenticationManager().authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        creds.getEmail(),
//                        creds.getPassword(),
//                        authorities
//                )
//        );
//    }


//    UserEntity currUser = userRepo.findByEmail(creds.getEmail());
//    Set<GrantedAuthority> authorities = new HashSet<>();
//	  if (currUser != null) {
//        authorities.add(new SimpleGrantedAuthority(currUser.getRole().getName()));
//    }
//	  return getAuthenticationManager().authenticate(
//    			new UsernamePasswordAuthenticationToken(
//            creds.getEmail(),
//    				creds.getPassword(),
//    authorities
//        		)



    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {

            UserLoginRequestModel creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserLoginRequestModel.class);

            UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
            UserDto user = userService.getUser(creds.getUsername());
            String role = user.getUserRole();
            Set<GrantedAuthority> authorities = new HashSet<>();

            if (user != null) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            return authenticationManager.authenticate( //here authenticationManager check the creds
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            authorities) //todo edw tha to allaksw
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //if user with username and password is succesfully authenticated then succesfulAuthentications is called
    //Otherwise,

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String userName = ((User) auth.getPrincipal()).getUsername();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUser(userName);

        String role = userDto.getUserRole();

//        String role = userService.getUser(userName).getUserRole();

        Claims claims = Jwts.claims().setSubject(userName);

        claims.put("role", new SimpleGrantedAuthority(role).getAuthority());

        String token = Jwts.builder()
//                .setSubject(userName)
                .setClaims(claims)//

                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET )
                .compact();


//        UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImpl");
//        UserDto userDto = userService.getUser(userName);

        //after web token JWT is generated then it will be added to the header
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

//        UserDto currUser = new UserDto();

//        String roleName = userService.getUser(userName).getRoleDto().getName();

//        res.getWriter().append("OK!!");


//        RoleEntity r = currUser.getCurrentUser().getRole();userName

        res.addHeader("UserID", userDto.getUserId());



    }





}
