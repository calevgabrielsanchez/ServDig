package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_ASEGURADO_PENSION database table.
 * 
 */
@Entity
@Table(name="DIT_ASEGURADO_PENSION")
public class DitAseguradoPension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ASEGURADO_PENSION", nullable=false, precision=22)
	private long cveIdAseguradoPension;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CANCELACION")
	private Date fecCancelacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO")
	private Date fecInicio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PREVISTA_TERMINO")
	private Date fecPrevistaTermino;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name="IMP_DEVUELTO", length=50)
	private String impDevuelto;

	@Column(name="IMP_PAGADO", length=50)
	private String impPagado;

	@Column(name="IMP_RCV", length=50)
	private String impRcv;

	@Column(name="POR_INCAPACIDAD_PERM_PARCIAL", length=50)
	private String porIncapacidadPermParcial;

	@Column(name="TIP_ESTATUS", precision=22)
	private BigDecimal tipEstatus;

	//bi-directional many-to-one association to DitAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO")
	private DitAsegurado ditAsegurado;

	//bi-directional many-to-one association to DitBalancePrestacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_BALANCE_PRESTACION")
	private DitBalancePrestacion ditBalancePrestacion;

	//bi-directional many-to-one association to DitAseguradoPensionHist
	@OneToMany(mappedBy="ditAseguradoPension")
	private List<DitAseguradoPensionHist> ditAseguradoPensionHists;

	//bi-directional many-to-one association to DitCuotasCotizante
	@OneToMany(mappedBy="ditAseguradoPension")
	private List<DitCuotasCotizante> ditCuotasCotizantes;
	
	//bi-directional many-to-one association to DicTipoPension
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_PENSION")
	private DicTipoPension dicTipoPension;

	//bi-directional many-to-one association to DitLlaveAsegurado
	@OneToMany(mappedBy="ditAseguradoPension", fetch=FetchType.LAZY)
	private List<DitLlaveAsegurado> ditLlaveAsegurados;

	public long getCveIdAseguradoPension() {
		return this.cveIdAseguradoPension;
	}

	public void setCveIdAseguradoPension(long cveIdAseguradoPension) {
		this.cveIdAseguradoPension = cveIdAseguradoPension;
	}

	public Date getFecCancelacion() {
		return this.fecCancelacion;
	}

	public void setFecCancelacion(Date fecCancelacion) {
		this.fecCancelacion = fecCancelacion;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecPrevistaTermino() {
		return this.fecPrevistaTermino;
	}

	public void setFecPrevistaTermino(Date fecPrevistaTermino) {
		this.fecPrevistaTermino = fecPrevistaTermino;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public String getImpDevuelto() {
		return this.impDevuelto;
	}

	public void setImpDevuelto(String impDevuelto) {
		this.impDevuelto = impDevuelto;
	}

	public String getImpPagado() {
		return this.impPagado;
	}

	public void setImpPagado(String impPagado) {
		this.impPagado = impPagado;
	}

	public String getImpRcv() {
		return this.impRcv;
	}

	public void setImpRcv(String impRcv) {
		this.impRcv = impRcv;
	}

	public String getPorIncapacidadPermParcial() {
		return this.porIncapacidadPermParcial;
	}

	public void setPorIncapacidadPermParcial(String porIncapacidadPermParcial) {
		this.porIncapacidadPermParcial = porIncapacidadPermParcial;
	}

	public BigDecimal getTipEstatus() {
		return this.tipEstatus;
	}

	public void setTipEstatus(BigDecimal tipEstatus) {
		this.tipEstatus = tipEstatus;
	}

	public DitAsegurado getDitAsegurado() {
		return this.ditAsegurado;
	}

	public void setDitAsegurado(DitAsegurado ditAsegurado) {
		this.ditAsegurado = ditAsegurado;
	}
	
	public DitBalancePrestacion getDitBalancePrestacion() {
		return this.ditBalancePrestacion;
	}

	public void setDitBalancePrestacion(DitBalancePrestacion ditBalancePrestacion) {
		this.ditBalancePrestacion = ditBalancePrestacion;
	}
	
	public List<DitAseguradoPensionHist> getDitAseguradoPensionHists() {
		return this.ditAseguradoPensionHists;
	}

	public void setDitAseguradoPensionHists(List<DitAseguradoPensionHist> ditAseguradoPensionHists) {
		this.ditAseguradoPensionHists = ditAseguradoPensionHists;
	}
	
	public List<DitCuotasCotizante> getDitCuotasCotizantes() {
		return this.ditCuotasCotizantes;
	}

	public void setDitCuotasCotizantes(List<DitCuotasCotizante> ditCuotasCotizantes) {
		this.ditCuotasCotizantes = ditCuotasCotizantes;
	}

	/**
	 * @return the dicTipoPension
	 */
	public DicTipoPension getDicTipoPension() {
		return dicTipoPension;
	}

	/**
	 * @param dicTipoPension the dicTipoPension to set
	 */
	public void setDicTipoPension(DicTipoPension dicTipoPension) {
		this.dicTipoPension = dicTipoPension;
	}

	public List<DitLlaveAsegurado> getDitLlaveAsegurados() {
		return ditLlaveAsegurados;
	}

	public void setDitLlaveAsegurados(List<DitLlaveAsegurado> ditLlaveAsegurados) {
		this.ditLlaveAsegurados = ditLlaveAsegurados;
	}
	
}