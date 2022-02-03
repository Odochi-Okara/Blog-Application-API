package com.example.blogapplicationapi.controller;



import com.example.blogapplicationapi.model.Comment;
import com.example.blogapplicationapi.model.Person;
import com.example.blogapplicationapi.repository.UserRepository;
import com.example.blogapplicationapi.service.serviceImplementation.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class CommentController {
    private final CommentServiceImp commentService;
    private final UserRepository userRepository;

    @Autowired
    public CommentController(CommentServiceImp commentService, UserRepository userRepository) {
        this.commentService = commentService;
        this.userRepository = userRepository;
    }

    @PostMapping("/createComment/{id}")
    public ResponseEntity<?> createComment (@PathVariable("id") Long postId, @RequestBody Comment comment, HttpSession session){
        Long id = (Long) session.getAttribute("id");
        Person person = userRepository.getById(id);

        if(person == null){
           return  new ResponseEntity("Cannot make comment", HttpStatus.BAD_REQUEST);
        }

        comment.setPerson(person);

        if(commentService.createComment(comment,person.getUser_id(),postId)){
            session.setAttribute("id",person.getUser_id() );
            return new ResponseEntity<>(comment.getDescription(), HttpStatus.OK) ;
        }
        return new ResponseEntity<>("comment not created", HttpStatus.BAD_REQUEST);
    }
}
