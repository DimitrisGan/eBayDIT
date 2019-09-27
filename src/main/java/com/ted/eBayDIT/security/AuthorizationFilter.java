package com.ted.eBayDIT.security;


import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.HashSet;
import java.util.Set;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


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

            String role = (String) Jwts.parser()
                    .setSigningKey(SecurityConstants.TOKEN_SECRET )
                    .parseClaimsJws(token)
                    .getBody().get("role");

            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(role));

            if (user!=null){
                /* get/return the user! */
                return new UsernamePasswordAuthenticationToken(user,null,authorities);

            }

            return null;
        }
        return null;
    }

}
