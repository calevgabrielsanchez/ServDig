package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Tramite;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

@XmlRootElement
public class CircunscripcionForanea extends Tramite{

	private static final long serialVersionUID = 1L;
	private Date fecInicioCircunscripcion;
	private Date fecFinCircunscripcion;
	private Integer indCircunscripcionActiva;
	private Domicilio domicilioOrigen;
	private Domicilio domicilioDestino;
	private MedicoEnTurno medicoEnTurnoOrigen;
	private MedicoEnTurno medicoEnTurnoDestino;
	private Tramite tramiteSuspension;
	
	public Date getFecInicioCircunscripcion() {
		return fecInicioCircunscripcion;
	}
	
	public void setFecInicioCircunscripcion(Date fecInicioCircunscripcion) {
		this.fecInicioCircunscripcion = fecInicioCircunscripcion;
	}
	
	public Date getFecFinCircunscripcion() {
		return fecFinCircunscripcion;
	}
	
	public void setFecFinCircunscripcion(Date fecFinCircunscripcion) {
		this.fecFinCircunscripcion = fecFinCircunscripcion;
	}
	
	public Integer getIndCircunscripcionActiva() {
		return indCircunscripcionActiva;
	}
	
	public void setIndCircunscripcionActiva(Integer indCircunscripcionActiva) {
		this.indCircunscripcionActiva = indCircunscripcionActiva;
	}
	
	public Domicilio getDomicilioOrigen() {
		return domicilioOrigen;
	}
	
	public void setDomicilioOrigen(Domicilio domicilioOrigen) {
		this.domicilioOrigen = domicilioOrigen;
	}
	
	public Domicilio getDomicilioDestino() {
		return domicilioDestino;
	}
	
	public void setDomicilioDestino(Domicilio domicilioDestino) {
		this.domicilioDestino = domicilioDestino;
	}
	
	public MedicoEnTurno getMedicoEnTurnoOrigen() {
		return medicoEnTurnoOrigen;
	}
	
	public void setMedicoEnTurnoOrigen(MedicoEnTurno medicoEnTurnoOrigen) {
		this.medicoEnTurnoOrigen = medicoEnTurnoOrigen;
	}
	
	public MedicoEnTurno getMedicoEnTurnoDestino() {
		return medicoEnTurnoDestino;
	}
	
	public void setMedicoEnTurnoDestino(MedicoEnTurno medicoEnTurnoDestino) {
		this.medicoEnTurnoDestino = medicoEnTurnoDestino;
	}
	
	public Tramite getTramiteSuspension() {
		return tramiteSuspension;
	}
	
	public void setTramiteSuspension(Tramite tramiteSuspension) {
		this.tramiteSuspension = tramiteSuspension;
	}
	
}
