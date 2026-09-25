package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the IETC_DATOS_CERTIFICADO database table.
 * 
 */
@Entity
@Table(name="IETC_DATOS_CERTIFICADO")
public class IetcDatosCertificado implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IetcDatosCertificadoPK id;

	@Column(name="CORREO_ELECTRONICO", length=40)
	private String correoElectronico;

	@Column(length=18)
	private String curp;

	@Column(name="CVE_REQUERIMIENTO", nullable=false, precision=22)
	private BigDecimal cveRequerimiento;

	@Column(name="CVE_SERIAL_ANTERIOR", length=20)
	private String cveSerialAnterior;

	@Column(name="FEC_VALIDO_FIN", length=20)
	private String fecValidoFin;

	@Column(name="FEC_VALIDO_INICIO", length=20)
	private String fecValidoInicio;

	@Column(name="NOM_NOMBRE_COMPLETO", length=80)
	private String nomNombreCompleto;

	@Column(name="NOM_USUARIO", length=18)
	private String nomUsuario;

	@Column(name="NUM_ESTATUS", length=20)
	private String numEstatus;

	@Column(name="RFC_ASOCIADO", length=25)
	private String rfcAsociado;

	@Column(length=20)
	private String telefono;

	//bi-directional many-to-one association to IetcCartasIetcFiel
	@OneToMany(mappedBy="ietcDatosCertificado")
	private List<IetcCartasIetcFiel> ietcCartasIetcFiels;

	//bi-directional many-to-one association to IetcCertificadoReq
	@OneToMany(mappedBy="ietcDatosCertificado")
	private List<IetcCertificadoReq> ietcCertificadoReqs;

	//bi-directional many-to-one association to IetcRegistrosContador
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_REGCONTADOR", nullable=false, insertable=false, updatable=false)
	private IetcRegistrosContador ietcRegistrosContador;

    public IetcDatosCertificado() {
    }

	public IetcDatosCertificadoPK getId() {
		return this.id;
	}

	public void setId(IetcDatosCertificadoPK id) {
		this.id = id;
	}
	
	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public BigDecimal getCveRequerimiento() {
		return this.cveRequerimiento;
	}

	public void setCveRequerimiento(BigDecimal cveRequerimiento) {
		this.cveRequerimiento = cveRequerimiento;
	}

	public String getCveSerialAnterior() {
		return this.cveSerialAnterior;
	}

	public void setCveSerialAnterior(String cveSerialAnterior) {
		this.cveSerialAnterior = cveSerialAnterior;
	}

	public String getFecValidoFin() {
		return this.fecValidoFin;
	}

	public void setFecValidoFin(String fecValidoFin) {
		this.fecValidoFin = fecValidoFin;
	}

	public String getFecValidoInicio() {
		return this.fecValidoInicio;
	}

	public void setFecValidoInicio(String fecValidoInicio) {
		this.fecValidoInicio = fecValidoInicio;
	}

	public String getNomNombreCompleto() {
		return this.nomNombreCompleto;
	}

	public void setNomNombreCompleto(String nomNombreCompleto) {
		this.nomNombreCompleto = nomNombreCompleto;
	}

	public String getNomUsuario() {
		return this.nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public String getNumEstatus() {
		return this.numEstatus;
	}

	public void setNumEstatus(String numEstatus) {
		this.numEstatus = numEstatus;
	}

	public String getRfcAsociado() {
		return this.rfcAsociado;
	}

	public void setRfcAsociado(String rfcAsociado) {
		this.rfcAsociado = rfcAsociado;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<IetcCartasIetcFiel> getIetcCartasIetcFiels() {
		return this.ietcCartasIetcFiels;
	}

	public void setIetcCartasIetcFiels(List<IetcCartasIetcFiel> ietcCartasIetcFiels) {
		this.ietcCartasIetcFiels = ietcCartasIetcFiels;
	}
	
	public List<IetcCertificadoReq> getIetcCertificadoReqs() {
		return this.ietcCertificadoReqs;
	}

	public void setIetcCertificadoReqs(List<IetcCertificadoReq> ietcCertificadoReqs) {
		this.ietcCertificadoReqs = ietcCertificadoReqs;
	}
	
	public IetcRegistrosContador getIetcRegistrosContador() {
		return this.ietcRegistrosContador;
	}

	public void setIetcRegistrosContador(IetcRegistrosContador ietcRegistrosContador) {
		this.ietcRegistrosContador = ietcRegistrosContador;
	}
	
}