package logicaPersistencia;

import java.sql.Connection;


public abstract interface IConexion
{
	public Connection GetConnection();
}
