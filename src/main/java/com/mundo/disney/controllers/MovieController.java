package com.mundo.disney.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mundo.disney.Entities.Movie;
import com.mundo.disney.dto.MovieDTO;
import com.mundo.disney.excepciones.Excepcion;
import com.mundo.disney.services.MovieService;
import com.mundo.disney.config.SwaggerConfig;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/movies")
@Validated
@Api(tags = {SwaggerConfig.PELICULAS})
public class MovieController {

	@Autowired
	MovieService peliculaServicio;

	/**
	 * Permite filtrar peliculas curl --location --request GET
	 * 'http://localhost:8081//movies?name=nombre' Permite filtrar peliculas por su
	 * titulo.
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<MovieDTO> filtrarPorNombre(
			@RequestParam(name = "name", required = false) @javax.validation.constraints.Size(min = 1, max = 20) String nombre,
			@RequestParam(name = "genre", required = false) Long idGenero) throws Excepcion {
		List<Movie> peliculas = null;
		if (nombre != null)
			peliculas = peliculaServicio.getMovieByName(nombre);
		else if (idGenero != null)
			peliculas = peliculaServicio.getMovieByGenre(idGenero);
		else
			peliculas = peliculaServicio.getAll();

		List<MovieDTO> dtos = new ArrayList<MovieDTO>();
		for (Movie p : peliculas) {
			dtos.add(buildResponse(p));
		}

		return dtos;
	}

	/**
	 * Permite filtrar peliculas curl --location --request GET
	 * 'http://localhost:8081//Movies?genre=idGenero' Permite filtrar peliculas por
	 * su genero.
	 *//*
		 * @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) public
		 * List<MovieDTO> filtrarPorGenero(
		 * 
		 * @RequestParam(name = "genre", required = false) Long idGenero) throws
		 * Excepcion { List<Movie> peliculas =
		 * peliculaServicio.getMovieByGenre(idGenero); List<MovieDTO> dtos = new
		 * ArrayList<MovieDTO>(); for (Movie p : peliculas) {
		 * dtos.add(buildResponse(p)); }
		 * 
		 * return dtos; }
		 */

	/**
	 * Inserta una nueva pelicula en la base de datos curl --location --request POST
	 * 'http://localhost:8081/movies' --header 'Accept: application/json' --header
	 * 'Content-Type: application/json' --data-raw '{ "titulo": "El dorado",
	 * "fechaDeCreacion": 2022-03-26, "calificacion": 4 }'
	 * 
	 * @param pelicula Movie a insertar
	 * @return Movie insertado o error en otro caso
	 * @throws Exception
	 */
	@PostMapping
	public ResponseEntity<Object> guardar(@Valid @RequestBody MovieDTO form, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null);
		}

		Movie p = form.toPojo();

		peliculaServicio.insert(p);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();
		return ResponseEntity.created(location).build();

	}

	/**
	 * Modifica un personaje existente en la base de datos: curl --location
	 * --request PUT 'http://localhost:8081/movies/32' --header 'Accept:
	 * application/json' --header 'Content-Type: application/json' --data-raw '{
	 * "titulo": "El dorado", "fechaDeCreacion": 2022-03-26, "calificacion": 4 , "genero": aventura}'
	 * 
	 * @param p Movie a modificar
	 * @return Movie Editado o error en otro caso
	 * @throws Excepcion
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> actualizar(@RequestBody MovieDTO form, @PathVariable long id) throws Excepcion {

		Movie p = form.toPojo();
		p.setId(id); // El id es el identificador, con lo cual es el único dato que no permito
						// modificar
		peliculaServicio.update(p);
		return ResponseEntity.ok(buildResponse(p));
	}

	/**
	 * Métdo auxiliar que toma los datos del pojo y construye el objeto a devolver
	 * en la response, con su hipervinculos
	 * 
	 * @param pojo
	 * @return
	 * @throws Excepcion
	 */
	private MovieDTO buildResponse(Movie pojo) throws Excepcion {
		try {
			MovieDTO dto = new MovieDTO(pojo);
			// Self link
			Link selfLink = WebMvcLinkBuilder.linkTo(CharacterController.class).slash(pojo.getId()).withSelfRel();
			// Method link: Link al servicio que permitirá navegar hacia las peliculas
			// relacionadas al personaje
			/*
			 * Link peliculasLink =
			 * WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CiudadController.class)
			 * .getById(pojo.getCiudad().getId())) .withRel("Pelicula");
			 */
			dto.add(selfLink);
			// dto.add(ciudadLink);
			return dto;
		} catch (Exception e) {
			throw new Excepcion(e.getMessage(), 500);
		}
	}

}
