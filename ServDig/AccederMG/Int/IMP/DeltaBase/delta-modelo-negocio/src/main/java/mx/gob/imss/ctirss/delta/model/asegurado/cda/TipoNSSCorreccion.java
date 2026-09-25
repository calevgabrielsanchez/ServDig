package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TipoNSSCorreccion extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idTipoCertificacion;
	private Integer idTipoNSSAclaracion;
	private String desTipoNSSAclaracion;

	public Integer getIdTipoNSSAclaracion() {
		return idTipoNSSAclaracion;
	}

	public void setIdTipoNSSAclaracion(Integer idTipoNSSAclaracion) {
		this.idTipoNSSAclaracion = idTipoNSSAclaracion;
	}

	public String getDesTipoNSSAclaracion() {
		return desTipoNSSAclaracion;
	}

	public void setDesTipoNSSAclaracion(String desTipoNSSAclaracion) {
		this.desTipoNSSAclaracion = desTipoNSSAclaracion;
	}

	public Long getIdTipoCertificacion() {
		return idTipoCertificacion;
	}

	public void setIdTipoCertificacion(Long idTipoCertificacion) {
		this.idTipoCertificacion = idTipoCertificacion;
	}
	
	

}
