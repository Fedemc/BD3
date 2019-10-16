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
	
	// Verificar q al menos exista una temporada
	public VOTempMaxPart TempMasParticipantes() throws RemoteException, PersistenciaException
	{
		int max = 0;
		int nroTempMax = 0;
		int cantParts = 0;
		boolean error = false;
		VOTempMaxPart resu = new VOTempMaxPart(1,1,1,1);
		// Traerme todas las temporadas
		// Ver cual tiene mas participantes
		// Devolver nro Temp y ejecutar TempConNroTemp
		List<VOTemporada> listaTemporadas = new ArrayList<VOTemporada>();
		try
		{
			listaTemporadas = abd.ListarTemporadas(con);
			if(listaTemporadas.isEmpty())
			{
				error=true;
			}
			else
			{
				for(VOTemporada t : listaTemporadas)
				{
					int nroTemp = t.getNroTemp();
					int cantParticipantes = abd.CantParticipantesTemp(con, nroTemp);
					if(cantParticipantes > max)
					{
						max = cantParticipantes;
						nroTempMax = nroTemp;
						cantParts = cantParticipantes;
					}
				}
				
				// Me traigo el VO de la temporada con mas participantes
				VOTemporada voT = abd.TempConNroTemp(con, nroTempMax);
				resu = new VOTempMaxPart(voT.getAnio(), voT.getNroTemp(), voT.getCantCapitulos(), cantParts);
			}
		}
		catch(SQLException sqlEx)
		{
			String msj = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(msj);
		}
		
		if(error)
		{	
			String msj = "ERROR: No existen Temporadas registradas en el sistema.";
			throw new PersistenciaException(msj);
		}
		
		return resu;
	}
	
	public void RegistrarVictoria(int nroTemp, int nroPart) throws RemoteException, PersistenciaException
	{
		boolean errorVOT=false, errorVODQ=false;
		
		try
		{
			VOTemporada voT = abd.TempConNroTemp(con, nroTemp);
			if(voT.equals(null))
			{
				errorVOT = true;
			}
			VODragQueen voDQ = abd.DragQueenConNroPart(con, nroPart);
			if(voDQ.equals(null))
			{
				errorVODQ = true;
			}
			
			if(errorVOT)
			{
				String msj = "ERROR: No existe una Temporada con el nroTemp(" + nroTemp + ") en el sistema";
				throw new PersistenciaException(msj);
			}
			
			if(errorVOT)
			{
				String msj = "ERROR: No existe una Temporada con el nroTemp(" + nroTemp + ") en el sistema";
				throw new PersistenciaException(msj);
			}
			
			abd.RegistrarVictoria(con, nroTemp, nroPart);
		}
		catch(SQLException sqlEx)
		{
			String msj = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(msj);
		}
	}
	
	public VODragQueenVictorias ObtenerGanadora(int nroTemp) throws RemoteException, PersistenciaException
	{
		VODragQueenVictorias resu = new VODragQueenVictorias("",1,1,1);
		boolean errorVOT = false, errorNoHayDQs = false;
		try
		{
			VOTemporada voT = abd.TempConNroTemp(con, nroTemp);
			if(voT.equals(null))
			{
				errorVOT = true;
			}
			
			int cantParticipantes = 0;
			cantParticipantes = abd.CantParticipantesTemp(con, nroTemp);
			if(cantParticipantes == 0)
			{
				errorNoHayDQs = true;
			}
			
			if(errorVOT)
			{
				String msj = "ERROR: No existe una Temporada con el nroTemp(" + nroTemp + ") en el sistema.";
				throw new PersistenciaException(msj);
			}
			
			if(errorVOT)
			{
				String msj = "ERROR: La Temporada con el nroTemp(" + nroTemp + ") no tiene participantes registrados.";
				throw new PersistenciaException(msj);
			}
			
			resu = abd.NroPartDragQueenConMasVictorias(con, nroTemp);
			
		}
		catch(SQLException sqlEx)
		{
			String msj = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(msj);
		}
		
		return resu;
	}
	
}
