package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIC_TIPOS_EJECUTOR database table.
 * 
 */
@Entity
@Table(name="DIC_TIPOS_EJECUTOR")
public class DicTiposEjecutor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_TIPO_EJECUTOR", nullable=false, length=1)
	private String cveTipoEjecutor;

	@Column(name="DES_TIPO_EJECUTOR", length=50)
	private String desTipoEjecutor;

    public DicTiposEjecutor() {
    }

	public String getCveTipoEjecutor() {
		return this.cveTipoEjecutor;
	}

	public void setCveTipoEjecutor(String cveTipoEjecutor) {
		this.cveTipoEjecutor = cveTipoEjecutor;
	}

	public String getDesTipoEjecutor() {
		return this.desTipoEjecutor;
	}

	public void setDesTipoEjecutor(String desTipoEjecutor) {
		this.desTipoEjecutor = desTipoEjecutor;
	}

}