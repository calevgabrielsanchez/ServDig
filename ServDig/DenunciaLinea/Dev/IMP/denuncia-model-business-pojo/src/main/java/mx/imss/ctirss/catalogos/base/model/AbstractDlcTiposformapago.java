package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.catalogos.model.DlcTipoconcepto;
import mx.imss.ctirss.catalogos.model.DlcTiposformapago;
import mx.imss.ctirss.framework.base.model.AbstractModel;

import java.math.BigDecimal;


/**
 * The persistent class for the DLC_TIPOSFORMAPAGO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcTiposformapago extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_FORMAPAGO")
	private Long cveFormapago;

	@Column(name="CVE_CONCEPTO")
	private BigDecimal cveConcepto;

	@Column(name="DESC_FORMAPAGO")
	private String descFormapago;
	
	//bi-directional many-to-one association to DlcTipoconcepto
 /*   @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_CONCEPTO", insertable=false, updatable=false)
	private DlcTipoconcepto dlcTipoconcepto;
*/
	    
    public AbstractDlcTiposformapago() {
    }

	public Long getCveFormapago() {
		return this.cveFormapago;
	}

	public void setCveFormapago(Long cveFormapago) {
		this.cveFormapago = cveFormapago;
	}

	public BigDecimal getCveConcepto() {
		return this.cveConcepto;
	}

	public void setCveConcepto(BigDecimal cveConcepto) {
		this.cveConcepto = cveConcepto;
	}

	public String getDescFormapago() {
		return this.descFormapago;
	}

	public void setDescFormapago(String descFormapago) {
		this.descFormapago = descFormapago;
	}

	
}