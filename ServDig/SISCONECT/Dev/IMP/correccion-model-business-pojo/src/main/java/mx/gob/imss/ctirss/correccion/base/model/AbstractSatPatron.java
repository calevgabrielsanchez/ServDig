package mx.gob.imss.ctirss.correccion.base.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the SAT_PATRON database table.
 * 
 */
@MappedSuperclass
public class AbstractSatPatron extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CVE_PATRON_GENERATOR", sequenceName="SAS_CVE_PK_PATRON")
	@GeneratedValue(generator="CVE_PATRON_GENERATOR")
	@Column(name="CVE_PK")
	private Long cvePK;

	
	
	@Column(name="NUM_HIBERNATE_VERSION")
	private Integer numHibernateVersion;

	@Column(name="REF_CURP")
	private String curp;

	@Column(name="NOM_RAZONSOCIAL")
	private String razonSocial;

	@Column(name="REF_RFC")
	private String rfc;
	
	@Column(name="CVE_FK_SUBDELEGACION")
	private Integer cveSubdelegacion;

	@Column(name="NUM_REGISTROPATRONAL")
	private String registroPatronal;
	
	@Column(name="CVE_FK_UBICACION")
	private Integer fkUbicacion;

	public Long getCvePK() {
		return cvePK;
	}

	public void setCvePK(Long cvePK) {
		this.cvePK = cvePK;
	}

	public Integer getNumHibernateVersion() {
		return numHibernateVersion;
	}

	public void setNumHibernateVersion(Integer numHibernateVersion) {
		this.numHibernateVersion = numHibernateVersion;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public Integer getFkUbicacion() {
		return fkUbicacion;
	}

	public void setFkUbicacion(Integer fkUbicacion) {
		this.fkUbicacion = fkUbicacion;
	}

	public Integer getCveSubdelegacion() {
		return cveSubdelegacion;
	}

	public void setCveSubdelegacion(Integer cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}
	
	
	
	
}