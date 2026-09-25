
package mx.gob.imss.ctirss.delta.model.clasificacion;

/**
 *
 * @author Eduardo Gonzalez
 * @since 10/10/2012
 */
public enum GrupoAnalisisCeEnum {
    
	INSCRIPCION_INICIAL(1, "INSCRIPCION INICIAL"),
    MODIFICACION_PATRONAL(2, "MODIFICACION PATRONAL");
    
    private final int clave;
	private final String descripcion;
	
	
	private GrupoAnalisisCeEnum(int clave, String descripcion) {
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
