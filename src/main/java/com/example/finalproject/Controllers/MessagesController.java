package com.example.finalproject.Controllers;

import com.example.finalproject.DTO.MessagesDTO;
import com.example.finalproject.DTO.UsersDTO;
import com.example.finalproject.Entity.Messages;
import com.example.finalproject.Entity.Users;
import com.example.finalproject.Exceptions.MessagesException;
import com.example.finalproject.Exceptions.MessagesResponse;
import com.example.finalproject.Exceptions.NotFoundDialogsResponse;
import com.example.finalproject.Services.MessagesService;
import com.example.finalproject.Services.UsersServices;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.NotDirectoryException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/messages")
public class MessagesController {

    private final UsersServices usersServices;

    private final MessagesService messagesService;

    public MessagesController(UsersServices usersServices, MessagesService messagesService) {
        this.usersServices = usersServices;
        this.messagesService = messagesService;
    }

    @PostMapping("/send/{rec_id}")
    public ResponseEntity<HttpStatus> sendMessage(@PathVariable("rec_id") long id, @RequestBody Messages message, Principal principal){
        if (!message.getText().isEmpty())
        {
            message.setRecipientId(id);
            message.setSenderId(usersServices.userIdInfo(principal));
            messagesService.sendMessage(message);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/search/{id}")
    public List<MessagesDTO> findMessagesWithUser(@PathVariable("id") long id, Principal principal){
        Long id2 = usersServices.userIdInfo(principal);
        return messagesService.findMessagesWithUser(id, id2).stream().map(this::convertToMessages).collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<MessagesResponse> handlerException(MessagesException e){
        MessagesResponse response = new MessagesResponse(
                "Error: Dialog with user not found"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/search/all")
    public List<Long> allDialogs(Messages messages, Principal principal){
        long id = usersServices.userIdInfo(principal);
        return messagesService.findDialogs(id, id);
    }

    @ExceptionHandler
    private ResponseEntity<NotFoundDialogsResponse> handlerException(NotDirectoryException e){
        NotFoundDialogsResponse response = new NotFoundDialogsResponse(
                "Error: User have not dialogs"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public MessagesDTO convertToMessages(Messages messages){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(messages, MessagesDTO.class);
    }

    public Messages convertToDTO(MessagesDTO messagesDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(messagesDTO, Messages.class);
    }

}
