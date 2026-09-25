package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADT_CAT_TIPO_MOV database table.
 * 
 */
@Entity
@Table(name="ADT_CAT_TIPO_MOV")
@NamedQuery(name="AdtCatTipoMov.findAll", query="SELECT a FROM AdtCatTipoMov a")
public class AdtCatTipoMov implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_TIPO_REGISTRO")
	private long cveTipoRegistro;

	@Column(name="DES_TIPO_MOV")
	private String desTipoMov;

	public AdtCatTipoMov() {
	}

	public long getCveTipoRegistro() {
		return this.cveTipoRegistro;
	}

	public void setCveTipoRegistro(long cveTipoRegistro) {
		this.cveTipoRegistro = cveTipoRegistro;
	}

	public String getDesTipoMov() {
		return this.desTipoMov;
	}

	public void setDesTipoMov(String desTipoMov) {
		this.desTipoMov = desTipoMov;
	}

}