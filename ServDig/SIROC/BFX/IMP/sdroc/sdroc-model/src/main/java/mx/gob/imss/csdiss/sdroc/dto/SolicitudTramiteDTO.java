package mx.gob.imss.csdiss.sdroc.dto;

/**
 * DelegacionDTO 
 */
public class SolicitudTramiteDTO implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8613451550306323972L;
	
	private String folioSolicitud;
	private Long cveIdTramite;
	
	public SolicitudTramiteDTO(){
		
	}
	
	
	public SolicitudTramiteDTO(String folioSolicitud, Long cveIdTramite) {
		this.folioSolicitud = folioSolicitud;
		this.cveIdTramite = cveIdTramite;
	}


	public String getFolioSolicitud() {
		return folioSolicitud;
	}


	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}


	public Long getCveIdTramite() {
		return cveIdTramite;
	}


	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}

	
	
}
