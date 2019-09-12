package accesoBD;

public class Consultas 
{
	public static String ListarExamenes()
	{
		String consultaListarExamenes = "SELECT * FROM examenes;";
		return consultaListarExamenes;
	}
	
	public static String InsertarResultado()
	{
		String consultaInsertarResultado = "INSERT INTO RESULTADOS VALUES (?,?,?);";
		return consultaInsertarResultado;
	}

}
