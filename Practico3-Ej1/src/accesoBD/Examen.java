package accesoBD;

public class Examen 
{
	private String codigo;
	private String materia;
	private String periodo;
	
	
	public void SetCodigo(String nuevoCodigo)
	{
		this.codigo=nuevoCodigo;
	}
	
	public void SetMateria(String nuevaMateria)
	{
		this.materia=nuevaMateria;
	}
	
	public void SetPeriodo(String nuevoPeriodo)
	{
		this.periodo=nuevoPeriodo;
	}
	
	public String GetCodigo()
	{
		return this.codigo;
	}
	
	public String GetMateria()
	{
		return this.materia;
	}
	
	public String GetPeriodo()
	{
		return this.periodo;
	}
		
}
