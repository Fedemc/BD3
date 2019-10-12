package logicaPersistencia;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import logicaPersistencia.accesoBD.AccesoBD;
import logicaPersistencia.excepciones.*;
import logicaPersistencia.valueObjects.*;

public class Fachada extends UnicastRemoteObject implements IFachada
{
	
	private static Fachada instancia;
	private AccesoBD abd;
	private Connection con;
	
	private Fachada() throws RemoteException
	{
		abd = new AccesoBD();
	}
	
	public static Fachada GetInstancia() 
	{
		if(instancia == null)
		{
			try
			{
				instancia = new Fachada();
			}
			catch(RemoteException rEx)
			{
				rEx.printStackTrace();
			}
			
		}
		
		return instancia;
	}
	
	public void SetConnectionFachada(Connection nuevaCon) throws RemoteException
	{
		con=nuevaCon;
	}
	
	/* Registrar una nueva temporada */
	public void NuevaTemporada(VOTemporada voT) throws RemoteException, PersistenciaException
	{
		int nroTemp = voT.getNroTemp();
		boolean existe=false;
		try
		{
			VOTemporada voExiste = abd.TempConNroTemp(con, nroTemp);
			if(voExiste != null)
			{
				existe=true;
			}
		}
		catch(SQLException sqlEx)
		{
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
		if(!existe)
		{
			int anio = voT.getAnio();
			int cantCaps = voT.getCantCapitulos();
			
			try
			{
				abd.NuevaTemporada(con, nroTemp, anio, cantCaps);
			}
			catch(SQLException sqlEx)
			{
				String error = "Error de SQL: " + sqlEx.getMessage();
				throw new PersistenciaException(error);
			}
		}
		else
		{
			throw new PersistenciaException("ERRORO: Ya existe una temporada con ese nro de temporada.");
		}
	}
	
	/* Inscribir una nueva DragQueen */
	public void InscribirDragQueen(VODragQueen voD) throws RemoteException, PersistenciaException
	{
		String nombre = voD.getNombre();
		int nroTemp = voD.getNroTemp();
		try
		{
			abd.InscribirDragQueen(con, nombre, nroTemp);
		}
		catch(SQLException sqlEx)
		{
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
	}
	
	/* Listar todas las temporadas */
	public List<VOTemporada> ListarTemporadas() throws RemoteException, PersistenciaException
	{
		List<VOTemporada> resu = new ArrayList<VOTemporada>();
		
		try
		{
			resu = abd.ListarTemporadas(con);
		}
		catch(SQLException sqlEx)
		{
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		return resu;
	}
	
	/* Listar todas las DragQueens */
	public List<VODragQueenVictorias> ListarDragQueens() throws RemoteException, PersistenciaException
	{
		List<VODragQueenVictorias> resu = new ArrayList<VODragQueenVictorias>();
		try
		{
			resu = abd.ListarDragQueens(con);
		}
		catch(SQLException sqlEx)
		{
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		return resu;
	}
	
	/*
	listarDragQueens(nroTemp:int): List<DragQueenVictorias>
	tempMasParticipantes():VOTempMaxParts
	registrarVictoria(nroTemp: int, nroPart:int):void
	obtenerGanadora(nroTemp:int):VODragQueenVictorias
	*/
	
	
	
	
}
