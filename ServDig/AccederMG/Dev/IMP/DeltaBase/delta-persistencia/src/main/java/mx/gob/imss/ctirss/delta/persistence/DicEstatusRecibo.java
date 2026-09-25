package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIC_ESTATUS_RECIBOS database table.
 * 
 */
@Entity
@Table(name="DIC_ESTATUS_RECIBOS")
public class DicEstatusRecibo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ESTATUS_RECIBO", nullable=false, precision=2)
	private long cveEstatusRecibo;

	@Column(name="DES_ESTATUS_RECIBO", length=50)
	private String desEstatusRecibo;

    public DicEstatusRecibo() {
    }

	public long getCveEstatusRecibo() {
		return this.cveEstatusRecibo;
	}

	public void setCveEstatusRecibo(long cveEstatusRecibo) {
		this.cveEstatusRecibo = cveEstatusRecibo;
	}

	public String getDesEstatusRecibo() {
		return this.desEstatusRecibo;
	}

	public void setDesEstatusRecibo(String desEstatusRecibo) {
		this.desEstatusRecibo = desEstatusRecibo;
	}

}