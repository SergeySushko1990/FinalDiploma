package com.example.finalproject.Controllers;

import com.example.finalproject.DTO.NewsDTO;
import com.example.finalproject.Entity.News;

import com.example.finalproject.Exceptions.NewsException;
import com.example.finalproject.Exceptions.NewsResponse;
import com.example.finalproject.Services.NewsService;
import com.example.finalproject.Services.UsersServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/news")
@AllArgsConstructor
public class NewsController {

        private final NewsService newsService;

        private final UsersServices usersServices;

        @GetMapping("/search/{text}")
        public List<NewsDTO> searchPost(@PathVariable("text") String text){
            return newsService.findPost(text).stream().map(this::convertToNews).collect(Collectors.toList());
        }
        @GetMapping("/search/user/{id}")
        public List<NewsDTO> searchPostsUser(@PathVariable("id") long id){
        return newsService.findPostByUser(id).stream().map(this::convertToNews).collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<NewsResponse> handlerException(NewsException e){
        NewsResponse response = new NewsResponse(
                "Error: News not found"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

        @PostMapping("/save")
        public String savePost(@RequestBody News news, Principal principal){
            news.setUser_id(usersServices.userIdInfo(principal));
            newsService.savePost(news);
            return "OK";
        }

    public NewsDTO convertToNews(News news){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(news, NewsDTO.class);
    }

    public News convertToDTO(NewsDTO newsDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(newsDTO, News.class);
    }
}
