package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_BALANCE_PRESTACION database table.
 * 
 */
@Entity
@Table(name="DIT_BALANCE_PRESTACION")
public class DitBalancePrestacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_BALANCE_PRESTACION", nullable=false, precision=22)
	private long cveIdBalancePrestacion;

	@Column(name="CAN_SEMANA_EQUIVALENTE", precision=22)
	private BigDecimal canSemanaEquivalente;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PRESTACION")
	private Date fecPrestacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_SINIESTRO")
	private Date fecSiniestro;

	@Column(name="IMP_EQUIVALENTE", length=50)
	private String impEquivalente;

	@Column(name="IMP_SALDO_RCV", length=50)
	private String impSaldoRcv;

	//bi-directional many-to-one association to DitAseguradoPension
	@OneToMany(mappedBy="ditBalancePrestacion")
	private List<DitAseguradoPension> ditAseguradoPensions;

	//bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASIGNACION_NSS")
	private DitAsignacionNss ditAsignacionNss;

	//bi-directional many-to-one association to DicTipoRetiro
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_RETIRO_REINTEGRO")
	private DicTipoRetiro dicTipoRetiro;

	//bi-directional many-to-one association to DitIncapacidad
	@OneToMany(mappedBy="ditBalancePrestacion")
	private List<DitIncapacidad> ditIncapacidads;

	// bi-directional many-to-one association to DitLlaveAsegurado
	@OneToMany(mappedBy = "ditBalancePrestacion")
	private List<DitLlaveAsegurado> ditLlaveAsegurados;

    public List<DitLlaveAsegurado> getDitLlaveAsegurados() {
		return ditLlaveAsegurados;
	}

	public void setDitLlaveAsegurados(List<DitLlaveAsegurado> ditLlaveAsegurados) {
		this.ditLlaveAsegurados = ditLlaveAsegurados;
	}

	public DitBalancePrestacion() {
    }

	public long getCveIdBalancePrestacion() {
		return this.cveIdBalancePrestacion;
	}

	public void setCveIdBalancePrestacion(long cveIdBalancePrestacion) {
		this.cveIdBalancePrestacion = cveIdBalancePrestacion;
	}

	public BigDecimal getCanSemanaEquivalente() {
		return this.canSemanaEquivalente;
	}

	public void setCanSemanaEquivalente(BigDecimal canSemanaEquivalente) {
		this.canSemanaEquivalente = canSemanaEquivalente;
	}

	public Date getFecPrestacion() {
		return this.fecPrestacion;
	}

	public void setFecPrestacion(Date fecPrestacion) {
		this.fecPrestacion = fecPrestacion;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecSiniestro() {
		return this.fecSiniestro;
	}

	public void setFecSiniestro(Date fecSiniestro) {
		this.fecSiniestro = fecSiniestro;
	}

	public String getImpEquivalente() {
		return this.impEquivalente;
	}

	public void setImpEquivalente(String impEquivalente) {
		this.impEquivalente = impEquivalente;
	}

	public String getImpSaldoRcv() {
		return this.impSaldoRcv;
	}

	public void setImpSaldoRcv(String impSaldoRcv) {
		this.impSaldoRcv = impSaldoRcv;
	}

	public List<DitAseguradoPension> getDitAseguradoPensions() {
		return this.ditAseguradoPensions;
	}

	public void setDitAseguradoPensions(List<DitAseguradoPension> ditAseguradoPensions) {
		this.ditAseguradoPensions = ditAseguradoPensions;
	}
	
	public DitAsignacionNss getDitAsignacionNss() {
		return this.ditAsignacionNss;
	}

	public void setDitAsignacionNss(DitAsignacionNss ditAsignacionNss) {
		this.ditAsignacionNss = ditAsignacionNss;
	}
	
	public DicTipoRetiro getDicTipoRetiro() {
		return this.dicTipoRetiro;
	}

	public void setDicTipoRetiro(DicTipoRetiro dicTipoRetiro) {
		this.dicTipoRetiro = dicTipoRetiro;
	}
	
	public List<DitIncapacidad> getDitIncapacidads() {
		return this.ditIncapacidads;
	}

	public void setDitIncapacidads(List<DitIncapacidad> ditIncapacidads) {
		this.ditIncapacidads = ditIncapacidads;
	}
	
}