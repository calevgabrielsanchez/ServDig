package mx.gob.imss.ctirss.correccion.presentacion.service.ejb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrcStatus;
import mx.gob.imss.ctirss.correccion.bean.CopPagadas;
import mx.gob.imss.ctirss.correccion.bean.DataTableSolCorreccion;
import mx.gob.imss.ctirss.correccion.bean.PresentacionCorreccionVO;
import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SatObra;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CorreccionSeguimientoGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.DetalleCOPSeguimientoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.EstudioSolicitudCorreccionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RecepcionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.ResumenSeguimientoVO;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.ejb.DeteccionServiceRemote;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.framework.exception.promocion.NotFoundRegistroPatronalException;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness.NumberTypes;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness.OperationTypes;
import mx.gob.imss.ctirss.correccion.invitacion.service.ejb.InvitacionServiceRemote;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuarioFuncionario;
import mx.gob.imss.ctirss.correccion.model.CgcCatMotivoRechazo;
import mx.gob.imss.ctirss.correccion.model.CrcStatus;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtCoppagada;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.model.CrtProrroga;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.ErrorValidation;
import mx.gob.imss.ctirss.correccion.model.PresentacionCorreccionModel;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.AnexoSolicitudCorreccionDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.CopPagadaDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.MotivoRechazoDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.PresentacionCorreccionDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.ProrrogaCorreccionDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.SatPatronDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.SolicitudCorreccionDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.SolicitudCorreccionStatusDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.TipoCorreccionDAO;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.PromocionServiceRemote;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao.SelectorDAO;
import mx.gob.imss.ctirss.correccion.service.ejb.ObraServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.PatronesServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.PatronDaoLocal;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.dao.SolicitudCorreccionDAOLocal;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.ejb.dao.DomiciliosInegiDAOLocal;


import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "presentacionCorreccionService", mappedName = "presentacionCorreccionService")
public class PresentacionCorreccionServiceBean extends AbstractService	implements PresentacionCorreccionServiceControllerRemote {
	/**
	 * 
	 */
	private final static Logger logger = Logger.getLogger(PresentacionCorreccionServiceBean.class);
	private static final String RECHAZADA_SIN_MOTIVO = "Esta solicitud esta rechazada pero no tiene un motivo...";

	private DgDomicilioGeografico domFiscal = new DgDomicilioGeografico();
	
	
	@EJB SolicitudCorreccionDAO solCorrDao;
	@EJB SatPatronDAO satPatronDao;
	@EJB MotivoRechazoDAO motivoRechazoDao;
	@EJB TipoCorreccionDAO tipoCorreccionDao;
	@EJB SolicitudCorreccionStatusDAO statusDao;
	@EJB AnexoSolicitudCorreccionDAO anexoSolCorrDao;
	@EJB ProrrogaCorreccionDAO prorrogaDao;
	@EJB DomiciliosInegiDAOLocal<DgDomicilioGeografico> domicilioDao;
	@EJB PresentacionCorreccionDAO presentacionCorreccionDao;
	@EJB CopPagadaDAO copPagadaDao;
	@EJB SelectorDAO selectorDao;

	@EJB PatronesServiceRemote patronesService;
	@EJB DeteccionServiceRemote<CrtDeteccion> deteccionServiceBean;
	
	@EJB PatronDaoLocal<?> daoPatron;
	@EJB SolicitudCorreccionDAOLocal<?> daoSolicitudCorreccion;
	
	@EJB ObraServiceRemote obraService;

	@EJB InvitacionServiceRemote<?> invitacionServiceBean;
	@EJB PromocionServiceRemote<?> promocionServiceBean;

	public List<DataTableSolCorreccion> buscarFoliosSolicitudCorreccionPorRegPatronal(
			String regPatronal, String folioCorreccion) {
		List<SatPatron> patrones = satPatronDao.findByCriteria(Restrictions.eq("registroPatronal", regPatronal));
		List<DataTableSolCorreccion> datos = new ArrayList<DataTableSolCorreccion>();
		if (patrones != null && patrones.size() > 0) {
			SatPatron patron = patrones.get(0);
			List<CrtSolicitudcorr> folios = solCorrDao.findByCriteria(Restrictions.eq("cvePatron", patron.getCvePK()),Restrictions.eq("nuFolio", folioCorreccion));
			datos = fillDataTableSolCorr(folios);
		}

		return datos;
	}

