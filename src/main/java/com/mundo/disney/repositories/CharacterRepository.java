package com.mundo.disney.repositories;

import java.util.List;

import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.mundo.disney.Entities.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {

	public List<Character> findByNombreLike(String nombre);
	
	public List<Character> findByEdadEquals(int edad);
	
	public List<Character> findByPesoEquals(double peso);
	
	@Transactional
	@Modifying
	@Query(value= "UPDATE character c SET c.nombre = ?2, c.edad= ?3, c.peso=?4 , c.historia=?5 WHERE c.id = =?1",nativeQuery= true)
	public void update(Long id, String nombre, int edad, double peso, String historia);		
	
	
	
}
