package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDT_SOLICITUD_PRORROGA database table.
 * 
 */
@Entity
@Table(name="FDT_SOLICITUD_PRORROGA")
public class FdtSolicitudProrroga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NU_SOLICITUD", nullable=false, precision=22)
	private long nuSolicitud;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_SOLICITUD_PRORROGA")
	private Date fhSolicitudProrroga;

	@Column(name="IN_AUTORIZADA", nullable=false, length=1)
	private String inAutorizada;

	@Column(name="IN_TIPO", nullable=false, length=2)
	private String inTipo;

	@Column(name="NU_DIAS_SOLICITADOS", precision=22)
	private BigDecimal nuDiasSolicitados;

	@Column(name="TX_CAUSAS", length=1600)
	private String txCausas;

	//bi-directional many-to-one association to FdtAvisoSustitucion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUSTITUCION")
	private FdtAvisoSustitucion fdtAvisoSustitucion;

	//bi-directional many-to-one association to FdtAviso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AVISO")
	private FdtAviso fdtAviso;

    public FdtSolicitudProrroga() {
    }

	public long getNuSolicitud() {
		return this.nuSolicitud;
	}

	public void setNuSolicitud(long nuSolicitud) {
		this.nuSolicitud = nuSolicitud;
	}

	public Date getFhSolicitudProrroga() {
		return this.fhSolicitudProrroga;
	}

	public void setFhSolicitudProrroga(Date fhSolicitudProrroga) {
		this.fhSolicitudProrroga = fhSolicitudProrroga;
	}

	public String getInAutorizada() {
		return this.inAutorizada;
	}

	public void setInAutorizada(String inAutorizada) {
		this.inAutorizada = inAutorizada;
	}

	public String getInTipo() {
		return this.inTipo;
	}

	public void setInTipo(String inTipo) {
		this.inTipo = inTipo;
	}

	public BigDecimal getNuDiasSolicitados() {
		return this.nuDiasSolicitados;
	}

	public void setNuDiasSolicitados(BigDecimal nuDiasSolicitados) {
		this.nuDiasSolicitados = nuDiasSolicitados;
	}

	public String getTxCausas() {
		return this.txCausas;
	}

	public void setTxCausas(String txCausas) {
		this.txCausas = txCausas;
	}

	public FdtAvisoSustitucion getFdtAvisoSustitucion() {
		return this.fdtAvisoSustitucion;
	}

	public void setFdtAvisoSustitucion(FdtAvisoSustitucion fdtAvisoSustitucion) {
		this.fdtAvisoSustitucion = fdtAvisoSustitucion;
	}
	
	public FdtAviso getFdtAviso() {
		return this.fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}
	
}