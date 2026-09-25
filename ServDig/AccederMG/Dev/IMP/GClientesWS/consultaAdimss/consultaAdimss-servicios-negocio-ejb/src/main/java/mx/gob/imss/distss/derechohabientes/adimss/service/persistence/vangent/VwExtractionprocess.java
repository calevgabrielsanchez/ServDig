package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the VW_EXTRACTIONPROCESS database table.
 * 
 */
@Embeddable
@Table(name="VW_EXTRACTIONPROCESS")
@NamedQuery(name="VwExtractionprocess.findAll", query="SELECT v FROM VwExtractionprocess v")
public class VwExtractionprocess implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal extraidas;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name="HUELLA_STATUS")
	private String huellaStatus;

	private BigDecimal idenrol;

	private BigDecimal idprocessjobdetail;

	@Column(name="TARGET_IMSS")
	private BigDecimal targetImss;

	public VwExtractionprocess() {
	}

	public BigDecimal getExtraidas() {
		return this.extraidas;
	}

	public void setExtraidas(BigDecimal extraidas) {
		this.extraidas = extraidas;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHuellaStatus() {
		return this.huellaStatus;
	}

	public void setHuellaStatus(String huellaStatus) {
		this.huellaStatus = huellaStatus;
	}

	public BigDecimal getIdenrol() {
		return this.idenrol;
	}

	public void setIdenrol(BigDecimal idenrol) {
		this.idenrol = idenrol;
	}

	public BigDecimal getIdprocessjobdetail() {
		return this.idprocessjobdetail;
	}

	public void setIdprocessjobdetail(BigDecimal idprocessjobdetail) {
		this.idprocessjobdetail = idprocessjobdetail;
	}

	public BigDecimal getTargetImss() {
		return this.targetImss;
	}

	public void setTargetImss(BigDecimal targetImss) {
		this.targetImss = targetImss;
	}

}