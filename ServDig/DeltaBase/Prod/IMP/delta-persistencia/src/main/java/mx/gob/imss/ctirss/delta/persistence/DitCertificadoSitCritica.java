package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_CERTIFICADO_SIT_CRITICA database table.
 * 
 */
@Entity
@Table(name="DIT_CERTIFICADO_SIT_CRITICA")
public class DitCertificadoSitCritica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_TERMINO_INCAPACIDAD", nullable=false)
	private Date fecTerminoIncapacidad;

	@Column(name="REF_ENFERMEDAD_PADECIDA", nullable=false, length=1024)
	private String refEnfermedadPadecida;
	
	

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PROB_INICIO", nullable=false)
	private Date fecProbInicio;
	

	
	//bi-directional many-to-one association to DitMedicoEspecialidad
	@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="CVE_ID_MEDICO_ESPECIALIDAD")
		private DitMedicoEspecialidad ditMedicoEspecialidad;

	
	
	//bi-directional one-to-one association to DitDocumentoProbatorio
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false)
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
	
	
	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

    public DitCertificadoSitCritica() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public Date getFecTerminoIncapacidad() {
		return this.fecTerminoIncapacidad;
	}

	public void setFecTerminoIncapacidad(Date fecTerminoIncapacidad) {
		this.fecTerminoIncapacidad = fecTerminoIncapacidad;
	}

	public String getRefEnfermedadPadecida() {
		return this.refEnfermedadPadecida;
	}

	public void setRefEnfermedadPadecida(String refEnfermedadPadecida) {
		this.refEnfermedadPadecida = refEnfermedadPadecida;
	}
	
	public DitMedicoEspecialidad getDitMedicoEspecialidad() {
		return this.ditMedicoEspecialidad;
	}

	public void setDitMedicoEspecialidad(DitMedicoEspecialidad ditMedicoEspecialidad) {
		this.ditMedicoEspecialidad = ditMedicoEspecialidad;
	}

	public Date getFecProbInicio() {
		return fecProbInicio;
	}

	public void setFecProbInicio(Date fecProbInicio) {
		this.fecProbInicio = fecProbInicio;
	}


}