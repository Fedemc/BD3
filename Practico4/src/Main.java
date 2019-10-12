import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;

import logicaPersistencia.Fachada;

public class Main 
{
	public static void main (String[] args)
	{
		String error= new String();
		String driver=null;
		String url=null;
		String usuario=null;
		String password=null;
		String db=null;
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
			db=p.getProperty("DB");

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
			ResultSet rstDB = dbmtd.getCatalogs();
			con.setAutoCommit(false);
			con.setTransactionIsolation(con.TRANSACTION_SERIALIZABLE);
			boolean existe=false;
			while(rstDB.next() && !existe)
			{
				String nombreDB = rstDB.getString(1);
				if(nombreDB.equals(db))
				{
					existe=true;
				}
			}
			
			if(!existe)
			{		
				String consultaCrearBD="CREATE DATABASE ?;";
				String consultaCrearTablaTemporadas = "CREATE TABLE Temporadas("
						+ "nroTemp INT, anio INT, cantCapitulos INT,"
						+ "PRIMARY KEY (nroTemp));";
				String consultaCrearTablaDragQueens = "CREATE TABLE DragQueens("
						+ "nroPart INT, nombre VARCHAR(45), cantVictorias INT DEFAULT 0, nroTemp INT, "
						+ "PRIMARY KEY (nroPart,nombre), FOREIGN KEY (nroTemp) REFERENCES Temporadas(nroTemp)"
						+ ");";
				
				System.out.print("Creando BD y tablas.\n");
				PreparedStatement pstmt=con.prepareStatement(consultaCrearBD);
				pstmt.setString(1, db);
				pstmt.executeUpdate(consultaCrearBD);
				System.out.println("DB creada.");
				pstmt=con.prepareStatement("Use ?;");
				pstmt.setString(1, db);
				pstmt.executeQuery();
				Statement stmt=con.createStatement();
				stmt.executeUpdate(consultaCrearTablaTemporadas);
				System.out.println("Tabla Temporadas creada.");
				stmt.executeUpdate(consultaCrearTablaDragQueens);
				System.out.println("Tabla DragQueens creada.");
				
				String consultaCrearDatosExamenes = "INSERT INTO Examenes"
						+ " values ('MD2012Dic','Matemática discreta','Diciembre 2012'), "
						+ "('P12012Dic','Programación','Diciembre 2012'), "
						+ "('BD2012Dic','Bases de datos','Diciembre 2012'), "
						+ "('MD2013Feb','Matemática discreta','Febrero 2013');";
				//stmt.executeUpdate(consultaCrearDatosExamenes);
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
		
		System.out.println("Accediendo a Fachada");
		Fachada fachada = Fachada.GetInstancia();
		try
		{
			fachada.SetConnectionFachada(con);
		}
		catch(RemoteException rEx)
		{
			rEx.printStackTrace();
		}
	}
}
