package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.model.*;

import mx.imss.ctirss.catalogos.model.DlcTiposformapago;
import mx.imss.ctirss.framework.annotations.IgnoreAtributosEnCriteria;
import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_TIPOCONCEPTO database table.
 * 
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDlcTipoconcepto extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_CONCEPTO")
	private Long cveConcepto;

	@Column(name="DES_CONCEPTO")
	private String desConcepto;

	//bi-directional many-to-one association to DltFormapago
	@OneToMany(mappedBy="dlcTipoconcepto", fetch=FetchType.LAZY)
	private Set<DltFormapago> dltFormapagos;

	
	public Long getCveConcepto() {
		return this.cveConcepto;
	}

	public void setCveConcepto(Long cveConcepto) {
		this.cveConcepto = cveConcepto;
	}

	public String getDesConcepto() {
		return this.desConcepto;
	}

	public void setDesConcepto(String desConcepto) {
		this.desConcepto = desConcepto;
	}

	@JsonIgnore
	public Set<DltFormapago> getDltFormapagos() {
		return this.dltFormapagos;
	}

	public void setDltFormapagos(Set<DltFormapago> dltFormapagos) {
		this.dltFormapagos = dltFormapagos;
	}

	/*public Set<DlcTiposformapago> getDlcTiposFormapagos() {
		return dlcTiposFormapagos;
	}

	public void setDlcTiposFormapagos(Set<DlcTiposformapago> dlcTiposFormapagos) {
		this.dlcTiposFormapagos = dlcTiposFormapagos;
	}
	*/
	
	
}