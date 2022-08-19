package com.mundo.disney.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mundo.disney.Entities.Genre;
import com.mundo.disney.excepciones.Excepcion;

@Service
public interface GenreService {

	/**
	 * Devuelve la lista completa de Personajes guardados en el sistema
	 * @return Lista de Character
	 */
	public List<Genre> getAll();
	
	/**
	 * Inserta un nuevo genero a la base de datos.
	 * @param Genre
	 * @throws Excepcion
	 */
	public void insert(Genre genero) throws Excepcion;
	
}
