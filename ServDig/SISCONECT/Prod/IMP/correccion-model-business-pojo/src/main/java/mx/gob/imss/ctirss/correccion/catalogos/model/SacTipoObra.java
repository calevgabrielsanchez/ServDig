package mx.gob.imss.ctirss.correccion.catalogos.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Entity
@Table(name="SAC_TIPOOBRA")
public class SacTipoObra extends AbstractModel {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_PK")
	private Long idTipoObra;
	
	@Column(name="NUM_HIBERNATE_VERSION")
	private Integer hibernateVersion;
	
	@Column(name="CVE_CODIGO")
	private String cveCodigo;
	
	@Column(name="TIP_CLASEOBRA")
	private String tipoClaseObra;
	
	@Column(name="DES_TIPOOBRA")
	private String descripcionTipoObra;
	
	@Column(name="TIP_CONVENIO15B")
	private Short convenio15B;
	
	@Column(name="NUM_COEFICIENTECONVENIO")
	private BigDecimal coeficienteConvenio;
	
	public Long getIdTipoObra() {
		return idTipoObra;
	}
	public void setIdTipoObra(Long idTipoObra) {
		this.idTipoObra = idTipoObra;
	}
	public Integer getHibernateVersion() {
		return hibernateVersion;
	}
	public void setHibernateVersion(Integer hibernateVersion) {
		this.hibernateVersion = hibernateVersion;
	}
	public String getCveCodigo() {
		return cveCodigo;
	}
	public void setCveCodigo(String cveCodigo) {
		this.cveCodigo = cveCodigo;
	}
	public String getTipoClaseObra() {
		return tipoClaseObra;
	}
	public void setTipoClaseObra(String tipoClaseObra) {
		this.tipoClaseObra = tipoClaseObra;
	}
	public String getDescripcionTipoObra() {
		return descripcionTipoObra;
	}
	public void setDescripcionTipoObra(String descripcionTipoObra) {
		this.descripcionTipoObra = descripcionTipoObra;
	}
	public Short getConvenio15B() {
		return convenio15B;
	}
	public void setConvenio15B(Short convenio15b) {
		convenio15B = convenio15b;
	}
	public BigDecimal getCoeficienteConvenio() {
		return coeficienteConvenio;
	}
	public void setCoeficienteConvenio(BigDecimal coeficienteConvenio) {
		this.coeficienteConvenio = coeficienteConvenio;
	}
}
