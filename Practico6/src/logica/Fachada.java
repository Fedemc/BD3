package logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import logica.excepciones.LogicaException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOPersona;
import persistencia.DAOPersonas;

public class Fachada 
{
	private DAOPersonas daop;
	
	
	public Fachada()
	{
		daop = new DAOPersonas();
	}
	
	
	public void nuevaPersona(VOPersona vop) throws PersistenciaException, LogicaException
	{
		if(!daop.member(vop.getCedula()))
		{
			Persona per = new Persona(vop.getCedula(), vop.getNombre(), vop.getEdad());
			daop.insert(per);
		}
		else
		{
			throw new LogicaException("Ya existe una persona registrada con esa cedula ("+vop.getCedula()+")");
		}
	}
	
	public VOPersona obtenerPersona(int ced) throws PersistenciaException, LogicaException
	{
		VOPersona retorno = null;
		if(daop.member(ced))
		{
			Persona per = daop.find(ced);
			retorno = new VOPersona(per.getCedula(), per.getNombre(), per.getEdad());
		}
		else
		{
			throw new LogicaException("No existe una persona registrada con esa cedula ("+ced+") en el sistema");
		}
		
		return retorno;		
	}
	
	public List<VOPersona> listarMayores(int edad) throws PersistenciaException
	{
		List<VOPersona> resu = daop.listarMayores(edad);
		
		return resu;
	}

}
