package com.example.finalproject.Controllers;

import com.example.finalproject.DTO.UsersDTO;
import com.example.finalproject.Entity.Info;
import com.example.finalproject.Entity.Users;
import com.example.finalproject.Exceptions.UserException;
import com.example.finalproject.Exceptions.UserResponse;
import com.example.finalproject.Services.UserInfoService;
import com.example.finalproject.Services.UsersServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
@AllArgsConstructor
public class Profiles {

    private final UsersServices usersServices;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoService userInfoService;

    @GetMapping("/users/all")
    public List<UsersDTO> allUsers(){
        return usersServices.findAll().stream().map(this::convertToUsers)
                .collect(Collectors.toList());
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> saveUser(@RequestBody Users user, BindingResult bindingResult){
        if (bindingResult.hasErrors())
        {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            usersServices.save(user);
            Info info = new Info();
            info.setUserId(user.getId());
            userInfoService.saveInfo(info);
            return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/users/finduser/{text}")
    public List<Info> findUser(@PathVariable("text") String text){
        return userInfoService.findUser(text, text);
    }

    @ExceptionHandler
    private ResponseEntity<UserResponse> handlerException(UserException e){
        UserResponse response = new UserResponse(
                "Error: User not found"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/users/update")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody Users user, Principal principal){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(usersServices.userIdInfo(principal));
        usersServices.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/users/updateinfo")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody Info info, Principal principal){
        info.setUserId(usersServices.userIdInfo(principal));
        userInfoService.saveInfo(info);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    public UsersDTO convertToUsers(Users users){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(users, UsersDTO.class);
    }

    public Users convertToDTO(UsersDTO usersDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(usersDTO, Users.class);
    }

}
