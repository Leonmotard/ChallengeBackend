package com.mundo.disney.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mundo.disney.Entities.Genre;
import com.mundo.disney.dto.GenreDTO;
import com.mundo.disney.services.GenreService;

import io.swagger.annotations.Api;

import com.mundo.disney.config.SwaggerConfig;


@RestController
@RequestMapping("/genre")
@Validated
@Api(tags = { SwaggerConfig.GENERO})
public class GenreController {


	@Autowired
	GenreService generoServicio;
	
	/**
	 * Inserta un nuevo genero en la base de datos
	 * 			curl --location --request POST 'http://localhost:8081/genre' 
	 *			--header 'Accept: application/json' 
	 * 			--header 'Content-Type: application/json' 
	 *			--data-raw '{
	 *			    "nombre": "Suspenso"
	 *			}'
	 * @param genero Genre  a insertar
	 * @return Genre insertada o error en otro caso 
	 * @throws Exception 
	 */
	 
	@PostMapping
	public ResponseEntity<Object> guardar( @Valid @RequestBody GenreDTO form, BindingResult result) throws Exception{
		
		if(result.hasErrors())
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null);
		}
		
		Genre genero = form.toPojo();
		
		generoServicio.insert(genero);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(genero.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
