/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatStatus;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;

/**
 * The persistent class for the CGC_REGLANEGOCIO database table.
 * 
 */

/**
 * @author Adolfo Meza
 * 
 */
@MappedSuperclass
public abstract class AbstractCgcReglaNegocio extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_REGLANEGOCIO")
	public Long cveReglaNegocio;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
	private CgcCatTipo cgcCatTipo;

	@Column(name = "NOMBRECONTROL")
	private String nombrecontrol;

	@Column(name = "NOMBRECAMPO")
	private String nombreCampo;

	private BigDecimal controlproceso;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	@Column(name = "FEC_FECHAREG")
	private Date fecFechareg;

	private String hijos;

	@Column(name = "HIJOSINDEPENDIENTES")
	private String hijosindependientes;

	private String mensaje;

	@Column(name = "NIVEL")
	private Float nivel;

	private String nombrecampo;

	private String valliminferior;

	private String vallimsuperior;

	@ManyToOne
	@JoinColumn(name = "ID_STATUS")
	private CgcCatStatus cgcCatStatus;

	public CgcCatStatus getCgcCatStatus() {
		return cgcCatStatus;
	}

	public void setCgcCatStatus(CgcCatStatus cgcCatStatus) {
		this.cgcCatStatus = cgcCatStatus;
	}

	public AbstractCgcReglaNegocio() {
	}

	public String getNombrecontrol() {
		return nombrecontrol;
	}

	public void setNombrecontrol(String nombrecontrol) {
		this.nombrecontrol = nombrecontrol;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public CgcCatTipo getCgcCatTipo() {
		return cgcCatTipo;
	}

	public void setCgcCatTipo(CgcCatTipo cgcCatTipo) {
		this.cgcCatTipo = cgcCatTipo;
	}

	public Long getCveReglaNegocio() {
		return cveReglaNegocio;
	}

	public void setCveReglaNegocio(Long cveReglaNegocio) {
		this.cveReglaNegocio = cveReglaNegocio;
	}

	public BigDecimal getControlproceso() {
		return this.controlproceso;
	}

	public void setControlproceso(BigDecimal controlproceso) {
		this.controlproceso = controlproceso;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public String getHijos() {
		return this.hijos;
	}

	public void setHijos(String hijos) {
		this.hijos = hijos;
	}

	public String getHijosindependientes() {
		return this.hijosindependientes;
	}

	public void setHijosindependientes(String hijosindependientes) {
		this.hijosindependientes = hijosindependientes;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Float getNivel() {
		return nivel;
	}

	public void setNivel(Float nivel) {
		this.nivel = nivel;
	}

	public String getNombrecampo() {
		return this.nombrecampo;
	}

	public void setNombrecampo(String nombrecampo) {
		this.nombrecampo = nombrecampo;
	}

	public String getValliminferior() {
		return this.valliminferior;
	}

	public void setValliminferior(String valliminferior) {
		this.valliminferior = valliminferior;
	}

	public String getVallimsuperior() {
		return this.vallimsuperior;
	}

	public void setVallimsuperior(String vallimsuperior) {
		this.vallimsuperior = vallimsuperior;
	}

}