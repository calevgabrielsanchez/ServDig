package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SPC_MUNICIPIO_DELEGACION database table.
 * 
 */
@Entity
@Table(name="SPC_MUNICIPIO_DELEGACION")
@NamedQuery(name="SpcMunicipioDelegacion.findAll", query="SELECT s FROM SpcMunicipioDelegacion s")
public class SpcMunicipioDelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SpcMunicipioDelegacionPK id;

	@Column(name="DES_MUNICIPIO_DELEGACION")
	private String desMunicipioDelegacion;

	public SpcMunicipioDelegacion() {
	}

	public SpcMunicipioDelegacionPK getId() {
		return this.id;
	}

	public void setId(SpcMunicipioDelegacionPK id) {
		this.id = id;
	}

	public String getDesMunicipioDelegacion() {
		return this.desMunicipioDelegacion;
	}

	public void setDesMunicipioDelegacion(String desMunicipioDelegacion) {
		this.desMunicipioDelegacion = desMunicipioDelegacion;
	}

}