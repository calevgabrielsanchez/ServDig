package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_NAL database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_NAL")
@NamedQuery(name="AdtCatNal.findAll", query="SELECT a FROM AdtCatNal a")
public class AdtCatNal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_NACIONALIDAD")
	private String cveNacionalidad;

	@Column(name="DES_NACIONALIDAD")
	private String desNacionalidad;

	@Column(name="DES_PAIS")
	private String desPais;

	public AdtCatNal() {
	}

	public String getCveNacionalidad() {
		return this.cveNacionalidad;
	}

	public void setCveNacionalidad(String cveNacionalidad) {
		this.cveNacionalidad = cveNacionalidad;
	}

	public String getDesNacionalidad() {
		return this.desNacionalidad;
	}

	public void setDesNacionalidad(String desNacionalidad) {
		this.desNacionalidad = desNacionalidad;
	}

	public String getDesPais() {
		return this.desPais;
	}

	public void setDesPais(String desPais) {
		this.desPais = desPais;
	}

}