	public List<DataTableSolCorreccion> buscarFolioSolicitudCorrecion(
			String folioSolCorreccion) {
		logger.debug("*luis* Llegamos al mentado Bean de servicio .......");

		List<CrtSolicitudcorr> solicitudes = solCorrDao.findByCriteria(Restrictions.eq("nuFolio", folioSolCorreccion));
		List<DataTableSolCorreccion> datos = null;
		if (solicitudes != null
				&& solicitudes.size() > 0
				&& !presentacionCorreccionDao.isFolioCorreccionPresentado(solicitudes.get(0).getCveSolicitudCorr())) {
			datos = fillDataTableSolCorr(solicitudes);
			return datos;
		} else {
			return new ArrayList<DataTableSolCorreccion>();
		}
	}

	
	public PresentacionCorreccionModel buscarFolioDeCorreccion(Integer solicitudCorreccion) {
		PresentacionCorreccionModel modelo = new PresentacionCorreccionModel();
		CrtSolicitudcorr solicitudCorr = solCorrDao.findById(solicitudCorreccion, false);
		modelo.setCveSolicitudCorreccion(solicitudCorreccion);
		
		List<CrtProrroga> prorroga = prorrogaDao.findByCriteria(Restrictions.eq("cveSolicitudcorr", new BigDecimal(solicitudCorr.getCveSolicitudCorr())));
		
		
		modelo.setFechaEjercicioInicial(ConstantesBusiness.dateToStringFormat(solicitudCorr.getFecFechaPeriodoIni(), ConstantesBusiness.DD_MM_YYYY));
		modelo.setFechaEjercicioFinal(ConstantesBusiness.dateToStringFormat(solicitudCorr.getFecFechaPeriodoFin(), ConstantesBusiness.DD_MM_YYYY));
		modelo.setTipoDeCorreccion(solicitudCorr.getCveTipoCorreccion());
		modelo.setNumeroTrabajadores(solicitudCorr.getNumTrabajadores());
		modelo.setIdTipoDeSolicitud(solicitudCorr.getIdTipoSolicitud());
		modelo.setCveNroRegObra(solicitudCorr.getCveNumeroRegObra()!=null ? solicitudCorr.getCveNumeroRegObra().setScale(0).toString():null);
		modelo.setFechaAutorizacionCorreccionEspontanea(ConstantesBusiness.dateToStringFormat(solicitudCorr.getFecFechaAutorizacionCorreccion(), ConstantesBusiness.DD_MM_YYYY));
		
		if (solicitudCorr.getCveTipoCorreccion().intValue() == ConstantesBusiness.SOLICITUD_CORRECCION_INVITACION) {
			CrtInvitacion invitacion = presentacionCorreccionDao.buscarInvitacionDeSolicitudCorreccion(solicitudCorreccion);
			modelo.setFechaAceptacionInvitacionCorreccion(ConstantesBusiness.dateToStringFormat(invitacion.getFecAtencion(), ConstantesBusiness.DD_MM_YYYY));
		}
		if (prorroga != null && prorroga.size() > 0) {
			modelo.setFechaProrroga(ConstantesBusiness.dateToStringFormat(prorroga.get(0).getFecElaborasolpro(), ConstantesBusiness.DD_MM_YYYY));
		}
		
		/** Patron principal Domicilio Fiscal*/
		List<CrtAnexosolcorrpat> anexoPatPrincipal = anexoSolCorrDao.findByCriteria(Restrictions.eq("cveSolicitudCorr", solicitudCorreccion), 
																					Restrictions.eq("tipoPatron", CrtAnexosolcorrpat.TIPO_REGISTRO_RP_FISCAL));
		
		/*Validamos que el RP cuente con el formato requerido, todos deben de contar con este registro "F" */
		if(anexoPatPrincipal==null || anexoPatPrincipal.isEmpty()){
			StringBuffer msg = new StringBuffer();
			msg.append("El Registro Patronal principal dentro de la base de datos ")
			.append("NO cuenta con el formato correcto, al menos debe de existir un Registro Patronal ")
			.append("con F en el campo IN_TP_PATRON ");
			
			throw new EJBException(msg.toString());
		}else{
			
			/** Domicilio Fiscal */
			CrtAnexosolcorrpat patPrincipal = anexoPatPrincipal.get(0);
			patPrincipal.setCopPagadas(obtenerCopPagadasPorId(patPrincipal.getCveAnexoSolicitudCorrPat()));
			SatPatron patron = satPatronDao.findById(patPrincipal.getCvePatron(), false);
			modelo.setRazonSocial(patPrincipal.getTxRazonSocial());
			modelo.setCurp(patPrincipal.getTxCurp());
			modelo.setRfc(patPrincipal.getTxRfc());
			modelo.setNumeroRegistroPatronal(patron.getRegistroPatronal());
			modelo.setFolioCorreccion(solicitudCorr.getNuFolio());
			modelo.setDigitoVerificador((short) patronesService.generaDigitoVerificador(patron.getRegistroPatronal().substring(0, patron.getRegistroPatronal().length()-1)));
			modelo.setTelefono(patPrincipal.getTxTelefono());
			modelo.setEmail(patPrincipal.getTxEmail());
			
			domFiscal.setDomicilioId(patPrincipal.getCveDomicilio());			
			domFiscal = domicilioDao.consultaPorClave(domFiscal);
			
			
			
			//Se recupera de BDTU
			domFiscal=domicilioDao.getDomicilioBDTU(domFiscal);

			
			patPrincipal.setDomicilioGeografico(domFiscal);
			patPrincipal.setDirreccionInegi(domFiscal);
			
			modelo.setRegistroPatronalFiscal(patron.getRegistroPatronal());
			
			
			modelo.setCalle(patPrincipal.getCalle());
			if(domFiscal!=null)
			modelo.setIdDomicilioFiscal(domFiscal.getDomicilioId());
			modelo.setNumExterior(((patPrincipal.getNumExterior())!=null ? patPrincipal.getNumExterior() : "")
								  +" "+
								  ((patPrincipal.getNumExteriorAlfa())!=null ? patPrincipal.getNumExteriorAlfa() : ""));
			
			modelo.setNumInterior(((patPrincipal.getNumInterior())!=null ? patPrincipal.getNumInterior() : "")
					  +" "+
					  ((patPrincipal.getNumInteriorAlfa())!=null ? patPrincipal.getNumInteriorAlfa() : ""));
			
			
			modelo.setColonia(patPrincipal.getColonia());
			modelo.setMunicipio(patPrincipal.getMunicipio());
			modelo.setLocalidad(patPrincipal.getLocalidad());
			modelo.setEntidadFederativa(patPrincipal.getEntidadFederativa());
			modelo.setCodigoPostal(patPrincipal.getCodigoPostal());
						
			
			/** Obra en caso de que exista */
			
			if(solicitudCorr.getIdTipoSolicitud()!=null && solicitudCorr.getIdTipoSolicitud().intValue()==CrtSolicitudcorr.SOLICITUD_TIPO_CONSTRUCCION){
				
				if(solicitudCorr!=null && solicitudCorr.getCveNumeroRegObra()!=null){
					
					//La obra cuenta con un domicilio normado por SATIC
					
					SatObra saticObra = this.obraService.validaObra(solicitudCorr.getCveNumeroRegObra().toString());
					
					if(saticObra!=null){
						modelo.setCalleObra(saticObra.getUbicacion().getCalle());
						modelo.setNumExteriorObra(saticObra.getUbicacion().getNumeroExterior());
						modelo.setNumInteriorObra(saticObra.getUbicacion().getNumeroInterior());
						modelo.setColoniaObra(saticObra.getUbicacion().getColonia());
						modelo.setMunicipioObra(saticObra.getUbicacion().getMunicipio().getNombre());
						modelo.setLocalidadObra(saticObra.getUbicacion().getMunicipio().getNombre());
						modelo.setEntidadFederativaObra(saticObra.getUbicacion().getMunicipio().getSacEntidadFederativa().getNomNombre());
						modelo.setCodigoPostalObra(saticObra.getUbicacion().getCodigoPostal());
					}
					
				}else{
					
					//La obra cuenta con un domicilio normado por INEGI
					List<CrtAnexosolcorrpat> registroPatronalObra = anexoSolCorrDao.findByCriteria(Restrictions.eq("cveSolicitudCorr", solicitudCorreccion), 
							Restrictions.eq("tipoPatron", CrtAnexosolcorrpat.TIPO_REGISTRO_RP_OBRA));
					
					if(registroPatronalObra!=null && !registroPatronalObra.isEmpty()){
						
						CrtAnexosolcorrpat patronObra = registroPatronalObra.get(0);
						DgDomicilioGeografico domObra = new DgDomicilioGeografico();
						
						domObra.setDomicilioId(patronObra.getCveDomicilio());
						domObra = domicilioDao.consultaPorClave(domObra);
						
						//Se recupera de BDTU
						domicilioDao.getDomicilioBDTU(domObra);
						
						patronObra.setDomicilioGeografico(domObra);
						patronObra.setDirreccionInegi(domObra);
						
						modelo.setIdDomicilioObra(domObra.getDomicilioId());
						modelo.setCalleObra(patronObra.getCalle());
						modelo.setNumExteriorObra(patronObra.getNumExterior()+" "+patronObra.getNumExteriorAlfa());
						modelo.setNumInteriorObra(patronObra.getNumInterior()+" "+patronObra.getNumInteriorAlfa());
						modelo.setColoniaObra(patronObra.getColonia());
						modelo.setMunicipioObra(patronObra.getMunicipio());
						modelo.setLocalidadObra(patronObra.getLocalidad());
						modelo.setEntidadFederativaObra(patronObra.getEntidadFederativa());
						modelo.setCodigoPostalObra(patronObra.getCodigoPostal());
						
					}
				}
			}
			
			/*Registro Patronal del Centro de Trabajo*/
			
			List<CrtAnexosolcorrpat> registroPatronalCentroTrabajo = anexoSolCorrDao.findByCriteria(Restrictions.eq("cveSolicitudCorr", solicitudCorreccion), 
					Restrictions.eq("tipoPatron", CrtAnexosolcorrpat.TIPO_REGISTRO_RP_CENTRO_TRABAJO));
			System.out.println("PAso consulta");
			if(registroPatronalCentroTrabajo==null || registroPatronalCentroTrabajo.isEmpty()){
				StringBuffer msg = new StringBuffer();
				msg.append("El Registro Patronal del centro de trabajo dentro de la base de datos ")
				.append("NO cuenta con el formato correcto, al menos debe de existir un Registro Patronal ")
				.append("con C en el campo IN_TP_PATRON ");
				
				throw new EJBException(msg.toString());
			}
			
			CrtAnexosolcorrpat registroCentroTrabajo = registroPatronalCentroTrabajo.get(0);
			SatPatron patronCentroTrabajo = satPatronDao.findById(registroCentroTrabajo.getCvePatron(), false);
			
			DgDomicilioGeografico domCentroTrabajo = new DgDomicilioGeografico();
			
			domCentroTrabajo.setDomicilioId(registroCentroTrabajo.getCveDomicilio());
			domCentroTrabajo = domicilioDao.consultaPorClave(domCentroTrabajo);
			
			//Se recupera de BDTU
			domicilioDao.getDomicilioBDTU(domCentroTrabajo);
			
			registroCentroTrabajo.setDomicilioGeografico(domCentroTrabajo);
			registroCentroTrabajo.setDirreccionInegi(domCentroTrabajo);
			
			modelo.setRegistroPatronalCentroTrabajo(patronCentroTrabajo.getRegistroPatronal());
			modelo.setCalleCentroTrabajo(registroCentroTrabajo.getCalle());
			if(domCentroTrabajo!=null)
			modelo.setIdDomicilioCentroTrabajo(domCentroTrabajo.getDomicilioId());
			modelo.setNumExteriorCentroTrabajo(((registroCentroTrabajo.getNumExterior())!=null ? registroCentroTrabajo.getNumExterior() : "")
					  +" "+
					  ((registroCentroTrabajo.getNumExteriorAlfa())!=null ? registroCentroTrabajo.getNumExteriorAlfa() : ""));

			modelo.setNumInteriorCentroTrabajo(((registroCentroTrabajo.getNumInterior())!=null ? registroCentroTrabajo.getNumInterior() : "")
					+" "+
					((registroCentroTrabajo.getNumInteriorAlfa())!=null ? registroCentroTrabajo.getNumInteriorAlfa() : ""));

			
			modelo.setColoniaCentroTrabajo(registroCentroTrabajo.getColonia());
			modelo.setMunicipioCentroTrabajo(registroCentroTrabajo.getMunicipio());
			modelo.setLocalidadCentroTrabajo(registroCentroTrabajo.getLocalidad());
			modelo.setEntidadFederativaCentroTrabajo(registroCentroTrabajo.getEntidadFederativa());
			modelo.setCodigoPostalCentroTrabajo(registroCentroTrabajo.getCodigoPostal());
			
			modelo.setActividadCentroTrabajo(registroCentroTrabajo.getTxActividad());
			modelo.setFraccionCentroTrabajo(registroCentroTrabajo.getTxFraccion());
			modelo.setClaseCentroTrabajo(registroCentroTrabajo.getTxClase());
			modelo.setPrimaCentroTrabajo(registroCentroTrabajo.getTxPrima());
			

		}
		
		return modelo;
	}

