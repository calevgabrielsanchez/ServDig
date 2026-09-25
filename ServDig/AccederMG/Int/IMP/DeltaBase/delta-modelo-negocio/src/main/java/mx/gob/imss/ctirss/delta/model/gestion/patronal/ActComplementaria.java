package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ActComplementaria extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal indNodistribuye;
	private BigDecimal indPrestaserv;
	private BigDecimal indRpclase;
	private BigDecimal indTransporteajeno;
	private BigDecimal indTransportepropio;
	private BigDecimal nuCentrostrajo;
//	private FdtRegistroPeriodo fdtRegistroPeriodo;
	
	private Long cveActcomple;
	public Long getCveActcomple() {
		return cveActcomple;
	}
	public void setCveActcomple(Long cveActcomple) {
		this.cveActcomple = cveActcomple;
	}
	public BigDecimal getIndNodistribuye() {
		return indNodistribuye;
	}
	public void setIndNodistribuye(BigDecimal indNodistribuye) {
		this.indNodistribuye = indNodistribuye;
	}
	public BigDecimal getIndPrestaserv() {
		return indPrestaserv;
	}
	public void setIndPrestaserv(BigDecimal indPrestaserv) {
		this.indPrestaserv = indPrestaserv;
	}
	public BigDecimal getIndRpclase() {
		return indRpclase;
	}
	public void setIndRpclase(BigDecimal indRpclase) {
		this.indRpclase = indRpclase;
	}
	public BigDecimal getIndTransporteajeno() {
		return indTransporteajeno;
	}
	public void setIndTransporteajeno(BigDecimal indTransporteajeno) {
		this.indTransporteajeno = indTransporteajeno;
	}
	public BigDecimal getIndTransportepropio() {
		return indTransportepropio;
	}
	public void setIndTransportepropio(BigDecimal indTransportepropio) {
		this.indTransportepropio = indTransportepropio;
	}
	public BigDecimal getNuCentrostrajo() {
		return nuCentrostrajo;
	}
	public void setNuCentrostrajo(BigDecimal nuCentrostrajo) {
		this.nuCentrostrajo = nuCentrostrajo;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActComplementaria [indNodistribuye=");
		builder.append(indNodistribuye);
		builder.append(", indPrestaserv=");
		builder.append(indPrestaserv);
		builder.append(", indRpclase=");
		builder.append(indRpclase);
		builder.append(", indTransporteajeno=");
		builder.append(indTransporteajeno);
		builder.append(", indTransportepropio=");
		builder.append(indTransportepropio);
		builder.append(", nuCentrostrajo=");
		builder.append(nuCentrostrajo);
		builder.append(", cveActcomple=");
		builder.append(cveActcomple);
		builder.append("]");
		return builder.toString();
	}
	
}
