package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_TIPO_FORMATO database table.
 * 
 */
@Entity
@Table(name="SPC_TIPO_FORMATO")
@NamedQuery(name="SpcTipoFormato.findAll", query="SELECT s FROM SpcTipoFormato s")
public class SpcTipoFormato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_TIPO_FORMATO_IDTIPOFORMATO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_TIPO_FORMATO_IDTIPOFORMATO_GENERATOR")
	@Column(name="ID_TIPO_FORMATO")
	private long idTipoFormato;

	@Column(name="DES_TIPO_FORMATO")
	private String desTipoFormato;

	//bi-directional many-to-one association to SptDictamen
	@OneToMany(mappedBy="spcTipoFormato")
	private List<SptDictamen> sptDictamens;

	//bi-directional many-to-one association to SptDictamenLaudo
	@OneToMany(mappedBy="spcTipoFormato")
	private List<SptDictamenLaudo> sptDictamenLaudos;

	public SpcTipoFormato() {
	}

	public long getIdTipoFormato() {
		return this.idTipoFormato;
	}

	public void setIdTipoFormato(long idTipoFormato) {
		this.idTipoFormato = idTipoFormato;
	}

	public String getDesTipoFormato() {
		return this.desTipoFormato;
	}

	public void setDesTipoFormato(String desTipoFormato) {
		this.desTipoFormato = desTipoFormato;
	}

	public List<SptDictamen> getSptDictamens() {
		return this.sptDictamens;
	}

	public void setSptDictamens(List<SptDictamen> sptDictamens) {
		this.sptDictamens = sptDictamens;
	}

	public SptDictamen addSptDictamen(SptDictamen sptDictamen) {
		getSptDictamens().add(sptDictamen);
		sptDictamen.setSpcTipoFormato(this);

		return sptDictamen;
	}

	public SptDictamen removeSptDictamen(SptDictamen sptDictamen) {
		getSptDictamens().remove(sptDictamen);
		sptDictamen.setSpcTipoFormato(null);

		return sptDictamen;
	}

	public List<SptDictamenLaudo> getSptDictamenLaudos() {
		return this.sptDictamenLaudos;
	}

	public void setSptDictamenLaudos(List<SptDictamenLaudo> sptDictamenLaudos) {
		this.sptDictamenLaudos = sptDictamenLaudos;
	}

	public SptDictamenLaudo addSptDictamenLaudo(SptDictamenLaudo sptDictamenLaudo) {
		getSptDictamenLaudos().add(sptDictamenLaudo);
		sptDictamenLaudo.setSpcTipoFormato(this);

		return sptDictamenLaudo;
	}

	public SptDictamenLaudo removeSptDictamenLaudo(SptDictamenLaudo sptDictamenLaudo) {
		getSptDictamenLaudos().remove(sptDictamenLaudo);
		sptDictamenLaudo.setSpcTipoFormato(null);

		return sptDictamenLaudo;
	}

}