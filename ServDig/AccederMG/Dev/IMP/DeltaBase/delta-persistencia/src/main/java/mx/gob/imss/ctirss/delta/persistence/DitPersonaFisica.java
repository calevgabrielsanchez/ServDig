package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_PERSONA_FISICA database table.
 * 
 */
@Entity
@Table(name="DIT_PERSONA_FISICA")
public class DitPersonaFisica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_PERSONA_FISICA_CVEIDPERSONAFISICA_GENERATOR", sequenceName="SEQ_DITPERSONAFISICA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_PERSONA_FISICA_CVEIDPERSONAFISICA_GENERATOR")
	@Column(name="CVE_ID_PERSONA_FISICA", unique=true, nullable=false, precision=22)
	private long cveIdPersonaFisica;

	@Column(name="DES_CAMARA_ORGANIZACION", length=255)
	private String desCamaraOrganizacion;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_ACREDITADO", precision=22)
	private BigDecimal indAcreditado;

	@Column(name="RFC", length=50)
	private String rfc;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@OneToMany(mappedBy="ditPersonaFisica")
	private List<DitPatronSujetoObligado> ditPatronSujetoObligados;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA")
	private DitPersona ditPersona;
	
	@OneToMany(mappedBy="ditPersonaFisica")
	private List<DitSocio> ditSocios;
	
	@OneToMany(mappedBy="ditPersonaFisica")
	private List<DitPersonaFDomFiscal> ditPersonaFDomFiscales;
	
	//bi-directional many-to-one association to DitPersonaFContactoFiscal
	@OneToMany(mappedBy="ditPersonaFisica")
	private List<DitPersonaFContactoFiscal> ditPersonaFContactoFiscales;

	//bi-directional many-to-one association to DitSituacionSat
	@OneToMany(mappedBy="ditPersonaFisica")
	private List<DitSituacionSat> ditSituacionesSat;
	
	//bi-directional many-to-one association to DitDatosPersonaSat
	@OneToMany(mappedBy="ditPersonaFisica", fetch = FetchType.EAGER)
	private List<DitDatosPersonaSat> ditDatosPersonaSat;

	//bi-directional many-to-one association to DitLlavePatron
	@OneToMany(mappedBy="ditPersonaFisica" , fetch=FetchType.LAZY)
	private List<DitLlavePatron> ditLlavePatrones;
	
	@OneToOne(mappedBy="ditPersonaFisica", cascade = CascadeType.REMOVE)
  	private DitDatosCertificadoFiel ditDatosCertificadoFiel;
	
    public DitPersonaFisica() {
    }

	public long getCveIdPersonaFisica() {
		return this.cveIdPersonaFisica;
	}

	public void setCveIdPersonaFisica(long cveIdPersonaFisica) {
		this.cveIdPersonaFisica = cveIdPersonaFisica;
	}

	public String getDesCamaraOrganizacion() {
		return this.desCamaraOrganizacion;
	}

	public void setDesCamaraOrganizacion(String desCamaraOrganizacion) {
		this.desCamaraOrganizacion = desCamaraOrganizacion;
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

	public BigDecimal getIndAcreditado() {
		return this.indAcreditado;
	}

	public void setIndAcreditado(BigDecimal indAcreditado) {
		this.indAcreditado = indAcreditado;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public List<DitPatronSujetoObligado> getDitPatronSujetoObligados() {
		return this.ditPatronSujetoObligados;
	}

	public void setDitPatronSujetoObligados(List<DitPatronSujetoObligado> ditPatronSujetoObligados) {
		this.ditPatronSujetoObligados = ditPatronSujetoObligados;
	}
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	public List<DitSocio> getDitSocios() {
		return ditSocios;
	}

	public void setDitSocios(List<DitSocio> ditSocios) {
		this.ditSocios = ditSocios;
	}

	public List<DitPersonaFDomFiscal> getDitPersonaFDomFiscales() {
		return ditPersonaFDomFiscales;
	}

	public void setDitPersonaFDomFiscales(
			List<DitPersonaFDomFiscal> ditPersonaFDomFiscales) {
		this.ditPersonaFDomFiscales = ditPersonaFDomFiscales;
	}

	public List<DitPersonaFContactoFiscal> getDitPersonaFContactoFiscales() {
		return ditPersonaFContactoFiscales;
	}

	public void setDitPersonaFContactoFiscales(
			List<DitPersonaFContactoFiscal> ditPersonaFContactoFiscales) {
		this.ditPersonaFContactoFiscales = ditPersonaFContactoFiscales;
	}
	
	public List<DitSituacionSat> getDitSituacionesSat() {
		return ditSituacionesSat;
	}
	public void setDitSituacionesSat(List<DitSituacionSat> ditSituacionesSat) {
		this.ditSituacionesSat = ditSituacionesSat;
	}

	public List<DitDatosPersonaSat> getDitDatosPersonaSat() {
		return ditDatosPersonaSat;
	}

	public void setDitDatosPersonaSat(List<DitDatosPersonaSat> ditDatosPersonaSat) {
		this.ditDatosPersonaSat = ditDatosPersonaSat;
	}

	public List<DitLlavePatron> getDitLlavePatrones() {
		return ditLlavePatrones;
	}

	public void setDitLlavePatrones(List<DitLlavePatron> ditLlavePatrones) {
		this.ditLlavePatrones = ditLlavePatrones;
	}

	public DitDatosCertificadoFiel getDitDatosCertificadoFiel() {
		return ditDatosCertificadoFiel;
	}

	public void setDitDatosCertificadoFiel(
			DitDatosCertificadoFiel ditDatosCertificadoFiel) {
		this.ditDatosCertificadoFiel = ditDatosCertificadoFiel;
	}
	
}