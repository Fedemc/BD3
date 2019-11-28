import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PruebaJPA 
{
	public static void main(String[] args) 
	{
		EntityManagerFactory factory = null;
		EntityManager manager = null;
		try 
		{
			/* Establezco conexión con la BD a través del framework de persistencia */
			/* Le solicito al EntityManagerFactory un EntityManager para manejar la conexión */
			factory = Persistence.createEntityManagerFactory("pruebaJPA");
			manager = factory.createEntityManager();
			
			/* Inicio una transacción */
			manager.getTransaction().begin();
			
			/* Creo tres nuevos estudiantes */
			Estudiante e1 = new Estudiante();
			e1.setCedula(1111111);
			e1.setNombre("Uno");
			
			Estudiante e2 = new Estudiante();
			e2.setCedula(2222222);
			e2.setNombre("Dos");
			
			Estudiante e3 = new Estudiante();
			e3.setCedula(3333333);
			e3.setNombre("Tres");

			/* Almaceno los tres nuevos estudiantes en la BD  */
			manager.persist(e1);
			manager.persist(e2);
			manager.persist(e3);
			
			/* Busco un estudiante que no existe en la BD */
			Estudiante e11 = (Estudiante) manager.find(Estudiante.class, 1234567);
			if (e11 == null)
				System.out.println ("No existe estudiante con cedula 1234567");
			else
				System.out.println ("No deberia ejecutarse este else");
			
			/* Busco al estudiante Dos en la BD y le modifico su nombre */
			Estudiante e22 = (Estudiante) manager.find(Estudiante.class, 2222222);
			if (e22 == null)
				System.out.println ("No deberia ejecutarse este if");
			else
				e22.setNombre("Nuevo Dos");
			
			/* Busco al estudiante Tres en la BD y lo elimino */
			Estudiante e33 = (Estudiante) manager.find(Estudiante.class, 3333333);
			if (e33 == null)
				System.out.println ("No deberia ejecutarse este if");
			else
				manager.remove(e33);
		    
			/* Listo por pantalla los estudiantes finalmente almacenados en la BD */
		    Query query = manager.createNamedQuery("Estudiante.verEstudiantes", Estudiante.class);
			@SuppressWarnings("unchecked")
			List<Estudiante> lista = query.getResultList();
			Iterator <Estudiante> iterator = lista.iterator();
		    while (iterator.hasNext())
		    {
		    	Estudiante est = (Estudiante) iterator.next();
		    	System.out.println (est.getCedula() + " - " + est.getNombre());
		    }
			
			/* Si todo salió bien, cierro la transacción mediante commit */
		    manager.getTransaction().commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			/* Si algo salió mal, cierro la transacción mediante rollback */
			if (manager != null)
		    	manager.getTransaction().rollback();
		}
	}
}
