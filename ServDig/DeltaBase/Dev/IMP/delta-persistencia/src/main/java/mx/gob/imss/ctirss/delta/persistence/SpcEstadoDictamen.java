package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_ESTADO_DICTAMEN database table.
 * 
 */
@Entity
@Table(name="SPC_ESTADO_DICTAMEN")
@NamedQuery(name="SpcEstadoDictamen.findAll", query="SELECT s FROM SpcEstadoDictamen s")
public class SpcEstadoDictamen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_ESTADO_DICTAMEN_IDESTADODICTAMEN_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_ESTADO_DICTAMEN_IDESTADODICTAMEN_GENERATOR")
	@Column(name="ID_ESTADO_DICTAMEN")
	private String idEstadoDictamen;

	@Column(name="DES_ESTADO_DICTAMEN")
	private String desEstadoDictamen;

	//bi-directional many-to-one association to SptDictamen
	@OneToMany(mappedBy="spcEstadoDictamen")
	private List<SptDictamen> sptDictamens;

	//bi-directional many-to-one association to SptDictamenCambioEdo
	@OneToMany(mappedBy="spcEstadoDictamen")
	private List<SptDictamenCambioEdo> sptDictamenCambioEdos;

	public SpcEstadoDictamen() {
	}

	public String getIdEstadoDictamen() {
		return this.idEstadoDictamen;
	}

	public void setIdEstadoDictamen(String idEstadoDictamen) {
		this.idEstadoDictamen = idEstadoDictamen;
	}

	public String getDesEstadoDictamen() {
		return this.desEstadoDictamen;
	}

	public void setDesEstadoDictamen(String desEstadoDictamen) {
		this.desEstadoDictamen = desEstadoDictamen;
	}

	public List<SptDictamen> getSptDictamens() {
		return this.sptDictamens;
	}

	public void setSptDictamens(List<SptDictamen> sptDictamens) {
		this.sptDictamens = sptDictamens;
	}

	public SptDictamen addSptDictamen(SptDictamen sptDictamen) {
		getSptDictamens().add(sptDictamen);
		sptDictamen.setSpcEstadoDictamen(this);

		return sptDictamen;
	}

	public SptDictamen removeSptDictamen(SptDictamen sptDictamen) {
		getSptDictamens().remove(sptDictamen);
		sptDictamen.setSpcEstadoDictamen(null);

		return sptDictamen;
	}

	public List<SptDictamenCambioEdo> getSptDictamenCambioEdos() {
		return this.sptDictamenCambioEdos;
	}

	public void setSptDictamenCambioEdos(List<SptDictamenCambioEdo> sptDictamenCambioEdos) {
		this.sptDictamenCambioEdos = sptDictamenCambioEdos;
	}

	public SptDictamenCambioEdo addSptDictamenCambioEdo(SptDictamenCambioEdo sptDictamenCambioEdo) {
		getSptDictamenCambioEdos().add(sptDictamenCambioEdo);
		sptDictamenCambioEdo.setSpcEstadoDictamen(this);

		return sptDictamenCambioEdo;
	}

	public SptDictamenCambioEdo removeSptDictamenCambioEdo(SptDictamenCambioEdo sptDictamenCambioEdo) {
		getSptDictamenCambioEdos().remove(sptDictamenCambioEdo);
		sptDictamenCambioEdo.setSpcEstadoDictamen(null);

		return sptDictamenCambioEdo;
	}

}