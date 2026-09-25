package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;

/**
 * @author ghdolores
 * 
 */
public class ComprobanteVigenciaDerechosDTO  extends AseguradoDTO implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String detalleSituacion;
	private String delegacion;
	private String umf;
	private String modalidadPatron;
	private Date ultimoMovimiento;
	private Date fechaExpedicion;
	private String tipoMovimiento;
	private List<BeneficiarioDTO> beneficiarios;
	private List<ServiciosDTO> servicios;
	private List<PatronDTO> patrones;
	private String bandera;
	private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaNotarial;
	private String numeroSerie;
	private String nombreUsuario;
	private String delegacionUsuario;
	private String unidadUsuario;
	private Date fechaValidezConstancia;
	private String nombrePatron;
	private List<PrestacionesDTO> prestaciones;
	private String mensajeBeneficiarios;
	private EstadoDerechohabiente estadoDerechohabiente;
	private SubEstadoDerechohabiente subEstadoDerechohabiente;
	private Date fechaInicioVigencia;
	private Date fechaFinVigencia;
	private boolean modalidad17;
	private boolean patron17ConServicios;
	private boolean modalidad32;
	
	
	
	public boolean isModalidad32() {
		return modalidad32;
	}

	public void setModalidad32(boolean modalidad32) {
		this.modalidad32 = modalidad32;
	}

	public List<PrestacionesDTO> getPrestaciones() {
		return prestaciones;
	}

	public void setPrestaciones(List<PrestacionesDTO> prestaciones) {
		this.prestaciones = prestaciones;
	}

	public List<PatronDTO> getPatrones() {
		return patrones;
	}

	public void setPatrones(List<PatronDTO> patrones) {
		this.patrones = patrones;
	}

	public String getDetalleSituacion() {
		return detalleSituacion;
	}

	public void setDetalleSituacion(String detalleSituacion) {
		this.detalleSituacion = detalleSituacion;
	}

	public String getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}

	public String getUmf() {
		return umf;
	}

	public void setUmf(String umf) {
		this.umf = umf;
	}

	public List<BeneficiarioDTO> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<BeneficiarioDTO> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public String getModalidadPatron() {
		return modalidadPatron;
	}

	public void setModalidadPatron(String modalidadPatron) {
		this.modalidadPatron = modalidadPatron;
	}

	public Date getUltimoMovimiento() {
		return ultimoMovimiento;
	}

	public void setUltimoMovimiento(Date ultimoMovimiento) {
		this.ultimoMovimiento = ultimoMovimiento;
	}

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public List<ServiciosDTO> getServicios() {
		return servicios;
	}

	public void setServicios(List<ServiciosDTO> servicios) {
		this.servicios = servicios;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getBandera() {
		return bandera;
	}

	public void setBandera(String bandera) {
		this.bandera = bandera;
	}

	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public String getSelloDigital() {
		return selloDigital;
	}

	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}

	public String getSecuenciaNotarial() {
		return secuenciaNotarial;
	}

	public void setSecuenciaNotarial(String secuenciaNotarial) {
		this.secuenciaNotarial = secuenciaNotarial;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getDelegacionUsuario() {
		return delegacionUsuario;
	}

	public void setDelegacionUsuario(String delegacionUsuario) {
		this.delegacionUsuario = delegacionUsuario;
	}

	public String getUnidadUsuario() {
		return unidadUsuario;
	}

	public void setUnidadUsuario(String unidadUsuario) {
		this.unidadUsuario = unidadUsuario;
	}

	public Date getFechaValidezConstancia() {
		return fechaValidezConstancia;
	}

	public void setFechaValidezConstancia(Date fechaValidezConstancia) {
		this.fechaValidezConstancia = fechaValidezConstancia;
	}

	public String getNombrePatron() {
		return nombrePatron;
	}

	public void setNombrePatron(String nombrePatron) {
		this.nombrePatron = nombrePatron;
	}

	public String getMensajeBeneficiarios() {
		return mensajeBeneficiarios;
	}

	public void setMensajeBeneficiarios(String mensajeBeneficiarios) {
		this.mensajeBeneficiarios = mensajeBeneficiarios;
	}

	public EstadoDerechohabiente getEstadoDerechohabiente() {
		return estadoDerechohabiente;
	}

	public void setEstadoDerechohabiente(EstadoDerechohabiente estadoDerechohabiente) {
		this.estadoDerechohabiente = estadoDerechohabiente;
	}

	public SubEstadoDerechohabiente getSubEstadoDerechohabiente() {
		return subEstadoDerechohabiente;
	}

	public void setSubEstadoDerechohabiente(SubEstadoDerechohabiente subEstadoDerechohabiente) {
		this.subEstadoDerechohabiente = subEstadoDerechohabiente;
	}

	public Date getFechaInicioVigencia() {
		return fechaInicioVigencia;
	}

	public void setFechaInicioVigencia(Date fechaInicioVigencia) {
		this.fechaInicioVigencia = fechaInicioVigencia;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public boolean getModalidad17() {
		return modalidad17;
	}

	public void setModalidad17(boolean modalidad17) {
		this.modalidad17 = modalidad17;
	}

	public boolean getPatron17ConServicios() {
		return patron17ConServicios;
	}

	public void setPatron17ConServicios(boolean patron17ConServicios) {
		this.patron17ConServicios = patron17ConServicios;
	}
	
}