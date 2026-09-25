package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the FDT_CARTA_PRESENTACION database table.
 * 
 */
@Entity
@Table(name="FDT_CARTA_PRESENTACION")
public class FdtCartaPresentacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_DICTAMEN", nullable=false, precision=22)
	private long idDictamen;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_CAPTURA")
	private Date fhCaptura;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_EXPEDICION")
	private Date fhExpedicion;

	@Column(name="ID_AVISO", nullable=false, precision=22)
	private BigDecimal idAviso;

	@Column(name="IND_DOMICILIADO", precision=22)
	private BigDecimal indDomiciliado;

	@Column(name="TX_ACTIVIDAD", length=255)
	private String txActividad;

	@Column(name="TX_CALLE", length=50)
	private String txCalle;

	@Column(name="TX_COLONIA", length=50)
	private String txColonia;

	@Column(name="TX_CORREOE", length=50)
	private String txCorreoe;

	@Column(name="TX_CP", length=50)
	private String txCp;

	@Column(name="TX_DELEG", length=50)
	private String txDeleg;

	@Column(name="TX_ENTFED", length=50)
	private String txEntfed;

	@Column(name="TX_LADA", length=50)
	private String txLada;

	@Column(name="TX_LUGAR_ELABORA", length=250)
	private String txLugarElabora;

	@Column(name="TX_NO_ESCRITURA", length=20)
	private String txNoEscritura;

	@Column(name="TX_NO_EXTERIOR", length=50)
	private String txNoExterior;

	@Column(name="TX_NO_INTERIOR", length=50)
	private String txNoInterior;

	@Column(name="TX_NO_NOTARIA", length=50)
	private String txNoNotaria;

	@Column(name="TX_RAZONSOCIAL", length=100)
	private String txRazonsocial;

	@Column(name="TX_REGPATRON", length=50)
	private String txRegpatron;

	@Column(name="TX_REP_LEGAL", length=50)
	private String txRepLegal;

	@Column(name="TX_RFC", length=13)
	private String txRfc;

	@Column(name="TX_TELEFONO", length=50)
	private String txTelefono;

	@Column(name="TX_TIPO_DICTAMEN", length=50)
	private String txTipoDictamen;

	//bi-directional one-to-one association to FdtPeriodo
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
	private FdtPeriodo fdtPeriodo;

	//bi-directional many-to-one association to FdtCartaPresentDom
	@OneToMany(mappedBy="fdtCartaPresentacion")
	private List<FdtCartaPresentDom> fdtCartaPresentDoms;

    public FdtCartaPresentacion() {
    }

	public long getIdDictamen() {
		return this.idDictamen;
	}

	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}

	public Date getFhCaptura() {
		return this.fhCaptura;
	}

	public void setFhCaptura(Date fhCaptura) {
		this.fhCaptura = fhCaptura;
	}

	public Date getFhExpedicion() {
		return this.fhExpedicion;
	}

	public void setFhExpedicion(Date fhExpedicion) {
		this.fhExpedicion = fhExpedicion;
	}

	public BigDecimal getIdAviso() {
		return this.idAviso;
	}

	public void setIdAviso(BigDecimal idAviso) {
		this.idAviso = idAviso;
	}

	public BigDecimal getIndDomiciliado() {
		return this.indDomiciliado;
	}

	public void setIndDomiciliado(BigDecimal indDomiciliado) {
		this.indDomiciliado = indDomiciliado;
	}

	public String getTxActividad() {
		return this.txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
	}

	public String getTxCalle() {
		return this.txCalle;
	}

	public void setTxCalle(String txCalle) {
		this.txCalle = txCalle;
	}

	public String getTxColonia() {
		return this.txColonia;
	}

	public void setTxColonia(String txColonia) {
		this.txColonia = txColonia;
	}

	public String getTxCorreoe() {
		return this.txCorreoe;
	}

	public void setTxCorreoe(String txCorreoe) {
		this.txCorreoe = txCorreoe;
	}

	public String getTxCp() {
		return this.txCp;
	}

	public void setTxCp(String txCp) {
		this.txCp = txCp;
	}

	public String getTxDeleg() {
		return this.txDeleg;
	}

	public void setTxDeleg(String txDeleg) {
		this.txDeleg = txDeleg;
	}

	public String getTxEntfed() {
		return this.txEntfed;
	}

	public void setTxEntfed(String txEntfed) {
		this.txEntfed = txEntfed;
	}

	public String getTxLada() {
		return this.txLada;
	}

	public void setTxLada(String txLada) {
		this.txLada = txLada;
	}

	public String getTxLugarElabora() {
		return this.txLugarElabora;
	}

	public void setTxLugarElabora(String txLugarElabora) {
		this.txLugarElabora = txLugarElabora;
	}

	public String getTxNoEscritura() {
		return this.txNoEscritura;
	}

	public void setTxNoEscritura(String txNoEscritura) {
		this.txNoEscritura = txNoEscritura;
	}

	public String getTxNoExterior() {
		return this.txNoExterior;
	}

	public void setTxNoExterior(String txNoExterior) {
		this.txNoExterior = txNoExterior;
	}

	public String getTxNoInterior() {
		return this.txNoInterior;
	}

	public void setTxNoInterior(String txNoInterior) {
		this.txNoInterior = txNoInterior;
	}

	public String getTxNoNotaria() {
		return this.txNoNotaria;
	}

	public void setTxNoNotaria(String txNoNotaria) {
		this.txNoNotaria = txNoNotaria;
	}

	public String getTxRazonsocial() {
		return this.txRazonsocial;
	}

	public void setTxRazonsocial(String txRazonsocial) {
		this.txRazonsocial = txRazonsocial;
	}

	public String getTxRegpatron() {
		return this.txRegpatron;
	}

	public void setTxRegpatron(String txRegpatron) {
		this.txRegpatron = txRegpatron;
	}

	public String getTxRepLegal() {
		return this.txRepLegal;
	}

	public void setTxRepLegal(String txRepLegal) {
		this.txRepLegal = txRepLegal;
	}

	public String getTxRfc() {
		return this.txRfc;
	}

	public void setTxRfc(String txRfc) {
		this.txRfc = txRfc;
	}

	public String getTxTelefono() {
		return this.txTelefono;
	}

	public void setTxTelefono(String txTelefono) {
		this.txTelefono = txTelefono;
	}

	public String getTxTipoDictamen() {
		return this.txTipoDictamen;
	}

	public void setTxTipoDictamen(String txTipoDictamen) {
		this.txTipoDictamen = txTipoDictamen;
	}

	public FdtPeriodo getFdtPeriodo() {
		return this.fdtPeriodo;
	}

	public void setFdtPeriodo(FdtPeriodo fdtPeriodo) {
		this.fdtPeriodo = fdtPeriodo;
	}
	
	public List<FdtCartaPresentDom> getFdtCartaPresentDoms() {
		return this.fdtCartaPresentDoms;
	}

	public void setFdtCartaPresentDoms(List<FdtCartaPresentDom> fdtCartaPresentDoms) {
		this.fdtCartaPresentDoms = fdtCartaPresentDoms;
	}
	
}