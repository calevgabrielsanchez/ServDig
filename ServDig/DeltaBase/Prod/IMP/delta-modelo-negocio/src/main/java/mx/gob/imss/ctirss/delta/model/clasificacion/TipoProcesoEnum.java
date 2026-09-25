package mx.gob.imss.ctirss.delta.model.clasificacion;

/**
 * Distinción del proceso correspondiente a los tipos de causas de análisis.
 * @author Eduardo
 * @since 29/10/2012
 */
public enum TipoProcesoEnum {
    
	GESTION_PATRONAL(1, "GESTION PATRONAL"),
    GESTION_CLASIFICACION_EMPRESAS(2, "GESTION DE LA CLASIFICACION DE EMPRESAS");
    
    private final int clave;
	private final String descripcion;
	
	private TipoProcesoEnum(int clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getClave() {
		return clave;
	}	
	
}