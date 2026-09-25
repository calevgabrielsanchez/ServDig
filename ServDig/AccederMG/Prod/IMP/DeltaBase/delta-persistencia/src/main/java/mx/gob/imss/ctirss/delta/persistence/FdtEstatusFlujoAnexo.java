package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDT_ESTATUS_FLUJO_ANEXO database table.
 * 
 */
@Entity
@Table(name="FDT_ESTATUS_FLUJO_ANEXO")
public class FdtEstatusFlujoAnexo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ESTATUS", nullable=false, precision=22)
	private long idEstatus;

	@Column(name="TX_DESCRIPCION", length=20)
	private String txDescripcion;

	//bi-directional many-to-one association to FdtControFlujoAnexo
	@OneToMany(mappedBy="fdtEstatusFlujoAnexo")
	private List<FdtControFlujoAnexo> fdtControFlujoAnexos;

    public FdtEstatusFlujoAnexo() {
    }

	public long getIdEstatus() {
		return this.idEstatus;
	}

	public void setIdEstatus(long idEstatus) {
		this.idEstatus = idEstatus;
	}

	public String getTxDescripcion() {
		return this.txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

	public List<FdtControFlujoAnexo> getFdtControFlujoAnexos() {
		return this.fdtControFlujoAnexos;
	}

	public void setFdtControFlujoAnexos(List<FdtControFlujoAnexo> fdtControFlujoAnexos) {
		this.fdtControFlujoAnexos = fdtControFlujoAnexos;
	}
	
}