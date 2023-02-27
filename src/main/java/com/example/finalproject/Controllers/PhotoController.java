package com.example.finalproject.Controllers;

import com.example.finalproject.DTO.PhotoDTO;
import com.example.finalproject.DTO.UsersDTO;
import com.example.finalproject.Entity.Photo;
import com.example.finalproject.Entity.Users;
import com.example.finalproject.Exceptions.PersonWithoutPhotoResponse;
import com.example.finalproject.Exceptions.WithoutPhotoException;
import com.example.finalproject.Repository.PhotoRepository;
import com.example.finalproject.Services.PhotoService;
import com.example.finalproject.Services.UsersServices;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class PhotoController {

    private final PhotoRepository photoRepository;

    private final UsersServices usersServices;

    private final PhotoService photoService;

    public PhotoController(PhotoRepository photoRepository, UsersServices usersServices, PhotoService photoService) {
        this.photoRepository = photoRepository;
        this.usersServices = usersServices;
        this.photoService = photoService;
    }


    @PostMapping("users/photo/upload")
    public String uploadPhoto(@RequestParam("file") MultipartFile file, Principal principal) throws Exception {
        String name = String.valueOf(UUID.randomUUID());
        String Path_Directory = new ClassPathResource("static/images/").getFile().getAbsolutePath();
        Files.copy(file.getInputStream(), Paths.get(Path_Directory+File.separator+name+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        Photo photo = new Photo();
        photo.setUserid(usersServices.userIdInfo(principal));
        photo.setPhoto(Path_Directory);
        photo.setTime(String.valueOf(LocalDateTime.now()));
        photoRepository.save(photo);
        return "Successfully Photo is uploaded";
    }

    @GetMapping("/users/photo/{id}")
    public List<PhotoDTO> userPhoto(@PathVariable("id") long id, Photo Photo){
        return photoService.findByUserId(id).stream()
                .map(this::convertToPhoto)
                .collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<PersonWithoutPhotoResponse> handlerException(WithoutPhotoException e){
        PersonWithoutPhotoResponse response = new PersonWithoutPhotoResponse(
                "Error: User without photo"
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public PhotoDTO convertToPhoto(Photo photo){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(photo, PhotoDTO.class);
    }

    public Photo convertToDTO(PhotoDTO photoDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(photoDTO, Photo.class);
    }

}
