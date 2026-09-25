package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

/**
 * Clase de transporte de informacion del aprobador.
 * 
 * @author Jonathan Diaz
 *
 */
public class AprobadorDTO implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -6478243192076313737L;

	private long cveIdAprobador;
	private SolicitudDTO solicitud;
	private String matricula;
	private EstatusDTO estatus;
	private int tipoAprobador = 0;
	private int tipoAprobadorDpes;

	/**
	 * @return the cveIdAprobador
	 */
	public long getCveIdAprobador() {
		return cveIdAprobador;
	}

	/**
	 * @param cveIdAprobador
	 *            the cveIdAprobador to set
	 */
	public void setCveIdAprobador(long cveIdAprobador) {
		this.cveIdAprobador = cveIdAprobador;
	}

	/**
	 * @return the solicitud
	 */
	public SolicitudDTO getSolicitud() {
		return solicitud;
	}

	/**
	 * @param solicitud
	 *            the solicitud to set
	 */
	public void setSolicitud(SolicitudDTO solicitud) {
		this.solicitud = solicitud;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula
	 *            the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * @return the estatus
	 */
	public EstatusDTO getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus
	 *            the estatus to set
	 */
	public void setEstatus(EstatusDTO estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the tipoAprobador
	 */
	public int getTipoAprobador() {
		return tipoAprobador;
	}

	/**
	 * @param tipoAprobador
	 *            the tipoAprobador to set
	 */
	public void setTipoAprobador(int tipoAprobador) {
		this.tipoAprobador = tipoAprobador;
	}

	/**
	 * @return the tipoAprobadorDpes
	 */
	public int getTipoAprobadorDpes() {
		return tipoAprobadorDpes;
	}

	/**
	 * @param tipoAprobadorDpes
	 *            the tipoAprobadorDpes to set
	 */
	public void setTipoAprobadorDpes(int tipoAprobadorDpes) {
		this.tipoAprobadorDpes = tipoAprobadorDpes;
	}

	/**
	 * @return the solicitud
	 */
	public String getDatos() {
		if (solicitud != null) {
			return "Usuario: " + solicitud.getNombreCompleto() + ", Area normativa: "
					+ solicitud.getAreaNorm().getDesAreanorma();
		} else {
			return "";
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "mx.gob.imss.ctirss.sso.admonusuarios.dto[ "
				+ "  cveIdAprobador=" + this.cveIdAprobador 
				+ ", matricula=" + this.matricula 
				+ ", tipoAprobador=" + this.tipoAprobador 
				+ ", datosBitacora=" + this.solicitud != null ? this.solicitud.datosBitacora() : "" 
				+ " ]";
	}

}
