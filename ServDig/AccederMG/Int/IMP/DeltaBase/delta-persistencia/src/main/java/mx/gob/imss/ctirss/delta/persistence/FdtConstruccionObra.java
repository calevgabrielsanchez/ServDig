package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_CONSTRUCCION_OBRAS database table.
 * 
 */
@Entity
@Table(name="FDT_CONSTRUCCION_OBRAS")
public class FdtConstruccionObra implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtConstruccionObraPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_A")
	private Date fhPeriodoA;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_DE")
	private Date fhPeriodoDe;

	@Column(name="IND_DOMICILIADO", precision=22)
	private BigDecimal indDomiciliado;

	@Column(name="NU_OBRA_SATIC", precision=63)
	private double nuObraSatic;

	@Column(name="NU_TRABAJADORES", precision=10)
	private BigDecimal nuTrabajadores;

	@Column(name="TOTAL_REMUNERACION", precision=16, scale=2)
	private BigDecimal totalRemuneracion;

	@Column(name="TX_CALLE", length=150)
	private String txCalle;

	@Column(name="TX_CLASE_OBRA", nullable=false, length=20)
	private String txClaseObra;

	@Column(name="TX_COLONIA", length=50)
	private String txColonia;

	@Column(name="TX_ENTIDAD", length=50)
	private String txEntidad;

	@Column(name="TX_INCIDENCIA", length=15)
	private String txIncidencia;

	@Column(name="TX_LOTE", length=6)
	private String txLote;

	@Column(name="TX_MANZANA", length=6)
	private String txManzana;

	@Column(name="TX_MUNICIPIO", length=50)
	private String txMunicipio;

	@Column(name="TX_NUMERO", length=10)
	private String txNumero;

	//bi-directional many-to-one association to FdtTipoObra
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_OBRA", nullable=false)
	private FdtTipoObra fdtTipoObra;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

	//bi-directional many-to-one association to FdtConstruccionObrasDom
	@OneToMany(mappedBy="fdtConstruccionObra")
	private List<FdtConstruccionObrasDom> fdtConstruccionObrasDoms;

	//bi-directional many-to-one association to FdtConstruccionSubcontratist
	@OneToMany(mappedBy="fdtConstruccionObra")
	private List<FdtConstruccionSubcontratist> fdtConstruccionSubcontratists;

    public FdtConstruccionObra() {
    }

	public FdtConstruccionObraPK getId() {
		return this.id;
	}

	public void setId(FdtConstruccionObraPK id) {
		this.id = id;
	}
	
	public Date getFhPeriodoA() {
		return this.fhPeriodoA;
	}

	public void setFhPeriodoA(Date fhPeriodoA) {
		this.fhPeriodoA = fhPeriodoA;
	}

	public Date getFhPeriodoDe() {
		return this.fhPeriodoDe;
	}

	public void setFhPeriodoDe(Date fhPeriodoDe) {
		this.fhPeriodoDe = fhPeriodoDe;
	}

	public BigDecimal getIndDomiciliado() {
		return this.indDomiciliado;
	}

	public void setIndDomiciliado(BigDecimal indDomiciliado) {
		this.indDomiciliado = indDomiciliado;
	}

	public double getNuObraSatic() {
		return this.nuObraSatic;
	}

	public void setNuObraSatic(double nuObraSatic) {
		this.nuObraSatic = nuObraSatic;
	}

	public BigDecimal getNuTrabajadores() {
		return this.nuTrabajadores;
	}

	public void setNuTrabajadores(BigDecimal nuTrabajadores) {
		this.nuTrabajadores = nuTrabajadores;
	}

	public BigDecimal getTotalRemuneracion() {
		return this.totalRemuneracion;
	}

	public void setTotalRemuneracion(BigDecimal totalRemuneracion) {
		this.totalRemuneracion = totalRemuneracion;
	}

	public String getTxCalle() {
		return this.txCalle;
	}

	public void setTxCalle(String txCalle) {
		this.txCalle = txCalle;
	}

	public String getTxClaseObra() {
		return this.txClaseObra;
	}

	public void setTxClaseObra(String txClaseObra) {
		this.txClaseObra = txClaseObra;
	}

	public String getTxColonia() {
		return this.txColonia;
	}

	public void setTxColonia(String txColonia) {
		this.txColonia = txColonia;
	}

	public String getTxEntidad() {
		return this.txEntidad;
	}

	public void setTxEntidad(String txEntidad) {
		this.txEntidad = txEntidad;
	}

	public String getTxIncidencia() {
		return this.txIncidencia;
	}

	public void setTxIncidencia(String txIncidencia) {
		this.txIncidencia = txIncidencia;
	}

	public String getTxLote() {
		return this.txLote;
	}

	public void setTxLote(String txLote) {
		this.txLote = txLote;
	}

	public String getTxManzana() {
		return this.txManzana;
	}

	public void setTxManzana(String txManzana) {
		this.txManzana = txManzana;
	}

	public String getTxMunicipio() {
		return this.txMunicipio;
	}

	public void setTxMunicipio(String txMunicipio) {
		this.txMunicipio = txMunicipio;
	}

	public String getTxNumero() {
		return this.txNumero;
	}

	public void setTxNumero(String txNumero) {
		this.txNumero = txNumero;
	}

	public FdtTipoObra getFdtTipoObra() {
		return this.fdtTipoObra;
	}

	public void setFdtTipoObra(FdtTipoObra fdtTipoObra) {
		this.fdtTipoObra = fdtTipoObra;
	}
	
	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
	public List<FdtConstruccionObrasDom> getFdtConstruccionObrasDoms() {
		return this.fdtConstruccionObrasDoms;
	}

	public void setFdtConstruccionObrasDoms(List<FdtConstruccionObrasDom> fdtConstruccionObrasDoms) {
		this.fdtConstruccionObrasDoms = fdtConstruccionObrasDoms;
	}
	
	public List<FdtConstruccionSubcontratist> getFdtConstruccionSubcontratists() {
		return this.fdtConstruccionSubcontratists;
	}

	public void setFdtConstruccionSubcontratists(List<FdtConstruccionSubcontratist> fdtConstruccionSubcontratists) {
		this.fdtConstruccionSubcontratists = fdtConstruccionSubcontratists;
	}
	
}