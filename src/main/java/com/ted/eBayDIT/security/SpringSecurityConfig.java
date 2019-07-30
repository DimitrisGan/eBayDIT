package com.ted.eBayDIT.security;


//import com.ted.eBayDIT.service.UserService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
////
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    // override method to configure users for in-memory authentication ...
//
//    private final UserService userDetailsService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//
//    public SpringSecurityConfig(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    /*
//     *   Configure users (in memory, database, ldap, etc)
//     * */
//    //  Create 2 users for demo
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////
////        // add our users for in memory authentication
////        User.UserBuilder users = User.withDefaultPasswordEncoder();
////
//////        auth.inMemoryAuthentication()
//////                .withUser(users..username("john").password("test123").roles("EMPLOYEE"))
//////                .withUser(users.username("mary").password("test123").roles("MANAGER"))
//////                .withUser(users.username("susan").password("test123").roles("ADMIN"));
////
////    }
//
//
//    /*Configure security of web paths in application, login, logout etc
//     */
//    // Secure the endpoins with HTTP Basic authentication
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////
////
////        http.cors().and().csrf().disable().authorizeRequests()
////                .antMatchers("/").hasAnyRole("ADMIN", "MEMBER", "VISITOR")
////                .antMatchers("/members/**").hasRole("MEMBER")
////                .antMatchers("/systems/**").hasRole("ADMIN")
////                .and()
////
////                .formLogin()
//////                .loginPage("/login")
//////                .loginProcessingUrl("/authenticateTheUser")
////                .successHandler(mySuccessHandler)
////                .failureHandler(myFailureHandler)
//////                .permitAll()
////                .and()
////
////                .logout().permitAll();
////
////
////
////
//////                .antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
//////                .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
//////                .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
//////                .antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
//////                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
//////                .and()
//////                .csrf().disable()
//////                .formLogin().disable();
////    }
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .cors().and()
////                .csrf().disable().authorizeRequests()
////                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
////                .permitAll()
////                .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
////                .permitAll()
////                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
////                .permitAll()
////                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
////                .permitAll()
////                .antMatchers(SecurityConstants.H2_CONSOLE)
////                .permitAll()
////                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
////                .permitAll()
////                .anyRequest().authenticated().and()
////                .addFilter(getAuthenticationFilter())
////                .addFilter(new AuthorizationFilter(authenticationManager()))
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////
////        http.headers().frameOptions().disable();
////    }
//
//
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
////    }
////
////    protected AuthenticationFilter getAuthenticationFilter() throws Exception {
////        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
////        filter.setFilterProcessesUrl("/users/login");
////        return filter;
////    }
//
//    /*@Bean
//    public UserDetailsService userDetailsService() {
//        //ok for demo
//        UserEntity.UserBuilder users = UserEntity.withDefaultPasswordEncoder();
//
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(users.username("user").password("password").roles("USER").build());
//        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
//        return manager;
//    }*/
//
//}
