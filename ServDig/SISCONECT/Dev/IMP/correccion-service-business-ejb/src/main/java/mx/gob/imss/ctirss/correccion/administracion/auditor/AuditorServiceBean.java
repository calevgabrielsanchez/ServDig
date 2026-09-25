package mx.gob.imss.ctirss.correccion.administracion.auditor;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.administracion.auditor.ejb.AuditorServiceRemote;
import mx.gob.imss.ctirss.correccion.administracion.service.dao.AuditorDAOLocal;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.service.AbstractService;
import mx.gob.imss.ctirss.correccion.invitacion.service.dao.InvitacionDAOLocal;
import mx.gob.imss.ctirss.correccion.invitacion.service.ejb.InvitacionServiceRemote;
import mx.gob.imss.ctirss.correccion.model.CrtAuditorAsignado;
import mx.gob.imss.ctirss.correccion.model.CrtCorrPromInvita;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao.PromocionDAOLocal;
import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.dao.SolicitudCorreccionDAOLocal;

@Stateless(name="auditorService", mappedName = "auditorService")
public class AuditorServiceBean <T extends AbstractModel> extends AbstractService implements AuditorServiceRemote<T>{

	@EJB AuditorDAOLocal<T> daoAuditor;
	@EJB InvitacionDAOLocal<T> daoInvitacion;
	@EJB PromocionDAOLocal<T> daoPromocion;
	@EJB SolicitudCorreccionDAOLocal daoSolicitudCorreccion;
	@EJB InvitacionServiceRemote invitacionService;
	
	@Override
	public DatosSalidaPaginador<T> paginarPromocion(DatosEntradaPaginador<T> params) {
		return daoAuditor.paginaPromocion(params);
	}

	@Override
	public DatosSalidaPaginador<T> paginarInvitacion(DatosEntradaPaginador<T> params) {
		return daoAuditor.paginaInvitacion(params);
	}

	@Override
	public DatosSalidaPaginador<T> paginarSolicitud(DatosEntradaPaginador<T> params) {
		return daoAuditor.paginaSolicitud(params);
	}

	@Override
	public DatosSalidaPaginador<T> paginaAuditoresDisponibles(DatosEntradaPaginador<T> params) {
		return daoAuditor.paginaAuditoresDisponibles(params);
	}

	@Override
	public DatosSalidaPaginador<T> paginaCarga(DatosEntradaPaginador<T> params) {
		return daoAuditor.paginaCarga(params);
	}

	@Override
	public T agregar(T model) {		
		CrtAuditorAsignado auditorAsigando = (CrtAuditorAsignado)model;
		// Validacion que no exista id en la tabla crt_asignar_auditor
		CrtAuditorAsignado auxAuditorAsignado = new CrtAuditorAsignado();
		
		if(auditorAsigando.getCveInvitacion() != null){ // Valida la invitacion			
			model=asignaAudInvitacion(model);			
		} else if(auditorAsigando.getCvePromocion() != null){ // Valida la promocion
			model=asignaAudPromocion(model);
		} else if(auditorAsigando.getCveSolicitudCorr() != null){ // Valida la solicitud
			model=asignaAudCorreccion(model);			
		}
		
		return model;
	}

	/**
	 * Metodo para asignar auditor a la invitacion
	 * 
	 * @author Gerardo Salazar Vega
	 * @return T model
	 */
	private T asignaAudInvitacion (T model) {
		CrtAuditorAsignado auditorAsigando = (CrtAuditorAsignado)model;
		CrtAuditorAsignado auxAuditorAsignado = new CrtAuditorAsignado();
	
		auxAuditorAsignado.setCveInvitacion(auditorAsigando.getCveInvitacion());
		T auditorModel = (T)auxAuditorAsignado;
		auditorModel = daoAuditor.buscarAuditorAsignado(auditorModel);
		if(auditorModel == null){
			
			//insertar el id auditor asignado en la invitacion
			CrtInvitacion invitacion = new CrtInvitacion();
			invitacion.setCveInvitacion(BigDecimal.valueOf(auditorAsigando.getCveInvitacion()));
			T invitacionModel = (T) invitacion;
			invitacionModel = daoInvitacion.consultaPorClave(invitacionModel);
			if(invitacionModel != null){
				invitacion = (CrtInvitacion) invitacionModel;
//				invitacion.setCveAuditorAsignado(auditorAsigando.getCveAuditor().longValue());
				invitacion.setCveAuditorAsignado(auditorAsigando.getCveAuditorUsuarioAsignado());
				daoInvitacion.guardar(invitacion);				
				CrtSolicitudcorr solicitud = invitacionService.obtieneInvitaSolCorr(invitacion.getCveInvitacion());
				if(solicitud != null) {
//					solicitud.setCveAuditorAsignado(auditorAsigando.getCveAuditor().longValue());
					solicitud.setCveAuditorAsignado(auditorAsigando.getCveAuditorUsuarioAsignado());
					daoSolicitudCorreccion.save(solicitud);			
					((CrtAuditorAsignado)model).setCveSolicitudCorr(solicitud.getCveSolicitudCorr().longValue());
				}
				CrtPromocion promocion=null;
				if (invitacion.getCvePromocion()!=null) {
					promocion = new CrtPromocion();
					promocion.setCvePromocion(invitacion.getCvePromocion().longValue());
					promocion = (CrtPromocion) daoPromocion.consultaPorClave((T) promocion);
				} else {
					promocion = invitacionService.obtienePromocionInvita(invitacion.getCveInvitacion());
				}
				if (promocion!=null) {
//					promocion.setCveAuditorAsignado(auditorAsigando.getCveAuditor().longValue());
					promocion.setCveAuditorAsignado(auditorAsigando.getCveAuditorUsuarioAsignado());
					daoPromocion.guardar((T)promocion);
					((CrtAuditorAsignado)model).setCvePromocion(promocion.getCvePromocion());
				}
			}
			model = daoAuditor.agregar(model);
		} else model = null;
		return model;
	}

