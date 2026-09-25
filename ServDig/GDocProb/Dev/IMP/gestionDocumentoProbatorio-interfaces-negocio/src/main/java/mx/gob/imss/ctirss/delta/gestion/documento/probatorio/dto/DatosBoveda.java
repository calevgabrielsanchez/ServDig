package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.dto;

import java.io.Serializable;
import java.util.Map;

public class DatosBoveda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1786574956917978091L;
	
	private Long idTramite;
	private Integer tipoComponente;
	private Integer tipoTramite;
	private String folio;
	private String rutaBoveda;
	private String tipoDocumentos;
	private String tipoDocumental;
	private Map<String, String> datosAdicionales;
	
	public Long getIdTramite() {
		return idTramite;
	}
	
	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}
	
	public Integer getTipoComponente() {
		return tipoComponente;
	}
	
	public void setTipoComponente(Integer tipoComponente) {
		this.tipoComponente = tipoComponente;
	}
	
	public Integer getTipoTramite() {
		return tipoTramite;
	}
	
	public void setTipoTramite(Integer tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	
	public String getFolio() {
		return folio;
	}
	
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public String getRutaBoveda() {
		return rutaBoveda;
	}
	
	public void setRutaBoveda(String rutaBoveda) {
		this.rutaBoveda = rutaBoveda;
	}
	
	public String getTipoDocumentos() {
		return tipoDocumentos;
	}
	
	public void setTipoDocumentos(String tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}
	
	public String getTipoDocumental() {
		return tipoDocumental;
	}
	
	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}

	public Map<String, String> getDatosAdicionales() {
		return datosAdicionales;
	}

	public void setDatosAdicionales(Map<String, String> datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}

}