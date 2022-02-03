package com.example.blogapplicationapi.service;

import com.example.blogapplicationapi.model.Comment;

public interface CommentService {

    public boolean createComment(Comment comment, Long person_id, Long post_id);
}
