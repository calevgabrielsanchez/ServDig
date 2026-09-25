package mx.gob.imss.cit.cda.web.reportes.helper;

import java.util.ArrayList;
import java.util.Collection;
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
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.constants.FlujoTrabajoConstants;
import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;
import mx.gob.imss.cit.cda.web.reportes.constans.ReporteConstantes;
import mx.gob.imss.cit.cda.web.reportes.utils.ReportesCDAUtils;
import mx.gob.imss.cit.cda.web.reportes.vo.RequestTramitesReportesPage;
import mx.gob.imss.cit.cda.web.reportes.vo.TramitesReportes;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ProcesosNegocioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoTramiteReporteCDA;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ReporteCDA;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Component(BeansConstants.READ_TRAMITES_REPORTES_HELPER)
public class ReadTramitesReportesHelper implements
        ReadHelper<RequestTramitesReportesPage, Page<TramitesReportes>> {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(ReadTramitesReportesHelper.class);

    @Autowired
    @Qualifier("flujoTrabajoBusiness")
    private FlujoTrabajoRemote flujoTrabajoBusiness;


    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;
    
    @Autowired
    protected HttpSession httpSession;

    @SuppressWarnings("unchecked")
    @Override
    public ReadEvent<Page<TramitesReportes>> requestEvent(
            RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {
        LOGGER.debug("---PAGINA {}", requestReadEvent.getData().getPage());
        LOGGER.debug("---REPORTES CDA--- Usuario a buscar {}",
                requestReadEvent.getUserProfile().getPerfil()
                        .equals(RolUsuarioEnum.VENTANILLA.getRol())
                                ? requestReadEvent.getUserProfile().getUsuario()
                                : (FlujoTrabajoConstants.BPM_ADMIN
                                        + requestReadEvent.getUserProfile()
                                                .getIdSubdelegacion()));
        LOGGER.debug("---REPORTES CDA--- Usuario {}",
                requestReadEvent.getUserProfile().getUsuario());
        LOGGER.debug("---REPORTES CDA--- Rol {}",
                requestReadEvent.getUserProfile().getPerfil());

        DataPage dataPage = prepararFiltros(requestReadEvent);

        LOGGER.debug("por realizar busqueda");
        dataPage = realizarBusqueda(dataPage);
        Page<TramitesReportes> page = null;
        try {
            page = convertirAmodelo2(dataPage);
            LOGGER.debug("return pagina");
            return new ReadEvent<Page<TramitesReportes>>(
                    requestReadEvent.getKey(), page);
        } catch (Exception e) {
            LOGGER.error("---CDA---", e);
            return ReadEvent.notFound(requestReadEvent.getKey());
        }

    }

    private DataPage realizarBusqueda(DataPage dataPage) {
        List<Long> idsProcesos = new ArrayList<Long>();
        idsProcesos.add(ProcesosNegocioEnum.CDA.getId());
        DataPage dataPageReturn = null;
        dataPageReturn = flujoTrabajoBusiness
                .obtenerReportePrincipalCDA(dataPage);

        return dataPageReturn;
    }

    @SuppressWarnings("unchecked")
    private Page<TramitesReportes> convertirAmodelo2(DataPage dataPage) {

        TramitesReportes tramitesAsignados = null;
        Page<TramitesReportes> page = new Page<TramitesReportes>();
        List<TramitesReportes> list = new ArrayList<TramitesReportes>();
        Usuario autorizador = null;
        Usuario responsable = null;
        for (ReporteCDA respuesta : (Collection<ReporteCDA>) dataPage
                .getData()) {

            tramitesAsignados = new TramitesReportes();
            tramitesAsignados.setIdTramite(respuesta.getIdTramite());
            tramitesAsignados.setIdTarea(respuesta.getIdSolicitud());
            tramitesAsignados.setDelegacion(respuesta.getDelegacion());
            tramitesAsignados.setSubdelegacion(respuesta.getSubdelegacion());
            try {
                autorizador = responsablesDelegacionBusiness
                        .recuperaUsuarioEsquemaSeguridadByCURP(
                                respuesta.getAutorizo());
                responsable = responsablesDelegacionBusiness
                        .recuperaUsuarioEsquemaSeguridadByCURP(
                                respuesta.getResponsable());
            } catch (ClienteWebserviceResponsablesSubdelegacionException e) {
                LOGGER.error(
                        "ClienteWebserviceResponsablesSubdelegacionException {} ",
                        e);
            }
            if(autorizador == null){
            	autorizador = new Usuario();
            	autorizador.setFisica(new Fisica());
            	autorizador.getFisica().setNombre("SIN AUTORIZADOR");
            	autorizador.getFisica().setPrimerApellido(" ");
            	autorizador.getFisica().setSegundoApellido(" ");
            }
            if(responsable == null){
            	responsable = new Usuario();
            	responsable.setFisica(new Fisica());
            	responsable.getFisica().setNombre("RESPONSABLE DESCONOCIDO");
            	responsable.getFisica().setPrimerApellido(" ");
            	responsable.getFisica().setSegundoApellido(" ");
            }
            
            tramitesAsignados.setAutorizo(autorizador.getFisica().getNombre()
                    + " " + autorizador.getFisica().getPrimerApellido() + " "
                    + autorizador.getFisica().getSegundoApellido());
            tramitesAsignados.setResponsable(responsable.getFisica().getNombre()
                    + " " + responsable.getFisica().getPrimerApellido() + " "
                    + responsable.getFisica().getSegundoApellido());
            tramitesAsignados.setOrigen(respuesta.getOrigen());
            tramitesAsignados.setFolio(respuesta.getFolio());
            tramitesAsignados.setCurp(respuesta.getCurp());
            tramitesAsignados.setNssInvolucrados(respuesta.getNssInvolucrado());
            tramitesAsignados.setTipo(respuesta.getTipoTramite());
            tramitesAsignados.setEstatus(EstadoTramiteReporteCDA.parseIdToEstadoNegocioCDA(respuesta.getEstado()).getEstadoNegocio());
            if (respuesta.getReasignado().equals("1")) {
            	tramitesAsignados.setEstatus("REASIGNADA");
            }
            tramitesAsignados.setVencida(respuesta.getVencido());
            tramitesAsignados.setFechaSolicitud(respuesta.getFechaSolicitud());
            tramitesAsignados
                    .setFechaFinalizacion(respuesta.getFechaFinalizacion());
            tramitesAsignados
                    .setUltimaActualizacion(respuesta.getFechaActualizacion());
            list.add(tramitesAsignados);
        }
        httpSession.setAttribute(ReporteConstantes.LIST_TRAMITE_REPORTE, transformToDeltaModelTramitesReporte(list));
        dataPage.setCurrentPage(dataPage.getCurrentPage());
        page.setData(list);
        page.setCurrentPage(dataPage.getCurrentPage());
        page.setTotalOfRecords(dataPage.getTotalOfRecords());
        page.setPageSize(30L);

        return page;
    }

    private DataPage prepararFiltros(
        
        RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {
        DataPage dataPage = new DataPage();
        
        dataPage.setCurrentPage(requestReadEvent.getData().getPage());
        dataPage.setPageSize(30);
        dataPage.setData(ReportesCDAUtils.crearFiltrosReporteCDA(requestReadEvent));
        
        return dataPage;
    }
    
    private List<mx.gob.imss.ctirss.delta.model.asegurado.cda.TramitesReportes> transformToDeltaModelTramitesReporte(List<TramitesReportes> lista){
        List<mx.gob.imss.ctirss.delta.model.asegurado.cda.TramitesReportes> listaExcel = new ArrayList<mx.gob.imss.ctirss.delta.model.asegurado.cda.TramitesReportes>();
        for(TramitesReportes obj : lista){
            mx.gob.imss.ctirss.delta.model.asegurado.cda.TramitesReportes nuevo = new mx.gob.imss.ctirss.delta.model.asegurado.cda.TramitesReportes();
            nuevo.setAutorizo(obj.getAutorizo());
            nuevo.setCurp(obj.getCurp());
            nuevo.setDelegacion(obj.getDelegacion());
            nuevo.setEstatus(obj.getEstatus());
            nuevo.setFechaFinalizacion(obj.getFechaFinalizacion());
            nuevo.setFechaSolicitud(obj.getFechaSolicitud());
            nuevo.setFolio(obj.getFolio());
            nuevo.setIdTarea(obj.getIdTarea());
            nuevo.setIdTramite(obj.getIdTramite());
            nuevo.setNssInvolucrados(obj.getNssInvolucrados());
            nuevo.setOrigen(obj.getOrigen());
            nuevo.setResponsable(obj.getResponsable());
            nuevo.setSubdelegacion(obj.getSubdelegacion());
            nuevo.setTipo(obj.getTipo());
            nuevo.setUltimaActualizacion(obj.getUltimaActualizacion());
            nuevo.setVencida(obj.getVencida());
            
            listaExcel.add(nuevo);
        }
        
        return listaExcel;
    }
}
