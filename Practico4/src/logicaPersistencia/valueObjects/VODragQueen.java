package logicaPersistencia.valueObjects;

public class VODragQueen
{
	private String nombre;
	private int nroTemp;
	
	public VODragQueen(String nombre, int nroTemp)
	{
		this.nombre = nombre;
		this.nroTemp = nroTemp;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public int getNroTemp()
	{
		return nroTemp;
	}

	public void setNroTemp(int nroTemp)
	{
		this.nroTemp = nroTemp;
	}
	
	
}
