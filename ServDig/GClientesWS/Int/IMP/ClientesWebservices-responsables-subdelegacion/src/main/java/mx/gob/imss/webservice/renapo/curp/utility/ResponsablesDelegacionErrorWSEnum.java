package mx.gob.imss.webservice.renapo.curp.utility;

public enum ResponsablesDelegacionErrorWSEnum {
	
	EXITO(0,"Consulta Exitosa"),
	PARAMETROS_BUSQUEDA_INCORRECTO(1,"Parámetros de búsqueda incorrectos"),
	ERROR_CONSULTAR_INFORMACION(2,"Error al consultar informacion"),
	SISTEMA_NO_DISPONIBLE(3,"El sistema no está disponible");
	
	
	private int clave;
	private String mensaje;
	
	
	private ResponsablesDelegacionErrorWSEnum(int clave, String mensaje) {
		this.clave = clave;
		this.mensaje = mensaje;
	}
	
	public int getClave() {
		return clave;
	}
	public void setClave(int clave) {
		this.clave = clave;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	

}
