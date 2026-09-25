package mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.base.model.AbstractCrtCobranzaPagos;


@Entity
@Table(name="CRT_COBRANZA_PAGOS")
public class CrtCobranzaPagos extends AbstractCrtCobranzaPagos {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Transient
	private String fecPago;

	
	
	@Transient
	private String fecCarga;

	public String getFecPago() {
		return fecPago;
	}


	public void setFecPago(String fecPago) {
		this.fecPago = fecPago;
	}


	public String getFecCarga() {
		return fecCarga;
	}


	public void setFecCarga(String fecCarga) {
		this.fecCarga = fecCarga;
	}
	
	
	
}
