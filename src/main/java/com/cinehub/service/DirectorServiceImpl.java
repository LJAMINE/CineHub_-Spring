package com.cinehub.service;

import com.cinehub.exception.DeleteNotAllowedException;
import com.cinehub.exception.ResourceNotFoundException;
import com.cinehub.model.Director;
import com.cinehub.model.Film;
import com.cinehub.repository.DirectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public Director create(Director director) {
        return directorRepository.save(director);
    }

    @Override
    public Director update(Long id, Director director) {

        Director existing = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found with id: " + id));
        existing.setFirstName(director.getFirstName());
        existing.setLastName(director.getLastName());
        existing.setNationality(director.getNationality());
        existing.setBirthDate(director.getBirthDate());
        existing.setBiography(director.getBiography());
        return directorRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Director existing = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found with id: " + id));
        if (existing.getFilms() != null && !existing.getFilms().isEmpty()) {
            throw new DeleteNotAllowedException("Cannot delete director with associated films (id: " + id + ")");
        }
        directorRepository.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Director findById(Long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Film> findFilmsByDirectorId(Long id) {
        Director d = findById(id);
        return d.getFilms();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Director> searchByLastName(String lastName) {
        return directorRepository.findByLastNameContainingIgnoreCase(lastName == null ? "" : lastName);
    }
}