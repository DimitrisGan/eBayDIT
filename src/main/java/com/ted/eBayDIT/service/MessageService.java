package com.ted.eBayDIT.service;


import com.ted.eBayDIT.dto.MessageDto;
import com.ted.eBayDIT.dto.UserDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getInboxMessagesFromOtherUser(String currUserId, String otherUserId);

    List<MessageDto> getSentMessagesFromCurrentUserToOtherUser(String currUserId, String otherUserId);

    void sendMessage(String currUserId, String otherUserId, String message);
}
