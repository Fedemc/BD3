import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;

public class Main 
{
	public static void main (String[] args)
	{
		String error= new String();
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
				
			String consultaCrearBD=null;
			
			consultaCrearBD = "CREATE DATABASE escuelapractico3;";
			String consultaCrearTablaExamenes = "CREATE TABLE examenes("
					+ "codigo VARCHAR(45), materia VARCHAR(45), periodo VARCHAR(45),"
					+ "PRIMARY KEY (codigo))";
			String consultaCrearTablaResultados = "CREATE TABLE resultados("
					+ "cedula INT, codigo VARCHAR(45), calificacion INT, "
					+ "PRIMARY KEY (cedula), FOREIGN KEY (codigo) REFERENCES Examenes(codigo)"
					+ ");";
			
			System.out.print("Creando BD y tablas.\n");
			Statement stmt=con.createStatement();
			int resultado=stmt.executeUpdate(consultaCrearBD);
			System.out.println("DB creada: " + resultado);
			stmt.executeQuery("Use EscuelaPractico3;");
			resultado=stmt.executeUpdate(consultaCrearTablaExamenes);
			System.out.println("Tabla examenes creada.");
			resultado=stmt.executeUpdate(consultaCrearTablaResultados);
			System.out.println("Tabla resultados creada.");
			stmt.close();
			con.close();
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

		if(!error.isEmpty())
		{
			System.out.println("Ocurrió un error: " + error);
		}
		
	}
}
