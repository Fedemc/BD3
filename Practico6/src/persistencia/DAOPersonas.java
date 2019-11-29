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
import logica.valueObjects.VOPersona;


public class DAOPersonas 
{
	private EntityManagerFactory factory;
	
	public DAOPersonas()
	{
		factory = Persistence.createEntityManagerFactory("EntityPractico6");
	}
	
	public boolean member(int ced) throws PersistenciaException
	{
		boolean existe=false;
		EntityManager manager = null;
		try
		{
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			existe = manager.find(Persona.class, ced) != null;
			if(existe)
			{
				manager.getTransaction().commit();
			}
		}
		catch(Exception e)
		{
			if(manager != null)
			{
				manager.getTransaction().rollback();
			}
			throw new PersistenciaException(e.getMessage());
		}
		return existe;
	}
	
	public Persona find(int ced) throws PersistenciaException
	{
		Persona per = null;
		EntityManager manager = null;
		try
		{
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			per = (Persona) manager.find(logica.Persona.class, ced);
			manager.getTransaction().commit();			
		}
		
		catch(Exception e)
		{
			if(manager != null)
			{
				manager.getTransaction().rollback();
			}
			throw new PersistenciaException(e.getMessage());
		}
		
		return per;
	}
	
	public void insert(Persona per) throws PersistenciaException
	{
		EntityManager manager = null;
		try
		{
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.persist(per);
			manager.getTransaction().commit();
		}
		catch(Exception e)
		{
			if(manager != null)
			{
				manager.getTransaction().rollback();
			}
			throw new PersistenciaException(e.getMessage());
		}
	}
	
	public List<VOPersona> listarMayores(int edad) throws PersistenciaException
	{
		List<VOPersona> resu = new ArrayList<VOPersona>();
		EntityManager manager = null;
		try
		{
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			Query query = manager.createNamedQuery
				("Persona.personasMayorAEdad", logica.Persona.class);
			query.setParameter ("edad", edad);
			List<Persona> lista = query.getResultList();
			Iterator<Persona> iter = lista.iterator();
			while(iter.hasNext())
			{
				Persona per = (Persona) iter.next();
				VOPersona vop = new VOPersona(per.getCedula(), per.getNombre(), per.getEdad());
				resu.add(vop);
			}
			manager.getTransaction().commit();			
		}
		catch(Exception e)
		{
			if(manager != null)
			{
				manager.getTransaction().rollback();
			}
			throw new PersistenciaException(e.getMessage());
		}		
		
		return resu;
	}
}
