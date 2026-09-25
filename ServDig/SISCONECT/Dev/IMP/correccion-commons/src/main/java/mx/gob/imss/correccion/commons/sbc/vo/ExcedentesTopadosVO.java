package mx.gob.imss.correccion.commons.sbc.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcedentesTopadosVO implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private Integer cveEjercicio;
	private Integer cveAnexoSolCorrPat;
	private String folioCorreccion;
	private Double excedenteTopado;
	
	
	
	public ExcedentesTopadosVO(){}
	
	public ExcedentesTopadosVO(Integer cveEjercicio,
						       Integer cveAnexoSolCorrPat,
						       String folioCorreccion){
		
		this.cveEjercicio = cveEjercicio; 
		this.cveAnexoSolCorrPat = cveAnexoSolCorrPat; 
		this.folioCorreccion = folioCorreccion; 
		
	}
	
	public Integer getCveEjercicio() {
		return cveEjercicio;
	}
	public void setCveEjercicio(Integer cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}
	public Integer getCveAnexoSolCorrPat() {
		return cveAnexoSolCorrPat;
	}
	public void setCveAnexoSolCorrPat(Integer cveAnexoSolCorrPat) {
		this.cveAnexoSolCorrPat = cveAnexoSolCorrPat;
	}
	public String getFolioCorreccion() {
		return folioCorreccion;
	}
	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}

	public Double getExcedenteTopado() {
		return excedenteTopado;
	}

	public void setExcedenteTopado(Double excedenteTopado) {
		this.excedenteTopado = excedenteTopado;
	}
	
	

}
