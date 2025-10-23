package com.cinehub.repository;

import com.cinehub.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectorRepository extends JpaRepository<Director,Long> {
    List<Director> findByLastNameContainingIgnoreCase(String lastName);

}
