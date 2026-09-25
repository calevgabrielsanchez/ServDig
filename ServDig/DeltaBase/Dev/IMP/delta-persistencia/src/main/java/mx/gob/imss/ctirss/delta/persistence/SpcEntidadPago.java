package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the SPC_ENTIDAD_PAGO database table.
 * 
 */
@Entity
@Table(name="SPC_ENTIDAD_PAGO")
public class SpcEntidadPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ENTIDAD_PAGO")
	private String idEntidadPago;

	//bi-directional many-to-one association to SpcTipoEntidadPago
    @ManyToOne
	@JoinColumn(name="ID_TIPO_ENTIDAD_PAGO")
	private SpcTipoEntidadPago spcTipoEntidadPago;

	//bi-directional many-to-one association to SpcLugarPago
	@OneToMany(mappedBy="spcEntidadPago")
	private Set<SpcLugarPago> spcLugarPagos;
	
	@Column(name="DES_ENTIDAD_PAGO")
	private String desEntidadPago;

    public SpcEntidadPago() {
    }

	public String getIdEntidadPago() {
		return this.idEntidadPago;
	}

	public void setIdEntidadPago(String idEntidadPago) {
		this.idEntidadPago = idEntidadPago;
	}

	public SpcTipoEntidadPago getSpcTipoEntidadPago() {
		return this.spcTipoEntidadPago;
	}

	public void setSpcTipoEntidadPago(SpcTipoEntidadPago spcTipoEntidadPago) {
		this.spcTipoEntidadPago = spcTipoEntidadPago;
	}
	
	public Set<SpcLugarPago> getSpcLugarPagos() {
		return this.spcLugarPagos;
	}

	public void setSpcLugarPagos(Set<SpcLugarPago> spcLugarPagos) {
		this.spcLugarPagos = spcLugarPagos;
	}

	public String getDesEntidadPago() {
		return desEntidadPago;
	}

	public void setDesEntidadPago(String desEntidadPago) {
		this.desEntidadPago = desEntidadPago;
	}
	
}
