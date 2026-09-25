package mx.gob.imss.ctirss.idse.model.enums;

public enum TipoPersonaEnum {
	
	FISICA(1), 
	MORAL(2);
	
	private int id;

	TipoPersonaEnum(int id) {
		this.id=id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	//Obtener TipoPersonaEnum por medio de la clave.
    public static TipoPersonaEnum parse(int clave) {
    	TipoPersonaEnum eEnum = null;
        for (TipoPersonaEnum item : TipoPersonaEnum.values()) {
            if (item.getId()==clave) {
            	eEnum = item;
                break;
            }
        }
        return eEnum;
    }
    
}
