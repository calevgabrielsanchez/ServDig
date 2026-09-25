package mx.gob.imss.ctirss.delta.model.enums;


public enum ApartadoPersonaBeneficioEnum {
	
	APARTADO_A("A"), APARTADO_B("B"), APARTADO_C("C"), APARTADO_AB("AB");

	private String clave;

	private ApartadoPersonaBeneficioEnum(String clave) {
		this.clave = clave;
	}

	public String getClave() {
		return clave;
	}
	
	
}
