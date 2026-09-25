package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class MotivoAclaracion extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idMotivoAclaracion;
	private String descripcionMotivo;
	private Institucion institucion;

	/**
	 * Campo para almacenar el detalle de los motivos de aclaracion, ej. el
	 * monto del crédito derivado de una alcaracion por
	 * DESCUENTO_INDEBIDO_CREDITO
	 */
	private String detalleAclaracion;

	public Integer getIdMotivoAclaracion() {
		return idMotivoAclaracion;
	}

	public void setIdMotivoAclaracion(Integer idMotivoAclaracion) {
		this.idMotivoAclaracion = idMotivoAclaracion;
	}

	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}

	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public String getDetalleAclaracion() {
		return detalleAclaracion;
	}

	public void setDetalleAclaracion(String detalleAclaracion) {
		this.detalleAclaracion = detalleAclaracion;
	}

}
