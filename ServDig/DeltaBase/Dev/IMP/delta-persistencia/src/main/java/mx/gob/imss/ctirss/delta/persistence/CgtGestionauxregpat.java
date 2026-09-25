package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CGT_GESTIONAUXREGPAT database table.
 * 
 */
@Entity
@Table(name="CGT_GESTIONAUXREGPAT")
public class CgtGestionauxregpat implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CgtGestionauxregpatPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_FECHA_CARGA")
	private Date fhFechaCarga;

	@Column(name="IM_ACTUALIZACION", precision=12, scale=2)
	private BigDecimal imActualizacion;

	@Column(name="IM_MULTASPAGADAS", precision=12, scale=2)
	private BigDecimal imMultaspagadas;

	@Column(name="IM_RCVPAGADO", precision=12, scale=2)
	private BigDecimal imRcvpagado;

	@Column(name="IM_RECARGOS", precision=12, scale=2)
	private BigDecimal imRecargos;

	@Column(name="IM_RECLASIFICACION", precision=12, scale=2)
	private BigDecimal imReclasificacion;

	@Column(name="IM_SUERTEPRIN", precision=12, scale=2)
	private BigDecimal imSuerteprin;

	@Column(name="IM_TOTAL", precision=12, scale=2)
	private BigDecimal imTotal;

	@Column(name="NU_AVIBAJAPRESENTA", precision=22)
	private BigDecimal nuAvibajapresenta;

	@Column(name="NU_AVIDESCCORRESP1ER", precision=22)
	private BigDecimal nuAvidesccorresp1er;

	@Column(name="NU_AVIINSCBAJAS", precision=22)
	private BigDecimal nuAviinscbajas;

	@Column(name="NU_AVIMODIFSALARIO", precision=22)
	private BigDecimal nuAvimodifsalario;

	@Column(name="NU_AVIMODIFSALDESC", precision=22)
	private BigDecimal nuAvimodifsaldesc;

	@Column(name="NU_AVIRECTIFFECHAPOST", precision=22)
	private BigDecimal nuAvirectiffechapost;

	@Column(name="NU_AVITRABNOINSC", precision=22)
	private BigDecimal nuAvitrabnoinsc;

	@Column(name="NU_TPREGISTRO", precision=22)
	private BigDecimal nuTpregistro;

	@Column(name="NU_TRABREGULA", precision=22)
	private BigDecimal nuTrabregula;

	@Column(name="NU_TRABREVISADOS", precision=22)
	private BigDecimal nuTrabrevisados;

	@Column(name="TX_RPU", length=11)
	private String txRpu;

	//bi-directional many-to-one association to CgtGestionsinadi
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="TX_NUMAVISO", referencedColumnName="TX_NUMAVISO", nullable=false, insertable=false, updatable=false)
		})
	private CgtGestionsinadi cgtGestionsinadi;

	//bi-directional many-to-one association to FdtPatron
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL"),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON")
		})
	private FdtPatron fdtPatron;

	//bi-directional many-to-one association to CgtGestionpago
	@OneToMany(mappedBy="cgtGestionauxregpat")
	private List<CgtGestionpago> cgtGestionpagos;

    public CgtGestionauxregpat() {
    }

	public CgtGestionauxregpatPK getId() {
		return this.id;
	}

	public void setId(CgtGestionauxregpatPK id) {
		this.id = id;
	}
	
	public Date getFhFechaCarga() {
		return this.fhFechaCarga;
	}

	public void setFhFechaCarga(Date fhFechaCarga) {
		this.fhFechaCarga = fhFechaCarga;
	}

	public BigDecimal getImActualizacion() {
		return this.imActualizacion;
	}

	public void setImActualizacion(BigDecimal imActualizacion) {
		this.imActualizacion = imActualizacion;
	}

	public BigDecimal getImMultaspagadas() {
		return this.imMultaspagadas;
	}

	public void setImMultaspagadas(BigDecimal imMultaspagadas) {
		this.imMultaspagadas = imMultaspagadas;
	}

	public BigDecimal getImRcvpagado() {
		return this.imRcvpagado;
	}

	public void setImRcvpagado(BigDecimal imRcvpagado) {
		this.imRcvpagado = imRcvpagado;
	}

	public BigDecimal getImRecargos() {
		return this.imRecargos;
	}

	public void setImRecargos(BigDecimal imRecargos) {
		this.imRecargos = imRecargos;
	}

	public BigDecimal getImReclasificacion() {
		return this.imReclasificacion;
	}

	public void setImReclasificacion(BigDecimal imReclasificacion) {
		this.imReclasificacion = imReclasificacion;
	}

	public BigDecimal getImSuerteprin() {
		return this.imSuerteprin;
	}

	public void setImSuerteprin(BigDecimal imSuerteprin) {
		this.imSuerteprin = imSuerteprin;
	}

	public BigDecimal getImTotal() {
		return this.imTotal;
	}

	public void setImTotal(BigDecimal imTotal) {
		this.imTotal = imTotal;
	}

	public BigDecimal getNuAvibajapresenta() {
		return this.nuAvibajapresenta;
	}

	public void setNuAvibajapresenta(BigDecimal nuAvibajapresenta) {
		this.nuAvibajapresenta = nuAvibajapresenta;
	}

	public BigDecimal getNuAvidesccorresp1er() {
		return this.nuAvidesccorresp1er;
	}

	public void setNuAvidesccorresp1er(BigDecimal nuAvidesccorresp1er) {
		this.nuAvidesccorresp1er = nuAvidesccorresp1er;
	}

	public BigDecimal getNuAviinscbajas() {
		return this.nuAviinscbajas;
	}

	public void setNuAviinscbajas(BigDecimal nuAviinscbajas) {
		this.nuAviinscbajas = nuAviinscbajas;
	}

	public BigDecimal getNuAvimodifsalario() {
		return this.nuAvimodifsalario;
	}

	public void setNuAvimodifsalario(BigDecimal nuAvimodifsalario) {
		this.nuAvimodifsalario = nuAvimodifsalario;
	}

	public BigDecimal getNuAvimodifsaldesc() {
		return this.nuAvimodifsaldesc;
	}

	public void setNuAvimodifsaldesc(BigDecimal nuAvimodifsaldesc) {
		this.nuAvimodifsaldesc = nuAvimodifsaldesc;
	}

	public BigDecimal getNuAvirectiffechapost() {
		return this.nuAvirectiffechapost;
	}

	public void setNuAvirectiffechapost(BigDecimal nuAvirectiffechapost) {
		this.nuAvirectiffechapost = nuAvirectiffechapost;
	}

	public BigDecimal getNuAvitrabnoinsc() {
		return this.nuAvitrabnoinsc;
	}

	public void setNuAvitrabnoinsc(BigDecimal nuAvitrabnoinsc) {
		this.nuAvitrabnoinsc = nuAvitrabnoinsc;
	}

	public BigDecimal getNuTpregistro() {
		return this.nuTpregistro;
	}

	public void setNuTpregistro(BigDecimal nuTpregistro) {
		this.nuTpregistro = nuTpregistro;
	}

	public BigDecimal getNuTrabregula() {
		return this.nuTrabregula;
	}

	public void setNuTrabregula(BigDecimal nuTrabregula) {
		this.nuTrabregula = nuTrabregula;
	}

	public BigDecimal getNuTrabrevisados() {
		return this.nuTrabrevisados;
	}

	public void setNuTrabrevisados(BigDecimal nuTrabrevisados) {
		this.nuTrabrevisados = nuTrabrevisados;
	}

	public String getTxRpu() {
		return this.txRpu;
	}

	public void setTxRpu(String txRpu) {
		this.txRpu = txRpu;
	}

	public CgtGestionsinadi getCgtGestionsinadi() {
		return this.cgtGestionsinadi;
	}

	public void setCgtGestionsinadi(CgtGestionsinadi cgtGestionsinadi) {
		this.cgtGestionsinadi = cgtGestionsinadi;
	}
	
	public FdtPatron getFdtPatron() {
		return this.fdtPatron;
	}

	public void setFdtPatron(FdtPatron fdtPatron) {
		this.fdtPatron = fdtPatron;
	}
	
	public List<CgtGestionpago> getCgtGestionpagos() {
		return this.cgtGestionpagos;
	}

	public void setCgtGestionpagos(List<CgtGestionpago> cgtGestionpagos) {
		this.cgtGestionpagos = cgtGestionpagos;
	}
	
}