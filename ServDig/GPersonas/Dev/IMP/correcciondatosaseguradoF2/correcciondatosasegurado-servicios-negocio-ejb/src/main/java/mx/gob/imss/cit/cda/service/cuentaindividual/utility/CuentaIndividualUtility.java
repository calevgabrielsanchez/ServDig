package mx.gob.imss.cit.cda.service.cuentaindividual.utility;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.cuentaindividual.converter.CuentaIndividualConverterLocal;
import mx.gob.imss.cit.cda.service.cuentaindividual.entity.MovimientoAclaracionLocal;
import mx.gob.imss.cit.cda.service.entity.CuentaIndividualLocal;
import mx.gob.imss.cit.cda.service.entity.DetalleNssCdaLocal;
import mx.gob.imss.cit.cda.service.entity.MovimientoCuentaIndividualLocal;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.CuentaIndividualVo;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.RespuestaCuentaIndividual;
import mx.gob.imss.ctirss.delta.framework.exceptions.TipoAclaracionCuentaIndividualException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.MovimientosCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionPeriodoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitCtaIndNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;
import mx.gob.imss.ctirss.wsConsultaPatron.WSConsultaPatronServiceProxy;
import mx.gob.imss.ctirss.wsConsultaPatron.vo.InfoPatronEntrada;
import mx.gob.imss.ctirss.wsConsultaPatron.vo.InfoPatronSalida;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "cuentaIndividualUtility", mappedName = "cuentaIndividualUtility")
public class CuentaIndividualUtility implements CuentaIndividualUtilityLocal {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @EJB
    private CuentaIndividualLocal cuentaIndividualLocal;

    @EJB
    private DetalleNssCdaLocal detalleNssCdaLocal;

    @EJB
    private SolicitudBusinessRemote solicitudBusinessRemote;

    @EJB
    private CuentaIndividualConverterLocal cuentaIndividualConverter;

    @EJB
    private MovimientoCuentaIndividualLocal movimientoCuentaIndividualEntity;

    @EJB
    private MovimientoAclaracionLocal movimientoAclaracionEntity;

    @EJB
    private ServiceBusinessRemote serviceBusiness;

    @Override
    public List<PeriodoCuentaIndividual> convertRespuestaWsToModeloDominio(RespuestaCuentaIndividual respuesta, Long idDetalleNss) {
        List<PeriodoCuentaIndividual> periodos = new ArrayList<PeriodoCuentaIndividual>();
        for (CuentaIndividualVo cuenta : respuesta.getCuentaIndividual()) {
            PeriodoCuentaIndividual periodo = new PeriodoCuentaIndividual();
            try {
                periodo.setNss(cuenta.getNss());
                periodo.setNumeroRegistroPatronal(cuenta.getRegistroPatronal());
                //periodo.setRegistroPatronal(consultaNombreRegistroPatronal(cuenta.getClaveModalidad(), cuenta.getRegistroPatronal()));
                //periodo.setClaveModalidad(cuenta.getClaveModalidad());
                periodo.setFechaInicioMovimiento(cuenta.getFechaInicioMovimiento());
                periodo.setNumeroConsecutivoPeriodos(cuenta.getNumeroConsecutivoPeriodos());
                //periodo.setCurp(cuenta.getCurp());
                periodo.setFechaFinalMovimiento(cuenta.getFechaFinalMovimiento());
                periodo.setOrigenMovimientoInicial(cuenta.getOrigenMovimientoInicial());
                periodo.setOrigenMovimientoFinal(cuenta.getOrigenMovimientoFinal());
                periodo.setTipoMovimientoInicial(cuenta.getTipoMovimientoIniintcial());
                periodo.setTipoMovimientoFinal(cuenta.getTipoMovimientoFinal());
                periodo.setFechaRecepcionMovimiento(cuenta.getFechaRecepcionMovimiento());
                periodo.setSalarioBase(cuenta.getSalarioBase());
                periodo.setTipoSalario(cuenta.getTipoSalario());
                periodo.setJornadaSemanal(cuenta.getJornadaSemanal());
                periodo.setEventual(cuenta.getEventual());
                periodo.setSubrogacionServicio(cuenta.getSubrogacionServicio());
                periodo.setHuelga(cuenta.getHuelga());
                periodo.setExtemporaneoConvenioSuspension(cuenta.getExtemporaneoConvenioSuspencion());
                periodo.setFechaActualizacion(cuenta.getFechaActualizacion());
                periodo.setFechaActualizacion(cuenta.getFechaActualizacion());
                //periodo.setClaveDelegacionOrigen(cuenta.getClaveDelegacionOrigen());
                periodo.setClaveCiz(cuenta.getClaveCiz());
                //periodo.setCveIdDetalleNssCda(idDetalleNss);
                periodo.setFechaCarga(cuenta.getFechaCarga());
                periodos.add(periodo);
            } catch (Exception e) {
                logger.debug("Error al transformar las fechas del periodo, se excuye de la lista", e);
            }
        }

        return periodos;
    }

    /**
     * Método conexión a Webservice para obtener nombre o razon social de Patrón
     * *
     */
    private String consultaNombreRegistroPatronal(String claveModalidad, String registroPatronal) {
                   
        //String file=  CuentaIndividualBusiness.class.getClassLoader().getResource("").getFile();
        WSConsultaPatronServiceProxy proxy = new WSConsultaPatronServiceProxy();
        proxy.setEndpoint("http://dictamendigital-stage.imss.gob.mx/wsBDTUPatrones/WSConsultaPatronService");
        //http://dictamendigital.imss.gob.mx/wsBDTUPatrones/WSConsultaPatronService  PRODUCTIVO
        InfoPatronSalida respuesta = new InfoPatronSalida();
        try {

            respuesta = proxy.getInformacionPatron(crearPeticionPatron(claveModalidad, registroPatronal));
        } catch (RemoteException e) {
            logger.error("Error conexion webservice Patrón", e);
        }

        return respuesta.getRazonSocial();

    }

