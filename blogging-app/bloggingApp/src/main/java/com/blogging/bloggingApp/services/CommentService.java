package com.blogging.bloggingApp.services;

import com.blogging.bloggingApp.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);
}
