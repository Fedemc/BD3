package accesoBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class AccesoBD 
{
	public List<Examen> ListarExamenes(Connection con) throws SQLException
	{
		List<Examen> listaExamenes = new ArrayList<Examen>();
		
		//Traerse lista de examenes, a cada resultado insertarlo en la lista
		try
		{
			Statement stmt=con.createStatement();
			ResultSet rst=stmt.executeQuery(Consultas.ListarExamenes());
			while(rst.next())
			{
				// Codigo, materia y período
				Examen exam = new Examen();
				exam.SetCodigo(rst.getString(1));
				exam.SetMateria(rst.getString(2));
				exam.SetPeriodo(rst.getString(3));
				listaExamenes.add(exam);
			}
			
			rst.close();
			stmt.close();
			con.commit();
		}
		catch(SQLException sqlExc)
		{
			System.out.println(sqlExc.toString());
		}
		
		return listaExamenes;
	}
	
	public void IngresarResultado(Connection con, Resultado resu) throws SQLException
	{
		PreparedStatement pstmt=con.prepareStatement(Consultas.InsertarResultado());
		//cedula int, codigo string, calificacion int
		pstmt.setInt(1, resu.GetCedula());
		pstmt.setString(2, resu.GetCodigo());
		pstmt.setInt(3, resu.GetCalificacion());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		con.commit();
	}
	
	public void CerrarConexion(Connection con) throws SQLException 
	{
		con.close();
	}
}
