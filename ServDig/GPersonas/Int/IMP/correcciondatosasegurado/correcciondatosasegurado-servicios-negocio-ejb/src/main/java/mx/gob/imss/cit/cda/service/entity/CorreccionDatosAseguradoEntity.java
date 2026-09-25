package mx.gob.imss.cit.cda.service.entity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.cda.service.utility.TramiteCDAUtilityLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicOrigenCapturaNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatosAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitDatosLaborales;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

@Stateless
public class CorreccionDatosAseguradoEntity extends AbstractServiceEntity implements CorreccionDatosAseguradoLocal{
	
	@EJB
	TramiteCDAUtilityLocal utility;
	
	private static Logger logger = LoggerFactory.getLogger(CorreccionDatosAseguradoEntity.class);

	/**
	 * Metodo para persistir la solicitud
	 */
	@Override
	public TramiteCorreccionCurp almacenarTramiteCDA(Long idTramite, String curp) {
		DitCorreccionDatosAsegurado ditCorreccionDatosAsegurado = obtenerTramiteCorreccion(idTramite, curp);
		if(ditCorreccionDatosAsegurado == null){
			ditCorreccionDatosAsegurado = new DitCorreccionDatosAsegurado();
			DitTramite tramite = new DitTramite();
			tramite.setCveIdTramite(idTramite);
			ditCorreccionDatosAsegurado.setTramite(tramite);
			ditCorreccionDatosAsegurado.setRefCurp(curp);
			ditCorreccionDatosAsegurado.setFecRegistroAlta(new Date());
		}		
		
		ditCorreccionDatosAsegurado.setFecRegistroActualizado(new Date());
        ditCorreccionDatosAsegurado=em.merge(ditCorreccionDatosAsegurado);
		return utility.convertirEntityToModel(ditCorreccionDatosAsegurado);
	}
        
        /**
         * Metodo para persistir los datos laborales
         * @param idTramiteCorreccion
         * @param datosLaborales
         */
        @Override
        public void almacenaDatosLaborales(Long idTramiteCorreccion, DatosLaborales datosLaborales){
            DitDatosLaborales ddla = new DitDatosLaborales();
            DitCorreccionDatosAsegurado dcda = new DitCorreccionDatosAsegurado();
            dcda.setCveIdCorreccionDatosAsegurado(idTramiteCorreccion);
            
            ddla.setDitCorreccionDatosAsegurado(dcda);
            ddla.setNombrePatron(datosLaborales.getNombrePatron());            
            ddla.setCveEnt(datosLaborales.getEntidadFederativa().getClave());
            ddla.setRefFecInscripcion(datosLaborales.getFechaInscripcion());
            ddla.setRefFecBaja(datosLaborales.getFechaBaja());
            ddla.setRefRegistroPatronal(datosLaborales.getNrp());
            ddla.setDesDomicilio(datosLaborales.getDomicilio());
            ddla.setDesActividad(datosLaborales.getActividad());
            ddla.setFecRegistroAlta(new Date());
            ddla.setFecRegistroActualizado(new Date());
            
            em.persist(ddla);
        }

	/**
	 * Metodo para saber si existe una solicitud asociada al CURP que se ingreso  
	 */
	@Override
	public Long getIdTramiteActivo(List<String> curps, List<Integer> estadosValidos){
		StringBuffer sql = new StringBuffer();
		sql.append("select correccion from DitCorreccionDatosAsegurado correccion ");
		sql.append("where correccion.tramite.dicEstadoTramite.cveIdEstadoTramite in (:estadosTramite) and correccion.refCurp in(:curps) ");
		sql.append("and correccion.tramite.dicTipoTramite.cveIdTipoTramite=:cveTipoTramite ");
		if(estadosValidos.contains(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo())){
			sql.append("and correccion.tramite.ditSolicitud.fecRegistroAlta > :fechaLimite ");
		}
		sql.append(" order by correccion.fecRegistroAlta desc ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("curps", curps);
		query.setParameter("estadosTramite",estadosValidos);
		query.setParameter("cveTipoTramite", TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo());
		if(estadosValidos.contains(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo())){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); 
			c.add(Calendar.DATE, -DIAS_MAXIMOS_ESPERA);
			query.setParameter("fechaLimite",c.getTime());
		}
		List<DitCorreccionDatosAsegurado> result = null;
		Long resultFinal = null;
		try{
			result = (List<DitCorreccionDatosAsegurado>)query.getResultList();
			resultFinal = result.size()>=1 ? obtenerIdFinal(result) : null;
			log.debug("---CDA----El ID  a regresar es: "+resultFinal);
			return resultFinal;
		}catch(NoResultException nre){
			log.debug("--CDA-- No se pudo encontrar un idTramite asociado a la CURP");	
		}
		return resultFinal;
	}
	
