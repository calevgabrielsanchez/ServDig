package mx.gob.imss.cit.cda.web.constants;

public enum RolUsuarioEnum {
	
	AUTORIZADOR_OCE("JEFE DE OFICINA DE CLASIFICACION DE EMPRESAS", "AUTORIZADOR", "atencionAutorizador"),
	AUTORIZADOR_DAV("JEFE DE DEPARTAMENTO AFILIACION VIGENCIA", "AUTORIZADOR", "atencionAutorizador"),
	VENTANILLA("VENTANILLA", "RESPONSABLE", "atencionResponsable");
	
	private String rol;
	private String descripcion;
	private String defaultUrl;
	
	private RolUsuarioEnum() {
	}

	private RolUsuarioEnum(String rol, String descripcion, String defaultUrl) {
		this.rol = rol;
		this.descripcion = descripcion;
		this.defaultUrl = defaultUrl;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	
	
}
