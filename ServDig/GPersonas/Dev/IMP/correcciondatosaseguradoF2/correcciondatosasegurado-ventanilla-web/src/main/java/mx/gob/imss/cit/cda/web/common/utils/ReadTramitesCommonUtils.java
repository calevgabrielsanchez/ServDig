package mx.gob.imss.cit.cda.web.common.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.service.interfaces.ConsultaBandejaRemote;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.bandeja.vo.RequestSolicitudBandejaPage;
import mx.gob.imss.cit.cda.web.bandeja.vo.SolicitudBandeja;
import mx.gob.imss.cit.cda.web.bandeja.vo.TareaTramite;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.cda.web.utils.DeltaUtils;
import mx.gob.imss.cit.cda.web.utils.WorkFlowDataUtil;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReadTramitesCommonUtils {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private DeltaUtils deltaUtils = new DeltaUtils();
    
    private static final String FORMATO_FECHA ="dd/MM/yyyy";
    
    private static final String USUARIO_ERROR_SINDO = "SINDO";
    
    @Autowired
    @Qualifier("consultaBandejaBusiness")
    private ConsultaBandejaRemote consultaBandejaBusiness;
    
    @Autowired
    private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;
    
    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;
    
    
    public DataPage prepararFiltros(RequestReadEvent<RequestSolicitudBandejaPage> requestReadEvent) {

        DataPage dataPage = new DataPage();
        dataPage.setCurrentPage(requestReadEvent.getData().getPage());
        dataPage.setPageSize(requestReadEvent.getData().getPageSize());

        ArrayList<HashMap<String, String>> listFilter = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> filtros = new HashMap<String, String>();

        if (requestReadEvent.getData().getFilter() != null) {
            getLogger().debug("Filtros de Busqueda", requestReadEvent.getData() .getFilter().toString());

            filtros.put("filtroFolio", requestReadEvent.getData().getFilter().getFolio());
            
            if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getFechaSolicitud())){
                filtros.put("filtroFechaSolicitud",requestReadEvent.getData().getFilter().getFechaSolicitud());
            }
            
            filtros.put("filtroNss", requestReadEvent.getData().getFilter().getNss());

            if (StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getOrigen()) && !requestReadEvent.getData().getFilter().getOrigen().equals("-1")) {
                filtros.put("filtroOrigen", requestReadEvent.getData() .getFilter().getOrigen());
            }

            if (StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getResponsable()) && !requestReadEvent.getData().getFilter().getResponsable().equals("-1")) {
                filtros.put("filtroResponsable", requestReadEvent.getData().getFilter().getResponsable());
            }

            if (StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getAutorizo()) && !requestReadEvent.getData().getFilter().getAutorizo().equals("-1")) {
                filtros.put("filtroAutorizo", requestReadEvent.getData().getFilter().getAutorizo());
            }

            if (StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getEstado()) && !requestReadEvent.getData().getFilter().getEstado().equals("-1")) {
                filtros.put("filtroEstado", requestReadEvent.getData().getFilter().getEstado());
            }

            if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getCurp())){ 
                filtros.put("filtroCurp", requestReadEvent.getData().getFilter().getCurp());
            }

            if (StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getTramite()) && !requestReadEvent.getData().getFilter().getTramite() .equals("-1")) {
                filtros.put("filtroTramite", requestReadEvent.getData().getFilter().getTramite());
            }
            
            if(StringUtils.isNotBlank(requestReadEvent.getData().getFilter().getFechaActualizacion())){
                filtros.put("filtroFechaActualizacion",requestReadEvent.getData().getFilter().getFechaActualizacion());
            }

            if (requestReadEvent.getData().getFilter().getFoliosVencidos() != null && requestReadEvent.getData().getFilter().getFoliosVencidos()) {
                filtros.put("filtroVencido", requestReadEvent.getUserProfile().getUsuario());
            }

            if (requestReadEvent.getData().getFilter().getFoliosAsociados() != null && requestReadEvent.getData().getFilter().getFoliosAsociados()) {
                filtros.put("filtroUsuario", requestReadEvent.getUserProfile().getUsuario());
            }
        }
        
        listFilter.add(filtros);
        dataPage.setData(listFilter);

        return dataPage;
    }
    
    
    @SuppressWarnings("unchecked")
    public Page<SolicitudBandeja> transformPaginaBandeja(DataPage dataPage, String usuario, Integer pantalla, String folioConulta) {

        System.out.println("transformPaginaBandeja ");
        String[] nss;
        List<String> nssInvolucrados = new ArrayList<String>();
        Page<SolicitudBandeja> page = new Page<SolicitudBandeja>();
        List<SolicitudBandeja> list = new ArrayList<SolicitudBandeja>();
        List<SolicitudBandeja> lstSolicitudBandeja = new ArrayList<SolicitudBandeja>();;
        Map<String, String> funcionarios = new HashMap<String, String>();
        List<TareaTramite> tareasTramites = new ArrayList<TareaTramite>();
        TareaTramite tramiteTarea = new TareaTramite();


        if(dataPage != null && dataPage.getData() != null){

            SolicitudBandeja solicitud = new SolicitudBandeja();
            List<Object[]> rows = (List<Object[]>) dataPage.getData();
            for (Object[] row : rows) {
                solicitud = new SolicitudBandeja();
                solicitud.setFolio(row[0].toString());
                solicitud.setFechaSolicitud(row[1].toString());
 
                solicitud.setCurp(row[2] != null ? row[2].toString() : "");
                nss=row[3].toString().split("\\s+");
                nssInvolucrados=Arrays.asList(nss);
                solicitud.setNssListaInvolucrados(nssInvolucrados);
                solicitud.setOrigen(row[4].toString());
                if (row[5].toString().equals("SIN RESPONSABLE")) {
                     solicitud.setNombreCompletoResponsable(row[5].toString());                
                } else {
                    solicitud.setResponsable(row[5].toString());
                    solicitud.setNombreCompletoResponsable(obtenerResponsable(row[5].toString(),funcionarios));
                }
                if (row[6].toString().equals("SIN AUTORIZADOR")) {
                     solicitud.setNombreCompletoAutorizo(row[6].toString());                
                } else {
                    solicitud.setAutorizo(row[6].toString());
                    solicitud.setNombreCompletoAutorizo(obtenerResponsable(row[6].toString(),funcionarios));
                }    
                solicitud.setUltimaActualizacion(row[7].toString());
                solicitud.setPantallaConsulta(pantalla);
                solicitud.setFolioConsulta(folioConulta);
                solicitud.setEstatus(EstadoNegocioEnum.obtenerDescripcionNegocio(Integer.parseInt(row[8].toString())));
                solicitud.setTipo(row[9].toString());
                lstSolicitudBandeja.add(solicitud);               

                tareasTramites = new ArrayList<TareaTramite>();
                tramiteTarea = new TareaTramite();
                tramiteTarea.setIdTramite(row[10].toString());
                                solicitud.setIdTramite(row[10].toString());

                tramiteTarea.setIdTarea(row[11].toString());

                solicitud.setIdTarea(row[11].toString());
                solicitud.setTareasTramites(tareasTramites);
    
                tareasTramites.add(tramiteTarea);
                if ( row[12]!= null && row[12].toString().contains("REASIGNADA") ) {
                        solicitud.setEstatus("REASIGNADA");

                }    
            }
        }
    
        Collections.sort(lstSolicitudBandeja); 
        page.setData(lstSolicitudBandeja);
        page.setCurrentPage(dataPage.getCurrentPage());
        page.setTotalOfRecords(dataPage.getTotalOfRecords());
        page.setPageSize(dataPage.getPageSize());

        return page;
    }

    @SuppressWarnings("unchecked")
    public Page<SolicitudBandeja> convertirPaginaBandeja(DataPage dataPage, String usuario, Integer pantalla, String folioConulta) {

        Page<SolicitudBandeja> page = new Page<SolicitudBandeja>();
        List<SolicitudBandeja> list = new ArrayList<SolicitudBandeja>();
            
        if(dataPage != null && dataPage.getData() != null){
                
                List<TareaBandeja> tareasPorFolio = null;
                 
                List<TareaBandeja> bandejas = new ArrayList<TareaBandeja>();
                bandejas.addAll((List<TareaBandeja>) dataPage.getData());
                
                List<TareaBandeja> bandejasComparator = new ArrayList<TareaBandeja>();
                bandejasComparator.addAll((List<TareaBandeja>) dataPage.getData());
                
                String folio= null;
                 
                boolean continua = Boolean.TRUE;
                                
                for (int i=0; i < bandejas.size(); i++){ 
                                        
                    System.out.println(" 210918 JASX " + bandejas.size());
                    System.out.println(" 210918 JASX " + bandejas.get(0).toString());
                                        
                    TareaBandeja bandejaCompleta = bandejas.get(i);

                        if(folio!=null){
                                if(bandejaCompleta.getInicioTramite().getFolio().equals(folio)){
                                    continua = Boolean.FALSE;
                                }else{
                                    folio = null;  
    
                                    continua = Boolean.TRUE;
                                }
                            
                         }
                        
                    if (continua){
                        tareasParaRecorrer:{
                             
                        tareasPorFolio  = new ArrayList<TareaBandeja>();
                             
                            for(TareaBandeja bandeja: bandejasComparator) { 
                        
                                System.out.println("JASX bandejaCompleta"+bandejaCompleta.getInicioTramite().getFolio());
                                System.out.println("JASX bandeja"+bandeja.getInicioTramite().getFolio());
                                
                                if (bandejaCompleta.getInicioTramite().getFolio().equals(bandeja.getInicioTramite().getFolio())) {
                                   folio = bandeja.getInicioTramite().getFolio();
                                   tareasPorFolio.add(bandeja);         
                                } else {
                                    list.add(crearSolicitudBandeja(tareasPorFolio, usuario,pantalla,folioConulta)); 
                                    bandejasComparator.removeAll(tareasPorFolio);
                                    break tareasParaRecorrer;
                                }        
                                
                                /**Si es la ultima iteracion, que genere el ultimo objeto creado**/
                                if(bandejasComparator.indexOf(bandeja) == (bandejasComparator.size() -1)){
                                   list.add(crearSolicitudBandeja(tareasPorFolio, usuario,pantalla,folioConulta));  
                                   break;
                                }
                                   
                                System.out.println("JASX List size"+list.size());
                                
                            } //fin inner for       
                        } //fin tag
                    }//fin if boolean
                    
        }
        
                
        }
        
        Collections.sort(list); 
        page.setData(list);
        page.setCurrentPage(dataPage.getCurrentPage());
        page.setTotalOfRecords(dataPage.getTotalOfRecords());
        page.setPageSize(dataPage.getPageSize());

        return page;
    }


    
    public void procesarNssYTareasTramite(SolicitudBandeja solicitud, List<TareaBandeja> tareasPorFolio){
        
        /*Recorrido para datos multiples y seteos en unico tramite asignado*/
        List<TareaTramite> tareasTramites = new ArrayList<TareaTramite>();
        List<String> nssInvolucrados = new ArrayList<String>();
        Map<String, Object> datos = new HashMap<String, Object>();
        
        String nss= null;
        
        for (int i= 0; i < tareasPorFolio.size(); i++){
            TareaBandeja tarea = tareasPorFolio.get(i);
            TareaTramite tramiteTarea = new TareaTramite();
            tramiteTarea.setIdTarea(tarea.getIdTareaUsuario() != null  ? tarea.getIdTareaUsuario().toString() : StringUtils.EMPTY);
            tramiteTarea.setIdTramite(tarea.getIdTramite().toString());
            tareasTramites.add(tramiteTarea);
            
            if(StringUtils.isNotBlank(tarea.getInicioTramite().getData())){
                datos = WorkFlowDataUtil.generarJavaDataWf(tarea.getInicioTramite().getData());
                nss = (String)datos.get("nssInvolucrados");
            }
            nssInvolucrados.add(nss);
            
        }
        
        solicitud.setNssListaInvolucrados(nssInvolucrados);
        solicitud.setTareasTramites(tareasTramites);
        getLogger().debug("NSS involucrados {}" , nssInvolucrados.toString());
        
        
        
    }
    
    public void procesarFuncionarios(SolicitudBandeja solicitud,TareaBandeja bandeja){
        Map<String, String> funcionarios = new HashMap<String, String>();
        
        String nombreResponsable=null;
        String nombreAutorizador = null;
        
        if(StringUtils.isNotBlank(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))){
            nombreResponsable = obtenerResponsable(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()), funcionarios);
            getLogger().debug("---CDA--- Responsable a buscar por curp {} ",bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
            
        }   
        
        if(StringUtils.isNotBlank(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()))){
            nombreAutorizador = obtenerResponsable(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()), funcionarios);
            getLogger().debug("---CDA--- Autorizador a buscar por curp {} ",bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.AUTORIZADOR.getDescripcion()));
            
        }   
        
        solicitud.setNombreCompletoResponsable(nombreResponsable != null && ! nombreResponsable.isEmpty() ? 
                nombreResponsable : bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
        solicitud.setNombreCompletoAutorizo(nombreAutorizador == null ||  nombreAutorizador.equals("") ? "SIN AUTORIZADOR": nombreAutorizador);       
        solicitud.setResponsable(nombreResponsable != null && ! nombreResponsable.isEmpty() ? 
                nombreResponsable : bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
        solicitud.setAutorizo(nombreAutorizador == null ||  nombreAutorizador.equals("") ? "SIN AUTORIZADOR": nombreAutorizador);
        
    }

    public SolicitudBandeja crearSolicitudBandeja(List<TareaBandeja> tareasPorFolio, String usuario, Integer pantalla, String folioConulta)  {
        TareaBandeja bandeja = tareasPorFolio.get(0);
        SolicitudBandeja solicitud = new SolicitudBandeja();
        List<String> nssInvolucrados = new ArrayList<String>();

        String folio = bandeja.getInicioTramite().getFolio();
        solicitud.setFolio(folio);
        solicitud.setCurp(consultaBandejaBusiness.obtenerCurpPorFolio(folio)); //buscarlo por tramite
        solicitud.setTipo(consultaBandejaBusiness.obtenerTipoTramite(bandeja.getInicioTramite().getIdTramite()));
        procesarNssYTareasTramite(solicitud,tareasPorFolio);
        
        if(StringUtils.isNotBlank(bandeja.getInicioTramite().getData())){
            Map<String, Object> datos = WorkFlowDataUtil.generarJavaDataWf(bandeja.getInicioTramite().getData());
            getLogger().info("origen {}",datos.get("origen"));
            solicitud.setOrigen((String)datos.get("origen"));
//            getLogger().info("tipo Regularizacion {}",datos.get("tipoRegularizacion"));
//            solicitud.setTipo(datos.get("tipoRegularizacion")!= null ?TipoRegularizacionSolicitudCDAEnum.fromId(((Integer)datos.get("tipoRegularizacion"))).getDescripcion():"SIN TIPO");
        }
        
        procesarFuncionarios(solicitud,bandeja);
        
        solicitud.setEsPropietario(usuario.equalsIgnoreCase(bandeja.getInicioTramite().getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion())));
        
        //Cambiar estatus dependiendo del que sea diferente
        solicitud.setEstatus(StringUtils.isNotBlank(bandeja.getInicioTramite().getEstatus()) ? bandeja.getInicioTramite().getEstatus().toUpperCase() : "");
        
        //obtener el estado del tramite si es procesado, SINDO mostrarlo en la bandeja ya que este o se modifica en la instancia 
        //debido a que la rutina de ODI no tiene la capacidad de modificar el xml de la instancia.
        EstadoTramite estado = getRegistroSolicitudCorreccionDatosAseguradoBusiness().consultarEstadoTramiteById(bandeja.getIdTramite().longValue());
        
        getLogger().debug("---CDA--- Estado {} ",estado.getIdEstadoTramitePersona());
        
        if(estado.getIdEstadoTramitePersona().equals(EstadoTramiteEnum.PROCESADO_SINDO.getCodigo()) 
                || estado.getIdEstadoTramitePersona().equals(EstadoTramiteEnum.ERROR_SINDO.getCodigo())){
            agregarObservaciones(solicitud,estado,solicitud.getTareasTramites());
            solicitud.setEstatus(EstadoNegocioEnum.obtenerDescripcionNegocio(estado.getIdEstadoTramitePersona()));
        }
        
        try {
            solicitud.setFechaSolicitud(getDeltaUtils().cambiarFormatoFecha(FORMATO_FECHA, bandeja.getInicioTramite().getFechaSolicitud(), FORMATO_FECHA));
            solicitud.setUltimaActualizacion(getDeltaUtils().cambiarFormatoFecha(FORMATO_FECHA, bandeja.getInicioTramite().getFechaActualizacion(), FORMATO_FECHA));
        } catch (ParseException e1) {
            
            Log.error("-- CDA: Error Fechas ", e1);
        }
        
        solicitud.setPantallaConsulta(pantalla);
        solicitud.setFolioConsulta(folioConulta);
        
        getLogger().info("Solicitud Bandeja " + solicitud.toString());
        
        return solicitud;
    }
    
    public void agregarObservaciones(SolicitudBandeja tramitesAsignados,EstadoTramite estado, List<TareaTramite> tareasTramites){
        getLogger().debug("---CDA--- Estado Tramite Asignado {} ",tramitesAsignados.getEstatus());
        getLogger().debug("---CDA--- Estado Tramite {} ",EstadoNegocioEnum.obtenerDescripcionNegocio(estado.getIdEstadoTramitePersona()));
        if(!tramitesAsignados.getEstatus().equalsIgnoreCase(EstadoNegocioEnum.obtenerDescripcionNegocio(estado.getIdEstadoTramitePersona()))){
            try {
                for (TareaTramite tareaTramite :tareasTramites){
                    getRegistroSolicitudCorreccionDatosAseguradoBusiness().agregarObservacionesSubdelegacion(Long.parseLong(tareaTramite.getIdTramite()),
                                                                                                             USUARIO_ERROR_SINDO,
                                                                                                             tareaTramite.getIdTarea());
                }
            } catch (SolicitudNoEncontradaException e) {
                getLogger().error("Error {}",e);
            } catch (TramiteNoEncontradoException e) {
                getLogger().error("Error {}",e);
            } catch (IllegalArgumentException e) {
                getLogger().error("Error {}",e);
            }
                        
        }
        
    }
    
    public String obtenerResponsable(String curp, Map<String, String> funcionarios){
        String nombreCompleto = "" ;
        if(funcionarios.containsKey(curp)){
            nombreCompleto = funcionarios.get(curp);
            getLogger().debug("---CDA--- Funcionario exite en cache {}", curp);
        }else{
            try {           
                Usuario nombre = getResponsablesDelegacionBusiness().recuperaUsuarioEsquemaSeguridadByCURP(curp);
                if(nombre != null && nombre.getFisica() != null && nombre.getFisica().getNombreCompleto() != null){
                    nombreCompleto = nombre.getFisica().getNombreCompleto();
                }
            } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
                getLogger().error("ClienteWebserviceResponsablesSubdelegacionException {} ",e);
            }
            funcionarios.put(curp, nombreCompleto);
            getLogger().debug("---CDA--- Se agrega funcionario en cache {}", curp);
        }
        return nombreCompleto;
    }


    public ConsultaBandejaRemote getConsultaBandejaBusiness() {
        return consultaBandejaBusiness;
    }
    
    public RegistroSolicitudCorreccionDatosAseguradoRemote getRegistroSolicitudCorreccionDatosAseguradoBusiness() {
        return registroSolicitudCorreccionDatosAseguradoBusiness;
    }
    
    public ResponsablesDelegacionRemote getResponsablesDelegacionBusiness() {
        return responsablesDelegacionBusiness;
    }


    public Logger getLogger() {
        return logger;
    }


    public DeltaUtils getDeltaUtils() {
        return deltaUtils;
    }
    
}
