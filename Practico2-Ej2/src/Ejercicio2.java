import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Ejercicio2 
{
	
	public static void main (String[] args)
	{
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
			
			
			/* Creo la BD */
			String consulta;
			consulta="CREATE DATABASE Escuela";
			Statement stmt=con.createStatement();
			int resultado=stmt.executeUpdate(consulta);
			System.out.println("Filas afectadas: " + resultado);

			/* Empiezo a usar la BD */
			consulta="use Escuela;";
			ResultSet rstst=stmt.executeQuery(consulta);
			rstst.close();
			
			/* Creo la tabla Personas */
			consulta="CREATE TABLE Personas ("
					+ "cedula INT NOT NULL,"
					+ "nombre VARCHAR(45),"
					+ "apellido VARCHAR(45),"
					+ "PRIMARY KEY (cedula))";
			resultado=stmt.executeUpdate(consulta);
			System.out.println("Tabla Personas creada: " + resultado);
			
			/* Creo la tabla Maestras*/
			consulta="CREATE TABLE Maestras ("
					+ "cedula INT NOT NULL,"
					+ "grupo VARCHAR(45),"
					+ "PRIMARY KEY (cedula),"
					+ "FOREIGN KEY (cedula) REFERENCES Personas(cedula)"
					+ ")";
			resultado=stmt.executeUpdate(consulta);
			System.out.println("Tabla Maestras creada: " + resultado);
			
			/* Creo la tabla Alumnos*/
			consulta="CREATE TABLE Alumnos ("
					+ "cedula INT NOT NULL,"
					+ "cedulaMaestra INT NOT NULL,"
					+ "FOREIGN KEY (cedula) REFERENCES Personas(cedula),"
					+ "FOREIGN KEY (cedulaMaestra) REFERENCES Maestras(cedula)"
					+ ")";
			resultado=stmt.executeUpdate(consulta);
			System.out.println("Tabla Alumnos creada: " + resultado);
			
			stmt.close();
			con.close();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException fEx)
		{
			fEx.printStackTrace();
		}
		catch (IOException ioEx)
		{
			ioEx.printStackTrace();
		}
	}
}
