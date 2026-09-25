package mx.gob.imss.cit.cda.service.cuentaindividual.converter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.cuentaindividual.TipoListaCapturaEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.MovimientosCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.enums.OrigenPeriodoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionPeriodoEnum;
import mx.gob.imss.ctirss.delta.persistence.DicMovCorrecCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DicOrigenCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramCorreccionNss;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitCtaIndNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "cuentaIndividualConverter", mappedName =
        "cuentaIndividualConverter")
public class CuentaIndividualConverter implements CuentaIndividualConverterLocal {

  private final Logger logger = LoggerFactory.getLogger(
          CuentaIndividualConverter.class);

  @Override
  public List<DitCtaIndNssCda> convertModelToEntity(
          CuentaIndividualNss cuentaIndividualNss) {

    List<DitCtaIndNssCda> periodosPersistencia =
            new ArrayList<DitCtaIndNssCda>();

    for (PeriodosRegistroPatronal periodos : cuentaIndividualNss.
            getListaPeriodosRegistroPatronal()) {

      if (notEmpty(periodos.getPeriodosIncluidos())) {
        periodosPersistencia.addAll(convertEntity(periodos.
                getPeriodosIncluidos(), periodos, cuentaIndividualNss,
                OrigenPeriodoEnum.VENTANILLA.getId(),
                TipoListaCapturaEnum.INCLUIDOS.getId()));
      }

      if (notEmpty(periodos.getPeriodosModificados())) {
        periodosPersistencia.addAll(convertEntity(periodos.
                getPeriodosModificados(), periodos, cuentaIndividualNss,
                OrigenPeriodoEnum.VENTANILLA.getId(),
                TipoListaCapturaEnum.MODIFICADOS.getId()));
      }

      if (notEmpty(periodos.getPeriodosNuevos())) {
        periodosPersistencia.addAll(convertEntity(periodos.getPeriodosNuevos(),
                periodos, cuentaIndividualNss, OrigenPeriodoEnum.VENTANILLA.
                        getId(), TipoListaCapturaEnum.NUEVOS.getId()));
      }

      if (isEmpty(periodos.getPeriodosEliminados())
              && isEmpty(periodos.getPeriodosIncluidos())
              && isEmpty(periodos.getPeriodosModificados())
              && isEmpty(periodos.getPeriodosNuevos())) {

        periodosPersistencia.addAll(convertEntity(periodos.getPeriodos(),
                periodos,
                cuentaIndividualNss, OrigenPeriodoEnum.CUENTA_INDIVIDUAL.getId(),
                TipoListaCapturaEnum.ORIGINALES.getId()));
      }

    }

    return periodosPersistencia;
  }

  private void convertEntity_step1(DitCtaIndNssCda entity, PeriodoCuentaIndividual periodo){
    entity.setCveConsecPeriodos(Long.valueOf(periodo.
            getNumeroConsecutivoPeriodos()));
    entity.setFecIniMov(periodo.getFechaInicioMovimiento());
    entity.setFecFinMov(periodo.getFechaFinalMovimiento());
    entity.setCveIniMov(periodo.getOrigenMovimientoInicial() != null ?
            periodo.getOrigenMovimientoInicial().charAt(0) :
            ' ');
    entity.setCveFinMov(periodo.getOrigenMovimientoFinal() != null ?
            periodo.getOrigenMovimientoFinal().charAt(0) :
            ' ');
    entity.setCveTipoIniMov(Long.valueOf(periodo.getTipoMovimientoInicial()));
    entity.setCveTipoFinMov(Long.valueOf(periodo.getTipoMovimientoFinal()));
    entity.setFecRecepcionMov(periodo.getFechaRecepcionMovimiento() != null ?
            periodo.getFechaRecepcionMovimiento() :
            "0");
    entity.setSalarioBase(periodo.getSalarioBase());
    entity.setCveTipoSalario(periodo.getTipoSalario() != null ?
            periodo.getTipoSalario():
            "0");
    entity.setCveJornadaSemanal( periodo.getJornadaSemanal() != null ?
            periodo.getJornadaSemanal() :
            "0");
    entity.setCveEventual(periodo.getEventual() != null ?
            periodo.getEventual() :
            "0");
  }
  
