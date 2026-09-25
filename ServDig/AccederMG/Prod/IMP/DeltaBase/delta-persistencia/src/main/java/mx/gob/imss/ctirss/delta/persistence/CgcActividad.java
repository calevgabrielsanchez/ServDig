package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CGC_ACTIVIDAD database table.
 * 
 */
@Entity
@Table(name="CGC_ACTIVIDAD")
public class CgcActividad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ACTIVIDAD", nullable=false, precision=22)
	private long cveActividad;

	@Column(name="TX_ACTIVIDAD", length=250)
	private String txActividad;

	@Column(name="TX_CLAVE", length=5)
	private String txClave;

	//bi-directional many-to-one association to CgtGestionsinadi
	@OneToMany(mappedBy="cgcActividad")
	private List<CgtGestionsinadi> cgtGestionsinadis;

    public CgcActividad() {
    }

	public long getCveActividad() {
		return this.cveActividad;
	}

	public void setCveActividad(long cveActividad) {
		this.cveActividad = cveActividad;
	}

	public String getTxActividad() {
		return this.txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
	}

	public String getTxClave() {
		return this.txClave;
	}

	public void setTxClave(String txClave) {
		this.txClave = txClave;
	}

	public List<CgtGestionsinadi> getCgtGestionsinadis() {
		return this.cgtGestionsinadis;
	}

	public void setCgtGestionsinadis(List<CgtGestionsinadi> cgtGestionsinadis) {
		this.cgtGestionsinadis = cgtGestionsinadis;
	}
	
}