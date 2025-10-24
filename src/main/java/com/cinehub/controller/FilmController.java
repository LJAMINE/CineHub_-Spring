package com.cinehub.controller;


import com.cinehub.dto.FilmDto;
import com.cinehub.dto.FilmRequest;
import com.cinehub.model.Film;
import com.cinehub.service.FilmService;
import com.cinehub.util.EntityDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/films")
@Validated
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> get(@PathVariable("id") Long id) {
        Film film = filmService.findById(id);
        return ResponseEntity.ok(EntityDtoMapper.toFilmDto(film));
    }
    @GetMapping
    public ResponseEntity<List<FilmDto>> all() {
        List<FilmDto> dtos = filmService.findAll().stream().map(EntityDtoMapper::toFilmDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<FilmDto> create(@Valid @RequestBody FilmRequest req) {
        Film f = EntityDtoMapper.fromFilmRequest(req);
        Film saved = filmService.create(f);
        return ResponseEntity.ok(EntityDtoMapper.toFilmDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmDto> update (@PathVariable Long id,@Valid @RequestBody FilmRequest req){
        Film exiFilm=filmService.findById(id);
        EntityDtoMapper.updateFilmFromRequest(exiFilm,req);
        Film updated =filmService.update(id,exiFilm);
        return ResponseEntity.ok(EntityDtoMapper.toFilmDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filmService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
