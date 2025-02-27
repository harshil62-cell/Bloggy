package com.blogging.bloggingApp.Controllers;

import com.blogging.bloggingApp.payloads.CommentDto;
import com.blogging.bloggingApp.servicesimpl.CommentServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceimpl commentService;
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Integer postId){
        CommentDto comment = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(comment,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new String("deleted"),HttpStatus.OK);
    }
}
