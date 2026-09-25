package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDC_PORCENTAJE database table.
 * 
 */
@Entity
@Table(name="FDC_PORCENTAJE")
public class FdcPorcentaje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_PORCENTAJE", nullable=false, precision=22)
	private long cvePorcentaje;

	@Column(length=100)
	private String descripcion;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_FIN")
	private Date fechaFin;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_INI")
	private Date fechaIni;

	@Column(name="NUM_ANIO", precision=22)
	private BigDecimal numAnio;

	@Column(name="NUM_PORCENTAJE", nullable=false, precision=4, scale=2)
	private BigDecimal numPorcentaje;

	@Column(name="NUM_TOPEMAX", precision=15, scale=2)
	private BigDecimal numTopemax;

	@Column(name="NUM_TOPEMIN", precision=15, scale=2)
	private BigDecimal numTopemin;

	//bi-directional many-to-one association to FdtA1CopEjer
	@OneToMany(mappedBy="fdcPorcentaje")
	private List<FdtA1CopEjer> fdtA1CopEjers;

    public FdcPorcentaje() {
    }

	public long getCvePorcentaje() {
		return this.cvePorcentaje;
	}

	public void setCvePorcentaje(long cvePorcentaje) {
		this.cvePorcentaje = cvePorcentaje;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaIni() {
		return this.fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public BigDecimal getNumAnio() {
		return this.numAnio;
	}

	public void setNumAnio(BigDecimal numAnio) {
		this.numAnio = numAnio;
	}

	public BigDecimal getNumPorcentaje() {
		return this.numPorcentaje;
	}

	public void setNumPorcentaje(BigDecimal numPorcentaje) {
		this.numPorcentaje = numPorcentaje;
	}

	public BigDecimal getNumTopemax() {
		return this.numTopemax;
	}

	public void setNumTopemax(BigDecimal numTopemax) {
		this.numTopemax = numTopemax;
	}

	public BigDecimal getNumTopemin() {
		return this.numTopemin;
	}

	public void setNumTopemin(BigDecimal numTopemin) {
		this.numTopemin = numTopemin;
	}

	public List<FdtA1CopEjer> getFdtA1CopEjers() {
		return this.fdtA1CopEjers;
	}

	public void setFdtA1CopEjers(List<FdtA1CopEjer> fdtA1CopEjers) {
		this.fdtA1CopEjers = fdtA1CopEjers;
	}
	
}