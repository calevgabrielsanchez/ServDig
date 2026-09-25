package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the IETC_REGISTROS_CONTADOR database table.
 * 
 */
@Entity
@Table(name="IETC_REGISTROS_CONTADOR")
public class IetcRegistrosContador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_REGCONTADOR", nullable=false, precision=22)
	private long cveRegcontador;

	@Column(name="CVE_DELEG_ORIG", precision=2)
	private BigDecimal cveDelegOrig;

	@Column(name="FEC_ACTIVACION")
	private Timestamp fecActivacion;

	@Column(name="FEC_CANCELACION")
	private Timestamp fecCancelacion;

	@Column(name="FEC_RECEPCION")
	private Timestamp fecRecepcion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO")
	private Date fecRegistro;

	@Column(name="MOT_RECHAZO", length=100)
	private String motRechazo;

	@Column(name="NOM_CONTADOR", length=80)
	private String nomContador;

	@Column(name="NUM_CONTADOR", precision=22)
	private BigDecimal numContador;

	@Column(name="RFC_CONTADOR", length=13)
	private String rfcContador;

	@Column(name="SDELEG_ORIG", precision=2)
	private BigDecimal sdelegOrig;

	@Column(name="USR_AUTORIZA", length=20)
	private String usrAutoriza;

	@Column(name="USR_CANCELA", length=18)
	private String usrCancela;

	//bi-directional many-to-one association to IetcDatosCertificado
	@OneToMany(mappedBy="ietcRegistrosContador")
	private List<IetcDatosCertificado> ietcDatosCertificados;

	//bi-directional many-to-one association to IetcEstatus
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ESTATUS")
	private IetcEstatus ietcEstatus;

    public IetcRegistrosContador() {
    }

	public long getCveRegcontador() {
		return this.cveRegcontador;
	}

	public void setCveRegcontador(long cveRegcontador) {
		this.cveRegcontador = cveRegcontador;
	}

	public BigDecimal getCveDelegOrig() {
		return this.cveDelegOrig;
	}

	public void setCveDelegOrig(BigDecimal cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}

	public Timestamp getFecActivacion() {
		return this.fecActivacion;
	}

	public void setFecActivacion(Timestamp fecActivacion) {
		this.fecActivacion = fecActivacion;
	}

	public Timestamp getFecCancelacion() {
		return this.fecCancelacion;
	}

	public void setFecCancelacion(Timestamp fecCancelacion) {
		this.fecCancelacion = fecCancelacion;
	}

	public Timestamp getFecRecepcion() {
		return this.fecRecepcion;
	}

	public void setFecRecepcion(Timestamp fecRecepcion) {
		this.fecRecepcion = fecRecepcion;
	}

	public Date getFecRegistro() {
		return this.fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public String getMotRechazo() {
		return this.motRechazo;
	}

	public void setMotRechazo(String motRechazo) {
		this.motRechazo = motRechazo;
	}

	public String getNomContador() {
		return this.nomContador;
	}

	public void setNomContador(String nomContador) {
		this.nomContador = nomContador;
	}

	public BigDecimal getNumContador() {
		return this.numContador;
	}

	public void setNumContador(BigDecimal numContador) {
		this.numContador = numContador;
	}

	public String getRfcContador() {
		return this.rfcContador;
	}

	public void setRfcContador(String rfcContador) {
		this.rfcContador = rfcContador;
	}

	public BigDecimal getSdelegOrig() {
		return this.sdelegOrig;
	}

	public void setSdelegOrig(BigDecimal sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

	public String getUsrAutoriza() {
		return this.usrAutoriza;
	}

	public void setUsrAutoriza(String usrAutoriza) {
		this.usrAutoriza = usrAutoriza;
	}

	public String getUsrCancela() {
		return this.usrCancela;
	}

	public void setUsrCancela(String usrCancela) {
		this.usrCancela = usrCancela;
	}

	public List<IetcDatosCertificado> getIetcDatosCertificados() {
		return this.ietcDatosCertificados;
	}

	public void setIetcDatosCertificados(List<IetcDatosCertificado> ietcDatosCertificados) {
		this.ietcDatosCertificados = ietcDatosCertificados;
	}
	
	public IetcEstatus getIetcEstatus() {
		return this.ietcEstatus;
	}

	public void setIetcEstatus(IetcEstatus ietcEstatus) {
		this.ietcEstatus = ietcEstatus;
	}
	
}