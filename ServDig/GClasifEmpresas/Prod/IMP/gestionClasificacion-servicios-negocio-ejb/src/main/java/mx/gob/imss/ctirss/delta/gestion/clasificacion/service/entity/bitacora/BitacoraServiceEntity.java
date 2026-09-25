/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:BitacoraServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora;

import static mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.RECTIFICACION_DE_LA_CLASIFICACION_POR_PROCESO_DE_ANALISIS;
import static mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.RECTIFICACION_POR_ERROR_EN_EL_ANALISIS;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora.BitacoraServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacora;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosBitacoras;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstatusAnalisis;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DivSolicitudConcluida;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class BitacoraServiceEntity extends AbstractServiceEntity implements BitacoraServiceEntityLocal{

    private static final Logger log = LoggerFactory.getLogger(BitacoraServiceEntity.class);
	
	@EJB
	private BitacoraServiceUtilityLocal bitacoraUtility;

    @EJB
    private ActividadEcServiceRemote clasificacionActividadEconomicaService;

	private static String SQL_SELECT_BUSQUEDA_BITACORA = null;
	static{
		StringBuffer sb = new StringBuffer();
		sb.append("select hea.cveIdUsuarioSso, eace.desCausasAnalisis, hea.desComentario, ");
		sb.append(" to_char(hea.stpHistEstatusAnalisis,'DD/MM/YYYY HH24:MI'), ");
		sb.append(" CONCAT(CONCAT(pg.regPatron, pg.ditPatronSujetoObligado.dicModalidad.numModalidad), pg.digVer), ");
		sb.append(" hea.cveHistEstatusAnalisis, hea.ditAnalisisCe.cveIdAnalisis, ");
		sb.append(" t.dicTipoTramite.cveIdTipoTramite, tt.desTipoTramite, eace.cveIdEstatusAnalisis ");		
		sb.append(" from DitAnalisisCe ace, DicEstatusAnalisisCe eace, DitHistEstatusAnalisis hea, ");
		sb.append(" DitPatronGeneral pg, DitTramite t, DitTramitePatSujObligado tpso, ");
		sb.append(" DicTipoTramite tt ");
		sb.append(" where ace.cveIdAnalisis = hea.ditAnalisisCe.cveIdAnalisis ");
		sb.append(" and eace.cveIdEstatusAnalisis = hea.dicEstatusAnalisisCe.cveIdEstatusAnalisis ");
		sb.append(" and t.ditSolicitud.cveIdSolicitud = ace.cveIdSolicitud ");
		sb.append(" and tpso.id.cveIdTramite = t.cveIdTramite ");
		sb.append(" and tt.cveIdTipoTramite = t.dicTipoTramite.cveIdTipoTramite ");
		sb.append(" and pg.ditPatronSujetoObligado.cveIdPatronSujetoObligado = tpso.ditPatronSujetoObligado.cveIdPatronSujetoObligado ");		
		SQL_SELECT_BUSQUEDA_BITACORA = sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ElementoBitacora> buscaRegistros(FiltrosBitacoras filtrosBitacoras)
			throws Exception {
		List<Object[]> response= null;
		List<ElementoBitacora> lista = new ArrayList<ElementoBitacora>();
		ElementoBitacora e = new ElementoBitacora();
		try{
			String strquery  = componeQueryBusqueda(filtrosBitacoras);
			//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
			log.info("Query de consulta="+ strquery);
			Query query = this.getSession().createQuery(strquery.toString());
			response = query.list();
			for (Object[] obj : response) {
				e = new ElementoBitacora();
				// No se incluyen estatus asignados por el sistema
				if(obj[9] != null && Integer.parseInt(obj[9].toString()) !=  EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave()
						&& Integer.parseInt(obj[9].toString()) !=  EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()
						&& Integer.parseInt(obj[9].toString()) !=  EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()){
					e.setUsuario((String) obj[0]);
					e.setAcccionRealizada((String) obj[1]);
					e.setComentario((String) obj[2]);
					e.setFecha((String) obj[3]);
					e.setRegistroPatronal((String) obj[4]);
					lista.add(e);
				}
			}
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}	
		return lista;
	}

	private String componeQueryBusqueda(FiltrosBitacoras filtrosBitacoras){
		String query="";
		if(filtrosBitacoras!=null){
			if(filtrosBitacoras.getUsuario()!=null && filtrosBitacoras.getUsuario().trim().length()>0)
				query += " and upper(hea.cveIdUsuarioSso)='"+filtrosBitacoras.getUsuario().toUpperCase()+"' ";
			
			if (null != filtrosBitacoras.getPeriodoInicio() && null != filtrosBitacoras.getPeriodoFin()) {
				query += " and (trunc(hea.stpHistEstatusAnalisis) BETWEEN to_date('"
					  +	Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtrosBitacoras.getPeriodoInicio()) + "','yyyy-mm-dd') and to_date('"
					  + Constantes.FORMATO_FECHA_YYYY_MM_DD.format(filtrosBitacoras.getPeriodoFin()) + "','yyyy-mm-dd') )";
			}
			if(filtrosBitacoras.getDelegacion()!=null && filtrosBitacoras.getDelegacion().intValue()>-1)
				query += " and hea.dicSubdelegacion.dicDelegacion.cveIdDelegacion = "+ filtrosBitacoras.getDelegacion();
			if(filtrosBitacoras.getSubDelegacion()!=null && filtrosBitacoras.getSubDelegacion().intValue()>-1)
				query += " and hea.dicSubdelegacion.cveIdSubdelegacion= "+ filtrosBitacoras.getSubDelegacion();
			if(filtrosBitacoras.getCveIdGrupoAnalisisCe() != null){
				query += " and ace.dicGrupoAnalisisCe.cveIdGrupoAnalisisCe = " + filtrosBitacoras.getCveIdGrupoAnalisisCe();
			}
			//Se agrega esta condici�n para evitar mostrar en excel los tr�mites generados al crear CLEM o rechazar 
			query += " and t.dicTipoTramite.cveIdTipoTramite not in ("
				+ RECTIFICACION_DE_LA_CLASIFICACION_POR_PROCESO_DE_ANALISIS.getCodigo()
				+ ", "
				+ RECTIFICACION_POR_ERROR_EN_EL_ANALISIS.getCodigo()
				+ ") ";
			query += " order by hea.ditAnalisisCe.cveIdAnalisis, hea.stpHistEstatusAnalisis asc ";
		}
		query = SQL_SELECT_BUSQUEDA_BITACORA + query;
		return query;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ElementoBitacora> buscaComentariosDetalle(Long cveIdAnalisis, String cveUsuario)throws Exception {
		List<ElementoBitacora> response= null;
		try{
			StringBuffer hql = new StringBuffer();
			//Obtendra el nombre del campo clave y el campo descripcion del modelo pasado como parametro
			hql.append("select new mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacora ");		
			hql.append(" ( h.cveIdUsuarioSso,  e.desCausasAnalisis, h.desComentario, to_char(h.stpHistEstatusAnalisis ,'DD/MM/YYYY HH24:MI'), " +
					"      e.cveIdEstatusAnalisis) ");			
			hql.append("from DicEstatusAnalisisCe e, DitHistEstatusAnalisis h ");
			hql.append("where h.dicEstatusAnalisisCe.cveIdEstatusAnalisis = e.cveIdEstatusAnalisis ");	
			hql.append("  and h.ditAnalisisCe.cveIdAnalisis = "+cveIdAnalisis.toString()+" ");
			hql.append(" order by h.cveHistEstatusAnalisis asc ");
			Query query = this.getSession().createQuery(hql.toString());
			response = query.list();
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException(re);
		}	
		return response;
	}
	
	public EstatusAnalisisModel guardaBitacora(EstatusAnalisisModel model) 
			throws PersistenceException, ClasificacionException {
		log.debug("guardando bitacora [" + model  +"]");
		try {
			if(model.getComentario() != null && model.getComentario().length() > Constantes.LONGITUD_COMENTARIOS){
				log.debug("Error: El campo de comentarios es mayor a "+Constantes.LONGITUD_COMENTARIOS+" caracteres, longitud: " + model.getComentario().length());				
				throw new ClasificacionException("Error: El campo de comentarios es mayor a "+Constantes.LONGITUD_COMENTARIOS+" caracteres", 1);
			}						
			DitHistEstatusAnalisis entity = bitacoraUtility.convertirModelToEntity(model);
			Timestamp timestamp = new Timestamp(new Date().getTime());	
			entity.setStpHistEstatusAnalisis(timestamp);
			em.persist(entity);
			log.debug("ID del registro de bitacora [" + entity.getCveHistEstatusAnalisis()  +"]" );			
		} catch(ClasificacionException e) {
			log.error(e.getMessage());
			throw e;			
		} catch(Exception e) {
			log.error(e.getMessage());
			throw new PersistenceException(e);
		}
		
		return model;
	}
	
	@SuppressWarnings("unchecked")
	public EstatusAnalisisModel buscaClasificacionInicial(Long idAnalisis) throws PersistenceException{
		EstatusAnalisisModel model = null;
		List<DitHistEstatusAnalisis> lista = new ArrayList<DitHistEstatusAnalisis>();
		try{
			javax.persistence.Query query = em
					.createQuery(" from DitHistEstatusAnalisis hea " +
							" where hea.ditAnalisisCe.cveIdAnalisis = " + idAnalisis +
							" and hea.dicEstatusAnalisisCe.cveIdEstatusAnalisis = " + EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave() +
							" order by hea.stpHistEstatusAnalisis asc ");
			lista = query.getResultList();
			if(lista != null && lista.size() > 0){
                DitHistEstatusAnalisis entity = lista.get(0);
				model = bitacoraUtility.convertirEntityToModel(entity);
			}else{
				log.error("no hay registros");
			}
		}catch (Exception exc){
			log.error("Error en m\u00E9todo buscarClasificacionInicial");
			log.error(exc.getMessage());
			throw new PersistenceException(exc);
		}
		return model;
	}
	public String fraccionMovAnt (Long idAnalisis){
		DitHistEstatusAnalisis entity = null;
		List<DitHistEstatusAnalisis> lista = new ArrayList<DitHistEstatusAnalisis>();
		String fraccionPropuesta= null;
		
			javax.persistence.Query query = em
					.createQuery(" from DitHistEstatusAnalisis hea " +
							" where hea.ditAnalisisCe.cveIdAnalisis = " + idAnalisis +
							" and hea.dicEstatusAnalisisCe.cveIdEstatusAnalisis in ( " +
							 EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO.getClave() + " , " +
							 EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO_Y_RECHAZADO.getClave() +
							" ) order by hea.stpHistEstatusAnalisis desc ");
			lista = query.getResultList();
			if(lista.size() > 0){
				entity = lista.get(0);
				if(entity.getDicFraccionPro()!=null) {
				fraccionPropuesta = bitacoraUtility.obtenerFraccion(entity.getDicFraccionPro());
				}
				
			}else{
				log.error("no hay registros"); 
			}
		return fraccionPropuesta;
	}

    public DitClasificacion findDitClasificacion(DitHistEstatusAnalisis entity) {
        Session session = getSession();
        //Con el id analisis se busca la solicitud
        DivSolicitudConcluida divSolicitud = (DivSolicitudConcluida) session.createCriteria(DivSolicitudConcluida.class)
            .add(Restrictions.eq("cveIdAnalisis", new BigDecimal(entity.getDitAnalisisCe().getCveIdAnalisis())))
            .uniqueResult();
        //de la solicutd se usa el registro patronal para obtener el Patron General
        String regPatron = divSolicitud.getRegPatron();
        DitPatronGeneral ditPatronGeneral = (DitPatronGeneral) session.createCriteria(DitPatronGeneral.class)
            .add(Restrictions.ilike("regPatron", regPatron))
            .uniqueResult();
        //del patron general se obtiene el patron sujeto obligado
        DitPatronSujetoObligado ditPatronSujetoObligado = ditPatronGeneral.getDitPatronSujetoObligado();
        //del patron sujeto obligado se obtiene la clasificacion actual
        DitClasificacion ditClasificacion = (DitClasificacion) session.createCriteria(DitClasificacion.class)
                .add(Restrictions.isNull("fecRegistroBaja"))
                .add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado"
                            , ditPatronSujetoObligado.getCveIdPatronSujetoObligado()))
                .uniqueResult();
        return ditClasificacion;
    }

	@SuppressWarnings("unchecked")
	@Override
	public Clasificacion consultaClasificacionActualPorHistorico(Long idAnalisis)throws PersistenceException{
		javax.persistence.Query query=em.createQuery(
				" from DitHistEstatusAnalisis h "
				+ " where h.ditAnalisisCe.cveIdAnalisis= :idAnalisis "
				+ "order by h.cveHistEstatusAnalisis asc");
		query.setParameter("idAnalisis", idAnalisis);
		List<DitHistEstatusAnalisis> lstHistAnalisis=new ArrayList<DitHistEstatusAnalisis>();
		DitHistEstatusAnalisis ditHistEstatusAnalisis=new DitHistEstatusAnalisis();//=(DitHistEstatusAnalisis)query.getSingleResult();
		lstHistAnalisis=(List<DitHistEstatusAnalisis>)query.getResultList();
		ditHistEstatusAnalisis=lstHistAnalisis.get(0);
		Clasificacion clasificacion=new Clasificacion();
        Fraccion fraccion = null;
        try {
            fraccion = clasificacionActividadEconomicaService.obtenerFraccionClaseActiva(ditHistEstatusAnalisis.getDicFraccionDec().getCveIdFraccion());
        }
        catch(GestionPatronalBusinessException e) {
            log.warn("{}", e.getMessage(), e);
        }
        clasificacion.setFraccion(fraccion);
		return clasificacion;
	}
}
