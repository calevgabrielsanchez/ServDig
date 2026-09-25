/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public abstract class AbstractCgcCatOrigen extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ORIGEN")
	public long idOrigen;

	@Column(name = "DESC_ORIGEN")
	private String descOrigen;

	@Column(name = "FEC_FECHAREG")
	private Date fecFechaReg;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	public AbstractCgcCatOrigen() {
	}

	public long getIdOrigen() {
		return this.idOrigen;
	}

	public void setIdOrigen(long idOrigen) {
		this.idOrigen = idOrigen;
	}

	public String getDescOrigen() {
		return this.descOrigen;
	}

	public void setDescOrigen(String descOrigen) {
		this.descOrigen = descOrigen;
	}

	public Date getFecFechaReg() {
		return fecFechaReg;
	}

	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

}