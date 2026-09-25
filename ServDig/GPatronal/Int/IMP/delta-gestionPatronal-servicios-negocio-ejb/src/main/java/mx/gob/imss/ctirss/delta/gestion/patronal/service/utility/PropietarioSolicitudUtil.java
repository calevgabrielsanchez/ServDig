package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility;

public enum PropietarioSolicitudUtil {
	PERSONA(1),
	REGISTRO_PATRONAL(2);
	private int codigo;
	
	private PropietarioSolicitudUtil(int codigo){
		this.codigo=codigo;
	}

	public int getCodigo() {
		return codigo;
	}
	
}
