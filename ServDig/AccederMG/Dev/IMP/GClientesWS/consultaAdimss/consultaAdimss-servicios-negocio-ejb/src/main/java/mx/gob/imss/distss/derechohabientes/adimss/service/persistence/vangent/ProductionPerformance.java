package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRODUCTION_PERFORMANCE database table.
 * 
 */
@Embeddable
@Table(name="PRODUCTION_PERFORMANCE")
@NamedQuery(name="ProductionPerformance.findAll", query="SELECT p FROM ProductionPerformance p")
public class ProductionPerformance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CAPACITY_CE")
	private BigDecimal capacityCe;

	@Column(name="DAY_STATUS")
	private BigDecimal dayStatus;

	@Column(name="FORECAST_IMSS")
	private BigDecimal forecastImss;

	@Column(name="FORECAST_VANGENT")
	private BigDecimal forecastVangent;

	@Temporal(TemporalType.DATE)
	@Column(name="PRODUCTION_DATE")
	private Date productionDate;

	@Column(name="PRODUCTION_DAY_NAME")
	private String productionDayName;

	@Column(name="PRODUCTION_DAY_WEEK")
	private String productionDayWeek;

	@Column(name="PRODUCTION_WEEK")
	private String productionWeek;

	@Column(name="PRODUCTION_YEAR")
	private String productionYear;

	@Column(name="REAL_CE")
	private BigDecimal realCe;

	@Column(name="REAL_PRODUCTION")
	private BigDecimal realProduction;

	@Column(name="SLA_1")
	private BigDecimal sla1;

	@Column(name="SLA_6")
	private BigDecimal sla6;

	public ProductionPerformance() {
	}

	public BigDecimal getCapacityCe() {
		return this.capacityCe;
	}

	public void setCapacityCe(BigDecimal capacityCe) {
		this.capacityCe = capacityCe;
	}

	public BigDecimal getDayStatus() {
		return this.dayStatus;
	}

	public void setDayStatus(BigDecimal dayStatus) {
		this.dayStatus = dayStatus;
	}

	public BigDecimal getForecastImss() {
		return this.forecastImss;
	}

	public void setForecastImss(BigDecimal forecastImss) {
		this.forecastImss = forecastImss;
	}

	public BigDecimal getForecastVangent() {
		return this.forecastVangent;
	}

	public void setForecastVangent(BigDecimal forecastVangent) {
		this.forecastVangent = forecastVangent;
	}

	public Date getProductionDate() {
		return this.productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public String getProductionDayName() {
		return this.productionDayName;
	}

	public void setProductionDayName(String productionDayName) {
		this.productionDayName = productionDayName;
	}

	public String getProductionDayWeek() {
		return this.productionDayWeek;
	}

	public void setProductionDayWeek(String productionDayWeek) {
		this.productionDayWeek = productionDayWeek;
	}

	public String getProductionWeek() {
		return this.productionWeek;
	}

	public void setProductionWeek(String productionWeek) {
		this.productionWeek = productionWeek;
	}

	public String getProductionYear() {
		return this.productionYear;
	}

	public void setProductionYear(String productionYear) {
		this.productionYear = productionYear;
	}

	public BigDecimal getRealCe() {
		return this.realCe;
	}

	public void setRealCe(BigDecimal realCe) {
		this.realCe = realCe;
	}

	public BigDecimal getRealProduction() {
		return this.realProduction;
	}

	public void setRealProduction(BigDecimal realProduction) {
		this.realProduction = realProduction;
	}

	public BigDecimal getSla1() {
		return this.sla1;
	}

	public void setSla1(BigDecimal sla1) {
		this.sla1 = sla1;
	}

	public BigDecimal getSla6() {
		return this.sla6;
	}

	public void setSla6(BigDecimal sla6) {
		this.sla6 = sla6;
	}

}