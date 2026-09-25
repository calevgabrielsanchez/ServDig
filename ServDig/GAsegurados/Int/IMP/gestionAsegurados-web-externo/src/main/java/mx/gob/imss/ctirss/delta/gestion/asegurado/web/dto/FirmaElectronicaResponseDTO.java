package mx.gob.imss.ctirss.delta.gestion.asegurado.web.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;


public class FirmaElectronicaResponseDTO extends AbstractModel {
	
	private String token;
    private int resultado;
    private String texto;
    private String vigIni;
    private String vigFin;
    private String rfc;
    private String serie_cert;
    private String curp;

    // Getters y Setters
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getVigIni() {
		return vigIni;
	}

	public void setVigIni(String vigIni) {
		this.vigIni = vigIni;
	}

	public String getVigFin() {
		return vigFin;
	}

	public void setVigFin(String vigFin) {
		this.vigFin = vigFin;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	public String getSerie_cert() {
		return serie_cert;
	}

	public void setSerie_cert(String serie_cert) {
		this.serie_cert = serie_cert;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}
	

}
