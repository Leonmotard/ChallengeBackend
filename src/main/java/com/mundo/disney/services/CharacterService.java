package com.mundo.disney.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mundo.disney.Entities.Character;
import com.mundo.disney.excepciones.Excepcion;

@Service
public interface CharacterService {

	
	/**
	 * Devuelve la lista completa de Personajes guardados en el sistema
	 * @return Lista de Character
	 */
	public List<Character> getAll();
	
	
	/**
	 * Devuelve un personaje dado su ID.
	 * @return Character
	 */
	public Optional<Character> getCharacterById(Long id);
	
	/**
	 * Devuelve una lista personajes filtrados por su nombre
	 * @return List<Character>
	 */
	public List<Character> getCharacterByName(String nombre);
	
	
	/**
	 * Devuelve una lista de personajes filtrados por su edad
	 * @return List<Character>
	 */
	public List<Character> getCharacterByEdad(int edad);
	
	/**
	 * Devuelve una lista de personajes filtrados por la pelicula o serie en la que participan
	 * @return List<Character>
	 */
	public List<Character> getCharacterByMovie(Long idMovie);
	
	
	/**
	 * Devuelve una lista de personajes filtrados por su peso
	 * @return List<Character> 
	 */
	public List<Character> getCharacterByPeso(double peso);
	
	/**
	 * Actualiza datos de un Personaje
	 * @param personaje
	 * @throws Excepcion 
	 */
	public void update(Character personaje) throws Excepcion;
	
	/**
	 * Inserta un nuevo personaje en la base de datos
	 * @param personaje
	 * @throws Excepcion
	 */
	public void insert(Character personaje) throws Excepcion;
	
	/**
	 * Elimina un personaje
	 * @param int id
	 * @throws Excepcion
	 */
	public void delete(Long id) throws Excepcion;
	
	
}
