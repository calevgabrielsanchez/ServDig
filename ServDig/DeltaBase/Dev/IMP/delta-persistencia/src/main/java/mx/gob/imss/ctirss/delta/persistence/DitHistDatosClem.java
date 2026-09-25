package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_HIST_DATOS_CLEM database table.
 * 
 */
@Entity
@Table(name = "DIT_HIST_DATOS_CLEM")
public class DitHistDatosClem implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -1106001555648059810L;

	@Id
	@SequenceGenerator(name = "DIT_HIST_DATOS_CLEM_GENERATOR", sequenceName = "SEQ_DITHISTDATOSCLEM", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_HIST_DATOS_CLEM_GENERATOR")
	@Column(name = "CVE_ID_HIST_DATOS_CLEM")
	private long cveIdHistDatosClem;

	// bi-directional many-to-one association to DitDatosClem
	@ManyToOne
	@JoinColumn(name = "CVE_ID_CLEM")
	private DitDatosClem ditDatosClem;

	// bi-directional many-to-one association to DitAnalisisCe
	@ManyToOne
	@JoinColumn(name = "CVE_ID_ANALISIS")
	private DitAnalisisCe ditAnalisisCe;

	@Column(name = "NUM_FOLIO_RESOLUCION")
	private String numFolioResolucion;

	// bi-directional many-to-one association to DicTipoCausaAnalisis
	@ManyToOne
	@JoinColumn(name = "CVE_ID_TIPO_CAUSA")
	private DicTipoCausaAnalisis dicTipoCausaAnalisis;

	@Column(name = "DES_MOTIVOS")
	private String desMotivos;

	@Column(name = "DES_LUGAR_FECHA_EXP")
	private String desLugarFechaExp;

	@Column(name = "STP_HIST_DATOS_CLEM")
	private Timestamp stpHistDatosClem;

	@Lob()
	@Column(name = "REF_DOCUMENTO")
	private byte[] refDocumento;

	// bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne
	@JoinColumn(name = "CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;

	@Column(name = "NUM_BND_DELEGACIONAL")
	private BigDecimal numBndDelegacional;

	@Column(name = "DES_TITULAR")
	private String desTitular;// cveIdTitular;

	@Column(name = "DES_SUPLENTE")
	private String desSuplente;// cveIdSuplente;

	@Column(name="PUESTO", length=100)
	private String puesto;

	public long getCveIdHistDatosClem() {
		return cveIdHistDatosClem;
	}

	public void setCveIdHistDatosClem(long cveIdHistDatosClem) {
		this.cveIdHistDatosClem = cveIdHistDatosClem;
	}

	public DitDatosClem getDitDatosClem() {
		return ditDatosClem;
	}

	public void setDitDatosClem(DitDatosClem ditDatosClem) {
		this.ditDatosClem = ditDatosClem;
	}

	public DitAnalisisCe getDitAnalisisCe() {
		return ditAnalisisCe;
	}

	public void setDitAnalisisCe(DitAnalisisCe ditAnalisisCe) {
		this.ditAnalisisCe = ditAnalisisCe;
	}

	public String getNumFolioResolucion() {
		return numFolioResolucion;
	}

	public void setNumFolioResolucion(String numFolioResolucion) {
		this.numFolioResolucion = numFolioResolucion;
	}

	public DicTipoCausaAnalisis getDicTipoCausaAnalisis() {
		return dicTipoCausaAnalisis;
	}

	public void setDicTipoCausaAnalisis(
			DicTipoCausaAnalisis dicTipoCausaAnalisis) {
		this.dicTipoCausaAnalisis = dicTipoCausaAnalisis;
	}

	public String getDesMotivos() {
		return desMotivos;
	}

	public void setDesMotivos(String desMotivos) {
		this.desMotivos = desMotivos;
	}

	public String getDesLugarFechaExp() {
		return desLugarFechaExp;
	}

	public void setDesLugarFechaExp(String desLugarFechaExp) {
		this.desLugarFechaExp = desLugarFechaExp;
	}

	public Timestamp getStpHistDatosClem() {
		return stpHistDatosClem;
	}

	public void setStpHistDatosClem(Timestamp stpHistDatosClem) {
		this.stpHistDatosClem = stpHistDatosClem;
	}

	public byte[] getRefDocumento() {
		return refDocumento;
	}

	public void setRefDocumento(byte[] refDocumento) {
		this.refDocumento = refDocumento != null ? refDocumento.clone() : null;
	}

	public DicSubdelegacion getDicSubdelegacion() {
		return dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}

	public BigDecimal getNumBndDelegacional() {
		return numBndDelegacional;
	}

	public void setNumBndDelegacional(BigDecimal numBndDelegacional) {
		this.numBndDelegacional = numBndDelegacional;
	}

	public String getDesTitular() {
		return desTitular;
	}

	public void setDesTitular(String desTitular) {
		this.desTitular = desTitular;
	}

	public String getDesSuplente() {
		return desSuplente;
	}

	public void setDesSuplente(String desSuplente) {
		this.desSuplente = desSuplente;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	
}