package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcSubDelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import java.util.Set;


/**
 * The persistent class for the CRC_DELEGACION database table.
 * 
 */
@MappedSuperclass
public class AbstractCrcDelegacion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_DELEG_ORIG")
	private BigDecimal cveDelegOrig;

	@Column(name="DELEG_DESC")
	private String delegDesc;

	@Column(name="TX_DOMICILIO")
	private String txDomicilio;

	@Column(name="TX_NOMBRE_TIT")
	private String txNombreTit;

	@Column(name="ENT_FED")
	private BigDecimal EntFed;
	//bi-directional many-to-one association to CrcEntFed
//    @ManyToOne
//	@JoinColumn(name="ENT_FED")
//	private CrcEntFed crcEntFed;
//
//	//bi-directional many-to-one association to CrcSubdelegacion
	@OneToMany(mappedBy="crcDelegacion")
	private Set<CrcSubDelegacion> crcSubdelegacions;

    public AbstractCrcDelegacion() {
    }

	public BigDecimal getCveDelegOrig() {
		return this.cveDelegOrig;
	}

	public void setCveDelegOrig(BigDecimal cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}

	public String getDelegDesc() {
		return this.delegDesc;
	}

	public void setDelegDesc(String delegDesc) {
		this.delegDesc = delegDesc;
	}

	public String getTxDomicilio() {
		return this.txDomicilio;
	}

	public void setTxDomicilio(String txDomicilio) {
		this.txDomicilio = txDomicilio;
	}

	public String getTxNombreTit() {
		return this.txNombreTit;
	}

	public void setTxNombreTit(String txNombreTit) {
		this.txNombreTit = txNombreTit;
	}

	public BigDecimal getEntFed() {
		return EntFed;
	}

	public void setEntFed(BigDecimal entFed) {
		EntFed = entFed;
	}

//	public CrcEntFed getCrcEntFed() {
//		return this.crcEntFed;
//	}
//
//	public void setCrcEntFed(CrcEntFed crcEntFed) {
//		this.crcEntFed = crcEntFed;
//	}
//	
	public Set<CrcSubDelegacion> getCrcSubdelegacions() {
		return this.crcSubdelegacions;
	}

	public void setCrcSubdelegacions(Set<CrcSubDelegacion> crcSubdelegacions) {
		this.crcSubdelegacions = crcSubdelegacions;
	}
	
}