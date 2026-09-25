package mx.gob.imss.ctirss.correccion.promocion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CRT_PROMOCION database table.
 * 
 */
@MappedSuperclass
public class AbstractCrtPromocion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CVE_PROMOCION_GENERATOR", sequenceName="CRS_CVE_PROMOCION")
	@GeneratedValue(generator="CVE_PROMOCION_GENERATOR")
	@Column(name="CVE_PROMOCION")
	private Long cvePromocion;

	@Column(name="CVE_NROREGOBRA_SATIC")
	private BigDecimal cveNroregobraSatic;

	@Column(name="CVE_TIPOCORR")
	private Long cveTipocorr;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAEMISIONPRO")
	private Date fecFechaemisionpro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHANOTIF")
	private Date fecFechanotif;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAOFICIOPRO")
	private Date fecFechaoficiopro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREGULARIZA")
	private Date fecFecharegulariza;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAPAI")
	private Date fecFechapai;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHACANCELA")
	private Date fecFechaCancela;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_ATENCION")
	private Date fecFechaAtencion;
	
	@Column(name="NU_FOLIOPROMOCION")
	private String nuFoliopromocion;

	@Column(name="NU_OFICIOPRO")
	private String nuOficiopro;

	@Column(name="CVE_FK_SUBDELEGACION")
	private Long sdelegOrig;
	
	@Column(name="ID_CRITERIOSELECCION")
	private Long idCriterioSeleccion;

	@Column(name="CVE_DETECCION")
	private BigDecimal cveDeteccion;
	
	@Column(name="ID_MOTIVOCANCELACION")
	private BigDecimal idMotivoCancelacion;

	@Column(name="CVE_FK_PATRON")
	private Long cveFkPatron;
	
	@Column(name="CVE_SELECTOR")
	private Long cveSelector;	
	
	@Column(name="TX_OBSERVACIONES")
	private String txObservaciones;
	
	@Column(name="CVE_DOMGEO_PATRON")
	private BigDecimal cveDomGeoPatron;
	
	@Column(name="NU_VOLANTE_CANCELA")
	private String nuVolanteCancela;
	
	@Column(name="CVE_FUNCIONARIOAUTORIZA")
	private String cveFuncionarioAutoriza;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_AVISODICTAMEN")
	private Date fecAvisoDictamen;
	
	@Column(name="NUM_AVISODICTAMEN")
	private String numAvisoDictamen;

	@Temporal( TemporalType.DATE)
	//@Column(name="FEC_INICIALDICTAMEN")
	@Column(name="FEC_PERIODOINI")
	private Date fecInicialDictamen;
	
	@Temporal( TemporalType.DATE)
	//@Column(name="FEC_FINALDICTAMEN")
	@Column(name="FEC_PERIODOFIN")
	private Date fecFinalDictamen;
	
	@Column(name="CVE_ESTATUS")
	private Long cveEstatus;
	
	
	@Column(name="CVE_FK_PATRON_FISCA")
	private Long cveFkPatronFisica;
	
	@Column(name="CVE_FK_PATRON_OBRA")
	private Long cveFkPatronObra;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHADERISUBDELEG")
	private Date fecFechaDeriSubdelegacion;
	
	@Column(name="CVE_FK_SUBDELEG_DEST")
	private Long cveFkSubdelegacionDest;
	
	@Column(name="TX_REFDERIVACION")
	private String txRefDerivacion;
	
