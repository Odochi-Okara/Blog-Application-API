package com.example.blogapplicationapi.model;

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
public class Comment {

    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy  = SEQUENCE,
            generator = "comment_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private Person person;

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonManagedReference(value = "uses")
    private Post post;


    @OneToMany
    @JoinColumn
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Person> commentLikes;

    public Comment(String description) {
        this.description = description;
    }
}
