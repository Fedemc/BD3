package logicaPersistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import logicaPersistencia.Conexion;
import logicaPersistencia.IConexion;
import logicaPersistencia.excepciones.PersistenciaException;


public class PoolConexiones implements IPoolConexiones
{
	private String driver;
	private String url;
	private String user;
	private String pwd;
	private int nivelTransaccionalidad;
	private Conexion[] conexiones;
	private int tamanio;
	private int creadas;
	private int tope;
	
	public PoolConexiones() throws PersistenciaException
	{
		
		
		try
		{
			/* Obtengo los datos de conexión desde el archivo de configuración */
			Properties p=new Properties();
			String nomArch="config/config.properties";
			p.load(new FileInputStream(nomArch));
			driver=p.getProperty("driver");
			url = p.getProperty("url");
			user=p.getProperty("user");
			pwd=p.getProperty("password");
			tamanio=Integer.parseInt(p.getProperty("nroMaxConexiones"));
			creadas=0;
			tope=0;
			conexiones = new Conexion[tamanio];

			Class.forName(driver);
		}
		
		catch (ClassNotFoundException e)
		{
			String error= "Error al iniciar pool de conexiones: " + e.toString();
			throw new PersistenciaException(error);
		}
		catch (FileNotFoundException fEx)
		{
			String error= "Error al iniciar pool de conexiones: " + fEx.toString();
			throw new PersistenciaException(error);
		}
		catch (IOException ioEx)
		{
			String error= "Error al iniciar pool de conexiones: " + ioEx.toString();
			throw new PersistenciaException(error);
		}
	}
	
	
	public IConexion ObtenerConexiones(boolean modifica) throws PersistenciaException
	{
		IConexion conexion=null;
		boolean tengo = false;
		
		// Si ya cree el max de conexiones
		// Si no tengo conexiones en el arreglo
		while (!tengo)
		{
			if(tope == 0)
			{
				if(creadas < tamanio)
				{
					try
					{
						Connection con = DriverManager.getConnection(url,user,pwd);
						conexion = new Conexion(con);
						tengo = true;
					}
					catch(SQLException sqlEx)
					{
						String error= "Error al crear conexion: " + sqlEx.toString();
						throw new PersistenciaException(error);
					}
				}
				else
				{
					try {
						wait();
					} catch (InterruptedException e) {
						String error= "Error al pedir conexion: " + e.toString();
						throw new PersistenciaException(error);
					}
				}		
			}
			else
			{
				conexion=conexiones[tope];
				tope--;
				tengo = true;
			}					
		}
		try
		{
			conexion.GetConnection().setAutoCommit(false);
			conexion.GetConnection().setTransactionIsolation(nivelTransaccionalidad);
		}
		catch(SQLException sqlEx)
		{
			String error= "Error al setear autocommit y nivel de transaccionalidad: " + sqlEx.toString();
			throw new PersistenciaException(error);
		}
		
		return conexion;
	}
	
	public void LiberarConexion(IConexion conexion, boolean ok)
	{
		
	}
	
}
