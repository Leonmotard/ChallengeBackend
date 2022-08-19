package com.mundo.disney.repositories;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mundo.disney.Entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	
	public List<Movie> findByTituloLike(String nombre);
	
	public List<Movie> findByGenero(Long idGenero);
	
	@Transactional
	@Modifying
	@Query(value= "UPDATE movie m SET m.titulo=?2, m.fecha_creacion = ?3, m.calificacion = ?4, m.genero_id= ?5  WHERE m.id= ?1  ", nativeQuery= true)
	public void update(Long id, String titulo, LocalDate fechaCreacion, int calificacion, Long genero);		
	
	
}
