package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the V_TRAMITE_PENSION_NSS database table.
 * 
 */
@Entity
@Table(name = "V_TRAMITE_PENSION_NSS")
public class VTramitePensionNss implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VTramitePensionNssPK id;

	@Column(name = "CVE_ANTECEDENTE_CAMBIO_RAMA_PE")
	private String cveAntecedenteCambioRamaPe;

	@Temporal(TemporalType.DATE)
	@Column(name = "CVE_ANTECEDENTE_DERIVADA")
	private Date cveAntecedenteDerivada;

	@Column(name = "CVE_CTO_COSTO")
	private String cveCtoCosto;

	@Column(name = "CVE_CURP_PROCESAR")
	private String cveCurpProcesar;

	@Temporal(TemporalType.DATE)
	@Column(name = "CVE_DELEGACION")
	private Date cveDelegacion;

	@Column(name = "CVE_EDO_PROCESAR")
	private String cveEdoProcesar;

	@Column(name = "CVE_ID_TRAMITE_PENSION")
	private BigDecimal cveIdTramitePension;

	@Column(name = "CVE_MODALIDAD")
	private String cveModalidad;

	@Column(name = "CVE_PREI")
	private String cvePrei;

	@Column(name = "CVE_REGISTRO_PATRONAL")
	private String cveRegistroPatronal;

	@Column(name = "CVE_RESULTADO_PROCESAR")
	private String cveResultadoProcesar;

	@Column(name = "CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Column(name = "CVE_UMF_TRAMITADORA")
	private BigDecimal cveUmfTramitadora;

	@Temporal(TemporalType.DATE)
	@Column(name = "DES_CODIGO_ERROR_PROCESAR")
	private Date desCodigoErrorProcesar;

	@Column(name = "DES_MARCAS_PROCESAR")
	private String desMarcasProcesar;

	@Temporal(TemporalType.DATE)
	@Column(name = "DES_NOMBRE_PATRON")
	private Date desNombrePatron;

	@Column(name = "DES_SPES_ESTADO_FECHA")
	private String desSpesEstadoFecha;

	@Column(name = "DES_SPES_ESTADO_SOLICITUD")
	private String desSpesEstadoSolicitud;

	@Column(name = "EDO_WEB_SERVICE")
	private String edoWebService;

	@Column(name = "FEC_BAJA")
	private BigDecimal fecBaja;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_CERTIFICACION")
	private Date fecCertificacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ELECCION_REGIMEN")
	private Date fecEleccionRegimen;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ELECCION_REGIMEN_DERA")
	private Date fecEleccionRegimenDera;

	@Column(name = "FEC_EMISION_DER")
	private String fecEmisionDer;

	@Column(name = "FEC_EMISION_DERA")
	private BigDecimal fecEmisionDera;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_EMISION_RESOLUCION")
	private Date fecEmisionResolucion;

	@Column(name = "FEC_ENVIO_RELACION")
	private String fecEnvioRelacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INICIO_AJUSTE")
	private Date fecInicioAjuste;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INICIO_PAGO")
	private Date fecInicioPago;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_INICIO_PENSION_DEFINITIVA")
	private Date fecInicioPensionDefinitiva;

	@Column(name = "FEC_MOVIMIENTO")
	private String fecMovimiento;

	@Column(name = "FEC_PREVALID_CUENTA_INDIVIDUAL")
	private String fecPrevalidCuentaIndividual;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name = "FEC_REGISTRO_ALTA")
	private BigDecimal fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name = "FEC_SOL_CERTIFICACION")
	private BigDecimal fecSolCertificacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_SOLICITUD_PENSION")
	private Date fecSolicitudPension;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ULTIMA_COTIZACION")
	private Date fecUltimaCotizacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_VENCIMIENTO")
	private Date fecVencimiento;

	@Column(name = "FEC_VENCIMIENTO_CONSERVACION_D")
	private BigDecimal fecVencimientoConservacionD;

	@Column(name = "ID_ANTECEDENTE_RV")
	private String idAntecedenteRv;

	@Column(name = "ID_ART_BASE_NEGATIVA_CONS")
	private String idArtBaseNegativaCons;

	@Column(name = "ID_DIAGNOSTICO_CTAINDIVIDUAL")
	private String idDiagnosticoCtaindividual;

	@Column(name = "ID_DICTAMEN_LAUDO")
	private String idDictamenLaudo;

	@Column(name = "ID_ESQUEMA")
	private String idEsquema;

	@Column(name = "ID_ESTADO_FEC")
	private BigDecimal idEstadoFec;

	@Column(name = "ID_FORMA_PAGO_PENSION")
	private BigDecimal idFormaPagoPension;

	@Column(name = "ID_INCIDENCIA")
	private String idIncidencia;

	@Column(name = "ID_PAIS_CONVENIO")
	private String idPaisConvenio;

	@Column(name = "ID_PAIS_PAGO_EXTRANJERO")
	private String idPaisPagoExtranjero;

	@Column(name = "ID_PAIS_SEMANAS_RECONOCIDAS")
	private String idPaisSemanasReconocidas;

	@Column(name = "ID_RAMA")
	private String idRama;

	@Column(name = "ID_REGIMEN")
	private String idRegimen;

	@Column(name = "ID_SOLICITUD")
	private String idSolicitud;

	@Column(name = "ID_TIPO_JUICIO")
	private String idTipoJuicio;

	@Column(name = "ID_TIPO_PENSION")
	private String idTipoPension;

	@Column(name = "IND_ALTA_SPES")
	private String indAltaSpes;

	@Column(name = "IND_CONST_SEMANAS")
	private String indConstSemanas;

	@Column(name = "IND_CONVENIO_OTRO_PAIS")
	private String indConvenioOtroPais;

	@Column(name = "IND_COPIA_COMPONENTES")
	private String indCopiaComponentes;

	@Column(name = "IND_DERECHO_73")
	private String indDerecho73;

	@Column(name = "IND_DERECHO_97")
	private String indDerecho97;

	@Column(name = "IND_DERECHO_RETIRO")
	private BigDecimal indDerechoRetiro;

	@Column(name = "IND_FERROCARRILERO_CONVENIO")
	private String indFerrocarrileroConvenio;

	@Temporal(TemporalType.DATE)
	@Column(name = "IND_FIRMA_OTRA_RUEGO")
	private Date indFirmaOtraRuego;

	@Column(name = "IND_PAGO_AFORE")
	private String indPagoAfore;

	@Temporal(TemporalType.DATE)
	@Column(name = "IND_PMG")
	private Date indPmg;

	@Column(name = "IND_PROCESO_BATCH")
	private String indProcesoBatch;

	@Column(name = "IND_RECHAZO_PROCESAR")
	private String indRechazoProcesar;

	@Column(name = "IND_TRABAJADOR_BANCOMEX")
	private BigDecimal indTrabajadorBancomex;

	@Column(name = "IND_TRABAJADOR_IMSS")
	private String indTrabajadorImss;

	@Column(name = "IND_TRANSF_DER_ISSSTE")
	private String indTransfDerIssste;

	@Column(name = "IND_VAL_PREVIA")
	private String indValPrevia;

	@Column(name = "NOM_APELLIDO_MATERNO_PROCESAR")
	private String nomApellidoMaternoProcesar;

	@Column(name = "NOM_APELLIDO_PATERNO_PROCESAR")
	private String nomApellidoPaternoProcesar;

	@Column(name = "NOM_NOMBRE_PROCESAR")
	private String nomNombreProcesar;

	@Column(name = "NUM_CUANTIA_MENSUAL")
	private BigDecimal numCuantiaMensual;

	@Column(name = "NUM_IMPRESION")
	private String numImpresion;

	@Column(name = "NUM_SALARIO")
	private BigDecimal numSalario;

	@Column(name = "NUM_SALARIO_INV_73")
	private BigDecimal numSalarioInv73;

	@Column(name = "NUM_SALARIO_INV_97")
	private BigDecimal numSalarioInv97;

	@Column(name = "NUM_SALARIO_PROM_ADICIONAL")
	private String numSalarioPromAdicional;

	@Column(name = "NUM_SALARIO_W73")
	private BigDecimal numSalarioW73;

	@Column(name = "NUM_SALARIO_W97")
	private BigDecimal numSalarioW97;

	@Column(name = "NUM_SEC_SPES")
	private String numSecSpes;

	@Column(name = "NUM_SECUENCIA_SOL")
	private String numSecuenciaSol;

	@Column(name = "NUM_SEMANAS_31121990")
	private BigDecimal numSemanas31121990;

	@Column(name = "NUM_SEMANAS_EXTRANJERO")
	private BigDecimal numSemanasExtranjero;

	@Column(name = "NUM_SEMANAS_INCAPACIDAD")
	private BigDecimal numSemanasIncapacidad;

	@Column(name = "NUM_SEMANAS_ISSSTE")
	private String numSemanasIssste;

	@Column(name = "NUM_SEMANAS_MEJORA")
	private BigDecimal numSemanasMejora;

	@Temporal(TemporalType.DATE)
	@Column(name = "NUM_SEMANAS_RECONOCIDAS")
	private Date numSemanasReconocidas;

	@Temporal(TemporalType.DATE)
	@Column(name = "POR_AYUDA_ASISTENCIAL")
	private Date porAyudaAsistencial;

	@Temporal(TemporalType.DATE)
	@Column(name = "REF_EMAIL")
	private Date refEmail;

	public VTramitePensionNss() {
	}

	public VTramitePensionNssPK getId() {
		return id;
	}

	public void setId(VTramitePensionNssPK id) {
		this.id = id;
	}

	public String getCveAntecedenteCambioRamaPe() {
		return this.cveAntecedenteCambioRamaPe;
	}

	public void setCveAntecedenteCambioRamaPe(String cveAntecedenteCambioRamaPe) {
		this.cveAntecedenteCambioRamaPe = cveAntecedenteCambioRamaPe;
	}

	public Date getCveAntecedenteDerivada() {
		return this.cveAntecedenteDerivada;
	}

	public void setCveAntecedenteDerivada(Date cveAntecedenteDerivada) {
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

	public Date getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(Date cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveEdoProcesar() {
		return this.cveEdoProcesar;
	}

	public void setCveEdoProcesar(String cveEdoProcesar) {
		this.cveEdoProcesar = cveEdoProcesar;
	}

	public BigDecimal getCveIdTramitePension() {
		return this.cveIdTramitePension;
	}

	public void setCveIdTramitePension(BigDecimal cveIdTramitePension) {
		this.cveIdTramitePension = cveIdTramitePension;
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

	public BigDecimal getCveUmfTramitadora() {
		return this.cveUmfTramitadora;
	}

	public void setCveUmfTramitadora(BigDecimal cveUmfTramitadora) {
		this.cveUmfTramitadora = cveUmfTramitadora;
	}

	public Date getDesCodigoErrorProcesar() {
		return this.desCodigoErrorProcesar;
	}

	public void setDesCodigoErrorProcesar(Date desCodigoErrorProcesar) {
		this.desCodigoErrorProcesar = desCodigoErrorProcesar;
	}

	public String getDesMarcasProcesar() {
		return this.desMarcasProcesar;
	}

	public void setDesMarcasProcesar(String desMarcasProcesar) {
		this.desMarcasProcesar = desMarcasProcesar;
	}

	public Date getDesNombrePatron() {
		return this.desNombrePatron;
	}

	public void setDesNombrePatron(Date desNombrePatron) {
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

	public BigDecimal getFecBaja() {
		return this.fecBaja;
	}

	public void setFecBaja(BigDecimal fecBaja) {
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

	public String getFecEmisionDer() {
		return this.fecEmisionDer;
	}

	public void setFecEmisionDer(String fecEmisionDer) {
		this.fecEmisionDer = fecEmisionDer;
	}

	public BigDecimal getFecEmisionDera() {
		return this.fecEmisionDera;
	}

	public void setFecEmisionDera(BigDecimal fecEmisionDera) {
		this.fecEmisionDera = fecEmisionDera;
	}

	public Date getFecEmisionResolucion() {
		return this.fecEmisionResolucion;
	}

	public void setFecEmisionResolucion(Date fecEmisionResolucion) {
		this.fecEmisionResolucion = fecEmisionResolucion;
	}

	public String getFecEnvioRelacion() {
		return this.fecEnvioRelacion;
	}

	public void setFecEnvioRelacion(String fecEnvioRelacion) {
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

	public String getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(String fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public String getFecPrevalidCuentaIndividual() {
		return this.fecPrevalidCuentaIndividual;
	}

	public void setFecPrevalidCuentaIndividual(
			String fecPrevalidCuentaIndividual) {
		this.fecPrevalidCuentaIndividual = fecPrevalidCuentaIndividual;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public BigDecimal getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(BigDecimal fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public BigDecimal getFecSolCertificacion() {
		return this.fecSolCertificacion;
	}

	public void setFecSolCertificacion(BigDecimal fecSolCertificacion) {
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

	public BigDecimal getFecVencimientoConservacionD() {
		return this.fecVencimientoConservacionD;
	}

	public void setFecVencimientoConservacionD(
			BigDecimal fecVencimientoConservacionD) {
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

	public String getIdDictamenLaudo() {
		return this.idDictamenLaudo;
	}

	public void setIdDictamenLaudo(String idDictamenLaudo) {
		this.idDictamenLaudo = idDictamenLaudo;
	}

	public String getIdEsquema() {
		return this.idEsquema;
	}

	public void setIdEsquema(String idEsquema) {
		this.idEsquema = idEsquema;
	}

	public BigDecimal getIdEstadoFec() {
		return this.idEstadoFec;
	}

	public void setIdEstadoFec(BigDecimal idEstadoFec) {
		this.idEstadoFec = idEstadoFec;
	}

	public BigDecimal getIdFormaPagoPension() {
		return this.idFormaPagoPension;
	}

	public void setIdFormaPagoPension(BigDecimal idFormaPagoPension) {
		this.idFormaPagoPension = idFormaPagoPension;
	}

	public String getIdIncidencia() {
		return this.idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
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

	public String getIndCopiaComponentes() {
		return this.indCopiaComponentes;
	}

	public void setIndCopiaComponentes(String indCopiaComponentes) {
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

	public BigDecimal getIndDerechoRetiro() {
		return this.indDerechoRetiro;
	}

	public void setIndDerechoRetiro(BigDecimal indDerechoRetiro) {
		this.indDerechoRetiro = indDerechoRetiro;
	}

	public String getIndFerrocarrileroConvenio() {
		return this.indFerrocarrileroConvenio;
	}

	public void setIndFerrocarrileroConvenio(String indFerrocarrileroConvenio) {
		this.indFerrocarrileroConvenio = indFerrocarrileroConvenio;
	}

	public Date getIndFirmaOtraRuego() {
		return this.indFirmaOtraRuego;
	}

	public void setIndFirmaOtraRuego(Date indFirmaOtraRuego) {
		this.indFirmaOtraRuego = indFirmaOtraRuego;
	}

	public String getIndPagoAfore() {
		return this.indPagoAfore;
	}

	public void setIndPagoAfore(String indPagoAfore) {
		this.indPagoAfore = indPagoAfore;
	}

	public Date getIndPmg() {
		return this.indPmg;
	}

	public void setIndPmg(Date indPmg) {
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

	public BigDecimal getIndTrabajadorBancomex() {
		return this.indTrabajadorBancomex;
	}

	public void setIndTrabajadorBancomex(BigDecimal indTrabajadorBancomex) {
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

	public String getNumImpresion() {
		return this.numImpresion;
	}

	public void setNumImpresion(String numImpresion) {
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

	public String getNumSalarioPromAdicional() {
		return this.numSalarioPromAdicional;
	}

	public void setNumSalarioPromAdicional(String numSalarioPromAdicional) {
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

	public String getNumSecSpes() {
		return this.numSecSpes;
	}

	public void setNumSecSpes(String numSecSpes) {
		this.numSecSpes = numSecSpes;
	}

	public String getNumSecuenciaSol() {
		return this.numSecuenciaSol;
	}

	public void setNumSecuenciaSol(String numSecuenciaSol) {
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

	public String getNumSemanasIssste() {
		return this.numSemanasIssste;
	}

	public void setNumSemanasIssste(String numSemanasIssste) {
		this.numSemanasIssste = numSemanasIssste;
	}

	public BigDecimal getNumSemanasMejora() {
		return this.numSemanasMejora;
	}

	public void setNumSemanasMejora(BigDecimal numSemanasMejora) {
		this.numSemanasMejora = numSemanasMejora;
	}

	public Date getNumSemanasReconocidas() {
		return this.numSemanasReconocidas;
	}

	public void setNumSemanasReconocidas(Date numSemanasReconocidas) {
		this.numSemanasReconocidas = numSemanasReconocidas;
	}

	public Date getPorAyudaAsistencial() {
		return this.porAyudaAsistencial;
	}

	public void setPorAyudaAsistencial(Date porAyudaAsistencial) {
		this.porAyudaAsistencial = porAyudaAsistencial;
	}

	public Date getRefEmail() {
		return this.refEmail;
	}

	public void setRefEmail(Date refEmail) {
		this.refEmail = refEmail;
	}

}