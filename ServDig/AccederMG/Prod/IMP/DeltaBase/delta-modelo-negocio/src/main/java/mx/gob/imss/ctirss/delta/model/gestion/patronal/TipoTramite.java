package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoTramite extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2573712218729863607L;
	private Long idTipoTramite;
	private String descripcion;
	private byte[] guiaRapida;
	private byte[] guiaDetallada;

	public Long getIdTipoTramite() {
		return idTipoTramite;
	}

	public void setIdTipoTramite(Long idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the guiaRapida
	 */
	public byte[] getGuiaRapida() {
		return guiaRapida;
	}

	/**
	 * @param guiaRapida
	 *            the guiaRapida to set
	 */
	public void setGuiaRapida(byte[] guiaRapida) {
		this.guiaRapida = guiaRapida != null ? guiaRapida.clone() : null;
	}

	/**
	 * @return the guiaDetallada
	 */
	public byte[] getGuiaDetallada() {
		return guiaDetallada;
	}

	/**
	 * @param guiaDetallada
	 *            the guiaDetallada to set
	 */
	public void setGuiaDetallada(byte[] guiaDetallada) {
		this.guiaDetallada = guiaDetallada != null ? guiaDetallada.clone() : null;
	}

}
