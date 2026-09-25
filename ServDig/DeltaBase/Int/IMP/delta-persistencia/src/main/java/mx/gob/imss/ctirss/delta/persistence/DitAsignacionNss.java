package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NamedQueries({
	@NamedQuery(name = "buscaAsignacionXnss", 
			query = "select a from DitAsignacionNss a where a.numNss=:numNss and a.ditPersona.cveIdPersona=:idPersona"),
	@NamedQuery(name = "buscaAsignacionXnssSinPersona", 
			query = "select a from DitAsignacionNss a where a.numNss=:numNss"),
        @NamedQuery(name = "buscaAsignacionXpersona",
                query = "select a from DitAsignacionNss a where a.ditPersona.cveIdPersona=:idPersona")	
})

/**
 * The persistent class for the DIT_ASIGNACION_NSS database table.
 * 
 */
@Entity
@Table(name="DIT_ASIGNACION_NSS")
@Where(clause = "FEC_REGISTRO_BAJA is null")
public class DitAsignacionNss implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "DIT_ASIGNACION_NSS__CVEIDASIGNACIONNSS_GENERATOR", sequenceName = "SEQ_DITASIGNACIONNSS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_ASIGNACION_NSS__CVEIDASIGNACIONNSS_GENERATOR")
	@Column(name="CVE_ID_ASIGNACION_NSS", nullable=false, precision=22)
	private long cveIdAsignacionNss;

	@Column(name="CAN_SEMANA_COTIZADA", precision=22)
	private BigDecimal canSemanaCotizada;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_ACTIVO", precision=22)
	private BigDecimal indActivo;

	@Column(name="NUM_NSS", length=50)
	private String numNss;

	//bi-directional many-to-one association to DitAsegurado
	@OneToMany(mappedBy="ditAsignacionNss")
	private List<DitAsegurado> ditAsegurados;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA")
	private DitPersona ditPersona;

	//bi-directional many-to-one association to DitPersonaView
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CVE_ID_PERSONA", insertable=false, updatable=false)
	private DitPersona ditPersonaView;	
	
	//bi-directional many-to-one association to DitAsigNssHomDup
	@OneToMany(mappedBy="ditAsignacionNss1")
	private List<DitAsigNssHomDup> ditAsigNssHomDups1;

	//bi-directional many-to-one association to DitAsigNssHomDup
	@OneToMany(mappedBy="ditAsignacionNss2")
	private List<DitAsigNssHomDup> ditAsigNssHomDups2;

	//bi-directional many-to-one association to DitAsigNssParentesco
	@OneToMany(mappedBy="ditAsignacionNss")
	private List<DitAsigNssParentesco> ditAsigNssParentescos;

	//bi-directional many-to-one association to DitBalancePrestacion
	@OneToMany(mappedBy="ditAsignacionNss")
	private List<DitBalancePrestacion> ditBalancePrestacions;

	//bi-directional many-to-one association to DitCuotasCotizante
	@OneToMany(mappedBy="ditAsignacionNss")
	private List<DitCuotasCotizante> ditCuotasCotizantes;

	//bi-directional many-to-one association to DitGrupoFamiliar
	@OneToMany(mappedBy="ditAsignacionNss")
	private List<DitGrupoFamiliar> ditGrupoFamiliars;

	//bi-directional many-to-one association to DitLlaveAsegurado
	@OneToMany(mappedBy="ditAsignacionNss", fetch=FetchType.LAZY)
	private List<DitLlaveAsegurado> ditLlaveAsegurados;

    public DitAsignacionNss() {
    }
    
    public DitAsignacionNss(String numNss) {
		super();
		this.numNss = numNss;
	}
    
	public DitAsignacionNss(long cveIdAsignacionNss) {
		super();
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}

	public DitAsignacionNss(String numNss, DitPersona ditPersona) {
		super();
		this.numNss = numNss;
		this.ditPersona = ditPersona;
	}

	public long getCveIdAsignacionNss() {
		return this.cveIdAsignacionNss;
	}

	public void setCveIdAsignacionNss(long cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}

	public BigDecimal getCanSemanaCotizada() {
		return this.canSemanaCotizada;
	}

	public void setCanSemanaCotizada(BigDecimal canSemanaCotizada) {
		this.canSemanaCotizada = canSemanaCotizada;
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

	public String getNumNss() {
		return this.numNss;
	}

	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}

	public List<DitAsegurado> getDitAsegurados() {
		return this.ditAsegurados;
	}

	public void setDitAsegurados(List<DitAsegurado> ditAsegurados) {
		this.ditAsegurados = ditAsegurados;
	}
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
	public List<DitAsigNssHomDup> getDitAsigNssHomDups1() {
		return this.ditAsigNssHomDups1;
	}

	public void setDitAsigNssHomDups1(List<DitAsigNssHomDup> ditAsigNssHomDups1) {
		this.ditAsigNssHomDups1 = ditAsigNssHomDups1;
	}
	
	public List<DitAsigNssHomDup> getDitAsigNssHomDups2() {
		return this.ditAsigNssHomDups2;
	}

	public void setDitAsigNssHomDups2(List<DitAsigNssHomDup> ditAsigNssHomDups2) {
		this.ditAsigNssHomDups2 = ditAsigNssHomDups2;
	}
	
	public List<DitAsigNssParentesco> getDitAsigNssParentescos() {
		return this.ditAsigNssParentescos;
	}

	public void setDitAsigNssParentescos(List<DitAsigNssParentesco> ditAsigNssParentescos) {
		this.ditAsigNssParentescos = ditAsigNssParentescos;
	}
	
	public List<DitBalancePrestacion> getDitBalancePrestacions() {
		return this.ditBalancePrestacions;
	}

	public void setDitBalancePrestacions(List<DitBalancePrestacion> ditBalancePrestacions) {
		this.ditBalancePrestacions = ditBalancePrestacions;
	}
	
	public List<DitCuotasCotizante> getDitCuotasCotizantes() {
		return this.ditCuotasCotizantes;
	}

	public void setDitCuotasCotizantes(List<DitCuotasCotizante> ditCuotasCotizantes) {
		this.ditCuotasCotizantes = ditCuotasCotizantes;
	}
	
	public List<DitGrupoFamiliar> getDitGrupoFamiliars() {
		return this.ditGrupoFamiliars;
	}

	public void setDitGrupoFamiliars(List<DitGrupoFamiliar> ditGrupoFamiliars) {
		this.ditGrupoFamiliars = ditGrupoFamiliars;
	}
	
	public List<DitLlaveAsegurado> getDitLlaveAsegurados() {
		return ditLlaveAsegurados;
	}

	public void setDitLlaveAsegurados(List<DitLlaveAsegurado> ditLlaveAsegurados) {
		this.ditLlaveAsegurados = ditLlaveAsegurados;
	}

	public DitPersona getDitPersonaView() {
		return ditPersonaView;
	}

	public void setDitPersonaView(DitPersona ditPersonaView) {
		this.ditPersonaView = ditPersonaView;
	}
	
}