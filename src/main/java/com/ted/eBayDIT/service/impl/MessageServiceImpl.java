package com.ted.eBayDIT.service.impl;

import com.ted.eBayDIT.dto.MessageDto;
import com.ted.eBayDIT.entity.MessageEntity;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.ConnectivityRepository;
import com.ted.eBayDIT.repository.MessageRepository;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.service.ConnectivityService;
import com.ted.eBayDIT.service.MessageService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<MessageDto> getAllInboxMessages(String currUserId) {

        List<MessageDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity currUser = this.userRepo.findByUserId(currUserId);
        /*find all the records in Connective Table tha refer to currUser*/
        List<MessageEntity> messagesInboxList = messageRepo.findByReceiverAndDeletedByReceiverFalse(currUser);

        Collections.reverse(messagesInboxList);
        
        for (MessageEntity messageEntity : messagesInboxList) {
            MessageDto messageDto = modelMapper.map(messageEntity, MessageDto.class);
            returnValue.add(messageDto);

        }

        return returnValue;
    }



    @Override
    public List<MessageDto> getInboxMessagesFromOtherUser(String currUserId, String otherUserId) {
        List<MessageDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity currUser = this.userRepo.findByUserId(currUserId);
        UserEntity otherUser = this.userRepo.findByUserId(otherUserId);

        if (otherUser == null) throw new RuntimeException("Not existing user with such id!"); //check if is ended

        /*find all the records in Connective Table tha refer to currUser from specified other user*/
        List<MessageEntity> messagesInboxList = messageRepo.findBySenderAndReceiver(otherUser,currUser);

        Collections.reverse(messagesInboxList); /*sort messages by id --> this means by latest msg to oldest*/

        for (MessageEntity messageEntity : messagesInboxList) {
            MessageDto messageDto = modelMapper.map(messageEntity, MessageDto.class);
            returnValue.add(messageDto);
        }

        return returnValue;
    }



    @Override
    public List<MessageDto> getAllSentMessagesFromCurrentUserToAllOtherUsers(String currUserId) {
        List<MessageDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity currUser = this.userRepo.findByUserId(currUserId);

        List<MessageEntity> messagesSentList = messageRepo.findBySenderAndDeletedBySenderFalse(currUser); //find all the records in Connective Table tha refer to currUser

        Collections.reverse(messagesSentList);

        for (MessageEntity messageEntity : messagesSentList) {
            MessageDto messageDto = modelMapper.map(messageEntity, MessageDto.class);
            returnValue.add(messageDto);
        }

        return returnValue;


    }

    @Override
    public void deleteMessage(long id, String userId) {
        MessageEntity msg = this.messageRepo.findById(id);
        UserEntity currUser = this.userRepo.findByUserId(userId);

        if (msg == null) throw new RuntimeException("Can't delete message!Cause message with such id doesn't exist! "); //check if is ended

        if (msg.getSender() == currUser){
            msg.setDeletedBySender(true);
            messageRepo.save(msg);
        }

        if (msg.getReceiver() == currUser){
            msg.setDeletedByReceiver(true);
            messageRepo.save(msg);
        }

        if (msg.isDeletedByReceiver() && msg.isDeletedBySender()) //if is deletes from both users then delete the message from the db
            messageRepo.delete(msg);

    }

    @Override
    public void markMessageAsRead(long id) {
        MessageEntity msg = this.messageRepo.findById(id);

        if (msg == null) throw new RuntimeException("Can't mark as read message!Cause message with such id doesn't exist! "); //check if is ended

        msg.setRead(true);
        this.messageRepo.save(msg);

    }

    @Override
    public Integer getNewNotifsNumber(String currUserId) {
        UserEntity currUser = this.userRepo.findByUserId(currUserId);

        List<MessageEntity> newMessages = messageRepo.findByReceiverAndReadFalse(currUser);

        return newMessages.size();

    }

    @Override
    public List<MessageDto> getSentMessagesFromCurrentUserToOtherUser(String currUserId, String otherUserId) {
        List<MessageDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity currUser = this.userRepo.findByUserId(currUserId);
        UserEntity otherUser = this.userRepo.findByUserId(otherUserId);

        if (otherUser == null) throw new RuntimeException("Not existing user with such id!"); //check if is ended

        List<MessageEntity> messagesSentList = messageRepo.findBySenderAndReceiver(currUser,otherUser); //find all the records in Connective Table tha refer to currUser

        Collections.reverse(messagesSentList);

        for (MessageEntity messageEntity : messagesSentList) {
            MessageDto messageDto = modelMapper.map(messageEntity, MessageDto.class);
            returnValue.add(messageDto);
        }

        return returnValue;
    }

    @Override
    public void sendMessage(String currUserId, String otherUserId, String message,String subject) {
        UserEntity currUser = this.userRepo.findByUserId(currUserId);
        UserEntity otherUser = this.userRepo.findByUserId(otherUserId);

        if (otherUser == null) throw new RuntimeException("Not existing user with such id!"); //check if is ended
        if (currUser.equals(otherUser)) throw new RuntimeException("User can't message to himself!"); //check if is ended

        MessageEntity msgEntity = new MessageEntity();
        msgEntity.setSender(currUser);
        msgEntity.setReceiver(otherUser);
        msgEntity.setMessage(message);
        msgEntity.setSubject(subject);
        msgEntity.setRead(false);

        msgEntity.setDeletedByReceiver(false);
        msgEntity.setDeletedBySender(false);

        this.messageRepo.save(msgEntity);


    }


}
