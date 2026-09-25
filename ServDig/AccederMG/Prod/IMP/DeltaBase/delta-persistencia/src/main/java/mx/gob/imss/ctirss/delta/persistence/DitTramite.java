package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;




@Entity
@Table(name="DIT_TRAMITE")
public class DitTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_DITTRAMITE", sequenceName = "SEQ_DITTRAMITE")
    @GeneratedValue(generator = "SEQ_DITTRAMITE")
	@Column(name="CVE_ID_TRAMITE")
	private Long cveIdTramite;
	

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_TRAMITE")
	private Date fecTramite;

	@Column(name="IND_RESULTADO")
	private BigDecimal indResultado;

	@Column(name="REF_OBSERVACION")
	private String refObservacion;

	//bi-directional one-to-one association to DitDetalleTramite
	@OneToOne(mappedBy="ditTramite")
	private DitDetalleTramite ditDetalleTramite;

	//bi-directional many-to-many association to DitDocumentoProbatorio
    @ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="DIT_DOCUMENTACION_TRAMITE"
		, joinColumns={
			@JoinColumn(name="CVE_ID_TRAMITE")
			}
		, inverseJoinColumns={
			@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
			}
		)
	private List<DitDocumentoProbatorio> ditDocumentoProbatorios;
	
	//bi-directional many-to-one association to DicEstadoTramite
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ESTADO_TRAMITE")
	private DicEstadoTramite dicEstadoTramite;

	//bi-directional many-to-one association to DicRazonResultado
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_RAZON_RESULTADO")
	private DicRazonResultado dicRazonResultado;

	//bi-directional many-to-one association to DicTipoTramite
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_TRAMITE")
	private DicTipoTramite dicTipoTramite;
  
  //bi-directional many-to-one association to DitSolicitud
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SOLICITUD")
	private DitSolicitud ditSolicitud;
    
    
    @OneToMany(mappedBy="ditTramite", fetch=FetchType.LAZY)
    private List<DitTramitePersonaFisica> ditTramitePersonaFisica;
    
    /*
    @OneToMany(mappedBy="ditTramite", fetch=FetchType.LAZY)
    private List<DitBitacoraSegTramite> ditBitacoraSegTramite;
    */
    
    @OneToOne(mappedBy="ditTramite", fetch=FetchType.LAZY)
    private DitTramitePersonaMoral ditTramitePersonaMoral;
     
    @OneToMany(mappedBy="ditTramite", fetch=FetchType.LAZY)
    private List<DitTramitePatSujObligado> ditTramitePatSujObligados;
    
    
    /*
    
    @OneToMany(mappedBy="ditTramite", fetch=FetchType.LAZY)
    private List<DitProrroga> ditProrroga;
    
    @OneToMany(mappedBy="ditTramite", fetch=FetchType.LAZY)
    private List<DitRegistroDerechohabiente> ditRegistroDerechohabiente;
    
    @OneToMany(mappedBy="ditTramite", fetch=FetchType.LAZY)
    private List<DitCorreccionDatoDerechohab> ditCorreccionDatoDerechohab;
    
    @OneToMany(mappedBy="ditTramite", fetch=FetchType.LAZY)
    private List<DitCircunscripcionForanea> ditCircunscripcionForanea;
   
    */
    
    @OneToMany(mappedBy="ditTramite", fetch=FetchType.LAZY)
    private List<DitDocumentacionTramite> ditDocumentacionTramites;
    
    
    
    
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_PRESENTACION")
	private Date fecPresentacion;
    
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_EFECTO")
	private Date fecEfecto;
    
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_CONCLUSION")
	private Date fecConclusion;
    
    @Column(name="IND_RATIFICADO")
    private Boolean indRatificado;
	
    
  //bi-directional many-to-one association to DitOrdenPago
