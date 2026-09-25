package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PASAPORTE database table.
 * 
 */
@Entity
@Table(name="DIT_PASAPORTE")
public class DitPasaporte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CADUCIDAD", nullable=false)
	private Date fecCaducidad;

	@Column(name="NUM_PASAPORTE", nullable=false, length=10)
	private String numPasaporte;

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
    public DitPasaporte() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public Date getFecCaducidad() {
		return this.fecCaducidad;
	}

	public void setFecCaducidad(Date fecCaducidad) {
		this.fecCaducidad = fecCaducidad;
	}

	public String getNumPasaporte() {
		return this.numPasaporte;
	}

	public void setNumPasaporte(String numPasaporte) {
		this.numPasaporte = numPasaporte;
	}


}