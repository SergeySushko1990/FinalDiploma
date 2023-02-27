package com.example.finalproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "sender_id")
    private long senderId;

    @Column(name="recipientId")
    private long recipientId;

    @Column(name="text")
    private String text;

    @Column(name="time")
    private String time;

}
