import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Ejercicio4 
{	
	public static void main (String[] args)
	{
	
		String consulta = new String();
		String error= new String();

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
			
			/* con una sola consulta
			consulta="SELECT distinct p.cedula,p.nombre, p.apellido, count(*) as cantAlumnos\r\n" + 
					"FROM personas p, maestras m, alumnos a\r\n" + 
					"WHERE m.cedula=p.cedula AND m.cedula=a.cedulaMaestra\r\n" + 
					"group by m.cedula\r\n" + 
					"having cantAlumnos > ALL\r\n" + 
					"(SELECT distinct count(*)\r\n" + 
					"from maestras m, alumnos a\r\n" + 
					"where m.cedula= a.cedulaMaestra\r\n" + 
					"group by m.cedula\r\n" + 
					");";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(consulta);
			
			int cedula=0;
			String nombre= new String();
			String apellido= new String();
			
			while(rs.next())
			{
				cedula=rs.getInt("cedula");
				nombre=rs.getString("nombre");
				apellido=rs.getString("apellido");
			}
			
			System.out.println("--- Maestra con mas alumnos ---"
					+ "\nCedula: " + cedula
					+ "\nNombre: " + nombre
					+ "\nApellido: " + apellido);
			*/
			
			/* En 3 consultas */
			consulta="select cedula\r\n" + 
					"from maestras;";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(consulta);
			List<Integer> cedulasMaestras = new ArrayList<Integer>();
			int cedula;
			
			// Obtengo las cedulas de las maestras
			while(rs.next())
			{
				cedula=rs.getInt("cedula");
				cedulasMaestras.add(cedula);
			}
			
			rs.close();
			stmt.close();
			
			int cantAlumnos=0;
			int cantAlumnosMax=0;
			int cedMayor=0;
			// Para cada maestra obtengo la cantidad de alumnos y voy registrando la que lleva la mayor cantidad
			for(int ced : cedulasMaestras)
			{
				consulta="select count(*) as cantAlumnos " + 
						"from maestras m, alumnos a " + 
						"where m.cedula=a.cedulaMaestra AND m.cedula=? group by m.cedula;";
				PreparedStatement pstmt=con.prepareStatement(consulta);
				pstmt.setString(1, Integer.toString(ced));
				ResultSet rst=pstmt.executeQuery();
				while(rst.next())
				{
					cantAlumnos = rst.getInt("cantAlumnos");		
				}
				if(cantAlumnos > cantAlumnosMax)
				{
					cantAlumnosMax=cantAlumnos;
					cedMayor=ced;
				}
				rst.close();
				pstmt.close();
			}
			
			// De la maestra con la mayor cant de alumnos me traigo nombre y apellido
			consulta="select *"
					+ " from personas"
					+ " where cedula = ? ;";
			PreparedStatement pstmt=con.prepareStatement(consulta);
			pstmt.setString(1, Integer.toString(cedMayor));
			ResultSet rst=pstmt.executeQuery();
			String nombre=new String();
			String apellido=new String();
			while(rst.next())
			{
				nombre=rst.getString("nombre");
				apellido=rst.getString("apellido");
			}
			
			System.out.println("--- Maestra con mas alumnos ---"
					+ "\nCedula: " + cedMayor
					+ "\nNombre: " + nombre
					+ "\nApellido: " + apellido);
			
			rst.close();			
			pstmt.close();
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
