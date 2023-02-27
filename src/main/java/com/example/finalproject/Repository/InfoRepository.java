package com.example.finalproject.Repository;

import com.example.finalproject.Entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {
    List<Info> findBySurnameContainsOrNameContains(String text, String text2);
}
