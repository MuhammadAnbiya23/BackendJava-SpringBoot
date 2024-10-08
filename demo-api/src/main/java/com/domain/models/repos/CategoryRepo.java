package com.domain.models.repos;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.domain.models.entities.Category;

// Derived Query dari PagingAndSortingRepository
public interface CategoryRepo extends PagingAndSortingRepository<Category, Long> { 

    Category save(Category category);

    Optional<Category> findById(Long id);

    Iterable<Category> findAll();

    void deleteById(Long id);
    
    Page<Category> findByNameContains(String name, Pageable pageable);  // mencari berdasarkan nama tapi dengan Paging

    Iterable<Category> saveAll(Iterable<Category> categories);

}
