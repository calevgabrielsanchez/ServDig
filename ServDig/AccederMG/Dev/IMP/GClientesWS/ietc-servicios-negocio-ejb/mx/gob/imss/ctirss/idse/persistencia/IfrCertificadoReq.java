package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the IFR_CERTIFICADO_REQ database table.
 * 
 */
@Entity
@Table(name="IFR_CERTIFICADO_REQ")
@NamedQuery(name="IfrCertificadoReq.findAll", query="SELECT i FROM IfrCertificadoReq i")
public class IfrCertificadoReq implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IfrCertificadoReqPK id;

	//bi-directional one-to-one association to IfrCartasIdseFiel
	@OneToOne(mappedBy="ifrCertificadoReq")
	private IfrCartasIdseFiel ifrCartasIdseFiel;

	//bi-directional many-to-one association to IftDatosCertificado
	@ManyToOne
	@JoinColumn(name="CVE_SERIAL")
	private IftDatosCertificado iftDatosCertificado;

	//bi-directional many-to-one association to IftRegistrosPatronale
	@OneToMany(mappedBy="ifrCertificadoReq")
	private List<IftRegistrosPatronale> iftRegistrosPatronales;

	public IfrCertificadoReq() {
	}

	public IfrCertificadoReqPK getId() {
		return this.id;
	}

	public void setId(IfrCertificadoReqPK id) {
		this.id = id;
	}

	public IfrCartasIdseFiel getIfrCartasIdseFiel() {
		return this.ifrCartasIdseFiel;
	}

	public void setIfrCartasIdseFiel(IfrCartasIdseFiel ifrCartasIdseFiel) {
		this.ifrCartasIdseFiel = ifrCartasIdseFiel;
	}

	public IftDatosCertificado getIftDatosCertificado() {
		return this.iftDatosCertificado;
	}

	public void setIftDatosCertificado(IftDatosCertificado iftDatosCertificado) {
		this.iftDatosCertificado = iftDatosCertificado;
	}

	public List<IftRegistrosPatronale> getIftRegistrosPatronales() {
		return this.iftRegistrosPatronales;
	}

	public void setIftRegistrosPatronales(List<IftRegistrosPatronale> iftRegistrosPatronales) {
		this.iftRegistrosPatronales = iftRegistrosPatronales;
	}

	public IftRegistrosPatronale addIftRegistrosPatronale(IftRegistrosPatronale iftRegistrosPatronale) {
		getIftRegistrosPatronales().add(iftRegistrosPatronale);
		iftRegistrosPatronale.setIfrCertificadoReq(this);

		return iftRegistrosPatronale;
	}

	public IftRegistrosPatronale removeIftRegistrosPatronale(IftRegistrosPatronale iftRegistrosPatronale) {
		getIftRegistrosPatronales().remove(iftRegistrosPatronale);
		iftRegistrosPatronale.setIfrCertificadoReq(null);

		return iftRegistrosPatronale;
	}

}