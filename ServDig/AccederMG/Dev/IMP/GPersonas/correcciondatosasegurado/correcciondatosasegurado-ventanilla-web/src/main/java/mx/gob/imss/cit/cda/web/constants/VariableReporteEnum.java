package mx.gob.imss.cit.cda.web.constants;

public enum VariableReporteEnum {
	
	DELEGACION("Delegación", "delegacion"),
	SUBDELEGACION("Subdelegación", "subdelegacion"),
	RESPONSABLE("Responsabe", "responsable"),
	AUTORIZADOR("Autorizador", "autorizador"),
	ESTADO("Estado", "estado"),
	ORIGEN("Origen", "origen"),
	TIPO_TRAMITE("Tipo trámite", "tipoTramite");
	
	private String variable;
	private String descripcion;
	
	
	private VariableReporteEnum() {
	}

	private VariableReporteEnum(String variable, String descripcion) {
		this.variable = variable;
		this.descripcion = descripcion;
		
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

		
	
}
