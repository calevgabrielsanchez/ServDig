/**
 * RBGSoftware setting java code convention measurements 
 * 2013.07.23
 */
package mx.gob.imss.ctirss.correccion.seguimiento.service.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.service.ejb.PercepcionesServiceRemote;
import mx.gob.imss.ctirss.correccion.catalogos.service.ejb.dao.PercepcionesDAOLocal;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CedulaRevisionAudVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CedulaValidacionConsolidadoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CedulaValidacionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CorreccionSeguimientoGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.DevSubDelegacionSeguimientoCorreccionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RecepcionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RubroVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.TotalRpEjercVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegRegularizarObraGenericoTabVO;
import mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.ejb.DetBaseCotOmitidaServiceRemote;
import mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.ejb.dao.DetBaseCotOmitidaDAOLocal;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.model.CrcStatus;
import mx.gob.imss.ctirss.correccion.model.CrtDetBaseCotOmitida;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.model.CrtProrroga;
import mx.gob.imss.ctirss.correccion.model.CrtRevCedRevValAclara;
import mx.gob.imss.ctirss.correccion.model.CrtRevCedRevision;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivAFisca;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivASubd;
import mx.gob.imss.ctirss.correccion.model.CrtRevOficios;
import mx.gob.imss.ctirss.correccion.model.CrtRevRecepcion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.AnexoSolicitudCorreccionDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.PresentacionCorreccionDAO;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.ProrrogaCorreccionDAO;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao.SeguimientoCorreccionDAOLocal;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.pagos.service.ejb.PagosServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.CatalogoServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.PatronesServiceRemote;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CrtRevDerivAFiscaDAOLocal;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CrtRevDerivASubdDAOLocal;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.SacSubdelegacionDAOLocal;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.SolicitudServiceRemote;
import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.dao.SolicitudCorreccionDAOLocal;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;


@Stateless(name = "seguimientoCorreccionServiceCtr", mappedName = "seguimientoCorreccionServiceCtr")
public class SeguimientoCorreccionServiceCtrBean  extends AbstractService implements SeguimientoCorreccionServiceCtrRemote {
	
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(SeguimientoCorreccionServiceCtrBean.class);

	@EJB SeguimientoCorreccionDAOLocal<AbstractModel> daoSeguimientoCorreccion;
	@EJB CrtRevDerivASubdDAOLocal crtRevDerivASubdDao;
	@EJB SacSubdelegacionDAOLocal sacSubdelegacionDao;
	@EJB SolicitudCorreccionDAOLocal<CrtSolicitudcorr> daoSolicitudCorreccion;
	@EJB CrtRevDerivAFiscaDAOLocal crtRevDerivAFiscaDao;
	
	
	@EJB AnexoSolicitudCorreccionDAO anexoSolCorrDao;
	@EJB DetBaseCotOmitidaDAOLocal<CrtDetBaseCotOmitida> detBaseCotOmitidaDAO;
	@EJB SolicitudServiceRemote<CrtSolicitudcorr> solicitudServiceBean;
	@EJB DetBaseCotOmitidaServiceRemote<CrtDetBaseCotOmitida> detBaseCotOmitidaServiceRemote;
	@EJB PatronesServiceRemote patronesService;
	@EJB CatalogoServiceRemote<AbstractModel> catalogoService;
	@EJB PercepcionesDAOLocal<CrcPercepciones> daoPercepcionesDAOLocal;
	
	@EJB ProrrogaCorreccionDAO prorrogaDao;
	@EJB PercepcionesServiceRemote<CrcPercepciones> percepcionesServiceBean;
	@EJB PresentacionCorreccionDAO presentacionCorreccionDao;
	@EJB PagosServiceRemote<AbstractModel> pagosService;
	
	@Override
	public List<Object> getRegPatronales(
			CorreccionSeguimientoGenericoVO clase) {
		List<Object> patrones=anexoSolCorrDao.getByClaveSolicitudCorrEjercicio(clase.getCveSolCorr(), clase.getCveEjercicio());
	 	return patrones;
	}	

	
	

