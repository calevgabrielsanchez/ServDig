package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgtCatCriterioSeleccion;

/**
 * The persistent class for the CGT_PROMOCION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgtPromocion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	private String folio;

	@Transient
	private String folioNum;

	private String afil15;

	@Temporal(TemporalType.DATE)
	private Date aop;

	@Temporal(TemporalType.DATE)
	private Date c;

	private BigDecimal copconvsp;

	private BigDecimal costototalcontratado;

	@Temporal(TemporalType.DATE)
	private Date cr;

	@Column(name = "CVE_MODALIDAD")
	private BigDecimal cveModalidad;

	@Column(name = "CVE_PATRON")
	private String cvePatron;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	private BigDecimal dv;

	private BigDecimal esttrabajreg;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG")
	private Date fecFechareg;

	private String foliocorreccion;

	@Column(name = "ID_CODIGOOBRA")
	private BigDecimal idCodigoobra;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CRITERIOSELECCION")
	private CgtCatCriterioSeleccion cgtCatCriterioSeleccion;

	@Column(name = "ID_MOTIVOCANCELACION")
	private BigDecimal idMotivocancelacion;

	// bi-directional many-to-one association to AbstractCgcCatorigen
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ORIGEN")
	private CgcCatOrigen cgcCatOrigen;

	// bi-directional many-to-one association to AbstractCgcCattipo
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPO")
	private CgcCatTipo cgcCatTipo;

	@Column(name = "ID_STATUS")
	private BigDecimal idStatus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_SUBDELEGACION", insertable = true, updatable = true)
	private SacSubdelegacion sacSubdelegacion;

	private BigDecimal mesesestimados;

	private String noconvenio;

	private String nombre;

	private String nooficioc;

	@Temporal(TemporalType.DATE)
	private Date nop;

	private BigDecimal noparcialidades;

	private String observaciones;

	@Temporal(TemporalType.DATE)
	private Date oi;

	@Temporal(TemporalType.DATE)
	private Date ope;

	@Temporal(TemporalType.DATE)
	private Date pai;

	@Temporal(TemporalType.DATE)
	private Date periodoal;

	@Temporal(TemporalType.DATE)
	private Date periododel;

	private BigDecimal porcavance;

	private BigDecimal porcavanceestimado;

	private BigDecimal porcregularizado;

	@Temporal(TemporalType.DATE)
	private Date pr;

	private BigDecimal rcvconvsp;

	private String rnooficioope;

	@Temporal(TemporalType.DATE)
	private Date sp;

	private BigDecimal superficieestimada;

	private BigDecimal trabomisos;

	private BigDecimal trabrevisados;

	private BigDecimal trabsubddeclarados;

	private String ubicacion;

	public AbstractCgtPromocion() {
	}

	public String getFolio() {
		return this.folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getAfil15() {
		return this.afil15;
	}

	public void setAfil15(String afil15) {
		this.afil15 = afil15;
	}

	public Date getAop() {
		return this.aop;
	}

	public void setAop(Date aop) {
		this.aop = aop;
	}

	public Date getC() {
		return this.c;
	}

	public void setC(Date c) {
		this.c = c;
	}

	public BigDecimal getCopconvsp() {
		return this.copconvsp;
	}

	public void setCopconvsp(BigDecimal copconvsp) {
		this.copconvsp = copconvsp;
	}

	public BigDecimal getCostototalcontratado() {
		return this.costototalcontratado;
	}

	public void setCostototalcontratado(BigDecimal costototalcontratado) {
		this.costototalcontratado = costototalcontratado;
	}

	public Date getCr() {
		return this.cr;
	}

	public void setCr(Date cr) {
		this.cr = cr;
	}

	public BigDecimal getCveModalidad() {
		return this.cveModalidad;
	}

	public void setCveModalidad(BigDecimal cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

	public String getCvePatron() {
		return this.cvePatron;
	}

	public void setCvePatron(String cvePatron) {
		this.cvePatron = cvePatron;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public BigDecimal getDv() {
		return this.dv;
	}

	public void setDv(BigDecimal dv) {
		this.dv = dv;
	}

	public BigDecimal getEsttrabajreg() {
		return this.esttrabajreg;
	}

	public void setEsttrabajreg(BigDecimal esttrabajreg) {
		this.esttrabajreg = esttrabajreg;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public String getFoliocorreccion() {
		return this.foliocorreccion;
	}

	public void setFoliocorreccion(String foliocorreccion) {
		this.foliocorreccion = foliocorreccion;
	}

	public BigDecimal getIdCodigoobra() {
		return this.idCodigoobra;
	}

	public void setIdCodigoobra(BigDecimal idCodigoobra) {
		this.idCodigoobra = idCodigoobra;
	}

	public BigDecimal getIdMotivocancelacion() {
		return this.idMotivocancelacion;
	}

	public CgtCatCriterioSeleccion getCgtCatCriterioSeleccion() {
		return cgtCatCriterioSeleccion;
	}

	public void setCgtCatCriterioSeleccion(
			CgtCatCriterioSeleccion cgtCatCriterioSeleccion) {
		this.cgtCatCriterioSeleccion = cgtCatCriterioSeleccion;
	}

	public void setIdMotivocancelacion(BigDecimal idMotivocancelacion) {
		this.idMotivocancelacion = idMotivocancelacion;
	}

	public BigDecimal getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(BigDecimal idStatus) {
		this.idStatus = idStatus;
	}

	public SacSubdelegacion getSacSubdelegacion() {
		return sacSubdelegacion;
	}

	public void setSacSubdelegacion(SacSubdelegacion sacSubdelegacion) {
		this.sacSubdelegacion = sacSubdelegacion;
	}

	public BigDecimal getMesesestimados() {
		return this.mesesestimados;
	}

	public void setMesesestimados(BigDecimal mesesestimados) {
		this.mesesestimados = mesesestimados;
	}

	public String getNoconvenio() {
		return this.noconvenio;
	}

	public void setNoconvenio(String noconvenio) {
		this.noconvenio = noconvenio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNooficioc() {
		return this.nooficioc;
	}

	public void setNooficioc(String nooficioc) {
		this.nooficioc = nooficioc;
	}

	public Date getNop() {
		return this.nop;
	}

	public void setNop(Date nop) {
		this.nop = nop;
	}

	public BigDecimal getNoparcialidades() {
		return this.noparcialidades;
	}

	public void setNoparcialidades(BigDecimal noparcialidades) {
		this.noparcialidades = noparcialidades;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getOi() {
		return this.oi;
	}

	public void setOi(Date oi) {
		this.oi = oi;
	}

	public Date getOpe() {
		return this.ope;
	}

	public void setOpe(Date ope) {
		this.ope = ope;
	}

	public Date getPai() {
		return this.pai;
	}

	public void setPai(Date pai) {
		this.pai = pai;
	}

	public Date getPeriodoal() {
		return this.periodoal;
	}

	public void setPeriodoal(Date periodoal) {
		this.periodoal = periodoal;
	}

	public Date getPeriododel() {
		return this.periododel;
	}

	public void setPeriododel(Date periododel) {
		this.periododel = periododel;
	}

	public BigDecimal getPorcavance() {
		return this.porcavance;
	}

	public void setPorcavance(BigDecimal porcavance) {
		this.porcavance = porcavance;
	}

	public BigDecimal getPorcavanceestimado() {
		return this.porcavanceestimado;
	}

	public void setPorcavanceestimado(BigDecimal porcavanceestimado) {
		this.porcavanceestimado = porcavanceestimado;
	}

	public BigDecimal getPorcregularizado() {
		return this.porcregularizado;
	}

	public void setPorcregularizado(BigDecimal porcregularizado) {
		this.porcregularizado = porcregularizado;
	}

	public Date getPr() {
		return this.pr;
	}

	public void setPr(Date pr) {
		this.pr = pr;
	}

	public BigDecimal getRcvconvsp() {
		return this.rcvconvsp;
	}

	public void setRcvconvsp(BigDecimal rcvconvsp) {
		this.rcvconvsp = rcvconvsp;
	}

	public String getRnooficioope() {
		return this.rnooficioope;
	}

	public void setRnooficioope(String rnooficioope) {
		this.rnooficioope = rnooficioope;
	}

	public Date getSp() {
		return this.sp;
	}

	public void setSp(Date sp) {
		this.sp = sp;
	}

	public BigDecimal getSuperficieestimada() {
		return this.superficieestimada;
	}

	public void setSuperficieestimada(BigDecimal superficieestimada) {
		this.superficieestimada = superficieestimada;
	}

	public BigDecimal getTrabomisos() {
		return this.trabomisos;
	}

	public void setTrabomisos(BigDecimal trabomisos) {
		this.trabomisos = trabomisos;
	}

	public BigDecimal getTrabrevisados() {
		return this.trabrevisados;
	}

	public void setTrabrevisados(BigDecimal trabrevisados) {
		this.trabrevisados = trabrevisados;
	}

	public BigDecimal getTrabsubddeclarados() {
		return this.trabsubddeclarados;
	}

	public void setTrabsubddeclarados(BigDecimal trabsubddeclarados) {
		this.trabsubddeclarados = trabsubddeclarados;
	}

	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public CgcCatOrigen getCgcCatOrigen() {
		return cgcCatOrigen;
	}

	public void setCgcCatOrigen(CgcCatOrigen cgcCatOrigen) {
		this.cgcCatOrigen = cgcCatOrigen;
	}

	public CgcCatTipo getCgcCatTipo() {
		return cgcCatTipo;
	}

	public void setCgcCatTipo(CgcCatTipo cgcCatTipo) {
		this.cgcCatTipo = cgcCatTipo;
	}

	public String getFolioNum() {
		return folioNum;
	}

	public void setFolioNum(String folioNum) {
		this.folioNum = folioNum;
	}

}