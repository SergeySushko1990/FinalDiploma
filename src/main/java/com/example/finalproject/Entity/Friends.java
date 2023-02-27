package com.example.finalproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="friends")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friends {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="sender_id")
    private long senderId;

    @Column(name="recipient_id")
    private long recipientId;

    @Column(name="verification")
    private String verification;

}