    private String getDescripcionDelegacion(long claveDelegacion) {
        if(claveDelegacion != 0L)
        {
        	Delegacion delegacion = getSolicitudBusinessRemote().getDatosDelegacion(claveDelegacion);
            if (delegacion != null) {
                return delegacion.getDescripcion();
            }
        }
    	
        return StringUtils.EMPTY;
    }

    @Override
    public CuentaIndividualVO convertEntityToModel(
            DitCtaIndNssCda cuentaIndividualEntity) {
        logger.error("covertModelToEntity");
        logger.error("Convirtiendo a la entidad {} ", cuentaIndividualEntity.toString());
        CuentaIndividualVO cuentaIndividualVO = new CuentaIndividualVO();
        cuentaIndividualVO.setCveIdPeriodo(cuentaIndividualEntity.getCveIdCtaInd());
        cuentaIndividualVO.setNss(cuentaIndividualEntity.getNss());
        cuentaIndividualVO.setRegistroPatronal(cuentaIndividualEntity.getCveRegistroPatronal());
        cuentaIndividualVO.setNombreRP(cuentaIndividualEntity.getNomRazonSocial());
        cuentaIndividualVO.setClaveModalidad(cuentaIndividualEntity.getCveModalidad());
        cuentaIndividualVO.setFechaInicioMovimiento(cuentaIndividualEntity.getFecIniMov());
        cuentaIndividualVO.setNumeroConsecutivoPeriodos(cuentaIndividualEntity.getCveConsecPeriodos().intValue());
        cuentaIndividualVO.setCurp(cuentaIndividualEntity.getCurp());
        cuentaIndividualVO.setFechaFinalMovimiento(cuentaIndividualEntity.getFecFinMov());
        cuentaIndividualVO.setOrigenMovimientoInicial(String.valueOf(cuentaIndividualEntity.getCveIniMov()));
        cuentaIndividualVO.setOrigenMovimientoFinal(String.valueOf(cuentaIndividualEntity.getCveFinMov()));
        cuentaIndividualVO.setTipoMovimientoIniintcial(cuentaIndividualEntity.getCveTipoIniMov().intValue());
        cuentaIndividualVO.setTipoMovimientoFinal(cuentaIndividualEntity.getCveTipoFinMov().intValue());
        cuentaIndividualVO.setFechaRecepcionMovimiento(cuentaIndividualEntity.getFecRecepcionMov());
        cuentaIndividualVO.setSalarioBase(cuentaIndividualEntity.getSalarioBase());
        cuentaIndividualVO.setTipoSalario(String.valueOf(cuentaIndividualEntity.getCveTipoSalario()));
        cuentaIndividualVO.setJornadaSemanal(String.valueOf(cuentaIndividualEntity.getCveJornadaSemanal()));
        cuentaIndividualVO.setEventual(String.valueOf(cuentaIndividualEntity.getCveEventual()));
        cuentaIndividualVO.setSubrogacionServicio(String.valueOf(cuentaIndividualEntity.getCveSubrServicios()));
        cuentaIndividualVO.setHuelga(String.valueOf(cuentaIndividualEntity.getCveHuelga()));
        cuentaIndividualVO.setExtemporaneoConvenioSuspencion(String.valueOf(cuentaIndividualEntity.getCveExtConvSusp()));
        cuentaIndividualVO.setFechaActualizacion(cuentaIndividualEntity.getFecActualizacion());
        cuentaIndividualVO.setClaveDelegacionOrigen(cuentaIndividualEntity.getCveDelegacionOrigen().intValue());
        cuentaIndividualVO.setNombreDelegacionOrigen(getDescripcionDelegacion(cuentaIndividualEntity.getCveDelegacionOrigen()));
        cuentaIndividualVO.setClaveCiz(cuentaIndividualEntity.getCveCiz().intValue());
        cuentaIndividualVO.setFechaCarga(cuentaIndividualEntity.getFecCarga());
        cuentaIndividualVO.setDetalleNss(cuentaIndividualEntity.getDitDetalleNss().getCveDetalleNss());
        cuentaIndividualVO.setOrigenPeriodo(cuentaIndividualEntity.getDicOrigenCtaIndCda().getCveIdOrigenPeridoCtaInd().intValue());
        return cuentaIndividualVO;
    }

    @Override
    public InfoPatronEntrada crearPeticionPatron(String claveModalidad, String registroPatronal) {
        InfoPatronEntrada peticion = new InfoPatronEntrada();
        peticion.setDigitoVerificador(calcularDigitoVerificador(registroPatronal + claveModalidad));
        peticion.setModalidad(claveModalidad);
        peticion.setRegistroPatronal(registroPatronal);
        return peticion;
    }

    private static String calcularDigitoVerificador(String nrp) {

        int factorDeConversion = 10;

        int paso3 = 0;
        boolean bandera = true;
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int primeraLetra = alfabeto.indexOf(nrp.toUpperCase().charAt(0));
        if (primeraLetra != -1) {
            nrp = (primeraLetra + factorDeConversion) + nrp.substring(1, nrp.length());
        }
        int i = nrp.length() - 1;
        while (i >= 0) {
            if (bandera) {
                int porDos = Integer.parseInt(nrp.substring(i, i + 1)) * 2;
                if (porDos > 9) {
                    paso3 += (porDos % 10) + (porDos / 10);
                } else {
                    paso3 += porDos;
                }
                bandera = false;
            } else {
                paso3 += Integer.parseInt(nrp.substring(i, i + 1));
                bandera = true;
            }
            i--;
        }
        Integer digitoVerificador = 10 - (paso3 % 10);
        if (digitoVerificador > 9) {
            digitoVerificador = 0;
        }
        return digitoVerificador.toString();
    }

    public SolicitudBusinessRemote getSolicitudBusinessRemote() {
        return solicitudBusinessRemote;
    }

