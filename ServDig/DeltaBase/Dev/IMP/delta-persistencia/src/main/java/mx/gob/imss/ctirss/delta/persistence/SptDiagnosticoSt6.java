package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_DIAGNOSTICO_ST6 database table.
 * 
 */
@Entity
@Table(name="SPT_DIAGNOSTICO_ST6")
@NamedQuery(name="SptDiagnosticoSt6.findAll", query="SELECT s FROM SptDiagnosticoSt6 s")
public class SptDiagnosticoSt6 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDIAGNOSTICOST6", sequenceName = "SEQ_SPTDIAGNOSTICOST6")
	@GeneratedValue(generator = "SEQ_SPTDIAGNOSTICOST6")
	@Column(name="NUM_SECUENCIA")
	private long numSecuencia;

	@Column(name="CVE_DIAGNOSTICO")
	private String cveDiagnostico;

	@Column(name="DES_ANATOMOFUNCIONAL")
	private String desAnatomofuncional;

	@Column(name="DES_ETIOLOGICO")
	private String desEtiologico;

	@Column(name="DES_NOSOLOGICO")
	private String desNosologico;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_DIAGNOSTICO")
	private BigDecimal idDiagnostico;

	//bi-directional many-to-one association to SptDictamenSt6
	@ManyToOne
	@JoinColumn(name="CVE_ID_DICTAMEN")
	private SptDictamenSt6 sptDictamenSt6;

	public SptDiagnosticoSt6() {
	}

	public long getNumSecuencia() {
		return this.numSecuencia;
	}

	public void setNumSecuencia(long numSecuencia) {
		this.numSecuencia = numSecuencia;
	}

	public String getCveDiagnostico() {
		return this.cveDiagnostico;
	}

	public void setCveDiagnostico(String cveDiagnostico) {
		this.cveDiagnostico = cveDiagnostico;
	}

	public String getDesAnatomofuncional() {
		return this.desAnatomofuncional;
	}

	public void setDesAnatomofuncional(String desAnatomofuncional) {
		this.desAnatomofuncional = desAnatomofuncional;
	}

	public String getDesEtiologico() {
		return this.desEtiologico;
	}

	public void setDesEtiologico(String desEtiologico) {
		this.desEtiologico = desEtiologico;
	}

	public String getDesNosologico() {
		return this.desNosologico;
	}

	public void setDesNosologico(String desNosologico) {
		this.desNosologico = desNosologico;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
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

	public BigDecimal getIdDiagnostico() {
		return this.idDiagnostico;
	}

	public void setIdDiagnostico(BigDecimal idDiagnostico) {
		this.idDiagnostico = idDiagnostico;
	}

	public SptDictamenSt6 getSptDictamenSt6() {
		return this.sptDictamenSt6;
	}

	public void setSptDictamenSt6(SptDictamenSt6 sptDictamenSt6) {
		this.sptDictamenSt6 = sptDictamenSt6;
	}

}