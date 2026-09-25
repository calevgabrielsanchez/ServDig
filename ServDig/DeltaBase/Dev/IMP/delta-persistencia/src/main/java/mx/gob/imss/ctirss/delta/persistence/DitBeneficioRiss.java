package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
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

@Entity
@Table(name = "DIT_BENEFICIO_RISS")
public class DitBeneficioRiss implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_BENEFICIORISS_GENERATOR", sequenceName = "SEQ_DITBENEFICIORISS", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_BENEFICIORISS_GENERATOR")
	@Column(name = "CVE_ID_BENEFICIO_RISS")
	private Long cveIdBeneficioRiss;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_INICIO_RIF")
	private Date fecInicioRif;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FIN_RIF")
	private Date fecFinRif;
	
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

	
	public DitBeneficioRiss(){		
	}
	
	public DitBeneficioRiss(Date fecInicioRif, Date fecFinRif, Date fecRegistroAlta, 
			Date fecRegistroBaja, Date fecRegistroActualizado, DitBeneficio ditBeneficio) {
		super();		
		this.fecInicioRif = fecInicioRif;
		this.fecFinRif = fecFinRif;
		this.fecRegistroAlta = fecRegistroAlta;
		this.fecRegistroBaja = fecRegistroBaja;
		this.fecRegistroActualizado = fecRegistroActualizado;
		this.ditBeneficio = ditBeneficio;
	}

	
	public Long getCveIdBeneficioRiss() {
		return cveIdBeneficioRiss;
	}

	public void setCveIdBeneficioRiss(Long cveIdBeneficioRiss) {
		this.cveIdBeneficioRiss = cveIdBeneficioRiss;
	}

	public Date getFecInicioRif() {
		return fecInicioRif;
	}

	public void setFecInicioRif(Date fecInicioRif) {
		this.fecInicioRif = fecInicioRif;
	}

	public Date getFecFinRif() {
		return fecFinRif;
	}

	public void setFecFinRif(Date fecFinRif) {
		this.fecFinRif = fecFinRif;
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
	
}
