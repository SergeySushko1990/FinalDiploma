package com.example.finalproject.Services;

import com.example.finalproject.Entity.Users;
import com.example.finalproject.Repository.UsersRepository;
import com.example.finalproject.Security.UsersDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServices implements UserDetailsService {

    private final UsersRepository usersRepository;


    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    public Optional<Users> findOne(long id){
        return usersRepository.findById(id);
    }

    @Transactional
    public void save(Users user){
        user.setTime(String.valueOf(LocalDateTime.now()));
        user.setRole("ROLE_USER");
        usersRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = usersRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new UsersDetails(user.get());
    }

    public long userIdInfo(Principal principal){
        Optional<Users> user = usersRepository.findByUsername(principal.getName());
        return user.get().getId();

    }

    @Transactional
    public void deleteUser(long id){
        usersRepository.deleteById(id);
    }
}