    @Override
    public List<PeriodosRegistroPatronal> crearPeriodos(List<CuentaIndividualVO> periodosTramite, List<String> listaNss) {

        List<PeriodosRegistroPatronal> listaRegistroPatronal = new ArrayList<PeriodosRegistroPatronal>();
        PeriodosRegistroPatronal cuentaIndividual = new PeriodosRegistroPatronal();
        List<PeriodoCuentaIndividual> periodos = new ArrayList<PeriodoCuentaIndividual>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");

        for (CuentaIndividualVO periodoVO : periodosTramite) {
            try {

                if (listaRegistroPatronal.isEmpty() || !contieneRP(listaRegistroPatronal, periodoVO.getRegistroPatronal())) {
                    cuentaIndividual = crearEncabezadoRP(periodoVO, listaNss);
                    periodos = new ArrayList<PeriodoCuentaIndividual>();
                    listaRegistroPatronal.add(cuentaIndividual);
                }

                PeriodoCuentaIndividual periodo = new PeriodoCuentaIndividual();
                //      periodo.setClaveModalidad(periodoVO.getClaveModalidad());
                periodo.setFechaInicioMovimiento(df1.format(df.parse(periodoVO.getFechaInicioMovimiento())));
                periodo.setNumeroConsecutivoPeriodos(periodoVO.getNumeroConsecutivoPeriodos());
                //      periodo.setCurp(periodoVO.getCurp());
                periodo.setFechaFinalMovimiento(df1.format(df.parse(periodoVO.getFechaFinalMovimiento())));
                periodo.setOrigenMovimientoInicial(periodoVO.getOrigenMovimientoInicial());
                periodo.setOrigenMovimientoFinal(periodoVO.getOrigenMovimientoFinal());
                periodo.setTipoMovimientoInicial(periodoVO.getTipoMovimientoIniintcial());
                periodo.setTipoMovimientoFinal(periodoVO.getTipoMovimientoFinal());
                periodo.setFechaRecepcionMovimiento(df1.format(df.parse(periodoVO.getFechaRecepcionMovimiento())));
                periodo.setSalarioBase(periodoVO.getSalarioBase());
                periodo.setTipoSalario(periodoVO.getTipoSalario());
                periodo.setJornadaSemanal(periodoVO.getJornadaSemanal());
                periodo.setEventual(periodoVO.getEventual());
                periodo.setSubrogacionServicio(periodoVO.getSubrogacionServicio());
                periodo.setHuelga(periodoVO.getHuelga());
                periodo.setExtemporaneoConvenioSuspension(periodoVO.getExtemporaneoConvenioSuspencion());
                periodo.setFechaActualizacion(periodoVO.getFechaActualizacion());
                periodo.setFechaActualizacion(periodoVO.getFechaActualizacion());
                periodo.setFechaCarga(periodoVO.getFechaCarga());
                periodo.setNss(periodoVO.getNss());
                periodo.setClaveCiz(periodoVO.getClaveCiz());
//                periodo.setCveIdPeriodo(periodoVO.getCveIdPeriodo());
                periodos.add(periodo);
                cuentaIndividual.setPeriodos(periodos);

            } catch (ParseException ex) {
                logger.error("ERROR PARSE", ex);

            }
        }
        return listaRegistroPatronal;
    }

    /**
     * Verifica si la lista de periodos contiene el registro patronal
     *
     * @param list lista de periodos sin agrupar
     * @param rp registro patronal
     * @return true en caso de que si lo contenga
     */
    @Override
    public boolean contieneRP(List<PeriodosRegistroPatronal> list, String rp) {
        boolean contieneRP = false;
        if (!list.isEmpty()) {
            for (PeriodosRegistroPatronal cuenta : list) {
//                if (cuenta.getRegistroPatronal().equals(rp)) {
//                    contieneRP = true;
//                    break;
//                }
            }
        }
        return contieneRP;
    }

    @Override
    public PeriodosRegistroPatronal crearEncabezadoRP(CuentaIndividualVO bandeja, List<String> listaNss) {
        PeriodosRegistroPatronal cuenta = new PeriodosRegistroPatronal();

//        cuenta.setRegistroPatronal(bandeja.getRegistroPatronal());
//        cuenta.setClaveCiz(bandeja.getClaveCiz());
//        cuenta.setClaveDelegacionOrigen(bandeja.getClaveDelegacionOrigen());
//        cuenta.setNombreDelegacionOrigen(bandeja.getNombreDelegacionOrigen());
//        cuenta.setNssDestino(bandeja.getNss());
//        cuenta.setListaNss(listaNss);
//        cuenta.setNombreRegistroPatronal(bandeja.getNombreRP());
//        cuenta.setNumeroRegistroPatronal(bandeja.getRegistroPatronal());
        return cuenta;

    }

    /**
     * Obtener lista de nss
     *
     * @param listaTramites
     * @return lista de nss
     */
    @Override
    public List<String> obtenerListaNss(List<DitDetalleNss> listaTramites) {
        List<String> listaNss = new ArrayList<String>();

        for (DitDetalleNss tramite : listaTramites) {
            listaNss.add(tramite.getNss());
        }
        return listaNss;
    }

    @Override
    public void guardarPeriodos(CuentaIndividualNss cuentaIndividualNss) {
    	 logger.error("---CDA CI-----Inicia guardado de periodos------");
    	List<DitCtaIndNssCda> periodos = cuentaIndividualConverter.convertModelToEntity(cuentaIndividualNss);
        for (DitCtaIndNssCda ditCtaIndNssCda : periodos) {
        	logger.error("---CDA CI--LLAMA validacion---",ditCtaIndNssCda.toString());
        	ditCtaIndNssCda = validarTipoPeriodo(ditCtaIndNssCda);
        	logger.error("---CDA CI--PERIODO---",ditCtaIndNssCda.toString());
            cuentaIndividualLocal.guardarPeriodo(ditCtaIndNssCda);
        }
    }

