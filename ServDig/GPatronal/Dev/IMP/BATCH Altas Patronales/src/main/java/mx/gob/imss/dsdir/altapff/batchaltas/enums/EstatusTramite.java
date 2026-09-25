package mx.gob.imss.dsdir.altapff.batchaltas.enums;

public enum EstatusTramite {
	INICIADO(1),
	CERRADO(2),
	EN_ESPERA_DE_AUTORIZACION(3),
	EN_ESPERA_POR_TRAMITADOR(4),
	EN_ESPERA_POR_DERECHOHABIENTE(5),
	ACTIVO(6),	
	CANCELADO(7);
	private Integer estatus;
	
	private EstatusTramite(Integer estatus) {
		this.estatus = estatus;
	}

	public Integer getEstatus() {
		return estatus;
	}
	
}
