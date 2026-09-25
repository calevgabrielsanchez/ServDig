package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TramiteAsegurado extends Tramite implements Serializable {

	private static final long serialVersionUID = -6071257856836528619L;

	@XmlElement(name = "asignacionNss")
	private AsignacionNSS fisica;
	private AsignacionSerieNSS asignacionSerieNss;
	private Serie serie;
	
	/*
	 * Atributo para poder guardar en un trámite de asegurados, la información
	 * del ICA
	 */
	private ICADatosRespuesta icaDatosRespuesta;
	
	private String curpRENAPO;
	private SolicitudNssCorreo nssCorreo;
	private String origenAsegurado;

	public AsignacionNSS getFisica() {
		return fisica;
	}

	public void setFisica(final AsignacionNSS fisica) {
		this.fisica = fisica;
	}

	public AsignacionSerieNSS getAsignacionSerieNss() {
		return asignacionSerieNss;
	}

	public void setAsignacionSerieNss(AsignacionSerieNSS asignacionSerieNss) {
		this.asignacionSerieNss = asignacionSerieNss;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public ICADatosRespuesta getIcaDatosRespuesta() {
		return icaDatosRespuesta;
	}

	public void setIcaDatosRespuesta(ICADatosRespuesta icaDatosRespuesta) {
		this.icaDatosRespuesta = icaDatosRespuesta;
	}

	public String getCurpRENAPO() {
		return curpRENAPO;
	}

	public void setCurpRENAPO(String curpRENAPO) {
		this.curpRENAPO = curpRENAPO;
	}

	public SolicitudNssCorreo getNssCorreo() {
		return nssCorreo;
	}

	public void setNssCorreo(SolicitudNssCorreo nssCorreo) {
		this.nssCorreo = nssCorreo;
	}

	public String getOrigenAsegurado() {
		return origenAsegurado;
	}

	public void setOrigenAsegurado(String origenAsegurado) {
		this.origenAsegurado = origenAsegurado;
	}
}