    @Override
    public void actualizarPeriodos(CuentaIndividualNss cuentaIndividualNss) {
        logger.error("Actualizar periodos: {}", cuentaIndividualNss);
        
        //List<DitCtaIndNssCda> periodos = cuentaIndividualConverter.convertModelToEntity(cuentaIndividualNss);

        eliminarPrevios(cuentaIndividualNss.getCveIdDetalleNssCda());
        Long consecutivo = movimientoCuentaIndividualEntity.obtenerConsecutivoMovimientos(cuentaIndividualNss.getCveIdDetalleNssCda());
        
        //logger.error("Periodos: {}", periodos.size());
        
        /**
         * Realizar el procesamiento de los movimientos uno a uno por su
         * clasificacion.
         */
        for (PeriodosRegistroPatronal periodosRegistroPatronal : cuentaIndividualNss.
            getListaPeriodosRegistroPatronal()) {
          
          if( periodosRegistroPatronal.getPeriodosEliminados() != null ){
          
            for( PeriodoCuentaIndividual periodoCuentaIndividual : periodosRegistroPatronal.getPeriodosEliminados() ){

              MovimientosCuentaIndividual movimientosCuentaIndividual = new MovimientosCuentaIndividual();              
              movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.ELIMINAR.getId());
              movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());
              movimientosCuentaIndividual.setCuentaIndividualOrigen( periodoCuentaIndividual.getCveIdPeriodoAnterior() );
              movimientosCuentaIndividual.setConsecutivo(consecutivo);
              movimientosCuentaIndividual.setFechaAlta(new Date());
              
              DitCtaIndNssCda entity = cuentaIndividualConverter.modelToEntity(periodoCuentaIndividual, periodosRegistroPatronal, cuentaIndividualNss);             
              DitCorreccionCtaIndCda movimiento = cuentaIndividualConverter.convertMovimientiIndividualEntity(movimientosCuentaIndividual);
              cuentaIndividualLocal.guardarPeriodo(entity);
              movimientoCuentaIndividualEntity.guardarMovimiento(movimiento);
                
            }
            
            for( PeriodoCuentaIndividual periodoCuentaIndividual : periodosRegistroPatronal.getPeriodosNuevos()){

              MovimientosCuentaIndividual movimientosCuentaIndividual = new MovimientosCuentaIndividual();              
              movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.AGREGAR.getId());
              movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());              
              movimientosCuentaIndividual.setConsecutivo(consecutivo);
              movimientosCuentaIndividual.setFechaAlta(new Date());
              
