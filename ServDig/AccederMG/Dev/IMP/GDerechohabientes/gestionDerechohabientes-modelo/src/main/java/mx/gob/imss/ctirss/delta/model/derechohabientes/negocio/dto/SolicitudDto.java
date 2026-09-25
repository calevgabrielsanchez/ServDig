package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.List;


public class SolicitudDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1298820776465638767L;
	
	private String numNss;
	private PaginacionDto paginacionDto;
	private Long cveIdNSS; 
	private Long cveModulo;
	private List<Long> cveOrigenesSol;
	//indicador para saber si los origenes en la lista se exluyen es decir,
	//si en la lista viene ventanilla y el indicador viene en true se obtendran las solicitudes
	//que no tengan origen ventanilla, mientras que si es false solo se tomaran las que tengan los origenes de la lista
	private Boolean exluirOrigenes = false;
	private List<Long> estadosSolicitud = null;
	
	public Long getCveIdNSS() {
		return cveIdNSS;
	}
	public void setCveIdNSS(Long cveIdNSS) {
		this.cveIdNSS = cveIdNSS;
	}
	
	public List<Long> getCveOrigenesSol() {
		return cveOrigenesSol;
	}
	public void setCveOrigenesSol(List<Long> cveOrigenesSol) {
		this.cveOrigenesSol = cveOrigenesSol;
	}
	public Long getCveModulo() {
		return cveModulo;
	}
	public void setCveModulo(Long cveModulo) {
		this.cveModulo = cveModulo;
	}
	
	public List<Long> getEstadosSolicitud() {
		return estadosSolicitud;
	}
	public void setEstadosSolicitud(List<Long> estadosSolicitud) {
		this.estadosSolicitud = estadosSolicitud;
	}

	public String getNumNss() {
		return numNss;
	}
	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}
	public PaginacionDto getPaginacionDto() {
		return paginacionDto;
	}
	public void setPaginacionDto(PaginacionDto paginacionDto) {
		this.paginacionDto = paginacionDto;
	}
	public Boolean getExluirOrigenes() {
		return exluirOrigenes;
	}
	public void setExluirOrigenes(Boolean exluirOrigenes) {
		this.exluirOrigenes = exluirOrigenes;
	}
}