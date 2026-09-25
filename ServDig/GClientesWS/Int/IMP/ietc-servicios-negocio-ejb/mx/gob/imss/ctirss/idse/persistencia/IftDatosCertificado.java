package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the IFT_DATOS_CERTIFICADO database table.
 * 
 */
@Entity
@Table(name="IFT_DATOS_CERTIFICADO")
@NamedQuery(name="IftDatosCertificado.findAll", query="SELECT i FROM IftDatosCertificado i")
public class IftDatosCertificado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_SERIAL")
	private String cveSerial;

	@Column(name="CVE_SERIAL_ANTERIOR")
	private String cveSerialAnterior;

	@Column(name="ID_ROL_SOLICITANTE")
	private BigDecimal idRolSolicitante;

	@Column(name="NOM_NOMBRE_COMPLETO")
	private String nomNombreCompleto;

	@Column(name="NUM_ESTATUS")
	private BigDecimal numEstatus;

	@Column(name="REF_CORREO_ELECTRONICO")
	private String refCorreoElectronico;

	@Column(name="REF_CURP")
	private String refCurp;

	@Column(name="REF_NOMBRE_USUARIO")
	private String refNombreUsuario;

	@Column(name="REF_RFC_ASOCIADO")
	private String refRfcAsociado;

	@Column(name="STP_FECHA_VALIDO_FIN")
	private Timestamp stpFechaValidoFin;

	@Column(name="STP_FECHA_VALIDO_INICIO")
	private Timestamp stpFechaValidoInicio;

	private String telefono;

	//bi-directional many-to-one association to IfrCertificadoReq
	@OneToMany(mappedBy="iftDatosCertificado")
	private List<IfrCertificadoReq> ifrCertificadoReqs;

	public IftDatosCertificado() {
	}

	public String getCveSerial() {
		return this.cveSerial;
	}

	public void setCveSerial(String cveSerial) {
		this.cveSerial = cveSerial;
	}

	public String getCveSerialAnterior() {
		return this.cveSerialAnterior;
	}

	public void setCveSerialAnterior(String cveSerialAnterior) {
		this.cveSerialAnterior = cveSerialAnterior;
	}

	public BigDecimal getIdRolSolicitante() {
		return this.idRolSolicitante;
	}

	public void setIdRolSolicitante(BigDecimal idRolSolicitante) {
		this.idRolSolicitante = idRolSolicitante;
	}

	public String getNomNombreCompleto() {
		return this.nomNombreCompleto;
	}

	public void setNomNombreCompleto(String nomNombreCompleto) {
		this.nomNombreCompleto = nomNombreCompleto;
	}

	public BigDecimal getNumEstatus() {
		return this.numEstatus;
	}

	public void setNumEstatus(BigDecimal numEstatus) {
		this.numEstatus = numEstatus;
	}

	public String getRefCorreoElectronico() {
		return this.refCorreoElectronico;
	}

	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}

	public String getRefCurp() {
		return this.refCurp;
	}

	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}

	public String getRefNombreUsuario() {
		return this.refNombreUsuario;
	}

	public void setRefNombreUsuario(String refNombreUsuario) {
		this.refNombreUsuario = refNombreUsuario;
	}

	public String getRefRfcAsociado() {
		return this.refRfcAsociado;
	}

	public void setRefRfcAsociado(String refRfcAsociado) {
		this.refRfcAsociado = refRfcAsociado;
	}

	public Timestamp getStpFechaValidoFin() {
		return this.stpFechaValidoFin;
	}

	public void setStpFechaValidoFin(Timestamp stpFechaValidoFin) {
		this.stpFechaValidoFin = stpFechaValidoFin;
	}

	public Timestamp getStpFechaValidoInicio() {
		return this.stpFechaValidoInicio;
	}

	public void setStpFechaValidoInicio(Timestamp stpFechaValidoInicio) {
		this.stpFechaValidoInicio = stpFechaValidoInicio;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<IfrCertificadoReq> getIfrCertificadoReqs() {
		return this.ifrCertificadoReqs;
	}

	public void setIfrCertificadoReqs(List<IfrCertificadoReq> ifrCertificadoReqs) {
		this.ifrCertificadoReqs = ifrCertificadoReqs;
	}

	public IfrCertificadoReq addIfrCertificadoReq(IfrCertificadoReq ifrCertificadoReq) {
		getIfrCertificadoReqs().add(ifrCertificadoReq);
		ifrCertificadoReq.setIftDatosCertificado(this);

		return ifrCertificadoReq;
	}

	public IfrCertificadoReq removeIfrCertificadoReq(IfrCertificadoReq ifrCertificadoReq) {
		getIfrCertificadoReqs().remove(ifrCertificadoReq);
		ifrCertificadoReq.setIftDatosCertificado(null);

		return ifrCertificadoReq;
	}

}