package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObraSirocInput implements Serializable {

	private static final long serialVersionUID = 1L;

	// Nuevos parametros entrada
	private String anioFiscal;
	private String idDelegacion;
	private String idSubdelegacion;
	private String idClaseObra;
	private String idTipoPatron;
	private String idEstatusObra;
	private String idTipoIncidencia;
	private String estatusObra;
	private String numObra;
	public String getCampoAproximacion() {
		return campoAproximacion;
	}

	public void setCampoAproximacion(String campoAproximacion) {
		this.campoAproximacion = campoAproximacion;
	}

	private String campoAproximacion;
	
	public String getEstatusObra() {
		return estatusObra;
	}

	public void setEstatusObra(String estatusObra) {
		this.estatusObra = estatusObra;
	}


	// Parametros de salida

	public String getAnioFiscal() {
		return anioFiscal;
	}

	public void setAnioFiscal(String anioFiscal) {
		this.anioFiscal = anioFiscal;
	}

	public String getIdDelegacion() {
		return idDelegacion;
	}

	public void setIdDelegacion(String idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	public String getIdSubdelegacion() {
		return idSubdelegacion;
	}

	public void setIdSubdelegacion(String idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}

	public String getIdClaseObra() {
		return idClaseObra;
	}

	public void setIdClaseObra(String idClaseObra) {
		this.idClaseObra = idClaseObra;
	}

	public String getIdTipoPatron() {
		return idTipoPatron;
	}

	public void setIdTipoPatron(String idTipoPatron) {
		this.idTipoPatron = idTipoPatron;
	}

	public String getIdEstatusObra() {
		return idEstatusObra;
	}

	public void setIdEstatusObra(String idEstatusObra) {
		this.idEstatusObra = idEstatusObra;
	}

	public String getIdTipoIncidencia() {
		return idTipoIncidencia;
	}

	public void setIdTipoIncidencia(String idTipoIncidencia) {
		this.idTipoIncidencia = idTipoIncidencia;
	}

	public String getNumObra() {
		return numObra;
	}

	public void setNumObra(String numObra) {
		this.numObra = numObra;
	}

}
