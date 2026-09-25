package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_OBSTETRICO database table.
 * 
 */
@Entity
@Table(name="DIT_OBSTETRICO")
public class DitObstetrico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_ID_DOCUMENTO_PROBATORIO")
	private long cveIdDocumentoProbatorio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CERTIFICACION_MEDICA", nullable=false)
	private Date fecCertificacionMedica;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PARTO", nullable=false)
	private Date fecParto;
    
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PROB_CONCEPCION", nullable=true)
    private Date fecProbConcepcion;

	//bi-directional many-to-one association to DitMedicoEspecialidad
    @ManyToOne
	@JoinColumn(name="CVE_ID_MEDICO_ESPECIALIDAD")
	private DitMedicoEspecialidad ditMedicoEspecialidad;

	//bi-directional one-to-one association to DitDocumentoProbatorio
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false)
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
    public DitObstetrico() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public Date getFecCertificacionMedica() {
		return this.fecCertificacionMedica;
	}

	public void setFecCertificacionMedica(Date fecCertificacionMedica) {
		this.fecCertificacionMedica = fecCertificacionMedica;
	}

	public Date getFecParto() {
		return this.fecParto;
	}

	public void setFecParto(Date fecParto) {
		this.fecParto = fecParto;
	}

	
	public DitMedicoEspecialidad getDitMedicoEspecialidad() {
		return this.ditMedicoEspecialidad;
	}

	public void setDitMedicoEspecialidad(DitMedicoEspecialidad ditMedicoEspecialidad) {
		this.ditMedicoEspecialidad = ditMedicoEspecialidad;
	}
	
	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

	public Date getFecProbConcepcion() {
		return fecProbConcepcion;
	}

	public void setFecProbConcepcion(Date fecProbConcepcion) {
		this.fecProbConcepcion = fecProbConcepcion;
	}

	
}