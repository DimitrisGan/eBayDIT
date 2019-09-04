package com.ted.eBayDIT.ui.Controller;

import com.ted.eBayDIT.dto.MessageDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.ConnectivityService;
import com.ted.eBayDIT.service.MessageService;
import com.ted.eBayDIT.ui.model.request.MessageRequestInputModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {


    @Autowired
    SecurityService securityService;


//    @Autowired
//    private UserRepository userRepo;

//    @Autowired
//    private ConnectionRepository connRepo;
//
//    @Autowired
//    private ChatRepository chatRepo;
//
    @Autowired
    private MessageService messageService;

    @Autowired
    private ConnectivityService connectivityService;


    //todo finished needs debug
    @GetMapping(path ="/messages/allcontacts")
    public ResponseEntity<Object> getContacts() {
        ModelMapper modelMapper = new ModelMapper();


        String currUserId = securityService.getCurrentUser().getUserId();

        List<UserDto> contactsList = connectivityService.getAllContacts(currUserId);

//        ItemDto itemDto = modelMapper.map(auctionDetailsRequestModel, ItemDto.class);

        return new ResponseEntity<>(/*auctionsRespList,*/ HttpStatus.OK);

    }

    //todo finished needs debug
    @PostMapping(path ="/messages")
    public ResponseEntity<Object> sendMessageTo(@RequestBody MessageRequestInputModel input) {

        String currUserId = securityService.getCurrentUser().getUserId();

        messageService.sendMessage(currUserId,input.getUserId(),input.getMessage());

        return new ResponseEntity<>(HttpStatus.OK);

    }




    //gets the messages between current user and user specified by id
    @GetMapping(path ="/messages/inbox/{id}")
    public ResponseEntity<Object> getInboxMessagesFromOtherUserToCurrentUser(@RequestParam String id) {

        String currUserId = securityService.getCurrentUser().getUserId();

        List<MessageDto> inboxList = messageService.getInboxMessagesFromOtherUser(currUserId , id);


        ModelMapper modelMapper = new ModelMapper();
//        ItemDto itemDto = modelMapper.map(auctionDetailsRequestModel, ItemDto.class);


        return new ResponseEntity<>(/*auctionsRespList,*/ HttpStatus.OK);

    }


    //gets the messages between current user and user specified by id
    @GetMapping(path ="/messages/sent/{id}")
    public ResponseEntity<Object> getSentMessagesFromUserToOtherUser(@RequestParam String id) {

        String currUserId = securityService.getCurrentUser().getUserId();

        List<MessageDto> sentList = messageService.getSentMessagesFromCurrentUserToOtherUser(currUserId , id);


        ModelMapper modelMapper = new ModelMapper();
//        ItemDto itemDto = modelMapper.map(auctionDetailsRequestModel, ItemDto.class);


        return new ResponseEntity<>(/*auctionsRespList,*/ HttpStatus.OK);

    }


//todo unread messages
    //gets the messages between current user and user specified by id
//    @GetMapping(path ="/messages/sent/{id}")
//    public ResponseEntity<Object> getSentMessagesFromUserToOtherUser(@RequestParam String id) {
//
//        String currUserId = securityService.getCurrentUser().getUserId();
//
//        List<MessageDto> sentList = messageService.getSentMessagesFromCurrentUserToOtherUser(currUserId , id);
//
//
//        ModelMapper modelMapper = new ModelMapper();
////        ItemDto itemDto = modelMapper.map(auctionDetailsRequestModel, ItemDto.class);
//
//
//        return new ResponseEntity<>(/*auctionsRespList,*/ HttpStatus.OK);
//
//    }
//

//todo delete message
//    @DeleteMapping(path ="/messages/sent/{id}")
//    public ResponseEntity<Object> getSentMessagesFromUser(@RequestParam String id) {
//
//        String currUserId = securityService.getCurrentUser().getUserId();
//
//        List<MessageDto> sentList = messageService.getSentMessagesFromCurrentUserToOtherUser(currUserId , id);
//
//
//        ModelMapper modelMapper = new ModelMapper();
////        ItemDto itemDto = modelMapper.map(auctionDetailsRequestModel, ItemDto.class);
//
//
//        return new ResponseEntity<>(/*auctionsRespList,*/ HttpStatus.OK);
//
//    }




}
