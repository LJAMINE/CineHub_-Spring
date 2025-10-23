package com.cinehub.service;

import com.cinehub.model.Category;
import com.cinehub.model.Film;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    Category update(Long id, Category category);
    void delete(Long id);
    Category findById(Long id);
    List<Category> findAll();
    Category findByName(String name);
    List<Film> findFilmsByCategoryId(Long id);
}