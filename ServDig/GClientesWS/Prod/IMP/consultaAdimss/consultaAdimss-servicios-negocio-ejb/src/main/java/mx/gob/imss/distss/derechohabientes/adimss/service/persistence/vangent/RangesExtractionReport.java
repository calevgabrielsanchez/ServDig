package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RANGES_EXTRACTION_REPORT database table.
 * 
 */
@Entity
@Table(name="RANGES_EXTRACTION_REPORT")
@NamedQuery(name="RangesExtractionReport.findAll", query="SELECT r FROM RangesExtractionReport r")
public class RangesExtractionReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idranges;

	private BigDecimal bdcadimssenrolments;

	private BigDecimal bdcadimssfingerprints;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal emptyenrolments;

	private BigDecimal emptyfingerprints;

	private BigDecimal extractedenrolments;

	private BigDecimal extractedfingerprints;

	private BigDecimal notfoundenrolments;

	private BigDecimal notfoundfingerprints;

	private BigDecimal notreferenceenrolments;

	private BigDecimal notreferencefingerprints;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public RangesExtractionReport() {
	}

	public long getIdranges() {
		return this.idranges;
	}

	public void setIdranges(long idranges) {
		this.idranges = idranges;
	}

	public BigDecimal getBdcadimssenrolments() {
		return this.bdcadimssenrolments;
	}

	public void setBdcadimssenrolments(BigDecimal bdcadimssenrolments) {
		this.bdcadimssenrolments = bdcadimssenrolments;
	}

	public BigDecimal getBdcadimssfingerprints() {
		return this.bdcadimssfingerprints;
	}

	public void setBdcadimssfingerprints(BigDecimal bdcadimssfingerprints) {
		this.bdcadimssfingerprints = bdcadimssfingerprints;
	}

	public BigDecimal getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getEmptyenrolments() {
		return this.emptyenrolments;
	}

	public void setEmptyenrolments(BigDecimal emptyenrolments) {
		this.emptyenrolments = emptyenrolments;
	}

	public BigDecimal getEmptyfingerprints() {
		return this.emptyfingerprints;
	}

	public void setEmptyfingerprints(BigDecimal emptyfingerprints) {
		this.emptyfingerprints = emptyfingerprints;
	}

	public BigDecimal getExtractedenrolments() {
		return this.extractedenrolments;
	}

	public void setExtractedenrolments(BigDecimal extractedenrolments) {
		this.extractedenrolments = extractedenrolments;
	}

	public BigDecimal getExtractedfingerprints() {
		return this.extractedfingerprints;
	}

	public void setExtractedfingerprints(BigDecimal extractedfingerprints) {
		this.extractedfingerprints = extractedfingerprints;
	}

	public BigDecimal getNotfoundenrolments() {
		return this.notfoundenrolments;
	}

	public void setNotfoundenrolments(BigDecimal notfoundenrolments) {
		this.notfoundenrolments = notfoundenrolments;
	}

	public BigDecimal getNotfoundfingerprints() {
		return this.notfoundfingerprints;
	}

	public void setNotfoundfingerprints(BigDecimal notfoundfingerprints) {
		this.notfoundfingerprints = notfoundfingerprints;
	}

	public BigDecimal getNotreferenceenrolments() {
		return this.notreferenceenrolments;
	}

	public void setNotreferenceenrolments(BigDecimal notreferenceenrolments) {
		this.notreferenceenrolments = notreferenceenrolments;
	}

	public BigDecimal getNotreferencefingerprints() {
		return this.notreferencefingerprints;
	}

	public void setNotreferencefingerprints(BigDecimal notreferencefingerprints) {
		this.notreferencefingerprints = notreferencefingerprints;
	}

	public BigDecimal getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

}