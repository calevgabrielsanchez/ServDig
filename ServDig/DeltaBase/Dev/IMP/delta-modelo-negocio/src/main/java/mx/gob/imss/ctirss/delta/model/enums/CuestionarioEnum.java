package mx.gob.imss.ctirss.delta.model.enums;

public enum CuestionarioEnum {
	
	IVRO(1), SOLIC_PENSION(2);
	
	private int tipo;
	
	private  CuestionarioEnum(int tipo){
		this.tipo = tipo;
	}

	public int getTipo(){
		return this.tipo;
	}
}
