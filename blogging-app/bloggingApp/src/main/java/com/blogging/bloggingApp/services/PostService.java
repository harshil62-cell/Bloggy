package com.blogging.bloggingApp.services;

import com.blogging.bloggingApp.entities.Category;
import com.blogging.bloggingApp.entities.Post;
import com.blogging.bloggingApp.entities.User;
import com.blogging.bloggingApp.payloads.PostDto;
import com.blogging.bloggingApp.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    //update
    PostDto updatePost(PostDto postDto,Integer postId);
    //delete
    void deletePost(Integer postId);
    //get all posts
    PostResponse getAllPost(Integer pageSize, Integer pageNumber,String sortBy,String sortDir);
    //get single post
    PostDto getPost(Integer postId);
    //get all posts by category
    PostResponse getPostsByCategory(Integer categoryId,Integer pageSize,Integer pageNumber);
    //get all posts of a user
    PostResponse getPostsByUser(Integer id,Integer pageSize,Integer pageNumber);
    //search posts
    List<PostDto> searchPosts(String keyword);

}
