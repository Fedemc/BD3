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

public class PruebaAccesoBD
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

			/* 3. creo un PreparedStatement para insertar una persona en base de datos */
			String insert = "INSERT INTO Personas VALUES (?)";
			PreparedStatement pstmt = con.prepareStatement(insert);
			pstmt.setString(1, "Lorenzo Thomas");

			/* 4. ejecuto la sentencia de insercion y cierro el PreparedStatement */
			int cant = pstmt.executeUpdate();
			pstmt.close();
			System.out.print("Resultado de " + insert + ": ");
			System.out.println(cant + " filas afectadas");

			/* 5. creo un Statement para listar todas las personas de la base de datos */
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM Personas";

			/* 6. ejecuto la consulta, listo las personas y cierro el ResultSet y el Statement */
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Resultado de " + query);
			while (rs.next())
			{
				System.out.println("Nombre = " + rs.getString("nombre").trim());
			}
			rs.close();
			stmt.close();

			/* 7. por ultimo, cierro la conexion con la base de datos */
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
