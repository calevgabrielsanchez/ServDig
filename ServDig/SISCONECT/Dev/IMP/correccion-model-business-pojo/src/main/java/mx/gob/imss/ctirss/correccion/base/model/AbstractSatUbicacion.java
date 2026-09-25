package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CRC_PATRON database table.
 * 
 */
@MappedSuperclass
public class AbstractSatUbicacion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CVE_PATRON_GENERATOR", sequenceName="SAS_CVE_PK_UBICACION")
	@GeneratedValue(generator="CVE_PATRON_GENERATOR")
	@Column(name="CVE_PK")
	private Integer cvePK;

	@Column(name="NUM_HIBERNATE_VERSION")
	private Integer numHibernateVersion;

	@Column(name="DOM_CALLE")
	private String calle;

	@Column(name="NUM_CODIGOPOSTAL")
	private String codigoPostal;

	@Column(name="REF_COLONIA")
	private String colonia;

	@Column(name="REF_EMAIL")
	private String eMail;

	@Column(name="NUM_NROEXT")
	private String numeroExterior;

	@Column(name="NUM_NROINT")
	private String numeroInterior;

	@Column(name="NUM_TELEFONO")
	private Integer telefono;

	@Column(name="CVE_FK_MUNICIPIO")
	private Integer fkMunicipio;

	public Integer getCvePK() {
		return cvePK;
	}

	public void setCvePK(Integer cvePK) {
		this.cvePK = cvePK;
	}

	public Integer getNumHibernateVersion() {
		return numHibernateVersion;
	}

	public void setNumHibernateVersion(Integer numHibernateVersion) {
		this.numHibernateVersion = numHibernateVersion;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getNumeroExterior() {
		return numeroExterior;
	}

	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	public String getNumeroInterior() {
		return numeroInterior;
	}

	public void setNumeroInterior(String numeroInterior) {
		this.numeroInterior = numeroInterior;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public Integer getFkMunicipio() {
		return fkMunicipio;
	}

	public void setFkMunicipio(Integer fkMunicipio) {
		this.fkMunicipio = fkMunicipio;
	}



	
}