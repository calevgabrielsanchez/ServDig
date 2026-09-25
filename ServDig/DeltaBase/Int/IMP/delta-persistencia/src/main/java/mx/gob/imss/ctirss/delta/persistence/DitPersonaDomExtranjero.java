package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PERSONA_DOM_EXTRANJERO database table.
 * 
 */
@Entity
@Table(name="DIT_PERSONA_DOM_EXTRANJERO")
public class DitPersonaDomExtranjero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_DOM_PERSONA_EXTRANJERO", nullable=false, precision=22)
	private long cveIdDomPersonaExtranjero;

	@Column(length=100, name = "DES_CIUDAD")
	private String ciudad;

	@Column(name="DOMICILIO_EXTRANJ", length=100)
	private String domicilioExtranj;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(length=100, name = "DES_PAIS")
	private String pais;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA", nullable=false)
	private DitPersona ditPersona;

    public DitPersonaDomExtranjero() {
    }

	public long getCveIdDomPersonaExtranjero() {
		return this.cveIdDomPersonaExtranjero;
	}

	public void setCveIdDomPersonaExtranjero(long cveIdDomPersonaExtranjero) {
		this.cveIdDomPersonaExtranjero = cveIdDomPersonaExtranjero;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDomicilioExtranj() {
		return this.domicilioExtranj;
	}

	public void setDomicilioExtranj(String domicilioExtranj) {
		this.domicilioExtranj = domicilioExtranj;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
}