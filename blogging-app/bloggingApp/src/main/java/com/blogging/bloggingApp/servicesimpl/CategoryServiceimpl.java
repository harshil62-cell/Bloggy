package com.blogging.bloggingApp.servicesimpl;

import com.blogging.bloggingApp.Exceptions.ResourceNotFoundException;
import com.blogging.bloggingApp.Repositories.CategoryRepo;
import com.blogging.bloggingApp.entities.Category;
import com.blogging.bloggingApp.payloads.CategoryDto;
import com.blogging.bloggingApp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceimpl implements CategoryService {


    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category saved = this.categoryRepo.save(category);
        return this.modelMapper.map(saved,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category saved = this.categoryRepo.save(category);
        return this.modelMapper.map(saved,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","Category id",categoryId));
        this.categoryRepo.delete(category);


    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","Category id",categoryId));
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> all = this.categoryRepo.findAll();
        List<CategoryDto> dtoList=new ArrayList<>();
        for(Category c:all){
            dtoList.add(this.modelMapper.map(c, CategoryDto.class));
        }
        return dtoList;
    }
}
