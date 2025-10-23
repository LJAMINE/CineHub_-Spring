package com.cinehub.service;

import com.cinehub.model.Director;
import com.cinehub.model.Film;

import java.util.List;

public interface DirectorService {
    Director create(Director director);
    Director update(Long id, Director director);
    void delete(Long id);
    Director findById(Long id);
    List<Director> findAll();
    List<Film> findFilmsByDirectorId(Long id);
    List<Director> searchByLastName(String lastName);
}