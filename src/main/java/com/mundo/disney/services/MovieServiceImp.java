package com.mundo.disney.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mundo.disney.Entities.Genre;
import com.mundo.disney.Entities.Movie;
import com.mundo.disney.excepciones.Excepcion;
import com.mundo.disney.repositories.GenreRepository;
import com.mundo.disney.repositories.MovieRepository;

@Service
public class MovieServiceImp implements MovieService {

	@Autowired
	MovieRepository peliculaRepo;
	@Autowired
	private Validator validator; 
	@Autowired
	GenreRepository generoRepo;
	
	@Override
	public List<Movie> getAll() {
		
		return peliculaRepo.findAll();
	}

	@Override
	public List<Movie> getMovieByName(String nombre) {
		
		return peliculaRepo.findByTituloLike(nombre);
	}

	@Override
	public List<Movie> getMovieByGenre(Long idGenre) {
		Optional<Genre> gen = generoRepo.findById(idGenre);
		Genre genero = gen.get();
		return peliculaRepo.findByGenero(genero);
	}

	@Override
	public void update(Movie pelicula) throws Excepcion {
		
		Set<ConstraintViolation<Movie>> cv = validator.validate(pelicula);
		if(cv.size()>0)
		{
			String err="";
			for (ConstraintViolation<Movie> constraintViolation : cv) {
				err+=constraintViolation.getPropertyPath()+": "+constraintViolation.getMessage()+"\n";
			}
			throw new Excepcion(err,400);
		}
		else if(!peliculaRepo.findById(pelicula.getId()).isPresent())
		{
			throw new Excepcion("La pelicula que desea guardar no se encuentra en la base de datos.",400);
		}
		else
			peliculaRepo.update(pelicula.getId(), pelicula.getTitulo(), pelicula.getFechaDeCreacion(), pelicula.getCalificacion(), pelicula.getGenero().getId());

	}

	@Override
	public void insert(Movie pelicula) throws Excepcion {
		
		Set<ConstraintViolation<Movie>> cv = validator.validate(pelicula);
		if(cv.size()>0)
		{
			String err="";
			for (ConstraintViolation<Movie> constraintViolation : cv) {
				err+=constraintViolation.getPropertyPath()+": "+constraintViolation.getMessage()+"\n";
			}
			throw new Excepcion(err,400);
		}
		else if(peliculaRepo.findByTituloLike(pelicula.getTitulo()).contains(pelicula))
		{
			throw new Excepcion("La pelicula que desea guardar ya se encuentra en la base de datos.",400);
		}
		else
			peliculaRepo.save(pelicula);

	}

	@Override
	public void delete(Long id) throws Excepcion {
		
		if(!peliculaRepo.findById(id).isPresent())
		{
			throw new Excepcion("No existe una pelicula con el id ingresado",400);
		}
		else
			peliculaRepo.deleteById(id);

	}

}
