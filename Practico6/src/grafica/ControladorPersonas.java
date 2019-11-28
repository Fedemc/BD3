package grafica;

import java.util.ArrayList;
import java.util.List;

import grafica.VentanaPersonas;
import logica.Fachada;
import logica.excepciones.LogicaException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOPersona;

public class ControladorPersonas
{
	private VentanaPersonas vent;
	private Fachada fachada;
	
	public ControladorPersonas(VentanaPersonas v)
	{
		vent = v;
		fachada = new Fachada();
	}
	
	public void IngresarPersona(VOPersona vop)
	{
		try
		{
			fachada.nuevaPersona(vop);
			vent.mostrarResultado("Persona ingresada correctamente");
		}
		catch(PersistenciaException | LogicaException e)
		{
			vent.mostrarError(e.getMessage());
		}
	}
	
	public VOPersona ObtenerPersona(int ced)
	{
		VOPersona vop = null;
		try
		{
			vop = fachada.obtenerPersona(ced);
		}
		catch(PersistenciaException | LogicaException e)
		{
			vent.mostrarError(e.getMessage());
		}
		
		return vop;
	}
	
	public List<VOPersona> ListarMayores(int edad)
	{
		List<VOPersona> lista = new ArrayList<VOPersona>();
		try
		{
			lista = fachada.listarMayores(edad);
		}
		catch(PersistenciaException e)
		{
			vent.mostrarError(e.getMessage());
		}
		
		return lista;		
	}
}
