package com.mundo.disney.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mundo.disney.Entities.Character;
import com.mundo.disney.excepciones.Excepcion;
import com.mundo.disney.repositories.CharacterRepository;


@Service
public class CharacterServiceImp implements CharacterService {

	@Autowired
	private  Validator validator;
	
	@Autowired
	CharacterRepository personajeRepo;
	
	@Override
	public List<Character> getAll() {
		return personajeRepo.findAll();	
		
	}
	
	@Override
	public Optional<Character> getCharacterById(Long id) {
		return personajeRepo.findById(id);
	}

	@Override
	public List<Character> getCharacterByName(String nombre) {
		
		return personajeRepo.findByNombreLike(nombre);
		
	}

	@Override
	public List<Character> getCharacterByEdad(int edad) {
		
		return personajeRepo.findByEdadEquals(edad);
		
	}

	@Override
	public List<Character> getCharacterByMovie(Long idMovie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Character> getCharacterByPeso(double peso) {
		
		return personajeRepo.findByPesoEquals(peso);
	}

	@Override
	public void update(Character personaje) throws Excepcion {
		
		Set<ConstraintViolation<Character>> cv = validator.validate(personaje);
		if(cv.size()>0)
		{
			String err="";
			for (ConstraintViolation<Character> constraintViolation : cv) {
				err+=constraintViolation.getPropertyPath()+": "+constraintViolation.getMessage()+"\n";
			}
			throw new Excepcion(err,400);
		}
		else if(!personajeRepo.findById(personaje.getId()).isPresent())
		{
			throw new Excepcion("No se encuentra el personaje que desea modificar.",400);
		}
		else
			personajeRepo.update(personaje.getId(),personaje.getNombre(),personaje.getEdad(),personaje.getPeso(), personaje.getHistoria());

	}

	@Override
	public void insert(Character personaje) throws Excepcion {
		
		Set<ConstraintViolation<Character>> cv = validator.validate(personaje);
		if(cv.size()>0)
		{
			String err="";
			for (ConstraintViolation<Character> constraintViolation : cv) {
				err+=constraintViolation.getPropertyPath()+": "+constraintViolation.getMessage()+"\n";
			}
			throw new Excepcion(err,400);
		}
		else if(personajeRepo.findByNombreLike(personaje.getNombre()).contains(personaje))
		{
			throw new Excepcion("El personaje que desea guardar ya se encuentra en la base de datos.",400);
		}
		else
			personajeRepo.save(personaje);

	}

	@Override
	public void delete(Long id) throws Excepcion {
		if(!personajeRepo.findById(id).isPresent())
		{
			throw new Excepcion("No existe un personaje con el id ingresado",400);
		}
		else
			personajeRepo.deleteById(id);
	}

}