	private DitCorreccionDatosAsegurado obtenerTramiteCorreccion(Long idTramite, String curp){
		StringBuffer sql = new StringBuffer();
		sql.append("select correccion from DitCorreccionDatosAsegurado correccion ");
		sql.append("where correccion.tramite.dicEstadoTramite.cveIdEstadoTramite in (:estadosTramite)  ");
		if(StringUtils.isNotBlank(curp)){
			sql.append(" and correccion.refCurp=:curp ");
		}
		sql.append("and correccion.tramite.dicTipoTramite.cveIdTipoTramite=:cveTipoTramite ");
		sql.append("and correccion.tramite.cveIdTramite=:cveIdTramite ");
		Query query = em.createQuery(sql.toString());
		if(StringUtils.isNotBlank(curp)){
			query.setParameter("curp", curp);
		}
		query.setParameter("estadosTramite", 
				Arrays.asList(EstadoTramiteEnum.INICIADO.getCodigo()));
		query.setParameter("cveTipoTramite", TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo());
		query.setParameter("cveIdTramite", idTramite);
		
				
		return (query.getResultList() != null && query.getResultList().size() > 0)
				? (DitCorreccionDatosAsegurado) query.getSingleResult() : null;
	}
	
	
	public DicEstadoTramite consultarEstadoTramiteById(Long idTramite){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tramite.dicEstadoTramite FROM DitTramite tramite ");
		sql.append("WHERE tramite.dicTipoTramite.cveIdTipoTramite = :cveTipoTramite ");
		sql.append("AND tramite.cveIdTramite = :cveIdTramite ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("cveTipoTramite", TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo());
		query.setParameter("cveIdTramite", idTramite);
		
		return (DicEstadoTramite)query.getSingleResult();
		
	}
	
        @Override
	public DitDetalleNss bloquearNSS(String nss, Long idCorreccionDatosAseg, Long idOrigen){
		DitDetalleNss detalleNss = new DitDetalleNss();
                DicOrigenCapturaNssCda origen = new DicOrigenCapturaNssCda();
		DitCorreccionDatosAsegurado correccionDatos = new DitCorreccionDatosAsegurado();
		correccionDatos.setCveIdCorreccionDatosAsegurado(idCorreccionDatosAseg);
		
		detalleNss.setNss(nss);
		detalleNss.setCorreccionDatosAsegurado(correccionDatos);
		detalleNss.setFecRegistroAlta(new Date());
		detalleNss.setFecRegistroActualizado(new Date());
                origen.setCveDetalleNss(idOrigen);
		detalleNss.setDicOrigenCapturaNssCda(origen);
		em.merge(detalleNss);
		return detalleNss;
	}
	
	public DitDetalleNss consultarBloqueoNSS(String nss){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT detalle FROM DitDetalleNss detalle ");
		sql.append("WHERE detalle.Nss = :nss ");
		sql.append("AND detalle.correccionDatosAsegurado.tramite.dicTipoTramite.cveIdTipoTramite = :cveTipoTramite ");
		sql.append("AND detalle.correccionDatosAsegurado.tramite.dicEstadoTramite.cveIdEstadoTramite in (:estadosTramite) ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nss", nss);
		query.setParameter("cveTipoTramite", TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo());
		query.setParameter("estadosTramite", 
				Arrays.asList(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo(), 
						EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo(), 
						EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo(),
						EstadoTramiteEnum.ERROR_SINDO.getCodigo(), 
						EstadoTramiteEnum.ENVIA_CERTIFICACION_SINDO.getCodigo(),
						EstadoTramiteEnum.PROCESADO_SINDO.getCodigo(),
						EstadoTramiteEnum.SIN_RESPONSABLE.getCodigo()));
		
		DitDetalleNss detalle = null;
		try {
			detalle = (DitDetalleNss)query.getSingleResult();
		} catch (NoResultException e) {
			logger.error("---CDA--- Sin resultados el NSS {} no esta bloqueado", nss);
		}catch (NonUniqueResultException e) {
			logger.error("---CDA--- Sin resultados el NSS {} no esta bloqueado", nss);
		}
		
		return detalle;
	}
	
	//TODO insertar metodo guardado solicitar info aqui
	public int insertarResponableAutorizadorCorreccion (Long idTramite, int tipoUsr){
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("update DIT_CORRECCION_DATOS_ASEG set IND_TIPO_SOLICITUD_INFO = :cveTipoUsr where CVE_ID_TRAMITE = :cveIdTramite");
		
		
		javax.persistence.Query query = em.createNativeQuery(sql.toString());
		
			query.setParameter("cveIdTramite", idTramite);
	        query.setParameter("cveTipoUsr", tipoUsr);
		
	        query.executeUpdate();
	        
	        int numSolicitudesActualizadas = query.executeUpdate();
            log.error("Se actualizaron los sig numero de solicitudes: " + numSolicitudesActualizadas);
			return numSolicitudesActualizadas;
	        
			
	}
	
	public String obtenerResponsableTramiteCDA(Long idSolicitud){		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT solicitud.cveIdUsuario FROM DitSolicitud solicitud ");
		sql.append("WHERE solicitud.cveIdSolicitud = :idSolicitud ");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("idSolicitud", idSolicitud);
		
		String usuarioResponsable = (String)query.getSingleResult();
		
		return usuarioResponsable;
		
	}
	
	public String obtenerResponsableTramiteCDA(String folio){		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT solicitud.cveIdUsuario FROM DitSolicitud solicitud ");
		sql.append("WHERE solicitud.refFolio = :folio ");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("folio", folio);
		
		String usuarioResponsable;
		try {
			usuarioResponsable = (String)query.getSingleResult();
		} catch (NoResultException e) {
			logger.error("---CDA--- Sin resultados para folio " + folio, e);
			usuarioResponsable = null;
		} catch (NonUniqueResultException e) {
			logger.error("---CDA--- Sin resultados para folio " + folio, e);
			usuarioResponsable = null;
		}
		
		return usuarioResponsable;
		
	}
	
	public void actualizarSubdelegacionSolicitud (Solicitud sol){
		StringBuffer sql = new StringBuffer();
		
		sql.append("update DIT_SOLICITUD set CVE_ID_SUBDELEGACION = :subdelegacion where CVE_ID_SOLICITUD = :idSolicitud");
		
		
		javax.persistence.Query query = em.createNativeQuery(sql.toString());
		
			query.setParameter("subdelegacion", sol.getSubdelegacion().getId());
	        query.setParameter("idSolicitud", sol.getSolicitudId());
		
	        
	        
	        int numSolicitudesActualizadas = query.executeUpdate(); 
            log.debug("Se actualizaron los sig numero de solicitudes: " + numSolicitudesActualizadas);
	}
	
	public Long obtenerIdFinal(List<DitCorreccionDatosAsegurado> result){
		Long resultFinal=null;
		Map<Long, Long> iniciados = new HashMap<Long, Long>();
		log.debug("Total de registros encontrados: "+result.size());
		if(result.size()>1){
			for(int i=0; i<result.size(); i++){
				if(result.get(i).getTramite().getDicEstadoTramite().getCveIdEstadoTramite() == 1){
					iniciados.put(result.get(i).getTramite().getDicEstadoTramite().getCveIdEstadoTramite(),result.get(i).getTramite().getCveIdTramite());
				}
			}
			if(iniciados.size()>1){
				resultFinal = 0L;
			}else if(iniciados.size() == 1){
				resultFinal = iniciados.get(1L);
			}else if(iniciados.isEmpty()){
				resultFinal = result.get(0).getTramite().getCveIdTramite();
			} 
		}else if(result.size()==1){
			resultFinal = result.get(0).getTramite().getCveIdTramite();	
		}
		return resultFinal;
	}
}
