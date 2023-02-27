package com.example.finalproject.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @OneToOne
//    @JoinColumn(referencedColumnName = "id", name="user_id")
//    @JsonBackReference
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private UsersDTO user;

    @Column(name = "about_user")
    private String about_user;

    @Column(name = "photo")
    private String photo;

    @Column(name="user_id")
    private long userId;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

}
