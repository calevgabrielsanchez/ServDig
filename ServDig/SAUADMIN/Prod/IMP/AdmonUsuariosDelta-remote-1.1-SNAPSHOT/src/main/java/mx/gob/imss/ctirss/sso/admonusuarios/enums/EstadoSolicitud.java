/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.enums;

/**
 * @author Alan Rene Garcia Rico
 *
 */
public enum EstadoSolicitud {
	
	SOLICITADO(Long.valueOf(1)),
	APROBADO(Long.valueOf(2)),
	RECHAZADO(Long.valueOf(3)),
	BAJA(Long.valueOf(4)),
	REACTIVADO(Long.valueOf(5)),
	BAJA_POR_CAMBIO_DE_CURP(Long.valueOf(6)),
	CAMBIO_DATOS_PERSONALES(Long.valueOf(7)),
	CAMBIO_AREA_ADSCRIPCION(Long.valueOf(8)),
	CAMBIO_DE_PERFIL(Long.valueOf(9)),
	CAMBIO_DE_MODULO(Long.valueOf(10));
	
	private Long id;
	
	EstadoSolicitud(Long id) {
		this.id=id;
	}
	
	public Long getId() {
		return this.id;
	}
}
