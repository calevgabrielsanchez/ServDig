package mx.gob.imss.ctirss.correccion.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatFlujo;

/**
 * The persistent class for the CGC_CATTIPO database table.
 * 
 */

@MappedSuperclass
public abstract class AbstractCgcCatTipo extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_TIPO")
	public long idTipo;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "FEC_FECHAREG")
	private Date fecFechaReg;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	@ManyToOne
	@JoinColumn(name = "ID_FLUJO")
	private CgcCatFlujo cgcCatflujo;

	public AbstractCgcCatTipo() {
	}

	public long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(long idTipo) {
		this.idTipo = idTipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CgcCatFlujo getCgcCatflujo() {
		return cgcCatflujo;
	}

	public void setCgcCatflujo(CgcCatFlujo cgcCatflujo) {
		this.cgcCatflujo = cgcCatflujo;
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