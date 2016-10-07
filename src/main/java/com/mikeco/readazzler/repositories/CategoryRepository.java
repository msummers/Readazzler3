package com.mikeco.readazzler.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mikeco.readazzler.models.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
