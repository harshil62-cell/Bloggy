package com.blogging.bloggingApp.Repositories;

import com.blogging.bloggingApp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {


}
