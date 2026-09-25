package mx.gob.imss.ctirss.correccion.menu.base.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public abstract class AbstractMenu extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_PK")
	private Integer cvePK;
	
	@Column(name="DES_ETIQUETA")
	private String desEtiqueta;
	
	@Column(name="DES_URL")
	private String desURL;
	
	@Column(name="NUM_ORDEN")
	private Integer numOrden;
	
	@Column(name="CVE_FK_ROL")
	private Integer cveFkRol;
	
	@Column(name="CVE_FK_MENUITEM")
	private String cveFkMenuItem;
	
	@Column(name="NUM_HIBERNATE_VERSION")
	private String numHibernateVersion;

	public Integer getCvePK() {
		return cvePK;
	}

	public void setCvePK(Integer cvePK) {
		this.cvePK = cvePK;
	}

	public String getDesEtiqueta() {
		return desEtiqueta;
	}

	public void setDesEtiqueta(String desEtiqueta) {
		this.desEtiqueta = desEtiqueta;
	}

	public String getDesURL() {
		return desURL;
	}

	public void setDesURL(String desURL) {
		this.desURL = desURL;
	}

	public Integer getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(Integer numOrden) {
		this.numOrden = numOrden;
	}

	public Integer getCveFkRol() {
		return cveFkRol;
	}

	public void setCveFkRol(Integer cveFkRol) {
		this.cveFkRol = cveFkRol;
	}

	public String getCveFkMenuItem() {
		return cveFkMenuItem;
	}

	public void setCveFkMenuItem(String cveFkMenuItem) {
		this.cveFkMenuItem = cveFkMenuItem;
	}

	public String getNumHibernateVersion() {
		return numHibernateVersion;
	}

	public void setNumHibernateVersion(String numHibernateVersion) {
		this.numHibernateVersion = numHibernateVersion;
	}
	
	
}
