package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_CONTRO_FLUJO_ANEXOS database table.
 * 
 */
@Embeddable
public class FdtControFlujoAnexoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_ANEXO", unique=true, nullable=false, precision=22)
	private long idAnexo;

	@Column(name="ID_AVISO", unique=true, nullable=false, precision=22)
	private long idAviso;

    public FdtControFlujoAnexoPK() {
    }
	public long getIdAnexo() {
		return this.idAnexo;
	}
	public void setIdAnexo(long idAnexo) {
		this.idAnexo = idAnexo;
	}
	public long getIdAviso() {
		return this.idAviso;
	}
	public void setIdAviso(long idAviso) {
		this.idAviso = idAviso;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtControFlujoAnexoPK)) {
			return false;
		}
		FdtControFlujoAnexoPK castOther = (FdtControFlujoAnexoPK)other;
		return 
			(this.idAnexo == castOther.idAnexo)
			&& (this.idAviso == castOther.idAviso);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idAnexo ^ (this.idAnexo >>> 32)));
		hash = hash * prime + ((int) (this.idAviso ^ (this.idAviso >>> 32)));
		
		return hash;
    }
}