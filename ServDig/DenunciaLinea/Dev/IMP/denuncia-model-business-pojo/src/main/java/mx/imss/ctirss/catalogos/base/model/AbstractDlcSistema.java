package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.framework.base.model.AbstractModel;


/**
 * The persistent class for the DLC_SISTEMA database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcSistema extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_SISTEMA")
	private long cveSistema;

	@Column(name="CVE_CODIGO")
	private String cveCodigo;

	@Column(name="DESC_SISTEMA")
	private String descSistema;

    public AbstractDlcSistema() {
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

}