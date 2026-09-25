package mx.gob.imss.ctirss.correccion.login.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the SEG_PERFIL_USUARIO database table.
 * 
 */
@Entity
@OnSearchLlavePrimaria(atributos="cveIdPerfilUsuario")
@ComponentComboCampoDescripcion(atributo="desPerfilUsuario")
@Table(name="SEG_PERFIL_USUARIO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SegPerfilUsuario extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CVE_ID_PERFIL_USUARIO_GENERATOR", sequenceName="CRS_CVE_ID_PERFIL_USUARIO")
	@GeneratedValue(generator="CVE_ID_PERFIL_USUARIO_GENERATOR")
	@Column(name="CVE_ID_PERFIL_USUARIO")
	private Long cveIdPerfilUsuario;

	@Column(name="DES_PERFIL_USUARIO")
	private String desPerfilUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="TIP_PERFIL")
	private BigDecimal tipPerfil;

	//bi-directional many-to-one association to SegPerfilUsuario
    @ManyToOne
	@JoinColumn(name="CVE_ID_PERFIL_PADRE")
	private SegPerfilUsuario segPerfilUsuario;

	//bi-directional many-to-one association to SegPerfilUsuario
	@OneToMany(mappedBy="segPerfilUsuario")
	private Set<SegPerfilUsuario> segPerfilUsuarios;

	//bi-directional many-to-one association to SegRol
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ROL")
	private SegRol segRol;

	//bi-directional many-to-one association to SegUsuario
    @ManyToOne
	@JoinColumn(name="CVE_ID_USUARIO")
	private SegUsuario segUsuario;

    public SegPerfilUsuario() {
    }

	public Long getCveIdPerfilUsuario() {
		return this.cveIdPerfilUsuario;
	}

	public void setCveIdPerfilUsuario(Long cveIdPerfilUsuario) {
		this.cveIdPerfilUsuario = cveIdPerfilUsuario;
	}

	public String getDesPerfilUsuario() {
		return this.desPerfilUsuario;
	}

	public void setDesPerfilUsuario(String desPerfilUsuario) {
		this.desPerfilUsuario = desPerfilUsuario;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public BigDecimal getTipPerfil() {
		return this.tipPerfil;
	}

	public void setTipPerfil(BigDecimal tipPerfil) {
		this.tipPerfil = tipPerfil;
	}

	public SegPerfilUsuario getSegPerfilUsuario() {
		return this.segPerfilUsuario;
	}

	public void setSegPerfilUsuario(SegPerfilUsuario segPerfilUsuario) {
		this.segPerfilUsuario = segPerfilUsuario;
	}
	
	public Set<SegPerfilUsuario> getSegPerfilUsuarios() {
		return this.segPerfilUsuarios;
	}

	public void setSegPerfilUsuarios(Set<SegPerfilUsuario> segPerfilUsuarios) {
		this.segPerfilUsuarios = segPerfilUsuarios;
	}
	
	public SegRol getSegRol() {
		return this.segRol;
	}

	public void setSegRol(SegRol segRol) {
		this.segRol = segRol;
	}
	
	public SegUsuario getSegUsuario() {
		return this.segUsuario;
	}

	public void setSegUsuario(SegUsuario segUsuario) {
		this.segUsuario = segUsuario;
	}
	
}