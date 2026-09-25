package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CorreccionNSS extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String nss;
	private CertificacionNSS certificacionNSS;

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public CertificacionNSS getCertificacionNSS() {
		return certificacionNSS;
	}

	public void setCertificacionNSS(CertificacionNSS certificacionNSS) {
		this.certificacionNSS = certificacionNSS;
	}

}
