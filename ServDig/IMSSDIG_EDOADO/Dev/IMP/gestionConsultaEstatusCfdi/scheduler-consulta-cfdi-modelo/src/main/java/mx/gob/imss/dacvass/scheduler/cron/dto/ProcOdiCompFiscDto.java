package mx.gob.imss.dacvass.scheduler.cron.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProcOdiCompFiscDto implements Serializable {

	private static final long serialVersionUID = 6710639374981134136L;

	private long cveRegistro;
	private String nrp;
	private String rfc;
	private String nombre;
	private BigDecimal subToImss;
	private BigDecimal recImss;
	private BigDecimal actImss;
	private BigDecimal subToRcv;
	private BigDecimal recRcv;
	private BigDecimal actRcv;
	private String cfdiXml;
	private String uuid;
	private String odiEstatus;
	private Date fecProceso;
	private Date fecArchivo;
	private Date fecInicio;
	private Date fecFin;
	private Date fecRegistro;
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
