package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Parentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Turno;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

@XmlRootElement
public class DerechohabienteSinolave extends Persona implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6618906748572748995L;
	
	private EstadoDerechohabiente estadoDerechohabiente;
	private SubEstadoDerechohabiente subEstadoDerechohabiente;
	private Parentesco parentesco;
	private Date fechaInicioVigencia;
	private Date fechaFinVigencia;
	private String agregadoMedico;
	private String agregadoAfiliacion;
	private UnidadMedicaFamiliar umf;
	private Long cveIdAsignacionNssGrupoFamiliar;
	private String nssCabezaGrupoFamiliar;
	private Turno turno;
	private String consultorio;
	private String idee;
	private Subdelegacion subdelegacion;
	private Integer edad;
	private Integer edadAnios;
	private Integer edadMeses;
	private Integer edadDias;
	private String registroPatronal;
	
	
	
	
	
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
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
	public Parentesco getParentesco() {
		return parentesco;
	}
	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
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
	public String getAgregadoMedico() {
		return agregadoMedico;
	}
	public void setAgregadoMedico(String agregadoMedico) {
		this.agregadoMedico = agregadoMedico;
	}
	public String getAgregadoAfiliacion() {
		return agregadoAfiliacion;
	}
	public void setAgregadoAfiliacion(String agregadoAfiliacion) {
		this.agregadoAfiliacion = agregadoAfiliacion;
	}
	public UnidadMedicaFamiliar getUmf() {
		return umf;
	}
	public void setUmf(UnidadMedicaFamiliar umf) {
		this.umf = umf;
	}
	public Long getCveIdAsignacionNssGrupoFamiliar() {
		return cveIdAsignacionNssGrupoFamiliar;
	}
	public void setCveIdAsignacionNssGrupoFamiliar(Long cveIdAsignacionNssGrupoFamiliar) {
		this.cveIdAsignacionNssGrupoFamiliar = cveIdAsignacionNssGrupoFamiliar;
	}
	public String getNssCabezaGrupoFamiliar() {
		return nssCabezaGrupoFamiliar;
	}
	public void setNssCabezaGrupoFamiliar(String nssCabezaGrupoFamiliar) {
		this.nssCabezaGrupoFamiliar = nssCabezaGrupoFamiliar;
	}
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public String getConsultorio() {
		return consultorio;
	}
	public void setConsultorio(String consultorio) {
		this.consultorio = consultorio;
	}
	public String getIdee() {
		return idee;
	}
	public void setIdee(String idee) {
		this.idee = idee;
	}
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public Integer getEdadAnios() {
		return edadAnios;
	}
	public void setEdadAnios(Integer edadAnios) {
		this.edadAnios = edadAnios;
	}
	public Integer getEdadMeses() {
		return edadMeses;
	}
	public void setEdadMeses(Integer edadMeses) {
		this.edadMeses = edadMeses;
	}
	public Integer getEdadDias() {
		return edadDias;
	}
	public void setEdadDias(Integer edadDias) {
		this.edadDias = edadDias;
	}
	

}
