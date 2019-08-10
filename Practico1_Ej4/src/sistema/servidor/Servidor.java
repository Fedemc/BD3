package sistema.servidor;

import java.util.List;
import java.util.Properties;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.net.MalformedURLException;


public class Servidor 
{
	public static void main(String[] args)
	{
		List listaMensajes;
		
		try
		{
			//Leo datos del server
			Properties p = new Properties();
			String nomArch = "config/config-properties";
			p.load(new FileInputStream(nomArch));
			String ip=p.getProperty("ipServidor");
			String puerto=p.getProperty("puertoServidor");
			int port=Integer.parseInt(puerto);
			
			//Pongo a correr el rmiregistry
			LocateRegistry.createRegistry(port);
			
			//publico el objeto remoto en dicha ip y puerto
			
		}
		catch(RemoteException rEx)
		{
			rEx.printStackTrace();
		}
		catch(MalformedURLException mEx)
		{
			mEx.printStackTrace();
		}
		catch(FileNotFoundException fEx)
		{
			fEx.printStackTrace();
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
	}	
}
