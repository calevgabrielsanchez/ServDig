package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_REPRESENTANTE_LEGAL database table.
 * 
 */
@Entity
@Table(name="DIT_REPRESENTANTE_LEGAL")
public class DitRepresentanteLegal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_REPRESENTANTE_LEGAL_GENERATOR", sequenceName = "SEQ_DITREPRESENTANTELEGAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_REPRESENTANTE_LEGAL_GENERATOR")
	@Column(name="CVE_ID_REPRESENTANTE_LEGAL", nullable=false, precision=22)
	private long cveIdRepresentanteLegal;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_ACT_ADMON_DOMINIO", precision=22)
	private BigDecimal indActAdmonDominio;

	@Column(name="IND_ESTATUS", precision=22)
	private BigDecimal indEstatus;

	@Column(length=100)
	private String rupa;

	//bi-directional many-to-one association to DicMandato
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MANDATO")
	private DicMandato dicMandato;

	//bi-directional many-to-one association to DicTipoPoder
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_PODER")
	private DicTipoPoder dicTipoPoder;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA")
	private DitPersona ditPersona;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisicaRepresentada;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoralRepresentada;

	//bi-directional many-to-one association to DitRlFacultad
	@OneToMany(mappedBy="ditRepresentanteLegal")
	private List<DitRlFacultad> ditRlFacultads;
	
	@OneToMany(mappedBy="ditRepresentanteLegal")
	private List<DitRepresentanteLegalContac> ditRepresentanteLegalContacs;
	
    public DitRepresentanteLegal() {
    }

	public long getCveIdRepresentanteLegal() {
		return this.cveIdRepresentanteLegal;
	}

	public void setCveIdRepresentanteLegal(long cveIdRepresentanteLegal) {
		this.cveIdRepresentanteLegal = cveIdRepresentanteLegal;
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
	
	public BigDecimal getIndActAdmonDominio() {
		return indActAdmonDominio;
	}

	public void setIndActAdmonDominio(BigDecimal indActAdmonDominio) {
		this.indActAdmonDominio = indActAdmonDominio;
	}

	public BigDecimal getIndEstatus() {
		return this.indEstatus;
	}

	public void setIndEstatus(BigDecimal indEstatus) {
		this.indEstatus = indEstatus;
	}

	public String getRupa() {
		return this.rupa;
	}

	public void setRupa(String rupa) {
		this.rupa = rupa;
	}

	public DicMandato getDicMandato() {
		return this.dicMandato;
	}

	public void setDicMandato(DicMandato dicMandato) {
		this.dicMandato = dicMandato;
	}
	
	public DicTipoPoder getDicTipoPoder() {
		return this.dicTipoPoder;
	}

	public void setDicTipoPoder(DicTipoPoder dicTipoPoder) {
		this.dicTipoPoder = dicTipoPoder;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
	public List<DitRlFacultad> getDitRlFacultads() {
		return this.ditRlFacultads;
	}

	public void setDitRlFacultads(List<DitRlFacultad> ditRlFacultads) {
		this.ditRlFacultads = ditRlFacultads;
	}

	public List<DitRepresentanteLegalContac> getDitRepresentanteLegalContacs() {
		return ditRepresentanteLegalContacs;
	}

	public void setDitRepresentanteLegalContacs(
			List<DitRepresentanteLegalContac> ditRepresentanteLegalContacs) {
		this.ditRepresentanteLegalContacs = ditRepresentanteLegalContacs;
	}

	public DitPersonaFisica getDitPersonaFisicaRepresentada() {
		return ditPersonaFisicaRepresentada;
	}

	public void setDitPersonaFisicaRepresentada(
			DitPersonaFisica ditPersonaFisicaRepresentada) {
		this.ditPersonaFisicaRepresentada = ditPersonaFisicaRepresentada;
	}

	public DitPersonaMoral getDitPersonaMoralRepresentada() {
		return ditPersonaMoralRepresentada;
	}

	public void setDitPersonaMoralRepresentada(
			DitPersonaMoral ditPersonaMoralRepresentada) {
		this.ditPersonaMoralRepresentada = ditPersonaMoralRepresentada;
	}
	
	
	
}