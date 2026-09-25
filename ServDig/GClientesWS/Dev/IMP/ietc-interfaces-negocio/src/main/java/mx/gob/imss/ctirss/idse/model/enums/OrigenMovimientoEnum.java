package mx.gob.imss.ctirss.idse.model.enums;

public enum OrigenMovimientoEnum {

	VENTANILLA			(1, "VENTANILLA"),
	INTERNET			(2, "INTERNET"),
	MOVILES				(5, "MOVILES"),
	PORTAL_CIUDADANO	(6, "PORTAL CIUDADANO"),
	VENTANILLA_UNICA	(7, "VENTANILLA \u00DANICA");
	
	private int id;
	private String descripcion;

	OrigenMovimientoEnum(int id, String descripcion) {
		this.id=id;
		this.descripcion=descripcion;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	//Obtener OrigenMovimientoEnum por medio de la clave.
    public static OrigenMovimientoEnum parse(int clave) {
    	OrigenMovimientoEnum eEnum = null;
        for (OrigenMovimientoEnum item : OrigenMovimientoEnum.values()) {
            if (item.getId()==clave) {
            	eEnum = item;
                break;
            }
        }
        return eEnum;
    }
    
}
