package mx.gob.imss.ctirss.idse.model.enums;

public enum AccionMovimientosEnum {
	//INTERNET
	ADHESION_REGISTRO_PATRONAL						(10, "ADHESION DE REGISTRO PATRONAL DESDE IMSS DIGITAL INTERNET"),
	DISOSIACION_REGISTRO_PATRONAL					(11, "DISOCIACION DE REGISTRO PATRONAL DESDE IMSS DIGITAL INTERNET"),
	ADHESION_REPRESENTANTE_LEGAL					(12, "ADHESION DE REPRESENTANTE LEGAL DESDE IMSS DIGITAL INTERNET"),
	DISOSIACION_REPRESENTANTE_LEGAL					(13, "DISOCIACION DE REPRESENTANTE LEGAL DESDE IMSS DIGITAL INTERNET"),
	//VENTANILLA
	ADHESION_REGISTRO_PATRONAL_VENTANILLA			(14, "ADHESION DE REGISTRO PATRONAL DESDE VENTANILLA DIGITAL"),
	DISOSIACION_REGISTRO_PATRONAL_VENTANILLA		(15, "DISOCIACION DE REGISTRO PATRONAL DESDE VENTANILLA DIGITAL"),
	ADHESION_REPRESENTANTE_LEGAL_VENTANILLA			(16, "ADHESION DE REPRESENTANTE LEGAL DESDE VENTANILLA DIGITAL"),
	DISOSIACION_REPRESENTANTE_LEGAL_VENTANILLA		(17, "DISOCIACION DE REPRESENTANTE LEGAL DESDE VENTANILLA DIGITAL")
	
	;
	
	private long id;
	private String descripcion;

	AccionMovimientosEnum(long id, String descripcion) {
		this.id=id;
		this.descripcion=descripcion;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	//Obtener AccionMovimientosEnum por medio de la clave.
    public static AccionMovimientosEnum parse(long clave) {
    	AccionMovimientosEnum eEnum = null;
        for (AccionMovimientosEnum item : AccionMovimientosEnum.values()) {
            if (item.getId()==clave) {
            	eEnum = item;
                break;
            }
        }
        return eEnum;
    }
    
}
