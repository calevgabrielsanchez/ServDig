package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;

@XmlRootElement
public class DatosBasicosPersonaGrupoFamiliar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6065362673754162720L;
	
	private BigDecimal cveIdPersona;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String refCurp;
	private String numNssPersona;
	private BigDecimal cveIdAsignacionNss;
	private String refIDEE;
	private String refAgregadoMedico;
	private String refAgregadoAfiliacion;
	private BigDecimal cveIdParentescoGpoFamilar;
	private String descParentescoGpoFamilar;
	private String numNssGrpoFamiliar;
	private BigDecimal cveIdAsignacionNssGrupo;
	private EstadoDerechohabiente estadoDerechohabiente;
	private String derechoServicioMedico;
	private String clavePresupuestal;
	private String nombreUnidad;
	
	
	
	public String getDerechoServicioMedico() {
		return derechoServicioMedico;
	}
	public void setDerechoServicioMedico(String derechoServicioMedico) {
		this.derechoServicioMedico = derechoServicioMedico;
	}
	public BigDecimal getCveIdAsignacionNssGrupo() {
		return cveIdAsignacionNssGrupo;
	}
	public void setCveIdAsignacionNssGrupo(BigDecimal cveIdAsignacionNssGrp) {
		this.cveIdAsignacionNssGrupo = cveIdAsignacionNssGrp;
	}

	public EstadoDerechohabiente getEstadoDerechohabiente() {
		return estadoDerechohabiente;
	}
	public void setEstadoDerechohabiente(EstadoDerechohabiente estadoDerechohabiente) {
		this.estadoDerechohabiente = estadoDerechohabiente;
	}
	public BigDecimal getCveIdAsignacionNss() {
		return cveIdAsignacionNss;
	}
	public void setCveIdAsignacionNss(BigDecimal cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}
	public BigDecimal getCveIdPersona() 
	{
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
	public String getNumNssPersona() {
		return numNssPersona;
	}
	public void setNumNssPersona(String numNssPersona) {
		this.numNssPersona = numNssPersona;
	}
	public String getRefIDEE() {
		return refIDEE;
	}
	public void setRefIDEE(String refIDEE) {
		this.refIDEE = refIDEE;
	}
	public String getRefAgregadoMedico() {
		return refAgregadoMedico;
	}
	public void setRefAgregadoMedico(String refAgregadoMedico) {
		this.refAgregadoMedico = refAgregadoMedico;
	}
	public String getRefAgregadoAfiliacion() {
		return refAgregadoAfiliacion;
	}
	public void setRefAgregadoAfiliacion(String refAgregadoAfiliacion) {
		this.refAgregadoAfiliacion = refAgregadoAfiliacion;
	}
	public String getNumNssGrpoFamiliar() {
		return numNssGrpoFamiliar;
	}
	public void setNumNssGrpoFamiliar(String numNssGrpoFamiliar) {
		this.numNssGrpoFamiliar = numNssGrpoFamiliar;
	}
	public BigDecimal getCveIdParentescoGpoFamilar() {
		return cveIdParentescoGpoFamilar;
	}
	public void setCveIdParentescoGpoFamilar(BigDecimal cveIdParentescoGpoFamilar) {
		this.cveIdParentescoGpoFamilar = cveIdParentescoGpoFamilar;
	}
	public String getDescParentescoGpoFamilar() {
		return descParentescoGpoFamilar;
	}
	public void setDescParentescoGpoFamilar(String descParentescoGpoFamilar) {
		this.descParentescoGpoFamilar = descParentescoGpoFamilar;
	}
	public String getClavePresupuestal() {
		return clavePresupuestal;
	}
	public void setClavePresupuestal(String clavePresupuestal) {
		this.clavePresupuestal = clavePresupuestal;
	}
	public String getNombreUnidad() {
		return nombreUnidad;
	}
	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}
	
	
	
}
