package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CFC_USUARIOINT database table.
 * 
 */
@Entity
@Table(name="CFC_USUARIOINT")
public class CfcUsuarioint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_USUARIO", nullable=false, precision=22)
	private long cveUsuario;

	@Column(name="DESC_MATRICULA", length=15)
	private String descMatricula;

	@Column(name="DESC_NOMBRE", length=50)
	private String descNombre;

	@Column(name="DESC_PWD", length=10)
	private String descPwd;

	@Column(name="DESC_USUARIO", length=10)
	private String descUsuario;

	@Column(name="IND_HABILITADO", precision=22)
	private BigDecimal indHabilitado;

	@Column(length=60)
	private String mail;

	@Column(name="NOM_APMATERNO", length=50)
	private String nomApmaterno;

	@Column(name="NOM_APPATERNO", length=50)
	private String nomAppaterno;

	@Column(length=20)
	private String telefono;

	//bi-directional many-to-one association to CfcFiscaliza
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_FISCALIZA")
	private CfcFiscaliza cfcFiscaliza;

	//bi-directional many-to-one association to FdtSubdeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG"),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG")
		})
	private FdtSubdeleg fdtSubdeleg;

    public CfcUsuarioint() {
    }

	public long getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(long cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public String getDescMatricula() {
		return this.descMatricula;
	}

	public void setDescMatricula(String descMatricula) {
		this.descMatricula = descMatricula;
	}

	public String getDescNombre() {
		return this.descNombre;
	}

	public void setDescNombre(String descNombre) {
		this.descNombre = descNombre;
	}

	public String getDescPwd() {
		return this.descPwd;
	}

	public void setDescPwd(String descPwd) {
		this.descPwd = descPwd;
	}

	public String getDescUsuario() {
		return this.descUsuario;
	}

	public void setDescUsuario(String descUsuario) {
		this.descUsuario = descUsuario;
	}

	public BigDecimal getIndHabilitado() {
		return this.indHabilitado;
	}

	public void setIndHabilitado(BigDecimal indHabilitado) {
		this.indHabilitado = indHabilitado;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNomApmaterno() {
		return this.nomApmaterno;
	}

	public void setNomApmaterno(String nomApmaterno) {
		this.nomApmaterno = nomApmaterno;
	}

	public String getNomAppaterno() {
		return this.nomAppaterno;
	}

	public void setNomAppaterno(String nomAppaterno) {
		this.nomAppaterno = nomAppaterno;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public CfcFiscaliza getCfcFiscaliza() {
		return this.cfcFiscaliza;
	}

	public void setCfcFiscaliza(CfcFiscaliza cfcFiscaliza) {
		this.cfcFiscaliza = cfcFiscaliza;
	}
	
	public FdtSubdeleg getFdtSubdeleg() {
		return this.fdtSubdeleg;
	}

	public void setFdtSubdeleg(FdtSubdeleg fdtSubdeleg) {
		this.fdtSubdeleg = fdtSubdeleg;
	}
	
}