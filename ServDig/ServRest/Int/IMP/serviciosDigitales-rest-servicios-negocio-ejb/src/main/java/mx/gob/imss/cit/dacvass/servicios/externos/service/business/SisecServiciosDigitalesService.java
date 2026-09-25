package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec.ResumenAseguradoTramiteCda;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec.TramitesAbiertosAseguradoSisecResponse;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.asegurado.AseguradoServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISisecServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.cit.semanascotizadas.aclaracion.model.DatosRelacionLaboralReconocidaModel;
import mx.gob.imss.cit.semanascotizadas.aclaracion.services.RelacionLaboralServiceRemote;
import mx.gob.imss.cit.semanascotizadas.certificacion.services.CuentaIndividualServiceRemote;
import mx.gob.imss.cit.semanascotizadas.common.constants.SemanasCotizadasConstants;
import mx.gob.imss.cit.semanascotizadas.common.exception.SemanasCotizadasException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;



@Stateless(name = "sisecServiciosDigitalesService", mappedName = "sisecServiciosDigitalesService")
public class SisecServiciosDigitalesService implements ISisecServiciosDigitalesServiceRemote{
	
	private static final Logger log = LoggerFactory.getLogger(SisecServiciosDigitalesService.class);
	
	@EJB
	private AseguradoServiceEntityLocal aseguradoEntity;
	
	@EJB(mappedName = "relacionLaboralBussines")
	private RelacionLaboralServiceRemote servicioRelacionLaboralSisec;
	@EJB(mappedName="cuentaIndividualServiceBusiness")
	private CuentaIndividualServiceRemote servicioCuentaIndividual;
	
