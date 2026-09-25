package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;

@XmlRootElement
public class TramiteReactivacionDerechohab extends Tramite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5582901209802463152L;
	private Long idReactivacion;
	private Long idAsignacionNSS;
	private Long idBaja;
	private Date fechaReactivacion;
	private String motivo;
	private String matricula;
	private String fundamentoLegal;
	private EstadoDerechohabiente estadoDerechohabiente;
	
	public Long getIdReactivacion() {
		return idReactivacion;
	}
	public void setIdReactivacion(Long idReactivacion) {
		this.idReactivacion = idReactivacion;
	}
	public Long getIdAsignacionNSS() {
		return idAsignacionNSS;
	}
	public void setIdAsignacionNSS(Long idAsignacionNSS) {
		this.idAsignacionNSS = idAsignacionNSS;
	}
	public Long getIdBaja() {
		return idBaja;
	}
	public void setIdBaja(Long idBaja) {
		this.idBaja = idBaja;
	}
	public Date getFechaReactivacion() {
		return fechaReactivacion;
	}
	public void setFechaReactivacion(Date fechaReactivacion) {
		this.fechaReactivacion = fechaReactivacion;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getFundamentoLegal() {
		return fundamentoLegal;
	}
	public void setFundamentoLegal(String fundamentoLegal) {
		this.fundamentoLegal = fundamentoLegal;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public EstadoDerechohabiente getEstadoDerechohabiente() {
		return estadoDerechohabiente;
	}
	public void setEstadoDerechohabiente(EstadoDerechohabiente estadoDerechohabiente) {
		this.estadoDerechohabiente = estadoDerechohabiente;
	}
	
	
}