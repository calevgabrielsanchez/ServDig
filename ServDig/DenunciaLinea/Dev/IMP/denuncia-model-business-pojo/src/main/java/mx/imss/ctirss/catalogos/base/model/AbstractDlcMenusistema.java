package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;

import java.math.BigDecimal;


/**
 * The persistent class for the DLC_MENUSISTEMA database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcMenusistema extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_MENUSISTEMA")
	private long cveMenusistema;

	@Column(name="CVE_ID_MENU")
	private BigDecimal cveIdMenu;

	@Column(name="CVE_ROL")
	private BigDecimal cveRol;

	@Column(name="CVE_SISTEMA")
	private BigDecimal cveSistema;

    public AbstractDlcMenusistema() {
    }

	public long getCveMenusistema() {
		return this.cveMenusistema;
	}

	public void setCveMenusistema(long cveMenusistema) {
		this.cveMenusistema = cveMenusistema;
	}

	public BigDecimal getCveIdMenu() {
		return this.cveIdMenu;
	}

	public void setCveIdMenu(BigDecimal cveIdMenu) {
		this.cveIdMenu = cveIdMenu;
	}

	public BigDecimal getCveRol() {
		return this.cveRol;
	}

	public void setCveRol(BigDecimal cveRol) {
		this.cveRol = cveRol;
	}

	public BigDecimal getCveSistema() {
		return this.cveSistema;
	}

	public void setCveSistema(BigDecimal cveSistema) {
		this.cveSistema = cveSistema;
	}

}