//	@Column(name="CVE_AUDITOR_ASIGNADO")
//	private Long cveAuditorAsignado;
	
	@Column(name="CVE_AUDITOR_ASIGNADO")
	private String cveAuditorAsignado;
	
	@Column(name="ID_REGULARIZA_OBRA")
	private Long idRegulaObra;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_COTIZ_RAZ")
	private Date fecCotizRaz;

	@Transient
	private Integer idTipo;
	
	@Transient
	private Integer idOrigen;
	
    public AbstractCrtPromocion() {
    }

	public AbstractCrtPromocion(Long cvePromocion, String nuFoliopromocion,
			String nuOficiopro, Date fecFechaemisionpro, Date fecFechanotif,
			Long cveFkPatron, String cveUsuario, BigDecimal cveDeteccion,String txObservaciones,Long cveSelector, Long idCriterioSeleccion,String cveAuditorAsignado) {
		this.cvePromocion = cvePromocion;
		this.nuFoliopromocion = nuFoliopromocion;
		this.nuOficiopro = nuOficiopro;
		this.fecFechaemisionpro = fecFechaemisionpro;
		this.fecFechanotif = fecFechanotif;
		this.cveFkPatron = cveFkPatron;
		this.cveUsuario = cveUsuario;
		this.cveDeteccion = cveDeteccion;
		this.txObservaciones = txObservaciones;
		this.cveSelector = cveSelector;
		this.idCriterioSeleccion = idCriterioSeleccion;
		this.cveAuditorAsignado=cveAuditorAsignado;
		
	}
	
	public AbstractCrtPromocion(Long cvePromocion, String nuFoliopromocion,
			String nuOficiopro, Date fecFechaemisionpro, Date fecFechanotif,
			Long cveFkPatron, String cveUsuario, BigDecimal cveDeteccion) {
		this.cvePromocion = cvePromocion;
		this.nuFoliopromocion = nuFoliopromocion;
		this.nuOficiopro = nuOficiopro;
		this.fecFechaemisionpro = fecFechaemisionpro;
		this.fecFechanotif = fecFechanotif;
		this.cveFkPatron = cveFkPatron;
		this.cveUsuario = cveUsuario;
		this.cveDeteccion = cveDeteccion;
		
	}
	
	/**
	 * Constructor para Auditores
	 * @author Enrique Duran Jimenez
	 * @since 25/05/2012
	 */
	public AbstractCrtPromocion(Long cvePromocion, String nuFoliopromocion,
			Long cveFkPatron, Date fecInicialDictamen, Date fecFinalDictamen,
			Date fecFechaoficiopro) {
		this.cvePromocion = cvePromocion;
		this.nuFoliopromocion = nuFoliopromocion;
		this.cveFkPatron = cveFkPatron;
		this.fecInicialDictamen = fecInicialDictamen;
		this.fecFinalDictamen = fecFinalDictamen;	
		this.fecFechaoficiopro = fecFechaoficiopro;
	}

	
	public Long getCveEstatus() {
		return cveEstatus;
	}

	public void setCveEstatus(Long cveEstatus) {
		this.cveEstatus = cveEstatus;
	}

	public String getCveFuncionarioAutoriza() {
		return cveFuncionarioAutoriza;
	}

	public void setCveFuncionarioAutoriza(String cveFuncionarioAutoriza) {
		this.cveFuncionarioAutoriza = cveFuncionarioAutoriza;
	}

	public Date getFecAvisoDictamen() {
		return fecAvisoDictamen;
	}

	public void setFecAvisoDictamen(Date fecAvisoDictamen) {
		this.fecAvisoDictamen = fecAvisoDictamen;
	}

	public String getNumAvisoDictamen() {
		return numAvisoDictamen;
	}

	public void setNumAvisoDictamen(String numAvisoDictamen) {
		this.numAvisoDictamen = numAvisoDictamen;
	}

	public Date getFecInicialDictamen() {
		return fecInicialDictamen;
	}

	public void setFecInicialDictamen(Date fecInicialDictamen) {
		this.fecInicialDictamen = fecInicialDictamen;
	}

	public Date getFecFinalDictamen() {
		return fecFinalDictamen;
	}

	public void setFecFinalDictamen(Date fecFinalDictamen) {
		this.fecFinalDictamen = fecFinalDictamen;
	}

	
	public Long getCvePromocion() {
		return this.cvePromocion;
	}

	public void setCvePromocion(Long cvePromocion) {
		this.cvePromocion = cvePromocion;
	}

	public BigDecimal getCveNroregobraSatic() {
		return this.cveNroregobraSatic;
	}

	public void setCveNroregobraSatic(BigDecimal cveNroregobraSatic) {
		this.cveNroregobraSatic = cveNroregobraSatic;
	}

	public Long getCveTipocorr() {
		return this.cveTipocorr;
	}

	public void setCveTipocorr(Long cveTipocorr) {
		this.cveTipocorr = cveTipocorr;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecFechaemisionpro() {
		return this.fecFechaemisionpro;
	}

	public void setFecFechaemisionpro(Date fecFechaemisionpro) {
		this.fecFechaemisionpro = fecFechaemisionpro;
	}

	public Date getFecFechanotif() {
		return this.fecFechanotif;
	}

	public void setFecFechanotif(Date fecFechanotif) {
		this.fecFechanotif = fecFechanotif;
	}

	public Date getFecFechaoficiopro() {
		return this.fecFechaoficiopro;
	}

	public void setFecFechaoficiopro(Date fecFechaoficiopro) {
		this.fecFechaoficiopro = fecFechaoficiopro;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public String getNuFoliopromocion() {
		return this.nuFoliopromocion;
	}

	public void setNuFoliopromocion(String nuFoliopromocion) {
		this.nuFoliopromocion = nuFoliopromocion;
	}

	public String getNuOficiopro() {
		return this.nuOficiopro;
	}

	public void setNuOficiopro(String nuOficiopro) {
		this.nuOficiopro = nuOficiopro;
	}
	
	public Long getSdelegOrig() {
		return this.sdelegOrig;
	}

	public void setSdelegOrig(Long sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

	public Long getIdCriterioSeleccion() {
		return idCriterioSeleccion;
	}

	public void setIdCriterioSeleccion(Long idCriterioSeleccion) {
		this.idCriterioSeleccion = idCriterioSeleccion;
	}

	public BigDecimal getCveDeteccion() {
		return cveDeteccion;
	}

	public void setCveDeteccion(BigDecimal cveDeteccion) {
		this.cveDeteccion = cveDeteccion;
	}

	public Date getFecFecharegulariza() {
		return fecFecharegulariza;
	}

	public void setFecFecharegulariza(Date fecFecharegulariza) {
		this.fecFecharegulariza = fecFecharegulariza;
	}

	public Date getFecFechapai() {
		return fecFechapai;
	}

	public void setFecFechapai(Date fecFechapai) {
		this.fecFechapai = fecFechapai;
	}

	public Date getFecFechaCancela() {
		return fecFechaCancela;
	}

	public void setFecFechaCancela(Date fecFechaCancela) {
		this.fecFechaCancela = fecFechaCancela;
	}

	public Date getFecFechaAtencion() {
		return fecFechaAtencion;
	}

	public void setFecFechaAtencion(Date fecFechaAtencion) {
		this.fecFechaAtencion = fecFechaAtencion;
	}

	public BigDecimal getIdMotivoCancelacion() {
		return idMotivoCancelacion;
	}

	public void setIdMotivoCancelacion(BigDecimal idMotivoCancelacion) {
		this.idMotivoCancelacion = idMotivoCancelacion;
	}

	public Long getCveFkPatron() {
		return cveFkPatron;
	}

	public void setCveFkPatron(Long cveFkPatron) {
		this.cveFkPatron = cveFkPatron;
	}

	public BigDecimal getCveDomGeoPatron() {
		return cveDomGeoPatron;
	}

	public void setCveDomGeoPatron(BigDecimal cveDomGeoPatron) {
		this.cveDomGeoPatron = cveDomGeoPatron;
	}

	public String getNuVolanteCancela() {
		return nuVolanteCancela;
	}

	public void setNuVolanteCancela(String nuVolanteCancela) {
		this.nuVolanteCancela = nuVolanteCancela;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}
	
	public void setCveSelector(Long cveSelector) {
		this.cveSelector = cveSelector;
	}
	
	public Long getCveSelector() {
		return cveSelector;
	}	
	
	public String getTxObservaciones() {
		return txObservaciones;
	}

	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}

	/**
	 * @return the cveFkPatronFisica
	 */
	public Long getCveFkPatronFisica() {
		return cveFkPatronFisica;
	}

	/**
	 * @param cveFkPatronFisica the cveFkPatronFisica to set
	 */
	public void setCveFkPatronFisica(Long cveFkPatronFisica) {
		this.cveFkPatronFisica = cveFkPatronFisica;
	}

	/**
	 * @return the cveFkPatronObra
	 */
	public Long getCveFkPatronObra() {
		return cveFkPatronObra;
	}

	/**
	 * @param cveFkPatronObra the cveFkPatronObra to set
	 */
	public void setCveFkPatronObra(Long cveFkPatronObra) {
		this.cveFkPatronObra = cveFkPatronObra;
	}

	/**
	 * @return the fecFechaDeriSubdelegacion
	 */
	public Date getFecFechaDeriSubdelegacion() {
		return fecFechaDeriSubdelegacion;
	}

	/**
	 * @param fecFechaDeriSubdelegacion the fecFechaDeriSubdelegacion to set
	 */
	public void setFecFechaDeriSubdelegacion(Date fecFechaDeriSubdelegacion) {
		this.fecFechaDeriSubdelegacion = fecFechaDeriSubdelegacion;
	}

	/**
	 * @return the cveFkSubdelegacionDest
	 */
	public Long getCveFkSubdelegacionDest() {
		return cveFkSubdelegacionDest;
	}

	/**
	 * @param cveFkSubdelegacionDest the cveFkSubdelegacionDest to set
	 */
	public void setCveFkSubdelegacionDest(Long cveFkSubdelegacionDest) {
		this.cveFkSubdelegacionDest = cveFkSubdelegacionDest;
	}

	/**
	 * @return the txRefDerivacion
	 */
	public String getTxRefDerivacion() {
		return txRefDerivacion;
	}

	/**
	 * @param txRefDerivacion the txRefDerivacion to set
	 */
	public void setTxRefDerivacion(String txRefDerivacion) {
		this.txRefDerivacion = txRefDerivacion;
	}

//	/**
//	 * @return the cveAuditorAsignado
//	 */
//	public Long getCveAuditorAsignado() {
//		return cveAuditorAsignado;
//	}
//
//	/**
//	 * @param cveAuditorAsignado the cveAuditorAsignado to set
//	 */
//	public void setCveAuditorAsignado(Long cveAuditorAsignado) {
//		this.cveAuditorAsignado = cveAuditorAsignado;
//	}
	
	public String getCveAuditorAsignado() {
		return cveAuditorAsignado;
	}

	public void setCveAuditorAsignado(String cveAuditorAsignado) {
		this.cveAuditorAsignado = cveAuditorAsignado;
	}

	/**
	 * @return the idRegulaObra
	 */
	public Long getIdRegulaObra() {
		return idRegulaObra;
	}

	/**
	 * @param idRegulaObra the idRegulaObra to set
	 */
	public void setIdRegulaObra(Long idRegulaObra) {
		this.idRegulaObra = idRegulaObra;
	}

	/**
	 * @return the fecCotizRaz
	 */
	public Date getFecCotizRaz() {
		return fecCotizRaz;
	}

	/**
	 * @param fecCotizRaz the fecCotizRaz to set
	 */
	public void setFecCotizRaz(Date fecCotizRaz) {
		this.fecCotizRaz = fecCotizRaz;
	}
	
}