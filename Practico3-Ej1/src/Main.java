import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;
import presentacion.VentanaResultados;

public class Main 
{
	public static void main (String[] args)
	{
		String error= new String();
		String driver=null;
		String url=null;
		String usuario=null;
		String password=null;
		Connection con=null;
		
		try
		{
			/* Obtengo los datos de conexión desde el archivo de configuración */
			Properties p=new Properties();
			String nomArch="config/config.properties";
			p.load(new FileInputStream(nomArch));
			driver=p.getProperty("driver");
			url = p.getProperty("url");
			usuario=p.getProperty("user");
			password=p.getProperty("password");

			Class.forName(driver);
		}
		
		catch (ClassNotFoundException e)
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
		
		try
		{
			con = DriverManager.getConnection(url, usuario, password);
			
			DatabaseMetaData dbmtd=con.getMetaData();
			ResultSet rstDB = dbmtd.getSchemas(null, "escuelapractico3");
			if(rstDB.next())
			{
				con.setAutoCommit(false);
				con.setTransactionIsolation(con.TRANSACTION_SERIALIZABLE);
				
				String consultaCrearBD="CREATE DATABASE escuelapractico3;";
				String consultaCrearTablaExamenes = "CREATE TABLE examenes("
						+ "codigo VARCHAR(45), materia VARCHAR(45), periodo VARCHAR(45),"
						+ "PRIMARY KEY (codigo))";
				String consultaCrearTablaResultados = "CREATE TABLE resultados("
						+ "cedula INT, codigo VARCHAR(45), calificacion INT, "
						+ "PRIMARY KEY (cedula), FOREIGN KEY (codigo) REFERENCES Examenes(codigo)"
						+ ");";
				
				System.out.print("Creando BD y tablas.\n");
				Statement stmt=con.createStatement();
				stmt.executeUpdate(consultaCrearBD);
				System.out.println("DB creada.");
				stmt.executeQuery("Use EscuelaPractico3;");
				stmt.executeUpdate(consultaCrearTablaExamenes);
				System.out.println("Tabla examenes creada.");
				stmt.executeUpdate(consultaCrearTablaResultados);
				System.out.println("Tabla resultados creada.");
				
				String consultaCrearDatosExamenes = "INSERT INTO Examenes"
						+ " values ('MD2012Dic','Matemática discreta','Diciembre 2012'), "
						+ "('P12012Dic','Programación','Diciembre 2012'), "
						+ "('BD2012Dic','Bases de datos','Diciembre 2012'), "
						+ "('MD2013Feb','Matemática discreta','Febrero 2013');";
				stmt.executeUpdate(consultaCrearDatosExamenes);
				stmt.close();
				con.commit();
			}
			else
			{
				System.out.println("Ya existe la DB.");
				String usarBD="Use escuelapractico3;";
				Statement stmt=con.createStatement();
				stmt.executeQuery(usarBD);
				stmt.close();
			}
			
			con.setAutoCommit(false);
			VentanaResultados v = new VentanaResultados(con);
			v.setVisible(true);
		}
		catch (SQLException e)
		{
			try
			{
				con.rollback();
			}
			catch (SQLException ex)
			{
				error=ex.toString();
			}
			
			error= e.toString();
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (SQLException ex)
			{
				error=ex.toString();
			}
		}
	}
}
