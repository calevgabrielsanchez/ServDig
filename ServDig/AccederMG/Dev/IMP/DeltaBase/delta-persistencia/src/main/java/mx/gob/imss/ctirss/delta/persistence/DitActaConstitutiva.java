package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_ACTA_CONSTITUTIVA database table.
 * 
 */
@Entity
@Table(name="DIT_ACTA_CONSTITUTIVA")
public class DitActaConstitutiva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "DIT_ACTACONSTITUTIVA_CVEIDACTACONSTITUTIVA_GENERATOR", sequenceName = "SEQ_DITACTACONSTITUTIVA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_ACTACONSTITUTIVA_CVEIDACTACONSTITUTIVA_GENERATOR")
	@Column(name="CVE_ID_ACT_CONSTITUTIVA", nullable=false, precision=22)
	private Integer cveIdActConstitutiva;
	
	@Column(name="CVE_ENT")
	private String cveEnt;
	
	@Column(name="CVE_MUN")
	private String cveMun;

	@Column(name="DES_OBSERVACIONES", length=500)
	private String desObservaciones;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_EXPEDICION_ACTA")
	private Date fecExpedicionActa;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_REG_PUB_TRAMITE", precision=22)
	private BigDecimal indRegPubTramite;

	@Column(name="NUM_ACTA", length=50)
	private String numActa;

	@Column(name="NUM_ESCRITURA", length=50)
	private String numEscritura;

	@Column(name="NUM_FOJA", length=50)
	private String numFoja;

	@Column(name="NUM_FOLIO_MERCANTIL", length=100)
	private String numFolioMercantil;

	@Column(name="NUM_LIBRO", length=50)
	private String numLibro;

	@Column(name="NUM_NOTARIA", length=50)
	private String numNotaria;

	@Column(name="NUM_REGISTRO", length=100)
	private String numRegistro;
	
	@Column(name="NUM_SECCION", length=100)
	private String numSeccion;
	
	@Column(name="NUM_PARTIDA", length=100)
	private String numPartida;
	
	@Column(name="NUM_VOLUMEN", length=100)
	private String numVolumen;
	
	//bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;
	
	
	//bi-directional many-to-one association to DgCatMunicipio
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN", nullable=false, insertable=false, updatable=false)
		})
	private DgCatMunicipio dgCatMunicipio;

	public DitActaConstitutiva() {
    }

	public DitActaConstitutiva(Integer idActaConstitutiva) {
        this.cveIdActConstitutiva = idActaConstitutiva;
    }

    public Integer getCveIdActConstitutiva() {
		return this.cveIdActConstitutiva;
	}

	public void setCveIdActConstitutiva(Integer cveIdActConstitutiva) {
		this.cveIdActConstitutiva = cveIdActConstitutiva;
	}

	public String getCveEnt() {
		return cveEnt;
	}

	public void setCveEnt(String cveEnt) {
		this.cveEnt = cveEnt;
	}

	public String getCveMun() {
		return cveMun;
	}

	public void setCveMun(String cveMun) {
		this.cveMun = cveMun;
	}

	public String getDesObservaciones() {
		return this.desObservaciones;
	}

	public void setDesObservaciones(String desObservaciones) {
		this.desObservaciones = desObservaciones;
	}

	public Date getFecExpedicionActa() {
		return this.fecExpedicionActa;
	}

	public void setFecExpedicionActa(Date fecExpedicionActa) {
		this.fecExpedicionActa = fecExpedicionActa;
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

	public BigDecimal getIndRegPubTramite() {
		return this.indRegPubTramite;
	}

	public void setIndRegPubTramite(BigDecimal indRegPubTramite) {
		this.indRegPubTramite = indRegPubTramite;
	}

	public String getNumActa() {
		return this.numActa;
	}

	public void setNumActa(String numActa) {
		this.numActa = numActa;
	}

	public String getNumEscritura() {
		return this.numEscritura;
	}

	public void setNumEscritura(String numEscritura) {
		this.numEscritura = numEscritura;
	}

	public String getNumFoja() {
		return this.numFoja;
	}

	public void setNumFoja(String numFoja) {
		this.numFoja = numFoja;
	}

	public String getNumFolioMercantil() {
		return this.numFolioMercantil;
	}

	public void setNumFolioMercantil(String numFolioMercantil) {
		this.numFolioMercantil = numFolioMercantil;
	}

	public String getNumLibro() {
		return this.numLibro;
	}

	public void setNumLibro(String numLibro) {
		this.numLibro = numLibro;
	}

	public String getNumNotaria() {
		return this.numNotaria;
	}

	public void setNumNotaria(String numNotaria) {
		this.numNotaria = numNotaria;
	}

	public String getNumRegistro() {
		return this.numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}

	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}

	public String getNumSeccion() {
		return numSeccion;
	}

	public void setNumSeccion(String numSeccion) {
		this.numSeccion = numSeccion;
	}

	public String getNumPartida() {
		return numPartida;
	}

	public void setNumPartida(String numPartida) {
		this.numPartida = numPartida;
	}

	public String getNumVolumen() {
		return numVolumen;
	}

	public void setNumVolumen(String numVolumen) {
		this.numVolumen = numVolumen;
	}
	
    public DgCatMunicipio getDgCatMunicipio() {
		return dgCatMunicipio;
	}

	public void setDgCatMunicipio(DgCatMunicipio dgCatMunicipio) {
		this.dgCatMunicipio = dgCatMunicipio;
	}

}