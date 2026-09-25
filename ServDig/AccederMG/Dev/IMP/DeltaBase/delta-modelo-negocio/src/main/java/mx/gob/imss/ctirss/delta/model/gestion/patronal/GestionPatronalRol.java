package mx.gob.imss.ctirss.delta.model.gestion.patronal;

public enum GestionPatronalRol {
	TRAMITADOR(1),REPRESENTANTE_LEGAL(8), PATRON_SUJETO_OBLIGADO(2);
	
	private GestionPatronalRol(Integer valor){
		this.codigo=valor;
	}
	private Integer codigo;
	
	public Integer getCodigo(){
		return codigo;
	}
}