	/**
	 * Metodo para asignar auditor a la promocion
	 * 
	 * @author Gerardo Salazar Vega
	 * @return T model
	 */
	private T asignaAudPromocion (T model) {
		CrtAuditorAsignado auditorAsigando = (CrtAuditorAsignado)model;
		CrtAuditorAsignado auxAuditorAsignado = new CrtAuditorAsignado();
		
		auxAuditorAsignado.setCvePromocion(auditorAsigando.getCvePromocion());
		T auditorModel = (T)auxAuditorAsignado;
		auditorModel = daoAuditor.buscarAuditorAsignado(auditorModel);
		if(auditorModel == null){			
			//insertar el id auditor asignado en la promocion
			CrtPromocion promocion = new CrtPromocion();
			promocion.setCvePromocion(auditorAsigando.getCvePromocion());
			T promocionModel = (T) promocion;
			promocionModel = daoPromocion.consultaPorClave(promocionModel);
			if(promocionModel != null){
				promocion = (CrtPromocion) promocionModel;
//				promocion.setCveAuditorAsignado(auditorAsigando.getCveAuditor().longValue());
				promocion.setCveAuditorAsignado(auditorAsigando.getCveAuditorUsuarioAsignado());
				daoPromocion.agrega((T) promocion);			
			}
			CrtInvitacion invitacion = new CrtInvitacion();			
			invitacion = invitacionService.obtieneInvitaPromocion(auditorAsigando.getCvePromocion());
			if (invitacion!=null) {
//				invitacion.setCveAuditorAsignado(auditorAsigando.getCveAuditor().longValue());
				invitacion.setCveAuditorAsignado(auditorAsigando.getCveAuditorUsuarioAsignado());
				daoInvitacion.guardar(invitacion);
				((CrtAuditorAsignado)model).setCveInvitacion(invitacion.getCveInvitacion().longValue());
			}
			
			model = daoAuditor.agregar(model);
		} else model = null;
		
		return model;	
	}

	/**
	 * Metodo para asignar auditor a la correccion
	 * 
	 * @author Gerardo Salazar Vega
	 * @return T model
	 */
	private T asignaAudCorreccion (T model) {
		CrtAuditorAsignado auditorAsigando = (CrtAuditorAsignado)model;
		CrtAuditorAsignado auxAuditorAsignado = new CrtAuditorAsignado();
		
		auxAuditorAsignado.setCveSolicitudCorr(auditorAsigando.getCveSolicitudCorr());
		T auditorExistente = (T)auxAuditorAsignado;
		auditorExistente = daoAuditor.buscarAuditorAsignado(auditorExistente);
		if(auditorExistente == null){
			model = daoAuditor.agregar(model);
			//insertar el id auditor asignado en la promocion
			CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
			solicitud.setCveSolicitudCorr(auditorAsigando.getCveSolicitudCorr().intValue());
			solicitud = (CrtSolicitudcorr) daoSolicitudCorreccion.consultaPorClave(solicitud);
			if(solicitud != null){
//				solicitud.setCveAuditorAsignado(auditorAsigando.getCveAuditor().longValue());
				solicitud.setCveAuditorAsignado(auditorAsigando.getCveAuditorUsuarioAsignado());
				daoSolicitudCorreccion.save(solicitud);			
			}
		} else model = null;
		
		return model;	
	}
	
