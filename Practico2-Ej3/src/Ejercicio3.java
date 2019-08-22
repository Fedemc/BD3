import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Ejercicio3 
{	
	public static void main (String[] args)
	{
		System.out.println("Bienvenido al programa de consultas de la escuela.\nIngrese comando: ");
		
		InputStreamReader is = new InputStreamReader (System.in);
		BufferedReader br = new BufferedReader (is);
		String consulta = new String();
		String error= new String();

		try 
		{
			consulta=br.readLine();
		}
		catch (IOException ioEx)
		{
			System.out.println("Ocurrió un error: " + ioEx.toString());
		}
		
		while(consulta!="exit")
		{
			error="";
			try
			{
				/* Obtengo los datos de conexión desde el archivo de configuración */
				Properties p=new Properties();
				String nomArch="config/config.properties";
				p.load(new FileInputStream(nomArch));
				String driver=p.getProperty("driver");
				String url = p.getProperty("url");
				String usuario=p.getProperty("user");
				String password=p.getProperty("password");
				
				/* 1. cargo dinamicamente el driver de MySQL */
				Class.forName(driver);

				/* 2. una vez cargado el driver, me conecto con la base de datos */
				Connection con = DriverManager.getConnection(url, usuario, password);
					
				Statement stmt=con.createStatement();
				stmt.executeUpdate(consulta);
				
				stmt.close();
				con.close();
				consulta=br.readLine();
			}
			catch (ClassNotFoundException e)
			{
				error= e.toString();
			}
			catch (SQLException e)
			{
				error= e.toString();
			}
			catch (FileNotFoundException fEx)
			{
				error= fEx.toString();
			}
			catch (IOException ioEx)
			{
				error= ioEx.toString();
			}

			if(error!="")
			{
				System.out.println("Ocurrió un error: " + error);
			}
		}
		System.out.println("Hasta la próxima!");
		System.exit(0);
	}
}
