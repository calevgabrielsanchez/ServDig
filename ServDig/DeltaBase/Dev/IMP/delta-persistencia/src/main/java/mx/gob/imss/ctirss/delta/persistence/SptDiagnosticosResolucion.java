package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_DIAGNOSTICOS_RESOLUCION database table.
 * 
 */
@Entity
@Table(name="SPT_DIAGNOSTICOS_RESOLUCION")
@NamedQuery(name="SptDiagnosticosResolucion.findAll", query="SELECT s FROM SptDiagnosticosResolucion s")
public class SptDiagnosticosResolucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDIAGNOSTICOSRESOLUCION", sequenceName = "SEQ_SPTDIAGNOSTICOSRESOLUCION")
	@GeneratedValue(generator = "SEQ_SPTDIAGNOSTICOSRESOLUCION")
	@Column(name="CVE_ID_DIAGNOSTICO_RESOLUCION")
	private long cveIdDiagnosticoResolucion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptResolucion
	@ManyToOne
	@JoinColumn(name="CVE_ID_RESOLUCION")
	private SptResolucion sptResolucion;

	//bi-directional many-to-one association to StcCDiagnostico514513
	@ManyToOne
	@JoinColumn(name="CVE_DIAGNOSTICO")
	private StcCDiagnostico514513 stcCDiagnostico514513;

	public SptDiagnosticosResolucion() {
	}

	public long getCveIdDiagnosticoResolucion() {
		return this.cveIdDiagnosticoResolucion;
	}

	public void setCveIdDiagnosticoResolucion(long cveIdDiagnosticoResolucion) {
		this.cveIdDiagnosticoResolucion = cveIdDiagnosticoResolucion;
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

	public SptResolucion getSptResolucion() {
		return this.sptResolucion;
	}

	public void setSptResolucion(SptResolucion sptResolucion) {
		this.sptResolucion = sptResolucion;
	}

	public StcCDiagnostico514513 getStcCDiagnostico514513() {
		return this.stcCDiagnostico514513;
	}

	public void setStcCDiagnostico514513(StcCDiagnostico514513 stcCDiagnostico514513) {
		this.stcCDiagnostico514513 = stcCDiagnostico514513;
	}

}