package com.example.finalproject.Repository;

import com.example.finalproject.Entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<List<Photo>> findById(long id);

    @Query(value = "select * from photos where userid = ?1", nativeQuery = true)
    List<Photo> findByUserId(long id);
}
