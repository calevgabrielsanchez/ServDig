package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_DIAGNOSTICO_ST3 database table.
 * 
 */
@Entity
@Table(name="SPT_DIAGNOSTICO_ST3")
@NamedQuery(name="SptDiagnosticoSt3.findAll", query="SELECT s FROM SptDiagnosticoSt3 s")
public class SptDiagnosticoSt3 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDIAGNOSTICOST3", sequenceName = "SEQ_SPTDIAGNOSTICOST3")
	@GeneratedValue(generator = "SEQ_SPTDIAGNOSTICOST3")	
	@Column(name="NUM_SECUENCIA")
	private long numSecuencia;

	@Column(name="CVE_DIAGNOSTICO_513")
	private String cveDiagnostico513;

	@Column(name="CVE_DIAGNOSTICO_514")
	private String cveDiagnostico514;

	@Column(name="DES_OBSERVACION_513")
	private String desObservacion513;

	@Column(name="DES_OBSERVACION_514")
	private String desObservacion514;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Column(name="ID_DIAGNOSTICO")
	private BigDecimal idDiagnostico;

	@Column(name="POR_VALUACION")
	private BigDecimal porValuacion;

	//bi-directional many-to-one association to SptDictamenSt3
	@ManyToOne
	@JoinColumn(name="CVE_ID_DICTAMEN")
	private SptDictamenSt3 sptDictamenSt3;

	public SptDiagnosticoSt3() {
	}

	public long getNumSecuencia() {
		return this.numSecuencia;
	}

	public void setNumSecuencia(long numSecuencia) {
		this.numSecuencia = numSecuencia;
	}

	public String getCveDiagnostico513() {
		return this.cveDiagnostico513;
	}

	public void setCveDiagnostico513(String cveDiagnostico513) {
		this.cveDiagnostico513 = cveDiagnostico513;
	}

	public String getCveDiagnostico514() {
		return this.cveDiagnostico514;
	}

	public void setCveDiagnostico514(String cveDiagnostico514) {
		this.cveDiagnostico514 = cveDiagnostico514;
	}

	public String getDesObservacion513() {
		return this.desObservacion513;
	}

	public void setDesObservacion513(String desObservacion513) {
		this.desObservacion513 = desObservacion513;
	}

	public String getDesObservacion514() {
		return this.desObservacion514;
	}

	public void setDesObservacion514(String desObservacion514) {
		this.desObservacion514 = desObservacion514;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public BigDecimal getIdDiagnostico() {
		return this.idDiagnostico;
	}

	public void setIdDiagnostico(BigDecimal idDiagnostico) {
		this.idDiagnostico = idDiagnostico;
	}

	public BigDecimal getPorValuacion() {
		return this.porValuacion;
	}

	public void setPorValuacion(BigDecimal porValuacion) {
		this.porValuacion = porValuacion;
	}

	public SptDictamenSt3 getSptDictamenSt3() {
		return this.sptDictamenSt3;
	}

	public void setSptDictamenSt3(SptDictamenSt3 sptDictamenSt3) {
		this.sptDictamenSt3 = sptDictamenSt3;
	}

}