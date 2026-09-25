package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DIT_SOCIO database table.
 * 
 */
@Entity
@Table(name="DIT_SOCIO")
public class DitSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_SOCIO_CVEIDSOCIO_GENERATOR", sequenceName="SEQ_DITSOCIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_SOCIO_CVEIDSOCIO_GENERATOR")
	@Column(name="CVE_ID_SOCIO", unique=true, nullable=false, precision=22)
	private long cveIdSocio;

	@Column(name="CVE_ENT")
	private String cveEnt;

	@Column(name="DES_DENOM_RAZON_SOCIAL")
	private String desDenomRazonSocial;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CONTRATO")
	private Date fecContrato;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_EXTRANJERO")
	private BigDecimal indExtranjero;

	@Column(name="IND_RESIDENCIA_EXTRANJERO")
	private BigDecimal indResidenciaExtranjero;

	@Column(name="IND_TIPO_SOCIO")
	private BigDecimal indTipoSocio;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NOM_PRIMER_APELLIDO")
	private String nomPrimerApellido;

	@Column(name="NOM_SEGUNDO_APELLIDO")
	private String nomSegundoApellido;

	@Column(name="NUM_INSTRUMENTO")
	private String numInstrumento;

	@Column(name="NUM_NOTARIA")
	private String numNotaria;

	//bi-directional many-to-one association to DitPersonaFisica
    @ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisica;

	//bi-directional many-to-one association to DitPersonaMoral
    @ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

	//bi-directional many-to-one association to DitPersonaMoral
    @ManyToOne
	@JoinColumn(name="CVE_ID_PATRON")
	private DitPersonaMoral ditPatron;

	//bi-directional many-to-one association to DitSocioContacto
	@OneToMany(mappedBy="ditSocio")
	private Set<DitSocioContacto> ditSocioContactos;

    public DitSocio() {
    }

	public long getCveIdSocio() {
		return this.cveIdSocio;
	}

	public void setCveIdSocio(long cveIdSocio) {
		this.cveIdSocio = cveIdSocio;
	}

	public String getCveEnt() {
		return this.cveEnt;
	}

	public void setCveEnt(String cveEnt) {
		this.cveEnt = cveEnt;
	}

	public String getDesDenomRazonSocial() {
		return this.desDenomRazonSocial;
	}

	public void setDesDenomRazonSocial(String desDenomRazonSocial) {
		this.desDenomRazonSocial = desDenomRazonSocial;
	}

	public Date getFecContrato() {
		return this.fecContrato;
	}

	public void setFecContrato(Date fecContrato) {
		this.fecContrato = fecContrato;
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

	public BigDecimal getIndExtranjero() {
		return this.indExtranjero;
	}

	public void setIndExtranjero(BigDecimal indExtranjero) {
		this.indExtranjero = indExtranjero;
	}

	public BigDecimal getIndResidenciaExtranjero() {
		return this.indResidenciaExtranjero;
	}

	public void setIndResidenciaExtranjero(BigDecimal indResidenciaExtranjero) {
		this.indResidenciaExtranjero = indResidenciaExtranjero;
	}

	public BigDecimal getIndTipoSocio() {
		return this.indTipoSocio;
	}

	public void setIndTipoSocio(BigDecimal indTipoSocio) {
		this.indTipoSocio = indTipoSocio;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPrimerApellido() {
		return this.nomPrimerApellido;
	}

	public void setNomPrimerApellido(String nomPrimerApellido) {
		this.nomPrimerApellido = nomPrimerApellido;
	}

	public String getNomSegundoApellido() {
		return this.nomSegundoApellido;
	}

	public void setNomSegundoApellido(String nomSegundoApellido) {
		this.nomSegundoApellido = nomSegundoApellido;
	}

	public String getNumInstrumento() {
		return this.numInstrumento;
	}

	public void setNumInstrumento(String numInstrumento) {
		this.numInstrumento = numInstrumento;
	}

	public String getNumNotaria() {
		return this.numNotaria;
	}

	public void setNumNotaria(String numNotaria) {
		this.numNotaria = numNotaria;
	}

	public DitPersonaFisica getDitPersonaFisica() {
		return this.ditPersonaFisica;
	}

	public void setDitPersonaFisica(DitPersonaFisica ditPersonaFisica) {
		this.ditPersonaFisica = ditPersonaFisica;
	}
	
	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral1) {
		this.ditPersonaMoral = ditPersonaMoral1;
	}
	
	public DitPersonaMoral getDitPatron() {
		return this.ditPatron;
	}

	public void setDitPatron(DitPersonaMoral ditPatron) {
		this.ditPatron = ditPatron;
	}
	
	public Set<DitSocioContacto> getDitSocioContactos() {
		return this.ditSocioContactos;
	}

	public void setDitSocioContactos(Set<DitSocioContacto> ditSocioContactos) {
		this.ditSocioContactos = ditSocioContactos;
	}
	
}