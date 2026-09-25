package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_TIPO_JUICIO database table.
 * 
 */
@Entity
@Table(name="SPC_TIPO_JUICIO")
@NamedQuery(name="SpcTipoJuicio.findAll", query="SELECT s FROM SpcTipoJuicio s")
public class SpcTipoJuicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_TIPO_JUICIO_IDTIPOJUICIO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_TIPO_JUICIO_IDTIPOJUICIO_GENERATOR")
	@Column(name="ID_TIPO_JUICIO")
	private String idTipoJuicio;

	@Column(name="DES_TIPO_JUICIO")
	private String desTipoJuicio;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="spcTipoJuicio")
	private List<SptPension> sptPensions;

	public SpcTipoJuicio() {
	}

	public String getIdTipoJuicio() {
		return this.idTipoJuicio;
	}

	public void setIdTipoJuicio(String idTipoJuicio) {
		this.idTipoJuicio = idTipoJuicio;
	}

	public String getDesTipoJuicio() {
		return this.desTipoJuicio;
	}

	public void setDesTipoJuicio(String desTipoJuicio) {
		this.desTipoJuicio = desTipoJuicio;
	}

	public List<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(List<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}

	public SptPension addSptPension(SptPension sptPension) {
		getSptPensions().add(sptPension);
		sptPension.setSpcTipoJuicio(this);

		return sptPension;
	}

	public SptPension removeSptPension(SptPension sptPension) {
		getSptPensions().remove(sptPension);
		sptPension.setSpcTipoJuicio(null);

		return sptPension;
	}

}