	/**
	 * 
	 */
	public CrtPresentacorr presentarCorreccion(CrtPresentacorr solicitudCorreccion, UserSession user) {
		CrtSolicitudcorr solicitud = solCorrDao.findConcreteByCriteria(Restrictions.eq("nuFolio",solicitudCorreccion.getFolioCorreccion()));

		if(solicitud.getIdFormaPresenta()!=null && solicitud.getIdFormaPresenta().intValue()==ConstantesBusiness.USUARIO_INTERNET && user.getCveRol()!=ConstantesBusiness.ROL_USER_INTERNET){
			solicitudCorreccion.setFolioDesdeInternet(true);
			return solicitudCorreccion;
		}
		
		CrtPresentacorr presentacionCorreccion = new CrtPresentacorr();

		presentacionCorreccion.setCveSolicitudcorr(solicitud.getCveSolicitudCorr());
		presentacionCorreccion.setFecElaborapre(new Date());
		
		if (solicitud.getCveTipoCorreccion().intValue() == ConstantesBusiness.SOLICITUD_CORRECCION_ESPONTANEA) {
			presentacionCorreccion.setFecAutorizacion(solicitudCorreccion.getFecAutorizacion());
		}
		presentacionCorreccion.setFecAceptacion(new Date());
		presentacionCorreccion.setObservaciones(solicitudCorreccion.getObservaciones());
		
		presentacionCorreccion.setImpCop(solicitudCorreccion.getImpCop());
		presentacionCorreccion.setImpCopact(solicitudCorreccion.getImpCopact());
		presentacionCorreccion.setImpCoprec(solicitudCorreccion.getImpCoprec());
		presentacionCorreccion.setImpCoptot(solicitudCorreccion.getImpCoptot());
		presentacionCorreccion.setImpRcv(solicitudCorreccion.getImpRcv());
		presentacionCorreccion.setImpRcvact(solicitudCorreccion.getImpRcvact());
		presentacionCorreccion.setImpRcvrec(solicitudCorreccion.getImpRcvrec());
		presentacionCorreccion.setImpRcvtot(solicitudCorreccion.getImpRcvtot());
		presentacionCorreccion.setNuTrabreg(solicitudCorreccion.getNuTrabreg());
		presentacionCorreccion.setRefLugar(solicitudCorreccion.getRefLugar());
		presentacionCorreccion.setNuComprobantepago(solicitudCorreccion.getNuComprobantepago());
		presentacionCorreccion.setNuCompromovafil(solicitudCorreccion.getNuCompromovafil());
		presentacionCorreccion.setNuDoctosustento(solicitudCorreccion.getNuDoctosustento());
		presentacionCorreccion.setRepresentanteLegalElaboro(solicitudCorreccion.getRepresentanteLegalElaboro());
		
		presentacionCorreccion.setFecFechareg(new Date());
		presentacionCorreccion.setCveUsuario(user.getCurpUsuario()!=null ? user.getCurpUsuario().toString():null);

		presentacionCorreccion = presentacionCorreccionDao.makePersistent(presentacionCorreccion);
		
		if(presentacionCorreccion!=null && presentacionCorreccion.getCvePresentacorr()!=null && presentacionCorreccion.getCvePresentacorr()>0){
			solicitud.setCveStatus(CrtSolicitudcorr.SOLICITUD_PRESENTADA);
			daoSolicitudCorreccion.save(solicitud);
		}
			
		
		return presentacionCorreccion;
	}

	/**
	 * 
	 */	
	
