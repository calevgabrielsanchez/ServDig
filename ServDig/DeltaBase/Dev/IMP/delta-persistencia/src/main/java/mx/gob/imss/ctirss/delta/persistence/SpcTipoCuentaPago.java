package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SPC_TIPO_CUENTA_PAGO")
public class SpcTipoCuentaPago implements Serializable{

	private static final long serialVersionUID = -1153381775496610964L;

	@Id
	@Column(name="ID_TIPO_CUENTA_PAGO")
	private Long idTipoCuentaPago;
	
	@Column(name="DES_TIPO_CUENTA_PAGO")
	private String desTipoCuentaPago;
	
	@Column(name="ID_ENTIDAD_PAGO")
	private Long idEntidadPago;

	public Long getIdTipoCuentaPago() {
		return idTipoCuentaPago;
	}

	public void setIdTipoCuentaPago(Long idTipoCuentaPago) {
		this.idTipoCuentaPago = idTipoCuentaPago;
	}

	public String getDesTipoCuentaPago() {
		return desTipoCuentaPago;
	}

	public void setDesTipoCuentaPago(String desTipoCuentaPago) {
		this.desTipoCuentaPago = desTipoCuentaPago;
	}

	public Long getIdEntidadPago() {
		return idEntidadPago;
	}

	public void setIdEntidadPago(Long idEntidadPago) {
		this.idEntidadPago = idEntidadPago;
	}
	
	
}
