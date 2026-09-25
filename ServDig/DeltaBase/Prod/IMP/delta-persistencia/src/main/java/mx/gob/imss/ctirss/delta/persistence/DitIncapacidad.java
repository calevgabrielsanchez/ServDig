package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_INCAPACIDAD database table.
 * 
 */
@Entity
@Table(name="DIT_INCAPACIDAD")
public class DitIncapacidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_INCAPACIDAD", nullable=false, precision=22)
	private long cveIdIncapacidad;

	@Column(name="CAN_DIAS_SUBSIDIADOS", precision=22)
	private BigDecimal canDiasSubsidiados;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_DEFUNCION")
	private Date fecDefuncion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO")
	private Date fecInicio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_TERMINO")
	private Date fecTermino;

	@Column(name="IND_RECAIDA_REVALUACION", precision=22)
	private BigDecimal indRecaidaRevaluacion;

	@Column(name="POR_REVALUACION", precision=5, scale=2)
	private BigDecimal porRevaluacion;

	@Column(name="POR_VALUACION", precision=5, scale=2)
	private BigDecimal porValuacion;

	@Column(name="REF_FOLIO", length=100)
	private String refFolio;

	//bi-directional many-to-one association to DicCausaIncapacidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CAUSA_INCAPACIDAD")
	private DicCausaIncapacidad dicCausaIncapacidad;

	//bi-directional many-to-one association to DicTipoIncapacidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_INCAPACIDAD")
	private DicTipoIncapacidad dicTipoIncapacidad;

	//bi-directional many-to-one association to DitAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO")
	private DitAsegurado ditAsegurado;

	//bi-directional many-to-one association to DitBalancePrestacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_BALANCE_PRESTACION")
	private DitBalancePrestacion ditBalancePrestacion;

    public DitIncapacidad() {
    }

	public long getCveIdIncapacidad() {
		return this.cveIdIncapacidad;
	}

	public void setCveIdIncapacidad(long cveIdIncapacidad) {
		this.cveIdIncapacidad = cveIdIncapacidad;
	}

	public BigDecimal getCanDiasSubsidiados() {
		return this.canDiasSubsidiados;
	}

	public void setCanDiasSubsidiados(BigDecimal canDiasSubsidiados) {
		this.canDiasSubsidiados = canDiasSubsidiados;
	}

	public Date getFecDefuncion() {
		return this.fecDefuncion;
	}

	public void setFecDefuncion(Date fecDefuncion) {
		this.fecDefuncion = fecDefuncion;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
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

	public Date getFecTermino() {
		return this.fecTermino;
	}

	public void setFecTermino(Date fecTermino) {
		this.fecTermino = fecTermino;
	}

	public BigDecimal getIndRecaidaRevaluacion() {
		return this.indRecaidaRevaluacion;
	}

	public void setIndRecaidaRevaluacion(BigDecimal indRecaidaRevaluacion) {
		this.indRecaidaRevaluacion = indRecaidaRevaluacion;
	}

	public BigDecimal getPorRevaluacion() {
		return this.porRevaluacion;
	}

	public void setPorRevaluacion(BigDecimal porRevaluacion) {
		this.porRevaluacion = porRevaluacion;
	}

	public BigDecimal getPorValuacion() {
		return this.porValuacion;
	}

	public void setPorValuacion(BigDecimal porValuacion) {
		this.porValuacion = porValuacion;
	}

	public String getRefFolio() {
		return this.refFolio;
	}

	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}

	public DicCausaIncapacidad getDicCausaIncapacidad() {
		return this.dicCausaIncapacidad;
	}

	public void setDicCausaIncapacidad(DicCausaIncapacidad dicCausaIncapacidad) {
		this.dicCausaIncapacidad = dicCausaIncapacidad;
	}
	
	public DicTipoIncapacidad getDicTipoIncapacidad() {
		return this.dicTipoIncapacidad;
	}

	public void setDicTipoIncapacidad(DicTipoIncapacidad dicTipoIncapacidad) {
		this.dicTipoIncapacidad = dicTipoIncapacidad;
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
	
}