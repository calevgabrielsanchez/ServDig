package mx.gob.imss.cit.cda.service.bandeja.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import mx.gob.imss.cit.cda.service.bandeja.entity.specification.AclaracionByTipoTramiteSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.BaseSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.CorreccionByCurpSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.DetalleNSSByNSSSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.InstanciaByEstadoSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.InstanciaBySubdelegacionSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.ParticipanteByAutorizadorSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.ParticipanteByResponsableSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.SolicitudByFechaActualizacionSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.SolicitudByFechaSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.SolicitudByFolioSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.SolicitudByOrigenSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.SolicitudByRangoFechaSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.TareaByIdTareaSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.TramiteActivoSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.TramiteByEstadoSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.TramiteByEstadoVencidoSpecification;
import mx.gob.imss.cit.cda.service.bandeja.entity.specification.TramiteHistoricoSpecification;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class ConsultaBandejaEntity extends AbstractServiceEntity implements ConsultaBandejaLocal {

  private static final Logger logger = LoggerFactory.getLogger(ConsultaBandejaEntity.class);

  public String obtenerCurpPorFolio(String folio) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT corr.refCurp from DitCorreccionDatosAsegurado corr where corr.tramite.ditSolicitud.refFolio = :folio and corr.refCurp is not null ");
    Query query = em.createQuery(sql.toString());
    query.setParameter("folio", folio);
    query.setMaxResults(1);
    String curp = null;
    try {
      curp = (String) query.getSingleResult();
    } catch (NoResultException e) {
      logger.error(
              "---CDA--- Sin resultados la solicitud con folio {} no tiene curp asociada",
              folio);
    } catch (NonUniqueResultException e) {
      logger.error(
              "---CDA--- Sin resultados la solicitud con folio {} no tiene curp asociada",
              folio);
    }

    return curp;
  }

  public String obtenerTipoTramite(Long idTramite) {
    DitTramite tipoTramite = em.find(DitTramite.class, idTramite);
    if (tipoTramite.getDicTipoTramite() != null) {
      return tipoTramite.getDicTipoTramite().getDesTipoTramite();
    } else {
      return "SIN TIPO";
    }
  }

  
  /**
   * Muy chafa usar el DataPage para enviar los criterios, ni modo
   * */
  @SuppressWarnings("unchecked")
  private List<BaseSpecification> prepareSpecificationsConsultaTareas(DataPage dataPage, String usuario,
          String subdelegacion, boolean solicitudes){
            
    HashMap<String, String> filtros = ((List<HashMap<String, String>>)dataPage.getData()).get(0);
    
    List<BaseSpecification> specifications = new ArrayList<BaseSpecification>();            
    
    prepareSpecificationsConsultaTareasStep01(specifications, filtros);
    
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroAutorizo" ) )           ? new ParticipanteByAutorizadorSpecification(filtros.get( "filtroAutorizo" ))               : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroCurp" ) )               ? new CorreccionByCurpSpecification(filtros.get( "filtroCurp" ))                            : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroTramite" ) )            ? new AclaracionByTipoTramiteSpecification(filtros.get( "filtroTramite" ))                  : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroFechaActualizacion" ) ) ? new SolicitudByFechaActualizacionSpecification(filtros.get( "filtroFechaActualizacion" )) : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroVencido" ) )            ? new TramiteByEstadoVencidoSpecification()                                                 : null );

    String asignadas = filtros.get("filtroUsuario");

    if(solicitudes){
      specifications.add( new TareaByIdTareaSpecification() );
      specifications.add( new InstanciaByEstadoSpecification() );
    } else {
      specifications.add( new TareaByIdTareaSpecification() );
    }
    
    prepareSpecificationsConsultaTareasStep02(specifications, usuario, subdelegacion, asignadas);
    
    return specifications;
  
  }
  
  private void prepareSpecificationsConsultaTareasStep01( List<BaseSpecification> specifications, HashMap<String, String> filtros ){
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroFolio" ) )              ? new SolicitudByFolioSpecification(filtros.get( "filtroFolio" ))                           : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroNss" ) )                ? new DetalleNSSByNSSSpecification(filtros.get( "filtroNss" ))                              : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroFechaSolicitud" ) )     ? new SolicitudByFechaSpecification(filtros.get( "filtroFechaSolicitud" ))                  : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroEstado" ) )             ? new TramiteByEstadoSpecification(filtros.get( "filtroEstado" ))                           : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroOrigen" ) )             ? new SolicitudByOrigenSpecification(filtros.get( "filtroOrigen" ))                         : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroResponsable" ) )        ? new ParticipanteByResponsableSpecification(filtros.get( "filtroResponsable" ))            : null );
    specifications.add( StringUtils.isNotBlank( filtros.get( "filtroRangoFecha" ) )         ? new SolicitudByRangoFechaSpecification(filtros.get( "filtroRangoFecha" ))                 : null );
  }
  
  private void prepareSpecificationsConsultaTareasStep02(List<BaseSpecification> specifications, String usuario,
          String subdelegacion, String asignadas){
    
    // FIX: ???? supongo que es para hacer mas eficiente el query
    // al no buscar por subdelegacion ya que es muy costoso en el BLOB
    if (StringUtils.isBlank(asignadas)) {
      usuario = null;
    } else {
      subdelegacion = null;
    }
    
    specifications.add( StringUtils.isNotBlank( usuario ) ? new ParticipanteByResponsableSpecification( usuario ) : null );
    specifications.add( StringUtils.isNotBlank( subdelegacion ) ? new InstanciaBySubdelegacionSpecification( subdelegacion ) : null );
    
    //Eliminamos los nulos
    while(specifications.remove(null));
    
  }

  /**
   * Metodo para obtener una lista de tareas para la bandeja de la aplicacion
   * CDA
   *
   * @param dataPage Criterios utilizados para recuperar la informacion
   * @param usuario Usuario ejecutando la consulta
   * @param subdelegacion Subdelegacion a la que pertenece el usuario ejecutando
   * la operacion
   * @param idsProceso Identificador del proceso asociado a la aplicacion CDA
   * (1)
   *
   * @return Regresa un objeto Datapage con el conjunto de resultados de acuerdo
   * a los criterios (filtros) asi como el bloque de resultados paginados.
   *
   */
  @Override
  public DataPage consultaTareasCDA(DataPage dataPage, String usuario,
          String subdelegacion, Long idsProceso) {
    
    List<BaseSpecification> specifications = prepareSpecificationsConsultaTareas(dataPage, usuario, subdelegacion, true);
    specifications.add( new TramiteActivoSpecification() );
    return ejecutaConsulta(SQLConstants.SQL_SOLICITUDES, dataPage, specifications, idsProceso);
  }
  
  /**
   * Metodo para obtener una lista de tareas para la bandeja de CDA
   * <p>
   */
  private long cantidadTotalConsultaTareasCDA(String sqlBase, List<BaseSpecification> specifications, Long idProceso) {
    

    StringBuffer sql = new StringBuffer();

    sql.append("select to_char( count(1)) from ( ").append(sqlBase);
    for( BaseSpecification specification : specifications ){
      sql.append( " AND ( ").append( specification.prepareSQL()).append(" ) ");
    }
    sql.append(" ) ");
    sql.append(" t");

    logger.debug("[*] ConsultaTotalTareasCDA SQL string :" + sql);
    SQLQuery query = getSession().createSQLQuery(sql.toString());

    addSpecificationsCDA(query, idProceso);

    for( BaseSpecification specification : specifications ){
      specification.setParameter(query);
    }
    String resultado = (String) query.uniqueResult();
    return Long.parseLong(resultado);
  }

  
  
  /**
   * Metodo para obtener una lista de tareas para la bandeja de la aplicacion
   * CDA
   *
   * @param dataPage Criterios utilizados para recuperar la informacion
   * @param usuario Usuario ejecutando la consulta
   * @param subdelegacion Subdelegacion a la que pertenece el usuario ejecutando
   * la operacion
   * @param idsProceso Identificador del proceso asociado a la aplicacion CDA
   * (1)
   *
   * @return Regresa un objeto Datapage con el conjunto de resultados de acuerdo
   * a los criterios (filtros) asi como el bloque de resultados paginados.
   *
   */  
  @Override
  public DataPage consultaTareasHistCDA(DataPage dataPage, String usuario,
          String subdelegacion, Long idsProceso) {
    
    List<BaseSpecification> specifications = prepareSpecificationsConsultaTareas(dataPage, usuario, subdelegacion, false);
    specifications.add( new TramiteHistoricoSpecification() );
    return ejecutaConsulta(SQLConstants.SQL_HISTORICOS, dataPage, specifications, idsProceso);
    
  }
  
  @SuppressWarnings("unchecked")
