package com.example.finalproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="user_id")
    private long user_id;

    @Column(name="text")
    private String text;

    @Column(name="time")
    private String time;

}
