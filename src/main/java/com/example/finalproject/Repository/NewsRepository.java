package com.example.finalproject.Repository;

import com.example.finalproject.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByTextContains(String text);

    @Query(value = "select * from news where user_id=?1", nativeQuery = true)
    List<News> findByUser_id(long id);
}
