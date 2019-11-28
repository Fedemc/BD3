import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Estudiante")
@NamedQuery(name="Estudiante.verEstudiantes", query="SELECT e FROM Estudiante e")

public class Estudiante implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id	  
	  @Column(name = "cedula")
	  private int cedula;
	  
	  @Column(name = "nombre")
	  private String nombre;
	  
	  public int getCedula()
	  {
		  return cedula;
	  }

	  public String getNombre()
	  {
		  return nombre;
	  }

	  public void setCedula (int cedula)
	  {
		  this.cedula = cedula;
	  }
	  
	  public void setNombre (String nombre)
	  {
		  this.nombre = nombre;
	  }
}
