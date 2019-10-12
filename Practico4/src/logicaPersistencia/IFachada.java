package logicaPersistencia;

import java.rmi.Remote;
import java.rmi.RemoteException;

import logicaPersistencia.valueObjects.VOTemporada;
import logicaPersistencia.excepciones.*;


public interface IFachada extends Remote
{
	
	void NuevaTemporada(VOTemporada voT) throws RemoteException, PersistenciaException;

}
