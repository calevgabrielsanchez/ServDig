package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlRootElement( name = "NivelEducativo")
public class NivelEducativo extends AbstractModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4228567369500312120L;
	private Long idnivelEducativo;
	private String desNivelEducativo;
	
	public Long getIdnivelEducativo() {
		return idnivelEducativo;
	}
	public void setIdnivelEducativo(Long idnivelEducativo) {
		this.idnivelEducativo = idnivelEducativo;
	}
	public String getDesNivelEducativo() {
		return desNivelEducativo;
	}
	public void setDesNivelEducativo(String desNivelEducativo) {
		this.desNivelEducativo = desNivelEducativo;
	}
	
}
