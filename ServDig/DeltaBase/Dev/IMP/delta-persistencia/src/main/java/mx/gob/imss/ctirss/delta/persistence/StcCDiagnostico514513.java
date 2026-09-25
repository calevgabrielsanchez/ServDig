package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the STC_C_DIAGNOSTICO514513 database table.
 * 
 */
@Entity
@Table(name="STC_C_DIAGNOSTICO514513")
@NamedQuery(name="StcCDiagnostico514513.findAll", query="SELECT s FROM StcCDiagnostico514513 s")
public class StcCDiagnostico514513 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STC_C_DIAGNOSTICO514513_CVEDIAGNOSTICO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STC_C_DIAGNOSTICO514513_CVEDIAGNOSTICO_GENERATOR")
	@Column(name="CVE_DIAGNOSTICO")
	private String cveDiagnostico;

	@Column(name="DES_DIAGNOSTICO")
	private String desDiagnostico;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptDiagnosticosResolucion
	@OneToMany(mappedBy="stcCDiagnostico514513")
	private List<SptDiagnosticosResolucion> sptDiagnosticosResolucions;

	public StcCDiagnostico514513() {
	}

	public String getCveDiagnostico() {
		return this.cveDiagnostico;
	}

	public void setCveDiagnostico(String cveDiagnostico) {
		this.cveDiagnostico = cveDiagnostico;
	}

	public String getDesDiagnostico() {
		return this.desDiagnostico;
	}

	public void setDesDiagnostico(String desDiagnostico) {
		this.desDiagnostico = desDiagnostico;
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

	public List<SptDiagnosticosResolucion> getSptDiagnosticosResolucions() {
		return this.sptDiagnosticosResolucions;
	}

	public void setSptDiagnosticosResolucions(List<SptDiagnosticosResolucion> sptDiagnosticosResolucions) {
		this.sptDiagnosticosResolucions = sptDiagnosticosResolucions;
	}

	public SptDiagnosticosResolucion addSptDiagnosticosResolucion(SptDiagnosticosResolucion sptDiagnosticosResolucion) {
		getSptDiagnosticosResolucions().add(sptDiagnosticosResolucion);
		sptDiagnosticosResolucion.setStcCDiagnostico514513(this);

		return sptDiagnosticosResolucion;
	}

	public SptDiagnosticosResolucion removeSptDiagnosticosResolucion(SptDiagnosticosResolucion sptDiagnosticosResolucion) {
		getSptDiagnosticosResolucions().remove(sptDiagnosticosResolucion);
		sptDiagnosticosResolucion.setStcCDiagnostico514513(null);

		return sptDiagnosticosResolucion;
	}

}