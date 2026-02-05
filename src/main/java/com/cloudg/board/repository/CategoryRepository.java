package com.cloudg.board.repository;

import com.cloudg.board.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //findAll()
    //findById()
    //save()
    //deleteById()
}

