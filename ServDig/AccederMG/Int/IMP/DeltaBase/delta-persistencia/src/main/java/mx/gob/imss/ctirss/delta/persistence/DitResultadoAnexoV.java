package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_RESULTADO_ANEXOV database table.
 * 
 */
@Entity
@Table(name = "DIT_RESULTADO_ANEXOV")
public class DitResultadoAnexoV implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -6901716133834190914L;

	@Id
	@Column(name = "CVE_ID_ANALISIS")
	private long cveIdAnalisis;

	@Column(name = "NUM_FOLIO_ANEXOV")
	private String numFolioAnexoV;

	@Column(name = "NUM_FOLIO_FISCALIZACION")
	private String numFolioFiscalizacion;

	@Column(name = "IND_RECTIFICADO")
	private Boolean indRectificado;

	public long getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	public void setCveIdAnalisis(long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public String getNumFolioAnexoV() {
		return numFolioAnexoV;
	}

	public void setNumFolioAnexoV(String numFolioAnexoV) {
		this.numFolioAnexoV = numFolioAnexoV;
	}

	public String getNumFolioFiscalizacion() {
		return numFolioFiscalizacion;
	}

	public void setNumFolioFiscalizacion(String numFolioFiscalizacion) {
		this.numFolioFiscalizacion = numFolioFiscalizacion;
	}

	public Boolean getIndRectificado() {
		return indRectificado;
	}

	public void setIndRectificado(Boolean indRectificado) {
		this.indRectificado = indRectificado;
	}

}
