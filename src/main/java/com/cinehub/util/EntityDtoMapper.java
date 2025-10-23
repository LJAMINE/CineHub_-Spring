package com.cinehub.util;

import com.cinehub.dto.*;
import com.cinehub.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class EntityDtoMapper {

    private EntityDtoMapper() {}

    // Film mapping already present in your project (kept)
    public static com.cinehub.dto.FilmDto toFilmDto(Film f) {
        if (f == null) return null;
        com.cinehub.dto.FilmDto dto = new com.cinehub.dto.FilmDto();
        dto.setId(f.getIdFilm());
        dto.setTitle(f.getTitle());
        dto.setReleaseYear(f.getReleaseYear());
        dto.setDuration(f.getDuration());
        dto.setSynopsis(f.getSynopsis());
        dto.setRating(f.getRating());
        if (f.getDirector() != null) {
            dto.setDirectorId(f.getDirector().getIdDirector());
            dto.setDirectorName(f.getDirector().getFirstName() + " " + f.getDirector().getLastName());
        }
        if (f.getCategory() != null) {
            dto.setCategoryId(f.getCategory().getIdCategory());
            dto.setCategoryName(f.getCategory().getName());
        }
        return dto;
    }

    public static DirectorDto toDirectorDto(Director d) {
        if (d == null) return null;
        DirectorDto dto = new DirectorDto();
        dto.setId(d.getIdDirector());
        dto.setFirstName(d.getFirstName());
        dto.setLastName(d.getLastName());
        dto.setNationality(d.getNationality());
        if (d.getBirthDate() != null) dto.setBirthDate(d.getBirthDate().toString());
        dto.setBiography(d.getBiography());
        return dto;
    }

    public static CategoryDto toCategoryDto(Category c) {
        if (c == null) return null;
        CategoryDto dto = new CategoryDto();
        dto.setId(c.getIdCategory());
        dto.setName(c.getName());
        dto.setDescription(c.getDescription());
        return dto;
    }

    // create entity from request DTO (used for create)
    public static Director fromDirectorRequest(DirectorRequest req) {
        if (req == null) return null;
        Director d = new Director();
        d.setFirstName(req.getFirstName());
        d.setLastName(req.getLastName());
        d.setNationality(req.getNationality());
        d.setBiography(req.getBiography());
        if (req.getBirthDate() != null) {
            try {
                d.setBirthDate(LocalDate.parse(req.getBirthDate()));
            } catch (DateTimeParseException e) {
                // leave null; validation/parsing errors should be handled in service/controller
            }
        }
        return d;
    }

    public static Category fromCategoryRequest(CategoryRequest req) {
        if (req == null) return null;
        Category c = new Category();
        c.setName(req.getName());
        c.setDescription(req.getDescription());
        return c;
    }

    // update existing entity from request (used for update)
    public static void updateDirectorFromRequest(Director existing, DirectorRequest req) {
        if (existing == null || req == null) return;
        existing.setFirstName(req.getFirstName());
        existing.setLastName(req.getLastName());
        existing.setNationality(req.getNationality());
        existing.setBiography(req.getBiography());
        if (req.getBirthDate() != null) {
            try {
                existing.setBirthDate(LocalDate.parse(req.getBirthDate()));
            } catch (DateTimeParseException ignored) {}
        }
    }

    public static void updateCategoryFromRequest(Category existing, CategoryRequest req) {
        if (existing == null || req == null) return;
        existing.setName(req.getName());
        existing.setDescription(req.getDescription());
    }
}