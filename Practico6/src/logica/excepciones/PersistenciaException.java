package logica.excepciones;

public class PersistenciaException extends Exception 
{
	private String msj;
	
	public PersistenciaException(String mensaje)
	{
		msj = mensaje;
	}
	
	public String getMessage()
	{
		return msj;
	}
}
