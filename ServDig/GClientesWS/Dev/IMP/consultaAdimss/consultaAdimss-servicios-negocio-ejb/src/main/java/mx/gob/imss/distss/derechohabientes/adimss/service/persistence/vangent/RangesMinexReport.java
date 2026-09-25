package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RANGES_MINEX_REPORT database table.
 * 
 */
@Entity
@Table(name="RANGES_MINEX_REPORT")
@NamedQuery(name="RangesMinexReport.findAll", query="SELECT r FROM RangesMinexReport r")
public class RangesMinexReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idranges;

	private BigDecimal bdcadimssenrolments;

	private BigDecimal bdcadimssfingerprints;

	private BigDecimal codificatedfingerprints;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal emptyenrolments;

	private BigDecimal emptyfingerprints;

	private BigDecimal extractedenrolments;

	private BigDecimal extractedenrolnotcompleted;

	private BigDecimal extractedfingerprints;

	private BigDecimal loadedenrolments;

	private BigDecimal loadedenrolnotcompleted;

	private BigDecimal loadedfingerprints;

	private BigDecimal loadedprocessedenrolments;

	private BigDecimal notfoundenrolments;

	private BigDecimal notfoundfingerprints;

	private BigDecimal notreferenceenrolments;

	private BigDecimal notreferencefingerprints;

	private BigDecimal processedenrolments;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private BigDecimal withoutqualityfingerprints;

	public RangesMinexReport() {
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

	public BigDecimal getCodificatedfingerprints() {
		return this.codificatedfingerprints;
	}

	public void setCodificatedfingerprints(BigDecimal codificatedfingerprints) {
		this.codificatedfingerprints = codificatedfingerprints;
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

	public BigDecimal getExtractedenrolnotcompleted() {
		return this.extractedenrolnotcompleted;
	}

	public void setExtractedenrolnotcompleted(BigDecimal extractedenrolnotcompleted) {
		this.extractedenrolnotcompleted = extractedenrolnotcompleted;
	}

	public BigDecimal getExtractedfingerprints() {
		return this.extractedfingerprints;
	}

	public void setExtractedfingerprints(BigDecimal extractedfingerprints) {
		this.extractedfingerprints = extractedfingerprints;
	}

	public BigDecimal getLoadedenrolments() {
		return this.loadedenrolments;
	}

	public void setLoadedenrolments(BigDecimal loadedenrolments) {
		this.loadedenrolments = loadedenrolments;
	}

	public BigDecimal getLoadedenrolnotcompleted() {
		return this.loadedenrolnotcompleted;
	}

	public void setLoadedenrolnotcompleted(BigDecimal loadedenrolnotcompleted) {
		this.loadedenrolnotcompleted = loadedenrolnotcompleted;
	}

	public BigDecimal getLoadedfingerprints() {
		return this.loadedfingerprints;
	}

	public void setLoadedfingerprints(BigDecimal loadedfingerprints) {
		this.loadedfingerprints = loadedfingerprints;
	}

	public BigDecimal getLoadedprocessedenrolments() {
		return this.loadedprocessedenrolments;
	}

	public void setLoadedprocessedenrolments(BigDecimal loadedprocessedenrolments) {
		this.loadedprocessedenrolments = loadedprocessedenrolments;
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

	public BigDecimal getProcessedenrolments() {
		return this.processedenrolments;
	}

	public void setProcessedenrolments(BigDecimal processedenrolments) {
		this.processedenrolments = processedenrolments;
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

	public BigDecimal getWithoutqualityfingerprints() {
		return this.withoutqualityfingerprints;
	}

	public void setWithoutqualityfingerprints(BigDecimal withoutqualityfingerprints) {
		this.withoutqualityfingerprints = withoutqualityfingerprints;
	}

}