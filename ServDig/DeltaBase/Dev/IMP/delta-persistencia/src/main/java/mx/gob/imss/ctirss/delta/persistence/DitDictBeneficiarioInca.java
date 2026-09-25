package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_DICT_BENEFICIARIO_INCA database table.
 * 
 */
@Entity
@Table(name="DIT_DICT_BENEFICIARIO_INCA")
public class DitDictBeneficiarioInca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_INICIO_ENFERMEDAD", nullable=false)
	private Date fecInicioEnfermedad;
    

    

	@Column(name="REF_DIAGNOSTICO_PADECIMIENTO", nullable=false, length=1024)
	private String refDiagnosticoPadecimiento;
	
	@Column(name="IND_INCAPACIDAD_VIGENTE")
	private Integer indIncapacidadVigente;
	
	@Column(name="NUM_GRADO_INCAPACIDAD")
	private Integer numGradoIncaoacidad;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DELEGACION")
	private DicDelegacion dicDelegacion;
	
	

	
	//bi-directional many-to-one association to DitMedicoEspecialidad
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MEDICO_ESPECIALIDAD")
	private DitMedicoEspecialidad ditMedicoEspecialidad;


	//bi-directional many-to-one association to DicUmf
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_UMF")
	private DicUmf dicUmf;

	//bi-directional one-to-one association to DitDocumentoProbatorio
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false)
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
    public DitDictBeneficiarioInca() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public Date getFecInicioEnfermedad() {
		return this.fecInicioEnfermedad;
	}

	public void setFecInicioEnfermedad(Date fecInicioEnfermedad) {
		this.fecInicioEnfermedad = fecInicioEnfermedad;
	}

	public String getRefDiagnosticoPadecimiento() {
		return this.refDiagnosticoPadecimiento;
	}

	public void setRefDiagnosticoPadecimiento(String refDiagnosticoPadecimiento) {
		this.refDiagnosticoPadecimiento = refDiagnosticoPadecimiento;
	}

	public DitMedicoEspecialidad getDitMedicoEspecialidad() {
		return this.ditMedicoEspecialidad;
	}

	public void setDitMedicoEspecialidad(DitMedicoEspecialidad ditMedicoEspecialidad) {
		this.ditMedicoEspecialidad = ditMedicoEspecialidad;
	}
	
	public DicUmf getDicUmf() {
		return this.dicUmf;
	}

	public void setDicUmf(DicUmf dicUmf) {
		this.dicUmf = dicUmf;
	}

	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

	public Integer getIndIncapacidadVigente() {
		return indIncapacidadVigente;
	}

	public void setIndIncapacidadVigente(Integer indIncapacidadVigente) {
		this.indIncapacidadVigente = indIncapacidadVigente;
	}

	public Integer getNumGradoIncaoacidad() {
		return numGradoIncaoacidad;
	}

	public void setNumGradoIncaoacidad(Integer numGradoIncaoacidad) {
		this.numGradoIncaoacidad = numGradoIncaoacidad;
	}

	public DicDelegacion getDicDelegacion() {
		return dicDelegacion;
	}

	public void setDicDelegacion(DicDelegacion dicDelegacion) {
		this.dicDelegacion = dicDelegacion;
	}


	

}