package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class VTramitePensionNssPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_NSS")
	private String idNss;

	@Column(name = "CVE_ID_TRAMITE")
	private String cveIdTramite;
	
    public VTramitePensionNssPK() {
    }

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}

	public String getCveIdTramite() {
		return this.cveIdTramite;
	}

	public void setCveIdTramite(String cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}
}
