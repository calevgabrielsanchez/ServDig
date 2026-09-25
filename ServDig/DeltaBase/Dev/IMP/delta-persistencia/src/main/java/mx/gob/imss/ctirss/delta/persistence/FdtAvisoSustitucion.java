package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_AVISO_SUSTITUCION database table.
 * 
 */
@Entity
@Table(name="FDT_AVISO_SUSTITUCION")
public class FdtAvisoSustitucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_SUSTITUCION", nullable=false, precision=22)
	private long idSustitucion;

	@Column(name="CV_CURP", nullable=false, length=18)
	private String cvCurp;

	@Column(name="CV_CURP_ANT", nullable=false, length=18)
	private String cvCurpAnt;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_AUTORIZACION_RECHAZO_SUST")
	private Date fhAutorizacionRechazoSust;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_SUSTITUCION", nullable=false)
	private Date fhSustitucion;

	@Column(name="FOLIO_AVISO_ANT", length=20)
	private String folioAvisoAnt;

	@Column(name="IN_AUTORIZA", length=1)
	private String inAutoriza;

	@Column(name="IN_CPA_AUTORIZA", length=1)
	private String inCpaAutoriza;

	@Column(name="IN_POR_IMPEDIMENTOS", nullable=false, length=1)
	private String inPorImpedimentos;

	@Column(name="NU_SOLICITUD", precision=22)
	private BigDecimal nuSolicitud;

	@Column(name="TX_CAUSAS", length=1600)
	private String txCausas;

	//bi-directional many-to-one association to FdtAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AVISO")
	private FdtAviso fdtAviso;

	//bi-directional many-to-one association to FdtSolicitudProrroga
	@OneToMany(mappedBy="fdtAvisoSustitucion")
	private List<FdtSolicitudProrroga> fdtSolicitudProrrogas;

    public FdtAvisoSustitucion() {
    }

	public long getIdSustitucion() {
		return this.idSustitucion;
	}

	public void setIdSustitucion(long idSustitucion) {
		this.idSustitucion = idSustitucion;
	}

	public String getCvCurp() {
		return this.cvCurp;
	}

	public void setCvCurp(String cvCurp) {
		this.cvCurp = cvCurp;
	}

	public String getCvCurpAnt() {
		return this.cvCurpAnt;
	}

	public void setCvCurpAnt(String cvCurpAnt) {
		this.cvCurpAnt = cvCurpAnt;
	}

	public Date getFhAutorizacionRechazoSust() {
		return this.fhAutorizacionRechazoSust;
	}

	public void setFhAutorizacionRechazoSust(Date fhAutorizacionRechazoSust) {
		this.fhAutorizacionRechazoSust = fhAutorizacionRechazoSust;
	}

	public Date getFhSustitucion() {
		return this.fhSustitucion;
	}

	public void setFhSustitucion(Date fhSustitucion) {
		this.fhSustitucion = fhSustitucion;
	}

	public String getFolioAvisoAnt() {
		return this.folioAvisoAnt;
	}

	public void setFolioAvisoAnt(String folioAvisoAnt) {
		this.folioAvisoAnt = folioAvisoAnt;
	}

	public String getInAutoriza() {
		return this.inAutoriza;
	}

	public void setInAutoriza(String inAutoriza) {
		this.inAutoriza = inAutoriza;
	}

	public String getInCpaAutoriza() {
		return this.inCpaAutoriza;
	}

	public void setInCpaAutoriza(String inCpaAutoriza) {
		this.inCpaAutoriza = inCpaAutoriza;
	}

	public String getInPorImpedimentos() {
		return this.inPorImpedimentos;
	}

	public void setInPorImpedimentos(String inPorImpedimentos) {
		this.inPorImpedimentos = inPorImpedimentos;
	}

	public BigDecimal getNuSolicitud() {
		return this.nuSolicitud;
	}

	public void setNuSolicitud(BigDecimal nuSolicitud) {
		this.nuSolicitud = nuSolicitud;
	}

	public String getTxCausas() {
		return this.txCausas;
	}

	public void setTxCausas(String txCausas) {
		this.txCausas = txCausas;
	}

	public FdtAviso getFdtAviso() {
		return this.fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}
	
	public List<FdtSolicitudProrroga> getFdtSolicitudProrrogas() {
		return this.fdtSolicitudProrrogas;
	}

	public void setFdtSolicitudProrrogas(List<FdtSolicitudProrroga> fdtSolicitudProrrogas) {
		this.fdtSolicitudProrrogas = fdtSolicitudProrrogas;
	}
	
}