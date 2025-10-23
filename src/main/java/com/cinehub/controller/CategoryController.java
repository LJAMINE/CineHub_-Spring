package com.cinehub.controller;

import com.cinehub.dto.CategoryDto;
import com.cinehub.dto.CategoryRequest;
import com.cinehub.dto.FilmDto;
import com.cinehub.model.Film;
import com.cinehub.model.Category;
import com.cinehub.service.CategoryService;
import com.cinehub.util.EntityDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> all() {
        List<CategoryDto> dtos = categoryService.findAll().stream()
                .map(EntityDtoMapper::toCategoryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(EntityDtoMapper.toCategoryDto(categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryRequest req) {
        Category c = EntityDtoMapper.fromCategoryRequest(req);
        Category saved = categoryService.create(c);
        return ResponseEntity.ok(EntityDtoMapper.toCategoryDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest req) {
        Category existing = categoryService.findById(id);
        EntityDtoMapper.updateCategoryFromRequest(existing, req);
        Category updated = categoryService.update(id, existing);
        return ResponseEntity.ok(EntityDtoMapper.toCategoryDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/films")
    public ResponseEntity<List<FilmDto>> films(@PathVariable Long id) {
        Category c = categoryService.findById(id);
        List<Film> films = c.getFilms();
        List<FilmDto> dtos = films.stream().map(EntityDtoMapper::toFilmDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}