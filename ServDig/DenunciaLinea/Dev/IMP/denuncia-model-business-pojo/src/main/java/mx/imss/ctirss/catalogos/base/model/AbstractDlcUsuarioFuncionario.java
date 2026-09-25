package mx.imss.ctirss.catalogos.base.model;

import javax.persistence.*;
import mx.imss.ctirss.catalogos.model.DlcDelegacion;
import mx.imss.ctirss.catalogos.model.DlcSubdelegacion;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDenuncia;

import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the DLC_USUARIO_FUNCIONARIO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractDlcUsuarioFuncionario extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_USUARIO_FUNCIONARIO")
	private long cveIdUsuarioFuncionario;

	@Column(name="CVE_FK_CARGO")
	private BigDecimal cveFkCargo;
	
	//bi-directional many-to-one association to DlcDelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_DELEGACION")
	private DlcDelegacion dlcDelegacion;
    

	//bi-directional many-to-one association to DlcSubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DlcSubdelegacion dlcSubdelegacion;
 
	@Column(name="CVE_ID_USUARIO", insertable=false, updatable=false)
	private BigDecimal cveIdUsuario;

	@Column(name="DES_CARGO")
	private String desCargo;

	@Column(name="ID_AUDITOR_BORRAR")
	private BigDecimal idAuditorBorrar;

	@Column(name="IND_VIGENCIA")
	private Boolean indVigencia;

	@Column(name="NUM_EXTENSION_CONTACTO")
	private String numExtensionContacto;

	@Column(name="NUM_LADA_CONTACTO")
	private String numLadaContacto;

	@Column(name="NUM_TELEFONO_CONTACTO")
	private String numTelefonoContacto;

	@Column(name="REF_CORREO_ELECTRONICO_TRABAJO")
	private String refCorreoElectronicoTrabajo;

	@Column(name="TIP_CENTRO_TRABAJO")
	private BigDecimal tipCentroTrabajo;

	//bi-directional many-to-one association to SegUsuario
    @ManyToOne
	@JoinColumn(name="CVE_ID_USUARIO")
	private DlcUsuario dlcUsuario;
	
    
	//denuncias
	@OneToMany(mappedBy="dlcUsuarioFuncionario")
	private Set<DltDenuncia> dltDenuncias;
    
    public Set<DltDenuncia> getDltDenuncias() {
		return dltDenuncias;
	}

	public void setDltDenuncias(Set<DltDenuncia> dltDenuncias) {
		this.dltDenuncias = dltDenuncias;
	}

	public AbstractDlcUsuarioFuncionario() {
    }

	public long getCveIdUsuarioFuncionario() {
		return this.cveIdUsuarioFuncionario;
	}

	public void setCveIdUsuarioFuncionario(long cveIdUsuarioFuncionario) {
		this.cveIdUsuarioFuncionario = cveIdUsuarioFuncionario;
	}

	public BigDecimal getCveFkCargo() {
		return this.cveFkCargo;
	}

	public void setCveFkCargo(BigDecimal cveFkCargo) {
		this.cveFkCargo = cveFkCargo;
	}

	public BigDecimal getCveIdUsuario() {
		return this.cveIdUsuario;
	}

	public void setCveIdUsuario(BigDecimal cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}

	public String getDesCargo() {
		return this.desCargo;
	}

	public void setDesCargo(String desCargo) {
		this.desCargo = desCargo;
	}

	public BigDecimal getIdAuditorBorrar() {
		return this.idAuditorBorrar;
	}

	public void setIdAuditorBorrar(BigDecimal idAuditorBorrar) {
		this.idAuditorBorrar = idAuditorBorrar;
	}


	public String getNumExtensionContacto() {
		return this.numExtensionContacto;
	}

	public void setNumExtensionContacto(String numExtensionContacto) {
		this.numExtensionContacto = numExtensionContacto;
	}

	public String getNumLadaContacto() {
		return this.numLadaContacto;
	}

	public void setNumLadaContacto(String numLadaContacto) {
		this.numLadaContacto = numLadaContacto;
	}

	public String getNumTelefonoContacto() {
		return this.numTelefonoContacto;
	}

	public void setNumTelefonoContacto(String numTelefonoContacto) {
		this.numTelefonoContacto = numTelefonoContacto;
	}

	public String getRefCorreoElectronicoTrabajo() {
		return this.refCorreoElectronicoTrabajo;
	}

	public void setRefCorreoElectronicoTrabajo(String refCorreoElectronicoTrabajo) {
		this.refCorreoElectronicoTrabajo = refCorreoElectronicoTrabajo;
	}

	public BigDecimal getTipCentroTrabajo() {
		return this.tipCentroTrabajo;
	}

	public void setTipCentroTrabajo(BigDecimal tipCentroTrabajo) {
		this.tipCentroTrabajo = tipCentroTrabajo;
	}

	public DlcUsuario getDlcUsuario() {
		return dlcUsuario;
	}

	public void setDlcUsuario(DlcUsuario dlcUsuario) {
		this.dlcUsuario = dlcUsuario;
	}

	public Boolean getIndVigencia() {
		return indVigencia;
	}

	public void setIndVigencia(Boolean indVigencia) {
		this.indVigencia = indVigencia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public DlcDelegacion getDlcDelegacion() {
		return dlcDelegacion;
	}

	public void setDlcDelegacion(DlcDelegacion dlcDelegacion) {
		this.dlcDelegacion = dlcDelegacion;
	}

	public DlcSubdelegacion getDlcSubdelegacion() {
		return dlcSubdelegacion;
	}

	public void setDlcSubdelegacion(DlcSubdelegacion dlcSubdelegacion) {
		this.dlcSubdelegacion = dlcSubdelegacion;
	}

}