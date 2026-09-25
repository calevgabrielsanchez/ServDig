package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "DIT_DESCUENTO_BENEFICIO")
public class DitDescuentoBeneficio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_DESCUENTOBENEFICIO_GENERATOR", sequenceName = "SEQ_DITDESCUENTOBENEFICIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_DESCUENTOBENEFICIO_GENERATOR")
	@Column(name = "CVE_ID_DESCUENTO_BENEFICIO")
	private Long cveIdDescuentoBeneficio;
	
	@Column(name="NUM_PORCENTAJE", precision=5, scale=2)
	private BigDecimal numPorcentaje;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_INICIO")
	private Date fecInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FIN")
	private Date fecFin;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;	  
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;	
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_BENEFICIO")
	private DitBeneficio ditBeneficio;

	public DitDescuentoBeneficio() {
	}
	
	public DitDescuentoBeneficio(BigDecimal numPorcentaje, Date fecInicio,
			Date fecFin, Date fecRegistroAlta, Date fecRegistroBaja,
			Date fecRegistroActualizado, DitBeneficio ditBeneficio) {
		super();
		this.numPorcentaje = numPorcentaje;
		this.fecInicio = fecInicio;
		this.fecFin = fecFin;
		this.fecRegistroAlta = fecRegistroAlta;
		this.fecRegistroBaja = fecRegistroBaja;
		this.fecRegistroActualizado = fecRegistroActualizado;
		this.ditBeneficio = ditBeneficio;
	}

	
	public Long getCveIdDescuentoBeneficio() {
		return cveIdDescuentoBeneficio;
	}

	public void setCveIdDescuentoBeneficio(Long cveIdDescuentoBeneficio) {
		this.cveIdDescuentoBeneficio = cveIdDescuentoBeneficio;
	}

	public BigDecimal getNumPorcentaje() {
		return numPorcentaje;
	}

	public void setNumPorcentaje(BigDecimal numPorcentaje) {
		this.numPorcentaje = numPorcentaje;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public DitBeneficio getDitBeneficio() {
		return ditBeneficio;
	}

	public void setDitBeneficio(DitBeneficio ditBeneficio) {
		this.ditBeneficio = ditBeneficio;
	}

	@Transient
	private int anioFiscal;
	
	public int getAnioFiscal() {
		return anioFiscal;
	}

	public void setAnioFiscal(int anioFiscal) {
		this.anioFiscal = anioFiscal;
	}
	
}
