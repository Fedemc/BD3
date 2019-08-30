import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Ejercicio5 
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
			
			System.out.println("Ingrese cedula de maestra a borrar: ");
			
			InputStreamReader is = new InputStreamReader (System.in);
			BufferedReader br = new BufferedReader (is);
			String cedula=new String();
			try 
			{
				cedula=br.readLine();
			}
			catch (IOException ioEx)
			{
				System.out.println("Ocurrió un error: " + ioEx.toString());
			}
			
			String sp="{call BorrarMaestra(?)}";
			CallableStatement cstmt = con.prepareCall(sp);
			cstmt.setInt(1, Integer.parseInt(cedula));
			boolean isResultSet = cstmt.execute();
			
			if(isResultSet)
			{
				ResultSet rs1 = cstmt.getResultSet();
				while(rs1.next())
				{
					int ced = rs1.getInt("cedula");
					System.out.println("Primer select cedula de alumno a borrar: " + ced);
				}
				rs1.close();
				isResultSet = cstmt.getMoreResults();
				if(isResultSet)
				{
					ResultSet rs2 = cstmt.getResultSet();
					while(rs2.next())
					{
						String grupo = rs2.getString("grupo");
						System.out.println("Segundo select grupo de la maestra a borrar: " + grupo);
					}
					rs2.close();
					isResultSet = cstmt.getMoreResults();
					if(isResultSet)
					{
						ResultSet rs3=cstmt.getResultSet();
						while(rs3.next())
						{
							String nombre = rs3.getString("nombre");
							String apellido = rs3.getString("apellido");
							System.out.println("Tercer select, nombre y apellido de la maestra a borrar: " + nombre + " " + apellido);
						}
						rs3.close();
					}
				}
			}
			cstmt.close();
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

		if(error!="")
		{
			System.out.println("Ocurrió un error: " + error);
		}
		
	}
}
