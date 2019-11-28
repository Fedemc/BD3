package logica.valueObjects;

public class VOPersona 
{
	private int cedula;
	private String nombre;
	private int edad;
	
	public VOPersona(int ced, String nom, int ed)
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
