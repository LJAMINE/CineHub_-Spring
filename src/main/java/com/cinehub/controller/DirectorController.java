package com.cinehub.controller;


import com.cinehub.dto.DirectorDto;
import com.cinehub.dto.DirectorRequest;
import com.cinehub.dto.FilmDto;
import com.cinehub.model.Director;
import com.cinehub.model.Film;
import com.cinehub.service.DirectorService;
import com.cinehub.util.EntityDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/directors")
@Validated
public class DirectorController {


    private final DirectorService directorService;

    public DirectorController(DirectorService directorService){
        this.directorService=directorService;
    }

    @GetMapping
    public ResponseEntity<List<DirectorDto>> all (){
        List<DirectorDto>dtos=directorService.findAll().stream().map(EntityDtoMapper::toDirectorDto).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(EntityDtoMapper.toDirectorDto(directorService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<DirectorDto> create(@Valid @RequestBody DirectorRequest req) {
        Director d = EntityDtoMapper.fromDirectorRequest(req);
        Director saved = directorService.create(d);
        return ResponseEntity.ok(EntityDtoMapper.toDirectorDto(saved));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DirectorDto> update(@PathVariable Long id, @Valid @RequestBody DirectorRequest req) {
        com.cinehub.model.Director existing = directorService.findById(id);
        EntityDtoMapper.updateDirectorFromRequest(existing, req);
        com.cinehub.model.Director updated = directorService.update(id, existing);
        return ResponseEntity.ok(EntityDtoMapper.toDirectorDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        directorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/films")
    public ResponseEntity<List<FilmDto>> films(@PathVariable Long id) {
        List<Film> films = directorService.findFilmsByDirectorId(id);
        List<FilmDto> dtos = films.stream().map(EntityDtoMapper::toFilmDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DirectorDto>> search(@RequestParam("q") String q) {
        List<DirectorDto> dtos = directorService.searchByLastName(q).stream()
                .map(EntityDtoMapper::toDirectorDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

}
