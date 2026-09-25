package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@NamedQueries({
	@NamedQuery(name = "getUsuarioLogin", 
			query = "select u from DitUsuario u where u.nomUsuarioSistema=:nomUsuario")
})
/**
 * The persistent class for the DIT_USUARIO database table.
 * 
 */
@Entity
@Table(name="DIT_USUARIO")
public class DitUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_USUARIO", nullable=false, precision=22)
	private long cveIdUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NOM_MATERNO", length=200)
	private String nomMaterno;

	@Column(name="NOM_NOMBRE", length=200)
	private String nomNombre;

	@Column(name="NOM_PATERNO", length=200)
	private String nomPaterno;

	@Column(name="NOM_USUARIO_SISTEMA", length=20)
	private String nomUsuarioSistema;

	@Column(name="REF_PASSWORD", length=100)
	private String refPassword;

	@Column(name="TIP_USUARIO", precision=22)
	private BigDecimal tipUsuario;

	//bi-directional many-to-one association to DitBitacora
	@OneToMany(mappedBy="ditUsuario")
	private List<DitBitacora> ditBitacoras;

	//bi-directional many-to-one association to DitCuotasPatronSujetoOblig
	@OneToMany(mappedBy="ditUsuario")
	private List<DitCuotasPatronSujetoOblig> ditCuotasPatronSujetoObligs;

	//bi-directional many-to-one association to DitRegistroDerechohabiente
	@OneToMany(mappedBy="ditUsuario")
	private List<DitRegistroDerechohabiente> ditRegistroDerechohabientes;


	//bi-directional many-to-one association to DicPerfilUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERFIL_USUARIO")
	private DicPerfilUsuario dicPerfilUsuario;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA")
	private DitPersona ditPersona;

	//bi-directional many-to-one association to DitUsuarioFuncionario
	@OneToMany(mappedBy="ditUsuario")
	private List<DitUsuarioFuncionario> ditUsuarioFuncionarios;

	//bi-directional many-to-one association to DitUsuarioOrdinario
	@OneToMany(mappedBy="ditUsuario")
	private List<DitUsuarioOrdinario> ditUsuarioOrdinarios;

    public DitUsuario() {
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

	public List<DitBitacora> getDitBitacoras() {
		return this.ditBitacoras;
	}

	public void setDitBitacoras(List<DitBitacora> ditBitacoras) {
		this.ditBitacoras = ditBitacoras;
	}
	
	public List<DitCuotasPatronSujetoOblig> getDitCuotasPatronSujetoObligs() {
		return this.ditCuotasPatronSujetoObligs;
	}

	public void setDitCuotasPatronSujetoObligs(List<DitCuotasPatronSujetoOblig> ditCuotasPatronSujetoObligs) {
		this.ditCuotasPatronSujetoObligs = ditCuotasPatronSujetoObligs;
	}
	
	public List<DitRegistroDerechohabiente> getDitRegistroDerechohabientes() {
		return this.ditRegistroDerechohabientes;
	}

	public void setDitRegistroDerechohabientes(List<DitRegistroDerechohabiente> ditRegistroDerechohabientes) {
		this.ditRegistroDerechohabientes = ditRegistroDerechohabientes;
	}
	
	
	public DicPerfilUsuario getDicPerfilUsuario() {
		return this.dicPerfilUsuario;
	}

	public void setDicPerfilUsuario(DicPerfilUsuario dicPerfilUsuario) {
		this.dicPerfilUsuario = dicPerfilUsuario;
	}
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
	public List<DitUsuarioFuncionario> getDitUsuarioFuncionarios() {
		return this.ditUsuarioFuncionarios;
	}

	public void setDitUsuarioFuncionarios(List<DitUsuarioFuncionario> ditUsuarioFuncionarios) {
		this.ditUsuarioFuncionarios = ditUsuarioFuncionarios;
	}
	
	public List<DitUsuarioOrdinario> getDitUsuarioOrdinarios() {
		return this.ditUsuarioOrdinarios;
	}

	public void setDitUsuarioOrdinarios(List<DitUsuarioOrdinario> ditUsuarioOrdinarios) {
		this.ditUsuarioOrdinarios = ditUsuarioOrdinarios;
	}
	
}