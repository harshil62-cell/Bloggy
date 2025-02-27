package com.blogging.bloggingApp.payloads;

import com.blogging.bloggingApp.entities.Post;

public class CommentDto {

    private Integer Id;
    private String content;
    //private PostDto post;
    //private UserDto user;


    public CommentDto(Integer id, String content) {
        Id = id;
        this.content = content;
    }

    public CommentDto(){
        super();
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
