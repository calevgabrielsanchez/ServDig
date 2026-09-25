package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_IMPUGNACION database table.
 * 
 */
@Embeddable
public class FdtImpugnacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_SANCION", unique=true, nullable=false, precision=22)
	private long idSancion;

	@Column(name="ID_IMPUGNACION", unique=true, nullable=false, precision=22)
	private long idImpugnacion;

    public FdtImpugnacionPK() {
    }
	public long getIdSancion() {
		return this.idSancion;
	}
	public void setIdSancion(long idSancion) {
		this.idSancion = idSancion;
	}
	public long getIdImpugnacion() {
		return this.idImpugnacion;
	}
	public void setIdImpugnacion(long idImpugnacion) {
		this.idImpugnacion = idImpugnacion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtImpugnacionPK)) {
			return false;
		}
		FdtImpugnacionPK castOther = (FdtImpugnacionPK)other;
		return 
			(this.idSancion == castOther.idSancion)
			&& (this.idImpugnacion == castOther.idImpugnacion);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idSancion ^ (this.idSancion >>> 32)));
		hash = hash * prime + ((int) (this.idImpugnacion ^ (this.idImpugnacion >>> 32)));
		
		return hash;
    }
}