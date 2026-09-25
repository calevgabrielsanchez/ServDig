package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SPC_LUGAR_PAGO database table.
 * 
 */
@Embeddable
public class SpcLugarPagoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_SUCURSAL_LUGAR_PAGO")
	private String idSucursalLugarPago;

	@Column(name="ID_ENTIDAD_PAGO")
	private String idEntidadPago;

	@Column(name="CVE_ID_SUBDELEGACION")
	private long cveIdSubdelegacion;

    public SpcLugarPagoPK() {
    }
	public String getIdSucursalLugarPago() {
		return this.idSucursalLugarPago;
	}
	public void setIdSucursalLugarPago(String idSucursalLugarPago) {
		this.idSucursalLugarPago = idSucursalLugarPago;
	}
	public String getIdEntidadPago() {
		return this.idEntidadPago;
	}
	public void setIdEntidadPago(String idEntidadPago) {
		this.idEntidadPago = idEntidadPago;
	}
	public long getCveIdSubdelegacion() {
		return this.cveIdSubdelegacion;
	}
	public void setCveIdSubdelegacion(long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpcLugarPagoPK)) {
			return false;
		}
		SpcLugarPagoPK castOther = (SpcLugarPagoPK)other;
		return 
			this.idSucursalLugarPago.equals(castOther.idSucursalLugarPago)
			&& this.idEntidadPago.equals(castOther.idEntidadPago)
			&& (this.cveIdSubdelegacion == castOther.cveIdSubdelegacion);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idSucursalLugarPago.hashCode();
		hash = hash * prime + this.idEntidadPago.hashCode();
		hash = hash * prime + ((int) (this.cveIdSubdelegacion ^ (this.cveIdSubdelegacion >>> 32)));
		
		return hash;
    }
}