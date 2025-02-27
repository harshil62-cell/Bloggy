package com.blogging.bloggingApp.Repositories;

import com.blogging.bloggingApp.entities.Category;
import com.blogging.bloggingApp.entities.Post;
import com.blogging.bloggingApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    Page<Post> findByUser(User u,Pageable p);
    Page<Post> findByCategory(Category c, Pageable p);
    @Query("SELECT p FROM Post p WHERE p.title LIKE :key")
    List<Post> searchByTitle(@Param("key") String title);
    List<Post> findByUser(User u);
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.postId = :postId")
    Post findByUserIdAndPostId(@Param("userId") Integer userId, @Param("postId") Integer postId);




}
