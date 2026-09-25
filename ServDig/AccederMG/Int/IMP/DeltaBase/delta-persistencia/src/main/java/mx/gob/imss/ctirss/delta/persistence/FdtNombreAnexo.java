package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the FDT_NOMBRE_ANEXOS database table.
 * 
 */
@Entity
@Table(name="FDT_NOMBRE_ANEXOS")
public class FdtNombreAnexo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ANEXO", nullable=false, precision=22)
	private long idAnexo;

	@Column(name="TX_NOMBRE", length=15)
	private String txNombre;

	//bi-directional many-to-one association to FdtControFlujoAnexo
	@OneToMany(mappedBy="fdtNombreAnexo")
	private List<FdtControFlujoAnexo> fdtControFlujoAnexos;

    public FdtNombreAnexo() {
    }

	public long getIdAnexo() {
		return this.idAnexo;
	}

	public void setIdAnexo(long idAnexo) {
		this.idAnexo = idAnexo;
	}

	public String getTxNombre() {
		return this.txNombre;
	}

	public void setTxNombre(String txNombre) {
		this.txNombre = txNombre;
	}

	public List<FdtControFlujoAnexo> getFdtControFlujoAnexos() {
		return this.fdtControFlujoAnexos;
	}

	public void setFdtControFlujoAnexos(List<FdtControFlujoAnexo> fdtControFlujoAnexos) {
		this.fdtControFlujoAnexos = fdtControFlujoAnexos;
	}
	
}