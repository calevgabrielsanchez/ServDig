package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;


//TODO mapear a la tabla cuando este lista
@Entity
@Table(name="DIT_CAMBIO_MASIVO_CLINICA")
public class DitCambioMasivoClinica implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3035477345199302916L;

	@Id
	@SequenceGenerator(name="SEQ_DITCAMBIOMASIVOCLINICA", sequenceName="SEQ_DITCAMBIOMASIVOCLINICA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_DITCAMBIOMASIVOCLINICA")
	@Column(name="CVE_ID_CAMBIO_MASIVO_CLINICA")
	private Long cveIdCambioClinicaMasivo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TRAMITE")
	private DitTramite ditTramite;
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_UMF_CONS_TURNO_MED_O")
	private DitUmfConsTurnoMedico ditUmfConsTurnoMedicoO;
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_UMF_CONS_TURNO_MED_D")
	private DitUmfConsTurnoMedico ditUmfConsTurnoMedicoD;

	@ManyToOne
	@JoinColumn(name="CVE_ID_UMF_COD_POS_ORIGEN")
	private DitUmfCodPo ditUmfCodPoOrigen;
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_UMF_COD_POS_DESTINO")
	private DitUmfCodPo ditUmfCodPoDestino;
	
	
	public Long getCveIdCambioClinicaMasivo() {
		return cveIdCambioClinicaMasivo;
	}
	public void setCveIdCambioClinicaMasivo(Long cveIdCambioClinicaMasivo) {
		this.cveIdCambioClinicaMasivo = cveIdCambioClinicaMasivo;
	}
	public DitTramite getDitTramite() {
		return ditTramite;
	}
	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}
	public DitUmfCodPo getDitUmfCodPoOrigen() {
		return ditUmfCodPoOrigen;
	}
	public void setDitUmfCodPoOrigen(DitUmfCodPo ditUmfCodPoOrigen) {
		this.ditUmfCodPoOrigen = ditUmfCodPoOrigen;
	}
	public DitUmfCodPo getDitUmfCodPoDestino() {
		return ditUmfCodPoDestino;
	}
	public void setDitUmfCodPoDestino(DitUmfCodPo ditUmfCodPoDestino) {
		this.ditUmfCodPoDestino = ditUmfCodPoDestino;
	}
	public DitUmfConsTurnoMedico getDitUmfConsTurnoMedicoO() {
		return ditUmfConsTurnoMedicoO;
	}
	public void setDitUmfConsTurnoMedicoO(
			DitUmfConsTurnoMedico ditUmfConsTurnoMedicoO) {
		this.ditUmfConsTurnoMedicoO = ditUmfConsTurnoMedicoO;
	}
	public DitUmfConsTurnoMedico getDitUmfConsTurnoMedicoD() {
		return ditUmfConsTurnoMedicoD;
	}
	public void setDitUmfConsTurnoMedicoD(
			DitUmfConsTurnoMedico ditUmfConsTurnoMedicoD) {
		this.ditUmfConsTurnoMedicoD = ditUmfConsTurnoMedicoD;
	}	

	
	
	
}
