package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DitTramiteDictamenPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8983227965753568560L;
	@Column(name= "CVE_ID_PATRON_SUJETO_OBLIGADO",insertable=false,updatable=false)
	private Long cveIdPatronSujetoObligado;
	@Column(name= "CVE_ID_TRAMITE",insertable=false,updatable=false)
	private Long cveIdTramite;
	@Column(name= "CVE_ID_EJER_FISCAL",insertable=false,updatable=false)
	private Long cveIdEjercicioFiscal;
	public Long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}
	public void setCveIdPatronSujetoObligado(Long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}
	public Long getCveIdTramite() {
		return cveIdTramite;
	}
	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}
	public Long getCveIdEjercicioFiscal() {
		return cveIdEjercicioFiscal;
	}
	public void setCveIdEjercicioFiscal(Long cveIdEjercicioFiscal) {
		this.cveIdEjercicioFiscal = cveIdEjercicioFiscal;
	}
	
	
}
