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
			String ruta="//"+ip+":"+puerto+"/logica";
			
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
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// Obtengo el mensaje desde la página
		String mensaje=req.getParameter("mensaje");
		
		// Manejo de errores
		String msjError= new String();
		
		RequestDispatcher rd;
		
		// Le tiro el mensaje al server
		try
		{
			iLogica.IngresarMensaje(mensaje);
		}
		catch(RemoteException remEx)
		{
			msjError=remEx.toString();
			req.setAttribute("mensajeError", msjError);
			rd=req.getRequestDispatcher("Error.jsp");
		}
		
	}
}