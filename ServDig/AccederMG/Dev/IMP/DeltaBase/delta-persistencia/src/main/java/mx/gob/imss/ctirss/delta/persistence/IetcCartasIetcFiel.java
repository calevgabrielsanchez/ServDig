package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the IETC_CARTAS_IETC_FIEL database table.
 * 
 */
@Entity
@Table(name="IETC_CARTAS_IETC_FIEL")
public class IetcCartasIetcFiel implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IetcCartasIetcFielPK id;

    @Lob()
	@Column(name="CARTA_CONDICIONES")
	private byte[] cartaCondiciones;

    @Lob()
	@Column(name="CARTA_SINCRONIZACION")
	private byte[] cartaSincronizacion;

    @Lob()
	@Column(name="CARTA_SUBDELEGACION")
	private byte[] cartaSubdelegacion;

	@Column(length=20)
	private String digestion;

	@Column(name="FIRMA_CARTA", precision=22)
	private BigDecimal firmaCarta;

	@Column(precision=20)
	private BigDecimal nofolio;

	//bi-directional many-to-one association to IetcDatosCertificado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_REGCONTADOR", referencedColumnName="CVE_REGCONTADOR", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_SERIAL", referencedColumnName="CVE_SERIAL", nullable=false, insertable=false, updatable=false)
		})
	private IetcDatosCertificado ietcDatosCertificado;

    public IetcCartasIetcFiel() {
    }

	public IetcCartasIetcFielPK getId() {
		return this.id;
	}

	public void setId(IetcCartasIetcFielPK id) {
		this.id = id;
	}
	
	public byte[] getCartaCondiciones() {
		return this.cartaCondiciones;
	}

	public void setCartaCondiciones(byte[] cartaCondiciones) {
		this.cartaCondiciones = cartaCondiciones != null ? cartaCondiciones.clone() : null;
	}

	public byte[] getCartaSincronizacion() {
		return this.cartaSincronizacion;
	}

	public void setCartaSincronizacion(byte[] cartaSincronizacion) {
		this.cartaSincronizacion = cartaSincronizacion != null ? cartaSincronizacion.clone() : null;
	}

	public byte[] getCartaSubdelegacion() {
		return this.cartaSubdelegacion;
	}

	public void setCartaSubdelegacion(byte[] cartaSubdelegacion) {
		this.cartaSubdelegacion = cartaSubdelegacion != null ? cartaSubdelegacion.clone() : null;
	}

	public String getDigestion() {
		return this.digestion;
	}

	public void setDigestion(String digestion) {
		this.digestion = digestion;
	}

	public BigDecimal getFirmaCarta() {
		return this.firmaCarta;
	}

	public void setFirmaCarta(BigDecimal firmaCarta) {
		this.firmaCarta = firmaCarta;
	}

	public BigDecimal getNofolio() {
		return this.nofolio;
	}

	public void setNofolio(BigDecimal nofolio) {
		this.nofolio = nofolio;
	}

	public IetcDatosCertificado getIetcDatosCertificado() {
		return this.ietcDatosCertificado;
	}

	public void setIetcDatosCertificado(IetcDatosCertificado ietcDatosCertificado) {
		this.ietcDatosCertificado = ietcDatosCertificado;
	}
	
}