package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_TIPODOCUMENTO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcTipodocumento extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_TIPODOCUMENTO")
	private Long cveTipodocumento;

	@Column(name="DES_DOCUMENTO")
	private String desDocumento;

    public AbstractDlcTipodocumento() {
    }

	public Long getCveTipodocumento() {
		return this.cveTipodocumento;
	}

	public void setCveTipodocumento(Long cveTipodocumento) {
		this.cveTipodocumento = cveTipodocumento;
	}

	public String getDesDocumento() {
		return this.desDocumento;
	}

	public void setDesDocumento(String desDocumento) {
		this.desDocumento = desDocumento;
	}

}