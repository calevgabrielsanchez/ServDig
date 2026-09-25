package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the SPC_CARACTER database table.
 * 
 */
@Entity
@Table(name="SPC_CARACTER")
public class SpcCaracter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CARACTER")
	private Integer idCaracter;

	@Column(name="DES_CARACTER")
	private String desCaracter;

	//bi-directional many-to-one association to SptDictamen
	@OneToMany(mappedBy="spcCaracter")
	private Set<SptDictamen> sptDictamens;

	//bi-directional many-to-one association to SptDictamenLaudo
	@OneToMany(mappedBy="spcCaracter")
	private Set<SptDictamenLaudo> sptDictamenLaudos;

    public SpcCaracter() {
    }

	public Integer getIdCaracter() {
		return this.idCaracter;
	}

	public void setIdCaracter(Integer idCaracter) {
		this.idCaracter = idCaracter;
	}

	public String getDesCaracter() {
		return this.desCaracter;
	}

	public void setDesCaracter(String desCaracter) {
		this.desCaracter = desCaracter;
	}

	public Set<SptDictamen> getSptDictamens() {
		return this.sptDictamens;
	}

	public void setSptDictamens(Set<SptDictamen> sptDictamens) {
		this.sptDictamens = sptDictamens;
	}
	
	public Set<SptDictamenLaudo> getSptDictamenLaudos() {
		return this.sptDictamenLaudos;
	}

	public void setSptDictamenLaudos(Set<SptDictamenLaudo> sptDictamenLaudos) {
		this.sptDictamenLaudos = sptDictamenLaudos;
	}
	
}