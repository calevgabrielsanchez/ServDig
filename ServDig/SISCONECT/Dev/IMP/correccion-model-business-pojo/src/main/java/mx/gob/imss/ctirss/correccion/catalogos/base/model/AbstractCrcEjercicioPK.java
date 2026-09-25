package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.io.Serializable;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrcPatronPK;

public class AbstractCrcEjercicioPK implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long cveEjercicio;

	private Long cveAcexoCorrPat;

    public AbstractCrcEjercicioPK() {
    }

	public Long getCveEjercicio() {
		return cveEjercicio;
	}

	public void setCveEjercicio(Long cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}

	public Long getCveAcexoCorrPat() {
		return cveAcexoCorrPat;
	}

	public void setCveAcexoCorrPat(Long cveAcexoCorrPat) {
		this.cveAcexoCorrPat = cveAcexoCorrPat;
	}



	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AbstractCrcPatronPK)) {
			return false;
		}
		AbstractCrcEjercicioPK castOther = (AbstractCrcEjercicioPK)other;
		return 
			this.cveEjercicio.equals(castOther.cveEjercicio)
			&& (this.cveAcexoCorrPat == castOther.cveAcexoCorrPat);

    }
    
	public int hashCode() {
		return this.cveEjercicio.hashCode()+this.cveEjercicio.hashCode();
    }
}
