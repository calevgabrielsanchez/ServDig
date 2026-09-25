package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CFT_INTEGRAFISCALIZA database table.
 * 
 */
@Entity
@Table(name="CFT_INTEGRAFISCALIZA")
public class CftIntegrafiscaliza implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CftIntegrafiscalizaPK id;

	@Column(name="CVE_TPO_DICTAMEN", length=3)
	private String cveTpoDictamen;

	@Column(name="CVE_USUARIO", length=12)
	private String cveUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CARGA", nullable=false)
	private Date fecCarga;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_PER")
	private Date fecFinPer;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INI_PER")
	private Date fecIniPer;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_NOTIFICA_1")
	private Date fecNotifica1;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_NOTIFICA_2")
	private Date fecNotifica2;

	@Column(name="IND_AVANCE_OBRA", precision=22)
	private BigDecimal indAvanceObra;

	@Column(name="IND_EJER_DICTA", precision=22)
	private BigDecimal indEjerDicta;

	@Column(name="NOM_ARCHIVO", length=100)
	private String nomArchivo;

	@Column(name="NUM_AFIL15", length=50)
	private String numAfil15;

	@Column(name="RAZON_SOCIAL", nullable=false, length=200)
	private String razonSocial;

	@Column(name="REG_PATRONAL", nullable=false, length=11)
	private String regPatronal;

	//bi-directional many-to-one association to CfcTpofiscaliza
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_TPOFISCALIZA", nullable=false, insertable=false, updatable=false)
	private CfcTpofiscaliza cfcTpofiscaliza;

	//bi-directional many-to-one association to FdtSubdeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG", nullable=false, insertable=false, updatable=false)
		})
	private FdtSubdeleg fdtSubdeleg;

	//bi-directional many-to-one association to CftRegistrospat
	@OneToMany(mappedBy="cftIntegrafiscaliza")
	private List<CftRegistrospat> cftRegistrospats;

    public CftIntegrafiscaliza() {
    }

	public CftIntegrafiscalizaPK getId() {
		return this.id;
	}

	public void setId(CftIntegrafiscalizaPK id) {
		this.id = id;
	}
	
	public String getCveTpoDictamen() {
		return this.cveTpoDictamen;
	}

	public void setCveTpoDictamen(String cveTpoDictamen) {
		this.cveTpoDictamen = cveTpoDictamen;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecCarga() {
		return this.fecCarga;
	}

	public void setFecCarga(Date fecCarga) {
		this.fecCarga = fecCarga;
	}

	public Date getFecFinPer() {
		return this.fecFinPer;
	}

	public void setFecFinPer(Date fecFinPer) {
		this.fecFinPer = fecFinPer;
	}

	public Date getFecIniPer() {
		return this.fecIniPer;
	}

	public void setFecIniPer(Date fecIniPer) {
		this.fecIniPer = fecIniPer;
	}

	public Date getFecNotifica1() {
		return this.fecNotifica1;
	}

	public void setFecNotifica1(Date fecNotifica1) {
		this.fecNotifica1 = fecNotifica1;
	}

	public Date getFecNotifica2() {
		return this.fecNotifica2;
	}

	public void setFecNotifica2(Date fecNotifica2) {
		this.fecNotifica2 = fecNotifica2;
	}

	public BigDecimal getIndAvanceObra() {
		return this.indAvanceObra;
	}

	public void setIndAvanceObra(BigDecimal indAvanceObra) {
		this.indAvanceObra = indAvanceObra;
	}

	public BigDecimal getIndEjerDicta() {
		return this.indEjerDicta;
	}

	public void setIndEjerDicta(BigDecimal indEjerDicta) {
		this.indEjerDicta = indEjerDicta;
	}

	public String getNomArchivo() {
		return this.nomArchivo;
	}

	public void setNomArchivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}

	public String getNumAfil15() {
		return this.numAfil15;
	}

	public void setNumAfil15(String numAfil15) {
		this.numAfil15 = numAfil15;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRegPatronal() {
		return this.regPatronal;
	}

	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}

	public CfcTpofiscaliza getCfcTpofiscaliza() {
		return this.cfcTpofiscaliza;
	}

	public void setCfcTpofiscaliza(CfcTpofiscaliza cfcTpofiscaliza) {
		this.cfcTpofiscaliza = cfcTpofiscaliza;
	}
	
	public FdtSubdeleg getFdtSubdeleg() {
		return this.fdtSubdeleg;
	}

	public void setFdtSubdeleg(FdtSubdeleg fdtSubdeleg) {
		this.fdtSubdeleg = fdtSubdeleg;
	}
	
	public List<CftRegistrospat> getCftRegistrospats() {
		return this.cftRegistrospats;
	}

	public void setCftRegistrospats(List<CftRegistrospat> cftRegistrospats) {
		this.cftRegistrospats = cftRegistrospats;
	}
	
}