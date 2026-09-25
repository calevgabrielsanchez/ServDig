package mx.gob.imss.cit.cda.web.reportes.helper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.cit.cda.web.reportes.constans.ReporteConstantes;
import mx.gob.imss.cit.cda.web.reportes.utils.ReportesCDAUtils;
import mx.gob.imss.cit.cda.web.reportes.vo.DetalleReporteCDA;
import mx.gob.imss.cit.cda.web.reportes.vo.EstadisticasOrigenReporteCDAVO;
import mx.gob.imss.cit.cda.web.reportes.vo.RequestTramitesReportesPage;
import mx.gob.imss.cit.cda.web.reportes.vo.VariablesReportes;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TechnicalPersistenceException;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ConteoReporteCDA;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadisticasReporteCDA;
import mx.gob.imss.ctirss.delta.service.interfaces.ISelectService;

@Component(BeansConstants.VARIABLE_ELEGIDA_HELPER)
public class ObtenerEstadisticaHelper implements
        ReadHelper<RequestTramitesReportesPage, Page<DetalleReporteCDA>> {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(ObtenerEstadisticaHelper.class);

    @Autowired
    private ISelectService componentComboService;

    @Autowired
    @Qualifier("flujoTrabajoBusiness")
    private FlujoTrabajoRemote flujoTrabajoBusiness;
    
    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;
    
    @Autowired
    protected HttpSession httpSession;

    
    private void requestEventStep1(String delegacion, String subdelegacion){
      try {
            List<SelectBean> delegaciones = componentComboService.getActiveOptions("mx.gob.imss.ctirss.delta.persistence.DicDelegacion");
            for(SelectBean del : delegaciones){
                if(del.getId().equals(delegacion)){
                    delegacion = del.getDescripcion();
                    httpSession.setAttribute(ReporteConstantes.DELEGACION_REPORTES, delegacion);
                }
            List<SelectBean> subdelegaciones = componentComboService.getActiveOptions("mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion");
            for(SelectBean subdel : subdelegaciones){
                if(subdel.getId().equals(subdelegacion)){
                    subdelegacion = subdel.getDescripcion();
                    httpSession.setAttribute(ReporteConstantes.SUBDELEGACION_REPORTES, subdelegacion);
                }
                
            }
            }
        } catch (TechnicalPersistenceException e1) {
            LOGGER.error("TechnicalPersistenceException {} ", e1); 
        }
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public ReadEvent<Page<DetalleReporteCDA>> requestEvent(
            RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {
        Page<DetalleReporteCDA> page = new Page<DetalleReporteCDA>();
        String delegacion = null;
        String subdelegacion = null;
        
        UserProfile usuarioActivo = (UserProfile) httpSession.getAttribute(SessionConstants.USER_PROFILE);        
        delegacion = usuarioActivo.getIdDelegacion() != null ? usuarioActivo.getIdDelegacion().toString() : " ";        
        subdelegacion = usuarioActivo.getIdSubdelegacion() != null ? usuarioActivo.getIdSubdelegacion().toString() : " ";        
        String variableSeleccionada = requestReadEvent.getData().getFilter().getVariable();
 
        requestEventStep1(delegacion , subdelegacion);
        
        DataPage dataPage = null;
        if (requestReadEvent.getData().getFilter().getVariable() != null
                && !requestReadEvent.getData().getFilter().getVariable()
                        .equals("-1")) {
            dataPage = prepararFiltros(requestReadEvent);
            try {
                
                List<ConteoReporteCDA> variableReportes = (List<ConteoReporteCDA>) flujoTrabajoBusiness.obtenerConteoReportePrincipalCDA(dataPage).getData();
                
                List<EstadisticasReporteCDA> estadisticasOrigen = (List<EstadisticasReporteCDA>) flujoTrabajoBusiness.obtenerConteoOrigenesReportePrincipalCDA(prepararFiltros(requestReadEvent)).getData();
                
                if(variableSeleccionada.equals("autorizador")||variableSeleccionada.equals("responsable")){
                    for(ConteoReporteCDA responsable : variableReportes){
                        Usuario usuario = null;
                        try{
                             usuario = responsablesDelegacionBusiness.recuperaUsuarioEsquemaSeguridadByCURP(responsable.getDescripcion());
                        responsable.setDescripcion(usuario.getFisica().getNombre()+ " " + usuario.getFisica().getPrimerApellido() + " "+ usuario.getFisica().getSegundoApellido());
                        }catch (ClienteWebserviceResponsablesSubdelegacionException e) {
                            LOGGER.error("ClienteWebserviceResponsablesSubdelegacionException {} ", e);      
                        }catch (NullPointerException e) {
                            LOGGER.error("NullPointerException en WS {} ", e);      
                        }
                        
                        if(usuario == null){
                            if(variableSeleccionada.equals("autorizador")){
                                responsable.setDescripcion("SIN AUTORIZADOR");
                            }
                            if(variableSeleccionada.equals("responsable")){
                                responsable.setDescripcion("RESPONSABLE DESCONOCIDO");
                            }
                        }
                    }
                }
                
                page = obtenerPageFinal(gridDetalleEstadisticasTransform(variableReportes), gridDetalleEstadisticasOrigenTransform(estadisticasOrigen), variableSeleccionada, delegacion, subdelegacion);
                
                
            } catch (Exception e) {
                LOGGER.error("---CDA---", e);
                return ReadEvent.notFound(requestReadEvent.getKey());
            }
        } else {
            LOGGER.debug("No se selecciono ningun valor");
        }

        page.setCurrentPage(dataPage.getCurrentPage());
        page.setTotalOfRecords(dataPage.getTotalOfRecords());
        page.setPageSize(dataPage.getPageSize());
        return new ReadEvent<Page<DetalleReporteCDA>>(requestReadEvent.getKey(),
                page);
    }

    private DataPage prepararFiltros(RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {
        
        DataPage dataPage = new DataPage();
        
        dataPage.setCurrentPage(requestReadEvent.getData().getPage());
        dataPage.setPageSize(requestReadEvent.getData().getPageSize());
        dataPage.setData(ReportesCDAUtils.crearFiltrosReporteCDA(requestReadEvent));
        
        return dataPage;
    }

    private List<VariablesReportes> gridDetalleEstadisticasTransform(List<ConteoReporteCDA> lista) {
        
        List<VariablesReportes> list = new ArrayList<VariablesReportes>();
        for (ConteoReporteCDA obj : lista) {
            VariablesReportes registroTransform = new VariablesReportes();
            registroTransform.setDescripcion(obj.getDescripcion());
            registroTransform.setCantidad(obj.getTotal());
            list.add(registroTransform);
        }
        
        httpSession.setAttribute(ReporteConstantes.LIST_VARIABLES_REPORTE,list);
        
        
        return list;
    }
    

    private List<EstadisticasOrigenReporteCDAVO> gridDetalleEstadisticasOrigenTransform(List<EstadisticasReporteCDA> lista) {
        
        List<EstadisticasOrigenReporteCDAVO> list = new ArrayList<EstadisticasOrigenReporteCDAVO>();
        
        for (EstadisticasReporteCDA obj : lista) {
            EstadisticasOrigenReporteCDAVO nuevo = new EstadisticasOrigenReporteCDAVO();
            nuevo.setNumeroInternet(obj.getNumeroInternet());
            nuevo.setNumeroVentanilla(obj.getNumeroVentanilla());
            nuevo.setDescripcion(obj.getDescripcion());
            nuevo.setTotal(obj.getTotal());
            list.add(nuevo);
        }
        
        httpSession.setAttribute(ReporteConstantes.LIST_ORIGENES_REPORTES,list);  
        return list;
    }
    
    private  Page<DetalleReporteCDA> obtenerPageFinal (List<VariablesReportes> variablesReportes, List<EstadisticasOrigenReporteCDAVO> estadisticasReporteCDAVO, String variableSeleccionada, String delegacion, String subdelegacion){
        Page<DetalleReporteCDA> page = new Page<DetalleReporteCDA>();
        List<DetalleReporteCDA> datosFront = new ArrayList<DetalleReporteCDA>();
        
        Page<VariablesReportes> pageListaVariables = new Page<VariablesReportes>();
        Page<EstadisticasOrigenReporteCDAVO> pageEstadisticas = new Page<EstadisticasOrigenReporteCDAVO>();
        
        List<Page<VariablesReportes>> variablesListPage = new ArrayList<Page<VariablesReportes>>();
        List<Page<EstadisticasOrigenReporteCDAVO>> estadisticasListPage = new ArrayList<Page<EstadisticasOrigenReporteCDAVO>>();
        
        pageListaVariables.setData(variablesReportes);
        pageListaVariables.setCurrentPage(1);
        pageListaVariables.setPageSize(10);
        pageListaVariables.setTotalOfRecords(variablesReportes.size());
        
        pageEstadisticas.setData(estadisticasReporteCDAVO);
        pageEstadisticas.setCurrentPage(1);
        pageEstadisticas.setPageSize(10);
        pageEstadisticas.setTotalOfRecords(estadisticasReporteCDAVO.size());
        
        variablesListPage.add(pageListaVariables);
        estadisticasListPage.add(pageEstadisticas);
        
        
        DetalleReporteCDA obj = new DetalleReporteCDA();
        
        Integer sumaOrigenes = Integer.parseInt(estadisticasReporteCDAVO.get(0).getNumeroInternet()) + Integer.parseInt(estadisticasReporteCDAVO.get(0).getNumeroVentanilla());

        obj.setVariablesReportes(variablesListPage);
        obj.setEstadisticasReporteCDAVO(estadisticasListPage);
        obj.setDelegacionReporte(delegacion.toUpperCase());
        obj.setSubdelegacionReporte(subdelegacion.toUpperCase());
        obj.setVariableSeleccionadaReporte(variableSeleccionada.toUpperCase());
        obj.setEstadisticaReporte(sumaOrigenes.toString());
        
        datosFront.add(obj);
        page.setData(datosFront);
        page.setCurrentPage(1);
        page.setTotalOfRecords(2);
        page.setPageSize(10);
        return page;
    }
}