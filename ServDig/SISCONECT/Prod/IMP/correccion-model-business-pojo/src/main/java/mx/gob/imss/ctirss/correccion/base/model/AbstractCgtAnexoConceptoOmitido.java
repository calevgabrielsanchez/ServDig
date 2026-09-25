/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public abstract class AbstractCgtAnexoConceptoOmitido extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public AbstractCgtAnexoConceptoOmitidoPK id;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG")
	private Date fecFechareg;

	public AbstractCgtAnexoConceptoOmitido() {
	}

	public AbstractCgtAnexoConceptoOmitidoPK getId() {
		return this.id;
	}

	public void setId(AbstractCgtAnexoConceptoOmitidoPK id) {
		this.id = id;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

}