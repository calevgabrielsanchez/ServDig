package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SPC_PERIODO_PAGO_NOMINA database table.
 * 
 */
@Embeddable
public class SpcPeriodoPagoNominaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_PERIODO_PAGO")
	private String idPeriodoPago;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="ID_TIPO_ENTIDAD_PAGO")
	private String idTipoEntidadPago;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_NOMINA")
	private java.util.Date fecNomina;

    public SpcPeriodoPagoNominaPK() {
    }
	public String getIdPeriodoPago() {
		return this.idPeriodoPago;
	}
	public void setIdPeriodoPago(String idPeriodoPago) {
		this.idPeriodoPago = idPeriodoPago;
	}
	public String getCveDelegacion() {
		return this.cveDelegacion;
	}
	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public String getIdTipoEntidadPago() {
		return this.idTipoEntidadPago;
	}
	public void setIdTipoEntidadPago(String idTipoEntidadPago) {
		this.idTipoEntidadPago = idTipoEntidadPago;
	}
	public java.util.Date getFecNomina() {
		return this.fecNomina;
	}
	public void setFecNomina(java.util.Date fecNomina) {
		this.fecNomina = fecNomina;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpcPeriodoPagoNominaPK)) {
			return false;
		}
		SpcPeriodoPagoNominaPK castOther = (SpcPeriodoPagoNominaPK)other;
		return 
			this.idPeriodoPago.equals(castOther.idPeriodoPago)
			&& this.cveDelegacion.equals(castOther.cveDelegacion)
			&& this.idTipoEntidadPago.equals(castOther.idTipoEntidadPago)
			&& this.fecNomina.equals(castOther.fecNomina);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPeriodoPago.hashCode();
		hash = hash * prime + this.cveDelegacion.hashCode();
		hash = hash * prime + this.idTipoEntidadPago.hashCode();
		hash = hash * prime + this.fecNomina.hashCode();
		
		return hash;
    }
}