package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_MUNICIPIOS database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_MUNICIPIOS")
@NamedQuery(name="AdtCatMunicipio.findAll", query="SELECT a FROM AdtCatMunicipio a")
public class AdtCatMunicipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdtCatMunicipioPK id;

	@Column(name="DES_MUNICIPIO")
	private String desMunicipio;

	public AdtCatMunicipio() {
	}

	public AdtCatMunicipioPK getId() {
		return this.id;
	}

	public void setId(AdtCatMunicipioPK id) {
		this.id = id;
	}

	public String getDesMunicipio() {
		return this.desMunicipio;
	}

	public void setDesMunicipio(String desMunicipio) {
		this.desMunicipio = desMunicipio;
	}

}