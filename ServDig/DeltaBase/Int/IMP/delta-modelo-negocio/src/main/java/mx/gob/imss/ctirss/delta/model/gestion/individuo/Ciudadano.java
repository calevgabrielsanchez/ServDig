package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;

import org.apache.commons.lang.StringUtils;

public class Ciudadano implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long cveIdPersona;
	private Long cveIdFisica;
	private Long cveIdAsingacionNSS;
	private String curp;
	private String strNss;	
	private String rfc;

	private String nombreCompleto;
	
	public Long getCveIdPersona() {
		return cveIdPersona;
	}
	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}
	public Long getCveIdFisica() {
		return cveIdFisica;
	}
	public void setCveIdFisica(Long cveIdFisica) {
		this.cveIdFisica = cveIdFisica;
	}
	public Long getCveIdAsingacionNSS() {
		return cveIdAsingacionNSS;
	}
	public void setCveIdAsingacionNSS(Long cveIdAsingacionNSS) {
		this.cveIdAsingacionNSS = cveIdAsingacionNSS;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getStrNss() {
		return strNss;
	}
	public void setStrNss(String strNss) {
		this.strNss = strNss;
	}
	public String getNssCifrado() {
		String nssCifrado = null;
		if(StringUtils.isNotEmpty(this.strNss) && StringUtils.isNotBlank(this.strNss) ){
			try {
				nssCifrado = Base64Cipher.cifrar(this.strNss);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return nssCifrado;
	}

	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

}
