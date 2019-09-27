package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getInboxMessagesFromOtherUser(String currUserId, String otherUserId);

    List<MessageDto> getSentMessagesFromCurrentUserToOtherUser(String currUserId, String otherUserId);

    void sendMessage(String currUserId, String otherUserId, String message,String subject);

    List<MessageDto> getAllInboxMessages(String currUserId);

    List<MessageDto> getAllSentMessagesFromCurrentUserToAllOtherUsers(String currUserId);

    void deleteMessage(long id, String userId);

    void markMessageAsRead(long id);

    Integer getNewNotifsNumber(String currUserId);
}