  private void convertEntity_step2(DitCtaIndNssCda entity, PeriodoCuentaIndividual periodo){
    if (periodo.getSubrogacionServicio() != null) {
        entity.setCveSubrServicios(
                periodo.getSubrogacionServicio().length() > 0 ?
                periodo.getSubrogacionServicio():
                "0");
      }

      entity.setCveHuelga(periodo.getHuelga() != null ?
              periodo.getHuelga() :
              "0");
      entity.setCveExtConvSusp(periodo.getExtemporaneoConvenioSuspension()
              != null ?
                      periodo.getExtemporaneoConvenioSuspension() :
                      "0");
      entity.setFecActualizacion(periodo.getFechaActualizacion());
      entity.setNssDestino(periodo.getNss());
      entity.setCveIdCtaInd(periodo.getCveIdPeriodoCuentaIndividual() != null ?
              periodo.getCveIdPeriodoCuentaIndividual() :
              null);
  }
  
  private List<DitCtaIndNssCda> convertEntity(
          List<PeriodoCuentaIndividual> lista, 
          PeriodosRegistroPatronal periodos,
          CuentaIndividualNss cuentaIndividualNss, Long origenPeriodo,
          Long tipoLista) {

    List<DitCtaIndNssCda> periodosPersistencia =
            new ArrayList<DitCtaIndNssCda>();

    for (PeriodoCuentaIndividual periodo : lista) {
      logger.error("Periodo a 'convertir' {}", periodo);

      DicOrigenCtaIndCda dicOrigenCtaIndCda = new DicOrigenCtaIndCda();
      DitCtaIndNssCda entity = new DitCtaIndNssCda();
      DitDetalleNss detalleNss = new DitDetalleNss();

      entity.setNss(cuentaIndividualNss.getNss());
      entity.setCveRegistroPatronal(periodos.getNumeroRegistroPatronal());
      entity.setCveModalidad(periodos.getClaveModalidad());
      entity.setCurp(periodos.getCurp());
      
      this.convertEntity_step1(entity, periodo);
      this.convertEntity_step2(entity, periodo);
      
      entity.setCveDelegacionOrigen(Long.valueOf(periodo.
              getClaveDelegacionOrigen()));
      entity.setCveCiz(Long.valueOf(periodos.getClaveCiz()));
      entity.setFecCarga(periodo.getFechaCarga());
      detalleNss.setCveDetalleNss(cuentaIndividualNss.getCveIdDetalleNssCda());
      entity.setDitDetalleNss(detalleNss);
      dicOrigenCtaIndCda.setCveIdOrigenPeridoCtaInd(origenPeriodo);
      entity.setDicOrigenCtaIndCda(dicOrigenCtaIndCda);
      entity.setFecRegistroAlta(Calendar.getInstance().getTime());
      entity.setFecRegistroActualizado(Calendar.getInstance().getTime());
      entity.setNomRazonSocial(periodos.getNombreRegistroPatronal());
      
      
      if (periodo.getCveIdPeriodoAnterior() != null) {
        DitCtaIndNssCda periodoAnterior = new DitCtaIndNssCda();
        periodoAnterior.setCveIdCtaInd(periodo.getCveIdPeriodoAnterior());
        entity.setCveIdCtaIndPadre(periodoAnterior);
      }

      entity.setTipoRegularizacion(periodo.getTipoRegularizacionPeriodo()
              != null ?
                      periodo.getTipoRegularizacionPeriodo().getDesc() :
                      null);
      entity.setOrigenCaptura(tipoLista);
      entity.setIndHistoricoCentral(periodo.getHistorico());

      periodosPersistencia.add(entity);

    }

    return periodosPersistencia;
  }

