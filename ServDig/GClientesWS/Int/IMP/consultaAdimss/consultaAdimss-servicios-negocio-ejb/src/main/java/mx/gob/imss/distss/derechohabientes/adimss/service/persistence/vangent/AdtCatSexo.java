package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_SEXO database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_SEXO")
@NamedQuery(name="AdtCatSexo.findAll", query="SELECT a FROM AdtCatSexo a")
public class AdtCatSexo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_SEXO")
	private String cveSexo;

	@Column(name="DES_SEXO")
	private String desSexo;

	public AdtCatSexo() {
	}

	public String getCveSexo() {
		return this.cveSexo;
	}

	public void setCveSexo(String cveSexo) {
		this.cveSexo = cveSexo;
	}

	public String getDesSexo() {
		return this.desSexo;
	}

	public void setDesSexo(String desSexo) {
		this.desSexo = desSexo;
	}

}