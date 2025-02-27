package com.blogging.bloggingApp.servicesimpl;

import com.blogging.bloggingApp.Exceptions.ResourceNotFoundException;
import com.blogging.bloggingApp.Repositories.CategoryRepo;
import com.blogging.bloggingApp.Repositories.PostRepo;
import com.blogging.bloggingApp.Repositories.UserRepo;
import com.blogging.bloggingApp.entities.Category;
import com.blogging.bloggingApp.entities.Post;
import com.blogging.bloggingApp.entities.User;
import com.blogging.bloggingApp.payloads.PostDto;
import com.blogging.bloggingApp.payloads.PostResponse;
import com.blogging.bloggingApp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceimpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
        Category category=this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category","categoryid",categoryId));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setPostDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post saved = this.postRepo.save(post);
        return this.modelMapper.map(saved,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        this.postRepo.save(post);
        return this.modelMapper.map(post,PostDto.class);

    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageSize, Integer pageNumber,String sortBy,String sortDir) {
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else{
            sort=Sort.by(sortBy).descending();
        }
        Pageable p= PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> all = this.postRepo.findAll(p);
        List<Post> content = all.getContent();
        List<PostDto> toBeSent=new ArrayList<>();
        for(Post p1:content){
            toBeSent.add(this.modelMapper.map(p1,PostDto.class));
        }
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(toBeSent);
        postResponse.setPageNumber(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalElements(all.getTotalElements());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setCurrentPageLastPage(all.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageSize,Integer pageNumber) {
        Pageable p=PageRequest.of(pageNumber,pageSize);
        Category c = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "catid", categoryId));
        Page<Post> byCategory = this.postRepo.findByCategory(c,p);
        List<Post> content = byCategory.getContent();
        List<PostDto> toBeSent=new ArrayList<>();
        for(Post p1:byCategory){
            toBeSent.add(this.modelMapper.map(p1,PostDto.class));
        }
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(toBeSent);
        postResponse.setPageNumber(byCategory.getNumber());
        postResponse.setPageSize(byCategory.getSize());
        postResponse.setTotalElements(byCategory.getTotalElements());
        postResponse.setTotalPages(byCategory.getTotalPages());
        postResponse.setCurrentPageLastPage(byCategory.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId,Integer pageSize,Integer pageNumber) {
        Pageable p=PageRequest.of(pageNumber,pageSize);
        User u=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userid",userId));
        Page<Post> byUser=this.postRepo.findByUser(u,p);
        List<PostDto> toBeSent=new ArrayList<>();
        for(Post p1:byUser){
            toBeSent.add(this.modelMapper.map(p1,PostDto.class));
        }
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(toBeSent);
        postResponse.setPageNumber(byUser.getNumber());
        postResponse.setPageSize(byUser.getSize());
        postResponse.setTotalElements(byUser.getTotalElements());
        postResponse.setTotalPages(byUser.getTotalPages());
        postResponse.setCurrentPageLastPage(byUser.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> byTitleContaining = this.postRepo.searchByTitle("%"+keyword+"%");
        List<PostDto> toBeSent=new ArrayList<>();
        for(Post p:byTitleContaining){
            toBeSent.add(this.modelMapper.map(p,PostDto.class));
        }
        return toBeSent;
    }
}
