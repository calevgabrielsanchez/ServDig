package mx.gob.imss.dacvass.scheduler.cron.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PROC_ODI_COMP_FISC")
public class ProcOdiCompFiscEntity implements Serializable {

	private static final long serialVersionUID = -8640467397229570174L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CVE_REGISTRO")
	private long cveRegistro;

	@Column(name = "NRP")
	private String nrp;

	@Column(name = "RFC")
	private String rfc;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "SUBTOTIMSS")
	private BigDecimal subToImss;

	@Column(name = "RECIMSS")
	private BigDecimal recImss;

	@Column(name = "ACTIMSS")
	private BigDecimal actImss;

	@Column(name = "SUBTOTRCV")
	private BigDecimal subToRcv;

	@Column(name = "RECRCV")
	private BigDecimal recRcv;

	@Column(name = "ACTRCV")
	private BigDecimal actRcv;

	@Lob
	@Column(name = "CFDI_XML")
	private String cfdiXml;

	@Column(name = "\"UUID\"")
	private String uuid;

	@Column(name = "ODI_ESTATUS")
	private String odiEstatus;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_PROCESO")
	private Date fecProceso;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ARCHIVO")
	private Date fecArchivo;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INICIO")
	private Date fecInicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_FIN")
	private Date fecFin;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO")
	private Date fecRegistro;

	@Column(name = "CVE_CODIGORESPUESTA")
	private String cveCodigoRespuesta;

	public long getCveRegistro() {
		return cveRegistro;
	}

	public void setCveRegistro(long cveRegistro) {
		this.cveRegistro = cveRegistro;
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getSubToImss() {
		return subToImss;
	}

	public void setSubToImss(BigDecimal subToImss) {
		this.subToImss = subToImss;
	}

	public BigDecimal getRecImss() {
		return recImss;
	}

	public void setRecImss(BigDecimal recImss) {
		this.recImss = recImss;
	}

	public BigDecimal getActImss() {
		return actImss;
	}

	public void setActImss(BigDecimal actImss) {
		this.actImss = actImss;
	}

	public BigDecimal getSubToRcv() {
		return subToRcv;
	}

	public void setSubToRcv(BigDecimal subToRcv) {
		this.subToRcv = subToRcv;
	}

	public BigDecimal getRecRcv() {
		return recRcv;
	}

	public void setRecRcv(BigDecimal recRcv) {
		this.recRcv = recRcv;
	}

	public BigDecimal getActRcv() {
		return actRcv;
	}

	public void setActRcv(BigDecimal actRcv) {
		this.actRcv = actRcv;
	}

	public String getCfdiXml() {
		return cfdiXml;
	}

	public void setCfdiXml(String cfdiXml) {
		this.cfdiXml = cfdiXml;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOdiEstatus() {
		return odiEstatus;
	}

	public void setOdiEstatus(String odiEstatus) {
		this.odiEstatus = odiEstatus;
	}

	public Date getFecProceso() {
		return fecProceso;
	}

	public void setFecProceso(Date fecProceso) {
		this.fecProceso = fecProceso;
	}

	public Date getFecArchivo() {
		return fecArchivo;
	}

	public void setFecArchivo(Date fecArchivo) {
		this.fecArchivo = fecArchivo;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public String getCveCodigoRespuesta() {
		return cveCodigoRespuesta;
	}

	public void setCveCodigoRespuesta(String cveCodigoRespuesta) {
		this.cveCodigoRespuesta = cveCodigoRespuesta;
	}

}
