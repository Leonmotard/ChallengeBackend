package com.mundo.disney.Entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Character {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min = 1,max = 100, message = "Ingrese un nombre al personaje.")
	private String nombre;
	
	@NotNull(message = "Debe ingresar la edad del personaje")
	private int edad;
	
	@NotNull(message="Debe ingresar el peso del personaje")
	private Double peso;
	
	private String historia;
	
	@ManyToMany(targetEntity=Movie.class)
	List<Movie> peliculasAsociadas;
	
	
	public Character() {
		super();
	}


	public Character(@NotNull @Size(min = 1, max = 200, message = "Ingrese un nombre al personaje.") String nombre,
			@NotNull(message = "Debe ingresar la edad del personaje") int edad,
			@NotNull(message = "Debe ingresar el peso del personaje") Double peso, String historia,
			List<Movie> peliculasAsociadas) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.peso = peso;
		this.historia = historia;
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


	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}


	public double getPeso() {
		return peso;
	}


	public void setPeso(Double peso) {
		this.peso = peso;
	}


	public String getHistoria() {
		return historia;
	}


	public void setHistoria(String historia) {
		this.historia = historia;
	}


	
	  public List<Movie> getPeliculasAsociadas() { return peliculasAsociadas; }
	  
	  
	  public void setPeliculasAsociadas(List<Movie> peliculasAsociadas) {
	  this.peliculasAsociadas = peliculasAsociadas; }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + edad;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
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
		Character other = (Character) obj;
		if (edad != other.edad)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		return true;
	}
	 
	
	
	
	

}
