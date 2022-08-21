package com.mundo.disney.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mundo.disney.Entities.Genre;
import com.mundo.disney.excepciones.Excepcion;
import com.mundo.disney.repositories.GenreRepository;


@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	GenreRepository generoRepo;
	@Autowired
	private  Validator validator;
	
	@Override
	public List<Genre> getAll() {
		
		return generoRepo.findAll();
	}
	
	@Override
	public Optional<Genre> getById(Long idGenero){
		return generoRepo.findById(idGenero);
	}

	@Override
	public void insert(Genre genero) throws Excepcion {
		
		Set<ConstraintViolation<Genre>> cv = validator.validate(genero);
		if(cv.size()>0)
		{
			String err="";
			for (ConstraintViolation<Genre> constraintViolation : cv) {
				err+=constraintViolation.getPropertyPath()+": "+constraintViolation.getMessage()+"\n";
			}
			throw new Excepcion(err,400);
		}
		else if(generoRepo.findByNombre(genero.getNombre())!= null)
		{
			throw new Excepcion("El genero que desea guardar ya se encuentra en la base de datos.",400);
		}
		else
			generoRepo.save(genero);

	}

}
