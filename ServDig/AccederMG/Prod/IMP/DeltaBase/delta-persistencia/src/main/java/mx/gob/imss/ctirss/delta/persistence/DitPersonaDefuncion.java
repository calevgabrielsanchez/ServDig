package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PERSONA_DEFUNCION database table.
 * 
 */
@Entity
@Table(name="DIT_PERSONA_DEFUNCION")
public class DitPersonaDefuncion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PERSONA_DEFUNCION", nullable=false, precision=22)
	private long cveIdPersonaDefuncion;

    @Temporal( TemporalType.DATE)
	@Column(name="ANIO_REGISTRO")
	private Date anioRegistro;

	@Column(length=50)
	private String crip;

	@Column(name="DES_OFIC_REG_CIVIL", length=50)
	private String desOficRegCivil;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_ACTA", length=50)
	private String numActa;

	@Column(name="NUM_FOJA", length=50)
	private String numFoja;

	@Column(name="NUM_LIBRO", length=50)
	private String numLibro;

	@Column(name="NUM_TOMO", length=50)
	private String numTomo;

	//bi-directional many-to-one association to DgCatMunicipio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT"),
		@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN")
		})
	private DgCatMunicipio dgCatMunicipio;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA", nullable=false)
	private DitPersona ditPersona;

    public DitPersonaDefuncion() {
    }

	public long getCveIdPersonaDefuncion() {
		return this.cveIdPersonaDefuncion;
	}

	public void setCveIdPersonaDefuncion(long cveIdPersonaDefuncion) {
		this.cveIdPersonaDefuncion = cveIdPersonaDefuncion;
	}

	public Date getAnioRegistro() {
		return this.anioRegistro;
	}

	public void setAnioRegistro(Date anioRegistro) {
		this.anioRegistro = anioRegistro;
	}

	public String getCrip() {
		return this.crip;
	}

	public void setCrip(String crip) {
		this.crip = crip;
	}

	public String getDesOficRegCivil() {
		return this.desOficRegCivil;
	}

	public void setDesOficRegCivil(String desOficRegCivil) {
		this.desOficRegCivil = desOficRegCivil;
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

	public String getNumActa() {
		return this.numActa;
	}

	public void setNumActa(String numActa) {
		this.numActa = numActa;
	}

	public String getNumFoja() {
		return this.numFoja;
	}

	public void setNumFoja(String numFoja) {
		this.numFoja = numFoja;
	}

	public String getNumLibro() {
		return this.numLibro;
	}

	public void setNumLibro(String numLibro) {
		this.numLibro = numLibro;
	}

	public String getNumTomo() {
		return this.numTomo;
	}

	public void setNumTomo(String numTomo) {
		this.numTomo = numTomo;
	}

	public DgCatMunicipio getDgCatMunicipio() {
		return this.dgCatMunicipio;
	}

	public void setDgCatMunicipio(DgCatMunicipio dgCatMunicipio) {
		this.dgCatMunicipio = dgCatMunicipio;
	}
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
}