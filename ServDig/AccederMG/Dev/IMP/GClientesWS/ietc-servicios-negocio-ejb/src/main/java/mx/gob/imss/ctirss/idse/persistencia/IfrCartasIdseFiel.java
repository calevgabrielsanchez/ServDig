package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the IFR_CARTAS_IDSE_FIEL database table.
 * 
 */
@Entity
@Table(name="IFR_CARTAS_IDSE_FIEL")
@NamedQuery(name="IfrCartasIdseFiel.findAll", query="SELECT i FROM IfrCartasIdseFiel i")
public class IfrCartasIdseFiel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IfrCartasIdseFielPK id;

	private String digestion;

	private BigDecimal nofolio;

	@Column(name="SEQ_FIRMA_CARTA")
	private BigDecimal seqFirmaCarta;

	@Lob
	@Column(name="TIP_CARTA_CONDICIONES")
	private byte[] tipCartaCondiciones;

	@Lob
	@Column(name="TIP_CARTA_SINCRONIZACION")
	private byte[] tipCartaSincronizacion;

	@Lob
	@Column(name="TIP_CARTA_SUBDELEGACION")
	private byte[] tipCartaSubdelegacion;

	//bi-directional one-to-one association to IfrCertificadoReq
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_REQUERIMIENTO", referencedColumnName="CVE_REQUERIMIENTO"),
		@JoinColumn(name="CVE_SERIAL", referencedColumnName="CVE_SERIAL")
		})
	private IfrCertificadoReq ifrCertificadoReq;

	public IfrCartasIdseFiel() {
	}

	public IfrCartasIdseFielPK getId() {
		return this.id;
	}

	public void setId(IfrCartasIdseFielPK id) {
		this.id = id;
	}

	public String getDigestion() {
		return this.digestion;
	}

	public void setDigestion(String digestion) {
		this.digestion = digestion;
	}

	public BigDecimal getNofolio() {
		return this.nofolio;
	}

	public void setNofolio(BigDecimal nofolio) {
		this.nofolio = nofolio;
	}

	public BigDecimal getSeqFirmaCarta() {
		return this.seqFirmaCarta;
	}

	public void setSeqFirmaCarta(BigDecimal seqFirmaCarta) {
		this.seqFirmaCarta = seqFirmaCarta;
	}

	public byte[] getTipCartaCondiciones() {
		return this.tipCartaCondiciones;
	}

	public void setTipCartaCondiciones(byte[] tipCartaCondiciones) {
		this.tipCartaCondiciones = tipCartaCondiciones != null ? tipCartaCondiciones.clone() : null;
	}

	public byte[] getTipCartaSincronizacion() {
		return this.tipCartaSincronizacion;
	}

	public void setTipCartaSincronizacion(byte[] tipCartaSincronizacion) {
		this.tipCartaSincronizacion = tipCartaSincronizacion != null ? tipCartaSincronizacion.clone() : null;
	}

	public byte[] getTipCartaSubdelegacion() {
		return this.tipCartaSubdelegacion;
	}

	public void setTipCartaSubdelegacion(byte[] tipCartaSubdelegacion) {
		this.tipCartaSubdelegacion = tipCartaSubdelegacion != null ? tipCartaSubdelegacion.clone() : null;
	}

	public IfrCertificadoReq getIfrCertificadoReq() {
		return this.ifrCertificadoReq;
	}

	public void setIfrCertificadoReq(IfrCertificadoReq ifrCertificadoReq) {
		this.ifrCertificadoReq = ifrCertificadoReq;
	}

}