  public CuentaIndividualNss convertModelToEntity(List<DitCtaIndNssCda> entity) {
    return null;
  }

  /**
   * Metodo para convertir de la entidad al Modelo
   *
   * @param entity DitCtaIndNssCda
   * @param historico posibles valores 0 (individual), 1 (ventanilla)
   * @param opOrigen
   * @param opDestino
   *
   * @return
   */
  @Override
  public PeriodoCuentaIndividual convertEntityToModel(DitCtaIndNssCda entity,
          int historico, TipoRegularizacionPeriodoEnum opOrigen,
          TipoRegularizacionPeriodoEnum opDestino) {
    PeriodoCuentaIndividual periodoCuentaIndividual =
            new PeriodoCuentaIndividual();
    periodoCuentaIndividual.setClaveCiz(entity.getCveCiz() != null ?
            entity.getCveCiz().intValue() :
            0);
    periodoCuentaIndividual.setCveIdPeriodoAnterior(entity.getCveIdCtaIndPadre()
            != null ?
                    entity.getCveIdCtaIndPadre().getCveIdCtaInd() :
                    null);
    periodoCuentaIndividual.setCveIdPeriodoCuentaIndividual(entity.
            getCveIdCtaInd());
    periodoCuentaIndividual.setEventual(String.valueOf(entity.getCveEventual()));
    periodoCuentaIndividual.setExtemporaneoConvenioSuspension(String.valueOf(
            entity.getCveExtConvSusp()));
    periodoCuentaIndividual.setFechaActualizacion(entity.getFecActualizacion());
    periodoCuentaIndividual.setFechaCarga(entity.getFecCarga());
    periodoCuentaIndividual.setFechaFinalMovimiento(entity.getFecFinMov());
    periodoCuentaIndividual.setFechaInicioMovimiento(entity.getFecIniMov());
    periodoCuentaIndividual.setFechaRecepcionMovimiento(entity.
            getFecRecepcionMov());
    periodoCuentaIndividual.setHistorico(historico);
    periodoCuentaIndividual.setHuelga(String.valueOf(entity.getCveHuelga()));
    periodoCuentaIndividual.setJornadaSemanal(String.valueOf(entity.
            getCveJornadaSemanal()));
    periodoCuentaIndividual.setNss(entity.getNss());
    periodoCuentaIndividual.setNumeroConsecutivoPeriodos(entity.
            getCveIdCtaInd() != null ? 
                    entity.getCveIdCtaInd().intValue() :
                    0);
    periodoCuentaIndividual.setNumeroRegistroPatronal(entity.
            getCveRegistroPatronal());
    periodoCuentaIndividual.setSalarioBase(entity.getSalarioBase());
    periodoCuentaIndividual.setSubrogacionServicio(String.valueOf(entity.
            getCveSubrServicios()));
    periodoCuentaIndividual.setTipoMovimientoFinal(entity.getCveTipoFinMov()
            != null ?
                    entity.getCveTipoFinMov().intValue() :
                    0);
    periodoCuentaIndividual.setTipoMovimientoInicial(entity.getCveTipoIniMov()
            != null ?
                    entity.getCveTipoIniMov().intValue() :
                    0);
    periodoCuentaIndividual.setTipoSalario(String.valueOf(entity.
            getCveTipoSalario()));
    periodoCuentaIndividual.setOrigenMovimientoFinal(String.valueOf(entity.
            getCveFinMov()));
    periodoCuentaIndividual.setOrigenMovimientoInicial(String.valueOf(entity.
            getCveIniMov()));
    periodoCuentaIndividual.setTipoRegularizacionPeriodo(
            determinarTipoRegularizacionPeriodoEnum(opOrigen, opDestino));
    periodoCuentaIndividual.setCveIdPeriodoCuentaIndividual(entity.
            getCveIdCtaInd());
    logger.error(" -------> periodo.CuentaIndividual: {}",
            periodoCuentaIndividual.getCveIdPeriodoCuentaIndividual());

    return periodoCuentaIndividual;
  }

