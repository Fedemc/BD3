package logica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.NamedQueries;

import java.io.Serializable;

@Entity
@Table(name = "Persona")
@NamedQuery(name = "Persona.personasMayorAEdad", query = "SELECT p FROM Persona p WHERE p.edad > :edad")

public class Persona implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cedula")
	private int cedula;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "edad")
	private int edad;
	
	public Persona()
	{
		
	}
	
	public Persona(int ced, String nom, int ed)
	{
		cedula = ced;
		nombre = nom;
		edad = ed;
	}

	public int getCedula() 
	{
		return cedula;
	}

	public void setCedula(int cedula) 
	{
		this.cedula = cedula;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public int getEdad() 
	{
		return edad;
	}

	public void setEdad(int edad) 
	{
		this.edad = edad;
	}
	
	

}
