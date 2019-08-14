package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import servidor.ILogica;


public class Proceso extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ILogica iLogica;
	
	public void init()
	{
		try
		{
			//Intento conectarme
			String ip=super.getInitParameter("ipServidor");
			//String ip="127.0.0.1";
			String puerto=super.getInitParameter("puertoServidor");
			//String puerto="1099";
			String ruta="//"+ip+":"+puerto+"/fachadaLogica";
			
			//Voy a buscar el objeto remoto
			iLogica = (ILogica) Naming.lookup(ruta);
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
	}
}