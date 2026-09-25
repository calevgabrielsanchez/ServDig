package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDT_BENEFICIARIOSERVICIO database table.
 * 
 */
@Entity
@Table(name="FDT_BENEFICIARIOSERVICIO")
public class FdtBeneficiarioservicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_CENTROTRABAJO", nullable=false, precision=22)
	private long cveCentrotrabajo;

	@Column(name="CVE_REG_PATRONBENEF", length=11)
	private String cveRegPatronbenef;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CONTRATOFIN")
	private Date fecContratofin;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CONTRATOINI")
	private Date fecContratoini;

	@Column(name="NU_ADMINISTRATIVO", precision=22)
	private BigDecimal nuAdministrativo;

	@Column(name="NU_CANTIDADPERSONAL", precision=22)
	private BigDecimal nuCantidadpersonal;

	@Column(name="NU_OPERATIVO", precision=22)
	private BigDecimal nuOperativo;

	@Column(name="NU_PROFESIONAL", precision=22)
	private BigDecimal nuProfesional;

	@Column(name="TX_DOMICILIOSERVICIO", length=200)
	private String txDomicilioservicio;

	@Column(name="TX_PERFILPUESTOCATEG", length=100)
	private String txPerfilpuestocateg;

	@Column(name="TX_SERVICIOPRESTA", length=100)
	private String txServiciopresta;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

    public FdtBeneficiarioservicio() {
    }

	public long getCveCentrotrabajo() {
		return this.cveCentrotrabajo;
	}

	public void setCveCentrotrabajo(long cveCentrotrabajo) {
		this.cveCentrotrabajo = cveCentrotrabajo;
	}

	public String getCveRegPatronbenef() {
		return this.cveRegPatronbenef;
	}

	public void setCveRegPatronbenef(String cveRegPatronbenef) {
		this.cveRegPatronbenef = cveRegPatronbenef;
	}

	public Date getFecContratofin() {
		return this.fecContratofin;
	}

	public void setFecContratofin(Date fecContratofin) {
		this.fecContratofin = fecContratofin;
	}

	public Date getFecContratoini() {
		return this.fecContratoini;
	}

	public void setFecContratoini(Date fecContratoini) {
		this.fecContratoini = fecContratoini;
	}

	public BigDecimal getNuAdministrativo() {
		return this.nuAdministrativo;
	}

	public void setNuAdministrativo(BigDecimal nuAdministrativo) {
		this.nuAdministrativo = nuAdministrativo;
	}

	public BigDecimal getNuCantidadpersonal() {
		return this.nuCantidadpersonal;
	}

	public void setNuCantidadpersonal(BigDecimal nuCantidadpersonal) {
		this.nuCantidadpersonal = nuCantidadpersonal;
	}

	public BigDecimal getNuOperativo() {
		return this.nuOperativo;
	}

	public void setNuOperativo(BigDecimal nuOperativo) {
		this.nuOperativo = nuOperativo;
	}

	public BigDecimal getNuProfesional() {
		return this.nuProfesional;
	}

	public void setNuProfesional(BigDecimal nuProfesional) {
		this.nuProfesional = nuProfesional;
	}

	public String getTxDomicilioservicio() {
		return this.txDomicilioservicio;
	}

	public void setTxDomicilioservicio(String txDomicilioservicio) {
		this.txDomicilioservicio = txDomicilioservicio;
	}

	public String getTxPerfilpuestocateg() {
		return this.txPerfilpuestocateg;
	}

	public void setTxPerfilpuestocateg(String txPerfilpuestocateg) {
		this.txPerfilpuestocateg = txPerfilpuestocateg;
	}

	public String getTxServiciopresta() {
		return this.txServiciopresta;
	}

	public void setTxServiciopresta(String txServiciopresta) {
		this.txServiciopresta = txServiciopresta;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
}