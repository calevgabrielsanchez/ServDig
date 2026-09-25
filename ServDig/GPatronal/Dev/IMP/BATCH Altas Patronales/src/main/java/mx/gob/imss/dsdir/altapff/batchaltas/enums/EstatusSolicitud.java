package mx.gob.imss.dsdir.altapff.batchaltas.enums;

public enum EstatusSolicitud {
	REGISTRADA(1),
	ATENDIDA(2),
	CANCELADA(3),
	VALIDADA(4),
	EN_PROCESO(5),
	PROCESANDO_EN_VENTANILLA(6),
	PROCESANDO_EN_BACKOFFICE(7),
	CERRADA(8),
	CANCELADO_IMPROCEDENTE(14),
	PROCESADA_EN_BACKOFFICE(9),
	PROCESADA_EN_VENTANILLA(10),
	RECHAZADA(11),
	PRESENTARSE_EN_VENTANILLA(12),
	PARA_PROCESAR_EN_BACKOFFICE(13);
	
	private Integer estatus;
	
	private EstatusSolicitud(Integer estatus) {
		this.estatus = estatus;
	}

	public Integer getEstatus() {
		return estatus;
	}
	

}
