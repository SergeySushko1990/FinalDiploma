package com.example.finalproject.Services;

import com.example.finalproject.Entity.Photo;
import com.example.finalproject.Exceptions.WithoutPhotoException;
import com.example.finalproject.Repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public List<Photo> findByUserId(long id) {
        List<Photo> userPhotos = photoRepository.findByUserId(id);
        if (userPhotos.isEmpty()){
            throw new  WithoutPhotoException();
        }
        return userPhotos;
    }

    @Transactional
    public void deletePhoto(long id){
        photoRepository.deleteById(id);
    }
}
