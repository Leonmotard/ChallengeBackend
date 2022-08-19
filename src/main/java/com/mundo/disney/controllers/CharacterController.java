package com.mundo.disney.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.mundo.disney.services.CharacterService;
import com.mundo.disney.config.SwaggerConfig;

import io.swagger.annotations.Api;

import com.mundo.disney.dto.CharacterDTO;
import com.mundo.disney.excepciones.Excepcion;
import com.mundo.disney.Entities.Character;

@RestController
@RequestMapping("/characters")
@Validated
@Api(tags = { SwaggerConfig.PERSONAJES})
public class CharacterController {

	@Autowired
	CharacterService personajeServicio;

	/**
	 * Inserta un nuevo personaje en la base de datos curl --location --request POST
	 * 'http://localhost:8081/characters' --header 'Accept: application/json'
	 * --header 'Content-Type: application/json' --data-raw '{ "nombre": "Pedro",
	 * "edad": 20, "peso": 65, "historia": "Este personaje nace de una adaptación de
	 * la obra..." }'
	 * 
	 * @param p Character a insertar
	 * @return Character insertado o error en otro caso
	 * @throws Exception
	 */
	@PostMapping
	public ResponseEntity<Object> guardar(@Valid @RequestBody CharacterDTO form, BindingResult result)
			throws Exception {

		if (result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null);
		}

		Character p = form.toPojo();

		personajeServicio.insert(p);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();
		return ResponseEntity.created(location).build();

	}

	/**
	 * Modifica un personaje existente en la base de datos: curl --location
	 * --request PUT 'http://localhost:8081/characters/32' --header 'Accept:
	 * application/json' --header 'Content-Type: application/json' --data-raw '{
	 * "nombre": "Pedro", "edad": 20, "peso": 65, "historia": "Este personaje nace
	 * de una adaptación de la obra..." }'
	 * 
	 * @param p Character a modificar
	 * @return Character Editado o error en otro caso
	 * @throws Excepcion
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> actualizar(@RequestBody CharacterDTO form, @PathVariable long id) throws Excepcion {

		Character p = form.toPojo();
		p.setId(id); // El dni es el identificador, con lo cual es el único dato que no permito
						// modificar
		personajeServicio.update(p);
		return ResponseEntity.ok(buildResponse(p));
	}

	/**
	 * Permite filtrar personajes curl --location --request GET
	 * Permite filtras de personajes por su nombre.
	 * 'http://localhost:8081//characters?name=nombre'
	 * Permite filtras de personajes por su peso.
	 * 'http://localhost:8081/characters?weight=peso'
	 * Permite filtras de personajes por su edad
	 * 'http://localhost:8081/characters?age=edad'
	 * 
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<CharacterDTO> filtrarPersonajes(
			@RequestParam(name = "name", required = false) @javax.validation.constraints.Size(min = 1, max = 20) String nombre,
			@RequestParam(name = "age", required = false) @Min(1) Integer edad,
			@RequestParam(name = "weight", required = false) Double peso)
			throws Excepcion {
		List<Character> personajes = null;
		if (nombre != null) {
			personajes = personajeServicio.getCharacterByName(nombre);
		} else if (edad != null) {
			personajes = personajeServicio.getCharacterByEdad(edad);
		} else if (peso != null) {
			personajes = personajeServicio.getCharacterByPeso(peso);
		}
		else
			personajes = personajeServicio.getAll();
		List<CharacterDTO> dtos = new ArrayList<CharacterDTO>();
		for (Character p : personajes) {
			dtos.add(buildResponse(p));
		}

		return dtos;
	}

	/**
	 * Permite filtrar personajes curl --location --request GET
	 * 'http://localhost:8081/characters/id' Permite filtrar personajes por su
	 * id.
	 */
	@GetMapping(path = {"/{id}"}, produces = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CharacterDTO> getCharacterById( @PathVariable Long id) throws Exception{
		Optional<Character> personaje = personajeServicio.getCharacterById(id);
		
		if(personaje != null) {
			Character respuesta = personaje.get();
			return new ResponseEntity<CharacterDTO>(buildResponse(respuesta), HttpStatus.OK);
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	/**
		 * Permite filtrar personajes curl --location --request GET
		 * 'http://localhost:8081/characters?movies=idMovie' Permite filtrar personajes
		 * por la serie o pelicula en la que participan.
		 *//*
			 * @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) public
			 * List<CharacterDTO> filtrarPorPelicula(
			 * 
			 * @RequestParam(name = "movies", required =
			 * false) @javax.validation.constraints.NotNull() Long idMovie) throws Excepcion
			 * { List<Character> personajes =
			 * personajeServicio.getCharacterByMovie(idMovie); List<CharacterDTO> dtos = new
			 * ArrayList<CharacterDTO>(); for (Character p : personajes) {
			 * dtos.add(buildResponse(p)); }
			 * 
			 * return dtos; }
			 */

	/**
	 * Borra el personaje con el id indicado curl --location --request DELETE
	 * 'http://localhost:8081/characters/id'
	 * 
	 * @param id id del personaje a borrar
	 * @return ok en caso de borrar exitosamente la persona, error en otro caso
	 * @throws Excepcion
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) throws Excepcion {

		personajeServicio.delete(id);

		return ResponseEntity.ok().build();

	}

	/**
	 * Métdo auxiliar que toma los datos del pojo y construye el objeto a devolver
	 * en la response, con su hipervinculos
	 * 
	 * @param pojo
	 * @return
	 * @throws Excepcion
	 */
	private CharacterDTO buildResponse(Character pojo) throws Excepcion {
		try {
			CharacterDTO dto = new CharacterDTO(pojo);
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
