package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltMotivodenuncia;


/**
 * The persistent class for the DLC_MOTIVODENUNCIA database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcMotivodenuncia extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_MOTIVODENUNCIA")
	private Long cveMotivodenuncia;

	@Column(name="DES_MOTIVODENUNCIA")
	private String desMotivodenuncia;
	
	//bi-directional many-to-one association to DltMotivodenuncia
	@OneToMany(mappedBy="dlcMotivodenuncia",fetch=FetchType.EAGER)
	private Set<DltMotivodenuncia> dltMotivodenuncias;

    public AbstractDlcMotivodenuncia() {
    }

	public Long getCveMotivodenuncia() {
		return this.cveMotivodenuncia;
	}

	public void setCveMotivodenuncia(Long cveMotivodenuncia) {
		this.cveMotivodenuncia = cveMotivodenuncia;
	}

	public String getDesMotivodenuncia() {
		return this.desMotivodenuncia;
	}

	public void setDesMotivodenuncia(String desMotivodenuncia) {
		this.desMotivodenuncia = desMotivodenuncia;
	}

	@JsonIgnore
	public Set<DltMotivodenuncia> getDltMotivodenuncias() {
		return dltMotivodenuncias;
	}

	public void setDltMotivodenuncias(Set<DltMotivodenuncia> dltMotivodenuncias) {
		this.dltMotivodenuncias = dltMotivodenuncias;
	}

}