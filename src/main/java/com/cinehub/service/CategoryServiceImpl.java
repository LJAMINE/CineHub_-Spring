package com.cinehub.service;

import com.cinehub.exception.DeleteNotAllowedException;
import com.cinehub.exception.ResourceNotFoundException;
import com.cinehub.model.Category;
import com.cinehub.model.Film;
import com.cinehub.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public Category create(Category category) {
        return repo.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        Category existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        if (existing.getFilms() != null && !existing.getFilms().isEmpty()) {
            throw new DeleteNotAllowedException("Cannot delete category with associated films (id: " + id + ")");
        }
        repo.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category findByName(String name) {
        return repo.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Film> findFilmsByCategoryId(Long id) {
        Category c = findById(id);
        return c.getFilms();
    }
}