//  	@OneToMany(mappedBy="ditTramite")
//  	private List<DitOrdenPago> ditOrdenPagos;

    
    public DitTramite() {
		
	}
    
    public DitTramite(Long cveIdTramite) {
    	super();
    	this.cveIdTramite = cveIdTramite;
    }
	
	public DitTramite(Long cveIdTramite, DitDetalleTramite ditDetalleTramite,
			DicTipoTramite dicTipoTramite,
			Date fecPresentacion, Date fecEfecto, Date fecConclusion) {
		super();
		this.cveIdTramite = cveIdTramite;
		this.ditDetalleTramite = ditDetalleTramite;
		this.dicTipoTramite = dicTipoTramite;
		this.fecPresentacion = fecPresentacion;
		this.fecEfecto = fecEfecto;
		this.fecConclusion = fecConclusion;
	}

	public List<DitDocumentacionTramite> getDitDocumentacionTramites() {
		return ditDocumentacionTramites;
	}

	public void setDitDocumentacionTramites(
			List<DitDocumentacionTramite> ditDocumentacionTramites) {
		this.ditDocumentacionTramites = ditDocumentacionTramites;
	}

	
	public List<DitTramitePatSujObligado> getDitTramitePatSujObligados() {
		return ditTramitePatSujObligados;
	}

	public void setDitTramitePatSujObligados(
			List<DitTramitePatSujObligado> ditTramitePatSujObligados) {
		this.ditTramitePatSujObligados = ditTramitePatSujObligados;
	}
	
	public DitDetalleTramite getDitDetalleTramite() {
		return ditDetalleTramite;
	}

	public void setDitDetalleTramite(DitDetalleTramite ditDetalleTramite) {
		this.ditDetalleTramite = ditDetalleTramite;
	}

	public Long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
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

	public Date getFecTramite() {
		return this.fecTramite;
	}

	public void setFecTramite(Date fecTramite) {
		this.fecTramite = fecTramite;
	}

	public BigDecimal getIndResultado() {
		return this.indResultado;
	}

	public void setIndResultado(BigDecimal indResultado) {
		this.indResultado = indResultado;
	}

	public String getRefObservacion() {
		return this.refObservacion;
	}

	public void setRefObservacion(String refObservacion) {
		this.refObservacion = refObservacion;
	}
	
	
	public DicEstadoTramite getDicEstadoTramite() {
		return this.dicEstadoTramite;
	}

	public void setDicEstadoTramite(DicEstadoTramite dicEstadoTramite) {
		this.dicEstadoTramite = dicEstadoTramite;
	}
	
	public DicRazonResultado getDicRazonResultado() {
		return this.dicRazonResultado;
	}

	public void setDicRazonResultado(DicRazonResultado dicRazonResultado) {
		this.dicRazonResultado = dicRazonResultado;
	}
	
	public DicTipoTramite getDicTipoTramite() {
		return this.dicTipoTramite;
	}

	public void setDicTipoTramite(DicTipoTramite dicTipoTramite) {
		this.dicTipoTramite = dicTipoTramite;
	}

	
	public DitSolicitud getDitSolicitud() {
		return this.ditSolicitud;
	}

	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}

	public List <DitTramitePersonaFisica> getDitTramitePersonaFisica() {
		return ditTramitePersonaFisica;
	}

	public void setDitTramitePersonaFisica(
			List<DitTramitePersonaFisica> ditTramitePersonaFisica) {
		this.ditTramitePersonaFisica = ditTramitePersonaFisica;
	}

	public DitTramitePersonaMoral getDitTramitePersonaMoral() {
		return ditTramitePersonaMoral;
	}

	public void setDitTramitePersonaMoral(
			DitTramitePersonaMoral ditTramitePersonaMoral) {
		this.ditTramitePersonaMoral = ditTramitePersonaMoral;
	}

	public void setDitDocumentoProbatorios(List<DitDocumentoProbatorio> ditDocumentoProbatorios) {
		this.ditDocumentoProbatorios = ditDocumentoProbatorios;
	}

	public List<DitDocumentoProbatorio> getDitDocumentoProbatorios() {
		return ditDocumentoProbatorios;
	}

	public Date getFecPresentacion() {
		return fecPresentacion;
	}

	public void setFecPresentacion(Date fecPresentacion) {
		this.fecPresentacion = fecPresentacion;
	}

	public Date getFecEfecto() {
		return fecEfecto;
	}

	public void setFecEfecto(Date fecEfecto) {
		this.fecEfecto = fecEfecto;
	}

	public Date getFecConclusion() {
		return fecConclusion;
	}

	public void setFecConclusion(Date fecConclusion) {
		this.fecConclusion = fecConclusion;
	}

	public Boolean getIndRatificado() {
		return indRatificado;
	}

	public void setIndRatificado(Boolean indRatificado) {
		this.indRatificado = indRatificado;
	}

	
	
	/**
	 * @return the ditBitacoraSegTramite
	 */
	/*
	public List<DitBitacoraSegTramite> getDitBitacoraSegTramite() {
		return ditBitacoraSegTramite;
	}
	*/
	/**
	 * @param ditBitacoraSegTramite the ditBitacoraSegTramite to set
	 */
	/*
	public void setDitBitacoraSegTramite(
			List<DitBitacoraSegTramite> ditBitacoraSegTramite) {
		this.ditBitacoraSegTramite = ditBitacoraSegTramite;
	}
	*/
}