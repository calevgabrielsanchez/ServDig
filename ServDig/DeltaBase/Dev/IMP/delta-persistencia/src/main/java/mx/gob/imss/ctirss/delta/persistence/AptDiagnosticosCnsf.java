package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the APT_DIAGNOSTICOS_CNSF database table.
 * 
 */
@Entity
@Table(name="APT_DIAGNOSTICOS_CNSF")
@NamedQuery(name="AptDiagnosticosCnsf.findAll", query="SELECT a FROM AptDiagnosticosCnsf a")
public class AptDiagnosticosCnsf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AptDiagnosticosCnsfPK id;

	@Column(name="DES_DIAGNOSTICO")
	private String desDiagnostico;

	@Column(name="ID_CAUSA")
	private String idCausa;

	public AptDiagnosticosCnsf() {
	}

	public AptDiagnosticosCnsfPK getId() {
		return this.id;
	}

	public void setId(AptDiagnosticosCnsfPK id) {
		this.id = id;
	}

	public String getDesDiagnostico() {
		return this.desDiagnostico;
	}

	public void setDesDiagnostico(String desDiagnostico) {
		this.desDiagnostico = desDiagnostico;
	}

	public String getIdCausa() {
		return this.idCausa;
	}

	public void setIdCausa(String idCausa) {
		this.idCausa = idCausa;
	}

}