package com.mundo.disney.Entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
public class Genre {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min = 1,max = 200, message = "Ingrese un nombre al género.")
	private String nombre;
	
	
	
	@OneToMany
	@JoinColumn(name = "genero_id")
	private Set<Movie> peliculasAsociadas;

	

	public Genre() {
		super();
	}

	public Genre(@NotNull @Size(min = 1, max = 200, message = "Ingrese un nombre al género.") String nombre,
			Set<Movie> peliculasAsociadas) {
		super();
		this.nombre = nombre;
		this.peliculasAsociadas = peliculasAsociadas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Genre other = (Genre) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	
	
	
}
