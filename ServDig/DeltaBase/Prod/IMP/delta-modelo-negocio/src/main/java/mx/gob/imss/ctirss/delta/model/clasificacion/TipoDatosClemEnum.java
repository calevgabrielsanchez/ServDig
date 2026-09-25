package mx.gob.imss.ctirss.delta.model.clasificacion;
/**
 * 
 * @author Jguerra
 *
 */
public enum TipoDatosClemEnum {
	DELEGACIONAL(1, "CLEM DELEGACIONAL"),
	SUBDELEGACIONAL(2, "CLEM SUBDELEGACIONAL");
	
	private final int clave;
	private final String descripcion;
	
	private TipoDatosClemEnum(int cve, String des){
		this.clave = cve;
		this.descripcion = des;
	}

	public int getClave() {
		return clave;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	
}
