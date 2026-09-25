package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoOperacionNotificacionIVROEnum {
	
	RECEPCION_DE_PAGO(10),RECORDATORIO_DE_RENOVACION(20),RECORDATORIO_DE_PAGO(30),SUSPENSION_POR_FALTA_PAGO(40), NOTIFICACION_RENOVACION_MOD40(50),
        BAJA_POR_MORA(60), BAJA_POR_REINGRESO(70),RECEPCION_DE_PAGO_MOD40(80), FIN_TRAMITE(90),
        BAJA_POR_MORA_V2(61),
        BAJA_POR_REINGRESO_V2(71),
        SOLICITUD_BAJA_EXPRESA(85),
        CONFIRMACION_BAJA_EXPRESA(86);
	
	private int codigo;
	
	private TipoOperacionNotificacionIVROEnum(int codigo){
		this.codigo=codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	
}
