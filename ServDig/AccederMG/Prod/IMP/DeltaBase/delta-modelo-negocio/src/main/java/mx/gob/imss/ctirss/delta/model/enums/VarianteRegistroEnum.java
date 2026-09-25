package mx.gob.imss.ctirss.delta.model.enums;

public enum VarianteRegistroEnum {
	
	NORMAL(0,"NORMAL"), RECIEN_NACIDO(1, "RECIEN NACIDO"), ADOPCION(2, "ADOPCION"), RECONOCIMIENTO(3, "RECONOCIMIENTO");
	
	private int id;
	private String descripcion;

	private VarianteRegistroEnum(int id , String descrpcion) {
		this.id = id;
		this.descripcion = descrpcion;
	}

	public int getId() {
		return id;
	}
	
	public String getDescripcion(){
		return descripcion;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
