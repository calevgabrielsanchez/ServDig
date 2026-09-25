package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the FDT_FINHA database table.
 * 
 */
@Entity
@Table(name="FDT_FINHA")
public class FdtFinha implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_DELEG_ORIG", nullable=false, precision=2)
	private BigDecimal cveDelegOrig;

	@Column(length=255)
	private String descr;

    @Temporal( TemporalType.DATE)
	private Date fecha;

	@Column(name="ID_DIAIMSS", precision=22)
	private BigDecimal idDiaimss;

	@Column(name="ID_REGION", nullable=false, precision=22)
	private BigDecimal idRegion;

	@Column(name="SDELEG_ORIG", nullable=false, precision=2)
	private BigDecimal sdelegOrig;

    public FdtFinha() {
    }

	public BigDecimal getCveDelegOrig() {
		return this.cveDelegOrig;
	}

	public void setCveDelegOrig(BigDecimal cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getIdDiaimss() {
		return this.idDiaimss;
	}

	public void setIdDiaimss(BigDecimal idDiaimss) {
		this.idDiaimss = idDiaimss;
	}

	public BigDecimal getIdRegion() {
		return this.idRegion;
	}

	public void setIdRegion(BigDecimal idRegion) {
		this.idRegion = idRegion;
	}

	public BigDecimal getSdelegOrig() {
		return this.sdelegOrig;
	}

	public void setSdelegOrig(BigDecimal sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

}