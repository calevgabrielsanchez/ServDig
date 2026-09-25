package mx.imss.ctirss.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.imss.ctirss.catalogos.model.DlcRol;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.framework.base.model.AbstractModel;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DLC_PERFIL_USUARIO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcPerfilUsuario extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_PERFIL_USUARIO")
	private long cveIdPerfilUsuario;

	@Column(name="CVE_ID_PERFIL_PADRE")
	private BigDecimal cveIdPerfilPadre;

	//bi-directional many-to-one association to DlcUsuario
    @ManyToOne
	@JoinColumn(name="CVE_ID_USUARIO")
	private DlcUsuario dlcUsuario;

	//bi-directional many-to-one association to SegRol
    @ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ROL")
	private DlcRol dlcRol;

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

    public AbstractDlcPerfilUsuario() {
    }

	public long getCveIdPerfilUsuario() {
		return this.cveIdPerfilUsuario;
	}

	public void setCveIdPerfilUsuario(long cveIdPerfilUsuario) {
		this.cveIdPerfilUsuario = cveIdPerfilUsuario;
	}

	public BigDecimal getCveIdPerfilPadre() {
		return this.cveIdPerfilPadre;
	}

	public void setCveIdPerfilPadre(BigDecimal cveIdPerfilPadre) {
		this.cveIdPerfilPadre = cveIdPerfilPadre;
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

	public DlcUsuario getDlcUsuario() {
		return dlcUsuario;
	}

	public void setDlcUsuario(DlcUsuario dlcUsuario) {
		this.dlcUsuario = dlcUsuario;
	}

	public DlcRol getDlcRol() {
		return dlcRol;
	}

	public void setDlcRol(DlcRol dlcRol) {
		this.dlcRol = dlcRol;
	}

}