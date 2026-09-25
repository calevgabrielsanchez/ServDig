package mx.gob.imss.ctirss.idse.model.enums;

public enum EstadoRelacionEnum {

	ACTIVA(1 ,"RELACION ACTIVA"),
	INACTIVA(2, "RELACION INACTIVA");
	
	private long id;
	private String descripcion;

	EstadoRelacionEnum(long id, String descripcion) {
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

	//Obtener EstadoRelacionEnum por medio de la clave.
    public static EstadoRelacionEnum parse(long clave) {
    	EstadoRelacionEnum eEnum = null;
        for (EstadoRelacionEnum item : EstadoRelacionEnum.values()) {
            if (item.getId()==clave) {
            	eEnum = item;
                break;
            }
        }
        return eEnum;
    }
    
}