              DitCtaIndNssCda entity = cuentaIndividualConverter.modelToEntity(periodoCuentaIndividual, periodosRegistroPatronal, cuentaIndividualNss);             
              DitCorreccionCtaIndCda movimiento = cuentaIndividualConverter.convertMovimientiIndividualEntity(movimientosCuentaIndividual);
              cuentaIndividualLocal.guardarPeriodo(entity);
              movimiento.setCveIdCtaIndOperOrigen(entity);
              movimientoCuentaIndividualEntity.guardarMovimiento(movimiento);
                
            }
            
            for( PeriodoCuentaIndividual periodoCuentaIndividual : periodosRegistroPatronal.getPeriodosIncluidos()){

              MovimientosCuentaIndividual movimientosCuentaIndividual = new MovimientosCuentaIndividual();              
              movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.ELIMINAR.getId());
              movimientosCuentaIndividual.setMovimientoDestino(periodoCuentaIndividual.getTipoRegularizacionPeriodo().getId());
              movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());
              movimientosCuentaIndividual.setCuentaIndividualOrigen( periodoCuentaIndividual.getCveIdPeriodoAnterior() );              
              movimientosCuentaIndividual.setConsecutivo(consecutivo);
              movimientosCuentaIndividual.setFechaAlta(new Date());
              
              Long nssDestino = cuentaIndividualLocal.findbyNss(cuentaIndividualNss.getCveIdDetalleNssCda(), periodoCuentaIndividual.getNss() );
              
              movimientosCuentaIndividual.setNssDestino(nssDestino);
              
              DitCtaIndNssCda entity = cuentaIndividualConverter.modelToEntity(periodoCuentaIndividual, periodosRegistroPatronal, cuentaIndividualNss);             
              DitCorreccionCtaIndCda movimiento = cuentaIndividualConverter.convertMovimientiIndividualEntity(movimientosCuentaIndividual);
              cuentaIndividualLocal.guardarPeriodo(entity);
              movimiento.setCveIdCtaIndOperDestino(entity);
              movimientoCuentaIndividualEntity.guardarMovimiento(movimiento);
                
            }
            
            for( PeriodoCuentaIndividual periodoCuentaIndividual : periodosRegistroPatronal.getPeriodosModificados()){

              MovimientosCuentaIndividual movimientosCuentaIndividual = new MovimientosCuentaIndividual();              
              movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.MODIFICAR.getId());
              movimientosCuentaIndividual.setMovimientoDestino(TipoRegularizacionPeriodoEnum.MODIFICAR.getId());
              movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());
              movimientosCuentaIndividual.setNssDestino(cuentaIndividualNss.getCveIdDetalleNssCda());
              movimientosCuentaIndividual.setCuentaIndividualOrigen( periodoCuentaIndividual.getCveIdPeriodoAnterior() );              
              movimientosCuentaIndividual.setConsecutivo(consecutivo);
              movimientosCuentaIndividual.setFechaAlta(new Date());              
              
              DitCtaIndNssCda entity = cuentaIndividualConverter.modelToEntity(periodoCuentaIndividual, periodosRegistroPatronal, cuentaIndividualNss);             
              DitCorreccionCtaIndCda movimiento = cuentaIndividualConverter.convertMovimientiIndividualEntity(movimientosCuentaIndividual);
              cuentaIndividualLocal.guardarPeriodo(entity);
              movimiento.setCveIdCtaIndOperDestino(entity);
              movimientoCuentaIndividualEntity.guardarMovimiento(movimiento);
                
            }
          }
        }
        
        /*

        for (DitCtaIndNssCda ditCtaIndNssCda : periodos) {
            logger.error("Periodo: {}", ditCtaIndNssCda.getTipoRegularizacion());
            if (ditCtaIndNssCda.getTipoRegularizacion() != null && !TipoRegularizacionPeriodoEnum.INVALIDA.getDesc().equals(
                    ditCtaIndNssCda.getTipoRegularizacion() ) ) {

                DitCtaIndNssCda periodoNuevo = new DitCtaIndNssCda();
                if (!ditCtaIndNssCda.getTipoRegularizacion().equals(TipoRegularizacionPeriodoEnum.ELIMINAR.getDesc())) {
                    periodoNuevo = cuentaIndividualLocal.guardarPeriodo(ditCtaIndNssCda);//regresar id insertado
                }
                logger.error("Periodo Nuevo: {}", periodoNuevo.getCveIdCtaInd());
                MovimientosCuentaIndividual movimientosCuentaIndividual = new MovimientosCuentaIndividual();
                movimientosCuentaIndividual.setFechaAlta(Calendar.getInstance().getTime());

                if (ditCtaIndNssCda.getTipoRegularizacion().equals(TipoRegularizacionPeriodoEnum.AGREGAR.getDesc())) {
                    movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.AGREGAR.getId());
                    movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());
                    movimientosCuentaIndividual.setNssDestino(null);
                    movimientosCuentaIndividual.setMovimientoDestino(null);
                    movimientosCuentaIndividual.setCuentaIndividualOrigen(periodoNuevo.getCveIdCtaInd());
                    movimientosCuentaIndividual.setCuentaIndividualDestino(null);

                } else if (ditCtaIndNssCda.getTipoRegularizacion().equals(TipoRegularizacionPeriodoEnum.INCLUIR.getDesc())) {
                    Long nssDestino = cuentaIndividualLocal.findbyNss(cuentaIndividualNss.getCveIdDetalleNssCda(), ditCtaIndNssCda.getNssDestino());
                    logger.error("Movimiento Incluir: {}", movimientosCuentaIndividual);
                    movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.ELIMINAR.getId());
                    movimientosCuentaIndividual.setMovimientoDestino(TipoRegularizacionPeriodoEnum.INCLUIR.getId());
                    movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());
                    movimientosCuentaIndividual.setNssDestino(nssDestino);
                    movimientosCuentaIndividual.setCuentaIndividualOrigen(periodoNuevo.getCveIdCtaIndPadre().getCveIdCtaInd());
                    movimientosCuentaIndividual.setCuentaIndividualDestino(periodoNuevo.getCveIdCtaInd());
                    logger.error("Movimiento Incluir: {}", movimientosCuentaIndividual);

                } else if (ditCtaIndNssCda.getTipoRegularizacion().equals(TipoRegularizacionPeriodoEnum.MODIFICAR.getDesc())
                        && ditCtaIndNssCda.getOrigenCaptura().equals(TipoListaCapturaEnum.INCLUIDOS.getId())) {
                    Long nssDestino = cuentaIndividualLocal.findbyNss(cuentaIndividualNss.getCveIdDetalleNssCda(), ditCtaIndNssCda.getNssDestino());
                    movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.ELIMINAR.getId());
                    movimientosCuentaIndividual.setMovimientoDestino(TipoRegularizacionPeriodoEnum.MODIFICAR.getId());
                    movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());
                    movimientosCuentaIndividual.setNssDestino(nssDestino);
                    movimientosCuentaIndividual.setCuentaIndividualOrigen(periodoNuevo.getCveIdCtaIndPadre().getCveIdCtaInd());
                    movimientosCuentaIndividual.setCuentaIndividualDestino(periodoNuevo.getCveIdCtaInd());

                } else if (ditCtaIndNssCda.getTipoRegularizacion().equals(TipoRegularizacionPeriodoEnum.MODIFICAR.getDesc())
                        && ditCtaIndNssCda.getOrigenCaptura().equals(TipoListaCapturaEnum.MODIFICADOS.getId())) {
                    logger.error("cve periodo padre {} ", periodoNuevo.getCveIdCtaIndPadre().getCveIdCtaInd());
                    logger.error("cve periodo nuevo {} ", periodoNuevo.getCveIdCtaInd());
                    movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.MODIFICAR.getId());
                    movimientosCuentaIndividual.setMovimientoDestino(TipoRegularizacionPeriodoEnum.MODIFICAR.getId());
                    movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());
                    movimientosCuentaIndividual.setNssDestino(cuentaIndividualNss.getCveIdDetalleNssCda());
                    movimientosCuentaIndividual.setCuentaIndividualOrigen(periodoNuevo.getCveIdCtaIndPadre().getCveIdCtaInd());
                    movimientosCuentaIndividual.setCuentaIndividualDestino(periodoNuevo.getCveIdCtaInd());

                } else if (ditCtaIndNssCda.getTipoRegularizacion().equals(TipoRegularizacionPeriodoEnum.ELIMINAR.getDesc())) {
                    movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.ELIMINAR.getId());
                    movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());
                    movimientosCuentaIndividual.setCuentaIndividualOrigen(ditCtaIndNssCda.getCveIdCtaIndPadre().getCveIdCtaInd());
                }

                // Calculo de consecutivos
                movimientosCuentaIndividual.setConsecutivo(consecutivo);
                logger.error("Convert: {}", movimientosCuentaIndividual);
                DitCorreccionCtaIndCda movimiento = cuentaIndividualConverter.convertMovimientiIndividualEntity(movimientosCuentaIndividual);
                logger.error("Persist: {}", movimiento.getCveIdCtaIndOperOrigen().getCveIdCtaInd());
                movimientoCuentaIndividualEntity.guardarMovimiento(movimiento);
            }

        }

        for (PeriodosRegistroPatronal periodosRegistro : cuentaIndividualNss.getListaPeriodosRegistroPatronal()) {
            for (PeriodoCuentaIndividual periodo : periodosRegistro.getPeriodosEliminados()) {
                MovimientosCuentaIndividual movimientosCuentaIndividual = new MovimientosCuentaIndividual();
                movimientosCuentaIndividual.setFechaAlta(Calendar.getInstance().getTime());
                movimientosCuentaIndividual.setMovimientoOrigen(TipoRegularizacionPeriodoEnum.ELIMINAR.getId());
                movimientosCuentaIndividual.setNssOrigen(cuentaIndividualNss.getCveIdDetalleNssCda());
                movimientosCuentaIndividual.setCuentaIndividualOrigen(periodo.getCveIdPeriodoAnterior());
                movimientosCuentaIndividual.setConsecutivo(consecutivo);

                DitCorreccionCtaIndCda movimiento = cuentaIndividualConverter.convertMovimientiIndividualEntity(movimientosCuentaIndividual);
                movimientoCuentaIndividualEntity.guardarMovimiento(movimiento);
            }
        }
        * */

    }
    
    private void eliminarPrevios(Long cveIdDetalleNss){
        List<DitCtaIndNssCda> periodosEnBase = cuentaIndividualLocal.getPeriodosGuardadosVentanilla(cveIdDetalleNss);
        List<DitCorreccionCtaIndCda> correcciones = movimientoCuentaIndividualEntity.obtenerMovimientosEliminar(cveIdDetalleNss);

        if (notEmpty(periodosEnBase)) {
            for (DitCtaIndNssCda ditCtaIndNssCda : periodosEnBase) {
                cuentaIndividualLocal.bajaPeriodo(ditCtaIndNssCda);
            }
        }

        if (notEmpty(correcciones)) {
            for (DitCorreccionCtaIndCda ditCorreccion : correcciones) {
                ditCorreccion.setFecRegistroBaja(Calendar.getInstance().getTime());
                movimientoCuentaIndividualEntity.actualizarMovimiento(ditCorreccion);
            }
        }
    }
    public void agregaHomonimia (
    		DitCorreccionCtaIndCda movimiento, Long idMovimientoCuentaIlogica )
    		throws TipoAclaracionCuentaIndividualException
    {    	   
    	
    	List<Object> Listaid;
    	String convert;
    	Long idTramite = null;
    	logger.info("---CDA  CI----orgen  {}",movimiento.getCveDetalleNssOperOrigen() );
    	logger.info("---CDA  CI----destino  {}",movimiento.getCveDetalleNssOperDestino() );
    	if(movimiento.getCveDetalleNssOperOrigen()!=movimiento.getCveDetalleNssOperDestino())
    	{	
    		logger.info("---CDA  CI Origen----movimiento id  {}",movimiento.getCveDetalleNssOperOrigen().getCveDetalleNss());
    		Listaid = movimientoAclaracionEntity.obtenerTipoNSSAclaracion(movimiento.getCveDetalleNssOperOrigen().getCveDetalleNss());
    		    		
	    		if(Listaid == null || Listaid.isEmpty())
	    		{
	    			logger.info("---CDA  CI Destino ----movimiento id  {}",movimiento.getCveDetalleNssOperDestino().getCveDetalleNss());
	        		Listaid = movimientoAclaracionEntity.obtenerTipoNSSAclaracion(movimiento.getCveDetalleNssOperDestino().getCveDetalleNss());
	    		}
	    		if (Listaid != null && !Listaid.isEmpty()) {
	    			for(Object idtram : Listaid )
	            	{
	            		convert = String.valueOf(idtram);
	            		idTramite = Long.parseLong(convert);
	            	}
	    			DitMovAclaracionNssCda aclaracion = movimientoAclaracionEntity.guardarMovimiento(cuentaIndividualConverter.convertAclaracionMovimientosEntity(movimiento, TipoRegularizacionSolicitudCDAEnum.HOMONIMIA.getId()));
	        		idMovimientoCuentaIlogica = aclaracion.getCveIdMovAclaracionNss();    		       
	                movimiento.setCveIdMovAclaracionNss(idTramite);
	                logger.info("---CDA  CI ----idTramite {}", movimiento.getCveIdMovAclaracionNss());
	                logger.info("---CDA  CI ----getCveIdMovAclaracionNss {}", movimiento.getCveIdMovAclaracionNss());
	                movimientoCuentaIndividualEntity.actualizarMovimiento(movimiento);
	                logger.info("---CDA  CI ---- termina movimiento");
	    		} 		
	    		else {
	    			throw new TipoAclaracionCuentaIndividualException(
	    					movimiento.getCveDetalleNssOperDestino().getCveDetalleNss().toString());
	    		}    		
    	}
    	else
    	{    		           	
             DitMovAclaracionNssCda aclaracion = movimientoAclaracionEntity.guardarMovimiento(cuentaIndividualConverter.convertAclaracionMovimientosEntity(movimiento, TipoRegularizacionSolicitudCDAEnum.CUENTA_ILOGICA.getId()));
             idMovimientoCuentaIlogica = aclaracion.getCveIdMovAclaracionNss();
             movimiento.setCveIdMovAclaracionNss(idMovimientoCuentaIlogica);
             movimientoCuentaIndividualEntity.actualizarMovimiento(movimiento);
    	}
    	
    }
    
    public void guardarMovimientosAclaracionCuentaIndividual_step1(
            DitCorreccionCtaIndCda movimiento, Long idMovimientoCuentaIlogica )
            		throws TipoAclaracionCuentaIndividualException {
      if ( ((String.valueOf(movimiento.getCveIdMovOperOrigen().getCveIdMovCorreccion()).equals(TipoRegularizacionPeriodoEnum.ELIMINAR.getId())
                    || String.valueOf(movimiento.getCveIdMovOperOrigen().getCveIdMovCorreccion()).equals(TipoRegularizacionPeriodoEnum.AGREGAR.getId()))
                    && movimiento.getCveIdMovOperDestino() == null)
                    
                    || (String.valueOf(movimiento.getCveIdMovOperOrigen().getCveIdMovCorreccion()).equals(TipoRegularizacionPeriodoEnum.MODIFICAR.getId())
                    && String.valueOf(movimiento.getCveIdMovOperDestino().getCveIdMovCorreccion()).equals(TipoRegularizacionPeriodoEnum.MODIFICAR.getId()))) {
                //&& TipoNSSCorreccionEnum.CERTIFICADOR.getId().equals(movimiento.getCveDetalleNssOperOrigen().getDicTipoNss().getCveTipoNss())
                
                if(idMovimientoCuentaIlogica==null){
                	logger.info("Agregó, eliminó, y/o modificó  al menos un periodo a la cuenta individual, y en este periodo se marcó como NSS Destino, el NSS Certificador.");
                    DitMovAclaracionNssCda aclaracion = movimientoAclaracionEntity.guardarMovimiento(cuentaIndividualConverter.convertAclaracionMovimientosEntity(movimiento, TipoRegularizacionSolicitudCDAEnum.CUENTA_ILOGICA.getId()));
                    idMovimientoCuentaIlogica = aclaracion.getCveIdMovAclaracionNss();
                }
                logger.info("se guarda el movimiento aclaracion NSS {}", idMovimientoCuentaIlogica);
                movimiento.setCveIdMovAclaracionNss(idMovimientoCuentaIlogica);
                movimientoCuentaIndividualEntity.actualizarMovimiento(movimiento);
                logger.info("se guarda el movimiento {}", movimiento);

            }else
            {
            	logger.info("se guarda el movimiento aclaracion NSS {}", idMovimientoCuentaIlogica);
            	try {
            		agregaHomonimia(movimiento,idMovimientoCuentaIlogica);
            	}
            	catch(TipoAclaracionCuentaIndividualException e) {
            		throw e;
            	}
            	
            }
     
    }
    
    @Override
    public void guardarMovimientosAclaracionCuentaIndividual(String folio) 
    		throws TipoAclaracionCuentaIndividualException {
    	logger.info("Se inicia guardado de moviemiento de cuenta Individual ");
        List<DitCorreccionCtaIndCda> movimientos = movimientoCuentaIndividualEntity.obtenerMovimientosByFolio(folio);     
        Long idMovimientoInvasion=null;
        Long idMovimientoCuentaIlogica=null;
        Long idMovimientoDesvinculacion=null;
        boolean mensaje = false;
        for (DitCorreccionCtaIndCda movimiento : movimientos) {
            
            //Cuando el NSS “Corresponde a otro asegurado” y en su periodo se marcó como NSS Destino alguno diferente al NSS Origen.
            if (TipoNSSCorreccionEnum.CORRESPONDE_A_OTRA_PERSONA.getId().equals(movimiento.getCveDetalleNssOperOrigen().getDicTipoNss().getCveTipoNss())
                    && movimiento.getCveDetalleNssOperDestino() != null && !movimiento.getCveDetalleNssOperOrigen().getCveDetalleNss().equals(
                           movimiento.getCveDetalleNssOperDestino().getCveDetalleNss())) {
                
                if(idMovimientoInvasion==null){
                	logger.info("Cuando el NSS “Corresponde a otro asegurado” y en su periodo se marcó como NSS Destino alguno diferente al NSS Origen.");
                    DitMovAclaracionNssCda aclaracion = movimientoAclaracionEntity.guardarMovimiento(cuentaIndividualConverter.convertAclaracionMovimientosEntity(movimiento, TipoRegularizacionSolicitudCDAEnum.INVASION.getId()));
                    idMovimientoInvasion = aclaracion.getCveIdMovAclaracionNss();
                }
                logger.info("se guarda el movimiento aclaracion NSS {}", idMovimientoDesvinculacion);
                movimiento.setCveIdMovAclaracionNss(idMovimientoInvasion); 
                movimientoCuentaIndividualEntity.actualizarMovimiento(movimiento);
                logger.info("se guarda el movimiento {}", movimiento);
                
            //- En el NSS Certificador el primer apellido contiene la leyenda “PASO AL” o “CAMBIO AL”. 
            //- En el NSS Corresponde a otra persona, el primer apellido contiene la leyenda “PASO AL” o “CAMBIO AL”, y 
            } else if ((TipoNSSCorreccionEnum.CERTIFICADOR.getId().equals(movimiento.getCveDetalleNssOperOrigen().getDicTipoNss().getCveTipoNss())
                    && isLeyendaApellidoPaterno(movimiento.getCveDetalleNssOperOrigen().getNss())) ||
                    TipoNSSCorreccionEnum.CORRESPONDE_A_OTRA_PERSONA.getId().equals(movimiento.getCveDetalleNssOperOrigen().getDicTipoNss().getCveTipoNss())
                    && isLeyendaApellidoPaterno(movimiento.getCveDetalleNssOperOrigen().getNss())
                    && !movimiento.getCveDetalleNssOperOrigen().getCveDetalleNss().equals(
                            movimiento.getCveDetalleNssOperDestino()!=null?movimiento.getCveDetalleNssOperDestino().getCveDetalleNss():null)) {
                
                if(idMovimientoDesvinculacion==null){
                	logger.info("En el NSS Certificador el primer apellido contiene la leyenda “PASO AL” o “CAMBIO AL”." + 
                			"En el NSS Corresponde a otra persona, el primer apellido contiene la leyenda “PASO AL” o “CAMBIO AL”, y");
                    DitMovAclaracionNssCda aclaracion = movimientoAclaracionEntity.guardarMovimiento(cuentaIndividualConverter.convertAclaracionMovimientosEntity(movimiento, TipoRegularizacionSolicitudCDAEnum.DESVINCULACION.getId()));
                    idMovimientoDesvinculacion=aclaracion.getCveIdMovAclaracionNss();
                }
                logger.info("se guarda el movimiento aclaracion NSS {}", idMovimientoDesvinculacion);
                movimiento.setCveIdMovAclaracionNss(idMovimientoDesvinculacion);              
                movimientoCuentaIndividualEntity.actualizarMovimiento(movimiento);
                logger.info("se guarda el movimiento {}", movimiento);
               
            //Agregó, eliminó, y/o modificó  al menos un periodo a la cuenta individual, y en este periodo se marcó como NSS Destino, el NSS Certificador.
            } else {
            	try {
	             this.guardarMovimientosAclaracionCuentaIndividual_step1(
	                              movimiento, idMovimientoCuentaIlogica);
            	}
            	catch (TipoAclaracionCuentaIndividualException e) {
            		throw e;
            	}
            } 
        }        
    }
    
    @Override
    public List<String> obtenerMovimientosAclaracion(String folio){
        List<String> listaTipoTramite = new ArrayList<String>();
        List<DitMovAclaracionNssCda> listaMovimientos = movimientoAclaracionEntity.obtenerMovimientosAclaracion(folio);
        for (DitMovAclaracionNssCda aclaracion : listaMovimientos) {
        	if(aclaracion.getFecRegistroBaja()== null)        		
            if (aclaracion != null && aclaracion.getCveIdTipoTramCorrecNss() != null){
                if(listaTipoTramite.isEmpty()){
                	logger.info("--CDA-CI-Lista Aclaracion--{}",aclaracion.getCveIdTipoTramCorrecNss().getDescTipoCorreccionTramNss());
                   listaTipoTramite.add(aclaracion.getCveIdTipoTramCorrecNss().getDescTipoCorreccionTramNss());
                }else{
                   if(!listaTipoTramite.contains(aclaracion.getCveIdTipoTramCorrecNss().getDescTipoCorreccionTramNss())  ){
                	   logger.info("--CDA-CI-Lista Aclaracion--{}",aclaracion.getCveIdTipoTramCorrecNss().getDescTipoCorreccionTramNss());
                       listaTipoTramite.add(aclaracion.getCveIdTipoTramCorrecNss().getDescTipoCorreccionTramNss());
                   } 
                }
            }
        }
        return listaTipoTramite;
    }

    private Boolean isLeyendaApellidoPaterno(String nss) {
        Boolean tieneLeyenda = Boolean.FALSE;

        List<Fisica> personasFuentesNSS = serviceBusiness.getAseguradoByNSSLegadosyBDTU(nss, true);

        for (Fisica fisica : personasFuentesNSS) {
            if (fisica.getPrimerApellido().contains("PASO AL") || fisica.getPrimerApellido().contains("CAMBIO AL")) {
                tieneLeyenda = Boolean.TRUE;
            }
            break;
        }

        return tieneLeyenda;
    }

    @Override
    public CuentaIndividual findByFolio(String folioSolicitud) {
        CuentaIndividual cuentaIndividual = new CuentaIndividual();
        cuentaIndividual.setFolioSolicitud(folioSolicitud);
        cuentaIndividual.setListaCuentaIndividualNssAsociado(new ArrayList<CuentaIndividualNss>());
        cuentaIndividual.setListaCuentaIndividualNssCertificador(new ArrayList<CuentaIndividualNss>());
        cuentaIndividual.setListaCuentaIndividualNssNoPertenece(new ArrayList<CuentaIndividualNss>());
        /*
         * FIX: Desde aqui se puede obtener el listado de NSS
         */
        List<DitDetalleNss> detallesNss = detalleNssCdaLocal.getListNssByFolio(folioSolicitud);
        if (detallesNss != null) {
            for (DitDetalleNss detalle : detallesNss) {
                CuentaIndividualNss cuentaIndivNss = new CuentaIndividualNss();
                cuentaIndivNss.setNss(detalle.getNss());
                cuentaIndivNss.setCveIdDetalleNssCda(detalle.getCveDetalleNss());
                TipoNSSCorreccionEnum tipoNssCorreccion = TipoNSSCorreccionEnum.fromId(detalle.getDicTipoNss().getCveTipoNss());
                logger.debug(" --- -- --> nss encontrado:{} tipoNssCorreccion:{}", cuentaIndivNss.getNss(), tipoNssCorreccion.getId());

                switch (tipoNssCorreccion) {
                    case ASOCIADO_AL_CERTIFICADOR:
                        cuentaIndividual.getListaCuentaIndividualNssAsociado().add(cuentaIndivNss);
                        break;
                    case CERTIFICADOR:
                        cuentaIndividual.getListaCuentaIndividualNssCertificador().add(cuentaIndivNss);
                        break;
                    case CORRESPONDE_A_OTRA_PERSONA:
                        cuentaIndividual.getListaCuentaIndividualNssNoPertenece().add(cuentaIndivNss);
                        break;
                }
            }
        }

        return cuentaIndividual;
    }

    private DitCtaIndNssCda validarTipoPeriodo(DitCtaIndNssCda periodo)
    {
    	int wshistorico =periodo.getIndHistoricoCentral();
    	if(wshistorico == 1)
    	{
    		logger.info("--CDA-CI-Validacion--Se inicia la validacion del periodo del WS Historico");

    		
    		periodo.setCveSubrServicios("0");                             
    		periodo.setCveExtConvSusp("0");
    		periodo.setFecActualizacion("0");
        // El servicio ya regresa la delegacion de origen
    		//periodo.setCveDelegacionOrigen(0L);          
    		periodo.setFecCarga(null);
//    		
    	}
    	return periodo;
    }
    
    private static Boolean notEmpty(List lista) {
        return lista != null && !lista.isEmpty();
    }

}
