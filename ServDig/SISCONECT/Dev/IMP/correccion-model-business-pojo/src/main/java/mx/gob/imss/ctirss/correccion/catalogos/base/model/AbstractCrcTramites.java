package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


@MappedSuperclass
public class AbstractCrcTramites  extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_TRAMITE")	
	private Long cveTramite;
	
	@Column(name="DES_TRAMITE")	
	private String desTramite;

	
	public Long getCveTramite() {
		return cveTramite;
	}

	public void setCveTramite(Long cveTramite) {
		this.cveTramite = cveTramite;
	}

	public String getDesTramite() {
		return desTramite;
	}

	public void setDesTramite(String desTramite) {
		this.desTramite = desTramite;
	}
	
	
	
}