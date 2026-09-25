package mx.imss.ctirss.catalogos.base.model;


import javax.persistence.*;

import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;
import mx.imss.ctirss.framework.base.model.AbstractModel;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DLC_USUARIO database table.
 * 
 */
/**
 * @author Adolfo Meza
 *
 */
@MappedSuperclass
public abstract class AbstractDlcUsuario extends AbstractModel {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="DLC_USUARIO_CVEIDUSUARIO_GENERATOR", sequenceName="SEQ_CVE_USUARIODEN")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DLC_USUARIO_CVEIDUSUARIO_GENERATOR")
	@Column(name="CVE_ID_USUARIO")
	private long cveIdUsuario;

	@Column(name="CVE_ID_PERSONA")
	private Long cveIdPersona;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name="NOM_MATERNO")
	private String nomMaterno;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NOM_PATERNO")
	private String nomPaterno;

	@Column(name="NOM_USUARIO_SISTEMA")
	private String nomUsuarioSistema;

	@Column(name="NUM_MATRICULA")
	private String numMatricula;

	@Column(name="NUM_NSS")
	private String numNss;

	@Column(name="REF_PASSWORD")
	private String refPassword;

	@Column(name="TIP_USUARIO")
	private BigDecimal tipUsuario;

	

	
	
	
	//bi-directional many-to-one association to SegUsuarioFuncionario
	@OneToMany(mappedBy="dlcUsuario")
	private Set<DlcUsuarioFuncionario> dlcUsuarioFuncionarios;
	
    public AbstractDlcUsuario() {
    }

    
    
	public Set<DlcUsuarioFuncionario> getDlcUsuarioFuncionarios() {
		return dlcUsuarioFuncionarios;
	}



	public void setDlcUsuarioFuncionarios(
			Set<DlcUsuarioFuncionario> dlcUsuarioFuncionarios) {
		this.dlcUsuarioFuncionarios = dlcUsuarioFuncionarios;
	}



	public long getCveIdUsuario() {
		return this.cveIdUsuario;
	}

	public void setCveIdUsuario(long cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public String getNomMaterno() {
		return this.nomMaterno;
	}

	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPaterno() {
		return this.nomPaterno;
	}

	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	public String getNomUsuarioSistema() {
		return this.nomUsuarioSistema;
	}

	public void setNomUsuarioSistema(String nomUsuarioSistema) {
		this.nomUsuarioSistema = nomUsuarioSistema;
	}

	public String getNumMatricula() {
		return this.numMatricula;
	}

	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}

	public String getNumNss() {
		return this.numNss;
	}

	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}

	public String getRefPassword() {
		return this.refPassword;
	}

	public void setRefPassword(String refPassword) {
		this.refPassword = refPassword;
	}

	public BigDecimal getTipUsuario() {
		return this.tipUsuario;
	}

	public void setTipUsuario(BigDecimal tipUsuario) {
		this.tipUsuario = tipUsuario;
	}



	public Long getCveIdPersona() {
		return cveIdPersona;
	}



	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

}