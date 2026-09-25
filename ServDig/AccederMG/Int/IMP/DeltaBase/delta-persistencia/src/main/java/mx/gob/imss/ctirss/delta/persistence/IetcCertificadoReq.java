package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the IETC_CERTIFICADO_REQ database table.
 * 
 */
@Entity
@Table(name="IETC_CERTIFICADO_REQ")
public class IetcCertificadoReq implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IetcCertificadoReqPK id;

	//bi-directional many-to-one association to IetcDatosCertificado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_REGCONTADOR", referencedColumnName="CVE_REGCONTADOR", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_SERIAL", referencedColumnName="CVE_SERIAL", nullable=false, insertable=false, updatable=false)
		})
	private IetcDatosCertificado ietcDatosCertificado;

    public IetcCertificadoReq() {
    }

	public IetcCertificadoReqPK getId() {
		return this.id;
	}

	public void setId(IetcCertificadoReqPK id) {
		this.id = id;
	}
	
	public IetcDatosCertificado getIetcDatosCertificado() {
		return this.ietcDatosCertificado;
	}

	public void setIetcDatosCertificado(IetcDatosCertificado ietcDatosCertificado) {
		this.ietcDatosCertificado = ietcDatosCertificado;
	}
	
}