package logicaPersistencia.valueObjects;

public class VOTempMaxPart extends VOTemporada
{
	private int cantParticipantes;
	
	public VOTempMaxPart(int nroT, int anio, int cantC, int cantParticipantes)
	{
		super(nroT, anio, cantC);
		this.cantParticipantes = cantParticipantes;
	}

	public int getCantParticipantes()
	{
		return cantParticipantes;
	}

	public void setCantParticipantes(int cantParticipantes)
	{
		this.cantParticipantes = cantParticipantes;
	}
	
	
}

	
