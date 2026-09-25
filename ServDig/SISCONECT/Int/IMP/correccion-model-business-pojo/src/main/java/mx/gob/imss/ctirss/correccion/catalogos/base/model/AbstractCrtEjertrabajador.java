package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public class AbstractCrtEjertrabajador extends AbstractModel {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -911048471945250513L;
	private BigDecimal cveEjertrab;
	private Long cveAcexoCorrPat;
	private Long cveEjercicio;
	private Long cveCategoria;
	private BigDecimal cveTrabajador;
	private Date fecIngreso;
	private BigDecimal nuAntiguedadAnios;
	private String txDepartamento;
	//private String txCategoria;
	private BigDecimal impSalariodiario;
	private BigDecimal indPruebasel;
	private BigDecimal indExcsaltop;
	private BigDecimal indAnatiempext;
	private BigDecimal indAnahon;
	private String txActividad;
	private Date fecFechareg;
	private String cveUsuario;
	
	@Id
	@Column(name = "CVE_EJERTRAB", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getCveEjertrab() {
		return this.cveEjertrab;
	}

	public void setCveEjertrab(BigDecimal cveEjertrab) {
		this.cveEjertrab = cveEjertrab;
	}

	@Column(name = "CVE_TRABAJADOR")
	public BigDecimal getCveTrabajador() {
		return this.cveTrabajador;
	}

	public void setCveTrabajador(BigDecimal cveTrabajador) {
		this.cveTrabajador = cveTrabajador;
	}

	@Column(name = "CVE_ANEXOSOLCORRPAT")
	public Long getCveAcexoCorrPat() {
		return cveAcexoCorrPat;
	}

	public void setCveAcexoCorrPat(Long cveAcexoCorrPat) {
		this.cveAcexoCorrPat = cveAcexoCorrPat;
	}
	
	@Column(name = "CVE_EJERCICIO")
	public Long getCveEjercicio() {
		return cveEjercicio;
	}

	public void setCveEjercicio(Long cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INGRESO", length = 7)
	public Date getFecIngreso() {
		return this.fecIngreso;
	}

	public void setFecIngreso(Date fecIngreso) {
		this.fecIngreso = fecIngreso;
	}

	@Column(name = "NU_ANTIGUEDAD_ANIOS", precision = 22, scale = 0)
	public BigDecimal getNuAntiguedadAnios() {
		return this.nuAntiguedadAnios;
	}

	public void setNuAntiguedadAnios(BigDecimal nuAntiguedadAnios) {
		this.nuAntiguedadAnios = nuAntiguedadAnios;
	}

	@Column(name = "TX_DEPARTAMENTO", length = 50)
	public String getTxDepartamento() {
		return this.txDepartamento;
	}

	public void setTxDepartamento(String txDepartamento) {
		this.txDepartamento = txDepartamento;
	}

//	@Column(name = "TX_CATEGORIA", length = 50)
//	public String getTxCategoria() {
//		return this.txCategoria;
//	}

	


	
//	
//	public void setTxCategoria(String txCategoria) {
//		this.txCategoria = txCategoria;
//	}

	@Column(name = "IMP_SALARIODIARIO", precision = 10)
	public BigDecimal getImpSalariodiario() {
		return this.impSalariodiario;
	}

	public void setImpSalariodiario(BigDecimal impSalariodiario) {
		this.impSalariodiario = impSalariodiario;
	}

	@Column(name = "IND_PRUEBASEL", precision = 22, scale = 0)
	public BigDecimal getIndPruebasel() {
		return this.indPruebasel;
	}

	public void setIndPruebasel(BigDecimal indPruebasel) {
		this.indPruebasel = indPruebasel;
	}

	@Column(name = "IND_EXCSALTOP", precision = 22, scale = 0)
	public BigDecimal getIndExcsaltop() {
		return this.indExcsaltop;
	}

	public void setIndExcsaltop(BigDecimal indExcsaltop) {
		this.indExcsaltop = indExcsaltop;
	}

	@Column(name = "IND_ANATIEMPEXT", precision = 22, scale = 0)
	public BigDecimal getIndAnatiempext() {
		return this.indAnatiempext;
	}

	public void setIndAnatiempext(BigDecimal indAnatiempext) {
		this.indAnatiempext = indAnatiempext;
	}

	@Column(name = "IND_ANAHON", precision = 22, scale = 0)
	public BigDecimal getIndAnahon() {
		return this.indAnahon;
	}

	public void setIndAnahon(BigDecimal indAnahon) {
		this.indAnahon = indAnahon;
	}

	@Column(name = "TX_ACTIVIDAD", length = 50)
	public String getTxActividad() {
		return this.txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG", length = 7)
	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	@Column(name = "CVE_USUARIO", length = 20)
	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}


	public AbstractCrtEjertrabajador(BigDecimal cveEjertrab,
			Long cveAcexoCorrPat, Long cveEjercicio, BigDecimal cveTrabajador,
			Date fecIngreso, BigDecimal nuAntiguedadAnios,
			String txDepartamento, Long cveCategoria,
			BigDecimal impSalariodiario, BigDecimal indPruebasel,
			BigDecimal indExcsaltop, BigDecimal indAnatiempext,
			BigDecimal indAnahon, String txActividad, Date fecFechareg,
			String cveUsuario) {
		super();
		this.cveEjertrab = cveEjertrab;
		this.cveAcexoCorrPat = cveAcexoCorrPat;
		this.cveEjercicio = cveEjercicio;
		this.cveTrabajador = cveTrabajador;
		this.fecIngreso = fecIngreso;
		this.nuAntiguedadAnios = nuAntiguedadAnios;
		this.txDepartamento = txDepartamento;
		this.cveCategoria=cveCategoria;
		//this.txCategoria = txCategoria;
		this.impSalariodiario = impSalariodiario;
		this.indPruebasel = indPruebasel;
		this.indExcsaltop = indExcsaltop;
		this.indAnatiempext = indAnatiempext;
		this.indAnahon = indAnahon;
		this.txActividad = txActividad;
		this.fecFechareg = fecFechareg;
		this.cveUsuario = cveUsuario;
	}

	public AbstractCrtEjertrabajador() {
		super();
	}

	
	@Column(name = "CVE_GRUPOCATEGORIA")
	public Long getCveCategoria() {
		return cveCategoria;
	}

	public void setCveCategoria(Long cveCategoria) {
		this.cveCategoria = cveCategoria;
	}

	
	
	
}
