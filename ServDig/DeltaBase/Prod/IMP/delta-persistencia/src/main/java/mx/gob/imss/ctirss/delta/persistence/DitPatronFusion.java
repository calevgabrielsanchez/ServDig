package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;


/**
 * The persistent class for the DIT_PATRON_FUSION database table.
 * 
 */
@Entity
@Table(name="DIT_PATRON_FUSION")
@Where(clause = "FEC_REGISTRO_BAJA is null")
public class DitPatronFusion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_PATRON_FUSION_CVEIDPATRONFUSION_GENERATOR", sequenceName = "SEQ_DITPATRONFUSION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PATRON_FUSION_CVEIDPATRONFUSION_GENERATOR")
	@Column(name="CVE_ID_PATRON_FUSION", nullable=false, precision=22)
	private long cveIdPatronFusion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_TRABAJADORES", precision=22)
	private BigDecimal numTrabajadores;

	//bi-directional many-to-one association to DitPatronGeneral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_GENERAL")
	private DitPatronGeneral ditPatronGeneral1;

	//bi-directional many-to-one association to DitPatronGeneral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_FUSIONADO")
	private DitPatronGeneral ditPatronGeneral2;

    public DitPatronFusion() {
    }

	public long getCveIdPatronFusion() {
		return this.cveIdPatronFusion;
	}

	public void setCveIdPatronFusion(long cveIdPatronFusion) {
		this.cveIdPatronFusion = cveIdPatronFusion;
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

	public BigDecimal getNumTrabajadores() {
		return this.numTrabajadores;
	}

	public void setNumTrabajadores(BigDecimal numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}

	public DitPatronGeneral getDitPatronGeneral1() {
		return this.ditPatronGeneral1;
	}

	public void setDitPatronGeneral1(DitPatronGeneral ditPatronGeneral1) {
		this.ditPatronGeneral1 = ditPatronGeneral1;
	}
	
	public DitPatronGeneral getDitPatronGeneral2() {
		return this.ditPatronGeneral2;
	}

	public void setDitPatronGeneral2(DitPatronGeneral ditPatronGeneral2) {
		this.ditPatronGeneral2 = ditPatronGeneral2;
	}
	
}