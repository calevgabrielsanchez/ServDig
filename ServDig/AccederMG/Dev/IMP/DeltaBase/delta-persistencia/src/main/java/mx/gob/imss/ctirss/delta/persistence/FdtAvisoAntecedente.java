package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FDT_AVISO_ANTECEDENTES database table.
 * 
 */
@Entity
@Table(name="FDT_AVISO_ANTECEDENTES")
public class FdtAvisoAntecedente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CV_FOLIO_ORIGEN", length=16)
	private String cvFolioOrigen;

	@Column(name="SISTEMA_ORIGEN", length=12)
	private String sistemaOrigen;

	@Column(name="TIPO_ANTECEDENTE", length=25)
	private String tipoAntecedente;

	//bi-directional many-to-one association to FdtAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AVISO", nullable=false)
	private FdtAviso fdtAviso;

    public FdtAvisoAntecedente() {
    }

	public String getCvFolioOrigen() {
		return this.cvFolioOrigen;
	}

	public void setCvFolioOrigen(String cvFolioOrigen) {
		this.cvFolioOrigen = cvFolioOrigen;
	}

	public String getSistemaOrigen() {
		return this.sistemaOrigen;
	}

	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}

	public String getTipoAntecedente() {
		return this.tipoAntecedente;
	}

	public void setTipoAntecedente(String tipoAntecedente) {
		this.tipoAntecedente = tipoAntecedente;
	}

	public FdtAviso getFdtAviso() {
		return this.fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}
	
}