/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart-nez Cham-nica
 * @Proyecto: delta
 * @Archivo: EstadoSolicitudEnum.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 * @Fecha: 10:17:04
 */
public enum EstadoSolicitudEnum {
	REGISTRADA(1), ATENDIDA(2), CANCELADA(3), VALIDADA(4), PENDIENTE_AUTORIZACION(5), RECHAZADA(11);

	private Integer codigo;

	private EstadoSolicitudEnum(Integer valor) {
		codigo = valor;
	}

	public Integer getValor() {
		return codigo;
	}
}
