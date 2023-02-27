package com.example.finalproject.Services;

import com.example.finalproject.Entity.News;
import com.example.finalproject.Exceptions.NewsException;
import com.example.finalproject.Repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<News> findPost(String text){
        List<News> news = newsRepository.findByTextContains(text);
        if (news.isEmpty()) {
            throw new NewsException();
        }
        return news;
    }

    public List<News> findPostByUser(long id){
        List<News> news = newsRepository.findByUser_id(id);
        if (news.isEmpty()) {
            throw new NewsException();
        }
        return news;
    }


    @Transactional
    public News savePost(News news){
        news.setTime(String.valueOf(LocalDateTime.now()));
        return newsRepository.save(news);
    }

    @Transactional
    public void deletePost(long id){
        newsRepository.deleteById(id);
    }

}
