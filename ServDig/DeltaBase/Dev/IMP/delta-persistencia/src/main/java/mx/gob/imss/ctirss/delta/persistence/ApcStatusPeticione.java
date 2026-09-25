package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the APC_STATUS_PETICIONES database table.
 * 
 */
@Entity
@Table(name="APC_STATUS_PETICIONES")
public class ApcStatusPeticione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_STATUS_PETICION")
	private long idStatusPeticion;

	@Column(name="DESC_STATUS_DESCRIPCION")
	private String descStatusDescripcion;

	@Column(name="IND_STATUS_ACTIVO")
	private String indStatusActivo;

	//bi-directional many-to-one association to AptEnvioPreResolucionEnv
	@OneToMany(mappedBy="apcStatusPeticione")
	private Set<AptEnvioPreResolucionEnv> aptEnvioPreResolucionEnvs;

    public ApcStatusPeticione() {
    }

	public long getIdStatusPeticion() {
		return this.idStatusPeticion;
	}

	public void setIdStatusPeticion(long idStatusPeticion) {
		this.idStatusPeticion = idStatusPeticion;
	}

	public String getDescStatusDescripcion() {
		return this.descStatusDescripcion;
	}

	public void setDescStatusDescripcion(String descStatusDescripcion) {
		this.descStatusDescripcion = descStatusDescripcion;
	}

	public String getIndStatusActivo() {
		return this.indStatusActivo;
	}

	public void setIndStatusActivo(String indStatusActivo) {
		this.indStatusActivo = indStatusActivo;
	}

	public Set<AptEnvioPreResolucionEnv> getAptEnvioPreResolucionEnvs() {
		return this.aptEnvioPreResolucionEnvs;
	}

	public void setAptEnvioPreResolucionEnvs(Set<AptEnvioPreResolucionEnv> aptEnvioPreResolucionEnvs) {
		this.aptEnvioPreResolucionEnvs = aptEnvioPreResolucionEnvs;
	}
	
}