package com.cinehub.service;

import com.cinehub.exception.ResourceNotFoundException;
import com.cinehub.model.Category;
import com.cinehub.model.Director;
import com.cinehub.model.Film;
import com.cinehub.repository.CategoryRepository;
import com.cinehub.repository.DirectorRepository;
import com.cinehub.repository.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {


    private DirectorRepository directorRepository;
    private CategoryRepository categoryRepository;
    private FilmRepository filmRepository;

    public FilmServiceImpl(DirectorRepository directorRepository, CategoryRepository categoryRepository, FilmRepository filmRepository) {
        this.categoryRepository = categoryRepository;
        this.directorRepository = directorRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    @Transactional
    public Film create(Film film) {
        if (film.getDirector() != null && film.getDirector().getIdDirector() != null) {
            Long dirId = film.getDirector().getIdDirector();
            Director director = directorRepository.findById(dirId).orElseThrow(() -> new ResourceNotFoundException("director not found "));
            film.setDirector(director);
        }

        if (film.getCategory() != null && film.getCategory().getIdCategory() != null) {
            Long catId = film.getCategory().getIdCategory();
            Category category = categoryRepository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("category not found "));
            film.setCategory(category);
        }

        return filmRepository.save(film);
    }

    @Override
    public Film update(Long id, Film film) {

        Film existing = filmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("film not found "));
        existing.setTitle(film.getTitle());
        existing.setReleaseYear(film.getReleaseYear());
        existing.setRating(film.getRating());
        existing.setSynopsis(film.getSynopsis());
        existing.setDuration(film.getDuration());


        if (film.getDirector() != null) {
            Long dId = film.getDirector().getIdDirector();
            if (dId != null) {
                Director director = directorRepository.findById(dId)
                        .orElseThrow(() -> new ResourceNotFoundException("issue"));
                existing.setDirector(director);
            } else {
                existing.setDirector(null);
            }
        }

        if (film.getCategory() != null) {
            Long cId = film.getCategory().getIdCategory();
            if (cId != null) {
                Category category = categoryRepository.findById(cId)
                        .orElseThrow(() -> new ResourceNotFoundException("issue &"));
                existing.setCategory(category);
            } else {
                existing.setCategory(null);
            }
        }

        Film saved = filmRepository.save(existing);

        if (saved.getDirector() != null) {
            saved.getDirector().getFirstName() ;
        }
        if (saved.getCategory() != null) {
            saved.getCategory().getName();
        }
        return saved;

    }

    @Override
    @Transactional(readOnly = true)
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Film findById(Long id) {
        return filmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found movie"));
    }

    @Override
    public void delete(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found movie"));
        filmRepository.delete(film);
    }


    @Override
    public List<Film> findByDirectorId(Long directorId) {
        return filmRepository.findByDirectorId(directorId);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Film> findByCategoryId(Long categoryId) {
        return filmRepository.findByCategoryId(categoryId);
    }
}
