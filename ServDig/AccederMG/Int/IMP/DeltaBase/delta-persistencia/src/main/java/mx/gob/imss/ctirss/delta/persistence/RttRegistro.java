package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RTT_REGISTRO_RT")
public class RttRegistro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5840842757796432530L;

	@Id
	@Column(name = "CVE_ID_REGISTRO_RT")
	private Integer cveIdRegistroRt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "RTT_CVE_DELEGACION", insertable = true, updatable = true),
			@JoinColumn(name = "RTT_NUM_CICLO", insertable = true, updatable = true) })
	private RttEncabezado encabezado;

	@Column(name = "RTT_REG_PATRON")
	private String rttRegPatron;

	@Column(name = "RTT_NUM_NSS")
	private String rttNumNss;

	@Column(name = "RTT_CVE_PATRON")
	private String rttCvePatron;

	@Column(name = "RTT_REF_CURP")
	private String rttRefCurp;

	@Column(name = "RTT_NUM_CONSECUENCIA")
	private Integer rttNumConsecuencia;

	@Column(name = "RTT_FEC_CARGA")
	private Date rttFecCarga;

	@Column(name = "RTT_FEC_INICIO_ACCIDENTE")
	private Date rttFecInicioAccidente;

	@Column(name = "RTT_FEC_FIN_ACCIDENTE")
	private Date rttFecFinAccidente;

	@Column(name = "RTT_DIAS_SUBSIDIADOS")
	private Integer rttDiasSubsidiados;

	@Column(name = "RTT_NUM_TIPO_RIESGO")
	private Integer rttNumTipoRiesgo;

	@Column(name = "RTT_POR_INCAPACIDAD")
	private Integer rttPorIncapacidad;

	@Column(name = "RTT_REF_APELLIDO_PATERNO")
	private String rttRefApellidoPaterno;

	@Column(name = "RTT_REF_APELLIDO_MATERNO")
	private String rttRefApellidoMaterno;

	@Column(name = "RTT_NOMBRE")
	private String rttNombre;

	@Column(name = "RTT_REF_RECAIDA_REVALUAC")
	private String rttRefRecaidaRevaluac;

	@Column(name = "RTT_DEFUNCION")
	private String rttDefuncion;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "CVE_ID_REGISTRO_RT", 
		joinColumns = { @JoinColumn(name = "CVE_ID_REGISTRO_RT") }, 
		inverseJoinColumns = { @JoinColumn(name = "CVE_ID_MENSAJE") })
	List<RtcCatalogoMensajes> mensajes;

	public List<RtcCatalogoMensajes> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<RtcCatalogoMensajes> mensajes) {
		this.mensajes = mensajes;
	}

	public Integer getCveIdRegistroRt() {
		return cveIdRegistroRt;
	}

	public void setCveIdRegistroRt(Integer cveIdRegistroRt) {
		this.cveIdRegistroRt = cveIdRegistroRt;
	}

	public RttEncabezado getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(RttEncabezado encabezado) {
		this.encabezado = encabezado;
	}

	public String getRttRegPatron() {
		return rttRegPatron;
	}

	public void setRttRegPatron(String rttRegPatron) {
		this.rttRegPatron = rttRegPatron;
	}

	public String getRttNumNss() {
		return rttNumNss;
	}

	public void setRttNumNss(String rttNumNss) {
		this.rttNumNss = rttNumNss;
	}

	public String getRttCvePatron() {
		return rttCvePatron;
	}

	public void setRttCvePatron(String rttCvePatron) {
		this.rttCvePatron = rttCvePatron;
	}

	public String getRttRefCurp() {
		return rttRefCurp;
	}

	public void setRttRefCurp(String rttRefCurp) {
		this.rttRefCurp = rttRefCurp;
	}

	public Integer getRttNumConsecuencia() {
		return rttNumConsecuencia;
	}

	public void setRttNumConsecuencia(Integer rttNumConsecuencia) {
		this.rttNumConsecuencia = rttNumConsecuencia;
	}

	public Date getRttFecCarga() {
		return rttFecCarga;
	}

	public void setRttFecCarga(Date rttFecCarga) {
		this.rttFecCarga = rttFecCarga;
	}

	public Date getRttFecInicioAccidente() {
		return rttFecInicioAccidente;
	}

	public void setRttFecInicioAccidente(Date rttFecInicioAccidente) {
		this.rttFecInicioAccidente = rttFecInicioAccidente;
	}

	public Date getRttFecFinAccidente() {
		return rttFecFinAccidente;
	}

	public void setRttFecFinAccidente(Date rttFecFinAccidente) {
		this.rttFecFinAccidente = rttFecFinAccidente;
	}

	public Integer getRttDiasSubsidiados() {
		return rttDiasSubsidiados;
	}

	public void setRttDiasSubsidiados(Integer rttDiasSubsidiados) {
		this.rttDiasSubsidiados = rttDiasSubsidiados;
	}

	public Integer getRttNumTipoRiesgo() {
		return rttNumTipoRiesgo;
	}

	public void setRttNumTipoRiesgo(Integer rttNumTipoRiesgo) {
		this.rttNumTipoRiesgo = rttNumTipoRiesgo;
	}

	public Integer getRttPorIncapacidad() {
		return rttPorIncapacidad;
	}

	public void setRttPorIncapacidad(Integer rttPorIncapacidad) {
		this.rttPorIncapacidad = rttPorIncapacidad;
	}

	public String getRttRefApellidoPaterno() {
		return rttRefApellidoPaterno;
	}

	public void setRttRefApellidoPaterno(String rttRefApellidoPaterno) {
		this.rttRefApellidoPaterno = rttRefApellidoPaterno;
	}

	public String getRttRefApellidoMaterno() {
		return rttRefApellidoMaterno;
	}

	public void setRttRefApellidoMaterno(String rttRefApellidoMaterno) {
		this.rttRefApellidoMaterno = rttRefApellidoMaterno;
	}

	public String getRttNombre() {
		return rttNombre;
	}

	public void setRttNombre(String rttNombre) {
		this.rttNombre = rttNombre;
	}

	public String getRttRefRecaidaRevaluac() {
		return rttRefRecaidaRevaluac;
	}

	public void setRttRefRecaidaRevaluac(String rttRefRecaidaRevaluac) {
		this.rttRefRecaidaRevaluac = rttRefRecaidaRevaluac;
	}

	public String getRttDefuncion() {
		return rttDefuncion;
	}

	public void setRttDefuncion(String rttDefuncion) {
		this.rttDefuncion = rttDefuncion;
	}

}
