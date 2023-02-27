package com.example.finalproject.Services;

import com.example.finalproject.Entity.Info;
import com.example.finalproject.Exceptions.UserException;
import com.example.finalproject.Repository.InfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService {

        private final InfoRepository infoRepository;

        @Transactional
        public void saveInfo(Info info){
           infoRepository.save(info);
        }

        public List<Info> findUser(String text, String text2){
            text2 = text;
           List<Info> users =  infoRepository.findBySurnameContainsOrNameContains(text, text2);
           if(users.isEmpty()){
               throw new UserException();
           }
           return users;
        }
}
