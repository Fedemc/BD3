package logicaPersistencia.accesoBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.valueObjects.*;

public class AccesoBD
{
	public void NuevaTemporada(Connection con, int nroTemp, int anio, int cantCapitulos) throws SQLException 
	{
		String query = Consultas.NuevaTemporada();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		pstmt.setInt(2, anio);
		pstmt.setInt(3, cantCapitulos);
		pstmt.executeUpdate();
		
		pstmt.close();
	}
	
	public void InscribirDragQueen(Connection con, String nombre, int nroTemp) throws SQLException
	{
		int nroPart = 0;
		String cantParticipantesQuery = Consultas.CantParticipantesTemp();
		PreparedStatement pstmt = con.prepareStatement(cantParticipantesQuery);
		pstmt.setInt(1, nroTemp);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
		{
			nroPart = rs.getInt("cantParticipantes");
		}
		String query = Consultas.InscribirDragQueen();
		pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroPart);
		pstmt.setString(2, nombre);
		pstmt.setInt(3, nroTemp);
		pstmt.executeUpdate();
		
		pstmt.close();
	}
	
	public List<VOTemporada> ListarTemporadas(Connection con) throws SQLException
	{
		List<VOTemporada> resu = new ArrayList<VOTemporada>();
		String query = Consultas.ListarTemporadas();
		Statement stmt=con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			VOTemporada voTemp = new VOTemporada(rs.getInt("nroTemp"), rs.getInt("anio"), rs.getInt("cantCapitulos"));
			resu.add(voTemp);
		}
		
		rs.close();
		stmt.close();
		return resu;
	}
	
	public List<VODragQueenVictorias> ListarDragQueens(Connection con) throws SQLException
	{
		List<VODragQueenVictorias> resu = new ArrayList<VODragQueenVictorias>();
		String query = Consultas.ListarDragQueens();
		Statement stmt=con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			VODragQueenVictorias voDQ = new VODragQueenVictorias(rs.getString("nombre"), rs.getInt("nroTemp"), rs.getInt("nroPart"), rs.getInt("cantVictorias"));
			resu.add(voDQ);
		}
		
		rs.close();
		stmt.close();
		return resu;
	}
	
	/* Verificar que exista el nroTemp en la tabla antes de ejecutar la consulta */
	public VOTemporada TempConNroTemp(Connection con, int nroTemp) throws SQLException
	{
		VOTemporada resu = new VOTemporada(1,1,1);
		String query = Consultas.TempConNroTemp();
		PreparedStatement pstmt=con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next())
		{
			resu.setNroTemp(rs.getInt("nroTemp"));
			resu.setAnio(rs.getInt("anio"));
			resu.setCantCapitulos(rs.getInt("cantCapitulos"));
		}
		
		rs.close();
		pstmt.close();
		
		return resu;
	}
	
	
	public int CantParticipantesTemp(Connection con, int nroTemp) throws SQLException
	{
		int resu=0;
		String query = Consultas.CantParticipantesTemp();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
		{
			resu=rs.getInt("cantParticipantes");
		}
		
		rs.close();
		pstmt.close();
		
		return resu;
	}
	
	public void RegistrarVictoria(Connection con, int nroTemp, int nroPart) throws SQLException
	{
		String query = Consultas.RegistrarVictoria();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroPart);
		pstmt.setInt(2, nroTemp);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public int NroPartDragQueenConMasVictorias(Connection con, int nroTemp) throws SQLException
	{
		int nroPart = 0;
		int mayor = 0;
		String query = Consultas.NroPartDragQueenConMasVictorias();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int cantVictorias = rs.getInt("cantVictorias");
			if(cantVictorias >= mayor)
			{
				mayor = cantVictorias;
				nroPart = rs.getInt("nroPart");
			}
		}
		
		rs.close();
		pstmt.close();
		return nroPart;
	}
	
	public VODragQueen DragQueenConNroPart(Connection con, int nroPart) throws SQLException
	{
		VODragQueen resu = new VODragQueen("", 1);
		String query = Consultas.DragQueenConNroPart();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroPart);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
		{
			resu.setNombre(rs.getString("nombre"));
			resu.setNroTemp(rs.getInt("nroTemp"));
		}
		
		return resu;
	}	
	
}