  public PeriodoCuentaIndividual entityToModel(DitCtaIndNssCda entity) {
    PeriodoCuentaIndividual periodoCuentaIndividual =
            new PeriodoCuentaIndividual();
    // Estandar
    periodoCuentaIndividual.setClaveCiz(entity.getCveCiz() != null ?
            entity.getCveCiz().intValue() :
            0);
    periodoCuentaIndividual.setCveIdPeriodoAnterior(entity.getCveIdCtaIndPadre()
            != null ?
                    entity.getCveIdCtaIndPadre().getCveIdCtaInd() :
                    null);
    periodoCuentaIndividual.setCveIdPeriodoCuentaIndividual(entity.
            getCveIdCtaInd());
    periodoCuentaIndividual.setEventual(String.valueOf(entity.getCveEventual()));
    periodoCuentaIndividual.setExtemporaneoConvenioSuspension(String.valueOf(
            entity.getCveExtConvSusp()));
    periodoCuentaIndividual.setFechaActualizacion(entity.getFecActualizacion());
    periodoCuentaIndividual.setFechaCarga(entity.getFecCarga());
    periodoCuentaIndividual.setFechaFinalMovimiento(entity.getFecFinMov());
    periodoCuentaIndividual.setFechaInicioMovimiento(entity.getFecIniMov());
    periodoCuentaIndividual.setFechaRecepcionMovimiento(entity.
            getFecRecepcionMov());
    periodoCuentaIndividual.setHistorico(entity.getIndHistoricoCentral());
    periodoCuentaIndividual.setHuelga(String.valueOf(entity.getCveHuelga()));
    periodoCuentaIndividual.setJornadaSemanal(String.valueOf(entity.
            getCveJornadaSemanal()));
    periodoCuentaIndividual.setNss(entity.getNss());
    periodoCuentaIndividual.setNumeroConsecutivoPeriodos(entity.
            getCveIdCtaInd() != null ?
                    entity.getCveIdCtaInd().intValue() :
                    0);
    periodoCuentaIndividual.setNumeroRegistroPatronal(entity.
            getCveRegistroPatronal());
    periodoCuentaIndividual.setSalarioBase(entity.getSalarioBase());
    periodoCuentaIndividual.setSubrogacionServicio(String.valueOf(entity.
            getCveSubrServicios()));
    periodoCuentaIndividual.setTipoMovimientoFinal(entity.getCveTipoFinMov()
            != null ?
                    entity.getCveTipoFinMov().intValue() :
                    0);
    periodoCuentaIndividual.setTipoMovimientoInicial(entity.getCveTipoIniMov()
            != null ?
                    entity.getCveTipoIniMov().intValue() :
                    0);
    periodoCuentaIndividual.setTipoSalario(String.valueOf(entity.
            getCveTipoSalario()));
    periodoCuentaIndividual.setOrigenMovimientoFinal(String.valueOf(entity.
            getCveFinMov()));
    periodoCuentaIndividual.setOrigenMovimientoInicial(String.valueOf(entity.
            getCveIniMov()));
    return periodoCuentaIndividual;

  }

  /*
   * private String formatearFecha(String fechaSinFormato) {
   *
   * String fechaFormato = ""; StringTokenizer strtok = new
   * StringTokenizer(fechaSinFormato,"-",false); if( strtok.countTokens() == 3
   * ){ String year = strtok.nextToken(); String month = strtok.nextToken();
   * String day = strtok.nextToken();
   *
   * StringBuilder sb = new StringBuilder(); fechaFormato =
   * sb.append(day).append("/").append(month).append("/").append(year).toString();
   * }
   *
   * return fechaFormato;
    }
   */
  /**
   * Metodo auxiliar usado en el metodo 'convertModelToEntity'
   */
  
