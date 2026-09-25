package mx.gob.imss.ctirss.delta.model.gestion.individuo;

public enum TipoServicioModificacionEnum {

	ICA(1, "Identificación de Cambios Automáticos"),
	MDM(2, "Modificación Manual de Datos");
	
	private Integer idServicio;
	private String descripcion;
	
	private TipoServicioModificacionEnum(Integer idServicio, String descripcion) {
		this.idServicio = idServicio;
		this.descripcion = descripcion;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	
	
}