private DataPage ejecutaConsulta(String sqlBase, DataPage dataPage, List<BaseSpecification> specifications, Long idProceso){

    long maxResults;
    StringBuffer sql = new StringBuffer();
    maxResults = cantidadTotalConsultaTareasCDA(sqlBase, specifications, idProceso);
    logger.debug("valor de maxResults: " + maxResults);


    long totalOfPages = (long) Math.ceil((maxResults * 10 / dataPage.getPageSize()) / 10d);
    dataPage.setData(null);
    if (dataPage.getCurrentPage() > totalOfPages) {
      dataPage.setCurrentPage(totalOfPages);
    }

    if (maxResults > 0) {
      sql.append( sqlBase );      
      
      for( BaseSpecification specification : specifications ){
        sql.append( " AND ( ").append( specification.prepareSQL()).append(" ) ");
      }

      sql.append(" order by sol.cve_id_solicitud desc ");

      logger.debug("[*] ConsultaTareasCDA SQL string :{}" , sql);            
      
      int firstResult = (int) (dataPage.getCurrentPage() - 1) * dataPage.
              getPageSize();      
      int finalResult = (int) (dataPage.getCurrentPage()) * dataPage.
              getPageSize();
      if (dataPage.getPageSize() < maxResults) { 
    	  String consultaMax = SQLConstants.SQL_MAXIMO.replace("MAX_", String.valueOf(finalResult));
    	  sql.replace(0, sql.length(), consultaMax.replace("#", sql.toString()));    	  
      }
      if (firstResult > 0) {
    	  String consultaMin = SQLConstants.SQL_MINIMO.replace("MIN_", String.valueOf(firstResult));
    	  sql.replace(0, sql.length(), consultaMin.replace("#", sql.toString()));  	  
      }

      SQLQuery query = getSession().createSQLQuery(sql.toString());

      addSpecificationsCDA(query, idProceso);

      for( BaseSpecification specification : specifications ){
        specification.setParameter(query);
      }

      dataPage.setData(setDatosComplementarios((List<Object[]>) query.list()));
      logger.debug("[*] Tareas obtenidas size: {}" , dataPage.getData().size());

    }
    dataPage.setTotalOfRecords(maxResults);
    dataPage.setTotalOfPages(totalOfPages);
    logger.debug("[*] Termina consultaTareas CDA : Results: {}, PAGES: {}" , maxResults, totalOfPages);
    return dataPage;
  }

  /* Especificaciones base de CDA */
  private void addSpecificationsCDA (SQLQuery query, Long idProceso) {

    query.setParameter("idTipoSolicitud", TipoSolicitudEnum.CORRECCION_DATOS_ASEGURADO.getValor());
    query.setParameter("idTipoTramite", TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO.getCodigo());
    query.setParameter("proceso", idProceso);
  }

  /* Metodo para completar los datos de la query principal */
  private List<Object[]> setDatosComplementarios (List<Object[]> resultset){

    List <Long> idsInstancia = new ArrayList<Long>();
    List <Long> idsCorreccionDatosAseg = new ArrayList<Long>();

    for(Object[] d : resultset){
      idsInstancia.add(d[SQLConstants.INDEX_CVE_ID_INSTANCIA] != null ? ((BigDecimal) d[SQLConstants.INDEX_CVE_ID_INSTANCIA]).longValue() : null);
      idsCorreccionDatosAseg.add(d[SQLConstants.INDEX_CORRECCION_DATOS_ASEG] != null ? ((BigDecimal) d[SQLConstants.INDEX_CORRECCION_DATOS_ASEG]).longValue() : null);
    }

    if(!idsInstancia.isEmpty() && !idsCorreccionDatosAseg.isEmpty()) {

        List<Object[]> datosCompletos = new ArrayList<Object[]>();

        Map<String, Map<Object, String>> complementos = ejecutarQueries(idsCorreccionDatosAseg, idsInstancia);

        for (Object[] d : resultset) {
            Object[] data = new Object[d.length];
            for (int i = 0; i < d.length; i++) {
                data[i] = d[i];
                if (i == SQLConstants.INDEX_NSS_INVOLUCRADOS) {
                    data[i] = complementos.get(SQLConstants.NSS_INVOLUCRADOS).containsKey(d[SQLConstants.INDEX_CORRECCION_DATOS_ASEG]) ?
                            complementos.get(SQLConstants.NSS_INVOLUCRADOS).get(d[SQLConstants.INDEX_CORRECCION_DATOS_ASEG]) : " ";
                } else if (i == SQLConstants.INDEX_RESPONSABLE) {
                    data[i] = complementos.get(SQLConstants.RESPONSABLES_AUTORIZADORES).containsKey(d[SQLConstants.INDEX_CVE_ID_INSTANCIA]) ?
                            complementos.get(SQLConstants.RESPONSABLES_AUTORIZADORES).get(d[SQLConstants.INDEX_CVE_ID_INSTANCIA]).split("-")[0] : "SIN RESPONSABLE";
                } else if (i == SQLConstants.INDEX_AUTORIZADOR) {
                    data[i] = complementos.get(SQLConstants.RESPONSABLES_AUTORIZADORES).containsKey(d[SQLConstants.INDEX_CVE_ID_INSTANCIA]) ?
                            complementos.get(SQLConstants.RESPONSABLES_AUTORIZADORES).get(d[SQLConstants.INDEX_CVE_ID_INSTANCIA]).split("-")[1] : "SIN AUTORIZADOR";
                } else if (i == SQLConstants.INDEX_MOVIMIENTOS) {
                    data[i] = complementos.get(SQLConstants.MOVIMIENTOS).containsKey(d[SQLConstants.INDEX_CORRECCION_DATOS_ASEG]) ?
                            complementos.get(SQLConstants.MOVIMIENTOS).get(d[SQLConstants.INDEX_CORRECCION_DATOS_ASEG]) : "SIN TIPO";
                }
            }
            datosCompletos.add(data);
        }
        return datosCompletos;

    }else {
        return  null;
    }
  }

  /* Queries para obtener los datos restantes de la consulta principal nss involucrados, responsables, autorizadores y movimientos */
  private Map<String, Map<Object, String>> ejecutarQueries (List<Long> idsCorreccionDatosAseg, List<Long> idsInstancia){

    Map<String, Map<Object, String>> resultMap = new HashMap<String, Map<Object, String>>();

    SQLQuery sqlQuery = getSession().createSQLQuery(SQLConstants.SQL_NSS_INVOLUCRADOS);
    sqlQuery.setParameterList("idsCorreccionDatosAseg", idsCorreccionDatosAseg);
    resultMap.put(SQLConstants.NSS_INVOLUCRADOS, setResults(sqlQuery));

    sqlQuery = getSession().createSQLQuery(SQLConstants.SQL_RESPONSABLES_AUTORIZADORES);
    sqlQuery.setParameterList("idsInstancia", idsInstancia);
    resultMap.put(SQLConstants.RESPONSABLES_AUTORIZADORES, setResults(sqlQuery));

    sqlQuery = getSession().createSQLQuery(SQLConstants.SQL_MOVIMIENTOS);
    sqlQuery.setParameterList("idsCorreccionDatosAseg", idsCorreccionDatosAseg);
    resultMap.put(SQLConstants.MOVIMIENTOS, setResults(sqlQuery));

    return resultMap;
  }

  @SuppressWarnings("unchecked")
  private Map<Object, String> setResults(SQLQuery sqlQuery){

    Map<Object, String> result = new HashMap<Object, String>();
    for(Object[] d : (List<Object[]>) sqlQuery.list()){
      result.put(d[0], (String) d[1]);
    }

    return result;
  }

}
