/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CGT_ANEXORP database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgtAnexoRP extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	private AbstractCgtAnexoRPPK id;

	private BigDecimal atrabomisos;

	private BigDecimal atrabrevisados;

	private BigDecimal atrabsubdeclarados;

	private BigDecimal rtrabomisos;

	private BigDecimal rtrabrevisados;

	private BigDecimal rtrabsubdeclarados;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	private BigDecimal dv;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG")
	private Date fecFechareg;

	public AbstractCgtAnexoRP() {
	}

	public BigDecimal getAtrabomisos() {
		return this.atrabomisos;
	}

	public void setAtrabomisos(BigDecimal atrabomisos) {
		this.atrabomisos = atrabomisos;
	}

	public BigDecimal getAtrabrevisados() {
		return this.atrabrevisados;
	}

	public void setAtrabrevisados(BigDecimal atrabrevisados) {
		this.atrabrevisados = atrabrevisados;
	}

	public BigDecimal getAtrabsubdeclarados() {
		return this.atrabsubdeclarados;
	}

	public void setAtrabsubdeclarados(BigDecimal atrabsubdeclarados) {
		this.atrabsubdeclarados = atrabsubdeclarados;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public BigDecimal getDv() {
		return this.dv;
	}

	public void setDv(BigDecimal dv) {
		this.dv = dv;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public BigDecimal getRtrabomisos() {
		return this.rtrabomisos;
	}

	public void setRtrabomisos(BigDecimal rtrabomisos) {
		this.rtrabomisos = rtrabomisos;
	}

	public BigDecimal getRtrabrevisados() {
		return this.rtrabrevisados;
	}

	public void setRtrabrevisados(BigDecimal rtrabrevisados) {
		this.rtrabrevisados = rtrabrevisados;
	}

	public BigDecimal getRtrabsubdeclarados() {
		return this.rtrabsubdeclarados;
	}

	public void setRtrabsubdeclarados(BigDecimal rtrabsubdeclarados) {
		this.rtrabsubdeclarados = rtrabsubdeclarados;
	}

	public AbstractCgtAnexoRPPK getId() {
		return id;
	}

	public void setId(AbstractCgtAnexoRPPK id) {
		this.id = id;
	}

}