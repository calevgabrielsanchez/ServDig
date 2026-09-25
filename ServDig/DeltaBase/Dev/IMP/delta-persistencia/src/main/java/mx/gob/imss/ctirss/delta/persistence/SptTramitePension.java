package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_TRAMITE_PENSION database table.
 * 
 */
@Entity
@Table(name="SPT_TRAMITE_PENSION")
@NamedQuery(name="SptTramitePension.findAll", query="SELECT s FROM SptTramitePension s")
public class SptTramitePension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTTRAMITEPENSION", sequenceName = "SEQ_SPTTRAMITEPENSION")
	@GeneratedValue(generator = "SEQ_SPTTRAMITEPENSION")
	@Column(name="CVE_ID_TRAMITE_PENSION")
	private long cveIdTramitePension;

	@Column(name="CVE_ANTECEDENTE_CAMBIO_RAMA_PE")
	private String cveAntecedenteCambioRamaPe;

	@Column(name="CVE_ANTECEDENTE_DERIVADA")
	private String cveAntecedenteDerivada;

	@Column(name="CVE_CTO_COSTO")
	private String cveCtoCosto;

	@Column(name="CVE_CURP_PROCESAR")
	private String cveCurpProcesar;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="CVE_EDO_PROCESAR")
	private String cveEdoProcesar;

	@Column(name="CVE_MODALIDAD")
	private String cveModalidad;

	@Column(name="CVE_PREI")
	private String cvePrei;

	@Column(name="CVE_REGISTRO_PATRONAL")
	private String cveRegistroPatronal;

	@Column(name="CVE_RESULTADO_PROCESAR")
	private String cveResultadoProcesar;

	@Column(name="CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Column(name="CVE_UMF_TRAMITADORA")
	private String cveUmfTramitadora;

	@Column(name="DES_CODIGO_ERROR_PROCESAR")
	private String desCodigoErrorProcesar;

	@Column(name="DES_MARCAS_PROCESAR")
	private String desMarcasProcesar;

	@Column(name="DES_NOMBRE_PATRON")
	private String desNombrePatron;

	@Column(name="DES_SPES_ESTADO_FECHA")
	private String desSpesEstadoFecha;

	@Column(name="DES_SPES_ESTADO_SOLICITUD")
	private String desSpesEstadoSolicitud;

	@Column(name="EDO_WEB_SERVICE")
	private String edoWebService;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA")
	private Date fecBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_CERTIFICACION")
	private Date fecCertificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ELECCION_REGIMEN")
	private Date fecEleccionRegimen;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ELECCION_REGIMEN_DERA")
	private Date fecEleccionRegimenDera;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_EMISION_DER")
	private Date fecEmisionDer;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_EMISION_DERA")
	private Date fecEmisionDera;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_EMISION_RESOLUCION")
	private Date fecEmisionResolucion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ENVIO_RELACION")
	private Date fecEnvioRelacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_AJUSTE")
	private Date fecInicioAjuste;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PAGO")
	private Date fecInicioPago;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PENSION_DEFINITIVA")
	private Date fecInicioPensionDefinitiva;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_PREVALID_CUENTA_INDIVIDUAL")
	private Date fecPrevalidCuentaIndividual;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_SOL_CERTIFICACION")
	private Date fecSolCertificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_SOLICITUD_PENSION")
	private Date fecSolicitudPension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ULTIMA_COTIZACION")
	private Date fecUltimaCotizacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_VENCIMIENTO")
	private Date fecVencimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_VENCIMIENTO_CONSERVACION_D")
	private Date fecVencimientoConservacionD;

	@Column(name="ID_ANTECEDENTE_RV")
	private String idAntecedenteRv;

	@Column(name="ID_ART_BASE_NEGATIVA_CONS")
	private String idArtBaseNegativaCons;

	@Column(name="ID_DIAGNOSTICO_CTAINDIVIDUAL")
	private String idDiagnosticoCtaindividual;

	@Column(name="ID_DICTAMEN_LAUDO")
	private BigDecimal idDictamenLaudo;

	@Column(name="ID_ESQUEMA" )
	private String idEsquema;

	@Column(name="ID_ESTADO_FEC")
	private String idEstadoFec;

	@Column(name="ID_FORMA_PAGO_PENSION")
	private String idFormaPagoPension;

	@Column(name="ID_PAIS_CONVENIO")
	private String idPaisConvenio;

	@Column(name="ID_PAIS_PAGO_EXTRANJERO")
	private String idPaisPagoExtranjero;

	@Column(name="ID_PAIS_SEMANAS_RECONOCIDAS")
	private String idPaisSemanasReconocidas;

	@Column(name="ID_RAMA")
	private String idRama;

	@Column(name="ID_REGIMEN",insertable=false, updatable=false)
	private String idRegimen;

	@Column(name="ID_SOLICITUD")
	private String idSolicitud;

	@Column(name="ID_TIPO_JUICIO")
	private String idTipoJuicio;

	@Column(name="ID_TIPO_PENSION")
	private String idTipoPension;

	@Column(name="IND_ALTA_SPES")
	private String indAltaSpes;

	@Column(name="IND_CONST_SEMANAS")
	private String indConstSemanas;

	@Column(name="IND_CONVENIO_OTRO_PAIS")
	private String indConvenioOtroPais;

	@Column(name="IND_COPIA_COMPONENTES")
	private BigDecimal indCopiaComponentes;

	@Column(name="IND_DERECHO_73")
	private String indDerecho73;

	@Column(name="IND_DERECHO_97")
	private String indDerecho97;

	@Column(name="IND_DERECHO_RETIRO")
	private String indDerechoRetiro;

	@Column(name="IND_FERROCARRILERO_CONVENIO")
	private String indFerrocarrileroConvenio;

	@Column(name="IND_FIRMA_OTRA_RUEGO")
	private String indFirmaOtraRuego;

	@Column(name="IND_PAGO_AFORE")
	private String indPagoAfore;

	@Column(name="IND_PMG")
	private String indPmg;

	@Column(name="IND_PROCESO_BATCH")
	private String indProcesoBatch;

	@Column(name="IND_RECHAZO_PROCESAR")
	private String indRechazoProcesar;

	@Column(name="IND_TRABAJADOR_BANCOMEX")
	private String indTrabajadorBancomex;

	@Column(name="IND_TRABAJADOR_IMSS")
	private String indTrabajadorImss;

	@Column(name="IND_TRANSF_DER_ISSSTE")
	private String indTransfDerIssste;

	@Column(name="IND_VAL_PREVIA")
	private String indValPrevia;

	@Column(name="NOM_APELLIDO_MATERNO_PROCESAR")
	private String nomApellidoMaternoProcesar;

	@Column(name="NOM_APELLIDO_PATERNO_PROCESAR")
	private String nomApellidoPaternoProcesar;

	@Column(name="NOM_NOMBRE_PROCESAR")
	private String nomNombreProcesar;

	@Column(name="NUM_CUANTIA_MENSUAL")
	private BigDecimal numCuantiaMensual;

	@Column(name="NUM_IMPRESION")
	private BigDecimal numImpresion;

	@Column(name="NUM_SALARIO")
	private BigDecimal numSalario;

	@Column(name="NUM_SALARIO_INV_73")
	private BigDecimal numSalarioInv73;

	@Column(name="NUM_SALARIO_INV_97")
	private BigDecimal numSalarioInv97;

	@Column(name="NUM_SALARIO_PROM_ADICIONAL")
	private BigDecimal numSalarioPromAdicional;

	@Column(name="NUM_SALARIO_W73")
	private BigDecimal numSalarioW73;

	@Column(name="NUM_SALARIO_W97")
	private BigDecimal numSalarioW97;

	@Column(name="NUM_SEC_SPES")
	private BigDecimal numSecSpes;

	@Column(name="NUM_SECUENCIA_SOL")
	private BigDecimal numSecuenciaSol;

	@Column(name="NUM_SEMANAS_31121990")
	private BigDecimal numSemanas31121990;

	@Column(name="NUM_SEMANAS_EXTRANJERO")
	private BigDecimal numSemanasExtranjero;

	@Column(name="NUM_SEMANAS_INCAPACIDAD")
	private BigDecimal numSemanasIncapacidad;

	@Column(name="NUM_SEMANAS_ISSSTE")
	private BigDecimal numSemanasIssste;

	@Column(name="NUM_SEMANAS_MEJORA")
	private BigDecimal numSemanasMejora;

	@Column(name="NUM_SEMANAS_RECONOCIDAS")
	private BigDecimal numSemanasReconocidas;

	@Column(name="POR_AYUDA_ASISTENCIAL")
	private BigDecimal porAyudaAsistencial;

	@Column(name="REF_EMAIL")
	private String refEmail;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_PENSION")
	private Date fecInicioPension;	

	//bi-directional many-to-one association to AptCalculoCuantiasSolImss
	@OneToMany(mappedBy="sptTramitePension")
	private List<AptCalculoCuantiasSolImss> aptCalculoCuantiasSolImsses;

	//bi-directional many-to-one association to AptImportesResolucion
	@OneToMany(mappedBy="sptTramitePension")
	private List<AptImportesResolucion> aptImportesResolucions;

	//bi-directional many-to-one association to SptBajaSolicitud
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptBajaSolicitud> sptBajaSolicituds;

	//bi-directional many-to-one association to SptBeneficiarioSolicitud
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptBeneficiarioSolicitud> sptBeneficiarioSolicituds;

	//bi-directional many-to-one association to SptDocProbBenefSolic
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptDocProbBenefSolic> sptDocProbBenefSolics;

	//bi-directional many-to-one association to SptEnvComunicEntidade
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptEnvComunicEntidade> sptEnvComunicEntidades;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptPension> sptPensions;

	//bi-directional many-to-one association to SptResolucion
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptResolucion> sptResolucions;

	//bi-directional many-to-one association to DitTramite
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE")
	private DitTramite ditTramite;

	//bi-directional many-to-one association to SpcIncidencia
	@ManyToOne
	@JoinColumn(name="ID_INCIDENCIA")
	private SpcIncidencia spcIncidencia;

	//bi-directional many-to-one association to SptTramitePensionDictamen
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptTramitePensionDictamen> sptTramitePensionDictamens;

	//bi-directional many-to-one association to SptTramPensCertificado
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptTramPensCertificado> sptTramPensCertificados;
	
	
	//bi-directional many-to-one association to SpcEsquema
    @ManyToOne
	@JoinColumn(name="ID_ESQUEMA", insertable=false, updatable=false)
	private SpcEsquema spcEsquema;

	//bi-directional many-to-one association to SpcFormaPagoPension
    @ManyToOne
	@JoinColumn(name="ID_FORMA_PAGO_PENSION", insertable=false, updatable=false)
	private SpcFormaPagoPension spcFormaPagoPension;



	//bi-directional many-to-one association to SpcModalidad
//    @ManyToOne
//	@JoinColumns({
//		@JoinColumn(name="ID_MODALIDAD", referencedColumnName="ID_MODALIDAD"),
//		@JoinColumn(name="ID_REGIMEN", referencedColumnName="ID_REGIMEN")
//		})
//	private SpcModalidad spcModalidad; //FIXME no esta en base datos el campo id modalidad

	//bi-directional many-to-one association to SpcRama
    @ManyToOne
	@JoinColumn(name="ID_RAMA",insertable=false, updatable=false)
	private SpcRama spcRama;

	//bi-directional many-to-one association to SpcReformaLey
//    @ManyToOne
//	@JoinColumn(name="ID_REFORMA_LEY") // FIXME no esta el campo en base datos
//	private SpcReformaLey spcReformaLey;

	//bi-directional many-to-one association to SpcRegimen
    @ManyToOne
	@JoinColumn(name="ID_REGIMEN",insertable=false, updatable=false)
	private SpcRegimen spcRegimen;

	//bi-directional many-to-one association to SpcTipoJuicio
    @ManyToOne
	@JoinColumn(name="ID_TIPO_JUICIO", insertable=false, updatable=false)
	private SpcTipoJuicio spcTipoJuicio;

	//bi-directional many-to-one association to SpcTipoMovimiento
//    @ManyToOne
//	@JoinColumn(name="ID_TIPO_MOVIMIENTO", insertable=false, updatable=false)
//	private SpcTipoMovimiento spcTipoMovimiento; //FIXME no esta en base datos el campo

	//bi-directional many-to-one association to SpcTipoPension
    @ManyToOne
	@JoinColumn(name="ID_TIPO_PENSION",insertable=false, updatable=false)
	private SpcTipoPension spcTipoPension;


	
	
	//bi-directional many-to-one association to SptSolAntecedPrev
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptSolAntecedPrev> sptSolAntecedPrevs;
	
	//bi-directional many-to-one association to SptHistTramitePension
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptHistTramitePension> sptHistTramitePensions;	
	
	//bi-directional many-to-one association to SptGrupoFamiliarMov
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptGrupoFamiliarMov> sptGrupoFamiliarMovs;	
	
	//bi-directional many-to-one association to SptTitularGrupoMov
	@OneToMany(mappedBy="sptTramitePension")
	private List<SptTitularGrupoMov> sptTitularGrupoMovs;	
	
	
	
	
	

	public SptTramitePension() {
	}

	public long getCveIdTramitePension() {
		return this.cveIdTramitePension;
	}

	public void setCveIdTramitePension(long cveIdTramitePension) {
		this.cveIdTramitePension = cveIdTramitePension;
	}

	public String getCveAntecedenteCambioRamaPe() {
		return this.cveAntecedenteCambioRamaPe;
	}

	public void setCveAntecedenteCambioRamaPe(String cveAntecedenteCambioRamaPe) {
		this.cveAntecedenteCambioRamaPe = cveAntecedenteCambioRamaPe;
	}

	public String getCveAntecedenteDerivada() {
		return this.cveAntecedenteDerivada;
	}

	public void setCveAntecedenteDerivada(String cveAntecedenteDerivada) {
		this.cveAntecedenteDerivada = cveAntecedenteDerivada;
	}

	public String getCveCtoCosto() {
		return this.cveCtoCosto;
	}

	public void setCveCtoCosto(String cveCtoCosto) {
		this.cveCtoCosto = cveCtoCosto;
	}

	public String getCveCurpProcesar() {
		return this.cveCurpProcesar;
	}

	public void setCveCurpProcesar(String cveCurpProcesar) {
		this.cveCurpProcesar = cveCurpProcesar;
	}

	public String getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveEdoProcesar() {
		return this.cveEdoProcesar;
	}

	public void setCveEdoProcesar(String cveEdoProcesar) {
		this.cveEdoProcesar = cveEdoProcesar;
	}

	public String getCveModalidad() {
		return this.cveModalidad;
	}

	public void setCveModalidad(String cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

	public String getCvePrei() {
		return this.cvePrei;
	}

	public void setCvePrei(String cvePrei) {
		this.cvePrei = cvePrei;
	}

	public String getCveRegistroPatronal() {
		return this.cveRegistroPatronal;
	}

	public void setCveRegistroPatronal(String cveRegistroPatronal) {
		this.cveRegistroPatronal = cveRegistroPatronal;
	}

	public String getCveResultadoProcesar() {
		return this.cveResultadoProcesar;
	}

	public void setCveResultadoProcesar(String cveResultadoProcesar) {
		this.cveResultadoProcesar = cveResultadoProcesar;
	}

	public String getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public String getCveUmfTramitadora() {
		return this.cveUmfTramitadora;
	}

	public void setCveUmfTramitadora(String cveUmfTramitadora) {
		this.cveUmfTramitadora = cveUmfTramitadora;
	}

	public String getDesCodigoErrorProcesar() {
		return this.desCodigoErrorProcesar;
	}

	public void setDesCodigoErrorProcesar(String desCodigoErrorProcesar) {
		this.desCodigoErrorProcesar = desCodigoErrorProcesar;
	}

	public String getDesMarcasProcesar() {
		return this.desMarcasProcesar;
	}

	public void setDesMarcasProcesar(String desMarcasProcesar) {
		this.desMarcasProcesar = desMarcasProcesar;
	}

	public String getDesNombrePatron() {
		return this.desNombrePatron;
	}

	public void setDesNombrePatron(String desNombrePatron) {
		this.desNombrePatron = desNombrePatron;
	}

	public String getDesSpesEstadoFecha() {
		return this.desSpesEstadoFecha;
	}

	public void setDesSpesEstadoFecha(String desSpesEstadoFecha) {
		this.desSpesEstadoFecha = desSpesEstadoFecha;
	}

	public String getDesSpesEstadoSolicitud() {
		return this.desSpesEstadoSolicitud;
	}

	public void setDesSpesEstadoSolicitud(String desSpesEstadoSolicitud) {
		this.desSpesEstadoSolicitud = desSpesEstadoSolicitud;
	}

	public String getEdoWebService() {
		return this.edoWebService;
	}

	public void setEdoWebService(String edoWebService) {
		this.edoWebService = edoWebService;
	}

	public Date getFecBaja() {
		return this.fecBaja;
	}

	public void setFecBaja(Date fecBaja) {
		this.fecBaja = fecBaja;
	}

	public Date getFecCertificacion() {
		return this.fecCertificacion;
	}

	public void setFecCertificacion(Date fecCertificacion) {
		this.fecCertificacion = fecCertificacion;
	}

	public Date getFecEleccionRegimen() {
		return this.fecEleccionRegimen;
	}

	public void setFecEleccionRegimen(Date fecEleccionRegimen) {
		this.fecEleccionRegimen = fecEleccionRegimen;
	}

	public Date getFecEleccionRegimenDera() {
		return this.fecEleccionRegimenDera;
	}

	public void setFecEleccionRegimenDera(Date fecEleccionRegimenDera) {
		this.fecEleccionRegimenDera = fecEleccionRegimenDera;
	}

	public Date getFecEmisionDer() {
		return this.fecEmisionDer;
	}

	public void setFecEmisionDer(Date fecEmisionDer) {
		this.fecEmisionDer = fecEmisionDer;
	}

	public Date getFecEmisionDera() {
		return this.fecEmisionDera;
	}

	public void setFecEmisionDera(Date fecEmisionDera) {
		this.fecEmisionDera = fecEmisionDera;
	}

	public Date getFecEmisionResolucion() {
		return this.fecEmisionResolucion;
	}

	public void setFecEmisionResolucion(Date fecEmisionResolucion) {
		this.fecEmisionResolucion = fecEmisionResolucion;
	}

	public Date getFecEnvioRelacion() {
		return this.fecEnvioRelacion;
	}

	public void setFecEnvioRelacion(Date fecEnvioRelacion) {
		this.fecEnvioRelacion = fecEnvioRelacion;
	}

	public Date getFecInicioAjuste() {
		return this.fecInicioAjuste;
	}

	public void setFecInicioAjuste(Date fecInicioAjuste) {
		this.fecInicioAjuste = fecInicioAjuste;
	}

	public Date getFecInicioPago() {
		return this.fecInicioPago;
	}

	public void setFecInicioPago(Date fecInicioPago) {
		this.fecInicioPago = fecInicioPago;
	}

	public Date getFecInicioPensionDefinitiva() {
		return this.fecInicioPensionDefinitiva;
	}

	public void setFecInicioPensionDefinitiva(Date fecInicioPensionDefinitiva) {
		this.fecInicioPensionDefinitiva = fecInicioPensionDefinitiva;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public Date getFecPrevalidCuentaIndividual() {
		return this.fecPrevalidCuentaIndividual;
	}

	public void setFecPrevalidCuentaIndividual(Date fecPrevalidCuentaIndividual) {
		this.fecPrevalidCuentaIndividual = fecPrevalidCuentaIndividual;
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

	public Date getFecSolCertificacion() {
		return this.fecSolCertificacion;
	}

	public void setFecSolCertificacion(Date fecSolCertificacion) {
		this.fecSolCertificacion = fecSolCertificacion;
	}

	public Date getFecSolicitudPension() {
		return this.fecSolicitudPension;
	}

	public void setFecSolicitudPension(Date fecSolicitudPension) {
		this.fecSolicitudPension = fecSolicitudPension;
	}

	public Date getFecUltimaCotizacion() {
		return this.fecUltimaCotizacion;
	}

	public void setFecUltimaCotizacion(Date fecUltimaCotizacion) {
		this.fecUltimaCotizacion = fecUltimaCotizacion;
	}

	public Date getFecVencimiento() {
		return this.fecVencimiento;
	}

	public void setFecVencimiento(Date fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}

	public Date getFecVencimientoConservacionD() {
		return this.fecVencimientoConservacionD;
	}

	public void setFecVencimientoConservacionD(Date fecVencimientoConservacionD) {
		this.fecVencimientoConservacionD = fecVencimientoConservacionD;
	}

	public String getIdAntecedenteRv() {
		return this.idAntecedenteRv;
	}

	public void setIdAntecedenteRv(String idAntecedenteRv) {
		this.idAntecedenteRv = idAntecedenteRv;
	}

	public String getIdArtBaseNegativaCons() {
		return this.idArtBaseNegativaCons;
	}

	public void setIdArtBaseNegativaCons(String idArtBaseNegativaCons) {
		this.idArtBaseNegativaCons = idArtBaseNegativaCons;
	}

	public String getIdDiagnosticoCtaindividual() {
		return this.idDiagnosticoCtaindividual;
	}

	public void setIdDiagnosticoCtaindividual(String idDiagnosticoCtaindividual) {
		this.idDiagnosticoCtaindividual = idDiagnosticoCtaindividual;
	}

	public BigDecimal getIdDictamenLaudo() {
		return this.idDictamenLaudo;
	}

	public void setIdDictamenLaudo(BigDecimal idDictamenLaudo) {
		this.idDictamenLaudo = idDictamenLaudo;
	}

	public String getIdEsquema() {
		return this.idEsquema;
	}

	public void setIdEsquema(String idEsquema) {
		this.idEsquema = idEsquema;
	}

	public String getIdEstadoFec() {
		return this.idEstadoFec;
	}

	public void setIdEstadoFec(String idEstadoFec) {
		this.idEstadoFec = idEstadoFec;
	}

	public String getIdFormaPagoPension() {
		return this.idFormaPagoPension;
	}

	public void setIdFormaPagoPension(String idFormaPagoPension) {
		this.idFormaPagoPension = idFormaPagoPension;
	}

	public String getIdPaisConvenio() {
		return this.idPaisConvenio;
	}

	public void setIdPaisConvenio(String idPaisConvenio) {
		this.idPaisConvenio = idPaisConvenio;
	}

	public String getIdPaisPagoExtranjero() {
		return this.idPaisPagoExtranjero;
	}

	public void setIdPaisPagoExtranjero(String idPaisPagoExtranjero) {
		this.idPaisPagoExtranjero = idPaisPagoExtranjero;
	}

	public String getIdPaisSemanasReconocidas() {
		return this.idPaisSemanasReconocidas;
	}

	public void setIdPaisSemanasReconocidas(String idPaisSemanasReconocidas) {
		this.idPaisSemanasReconocidas = idPaisSemanasReconocidas;
	}

	public String getIdRama() {
		return this.idRama;
	}

	public void setIdRama(String idRama) {
		this.idRama = idRama;
	}

	public String getIdRegimen() {
		return this.idRegimen;
	}

	public void setIdRegimen(String idRegimen) {
		this.idRegimen = idRegimen;
	}

	public String getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getIdTipoJuicio() {
		return this.idTipoJuicio;
	}

	public void setIdTipoJuicio(String idTipoJuicio) {
		this.idTipoJuicio = idTipoJuicio;
	}

	public String getIdTipoPension() {
		return this.idTipoPension;
	}

	public void setIdTipoPension(String idTipoPension) {
		this.idTipoPension = idTipoPension;
	}

	public String getIndAltaSpes() {
		return this.indAltaSpes;
	}

	public void setIndAltaSpes(String indAltaSpes) {
		this.indAltaSpes = indAltaSpes;
	}

	public String getIndConstSemanas() {
		return this.indConstSemanas;
	}

	public void setIndConstSemanas(String indConstSemanas) {
		this.indConstSemanas = indConstSemanas;
	}

	public String getIndConvenioOtroPais() {
		return this.indConvenioOtroPais;
	}

	public void setIndConvenioOtroPais(String indConvenioOtroPais) {
		this.indConvenioOtroPais = indConvenioOtroPais;
	}

	public BigDecimal getIndCopiaComponentes() {
		return this.indCopiaComponentes;
	}

	public void setIndCopiaComponentes(BigDecimal indCopiaComponentes) {
		this.indCopiaComponentes = indCopiaComponentes;
	}

	public String getIndDerecho73() {
		return this.indDerecho73;
	}

	public void setIndDerecho73(String indDerecho73) {
		this.indDerecho73 = indDerecho73;
	}

	public String getIndDerecho97() {
		return this.indDerecho97;
	}

	public void setIndDerecho97(String indDerecho97) {
		this.indDerecho97 = indDerecho97;
	}

	public String getIndDerechoRetiro() {
		return this.indDerechoRetiro;
	}

	public void setIndDerechoRetiro(String indDerechoRetiro) {
		this.indDerechoRetiro = indDerechoRetiro;
	}

	public String getIndFerrocarrileroConvenio() {
		return this.indFerrocarrileroConvenio;
	}

	public void setIndFerrocarrileroConvenio(String indFerrocarrileroConvenio) {
		this.indFerrocarrileroConvenio = indFerrocarrileroConvenio;
	}

	public String getIndFirmaOtraRuego() {
		return this.indFirmaOtraRuego;
	}

	public void setIndFirmaOtraRuego(String indFirmaOtraRuego) {
		this.indFirmaOtraRuego = indFirmaOtraRuego;
	}

	public String getIndPagoAfore() {
		return this.indPagoAfore;
	}

	public void setIndPagoAfore(String indPagoAfore) {
		this.indPagoAfore = indPagoAfore;
	}

	public String getIndPmg() {
		return this.indPmg;
	}

	public void setIndPmg(String indPmg) {
		this.indPmg = indPmg;
	}

	public String getIndProcesoBatch() {
		return this.indProcesoBatch;
	}

	public void setIndProcesoBatch(String indProcesoBatch) {
		this.indProcesoBatch = indProcesoBatch;
	}

	public String getIndRechazoProcesar() {
		return this.indRechazoProcesar;
	}

	public void setIndRechazoProcesar(String indRechazoProcesar) {
		this.indRechazoProcesar = indRechazoProcesar;
	}

	public String getIndTrabajadorBancomex() {
		return this.indTrabajadorBancomex;
	}

	public void setIndTrabajadorBancomex(String indTrabajadorBancomex) {
		this.indTrabajadorBancomex = indTrabajadorBancomex;
	}

	public String getIndTrabajadorImss() {
		return this.indTrabajadorImss;
	}

	public void setIndTrabajadorImss(String indTrabajadorImss) {
		this.indTrabajadorImss = indTrabajadorImss;
	}

	public String getIndTransfDerIssste() {
		return this.indTransfDerIssste;
	}

	public void setIndTransfDerIssste(String indTransfDerIssste) {
		this.indTransfDerIssste = indTransfDerIssste;
	}

	public String getIndValPrevia() {
		return this.indValPrevia;
	}

	public void setIndValPrevia(String indValPrevia) {
		this.indValPrevia = indValPrevia;
	}

	public String getNomApellidoMaternoProcesar() {
		return this.nomApellidoMaternoProcesar;
	}

	public void setNomApellidoMaternoProcesar(String nomApellidoMaternoProcesar) {
		this.nomApellidoMaternoProcesar = nomApellidoMaternoProcesar;
	}

	public String getNomApellidoPaternoProcesar() {
		return this.nomApellidoPaternoProcesar;
	}

	public void setNomApellidoPaternoProcesar(String nomApellidoPaternoProcesar) {
		this.nomApellidoPaternoProcesar = nomApellidoPaternoProcesar;
	}

	public String getNomNombreProcesar() {
		return this.nomNombreProcesar;
	}

	public void setNomNombreProcesar(String nomNombreProcesar) {
		this.nomNombreProcesar = nomNombreProcesar;
	}

	public BigDecimal getNumCuantiaMensual() {
		return this.numCuantiaMensual;
	}

	public void setNumCuantiaMensual(BigDecimal numCuantiaMensual) {
		this.numCuantiaMensual = numCuantiaMensual;
	}

	public BigDecimal getNumImpresion() {
		return this.numImpresion;
	}

	public void setNumImpresion(BigDecimal numImpresion) {
		this.numImpresion = numImpresion;
	}

	public BigDecimal getNumSalario() {
		return this.numSalario;
	}

	public void setNumSalario(BigDecimal numSalario) {
		this.numSalario = numSalario;
	}

	public BigDecimal getNumSalarioInv73() {
		return this.numSalarioInv73;
	}

	public void setNumSalarioInv73(BigDecimal numSalarioInv73) {
		this.numSalarioInv73 = numSalarioInv73;
	}

	public BigDecimal getNumSalarioInv97() {
		return this.numSalarioInv97;
	}

	public void setNumSalarioInv97(BigDecimal numSalarioInv97) {
		this.numSalarioInv97 = numSalarioInv97;
	}

	public BigDecimal getNumSalarioPromAdicional() {
		return this.numSalarioPromAdicional;
	}

	public void setNumSalarioPromAdicional(BigDecimal numSalarioPromAdicional) {
		this.numSalarioPromAdicional = numSalarioPromAdicional;
	}

	public BigDecimal getNumSalarioW73() {
		return this.numSalarioW73;
	}

	public void setNumSalarioW73(BigDecimal numSalarioW73) {
		this.numSalarioW73 = numSalarioW73;
	}

	public BigDecimal getNumSalarioW97() {
		return this.numSalarioW97;
	}

	public void setNumSalarioW97(BigDecimal numSalarioW97) {
		this.numSalarioW97 = numSalarioW97;
	}

	public BigDecimal getNumSecSpes() {
		return this.numSecSpes;
	}

	public void setNumSecSpes(BigDecimal numSecSpes) {
		this.numSecSpes = numSecSpes;
	}

	public BigDecimal getNumSecuenciaSol() {
		return this.numSecuenciaSol;
	}

	public void setNumSecuenciaSol(BigDecimal numSecuenciaSol) {
		this.numSecuenciaSol = numSecuenciaSol;
	}

	public BigDecimal getNumSemanas31121990() {
		return this.numSemanas31121990;
	}

	public void setNumSemanas31121990(BigDecimal numSemanas31121990) {
		this.numSemanas31121990 = numSemanas31121990;
	}

	public BigDecimal getNumSemanasExtranjero() {
		return this.numSemanasExtranjero;
	}

	public void setNumSemanasExtranjero(BigDecimal numSemanasExtranjero) {
		this.numSemanasExtranjero = numSemanasExtranjero;
	}

	public BigDecimal getNumSemanasIncapacidad() {
		return this.numSemanasIncapacidad;
	}

	public void setNumSemanasIncapacidad(BigDecimal numSemanasIncapacidad) {
		this.numSemanasIncapacidad = numSemanasIncapacidad;
	}

	public BigDecimal getNumSemanasIssste() {
		return this.numSemanasIssste;
	}

	public void setNumSemanasIssste(BigDecimal numSemanasIssste) {
		this.numSemanasIssste = numSemanasIssste;
	}

	public BigDecimal getNumSemanasMejora() {
		return this.numSemanasMejora;
	}

	public void setNumSemanasMejora(BigDecimal numSemanasMejora) {
		this.numSemanasMejora = numSemanasMejora;
	}

	public BigDecimal getNumSemanasReconocidas() {
		return this.numSemanasReconocidas;
	}

	public void setNumSemanasReconocidas(BigDecimal numSemanasReconocidas) {
		this.numSemanasReconocidas = numSemanasReconocidas;
	}

	public BigDecimal getPorAyudaAsistencial() {
		return this.porAyudaAsistencial;
	}

	public void setPorAyudaAsistencial(BigDecimal porAyudaAsistencial) {
		this.porAyudaAsistencial = porAyudaAsistencial;
	}

	public String getRefEmail() {
		return this.refEmail;
	}

	public void setRefEmail(String refEmail) {
		this.refEmail = refEmail;
	}

	public List<AptCalculoCuantiasSolImss> getAptCalculoCuantiasSolImsses() {
		return this.aptCalculoCuantiasSolImsses;
	}

	public void setAptCalculoCuantiasSolImsses(List<AptCalculoCuantiasSolImss> aptCalculoCuantiasSolImsses) {
		this.aptCalculoCuantiasSolImsses = aptCalculoCuantiasSolImsses;
	}

	public AptCalculoCuantiasSolImss addAptCalculoCuantiasSolImss(AptCalculoCuantiasSolImss aptCalculoCuantiasSolImss) {
		getAptCalculoCuantiasSolImsses().add(aptCalculoCuantiasSolImss);
		aptCalculoCuantiasSolImss.setSptTramitePension(this);

		return aptCalculoCuantiasSolImss;
	}

	public AptCalculoCuantiasSolImss removeAptCalculoCuantiasSolImss(AptCalculoCuantiasSolImss aptCalculoCuantiasSolImss) {
		getAptCalculoCuantiasSolImsses().remove(aptCalculoCuantiasSolImss);
		aptCalculoCuantiasSolImss.setSptTramitePension(null);

		return aptCalculoCuantiasSolImss;
	}

	public List<AptImportesResolucion> getAptImportesResolucions() {
		return this.aptImportesResolucions;
	}

	public void setAptImportesResolucions(List<AptImportesResolucion> aptImportesResolucions) {
		this.aptImportesResolucions = aptImportesResolucions;
	}

	public AptImportesResolucion addAptImportesResolucion(AptImportesResolucion aptImportesResolucion) {
		getAptImportesResolucions().add(aptImportesResolucion);
		aptImportesResolucion.setSptTramitePension(this);

		return aptImportesResolucion;
	}

	public AptImportesResolucion removeAptImportesResolucion(AptImportesResolucion aptImportesResolucion) {
		getAptImportesResolucions().remove(aptImportesResolucion);
		aptImportesResolucion.setSptTramitePension(null);

		return aptImportesResolucion;
	}

	public List<SptBajaSolicitud> getSptBajaSolicituds() {
		return this.sptBajaSolicituds;
	}

	public void setSptBajaSolicituds(List<SptBajaSolicitud> sptBajaSolicituds) {
		this.sptBajaSolicituds = sptBajaSolicituds;
	}

	public SptBajaSolicitud addSptBajaSolicitud(SptBajaSolicitud sptBajaSolicitud) {
		getSptBajaSolicituds().add(sptBajaSolicitud);
		sptBajaSolicitud.setSptTramitePension(this);

		return sptBajaSolicitud;
	}

	public SptBajaSolicitud removeSptBajaSolicitud(SptBajaSolicitud sptBajaSolicitud) {
		getSptBajaSolicituds().remove(sptBajaSolicitud);
		sptBajaSolicitud.setSptTramitePension(null);

		return sptBajaSolicitud;
	}

	public List<SptBeneficiarioSolicitud> getSptBeneficiarioSolicituds() {
		return this.sptBeneficiarioSolicituds;
	}

	public void setSptBeneficiarioSolicituds(List<SptBeneficiarioSolicitud> sptBeneficiarioSolicituds) {
		this.sptBeneficiarioSolicituds = sptBeneficiarioSolicituds;
	}

	public SptBeneficiarioSolicitud addSptBeneficiarioSolicitud(SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
		getSptBeneficiarioSolicituds().add(sptBeneficiarioSolicitud);
		sptBeneficiarioSolicitud.setSptTramitePension(this);

		return sptBeneficiarioSolicitud;
	}

	public SptBeneficiarioSolicitud removeSptBeneficiarioSolicitud(SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
		getSptBeneficiarioSolicituds().remove(sptBeneficiarioSolicitud);
		sptBeneficiarioSolicitud.setSptTramitePension(null);

		return sptBeneficiarioSolicitud;
	}

	public List<SptDocProbBenefSolic> getSptDocProbBenefSolics() {
		return this.sptDocProbBenefSolics;
	}

	public void setSptDocProbBenefSolics(List<SptDocProbBenefSolic> sptDocProbBenefSolics) {
		this.sptDocProbBenefSolics = sptDocProbBenefSolics;
	}

	public SptDocProbBenefSolic addSptDocProbBenefSolic(SptDocProbBenefSolic sptDocProbBenefSolic) {
		getSptDocProbBenefSolics().add(sptDocProbBenefSolic);
		sptDocProbBenefSolic.setSptTramitePension(this);

		return sptDocProbBenefSolic;
	}

	public SptDocProbBenefSolic removeSptDocProbBenefSolic(SptDocProbBenefSolic sptDocProbBenefSolic) {
		getSptDocProbBenefSolics().remove(sptDocProbBenefSolic);
		sptDocProbBenefSolic.setSptTramitePension(null);

		return sptDocProbBenefSolic;
	}

	public List<SptEnvComunicEntidade> getSptEnvComunicEntidades() {
		return this.sptEnvComunicEntidades;
	}

	public void setSptEnvComunicEntidades(List<SptEnvComunicEntidade> sptEnvComunicEntidades) {
		this.sptEnvComunicEntidades = sptEnvComunicEntidades;
	}

	public SptEnvComunicEntidade addSptEnvComunicEntidade(SptEnvComunicEntidade sptEnvComunicEntidade) {
		getSptEnvComunicEntidades().add(sptEnvComunicEntidade);
		sptEnvComunicEntidade.setSptTramitePension(this);

		return sptEnvComunicEntidade;
	}

	public SptEnvComunicEntidade removeSptEnvComunicEntidade(SptEnvComunicEntidade sptEnvComunicEntidade) {
		getSptEnvComunicEntidades().remove(sptEnvComunicEntidade);
		sptEnvComunicEntidade.setSptTramitePension(null);

		return sptEnvComunicEntidade;
	}

	public List<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(List<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}

	public SptPension addSptPension(SptPension sptPension) {
		getSptPensions().add(sptPension);
		sptPension.setSptTramitePension(this);

		return sptPension;
	}

	public SptPension removeSptPension(SptPension sptPension) {
		getSptPensions().remove(sptPension);
		sptPension.setSptTramitePension(null);

		return sptPension;
	}

	public List<SptResolucion> getSptResolucions() {
		return this.sptResolucions;
	}

	public void setSptResolucions(List<SptResolucion> sptResolucions) {
		this.sptResolucions = sptResolucions;
	}

	public SptResolucion addSptResolucion(SptResolucion sptResolucion) {
		getSptResolucions().add(sptResolucion);
		sptResolucion.setSptTramitePension(this);

		return sptResolucion;
	}

	public SptResolucion removeSptResolucion(SptResolucion sptResolucion) {
		getSptResolucions().remove(sptResolucion);
		sptResolucion.setSptTramitePension(null);

		return sptResolucion;
	}

	public DitTramite getDitTramite() {
		return this.ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	public SpcIncidencia getSpcIncidencia() {
		return this.spcIncidencia;
	}

	public void setSpcIncidencia(SpcIncidencia spcIncidencia) {
		this.spcIncidencia = spcIncidencia;
	}

	public List<SptTramitePensionDictamen> getSptTramitePensionDictamens() {
		return this.sptTramitePensionDictamens;
	}

	public void setSptTramitePensionDictamens(List<SptTramitePensionDictamen> sptTramitePensionDictamens) {
		this.sptTramitePensionDictamens = sptTramitePensionDictamens;
	}

	public SptTramitePensionDictamen addSptTramitePensionDictamen(SptTramitePensionDictamen sptTramitePensionDictamen) {
		getSptTramitePensionDictamens().add(sptTramitePensionDictamen);
		sptTramitePensionDictamen.setSptTramitePension(this);

		return sptTramitePensionDictamen;
	}

	public SptTramitePensionDictamen removeSptTramitePensionDictamen(SptTramitePensionDictamen sptTramitePensionDictamen) {
		getSptTramitePensionDictamens().remove(sptTramitePensionDictamen);
		sptTramitePensionDictamen.setSptTramitePension(null);

		return sptTramitePensionDictamen;
	}

	public List<SptTramPensCertificado> getSptTramPensCertificados() {
		return this.sptTramPensCertificados;
	}

	public void setSptTramPensCertificados(List<SptTramPensCertificado> sptTramPensCertificados) {
		this.sptTramPensCertificados = sptTramPensCertificados;
	}

	public SptTramPensCertificado addSptTramPensCertificado(SptTramPensCertificado sptTramPensCertificado) {
		getSptTramPensCertificados().add(sptTramPensCertificado);
		sptTramPensCertificado.setSptTramitePension(this);

		return sptTramPensCertificado;
	}

	public SptTramPensCertificado removeSptTramPensCertificado(SptTramPensCertificado sptTramPensCertificado) {
		getSptTramPensCertificados().remove(sptTramPensCertificado);
		sptTramPensCertificado.setSptTramitePension(null);

		return sptTramPensCertificado;
	}

	public SpcEsquema getSpcEsquema() {
		return spcEsquema;
	}

	public void setSpcEsquema(SpcEsquema spcEsquema) {
		this.spcEsquema = spcEsquema;
	}

	public SpcFormaPagoPension getSpcFormaPagoPension() {
		return spcFormaPagoPension;
	}

	public void setSpcFormaPagoPension(SpcFormaPagoPension spcFormaPagoPension) {
		this.spcFormaPagoPension = spcFormaPagoPension;
	}

//	public SpcModalidad getSpcModalidad() {
//		return spcModalidad;
//	}
//
//	public void setSpcModalidad(SpcModalidad spcModalidad) {
//		this.spcModalidad = spcModalidad;
//	}

	public SpcRama getSpcRama() {
		return spcRama;
	}

	public void setSpcRama(SpcRama spcRama) {
		this.spcRama = spcRama;
	}

//	public SpcReformaLey getSpcReformaLey() {
//		return spcReformaLey;
//	}
//
//	public void setSpcReformaLey(SpcReformaLey spcReformaLey) {
//		this.spcReformaLey = spcReformaLey;
//	}

	public SpcRegimen getSpcRegimen() {
		return spcRegimen;
	}

	public void setSpcRegimen(SpcRegimen spcRegimen) {
		this.spcRegimen = spcRegimen;
	}

	public SpcTipoJuicio getSpcTipoJuicio() {
		return spcTipoJuicio;
	}

	public void setSpcTipoJuicio(SpcTipoJuicio spcTipoJuicio) {
		this.spcTipoJuicio = spcTipoJuicio;
	}

//	public SpcTipoMovimiento getSpcTipoMovimiento() {
//		return spcTipoMovimiento;
//	}
//
//	public void setSpcTipoMovimiento(SpcTipoMovimiento spcTipoMovimiento) {
//		this.spcTipoMovimiento = spcTipoMovimiento;
//	}

	public SpcTipoPension getSpcTipoPension() {
		return spcTipoPension;
	}

	public void setSpcTipoPension(SpcTipoPension spcTipoPension) {
		this.spcTipoPension = spcTipoPension;
	}

	public List<SptSolAntecedPrev> getSptSolAntecedPrevs() {
		return sptSolAntecedPrevs;
	}

	public void setSptSolAntecedPrevs(List<SptSolAntecedPrev> sptSolAntecedPrevs) {
		this.sptSolAntecedPrevs = sptSolAntecedPrevs;
	}

	public List<SptHistTramitePension> getSptHistTramitePensions() {
		return sptHistTramitePensions;
	}

	public void setSptHistTramitePensions(
			List<SptHistTramitePension> sptHistTramitePensions) {
		this.sptHistTramitePensions = sptHistTramitePensions;
	}

	public List<SptGrupoFamiliarMov> getSptGrupoFamiliarMovs() {
		return sptGrupoFamiliarMovs;
	}

	public void setSptGrupoFamiliarMovs(
			List<SptGrupoFamiliarMov> sptGrupoFamiliarMovs) {
		this.sptGrupoFamiliarMovs = sptGrupoFamiliarMovs;
	}

	public List<SptTitularGrupoMov> getSptTitularGrupoMovs() {
		return sptTitularGrupoMovs;
	}

	public void setSptTitularGrupoMovs(List<SptTitularGrupoMov> sptTitularGrupoMovs) {
		this.sptTitularGrupoMovs = sptTitularGrupoMovs;
	}

	public Date getFecInicioPension() {
		return this.fecInicioPension;
	}

	public void setFecInicioPension(Date fecInicioPension) {
		this.fecInicioPension = fecInicioPension;
	}
	

}