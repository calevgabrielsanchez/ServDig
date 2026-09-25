package mx.gob.imss.ctirss.delta.cobranza.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PROC_ODI_COMP_FISC database table.
 * 
 */
@Entity
@Table(name="PROC_ODI_COMP_FISC" , schema="MGCARGA1")
public class ProcOdiCompFisc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_REGISTRO")
	private long cveRegistro;

	private BigDecimal actimss;

	private BigDecimal actrcv;

	@Lob
	@Column(name="CFDI_XML")
	private String cfdiXml;

	private BigDecimal er;

	private String estatus;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ARCHIVO")
	private Date fecArchivo;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_FIN")
	private Date fecFin;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO")
	private Date fecInicio;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_PROCESO")
	private Date fecProceso;

	@Temporal(TemporalType.DATE)
	private Date fecpago;

	private BigDecimal folsua;

	private String nombre;

	private String nrp;

	@Column(name="ODI_ESTATUS")
	private String odiEstatus;
	
	
	@Column(name="CVE_CODIGORESPUESTA")
	private String cveCodigoRespuesta;

	private BigDecimal per;

	private BigDecimal recimss;

	private BigDecimal recrcv;

	private String rfc;

	private BigDecimal subtotimss;

	private BigDecimal subtotrcv;

	@Column(name="\"UUID\"")
	private String uuid;

	public ProcOdiCompFisc() {
	}

	public long getCveRegistro() {
		return this.cveRegistro;
	}

	public void setCveRegistro(long cveRegistro) {
		this.cveRegistro = cveRegistro;
	}

	public BigDecimal getActimss() {
		return this.actimss;
	}

	public void setActimss(BigDecimal actimss) {
		this.actimss = actimss;
	}

	public BigDecimal getActrcv() {
		return this.actrcv;
	}

	public void setActrcv(BigDecimal actrcv) {
		this.actrcv = actrcv;
	}

	public String getCfdiXml() {
		return this.cfdiXml;
	}

	public void setCfdiXml(String cfdiXml) {
		this.cfdiXml = cfdiXml;
	}

	public BigDecimal getEr() {
		return this.er;
	}

	public void setEr(BigDecimal er) {
		this.er = er;
	}

	public String getEstatus() {
		return this.estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Date getFecArchivo() {
		return this.fecArchivo;
	}

	public void setFecArchivo(Date fecArchivo) {
		this.fecArchivo = fecArchivo;
	}

	public Date getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecProceso() {
		return this.fecProceso;
	}

	public void setFecProceso(Date fecProceso) {
		this.fecProceso = fecProceso;
	}

	public Date getFecpago() {
		return this.fecpago;
	}

	public void setFecpago(Date fecpago) {
		this.fecpago = fecpago;
	}

	public BigDecimal getFolsua() {
		return this.folsua;
	}

	public void setFolsua(BigDecimal folsua) {
		this.folsua = folsua;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNrp() {
		return this.nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getOdiEstatus() {
		return this.odiEstatus;
	}

	public void setOdiEstatus(String odiEstatus) {
		this.odiEstatus = odiEstatus;
	}

	public BigDecimal getPer() {
		return this.per;
	}

	public void setPer(BigDecimal per) {
		this.per = per;
	}

	public BigDecimal getRecimss() {
		return this.recimss;
	}

	public void setRecimss(BigDecimal recimss) {
		this.recimss = recimss;
	}

	public BigDecimal getRecrcv() {
		return this.recrcv;
	}

	public void setRecrcv(BigDecimal recrcv) {
		this.recrcv = recrcv;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public BigDecimal getSubtotimss() {
		return this.subtotimss;
	}

	public void setSubtotimss(BigDecimal subtotimss) {
		this.subtotimss = subtotimss;
	}

	public BigDecimal getSubtotrcv() {
		return this.subtotrcv;
	}

	public void setSubtotrcv(BigDecimal subtotrcv) {
		this.subtotrcv = subtotrcv;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProcOdiCompFisc [cveRegistro=" + cveRegistro + ", actimss="
				+ actimss + ", actrcv=" + actrcv + ", cfdiXml=" + cfdiXml
				+ ", er=" + er + ", estatus=" + estatus + ", fecArchivo="
				+ fecArchivo + ", fecFin=" + fecFin + ", fecInicio="
				+ fecInicio + ", fecProceso=" + fecProceso + ", fecpago="
				+ fecpago + ", folsua=" + folsua + ", nombre=" + nombre
				+ ", nrp=" + nrp + ", odiEstatus=" + odiEstatus + ", per="
				+ per + ", recimss=" + recimss + ", recrcv=" + recrcv
				+ ", rfc=" + rfc + ", subtotimss=" + subtotimss
				+ ", subtotrcv=" + subtotrcv + ", uuid=" + uuid + "]";
	}

	/**
	 * @return the cveCodigoRespuesta
	 */
	public String getCveCodigoRespuesta() {
		return cveCodigoRespuesta;
	}

	/**
	 * @param cveCodigoRespuesta the cveCodigoRespuesta to set
	 */
	public void setCveCodigoRespuesta(String cveCodigoRespuesta) {
		this.cveCodigoRespuesta = cveCodigoRespuesta;
	}

}