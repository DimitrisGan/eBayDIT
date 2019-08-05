package com.ted.eBayDIT.service.impl;

import com.ted.eBayDIT.entity.RoleName;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.RoleEntity;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.RoleRepository;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;




    //todo initialize db properly
    @PostConstruct
    private void initDB() {
        if (roleRepo.findByUserRole(RoleName.ADMIN.name()) == null) {
            RoleEntity adminRole = new RoleEntity(RoleName.ADMIN.name());
            roleRepo.save(adminRole);
        }
        if (roleRepo.findByUserRole(RoleName.USER.name()) == null) {
            RoleEntity userRole = new RoleEntity(RoleName.USER.name());
            roleRepo.save(userRole);
        }
        if (userRepo.findByEmail("dimgan@di.uoa.gr") == null) {
            UserEntity admin1 = new UserEntity();
            admin1.setUsername("Dim_gan");
            admin1.setEncryptedPassword("admin1234");
            admin1.setEmail("dimgan@di.uoa.gr");
            admin1.setFirstName("Dimitris");
            admin1.setLastName("Gangas");
            admin1.setIsVerifiedByAdmin(true);

            saveAdmin(admin1);

        }
        if (userRepo.findByEmail("ylam@di.uoa.gr") == null) {
            UserEntity admin2 = new UserEntity();
            admin2.setUsername("Yannis_Lam");
            admin2.setEncryptedPassword("admin1234");
            admin2.setEmail("ylam@di.uoa.gr");
            admin2.setFirstName("Yannis");
            admin2.setLastName("Lamprou");
            admin2.setIsVerifiedByAdmin(true);

            saveAdmin(admin2);

        }
    }


    private void saveAdmin(UserEntity user) {
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getEncryptedPassword()));
        user.setRole(roleRepo.findByUserRole(RoleName.ADMIN.name()));
        //todo put exception if user is null
        userRepo.save(user);
    }

//    @Override
//    public void save(UserEntity user) {
//        user.setPassword(bCryptEncoder.encode(user.getPassword()));
//        //only users are created after the first configuration
//        user.setRole(roleRepo.findByName("ROLE_USER"));
//        userRepo.save(user);
//    }
//

    @Override
    public int createUser(UserDto user) {


        //todo check if username already exists in db
        UserEntity storedUserDetails = userRepo.findByUsername(user.getUsername());
        if (storedUserDetails != null) throw new RuntimeException("Record(username) already exists");

        //we have to store/save this info to userEntity
        UserEntity userEntity2save = new UserEntity();

//        BeanUtils.copyProperties(user,userEntity2save);
        ModelMapper modelMapper = new ModelMapper();
        userEntity2save = modelMapper.map(user, UserEntity.class);


        userEntity2save.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String publicUserId =utils.generateUserId(30);
        userEntity2save.setUserId(publicUserId);
        userEntity2save.setRole(this.roleRepo.findByUserRole(RoleName.USER.name()));

        //set verification =false to the newly created user
        //todo maybe(?) add routine if its admin to me verified instantly
        userEntity2save.setIsVerifiedByAdmin(false);

        storedUserDetails =  userRepo.save(userEntity2save);


        //now we have to return this back to our restcontroller
//        UserDto returnValue = new UserDto();
//        BeanUtils.copyProperties(storedUserDetails ,returnValue);

        return 0;
    }

    @Override
    public UserDto  updateUser(String userId ,UserDto user2update) {

        UserDto returnValue =new UserDto();
        UserEntity userEntity = userRepo.findByUserId(userId);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }

        //todo more fields should be available for update
        if (user2update.getEmail()      != null)    {userEntity.setEmail(user2update.getEmail()); }
        if (user2update.getFirstName()  != null)    {userEntity.setFirstName(user2update.getFirstName()); }
        if (user2update.getLastName()   != null)    {userEntity.setLastName(user2update.getLastName()); }


        UserEntity updatedUserDetails = userRepo.save(userEntity);

        BeanUtils.copyProperties(updatedUserDetails,returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUser(String username) {

        UserEntity userEntity = userRepo.findByUsername(username);

        if (userEntity == null) throw new UsernameNotFoundException(username);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);

        return returnValue;
    }

    //    public List<Us> getUser







    @Override
    public boolean userExists(String username) {
        UserEntity userEntity = this.userRepo.findByUsername(username);

        return userEntity != null; //if userEntity is null return false
    }

    @Override
    public String getRole(String username) {

//        this.roleRepo.findByUserRole()
        return null;
    }

    //    @Transactional
    @Override
    public void deleteUser(String userId) {

        UserEntity userEntity = this.userRepo.findByUserId(userId);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }
        this.userRepo.delete(userEntity);

    }

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> returnUsersList = new ArrayList<>();

        List<UserEntity> usersEntity =this.userRepo.findAll();

        for (UserEntity userEntity : usersEntity) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity,userDto);
            returnUsersList.add(userDto);
        }
        return returnUsersList;
    }



    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue =new UserDto();
        UserEntity userEntity = userRepo.findByUserId(userId);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }

        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepo.findByUsername(username);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getUsername(),userEntity.getEncryptedPassword(),new ArrayList<>()); //ArrayList stands for the authorities
    }





}
