package com.mundo.disney.dto;

import java.util.Set;



import org.springframework.hateoas.RepresentationModel;

import com.mundo.disney.Entities.Genre;
import com.mundo.disney.Entities.Movie;

public class GenreDTO extends RepresentationModel<GenreDTO> {
	

	private String nombre;
	
	private Set<Movie> peliculasAsociadas;

	public GenreDTO() {
	super();
	}
	
	public GenreDTO(Genre genero) {
		super();
		this.nombre = genero.getNombre();
		this.peliculasAsociadas = genero.getPeliculasAsociadas();
		
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Movie> getPeliculasAsociadas() {
		return peliculasAsociadas;
	}

	public void setPeliculasAsociadas(Set<Movie> peliculasAsociadas) {
		this.peliculasAsociadas = peliculasAsociadas;
	}
	
	public Genre toPojo() {
		Genre g = new Genre();
		g.setNombre(this.getNombre());
		g.setPeliculasAsociadas(this.getPeliculasAsociadas());
		return g;
	}
	
}
