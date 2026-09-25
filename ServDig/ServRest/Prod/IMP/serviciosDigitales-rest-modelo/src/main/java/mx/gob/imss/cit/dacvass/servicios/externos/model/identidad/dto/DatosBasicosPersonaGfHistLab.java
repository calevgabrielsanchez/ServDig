package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.AseguradoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

@XmlRootElement
public class DatosBasicosPersonaGfHistLab implements Serializable {

	private static final long serialVersionUID = 4362588416487077568L;
	
	private BigDecimal cveIdPersona;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String refCurp;
	private String refRfc;
	private String numNssPersona;
	private BigDecimal cveIdAsignacionNss;
	private String clavePresupuestal;
	private String cveUmf;
	private String cveDelegacion;
	private String tipoPension;
	private String calidad;
	private String estadoDerechohabiente;
	private Date fechaUltimoMovAfiliacion;
	private String tipoMvtoAsegurado;
	private String registroPatronal;
	private String derechoServicioMedico;
	private String nomDelegacion;
	private String nomUmf;
	private Date fechaAltaAsegurado;
	private BigDecimal domicilioId;
	private Domicilio domicilio;
	
	
	
	public BigDecimal getDomicilioId() {
		return domicilioId;
	}
	public void setDomicilioId(BigDecimal domicilioId) {
		this.domicilioId = domicilioId;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public Date getFechaAltaAsegurado() {
		return fechaAltaAsegurado;
	}
	public void setFechaAltaAsegurado(Date fechaAltaAsegurado) {
		this.fechaAltaAsegurado = fechaAltaAsegurado;
	}
	public String getNomDelegacion() {
		return nomDelegacion;
	}
	public void setNomDelegacion(String nomDelegacion) {
		this.nomDelegacion = nomDelegacion;
	}
	public String getNomUmf() {
		return nomUmf;
	}
	public void setNomUmf(String nomUmf) {
		this.nomUmf = nomUmf;
	}
	public String getDerechoServicioMedico() {
		return derechoServicioMedico;
	}
	public void setDerechoServicioMedico(String derechoServicioMedico) {
		this.derechoServicioMedico = derechoServicioMedico;
	}
	//private CabezaGrupoFamiliar cabezaGf;
	private List<AseguradoCuentaIndividual> lstCuentaIndividual;
	
	
	
	
	
	
	public String getCalidad() {
		return calidad;
	}
	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}
	public String getEstadoDerechohabiente() {
		return estadoDerechohabiente;
	}
	public void setEstadoDerechohabiente(String estadoDerechohabiente) {
		this.estadoDerechohabiente = estadoDerechohabiente;
	}
	public Date getFechaUltimoMovAfiliacion() {
		return fechaUltimoMovAfiliacion;
	}
	public void setFechaUltimoMovAfiliacion(Date fechaUltimoMovAfiliacion) {
		this.fechaUltimoMovAfiliacion = fechaUltimoMovAfiliacion;
	}
	public String getTipoMvtoAsegurado() {
		return tipoMvtoAsegurado;
	}
	public void setTipoMvtoAsegurado(String tipoMvtoAsegurado) {
		this.tipoMvtoAsegurado = tipoMvtoAsegurado;
	}
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public List<AseguradoCuentaIndividual> getLstCuentaIndividual() {
		return lstCuentaIndividual;
	}
	public void setLstCuentaIndividual(List<AseguradoCuentaIndividual> lstCuentaIndividual) {
		this.lstCuentaIndividual = lstCuentaIndividual;
	}
	public BigDecimal getCveIdPersona() {
		return cveIdPersona;
	}
	public void setCveIdPersona(BigDecimal cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getRefCurp() {
		return refCurp;
	}
	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}
	public String getRefRfc() {
		return refRfc;
	}
	public void setRefRfc(String refRfc) {
		this.refRfc = refRfc;
	}
	public String getNumNssPersona() {
		return numNssPersona;
	}
	public void setNumNssPersona(String numNssPersona) {
		this.numNssPersona = numNssPersona;
	}
	public BigDecimal getCveIdAsignacionNss() {
		return cveIdAsignacionNss;
	}
	public void setCveIdAsignacionNss(BigDecimal cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}
	public String getClavePresupuestal() {
		return clavePresupuestal;
	}
	public void setClavePresupuestal(String clavePresupuestal) {
		this.clavePresupuestal = clavePresupuestal;
	}
	public String getCveUmf() {
		return cveUmf;
	}
	public void setCveUmf(String cveUmf) {
		this.cveUmf = cveUmf;
	}
	public String getCveDelegacion() {
		return cveDelegacion;
	}
	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public String getTipoPension() {
		return tipoPension;
	}
	public void setTipoPension(String tipoPension) {
		this.tipoPension = tipoPension;
	}
	
	
	
	
	}