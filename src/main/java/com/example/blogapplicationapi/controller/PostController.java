package com.example.blogapplicationapi.controller;

import com.example.blogapplicationapi.model.Person;
import com.example.blogapplicationapi.model.Post;
import com.example.blogapplicationapi.repository.UserRepository;
import com.example.blogapplicationapi.service.serviceImplementation.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {


    private final PostServiceImp postServiceImp;

    private final UserRepository userRepository;

    @Autowired
    public PostController(PostServiceImp postServiceImp, UserRepository userRepository) {
        this.postServiceImp = postServiceImp;
        this.userRepository = userRepository;
    }


    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody Post post, HttpSession session){
        Long id = (Long) session.getAttribute("id");
        Person person = userRepository.getById(id);

        if(post.getDescription().length()== 0){
           return new ResponseEntity<>("Enter a text", HttpStatus.BAD_REQUEST);
        }
        postServiceImp.createPost(post, person);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(){
       List<Post> posts = postServiceImp.getAllOfPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/postsByUser")
    public ResponseEntity<?> postsByUser(HttpSession session){

        Long user_id = (Long) session.getAttribute("id");
        List<Post> posts = postServiceImp.findPostByUser(user_id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/addPostToFavourite/{post_id}")
    public ResponseEntity<?> addPostToFavourite(@PathVariable Long post_id, HttpSession session){

        Long user_id = (Long) session.getAttribute("id");
        postServiceImp.addPostToFavouriteList(user_id,post_id);
        return new ResponseEntity<>("post has been added", HttpStatus.OK);
    }

    @GetMapping("/favouritePosts")
    public  ResponseEntity<?> getListOfFavouritePosts(HttpSession session){
        Long user_id = (Long) session.getAttribute("id");
        List<Post> posts = postServiceImp.getListOfFavouritePosts(user_id);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @PostMapping("/likePost/{post_id}")
    public ResponseEntity<?> likePost(@PathVariable Long post_id, HttpSession session){
        Long user_id = (Long) session.getAttribute("id");
        postServiceImp.addLikes(post_id,user_id);
        return new ResponseEntity<>("post has been liked", HttpStatus.OK);
    }

    @GetMapping("/getLikes/{post_id}")
    public  ResponseEntity<?> getLikes(@PathVariable Long post_id){

       Integer numberOfLikes = postServiceImp.getLikes(post_id);
       return new ResponseEntity<>(numberOfLikes, HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/{id}")
    public  ResponseEntity<?> deletePost(@PathVariable("id") Long id, HttpSession session){

        Long id2 = (Long) session.getAttribute("id");
        Person person = userRepository.getById(id2);

        if (person == null){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        Post post = postServiceImp.getPostById(id);
        postServiceImp.deletePost(post);
        return new ResponseEntity<>( "Post has been deleted", HttpStatus.OK);
    }

}
