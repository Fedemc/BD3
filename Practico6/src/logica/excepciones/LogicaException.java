package logica.excepciones;

public class LogicaException extends Exception
{
	private String msj;
	
	public LogicaException(String mensaje)
	{
		msj = mensaje;
	}
	
	public String getMessage()
	{
		return msj;
	}

}
