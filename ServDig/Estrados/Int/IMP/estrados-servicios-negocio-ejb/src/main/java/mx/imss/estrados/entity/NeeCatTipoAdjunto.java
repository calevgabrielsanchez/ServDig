package mx.imss.estrados.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NEE_CAT_TIPO_ADJUNTO")
public class NeeCatTipoAdjunto {
	
	public NeeCatTipoAdjunto() {
	}
	
	@Id
	@Column(name="CVE_TIPO_ADJUNTO")
	private Integer cveTipoAdjunto;
	
	@Column(name="DES_TIPO_ADJUNTO")
	private String desTipoAdjunto;

	public Integer getCveTipoAdjunto() {
		return cveTipoAdjunto;
	}

	public void setCveTipoAdjunto(Integer cveTipoAdjunto) {
		this.cveTipoAdjunto = cveTipoAdjunto;
	}

	public String getDesTipoAdjunto() {
		return desTipoAdjunto;
	}

	public void setDesTipoAdjunto(String desTipoAdjunto) {
		this.desTipoAdjunto = desTipoAdjunto;
	}

}
