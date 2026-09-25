package mx.gob.imss.cit.cda.service.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import mx.gob.imss.cit.cda.service.cuentaindividual.utility.CuentaIndividualUtilityLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.Page;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.enums.OrigenPeriodoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoEstadoMovimientoEnviadoSindoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionPeriodoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitCtaIndNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class CuentaIndividualEntity extends AbstractServiceEntity implements CuentaIndividualLocal {

    @EJB
    CuentaIndividualUtilityLocal cuentaIndividualUtilityLocal;

    private static Logger logger = LoggerFactory.getLogger(CuentaIndividualEntity.class);
    
    
    @Override
    public List<CuentaIndividualVO> obtenerListaNSS(Long cveIdTramite) {
        logger.debug("obtenerListaNSS");
        logger.error("Antes de persistir la cuenta: {}", cveIdTramite);
        List<CuentaIndividualVO> listaVO = new ArrayList<CuentaIndividualVO>();
        TypedQuery<DitCtaIndNssCda> queryListUniqueNssTramite = em
                .createNamedQuery("ditCtaIndNssCda.listPeriodosPorIdTramite",
                        DitCtaIndNssCda.class);
        queryListUniqueNssTramite.setParameter("idTramite", cveIdTramite);
        List<DitCtaIndNssCda> listaNss = queryListUniqueNssTramite
                .getResultList();
        logger.error("Se obtuvieron resultados: {}", listaNss.size());
        for (DitCtaIndNssCda registro : listaNss) {
            listaVO.add(cuentaIndividualUtilityLocal
                    .convertEntityToModel(registro));
        }
        return listaVO;
    }

    @Override
    public boolean existenDatosIdtramite(Long cveIdTramite) {
        logger.debug("obtenerListaNSS");
        logger.error("Buscando información previa para: {}", cveIdTramite);
        Query existeInformacion = em.createNamedQuery(
                "ditCtaIndNssCda.existeInformacionPorIdTramite");
        existeInformacion.setParameter("idTramite", cveIdTramite);
        return ((Long) existeInformacion.getSingleResult()).intValue() > 0;
    }
    
    /**
     *  Metodo para persistir la entidad que representa cada periodo
     * @param periodo
     * @return 
     */
    @Override
    public DitCtaIndNssCda guardarPeriodo(DitCtaIndNssCda periodo) {
        logger.debug("guardarCuentaIndividual");
        logger.error("Antes de persistir la cuenta: {}", periodo.getNss());
        em.persist(periodo);
        em.flush();
        return periodo;
    }
    
    /**
     *  Metodo para actualizar la entidad que representa cada periodo
     * @param periodo
     */
    @Override
    public void actualizarPeriodo(DitCtaIndNssCda periodo) {
        logger.debug("guardarCuentaIndividual");
        logger.error("Antes de persistir la cuenta: {}", periodo.getNss());
        em.merge(periodo);
    }
    
    /**
     *  Metodo para conocer si por cada NSS con su clave existen registros en tabla de cuenta individual
         select cta.* from dit_detalle_nss_cda dd, dit_cta_ind_nss_cda cta 
        where cta.CVE_ID_DETALLE_NSS_CDA =dd.CVE_ID_DETALLE_NSS_CDA
        and cta.CVE_ID_DETALLE_NSS_CDA=5475
     * @param cveNssDetalle
     * @return 
     */
    @Override
    public Long getNumeroPeriodosPorNss(Long cveNssDetalle) {
        StringBuffer sql = new StringBuffer();
        
        sql.append(" Select count(a) from DitCtaIndNssCda a, DitDetalleNss b ");
        sql.append(" where b.cveDetalleNss=a.ditDetalleNss.cveDetalleNss ");
        sql.append(" and a.ditDetalleNss.cveDetalleNss= :cveNssDetalle ");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveNssDetalle", cveNssDetalle);
        
        Long result = (Long) query.getSingleResult();

        return result;
    }
    
    /**
     *  Metodo para obtener lista de peridos persisitidos por ventanilla
           select d.* from dit_cta_ind_nss_cda d, DIT_CORRECCION_CTA_IND_CDA b where d.CVE_ID_DETALLE_NSS_CDA=5478 AND d.CVE_ID_ORIGEN_PERIODO_CTA_IND=2
            and d.fec_registro_baja is null and b.FEC_MOV_ENV_SINDO is null and (b.CVE_ID_ESTADO_MOV_ENV_SINDO is null or  b.CVE_ID_ESTADO_MOV_ENV_SINDO = 3)
            and b.CVE_ID_CTA_IND_OPER_ORIGEN=d.CVE_ID_CTA_IND ;
     * @param cveNssDetalle
     * @return 
     */
    @Override
    public List<DitCtaIndNssCda> getPeriodosGuardadosVentanilla(Long cveNssDetalle) {        
        StringBuilder sql = new StringBuilder();
        logger.debug("--CDACI---Se obtienen registros guardados en CDA");
        sql.append(" SELECT d from DitCtaIndNssCda d , DitCorreccionCtaIndCda c ");
        sql.append(" WHERE d.ditDetalleNss.cveDetalleNss = :cveNssDetalle AND d.dicOrigenCtaIndCda.cveIdOrigenPeridoCtaInd = :origen ");
//        sql.append(" AND d.fecRegistroBaja IS NULL AND c.fecMovEnvSindo IS NULL AND (c.cveIdEstadoMovSindo IS NULL OR c.cveIdEstadoMovSindo = :edoSindo) ");
        sql.append(" AND d.fecRegistroBaja IS NULL AND (c.cveIdEstadoMovSindo IS NULL OR c.cveIdEstadoMovSindo = :edoSindo) ");
        sql.append(" AND c.cveIdCtaIndOperOrigen.cveIdCtaInd= d.cveIdCtaInd ");
        sql.append(" ORDER BY d.cveIdCtaInd asc");

        Query query = em.createQuery(sql.toString());
        logger.debug("--CDACI---clave ",cveNssDetalle);
        query.setParameter("cveNssDetalle", cveNssDetalle);
        query.setParameter("origen",OrigenPeriodoEnum.VENTANILLA.getId());
        query.setParameter("edoSindo",TipoEstadoMovimientoEnviadoSindoEnum.ERROR_SINDO.getId());
        
        return (List<DitCtaIndNssCda>) query.getResultList();
    }
    
    @Override
    public void bajaPeriodo(DitCtaIndNssCda periodo){
        logger.debug("Registro a dar de baja: {}", periodo.getCveIdCtaInd());
        periodo.setFecRegistroBaja(Calendar.getInstance().getTime());
        em.merge(periodo);
    }
    
    @Override
    public List<DitCtaIndNssCda> getPeriodosGuardadosIndividual(Long cveNssDetalle) {        
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT cor from DitCtaIndNssCda cor ");        
        sql.append(" WHERE cor.ditDetalleNss.cveDetalleNss = :cveNssDetalle ");
        sql.append(" AND cor.dicOrigenCtaIndCda.cveIdOrigenPeridoCtaInd = :origen"); // valida fechaRegistroBaja = null
        sql.append(" AND cor.fecRegistroBaja is NULL  ");        
        sql.append(" ORDER BY TO_DATE(cor.fecIniMov, 'DD/MM/YYYY') DESC ");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveNssDetalle", cveNssDetalle);
        query.setParameter("origen",OrigenPeriodoEnum.CUENTA_INDIVIDUAL.getId());
        
        return (List<DitCtaIndNssCda>) query.getResultList();
    }

    @Override
    public List<DitCtaIndNssCda> getPeriodosGuardados(Long cveNssDetalle) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT cta from DitCtaIndNssCda cta ");
        sql.append(" WHERE cta.ditDetalleNss.cveDetalleNss = :cveNssDetalle ");
        sql.append(" AND cta.dicOrigenCtaIndCda.cveIdOrigenPeridoCtaInd = :origen");
        sql.append(" ");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveNssDetalle", cveNssDetalle);
        query.setParameter("origen", OrigenPeriodoEnum.VENTANILLA.getId());

        return (List<DitCtaIndNssCda>) query.getResultList();
    }
    
    
    @Override
    public List<DitCorreccionCtaIndCda> getPeriodosEliminados(Long cveNssDetalle) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT cor from DitCorreccionCtaIndCda cor ");        
        sql.append(" WHERE cor.cveIdCtaIndOperOrigen.ditDetalleNss.cveDetalleNss = :cveNssDetalle ");        
        sql.append(" AND cor.fecRegistroBaja is NULL  ");
        sql.append(" AND cor.cveIdMovOperOrigen.cveIdMovCorreccion = :idMovOperacionOrigen ");
        sql.append(" AND cor.cveIdMovOperDestino.cveIdMovCorreccion is null");
        sql.append(" ORDER BY TO_DATE(cor.cveIdCtaIndOperOrigen.fecIniMov, 'DD/MM/YYYY') DESC ");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveNssDetalle", cveNssDetalle);        
        query.setParameter("idMovOperacionOrigen", TipoRegularizacionPeriodoEnum.ELIMINAR.getId() );        

        return (List<DitCorreccionCtaIndCda>) query.getResultList();
    }
    
    @Override
    public List<DitCorreccionCtaIndCda> getPeriodosAgregados(Long cveNssDetalle) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT cor from DitCorreccionCtaIndCda cor ");        
        sql.append(" WHERE cor.cveIdCtaIndOperOrigen.ditDetalleNss.cveDetalleNss = :cveNssDetalle ");        
        sql.append(" AND cor.fecRegistroBaja is NULL  ");
        sql.append(" AND cor.cveIdMovOperOrigen.cveIdMovCorreccion = :idMovOperacionOrigen ");    
        sql.append(" ORDER BY TO_DATE(cor.cveIdCtaIndOperOrigen.fecIniMov, 'DD/MM/YYYY') DESC ");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveNssDetalle", cveNssDetalle);        
        query.setParameter("idMovOperacionOrigen", TipoRegularizacionPeriodoEnum.AGREGAR.getId() );        

        return (List<DitCorreccionCtaIndCda>) query.getResultList();
    }

    @Override
    public List<DitCorreccionCtaIndCda> getPeriodosPorOperacionOrigen(Long cveNssDetalle, String idMovOperacionOrigen, String idMovOperacionDestino) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT cor from DitCorreccionCtaIndCda cor ");        
        sql.append(" WHERE cor.cveIdCtaIndOperOrigen.ditDetalleNss.cveDetalleNss = :cveNssDetalle ");        
        sql.append(" AND cor.fecRegistroBaja is NULL  ");
        sql.append(" AND cor.cveIdMovOperOrigen.cveIdMovCorreccion = :idMovOperacionOrigen ");
        sql.append(" AND cor.cveIdMovOperDestino.cveIdMovCorreccion = :idMovOperacionDestino");
        sql.append(" ORDER BY TO_DATE(cor.cveIdCtaIndOperOrigen.fecIniMov, 'DD/MM/YYYY') DESC ");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveNssDetalle", cveNssDetalle);        
        query.setParameter("idMovOperacionOrigen", idMovOperacionOrigen);
        query.setParameter("idMovOperacionDestino", idMovOperacionDestino);

        return (List<DitCorreccionCtaIndCda>) query.getResultList();
    }
    @Override
    public List<DitCorreccionCtaIndCda> getPeriodosPorOperacionDestino(Long cveNssDetalle, String idMovOperacionOrigen, String idMovOperacionDestino) {
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT cor from DitCorreccionCtaIndCda cor ");        
        sql.append(" WHERE cor.cveIdCtaIndOperDestino.ditDetalleNss.cveDetalleNss = :cveNssDetalle ");
        sql.append(" AND cor.fecRegistroBaja is NULL  ");
        sql.append(" AND cor.cveIdMovOperOrigen.cveIdMovCorreccion = :idMovOperacionOrigen ");
        sql.append(" AND cor.cveIdMovOperDestino.cveIdMovCorreccion = :idMovOperacionDestino");
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveNssDetalle", cveNssDetalle);        
        query.setParameter("idMovOperacionOrigen", idMovOperacionOrigen);
        query.setParameter("idMovOperacionDestino", idMovOperacionDestino);

        return (List<DitCorreccionCtaIndCda>) query.getResultList();
    }
    
    @Override
    public Long findbyNss(Long cveDetalleNss, String nss){
        Long idDetalle = null;
        StringBuilder sql = new StringBuilder();
        
        sql.append(" SELECT detalle FROM DitDetalleNss detalle, DitDetalleNss detalle2");
        sql.append(" WHERE detalle.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado = detalle2.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado ");
        sql.append(" AND detalle2.cveDetalleNss = :cveDetalleNss");  
        sql.append(" AND detalle.nss = :nss");  
        
        Query query = em.createQuery(sql.toString());
        query.setParameter("cveDetalleNss", cveDetalleNss);
        query.setParameter("nss", nss);
        
        DitDetalleNss ditDetalleNss = (DitDetalleNss) query.getSingleResult();
        if(ditDetalleNss != null){
            idDetalle = ditDetalleNss.getCveDetalleNss();
        }
        return idDetalle;
    }

  // Nuevos servicios de cuenta individual  
    
    
  @Override  
  public List<CuentaIndividualRegistroPatronal> getPeriodosRegistroPatronalByIdDetalle(
          Long cveIdDetalleNssCda) {
    //final String sql ="SELECT CVE_REGISTRO_PATRONAL, CVE_MODALIDAD, NOM_RAZON_SOCIAL, CVE_CIZ, CVE_DELEGACION_ORIGEN, TO_CHAR(MIN_FEC_INI_MOV,'DD/MM/YYYY') FEC_INI_MOV, TO_CHAR(MAX_FEC_FIN_MOV,'DD/MM/YYYY') FEC_FIN_MOV from( SELECT CVE_REGISTRO_PATRONAL, CVE_MODALIDAD, NOM_RAZON_SOCIAL, CVE_CIZ, CVE_DELEGACION_ORIGEN, MIN(TO_DATE(FEC_INI_MOV, 'DD/MM/YYYY' )) MIN_FEC_INI_MOV, MAX(TO_DATE(FEC_FIN_MOV, 'DD/MM/YYYY' )) MAX_FEC_FIN_MOV from DIT_CTA_IND_NSS_CDA WHERE CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda AND FEC_REGISTRO_BAJA is NULL group by CVE_REGISTRO_PATRONAL, CVE_MODALIDAD, NOM_RAZON_SOCIAL, CVE_CIZ, CVE_DELEGACION_ORIGEN ) AUX order by MAX_FEC_FIN_MOV DESC";
    final String sql = "SELECT AUX.CVE_REGISTRO_PATRONAL, CVE_MODALIDAD, NOM_RAZON_SOCIAL, CVE_CIZ, CVE_DELEGACION_ORIGEN, TO_CHAR(MIN_FEC_INI_MOV, 'DD/MM/YYYY') FEC_INI_MOV, TO_CHAR(MAX_FEC_FIN_MOV, 'DD/MM/YYYY') FEC_FIN_MOV, nvl(NUEVOS, 0) c1,  nvl(INCLUIDOS, 0) c3 , nvl(ELIMINADOS, 0) c2 , nvl(MODIFICADOS, 0) c4 from ( SELECT CVE_REGISTRO_PATRONAL, CVE_MODALIDAD, NOM_RAZON_SOCIAL, CVE_CIZ, CVE_DELEGACION_ORIGEN, MIN(TO_DATE(FEC_INI_MOV, 'DD/MM/YYYY' )) MIN_FEC_INI_MOV, MAX(TO_DATE(FEC_FIN_MOV, 'DD/MM/YYYY' )) MAX_FEC_FIN_MOV from DIT_CTA_IND_NSS_CDA WHERE CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda AND CVE_ID_ORIGEN_PERIODO_CTA_IND = :origen AND FEC_REGISTRO_BAJA is NULL group by CVE_REGISTRO_PATRONAL, CVE_MODALIDAD, NOM_RAZON_SOCIAL, CVE_CIZ, CVE_DELEGACION_ORIGEN ) AUX LEFT OUTER JOIN ( select CVE_REGISTRO_PATRONAL, count(*) NUEVOS from DIT_CORRECCION_CTA_IND_CDA a INNER JOIN DIT_CTA_IND_NSS_CDA b on CVE_ID_CTA_IND_OPER_ORIGEN = CVE_ID_CTA_IND where CVE_ID_DETALLE_NSS_CDA_ORIG =:cveIdDetalleNssCda and a.FEC_REGISTRO_BAJA is null and CVE_ID_MOV_OPER_ORIGEN = 'A' and CVE_ID_MOV_OPER_DESTINO is null group by CVE_REGISTRO_PATRONAL ) N on AUX.CVE_REGISTRO_PATRONAL = N.CVE_REGISTRO_PATRONAL LEFT OUTER JOIN ( select CVE_REGISTRO_PATRONAL, count(*) INCLUIDOS from DIT_CORRECCION_CTA_IND_CDA a INNER JOIN DIT_CTA_IND_NSS_CDA b on CVE_ID_CTA_IND_OPER_ORIGEN = CVE_ID_CTA_IND where CVE_ID_DETALLE_NSS_CDA_ORIG = :cveIdDetalleNssCda and a.FEC_REGISTRO_BAJA is null and CVE_ID_MOV_OPER_ORIGEN = 'E' and CVE_ID_MOV_OPER_DESTINO = 'A' group by CVE_REGISTRO_PATRONAL ) I on AUX.CVE_REGISTRO_PATRONAL = I.CVE_REGISTRO_PATRONAL LEFT OUTER JOIN ( select CVE_REGISTRO_PATRONAL, count(*) ELIMINADOS from DIT_CORRECCION_CTA_IND_CDA a INNER JOIN DIT_CTA_IND_NSS_CDA b on CVE_ID_CTA_IND_OPER_ORIGEN = CVE_ID_CTA_IND where CVE_ID_DETALLE_NSS_CDA_ORIG = :cveIdDetalleNssCda and a.FEC_REGISTRO_BAJA is null and CVE_ID_MOV_OPER_ORIGEN = 'E' and CVE_ID_MOV_OPER_DESTINO is null group by CVE_REGISTRO_PATRONAL ) E on AUX.CVE_REGISTRO_PATRONAL = E.CVE_REGISTRO_PATRONAL LEFT OUTER JOIN ( select CVE_REGISTRO_PATRONAL, count(*) MODIFICADOS from DIT_CORRECCION_CTA_IND_CDA a INNER JOIN DIT_CTA_IND_NSS_CDA b on CVE_ID_CTA_IND_OPER_ORIGEN = CVE_ID_CTA_IND where CVE_ID_DETALLE_NSS_CDA_ORIG = :cveIdDetalleNssCda and a.FEC_REGISTRO_BAJA is null and CVE_ID_MOV_OPER_ORIGEN = 'M' and CVE_ID_MOV_OPER_DESTINO = 'M' group by CVE_REGISTRO_PATRONAL ) M on AUX.CVE_REGISTRO_PATRONAL = M.CVE_REGISTRO_PATRONAL order by MAX_FEC_FIN_MOV DESC";
    Query nativeQuery = em.createNativeQuery(sql);    
    nativeQuery.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    nativeQuery.setParameter("origen", OrigenPeriodoEnum.CUENTA_INDIVIDUAL.getId());
    List<Object[]> list = nativeQuery.getResultList();
    List<CuentaIndividualRegistroPatronal> listRP = new ArrayList<CuentaIndividualRegistroPatronal>();
    for( Object[] row : list ){
      CuentaIndividualRegistroPatronal registroPatronal = new CuentaIndividualRegistroPatronal();
      registroPatronal.setCveIdDetalleNssCda(cveIdDetalleNssCda);
      registroPatronal.setNumeroRegistroPatronal( row[0] != null ? row[0].toString() : "");
      registroPatronal.setClaveModalidad( row[1] != null ? row[1].toString() : "" );
      registroPatronal.setNombreRegistroPatronal( row[2] != null ? row[2].toString() :"" );
      registroPatronal.setClaveCiz( row[3] != null ? row[3].toString() : "");
      registroPatronal.setClaveDelegacionOrigen(row[4] != null ? Long.parseLong( row[4].toString() ) : 0);
      registroPatronal.setFechaPeriodoInicial( row[5] != null ? row[5].toString() : "");
      registroPatronal.setFechaPeriodoFinal( row[6] != null ? row[6].toString() : "");
      registroPatronal.setNuevos( row[7] != null ? Long.parseLong( row[7].toString() ) : 0 );
      registroPatronal.setIncluidos( row[8] != null ? Long.parseLong( row[8].toString() ) : 0 );
      registroPatronal.setEliminados( row[9] != null ? Long.parseLong( row[9].toString() ) : 0 );
      registroPatronal.setModificados( row[10] != null ? Long.parseLong( row[10].toString() ) : 0 );
      listRP.add(registroPatronal);
    }
    return listRP;
  }
  
  @Override
  public int eliminarCorreccionesBycveIdDetalleNssCdaAndRegistroPatronal(Long cveIdDetalleNssCda, String registroPatronal){
    int total = 0;
    //final String sql = "update Dit_Correccion_Cta_Ind_Cda set fec_Registro_Baja = sysdate where fec_Registro_Baja is null and  cve_id_Detalle_Nss_CDA_Orig = :cveIdDetalleNssCda and CVE_ID_CTA_IND_OPER_ORIGEN IN ( select CVE_ID_CTA_IND from DIT_CTA_IND_NSS_CDA where CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda and CVE_REGISTRO_PATRONAL = :registroPatronal )";
    final String sql = "delete from  Dit_Correccion_Cta_Ind_Cda  where ( (CVE_ID_ESTADO_MOV_ENV_SINDO IS NULL OR CVE_ID_ESTADO_MOV_ENV_SINDO = :edoSindo)) and  cve_id_Detalle_Nss_CDA_Orig = :cveIdDetalleNssCda and CVE_ID_CTA_IND_OPER_ORIGEN IN ( select CVE_ID_CTA_IND from DIT_CTA_IND_NSS_CDA where CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda and CVE_REGISTRO_PATRONAL = :registroPatronal )";
    Query query = em.createNativeQuery(sql);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", registroPatronal);
    query.setParameter("edoSindo",TipoEstadoMovimientoEnviadoSindoEnum.ERROR_SINDO.getId());
    total +=query.executeUpdate();

    final String sql2 = "delete from  DIT_CTA_IND_NSS_CDA where  CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda and CVE_REGISTRO_PATRONAL = :registroPatronal and CVE_ID_ORIGEN_PERIODO_CTA_IND = :origen and  0 = ( select count(*) total from Dit_Correccion_Cta_Ind_Cda where CVE_ID_CTA_IND_OPER_ORIGEN = CVE_ID_CTA_IND or CVE_ID_CTA_IND_OPER_DESTINO = CVE_ID_CTA_IND )";
    //final String sql2 = "UPDATE DIT_CTA_IND_NSS_CDA set FEC_REGISTRO_BAJA = sysdate where FEC_REGISTRO_BAJA is null and CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda and CVE_REGISTRO_PATRONAL = :registroPatronal and CVE_ID_ORIGEN_PERIODO_CTA_IND = :origen";
    query = em.createNativeQuery(sql2);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", registroPatronal);
    query.setParameter("origen", OrigenPeriodoEnum.VENTANILLA.getId());
    total +=query.executeUpdate();

    return total;
  }
  
  @Override
  public List<DitCtaIndNssCda> obtenerPeriodosByCveIdDetalleNssCdaAndRegistroPatronal( Long cveIdDetalleNssCda, String registroPatronal){
    final String sql = "select d from DitCtaIndNssCda d where d.ditDetalleNss.cveDetalleNss = :cveIdDetalleNssCda and d.dicOrigenCtaIndCda.cveIdOrigenPeridoCtaInd = :origen and d.cveRegistroPatronal = :registroPatronal order by TO_DATE(d.fecFinMov , 'DD/MM/YYYY') desc";
    Query query = em.createQuery(sql);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", registroPatronal);
    query.setParameter("origen", OrigenPeriodoEnum.CUENTA_INDIVIDUAL.getId());
    return query.getResultList();
  }
  
  @Override
  public void crearPeriodo(DitCtaIndNssCda periodo){
    periodo.setCveIdCtaInd( null );
    em.persist(periodo);    
  }

  @Override
  public void crearCorreccion(DitCorreccionCtaIndCda correccion) {
    correccion.setCveIdCorreccionCtaIndCda( null );
    em.persist(correccion);
  }
  
  @Override
  public Long obtenerConsecutivoMovimientosByNss(String nss, Long cveIdDetalleNssCda){
    final String sql = "SELECT COALESCE(max(e.indConsecutivoMovimiento)+1 , 101) from DitCorreccionCtaIndCda e where e.cveDetalleNssOperOrigen.nss= :nss  and e.cveDetalleNssOperOrigen.cveDetalleNss <> :cveIdDetalleNssCda  and e.cveDetalleNssOperOrigen.fecRegistroBaja is  null and e.fecRegistroBaja is  null";
    Query query = em.createQuery(sql);
    query.setParameter("nss", nss);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    return (Long)query.getSingleResult();
  }

  @Override
  public Long obtenerTotalPeriodosByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal) {
    final String sql = "select count(d) from DitCtaIndNssCda d where d.ditDetalleNss.cveDetalleNss = :cveIdDetalleNssCda and d.dicOrigenCtaIndCda.cveIdOrigenPeridoCtaInd = :origen and d.cveRegistroPatronal = :registroPatronal";
    Query query = em.createQuery(sql);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", numeroRegistroPatronal);
    query.setParameter("origen", OrigenPeriodoEnum.CUENTA_INDIVIDUAL.getId());
    return (Long)query.getSingleResult();
  }

  @Override
  public List<DitCtaIndNssCda> obtenerPeriodosByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal, long pageNumber) {
    final String sql = "select d from DitCtaIndNssCda d where d.ditDetalleNss.cveDetalleNss = :cveIdDetalleNssCda and d.dicOrigenCtaIndCda.cveIdOrigenPeridoCtaInd = :origen and d.cveRegistroPatronal = :registroPatronal order by TO_DATE(d.fecFinMov , 'DD/MM/YYYY') desc";
    Query query = em.createQuery(sql);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", numeroRegistroPatronal);
    query.setParameter("origen", OrigenPeriodoEnum.CUENTA_INDIVIDUAL.getId());
    query.setFirstResult(Page.DEFAULT_PAGE_SIZE * ( (int) pageNumber - 1 ));
    query.setMaxResults(Page.DEFAULT_PAGE_SIZE);
    return query.getResultList();
  }

  @Override
  public List<DitCorreccionCtaIndCda> obtenerCorreccionesByIdsCtaInd(
          List<Long> ids) {
    final String sql = "select d from DitCorreccionCtaIndCda d where d.cveIdCtaIndOperOrigen.cveIdCtaInd in :ids and d.fecRegistroBaja is null";
    Query query = em.createQuery(sql);
    query.setParameter("ids", ids);    
    return query.getResultList();
  }

  @Override
  public Long obtenerTotalPeriodosCorreccionByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal) {
    final String sql = "select count(d) from DitCorreccionCtaIndCda c inner join c.cveIdCtaIndOperOrigen  d where c.fecRegistroBaja is null and d.ditDetalleNss.cveDetalleNss = :cveIdDetalleNssCda and d.dicOrigenCtaIndCda.cveIdOrigenPeridoCtaInd = :origen and d.cveRegistroPatronal = :registroPatronal";
    Query query = em.createQuery(sql);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", numeroRegistroPatronal);
    query.setParameter("origen", OrigenPeriodoEnum.CUENTA_INDIVIDUAL.getId());
    return (Long)query.getSingleResult();
  }

  @Override
  public List<DitCtaIndNssCda> obtenerPeriodosCorreccionByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal, long pageNumber) {
    final String sql = "select d from DitCorreccionCtaIndCda c inner join c.cveIdCtaIndOperOrigen  d where c.fecRegistroBaja is null and d.ditDetalleNss.cveDetalleNss = :cveIdDetalleNssCda and d.dicOrigenCtaIndCda.cveIdOrigenPeridoCtaInd = :origen and d.cveRegistroPatronal = :registroPatronal order by TO_DATE(d.fecFinMov , 'DD/MM/YYYY') desc";
    Query query = em.createQuery(sql);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", numeroRegistroPatronal);
    query.setParameter("origen", OrigenPeriodoEnum.CUENTA_INDIVIDUAL.getId());
    query.setFirstResult(Page.DEFAULT_PAGE_SIZE * ( (int) pageNumber - 1 ) );
    query.setMaxResults(Page.DEFAULT_PAGE_SIZE);
    return query.getResultList();
  }
  
  @Override
  public List<DitCtaIndNssCda> obtenerPeriodosNuevosByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal, long pageNumber) {
    final String sql = "select d from DitCorreccionCtaIndCda c inner join c.cveIdCtaIndOperOrigen  d where c.fecRegistroBaja is null and d.ditDetalleNss.cveDetalleNss = :cveIdDetalleNssCda and d.dicOrigenCtaIndCda.cveIdOrigenPeridoCtaInd = :origen and d.cveRegistroPatronal = :registroPatronal";
    Query query = em.createQuery(sql);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", numeroRegistroPatronal);
    query.setParameter("origen", OrigenPeriodoEnum.VENTANILLA.getId());
    //query.setFirstResult(((int)pageNumber-1)*10);
    //query.setMaxResults(10);
    return query.getResultList();
  }
  
  /*
   * Los periodos nuevos tienen el origen en ventanilla y el periodo padre en null
   */
  @Override
  public Long eliminarNuevosByCveIdDetalleNssCdaAndRegistroPatronal(Long cveIdDetalleNssCda, String numeroRegistroPatronal) {
    long total = 0;
    //final String sql = "update Dit_Correccion_Cta_Ind_Cda set fec_Registro_Baja = sysdate where fec_Registro_Baja is null and  CVE_ID_CTA_IND_OPER_ORIGEN IN ( select CVE_ID_CTA_IND from DIT_CTA_IND_NSS_CDA where CVE_ID_ORIGEN_PERIODO_CTA_IND = :origen and CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda and CVE_REGISTRO_PATRONAL = :registroPatronal and CVE_ID_CTA_IND_PADRE is null )";
    final String sql = "delete from  Dit_Correccion_Cta_Ind_Cda  where  ( (CVE_ID_ESTADO_MOV_ENV_SINDO IS NULL OR CVE_ID_ESTADO_MOV_ENV_SINDO = :edoSindo)) and  CVE_ID_CTA_IND_OPER_ORIGEN IN ( select CVE_ID_CTA_IND from DIT_CTA_IND_NSS_CDA where CVE_ID_ORIGEN_PERIODO_CTA_IND = :origen and CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda and CVE_REGISTRO_PATRONAL = :registroPatronal and CVE_ID_CTA_IND_PADRE is null )";
    Query query = em.createNativeQuery(sql);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", numeroRegistroPatronal);
    query.setParameter("origen", OrigenPeriodoEnum.VENTANILLA.getId());
    query.setParameter("edoSindo",TipoEstadoMovimientoEnviadoSindoEnum.ERROR_SINDO.getId());
    total +=query.executeUpdate();

    //final String sql2 = "update DIT_CTA_IND_NSS_CDA set fec_Registro_Baja = sysdate where fec_Registro_Baja is null and CVE_ID_ORIGEN_PERIODO_CTA_IND = :origen and CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda and CVE_REGISTRO_PATRONAL = :registroPatronal and CVE_ID_CTA_IND_PADRE is null";
    final String sql2 = "delete from  DIT_CTA_IND_NSS_CDA where  CVE_ID_ORIGEN_PERIODO_CTA_IND = :origen and CVE_ID_DETALLE_NSS_CDA = :cveIdDetalleNssCda and CVE_REGISTRO_PATRONAL = :registroPatronal and CVE_ID_CTA_IND_PADRE is null and  0 = ( select count(*) total from Dit_Correccion_Cta_Ind_Cda where CVE_ID_CTA_IND_OPER_ORIGEN = CVE_ID_CTA_IND or CVE_ID_CTA_IND_OPER_DESTINO = CVE_ID_CTA_IND ) ";
    query = em.createNativeQuery(sql2);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    query.setParameter("registroPatronal", numeroRegistroPatronal);
    query.setParameter("origen", OrigenPeriodoEnum.VENTANILLA.getId());
    total +=query.executeUpdate();

    return total;
  }

  @Override
  public Long eliminarCorreccionesByIdPeriodoOriginal(List<Long> ids) {
    long total = 0;
    //final String sql = "update Dit_Correccion_Cta_Ind_Cda set fec_Registro_Baja = sysdate where fec_Registro_Baja is null and   CVE_ID_CTA_IND_OPER_ORIGEN IN ( :ids )";
    final String sql = "delete from Dit_Correccion_Cta_Ind_Cda  where  CVE_ID_CTA_IND_OPER_ORIGEN IN ( :ids ) and ( (CVE_ID_ESTADO_MOV_ENV_SINDO IS NULL OR CVE_ID_ESTADO_MOV_ENV_SINDO = :edoSindo))";
    Query query = em.createNativeQuery(sql);
    query.setParameter("ids", ids);
    query.setParameter("edoSindo",TipoEstadoMovimientoEnviadoSindoEnum.ERROR_SINDO.getId());
    total +=query.executeUpdate();

    //final String sql2 = "UPDATE DIT_CTA_IND_NSS_CDA set FEC_REGISTRO_BAJA = sysdate where FEC_REGISTRO_BAJA is null and CVE_ID_CTA_IND_PADRE IN ( :ids )";
    final String sql2 = "delete from  DIT_CTA_IND_NSS_CDA  where  CVE_ID_CTA_IND_PADRE IN ( :ids ) and  0 = ( select count(*) total from Dit_Correccion_Cta_Ind_Cda where CVE_ID_CTA_IND_OPER_ORIGEN = CVE_ID_CTA_IND or CVE_ID_CTA_IND_OPER_DESTINO = CVE_ID_CTA_IND )  ";
    query = em.createNativeQuery(sql2);
    query.setParameter("ids", ids);
    total +=query.executeUpdate();

    return total;
  }
  
  @Override
  public List<DitMovAclaracionNssCda> obtenerMovimientosAclaracionByCveIdDetalleNssCda( Long cveIdDetalleNssCda) {    
    final String  sql = " SELECT motivo from DitMovAclaracionNssCda motivo  WHERE motivo.cveIdDetalleNssCda.cveDetalleNss = :cveIdDetalleNssCda and motivo.fecRegistroBaja is null";
    Query query = em.createQuery(sql);
    query.setParameter("cveIdDetalleNssCda", cveIdDetalleNssCda);
    return (List<DitMovAclaracionNssCda>) query.getResultList();
  }

  @Override
  public List<DitDetalleNss> getListNssByCveIdCorreccionDatosAsegurado(
          Long cveIdCorreccionDatosAsegurado) {
    final String  sql = " SELECT d from DitDetalleNss d  WHERE d.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado = :cveIdCorreccionDatosAsegurado and d.dicTipoNss.cveTipoNss <> :cveTipoNss";
    Query query = em.createQuery(sql);
    query.setParameter("cveIdCorreccionDatosAsegurado", cveIdCorreccionDatosAsegurado);
    query.setParameter("cveTipoNss", TipoNSSCorreccionEnum.NO_EXISTE_EN_CANASE.getId());
    return (List<DitDetalleNss>) query.getResultList();
  }

  @Override
  public List<DitDetalleNss> getListNssByCveIdCorreccionDatosAsegurado(
          Long cveIdCorreccionDatosAsegurado,
          TipoNSSCorreccionEnum tipoNSSCorreccionEnum) {
    final String  sql = " SELECT d from DitDetalleNss d  WHERE d.correccionDatosAsegurado.cveIdCorreccionDatosAsegurado = :cveIdCorreccionDatosAsegurado and d.dicTipoNss.cveTipoNss = :cveTipoNss";
    Query query = em.createQuery(sql);
    query.setParameter("cveIdCorreccionDatosAsegurado", cveIdCorreccionDatosAsegurado);
    query.setParameter("cveTipoNss", tipoNSSCorreccionEnum.getId());
    return (List<DitDetalleNss>) query.getResultList();
  }

  @Override
  public DitCtaIndNssCda obtenerPeriodoById(Long cveIdPeriodoCuentaIndividual) {
    return em.find(DitCtaIndNssCda.class, cveIdPeriodoCuentaIndividual);
  }
  
	public boolean eliminarMovimientosCuentaIndividual(long cveIdCorreccion) {
		int total = 0;
		final String sql = "delete from DIT_CORRECCION_CTA_IND_CDA where CVE_ID_DETALLE_NSS_CDA_ORIG in "
				+ "( select CVE_ID_DETALLE_NSS_CDA  from DIT_DETALLE_NSS_CDA where CVE_ID_CORRECCION_DATOS_ASEG = :cveIdCorreccion )";
		Query query = em.createNativeQuery(sql);
		query.setParameter("cveIdCorreccion", cveIdCorreccion);	
		try {
			total = query.executeUpdate();
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return total > 0;
	}
	
	  @Override
	  public List<Long> getListNssByFolioTramite(String folioTramite) {
	    Query query = em.createNamedQuery("ditCtaIndNssCda.existenMovimientosPorFolio");	    
	    query.setParameter("reffolio", folioTramite);
	    return (List<Long>) query.getResultList();
	  }

  @Override
  public DitDetalleNss findById(Long cveIdDetalleNssCda) {
    return em.find(DitDetalleNss.class, cveIdDetalleNssCda);
  }

}