	@Override
	public Integer buscaPatronAnexo(Integer id) {
		return daoAuditor.buscaPatronAnexo(id);
	}

	@Override
	public T buscaPorCveUsuario(T model) {
		return daoAuditor.buscaPorCveUsuario(model);
	}

	@Override
	public DatosSalidaPaginador<T> paginaReasignarAuditoresDisponibles(DatosEntradaPaginador<T> params) {
		return daoAuditor.paginaReasignarAuditoresDisponibles(params);
	}

	@Override
	public T buscarAsignado(T model) {
		CrtAuditorAsignado auditor = (CrtAuditorAsignado) daoAuditor.buscarAsignado(model);
		if(auditor != null){
			auditor.setFecFechaAsignacionFin(new Date());
			T auditorModel = (T)auditor;
			daoAuditor.agregar(auditorModel);
		}
		
		CrtAuditorAsignado auditornuevo = (CrtAuditorAsignado) daoAuditor.agregar(model);
		
		if(auditornuevo.getCveInvitacion() != null){
			CrtInvitacion invitacion = new CrtInvitacion();
			invitacion.setCveInvitacion(BigDecimal.valueOf(auditornuevo.getCveInvitacion()));
			T invitacionModel = (T)invitacion;
			invitacionModel = this.daoInvitacion.consultaPorClave(invitacionModel);
			if(invitacionModel != null){
				invitacion = (CrtInvitacion) invitacionModel;
//				invitacion.setCveAuditorAsignado(auditornuevo.getCveAuditor().longValue());
				invitacion.setCveAuditorAsignado(auditornuevo.getCveAuditorUsuarioAsignado());
				daoInvitacion.guardar(invitacion);
				auditornuevo.setFolio(invitacion.getNuFolioInvitacion());
				CrtSolicitudcorr solicitud = invitacionService.obtieneInvitaSolCorr(BigDecimal.valueOf(auditornuevo.getCveInvitacion()));
				if(solicitud != null){
//					solicitud.setCveAuditorAsignado(auditornuevo.getCveAuditor().longValue());
					solicitud.setCveAuditorAsignado(auditornuevo.getCveAuditorUsuarioAsignado());
					daoSolicitudCorreccion.save(solicitud);			
					((CrtAuditorAsignado)model).setCveSolicitudCorr(solicitud.getCveSolicitudCorr().longValue());
				}
				CrtPromocion promocion=null;
				if (invitacion.getCvePromocion()!=null) {
					promocion = new CrtPromocion();
					promocion.setCvePromocion(invitacion.getCvePromocion().longValue());
					promocion = (CrtPromocion) daoPromocion.consultaPorClave((T) promocion);
				} else {
					promocion = invitacionService.obtienePromocionInvita(invitacion.getCveInvitacion());
				}
				if (promocion!=null) {
//					promocion.setCveAuditorAsignado(auditornuevo.getCveAuditor().longValue());
					promocion.setCveAuditorAsignado(auditornuevo.getCveAuditorUsuarioAsignado());
					daoPromocion.guardar((T)promocion);
					((CrtAuditorAsignado)model).setCvePromocion(promocion.getCvePromocion());
				}
			}
		}if(auditornuevo.getCvePromocion() != null){
			CrtPromocion promocion = new CrtPromocion();
			promocion.setCvePromocion(auditornuevo.getCvePromocion());
			T promocionModel = (T) promocion;
			promocionModel = this.daoPromocion.consultaPorClave(promocionModel);
			if(promocionModel != null){
				promocion = (CrtPromocion) promocionModel;
//				promocion.setCveAuditorAsignado(auditornuevo.getCveAuditor().longValue());
				promocion.setCveAuditorAsignado(auditornuevo.getCveAuditorUsuarioAsignado());
				daoPromocion.agrega((T) promocion);
				auditornuevo.setFolio(promocion.getNuFoliopromocion());
			}
		}if(auditornuevo.getCveSolicitudCorr() != null){
			CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
			solicitud.setCveSolicitudCorr(auditornuevo.getCveSolicitudCorr().intValue());
			solicitud = (CrtSolicitudcorr) this.daoSolicitudCorreccion.consultaPorClave(solicitud);
			if(solicitud != null){
//				solicitud.setCveAuditorAsignado(auditornuevo.getCveAuditor().longValue());
				solicitud.setCveAuditorAsignado(auditornuevo.getCveAuditorUsuarioAsignado());
				daoSolicitudCorreccion.save(solicitud);
				auditornuevo.setFolio(solicitud.getNuFolio());
			}
		}
		T modelAuditor = (T) auditornuevo;
		return modelAuditor;
	}

}
