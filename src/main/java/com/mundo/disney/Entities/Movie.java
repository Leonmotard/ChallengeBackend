package com.mundo.disney.Entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull(message="Debe ingresar el título de la película.")
	private String titulo;
	
	private LocalDate fechaDeCreacion;
	
	@Min(1)
	@Max(5)
	private int calificacion;
	

	@ManyToMany(targetEntity = Character.class)
	private List<Character> personajesAsociados;
	
	@ManyToOne
	private Genre genero;
	
	
	public Movie() {
		super();
	}

	public Movie(@NotNull String titulo, LocalDate fechaDeCreacion, @Min(1) @Max(5) int calificacion,
			 List<Character> personajes) {
		super();
		this.titulo = titulo;
		this.fechaDeCreacion = fechaDeCreacion;
		this.calificacion = calificacion;
		this.personajesAsociados = personajes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Character> getPersonajes() {
		return personajesAsociados;
	}

	public void setPersonajes(List<Character> personajes) {
		this.personajesAsociados = personajes;
	}

	public Genre getGenero() {
		return genero;
	}

	public void setGenero(Genre genero) {
		this.genero = genero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaDeCreacion == null) ? 0 : fechaDeCreacion.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (fechaDeCreacion == null) {
			if (other.fechaDeCreacion != null)
				return false;
		} else if (!fechaDeCreacion.equals(other.fechaDeCreacion))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equalsIgnoreCase(other.titulo))
			return false;
		return true;
	}


	
	
	
}
