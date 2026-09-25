/**
 * 
 */
package mx.gob.imss.digital.modelo.patron;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.domicilio.MunicipioIMSS;

/**
 * @author User
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registroPatronal", namespace = "http://mx.gob.imss.digital.modelo.patron")
@XmlRootElement(name = "registroPatronal", namespace = "http://mx.gob.imss.digital.modelo.patron")
public class RegistroPatronal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5300788451956959956L;
	
	private String numeroRegistroPatronal;
	private Modalidad modalidad;
	private String digitoVerificador;
	private MunicipioIMSS municipioImss;
	private Domicilio centrotrabajo;
	
	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}
	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
	}
	public Modalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	public String getDigitoVerificador() {
		return digitoVerificador;
	}
	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}
	public MunicipioIMSS getMunicipioImss() {
		return municipioImss;
	}
	public void setMunicipioImss(MunicipioIMSS municipioImss) {
		this.municipioImss = municipioImss;
	}
	public Domicilio getCentrotrabajo() {
		return centrotrabajo;
	}
	public void setCentrotrabajo(Domicilio centrotrabajo) {
		this.centrotrabajo = centrotrabajo;
	}
	
}	
