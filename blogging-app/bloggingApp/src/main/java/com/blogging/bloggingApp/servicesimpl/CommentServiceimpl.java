package com.blogging.bloggingApp.servicesimpl;

import com.blogging.bloggingApp.Exceptions.ResourceNotFoundException;
import com.blogging.bloggingApp.Repositories.CommentRepo;
import com.blogging.bloggingApp.Repositories.PostRepo;
import com.blogging.bloggingApp.Repositories.UserRepo;
import com.blogging.bloggingApp.entities.Comment;
import com.blogging.bloggingApp.entities.Post;
import com.blogging.bloggingApp.entities.User;
import com.blogging.bloggingApp.payloads.CommentDto;
import com.blogging.bloggingApp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CommentServiceimpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postReo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        //User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
        Post byPost = this.postReo.findById(postId).
                orElseThrow(()->new ResourceNotFoundException("post","postid",postId));
        Comment c = this.modelMapper.map(commentDto, Comment.class);
        c.setPost(byPost);
        Comment saved = this.commentRepo.save(c);
        return this.modelMapper.map(saved,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("comment", "comment id", commentId)
        );
        this.commentRepo.delete(comment);

    }
}
