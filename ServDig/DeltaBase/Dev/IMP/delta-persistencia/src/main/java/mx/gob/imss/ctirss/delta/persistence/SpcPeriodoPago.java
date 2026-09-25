package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="SPC_PERIODO_PAGO")
public class SpcPeriodoPago implements Serializable{

	private static final long serialVersionUID = 4188505224521990641L;

	@Id
	@Column(name="ID_PERIODO_PAGO")
	private Long idPeriodoPago;
	
	@Column(name="ID_TIPO_ENTIDAD_PAGO")
	private Long idTipoEntidadPago;
	
	@Column(name="CVE_ID_DELEGACION")
	private String cveIdDelegacion;

	public Long getIdPeriodoPago() {
		return idPeriodoPago;
	}

	public void setIdPeriodoPago(Long idPeriodoPago) {
		this.idPeriodoPago = idPeriodoPago;
	}

	public Long getIdTipoEntidadPago() {
		return idTipoEntidadPago;
	}

	public void setIdTipoEntidadPago(Long idTipoEntidadPago) {
		this.idTipoEntidadPago = idTipoEntidadPago;
	}

	public String getCveIdDelegacion() {
		return cveIdDelegacion;
	}

	public void setCveIdDelegacion(String cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

}
