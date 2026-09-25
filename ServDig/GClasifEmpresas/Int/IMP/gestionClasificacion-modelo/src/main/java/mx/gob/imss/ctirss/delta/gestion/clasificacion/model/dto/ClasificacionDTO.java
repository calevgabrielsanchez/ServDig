package mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.Usuario;

public class ClasificacionDTO implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = 8013680795347182639L;
	private String cveIdDelegacion;
	private String cveIdSubdelegacion;
	private String cveNumDelegacion;
	private String cveNumSubdelegacion;
	private String cveIdSolicitud;
	private String regPatronal;
	private String tipoPersona;
	private String cveIdAnalisis;
	private String cveIdFraccionAct;
	private String cveIdFraccionPro;
	private String cveIdFraccionAnt;
	private String primaSRTAct;
	private String primaSRTPro;
	private String primaSRTAnt;
	private String comentarios;
	private String idEstatus;
	private Usuario usuario;
	private String cveIdGrupoAnalisisCe;
	
	private String giro;
	private int classe;
	private String numeroFolio;
	private int ciz;
	
	private String tTramite;
	/**
	 * @return the cveIdDelegacion
	 */
	public String getCveIdDelegacion() {
		return cveIdDelegacion;
	}

	/**
	 * @param cveIdDelegacion
	 *            the cveIdDelegacion to set
	 */
	public void setCveIdDelegacion(final String cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	/**
	 * @return the cveIdSubdelegacion
	 */
	public String getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}

	/**
	 * @param cveIdSubdelegacion
	 *            the cveIdSubdelegacion to set
	 */
	public void setCveIdSubdelegacion(final String cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	/**
	 * @return the cveIdSolicitud
	 */
	public String getCveIdSolicitud() {
		return cveIdSolicitud;
	}

	/**
	 * @param cveIdSolicitud
	 *            the cveIdSolicitud to set
	 */
	public void setCveIdSolicitud(final String cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	/**
	 * @return the regPatronal
	 */
	public String getRegPatronal() {
		return regPatronal;
	}

	/**
	 * @param regPatronal
	 *            the regPatronal to set
	 */
	public void setRegPatronal(final String regPatronal) {
		this.regPatronal = regPatronal;
	}

	/**
	 * @return the tipoPersona
	 */
	public String getTipoPersona() {
		return tipoPersona;
	}

	/**
	 * @param tipoPersona
	 *            the tipoPersona to set
	 */
	public void setTipoPersona(final String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	/**
	 * @return the cveIdAnalisis
	 */
	public String getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	/**
	 * @param cveIdAnalisis
	 *            the cveIdAnalisis to set
	 */
	public void setCveIdAnalisis(final String cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	/**
	 * @return the cveIdFraccionAct
	 */
	public String getCveIdFraccionAct() {
		return cveIdFraccionAct;
	}

	/**
	 * @param cveIdFraccionAct
	 *            the cveIdFraccionAct to set
	 */
	public void setCveIdFraccionAct(final String cveIdFraccionAct) {
		this.cveIdFraccionAct = cveIdFraccionAct;
	}

	/**
	 * @return the cveIdFraccionPro
	 */
	public String getCveIdFraccionPro() {
		return cveIdFraccionPro;
	}

	/**
	 * @param cveIdFraccionPro
	 *            the cveIdFraccionPro to set
	 */
	public void setCveIdFraccionPro(final String cveIdFraccionPro) {
		this.cveIdFraccionPro = cveIdFraccionPro;
	}

	/**
	 * @return the cveIdFraccionAnt
	 */
	public String getCveIdFraccionAnt() {
		return cveIdFraccionAnt;
	}

	/**
	 * @param cveIdFraccionAnt
	 *            the cveIdFraccionAnt to set
	 */
	public void setCveIdFraccionAnt(final String cveIdFraccionAnt) {
		this.cveIdFraccionAnt = cveIdFraccionAnt;
	}

	/**
	 * @return the primaSRTAct
	 */
	public String getPrimaSRTAct() {
		return primaSRTAct;
	}

	/**
	 * @param primaSRTAct
	 *            the primaSRTAct to set
	 */
	public void setPrimaSRTAct(final String primaSRTAct) {
		this.primaSRTAct = primaSRTAct;
	}

	/**
	 * @return the primaSRTPro
	 */
	public String getPrimaSRTPro() {
		return primaSRTPro;
	}

	/**
	 * @param primaSRTPro
	 *            the primaSRTPro to set
	 */
	public void setPrimaSRTPro(final String primaSRTPro) {
		this.primaSRTPro = primaSRTPro;
	}

	/**
	 * @return the primaSRTAnt
	 */
	public String getPrimaSRTAnt() {
		return primaSRTAnt;
	}

	/**
	 * @param primaSRTAnt
	 *            the primaSRTAnt to set
	 */
	public void setPrimaSRTAnt(final String primaSRTAnt) {
		this.primaSRTAnt = primaSRTAnt;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(final Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getIdEstatus() {
		return idEstatus;
	}

	public void setIdEstatus(String idEstatus) {
		this.idEstatus = idEstatus;
	}

	public String getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}

	public void setCveIdGrupoAnalisisCe(String cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}

	public String getGiro() {
		return giro;
	}

	public void setGiro(String giro) {
		this.giro = giro;
	}

	public int getClasse() {
		return classe;
	}

	public void setClasse(int classe) {
		this.classe = classe;
	}

	public String getNumeroFolio() {
		return numeroFolio;
	}

	public void setNumeroFolio(String numeroFolio) {
		this.numeroFolio = numeroFolio;
	}

	public int getCiz() {
		return ciz;
	}

	public void setCiz(int ciz) {
		this.ciz = ciz;
	}

	public String getCveNumDelegacion() {
		return cveNumDelegacion;
	}

	public void setCveNumDelegacion(String cveNumDelegacion) {
		this.cveNumDelegacion = cveNumDelegacion;
	}

	public String getCveNumSubdelegacion() {
		return cveNumSubdelegacion;
	}

	public void setCveNumSubdelegacion(String cveNumSubdelegacion) {
		this.cveNumSubdelegacion = cveNumSubdelegacion;
	}

	public String getTTramite() {
		return tTramite;
	}

	public void setTTramite(String tTramite) {
		this.tTramite = tTramite;
	}

	@Override
	public String toString() {
		return "ClasificacionDTO [cveIdDelegacion=" + cveIdDelegacion
				+ ", cveIdSubdelegacion=" + cveIdSubdelegacion
				+ ", cveNumDelegacion=" + cveNumDelegacion
				+ ", cveNumSubdelegacion=" + cveNumSubdelegacion
				+ ", cveIdSolicitud=" + cveIdSolicitud + ", regPatronal="
				+ regPatronal + ", tipoPersona=" + tipoPersona
				+ ", cveIdAnalisis=" + cveIdAnalisis + ", cveIdFraccionAct="
				+ cveIdFraccionAct + ", cveIdFraccionPro=" + cveIdFraccionPro
				+ ", cveIdFraccionAnt=" + cveIdFraccionAnt + ", primaSRTAct="
				+ primaSRTAct + ", primaSRTPro=" + primaSRTPro
				+ ", primaSRTAnt=" + primaSRTAnt + ", comentarios="
				+ comentarios + ", idEstatus=" + idEstatus + ", usuario="
				+ usuario + ", cveIdGrupoAnalisisCe=" + cveIdGrupoAnalisisCe
				+ ", giro=" + giro + ", classe=" + classe + ", numeroFolio="
				+ numeroFolio + ", ciz=" + ciz + ", tTramite=" + tTramite + "]";
	}
}