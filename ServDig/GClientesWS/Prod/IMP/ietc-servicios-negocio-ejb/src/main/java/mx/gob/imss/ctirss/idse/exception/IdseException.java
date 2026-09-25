package mx.gob.imss.ctirss.idse.exception;


@SuppressWarnings("serial")
public class IdseException extends Exception {
	
	private String situacion;
	private int codigo;
	
	public IdseException(int pCodigo, String pSituacion){
		this.situacion=pSituacion;
		this.codigo=pCodigo;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	
	
}
