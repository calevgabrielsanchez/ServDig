package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DIT_DATOS_CLEM database table.
 * 
 */
@Entity
@Table(name="DIT_DATOS_CLEM")
public class DitDatosClem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_DATOS_CLEM_GENERATOR", sequenceName = "SEQ_DITDATOSCLEM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_DATOS_CLEM_GENERATOR")
	@Column(name="CVE_ID_CLEM")
	private long cveIdClem;

	@Column(name="DES_SUPLENTE")
	private String desSuplente;//cveIdSuplente;

	@Column(name="DES_TITULAR")
	private String desTitular;//cveIdTitular;

	@Column(name="DES_LUGAR_FECHA_EXP")
	private String desLugarFechaExp;

	@Column(name="DES_MOTIVOS")
	private String desMotivos;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name="NUM_FOLIO_RESOLUCION")
	private String numFolioResolucion;

    @Lob()
	@Column(name="REF_DOCUMENTO")
	private byte[] refDocumento;
    
    @Column(name="NUM_BND_DELEGACIONAL")
	private BigDecimal numBndDelegacional;

	//bi-directional many-to-one association to DitAnalisisCe
    @ManyToOne
	@JoinColumn(name="CVE_ID_ANALISIS")
	private DitAnalisisCe ditAnalisisCe;

  //bi-directional many-to-one association to DitArticulo
	@OneToMany(mappedBy="ditDatosClem")
	private Set<DitArticulo> ditArticulos;
	
	//bi-directional many-to-one association to DicSubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;
    
	@Column(name="RFC", length=50)
	private String rfc;

	@Column(name="FOLIO", length=100)
	private String folio;

	@Column(name="ACUSE", length=250)
	private String acuse;

	@Column(name="FIRMA", length=1000)
	private String firma;

	@Column(name="CADORI", length=1000)
	private String cadori;

	@Column(name="PUESTO", length=100)
	private String puesto;

    
    public DitDatosClem() {
    }

	public long getCveIdClem() {
		return this.cveIdClem;
	}

	public void setCveIdClem(long cveIdClem) {
		this.cveIdClem = cveIdClem;
	}

	public String getDesSuplente() {
		return this.desSuplente;
	}

	public void setDesSuplente(String desSuplente) {
		this.desSuplente = desSuplente;
	}

	public String getDesTitular() {
		return this.desTitular;
	}

	public void setDesTitular(String desTitular) {
		this.desTitular = desTitular;
	}

	public String getDesLugarFechaExp() {
		return this.desLugarFechaExp;
	}

	public void setDesLugarFechaExp(String desLugarFechaExp) {
		this.desLugarFechaExp = desLugarFechaExp;
	}

	public String getDesMotivos() {
		return this.desMotivos;
	}

	public void setDesMotivos(String desMotivos) {
		this.desMotivos = desMotivos;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	
	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	
	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public String getNumFolioResolucion() {
		return this.numFolioResolucion;
	}

	public void setNumFolioResolucion(String numFolioResolucion) {
		this.numFolioResolucion = numFolioResolucion;
	}

	public byte[] getRefDocumento() {
		return this.refDocumento;
	}

	public void setRefDocumento(byte[] refDocumento) {
		this.refDocumento = refDocumento != null ? refDocumento.clone() : null;
	}

	public DitAnalisisCe getDitAnalisisCe() {
		return this.ditAnalisisCe;
	}

	public void setDitAnalisisCe(DitAnalisisCe ditAnalisisCe) {
		this.ditAnalisisCe = ditAnalisisCe;
	}

	public BigDecimal getNumBndDelegacional() {
		return numBndDelegacional;
	}

	public void setNumBndDelegacional(BigDecimal numBndDelegacional) {
		this.numBndDelegacional = numBndDelegacional;
	}

	public Set<DitArticulo> getDitArticulos() {
		return ditArticulos;
	}

	public void setDitArticulos(Set<DitArticulo> ditArticulos) {
		this.ditArticulos = ditArticulos;
	}

	public DicSubdelegacion getDicSubdelegacion() {
		return dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getAcuse() {
		return acuse;
	}

	public void setAcuse(String acuse) {
		this.acuse = acuse;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getCadori() {
		return cadori;
	}

	public void setCadori(String cadori) {
		this.cadori = cadori;
	}
	
	
}