package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_MOVTO_PAT_SUJ_OBLIG database table.
 * 
 */
@Entity
@Table(name="DIT_MOVTO_PAT_SUJ_OBLIG")
public class DitMovtoPatSujOblig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MOVTO_PAT_SUJ_OBLIG", nullable=false, precision=22)
	private long cveIdMovtoPatSujOblig;

	@Column(name="CVE_ID_PATRON_DESTINO", precision=22)
	private BigDecimal cveIdPatronDestino;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_AVISO_SAT")
	private Date fecAvisoSat;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INFORME")
	private Date fecInforme;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_OFICIO")
	private Date fecOficio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Column(name="FOLIO_MOVIMIENTO", length=100)
	private String folioMovimiento;

	@Column(name="NUMERO_OFICIO", length=100)
	private String numeroOficio;

	@Column(length=255)
	private String observaciones;

	//bi-directional many-to-one association to DitMovimientoSubdeleg
	@OneToMany(mappedBy="ditMovtoPatSujOblig")
	private List<DitMovimientoSubdeleg> ditMovimientoSubdelegs;

	//bi-directional many-to-one association to DicTipoMovtoPatSujoblig
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_MOVTO_PAT_SUJOBLIG")
	private DicTipoMovtoPatSujoblig dicTipoMovtoPatSujoblig;

	//bi-directional many-to-one association to DicCausa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CAUSA")
	private DicCausa dicCausa;

	//bi-directional many-to-one association to DicOrigMovtoPatSujoblig
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ORIG_MOVTO_PAT_SUJOBLIG")
	private DicOrigMovtoPatSujoblig dicOrigMovtoPatSujoblig;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DitPatsujobligSolicmovto
	@OneToMany(mappedBy="ditMovtoPatSujOblig")
	private List<DitPatsujobligSolicmovto> ditPatsujobligSolicmovtos;

    public DitMovtoPatSujOblig() {
    }

	public long getCveIdMovtoPatSujOblig() {
		return this.cveIdMovtoPatSujOblig;
	}

	public void setCveIdMovtoPatSujOblig(long cveIdMovtoPatSujOblig) {
		this.cveIdMovtoPatSujOblig = cveIdMovtoPatSujOblig;
	}

	public BigDecimal getCveIdPatronDestino() {
		return this.cveIdPatronDestino;
	}

	public void setCveIdPatronDestino(BigDecimal cveIdPatronDestino) {
		this.cveIdPatronDestino = cveIdPatronDestino;
	}

	public Date getFecAvisoSat() {
		return this.fecAvisoSat;
	}

	public void setFecAvisoSat(Date fecAvisoSat) {
		this.fecAvisoSat = fecAvisoSat;
	}

	public Date getFecInforme() {
		return this.fecInforme;
	}

	public void setFecInforme(Date fecInforme) {
		this.fecInforme = fecInforme;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public Date getFecOficio() {
		return this.fecOficio;
	}

	public void setFecOficio(Date fecOficio) {
		this.fecOficio = fecOficio;
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

	public String getFolioMovimiento() {
		return this.folioMovimiento;
	}

	public void setFolioMovimiento(String folioMovimiento) {
		this.folioMovimiento = folioMovimiento;
	}

	public String getNumeroOficio() {
		return this.numeroOficio;
	}

	public void setNumeroOficio(String numeroOficio) {
		this.numeroOficio = numeroOficio;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<DitMovimientoSubdeleg> getDitMovimientoSubdelegs() {
		return this.ditMovimientoSubdelegs;
	}

	public void setDitMovimientoSubdelegs(List<DitMovimientoSubdeleg> ditMovimientoSubdelegs) {
		this.ditMovimientoSubdelegs = ditMovimientoSubdelegs;
	}
	
	public DicTipoMovtoPatSujoblig getDicTipoMovtoPatSujoblig() {
		return this.dicTipoMovtoPatSujoblig;
	}

	public void setDicTipoMovtoPatSujoblig(DicTipoMovtoPatSujoblig dicTipoMovtoPatSujoblig) {
		this.dicTipoMovtoPatSujoblig = dicTipoMovtoPatSujoblig;
	}
	
	public DicCausa getDicCausa() {
		return this.dicCausa;
	}

	public void setDicCausa(DicCausa dicCausa) {
		this.dicCausa = dicCausa;
	}
	
	public DicOrigMovtoPatSujoblig getDicOrigMovtoPatSujoblig() {
		return this.dicOrigMovtoPatSujoblig;
	}

	public void setDicOrigMovtoPatSujoblig(DicOrigMovtoPatSujoblig dicOrigMovtoPatSujoblig) {
		this.dicOrigMovtoPatSujoblig = dicOrigMovtoPatSujoblig;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public List<DitPatsujobligSolicmovto> getDitPatsujobligSolicmovtos() {
		return this.ditPatsujobligSolicmovtos;
	}

	public void setDitPatsujobligSolicmovtos(List<DitPatsujobligSolicmovto> ditPatsujobligSolicmovtos) {
		this.ditPatsujobligSolicmovtos = ditPatsujobligSolicmovtos;
	}
	
}