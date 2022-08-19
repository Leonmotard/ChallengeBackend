package com.mundo.disney.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mundo.disney.Entities.Movie;
import com.mundo.disney.excepciones.Excepcion;

@Service
public interface MovieService {

	/**
	 * Devuelve la lista completa de Peliculas guardados en el sistema
	 * @return Lista de Movie
	 */
	public List<Movie> getAll();
	
	/**
	 * Devuelve una lista Peliculas filtrados por su nombre
	 * @return List<Movie>
	 */
	public List<Movie> getMovieByName(String nombre);
	
	
	
	/**
	 * Devuelve una lista de Peliculas filtradas por su genero
	 * @return List<Movie>
	 */
	public List<Movie> getMovieByGenre(Long idGenre);
	
	
	/**
	 * Actualiza datos de una Pelicula
	 * @param pelicula
	 * @throws Excepcion 
	 */
	public void update(Movie pelicula) throws Excepcion;
	
	/**
	 * Inserta un nuevo pelicula en la base de datos
	 * @param pelicula
	 * @throws Excepcion
	 */
	public void insert(Movie pelicula) throws Excepcion;
	
	/**
	 * Elimina un pelicula
	 * @param int id
	 * @throws Excepcion
	 */
	public void delete(Long id) throws Excepcion;

}
