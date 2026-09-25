package mx.gob.imss.ctirss.idse.model.enums;

public enum EstadoFielEnum {

	ACTIVA(1 ,"FIEL ACTIVA"),
	INACTIVA(2, "FIEL INACTIVA"),
	CANCELADA(3, "FIEL CANCELADA");
	
	private long id;
	private String descripcion;

	EstadoFielEnum(long id, String descripcion) {
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

	//Obtener EstadoFielEnum por medio de la clave.
    public static EstadoFielEnum parse(long clave) {
    	EstadoFielEnum eEnum = null;
        for (EstadoFielEnum item : EstadoFielEnum.values()) {
            if (item.getId()==clave) {
            	eEnum = item;
                break;
            }
        }
        return eEnum;
    }
	
}
