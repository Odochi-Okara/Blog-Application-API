package com.example.blogapplicationapi.service;

import com.example.blogapplicationapi.model.Person;
import com.example.blogapplicationapi.model.Post;

import java.util.List;

public interface PostService {

    public Post createPost(Post post, Person person);

    public Post getPostById(Long id);

    public void deletePost(Post post);

    public List<Post> getAllOfPosts();

    public List<Post> getListOfFavouritePosts(Long user_id);

    public  String addPostToFavouriteList(Long user_id,Long post_id);

    public List<Post> findPostByFriends(Long user_id);

    public List<Person> addLikes(Long post_id, Long user_id);

    public Integer getLikes(Long post_id);

}
