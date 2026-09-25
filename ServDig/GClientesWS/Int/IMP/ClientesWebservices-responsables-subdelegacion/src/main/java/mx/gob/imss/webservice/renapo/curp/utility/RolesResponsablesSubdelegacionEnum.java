package mx.gob.imss.webservice.renapo.curp.utility;

public enum RolesResponsablesSubdelegacionEnum {
	
	AUTORIZADOR("61,64"),
	RESPONSABLE("73");
	
	
	private String clave;
	
	
	private RolesResponsablesSubdelegacionEnum(String clave) {
		this.clave = clave;
	}


	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}
	

}
