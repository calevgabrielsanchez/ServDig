package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_TIPODENUNCIANTE database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcTipodenunciante extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_TIPODENUNCIANTE", nullable = false, unique = true)
	private Long cveTipodenunciante;

	@Column(name="DES_DENUNCIANTE")
	private String desDenunciante;

    public AbstractDlcTipodenunciante() {
    }

	public Long getCveTipodenunciante() {
		return this.cveTipodenunciante;
	}

	public void setCveTipodenunciante(Long cveTipodenunciante) {
		this.cveTipodenunciante = cveTipodenunciante;
	}

	public String getDesDenunciante() {
		return this.desDenunciante;
	}

	public void setDesDenunciante(String desDenunciante) {
		this.desDenunciante = desDenunciante;
	}

}