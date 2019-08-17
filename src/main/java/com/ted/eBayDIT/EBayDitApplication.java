package com.ted.eBayDIT;

import com.ted.eBayDIT.xmlParser.Items;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

import java.io.*; // will be using only File class from this package
import javax.xml.bind.*; // will be using JAXBContext,Marshaller and JAXBException classes from this package

@SpringBootApplication
public class EBayDitApplication {

    public static void main(String[] args) {

//        populateDataToDb test = new populateDataToDb();
//        test.extractXml();
//        test.sacw2

        SpringApplication.run(EBayDitApplication.class, args);

    }


    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SpringApplicationContext SpringApplicationContext()  {
        return new SpringApplicationContext();
    }




    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));

        config.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, UserID, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }



}
