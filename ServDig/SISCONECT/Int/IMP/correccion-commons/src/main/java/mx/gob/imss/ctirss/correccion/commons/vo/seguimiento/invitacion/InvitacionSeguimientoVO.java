/**
 * 
 */
package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.invitacion;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * Objeto visual con las propiedades mostradas para el seguimiento de las invitaciones
 * @author CesarAgustin
 * @version 1.0.0
 * @since 30/06/2012
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvitacionSeguimientoVO extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35500216939281199L;
	
	//Parametros para filtrar la busqueda
	private Map<String, Map> tiposCorr = new HashMap<String, Map>();
	private String fechaEmisionIni;
	private String fechaEmisionFin;
	private String folioInvitacion;
	private String idTipoCorr;
	private Long idSubDelegacion;
	
	private String fechaEmision;
	private String antecedente;
	private String regPatronal;
	private String razonSocial;
	private String numeroOficio;
	private Long cveIdUsuario;
	private Long cveRol;
	
	public Map<String, Map> getTiposCorr() {
		return tiposCorr;
	}
	public void setTiposCorr(Map<String, Map> tiposCorr) {
		this.tiposCorr = tiposCorr;
	}
	
	public String getFechaEmisionIni() {
		return fechaEmisionIni;
	}
	public void setFechaEmisionIni(String fechaEmisionIni) {
		this.fechaEmisionIni = fechaEmisionIni;
	}
	
	public String getFechaEmisionFin() {
		return fechaEmisionFin;
	}
	public void setFechaEmisionFin(String fechaEmisionFin) {
		this.fechaEmisionFin = fechaEmisionFin;
	}
	
	public String getFolioInvitacion() {
		return folioInvitacion;
	}
	public void setFolioInvitacion(String folioInvitacion) {
		this.folioInvitacion = folioInvitacion;
	}
	
	public String getIdTipoCorr() {
		return idTipoCorr;
	}
	public void setIdTipoCorr(String idTipoCorr) {
		this.idTipoCorr = idTipoCorr;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getAntecedente() {
		return antecedente;
	}
	public void setAntecedente(String antecedente) {
		this.antecedente = antecedente;
	}
	public String getRegPatronal() {
		return regPatronal;
	}
	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNumeroOficio() {
		return numeroOficio;
	}
	public void setNumeroOficio(String numeroOficio) {
		this.numeroOficio = numeroOficio;
	}
	public Long getIdSubDelegacion() {
		return idSubDelegacion;
	}
	public void setIdSubDelegacion(Long idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
	}
	/**
	 * @return cveIdUsuario
	 */
	public Long getCveIdUsuario() {
		return cveIdUsuario;
	}
	/**
	 * @param cveIdUsuario Clave del usuario en sesion
	 */
	public void setCveIdUsuario(Long cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}
	/**
	 * @return cveRol
	 */
	public Long getCveRol() {
		return cveRol;
	}
	/**
	 * @param cveRol Clave del rol del usuario en sesion
	 */
	public void setCveRol(Long cveRol) {
		this.cveRol = cveRol;
	}


}
