package com.mundo.disney.dto;

import java.time.LocalDate;
import java.util.List;



import org.springframework.hateoas.RepresentationModel;

import com.mundo.disney.Entities.Character;
import com.mundo.disney.Entities.Movie;

public class MovieDTO extends RepresentationModel<MovieDTO> {

	private String titulo;
	
	private LocalDate fechaDeCreacion;
	
	private int calificacion;
	
	private Long idGenero;

	private List<Character> personajesAsociados;
	
	
	
	public MovieDTO() {
		super();
	}
	
	public MovieDTO(Movie m) {
		this.calificacion = m.getCalificacion();
		this.fechaDeCreacion = m.getFechaDeCreacion();
		this.personajesAsociados = m.getPersonajes();
		this.titulo = m.getTitulo();
		this.idGenero = m.getGenero().getId();
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(LocalDate fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public List<Character> getPersonajesAsociados() {
		return personajesAsociados;
	}

	public void setPersonajesAsociados(List<Character> personajesAsociados) {
		this.personajesAsociados = personajesAsociados;
	}
	
	public Long getIdGenero() {
		return idGenero;
	}
	
	public void setIdGenero(Long genero) {
		this.idGenero = genero;
	}
	
	public Movie toPojo()
	{
		Movie m = new Movie();
		m.setCalificacion(this.getCalificacion());
		m.setFechaDeCreacion(this.getFechaDeCreacion());
		m.setTitulo(this.getTitulo());
		m.setPersonajes(this.getPersonajesAsociados());
		return m;
	}
	
}
