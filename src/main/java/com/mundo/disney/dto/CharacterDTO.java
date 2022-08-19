package com.mundo.disney.dto;

import java.util.List;
import org.springframework.hateoas.RepresentationModel;
import com.mundo.disney.Entities.Character;
import com.mundo.disney.Entities.Movie;

public class CharacterDTO extends RepresentationModel<CharacterDTO> {
	
	private String nombre;
	
	private int edad;
	
	private double peso;
	
	private String historia;
	
	List<Movie> peliculasAsociadas;
	
	public CharacterDTO() {
		super();
	}
	
	public CharacterDTO(Character c) {
		this.edad = c.getEdad();
		this.historia = c.getHistoria();
		this.nombre = c.getNombre();
		this.peliculasAsociadas = c.getPeliculasAsociadas();
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
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

	public void setPeso(double peso) {
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
	 
	
	public Character toPojo() {
		Character c = new Character();
		c.setEdad(this.getEdad());
		c.setHistoria(this.getHistoria());
		c.setNombre(this.getNombre());
		c.setPeliculasAsociadas(this.getPeliculasAsociadas());
		c.setPeso(this.getPeso());
		return c;
	}
}
