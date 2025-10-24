package com.cinehub.service;

import com.cinehub.model.Director;
import com.cinehub.model.Film;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FilmService {
    Film create(Film film);
    Film update(Long id, Film film);
    List<Film> findAll();
    Film findById(Long id);
    void delete(Long id);

    @Transactional(readOnly = true)
    List<Film> findByDirectorId(Long directorId);

    List<Film> findByCategoryId(Long categoryId);
}
