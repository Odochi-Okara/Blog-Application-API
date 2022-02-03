package com.example.blogapplicationapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue (strategy  = SEQUENCE,
            generator = "post_sequence")
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(45)")
    private String description;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    @JsonBackReference( value = "favourite")
    private Person person;

    @OneToMany
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
//   @JsonManagedReference(value = "postlikes")
    private List<Person> postLikes;


    @OnDelete( action =  OnDeleteAction.CASCADE)
    @OneToMany
    @JoinColumn
    private List<Comment> comments;

    public Post(String description, Person person, List<Person> postLikes, List<Comment> comments) {
        this.description = description;
        this.person = person;
        this.postLikes = postLikes;
        this.comments = comments;
    }
}
