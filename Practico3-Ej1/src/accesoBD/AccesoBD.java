package accesoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;

public class AccesoBD 
{
	public List<Examen> ListarExamenes(Connection con) throws SQLException
	{
		List<Examen> listaExamenes=null;
		
		//Traerse lista de examenes, a cada resultado insertarlo en la lista
		try
		{
			Statement stmt=con.createStatement();
			ResultSet rst=stmt.executeQuery(Consultas.ListarExamenes());
			while(rst.next())
			{
				// Codigo, materia y período
				Examen exam = new Examen();
				exam.SetCodigo(rst.getString(0));
				exam.SetMateria(rst.getString(1));
				exam.SetPeriodo(rst.getString(2));
				listaExamenes.add(exam);
			}
			
			rst.close();
			stmt.close();
		}
		catch(SQLException sqlExc)
		{
			System.out.println(sqlExc.toString());
		}
		
		return listaExamenes;
	}
	
	public void IngresarResultado(Connection con, Resultado resu)
	{
		try
		{
			PreparedStatement pstmt=con.prepareStatement(Consultas.InsertarResultado());
			//cedula int, codigo string, calificacion int
			pstmt.setInt(0, resu.GetCedula());
			pstmt.setString(1, resu.GetCodigo());
			pstmt.setInt(2, resu.GetCalificacion());
			
			pstmt.executeUpdate();
			
			pstmt.close();			
		}
		catch(SQLException sqlExc)
		{
			System.out.println(sqlExc.toString());
		}
	}
}
