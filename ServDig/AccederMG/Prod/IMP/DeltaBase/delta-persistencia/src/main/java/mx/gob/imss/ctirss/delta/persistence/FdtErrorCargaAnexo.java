package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FDT_ERROR_CARGA_ANEXOS database table.
 * 
 */
@Entity
@Table(name="FDT_ERROR_CARGA_ANEXOS")
public class FdtErrorCargaAnexo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ERROR", nullable=false, precision=22)
	private long idError;

	@Column(name="TX_ERROR", length=500)
	private String txError;

	//bi-directional many-to-one association to FdtControFlujoAnexo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ID_ANEXO", referencedColumnName="ID_ANEXO"),
		@JoinColumn(name="ID_AVISO", referencedColumnName="ID_AVISO")
		})
	private FdtControFlujoAnexo fdtControFlujoAnexo;

    public FdtErrorCargaAnexo() {
    }

	public long getIdError() {
		return this.idError;
	}

	public void setIdError(long idError) {
		this.idError = idError;
	}

	public String getTxError() {
		return this.txError;
	}

	public void setTxError(String txError) {
		this.txError = txError;
	}

	public FdtControFlujoAnexo getFdtControFlujoAnexo() {
		return this.fdtControFlujoAnexo;
	}

	public void setFdtControFlujoAnexo(FdtControFlujoAnexo fdtControFlujoAnexo) {
		this.fdtControFlujoAnexo = fdtControFlujoAnexo;
	}
	
}