package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDI_OFICIOS database table.
 * 
 */
@Entity
@Table(name="FDI_OFICIOS")
public class FdiOficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_OFICIO", nullable=false, precision=22)
	private long idOficio;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_DEBE_ENTREGA_REQUERIMIENTO")
	private Date fhDebeEntregaRequerimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENTREGA_OFICIO")
	private Date fhEntregaOficio;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_GENERACION", nullable=false)
	private Date fhGeneracion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_NOTIFICACION")
	private Date fhNotificacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PRESENTA_DOC_FALTANTE")
	private Date fhPresentaDocFaltante;

	@Column(name="ID_AVISO", nullable=false, precision=22)
	private BigDecimal idAviso;

	@Column(name="ID_TIPO_OFICIO", nullable=false, precision=22)
	private BigDecimal idTipoOficio;

	@Column(name="MULTA_PATRON", precision=10, scale=2)
	private BigDecimal multaPatron;

	@Column(name="NO_OFICIO", nullable=false, length=20)
	private String noOficio;

	@Column(name="NO_OFICIO_ANT", length=20)
	private String noOficioAnt;

	@Column(name="NU_REG_CPA", nullable=false, precision=22)
	private BigDecimal nuRegCpa;

	@Column(name="NU_TIPO_MODIFICACION", precision=22)
	private BigDecimal nuTipoModificacion;

	@Column(name="TX_CREDITO_FISCAL", length=25)
	private String txCreditoFiscal;

	@Column(name="TX_IRREGULARIDADES", length=250)
	private String txIrregularidades;

	@Column(name="TX_LUGAR_FECHA", length=150)
	private String txLugarFecha;

	@Column(name="TX_OBSERVACIONES", length=200)
	private String txObservaciones;

	@Column(name="TX_PRORROGA", length=1)
	private String txProrroga;

	@Column(name="TX_REFERENCIA", length=20)
	private String txReferencia;

	@Column(name="TX_STATUS_ACLARACION", length=40)
	private String txStatusAclaracion;

	@Column(name="TX_STATUS_OFICIO", length=30)
	private String txStatusOficio;

	//bi-directional many-to-one association to FdiDocumentacion
	@OneToMany(mappedBy="fdiOficio")
	private List<FdiDocumentacion> fdiDocumentacions;

    public FdiOficio() {
    }

	public long getIdOficio() {
		return this.idOficio;
	}

	public void setIdOficio(long idOficio) {
		this.idOficio = idOficio;
	}

	public Date getFhDebeEntregaRequerimiento() {
		return this.fhDebeEntregaRequerimiento;
	}

	public void setFhDebeEntregaRequerimiento(Date fhDebeEntregaRequerimiento) {
		this.fhDebeEntregaRequerimiento = fhDebeEntregaRequerimiento;
	}

	public Date getFhEntregaOficio() {
		return this.fhEntregaOficio;
	}

	public void setFhEntregaOficio(Date fhEntregaOficio) {
		this.fhEntregaOficio = fhEntregaOficio;
	}

	public Date getFhGeneracion() {
		return this.fhGeneracion;
	}

	public void setFhGeneracion(Date fhGeneracion) {
		this.fhGeneracion = fhGeneracion;
	}

	public Date getFhNotificacion() {
		return this.fhNotificacion;
	}

	public void setFhNotificacion(Date fhNotificacion) {
		this.fhNotificacion = fhNotificacion;
	}

	public Date getFhPresentaDocFaltante() {
		return this.fhPresentaDocFaltante;
	}

	public void setFhPresentaDocFaltante(Date fhPresentaDocFaltante) {
		this.fhPresentaDocFaltante = fhPresentaDocFaltante;
	}

	public BigDecimal getIdAviso() {
		return this.idAviso;
	}

	public void setIdAviso(BigDecimal idAviso) {
		this.idAviso = idAviso;
	}

	public BigDecimal getIdTipoOficio() {
		return this.idTipoOficio;
	}

	public void setIdTipoOficio(BigDecimal idTipoOficio) {
		this.idTipoOficio = idTipoOficio;
	}

	public BigDecimal getMultaPatron() {
		return this.multaPatron;
	}

	public void setMultaPatron(BigDecimal multaPatron) {
		this.multaPatron = multaPatron;
	}

	public String getNoOficio() {
		return this.noOficio;
	}

	public void setNoOficio(String noOficio) {
		this.noOficio = noOficio;
	}

	public String getNoOficioAnt() {
		return this.noOficioAnt;
	}

	public void setNoOficioAnt(String noOficioAnt) {
		this.noOficioAnt = noOficioAnt;
	}

	public BigDecimal getNuRegCpa() {
		return this.nuRegCpa;
	}

	public void setNuRegCpa(BigDecimal nuRegCpa) {
		this.nuRegCpa = nuRegCpa;
	}

	public BigDecimal getNuTipoModificacion() {
		return this.nuTipoModificacion;
	}

	public void setNuTipoModificacion(BigDecimal nuTipoModificacion) {
		this.nuTipoModificacion = nuTipoModificacion;
	}

	public String getTxCreditoFiscal() {
		return this.txCreditoFiscal;
	}

	public void setTxCreditoFiscal(String txCreditoFiscal) {
		this.txCreditoFiscal = txCreditoFiscal;
	}

	public String getTxIrregularidades() {
		return this.txIrregularidades;
	}

	public void setTxIrregularidades(String txIrregularidades) {
		this.txIrregularidades = txIrregularidades;
	}

	public String getTxLugarFecha() {
		return this.txLugarFecha;
	}

	public void setTxLugarFecha(String txLugarFecha) {
		this.txLugarFecha = txLugarFecha;
	}

	public String getTxObservaciones() {
		return this.txObservaciones;
	}

	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}

	public String getTxProrroga() {
		return this.txProrroga;
	}

	public void setTxProrroga(String txProrroga) {
		this.txProrroga = txProrroga;
	}

	public String getTxReferencia() {
		return this.txReferencia;
	}

	public void setTxReferencia(String txReferencia) {
		this.txReferencia = txReferencia;
	}

	public String getTxStatusAclaracion() {
		return this.txStatusAclaracion;
	}

	public void setTxStatusAclaracion(String txStatusAclaracion) {
		this.txStatusAclaracion = txStatusAclaracion;
	}

	public String getTxStatusOficio() {
		return this.txStatusOficio;
	}

	public void setTxStatusOficio(String txStatusOficio) {
		this.txStatusOficio = txStatusOficio;
	}

	public List<FdiDocumentacion> getFdiDocumentacions() {
		return this.fdiDocumentacions;
	}

	public void setFdiDocumentacions(List<FdiDocumentacion> fdiDocumentacions) {
		this.fdiDocumentacions = fdiDocumentacions;
	}
	
}