/**
 * RBG clean service
 * 2013-AGO-03
 */
package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public abstract class AbstractCgtAnexoPago extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_PAGO")
	@SequenceGenerator(name = "ID_PAGO_GENERATOR", sequenceName = "CRS_ID_PAGO")
	@GeneratedValue(generator = "ID_PAGO_GENERATOR")
	public long idPago;

	private BigDecimal copact;

	private BigDecimal copmultas;

	private BigDecimal copperiodo;

	private BigDecimal coprec;

	private BigDecimal copsp;

	@Column(name = "CVE_MODALIDAD")
	private BigDecimal cveModalidad;

	@Column(name = "CVE_PATRON")
	private String cvePatron;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG")
	private Date fecFechareg;

	@Temporal(TemporalType.DATE)
	private Date fechapago;

	private String folioordeningreso;

	private String foliosua;

	private String nocredito;

	private BigDecimal rcvact;

	private BigDecimal rcvmultas;

	private BigDecimal rcvperiodo;

	private BigDecimal rcvrec;

	private BigDecimal rcvsp;

	@Column(name = "ID_PROCESO")
	private Integer idProceso;

	// bi-directional many-to-one association to AbstractCgtPromocion

	@Column(name = "FOLIO")
	private String folio;

	public Integer getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}

	public AbstractCgtAnexoPago() {
	}

	public long getIdPago() {
		return this.idPago;
	}

	public void setIdPago(long idPago) {
		this.idPago = idPago;
	}

	public BigDecimal getCopact() {
		return this.copact;
	}

	public void setCopact(BigDecimal copact) {
		this.copact = copact;
	}

	public BigDecimal getCopmultas() {
		return this.copmultas;
	}

	public void setCopmultas(BigDecimal copmultas) {
		this.copmultas = copmultas;
	}

	public BigDecimal getCopperiodo() {
		return this.copperiodo;
	}

	public void setCopperiodo(BigDecimal copperiodo) {
		this.copperiodo = copperiodo;
	}

	public BigDecimal getCoprec() {
		return this.coprec;
	}

	public void setCoprec(BigDecimal coprec) {
		this.coprec = coprec;
	}

	public BigDecimal getCopsp() {
		return this.copsp;
	}

	public void setCopsp(BigDecimal copsp) {
		this.copsp = copsp;
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

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public Date getFechapago() {
		return this.fechapago;
	}

	public void setFechapago(Date fechapago) {
		this.fechapago = fechapago;
	}

	public String getFolioordeningreso() {
		return this.folioordeningreso;
	}

	public void setFolioordeningreso(String folioordeningreso) {
		this.folioordeningreso = folioordeningreso;
	}

	public String getFoliosua() {
		return this.foliosua;
	}

	public void setFoliosua(String foliosua) {
		this.foliosua = foliosua;
	}

	public String getNocredito() {
		return this.nocredito;
	}

	public void setNocredito(String nocredito) {
		this.nocredito = nocredito;
	}

	public BigDecimal getRcvact() {
		return this.rcvact;
	}

	public void setRcvact(BigDecimal rcvact) {
		this.rcvact = rcvact;
	}

	public BigDecimal getRcvmultas() {
		return this.rcvmultas;
	}

	public void setRcvmultas(BigDecimal rcvmultas) {
		this.rcvmultas = rcvmultas;
	}

	public BigDecimal getRcvperiodo() {
		return this.rcvperiodo;
	}

	public void setRcvperiodo(BigDecimal rcvperiodo) {
		this.rcvperiodo = rcvperiodo;
	}

	public BigDecimal getRcvrec() {
		return this.rcvrec;
	}

	public void setRcvrec(BigDecimal rcvrec) {
		this.rcvrec = rcvrec;
	}

	public BigDecimal getRcvsp() {
		return this.rcvsp;
	}

	public void setRcvsp(BigDecimal rcvsp) {
		this.rcvsp = rcvsp;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

}