	@EJB(mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;
	
	@EJB( mappedName = "serviceBusiness")
	private ServiceBusinessRemote serviceBusiness;
	

	@Override
	public List<ResumenAseguradoTramiteCda> getResumenAseguradoTramiteCda(String refCurp, String nss)
				throws ServiciosRestException {
		log.debug("llege la la consulta de resumen de asegurado CDA y pase validacones " + refCurp);
		ValidacionesComunesUtil.validaEstructuraNSS(nss);
		refCurp=ValidacionesComunesUtil.validaEstructuraCurp(refCurp);
		try {
			return aseguradoEntity.getResumenAseguradoTramiteCda(refCurp, nss);
		}catch(Exception e) {
			log.error("corruio un error al consumir el entity getResumenAseguradoTramiteCda" + refCurp , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "ocurrio un erro al consultarlos tramites de CDA asegurado " +  nss);
		}
		
	}
	
	@Override
	public TramitesAbiertosAseguradoSisecResponse validaTramiteSisecAseguradoByEstado(String nss, Long estadoTramite)
				throws ServiciosRestException {
		log.debug("llege la la consulta de tramites abiertos SISEC consultaTramitesAbiertosSisec " + nss);
		ValidacionesComunesUtil.validaEstructuraNSS(nss);
		ValidacionesComunesUtil.validaIdCatalogo(estadoTramite, "El estado del tramite no puede ser nulo");
		TramitesAbiertosAseguradoSisecResponse respuestaSisec = new TramitesAbiertosAseguradoSisecResponse();
		respuestaSisec.setIndicadorTramitesAbierto(false);
		respuestaSisec.setMensaje("Sin tramites abiertos");
		List<Long> idsEstados = new ArrayList<Long>();
        Long idTipoSolicitudImss;
        Long idTipoSolicitudIsste;
        List<Long> solicitudesImss;
        List<Long> solicitudesIsste;
        idsEstados.add(Long.valueOf(estadoTramite));
        idTipoSolicitudImss = SemanasCotizadasConstants.SOLICITUD_ACLARACION_SEM_COTIZADAS_IMSS;
        idTipoSolicitudIsste = SemanasCotizadasConstants.SOLICITUD_ACLARACION_SEM_COTIZADAS_ISSSTE;
        Solicitud solicitdSISEC = new Solicitud();
		try {
			/* Validacion datos del asegurado */
			AsignacionNSS asegurado= serviceBusiness.obtenerAseguradoPorNss(nss);
			if(asegurado == null ) {
				log.debug(" no se localizo el asegurado en BDTU " + nss);
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo404, ErrorResponseBean.codigo404Descripcion,
						"No se encontro al informacion del Asegurado con el NSS " + nss , "No se encontro  informacion del Asegurado con el NSS " + nss ));
			}
			log.debug("el asegurado tiene el cveIdPersona " + asegurado.getIdPersona());
			    solicitudesImss = solicitudBusiness.encontrarSolicitudesPorPersonaYEstadosDeTramite(asegurado.getIdPersona(), idsEstados,
		                idTipoSolicitudImss);
			    if(solicitudesImss != null && !solicitudesImss.isEmpty()) {
			    	solicitdSISEC.setSolicitudId(solicitudesImss.get(0));
			    	solicitdSISEC = solicitudBusiness.consultar(solicitdSISEC);
			    	respuestaSisec.setIndicadorTramitesAbierto(true);
			    	respuestaSisec.setMensaje(solicitdSISEC.getTipoSolicitud().getDescripcion() + "," 
			    			+solicitdSISEC.getTramites().get(0).getTipoTramite().getDescripcion() );
			    	return respuestaSisec;
			    }
			    solicitudesIsste = solicitudBusiness.encontrarSolicitudesPorPersonaYEstadosDeTramite(asegurado.getIdPersona(), idsEstados,
		                idTipoSolicitudIsste);
			    if(solicitudesIsste != null && !solicitudesIsste.isEmpty()) {
			    	solicitdSISEC.setSolicitudId(solicitudesIsste.get(0));
			    	solicitdSISEC = solicitudBusiness.consultar(solicitdSISEC);
			    	respuestaSisec.setIndicadorTramitesAbierto(true);
			    	respuestaSisec.setMensaje(solicitdSISEC.getTipoSolicitud().getDescripcion() + "," 
			    			+solicitdSISEC.getTramites().get(0).getTipoTramite().getDescripcion() );
			    	return respuestaSisec;
			    }
			return respuestaSisec;
		}catch(ServiciosRestException e) {
			throw e;
		}catch(Exception e) {
			log.error("corruio un error al consultar los tramites de sisec en BDTU" + nss , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "corruio un error al consultar los tramites de sisec en BDTU " +  nss);
		}
		
	}
	

	@Override
	public List<DatosRelacionLaboralReconocidaModel> getPeriodosAprobadosByTipoTramiteSisec(String nss, String refCurp, Long tipoTramite)
			throws ServiciosRestException {
		log.debug("llege a la consulta periodos agregados  getPeriodosAprobadosByTipoTramiteSisec " + nss);
		ValidacionesComunesUtil.validaEstructuraNSS(nss);
		refCurp =ValidacionesComunesUtil.validaEstructuraCurp(refCurp);
		ValidacionesComunesUtil.validaIdCatalogo(tipoTramite, "tipoTramite");;
		if(tipoTramite !=1 && tipoTramite !=2 && tipoTramite !=3) {
			log.debug("el tipo de tramite no es valido" + tipoTramite);
			throw ValidacionesComunesUtil.getServiciosRestException("el tipo de tramite no es valido ");
		}
		try {
			log.debug("antes de invocar el servicios de SISEC");
			List<DatosRelacionLaboralReconocidaModel> lstRealcionLaboral =  servicioRelacionLaboralSisec.getRelacionesLaboralesReconocidasPorTipoRelacionLaboral(nss, refCurp, tipoTramite);
			log.debug("regrese de la llamada del servicio de SISEC");
			return lstRealcionLaboral;
		}catch(SemanasCotizadasException e) {
			log.error("corruio un error al consumir el servicio de  getRelacionesLaboralesReconocidasPorTipoRelacionLaboral " + nss , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "corruio un error al consumir el servicio de  getRelacionesLaboralesReconocidasPorTipoRelacionLaboral " +  nss);
		}catch(Exception e) {
			log.error("corruio un error no especificado en  getRelacionesLaboralesReconocidasPorTipoRelacionLaboral " + nss , e);
			throw ValidacionesComunesUtil.getServiciosRestException(e, "corruio un error no especificado en en getRelacionesLaboralesReconocidasPorTipoRelacionLaboral " +  nss);
	
		}
	}

	
	

}
