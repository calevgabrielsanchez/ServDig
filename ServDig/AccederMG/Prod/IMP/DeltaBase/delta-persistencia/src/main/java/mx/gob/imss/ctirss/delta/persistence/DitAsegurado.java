package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_ASEGURADO database table.
 * 
 */
@NamedQueries({
	@NamedQuery(name = "buscaAseguradoRegistrado", 
			query = "select a from DitAsegurado a where a.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss"),
	@NamedQuery(name = "buscaAsegurado", 
			query = "select a from DitAsegurado a where a.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss and a.ditPatronSujetoObligado.cveIdPatronSujetoObligado=:idPatronSujeto")		
})
@Entity
@Table(name="DIT_ASEGURADO")
public class DitAsegurado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_ASEGURADO__CVEIDASEGURADO_GENERATOR", sequenceName = "SEQ_DITASEGURADO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ASEGURADO", nullable=false, precision=22)
	private long cveIdAsegurado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ALTA")
	private Date fecAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_BAJA")
	private Date fecBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_ACTIVO", precision=22)
	private BigDecimal indActivo;

	@Column(name="TIP_BENEFICIARIO", precision=22)
	private BigDecimal tipBeneficiario;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASIGNACION_NSS")
	private DitAsignacionNss ditAsignacionNss;

	//bi-directional many-to-one association to DitAseguradoGuarderia
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitAseguradoGuarderia> ditAseguradoGuarderias;

	//bi-directional many-to-one association to DitAseguradoHuelga
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitAseguradoHuelga> ditAseguradoHuelgas;

	//bi-directional many-to-one association to DitAseguradoPension
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitAseguradoPension> ditAseguradoPensions;

	//bi-directional many-to-one association to DitAutorizacionPermte
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitAutorizacionPermte> ditAutorizacionPermtes;

	//bi-directional many-to-one association to DitCuestRAsegurado
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitCuestRAsegurado> ditCuestRAsegurados;

	//bi-directional many-to-one association to DitCuotasCotizante
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitCuotasCotizante> ditCuotasCotizantes;

	//bi-directional many-to-one association to DitIncapacidad
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitIncapacidad> ditIncapacidads;

	//bi-directional many-to-one association to DitMovasegAjuste
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitMovasegAjuste> ditMovasegAjustes;

	//bi-directional many-to-one association to DitMovimientoAsegurado
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitMovimientoAsegurado> ditMovimientoAsegurados;

	//bi-directional many-to-one association to DitMovtosAusentismo
	@OneToMany(mappedBy="ditAsegurado")
	private List<DitMovtosAusentismo> ditMovtosAusentismos;

    public DitAsegurado() {
    }

	public long getCveIdAsegurado() {
		return this.cveIdAsegurado;
	}

	public void setCveIdAsegurado(long cveIdAsegurado) {
		this.cveIdAsegurado = cveIdAsegurado;
	}

	public Date getFecAlta() {
		return this.fecAlta;
	}

	public void setFecAlta(Date fecAlta) {
		this.fecAlta = fecAlta;
	}

	public Date getFecBaja() {
		return this.fecBaja;
	}

	public void setFecBaja(Date fecBaja) {
		this.fecBaja = fecBaja;
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

	public BigDecimal getIndActivo() {
		return this.indActivo;
	}

	public void setIndActivo(BigDecimal indActivo) {
		this.indActivo = indActivo;
	}

	public BigDecimal getTipBeneficiario() {
		return this.tipBeneficiario;
	}

	public void setTipBeneficiario(BigDecimal tipBeneficiario) {
		this.tipBeneficiario = tipBeneficiario;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DitAsignacionNss getDitAsignacionNss() {
		return this.ditAsignacionNss;
	}

	public void setDitAsignacionNss(DitAsignacionNss ditAsignacionNss) {
		this.ditAsignacionNss = ditAsignacionNss;
	}
	
	public List<DitAseguradoGuarderia> getDitAseguradoGuarderias() {
		return this.ditAseguradoGuarderias;
	}

	public void setDitAseguradoGuarderias(List<DitAseguradoGuarderia> ditAseguradoGuarderias) {
		this.ditAseguradoGuarderias = ditAseguradoGuarderias;
	}
	
	public List<DitAseguradoHuelga> getDitAseguradoHuelgas() {
		return this.ditAseguradoHuelgas;
	}

	public void setDitAseguradoHuelgas(List<DitAseguradoHuelga> ditAseguradoHuelgas) {
		this.ditAseguradoHuelgas = ditAseguradoHuelgas;
	}
	
	public List<DitAseguradoPension> getDitAseguradoPensions() {
		return this.ditAseguradoPensions;
	}

	public void setDitAseguradoPensions(List<DitAseguradoPension> ditAseguradoPensions) {
		this.ditAseguradoPensions = ditAseguradoPensions;
	}
	
	public List<DitAutorizacionPermte> getDitAutorizacionPermtes() {
		return this.ditAutorizacionPermtes;
	}

	public void setDitAutorizacionPermtes(List<DitAutorizacionPermte> ditAutorizacionPermtes) {
		this.ditAutorizacionPermtes = ditAutorizacionPermtes;
	}
	
	public List<DitCuestRAsegurado> getDitCuestRAsegurados() {
		return this.ditCuestRAsegurados;
	}

	public void setDitCuestRAsegurados(List<DitCuestRAsegurado> ditCuestRAsegurados) {
		this.ditCuestRAsegurados = ditCuestRAsegurados;
	}
	
	public List<DitCuotasCotizante> getDitCuotasCotizantes() {
		return this.ditCuotasCotizantes;
	}

	public void setDitCuotasCotizantes(List<DitCuotasCotizante> ditCuotasCotizantes) {
		this.ditCuotasCotizantes = ditCuotasCotizantes;
	}
	
	public List<DitIncapacidad> getDitIncapacidads() {
		return this.ditIncapacidads;
	}

	public void setDitIncapacidads(List<DitIncapacidad> ditIncapacidads) {
		this.ditIncapacidads = ditIncapacidads;
	}
	
	public List<DitMovasegAjuste> getDitMovasegAjustes() {
		return this.ditMovasegAjustes;
	}

	public void setDitMovasegAjustes(List<DitMovasegAjuste> ditMovasegAjustes) {
		this.ditMovasegAjustes = ditMovasegAjustes;
	}
	
	public List<DitMovimientoAsegurado> getDitMovimientoAsegurados() {
		return this.ditMovimientoAsegurados;
	}

	public void setDitMovimientoAsegurados(List<DitMovimientoAsegurado> ditMovimientoAsegurados) {
		this.ditMovimientoAsegurados = ditMovimientoAsegurados;
	}
	
	public List<DitMovtosAusentismo> getDitMovtosAusentismos() {
		return this.ditMovtosAusentismos;
	}

	public void setDitMovtosAusentismos(List<DitMovtosAusentismo> ditMovtosAusentismos) {
		this.ditMovtosAusentismos = ditMovtosAusentismos;
	}
	
}