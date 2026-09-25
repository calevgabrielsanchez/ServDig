package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@XmlRootElement
public class TramiteRiss extends Tramite implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Fisica fisica;	
	private List<Long> listaCveIdSujetosObligados;
	private String tipoApartadoCandidato;
	private List<DescuentoBeneficio> listaDescuentosBeneficio;
	private String motivoRechazo;
	private String rfcSolicitud;
	
	private RespuestaRifSat respuestaRifSat;
	private boolean respuestaInfonavit;
	private Integer idBeneficioPatronExistente; 
	
	public Fisica getFisica() {
        return fisica;
    }
    public void setFisica(final Fisica fisica) {
        this.fisica = fisica;
    }    
	public List<Long> getListaCveIdSujetosObligados() {
		return listaCveIdSujetosObligados;
	}
	public void setListaCveIdSujetosObligados(List<Long> listaCveIdSujetosObligados) {
		this.listaCveIdSujetosObligados = listaCveIdSujetosObligados;
	}
	public String getTipoApartadoCandidato() {
		return tipoApartadoCandidato;
	}
	public void setTipoApartadoCandidato(String tipoApartadoCandidato) {
		this.tipoApartadoCandidato = tipoApartadoCandidato;
	}
	public List<DescuentoBeneficio> getListaDescuentosBeneficio() {
		return listaDescuentosBeneficio;
	}
	public void setListaDescuentosBeneficio(
			List<DescuentoBeneficio> listaDescuentosBeneficio) {
		this.listaDescuentosBeneficio = listaDescuentosBeneficio;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	public String getRfcSolicitud() {
		return rfcSolicitud;
	}
	public void setRfcSolicitud(String rfcSolicitud) {
		this.rfcSolicitud = rfcSolicitud;
	}
	public RespuestaRifSat getRespuestaRifSat() {
		return respuestaRifSat;
	}
	public void setRespuestaRifSat(RespuestaRifSat respuestaRifSat) {
		this.respuestaRifSat = respuestaRifSat;
	}
	public boolean isRespuestaInfonavit() {
		return respuestaInfonavit;
	}
	public void setRespuestaInfonavit(boolean respuestaInfonavit) {
		this.respuestaInfonavit = respuestaInfonavit;
	} 
	public Integer getIdBeneficioPatronExistente() {
		return idBeneficioPatronExistente;
	}
	public void setIdBeneficioPatronExistente(Integer idBeneficioPatronExistente) {
		this.idBeneficioPatronExistente = idBeneficioPatronExistente;
	}
	
}
