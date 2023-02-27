package com.example.finalproject.Controllers;


import com.example.finalproject.Repository.UsersRepository;
import com.example.finalproject.Services.NewsService;
import com.example.finalproject.Services.UsersServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AdminController {

    private final UsersServices usersServices;
    private final NewsService newsService;

    @PostMapping("admin/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") long id){
         usersServices.deleteUser(id);
         return "Ok";
    }

    @PostMapping("admin/deletenews/{id}")
    public String deletePost(@PathVariable("id") long id){
        newsService.deletePost(id);
        return "Ok";
    }


}
