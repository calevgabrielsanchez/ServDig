package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_CONSTRUCCION_SUBCONTRATIST database table.
 * 
 */
@Embeddable
public class FdtConstruccionSubcontratistPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_OBRA", unique=true, nullable=false, precision=22)
	private long nuObra;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="REG_PATRON", unique=true, nullable=false, length=8)
	private String regPatron;

	@Column(name="CVE_MODAL", unique=true, nullable=false, precision=2)
	private long cveModal;

	@Column(name="REG_PATRON_SUBCONTRATISTA", unique=true, nullable=false, length=10)
	private String regPatronSubcontratista;

	@Column(name="CVE_MODAL_SUBCONTRATISTA", unique=true, nullable=false, precision=22)
	private long cveModalSubcontratista;

	@Column(name="FASE_SUBCONTRATADA", unique=true, nullable=false, precision=22)
	private long faseSubcontratada;

    public FdtConstruccionSubcontratistPK() {
    }
	public long getNuObra() {
		return this.nuObra;
	}
	public void setNuObra(long nuObra) {
		this.nuObra = nuObra;
	}
	public long getIdDictamen() {
		return this.idDictamen;
	}
	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}
	public String getRegPatron() {
		return this.regPatron;
	}
	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}
	public long getCveModal() {
		return this.cveModal;
	}
	public void setCveModal(long cveModal) {
		this.cveModal = cveModal;
	}
	public String getRegPatronSubcontratista() {
		return this.regPatronSubcontratista;
	}
	public void setRegPatronSubcontratista(String regPatronSubcontratista) {
		this.regPatronSubcontratista = regPatronSubcontratista;
	}
	public long getCveModalSubcontratista() {
		return this.cveModalSubcontratista;
	}
	public void setCveModalSubcontratista(long cveModalSubcontratista) {
		this.cveModalSubcontratista = cveModalSubcontratista;
	}
	public long getFaseSubcontratada() {
		return this.faseSubcontratada;
	}
	public void setFaseSubcontratada(long faseSubcontratada) {
		this.faseSubcontratada = faseSubcontratada;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtConstruccionSubcontratistPK)) {
			return false;
		}
		FdtConstruccionSubcontratistPK castOther = (FdtConstruccionSubcontratistPK)other;
		return 
			(this.nuObra == castOther.nuObra)
			&& (this.idDictamen == castOther.idDictamen)
			&& this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal)
			&& this.regPatronSubcontratista.equals(castOther.regPatronSubcontratista)
			&& (this.cveModalSubcontratista == castOther.cveModalSubcontratista)
			&& (this.faseSubcontratada == castOther.faseSubcontratada);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.nuObra ^ (this.nuObra >>> 32)));
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		hash = hash * prime + this.regPatronSubcontratista.hashCode();
		hash = hash * prime + ((int) (this.cveModalSubcontratista ^ (this.cveModalSubcontratista >>> 32)));
		hash = hash * prime + ((int) (this.faseSubcontratada ^ (this.faseSubcontratada >>> 32)));
		
		return hash;
    }
}