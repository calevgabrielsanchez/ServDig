package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcDelegacion;


/**
 * The persistent class for the CRC_SUBDELEGACION database table.
 * 
 */
@MappedSuperclass
@IdClass(CrcSubdelegacionPK.class)
public class AbstractCrcSubdelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_DELEG_ORIG")
	private BigDecimal cveDelegOrig;

	@Id
	@Column(name="SDELEG_ORIG")
	private BigDecimal sdelegOrig;

	@Column(name="SDELEG_DESC")
	private String sdelegDesc;

	@Column(name="TX_DOMICILIO")
	private String txDomicilio;

	@Column(name="TX_NOMBRE_DEPTO")
	private String txNombreDepto;

	@Column(name="TX_NOMBRE_TIT")
	private String txNombreTit;

	//bi-directional many-to-one association to CrcDelegacion
    @ManyToOne
	@JoinColumn(name="CVE_DELEG_ORIG",insertable = false, updatable = false)
	private CrcDelegacion crcDelegacion;

    public AbstractCrcSubdelegacion() {
    }

    public BigDecimal getCveDelegOrig() {
		return this.cveDelegOrig;
	}
	public void setCveDelegOrig(BigDecimal cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}
	public BigDecimal getSdelegOrig() {
		return this.sdelegOrig;
	}
	public void setSdelegOrig(BigDecimal sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}
	
	public String getSdelegDesc() {
		return this.sdelegDesc;
	}

	public void setSdelegDesc(String sdelegDesc) {
		this.sdelegDesc = sdelegDesc;
	}

	public String getTxDomicilio() {
		return this.txDomicilio;
	}

	public void setTxDomicilio(String txDomicilio) {
		this.txDomicilio = txDomicilio;
	}

	public String getTxNombreDepto() {
		return this.txNombreDepto;
	}

	public void setTxNombreDepto(String txNombreDepto) {
		this.txNombreDepto = txNombreDepto;
	}

	public String getTxNombreTit() {
		return this.txNombreTit;
	}

	public void setTxNombreTit(String txNombreTit) {
		this.txNombreTit = txNombreTit;
	}

	public CrcDelegacion getCrcDelegacion() {
		return this.crcDelegacion;
	}

	public void setCrcDelegacion(CrcDelegacion crcDelegacion) {
		this.crcDelegacion = crcDelegacion;
	}
	
}