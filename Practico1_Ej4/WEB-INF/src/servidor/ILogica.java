package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILogica extends Remote 
{
	void IngresarMensaje(String msj) throws RemoteException;
	void ListarMensajes() throws RemoteException;
}
