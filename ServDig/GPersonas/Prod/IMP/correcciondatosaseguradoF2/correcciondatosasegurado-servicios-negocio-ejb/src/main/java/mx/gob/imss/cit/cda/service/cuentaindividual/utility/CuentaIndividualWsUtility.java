package mx.gob.imss.cit.cda.service.cuentaindividual.utility;

import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.BindingProvider;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualNoDisponibleException;
import mx.gob.imss.cit.cda.service.utility.PropertiesEndpoints;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.CuentaIndividualVo;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.RespuestaCuentaIndividual;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual_Service;
import mx.gob.imss.cit.ws.cuentaindividual.historico.HistoricoCentralVo;
import mx.gob.imss.cit.ws.cuentaindividual.historico.RespuestaHistoricoCentral;
import mx.gob.imss.cit.ws.cuentaindividual.historico.WsHistorioCentralService;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoMovtoAseguradoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.wsConsultaPatron.WSConsultaPatronServiceProxy;
import mx.gob.imss.ctirss.wsConsultaPatron.vo.InfoPatronSalida;
import mx.gob.imss.ctirss.wsConsultaPatron.vo.InfoPatronEntrada;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class CuentaIndividualWsUtility extends AbstractServiceEntity implements CuentaIndividualWsUtilityLocal {
    
    /**
     * Logger de la clase
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @EJB
    private SolicitudBusinessRemote solicitudBusinessRemote;
    
    private static final String REQUEST_TIMEOUT = "common.request.timeout";
    private static final String CONNECT_TIMEOUT = "common.connect.timeout";
    
    @Override
    public List<PeriodosRegistroPatronal> buscarPeriodosRegistroPatronalPorNss(String nss, List<String> listaNss) throws CuentaIndividualNoDisponibleException{
        List<PeriodosRegistroPatronal> periodosRegistroPatronal = new ArrayList<PeriodosRegistroPatronal>();
        logger.error(" --- -- NSS recibido: {}", nss);
        try {
            List<CuentaIndividualVo> periodosCuentaIndividual = consultaWsCuentaIndividual(nss);
            //List<HistoricoCuentaIndividualVo> periodosCuentaIndividualHistorico = consultaWsCuentaIndividualHistorico(nss);

            List<HistoricoCentralVo> periodosCuentaIndividualHistorico = consultaWsCuentaIndividualHistorico(nss);
            
            // Agrupa por Registro Patronal los Periodos obtenidos de WS Cuenta Individual y WS Cuenta Indiivual Historico
            Map<String, PeriodosRegistroPatronal> mapaListas = agrupaListasCuentaIndividual(periodosCuentaIndividual, periodosCuentaIndividualHistorico, listaNss);

            // Se vuelven a conformar en una lista los periodos agrupados
            for (Entry<String, PeriodosRegistroPatronal> elemento : mapaListas.entrySet()) {
                periodosRegistroPatronal.add(elemento.getValue());
                logger.error(" --- -- --- RegistroPatronal: {} con {} periodos",
                        elemento.getValue().getNumeroRegistroPatronal(), elemento.getValue().getPeriodos().size());
            }

            // Obtener y asignar Registro Patronal
            for (PeriodosRegistroPatronal periodo : periodosRegistroPatronal) {
                periodo.setNombreRegistroPatronal(consultaWsRegistroPatronal(periodo.getClaveModalidad(), periodo.getNumeroRegistroPatronal()));
            }

            // Obtener y asignar Delegación origen
            for (PeriodosRegistroPatronal periodo : periodosRegistroPatronal) {
                periodo.setNombreDelegacionOrigen(consultaDelegacion(periodo.getClaveDelegacionOrigen()));
            }
            
        }catch( CuentaIndividualNoDisponibleException e1 ){
          logger.error("Ocurrió una excepción al buscarPeriodosRegistroPatronalPorNss", e1);
          throw e1;
        } 
        catch (Exception exception) {
            logger.error("Ocurrió una excepción al buscarPeriodosRegistroPatronalPorNss", exception);
        }
        return periodosRegistroPatronal;
    }
    @Override
    public List<PeriodoMovimientoAfiliatorio> obtenerPeriodosMovimientoAfiliatorioNSS(String nss) {

        try {
            return this.obtenerPeriodosMovimientoAfiliatorio(this.consultaWsCuentaIndividual(nss));
        } catch (Exception e) {
            logger.error("Ocurrió una excepción al consultar la cuenta individual NSS " + nss);
            return null;
        }

    }
    
    private Map<String, PeriodosRegistroPatronal> agrupaListasCuentaIndividual(
            List<CuentaIndividualVo> listaCuentaInd, List<HistoricoCentralVo> listaCuentaIndHist, List<String> listaNss) {
        Map<String, PeriodosRegistroPatronal> mapaPeriodos = new HashMap<String, PeriodosRegistroPatronal>();
        
        if (listaCuentaInd != null && !listaCuentaInd.isEmpty()){
            logger.error(" --- -- INICIA AGRUPACIÓN DE PERIODOS DE CUENTA INDIVIDUAL  --- -- ");
            for (CuentaIndividualVo ctaIndiv: listaCuentaInd){
                // Si el periodo ya está contenido en el mapa, sólo agrega PeriodoCuentaIndividual a lista de periodos
                if (mapaPeriodos.containsKey(ctaIndiv.getRegistroPatronal() + ctaIndiv.getClaveModalidad())) {
                    logger.error(" --- -- --- --- {} registro patronal EXISTENTE", ctaIndiv.getRegistroPatronal());
                    logger.error(" ----------> nss agregado: {}", ctaIndiv.getNss());
                    mapaPeriodos.get(ctaIndiv.getRegistroPatronal() + ctaIndiv.getClaveModalidad()).getPeriodos()
                            .add(armaPeriodoCuentaIndividual(ctaIndiv));
                    
                } else {
                    logger.error(" --- -- --- {} registro patronal NUEVO", ctaIndiv.getRegistroPatronal());
                    // Si no está en el mapa, crea nueva instancia de PeriodosRegistroPatronal con su lista periodos e ingresa el primer elemento
                    PeriodosRegistroPatronal periodoRP = new PeriodosRegistroPatronal();
                    periodoRP.setNumeroRegistroPatronal(ctaIndiv.getRegistroPatronal());
                    periodoRP.setClaveModalidad(ctaIndiv.getClaveModalidad());
                    periodoRP.setClaveDelegacionOrigen(ctaIndiv.getClaveDelegacionOrigen());
                    periodoRP.setPeriodos(new ArrayList<PeriodoCuentaIndividual>());
                    periodoRP.setListaNss(listaNss);
                    periodoRP.setClaveCiz( ctaIndiv.getClaveCiz() );
                    periodoRP.getPeriodos().add(armaPeriodoCuentaIndividual(ctaIndiv));
                    mapaPeriodos.put(ctaIndiv.getRegistroPatronal() + ctaIndiv.getClaveModalidad(), periodoRP);
                }
            }
        }           
        if (listaCuentaIndHist != null && !listaCuentaIndHist.isEmpty()){
            logger.error(" --- -- INICIA AGRUPACIÓN DE PERIODOS DE CUENTA INDIVIDUAL HISTÓRICO --- -- ");
            for (HistoricoCentralVo histCtaIndiv: listaCuentaIndHist){
                if (mapaPeriodos.containsKey(histCtaIndiv.getRegistroPatronal() + histCtaIndiv.getClaveModalidad())) {
                    logger.error(" --- -- --- --- {} registro patronal EXISTENTE ", histCtaIndiv.getRegistroPatronal());
                    logger.error(" ----------> NSS AGREGADO: {}", histCtaIndiv.getNss());
                    mapaPeriodos.get(histCtaIndiv.getRegistroPatronal() + histCtaIndiv.getClaveModalidad())
                            .getPeriodos().add(armaPeriodoCuentaIndividualHistorico(histCtaIndiv));
                } else {
                    logger.error(" --- -- --- {} registro patronal NUEVO", histCtaIndiv.getRegistroPatronal());
                    // Si no está en el mapa, crea nueva instancia de PeriodosRegistroPatronal con su lista periodos e ingresa el primer elemento
                    PeriodosRegistroPatronal periodoRP = new PeriodosRegistroPatronal();
                    periodoRP.setNumeroRegistroPatronal(histCtaIndiv.getRegistroPatronal());
                    periodoRP.setClaveModalidad(histCtaIndiv.getClaveModalidad());
                    periodoRP.setClaveDelegacionOrigen(Integer.parseInt(histCtaIndiv.getCveDelegOrig()));
                    periodoRP.setPeriodos(new ArrayList<PeriodoCuentaIndividual>());
                    periodoRP.setListaNss(listaNss);
                    periodoRP.setClaveCiz( histCtaIndiv.getClaveCiz() );
                    periodoRP.getPeriodos().add(armaPeriodoCuentaIndividualHistorico(histCtaIndiv));
                    mapaPeriodos.put(histCtaIndiv.getRegistroPatronal() + histCtaIndiv.getClaveModalidad(), periodoRP);
                }
            }
        }
        
        return mapaPeriodos;
    }
    
    
    private String validarCampVacioTipoSalario(CuentaIndividualVo ctaIndiv) 
    {
    	if((ctaIndiv.getTipoSalario().trim()).isEmpty()){
    		ctaIndiv.setTipoSalario(null);
    	}
    return ctaIndiv.getTipoSalario();
    }
    
    private String validarCampVacioJornadaSemanal(CuentaIndividualVo ctaIndiv) 
    {
    	if((ctaIndiv.getJornadaSemanal().trim()).isEmpty() ){
    		ctaIndiv.setJornadaSemanal(null);
    	}
    return ctaIndiv.getJornadaSemanal();
    } 
    
    private String validarCampVacioEventual(CuentaIndividualVo ctaIndiv) 
    {
    	
    	if((ctaIndiv.getEventual().trim()).isEmpty() ){
    		ctaIndiv.setEventual(null);
    	}    	
    	return ctaIndiv.getEventual();
    }
    private String validarExtemporaneoConvenioSuspension(CuentaIndividualVo ctaIndiv) 
    {    	
    	if((ctaIndiv.getExtemporaneoConvenioSuspencion().trim()).isEmpty() 
    			|| ctaIndiv.getExtemporaneoConvenioSuspencion()==null )
    	{
    		ctaIndiv.setExtemporaneoConvenioSuspencion(null);
    	}    	
    	return ctaIndiv.getExtemporaneoConvenioSuspencion();
    }
    
    private String validarFechaActualizacionl(String validarFechaActualizacionl) 
    {    	
    	if((validarFechaActualizacionl.trim()).isEmpty()|| validarFechaActualizacionl == null ){
    		validarFechaActualizacionl=null;
    	}    	
    	return validarFechaActualizacionl;
    }
    
    private String validarsetFechaCarga(String setFechaCarga) 
    {
    	
    	if((setFechaCarga.trim()).isEmpty()|| setFechaCarga == null ){
    		setFechaCarga=null;
    	}    	
    	return setFechaCarga;
    }
    private PeriodoCuentaIndividual armaPeriodoCuentaIndividual(CuentaIndividualVo ctaIndiv) {
        int INDIVIDUAL = 0;
        PeriodoCuentaIndividual periodoCuentaIndividual = new PeriodoCuentaIndividual();
        periodoCuentaIndividual.setNss(ctaIndiv.getNss());
        periodoCuentaIndividual.setFechaInicioMovimiento(formatearFecha(ctaIndiv.getFechaInicioMovimiento()));
        periodoCuentaIndividual.setNumeroRegistroPatronal(ctaIndiv.getRegistroPatronal());
        periodoCuentaIndividual.setNumeroConsecutivoPeriodos(ctaIndiv.getNumeroConsecutivoPeriodos());
        periodoCuentaIndividual.setFechaFinalMovimiento(formatearFecha(ctaIndiv.getFechaFinalMovimiento()));
        periodoCuentaIndividual.setOrigenMovimientoInicial(ctaIndiv.getOrigenMovimientoInicial());
        periodoCuentaIndividual.setOrigenMovimientoFinal(ctaIndiv.getOrigenMovimientoFinal());
        periodoCuentaIndividual.setTipoMovimientoInicial(ctaIndiv.getTipoMovimientoIniintcial());
        periodoCuentaIndividual.setTipoMovimientoFinal(ctaIndiv.getTipoMovimientoFinal());
        periodoCuentaIndividual.setFechaRecepcionMovimiento(formatearFecha(ctaIndiv.getFechaRecepcionMovimiento()));
        periodoCuentaIndividual.setSalarioBase(ctaIndiv.getSalarioBase());
        periodoCuentaIndividual.setSubrogacionServicio(ctaIndiv.getSubrogacionServicio());
        periodoCuentaIndividual.setHuelga(ctaIndiv.getHuelga());
        periodoCuentaIndividual.setClaveCiz(ctaIndiv.getClaveCiz());
        
        periodoCuentaIndividual.setTipoSalario(validarCampVacioTipoSalario(ctaIndiv));
        periodoCuentaIndividual.setJornadaSemanal(validarCampVacioJornadaSemanal(ctaIndiv));
        periodoCuentaIndividual.setEventual(validarCampVacioEventual(ctaIndiv));
                
//        periodoCuentaIndividual.setExtemporaneoConvenioSuspension(ctaIndiv.getExtemporaneoConvenioSuspencion());
//        periodoCuentaIndividual.setFechaActualizacion(formatearFecha(ctaIndiv.getFechaActualizacion()));        
//        periodoCuentaIndividual.setFechaCarga(formatearFecha(ctaIndiv.getFechaCarga()));
//        periodoCuentaIndividual.setTipoSalario(ctaIndiv.getTipoSalario());
//        periodoCuentaIndividual.setJornadaSemanal(ctaIndiv.getJornadaSemanal());
//        periodoCuentaIndividual.setEventual(ctaIndiv.getEventual());
        periodoCuentaIndividual.setFechaActualizacion(validarFechaActualizacionl(formatearFecha(ctaIndiv.getFechaActualizacion())));//poner nulo 
        periodoCuentaIndividual.setFechaCarga(validarsetFechaCarga(formatearFecha(ctaIndiv.getFechaCarga())));
        periodoCuentaIndividual.setExtemporaneoConvenioSuspension(validarExtemporaneoConvenioSuspension(ctaIndiv));//null
        logger.info("--CDA cI valores-- -");
        logger.info("setTipoSalario {}" ,       		periodoCuentaIndividual.getTipoSalario());
        logger.info("setJornadaSemanal - {} ", periodoCuentaIndividual.getJornadaSemanal());
        logger.info(" setFechaActualizacion - {}",periodoCuentaIndividual.getFechaActualizacion());
        logger.info("setExtemporaneoConvenioSuspension- {}",	periodoCuentaIndividual.getExtemporaneoConvenioSuspension());
      	logger.info("setEventual- {} ",periodoCuentaIndividual.getEventual());
        logger.info(" setFechaCarga- {}",periodoCuentaIndividual.getFechaCarga());
        periodoCuentaIndividual.setHistorico(INDIVIDUAL);
        
        periodoCuentaIndividual.setClaveDelegacionOrigen( ctaIndiv.getClaveDelegacionOrigen() );
        
        return periodoCuentaIndividual;
    }
    
    private PeriodoCuentaIndividual armaPeriodoCuentaIndividualHistorico(HistoricoCentralVo ctaIndiv) {
        int HISTORICO = 1;
        PeriodoCuentaIndividual periodoCuentaIndividual = new PeriodoCuentaIndividual();
        periodoCuentaIndividual.setNss(ctaIndiv.getNss());
        periodoCuentaIndividual.setFechaInicioMovimiento(formatearFecha(ctaIndiv.getFechaInicioMovimiento()));
        periodoCuentaIndividual.setNumeroRegistroPatronal(ctaIndiv.getRegistroPatronal());
        periodoCuentaIndividual.setNumeroConsecutivoPeriodos( (int) ctaIndiv.getNumeroConsecutivoPeriodos());
        periodoCuentaIndividual.setFechaFinalMovimiento(formatearFecha(ctaIndiv.getFechaFinalMovimiento()));
        periodoCuentaIndividual.setOrigenMovimientoInicial(ctaIndiv.getOrigenMovimientoInicial());
        periodoCuentaIndividual.setOrigenMovimientoFinal(ctaIndiv.getOrigenMovimientoFinal());
        periodoCuentaIndividual.setTipoMovimientoInicial(ctaIndiv.getTipoMovimientoInicial() );
        periodoCuentaIndividual.setTipoMovimientoFinal(ctaIndiv.getTipoMovimientoFinal());
        periodoCuentaIndividual.setFechaRecepcionMovimiento(formatearFecha(ctaIndiv.getFechaRecepcionMovimiento()));
        periodoCuentaIndividual.setSalarioBase(ctaIndiv.getSalarioBase());
        periodoCuentaIndividual.setHuelga(ctaIndiv.getHuelga());        
        periodoCuentaIndividual.setClaveCiz(ctaIndiv.getClaveCiz());
        periodoCuentaIndividual.setTipoSalario(ctaIndiv.getTipoSalario());
        periodoCuentaIndividual.setClaveDelegacionOrigen( Integer.parseInt( ctaIndiv.getCveDelegOrig() ) ); 
        //ctaIndiv.getCveDelegOrig()
//        periodoCuentaIndividual.setExtemporaneoConvenioSuspension(null);
//        periodoCuentaIndividual.setFechaActualizacion(null);
//        periodoCuentaIndividual.setFechaCarga(null);
        periodoCuentaIndividual.setHistorico(HISTORICO);
        
        return periodoCuentaIndividual;
    }
    
    private String formatearFecha(String fechaSinFormato) {      
      String fechaFormato = "";
      StringTokenizer strtok = new StringTokenizer(fechaSinFormato,"-",false);
      if( strtok.countTokens() == 3 ){
        String year = strtok.nextToken();
        String month = strtok.nextToken();
        String day = strtok.nextToken();
        
        StringBuilder sb = new StringBuilder();
        fechaFormato = sb.append(day).append("/").append(month).append("/").append(year).toString();
      }
        
        return fechaFormato;
    }
    

    private List<CuentaIndividualVo> consultaWsCuentaIndividual(String nss) throws CuentaIndividualNoDisponibleException{
        PropertiesEndpoints properties = new PropertiesEndpoints();
        Map<String, String> opciones = properties.getOpciones();
        
        List<CuentaIndividualVo> listaCuentaIndividualVO = new ArrayList<CuentaIndividualVo>();

        try {
            logger.error(" ---- -- Consumiendo el web service de Cuenta Individual");
            logger.error("Timeouts: " + opciones.get(CONNECT_TIMEOUT));
            WSNssCuentaIndividual_Service service = 
                    new WSNssCuentaIndividual_Service(new URL(opciones.get("servicio.cuentaindividual.documento.wsdl")),
                    new javax.xml.namespace.QName("http://cuentaIndividual.imss.gob.mx/", "WSNssCuentaIndividual"));
            WSNssCuentaIndividual port = service.getWSNssCuentaIndividualPort();
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout",
                    Integer.parseInt(opciones.get(REQUEST_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout",
                    Integer.parseInt(opciones.get(CONNECT_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.request.timeout",
                    Integer.parseInt(opciones.get(REQUEST_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.connect.timeout",
                    Integer.parseInt(opciones.get(CONNECT_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("javax.xml.ws.client.receiveTimeout",
                    Integer.parseInt(opciones.get(REQUEST_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("javax.xml.ws.client.connectionTimeout",
                    Integer.parseInt(opciones.get(CONNECT_TIMEOUT)));            
            RespuestaCuentaIndividual respuesta = port.getCuentaIndividual(nss);
            if (respuesta != null ){
                listaCuentaIndividualVO = respuesta.getCuentaIndividual();
            }
        } catch (Exception exception) {
            logger.error(" ---- -- Ocurrio un error al consumir el web service", exception);
            throw new CuentaIndividualNoDisponibleException("No fue posible consultar la Cuenta Individual");
        }        
        return listaCuentaIndividualVO;
    }
    
    
    
    private List<HistoricoCentralVo> consultaWsCuentaIndividualHistorico(String nss) throws CuentaIndividualNoDisponibleException{
      try{
      WsHistorioCentralService service = new WsHistorioCentralService();
      RespuestaHistoricoCentral historicoCentralByNss =
              service.getWSHistoricoCentralPort().getHistoricoCentralByNss(nss);
      return historicoCentralByNss.getListaRespuesta();
      }catch(Exception e){
        logger.error(" ---- -- Ocurrio un error al consumir el web service", e);
        throw new CuentaIndividualNoDisponibleException("No fue posible consultar la Cuenta Individual Historica");
      }
    }
    
    /*
    private List<HistoricoCuentaIndividualVo> consultaWsCuentaIndividualHistorico(String nss){        
        PropertiesEndpoints properties = new PropertiesEndpoints();
        Map<String, String> opciones = properties.getOpciones();
        
        List<HistoricoCuentaIndividualVo> listaHistoricoCuentaIndividualVo = new ArrayList<HistoricoCuentaIndividualVo>();

        try {
            logger.error(" ---- -- Consumiendo el web service de Cuenta Individual Historico");
            logger.error("Timeouts: " + opciones.get(CONNECT_TIMEOUT));
            WSHistoricoCentralSISEC_Service service = 
                    new WSHistoricoCentralSISEC_Service(new URL(opciones.get("servicio.cuentaindivhistorico.documento.wsdl")),
                    new javax.xml.namespace.QName("http://cuentaIndividual.sisec.imss.gob.mx/","WSHistoricoCentralSISEC"));
            WSHistoricoCentralSISEC port = service.getWSHistoricoCentralSISECPort();
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout",
                    Integer.parseInt(opciones.get(REQUEST_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout",
                    Integer.parseInt(opciones.get(CONNECT_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.request.timeout",
                    Integer.parseInt(opciones.get(REQUEST_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.connect.timeout",
                    Integer.parseInt(opciones.get(CONNECT_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("javax.xml.ws.client.receiveTimeout",
                    Integer.parseInt(opciones.get(REQUEST_TIMEOUT)));
            ((BindingProvider) port).getRequestContext().put("javax.xml.ws.client.connectionTimeout",
                    Integer.parseInt(opciones.get(CONNECT_TIMEOUT)));            
            
            RespuestaHistoricoCuentaIndividual respHistoricoCuentaIndividual = port.getHistoricoCentralSISEC(nss);
                        
            if (respHistoricoCuentaIndividual != null ){
                listaHistoricoCuentaIndividualVo = respHistoricoCuentaIndividual.getHistcuentaIndividual();
            }
        } catch (Exception exception) {
            logger.error(" ---- -- Ocurrio un error al consumir el web service", exception);
        }
        return listaHistoricoCuentaIndividualVo;
    }    
    * /
    
    /**
     * Consume al EJB local para obtener la descripción de la Delegación
     * @param claveDelegacion
     * @return 
     */
    private String consultaDelegacion(long claveDelegacion) {
        try {
            logger.error(" ---- -- Consumiendo el web service para cveDelegacion: {}", claveDelegacion);
            Delegacion delegacion = getSolicitudBusinessRemote().getDatosDelegacion(claveDelegacion);
            if (delegacion != null) {
                return delegacion.getDescripcion();
            }
        } catch (Exception exception) {
            logger.error(" ---- -- Ocurrio un error al consumir el web service", exception);
        }        
        return StringUtils.EMPTY;
    }
    
    /**
     * Método para invocar al web service de Registro Patronal 
     */
    private String consultaWsRegistroPatronal(String claveModalidad, String numRegistroPatronal) {
        PropertiesEndpoints properties = new PropertiesEndpoints();
        Map<String, String> opciones = properties.getOpciones();
        
        logger.error(" ---- -- Consumiendo el web service de Registro Patronal {}  {}", claveModalidad, numRegistroPatronal);
        WSConsultaPatronServiceProxy proxy = new WSConsultaPatronServiceProxy();
        proxy.setEndpoint(opciones.get("servicio.registropatronal"));
        InfoPatronSalida respuesta = new InfoPatronSalida();
        try {
            respuesta = proxy.getInformacionPatron(crearPeticionPatron(claveModalidad, numRegistroPatronal));
        } catch (RemoteException remoteException) {
            logger.error("Error de conexion al web service de Registro Patronal", remoteException);
        }
        return respuesta.getRazonSocial();
    }
    
    /**
     * Metodo auxiliar usado por el metodo consultaNombreRegistroPatronal
     * @param claveModalidad
     * @param registroPatronal
     * @return InfoPatronEntrada
     */
    private InfoPatronEntrada crearPeticionPatron(String claveModalidad, String registroPatronal){
        InfoPatronEntrada peticion = new InfoPatronEntrada();
        peticion.setDigitoVerificador("1");
        peticion.setModalidad(claveModalidad);
        peticion.setRegistroPatronal(registroPatronal);
        return peticion;
    }

    private List<PeriodoMovimientoAfiliatorio> obtenerPeriodosMovimientoAfiliatorio(List<CuentaIndividualVo> respuesta) {

        List<PeriodoMovimientoAfiliatorio> periodos = new ArrayList<PeriodoMovimientoAfiliatorio>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (CuentaIndividualVo cuenta : respuesta) {
            PeriodoMovimientoAfiliatorio periodo = new PeriodoMovimientoAfiliatorio();

            try {
                TipoMovtoAsegurado mvtoFinal = new TipoMovtoAsegurado();
                mvtoFinal.setIdTipoMvtoAsegurado(cuenta.getTipoMovimientoFinal());
                mvtoFinal.setDesTipoMvtoAsegurado(TipoMovtoAseguradoEnum.getById(Integer.valueOf(cuenta.getTipoMovimientoFinal()).longValue()).getDesTipoMvtoAsegurado());
                TipoMovtoAsegurado mvtoInicial = new TipoMovtoAsegurado();
                mvtoInicial.setIdTipoMvtoAsegurado(Integer.valueOf(cuenta.getTipoMovimientoIniintcial()).longValue());
                mvtoFinal.setDesTipoMvtoAsegurado(TipoMovtoAseguradoEnum.getById(Integer.valueOf(cuenta.getTipoMovimientoIniintcial()).longValue()).getDesTipoMvtoAsegurado());
                periodo.setFechaFinalMovimiento(dateFormat.parse(cuenta.getFechaFinalMovimiento()));
                periodo.setFechaInicioMovimiento(dateFormat.parse(cuenta.getFechaInicioMovimiento()));
                periodo.setNrp(cuenta.getRegistroPatronal());
                periodo.setNss(cuenta.getNss());
                periodo.setTipoMovimientoFinal(mvtoFinal);
                periodo.setTipoMovimientoInicial(mvtoInicial);
                periodo.setCveModalidad(this.getModalidad(cuenta));
                periodos.add(periodo);
            } catch (ParseException var9) {
                this.logger.debug("Error al transformar las fechas del periodo, se excuye de la lista", var9);
            }
        }

        return periodos;
    }

    private Modalidad getModalidad(CuentaIndividualVo cuenta) {

        Modalidad modalidad = new Modalidad();
        for (ModalidadEnum modalidadEnum : ModalidadEnum.values()) {
            if (modalidadEnum.getNumModalidad().equals(cuenta.getClaveModalidad())) {
                modalidad.setDesCorta(modalidadEnum.getNumModalidad());
                modalidad.setIdModalidad(modalidadEnum.getId());
                break;
            }
        }
        return modalidad;
    }
    
    public SolicitudBusinessRemote getSolicitudBusinessRemote() {
        return solicitudBusinessRemote;
    }
}
