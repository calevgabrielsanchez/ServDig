package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_ESQUEMA database table.
 * 
 */
@Entity
@Table(name="SPC_ESQUEMA")
@NamedQuery(name="SpcEsquema.findAll", query="SELECT s FROM SpcEsquema s")
public class SpcEsquema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_ESQUEMA_IDESQUEMA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_ESQUEMA_IDESQUEMA_GENERATOR")
	@Column(name="ID_ESQUEMA")
	private String idEsquema;

	@Column(name="DES_ESQUEMA")
	private String desEsquema;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="spcEsquema")
	private List<SptPension> sptPensions;

	public SpcEsquema() {
	}

	public String getIdEsquema() {
		return this.idEsquema;
	}

	public void setIdEsquema(String idEsquema) {
		this.idEsquema = idEsquema;
	}

	public String getDesEsquema() {
		return this.desEsquema;
	}

	public void setDesEsquema(String desEsquema) {
		this.desEsquema = desEsquema;
	}

	public List<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(List<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}

	public SptPension addSptPension(SptPension sptPension) {
		getSptPensions().add(sptPension);
		sptPension.setSpcEsquema(this);

		return sptPension;
	}

	public SptPension removeSptPension(SptPension sptPension) {
		getSptPensions().remove(sptPension);
		sptPension.setSpcEsquema(null);

		return sptPension;
	}

}