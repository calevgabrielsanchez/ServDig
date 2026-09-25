package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CGT_GESTIONPAGOS database table.
 * 
 */
@Entity
@Table(name="CGT_GESTIONPAGOS")
public class CgtGestionpago implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CgtGestionpagoPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PAGO")
	private Date fhPago;

	@Column(name="IM_ACTUALIZACION", precision=12, scale=2)
	private BigDecimal imActualizacion;

	@Column(name="IM_RECARGOS", precision=12, scale=2)
	private BigDecimal imRecargos;

	@Column(name="IM_SUERTEPRIN", precision=12, scale=2)
	private BigDecimal imSuerteprin;

	@Column(name="IM_TOTAL", precision=12, scale=2)
	private BigDecimal imTotal;

	@Column(name="NU_TPREGISTRO", precision=22)
	private BigDecimal nuTpregistro;

	@Column(name="NUM_TPFOLIO", precision=22)
	private BigDecimal numTpfolio;

	//bi-directional many-to-one association to CgtGestionauxregpat
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="TX_NUMAVISO", referencedColumnName="TX_NUMAVISO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="TX_REGPATRONAL", referencedColumnName="TX_REGPATRONAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="TX_REGPATRONALINSC", referencedColumnName="TX_REGPATRONALINSC", nullable=false, insertable=false, updatable=false)
		})
	private CgtGestionauxregpat cgtGestionauxregpat;

    public CgtGestionpago() {
    }

	public CgtGestionpagoPK getId() {
		return this.id;
	}

	public void setId(CgtGestionpagoPK id) {
		this.id = id;
	}
	
	public Date getFhPago() {
		return this.fhPago;
	}

	public void setFhPago(Date fhPago) {
		this.fhPago = fhPago;
	}

	public BigDecimal getImActualizacion() {
		return this.imActualizacion;
	}

	public void setImActualizacion(BigDecimal imActualizacion) {
		this.imActualizacion = imActualizacion;
	}

	public BigDecimal getImRecargos() {
		return this.imRecargos;
	}

	public void setImRecargos(BigDecimal imRecargos) {
		this.imRecargos = imRecargos;
	}

	public BigDecimal getImSuerteprin() {
		return this.imSuerteprin;
	}

	public void setImSuerteprin(BigDecimal imSuerteprin) {
		this.imSuerteprin = imSuerteprin;
	}

	public BigDecimal getImTotal() {
		return this.imTotal;
	}

	public void setImTotal(BigDecimal imTotal) {
		this.imTotal = imTotal;
	}

	public BigDecimal getNuTpregistro() {
		return this.nuTpregistro;
	}

	public void setNuTpregistro(BigDecimal nuTpregistro) {
		this.nuTpregistro = nuTpregistro;
	}

	public BigDecimal getNumTpfolio() {
		return this.numTpfolio;
	}

	public void setNumTpfolio(BigDecimal numTpfolio) {
		this.numTpfolio = numTpfolio;
	}

	public CgtGestionauxregpat getCgtGestionauxregpat() {
		return this.cgtGestionauxregpat;
	}

	public void setCgtGestionauxregpat(CgtGestionauxregpat cgtGestionauxregpat) {
		this.cgtGestionauxregpat = cgtGestionauxregpat;
	}
	
}