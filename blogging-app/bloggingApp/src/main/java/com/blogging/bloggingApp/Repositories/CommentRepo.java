package com.blogging.bloggingApp.Repositories;

import com.blogging.bloggingApp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
    //Optional<Comment> findByIdAndUser_Id(Integer commentId, Integer userId);
}
