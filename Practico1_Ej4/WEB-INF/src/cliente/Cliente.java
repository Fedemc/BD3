package cliente;

import servidor.ILogica;
import java.util.Properties;
import java.io.FileInputStream;
import java.rmi.Naming;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Cliente 
{
	private static ILogica interfazLogica;
	
	public static void main(String[] args)
	{
		try
		{
			//Intento conectarme
			Properties p=new Properties();
			String nomArch="config.properties";
			p.load(new FileInputStream(nomArch));
			String ip=p.getProperty("ipServidor");
			String puerto=p.getProperty("puertoServidor");
			String ruta="//"+ip+":"+puerto+"/logica";
			
			//Voy a buscar el objeto remoto
			interfazLogica = (ILogica) Naming.lookup(ruta);
		}
		catch(MalformedURLException mEx)
		{
			mEx.printStackTrace();			
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();			
		}
		catch(NotBoundException nobEx)
		{
			nobEx.printStackTrace();
		}
		
		// Listar mensajes
		try
		{
			interfazLogica.ListarMensajes();
		}
		catch(RemoteException rEx)
		{
			rEx.printStackTrace();
		}
		
	}
}
