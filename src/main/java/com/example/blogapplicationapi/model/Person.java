package com.example.blogapplicationapi.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {

    @Id
    @SequenceGenerator(name = "person_sequence", sequenceName = "person_sequence", allocationSize = 1)
    @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "person_sequence")
    private Long user_id;

    @Column( nullable = false, columnDefinition = "VARCHAR(45)")
    private String username;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(45)")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(45)")
    private String password;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

  @ManyToMany
  @JsonManagedReference( value = "favourite")
  @JoinColumn
  @OnDelete( action =  OnDeleteAction.CASCADE)
  private List<Post> favourite;

    @ManyToMany
//  @JsonManagedReference (value = "uses")
    @JoinColumn
    @OnDelete(action =  OnDeleteAction.CASCADE)
    private List<Person> friends;

    @OneToOne
    private Likes likes;

    public Person(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
