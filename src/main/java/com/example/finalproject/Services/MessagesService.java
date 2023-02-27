package com.example.finalproject.Services;

import com.example.finalproject.Entity.Messages;
import com.example.finalproject.Exceptions.MessagesException;
import com.example.finalproject.Exceptions.NotFoundDialogsException;
import com.example.finalproject.Repository.MessagesRepository;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;


    public MessagesService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public Messages sendMessage(Messages messages) {
        messages.setTime(String.valueOf(LocalDateTime.now()));
        return messagesRepository.save(messages);
    }

    public List<Messages> findMessagesWithUser(long id, long id2){
        List<Messages> messages = messagesRepository.findMessagesWithUser(id, id2);
        if (messages.isEmpty()) {
            throw new MessagesException();
        }
       return messages;
    }

    public List<Long> findDialogs(long id, long id2){
        id2 = id;
        List<Long> messages = messagesRepository.findBySenderIdOrRecipientId(id, id2).stream()
                .map(key -> key.getSenderId())
                .filter(k -> k != id)
                .distinct()
                .collect(Collectors.toList());
        List<Long> messages2 = messagesRepository.findBySenderIdOrRecipientId(id, id2).stream()
                .map(key -> key.getRecipientId())
                .filter(k -> k != id)
                .distinct()
                .collect(Collectors.toList());
        List<Long> finalMessages = Stream.concat(messages.stream(), messages2.stream()).distinct().toList();
        if (finalMessages.isEmpty()){
            throw new NotFoundDialogsException();
        }
        return finalMessages;

    }
}
