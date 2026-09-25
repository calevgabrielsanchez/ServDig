package mx.gob.imss.cit.cda.service.cuentaindividual.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.cuentaindividual.converter.CuentaIndividualConverterLocal;
import mx.gob.imss.cit.cda.service.cuentaindividual.entity.MovimientoAclaracionLocal;
import mx.gob.imss.cit.cda.service.entity.CuentaIndividualLocal;
import mx.gob.imss.cit.cda.service.entity.DetalleNssCdaLocal;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoEstadoMovimientoEnviadoSindoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionNSSEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionPeriodoEnum;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitCtaIndNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitTipoCertificacionCorreccion;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class CuentaIndividualNssUtility extends AbstractServiceEntity implements CuentaIndividualNssUtilityLocal {
    
    /**
     * Logger de la clase
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @EJB
    private DetalleNssCdaLocal detalleNssCdaLocal;
    
    @EJB
    private CuentaIndividualLocal cuentaIndividualLocal;
     
    @EJB
    private CuentaIndividualConverterLocal cuentaIndividualConverterLocal;
    
    @EJB
    private SolicitudBusinessRemote solicitudBusinessRemote;
    
    @EJB
    private MovimientoAclaracionLocal movimientoAclaracionEntity;
    
    
    
    private void preparePeriodos( CuentaIndividualNss cuentaInd, Map<String, PeriodosRegistroPatronal> mapPeriodosRegistroPatronal, List<String> listaNss){
    
      List<DitCtaIndNssCda> listaEntityPeriodosExistentes = cuentaIndividualLocal.getPeriodosGuardadosIndividual(cuentaInd.getCveIdDetalleNssCda());
            
            
      for(DitCtaIndNssCda entity : listaEntityPeriodosExistentes ){
        // Si no se encuentra el RP se crea e ingresa al mapa
        if( !mapPeriodosRegistroPatronal.containsKey( entity.getCveRegistroPatronal() ) ){
          // FIX: Validar si se requieren mas datos
          PeriodosRegistroPatronal periodosRegistroPatronal = new PeriodosRegistroPatronal();
          periodosRegistroPatronal.setNumeroRegistroPatronal( entity.getCveRegistroPatronal() );
          periodosRegistroPatronal.setNombreRegistroPatronal( entity.getNomRazonSocial() );
          periodosRegistroPatronal.setClaveDelegacionOrigen( entity.getCveDelegacionOrigen().intValue() );
          periodosRegistroPatronal.setClaveModalidad( entity.getCveModalidad() );
          periodosRegistroPatronal.setClaveCiz( entity.getCveCiz().intValue() );
          periodosRegistroPatronal.setListaNss(listaNss);
          if(entity.getCveDelegacionOrigen() != null && entity.getCveDelegacionOrigen() !=0)
          {
        	  Delegacion delegacion = solicitudBusinessRemote.getDatosDelegacion(entity.getCveDelegacionOrigen());
        	  if( delegacion != null ){
                  periodosRegistroPatronal.setNombreDelegacionOrigen(
                          delegacion.getDescripcion() );
                }

          }
          
         

          mapPeriodosRegistroPatronal.put( entity.getCveRegistroPatronal(), periodosRegistroPatronal);
        }
        // Estos son los periodos originales, no tienen regularizacion
        PeriodoCuentaIndividual periodoCuentaIndividual = cuentaIndividualConverterLocal.entityToModel(entity);

        mapPeriodosRegistroPatronal.get( entity.getCveRegistroPatronal() ).getPeriodos().add(periodoCuentaIndividual);
        logger.error(" -------> periodo.CuentaIndividual: {}", periodoCuentaIndividual.getCveIdPeriodoCuentaIndividual());

      }
    
    }
    
    
    private void preparePeriodosAgregados(CuentaIndividualNss cuentaInd, Map<String, PeriodosRegistroPatronal> mapPeriodosRegistroPatronal){
      List<DitCorreccionCtaIndCda> listaEntityPeriodosAgregados = cuentaIndividualLocal.getPeriodosAgregados(cuentaInd.getCveIdDetalleNssCda());
            
      for(DitCorreccionCtaIndCda correccionCtaIndCda : listaEntityPeriodosAgregados){                            
        // Periodos Nuevos la referencia al periodo queda en el Origen
        // FIX: Validar las propiedades a setear de los periodos de origen ventanilla
        DitCtaIndNssCda entity = correccionCtaIndCda.getCveIdCtaIndOperOrigen();
        PeriodoCuentaIndividual periodoCuentaIndividual = cuentaIndividualConverterLocal.entityToModel(entity);
        periodoCuentaIndividual.setTipoRegularizacionPeriodo( TipoRegularizacionPeriodoEnum.AGREGAR);
        periodoCuentaIndividual.setIndicadorConsecutivoMovimiento(correccionCtaIndCda.getIndConsecutivoMovimiento().intValue()  );
        periodoCuentaIndividual.setEstatus( correccionCtaIndCda.getCveIdEstadoMovSindo() != null ? TipoEstadoMovimientoEnviadoSindoEnum.fromId( correccionCtaIndCda.getCveIdEstadoMovSindo() ).getDescripcion() : StringUtils.EMPTY);

        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        mapPeriodosRegistroPatronal.get( entity.getCveRegistroPatronal() ).getPeriodosNuevos().add(periodoCuentaIndividual);
      }
    
    }
    
    private void preparePeriodosModificados(CuentaIndividualNss cuentaInd, Map<String, PeriodosRegistroPatronal> mapPeriodosRegistroPatronal){
      List<DitCorreccionCtaIndCda> listaEntityPeriodosModificados = cuentaIndividualLocal.getPeriodosPorOperacionDestino(cuentaInd.getCveIdDetalleNssCda(), TipoRegularizacionPeriodoEnum.MODIFICAR.getId(), TipoRegularizacionPeriodoEnum.MODIFICAR.getId());
            
      for(DitCorreccionCtaIndCda correccionCtaIndCda : listaEntityPeriodosModificados){                            
        // Periodos Modificados la referencia al periodo queda en el Destino
        // FIX: Validar las propiedades a setear de los periodos de origen ventanilla
        DitCtaIndNssCda entity = correccionCtaIndCda.getCveIdCtaIndOperDestino();

        PeriodoCuentaIndividual periodoCuentaIndividual = cuentaIndividualConverterLocal.entityToModel(entity);
        periodoCuentaIndividual.setIndicadorConsecutivoMovimiento(correccionCtaIndCda.getIndConsecutivoMovimiento().intValue()  );
        periodoCuentaIndividual.setTipoRegularizacionPeriodo( TipoRegularizacionPeriodoEnum.MODIFICAR);
        periodoCuentaIndividual.setCveIdPeriodoAnterior( correccionCtaIndCda.getCveIdCtaIndOperOrigen().getCveIdCtaInd() );
        periodoCuentaIndividual.setEstatus( correccionCtaIndCda.getCveIdEstadoMovSindo() != null ? TipoEstadoMovimientoEnviadoSindoEnum.fromId( correccionCtaIndCda.getCveIdEstadoMovSindo() ).getDescripcion() : StringUtils.EMPTY);
        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        mapPeriodosRegistroPatronal.get( entity.getCveRegistroPatronal() ).getPeriodosModificados().add(periodoCuentaIndividual);
      }
    
    }
    
    private void preparePeriodosIncluidos(CuentaIndividualNss cuentaInd, Map<String, PeriodosRegistroPatronal> mapPeriodosRegistroPatronal){
      List<DitCorreccionCtaIndCda> listaEntityPeriodosIncluidos = cuentaIndividualLocal.getPeriodosPorOperacionOrigen(cuentaInd.getCveIdDetalleNssCda(), TipoRegularizacionPeriodoEnum.ELIMINAR.getId(), TipoRegularizacionPeriodoEnum.AGREGAR.getId());
      logger.error( "Inlcuidos 1: {}" +  listaEntityPeriodosIncluidos.size() );
      for(DitCorreccionCtaIndCda correccionCtaIndCda : listaEntityPeriodosIncluidos){                            
        // Periodos Incluidos la referencia al periodo queda en el Destino y quedan marcados como Incluidos
        // FIX: Validar las propiedades a setear de los periodos de origen ventanilla
        DitCtaIndNssCda entity = correccionCtaIndCda.getCveIdCtaIndOperDestino();
        PeriodoCuentaIndividual periodoCuentaIndividual = cuentaIndividualConverterLocal.entityToModel(entity);
        periodoCuentaIndividual.setIndicadorConsecutivoMovimiento(correccionCtaIndCda.getIndConsecutivoMovimiento().intValue()  );
        periodoCuentaIndividual.setTipoRegularizacionPeriodo( TipoRegularizacionPeriodoEnum.INCLUIR);
        periodoCuentaIndividual.setCveIdPeriodoAnterior( correccionCtaIndCda.getCveIdCtaIndOperOrigen().getCveIdCtaInd() );
        periodoCuentaIndividual.setEstatus( correccionCtaIndCda.getCveIdEstadoMovSindo() != null ? TipoEstadoMovimientoEnviadoSindoEnum.fromId( correccionCtaIndCda.getCveIdEstadoMovSindo() ).getDescripcion() : StringUtils.EMPTY);
        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        mapPeriodosRegistroPatronal.get( entity.getCveRegistroPatronal() ).getPeriodosIncluidos().add(periodoCuentaIndividual);
      }           
      // Se agregan a incluidos (2o criterio: Criterio opraciones ELIMINAR y MODIFICAR)
      listaEntityPeriodosIncluidos = cuentaIndividualLocal.getPeriodosPorOperacionOrigen(cuentaInd.getCveIdDetalleNssCda(), TipoRegularizacionPeriodoEnum.ELIMINAR.getId(), TipoRegularizacionPeriodoEnum.MODIFICAR.getId());
      logger.error( "Inlcuidos 2: {}" +  listaEntityPeriodosIncluidos.size() );
      for(DitCorreccionCtaIndCda correccionCtaIndCda : listaEntityPeriodosIncluidos){                            
        // Periodos Incluidos la referencia al periodo queda en el Destino y quedan marcados como Incluidos
        // FIX: Validar las propiedades a setear de los periodos de origen ventanilla
        DitCtaIndNssCda entity = correccionCtaIndCda.getCveIdCtaIndOperDestino();
        PeriodoCuentaIndividual periodoCuentaIndividual = cuentaIndividualConverterLocal.entityToModel(entity);
        periodoCuentaIndividual.setIndicadorConsecutivoMovimiento(correccionCtaIndCda.getIndConsecutivoMovimiento().intValue()  );
        periodoCuentaIndividual.setTipoRegularizacionPeriodo( TipoRegularizacionPeriodoEnum.MODIFICAR);
        periodoCuentaIndividual.setCveIdPeriodoAnterior( correccionCtaIndCda.getCveIdCtaIndOperOrigen().getCveIdCtaInd() );
        periodoCuentaIndividual.setEstatus( correccionCtaIndCda.getCveIdEstadoMovSindo() != null ? TipoEstadoMovimientoEnviadoSindoEnum.fromId( correccionCtaIndCda.getCveIdEstadoMovSindo() ).getDescripcion() : StringUtils.EMPTY);
        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        mapPeriodosRegistroPatronal.get( entity.getCveRegistroPatronal() ).getPeriodosIncluidos().add(periodoCuentaIndividual);
      }
    }

    private void preparePeriodosEliminados(CuentaIndividualNss cuentaInd, Map<String, PeriodosRegistroPatronal> mapPeriodosRegistroPatronal){
      logger.error("Buscando periodos eliminados: {}" , cuentaInd.getCveIdDetalleNssCda() );
      List<DitCorreccionCtaIndCda> listaEntityPeriodosEliminados = cuentaIndividualLocal.getPeriodosEliminados( cuentaInd.getCveIdDetalleNssCda() );
      logger.error("Periodos eencontrados: {}" , listaEntityPeriodosEliminados.size() );
      for(DitCorreccionCtaIndCda correccionCtaIndCda : listaEntityPeriodosEliminados){                            
        // Periodos Eliminados la referencia al periodo queda en el Origen y quedan marcados como ELIMINADOS
        // FIX: Validar las propiedades a setear de los periodos de origen ventanilla
        DitCtaIndNssCda entity = correccionCtaIndCda.getCveIdCtaIndOperOrigen();
        PeriodoCuentaIndividual periodoCuentaIndividual = cuentaIndividualConverterLocal.entityToModel(entity);
        periodoCuentaIndividual.setIndicadorConsecutivoMovimiento(correccionCtaIndCda.getIndConsecutivoMovimiento().intValue()  );
        periodoCuentaIndividual.setTipoRegularizacionPeriodo( TipoRegularizacionPeriodoEnum.ELIMINAR);
        periodoCuentaIndividual.setCveIdPeriodoAnterior( correccionCtaIndCda.getCveIdCtaIndOperOrigen().getCveIdCtaInd() );
        periodoCuentaIndividual.setEstatus( correccionCtaIndCda.getCveIdEstadoMovSindo() != null ? TipoEstadoMovimientoEnviadoSindoEnum.fromId( correccionCtaIndCda.getCveIdEstadoMovSindo() ).getDescripcion() : StringUtils.EMPTY);
        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        periodoCuentaIndividual.setFechaProceso( obtenerFechaStr(correccionCtaIndCda.getFecMovEnvSindo()) );
        mapPeriodosRegistroPatronal.get( entity.getCveRegistroPatronal() ).getPeriodosEliminados().add(periodoCuentaIndividual);
      }
    }
    
    @Override
    public CuentaIndividualNss findDetalleByFolioNss(String folioSolicitud, String nss, List<String> listaNss) {
        CuentaIndividualNss cuentaInd = new CuentaIndividualNss();
          
        // Obtengo la cveDetalle
        DitDetalleNss detalle = detalleNssCdaLocal.getDetalleNssByFolioNss(folioSolicitud, nss);
        if (detalle != null) { // por cada NSS busco periodos
            cuentaInd.setNss(detalle.getNss());
            cuentaInd.setCveIdDetalleNssCda(detalle.getCveDetalleNss());
            cuentaInd.setTipoCorreccion(TipoNSSCorreccionEnum.fromId(detalle.getDicTipoNss().getCveTipoNss()));
            
            /*
             * FIX: Validar esto de la certificacion
             */
            if (detalle.getListaCertificacion() != null) {
                cuentaInd.setTipoRegularizacion(new ArrayList<TipoRegularizacionNSSEnum>());
                for (DitTipoCertificacionCorreccion cert: detalle.getListaCertificacion()) {
                    Long idTipoRegularizacion = cert.getCveTipoCertificacion();
                    cuentaInd.getTipoRegularizacion().add(TipoRegularizacionNSSEnum.fromId(idTipoRegularizacion));
                    logger.error(" ---> tipo regularización: {}", TipoRegularizacionNSSEnum.fromId(idTipoRegularizacion));
                }                
            }
            
            /*
             * Para armar la lista de PeriodoRegistroPatronal solo se requieren los periodos
             * provenientes de cuenta individual.
             * Despues para cada periodo proveniente de ventanilla se puede asignar a uno
             * de los PeriodosRegistroPatronal existente.
             */
            
            //1. periodos de cta individual --> agruparlos
            Map<String, PeriodosRegistroPatronal> mapPeriodosRegistroPatronal = new HashMap<String, PeriodosRegistroPatronal>();
            
            preparePeriodos(cuentaInd, mapPeriodosRegistroPatronal, listaNss);
            
            
            /*
             * En este punto ya se tiene un mapa de Registros Patronales, mismo que ya no se incrementara
             * porque no puede haber movimientos de periodos que pertenezcan a otros registros patronales
             * 
             * En lugar de compartir una lista se compartira el mapa ya creado para que sobre el se incluyan
             * los periodos.
             */            
            preparePeriodosAgregados(cuentaInd,mapPeriodosRegistroPatronal);
            preparePeriodosModificados(cuentaInd,mapPeriodosRegistroPatronal);
            preparePeriodosIncluidos(cuentaInd, mapPeriodosRegistroPatronal);
            preparePeriodosEliminados(cuentaInd, mapPeriodosRegistroPatronal);
            
            for (Map.Entry<String, PeriodosRegistroPatronal> elemento : mapPeriodosRegistroPatronal.entrySet()) {
              cuentaInd.getListaPeriodosRegistroPatronal().add( elemento.getValue() );
            }                
        }
                
        return cuentaInd;
    }
    
    private String obtenerFechaStr(Date fechaDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        return fechaDate != null ? formatter.format(fechaDate) : null;
    }    
    
    @Override
    public List<String> getMovimientosAclaracionByFolioNss(String folio, String nss){
        List<String> listaTipoTramite= new ArrayList<String>();
        List<DitMovAclaracionNssCda> listaMovimientos =  movimientoAclaracionEntity.obtenerMovimientosAclaracionByFolioNss(folio, nss);
        for (DitMovAclaracionNssCda aclaracion : listaMovimientos) {
          if(aclaracion != null && aclaracion.getCveIdTipoTramCorrecNss() != null ){ 
              if(listaTipoTramite.isEmpty()){
                  listaTipoTramite.add(aclaracion.getCveIdTipoTramCorrecNss().getDescTipoCorreccionTramNss());
              }else{
                  if(!listaTipoTramite.contains(aclaracion.getCveIdTipoTramCorrecNss().getDescTipoCorreccionTramNss())  ){
                      listaTipoTramite.add(aclaracion.getCveIdTipoTramCorrecNss().getDescTipoCorreccionTramNss());
                   } 
               }
          }

        }
        return listaTipoTramite;
    }
   
    
}
