package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@XmlRootElement
public class TramiteBajaDerechohabiente extends Tramite {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9221152732408475308L;
	private Long idAsignacionNSS;
	private Fisica fisica;
	private Date fechaDefuncion;
	private String observaciones;
	private String matricula;
	private String fundamentoLegal;
	private String motivo;
	private EstadoDerechohabiente estadoDerechohabiente;
	
	public TramiteBajaDerechohabiente(){
		
	}
	
	public TramiteBajaDerechohabiente(Long tramiteId){
		super(tramiteId);
	}
	
	public Long getIdAsignacionNSS() {
		return idAsignacionNSS;
	}

	public void setIdAsignacionNSS(Long idAsignacionNSS) {
		this.idAsignacionNSS = idAsignacionNSS;
	}

	public Fisica getFisica() {
		return fisica;
	}
	
	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}
	
	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}
	
	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}
	
	public String getObservaciones() {
		return observaciones;
	}
	
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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