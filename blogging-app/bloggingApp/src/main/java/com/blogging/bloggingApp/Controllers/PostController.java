package com.blogging.bloggingApp.Controllers;

import com.blogging.bloggingApp.config.AppConstants;
import com.blogging.bloggingApp.entities.Post;
import com.blogging.bloggingApp.payloads.PostDto;
import com.blogging.bloggingApp.payloads.PostResponse;
import com.blogging.bloggingApp.servicesimpl.CategoryServiceimpl;
import com.blogging.bloggingApp.servicesimpl.PostServiceimpl;
import com.blogging.bloggingApp.servicesimpl.UserServiceimpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController  {

    @Autowired
    private PostServiceimpl postServiceimpl;
    @Autowired
    private UserServiceimpl userServiceimpl;
    @Autowired
    private CategoryServiceimpl categoryServiceimpl;

    //create
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){

        PostDto post = this.postServiceimpl.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);

    }

    //get by user
    //implement pagination here as well-implemented
    @GetMapping("/user/{userId}")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
                                                        @RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                                        @RequestParam(value="pageSize",defaultValue = "5",required=false) Integer pageSize
    )
    {
        PostResponse posts=this.postServiceimpl.getPostsByUser(userId,pageSize,pageNumber);
        //List<PostDto> posts=this.postServiceimpl.getPostsByUser(userId);
        return new ResponseEntity<>(posts,HttpStatus.FOUND);
    }

    //get by category
    //implement pagination here as well-implemented
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
        @RequestParam(value="pageNumber",defaultValue="0",required=false) Integer pageNumber,
        @RequestParam(value="pageSize",defaultValue = "5",required = false) Integer pageSize
    ){
        PostResponse posts=this.postServiceimpl.getPostsByCategory(categoryId,pageSize,pageNumber);
        return new ResponseEntity<>(posts,HttpStatus.FOUND);
    }

    //get all posts
    @GetMapping("/")
    //hard coding values is not a good practice so we will make a class for it so we can use it at
    //multiple places and make changes easily
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required=false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){
        PostResponse allPost = this.postServiceimpl.getAllPost(pageSize, pageNumber,sortBy,sortDir);
        return new ResponseEntity<>(allPost,HttpStatus.OK);
    }

    //get post by id
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
         PostDto all= this.postServiceimpl.getPost(postId);
         return new ResponseEntity<>(all,HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable Integer postId){
        this.postServiceimpl.deletePost(postId);
        return new ResponseEntity<>(new String("Deleted"),HttpStatus.OK);
    }

    //update post
    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto post,@PathVariable Integer postId){
        PostDto postDto = this.postServiceimpl.updatePost(post, postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    //search by keyword
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByKeyword(@PathVariable String keyword){
        List<PostDto> postDtos = this.postServiceimpl.searchPosts(keyword);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }


}
