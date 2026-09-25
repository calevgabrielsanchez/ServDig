package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_SOLICITUD database table.
 * 
 */
@Entity
@NamedQueries({
	
    @NamedQuery(name="DitSolicitud.getSolicitudesVencidas",
                query="SELECT s FROM DitSolicitud s WHERE s.fecSolicitud <= :hoyMenosDosDias AND s.dicEstadoSolicitud.cveIdEstadoSolicitud = 2"),

    @NamedQuery(name="DitSolicitud.getCveIdEstadoSolicitud", query="SELECT s.dicEstadoSolicitud.cveIdEstadoSolicitud FROM DitSolicitud s")
})
@Table(name="DIT_SOLICITUD")
public class DitSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="DIT_SOLICITUD_CVEIDSOLICITUD_GENERATOR", sequenceName="SEQ_DITSOLICITUD", allocationSize = 1)
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_SOLICITUD_CVEIDSOLICITUD_GENERATOR")
	@Column(name="CVE_ID_SOLICITUD")
	private long cveIdSolicitud;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_CITA")
	private Date fecCita;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_SOLICITUD", nullable=false)
	private Date fecSolicitud;

	@Column(name="REF_FOLIO", nullable=false, length=255)
	private String refFolio;
	
	@Column(name="CVE_ID_USUARIO", length=20)
	private String cveIdUsuario;

	@Column(name="REF_OBSERVACION", length=2056)
	private String refObservacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FEC_CONCLUSION")
    private Date fecConclusion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FEC_PRESENTACION")
    private Date fecPresentacion;

	//bi-directional many-to-one association to DicTipoSolicitud
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_SOLICITUD")
	private DicTipoSolicitud dicTipoSolicitud;

	//bi-directional many-to-one association to DicEstadoSolicitud
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ESTADO_SOLICITUD")
	private DicEstadoSolicitud dicEstadoSolicitud;

	//bi-directional many-to-one association to DicRazonCancelacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_RAZON_CANCELACION")
	private DicRazonCancelacion dicRazonCancelacion;

	//bi-directional many-to-one association to DitUmfTurno
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ID_TURNO", referencedColumnName="CVE_ID_TURNO"),
		@JoinColumn(name="CVE_ID_UMF", referencedColumnName="CVE_ID_UMF")
		})
	private DitUmfTurno ditUmfTurno;

	//bi-directional many-to-one association to DitSolicitudSeguimiento
	@OneToMany(mappedBy="ditSolicitud")
	private List<DitSolicitudSeguimiento> ditSolicitudSeguimientos;
	
	/*
	//bi-directional many-to-one association to DitSolicitudSeguimiento
	@OneToMany(mappedBy="ditSolicitud")
	private List<DitBitacoraSegSolicitud> ditBitacoraSegSolicitudes;
	*/
	//bi-directional many-to-one association to DitTramite
	@OneToMany(mappedBy="ditSolicitud", fetch = FetchType.LAZY)
	private List<DitTramite> ditTramites;

	 //bi-directional many-to-one association to DitPersonaInteresadaSol
	@OneToMany(mappedBy="ditSolicitud", fetch = FetchType.LAZY)
	private List<DitPersonaInteresadaSol> ditPersonaInteresadaSols;
	
	//bi-directional many-to-one association to DitSolicitudDocumento
	@OneToOne(mappedBy="ditSolicitud", cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
	private DitSolicitudDocumento ditSolicitudDocumento;
	
	//bi-directional many-to-one association to DicTipoSolicitud
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;
		
	//bi-directional one-to-one association to DitReintentoRpc
	@OneToOne(mappedBy="ditSolicitud", fetch=FetchType.LAZY)
	private DitReintentoRPC ditReintentoRPC;
	
	@OneToMany(mappedBy="ditSolicitud" , fetch=FetchType.LAZY)
	private List<DitSolicitudFirmaDigital> ditSolicitudFirmaDigitals;
	
	// bi-directional many-to-one association to DicOrigenSolicitud
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_ORIGEN_SOLICITUD")
	private DicOrigenSolicitud dicOrigenSolicitud;
	
	/**
	//bi-directional one-to-one association to DitReintentoRpc
	@OneToMany(mappedBy="ditSolicitud", fetch = FetchType.LAZY)
	private List<DitCitaSolicitud> ditCitaSolicituds;
	**/
	
	public DitSolicitud() {
		
		/*
		 * Se pone aqu� el setteo del origen de la solicitud, para que cualquier
		 * solicitud que se este creando ya tenga el origen INTERNET(2)
		 */
		DicOrigenSolicitud dicOrigenSolicitud = new DicOrigenSolicitud();
		dicOrigenSolicitud.setCveIdOrigenSolicitud(2);
		
		this.dicOrigenSolicitud = dicOrigenSolicitud;
    }

	public long getCveIdSolicitud() {
		return this.cveIdSolicitud;
	}

	public void setCveIdSolicitud(long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	public Date getFecCita() {
		return this.fecCita;
	}

	public void setFecCita(Date fecCita) {
		this.fecCita = fecCita;
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

	public Date getFecSolicitud() {
		return this.fecSolicitud;
	}

	public void setFecSolicitud(Date fecSolicitud) {
		this.fecSolicitud = fecSolicitud;
	}

	public String getRefFolio() {
		return this.refFolio;
	}

	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}

	public String getRefObservacion() {
		return this.refObservacion;
	}

	public void setRefObservacion(String refObservacion) {
		this.refObservacion = refObservacion;
	}

	public DicTipoSolicitud getDicTipoSolicitud() {
		return this.dicTipoSolicitud;
	}

	public void setDicTipoSolicitud(DicTipoSolicitud dicTipoSolicitud) {
		this.dicTipoSolicitud = dicTipoSolicitud;
	}
	
	public DicEstadoSolicitud getDicEstadoSolicitud() {
		return this.dicEstadoSolicitud;
	}

	public void setDicEstadoSolicitud(DicEstadoSolicitud dicEstadoSolicitud) {
		this.dicEstadoSolicitud = dicEstadoSolicitud;
	}
	
	public DicRazonCancelacion getDicRazonCancelacion() {
		return this.dicRazonCancelacion;
	}

	public void setDicRazonCancelacion(DicRazonCancelacion dicRazonCancelacion) {
		this.dicRazonCancelacion = dicRazonCancelacion;
	}
	
	public DitUmfTurno getDitUmfTurno() {
		return this.ditUmfTurno;
	}

	public void setDitUmfTurno(DitUmfTurno ditUmfTurno) {
		this.ditUmfTurno = ditUmfTurno;
	}
	
	public List<DitSolicitudSeguimiento> getDitSolicitudSeguimientos() {
		return this.ditSolicitudSeguimientos;
	}

	public void setDitSolicitudSeguimientos(List<DitSolicitudSeguimiento> ditSolicitudSeguimientos) {
		this.ditSolicitudSeguimientos = ditSolicitudSeguimientos;
	}

    public List<DitTramite> getDitTramites() {
		return this.ditTramites;
	}

	public void setDitTramites(List<DitTramite> ditTramites) {
		this.ditTramites = ditTramites;
	}
	
	public List<DitPersonaInteresadaSol> getDitPersonaInteresadaSols() {
		return ditPersonaInteresadaSols;
	}

	public void setDitPersonaInteresadaSols(
			List<DitPersonaInteresadaSol> ditPersonaInteresadaSols) {
		this.ditPersonaInteresadaSols = ditPersonaInteresadaSols;
	}
	
	public DitSolicitudDocumento getDitSolicitudDocumento() {
		return this.ditSolicitudDocumento;
	}
	
	public void setDitSolicitudDocumento(DitSolicitudDocumento ditSolicitudDocumento) {
		this.ditSolicitudDocumento = ditSolicitudDocumento;
	}

	public DicSubdelegacion getDicSubdelegacion() {
		return dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}

	public DitReintentoRPC getDitReintentoRPC() {
		return ditReintentoRPC;
	}

	public void setDitReintentoRPC(DitReintentoRPC ditReintentoRpc) {
		this.ditReintentoRPC = ditReintentoRpc;
	}

	/**
	 * @return the ditBitacoraSegSolicitudes
	 */
	/*
	public List<DitBitacoraSegSolicitud> getDitBitacoraSegSolicitudes() {
		return ditBitacoraSegSolicitudes;
	}
	*/
	/**
	 * @param ditBitacoraSegSolicitudes the ditBitacoraSegSolicitudes to set
	 */
	/*
	public void setDitBitacoraSegSolicitudes(
			List<DitBitacoraSegSolicitud> ditBitacoraSegSolicitudes) {
		this.ditBitacoraSegSolicitudes = ditBitacoraSegSolicitudes;
	}
	*/
    public Date getFecConclusion() {
        return fecConclusion;
    }

    public void setFecConclusion(Date fecConclusion) {
        this.fecConclusion = fecConclusion;
    }


    public Date getFecPresentacion() {
        return fecPresentacion;
    }

    public void setFecPresentacion(Date fecPresentacion) {
        this.fecPresentacion = fecPresentacion;
    }

	public List<DitSolicitudFirmaDigital> getDitSolicitudFirmaDigitals() {
		return ditSolicitudFirmaDigitals;
	}

	public void setDitSolicitudFirmaDigitals(
			List<DitSolicitudFirmaDigital> ditSolicitudFirmaDigitals) {
		this.ditSolicitudFirmaDigitals = ditSolicitudFirmaDigitals;
	}

	public DicOrigenSolicitud getDicOrigenSolicitud() {
		return dicOrigenSolicitud;
	}

	public void setDicOrigenSolicitud(DicOrigenSolicitud dicOrigenSolicitud) {
		this.dicOrigenSolicitud = dicOrigenSolicitud;
	}
	
	public String getCveIdUsuario() {
		return cveIdUsuario;
	}

	public void setCveIdUsuario(String cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}

	
	
}