  private TipoRegularizacionPeriodoEnum determinarTipoRegularizacionPeriodoEnum_step1(
          TipoRegularizacionPeriodoEnum opOrigen,
          TipoRegularizacionPeriodoEnum opDestino){
    if (opDestino != null) {
            //Incluidos
            switch (opDestino) {
              case AGREGAR:
                return TipoRegularizacionPeriodoEnum.AGREGAR;
              case MODIFICAR:
                return TipoRegularizacionPeriodoEnum.INCLUIR;
            }
          } else { //Eliminados
            return TipoRegularizacionPeriodoEnum.ELIMINAR;
          }
    
    return TipoRegularizacionPeriodoEnum.INVALIDA;
    
  }
  
  private TipoRegularizacionPeriodoEnum determinarTipoRegularizacionPeriodoEnum(
          TipoRegularizacionPeriodoEnum opOrigen,
          TipoRegularizacionPeriodoEnum opDestino) {
    if (opOrigen != null) {
      switch (opOrigen) {
        case ELIMINAR:
          return determinarTipoRegularizacionPeriodoEnum_step1(opOrigen,
                  opDestino);          
        case MODIFICAR: //Modificados
          if (opDestino.equals(TipoRegularizacionPeriodoEnum.MODIFICAR)) {
            return TipoRegularizacionPeriodoEnum.ELIMINAR;
          }
        case AGREGAR: //Agregados
          if (opDestino == null) {
            return TipoRegularizacionPeriodoEnum.AGREGAR;
          }
      }
    }
    return TipoRegularizacionPeriodoEnum.INVALIDA;
  }

  private static Boolean isEmpty(List<PeriodoCuentaIndividual> lista) {
    return lista == null || lista.isEmpty();
  }

  private static Boolean notEmpty(List<PeriodoCuentaIndividual> lista) {
    return lista != null && !lista.isEmpty();
  }

  private void convertMovimientiIndividualEntity_step1(MovimientosCuentaIndividual movimientosCuentaIndividual, DitCorreccionCtaIndCda ditCorreccionCtaIndCda ){
    if (movimientosCuentaIndividual.getNssDestino() != null) {
      DitDetalleNss ditDetalleNssOperDestino = new DitDetalleNss();
      ditDetalleNssOperDestino.setCveDetalleNss(movimientosCuentaIndividual.
              getNssDestino());// NSS DESTINO
      ditCorreccionCtaIndCda.setCveDetalleNssOperDestino(
              ditDetalleNssOperDestino);
    }

    if (movimientosCuentaIndividual.getCuentaIndividualDestino() != null) {
      DitCtaIndNssCda ditCtaIndOperDestino = new DitCtaIndNssCda();
      ditCtaIndOperDestino.setCveIdCtaInd(movimientosCuentaIndividual.
              getCuentaIndividualDestino()); //CUENTA INDIVIDUAL DESTINO
      ditCorreccionCtaIndCda.setCveIdCtaIndOperDestino(ditCtaIndOperDestino);
    }

    if (movimientosCuentaIndividual.getMovimientoDestino() != null) {
      DicMovCorrecCtaIndCda dicMovOperDestino = new DicMovCorrecCtaIndCda();
      dicMovOperDestino.setCveIdMovCorreccion(movimientosCuentaIndividual.
              getMovimientoDestino() != null ?
                      movimientosCuentaIndividual.getMovimientoDestino().charAt(
                              0) :
                      ' '); //ENUMERACIONES DE MOVIMIENTOS DESTINO
      ditCorreccionCtaIndCda.setCveIdMovOperDestino(dicMovOperDestino);
    }
    if (movimientosCuentaIndividual.getNssOrigen() != null) {
      DitDetalleNss ditDetalleNssOperOrigen = new DitDetalleNss();
      ditDetalleNssOperOrigen.setCveDetalleNss(movimientosCuentaIndividual.
              getNssOrigen());// NSS ORIGEN
      ditCorreccionCtaIndCda.setCveDetalleNssOperOrigen(ditDetalleNssOperOrigen);
    }

    
  }
  
  
  @Override
  public DitCorreccionCtaIndCda convertMovimientiIndividualEntity(
          MovimientosCuentaIndividual movimientosCuentaIndividual) {
    DitCorreccionCtaIndCda ditCorreccionCtaIndCda = new DitCorreccionCtaIndCda();

    this.convertMovimientiIndividualEntity_step1(movimientosCuentaIndividual,
            ditCorreccionCtaIndCda);

    if (movimientosCuentaIndividual.getCuentaIndividualOrigen() != null) {
      DitCtaIndNssCda ditCtaIndOperOrigen = new DitCtaIndNssCda();
      ditCtaIndOperOrigen.setCveIdCtaInd(movimientosCuentaIndividual.
              getCuentaIndividualOrigen()); //CUENTA INDIVIDUAL ORIGEN
      ditCorreccionCtaIndCda.setCveIdCtaIndOperOrigen(ditCtaIndOperOrigen);
    }
    
    if (movimientosCuentaIndividual.getMovimientoOrigen() != null) {
      DicMovCorrecCtaIndCda ditMovOperOrigen = new DicMovCorrecCtaIndCda();
      ditMovOperOrigen.setCveIdMovCorreccion(movimientosCuentaIndividual.
              getMovimientoOrigen() != null ?
                      movimientosCuentaIndividual.getMovimientoOrigen().
                              charAt(0) :
                      ' '); //ENUMERACIONES DE MOVIMIENTOS ORIGEN
      ditCorreccionCtaIndCda.setCveIdMovOperOrigen(ditMovOperOrigen);
    }

    ditCorreccionCtaIndCda.setIndConsecutivoMovimiento(
            movimientosCuentaIndividual.getConsecutivo()); //Cálculo de consecutivo
    ditCorreccionCtaIndCda.setCveIdMovAclaracionNss(movimientosCuentaIndividual.
            getClaveMovimientoAclaracion());//ligado con motivo aclaracion 

    ditCorreccionCtaIndCda.setFecRegistroAlta(movimientosCuentaIndividual.
            getFechaAlta());
    ditCorreccionCtaIndCda.setFecRegistroBaja(movimientosCuentaIndividual.
            getFechaBaja());
    ditCorreccionCtaIndCda.setFecRegistroActualizado(Calendar.getInstance().
            getTime());

    return ditCorreccionCtaIndCda;

  }

