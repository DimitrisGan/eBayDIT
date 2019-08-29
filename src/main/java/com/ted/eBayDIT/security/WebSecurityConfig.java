package com.ted.eBayDIT.security;


import com.ted.eBayDIT.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // override method to configure users for in-memory authentication ...

    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public WebSecurityConfig(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*
     *   Configure users (in memory, database, ldap, etc)
     * */
    //  Create 2 users for demo
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

        // add our users for in memory authentication
//        auth.inMemoryAuthentication()
//                .withUser("adminUser").password(bCryptPasswordEncoder.encode("admin1234")).roles("ADMIN");
//                .withUser(users.username("mary").password("test123").roles("MANAGER"))
//                .withUser(users.username("susan").password("test123").roles("ADMIN"));

    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
//        configuration.setAllowedOrigins(Arrays.asList(FRONT_END_SERVER));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));

        // This allow us to expose the headers
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



//
//    /*Configure security of web paths in application, login, logout etc
//     */
//    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.cors().and().csrf().disable().authorizeRequests()


                .antMatchers(SecurityConstants.SIGN_UP_URL).permitAll()
                .antMatchers("/search/**").permitAll()

                .antMatchers("/login").permitAll()

                .antMatchers(HttpMethod.POST ,"/exists/**").permitAll()

                .antMatchers("/profile").authenticated()

                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().hasAnyAuthority("USER","ADMIN")
                .and()
//                .addFilter(new AuthenticationFilter(authenticationManager())); //no more neede because we want our custom login form url
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy((SessionCreationPolicy.STATELESS));



//                .antMatchers("/").hasAnyRole("ADMIN", "MEMBER", "VISITOR")
//                .antMatchers("/members/**").hasRole("MEMBER")
//                .antMatchers("/systems/**").hasRole("ADMIN")
//                .and()
//
//                .formLogin()
////                .loginPage("/login")
////                .loginProcessingUrl("/authenticateTheUser")
//                .successHandler(mySuccessHandler)
//                .failureHandler(myFailureHandler)
////                .permitAll()
//                .and()
//
//                .logout().permitAll();




//                .antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
//                .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
//                .and()
//                .csrf().disable()
//                .formLogin().disable();
    }


    //creates a new instance of Authentication filter and takes as parameter Authentication Manager
    //once we have the auth filter we then setFilterProcessesUrl for /login url
    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

}
