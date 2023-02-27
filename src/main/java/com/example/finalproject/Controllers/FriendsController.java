package com.example.finalproject.Controllers;

import com.example.finalproject.DTO.FriendsDTO;
import com.example.finalproject.DTO.UsersDTO;
import com.example.finalproject.Entity.Friends;
import com.example.finalproject.Entity.Users;
import com.example.finalproject.Exceptions.FriendsException;
import com.example.finalproject.Exceptions.FriendsResponse;
import com.example.finalproject.Exceptions.PersonWithoutPhotoResponse;
import com.example.finalproject.Exceptions.WithoutPhotoException;
import com.example.finalproject.Repository.FriendsRepository;
import com.example.finalproject.Services.FriendsService;
import com.example.finalproject.Services.UsersServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/friends")
@AllArgsConstructor
public class FriendsController {

    private final FriendsService friendsService;
    private final UsersServices usersServices;
    private final FriendsRepository friendsRepository;

    @PostMapping("/addfriend/{id}")
    public void addfriend(@PathVariable("id") long recId, @RequestBody Friends friend, BindingResult bindingResult, Principal principal) {
        friend.setSenderId(usersServices.userIdInfo(principal));
        friend.setRecipientId(recId);
        friendsService.addFriend(friend);
    }

    @GetMapping("/search/{id}")
    public List<Long> searchFriends(@PathVariable("id") long id){
        return friendsService.findFriends(id, id);
    }

    @ExceptionHandler
    private ResponseEntity<FriendsResponse> handlerException(FriendsException e){
        FriendsResponse response = new FriendsResponse(
                "Error: Friends not found"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFriend(@PathVariable("id") long id, Principal principal){
        long id2 = usersServices.userIdInfo(principal);
        friendsService.deleteFriend(id, id2);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public FriendsDTO convertToFriends(Friends friends){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(friends, FriendsDTO.class);
    }

    public Friends convertToDTO(FriendsDTO friendsDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(friendsDTO, Friends.class);
    }
}
