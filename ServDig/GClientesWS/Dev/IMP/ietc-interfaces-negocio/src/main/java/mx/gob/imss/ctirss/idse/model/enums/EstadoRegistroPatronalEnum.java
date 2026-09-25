package mx.gob.imss.ctirss.idse.model.enums;

public enum EstadoRegistroPatronalEnum {
	
	RECIBIDO (1 ,"REGISTRO PATRONAL RECIBIDO"),
	ACTIVADO (2, "REGISTRO PATRONAL ACTIVADO"),
	CANCELADO(3, "REGISTRO PATRONAL CANCELADO");
	
	private long id;
	private String descripcion;

	EstadoRegistroPatronalEnum(long id, String descripcion) {
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

	//Obtener EstadoRegistroPatronalEnum por medio de la clave.
    public static EstadoRegistroPatronalEnum parse(long clave) {
    	EstadoRegistroPatronalEnum eEnum = null;
        for (EstadoRegistroPatronalEnum item : EstadoRegistroPatronalEnum.values()) {
            if (item.getId()==clave) {
            	eEnum = item;
                break;
            }
        }
        return eEnum;
    }
}
