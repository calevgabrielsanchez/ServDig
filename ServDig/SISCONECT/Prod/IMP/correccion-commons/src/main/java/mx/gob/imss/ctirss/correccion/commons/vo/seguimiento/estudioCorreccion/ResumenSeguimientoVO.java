package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.session.UserSession;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumenSeguimientoVO extends AbstractModel implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String domicilioFiscal;
	private String domicilioCentroTrabajo;
	private String domicilioObra;
	List<SatPatron> registrosPatronalesAsociados;
	
	//antecedentes
	private String origen;
	private String folioPrograma;
	private String fechaEmisionOficio;
	private String fechaNotificacionOficio;
	
	
	List<DetalleCOPSeguimientoVO> copPagadas;
	
	
	//solicitud de correccion
	private String fechaIngresoSolCorr;
	private String fechaAutoIngresoSolCorr;
	private String fechaSolProrroga;
	private String fechaAutoProrroga;
	private String fechaRechazoProrroga;
	private String fechaPresentacion;
	private String fechaAutoPresenta;
	private String fechaElaboSolCorr;
	
	
	private Integer numTrabajaRegular;
	
	private UserSession user;
	
	public String getDomicilioFiscal() {
		return domicilioFiscal;
	}
	public void setDomicilioFiscal(String domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}
	public String getDomicilioCentroTrabajo() {
		return domicilioCentroTrabajo;
	}
	public void setDomicilioCentroTrabajo(String domicilioCentroTrabajo) {
		this.domicilioCentroTrabajo = domicilioCentroTrabajo;
	}
	public String getDomicilioObra() {
		return domicilioObra;
	}
	public void setDomicilioObra(String domicilioObra) {
		this.domicilioObra = domicilioObra;
	}
	public List<SatPatron> getRegistrosPatronalesAsociados() {
		return registrosPatronalesAsociados;
	}
	public void setRegistrosPatronalesAsociados(
			List<SatPatron> registrosPatronalesAsociados) {
		this.registrosPatronalesAsociados = registrosPatronalesAsociados;
	}
	public List<DetalleCOPSeguimientoVO> getCopPagadas() {
		return copPagadas;
	}
	public void setCopPagadas(List<DetalleCOPSeguimientoVO> copPagadas) {
		this.copPagadas = copPagadas;
	}
	public Integer getNumTrabajaRegular() {
		return numTrabajaRegular;
	}
	public void setNumTrabajaRegular(Integer numTrabajaRegular) {
		this.numTrabajaRegular = numTrabajaRegular;
	}
	
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getFolioPrograma() {
		return folioPrograma;
	}
	public void setFolioPrograma(String folioPrograma) {
		this.folioPrograma = folioPrograma;
	}
	public String getFechaEmisionOficio() {
		return fechaEmisionOficio;
	}
	public void setFechaEmisionOficio(String fechaEmisionOficio) {
		this.fechaEmisionOficio = fechaEmisionOficio;
	}
	public String getFechaNotificacionOficio() {
		return fechaNotificacionOficio;
	}
	public void setFechaNotificacionOficio(String fechaNotificacionOficio) {
		this.fechaNotificacionOficio = fechaNotificacionOficio;
	}
	public String getFechaIngresoSolCorr() {
		return fechaIngresoSolCorr;
	}
	public void setFechaIngresoSolCorr(String fechaIngresoSolCorr) {
		this.fechaIngresoSolCorr = fechaIngresoSolCorr;
	}
	public String getFechaAutoIngresoSolCorr() {
		return fechaAutoIngresoSolCorr;
	}
	public void setFechaAutoIngresoSolCorr(String fechaAutoIngresoSolCorr) {
		this.fechaAutoIngresoSolCorr = fechaAutoIngresoSolCorr;
	}
	public String getFechaSolProrroga() {
		return fechaSolProrroga;
	}
	public void setFechaSolProrroga(String fechaSolProrroga) {
		this.fechaSolProrroga = fechaSolProrroga;
	}
	public String getFechaAutoProrroga() {
		return fechaAutoProrroga;
	}
	public void setFechaAutoProrroga(String fechaAutoProrroga) {
		this.fechaAutoProrroga = fechaAutoProrroga;
	}
	public String getFechaRechazoProrroga() {
		return fechaRechazoProrroga;
	}
	public void setFechaRechazoProrroga(String fechaRechazoProrroga) {
		this.fechaRechazoProrroga = fechaRechazoProrroga;
	}
	
	public String getFechaAutoPresenta() {
		return fechaAutoPresenta;
	}
	public void setFechaAutoPresenta(String fechaAutoPresenta) {
		this.fechaAutoPresenta = fechaAutoPresenta;
	}
	public String getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public UserSession getUser() {
		return user;
	}
	public void setUser(UserSession user) {
		this.user = user;
	}
	public String getFechaElaboSolCorr() {
		return fechaElaboSolCorr;
	}
	public void setFechaElaboSolCorr(String fechaElaboSolCorr) {
		this.fechaElaboSolCorr = fechaElaboSolCorr;
	}
	

}
