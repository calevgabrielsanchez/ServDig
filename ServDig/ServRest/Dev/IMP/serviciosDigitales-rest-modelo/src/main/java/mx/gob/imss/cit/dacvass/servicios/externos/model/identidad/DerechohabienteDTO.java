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
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;


@XmlRootElement
public class DerechohabienteDTO extends Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2205788007005452539L;
	
	private Domicilio domicilio;
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
	private Subdelegacion subdelegacion;
	
	
	
	
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
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


	
}