	public PresentacionCorreccionVO obtenerPresentacionCorreccion(Integer solicitudCorreccion) {
		PresentacionCorreccionVO preCorr = new PresentacionCorreccionVO();
		List<CopPagadas> cuotas = new ArrayList<CopPagadas>();
		CrtSolicitudcorr solicitudCorr = solCorrDao.findById(solicitudCorreccion, false);
		CrtPresentacorr presentacion = presentacionCorreccionDao.findConcreteByCriteria(Restrictions.eq("cveSolicitudcorr", solicitudCorreccion));

		List<CrtProrroga> prorroga = prorrogaDao.findByCriteria(Restrictions.eq("cveSolicitudcorr", new BigDecimal(solicitudCorr.getCveSolicitudCorr())));

		//List<CrtAnexosolcorrpat> lstAnexo = daoSolicitudCorreccion.consultarAnexoSolicitudesReport(solicitudCorr.getCveSolicitudCorr());
		List<CrtAnexosolcorrpat> lstAnexo = daoSolicitudCorreccion.consultarAnexoSolicitudesReport(presentacion.getCveSolicitudcorr());
		
		if(lstAnexo != null && lstAnexo.size() > 0){
			for (Iterator<CrtAnexosolcorrpat> iterator = lstAnexo.iterator(); iterator.hasNext();) {
				CrtAnexosolcorrpat crtAnexosolcorrpat = (CrtAnexosolcorrpat) iterator.next();
				if(crtAnexosolcorrpat.getTipoPatron().equals("O")){
					DgDomicilioGeografico domicilioObra = new DgDomicilioGeografico();
					domicilioObra.setDomicilioId(crtAnexosolcorrpat.getCveDomicilio());
					domicilioObra = domicilioDao.consultaPorClave(domicilioObra);
					
					//Se recupera de BDTU
					domicilioObra=domicilioDao.getDomicilioBDTU(domicilioObra);
					
					if(domicilioObra != null){
					
						preCorr.setCalleObra(domicilioObra.getNomvial());
						preCorr.setNumeroExtObra(ConstantesBusiness.numberToString(domicilioObra.getNumextnum(), NumberTypes.INTEGER));
						preCorr.setNumeroIntObra(ConstantesBusiness.numberToString(domicilioObra.getNumintnum(), NumberTypes.INTEGER));
						preCorr.setLocalidadObra(domicilioObra.getDgCatLocalidad().getNomLoc());
						preCorr.setColoniaObra(domicilioObra.getDgCatLocalidad().getNomLoc());
						preCorr.setMunicipioObra(domicilioObra.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
						preCorr.setEntidadFederativaObra(domicilioObra.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
						preCorr.setCodigoPostalObra(domicilioObra.getDgCodigosPostales().getId().getCodigo());
						preCorr.setCorreoElectronico(crtAnexosolcorrpat.getTxEmail());
						preCorr.setTelefonoDomicilio(crtAnexosolcorrpat.getTxTelefono());
					}
					
				}
				if(crtAnexosolcorrpat.getTipoPatron().equals("F")){

						preCorr.setCalle(domFiscal.getNomvial());
						
						preCorr.setNumeroExt(ConstantesBusiness.numberToString(domFiscal.getNumextnum(), NumberTypes.INTEGER));
						preCorr.setNumeroInt(ConstantesBusiness.numberToString(domFiscal.getNumintnum(), NumberTypes.INTEGER));
						
						preCorr.setLocalidad(domFiscal.getDgAsentamiento().getDgCatLocalidad().getNomLoc());
						preCorr.setColonia(domFiscal.getDgAsentamiento().getNomAsen());
						preCorr.setMunicipio(domFiscal.getDgAsentamiento().getDgCatLocalidad().getDgCatMunicipio().getNomMun());
						
						preCorr.setEntidadFederativa(domFiscal.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
						preCorr.setCodigoPostal(domFiscal.getDgCodigosPostales().getId().getCodigo()); 
						preCorr.setCorreoElectronico(crtAnexosolcorrpat.getTxEmail());
						preCorr.setTelefonoDomicilio(crtAnexosolcorrpat.getTxTelefono());
						
				}if(crtAnexosolcorrpat.getCvePatronPr() != null){
					SatPatron patron = daoPatron.getById(crtAnexosolcorrpat.getCvePatronPr());
					preCorr.setNombrePatron(crtAnexosolcorrpat.getTxRazonSocial());
					preCorr.setCurp(crtAnexosolcorrpat.getTxCurp());
					preCorr.setRfc(crtAnexosolcorrpat.getTxRfc());
					preCorr.setRegistroPatron(patron.getRegistroPatronal());
					preCorr.setFolioSolicitud(solicitudCorr.getNuFolio());
					preCorr.setDigitoVerificador(ConstantesBusiness.numberToString(patronesService.generaDigitoVerificador(patron.getRegistroPatronal()), NumberTypes.INTEGER));
					preCorr.setCorreoElectronico(crtAnexosolcorrpat.getTxEmail());
					preCorr.setTelefonoDomicilio(crtAnexosolcorrpat.getTxTelefono());
//					preCorr.setCorreoElectronico(crtAnexosolcorrpat.getTxEmail());
				}
			}
		}
			

		preCorr.setFechaInicialPeriodo(ConstantesBusiness.dateToStringFormat(solicitudCorr.getFecFechaPeriodoIni(), ConstantesBusiness.DD_MM_YYYY));
		preCorr.setFechaFinalPeriodo(ConstantesBusiness.dateToStringFormat(solicitudCorr.getFecFechaPeriodoFin(), ConstantesBusiness.DD_MM_YYYY));
		preCorr.setTipoCorreccion(ConstantesBusiness.numberToString(solicitudCorr.getCveTipoCorreccion(), NumberTypes.INTEGER));
		preCorr.setNumTrabajadoresRegularizadosTotal(ConstantesBusiness.numberToString(presentacion.getNuTrabreg(), NumberTypes.INTEGER));
		preCorr.setRepresentanteLegal(presentacion.getRepresentanteLegalElaboro());

		if (solicitudCorr.getCveTipoCorreccion().intValue() == ConstantesBusiness.SOLICITUD_CORRECCION_ESPONTANEA) {
			preCorr.setImagenRadioBtnTCEspontanea("radiobutton-checked-hi.jpg");
			preCorr.setImagenRadioBtnTCInvitacion("radiobutton-unchecked-hi.jpg");
			preCorr.setFechaAutorizacionEspontanea(ConstantesBusiness.dateToStringFormat(presentacion.getFecAutorizacion(), ConstantesBusiness.DD_MM_YYYY));
		} else if (solicitudCorr.getCveTipoCorreccion().intValue() == ConstantesBusiness.SOLICITUD_CORRECCION_INVITACION) {
			preCorr.setImagenRadioBtnTCInvitacion("radiobutton-checked-hi.jpg");
			preCorr.setImagenRadioBtnTCEspontanea("radiobutton-unchecked-hi.jpg");
			CrtInvitacion invitacion = presentacionCorreccionDao.buscarInvitacionDeSolicitudCorreccion(solicitudCorreccion);
			preCorr.setFechaAceptacionInvitacion(ConstantesBusiness.dateToStringFormat(invitacion.getFecAtencion(), ConstantesBusiness.DD_MM_YYYY));
		} else {
			preCorr.setImagenRadioBtnTCInvitacion("radiobutton-unchecked-hi.jpg");
			preCorr.setImagenRadioBtnTCEspontanea("radiobutton-unchecked-hi.jpg");
		}

		if (presentacion.getNuComprobantepago().intValue() == 1) {
			preCorr.setImagenRadioBtnDocComprobantePago("radiobutton-checked-hi.jpg");
		} else {
			preCorr.setImagenRadioBtnDocComprobantePago("radiobutton-unchecked-hi.jpg");
		}

		if (presentacion.getNuCompromovafil().intValue() == 1) {
			preCorr.setImagenRadioBtnDocComprobantePresentacionAvisos("radiobutton-checked-hi.jpg");
		} else {
			preCorr.setImagenRadioBtnDocComprobantePresentacionAvisos("radiobutton-unchecked-hi.jpg");
		}

		if (presentacion.getNuDoctosustento().intValue() == 1) {
			preCorr.setImagenRadioBtnDocSustentaCorreccion("radiobutton-checked-hi.jpg");
		} else {
			preCorr.setImagenRadioBtnDocSustentaCorreccion("radiobutton-unchecked-hi.jpg");
		}

		preCorr.setObservaciones(presentacion.getObservaciones());
		preCorr.setLugarElaboracion(presentacion.getRefLugar());
		preCorr.setFechaElaboracionPresentacionCorr(ConstantesBusiness.dateToStringFormat(presentacion.getFecElaborapre(), ConstantesBusiness.DD_MM_YYYY));

		if (prorroga != null && prorroga.size() > 0) {
			preCorr.setFechaProrroga(ConstantesBusiness.dateToStringFormat(prorroga.get(0).getFecElaborasolpro(), ConstantesBusiness.DD_MM_YYYY));
		}
		CopPagadas cop = new CopPagadas();
		for (CrtAnexosolcorrpat anxo : lstAnexo) {
			
			DgDomicilioGeografico dom = new DgDomicilioGeografico();
			if(anxo.getTipoPatron().equals("C")){
				dom.setDomicilioId(anxo.getCveDomicilio());
				DgDomicilioGeografico domConsulta = domicilioDao.consultaPorClave(dom);
				
				//Se recupera de BDTU
				domicilioDao.getDomicilioBDTU(domConsulta);
				
//				cop.setCalle(domConsulta.getDescripc());
				cop.setCalle(domConsulta.getNomvial());
				cop.setCodigoPostal(domConsulta.getDgCodigosPostales().getId().getCodigo());
				cop.setColonia(domConsulta.getNomvial());
				cop.setNumExt(ConstantesBusiness.numberToString(domConsulta.getNumextnum(), NumberTypes.INTEGER));
				cop.setNumInt(ConstantesBusiness.numberToString(domConsulta.getNumintnum(), NumberTypes.INTEGER));
				cop.setMunicipio(domConsulta.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
				cop.setLocalidad(domConsulta.getDgCatLocalidad().getNomLoc());
				cop.setEntidadFederativa(domConsulta.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
			}
			
			if(anxo != null && anxo.getCvePatron() != null){
				SatPatron patronAnexo = daoPatron.getById(anxo.getCvePatron());
				if(patronAnexo != null){
					cop.setRegistroPatronal(patronAnexo.getRegistroPatronal().substring(0,10));
					cop.setDigitoVerificador(String.valueOf(patronesService.generaDigitoVerificador(patronAnexo.getRegistroPatronal().substring(0,10))));
				}
				
			}
			

//			CrtCoppagada cuota = obtenerCopPagadasPorId(anxo.getCveAnexoSolicitudCorrPat());
//			if(cuota != null){				
//				cop.setCuotasImss(ConstantesBusiness.numberToString(cuota.getImpCOP(), NumberTypes.BIGDECIMAL));
//				cop.setCuotasImssActualizacion(ConstantesBusiness.numberToString(cuota.getImpCOPAct(), NumberTypes.BIGDECIMAL));
//				cop.setCuotasImssRecargos(ConstantesBusiness.numberToString(cuota.getImpCOPRec(), NumberTypes.BIGDECIMAL));
//				cop.setCuotasImssTotal(ConstantesBusiness.numberToString(cuota.getImpCOPTot(), NumberTypes.BIGDECIMAL));
//				cop.setRcv(ConstantesBusiness.numberToString(cuota.getImpRCV(), NumberTypes.BIGDECIMAL));
//				cop.setRcvActualizacion(ConstantesBusiness.numberToString(cuota.getImpRCVAct(), NumberTypes.BIGDECIMAL));
//				cop.setRcvRecargos(ConstantesBusiness.numberToString(cuota.getImpRCVRec(), NumberTypes.BIGDECIMAL));
//				cop.setRcvTotal(ConstantesBusiness.numberToString(cuota.getImpRCVTot(), NumberTypes.BIGDECIMAL));
//	 
//				cop.setTotalCuotasRcv(ConstantesBusiness.numberToString(OperationTypes.SUMA, cuota.getImpCOP(), cuota.getImpRCV()));
//				cop.setTotalActualizacion(ConstantesBusiness.numberToString(OperationTypes.SUMA, cuota.getImpCOPAct(), cuota.getImpRCVAct()));
//				cop.setTotalRecargos(ConstantesBusiness.numberToString(OperationTypes.SUMA, cuota.getImpCOPRec(), cuota.getImpRCVRec()));
//				cop.setGranTotal(ConstantesBusiness.numberToString(OperationTypes.SUMA, cuota.getImpCOPTot(), cuota.getImpRCVTot()));
//				cop.setNumTrabajadoresRegularizados(ConstantesBusiness.numberToString(cuota.getNuTrabRegu(), NumberTypes.INTEGER));
//				cuotas.add(cop);
//			}
			
			
			
			List<CrtCoppagada> listaCops=obtenerListaCopPagadasPorId(anxo.getCveAnexoSolicitudCorrPat());
			if(listaCops!=null){
			for(CrtCoppagada cuota:listaCops){
				cop.setCuotasImss(ConstantesBusiness.numberToString(cuota.getImpCOP().add(new BigDecimal(cop.getCuotasImss()!=null? cop.getCuotasImss():"0")), NumberTypes.BIGDECIMAL));
				cop.setCuotasImssActualizacion(ConstantesBusiness.numberToString(cuota.getImpCOPAct().add(new BigDecimal(cop.getCuotasImssActualizacion()!=null ?cop.getCuotasImssActualizacion():"0")), NumberTypes.BIGDECIMAL));
				cop.setCuotasImssRecargos(ConstantesBusiness.numberToString(cuota.getImpCOPRec().add(new BigDecimal(cop.getCuotasImssRecargos()!=null?cop.getCuotasImssRecargos():"0")), NumberTypes.BIGDECIMAL));
				cop.setCuotasImssTotal(ConstantesBusiness.numberToString(cuota.getImpCOPTot().add(new BigDecimal(cop.getCuotasImssTotal()!=null?cop.getCuotasImssTotal():"0")), NumberTypes.BIGDECIMAL));
				cop.setRcv(ConstantesBusiness.numberToString(cuota.getImpRCV().add(new BigDecimal(cop.getRcv()!=null?cop.getRcv():"0")), NumberTypes.BIGDECIMAL));
				cop.setRcvActualizacion(ConstantesBusiness.numberToString(cuota.getImpRCVAct().add(new BigDecimal(cop.getRcvActualizacion()!=null?cop.getRcvActualizacion():"0")), NumberTypes.BIGDECIMAL));
				cop.setRcvRecargos(ConstantesBusiness.numberToString(cuota.getImpRCVRec().add(new BigDecimal(cop.getRcvRecargos()!=null? cop.getRcvRecargos():"0")), NumberTypes.BIGDECIMAL));
				cop.setRcvTotal(ConstantesBusiness.numberToString(cuota.getImpRCVTot().add(new BigDecimal(cop.getRcvTotal()!=null ? cop.getRcvTotal():"0")), NumberTypes.BIGDECIMAL));
	 
				cop.setTotalCuotasRcv(ConstantesBusiness.numberToString(OperationTypes.SUMA, cuota.getImpCOP(), cuota.getImpRCV(),new BigDecimal(cop.getTotalCuotasRcv()!=null?cop.getTotalCuotasRcv():"0")));
				cop.setTotalActualizacion(ConstantesBusiness.numberToString(OperationTypes.SUMA, cuota.getImpCOPAct(), cuota.getImpRCVAct(),new BigDecimal(cop.getTotalActualizacion()!=null?cop.getTotalActualizacion():"0")));
				cop.setTotalRecargos(ConstantesBusiness.numberToString(OperationTypes.SUMA, cuota.getImpCOPRec(), cuota.getImpRCVRec(),new BigDecimal(cop.getTotalRecargos()!=null?cop.getTotalRecargos():"0")));
				cop.setGranTotal(ConstantesBusiness.numberToString(OperationTypes.SUMA, cuota.getImpCOPTot(), cuota.getImpRCVTot(),new BigDecimal(cop.getGranTotal()!=null?cop.getGranTotal():"0")));
				cop.setNumTrabajadoresRegularizados(ConstantesBusiness.numberToString((cuota.getNuTrabRegu()!=null ?cuota.getNuTrabRegu():0)+Integer.valueOf(cop.getNumTrabajadoresRegularizados()!=null?cop.getNumTrabajadoresRegularizados():"0").intValue(), NumberTypes.INTEGER));
				//cuotas.add(cop);
			}
			cuotas.add(cop);
			}
		}

		preCorr.setCopPagadas(cuotas);

		return preCorr;
	}

	
	
	/**
	 * 
	 * @param cveAnexoSolicitudCorrPat
	 * @return
	 * 
	 */
	private CrtCoppagada obtenerCopPagadasPorId(Integer cveAnexoSolicitudCorrPat) {
		CrtCoppagada copRegresa = new CrtCoppagada();
		List<CrtCoppagada> copPagada = copPagadaDao.findByCriteria(Restrictions.eq("cveAnexoSolCorrPat", cveAnexoSolicitudCorrPat));
		if(copPagada != null && copPagada.size() > 0){
			copRegresa = copPagada.get(0);
		}else{
			copRegresa = null;
		}
		return copRegresa;
	}

	
	
	private List<CrtCoppagada> obtenerListaCopPagadasPorId(Integer cveAnexoSolicitudCorrPat) {
		
		List<CrtCoppagada> copPagada = copPagadaDao.findByCriteria(Restrictions.eq("cveAnexoSolCorrPat", cveAnexoSolicitudCorrPat));
		if(copPagada != null && copPagada.size() > 0){
			return copPagada;
		}else{
			copPagada = null;
		}
		return copPagada;
	}

	/**
	 * 
	 * @param solicitudes
	 * @return List<DataTableSolCorreccion> lista de filas de la tabla que se
	 *         renderiza en el jsp presentacionCorreccion.jsp
	 * 
	 */
	private List<DataTableSolCorreccion> fillDataTableSolCorr(
			List<CrtSolicitudcorr> solicitudes) {
		List<DataTableSolCorreccion> datos = new ArrayList<DataTableSolCorreccion>();
		if (solicitudes != null && solicitudes.size() > 0) {
			for (CrtSolicitudcorr sol : solicitudes) {
				DataTableSolCorreccion row = new DataTableSolCorreccion();
				CgcCatMotivoRechazo rechazo = null;
				AbstractCrcStatus estatus = statusDao.findById(new Long(sol.getCveStatus()), false);

				if (sol.getCveStatus().intValue() == ConstantesBusiness.ESTATUS_RECHAZADO) {
					if (sol.getCveMotivoRechazo() != null) {
						rechazo = motivoRechazoDao.findById(new Long(sol.getCveMotivoRechazo()), false);
						row.setMotivoRechazo(rechazo.getMotivorechazo());
					} else {
						row.setMotivoRechazo(RECHAZADA_SIN_MOTIVO);
					}
				}

				row.setNuFolioSolCorreccion(sol.getNuFolio().trim());
				row.setIdSolicitudCorreccion(sol.getCveSolicitudCorr());

				if (sol.getCveTipoCorreccion() != null) {
					CrcTipoCorr tipoCorr = tipoCorreccionDao.findById(new Long(sol.getCveTipoCorreccion()), false);
					row.setTipoCorreccion(tipoCorr.getTxDescripcion());
				}

				row.setEstatus(estatus.getTxDescripcion());
				row.setIdEstatus(sol.getCveStatus());

				if (sol.getFecFechaLimite() != null)
					row.setFdLimiteSolicitud(ConstantesBusiness.dateToStringFormat(sol.getFecFechaLimite(), ConstantesBusiness.DD_MM_YYYY));

				datos.add(row);
			}
		}
		return datos;
	}

	public List<ErrorValidation> validarSolicitudCorreccion(Integer solicitudCorreccion) {
		List<ErrorValidation> validaciones = null;
		
		validaciones = solCorrDao.isFolioCorreccionValidoParaPresentacion(solicitudCorreccion);
		validaciones.addAll(anexoSolCorrDao.isFolioCorrecionConAnexoPatronal(solicitudCorreccion));
		
		/*Long filas = copPagadaDao.isFolioCorreccionConCopPagada(solicitudCorreccion);
		
		if(filas == null || 
				filas.longValue() == 0){
			validaciones.add(new ErrorValidation("Las cuotas de recuperaci&oacute;n no est&aacute;n cargadas en el sistema", true));
		}
		*/

		return validaciones;
	}

	public Map<String, List<SelectBean>> obtenerTipoPromocionYOrigen(Integer idflujo, Integer idTipo) {
		Map<String, List<SelectBean>> mapa = new HashMap<String, List<SelectBean>>();
		List<SelectBean> tipoPromocion = solCorrDao.tiposPromocionCarga(idflujo, idTipo);
		List<SelectBean> origenes = solCorrDao.origenesPromocion();
		mapa.put(ConstantesBusiness.LISTA_TIPOS_PROMOCION, tipoPromocion);
		mapa.put(ConstantesBusiness.LISTA_ORIGENES, origenes);
		return mapa;
	}
	
	/**
	 * Metodo que obtiene los catalogos iniciales en el selector (Promocion) 
	 * 
	 * @author Oscar German Beltran Ortega
	 * @version 1.0.1
	 */
	public Map<String, List<SelectBean>> obtenerTipoPromocionYOrigenSelector(Integer idflujo, Integer idTipo) {
		Map<String, List<SelectBean>> mapa = new HashMap<String, List<SelectBean>>();
		List<SelectBean> listaTemporal= new ArrayList<SelectBean>(); 
		
		//filtramos las promociones para solo tomar ordinario y sal base cot.
		List<SelectBean> tipoPromocion = solCorrDao.tiposPromocionCarga(idflujo, idTipo);
		for(SelectBean tipo:tipoPromocion){

			if(ConstantesBusiness.TIPO_ORDINARIO.equals(tipo.getId()) || ConstantesBusiness.TIPO_SALARIO_BASE_COTIZACION.equals(tipo.getId())){
				listaTemporal.add(tipo);
			}
		}
		tipoPromocion.clear();
		tipoPromocion.addAll(listaTemporal);
		mapa.put(ConstantesBusiness.LISTA_TIPOS_PROMOCION, tipoPromocion);
		listaTemporal.clear();
		
		//filtramos el origen para solo tomar ORIGEN_PROGRAMADO_NIVEL_CENTRAL
		List<SelectBean> origenes = solCorrDao.origenesPromocion();
		for(SelectBean origen : origenes){
			if(ConstantesBusiness.ORIGEN_PROGRAMADO_NIVEL_CENTRAL.equals(origen.getId())){
				listaTemporal.add(origen);
				break;
			}
		}
		origenes.clear();
		origenes.addAll(listaTemporal);
		mapa.put(ConstantesBusiness.LISTA_ORIGENES, origenes);

		return mapa;
	}

	public List<SelectBean> obtenerCriteriosSeleccion(Integer idTipo, Integer idOrigen) {
		return solCorrDao.criterioSeleccionPromocion(idTipo, idOrigen);
	}

	public List<ErrorValidation> guardaCriterioSeleccion(Long idTipo, Long id, Long idDelegacion, Long idSubDelegacion, String registroPatronal, String usuario) throws NotFoundRegistroPatronalException{
		List<ErrorValidation> registros = new ArrayList<ErrorValidation>();
		try{

				CrtSelector selector = new CrtSelector();
				selector.setNuRegistroPatronal(registroPatronal);
				selector.setCriterioSeleccion(id);
				selector.setIdPromocionado("0");
				selector.setCveUsuario(usuario);
				selector.setFecFecharegistro(new Date());
				SacSubdelegacion subDelegacion = selectorDao.obtenerSubDelegacion(idDelegacion, idSubDelegacion);
				selector.setCgcCatcriterioseleccion(selectorDao.obtenerCriterio(id));
				if(subDelegacion != null){
					selector.setSacDelegacion(subDelegacion.getSacDelegacion());
					selector.setSacSubdelegacion(subDelegacion);
					selector = selectorDao.makePersistent(selector);
					registros.add(new ErrorValidation("Registro Insertado", false));
				}
		}catch (EJBException ex) {
			registros.add(new ErrorValidation(ex.getLocalizedMessage(), true));
		}
		return registros;
	}

	@Override
	public List<ErrorValidation> guardarDeteccion(CrtDeteccion deteccion, String delegacion, String subDelegacion,Long idSubDelegacion) {
		List<ErrorValidation> errs = new ArrayList<ErrorValidation>();
		Long del = new Long(delegacion);
		Long subDel = new Long(subDelegacion);
		try {
			Calendar cal = Calendar.getInstance();
			
			deteccion.setFechaDeteccion(new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(deteccion.getFecFechadeteccionFc()));
			if(deteccionServiceBean.validaNuReporte(deteccion)!=null){
			//if (selectorDao.existeNumeroReporteObra(deteccion)) {
				logger.warn("Numero de Reporte de Obra Duplicada :: " +  deteccion.getNuReportectrlobra());
				errs.add(new ErrorValidation("N\u00FAmero de Reporte de Obra "+ deteccion.getNuReportectrlobra()+" Duplicado " , true));
				return errs;
			}
			
			cal.setTime(deteccion.getFecFechadeteccionFc());
			
			//CrtNroFolio folio = selectorDao.obtenerSiguienteFolio(del, subDel, deteccion.getCveTipocorr(), deteccion.getFecFechadeteccionFc());

			String folio = this.generaFolioDeteccion(del,subDel,Functions.stringToDate(deteccion.getFechaDeteccion()));
			
			deteccion.setNuFoliodeteccion(folio);
			deteccion.setSdelegOrig(new BigDecimal(idSubDelegacion));//aqui va el idSubdelegacion de la BD
			
			List<SatPatron> patrones = satPatronDao.findByCriteria(Restrictions.eq("registroPatronal", deteccion.getRegPatron()));
			if (patrones.size() > 0) {
				deteccion.setCveFkPatron(patrones.get(0).getCvePK());
			}

			deteccion = selectorDao.guardarDeteccionCarga(deteccion);
			errs.add(new ErrorValidation("Registro Insertado", false));
		} catch (EJBException e) {
			e.printStackTrace();
			errs.add(new ErrorValidation(e.getLocalizedMessage(), true));
		}
		return errs;
	}
	
	
	public String generaFolioDeteccion(Long del,Long sDel, Date fecha){
		
		String consecutivo = this.deteccionServiceBean.obtieneFolios(del, sDel, fecha);
		
		
		return consecutivo;
	}

	@Override
	public List<SegUsuarioFuncionario> cargarCensores(Long idDelegacacion,
			Long idSubdelegacion) {
		List<SegUsuarioFuncionario> lista = null;
		try{
			lista = selectorDao.cargarCensoresDeteccion(idDelegacacion, idSubdelegacion);
		}catch (EJBException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	/**
	 * Obtiene una lista con la informacion basica de las solicirudes de correccion que estan 
	 * en estatus de presentadas.
	 * @author CesarAgustin
	 * @version 1.0.0
	 * 
	 */
	@Override
	public List<EstudioSolicitudCorreccionVO> consultaSolicitudesCorr(
			EstudioSolicitudCorreccionVO solicitudCorr) {
		
		logger.info("/**** Servicio para obtener las solicitudes de correccion de acuerdo a los filtros recibidos ****/");
		
		List<Object[]> result = solCorrDao.obtnerSolSeguimientoCorreccion(solicitudCorr);		
		List<EstudioSolicitudCorreccionVO> listaRetorno = new ArrayList<EstudioSolicitudCorreccionVO>();
		
		for (Object[] itemBD : result) {
			EstudioSolicitudCorreccionVO correccionVO = new EstudioSolicitudCorreccionVO(itemBD);
			//calcular los dias transcurridos
			correccionVO.setDiasTranscurridos(calculaDiasTranscurridos(correccionVO.getFecFechaEstatus()));
			listaRetorno.add(correccionVO);
			
		}
		
		return listaRetorno;
	}

	/**
	 * Obtiene la informacion detallada de una solicitud de correccion, para mostrarla en la recepcion.
	 * @author CesarAgustin
	 * @version 1.0.0
	 * @since 28/06/2012
	 * @param idSolicitud
	 */
	@Override
	public RecepcionSeguimientoVO detalleSolicitudCorreccion(Integer idSolicitud) {
		
		logger.info("/**** Servicio para obtener el detalle de una solicitud de correccion de acuerdo a su identificador ****/");
		
		Object[] result = solCorrDao.obtenerDetalleSolCorr(idSolicitud);
		
		RecepcionSeguimientoVO retorno = new RecepcionSeguimientoVO();
		int i=0;
			
		retorno.setIdSolicitud(Integer.parseInt(String.valueOf(result[i++])));
		retorno.setFolioCorr(String.valueOf(result[i++]));
		retorno.setFechaPresenta(Functions.dateToString2((Date) result[i++]));
		retorno.setRazonSocial(String.valueOf(result[i++]));
		retorno.setRegPatronal(String.valueOf(result[i++]));
		retorno.setFechaPresentaIni(Functions.dateToString2((Date) result[i++]));
		retorno.setFechaPresentaFin(Functions.dateToString2((Date) result[i++]));
		retorno.setTipoSolicitud(Integer.parseInt(String.valueOf(result[i++])));
		retorno.setClaveObra((BigDecimal)result[i++]);
		retorno.setPago((BigDecimal)result[i++]);
		retorno.setAfilia((BigDecimal)result[i++]);
		retorno.setDocto((BigDecimal)result[i++]);
		retorno.setIdPresentaCorr(Integer.parseInt(String.valueOf(result[i++])));
		
		if (retorno.getTipoSolicitud()!=null && retorno.getTipoSolicitud()==CrtSolicitudcorr.SOLICITUD_TIPO_CONSTRUCCION) {
			if (retorno.getClaveObra()!=null){
				SatObra saticObra = this.obraService.validaObra(retorno.getClaveObra().toString());
				
				if(saticObra!=null){
					retorno.setCalle(saticObra.getUbicacion().getCalle());
					retorno.setNumExt(saticObra.getUbicacion().getNumeroExterior());
					retorno.setNumInt(saticObra.getUbicacion().getNumeroInterior()!=null?saticObra.getUbicacion().getNumeroInterior():"");
					retorno.setColonia(saticObra.getUbicacion().getColonia());					
					retorno.setCodPostal(saticObra.getUbicacion().getCodigoPostal());
				}				
			}else{				
				//La obra cuenta con un domicilio normado por INEGI
				List<CrtAnexosolcorrpat> registroPatronalObra = anexoSolCorrDao.findByCriteria(Restrictions.eq("cveSolicitudCorr", retorno.getIdSolicitud()), 
						Restrictions.eq("tipoPatron", CrtAnexosolcorrpat.TIPO_REGISTRO_RP_OBRA));
				
				if(registroPatronalObra!=null && !registroPatronalObra.isEmpty()){
					
					CrtAnexosolcorrpat patronObra = registroPatronalObra.get(0);
					DgDomicilioGeografico domObra = new DgDomicilioGeografico();
					
					domObra.setDomicilioId(patronObra.getCveDomicilio());
					domObra = domicilioDao.consultaPorClave(domObra);

					//Se recupera de BDTU
					domObra=domicilioDao.getDomicilioBDTU(domObra);
					
					
					patronObra.setDomicilioGeografico(domObra);
					patronObra.setDirreccionInegi(domObra);
					
					retorno.setCalle(patronObra.getCalle());
					retorno.setNumExt((patronObra.getNumExterior()!=null?patronObra.getNumExterior():" ")+" "+(patronObra.getNumExteriorAlfa()!=null?patronObra.getNumExteriorAlfa():" "));
					retorno.setNumInt((patronObra.getNumInterior()!=null?patronObra.getNumInterior():" ")+" "+(patronObra.getNumInteriorAlfa()!=null?patronObra.getNumInteriorAlfa():" "));
					retorno.setColonia(patronObra.getColonia());					
					retorno.setCodPostal(patronObra.getCodigoPostal());					
				}
			}
		}
			
		return retorno;
	}

	/**
	 * @author CesarAgustin
	 * @version 1.0.0
	 * @since 29/06/2012
	 */
	@Override
	public Boolean guardarRecepcionSeguimiento(RecepcionSeguimientoVO presentaCorr) {
		
		Boolean retorno = false;
		
		if (presentaCorr.getIdPresentaCorr()!=null) {
			logger.info("/**** Servicio para guardar informacion de recepcion de seguimiento para la presentacion correccion ::  ****/"+presentaCorr.getIdPresentaCorr());
		}
		
		CrtPresentacorr presentaActual = presentacionCorreccionDao.findById(presentaCorr.getIdPresentaCorr(), false); 
		
		presentaActual.setObservaciones(presentaCorr.getObservaciones());
		presentaActual.setNuComprobantepago(presentaCorr.getPago());
		presentaActual.setNuCompromovafil(presentaCorr.getAfilia());
		presentaActual.setNuDoctosustento(presentaCorr.getDocto());
		presentaActual.setCveUsuario(presentaCorr.getCveUsuario());
		if (presentaCorr.getFechaRegistro()!=null) {
			presentaActual.setFecFechareg(presentaCorr.getFechaRegistro());
		}		
		
		CrtPresentacorr actualizado = presentacionCorreccionDao.makePersistent(presentaActual);
		
		if (actualizado.getCvePresentacorr().equals(presentaCorr.getIdPresentaCorr())) {
			logger.info("/**** La actualizacion de realizo con exito  ****/");
			retorno = true;
		}
		return retorno;
	}

	@Override
	public List<CrtSolicitudcorr> obtenerCorrInvitacionPorCveInvitacion(
			BigDecimal cveInvitacion) {
		
		List<CrtSolicitudcorr> listaResultante = solCorrDao.obtenerCorrInvitacionPorCveInvitacion(cveInvitacion);
		
		return listaResultante;
	}

	/**
	 * Metodo que llena el VO para regresar los datos en pantalla
	 * @author Enrique Duran JImenez
	 * @since  26/07/2012
	 */
	@Override
	public CorreccionSeguimientoGenericoVO llenaCorreccionMain(Integer idSolicitud) {
		
		CorreccionSeguimientoGenericoVO salida = new CorreccionSeguimientoGenericoVO();
		CrtSolicitudcorr solicitud = solCorrDao.findById(idSolicitud, false);
		CrtPresentacorr presentacion = presentacionCorreccionDao.findConcreteByCriteria(Restrictions.eq("cveSolicitudcorr", idSolicitud));

		if(solicitud != null){
			salida.setCveSolCorr(solicitud.getCveSolicitudCorr());
			salida.setNuFolio(solicitud.getNuFolio());
			
			
			if(solicitud.getCveUsuario()!=null && !solicitud.getCveUsuario().equals("null") ){
				salida.setCveIdUsuarioCorr((solicitud.getCveUsuario()));	
			}
			
			salida.setCveAuditorAsignado(solicitud.getCveAuditorAsignado());
			salida.setCveTipoCorreccion(solicitud.getCveTipoCorreccion());
			
			if(solicitud.getFecFechaPeriodoIni() != null){
				salida.setFecFechaPeriodoIni(Functions.dateToString(solicitud.getFecFechaPeriodoIni()));
			}
			if(solicitud.getFecFechaPeriodoFin() != null){
				salida.setFecFechaPeriodoFin(Functions.dateToString(solicitud.getFecFechaPeriodoFin()));
			}
			if(solicitud.getFecFechaElacoracionCorreccion() != null){
				salida.setFecElaboraSolCorr(Functions.dateToString(solicitud.getFecFechaElacoracionCorreccion()));
			}
			if(solicitud.getCvePatron() != null){
				SatPatron patron = new SatPatron();
				patron = (SatPatron)satPatronDao.findById(solicitud.getCvePatron(), false);
				if(patron != null){
					salida.setRegPatronal(patron.getRegistroPatronal());
					salida.setRazonSocial(patron.getRazonSocial());
				}
			}
			if(solicitud.getCveNumeroRegObra() != null){
				salida.setRegObra(solicitud.getCveNumeroRegObra().toString());
			}
			if(presentacion != null){
				salida.setCvePresentaCorr(presentacion.getCvePresentacorr());
				if(presentacion.getFecElaborapre()!= null){
					salida.setFecElaboraPre(Functions.dateToString(presentacion.getFecElaborapre()));
				}
			}
		}
		
		
		return salida;
	}
	
	private Long calculaDiasTranscurridos(Date fechaEstatus){
		
		 Long diasDiferencia= Functions.calculaDifDiasFechas(new Date(), fechaEstatus);
		 		
		return diasDiferencia;
	}

	/**
	 * Metodo que construye el objeto de resumen, de acuerdo a parametros de consulta
	 * 
	 * @author Jorge Hernandez Almazan
	 * @param registroPatronal 
	 * @param claveSolCorr
	 * @param rpsInscritos
	 * @return
	 */
	
	@Override
	public ResumenSeguimientoVO llenaResumenSeguimiento(PresentacionCorreccionModel presentacionCorrecionModel,String registroPatronal,Integer claveSolCorr,List<?> rpsInscritos) {
	
		//Datos Generales
		//Generacion de Domicilios
		List<SatPatron> regPatrAso=new ArrayList<SatPatron>();
		List<DetalleCOPSeguimientoVO> copPagadas=new ArrayList<DetalleCOPSeguimientoVO>();
		SatPatron patr=null;
		ResumenSeguimientoVO vo=new ResumenSeguimientoVO();
		generaDomicilios(vo, presentacionCorrecionModel);
	
		//Registros Patronales asociados y totalCOP
		DetalleCOPSeguimientoVO copS=null;
		DetalleCOPSeguimientoVO rcvS=null;
		
		//Lista de COP y RCV 
		List<DetalleCOPSeguimientoVO> listaCOP=new ArrayList<DetalleCOPSeguimientoVO>();
		List<DetalleCOPSeguimientoVO> listaRCV=new ArrayList<DetalleCOPSeguimientoVO>();
		
		for(Object ps:rpsInscritos){			
			Object[] reg=(Object[]) ps;
			copS=new DetalleCOPSeguimientoVO();
			rcvS=new DetalleCOPSeguimientoVO();
			copS.setSuertePrincipal(Functions.parserBigDecimal(reg[1]));
			copS.setActualizaciones(Functions.parserBigDecimal(reg[2]));
			copS.setRecargos(Functions.parserBigDecimal(reg[3]));
			copS.setTotales(Functions.parserBigDecimal(reg[4]));	
			rcvS.setSuertePrincipal(Functions.parserBigDecimal(reg[5]));
			rcvS.setActualizaciones(Functions.parserBigDecimal(reg[6]));
			rcvS.setRecargos(Functions.parserBigDecimal(reg[7]));
			rcvS.setTotales(Functions.parserBigDecimal(reg[8]));			
			listaCOP.add(copS);
			listaRCV.add(rcvS);
			patr=patronesService.getByRegistroPatronal(reg[0].toString());
			regPatrAso.add(patr);		
		}
		
		//listas para la ejecucion de sumas
		DetalleCOPSeguimientoVO totalcopS=new DetalleCOPSeguimientoVO();		
		DetalleCOPSeguimientoVO totalrcvS=new DetalleCOPSeguimientoVO();
		DetalleCOPSeguimientoVO totalVOS=new DetalleCOPSeguimientoVO();
		
		//inicializamos valores
		totalcopS.setConcepto("COP");
		totalcopS.setActualizaciones(BigDecimal.ZERO);
		totalcopS.setRecargos(BigDecimal.ZERO);
		totalcopS.setSuertePrincipal(BigDecimal.ZERO);
		totalcopS.setTotales(BigDecimal.ZERO);
		totalrcvS.setConcepto("RCV");
		totalrcvS.setActualizaciones(BigDecimal.ZERO);
		totalrcvS.setRecargos(BigDecimal.ZERO);
		totalrcvS.setSuertePrincipal(BigDecimal.ZERO);
		totalrcvS.setTotales(BigDecimal.ZERO);
		
		//iteracion por todos los COP/RCV
		for(int s=0;s<rpsInscritos.size();s++){
			totalcopS.setSuertePrincipal(totalcopS.getSuertePrincipal().add(listaCOP.get(s).getSuertePrincipal()));
			totalcopS.setActualizaciones(totalcopS.getActualizaciones().add(listaCOP.get(s).getActualizaciones()));
			totalcopS.setRecargos(totalcopS.getRecargos().add(listaCOP.get(s).getRecargos()));
			totalcopS.setTotales(totalcopS.getTotales().add(listaCOP.get(s).getTotales()));						
			totalrcvS.setSuertePrincipal(totalrcvS.getSuertePrincipal().add(listaRCV.get(s).getSuertePrincipal()));
			totalrcvS.setActualizaciones(totalrcvS.getActualizaciones().add(listaRCV.get(s).getActualizaciones()));
			totalrcvS.setRecargos(totalrcvS.getRecargos().add(listaRCV.get(s).getRecargos()));
			totalrcvS.setTotales(totalrcvS.getTotales().add(listaRCV.get(s).getTotales()));		
					
		}
		
		//Calculo total 
		totalVOS.setConcepto("Total");
		totalVOS.setSuertePrincipal(totalrcvS.getSuertePrincipal().add(totalcopS.getSuertePrincipal()));
		totalVOS.setActualizaciones(totalrcvS.getActualizaciones().add(totalcopS.getActualizaciones()));
		totalVOS.setRecargos(totalrcvS.getRecargos().add(totalcopS.getRecargos()));
		totalVOS.setTotales(totalrcvS.getTotales().add(totalcopS.getTotales()));
		copPagadas.add(totalcopS);
		copPagadas.add(totalrcvS);
		copPagadas.add(totalVOS);
		vo.setCopPagadas(copPagadas);
		vo.setRegistrosPatronalesAsociados(regPatrAso);
		
		//solicitud de correcion		
		CrtSolicitudcorr cr=solCorrDao.getByClaveSolCorr(claveSolCorr);
		vo.setFechaAutoIngresoSolCorr(Functions.dateToString2(cr.getFecFechaAutorizacionCorreccion()));
		vo.setFechaElaboSolCorr(Functions.dateToString2(cr.getFecFechaElacoracionCorreccion()));
		//genera antecedentes		
		validaPromocionInvitacion(vo,registroPatronal,cr);
		
		
		CrtProrroga pro=prorrogaDao.getByClaveSolCorr(claveSolCorr);
		CrtPresentacorr prese=presentacionCorreccionDao.getByClaveSolCorr(claveSolCorr);
		
		if(cr!=null)
			vo.setFechaIngresoSolCorr(Functions.dateToString2(cr.getFecFechaElacoracionCorreccion()));		
		
		if(pro!=null){
			vo.setFechaSolProrroga(Functions.dateToString2(pro.getFecElaborasolpro()));			
			if(pro != null && pro.getCveStatus()!= null && pro.getCveStatus().intValue()==CrcStatus.APROBADA){
				vo.setFechaAutoProrroga(Functions.dateToString2(pro.getFecFechareg()));
				vo.setFechaRechazoProrroga("N/A");
			}else if(pro.getCveStatus() != null && pro.getCveStatus().intValue()==CrcStatus.RECHAZADA){
				vo.setFechaAutoProrroga("N/A");
				vo.setFechaRechazoProrroga(Functions.dateToString2(pro.getFecFechareg()));
			}
		}
		if(prese!=null){
			vo.setFechaPresentacion(Functions.dateToString2(prese.getFecElaborapre()));
			vo.setFechaAutoPresenta(Functions.dateToString2(prese.getFecAutorizacion()));	
			vo.setNumTrabajaRegular(prese.getNuTrabreg());
		}
	return vo;
	}
	
	
	
	/**
	 * Metodo que valida las promociones e invitaciones correspondientes a un registro patronal
	 * 
	 * @author Jorge Hernandez Almazan
	 * @param resumenSeguimientoVO Beans 
	 * @param presentacionCorrecionModel 
	 * @return
	 */	
	private void validaPromocionInvitacion(ResumenSeguimientoVO vo,String registroPatronal,CrtSolicitudcorr solicitudcorr){
			System.out.println("Validando invitacion");
			CrtInvitacion invitacion = null;
			CrtPromocion promocion = null;
			SatPatron patCorr = patronesService.getByRegistroPatronal(registroPatronal);
			if(patCorr!=null){
					invitacion = invitacionServiceBean.validaInvitacionExistente(
						patCorr.getCvePK().longValue(),
							solicitudcorr.getFecFechaPeriodoIni(),
								solicitudcorr.getFecFechaPeriodoFin());
				if(invitacion != null){
					vo.setOrigen("Invitacion");
					vo.setFolioPrograma(invitacion.getNuFolioInvitacion());
					vo.setFechaEmisionOficio(Functions.dateToString2(invitacion.getFecFechaemision()));
					vo.setFechaNotificacionOficio(Functions.dateToString2(invitacion.getFecFechanotifi()));						
				}else{
					promocion = promocionServiceBean.validaPromocionExistente(
						patCorr.getCvePK(),
							solicitudcorr.getFecFechaPeriodoIni(),
								solicitudcorr.getFecFechaPeriodoFin());
					if(promocion!=null){
						vo.setOrigen("Invitacion");
						vo.setFolioPrograma(promocion.getNuFoliopromocion());
						vo.setFechaEmisionOficio(Functions.dateToString2(promocion.getFecFechaemisionpro()));
						vo.setFechaNotificacionOficio(Functions.dateToString2(promocion.getFecFechanotif()));
					}else{
						vo.setOrigen("N/A");
						vo.setFolioPrograma("N/A");
						vo.setFechaEmisionOficio("N/A");
						vo.setFechaNotificacionOficio("N/A");
						}
					}
				}
			}
		
		
		/**
		 * Metodo que genera la concatenacion de los domicilios correspondientes
		 * 
		 * @author Jorge Hernandez Almazan
		 * @param resumenSeguimientoVO Beans 
		 * @param presentacionCorrecionModel 
		 * @return
		 */	
		private void generaDomicilios(ResumenSeguimientoVO vo,PresentacionCorreccionModel presentacionCorrecionModel){			
			StringBuffer domF=new StringBuffer();
			StringBuffer domc=new StringBuffer();
			StringBuffer domO=new StringBuffer();		
			domF.append(" Calle :"+presentacionCorrecionModel.getCalle()).
			append(" Num Ext: "+presentacionCorrecionModel.getNumExterior()).
			append(" Num Int: "+presentacionCorrecionModel.getNumInterior()).
			append(" Col: "+presentacionCorrecionModel.getColonia()).
			append(" Munc:"+presentacionCorrecionModel.getMunicipio()).
			append(", "+presentacionCorrecionModel.getLocalidad()).
			append(", "+presentacionCorrecionModel.getEntidadFederativa()).append(" C.P. "+presentacionCorrecionModel.getCodigoPostal());			
			if(presentacionCorrecionModel.getIdDomicilioFiscal()==presentacionCorrecionModel.getIdDomicilioObra()){
				domO.append("-1");
			}else{
				domO.append(" Calle :"+presentacionCorrecionModel.getCalleObra()).
				append(" Num Ext: "+presentacionCorrecionModel.getNumExteriorObra()).
				append(" Num Int: "+presentacionCorrecionModel.getNumInteriorObra()).
				append(" Col: "+presentacionCorrecionModel.getColoniaObra()).
				append(" Munc:"+presentacionCorrecionModel.getMunicipioObra()).
				append(", "+presentacionCorrecionModel.getLocalidadObra()).
				append(", "+presentacionCorrecionModel.getEntidadFederativaObra()).
				append(" C.P. "+presentacionCorrecionModel.getCodigoPostalObra());
			}
			
			domc.append(" Calle :"+presentacionCorrecionModel.getCalleCentroTrabajo()).
			append(" Num Ext: "+presentacionCorrecionModel.getNumExteriorCentroTrabajo()).
			append(" Num Int: "+presentacionCorrecionModel.getNumInteriorCentroTrabajo()).
			append(" Col: "+presentacionCorrecionModel.getColoniaCentroTrabajo()).
			append(" Munc:"+presentacionCorrecionModel.getMunicipioCentroTrabajo()).
			append(", "+presentacionCorrecionModel.getLocalidadCentroTrabajo()).
			append(", "+presentacionCorrecionModel.getEntidadFederativaCentroTrabajo()).
			append(" C.P. "+presentacionCorrecionModel.getCodigoPostalCentroTrabajo());		
			vo.setDomicilioFiscal(domF.toString().replace("null", ""));
			vo.setDomicilioCentroTrabajo(domc.toString().replace("null", ""));
			vo.setDomicilioObra(domO.toString().replace("null", ""));
		}
}