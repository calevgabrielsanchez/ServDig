package mx.gob.imss.ctirss.correccion.login.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the SEG_MENUSISTEMA database table.
 * 
 */
@Entity
@Table(name="SEG_MENUSISTEMA")
public class SegMenusistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_MENUSISTEMA")
	private Long cveMenusistema;

	//bi-directional many-to-one association to SegMenu
    @ManyToOne
	@JoinColumn(name="CVE_ID_MENU")
	private SegMenu segMenu;

	//bi-directional many-to-one association to SegRol
    @ManyToOne
	@JoinColumn(name="CVE_ROL")
	private SegRol segRol;

	//bi-directional many-to-one association to SegSistema
    @ManyToOne
	@JoinColumn(name="CVE_SISTEMA")
	private SegSistema segSistema;

    public SegMenusistema() {
    }

	public Long getCveMenusistema() {
		return this.cveMenusistema;
	}

	public void setCveMenusistema(Long cveMenusistema) {
		this.cveMenusistema = cveMenusistema;
	}

	public SegMenu getSegMenu() {
		return this.segMenu;
	}

	public void setSegMenu(SegMenu segMenu) {
		this.segMenu = segMenu;
	}
	
	public SegRol getSegRol() {
		return this.segRol;
	}

	public void setSegRol(SegRol segRol) {
		this.segRol = segRol;
	}
	
	public SegSistema getSegSistema() {
		return this.segSistema;
	}

	public void setSegSistema(SegSistema segSistema) {
		this.segSistema = segSistema;
	}
	
}