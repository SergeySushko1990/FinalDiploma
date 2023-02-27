package com.example.finalproject.Repository;

import com.example.finalproject.Entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
    @Query(value = "SELECT * FROM messages m WHERE (m.sender_id = ?1 OR m.recipient_id = ?1) AND (m.sender_id = ?2 OR m.recipient_id = ?2)",
            nativeQuery = true)
    List<Messages> findMessagesWithUser(long id, long id2);

    List<Messages> findBySenderIdOrRecipientId(long id, long id2);

}
