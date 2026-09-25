package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the APT_DIAGNOSTICOS_CNSF database table.
 * 
 */
@Embeddable
public class AptDiagnosticosCnsfPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DIAGNOSTICO")
	private String idDiagnostico;

	@Column(name="ID_PROCESO_OCUPADO")
	private String idProcesoOcupado;

	public AptDiagnosticosCnsfPK() {
	}
	public String getIdDiagnostico() {
		return this.idDiagnostico;
	}
	public void setIdDiagnostico(String idDiagnostico) {
		this.idDiagnostico = idDiagnostico;
	}
	public String getIdProcesoOcupado() {
		return this.idProcesoOcupado;
	}
	public void setIdProcesoOcupado(String idProcesoOcupado) {
		this.idProcesoOcupado = idProcesoOcupado;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AptDiagnosticosCnsfPK)) {
			return false;
		}
		AptDiagnosticosCnsfPK castOther = (AptDiagnosticosCnsfPK)other;
		return 
			this.idDiagnostico.equals(castOther.idDiagnostico)
			&& this.idProcesoOcupado.equals(castOther.idProcesoOcupado);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idDiagnostico.hashCode();
		hash = hash * prime + this.idProcesoOcupado.hashCode();
		
		return hash;
	}
}