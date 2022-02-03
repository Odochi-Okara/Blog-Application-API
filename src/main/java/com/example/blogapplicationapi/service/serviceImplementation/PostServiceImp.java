package com.example.blogapplicationapi.service.serviceImplementation;

import com.example.blogapplicationapi.model.Person;
import com.example.blogapplicationapi.model.Post;
import com.example.blogapplicationapi.repository.PostRepository;
import com.example.blogapplicationapi.repository.UserRepository;
import com.example.blogapplicationapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImp implements PostService {


    private final PostRepository repository;

    private final UserRepository userRepository;

    private final UserServiceImp userServiceImp;

    @Autowired
    public PostServiceImp(PostRepository repository, UserRepository userRepository, UserServiceImp userServiceImp) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.userServiceImp = userServiceImp;
    }

    @Override
    public Post createPost(Post post, Person person) {
        post.setPerson(person);
        return repository.save(post);
    }


    public Post getPostById(Long id) {
        Post post = repository.getPostById(id);
        return post;
    }

    @Override
    public void deletePost(Post post) {
        repository.delete(post);
    }

    @Override
    public List<Post> getAllOfPosts() {
        List<Post> posts = repository.findAll();
        return posts;
    }


    public  List<Post> findPostByUser(Long user_id){
        Person person = userRepository.getById(user_id);
        return repository.findPostByPerson(person);
    }

    public Integer getLikes(Long post_id){
        Post post = repository.getById(post_id);
        return post.getPostLikes().size();
    }

    @Override
    public List<Person> addLikes(Long post_id, Long user_id) {

        Person person = userRepository.getById(user_id);
        Post post = repository.getById(post_id);

         post.getPostLikes().add(person);
         repository.save(post);
         return post.getPostLikes();
    }

    @Override
    public  String addPostToFavouriteList(Long user_id,Long post_id){
        Post post = repository.getById(post_id);

        Person person = userRepository.getById(user_id);

        if(person.getFavourite().contains(post)){
            return "Post is already in favourite";
        }

        person.getFavourite().add(post);
        userRepository.save(person);
        return  "Post added to favourite";
    }

    @Override
    public List<Post> getListOfFavouritePosts(Long user_id) {

        Person person = userRepository.getById(user_id);

        return person.getFavourite();
  }

    @Override
    public List<Post> findPostByFriends(Long user_id){
        Person person = userRepository.getById(user_id);
        List<Person> listOfFriends = userServiceImp.getFriends(user_id);

        List<Post> listOfFriendsMadeByFriends = new ArrayList<>();

        for (int i = 0; i < listOfFriends.size(); i++) {
//            listOfFriends.
        }
        return null;
    }
}
