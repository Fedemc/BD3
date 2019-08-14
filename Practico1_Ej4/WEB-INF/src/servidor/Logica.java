package servidor;

import java.util.List;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Logica extends UnicastRemoteObject implements ILogica
{
	private List<String> listaMensajes;
	private static Logica instanciaLogica;
	
	private Logica() throws RemoteException
	{
		
	}
	
	// singleton de la logica
	public static Logica getInstancia()
	{
		if(instanciaLogica == null)
		{
			try
			{
				instanciaLogica = new Logica();
			}
			catch(RemoteException rEx)
			{
				rEx.printStackTrace();
			}
		}
		
		return instanciaLogica;	
	}
	
	
	public void IngresarMensaje(String msj) throws RemoteException
	{
		listaMensajes.add(msj);
	}
	
	public void ListarMensajes() throws RemoteException
	{
		for(String mensaje : listaMensajes)
		{
			System.out.println(mensaje);
		}
	}
}