  @Override
  public DitMovAclaracionNssCda convertAclaracionMovimientosEntity(
          DitCorreccionCtaIndCda correccion, Long tipoAclaracion) {
    DitMovAclaracionNssCda aclaracion = new DitMovAclaracionNssCda();
    aclaracion.setCveIdDetalleNssCda(correccion.getCveDetalleNssOperOrigen());
    aclaracion.setFecRegistroAlta(Calendar.getInstance().getTime());
    aclaracion.setFecRegistroActualizado(Calendar.getInstance().getTime());
    DicTipoTramCorreccionNss tipoCorreccion = new DicTipoTramCorreccionNss();
    tipoCorreccion.setCveIdTipoTramCorrecNss(tipoAclaracion);
    aclaracion.setCveIdTipoTramCorrecNss(tipoCorreccion);
    return aclaracion;
  }
  
  private void modelToEntity_step1(DitCtaIndNssCda entity, PeriodoCuentaIndividual periodo){
    entity.setCveIniMov(periodo.getOrigenMovimientoInicial() != null ?
              periodo.getOrigenMovimientoInicial().charAt(0) :
              ' ');
      entity.setCveFinMov(periodo.getOrigenMovimientoFinal() != null ?
              periodo.getOrigenMovimientoFinal().charAt(0) :
              ' ');
      entity.setCveTipoIniMov(Long.valueOf(periodo.getTipoMovimientoInicial()));
      entity.setCveTipoFinMov(Long.valueOf(periodo.getTipoMovimientoFinal()));
      entity.setFecRecepcionMov(periodo.getFechaRecepcionMovimiento() != null ?
              periodo.getFechaRecepcionMovimiento() :
              "0");
      entity.setSalarioBase(periodo.getSalarioBase());
      entity.setCveTipoSalario(periodo.getTipoSalario() != null ?
              periodo.getTipoSalario() :
              "0");
      entity.setCveJornadaSemanal(periodo.getJornadaSemanal() != null ?
              periodo.getJornadaSemanal() :
              "0");
      entity.setCveEventual(periodo.getEventual() != null ?
              periodo.getEventual() :
              "0");
  }

