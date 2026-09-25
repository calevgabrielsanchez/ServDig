package mx.gob.imss.ctirss.delta.model.gestion.seguro;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;

/**
 * Entity DitSeguroIvro
 * 
 * @author IMSS
 *
 */
public class SeguroIvro extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private long cveIdSeguroIvro;
	private Persona titular;
	private Date fechaInicio;	
	private Date fechaFin;
	private EstadoSeguroIvro estadoSeguro;
	private Modalidad modalidad;	
	private Date fechaBaja;
	private Date fechaActualizado;
	private Date fechaAlta;
	
	
	public SeguroIvro(){
		super();
	}
	
	
	public long getCveIdSeguroIvro() {
		return cveIdSeguroIvro;
	}
	public void setCveIdSeguroIvro(long cveIdSeguroIvro) {
		this.cveIdSeguroIvro = cveIdSeguroIvro;
	}
	public Persona getTitular() {
		return titular;
	}
	public void setTitular(Persona titular) {
		this.titular = titular;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public EstadoSeguroIvro getEstadoSeguro() {
		return estadoSeguro;
	}
	public void setEstadoSeguro(EstadoSeguroIvro estadoSeguro) {
		this.estadoSeguro = estadoSeguro;
	}
	public Modalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public Date getFechaActualizado() {
		return fechaActualizado;
	}
	public void setFechaActualizado(Date fechaActualizado) {
		this.fechaActualizado = fechaActualizado;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
}
