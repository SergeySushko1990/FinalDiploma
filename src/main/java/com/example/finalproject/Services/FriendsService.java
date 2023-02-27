package com.example.finalproject.Services;

import com.example.finalproject.Entity.Friends;
import com.example.finalproject.Exceptions.FriendsException;
import com.example.finalproject.Repository.FriendsRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Data
@AllArgsConstructor
public class FriendsService {

    private final FriendsRepository friendsRepository;

    public List<Long> findFriends(long id, long id2){
        id2 = id;
        List<Long> friends = friendsRepository.findBySenderIdOrRecipientId(id, id2).stream()
                .map(key -> key.getSenderId())
                .filter(k -> k != id)
                .distinct()
                .collect(Collectors.toList());
        List<Long> friends2 = friendsRepository.findBySenderIdOrRecipientId(id, id2).stream()
                .map(key -> key.getRecipientId())
                .filter(k -> k != id)
                .distinct()
                .collect(Collectors.toList());
        List<Long> totalFriends = Stream.concat(friends.stream(), friends2.stream()).distinct().toList();
        if (totalFriends.isEmpty()) {
            throw new FriendsException();
        }
        return totalFriends;
    }

    @Transactional
    public void addFriend(Friends friend){
        friend.setVerification("true");
        friendsRepository.save(friend);
    }

    @Transactional
    public void deleteFriend(long id, long id2){
        long d = friendsRepository.fromFriends(id, id2);
        friendsRepository.deleteById(d);
    }

}
