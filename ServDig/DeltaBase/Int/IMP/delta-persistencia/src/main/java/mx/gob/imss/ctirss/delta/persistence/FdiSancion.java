package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDI_SANCION database table.
 * 
 */
@Entity
@Table(name="FDI_SANCION")
public class FdiSancion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_SANCION", nullable=false, precision=22)
	private long idSancion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ELABORA_NTPI")
	private Date fhElaboraNtpi;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ELABORA_NTS")
	private Date fhElaboraNts;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_FIN_SANCION")
	private Date fhFinSancion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_INICIO_SANCION")
	private Date fhInicioSancion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_MODIFICA")
	private Date fhModifica;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_NOTIFICA_PREIRREG")
	private Date fhNotificaPreirreg;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_NOTIFICA_SANCION")
	private Date fhNotificaSancion;

    @Lob()
	@Column(name="TX_CAUSA", nullable=false)
	private byte[] txCausa;

	@Column(name="TX_USER", length=18)
	private String txUser;

	//bi-directional many-to-one association to FdiTpSancion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TPO_SANCION")
	private FdiTpSancion fdiTpSancion;

	//bi-directional many-to-one association to FdiCpa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CV_CURP")
	private FdiCpa fdiCpa;

	//bi-directional many-to-one association to FdtPatronCpa
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RELACION")
	private FdtPatronCpa fdtPatronCpa;

	//bi-directional many-to-one association to FdcStatusSancion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_STATUS")
	private FdcStatusSancion fdcStatusSancion;

	//bi-directional many-to-one association to FdtSubdeleg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG"),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG")
		})
	private FdtSubdeleg fdtSubdeleg;

	//bi-directional many-to-one association to FdtImpugnacion
	@OneToMany(mappedBy="fdiSancion")
	private List<FdtImpugnacion> fdtImpugnacions;

    public FdiSancion() {
    }

	public long getIdSancion() {
		return this.idSancion;
	}

	public void setIdSancion(long idSancion) {
		this.idSancion = idSancion;
	}

	public Date getFhElaboraNtpi() {
		return this.fhElaboraNtpi;
	}

	public void setFhElaboraNtpi(Date fhElaboraNtpi) {
		this.fhElaboraNtpi = fhElaboraNtpi;
	}

	public Date getFhElaboraNts() {
		return this.fhElaboraNts;
	}

	public void setFhElaboraNts(Date fhElaboraNts) {
		this.fhElaboraNts = fhElaboraNts;
	}

	public Date getFhFinSancion() {
		return this.fhFinSancion;
	}

	public void setFhFinSancion(Date fhFinSancion) {
		this.fhFinSancion = fhFinSancion;
	}

	public Date getFhInicioSancion() {
		return this.fhInicioSancion;
	}

	public void setFhInicioSancion(Date fhInicioSancion) {
		this.fhInicioSancion = fhInicioSancion;
	}

	public Date getFhModifica() {
		return this.fhModifica;
	}

	public void setFhModifica(Date fhModifica) {
		this.fhModifica = fhModifica;
	}

	public Date getFhNotificaPreirreg() {
		return this.fhNotificaPreirreg;
	}

	public void setFhNotificaPreirreg(Date fhNotificaPreirreg) {
		this.fhNotificaPreirreg = fhNotificaPreirreg;
	}

	public Date getFhNotificaSancion() {
		return this.fhNotificaSancion;
	}

	public void setFhNotificaSancion(Date fhNotificaSancion) {
		this.fhNotificaSancion = fhNotificaSancion;
	}

	public byte[] getTxCausa() {
		return this.txCausa;
	}

	public void setTxCausa(byte[] txCausa) {
		this.txCausa = txCausa != null ? txCausa.clone() : null;
	}

	public String getTxUser() {
		return this.txUser;
	}

	public void setTxUser(String txUser) {
		this.txUser = txUser;
	}

	public FdiTpSancion getFdiTpSancion() {
		return this.fdiTpSancion;
	}

	public void setFdiTpSancion(FdiTpSancion fdiTpSancion) {
		this.fdiTpSancion = fdiTpSancion;
	}
	
	public FdiCpa getFdiCpa() {
		return this.fdiCpa;
	}

	public void setFdiCpa(FdiCpa fdiCpa) {
		this.fdiCpa = fdiCpa;
	}
	
	public FdtPatronCpa getFdtPatronCpa() {
		return this.fdtPatronCpa;
	}

	public void setFdtPatronCpa(FdtPatronCpa fdtPatronCpa) {
		this.fdtPatronCpa = fdtPatronCpa;
	}
	
	public FdcStatusSancion getFdcStatusSancion() {
		return this.fdcStatusSancion;
	}

	public void setFdcStatusSancion(FdcStatusSancion fdcStatusSancion) {
		this.fdcStatusSancion = fdcStatusSancion;
	}
	
	public FdtSubdeleg getFdtSubdeleg() {
		return this.fdtSubdeleg;
	}

	public void setFdtSubdeleg(FdtSubdeleg fdtSubdeleg) {
		this.fdtSubdeleg = fdtSubdeleg;
	}
	
	public List<FdtImpugnacion> getFdtImpugnacions() {
		return this.fdtImpugnacions;
	}

	public void setFdtImpugnacions(List<FdtImpugnacion> fdtImpugnacions) {
		this.fdtImpugnacions = fdtImpugnacions;
	}
	
}