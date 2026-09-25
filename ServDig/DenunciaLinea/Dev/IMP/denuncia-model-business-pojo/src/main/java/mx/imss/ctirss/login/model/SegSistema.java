package mx.imss.ctirss.login.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the SEG_SISTEMA database table.
 * 
 */
@Entity
@Table(name="SEG_SISTEMA")
public class SegSistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_SISTEMA")
	private long cveSistema;

	@Column(name="CVE_CODIGO")
	private String cveCodigo;

	@Column(name="DESC_SISTEMA")
	private String descSistema;

	//bi-directional many-to-one association to SegMenusistema
	@OneToMany(mappedBy="segSistema")
	private Set<SegMenusistema> segMenusistemas;

    public SegSistema() {
    }

	public long getCveSistema() {
		return this.cveSistema;
	}

	public void setCveSistema(long cveSistema) {
		this.cveSistema = cveSistema;
	}

	public String getCveCodigo() {
		return this.cveCodigo;
	}

	public void setCveCodigo(String cveCodigo) {
		this.cveCodigo = cveCodigo;
	}

	public String getDescSistema() {
		return this.descSistema;
	}

	public void setDescSistema(String descSistema) {
		this.descSistema = descSistema;
	}

	public Set<SegMenusistema> getSegMenusistemas() {
		return this.segMenusistemas;
	}

	public void setSegMenusistemas(Set<SegMenusistema> segMenusistemas) {
		this.segMenusistemas = segMenusistemas;
	}
	
}