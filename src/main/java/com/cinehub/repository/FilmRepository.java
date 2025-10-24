package com.cinehub.repository;

import com.cinehub.model.Category;
import com.cinehub.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    //    List<Film> findByDirectorId(Long directorId);
//    List<Film> findByCategoryId(Long categoryId);
    @Query("SELECT f FROM Film f WHERE f.director.idDirector = :directorId")
    List<Film> findByDirectorId(@Param("directorId") Long directorId);

    @Query("SELECT f FROM Film f WHERE f.category.idCategory = :categoryId")
    List<Film> findByCategoryId(@Param("categoryId") Long categoryId);

}
