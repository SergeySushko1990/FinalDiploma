package com.example.finalproject.Repository;

import com.example.finalproject.Entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    List<Friends> findBySenderIdOrRecipientId(Long id, Long id2);


    @Query(value = "select * from friends where (sender_id=?1 and recipient_id=?2) or (sender_id=?2 and recipient_id=?1)", nativeQuery = true)
    long fromFriends(long id, long id2);


}
