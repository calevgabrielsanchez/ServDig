package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A4_REMUNERACION database table.
 * 
 */
@Embeddable
public class FdtA4RemuneracionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="NU_CONSECUTIVO", unique=true, nullable=false, precision=22)
	private long nuConsecutivo;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="REG_PATRON", unique=true, nullable=false, length=8)
	private String regPatron;

	@Column(name="CVE_MODAL", unique=true, nullable=false, precision=2)
	private long cveModal;

	@Column(name="ID_REMUNERACION", unique=true, nullable=false, precision=22)
	private long idRemuneracion;

    public FdtA4RemuneracionPK() {
    }
	public long getNuConsecutivo() {
		return this.nuConsecutivo;
	}
	public void setNuConsecutivo(long nuConsecutivo) {
		this.nuConsecutivo = nuConsecutivo;
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
	public long getIdRemuneracion() {
		return this.idRemuneracion;
	}
	public void setIdRemuneracion(long idRemuneracion) {
		this.idRemuneracion = idRemuneracion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA4RemuneracionPK)) {
			return false;
		}
		FdtA4RemuneracionPK castOther = (FdtA4RemuneracionPK)other;
		return 
			(this.nuConsecutivo == castOther.nuConsecutivo)
			&& (this.idDictamen == castOther.idDictamen)
			&& this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal)
			&& (this.idRemuneracion == castOther.idRemuneracion);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.nuConsecutivo ^ (this.nuConsecutivo >>> 32)));
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		hash = hash * prime + ((int) (this.idRemuneracion ^ (this.idRemuneracion >>> 32)));
		
		return hash;
    }
}