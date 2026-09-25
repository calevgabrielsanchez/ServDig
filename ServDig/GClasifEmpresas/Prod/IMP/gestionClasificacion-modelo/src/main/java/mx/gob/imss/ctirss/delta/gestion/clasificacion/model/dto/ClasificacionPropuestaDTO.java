package mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto;

import java.io.Serializable;

public class ClasificacionPropuestaDTO extends ClasificacionDTO implements Serializable{
	private static final long serialVersionUID = -1831910694978638804L;
	
	private String indRegPatClase;
	private String cveIdDivision;
	private String cveIdGrupo;
	private String clase;
	private String actividadDetectada;
	private String rfc;
	private String cveUsuarioAsignado;
	private String indModAut;
	private String fraccionAnterior;
	private String primaSugerida;
	
	public String getIndRegPatClase() {
		return indRegPatClase;
	}
	public void setIndRegPatClase(String indRegPatClase) {
		this.indRegPatClase = indRegPatClase;
	}
	public String getCveIdDivision() {
		return cveIdDivision;
	}
	public void setCveIdDivision(String cveIdDivision) {
		this.cveIdDivision = cveIdDivision;
	}
	public String getCveIdGrupo() {
		return cveIdGrupo;
	}
	public void setCveIdGrupo(String cveIdGrupo) {
		this.cveIdGrupo = cveIdGrupo;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getActividadDetectada() {
		return actividadDetectada;
	}
	public void setActividadDetectada(String actividadDetectada) {
		this.actividadDetectada = actividadDetectada;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCveUsuarioAsignado() {
		return cveUsuarioAsignado;
	}
	public void setCveUsuarioAsignado(String cveUsuarioAsignado) {
		this.cveUsuarioAsignado = cveUsuarioAsignado;
	}
	public String getIndModAut() {
		return indModAut;
	}
	public void setIndModAut(String indModAut) {
		this.indModAut = indModAut;
	}
	public String getFraccionAnterior() {
		return fraccionAnterior;
	}
	public void setFraccionAnterior(String fraccionAnterior) {
		this.fraccionAnterior = fraccionAnterior;
	}
	
	/**
	 * @return the primaSugerida
	 */
	public String getPrimaSugerida() {
		return primaSugerida;
	}
	/**
	 * @param primaSugerida the primaSugerida to set
	 */
	public void setPrimaSugerida(String primaSugerida) {
		this.primaSugerida = primaSugerida;
	}
	@Override
	public String toString(){
		return "ClasificacionPropuestaDTO [indRegPatClase=" + indRegPatClase
				+ ", cveIdDivision=" + cveIdDivision + ", cveIdGrupo="
				+ cveIdGrupo + ", clase=" + clase + ", actividadDetectada="
				+ actividadDetectada + ", rfc=" + rfc + ", cveUsuarioAsignado="
				+ cveUsuarioAsignado + ", indModAut=" + indModAut
				+ ", fraccionAnterior=" + fraccionAnterior + "]";
	}
}