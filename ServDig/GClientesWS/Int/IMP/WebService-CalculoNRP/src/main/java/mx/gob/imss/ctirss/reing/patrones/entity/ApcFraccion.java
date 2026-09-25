package mx.gob.imss.ctirss.reing.patrones.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the APC_FRACCION database table.
 * 
 */
@Entity
@Table(name="APC_FRACCION")
public class ApcFraccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApcFraccionPK id;

	@Column(name="CVE_CLASE")
	private String cveClase;

	@Column(name="DES_ACTIVIDAD")
	private String desActividad;

	@Column(name="NOM_ACTIVIDAD")
	private String nomActividad;

	@Column(name="NUM_PRIMA_MEDIA")
	private BigDecimal numPrimaMedia;

	//bi-directional many-to-one association to AptRegistroPatronal
	@OneToMany(mappedBy="apcFraccion")
	private Set<AptRegistroPatronal> aptRegistroPatronals;

    public ApcFraccion() {
    }

	public ApcFraccionPK getId() {
		return this.id;
	}

	public void setId(ApcFraccionPK id) {
		this.id = id;
	}
	
	public String getCveClase() {
		return this.cveClase;
	}

	public void setCveClase(String cveClase) {
		this.cveClase = cveClase;
	}

	public String getDesActividad() {
		return this.desActividad;
	}

	public void setDesActividad(String desActividad) {
		this.desActividad = desActividad;
	}

	public String getNomActividad() {
		return this.nomActividad;
	}

	public void setNomActividad(String nomActividad) {
		this.nomActividad = nomActividad;
	}

	public BigDecimal getNumPrimaMedia() {
		return this.numPrimaMedia;
	}

	public void setNumPrimaMedia(BigDecimal numPrimaMedia) {
		this.numPrimaMedia = numPrimaMedia;
	}

	public Set<AptRegistroPatronal> getAptRegistroPatronals() {
		return this.aptRegistroPatronals;
	}

	public void setAptRegistroPatronals(Set<AptRegistroPatronal> aptRegistroPatronals) {
		this.aptRegistroPatronals = aptRegistroPatronals;
	}
	
}