package mx.imss.estrados.service.ejb.cron;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import mx.imss.estrados.entity.NeeCatProceso;
import mx.imss.estrados.entity.NeeCatStatus;
import mx.imss.estrados.entity.NeeCatTipodocumento;
import mx.imss.estrados.entity.NeeDocumentosAdjuntos;
import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;
import mx.imss.estrados.repository.AbstractRespository;
import mx.imss.estrados.utils.NotificacionHelper;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

@Stateless
public class EjecutaTareasCronDAOImpl extends AbstractRespository implements EjecutaTareasCronDAO {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(EjecutaTareasCronDAOImpl.class);
	
	@Override
	public List<NeeNotificaciones> consultaNotificacionesAModificar(Integer idStatus) {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		
		
		// Publicacion de notificaciones
		
//		String dateString = "03/07/2019";
//		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//		Date result = null;
//		try {
//			result = df.parse(dateString);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		criteria.add(Restrictions.eq("neeCatStatus.cveStatus", idStatus)).add(Restrictions.eq("fecPublicacion", result));
		
		criteria.add(Restrictions.eq("neeCatStatus.cveStatus", idStatus)).add(Restrictions.eq("fecPublicacion", new Date()));
		
		// Publicacion de notificaciones
		
		
		List<NeeNotificaciones> lista = criteria.list();
		List<NeeNotificaciones> listaNoti = new ArrayList<NeeNotificaciones>();
		for(NeeNotificaciones noti : lista ){
			NotificacionHelper helper = new NotificacionHelper();
			
			noti.setNotificacionesDTO(helper.setterNotificacionesEntityToNotificacionesDTO(noti));
			listaNoti.add(noti);
		}
		
	
		return listaNoti;
	}
	
	@Override
	public List<NeeNotificaciones> consultaNotificacionesARetirar(Integer idStatus) {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		
		// Retiro de notificaciones
		
//		String dateString = "03/07/2019";
//		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//		Date result = null;
//		try {
//			result = df.parse(dateString);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		criteria.add(Restrictions.eq("neeCatStatus.cveStatus", idStatus)).add(Restrictions.eq("fecRetiroPublicacion", result));
		
		criteria.add(Restrictions.eq("neeCatStatus.cveStatus", idStatus)).add(Restrictions.eq("fecRetiroPublicacion", new Date()));

		List<NeeNotificaciones> lista = criteria.list();
		List<NeeNotificaciones> listaNoti = new ArrayList<NeeNotificaciones>();
		for(NeeNotificaciones noti : lista ){
			NotificacionHelper helper = new NotificacionHelper();
			
			noti.setNotificacionesDTO(helper.setterNotificacionesEntityToNotificacionesDTO(noti));
			listaNoti.add(noti);
		}
		
		
		return listaNoti;
	}
	
	@Override
	public NeeDocumentosAdjuntos consultaDoctoAdjunto(long idNotificacion, int idTipoAdjunto){
		Criteria criteria = getSession().createCriteria(NeeDocumentosAdjuntos.class);
		criteria.add(Restrictions.eq("neeNotificaciones.cveNotificaciones", idNotificacion))
		.add(Restrictions.eq("neeCatTipoAdjunto.cveTipoAdjunto", idTipoAdjunto));
		return (NeeDocumentosAdjuntos) criteria.uniqueResult();
	}
	
	@Override
	public SsoVwUsuario consultarDatosUsuario(String curp){
		Criteria criteria = getSession().createCriteria(SsoVwUsuario.class);
		System.out.println("Entra 1 CURP");
		criteria.add(Restrictions.eq("desUsrCURP", curp));
		return (SsoVwUsuario) criteria.uniqueResult();
	}

	@Override
	public NeeCatTipodocumento consultaTipoDocumento(Integer idDocumento) {
		Criteria criteria = getSession().createCriteria(NeeCatTipodocumento.class);
		criteria.add(Restrictions.eq("cveTipodocto", idDocumento));
		return (NeeCatTipodocumento) criteria.uniqueResult();
	}

	@Override
	public NeeCatProceso consultaProceso(Integer idProceso) {
		Criteria criteria = getSession().createCriteria(NeeCatProceso.class);
		criteria.add(Restrictions.eq("cveProceso", idProceso));
		return (NeeCatProceso) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NeeNotificaciones> consultaNotificacionesAModificarPorFecha(
			Integer idBusqueda, Date fecha) {
		Criteria criteria = getSession().createCriteria(NeeNotificaciones.class);
		
		if (idBusqueda == 1) {
			criteria.add(Restrictions.eq("neeCatStatus.cveStatus", idBusqueda)).add(Restrictions.eq("fecPublicacion", fecha));
		} else {
			criteria.add(Restrictions.eq("neeCatStatus.cveStatus", idBusqueda)).add(Restrictions.eq("fecRetiroPublicacion", fecha));
		}

		List<NeeNotificaciones> lista = criteria.list();
//		NeeCatStatus estatus = new NeeCatStatus();
//		estatus = obtenerEStatus(idCambio);
		
//		for(NeeNotificaciones notif : lista){
//			notif.setNeeCatStatus(estatus);
//			getSession().update(notif);
//			getSession().flush();
//		}
//	
//		return lista.size();
		
		List<NeeNotificaciones> listaNoti = new ArrayList<NeeNotificaciones>();
		for(NeeNotificaciones noti : lista ){
			NotificacionHelper helper = new NotificacionHelper();
			
			noti.setNotificacionesDTO(helper.setterNotificacionesEntityToNotificacionesDTO(noti));
			listaNoti.add(noti);
		}
	
		return listaNoti;
	}
	
	public List<Date> obtenerFechasConNotificacionesPendientes(
			Integer idBusqueda) {		
		
		String campo;
		if (idBusqueda == 1) {
			campo = "fec_publicacion";
		} else {
			campo = "fec_retiro_publicacion";
		}
		
		String query = "select distinct " + campo + " from nee_notificaciones" + 
				" where " + campo + " <= ? " +
				" and cve_status = ?" +
				" order by " + campo;
				
		SQLQuery consulta = getSession().createSQLQuery(query);
		consulta.setDate(0, new Date());
		consulta.setInteger(1, idBusqueda);		
		
		List<Object> lista = consulta.list();
		List<Date> resultados = new ArrayList<Date>(lista.size());
		
		for (Object fecha: lista) {
			resultados.add((Date)fecha);
		}
		
		return resultados;
	}
	
	/**
	 * Metodo para obtener de la base de datos el Status
	 * @param idEstatus
	 * @return
	 */
	private NeeCatStatus obtenerEStatus(Integer idEstatus) {
		Criteria criteria = getSession().createCriteria(NeeCatStatus.class);
		criteria.add(Restrictions.eq("cveStatus", idEstatus));
		return (NeeCatStatus) criteria.uniqueResult();
	}
	

}