	/**
	 * Metodo que determina el estado de la recepcion
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	
	@Override
	public CedulaRevisionAudVO determinaEstadoRecepcion(
			CedulaRevisionAudVO cedulaRev, CorreccionSeguimientoGenericoVO clase) {
		boolean flagEstus=false;
		logger.info("LA clase  con cvePresentacion "+clase.getCvePresentaCorr());
		logger.info("El rol del isuario es "+clase.getUser().getCveRol());
		CrtRevRecepcion rec=new CrtRevRecepcion();
		rec.setCvePresentacorr(clase.getCvePresentaCorr());
		rec.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);//Si ya se guardo  se recupera 
		
		rec=daoSeguimientoCorreccion.consultaRecepcion(rec);
		if(rec==null){//si no se ha guardado ninguna cedula se permite el acceso al detalle
			flagEstus=true;
			cedulaRev.setAccesoDetalle(flagEstus);
			return cedulaRev;
		}
		
		//caso para auditor
		if(clase.getUser().getCveRol()==ConstantesBusiness.ROL_AUDITOR){
			logger.info("Entra auditor");
			 if(rec.getIndAutorizaRevision()==null || rec.getIndAutorizaRevision()!=ConstantesBusiness.ESTATUS_REV_AUTO_REV && rec.getIndAutorizaRevision()!=ConstantesBusiness.ESTATUS_REV_AUTORIZADA){
				 flagEstus=true;				 
			 }else if(rec.getIndAutorizaRevision()==ConstantesBusiness.ESTATUS_REV_AUTO_REV){				 
				 cedulaRev.setRazonAcceso("La c\u00e9dula ya se encuentra finalizada ");
			 }else if(rec.getIndAutorizaRevision()==ConstantesBusiness.ESTATUS_REV_AUTORIZADA){
				 cedulaRev.setRazonAcceso("La c\u00e9dula ya fue autorizada ");
			 }
		//caso para jefe de oficina
		//}else if(clase.getUser().getCveRol()==ConstantesBusiness.ROL_SUPERVISOR_OFICINA_CORRECCION){
		}else if(clase.getUser().getCveRol()==ConstantesBusiness.ROL_JEFE_OFICINA_CORRECCION_Y_DICTAMEN || 
				 clase.getUser().getCveRol()==ConstantesBusiness.ROL_JEFE_DEPARTAMENTO_AUDITORIA_A_PATRONES ||
				 clase.getUser().getCveRol()==ConstantesBusiness.JEFE_OF_CORRECCION
				 ){
		//}else{
			logger.info("Entra Jefe ");
			if(rec.getIndAutorizaRevision()==null){
				cedulaRev.setRazonAcceso("La cedula no ha sido finalizada por el auditor");
			}else if(rec.getIndAutorizaRevision()==ConstantesBusiness.ESTATUS_REV_AUTO_REV || rec.getIndAutorizaRevision()==ConstantesBusiness.ESTATUS_REV_RECHAZADA || rec.getIndAutorizaRevision()==ConstantesBusiness.ESTATUS_REV_AUTORIZADA ){
				 flagEstus=true;
			 }
		}
		
		cedulaRev.setAccesoDetalle(flagEstus);
		return cedulaRev;
	}
	
	
	/**
	 * Metodo encargado de la actualizacion de la revision de la cedula
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	
	@Override
	public boolean autorizaCedulaRevision(CorreccionSeguimientoGenericoVO clase) {
		
		List<Object> reg=daoSeguimientoCorreccion.consultaRegistroCedulaRevByCvePresenta(clase.getCvePresentaCorr());
		List<CrtRevCedRevValAclara> aclarados=new ArrayList<CrtRevCedRevValAclara>();
		boolean flagAutoriza=true;

		Integer anexoSolCorr;
		Integer cveEjercicio;
		Integer cvePresentaCorr;
		CrtRevCedRevValAclara aclara=new CrtRevCedRevValAclara();
		for(Object so:reg){
			Object[] currentObj = (Object[]) so;
			anexoSolCorr=Integer.valueOf(currentObj[0].toString());
			cveEjercicio=Integer.valueOf(currentObj[1].toString());
			cvePresentaCorr=Integer.valueOf(currentObj[2].toString());
			aclara.setCveAnexoSolicitudCorrPat(anexoSolCorr);
			aclara.setCveEjercicio(cveEjercicio.longValue());
			aclara.setCvePresentaCorr(cvePresentaCorr.longValue());
			aclarados=daoSeguimientoCorreccion.consultaRubrosRevCeduAclarado(aclara);
			for(CrtRevCedRevValAclara acl:aclarados){
				
				if(acl.getIndAutorizaRevPorAclarar()==null || acl.getIndAutorizaRevPorAclarar()==0){
					flagAutoriza=false;
				}
			}
			
		}
			
			//Se autoriza la recepcion
			if(flagAutoriza){
				//actualizamos presenta
				CrtRevRecepcion rec=new CrtRevRecepcion();
				rec.setCvePresentacorr(clase.getCvePresentaCorr());
				rec.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);
				rec=daoSeguimientoCorreccion.consultaRecepcion(rec);
				clase.setCveRecepcion(rec.getCveRevRecepcion());
				if(rec!=null){
					rec.setIndAutorizaRevision(ConstantesBusiness.ESTATUS_REV_AUTORIZADA);
					rec.setIndPresuntivo((clase.getCedulaRevisionAudVO().isPresuntivo())?1:0);
					daoSeguimientoCorreccion.guardaRecepcion(rec);
				}			
				
			}
								
		return flagAutoriza;
	}

	/**
	 * Metodo encargado del rechazo de una cedula
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	
	@Override
	public String rechazaCedulaRevision(CorreccionSeguimientoGenericoVO clase) {
		CrtRevRecepcion rec=new CrtRevRecepcion();
		rec.setCvePresentacorr(clase.getCvePresentaCorr());
		rec.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);
		rec=daoSeguimientoCorreccion.consultaRecepcion(rec);
		if(rec!=null){
			rec.setIndAutorizaRevision(ConstantesBusiness.ESTATUS_REV_RECHAZADA);
			rec.setIndPresuntivo((clase.getCedulaRevisionAudVO().isPresuntivo())?1:0);
			daoSeguimientoCorreccion.guardaRecepcion(rec);
		}			
		return "Cedula Rechazada";
	}

	

	/**
	 * Metodo que finaliza la captura de una cedula
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	@Override
	public String finalizaCedulaRevision(
			CorreccionSeguimientoGenericoVO clase) {
		
		List<Object> reg=daoSeguimientoCorreccion.consultaRegistroCedulaRevByCvePresenta(clase.getCvePresentaCorr());
		Integer anexoSolCorr;
		Integer cveEjercicio;
		Integer cvePresentaCorr;
		boolean flagFinaliza=true;
		CrtRevCedRevValAclara aclara=new CrtRevCedRevValAclara();
		for(Object so:reg){
			Object[] currentObj = (Object[]) so;
			anexoSolCorr=Integer.valueOf(currentObj[0].toString());
			cveEjercicio=Integer.valueOf(currentObj[1].toString());
			cvePresentaCorr=Integer.valueOf(currentObj[2].toString());
			aclara.setCveAnexoSolicitudCorrPat(anexoSolCorr);
			aclara.setCveEjercicio(cveEjercicio.longValue());
			aclara.setCvePresentaCorr(cvePresentaCorr.longValue());
			if(daoSeguimientoCorreccion.consultaRubrosRevCeduAclarado(aclara).isEmpty()){
				flagFinaliza=false;
			}			
		}
		
		if(flagFinaliza){
			//actualizamos presenta
			CrtRevRecepcion rec=new CrtRevRecepcion();
			rec.setCvePresentacorr(clase.getCvePresentaCorr());
			rec.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);
			rec=daoSeguimientoCorreccion.consultaRecepcion(rec);
			
			CrtRevRecepcion recAuto=new CrtRevRecepcion();
			recAuto.setCvePresentacorr(clase.getCvePresentaCorr());
			recAuto.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
			recAuto=daoSeguimientoCorreccion.consultaRecepcion(recAuto);
						
			
			if(rec!=null){
				rec.setFecFechaAplicRevision(Calendar.getInstance().getTime());
				rec.setIndAutorizaRevision(ConstantesBusiness.ESTATUS_REV_AUTO_REV);
				daoSeguimientoCorreccion.guardaRecepcion(rec);
			}		
			if(recAuto!=null){
				recAuto.setFecFechaAplicRevision(Calendar.getInstance().getTime());
				daoSeguimientoCorreccion.guardaRecepcion(recAuto);
			}
			return "Revision de Cedula Finalizada";
		}else{
			return "Existen Cedulas sin capturar";
		}
	}
	

	
	/**
	 * Metodo encargado de almacenar la cedula correspondiente
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	@Override
	public CorreccionSeguimientoGenericoVO guardaCedulaRevision(CorreccionSeguimientoGenericoVO clase, boolean isSupervisor) {
		logger.info("Guardando Revision de cedula");
		CrtRevCedRevision cedRev=new CrtRevCedRevision();
		boolean flagUpdate=false;
		boolean flagProceRazonable=true;
		logger.info("Ejercicio "+clase.getCveEjercicio());
		logger.info("CvePresentacion "+clase.getCvePresentaCorr());
		logger.info("RegPatronal "+clase.getRegPatronal());
		
		cedRev.setCveEjercicio(clase.getCveEjercicio());
		cedRev.setCvePresentaCorr(clase.getCvePresentaCorr().longValue());
		cedRev.setRegistroPatronal(clase.getRegPatronal());
		cedRev.setCveAnexoSolicitudCorrPat(Integer.valueOf(clase.getCveAnexoSolCorr()));
		// Si existe se actualiza, de lo contrario se crea una nueva
		cedRev=daoSeguimientoCorreccion.consultaCedulaRevisionPorParams(cedRev);
		
		if(cedRev==null){
			cedRev=new CrtRevCedRevision();
		}
		cedRev.setCveAnexoSolicitudCorrPat(Integer.valueOf(clase.getCedulaRevisionAudVO().getCveAnexoSolCorrPat()));
		cedRev.setCveEjercicio(clase.getCveEjercicio());
		cedRev.setCvePresentaCorr(Long.valueOf(clase.getCvePresentaCorr()));
		cedRev.setImporteBaseCotPagImss(new BigDecimal(clase.getCedulaRevisionAudVO().getBaseCotPagaImsIMSS()));
		cedRev.setImporteBaseCotPatron(new BigDecimal(clase.getCedulaRevisionAudVO().getBaseCotPagaImsPatron()));
		cedRev.setImporteDIfBaseCotPagImss(new BigDecimal(clase.getCedulaRevisionAudVO().getDifBaseCotiIMSS()));
		cedRev.setImporteDifBaseCotPatron(new BigDecimal(clase.getCedulaRevisionAudVO().getDifBaseCotiPatron()));
		cedRev.setClaveUsuario(String.valueOf(clase.getUsuarioFirmado().getCurpUsuario()));
		cedRev.setFechaReg(Calendar.getInstance().getTime());	
		cedRev.setIndRazonable(clase.getCedulaRevisionAudVO().isRazonable() ? 1:0);
		
		
		//En caso de que sea supervisor se guardan las selecciones
		if(isSupervisor){
			cedRev.setIndAutorizaBaseCotPagImss(clase.getCedulaRevisionAudVO().isAutorizaBaseCotPaga() ? 1:0);
			cedRev.setIndAutorizaDifCotPagImss(clase.getCedulaRevisionAudVO().isAutorizaDifBaseCot() ? 1:0);
			
		}
		daoSeguimientoCorreccion.guardaCedulaRevision(cedRev);
		
		cedRev=new CrtRevCedRevision();
		cedRev.setCvePresentaCorr(clase.getCvePresentaCorr().longValue());
		
		List<CrtRevCedRevision> cedulas=daoSeguimientoCorreccion.consultaCedulaRevisionPorCvePresenta(cedRev);
		
		for(CrtRevCedRevision ce:cedulas){
			if(ce.getIndRazonable()==null || ce.getIndRazonable().intValue()==0){
				flagProceRazonable=false;
			}
		}
		//Se consulta si existen los detalles
		CrtRevCedRevValAclara crt=new CrtRevCedRevValAclara();
		List<CrtRevCedRevValAclara> rubAclara=null;
		HashMap<Integer, CrtRevCedRevValAclara> mapRubAcl=null;
		List<RubroVO> rubrosRevisar=null;
		CrtRevCedRevValAclara aclara=null;
		
		crt.setCveAnexoSolicitudCorrPat(Integer.valueOf(clase.getCveAnexoSolCorr()));
		crt.setCveEjercicio(clase.getCveEjercicio());
		crt.setCvePresentaCorr(Long.valueOf(clase.getCvePresentaCorr()));
		
		rubAclara=daoSeguimientoCorreccion.consultaRubrosRevCeduAclarado(crt);		
		mapRubAcl=new HashMap<Integer, CrtRevCedRevValAclara>();
		
		for(CrtRevCedRevValAclara t:rubAclara){
			mapRubAcl.put(t.getCvePercepcion(), t);
		}
		
		if(!rubAclara.isEmpty()){
			flagUpdate=true;
		}
		
		//Actualizacion de detalles de la revision
		rubrosRevisar=clase.getCedulaRevisionAudVO().getRubros();		
		for(RubroVO rub:rubrosRevisar){
			if(flagUpdate && mapRubAcl.get(rub.getCvePercepcion())!=null){
				aclara=mapRubAcl.get(rub.getCvePercepcion());
			}else{
				aclara=new CrtRevCedRevValAclara();
			}			
			aclara.setClaveUsuario(String.valueOf(clase.getUsuarioFirmado().getCurpUsuario()));
			aclara.setCveAnexoSolicitudCorrPat(Integer.valueOf(clase.getCedulaRevisionAudVO().getCveAnexoSolCorrPat()));
			aclara.setCveEjercicio(clase.getCveEjercicio());
			aclara.setCvePercepcion(rub.getCvePercepcion());
			aclara.setCvePresentaCorr(Long.valueOf(clase.getCvePresentaCorr()));
			aclara.setFechaReg(Calendar.getInstance().getTime());
			aclara.setImpRevAclarado(new BigDecimal(rub.getImporteAclarado()==null?"0.0":rub.getImporteAclarado()));
			aclara.setImpRevPorAclarar(new BigDecimal(rub.getImportePorAclarar()==null?"0.0":rub.getImportePorAclarar()));
			aclara.setImpTotalPercepcion(new BigDecimal(rub.getTotal()==null?"0.0":rub.getTotal()));
			if(isSupervisor){
				aclara.setIndAutorizaRevPorAclarar(rub.isDatoCorrecto() ? 1:0);
			}
			
			if(flagUpdate){
				mapRubAcl.remove(aclara.getCvePercepcion());
			}
			daoSeguimientoCorreccion.guardaCedulaRevisionAclarado(aclara);
		}
		if(!mapRubAcl.isEmpty()){
			Iterator<?> it = mapRubAcl.entrySet().iterator();
			while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry e = (Map.Entry)it.next();
			CrtRevCedRevValAclara ob=(CrtRevCedRevValAclara) e.getValue();
			daoSeguimientoCorreccion.eliminaCedulaRevisionAclarado(ob);
			}

		}
		
		//Se actualiza presuntivo y observaciones
		CrtRevRecepcion receRevision=new CrtRevRecepcion();
		receRevision.setCvePresentacorr(clase.getCvePresentaCorr());
		receRevision.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);
		receRevision=daoSeguimientoCorreccion.consultaRecepcion(receRevision);
		
		CrtRevRecepcion receAutodet=new CrtRevRecepcion();
		receAutodet.setCvePresentacorr(clase.getCvePresentaCorr());
		receAutodet.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
		receAutodet=daoSeguimientoCorreccion.consultaRecepcion(receAutodet);
		
		//si existe se actualizan valores
		if(receRevision!=null){
			
			clase.setCveRecepcion(receRevision.getCveRevRecepcion());
			if(flagProceRazonable){
				//actualizacion de la recepcion a 12
				receRevision.setCveStatus(CatEstatus.CORRECCION_CONCLUIDA_PARAMETRO_RAZONABLE.getId().intValue());	
				if(receAutodet!=null){
					receAutodet.setCveStatus(CatEstatus.CORRECCION_CONCLUIDA_PARAMETRO_RAZONABLE.getId().intValue());
					daoSeguimientoCorreccion.guardaRecepcion(receAutodet);
				}
			}
			receRevision.setObservaciones(clase.getCedulaRevisionAudVO().getObservaciones());
			receRevision.setIndPresuntivo((clase.getCedulaRevisionAudVO().isPresuntivo())?1:0);
			
			daoSeguimientoCorreccion.guardaRecepcion(receRevision);
			
		}else{
			//si no existe  se crea una recepcion con tipo pago revision
			receRevision=new CrtRevRecepcion();
			receRevision.setCvePresentacorr(clase.getCvePresentaCorr());
			receRevision.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);
			receRevision.setObservaciones(clase.getCedulaRevisionAudVO().getObservaciones());
			receRevision.setIndPresuntivo((clase.getCedulaRevisionAudVO().isPresuntivo())?1:0);
			receRevision.setCveStatus(CatEstatus.PROCESO_DE_REVISION.getId().intValue());
			daoSeguimientoCorreccion.guardaRecepcion(receRevision);
			
			if(receAutodet!=null){
				receAutodet.setCveStatus(CatEstatus.PROCESO_DE_REVISION.getId().intValue());				
				daoSeguimientoCorreccion.guardaRecepcion(receAutodet);
			}
			
		}
		
		clase.setExito("Informacion ha sido almacenada");
		return clase;
	}
	
	/**
	 * Metodo que genera un el objeto de consulota para la revision de cedula
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 * 
 * 	RBGSoftware setting java code convention measurements 
 * 2013.07.23 
	 * 
	 */
	@Override
	public CorreccionSeguimientoGenericoVO generaCedulaRevision(CorreccionSeguimientoGenericoVO clase) {

		logger.debug("Generando cedula de revision");
		CorreccionSeguimientoGenericoVO res=new CorreccionSeguimientoGenericoVO();
		CedulaRevisionAudVO vo=new CedulaRevisionAudVO();
		
		
		String tipoFoli=clase.getNuFolio().split("/")[1];		
		vo.setSolicitudConstruccion(((tipoFoli.equals("CCE") || tipoFoli.equals("CCI") )? true:false));
		CrtRevRecepcion recAutodete=new CrtRevRecepcion();
		CrtRevRecepcion recRevision=new CrtRevRecepcion();
		
		recRevision.setCvePresentacorr(clase.getCvePresentaCorr());
		recRevision.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);	
		recRevision=daoSeguimientoCorreccion.consultaRecepcion(recRevision);
		
		
		recAutodete.setCvePresentacorr(clase.getCvePresentaCorr());
		recAutodete.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);			
		recAutodete.setIndPresuntivo(0);
		recAutodete.setFecFechaAplicRevision(Calendar.getInstance().getTime());
		recAutodete.setFecFechaReg(Calendar.getInstance().getTime());
		recAutodete.setCveUsuario(String.valueOf(clase.getUsuarioFirmado().getCurpUsuario()));
		recAutodete=daoSeguimientoCorreccion.consultaRecepcion(recAutodete);	
		
		//Consulta de la revRecepcion
		if(recRevision!=null){
			if((recRevision.getIndPresuntivo()!=null)){
				vo.setPresuntivo(recRevision.getIndPresuntivo()==1 ? true:false);
			}
				vo.setFechaAplicacionCedula(Functions.dateToString2(recRevision.getFecFechaAplicRevision()));
				vo.setObservaciones(recRevision.getObservaciones());
				clase.setCveRecepcion(recRevision.getCveRevRecepcion());
				res.setCveStatusRecepcion(recRevision.getCveStatus());
				res.setCveRecepcion(recRevision.getCveRevRecepcion());
				vo.setIndAutorizaRevision(recRevision.getIndAutorizaRevision());
		}
	
		//Se consulta la lista de totales RP base COT Paga		
		vo.setTotalRpEjer(generaTotalesRP(clase));
		//Termina  consulta la lista de totales RP base COT Paga	
		
		
		
		List<Long> numEjercicios=null;		
		numEjercicios=anexoSolCorrDao.getEjerciciosByCveSolCorr(clase.getCveSolCorr());
		vo.setEjercicios(numEjercicios);
	

		res.setCedulaRevisionAudVO(vo);
		return res;
	}
	
	
	private List<TotalRpEjercVO> generaTotalesRP(CorreccionSeguimientoGenericoVO clase){
		
		CrtRevCedRevision ob=new CrtRevCedRevision();
		ob.setCvePresentaCorr(clase.getCvePresentaCorr().longValue());
		List<CrtRevCedRevision> listaCedRev=daoSeguimientoCorreccion.consultaCedulaRevisionPorCvePresenta(ob);
		List<TotalRpEjercVO> totalRp=new ArrayList<TotalRpEjercVO>();
		TotalRpEjercVO regTota=null;
		Float difBaseCot=0.0f;
		Float totalPorAclara=0.0f;
		Float porcentaje=0.0f;
		
		for(CrtRevCedRevision crtRev:listaCedRev){
			regTota=new TotalRpEjercVO();
			regTota.setRegistroPatronal(anexoSolCorrDao.getRegistroPatronalByAnexoSolCorrPat(crtRev.getCveAnexoSolicitudCorrPat()));//Buscar base segun anexoSolcorr
			regTota.setPeriodo(String.valueOf(crtRev.getCveEjercicio()));
			regTota.setBaseCotPagada(crtRev.getImporteBaseCotPagImss());
			regTota.setDifBaseCotPagada(crtRev.getImporteDIfBaseCotPagImss());
			regTota.setTotalBase(crtRev.getImporteBaseCotPagImss().add(crtRev.getImporteDIfBaseCotPagImss()));
			regTota.setTotalPorAclarar(daoSeguimientoCorreccion.sumaTotalPorAclarar(crtRev));
			regTota.setTotalPorAclarar(regTota.getTotalPorAclarar()!=null ? regTota.getTotalPorAclarar():BigDecimal.ZERO);
			if(regTota.getTotalPorAclarar()!=null){
				difBaseCot=regTota.getDifBaseCotPagada().floatValue();
				totalPorAclara=regTota.getTotalPorAclarar().floatValue();
				
				if(difBaseCot<=0){
					regTota.setRazonable("NO");
				}else{
					porcentaje=(totalPorAclara/difBaseCot)*100;
					if(porcentaje.floatValue()<=9.0 && porcentaje.floatValue()>0){
						regTota.setRazonable("SI");
					}else{
						regTota.setRazonable("NO");
					}
				}
			}
			
			
			regTota.setEstatus(getEstatusDetalleCedula(crtRev));			
			totalRp.add(regTota);
		}
		
		return totalRp;
	}
	
	private String getEstatusDetalleCedula(CrtRevCedRevision crtRev){
		logger.info("determinando estatus del detalle total");
		boolean flagCedRev=false;
		boolean flagCedRevAclarado=true;
		boolean flagPend=false;
		List<CrtRevCedRevValAclara> listaRubros=null;
		String estatus="test";
		if (crtRev.getIndAutorizaBaseCotPagImss()!=null && crtRev.getIndAutorizaBaseCotPagImss() ==1 && crtRev.getIndAutorizaDifCotPagImss() == 1) {			
			flagCedRev=true;
		}else if(crtRev.getIndAutorizaBaseCotPagImss()==null || crtRev.getIndAutorizaDifCotPagImss()==null ){
			flagPend=true;
		}else if(crtRev.getIndAutorizaBaseCotPagImss() == 0|| crtRev.getIndAutorizaDifCotPagImss() == 0){
			flagCedRev=false;
		}
		CrtRevCedRevValAclara rvcAcla=new CrtRevCedRevValAclara();
		rvcAcla.setCveAnexoSolicitudCorrPat(crtRev.getCveAnexoSolicitudCorrPat());
		rvcAcla.setCveEjercicio(crtRev.getCveEjercicio());
		rvcAcla.setCvePresentaCorr(crtRev.getCvePresentaCorr());		
		listaRubros=daoSeguimientoCorreccion.consultaRubrosRevCeduAclarado(rvcAcla);	
		
		for(CrtRevCedRevValAclara de:listaRubros){
			if(de.getIndAutorizaRevPorAclarar()==null){
				flagPend=true;
				break;
			}else if(de.getIndAutorizaRevPorAclarar()!=null && de.getIndAutorizaRevPorAclarar()==0){
				flagCedRevAclarado=false;
				break;
			}
		}
		if(flagPend){
			estatus="PENDIENTE";
		}else if(flagCedRev && flagCedRevAclarado){
			estatus="AUTORIZADO";
		}else{
			estatus="RECHAZADO";
		}
		return estatus;
	}

	/**
	 * Metodo obtiene la lista de rubros
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	
	@Override
	public CedulaRevisionAudVO  getRubros(CorreccionSeguimientoGenericoVO clase) {
		
		// se busca primero en la tabla de revision para realizar el enlace de objetos
		CedulaRevisionAudVO vo=new CedulaRevisionAudVO();
		logger.info("Bean inyectadoaaa "+daoPercepcionesDAOLocal);
		CrcPercepciones per=new CrcPercepciones();
		
		per.setCveSolicitudCorr(clase.getCveSolCorr());
		List<CrcPercepciones> listaPercepciones=daoPercepcionesDAOLocal.consultaPorCveSolCorr(per);
		
		vo.setCveAnexoSolCorrPat(String.valueOf(clase.getCveAnexoSolCorr()));
				
		List<RubroVO> rubros=new ArrayList<RubroVO>();
		List<RubroVO> rubrosExistentes=new ArrayList<RubroVO>();
		RubroVO rubro;
		String SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_A_PERCEPCIONES;
		SQL = SQL.replace("{1}", clase.getNuFolio());
		SQL = SQL.replace("{2}", String.valueOf(clase.getCveEjercicio()));
		SQL = SQL.replace("{3}", clase.getRegPatronal());
		SQL = SQL.replace("{4}", clase.getUser().getIdSubDelegacion().toString());
		List<AbstractModel> lista=catalogoService.consultaSQL(SQL);
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;		
		
			while(iter.hasNext()){				
				currentObj = (Object[])iter.next();
				rubro=new RubroVO();
				rubro.setCvePercepcion(((BigDecimal) currentObj[0]).intValue());
				rubro.setConcepto(currentObj[1].toString());
				
				rubro.setAutoDeterminacion(Functions.currencyMask(((BigDecimal) currentObj[2]).doubleValue()));
				rubro.setImporteAclarado("0");
				rubro.setImportePorAclarar("0");
				rubro.setTotal("0");
				rubros.add(rubro);
			}			
		}
		
		CrtRevCedRevValAclara crt=new CrtRevCedRevValAclara();
		crt.setCveAnexoSolicitudCorrPat(Integer.valueOf(clase.getCveAnexoSolCorr()));
		crt.setCveEjercicio(clase.getCveEjercicio());
		crt.setCvePresentaCorr(Long.valueOf(clase.getCvePresentaCorr()));
		
		List<CrtRevCedRevValAclara> rubAclara=daoSeguimientoCorreccion.consultaRubrosRevCeduAclarado(crt);
		logger.info("La lista de alcarados "+rubAclara.size());
		HashMap<Integer, CrtRevCedRevValAclara> mapRubAcl=new HashMap<Integer, CrtRevCedRevValAclara>();
		for(CrtRevCedRevValAclara t:rubAclara){
			mapRubAcl.put(t.getCvePercepcion(), t);
		}
		if(!rubAclara.isEmpty() && !rubros.isEmpty()){
			logger.info("Entra aqui rube");
			for(RubroVO rubroVO:rubros){
				CrtRevCedRevValAclara ruM=mapRubAcl.get(rubroVO.getCvePercepcion());
				if(ruM!=null){
					rubroVO.setImporteAclarado(String.valueOf((ruM.getImpRevAclarado()==null? "0.0":ruM.getImpRevAclarado())));
					rubroVO.setImportePorAclarar(String.valueOf((ruM.getImpRevPorAclarar())==null?"0.0":ruM.getImpRevPorAclarar()));
					rubroVO.setTotal(String.valueOf((ruM.getImpTotalPercepcion()==null?"0.0":ruM.getImpTotalPercepcion())));
					if(ruM.getIndAutorizaRevPorAclarar()!=null){
						rubroVO.setDatoCorrecto(ruM.getIndAutorizaRevPorAclarar()==1? true:false);	
					}
					rubrosExistentes.add(rubroVO);
				}				
			}
			
			boolean flag;
			if(mapRubAcl.size()!=rubros.size()){
				Iterator<?> it = mapRubAcl.entrySet().iterator();				
				while (it.hasNext()) {
					@SuppressWarnings("rawtypes")
					Map.Entry e = (Map.Entry)it.next();
					flag=false;
					CrtRevCedRevValAclara ruM=(CrtRevCedRevValAclara) e.getValue();					
					for(RubroVO voR:rubros){
						if(voR.getCvePercepcion()==ruM.getCvePercepcion()){
							flag=true;
						}
					}
					if(!flag){
						logger.info("Existen registros nuevos");
						HashMap<Integer,CrcPercepciones > percep=new HashMap<Integer, CrcPercepciones>();
						for(CrcPercepciones cr:listaPercepciones){
							percep.put(cr.getCvePercepcion(),cr);
						}
						
						rubro=new RubroVO();
						rubro.setCvePercepcion(ruM.getCvePercepcion());
						rubro.setConcepto(percep.get(ruM.getCvePercepcion()).getTxRemuneracion());						
						rubro.setAutoDeterminacion("0.0");
						rubro.setImporteAclarado(String.valueOf(ruM.getImpRevAclarado()));
						rubro.setImportePorAclarar(String.valueOf(ruM.getImpRevPorAclarar()));
						rubro.setTotal(String.valueOf((ruM.getImpTotalPercepcion()==null?"0.0":ruM.getImpTotalPercepcion())));
						if(ruM.getIndAutorizaRevPorAclarar()!=null){
							rubro.setDatoCorrecto(ruM.getIndAutorizaRevPorAclarar().intValue()==1 ?true:false);
						}else{
							rubro.setDatoCorrecto(false);
						}
						
						rubrosExistentes.add(rubro);
					}
				}
			}
			
			vo.setRubros(rubrosExistentes);
		}else if(!rubAclara.isEmpty() && rubros.isEmpty()){
			
			RubroVO rubroVO=null;
			logger.info("No existen registros en el rubro solo nuevas");
			HashMap<Integer,CrcPercepciones > percep=new HashMap<Integer, CrcPercepciones>();
			for(CrcPercepciones cr:listaPercepciones){
				percep.put(cr.getCvePercepcion(),cr);
			}
			
			
			for(CrtRevCedRevValAclara acla:rubAclara){
				rubroVO=new RubroVO();
				if(acla.getImpRevAclarado()==null){
					acla.setImpRevAclarado(BigDecimal.ZERO);
				}
					
				rubroVO.setImporteAclarado(String.valueOf(acla.getImpRevAclarado()));
				rubroVO.setImportePorAclarar(String.valueOf((acla.getImpRevPorAclarar())==null?0.0:acla.getImpRevPorAclarar()));
				rubroVO.setTotal(String.valueOf(acla.getImpTotalPercepcion()));
				rubroVO.setAutoDeterminacion(Functions.currencyMask(0.0));
				rubroVO.setCvePercepcion(acla.getCvePercepcion());
				rubroVO.setConcepto(percep.get(acla.getCvePercepcion()).getTxRemuneracion());
				if(acla.getIndAutorizaRevPorAclarar()!=null)
					rubroVO.setDatoCorrecto(acla.getIndAutorizaRevPorAclarar()==1? true:false);					
				rubrosExistentes.add(rubroVO);
			}
				vo.setRubros(rubrosExistentes);
		}else{
			vo.setRubros(rubros);
		}
		int s=0;
		for(RubroVO rv:vo.getRubros()){
			rv.setIdRow(s++);
		}
		
		vo.setPercepciones(listaPercepciones);
		vo.setTotalRpEjer(generaTotalesRP(clase));
	
		return vo;
	}


	/**
	 * Metodo consulta la revision de una cedula
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	@Override
	public CrtRevCedRevision consultaCedulaRevision(
			CorreccionSeguimientoGenericoVO clase) {
		logger.info("Cve presentacion "+clase.getCvePresentaCorr());
		logger.info("Ejercicio "+clase.getCveEjercicio());
		logger.info("RegPAtron "+clase.getRegPatronal());
		logger.info("CveAnexoSol "+clase.getCveAnexoSolCorr());
		CrtRevCedRevision rev=new CrtRevCedRevision();
		rev.setCveEjercicio(clase.getCveEjercicio());
		rev.setCvePresentaCorr(clase.getCvePresentaCorr().longValue());
		rev.setRegistroPatronal(clase.getRegPatronal());
		rev.setCveAnexoSolicitudCorrPat(Integer.valueOf(clase.getCveAnexoSolCorr()));
		rev=daoSeguimientoCorreccion.consultaCedulaRevisionPorParams(rev);
		if(rev!=null){
			return rev;
		}else{
			return null;
		}
	}

	
	
	@Override
	public CorreccionSeguimientoGenericoVO generaRecepcion(CorreccionSeguimientoGenericoVO voCorreccion){
		
		if(voCorreccion != null){
			CrtRevRecepcion crtRevRecepcion = new CrtRevRecepcion();
			crtRevRecepcion.setCvePresentacorr(voCorreccion.getRecepcionVO().getIdPresentaCorreccion());
			if(voCorreccion.getRecepcionVO()!= null){
				
				//recepcion existente . ya ha sido guardada anteriormente. guardado parcial
				if(voCorreccion.getRecepcionVO().getCveRevRecepcion() != null){ 

					
					crtRevRecepcion = consultaRecepcion(voCorreccion.getRecepcionVO().getIdPresentaCorreccion(),ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
					if(voCorreccion.getRecepcionVO().getConsolidaImporteVo().getTrabRevisados()!= null){
						crtRevRecepcion.setNumTrabrevisados(new BigDecimal( voCorreccion.getRecepcionVO().getConsolidaImporteVo().getTrabRevisados()));
					}
					if(voCorreccion.getRecepcionVO().getConsolidaImporteVo().getTrabOmisos()!= null){
						crtRevRecepcion.setNumTrabomisos(new BigDecimal( voCorreccion.getRecepcionVO().getConsolidaImporteVo().getTrabOmisos()));
					}
					
					if(voCorreccion.getRecepcionVO().getConsolidaImporteVo().getTrabSubdeclarados()!= null){
						crtRevRecepcion.setNumTrabSubdeclarados(new BigDecimal( voCorreccion.getRecepcionVO().getConsolidaImporteVo().getTrabSubdeclarados()));
					}
					if(voCorreccion.getRecepcionVO().getConsolidaImporteVo().getSuertePpalDetCOP()!= null){
						crtRevRecepcion.setImpSPCopAutDet(new BigDecimal( voCorreccion.getRecepcionVO().getConsolidaImporteVo().getSuertePpalDetCOP()));
					}
					if(voCorreccion.getRecepcionVO().getConsolidaImporteVo().getSuertePpalDetRCV()!= null){
						crtRevRecepcion.setImpSPRcvAutDet(new BigDecimal( voCorreccion.getRecepcionVO().getConsolidaImporteVo().getSuertePpalDetRCV()));
					}
					crtRevRecepcion.setFecFechaReg(new Date());
					
					//if(voCorreccion.getRecepcionVO().isPreGuardado()){
						crtRevRecepcion.setFecFechaRecepcion(new Date());
					//}
					
				}else{
					
					//los 3 checbox de recepcion
					//ckeckbox Documentación que sustenta la corrección. 
					if(voCorreccion.getRecepcionVO().isDocumentacionSustenta()){
						crtRevRecepcion.setNuDoctoSustento(new Integer(1));
					}else {
						crtRevRecepcion.setNuDoctoSustento(new Integer(0));
					}
					
					//chekbox bante de pago, y en su caso el convenio del tramite del pago diferido o en parcialidades. 
					if(voCorreccion.getRecepcionVO().isComprobPagoConvenio()){
						crtRevRecepcion.setNuComprobantePago(new Integer(1));
					}else{
						crtRevRecepcion.setNuComprobantePago(new Integer(0));
					}
					
					//Comprobante de la presentación de los avisos afiliatorios, derivados de la correción. 
					if(voCorreccion.getRecepcionVO().isComprobPresAvisosAfil()){
						crtRevRecepcion.setNuComproMovAfil(new Integer(1));
					}else{
						crtRevRecepcion.setNuComproMovAfil(new Integer(0));
					}
	
						
					
					if(voCorreccion.getRecepcionVO().getConsolidaImporteVo()!= null){
						//consolidacion
						SegRegularizarObraGenericoTabVO consolidaVO = voCorreccion.getRecepcionVO().getConsolidaImporteVo();
						if(consolidaVO.getPorcAvance() != null ){
							crtRevRecepcion.setPorcentajeAvance(new BigDecimal(consolidaVO.getPorcAvance()));
						}
						if(consolidaVO.getPorcRegularizado() != null){
							crtRevRecepcion.setPorcentajeRegula(new BigDecimal(consolidaVO.getPorcRegularizado()));
						}
						
						if(consolidaVO.getNumParcialidades()!=null){
							crtRevRecepcion.setNumParcialidades(Integer.valueOf(consolidaVO.getNumParcialidades()));
						}
						
						if(consolidaVO.getTrabRevisados() != null){
							crtRevRecepcion.setNumTrabrevisados(new BigDecimal(consolidaVO.getTrabRevisados()));
						}
						
						if(consolidaVO.getTrabOmisos() != null){
							crtRevRecepcion.setNumTrabomisos(new BigDecimal(consolidaVO.getTrabOmisos()));
						}
						
						if(consolidaVO.getTrabSubdeclarados() != null){
							crtRevRecepcion.setNumTrabSubdeclarados(new BigDecimal(consolidaVO.getTrabSubdeclarados()));
						}
						
						if(consolidaVO.getTrabRegularizados() != null){
							crtRevRecepcion.setNumRegularizados(new BigDecimal(consolidaVO.getTrabRegularizados()));
						}
						
						if(consolidaVO.getSuertePpalDetCOP() != null){
							crtRevRecepcion.setImpSPCopAutDet((new BigDecimal(consolidaVO.getSuertePpalDetCOP())));
						}
						
						if(consolidaVO.getSuertePpalDetRCV() != null){
							crtRevRecepcion.setImpSPRcvAutDet((new BigDecimal(consolidaVO.getSuertePpalDetRCV())));
						}
						
						//if(voCorreccion.getRecepcionVO().isPreGuardado()){
							
						}
						
						crtRevRecepcion.setFecFechaReg(new Date());
						crtRevRecepcion.setFecFechaRecepcion(new Date());
						
						crtRevRecepcion.setCveUsuario(voCorreccion.getRecepcionVO().getCveUsuario());
						crtRevRecepcion.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
						
						if(voCorreccion.getRecepcionVO().isComprobanteConvenio()){
							crtRevRecepcion.setComprobanteConvenio(new Integer(1));
						}else{
							crtRevRecepcion.setComprobanteConvenio(new Integer(0));
						}
						
						
					//}
					crtRevRecepcion.setObservaciones(voCorreccion.getRecepcionVO().getObservaciones());
					
				}//fin else
			}
			crtRevRecepcion.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
			//define estatus de la rececpcion
			voCorreccion.setRecepcionVO(defineEstatusPromocion(voCorreccion.getRecepcionVO()));
			
			crtRevRecepcion.setCveStatus(voCorreccion.getRecepcionVO().getCveEstatus());
			crtRevRecepcion.setFecFechaReg(new Date());
			crtRevRecepcion.setFecFechaRecepcion(new Date());
			crtRevRecepcion = daoSeguimientoCorreccion.guardaRecepcion(crtRevRecepcion);
			voCorreccion.getRecepcionVO().setFechaPresentacionCorr(Functions.dateToString(crtRevRecepcion.getFecFechaReg()));
			
			voCorreccion.getRecepcionVO().setCveRevRecepcion(Integer.valueOf(crtRevRecepcion.getCveRevRecepcion()+""));
			
			
			//Actualizacion a SOlicitud COrreccion
			CrtPresentacorr presentacion = new CrtPresentacorr();
			presentacion.setCvePresentacorr(crtRevRecepcion.getCvePresentacorr());
			//presentacion.setCveSolicitudcorr(cveSolicitudcorr)
			CrtPresentacorr presentacionConsulta =presentacionCorreccionDao.findById(crtRevRecepcion.getCvePresentacorr(),true);
			
			CrtSolicitudcorr crtSolicitudcorr = new CrtSolicitudcorr();
			crtSolicitudcorr.setCveSolicitudCorr(presentacionConsulta.getCveSolicitudcorr());
			CrtSolicitudcorr crtSolicitudcorrActualizar = daoSolicitudCorreccion.consultaPorClave(crtSolicitudcorr);
			
			crtSolicitudcorrActualizar.setCveStatus(defineEstatusSolCorreccion(voCorreccion.getRecepcionVO()));//SE pasa el estatus ya seleccionado en recepcion
			
			daoSolicitudCorreccion.save(crtSolicitudcorrActualizar);
			
			//definicion de estautus de la recepcion
			defineEstatusPromocion(voCorreccion.getRecepcionVO());
			
		}
		return voCorreccion;
	}

	public CrtRevRecepcion consultaRecepcion(Integer idPresentaCorreccion, Integer tipoPagoRecepcion){
		
		CrtRevRecepcion crtRevRecepcion = new CrtRevRecepcion();
		if(idPresentaCorreccion != null){
			crtRevRecepcion.setCvePresentacorr(idPresentaCorreccion);
			
		}
		
		if(tipoPagoRecepcion != null){
			crtRevRecepcion.setIndTipoPago(tipoPagoRecepcion);
		}
		
		crtRevRecepcion  =daoSeguimientoCorreccion.consultaRecepcion(crtRevRecepcion);
		logger.debug("crtRevRecepcion dao "+ crtRevRecepcion);
		if(crtRevRecepcion != null){
			crtRevRecepcion.setFechaRegTxt(Functions.dateToString(crtRevRecepcion.getFecFechaReg()));
			crtRevRecepcion.setFechaPresentacionTxt(Functions.dateToString(crtRevRecepcion.getFecFechaReg()));
		}else{
			return crtRevRecepcion;
		}
		
		//seccion para definir la descripcion de estatus, se auxilia del VO solo para definir el estatus 
		RecepcionSeguimientoVO recepcionVO = new RecepcionSeguimientoVO();
		if(crtRevRecepcion.getNuDoctoSustento()==null || crtRevRecepcion.getNuDoctoSustento()==0){
			recepcionVO.setDocumentacionSustenta(false);
		}else{
			recepcionVO.setDocumentacionSustenta(true);
		}
		
		if(crtRevRecepcion.getNuComprobantePago()==null || crtRevRecepcion.getNuComprobantePago()==0){
			recepcionVO.setComprobPagoConvenio(false);
		}else{
			recepcionVO.setComprobPagoConvenio(true);
		}
		
		
		if(crtRevRecepcion.getComprobanteConvenio()==null || crtRevRecepcion.getComprobanteConvenio()==0){
			recepcionVO.setComprobanteConvenio(false);
		}else{
			recepcionVO.setComprobanteConvenio(true);
		}
		 
		if(crtRevRecepcion.getNuComproMovAfil()==null || crtRevRecepcion.getNuComproMovAfil()==0){
			recepcionVO.setComprobPresAvisosAfil(false);
		}
		else{
			recepcionVO.setComprobPresAvisosAfil(true);
		}
		
		recepcionVO = defineEstatusPromocion(recepcionVO);
		crtRevRecepcion.setEstatusPresentacionDescripcion(recepcionVO.getEstatusPresentacionCorr()); //termia descripcion estatus
		
		
		return crtRevRecepcion;
	}
	
	
	@Override
	public CrtRevOficios consultaRevOficiosPorClave(CrtRevOficios crtRevOficios) {
		return daoSeguimientoCorreccion.consultaRevOficiosPorClave(crtRevOficios);
	}
	

	@Override
	public CrtRevDerivASubd registraCrtRevDerivASubd(DevSubDelegacionSeguimientoCorreccionVO derivSubDelTabVO, DgDomicilioGeografico dom, UserSession user) {
		CrtRevDerivASubd derivacion = new CrtRevDerivASubd();
		derivacion.setClaveUsuario(user.getCurpUsuario().toString());
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		solicitud.setCveSolicitudCorr(derivSubDelTabVO.getCveSolicitud());
		solicitud=(CrtSolicitudcorr)daoSolicitudCorreccion.consultaPorClave(solicitud);
		derivacion.setDomicilio(dom);
		derivacion.setDomicilioId(dom.getDomicilioId());
		SacSubdelegacion subDelegDestino = sacSubdelegacionDao.findById(derivSubDelTabVO.getIdNuevaSubdelegacion(), false);		
		SacSubdelegacion subDelegOrigen = sacSubdelegacionDao.findById(derivSubDelTabVO.getIdAnteriorSubdelegacion(), false);
		derivacion.setSolicitudCorr(solicitud);
		derivacion.setSubdelegDestino(subDelegDestino);
		derivacion.setSubdelegOrigen(subDelegOrigen);
		derivacion.setNumFolio(derivSubDelTabVO.getFolioDerivacion());
		derivacion.setFechaDerivacion(Functions.stringToDate(derivSubDelTabVO.getFechaDerivacion()));
		derivacion.setFechaReg(new Date());
		if(user.getNombreCompleto().length()>50){
			derivacion.setNomUsuarioDeriva(user.getNombreCompleto().substring(0, 50));
		}else{
			derivacion.setNomUsuarioDeriva(user.getNombreCompleto());	
		}
		
		derivacion.setClaveUsuario(user.getCurpUsuario().toString());
		
		//solicitud.setCveSubdelegacion(derivSubDelTabVO.getIdNuevaSubdelegacion());
    	solicitud.setCveStatus(CatEstatus.DERIVADO_SUBDELEGACION.getId().intValue());  // EL NUEVO ESTATUS
		daoSolicitudCorreccion.update(solicitud);
		
		crtRevDerivASubdDao.makePersistent(derivacion);
		return derivacion;
	}
	
	
	
	@Override
	public CrtRevDerivASubd consultaCrtRevDerivASubdCveSolCorr(
			Integer cveSolCorr) {
		CrtRevDerivASubd va=daoSeguimientoCorreccion.getDerivASubdByClaveSolCorr(cveSolCorr);
		if(va!=null){
			va.setFechaFecDerivacion(Functions.dateToString2(va.getFechaDerivacion()));
		}
		return va;
		
	}

	@Override
	public CrtRevOficios guardaReqDoc(CrtRevOficios crtRevOficios) {
		return daoSeguimientoCorreccion.guardaReqDoc(crtRevOficios);
	}

	@Override
	public CrtPresentacorr consultaCrtPresentacorrPorClave(	CrtPresentacorr crtPresentacorr) {
		return daoSeguimientoCorreccion.consultaCrtPresentacorrPorClave(crtPresentacorr);
	}

	@Override
	public CrtRevOficios consultaRevOficiosPorCveSolCorr(CrtRevOficios crtRevOficios) {
		return daoSeguimientoCorreccion.consultaRevOficiosPorCveSolCorr(crtRevOficios);
	}


	public CrtRevDerivAFisca guardaDerivAFisca(CrtRevDerivAFisca crtRevDerivAFisca) {
		/*
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		solicitud.setCveSolicitudCorr(crtRevDerivAFisca.getCveSolicitudCorr());
		solicitud = (CrtSolicitudcorr) daoSolicitudCorreccion.consultaPorClave(solicitud);
		*/
		return crtRevDerivAFiscaDao.makePersistent(crtRevDerivAFisca);
	}


	@Override
	public CrtRevOficios consultaRevOficiosPorCveSolCorrOR(CrtRevOficios crtRevOficios) {
		
		CrtRevOficios model = new CrtRevOficios();
		CrtProrroga crtProrroga = prorrogaDao.getByClaveSolCorr(crtRevOficios.getCveSolCorr().intValue());
		model = daoSeguimientoCorreccion.consultaRevOficiosPorCveSolCorr(crtRevOficios);
		if(crtProrroga != null && model != null){
			if(crtProrroga.getCveStatus().intValue() == CrcStatus.APROBADA){
				model.setFechaAutProrroga(Functions.dateToString(crtProrroga.getFecFechareg()));
			}
		}
		return model;
	}
	


	public CrtRevDerivAFisca consultaDerivAFisca(Long cveRevDerivAFis) {
		CrtRevDerivAFisca derivacion = (CrtRevDerivAFisca) crtRevDerivAFiscaDao.findById(cveRevDerivAFis, false);
		derivacion.getCveRevDerivAFis(); // Solo para usar el proxy y provocar la carga de la instancia
		logger.debug("Deriv fecha reg.= " +  derivacion.getFecFechaReg());
		return derivacion;
	}

	public CrtRevDerivAFisca buscaDerivAFiscaPorSolicitud(Integer cveSolicitud) {
		return (CrtRevDerivAFisca) crtRevDerivAFiscaDao.buscaPorSolicitudCorr(cveSolicitud);
	}

	public CrtRevDerivAFisca reactivaSolicitud(CrtRevDerivAFisca derivacion) {
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		solicitud.setCveSolicitudCorr(derivacion.getCveSolicitudCorr());
		solicitud =(CrtSolicitudcorr)daoSolicitudCorreccion.consultaPorClave(solicitud);
//		solicitud.setCveStatusCorreccion(cveStatusCorreccion);
		crtRevDerivAFiscaDao.makePersistent(derivacion);
		daoSolicitudCorreccion.save(solicitud);
		
		return derivacion;
	}
  
	public CedulaValidacionVO generaCedulaValidacion(CedulaValidacionVO cedulaValidacionVO){
		
		if(cedulaValidacionVO != null){
			//CrtRevRecepcion crtRevRecepcion = null;
			
			guardaRecConsolidacion(cedulaValidacionVO);

			CrtRevCedRevValAclara crt=new CrtRevCedRevValAclara();
			crt.setCveAnexoSolicitudCorrPat(Integer.valueOf(cedulaValidacionVO.getCveAnexoSolCorrPat()));
			crt.setCveEjercicio(cedulaValidacionVO.getCveEjercicio().longValue());
			crt.setCvePresentaCorr(cedulaValidacionVO.getCvePresentaCorr().longValue());
			
			List<CrtRevCedRevValAclara> list=daoSeguimientoCorreccion.consultaRubrosRevCeduAclarado(crt);	
			System.out.println("LA lista de detalles recupera es  "+list.size());
			Map<Integer,CrtRevCedRevValAclara> conceptos=new HashMap<Integer, CrtRevCedRevValAclara>();
			for(CrtRevCedRevValAclara det:list){
				conceptos.put(det.getCvePercepcion(),det);				
			}
			//lista de objetos actualizar
			CrtRevCedRevValAclara acla=null;
			for(CedulaValidacionConsolidadoVO vo:cedulaValidacionVO.getListaPercepciones()){
				System.out.println("LA clave de percepcion es "+vo.getCvePercepcion());
				acla=conceptos.get(vo.getCvePercepcion());
				acla.setImpRevPorAclarar(Functions.parserBigDecimal(vo.getImportexAclarar()));
				acla.setImporteValAclarado(Functions.parserBigDecimal(vo.getAclarado()));
				acla.setImpValAclaradoOfResul(Functions.parserBigDecimal(vo.getAclaradoOficioResultados()));
				acla.setImpValTotPagado(Functions.parserBigDecimal(vo.getTotalPagado()));
				acla.setIndAutorizaValAclara(vo.isAutorizaAclarado() ? 1:0);
				acla.setIndAutorizaAclaraOfResul(vo.isAutorizaAclaradoOficioRes() ? 1:0);
				acla.setIndAutorizaTotPagado(vo.isAutorizaTotalPagado() ? 1 :0);
				daoSeguimientoCorreccion.guardaCedulaRevisionAclarado(acla);
			}			
		}
		return cedulaValidacionVO;

	}




	/**
	 * 
	 * @param 
	 * @author Oscar Beltran Ortega
	 * @return void
		 */
	private void guardaRecConsolidacion(CedulaValidacionVO cedulaValidacionVO) {
		CrtRevRecepcion crtRevRecepcion;
		if(cedulaValidacionVO.getConsolidaImporteVo()!= null && cedulaValidacionVO.getCveConsolidaImporte()!= null){
			//datos capturados del usuario
			SegRegularizarObraGenericoTabVO consolidaVO = cedulaValidacionVO.getConsolidaImporteVo();
			
			crtRevRecepcion = consultaRecepcion(cedulaValidacionVO.getCvePresentaCorr(),ConstantesBusiness.TIPO_PAGO_REVISION);
			//crtRevRecepcion.setCvePresentacorr(cedulaValidacionVO.getCvePresentaCorr());
			crtRevRecepcion.setPorcentajeAvance(Functions.parserBigDecimal(consolidaVO.getPorcAvance()));
			crtRevRecepcion.setPorcentajeRegula(Functions.parserBigDecimal(consolidaVO.getPorcRegularizado()));
				

			
			if(consolidaVO.getNumParcialidades()!=null){
				crtRevRecepcion.setNumParcialidades(Integer.valueOf(consolidaVO.getNumParcialidades()));
			}
				
			if(consolidaVO.getTrabRevisados() != null){
				crtRevRecepcion.setNumTrabrevisados(Functions.parserBigDecimal(consolidaVO.getTrabRevisados()));
			}
				
			if(consolidaVO.getTrabOmisos() != null){
				crtRevRecepcion.setNumTrabomisos(Functions.parserBigDecimal(consolidaVO.getTrabOmisos()));
			}
				
			if(consolidaVO.getTrabSubdeclarados() != null){
				crtRevRecepcion.setNumTrabSubdeclarados(Functions.parserBigDecimal(consolidaVO.getTrabSubdeclarados()));
			}
			
			if(consolidaVO.getTrabRegularizados() != null){
				crtRevRecepcion.setNumRegularizados(Functions.parserBigDecimal(consolidaVO.getTrabRegularizados()));
			}
			
			if(consolidaVO.getSuertePpalDetCOP() != null){
				crtRevRecepcion.setImpSPCopAutDet((new BigDecimal(consolidaVO.getSuertePpalDetCOP())));
			}
				
			if(consolidaVO.getSuertePpalDetRCV() != null){
				crtRevRecepcion.setImpSPRcvAutDet((new BigDecimal(consolidaVO.getSuertePpalDetRCV())));
			}
			
			
				
			crtRevRecepcion.setFecFechaReg(new Date());
			crtRevRecepcion.setFecFechaRecepcion(new Date());
		
			if(cedulaValidacionVO.isComprobanteConvenio()){
				crtRevRecepcion.setComprobanteConvenio(new Integer(1));
			}else{
				crtRevRecepcion.setComprobanteConvenio(new Integer(0));
			}
					
			
			crtRevRecepcion = daoSeguimientoCorreccion.guardaRecepcion(crtRevRecepcion);
			cedulaValidacionVO.setCveConsolidaImporte(Integer.valueOf(crtRevRecepcion.getCveRevRecepcion()+""));
			
			CrtRevRecepcion recepcionHistorico =  consultaRecepcion(cedulaValidacionVO.getCvePresentaCorr(), 1);
			Integer estatus=defineEstatusRecepcionCedVal(cedulaValidacionVO);
			if(estatus.intValue()!=0){
				recepcionHistorico.setCveStatus(defineEstatusRecepcionCedVal(cedulaValidacionVO));
				daoSeguimientoCorreccion.guardaRecepcion(recepcionHistorico);	
			}
			
		}//fin if
	}
	
	@Override
	public List<CedulaValidacionConsolidadoVO> consultaCedulaValidacionConsolidado(CorreccionSeguimientoGenericoVO seguimiento) {
		
		List<Object> reg=daoSeguimientoCorreccion.consultaCedulaValidacionConsolidado(seguimiento.getCvePresentaCorr());
		CedulaValidacionConsolidadoVO consolidado;
		ArrayList<CedulaValidacionConsolidadoVO> listaConsolidado= new ArrayList<CedulaValidacionConsolidadoVO>();
		
		for(Object datos:reg){
			Object[] valDato = (Object[]) datos;
			consolidado = new CedulaValidacionConsolidadoVO();
			consolidado.setConcepto(valDato[0].toString());			
			consolidado.setImportexAclarar(valDato[1].toString());			
			consolidado.setAclarado(valDato[2].toString());
			consolidado.setAclaradoOficioResultados(valDato[3].toString());
			consolidado.setTotalPagado(valDato[4].toString());
			consolidado.setDiferenciaAclarado();
			consolidado.setTotalAPagar();
			consolidado.setDiferenciaPagado();
			listaConsolidado.add(consolidado);
		}
		return listaConsolidado;
	}
	

	public List<Long> recuperaEjercicios(Integer claveSolCorr){
		List<Long> numEjercicios=null;		
		numEjercicios=anexoSolCorrDao.getEjerciciosByCveSolCorr(claveSolCorr);
		return numEjercicios;
		
	}




	@Override
	public CedulaValidacionVO recuperaDetalleCedulaValidacion(
			CedulaValidacionVO cedulaValidacionVO) {

		CedulaValidacionVO valid=new CedulaValidacionVO();
		CrtRevCedRevValAclara crt=new CrtRevCedRevValAclara();
		int idRow=0;
		CrtRevRecepcion rec=new CrtRevRecepcion();
		rec.setCvePresentacorr(cedulaValidacionVO.getCvePresentaCorr());
		rec.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);
		
		rec=daoSeguimientoCorreccion.consultaRecepcion(rec);
		valid.setIndValPrimera(rec.getIndAutorizaValPrimera()!=null ? rec.getIndAutorizaValPrimera().toString() : null);
		valid.setIndValSegunda(rec.getIndAutorizaValSegunda()!=null ? rec.getIndAutorizaValSegunda().toString() : null);
		
		
		
		crt.setCveAnexoSolicitudCorrPat(Integer.valueOf(cedulaValidacionVO.getCveAnexoSolCorrPat()));
		crt.setCveEjercicio(cedulaValidacionVO.getCveEjercicio().longValue());
		crt.setCvePresentaCorr(Long.valueOf(cedulaValidacionVO.getCvePresentaCorr()));
		
		List<CrtRevCedRevValAclara> rubAclara=daoSeguimientoCorreccion.consultaRubrosRevCeduAclarado(crt);
		List<CedulaValidacionConsolidadoVO> listaPercepciones=new ArrayList<CedulaValidacionConsolidadoVO>();
		CedulaValidacionConsolidadoVO vo=null;
		
		for(CrtRevCedRevValAclara rubA:rubAclara){
			vo=new CedulaValidacionConsolidadoVO();
			vo.setImportexAclarar(Functions.bigDecimalToString(rubA.getImpRevPorAclarar()));
			vo.setAclarado(Functions.bigDecimalToString(rubA.getImporteValAclarado()));
			vo.setAutorizaAclarado((rubA.getIndAutorizaValAclara()!=null && rubA.getIndAutorizaValAclara().intValue()==1) ? true :false);
			vo.setAclaradoOficioResultados(Functions.bigDecimalToString(rubA.getImpValAclaradoOfResul()));
			vo.setAutorizaAclaradoOficioRes((rubA.getIndAutorizaAclaraOfResul()!=null && rubA.getIndAutorizaAclaraOfResul().intValue()==1) ? true :false);
			vo.setTotalPagado(Functions.bigDecimalToString(rubA.getImpValTotPagado()));
			vo.setAutorizaTotalPagado((rubA.getIndAutorizaTotPagado()!=null && rubA.getIndAutorizaTotPagado().intValue()==1 )? true :false);
			vo.setIdRow(idRow++);
			CrcPercepciones p=new CrcPercepciones();
			p.setCvePercepcion(rubA.getCvePercepcion());
			vo.setConcepto(percepcionesServiceBean.consultaPorClave(p).getTxRemuneracion());
			vo.setCvePercepcion(rubA.getCvePercepcion());
			listaPercepciones.add(vo);
		}
		
		valid.setListaPercepciones(listaPercepciones);
		return valid;
	}




	@Override
	public CedulaValidacionVO finalizaCedulaValidacion(
		   CedulaValidacionVO cedulaValidacionVO) {
		
		CrtRevRecepcion rec=new CrtRevRecepcion();
		rec.setCvePresentacorr(cedulaValidacionVO.getCvePresentaCorr());
		rec.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);
		rec=daoSeguimientoCorreccion.consultaRecepcion(rec);
		if(cedulaValidacionVO.getSeccionAutoriza().intValue()==ConstantesBusiness.NIVEL_SECCION_A_CEDULA_VALIDACION){
			rec.setIndAutorizaValPrimera(ConstantesBusiness.ESTATUS_REV_VAL_FINALIZADO_AUDITOR);
		}else if(cedulaValidacionVO.getSeccionAutoriza().intValue()==ConstantesBusiness.NIVEL_SECCION_B_CEDULA_VALIDACION){
			rec.setIndAutorizaValSegunda(ConstantesBusiness.ESTATUS_REV_VAL_FINALIZADO_AUDITOR);
		}
		
		daoSeguimientoCorreccion.guardaRecepcion(rec);
		cedulaValidacionVO.setResultado("El proceso ha finalizado correctamente");
		return cedulaValidacionVO;
	}




	@Override
	public String autorizaCedulaValidacion(
			CedulaValidacionVO cedulaValidacionVO) {

		boolean flagAutorizar=true;
		CrtRevRecepcion rec=new CrtRevRecepcion();
		CrtRevRecepcion recAuto=new CrtRevRecepcion();
		rec.setCvePresentacorr(cedulaValidacionVO.getCvePresentaCorr());
		rec.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);
		rec=daoSeguimientoCorreccion.consultaRecepcion(rec);
		
		
		recAuto.setCvePresentacorr(cedulaValidacionVO.getCvePresentaCorr());
		recAuto.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
		recAuto=daoSeguimientoCorreccion.consultaRecepcion(recAuto);
		
		CrtRevCedRevValAclara acla=new CrtRevCedRevValAclara();
		acla.setCvePresentaCorr(cedulaValidacionVO.getCvePresentaCorr().longValue());
		List<CrtRevCedRevValAclara> listaPercepc=daoSeguimientoCorreccion.consultaRubrosRevCeduAclaradoByCvePresentacion(acla);
		if(cedulaValidacionVO.getSeccionAutoriza()!=null && cedulaValidacionVO.getSeccionAutoriza().intValue()==1){
			for(CrtRevCedRevValAclara vo:listaPercepc){
				if(vo.getIndAutorizaValAclara()==null || vo.getIndAutorizaValAclara().intValue()==0){
					flagAutorizar=false;
					break;
				}
			}
			
			if(flagAutorizar){
				rec.setIndAutorizaValPrimera(ConstantesBusiness.ESTATUS_REV_VAL_AUTORIZADO_SUPERV);
				daoSeguimientoCorreccion.guardaRecepcion(rec);
			}
			
		}else{
			
			BigDecimal acumuladoTotalAPagar=BigDecimal.ZERO;
			BigDecimal totalPagarReg=BigDecimal.ZERO;
			for(CrtRevCedRevValAclara vo:listaPercepc){
				//vo.getim
				totalPagarReg=BigDecimal.ZERO;
				totalPagarReg=(vo.getImpRevPorAclarar().subtract(vo.getImporteValAclarado())).subtract(vo.getImpValAclaradoOfResul()).subtract(vo.getImpValTotPagado());
				acumuladoTotalAPagar=acumuladoTotalAPagar.add(totalPagarReg);
				System.out.println("Total a Pagar Reg "+totalPagarReg);
				if(vo.getIndAutorizaAclaraOfResul()==null || vo.getIndAutorizaAclaraOfResul().intValue()==0 || vo.getIndAutorizaTotPagado()==null || vo.getIndAutorizaTotPagado().intValue()==0){
					flagAutorizar=false;
					break;
				}
			}
			
			
			if(flagAutorizar){				
				if(acumuladoTotalAPagar.compareTo(BigDecimal.ZERO)==0){
					recAuto.setCveStatus(CatEstatus.DIFERENCIAS_DETERMINADAS_REVISION_ACLARADAS.getId().intValue());
					CrtPresentacorr presentacion = new CrtPresentacorr();
					presentacion.setCvePresentacorr(cedulaValidacionVO.getCvePresentaCorr());
					CrtPresentacorr presentacionConsulta =presentacionCorreccionDao.findById(cedulaValidacionVO.getCvePresentaCorr(),true);
					System.out.println("Solicitud corrección "+presentacionConsulta.getCveSolicitudcorr());
					CrtSolicitudcorr sol=new CrtSolicitudcorr();
					sol.setCveSolicitudCorr(presentacionConsulta.getCveSolicitudcorr());
					sol=daoSolicitudCorreccion.consultaPorClave(sol);
					System.out.println("sol "+sol.getCveSolicitudCorr());
					sol.setCveStatus(CatEstatus.DIFERENCIAS_DETERMINADAS_REVISION_ACLARADAS.getId().intValue());
					sol.setCveStatusCorreccion(CatEstatus.DIFERENCIAS_DETERMINADAS_REVISION_ACLARADAS.getId().intValue());
					daoSolicitudCorreccion.update(sol);
					daoSeguimientoCorreccion.guardaRecepcion(recAuto);
				}else{
					recAuto.setCveStatus(CatEstatus.DIFERENCIAS_DETERMINADAS_REVISION_ACLARADAS.getId().intValue());
					CrtPresentacorr presentacion = new CrtPresentacorr();
					presentacion.setCvePresentacorr(cedulaValidacionVO.getCvePresentaCorr());
					CrtPresentacorr presentacionConsulta =presentacionCorreccionDao.findById(cedulaValidacionVO.getCvePresentaCorr(),true);
					System.out.println("Solicitud corrección "+presentacionConsulta.getCveSolicitudcorr());
					CrtSolicitudcorr sol=new CrtSolicitudcorr();
					sol.setCveSolicitudCorr(presentacionConsulta.getCveSolicitudcorr());
					sol=daoSolicitudCorreccion.consultaPorClave(sol);
					sol.setCveStatus(CatEstatus.DIFERENCIAS_DETERMINADAS_REVISION_PROCESO_PAGO.getId().intValue());
					sol.setCveStatusCorreccion(CatEstatus.DIFERENCIAS_DETERMINADAS_REVISION_PROCESO_PAGO.getId().intValue());
					daoSolicitudCorreccion.update(sol);
					recAuto.setCveStatus(CatEstatus.DIFERENCIAS_DETERMINADAS_REVISION_PROCESO_PAGO.getId().intValue());
					daoSeguimientoCorreccion.guardaRecepcion(recAuto);
				}				
				rec.setIndAutorizaValSegunda(ConstantesBusiness.ESTATUS_REV_VAL_AUTORIZADO_SUPERV);
				daoSeguimientoCorreccion.guardaRecepcion(rec);
			}
		}
		
		if(flagAutorizar){
			return "Autorización Efectuada";
		}else{
			return "Faltan C\u00e9dulas por autorizar";
		}
	}


	@Override
	public String rechazaCedulaValidacion(CedulaValidacionVO cedulaValidacionVO) {

		CrtRevRecepcion rec=new CrtRevRecepcion();
		rec.setCvePresentacorr(cedulaValidacionVO.getCvePresentaCorr());
		rec.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_REVISION);
		rec=daoSeguimientoCorreccion.consultaRecepcion(rec);
		if(cedulaValidacionVO.getSeccionAutoriza().intValue()==1){			
			rec.setIndAutorizaValPrimera(ConstantesBusiness.ESTATUS_REV_VAL_RECHAZADO_SUPERV);
		}else{
			rec.setIndAutorizaValSegunda(ConstantesBusiness.ESTATUS_REV_VAL_RECHAZADO_SUPERV);
		}	
		daoSeguimientoCorreccion.guardaRecepcion(rec);
		return "C\u00e9dula Rechazada";
	}


	//OGBO se cambia el guardado del estatus de la recepcion (10), pero puede que se regrese al anterior (7,8,9)
	private RecepcionSeguimientoVO defineEstatusPromocion(RecepcionSeguimientoVO  recepcionSeguimientoVO){
		if(recepcionSeguimientoVO.isDocumentacionSustenta() && !recepcionSeguimientoVO.isComprobPagoConvenio() && !recepcionSeguimientoVO.isComprobPresAvisosAfil()){
			//recepcionSeguimientoVO.setCveEstatus(CatEstatus.AUTODETERMINACION_CORRECCION_SIN_RESULTADOS.getId().intValue());
			recepcionSeguimientoVO.setCveEstatus(CatEstatus.PROCESO_DE_REVISION.getId().intValue());
			recepcionSeguimientoVO.setEstatusPresentacionCorr("Autodeterminación Presentada sin resultados");
		}else
		
		//chekbox bante de pago, y en su caso el convenio del tramite del pago diferido o en parcialidades. 
		if(recepcionSeguimientoVO.isDocumentacionSustenta() && !recepcionSeguimientoVO.isComprobanteConvenio() 
				&& ( recepcionSeguimientoVO.isComprobPagoConvenio() || recepcionSeguimientoVO.isComprobPresAvisosAfil() )){
			//recepcionSeguimientoVO.setCveEstatus(CatEstatus.AUTODETERMINACION_CORRECCION_PAGADA.getId().intValue());
			recepcionSeguimientoVO.setCveEstatus(CatEstatus.PROCESO_DE_REVISION.getId().intValue());
			recepcionSeguimientoVO.setEstatusPresentacionCorr("Autodeterminación con Resultados");
		}else
		
		//Comprobante de la presentación de los avisos afiliatorios, derivados de la correción. 
		if(recepcionSeguimientoVO.isDocumentacionSustenta() && recepcionSeguimientoVO.isComprobanteConvenio() 
				&& ( recepcionSeguimientoVO.isComprobPagoConvenio() || recepcionSeguimientoVO.isComprobPresAvisosAfil() ) ){
			//recepcionSeguimientoVO.setCveEstatus(CatEstatus.AUTODETERMINACION_CORRECCION_PROCESO_DE_PAGO.getId().intValue());
			recepcionSeguimientoVO.setCveEstatus(CatEstatus.PROCESO_DE_REVISION.getId().intValue());
			recepcionSeguimientoVO.setEstatusPresentacionCorr("Autodeterminación en Proceso de Pago");
		}
		return recepcionSeguimientoVO;
	}	
	
	
	private int defineEstatusSolCorreccion(RecepcionSeguimientoVO  recepcionSeguimientoVO){
		int estutusRecepcionSolCorr= 0;
		if(recepcionSeguimientoVO.isDocumentacionSustenta() && !recepcionSeguimientoVO.isComprobPagoConvenio() && !recepcionSeguimientoVO.isComprobPresAvisosAfil()){
			estutusRecepcionSolCorr = CatEstatus.AUTODETERMINACION_CORRECCION_SIN_RESULTADOS.getId().intValue();
			//recepcionSeguimientoVO.setEstatusPresentacionCorr("Autodeterminación Presentada sin resultados");
		}else
			
		//chekbox bante de pago, y en su caso el convenio del tramite del pago diferido o en parcialidades. 
		if(recepcionSeguimientoVO.isDocumentacionSustenta() && !recepcionSeguimientoVO.isComprobanteConvenio() 
				&& ( recepcionSeguimientoVO.isComprobPagoConvenio() || recepcionSeguimientoVO.isComprobPresAvisosAfil() )){
			estutusRecepcionSolCorr = CatEstatus.AUTODETERMINACION_CORRECCION_PAGADA.getId().intValue();
			//recepcionSeguimientoVO.setEstatusPresentacionCorr("Autodeterminación con Resultados");
		}else
			
		//Comprobante de la presentación de los avisos afiliatorios, derivados de la correción. 
		if(recepcionSeguimientoVO.isDocumentacionSustenta() && recepcionSeguimientoVO.isComprobanteConvenio() 
				&& ( recepcionSeguimientoVO.isComprobPagoConvenio() || recepcionSeguimientoVO.isComprobPresAvisosAfil() ) ){
			estutusRecepcionSolCorr = CatEstatus.AUTODETERMINACION_CORRECCION_PROCESO_DE_PAGO.getId().intValue();
			//recepcionSeguimientoVO.setEstatusPresentacionCorr("Autodeterminación en Proceso de Pago");
		}
		return estutusRecepcionSolCorr;
	}	
	
	/**
	 * Metodo que actualiza el estado de la recepcion
	 *
	 * @author Gerardo Salazar Vegai
	 * @version 1.0.0
	 */	
	public void guardaEstatusRecepcion(Integer cvePresentaCorr, Integer estatus) {
		CrtRevRecepcion receRecepci = new CrtRevRecepcion();
		receRecepci.setCvePresentacorr(cvePresentaCorr);
		receRecepci.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
		receRecepci=daoSeguimientoCorreccion.consultaRecepcion(receRecepci);
		if(receRecepci!=null){
			receRecepci.setFecFechaReg(new Date());
			receRecepci.setCveStatus(estatus);
		} else {
			//si no existe  se crea una recepcion con tipo pago recepcion
			receRecepci=new CrtRevRecepcion();
			receRecepci.setCvePresentacorr(cvePresentaCorr);
			receRecepci.setIndTipoPago(ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
			receRecepci.setFecFechaReg(new Date());
			receRecepci.setCveStatus(estatus);			
		}
		CrtPresentacorr presenta=new CrtPresentacorr();
		presenta.setCvePresentacorr(cvePresentaCorr);
		daoSeguimientoCorreccion.guardaRecepcion(receRecepci);		
		presenta=daoSeguimientoCorreccion.consultaCrtPresentacorrByPk(presenta);
		Integer cveSolcorr=presenta.getCveSolicitudcorr();
		actualizaEstatusCorreccion(cveSolcorr, estatus);

	}			

	/**
	 * Metodo que actualiza el estado de la solicitud de correccion
	 *
	 * 
	 * @version 1.0.0
	 */	
	public void actualizaEstatusCorreccion(Integer cveSolicitudCorr, Integer estatus){
		CrtSolicitudcorr crtSolicitudcorr = new CrtSolicitudcorr();
		crtSolicitudcorr.setCveSolicitudCorr(cveSolicitudCorr);
		CrtSolicitudcorr crtSolicitudcorrActualizar = daoSolicitudCorreccion.consultaPorClave(crtSolicitudcorr);		
		crtSolicitudcorrActualizar.setCveStatus(estatus);		
		daoSolicitudCorreccion.save(crtSolicitudcorrActualizar);
	}	
	
	private Integer defineEstatusRecepcionCedVal(CedulaValidacionVO cedulaValidacionVO){
		Integer estatusRecepcion = 0;
		Integer numParcialidades = 0 ;
		
		CrtRevPagos pagoTotal = null;
		if(cedulaValidacionVO.getConsolidaImporteVo() != null && cedulaValidacionVO.getConsolidaImporteVo().getNumParcialidades()!=null 
				 && cedulaValidacionVO.getConsolidaImporteVo().getNumParcialidades()!=""){
			numParcialidades = Integer.valueOf(cedulaValidacionVO.getConsolidaImporteVo().getNumParcialidades());
		}else{
			numParcialidades = 0;
		}
		
		if(cedulaValidacionVO.isComprobanteConvenio() && numParcialidades > 1 ){
			estatusRecepcion = CatEstatus.DIFERENCIAS_DETERMINADAS_REVISION_PROCESO_PAGO.getId().intValue();
		}else
		if(cedulaValidacionVO.getConsolidaImporteVo()!= null ){
			
			try {
				pagoTotal = pagosService.getSumarizado(cedulaValidacionVO.getNumFolio(),2,null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(pagoTotal != null){
				logger.info("pagoTotal.getImpCopsp() :" + pagoTotal.getImpCopsp()); //pagados
				logger.info("pagoTotal.getImpRcvsp() : " + pagoTotal.getImpRcvsp()); //pagados
				logger.info("pagoTotal.getImpCoptot() : " + cedulaValidacionVO.getConsolidaImporteVo().getSuertePpalDetCOP()); 
				logger.info("pagoTotal.getImpRcvtot() : " + cedulaValidacionVO.getConsolidaImporteVo().getSuertePpalDetRCV()); 
				if((Double.parseDouble(cedulaValidacionVO.getConsolidaImporteVo().getSuertePpalDetCOP()!=null ? cedulaValidacionVO.getConsolidaImporteVo().getSuertePpalDetCOP():"0.0")>0 
						&& Double.parseDouble(cedulaValidacionVO.getConsolidaImporteVo().getSuertePpalDetRCV())>0) &&
						(pagoTotal.getImpCopsp().compareTo(BigDecimal.ZERO)==0 && pagoTotal.getImpRcvsp().compareTo(BigDecimal.ZERO)==0)){
					estatusRecepcion = CatEstatus.DIFERENCIAS_DETERMINADAS_REVISION_PAGADAS.getId().intValue();
				}
			}
		}
		
		return estatusRecepcion;
	}
}
