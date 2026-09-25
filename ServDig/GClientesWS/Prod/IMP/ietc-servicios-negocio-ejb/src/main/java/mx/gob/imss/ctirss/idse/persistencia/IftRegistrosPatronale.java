package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the IFT_REGISTROS_PATRONALES database table.
 * 
 */
@Entity
@Table(name="IFT_REGISTROS_PATRONALES")
@NamedQuery(name="IftRegistrosPatronale.findAll", query="SELECT i FROM IftRegistrosPatronale i")
public class IftRegistrosPatronale implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IftRegistrosPatronalePK id;

	@Column(name="CVE_ESTATUS")
	private java.math.BigDecimal cveEstatus;

	@Column(name="CVE_TIPO_PERSONA")
	private java.math.BigDecimal cveTipoPersona;

	@Column(name="REF_MOTIVO_RECHAZO")
	private String refMotivoRechazo;

	@Column(name="REF_RAZON_SOCIAL")
	private String refRazonSocial;

	@Column(name="REF_RFC_REGISTRO_PATRONAL")
	private String refRfcRegistroPatronal;

	@Column(name="REF_USUARIO_SUBDEL_AUTORIZA")
	private String refUsuarioSubdelAutoriza;

	@Column(name="REF_USUARIO_SUBDEL_CANCELA")
	private String refUsuarioSubdelCancela;

	@Column(name="STP_FECHA_ACTIVACION")
	private Date stpFechaActivacion;

	@Column(name="STP_FECHA_CANCELACION")
	private Date stpFechaCancelacion;

	@Column(name="STP_FECHA_RECEPCION")
	private Date stpFechaRecepcion;

	//bi-directional many-to-one association to IfrCertificadoReq
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_REQUERIMIENTO", referencedColumnName="CVE_REQUERIMIENTO", insertable=false, updatable=false),
		@JoinColumn(name="CVE_SERIAL", referencedColumnName="CVE_SERIAL", insertable=false, updatable=false)
		})
	private IfrCertificadoReq ifrCertificadoReq;

	public IftRegistrosPatronale() {
	}

	public IftRegistrosPatronalePK getId() {
		return this.id;
	}

	public void setId(IftRegistrosPatronalePK id) {
		this.id = id;
	}

	public java.math.BigDecimal getCveEstatus() {
		return this.cveEstatus;
	}

	public void setCveEstatus(java.math.BigDecimal cveEstatus) {
		this.cveEstatus = cveEstatus;
	}

	public java.math.BigDecimal getCveTipoPersona() {
		return this.cveTipoPersona;
	}

	public void setCveTipoPersona(java.math.BigDecimal cveTipoPersona) {
		this.cveTipoPersona = cveTipoPersona;
	}

	public String getRefMotivoRechazo() {
		return this.refMotivoRechazo;
	}

	public void setRefMotivoRechazo(String refMotivoRechazo) {
		this.refMotivoRechazo = refMotivoRechazo;
	}

	public String getRefRazonSocial() {
		return this.refRazonSocial;
	}

	public void setRefRazonSocial(String refRazonSocial) {
		this.refRazonSocial = refRazonSocial;
	}

	public String getRefRfcRegistroPatronal() {
		return this.refRfcRegistroPatronal;
	}

	public void setRefRfcRegistroPatronal(String refRfcRegistroPatronal) {
		this.refRfcRegistroPatronal = refRfcRegistroPatronal;
	}

	public String getRefUsuarioSubdelAutoriza() {
		return this.refUsuarioSubdelAutoriza;
	}

	public void setRefUsuarioSubdelAutoriza(String refUsuarioSubdelAutoriza) {
		this.refUsuarioSubdelAutoriza = refUsuarioSubdelAutoriza;
	}

	public String getRefUsuarioSubdelCancela() {
		return this.refUsuarioSubdelCancela;
	}

	public void setRefUsuarioSubdelCancela(String refUsuarioSubdelCancela) {
		this.refUsuarioSubdelCancela = refUsuarioSubdelCancela;
	}

	public Date getStpFechaActivacion() {
		return this.stpFechaActivacion;
	}

	public void setStpFechaActivacion(Date stpFechaActivacion) {
		this.stpFechaActivacion = stpFechaActivacion;
	}

	public Date getStpFechaCancelacion() {
		return this.stpFechaCancelacion;
	}

	public void setStpFechaCancelacion(Timestamp stpFechaCancelacion) {
		this.stpFechaCancelacion = stpFechaCancelacion;
	}

	public Date getStpFechaRecepcion() {
		return this.stpFechaRecepcion;
	}

	public void setStpFechaRecepcion(Date stpFechaRecepcion) {
		this.stpFechaRecepcion = stpFechaRecepcion;
	}

	public IfrCertificadoReq getIfrCertificadoReq() {
		return this.ifrCertificadoReq;
	}

	public void setIfrCertificadoReq(IfrCertificadoReq ifrCertificadoReq) {
		this.ifrCertificadoReq = ifrCertificadoReq;
	}

}