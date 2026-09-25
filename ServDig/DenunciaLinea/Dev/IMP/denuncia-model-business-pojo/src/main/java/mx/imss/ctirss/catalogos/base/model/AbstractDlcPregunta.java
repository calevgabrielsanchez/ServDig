package mx.imss.ctirss.catalogos.base.model;



import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_STATUS database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcPregunta extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_PREGUNTA")
	private long cvePregunta;

	@Column(name="DES_PREGUNTA")
	private String desPregunta;

    public AbstractDlcPregunta() {
    }

	public long getCvePregunta() {
		return this.cvePregunta;
	}

	public void setCvePregunta(long cvePregunta) {
		this.cvePregunta = cvePregunta;
	}

	public String getDesPregunta() {
		return this.desPregunta;
	}

	public void setDesPregunta(String desPregunta) {
		this.desPregunta = desPregunta;
	}

}