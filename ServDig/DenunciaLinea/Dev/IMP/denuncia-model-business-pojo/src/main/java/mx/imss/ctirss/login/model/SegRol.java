package mx.imss.ctirss.login.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the SEG_ROL database table.
 * 
 */
@Entity
@Table(name="SEG_ROL")
public class SegRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ROL")
	private long cveRol;

	@Column(name="DESC_ROL")
	private String descRol;

	//bi-directional many-to-one association to SegMenusistema
	@OneToMany(mappedBy="segRol")
	private Set<SegMenusistema> segMenusistemas;

	//bi-directional many-to-one association to SegPerfilUsuario
	@OneToMany(mappedBy="segRol")
	private Set<SegPerfilUsuario> segPerfilUsuarios;

    public SegRol() {
    }

	public long getCveRol() {
		return this.cveRol;
	}

	public void setCveRol(long cveRol) {
		this.cveRol = cveRol;
	}

	public String getDescRol() {
		return this.descRol;
	}

	public void setDescRol(String descRol) {
		this.descRol = descRol;
	}

	public Set<SegMenusistema> getSegMenusistemas() {
		return this.segMenusistemas;
	}

	public void setSegMenusistemas(Set<SegMenusistema> segMenusistemas) {
		this.segMenusistemas = segMenusistemas;
	}
	
	public Set<SegPerfilUsuario> getSegPerfilUsuarios() {
		return this.segPerfilUsuarios;
	}

	public void setSegPerfilUsuarios(Set<SegPerfilUsuario> segPerfilUsuarios) {
		this.segPerfilUsuarios = segPerfilUsuarios;
	}
	
}