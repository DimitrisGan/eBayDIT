package com.ted.eBayDIT.service.impl;

import com.ted.eBayDIT.entity.*;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.repository.BidderDetailsRepository;
import com.ted.eBayDIT.repository.RoleRepository;
import com.ted.eBayDIT.repository.SellerDetailsRepository;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private Utils utils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SellerDetailsRepository sellerRepo;

    @Autowired
    private BidderDetailsRepository bidderRepo;


    private void saveNewSellerRecord(UserEntity user){
        SellerDetailsEntity seller = new SellerDetailsEntity();
        seller.setRating(5); //set starting rating value to 5
        seller.setUser(user);
        user.setSeller(seller);
//        sellerRepo.save(seller);
    }

    private void saveNewBidderRecord(UserEntity user) {
        BidderDetailsEntity bidder = new BidderDetailsEntity();
        bidder.setRating(3); //set starting rating value to 3
        bidder.setUser(user);
        user.setBidder(bidder);
//        bidderRepo.save(bidder);
    }

    //initialize db with 2 admins
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
            admin1.setVerified(true);
            admin1.setUserId("admin1");

            admin1.setCountry("Greece");
            admin1.setPhoneNumber("6967510111");
            admin1.setLocation("DI");

            saveAdmin(admin1);

        }
        if (userRepo.findByEmail("ylam@di.uoa.gr") == null) {
            UserEntity admin2 = new UserEntity();
            admin2.setUsername("Yannis_Lam");
            admin2.setEncryptedPassword("admin1234");
            admin2.setEmail("ylam@di.uoa.gr");
            admin2.setFirstName("Yannis");
            admin2.setLastName("Lamprou");
            admin2.setVerified(true);
            admin2.setUserId("admin2");

            admin2.setCountry("Greece");
            admin2.setPhoneNumber("6967510112");
            admin2.setLocation("DI");

            saveAdmin(admin2);

        }


        
    }


    private void saveAdmin(UserEntity user) {
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getEncryptedPassword()));
        user.setRole(roleRepo.findByUserRole(RoleName.ADMIN.name()));

        saveNewSellerRecord(user);
        saveNewBidderRecord(user);

        userRepo.save(user);
    }



    @Override
    public void createUser(UserDto user) {


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
        userEntity2save.setVerified(false);

        saveNewSellerRecord(userEntity2save);
        saveNewBidderRecord(userEntity2save);

        storedUserDetails =  userRepo.save(userEntity2save);


        //now we have to return this back to our restcontroller
//        UserDto returnValue = new UserDto();
//        BeanUtils.copyProperties(storedUserDetails ,returnValue);

    }

    @Override
    public UserDto  updateUser(String userId ,UserDto user2update) {

        UserDto returnValue =new UserDto();
        UserEntity userEntity = userRepo.findByUserId(userId);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }

        if (user2update.getEmail()      != null)    {userEntity.setEmail(user2update.getEmail()); }
        if (user2update.getFirstName()  != null)    {userEntity.setFirstName(user2update.getFirstName()); }
        if (user2update.getLastName()   != null)    {userEntity.setLastName(user2update.getLastName()); }

        if (user2update.getLocation()        != null)    {userEntity.setLocation(user2update.getLocation()); }
        if (user2update.getPhoneNumber()    != null)    {userEntity.setPhoneNumber(user2update.getPhoneNumber()); }
        if (user2update.getCountry()        != null)    {userEntity.setCountry(user2update.getCountry()); }
        if (user2update.getAfm()            != null)    {userEntity.setAfm(user2update.getAfm()); }





        UserEntity updatedUserDetails = userRepo.save(userEntity);

//        BeanUtils.copyProperties(updatedUserDetails,returnValue);
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(updatedUserDetails, UserDto.class);


        return returnValue;
    }


    @Override
    public UserDto verifyUser(String userId) {
        UserDto returnValue =new UserDto();

        UserEntity userEntity = userRepo.findByUserId(userId);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }

        userEntity.setVerified(true);

        this.userRepo.save(userEntity);

        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(userEntity, UserDto.class);

        return returnValue;

    }

    @Override
    public void verifyAll() {

        List<UserEntity> allNotVerifiedList = this.userRepo.findByVerifiedFalse();

        for (UserEntity userEntity : allNotVerifiedList) {
            userEntity.setVerified(true);
            this.userRepo.save(userEntity);

        }

    }

    @Override
    public int usersNumber() {
        return this.userRepo.findAll().size();
    }

    @Override
    public UserDto updatePassword(String userId, String newPassword) {
        UserDto returnValue =new UserDto();

        UserEntity userEntity = userRepo.findByUserId(userId);

        if (userEntity == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(newPassword));

        UserEntity updatedUserDetails = userRepo.save(userEntity);

        //        BeanUtils.copyProperties(updatedUserDetails,returnValue);
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(updatedUserDetails, UserDto.class);

        return returnValue;

    }

    @Override
    public boolean isPasswordEqual(String userId, String pass) {
        UserEntity userEntity = userRepo.findByUserId(userId);

        return bCryptPasswordEncoder.matches(pass, userEntity.getEncryptedPassword());
    }


    @Override
    public UserDto getUser(String username) {

        UserEntity userEntity = userRepo.findByUsername(username);

        if (userEntity == null) throw new UsernameNotFoundException(username);


        ModelMapper modelMapper = new ModelMapper();
        UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

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

        ModelMapper modelMapper = new ModelMapper();
        for (UserEntity userEntity : usersEntity) {

            UserDto userDto = modelMapper.map(userEntity, UserDto.class);

            returnUsersList.add(userDto);
        }
        return returnUsersList;
    }


    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue =new UserDto();
        UserEntity user = userRepo.findByUserId(userId);

        if (user == null) { //if username was not found throw exception
            throw new UsernameNotFoundException(userId);
        }

        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(user, UserDto.class);
        //BeanUtils.copyProperties(userEntity,returnValue);

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






    @Override
    public List<UserDto> getAllUsers(int pageNo, int pageSize, String sortBy, String sortType) {

//        if(pageNo>0) pageNo = pageNo-1; //to not get confused wit zero page

        List<UserDto> returnValue = new ArrayList<>();
        Pageable paging;

        if (sortType.equals("asc"))
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        else
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());


        Page<UserEntity> pagedResult = userRepo.findAll(paging);
        int totalPages = pagedResult.getTotalPages();

        List<UserEntity> users = pagedResult.getContent();

        for (UserEntity userEntity: users){
            ModelMapper modelMapper = new ModelMapper();
            UserDto userDto = modelMapper.map(userEntity, UserDto.class);
            userDto.setTotalPages(totalPages);
            returnValue.add(userDto);
        }

        return returnValue;

    }


    @Override
    public List<UserDto> getAllNotVerifiedUsers() {


        List<UserDto> returnList = new ArrayList<>();



        List<UserEntity> notVerifiedUsersList = new ArrayList<>();
        notVerifiedUsersList = userRepo.findByVerifiedFalse();

        ModelMapper modelMapper = new ModelMapper();
        for (UserEntity user : notVerifiedUsersList) {
            new UserDto();

            UserDto userDto = modelMapper.map(user, UserDto.class);
            returnList.add(userDto);
        }

        return returnList;
    }


}
