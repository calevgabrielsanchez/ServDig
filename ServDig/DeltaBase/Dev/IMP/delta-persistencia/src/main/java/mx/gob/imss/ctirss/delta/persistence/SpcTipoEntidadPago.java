package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the SPC_TIPO_ENTIDAD_PAGO database table.
 * 
 */
@Entity
@Table(name="SPC_TIPO_ENTIDAD_PAGO")
public class SpcTipoEntidadPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_TIPO_ENTIDAD_PAGO")
	private String idTipoEntidadPago;

	@Column(name="DES_TIPO_ENTIDAD_PAGO")
	private String desTipoEntidadPago;

	//bi-directional many-to-one association to SpcEntidadPago
	@OneToMany(mappedBy="spcTipoEntidadPago")
	private Set<SpcEntidadPago> spcEntidadPagos;

    public SpcTipoEntidadPago() {
    }

	public String getIdTipoEntidadPago() {
		return this.idTipoEntidadPago;
	}

	public void setIdTipoEntidadPago(String idTipoEntidadPago) {
		this.idTipoEntidadPago = idTipoEntidadPago;
	}

	public String getDesTipoEntidadPago() {
		return this.desTipoEntidadPago;
	}

	public void setDesTipoEntidadPago(String desTipoEntidadPago) {
		this.desTipoEntidadPago = desTipoEntidadPago;
	}

	public Set<SpcEntidadPago> getSpcEntidadPagos() {
		return this.spcEntidadPagos;
	}

	public void setSpcEntidadPagos(Set<SpcEntidadPago> spcEntidadPagos) {
		this.spcEntidadPagos = spcEntidadPagos;
	}
	
}