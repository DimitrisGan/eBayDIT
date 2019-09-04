package com.ted.eBayDIT.ui.Controller;

import com.ted.eBayDIT.dto.MessageDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.ConnectivityService;
import com.ted.eBayDIT.service.MessageService;
import com.ted.eBayDIT.ui.model.request.MessageRequestInputModel;
import com.ted.eBayDIT.ui.model.response.MessageResponseModel;
import com.ted.eBayDIT.ui.model.response.UserDetailsResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {


    @Autowired
    SecurityService securityService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ConnectivityService connectivityService;


    //todo needs debug
    @GetMapping(path ="/messages/allcontacts")
    public ResponseEntity<Object> getContacts() {
        ModelMapper modelMapper = new ModelMapper();
        List<UserDetailsResponseModel> returnContactsList = new ArrayList<>();

        String currUserId = securityService.getCurrentUser().getUserId();

        List<UserDto> contactsList = connectivityService.getAllContacts(currUserId);

        for (UserDto userDto : contactsList) {
            UserDetailsResponseModel returnContact = modelMapper.map(userDto, UserDetailsResponseModel.class);
            returnContactsList.add(returnContact);
        }
        return new ResponseEntity<>(returnContactsList, HttpStatus.OK);
    }

    //todo needs debug
    @PostMapping(path ="/messages")
    public ResponseEntity<Object> sendMessageTo(@RequestBody MessageRequestInputModel input) {

        String currUserId = securityService.getCurrentUser().getUserId();

        messageService.sendMessage(currUserId,input.getUserId(),input.getMessage());

        return new ResponseEntity<>(HttpStatus.OK);

    }




    //gets the inbox messages between current user and user specified by id
    @GetMapping(path ="/messages/inbox/{id}")
    public ResponseEntity<Object> getInboxMessagesFromOtherUserToCurrentUser(@RequestParam String id) {
        ModelMapper modelMapper = new ModelMapper();
        List<MessageResponseModel> returnInboxList = new ArrayList<>();

        String currUserId = securityService.getCurrentUser().getUserId();

        List<MessageDto> inboxList = messageService.getInboxMessagesFromOtherUser(currUserId , id);

        for (MessageDto messageDto : inboxList) {
            MessageResponseModel msgResp = modelMapper.map(messageDto, MessageResponseModel.class);
            returnInboxList.add(msgResp);

        }

        return new ResponseEntity<>(returnInboxList, HttpStatus.OK);

    }

    //gets the sent messages between user specified by id and current user
    @GetMapping(path ="/messages/sent/{id}")
    public ResponseEntity<Object> getSentMessagesFromUserToOtherUser(@RequestParam String id) {
        ModelMapper modelMapper = new ModelMapper();
        List<MessageResponseModel> returnSentList = new ArrayList<>();

        String currUserId = securityService.getCurrentUser().getUserId();

        List<MessageDto> sentList = messageService.getSentMessagesFromCurrentUserToOtherUser(currUserId , id);

        for (MessageDto messageDto : sentList) {
            MessageResponseModel msgResp = modelMapper.map(messageDto, MessageResponseModel.class);
            returnSentList.add(msgResp);
        }

        return new ResponseEntity<>(returnSentList, HttpStatus.OK);

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
