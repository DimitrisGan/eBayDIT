package com.ted.eBayDIT.security;

import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
public class SecurityServiceImpl implements SecurityService{

    private final UserRepository userRepository;

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            UserEntity authUser =  userRepository.findByUsername((String)auth.getPrincipal());

            ModelMapper modelMapper = new ModelMapper();

            return modelMapper.map(authUser, UserDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
