package mx.gob.imss.ctirss.delta.model.asegurado;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class EstadoAsignacion extends AbstractModel implements Serializable {
	private static final long serialVersionUID = 1333157618525782343L;

	private String curp;
	protected String nombre;
	protected String apellidoPaterno;
	protected String apellidoMaterno;

	private String nssAsignado;
	private boolean exitoAlta;
	private String mensajeError;
	private Date fechaAlta;
	private Long idAsegurado;
	private boolean errorNoControlado;

	/**
	 * @return the nssAsignado
	 */
	public String getNssAsignado() {
		return nssAsignado;
	}

	/**
	 * @param nssAsignado
	 *            the nssAsignado to set
	 */
	public void setNssAsignado(String nssAsignado) {
		this.nssAsignado = nssAsignado;
	}

	/**
	 * @return the exitoAlta
	 */
	public boolean isExitoAlta() {
		return exitoAlta;
	}

	/**
	 * @param exitoAlta
	 *            the exitoAlta to set
	 */
	public void setExitoAlta(boolean exitoAlta) {
		this.exitoAlta = exitoAlta;
	}

	/**
	 * @return the mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * @param mensajeError
	 *            the mensajeError to set
	 */
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	/**
	 * @return the fechaAlta
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta
	 *            the fechaAlta to set
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * @return the idAsegurado
	 */
	public Long getIdAsegurado() {
		return idAsegurado;
	}

	/**
	 * @param idAsegurado
	 *            the idAsegurado to set
	 */
	public void setIdAsegurado(Long idAsegurado) {
		this.idAsegurado = idAsegurado;
	}

	/**
	 * @return the errorNoControlado
	 */
	public boolean isErrorNoControlado() {
		return errorNoControlado;
	}

	/**
	 * @param errorNoControlado the errorNoControlado to set
	 */
	public void setErrorNoControlado(boolean errorNoControlado) {
		this.errorNoControlado = errorNoControlado;
	}

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	@Override
	public String toString() {
		StringBuffer sbEstadoAsignacion = new StringBuffer();

		sbEstadoAsignacion.append("EstadoAsignacion [curp=").append(curp)
				.append(", nombre=").append(nombre)
				.append(", apellidoPaterno=").append(apellidoPaterno)
				.append(", apellidoMaterno=").append(apellidoMaterno)
				.append(", nssAsignado=").append(nssAsignado)
				.append(", exitoAlta=").append(exitoAlta)
				.append(", mensajeError=").append(mensajeError)
				.append(", fechaAlta=").append(fechaAlta)
				.append(", idAsegurado=").append(idAsegurado)
				.append(", errorNoControlado=").append(errorNoControlado)
				.append("]");

		return sbEstadoAsignacion.toString();
	}

}
