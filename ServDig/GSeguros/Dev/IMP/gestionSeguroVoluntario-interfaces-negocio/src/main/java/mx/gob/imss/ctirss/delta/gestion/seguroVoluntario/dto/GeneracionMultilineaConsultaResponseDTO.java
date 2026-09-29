package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;

/**
 * Envoltura de la respuesta de la consulta de la generacion de multilinea.
 *
 * Mantiene la estructura del servicio externo (codigo, descripcion y dato) pero
 * con tipos propios de la aplicacion.
 *
 * @author OPENCODE
 */
public class GeneracionMultilineaConsultaResponseDTO implements Serializable {

	private static final long serialVersionUID = 6095221764173118265L;

	/**
	 * Codigo devuelto por el servicio externo. Solo se llena cuando la consulta
	 * fue exitosa; si el servicio falla se propaga la excepcion.
	 */
	private String codigo;
	/**
	 * Descripcion legible del resultado.
	 */
	private String descripcion;
	/**
	 * Datos de la multilinea. Puede venir nulo cuando el externo respondio sin
	 * informacion.
	 */
	private GeneracionMultilineaConsultaDTO vrDto;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public GeneracionMultilineaConsultaDTO getVrDto() {
		return vrDto;
	}

	public void setVrDto(GeneracionMultilineaConsultaDTO vrDto) {
		this.vrDto = vrDto;
	}
}
