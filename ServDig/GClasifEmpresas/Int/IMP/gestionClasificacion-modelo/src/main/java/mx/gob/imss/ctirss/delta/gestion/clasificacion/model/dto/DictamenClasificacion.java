package mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DictamenClasificacion extends AbstractModel {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 154009046915199848L;

	private long cveIdClasificacion;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private BigDecimal indDistribucionEntrega;
	private BigDecimal indEvaluada;
	private BigDecimal indPrestaServicioPersonal;
	private BigDecimal indRegPatClase;
	private BigDecimal indServicioOtrasPersonas;
	private BigDecimal indTransporteAjeno;
	private BigDecimal indTransportePropio;
	private String manifestacion;
	private BigDecimal numCentrosTraba;
	private long cveIdFraccionClase;
	private long cveIdPatronSujetoObligado;
	private BigDecimal numPrimaPago;
	
	@Override
	public String toString() {
		return "ClasificacionDtm [cveIdClasificacion=" + cveIdClasificacion
				+ ", fecRegistroActualizado=" + fecRegistroActualizado
				+ ", fecRegistroAlta=" + fecRegistroAlta + ", fecRegistroBaja="
				+ fecRegistroBaja + ", indDistribucionEntrega="
				+ indDistribucionEntrega + ", indEvaluada=" + indEvaluada
				+ ", indPrestaServicioPersonal=" + indPrestaServicioPersonal
				+ ", indRegPatClase=" + indRegPatClase
				+ ", indServicioOtrasPersonas=" + indServicioOtrasPersonas
				+ ", indTransporteAjeno=" + indTransporteAjeno
				+ ", indTransportePropio=" + indTransportePropio
				+ ", manifestacion=" + manifestacion + ", numCentrosTraba="
				+ numCentrosTraba + ", cveIdFraccionClase="
				+ cveIdFraccionClase + ", cveIdPatronSujetoObligado="
				+ cveIdPatronSujetoObligado + ", numPrimaPago=" + numPrimaPago
				+ "]";
	}
	public long getCveIdClasificacion() {
		return cveIdClasificacion;
	}
	public void setCveIdClasificacion(long cveIdClasificacion) {
		this.cveIdClasificacion = cveIdClasificacion;
	}
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	public BigDecimal getIndDistribucionEntrega() {
		return indDistribucionEntrega;
	}
	public void setIndDistribucionEntrega(BigDecimal indDistribucionEntrega) {
		this.indDistribucionEntrega = indDistribucionEntrega;
	}
	public BigDecimal getIndEvaluada() {
		return indEvaluada;
	}
	public void setIndEvaluada(BigDecimal indEvaluada) {
		this.indEvaluada = indEvaluada;
	}
	public BigDecimal getIndPrestaServicioPersonal() {
		return indPrestaServicioPersonal;
	}
	public void setIndPrestaServicioPersonal(BigDecimal indPrestaServicioPersonal) {
		this.indPrestaServicioPersonal = indPrestaServicioPersonal;
	}
	public BigDecimal getIndRegPatClase() {
		return indRegPatClase;
	}
	public void setIndRegPatClase(BigDecimal indRegPatClase) {
		this.indRegPatClase = indRegPatClase;
	}
	public BigDecimal getIndServicioOtrasPersonas() {
		return indServicioOtrasPersonas;
	}
	public void setIndServicioOtrasPersonas(BigDecimal indServicioOtrasPersonas) {
		this.indServicioOtrasPersonas = indServicioOtrasPersonas;
	}
	public BigDecimal getIndTransporteAjeno() {
		return indTransporteAjeno;
	}
	public void setIndTransporteAjeno(BigDecimal indTransporteAjeno) {
		this.indTransporteAjeno = indTransporteAjeno;
	}
	public BigDecimal getIndTransportePropio() {
		return indTransportePropio;
	}
	public void setIndTransportePropio(BigDecimal indTransportePropio) {
		this.indTransportePropio = indTransportePropio;
	}
	public String getManifestacion() {
		return manifestacion;
	}
	public void setManifestacion(String manifestacion) {
		this.manifestacion = manifestacion;
	}
	public BigDecimal getNumCentrosTraba() {
		return numCentrosTraba;
	}
	public void setNumCentrosTraba(BigDecimal numCentrosTraba) {
		this.numCentrosTraba = numCentrosTraba;
	}
	public long getCveIdFraccionClase() {
		return cveIdFraccionClase;
	}
	public void setCveIdFraccionClase(long cveIdFraccionClase) {
		this.cveIdFraccionClase = cveIdFraccionClase;
	}
	public long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}
	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}
	public BigDecimal getNumPrimaPago() {
		return numPrimaPago;
	}
	public void setNumPrimaPago(BigDecimal numPrimaPago) {
		this.numPrimaPago = numPrimaPago;
	}
	
}
