package com.example.blogapplicationapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    @Id
    @SequenceGenerator(name = "likes_sequence",
            sequenceName = "likes_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy  = SEQUENCE,
            generator = "likes_sequence")
    private String id;

    @OneToOne
    @JoinColumn
    private  Person person;

    @OneToOne
    @JoinColumn
    private  Post post;
}
