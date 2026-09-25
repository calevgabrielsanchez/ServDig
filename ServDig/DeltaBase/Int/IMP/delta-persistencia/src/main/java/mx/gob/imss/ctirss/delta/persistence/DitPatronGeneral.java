package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_PATRON_GENERAL database table.
 * 
 */
@Entity
@Table(name="DIT_PATRON_GENERAL")
public class DitPatronGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_PATRON_GENERAL_GENERATOR", sequenceName = "SEQ_DITPATRONGENERAL", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PATRON_GENERAL_GENERATOR")
	@Column(name="CVE_ID_PATRON_GENERAL", nullable=false, precision=22)
	private long cveIdPatronGeneral;

	@Column(name="DIG_VER", length=1)
	private String digVer;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name="POR_AUSENTISMO", precision=18, scale=15)
	private BigDecimal porAusentismo;

	@Column(name="REG_PATRON", length=50)
	private String regPatron;

	//bi-directional many-to-one association to DitPatronEscision
	@OneToMany(mappedBy="ditPatronGeneral1")
	private List<DitPatronEscision> ditPatronEscisions1;

	//bi-directional many-to-one association to DitPatronEscision
	@OneToMany(mappedBy="ditPatronGeneral2")
	private List<DitPatronEscision> ditPatronEscisions2;

	//bi-directional many-to-one association to DitPatronFusion
	@OneToMany(mappedBy="ditPatronGeneral1")
	private List<DitPatronFusion> ditPatronFusions1;

	//bi-directional many-to-one association to DitPatronFusion
	@OneToMany(mappedBy="ditPatronGeneral2")
	private List<DitPatronFusion> ditPatronFusions2;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DicTipoRegPatron
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_REG_PATRON")
	private DicTipoRegPatron dicTipoRegPatron;

	//bi-directional many-to-one association to DitPatronGeneral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_RELACIONADO")
	private DitPatronGeneral ditPatronGeneral;

	//bi-directional many-to-one association to DitPatronGeneral
	@OneToMany(mappedBy="ditPatronGeneral")
	private List<DitPatronGeneral> ditPatronGenerals;

	//bi-directional many-to-one association to DitLlavePatron
	@OneToMany(mappedBy="ditPatronGeneral", fetch=FetchType.LAZY)
	private List<DitLlavePatron> ditLlavePatrones;
	
	
	//bi-directional one-to-one association to DitPatronGeneral
	@OneToOne(mappedBy="ditPatronGeneral")
	private DitDtsExtraPatron ditDtsExtraPatron;
	

	public DitPatronGeneral() {
    }

	public long getCveIdPatronGeneral() {
		return this.cveIdPatronGeneral;
	}

	public void setCveIdPatronGeneral(long cveIdPatronGeneral) {
		this.cveIdPatronGeneral = cveIdPatronGeneral;
	}

	public String getDigVer() {
		return this.digVer;
	}

	public void setDigVer(String digVer) {
		this.digVer = digVer;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public BigDecimal getPorAusentismo() {
		return this.porAusentismo;
	}

	public void setPorAusentismo(BigDecimal porAusentismo) {
		this.porAusentismo = porAusentismo;
	}

	public String getRegPatron() {
		return this.regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public List<DitPatronEscision> getDitPatronEscisions1() {
		return this.ditPatronEscisions1;
	}

	public void setDitPatronEscisions1(List<DitPatronEscision> ditPatronEscisions1) {
		this.ditPatronEscisions1 = ditPatronEscisions1;
	}
	
	public List<DitPatronEscision> getDitPatronEscisions2() {
		return this.ditPatronEscisions2;
	}

	public void setDitPatronEscisions2(List<DitPatronEscision> ditPatronEscisions2) {
		this.ditPatronEscisions2 = ditPatronEscisions2;
	}
	
	public List<DitPatronFusion> getDitPatronFusions1() {
		return this.ditPatronFusions1;
	}

	public void setDitPatronFusions1(List<DitPatronFusion> ditPatronFusions1) {
		this.ditPatronFusions1 = ditPatronFusions1;
	}
	
	public List<DitPatronFusion> getDitPatronFusions2() {
		return this.ditPatronFusions2;
	}

	public void setDitPatronFusions2(List<DitPatronFusion> ditPatronFusions2) {
		this.ditPatronFusions2 = ditPatronFusions2;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DicTipoRegPatron getDicTipoRegPatron() {
		return this.dicTipoRegPatron;
	}

	public void setDicTipoRegPatron(DicTipoRegPatron dicTipoRegPatron) {
		this.dicTipoRegPatron = dicTipoRegPatron;
	}
	
	public DitPatronGeneral getDitPatronGeneral() {
		return this.ditPatronGeneral;
	}

	public void setDitPatronGeneral(DitPatronGeneral ditPatronGeneral) {
		this.ditPatronGeneral = ditPatronGeneral;
	}
	
	public List<DitPatronGeneral> getDitPatronGenerals() {
		return this.ditPatronGenerals;
	}

	public void setDitPatronGenerals(List<DitPatronGeneral> ditPatronGenerals) {
		this.ditPatronGenerals = ditPatronGenerals;
	}

	public List<DitLlavePatron> getDitLlavePatrones() {
		return ditLlavePatrones;
	}

	public void setDitLlavePatrones(List<DitLlavePatron> ditLlavePatrones) {
		this.ditLlavePatrones = ditLlavePatrones;
	}
	
	public DitDtsExtraPatron getDitDtsExtraPatron() {
			return ditDtsExtraPatron;
	}

	public void setDitDtsExtraPatron(DitDtsExtraPatron ditDtsExtraPatron) {
			this.ditDtsExtraPatron = ditDtsExtraPatron;
	}

	
}