  @Override
  public DitCtaIndNssCda modelToEntity(PeriodoCuentaIndividual periodo, PeriodosRegistroPatronal periodosRegistroPatronal,CuentaIndividualNss cuentaIndividualNss) {
    DicOrigenCtaIndCda dicOrigenCtaIndCda = new DicOrigenCtaIndCda();
      DitCtaIndNssCda entity = new DitCtaIndNssCda();
      DitDetalleNss detalleNss = new DitDetalleNss();

      entity.setNss(periodo.getNss());
      entity.setCveRegistroPatronal(periodosRegistroPatronal.getNumeroRegistroPatronal());
      entity.setCveModalidad(periodosRegistroPatronal.getClaveModalidad());
      entity.setCurp(periodosRegistroPatronal.getCurp());
      entity.setCveConsecPeriodos(Long.valueOf(periodo.
              getNumeroConsecutivoPeriodos()));
      entity.setFecIniMov(periodo.getFechaInicioMovimiento());
      entity.setFecFinMov(periodo.getFechaFinalMovimiento());
      
      this.modelToEntity_step1(entity, periodo);
      
      if (periodo.getSubrogacionServicio() != null) {
        entity.setCveSubrServicios(
                periodo.getSubrogacionServicio().length() > 0 ?
                periodo.getSubrogacionServicio() :
                "0");
      }

      entity.setCveHuelga(periodo.getHuelga() != null ?
              periodo.getHuelga() :
              "0");
      entity.setCveExtConvSusp(periodo.getExtemporaneoConvenioSuspension()
              != null ?
                      periodo.getExtemporaneoConvenioSuspension() :
                      "0");
      entity.setFecActualizacion(periodo.getFechaActualizacion());
      entity.setCveDelegacionOrigen(Long.valueOf(periodosRegistroPatronal.
              getClaveDelegacionOrigen()));
      entity.setCveCiz(Long.valueOf(periodosRegistroPatronal.getClaveCiz()));
      entity.setFecCarga(periodo.getFechaCarga());
      detalleNss.setCveDetalleNss(cuentaIndividualNss.getCveIdDetalleNssCda());
      entity.setDitDetalleNss(detalleNss);
      
      dicOrigenCtaIndCda.setCveIdOrigenPeridoCtaInd( OrigenPeriodoEnum.VENTANILLA.getId() );
      entity.setDicOrigenCtaIndCda(dicOrigenCtaIndCda);
      
        
      
      entity.setFecRegistroAlta(Calendar.getInstance().getTime());
      entity.setFecRegistroActualizado(Calendar.getInstance().getTime());
      entity.setNomRazonSocial(periodosRegistroPatronal.getNombreRegistroPatronal());
      entity.setNssDestino(periodo.getNss());
      /*entity.setCveIdCtaInd(periodo.getCveIdPeriodoCuentaIndividual() != null ?
              periodo.getCveIdPeriodoCuentaIndividual() :
              null);*/
      if (periodo.getCveIdPeriodoAnterior() != null) {
        DitCtaIndNssCda periodoAnterior = new DitCtaIndNssCda();
        periodoAnterior.setCveIdCtaInd(periodo.getCveIdPeriodoAnterior());
        entity.setCveIdCtaIndPadre(periodoAnterior);
      }

      entity.setTipoRegularizacion(periodo.getTipoRegularizacionPeriodo()
              != null ?
                      periodo.getTipoRegularizacionPeriodo().getDesc() :
                      "0");
      
      entity.setIndHistoricoCentral(periodo.getHistorico());
      
      return entity;
  }

}
