package persistencia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import logica.Persona;
import logica.excepciones.PersistenciaException;


public class DAOPersonas 
{
	private EntityManager manager;
	
	public DAOPersonas()
	{
		EntityManagerFactory factory = null;
		manager = null;
		factory = Persistence.createEntityManagerFactory("EntityPractico6");
		manager = factory.createEntityManager();
	}
	
	public boolean member(int ced) throws PersistenciaException
	{
		boolean existe=false;
		try
		{
			existe = manager.find(Persona.class, ced) != null;
		}
		catch(Exception e)
		{
			throw new PersistenciaException(e.getMessage());
		}
		return existe;
	}
	
	public Persona find(int ced) throws PersistenciaException
	{
		Persona per = null;
		try
		{
			 per = (Persona) manager.find(logica.Persona.class, ced);
		}
		
		catch(Exception e)
		{
			throw new PersistenciaException(e.getMessage());
		}
		
		return per;
	}
	
	public void insert(Persona per) throws PersistenciaException
	{
		try
		{
			manager.persist(per);
		}
		catch(Exception e)
		{
			throw new PersistenciaException(e.getMessage());
		}
	}
	
	public List<Persona> listarMayores(int edad) throws PersistenciaException
	{
		List<Persona> lista;
		try
		{
			Query query = manager.createNamedQuery
				("Persona.personasMayorAEdad", logica.Persona.class);
				//query.setParameter ("edad", edad);
				lista = query.getResultList();
		}
		catch(Exception e)
		{
			throw new PersistenciaException(e.getMessage());
		}		
		
		return lista;
	}
}
