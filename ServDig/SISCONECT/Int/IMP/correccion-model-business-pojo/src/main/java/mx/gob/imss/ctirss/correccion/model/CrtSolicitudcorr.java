package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.bean.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramiteMensajes;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;

import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
   
@Entity
@Table(name="CRT_SOLICITUDCORR")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrtSolicitudcorr extends AbstractCrtSolicitudcorr{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	public static final Integer SOLICITUD_UN_RP =1;
	
	@Transient
	public static final Integer SOLICITUD_VARIOS_RP =2;
	
	@Transient
	public static final Integer SOLICITUD_TIPO_CONSTRUCCION =1;
	
	@Transient
	public static final Integer SOLICITUD_TIPO_ORDINARIO = 0;
	
	@Transient
	public static final Integer SOLICITUD_SOLICITADA = 1;
	
	@Transient
	public static final Integer SOLICITUD_AUTORIZADA = 2;
	
	@Transient
	public static final Integer SOLICITUD_RECHAZADA = 3;
	
	@Transient
	public static final Integer SOLICITUD_PRESENTADA = 4;
	
	@Transient
	private List<CrtInvitacionRP> lsPatronesInscInvitacion;
	
	
	@Transient
	private boolean recuperaFiscal;
	
	/**
	 * Indica si la solicitud presentada
	 * es de uno o varios registros patronales.
	 * 1.- Un registro patronal
	 * 2.- Varios registros patronales
	 */
	@Transient
	private Integer unoVariosRp;

	@Transient
	private SatPatron patronPrincipal;
	@Transient
	private SatPatron patronCorregir;
	@Transient
	private SatPatron patronObra;
	
	@Transient
	private SatObra saticObra;

	@Transient
	private List patrones;

	@Transient
	private Hashtable domicilios;
	
	@Transient
	private List periodos;

	@Transient
	private String patron;

	@Transient
	private CrtPromocion promocion;
	
	@Transient
	private CrtInvitacion invitacion;
	
	@Transient
	private String numeroObra;
	@Transient
	private String tipoObra;

	@Transient
	private String rfcPatronCorregir;
	@Transient
	private String curpPatronCorregir;
	@Transient
	private String razonSocialPatronCorregir;
	@Transient
	private String callePatronCorregir;
	@Transient
	private String numExteriorPatronCorregir;
	@Transient
	private String numInteriorPatronCorregir;
	@Transient
	private String coloniaPatronCorregir;
	@Transient
	private String clasePatronCorregir;
	@Transient
	private String fraccionPatronCorregir;
	@Transient
	private String primaPatronCorregir;
	
	@Transient
	private String patronDom;
	@Transient
	private String callePatronDom;
	@Transient
	private String numExteriorPatronDom;
	@Transient
	private String numInteriorPatronDom;
	@Transient
	private String coloniaPatronDom;
	
	@Transient
	private String telefonoPatron;
	@Transient
	private String emailPatron;

	@Transient
	private String representante;
	@Transient
	private String tipo;
	@Transient
	private String numeroTrabajadores;
	@Transient
	private String actividad;
	@Transient
	private String folioInvitacion;

	@Transient
	private String fechaPresentacion;
	@Transient
	private String fechaInicial;
	@Transient
	private String fechaFinal;
	@Transient
	private boolean personaFisica;
	@Transient
	private String internet;

	@Transient
	List<CrtAnexosolcorrpat> lstAnexoSolicitudesCorr;


	@Transient
	private Integer periodo;
	
	@Transient
	private String razonSocialPatronPrin;

	@Transient
	private String fechaRecepcionOficio;
	
	
	@Transient
	private String idTramite;
	
	
	@Transient
	private RespuestaFirmadoSimple respuestaObjetoFirmadoSimple;
	
	@Transient
	private String urlAcuseFirma;
	
	
	@Transient
	private Object firmaDigital;
	
	@Transient
	private FirmaElectronica firmaElectroResultado;



	public FirmaElectronica getFirmaElectroResultado() {
		return firmaElectroResultado;
	}

	public void setFirmaElectroResultado(FirmaElectronica firmaElectroResultado) {
		this.firmaElectroResultado = firmaElectroResultado;
	}

	public boolean isPersonaFisica() {
		return personaFisica;
	}

	public void setPersonaFisica(boolean personaFisica) {
		this.personaFisica = personaFisica;
	}

	@Transient
	private String motivoRechazo;

	@Transient
	private String idMotivoRechazo;

	
	@Transient
	private CgtCorreccion correccionGestion;

	@Transient
	private List correccionPatronesGestion;
	
	
	@Transient
	private CrcTramiteMensajes mensaje;
	
	public String getFolioInvitacion() {
		return folioInvitacion;
	}

	public void setFolioInvitacion(String folioInvitacion) {
		this.folioInvitacion = folioInvitacion;
	}

	public CgtCorreccion getCorreccionGestion() {
		return correccionGestion;
	}

	public String getUrlAcuseFirma() {
		return urlAcuseFirma;
	}

	public void setUrlAcuseFirma(String urlAcuseFirma) {
		this.urlAcuseFirma = urlAcuseFirma;
	}

	public void setCorreccionGestion(CgtCorreccion correccionGestion) {
		this.correccionGestion = correccionGestion;
	}

	public List getCorreccionPatronesGestion() {
		return correccionPatronesGestion;
	}

	public void setCorreccionPatronesGestion(List correccionPatronesGestion) {
		this.correccionPatronesGestion = correccionPatronesGestion;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getIdMotivoRechazo() {
		return idMotivoRechazo;
	}

	public void setIdMotivoRechazo(String idMotiboRechazo) {
		this.idMotivoRechazo = idMotiboRechazo;
	}

	public String getFechaRecepcionOficio() {
		return fechaRecepcionOficio;
	}

	public void setFechaRecepcionOficio(String fechaRecepcionOficio) {
		this.fechaRecepcionOficio = fechaRecepcionOficio;
	}

	public String getTipoObra() {
		return tipoObra;
	}

	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}

	public CrtPromocion getPromocion() {
		return promocion;
	}

	public void setPromocion(CrtPromocion promocion) {
		this.promocion = promocion;
	}

	public CrtInvitacion getInvitacion() {
		return invitacion;
	}

	public void setInvitacion(CrtInvitacion invitacion) {
		this.invitacion = invitacion;
	}

	public Hashtable getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(Hashtable domicilios) {
		this.domicilios = domicilios;
	}

	public String getNumeroObra() {
		return numeroObra;
	}

	public void setNumeroObra(String numeroObra) {
		this.numeroObra = numeroObra;
	}

	public List getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List periodos) {
		this.periodos = periodos;
	}

	public void setFechaPresentacion(String fechaPresentacion) {
		setFecFechaElacoracionCorreccion(Functions.stringToDate(fechaPresentacion));
	}

	public void setFechaInicial(String fechaInicial) {
		setFecFechaPeriodoIni(Functions.stringToDate(fechaInicial));
	}

	public void setFechaFinal(String fechaFinal) {
		setFecFechaPeriodoFin(Functions.stringToDate(fechaFinal));
	}

	public SatPatron getPatronPrincipal() {
		return patronPrincipal;
	}

	public void setPatronPrincipal(SatPatron patronPrincipal) {
		this.patronPrincipal = patronPrincipal;
	}

	public String getFechaInicial(){
		return Functions.dateToString(this.getFecFechaPeriodoIni());
	}

	public String getFechaFinal(){
		return Functions.dateToString(this.getFecFechaPeriodoFin());
	}
	
	public String getFechaPresentacion(){
		return Functions.dateToString(this.getFecFechaElacoracionCorreccion());
	}
	
	public String getPatron() {
		return patron;
	}
	public void setPatron(String patron) {
		this.patron = patron;
	}
	public List getPatrones() {
		return patrones;
	}
	public void setPatrones(List patrones) {
		this.patrones = patrones;
	}
	public SatPatron getPatronCorregir() {
		return patronCorregir;
	}

	public void setPatronCorregir(SatPatron patronAcorregir) {
		this.patronCorregir = patronAcorregir;
	}

	public String getRfcPatronCorregir() {
		return rfcPatronCorregir;
	}
	public void setRfcPatronCorregir(String rfcPatronCorregir) {
		this.rfcPatronCorregir = rfcPatronCorregir;
	}
	public String getCurpPatronCorregir() {
		return curpPatronCorregir;
	}
	public void setCurpPatronCorregir(String curpPatronCorregir) {
		this.curpPatronCorregir = curpPatronCorregir;
	}
	public String getRazonSocialPatronCorregir() {
		return razonSocialPatronCorregir;
	}
	public void setRazonSocialPatronCorregir(String razonSocialPatronCorregir) {
		this.razonSocialPatronCorregir = razonSocialPatronCorregir;
	}
	public String getCallePatronCorregir() {
		return callePatronCorregir;
	}
	public void setCallePatronCorregir(String callePatronCorregir) {
		this.callePatronCorregir = callePatronCorregir;
	}
	public String getNumExteriorPatronCorregir() {
		return numExteriorPatronCorregir;
	}
	public void setNumExteriorPatronCorregir(String numExteriorPatronCorregir) {
		this.numExteriorPatronCorregir = numExteriorPatronCorregir;
	}
	public String getNumInteriorPatronCorregir() {
		return numInteriorPatronCorregir;
	}
	public void setNumInteriorPatronCorregir(String numInteriorPatronCorregir) {
		this.numInteriorPatronCorregir = numInteriorPatronCorregir;
	}
	public String getColoniaPatronCorregir() {
		return coloniaPatronCorregir;
	}
	public void setColoniaPatronCorregir(String coloniaPatronCorregir) {
		this.coloniaPatronCorregir = coloniaPatronCorregir;
	}
	public String getClasePatronCorregir() {
		return clasePatronCorregir;
	}
	public void setClasePatronCorregir(String clasePatronCorregir) {
		this.clasePatronCorregir = clasePatronCorregir;
	}
	public String getFraccionPatronCorregir() {
		return fraccionPatronCorregir;
	}
	public void setFraccionPatronCorregir(String fraccionPatronCorregir) {
		this.fraccionPatronCorregir = fraccionPatronCorregir;
	}
	public String getPrimaPatronCorregir() {
		return primaPatronCorregir;
	}
	public void setPrimaPatronCorregir(String primaPatronCorregir) {
		this.primaPatronCorregir = primaPatronCorregir;
	}
	public String getPatronDom() {
		return patronDom;
	}
	public void setPatronDom(String patronDom) {
		this.patronDom = patronDom;
	}
	public String getCallePatronDom() {
		return callePatronDom;
	}
	public void setCallePatronDom(String callePatronDom) {
		this.callePatronDom = callePatronDom;
	}
	public String getNumExteriorPatronDom() {
		return numExteriorPatronDom;
	}
	public void setNumExteriorPatronDom(String numExteriorPatronDom) {
		this.numExteriorPatronDom = numExteriorPatronDom;
	}
	public String getNumInteriorPatronDom() {
		return numInteriorPatronDom;
	}
	public void setNumInteriorPatronDom(String numInteriorPatronDom) {
		this.numInteriorPatronDom = numInteriorPatronDom;
	}
	public String getColoniaPatronDom() {
		return coloniaPatronDom;
	}
	public void setColoniaPatronDom(String coloniaPatronDom) {
		this.coloniaPatronDom = coloniaPatronDom;
	}
	public String getTelefonoPatron() {
		return telefonoPatron;
	}

	public void setTelefonoPatron(String telefonoPatron) {
		this.telefonoPatron = telefonoPatron;
	}

	public String getEmailPatron() {
		return emailPatron;
	}

	public void setEmailPatron(String emailPatron) {
		this.emailPatron = emailPatron;
	}

	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNumeroTrabajadores() {
		return numeroTrabajadores;
	}
	public void setNumeroTrabajadores(String numeroTrabajadores) {
		this.numeroTrabajadores = numeroTrabajadores;
	}
	public String getActividad() {
		return actividad;
	}
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	
	public List<CrtAnexosolcorrpat> getLstAnexoSolicitudesCorr() {
		return lstAnexoSolicitudesCorr;
	}

	public void setLstAnexoSolicitudesCorr(List<CrtAnexosolcorrpat> lstAnexoSolicitudesCorr) {
		this.lstAnexoSolicitudesCorr = lstAnexoSolicitudesCorr;
	}
	
	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	

	public String getRazonSocialPatronPrin() {
		return razonSocialPatronPrin;
	}

	public void setRazonSocialPatronPrin(String razonSocialPatronPrin) {
		this.razonSocialPatronPrin = razonSocialPatronPrin;
	}
	
	/**
	 * Permite obtener si la solicitud fue realizada
	 * por uno o varios registros patronales.
	 * 
	 * @see this.SOLICITUD_VARIOS_RP
	 * @see this.SOLICITUD_UN_RP
	 * @author Marco Antonio Nieto Plett
	 * @return 1 si es de un registro patonal, 2 si es de varios
	 */
	public Integer getUnoVariosRp() {
			
				return unoVariosRp;
	}

	/**
	 * Permite ingresar el tipo de solicitud en caso
	 * de que sea de uno o varios registros patronales.
	 * @see this.SOLICITUD_VARIOS_RP
	 * @see this.SOLICITUD_UN_RP
	 * @author Marco Antonio Nieto Plett
	 * @param unoVariosRp
	 */
	public void setUnoVariosRp(Integer unoVariosRp) {
		this.unoVariosRp = unoVariosRp;
	}
	
	public String imprimeObjeto(){
		return new StringBuffer().append("CrtSolicitudcorr{")
								 .append("nuFolio:").append(this.getNuFolio()).append(";\n")
								 .append("patronCorregir.registroPatronalSD:").append(this.getPatronCorregir().getRegistroPatronalSD()).append(";\n")
								 .append("patronCorregir.razonSocial:").append(this.getPatronCorregir().getRazonSocial()).append(";\n")
								 .append("patronCorregir.domicilioCompleto:").append(this.getPatronCorregir().getDomicilioCompleto()).append(";\n")
								 .append("fechaInicial:").append(this.getFechaInicial()).append(";\n")
								 .append("fechaFinal:").append(this.getFechaFinal()).append(";\n")
								 .append("}")
								 .toString();
	}
	
	/**
	 * Constructor para Auditor
	 * @author Enrique Duran Jimenez
	 * @since 25/05/2012
	 */
	public CrtSolicitudcorr(Integer cveSolicitudCorr, String nuFolio,
			Long cvePatron, Date fecFechaPeriodoIni, Date fecFechaPeriodoFin,
			Date fecFechaElacoracionCorreccion) {
		
		super( cveSolicitudCorr, nuFolio,cvePatron, fecFechaPeriodoIni, fecFechaPeriodoFin, fecFechaElacoracionCorreccion);	
	}
	
	public CrtSolicitudcorr() {
		super();
	}

	public SatPatron getPatronObra() {
		return patronObra;
	}

	public void setPatronObra(SatPatron patronObra) {
		this.patronObra = patronObra;
	}

	public SatObra getSaticObra() {
		return saticObra;
	}

	public void setSaticObra(SatObra saticObra) {
		this.saticObra = saticObra;
	}

	/**
	 * @return the internet
	 */
	public String getInternet() {
		return internet;
	}

	/**
	 * @param internet the internet to set
	 */
	public void setInternet(String internet) {
		this.internet = internet;
	}

	public List<CrtInvitacionRP> getLsPatronesInscInvitacion() {
		return lsPatronesInscInvitacion;
	}

	public void setLsPatronesInscInvitacion(List<CrtInvitacionRP> lsPatronesInscInvitacion) {
		this.lsPatronesInscInvitacion = lsPatronesInscInvitacion;
	}


	public CrcTramiteMensajes getMensaje() {
		return mensaje;
	}

	public void setMensaje(CrcTramiteMensajes mensaje) {
		this.mensaje = mensaje;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}

	public boolean isRecuperaFiscal() {
		return recuperaFiscal;
	}

	public void setRecuperaFiscal(boolean recuperaFiscal) {
		this.recuperaFiscal = recuperaFiscal;
	}



	public Object getFirmaDigital() {
		return firmaDigital;
	}

	public void setFirmaDigital(Object firmaDigital) {
		this.firmaDigital = firmaDigital;
	}

	public RespuestaFirmadoSimple getRespuestaObjetoFirmadoSimple() {
		return respuestaObjetoFirmadoSimple;
	}

	public void setRespuestaObjetoFirmadoSimple(
			RespuestaFirmadoSimple respuestaObjetoFirmadoSimple) {
		this.respuestaObjetoFirmadoSimple = respuestaObjetoFirmadoSimple;
	}

	
	
	





	

	
}
