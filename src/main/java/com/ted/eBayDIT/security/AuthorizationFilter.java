package com.ted.eBayDIT.security;


import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    //when http request is made and this filter is triggered then this methos is called

    //comments for me : otan kapoios stelnei users/{userId}
    //prepei na kserw oti autos einai o xrhsths pou exei to JWT
    //opote prepei na to diastaurwnw kai na mhn afhnw opoiondhpote na kanei PUT etc.
    //an gnwrizei to userId kai sunepws tou endpoint tou url
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
          chain.doFilter(request,response); //continue to the other filters of the filter-chain
          return;
        }

        //if header[Authentication column] is not null and starts with prefix Bearer
        //then we need the UsernamePasswordAuthenticationToken and store it to SecurityContextHolder
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //now we have in SecurityContextHolder the user
        chain.doFilter(request,response);

    }


    //functions job is to return UsernamePasswordAuthenticationToken
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token !=null){

            //we dont need BEARER_ prefix anymore
            token = token.replace(SecurityConstants.TOKEN_PREFIX,"");

            //access/parse that JW token value ,decrypt it and get UserDetails on that token
            //process is done by Spring framework
            String user = Jwts.parser()
                    .setSigningKey( SecurityConstants.TOKEN_SECRET ) //we need to provide also the token_secret with its JW token was generated
                    .parseClaimsJws( token )
                    .getBody()
                    .getSubject();


            if (user!=null){
                /*get/return the user!*/
                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            }

            return null;
        }
        return null;
    }

}
