package mx.gob.imss.digital.modelo.sindo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultadoVigenciaSeguroFamiliar", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "resultadoVigenciaSeguroFamiliar", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class ResultadoVigenciaSeguroFamiliar implements Serializable{
	private static final long serialVersionUID = 2260026736468904384L;
	private String estadoVigencia;
	private ModalidadTrabajador[] listModVigentes;
	private String indPension;
	private String indTrabajadorIMSS;
	private String fecUltimaBajaObligatorio;
	private String fecUltimaBajaMod33;
	private String semanasCotizadas;

	public String getEstadoVigencia() {
		return estadoVigencia;
	}

	public void setEstadoVigencia(String estadoVigencia) {
		this.estadoVigencia = estadoVigencia;
	}

	public ModalidadTrabajador[] getListModVigentes() {
		return listModVigentes;
	}

	public void setListModVigentes(ModalidadTrabajador[] listModVigentes) {
		this.listModVigentes = listModVigentes != null ? listModVigentes.clone() : null;
	}

	public String getIndPension() {
		return indPension;
	}

	public void setIndPension(String indPension) {
		this.indPension = indPension;
	}

	public String getIndTrabajadorIMSS() {
		return indTrabajadorIMSS;
	}

	public void setIndTrabajadorIMSS(String indTrabajadorIMSS) {
		this.indTrabajadorIMSS = indTrabajadorIMSS;
	}

	public String getFecUltimaBajaObligatorio() {
		return fecUltimaBajaObligatorio;
	}

	public void setFecUltimaBajaObligatorio(String fecUltimaBajaObligatorio) {
		this.fecUltimaBajaObligatorio = fecUltimaBajaObligatorio;
	}

	public String getFecUltimaBajaMod33() {
		return fecUltimaBajaMod33;
	}

	public void setFecUltimaBajaMod33(String fecUltimaBajaMod33) {
		this.fecUltimaBajaMod33 = fecUltimaBajaMod33;
	}

	public String getSemanasCotizadas() {
		return semanasCotizadas;
	}

	public void setSemanasCotizadas(String semanasCotizadas) {
		this.semanasCotizadas = semanasCotizadas;
	}

}
