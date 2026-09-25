package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TIPOENROLAMIENTO database table.
 * 
 */
@Entity
@NamedQuery(name="Tipoenrolamiento.findAll", query="SELECT t FROM Tipoenrolamiento t")
public class Tipoenrolamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idtipoenrolamiento;

	private String descriptipo;

	//bi-directional many-to-one association to Userattention
	@OneToMany(mappedBy="tipoenrolamiento")
	private List<Userattention> userattentions;

	public Tipoenrolamiento() {
	}

	public long getIdtipoenrolamiento() {
		return this.idtipoenrolamiento;
	}

	public void setIdtipoenrolamiento(long idtipoenrolamiento) {
		this.idtipoenrolamiento = idtipoenrolamiento;
	}

	public String getDescriptipo() {
		return this.descriptipo;
	}

	public void setDescriptipo(String descriptipo) {
		this.descriptipo = descriptipo;
	}

	public List<Userattention> getUserattentions() {
		return this.userattentions;
	}

	public void setUserattentions(List<Userattention> userattentions) {
		this.userattentions = userattentions;
	}

	public Userattention addUserattention(Userattention userattention) {
		getUserattentions().add(userattention);
		userattention.setTipoenrolamiento(this);

		return userattention;
	}

	public Userattention removeUserattention(Userattention userattention) {
		getUserattentions().remove(userattention);
		userattention.setTipoenrolamiento(null);

		return userattention;
	}

}