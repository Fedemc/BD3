package accesoBD;

public class Resultado 
{
	// cedula, codigo, calificacion
	private int cedula;
	private String codigo;
	private int calificacion;
	
	public void SetCedula(int nuevaCedula)
	{
		this.cedula=nuevaCedula;
	}
	
	public void SetCodigo(String nuevoCodigo)
	{
		this.codigo=nuevoCodigo;
	}
	
	public void SetCalificacion(int nuevaCalificacion)
	{
		this.calificacion=nuevaCalificacion;
	}
	
	public int GetCedula()
	{
		return this.cedula;
	}
	
	public String GetCodigo()
	{
		return this.codigo;
	}
	
	public int GetCalificacion()
	{
		return this.calificacion;
	}
}

