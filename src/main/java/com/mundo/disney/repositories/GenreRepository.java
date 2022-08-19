package com.mundo.disney.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundo.disney.Entities.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

	public Genre findByNombre(String nombre);
	
}
