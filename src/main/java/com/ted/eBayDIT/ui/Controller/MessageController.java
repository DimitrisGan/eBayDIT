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
import org.modelmapper.convention.MatchingStrategies;
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

    //todo msg

//todo    GET "/messages/notifs" -->check
//todo    DELETE /messages/{messageId} --> check
//TODO NA PAIRNEI OLA TA INBOX ++ THELOUME SUBJECT - TITLE  --> check
//TODO NA PAIRNEI OLA TA SENT  ++ THELOUME SUBJECT - TITLE  --> check

    //todo delete a msg from mesg id -->check

    //todo todo get all sent messages -->check

    //todo change notif policy  --> check

    //todo --notif number for all new msgs --> check

    //todo read msg front will give id of msg and i will mark it as read --> check

    @GetMapping(path ="/messages/notifs")
    public ResponseEntity<Object> getNewNotifs() {
        Integer notifsNumber;
        String currUserId = securityService.getCurrentUser().getUserId();

        notifsNumber = this.messageService.getNewNotifsNumber(currUserId);

        return new ResponseEntity<>(notifsNumber, HttpStatus.OK);
    }


    @GetMapping(path ="/messages/allcontacts")
    public ResponseEntity<Object> getContacts() {
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<UserDetailsResponseModel> returnContactsList = new ArrayList<>();

        String currUserId = securityService.getCurrentUser().getUserId();

        List<UserDto> contactsList = connectivityService.getAllContacts(currUserId);

        for (UserDto userDto : contactsList) {
            UserDetailsResponseModel returnContact = modelMapper.map(userDto, UserDetailsResponseModel.class);
            returnContactsList.add(returnContact);
        }
        return new ResponseEntity<>(returnContactsList, HttpStatus.OK);
    }

    //delete message with given id
    @DeleteMapping(path ="/messages/{id}")
    public ResponseEntity<Object> deleteMessage(@PathVariable Long id) {

        this.messageService.deleteMessage(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path ="/messages/{id}")
    public ResponseEntity<Object> markMessageAsRead(@PathVariable Long id) {

        String currUserId = securityService.getCurrentUser().getUserId();

        messageService.markMessageAsRead(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    //todo needs debug
    @PostMapping(path ="/messages")
    public ResponseEntity<Object> sendMessageTo(@RequestBody MessageRequestInputModel input) {

        String currUserId = securityService.getCurrentUser().getUserId();

        messageService.sendMessage(currUserId,input.getUserId(),input.getMessage(),input.getSubject());

        return new ResponseEntity<>(HttpStatus.OK);

    }


    //todo get all inbox messages from all users

    @GetMapping(path ="/messages/inbox/all")
    public ResponseEntity<Object> getAllInboxMessagesFromAllOtherUsersToCurrentUser() {
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<MessageResponseModel> returnInboxList = new ArrayList<>();

        String currUserId = securityService.getCurrentUser().getUserId();

        List<MessageDto> inboxList = messageService.getAllInboxMessages(currUserId);

        for (MessageDto messageDto : inboxList) {
            MessageResponseModel msgResp = modelMapper.map(messageDto, MessageResponseModel.class);
            msgResp.setSenderUserId(messageDto.getSender().getUserId());
            msgResp.setReceiverUserId(messageDto.getReceiver().getUserId());
            returnInboxList.add(msgResp);

        }

        return new ResponseEntity<>(returnInboxList, HttpStatus.OK);

    }




    //todo needs debug
    //gets the inbox messages between current user and user specified by id
    @GetMapping(path ="/messages/inbox/{id}")
    public ResponseEntity<Object> getInboxMessagesFromOtherUserToCurrentUser(@PathVariable String id) {
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<MessageResponseModel> returnInboxList = new ArrayList<>();

        String currUserId = securityService.getCurrentUser().getUserId();

        List<MessageDto> inboxList = messageService.getInboxMessagesFromOtherUser(currUserId , id);

        for (MessageDto messageDto : inboxList) {
            MessageResponseModel msgResp = modelMapper.map(messageDto, MessageResponseModel.class);
            msgResp.setSenderUserId(messageDto.getSender().getUserId());
            msgResp.setReceiverUserId(messageDto.getReceiver().getUserId());
            returnInboxList.add(msgResp);

        }

        return new ResponseEntity<>(returnInboxList, HttpStatus.OK);

    }


    @GetMapping(path ="/messages/sent/all")
    public ResponseEntity<Object> getAllSentMessagesFromUserToAllOtherUsers() {
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<MessageResponseModel> returnSentList = new ArrayList<>();

        String currUserId = securityService.getCurrentUser().getUserId();

        List<MessageDto> sentList = messageService.getAllSentMessagesFromCurrentUserToAllOtherUsers(currUserId);

        for (MessageDto messageDto : sentList) {
            MessageResponseModel msgResp = modelMapper.map(messageDto, MessageResponseModel.class);
            msgResp.setSenderUserId(messageDto.getSender().getUserId());
            msgResp.setReceiverUserId(messageDto.getReceiver().getUserId());
            returnSentList.add(msgResp);
        }

        return new ResponseEntity<>(returnSentList, HttpStatus.OK);

    }

    //todo needs debug
    //gets the sent messages between user specified by id and current user
    @GetMapping(path ="/messages/sent/{id}")
    public ResponseEntity<Object> getSentMessagesFromUserToOtherUser(@PathVariable String id) {
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<MessageResponseModel> returnSentList = new ArrayList<>();

        String currUserId = securityService.getCurrentUser().getUserId();

        List<MessageDto> sentList = messageService.getSentMessagesFromCurrentUserToOtherUser(currUserId , id);

        for (MessageDto messageDto : sentList) {
            MessageResponseModel msgResp = modelMapper.map(messageDto, MessageResponseModel.class);
            msgResp.setSenderUserId(messageDto.getSender().getUserId());
            msgResp.setReceiverUserId(messageDto.getReceiver().getUserId());
            returnSentList.add(msgResp);
        }

        return new ResponseEntity<>(returnSentList, HttpStatus.OK);

    }




}
