package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the FDI_TP_OFICIO_ESP database table.
 * 
 */
@Entity
@Table(name="FDI_TP_OFICIO_ESP")
public class FdiTpOficioEsp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TP_OFICIO", nullable=false, precision=22)
	private long idTpOficio;

	@Column(name="TX_DESC_OFICIO", nullable=false, length=150)
	private String txDescOficio;

    public FdiTpOficioEsp() {
    }

	public long getIdTpOficio() {
		return this.idTpOficio;
	}

	public void setIdTpOficio(long idTpOficio) {
		this.idTpOficio = idTpOficio;
	}

	public String getTxDescOficio() {
		return this.txDescOficio;
	}

	public void setTxDescOficio(String txDescOficio) {
		this.txDescOficio = txDescOficio;
	}

}