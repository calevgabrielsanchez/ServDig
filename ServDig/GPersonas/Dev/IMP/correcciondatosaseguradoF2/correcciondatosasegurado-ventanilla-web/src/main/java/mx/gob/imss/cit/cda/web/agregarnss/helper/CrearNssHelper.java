package mx.gob.imss.cit.cda.web.agregarnss.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.AgregarNssRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.agregarnss.exception.AgregarNssException;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.NSSAdicional;
import mx.gob.imss.cit.cda.web.consulta.utils.ReadSolicitudUtils;
import mx.gob.imss.cit.cda.web.utils.RegistroCorreccionCurpUtil;
import mx.gob.imss.cit.cda.web.utils.TransformerRegistroUtils;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(BeansConstants.UPDATE_NSS_HELPER)
public class CrearNssHelper
        implements
        UpdateHelper<NSSAdicional, mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud> {

    private final Logger log = LoggerFactory.getLogger(CrearNssHelper.class);
    private static final String DOCTO = "   docto";
    private static final String BOVEDA_DOC_ID = "      BovedaDocId";
    private static final String NOM_NOMBRE_DOCUMENTO = "      NomNombreDocumento";

    @Autowired
    @Qualifier("agregarNssBusiness")
    private AgregarNssRemote agregarNssBusiness;

    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

    @Autowired
    @Qualifier("solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;

    @Autowired
    private RegistroCorreccionCurpUtil registroCorreccionCurpUtil;

    @Autowired
    private ReadSolicitudUtils readSolicitudUtils;

    @Override
    public UpdatedEvent<mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud> requestEvent(
            UpdateEvent<NSSAdicional> requestUpdateEvent) {
        log.debug("JAAS" + requestUpdateEvent.getData().toString());
        log.debug("JAAS" + requestUpdateEvent.getData().getFolio());
        log.debug("JAAS" + requestUpdateEvent.getData().getDocumentosProbatorios().size());

        mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud solicitudModel = new mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud();
        Solicitud solicitud = new Solicitud();
        try {

            //Obtenemos la solicitud 
            solicitud = obtenerSolicitud(requestUpdateEvent.getData().getFolio());

            //Agregamos el nuevo nss a la solicitud
            TramiteCorreccionCurp tramitecurp = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
            CorreccionNSS correccionNSS = this.convertirDatosVistaAModelo(requestUpdateEvent);
            
            //Se agrega el NSS al bean del tramite
            log.debug("tramitecurp.getListaNssCorreccion() ntes:" + tramitecurp.getListaNssCorreccion().size());
            tramitecurp = this.agregarNssTramite(tramitecurp, correccionNSS);
            log.debug("tramitecurp.getListaNssCorreccion() despues:" + tramitecurp.getListaNssCorreccion().size());

            //Enviamos  guardar, tanto datos en BD, como el xml
            this.agregarNssBusiness.agregarNss(tramitecurp, correccionNSS);

            solicitud = obtenerSolicitud(requestUpdateEvent.getData().getFolio());
            TramiteCorreccionCurp tramiteCorreccionCurp = (TramiteCorreccionCurp) solicitud.getTramites().get(0);

            solicitudModel.setGridNssSolicitud(readSolicitudUtils.getGridNssSolicitud(tramiteCorreccionCurp.getListaNssCorreccion(), true));
            solicitudModel.setGridNssVentanilla(readSolicitudUtils.getGridNssSolicitud(tramiteCorreccionCurp.getListaNssCorreccion(), false));

        } catch (TramiteNoEncontradoException ex) {
            log.error("---------------------Error---------------------{}", ex);
        } catch (RegistrarDocumentoProbatorioException ex) {
            log.error("---------------------Error---------------------{}", ex);
        } catch (DocumentoProbatorioException ex) {
            log.error("---------------------Error---------------------{}", ex);
        } catch (AgregarNssException e2) {
            log.error("---------------------Error---------------------{}", e2);
        }
        return new UpdatedEvent<mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud>(
                requestUpdateEvent.getKey(), solicitudModel);
    }
    
    //Agrega el NSS al listado de nss que tiqene el tramite, si encuentra ue el NSS ya estaba, lo sustiruye
    private TramiteCorreccionCurp agregarNssTramite(TramiteCorreccionCurp tramiteCorreccionCurp,
            CorreccionNSS correccionNSSXAgregar){
        List<CorreccionNSS> listaNueva = new ArrayList<CorreccionNSS>();
        boolean encontrado = false;
        
        if(tramiteCorreccionCurp != null){
            log.debug("agregarNssTramite se agrega:  " + correccionNSSXAgregar.getNss());
            for (CorreccionNSS correccionNSS : tramiteCorreccionCurp.getListaNssCorreccion()) {
                log.debug("  ---   nss existente en la lista " + correccionNSSXAgregar.getNss());
                if(correccionNSS.getNss().equals(correccionNSSXAgregar.getNss())){
                    encontrado = true;
                    //Se copia el origen que ya tenía el NSS
                    correccionNSSXAgregar.setOrigen(correccionNSS.getOrigen());
                    listaNueva.add(correccionNSSXAgregar);
                }else{
                    listaNueva.add(correccionNSS);
                }                
            }
        }
        
        if(!encontrado){
            listaNueva.add(correccionNSSXAgregar);
        }
        tramiteCorreccionCurp.setListaNssCorreccion(listaNueva);
        return tramiteCorreccionCurp;
    }

    private CorreccionNSS convertirDatosVistaAModelo(UpdateEvent<NSSAdicional> requestUpdateEvent) {
        CorreccionNSS correccionNSS = new CorreccionNSS();
        correccionNSS.setNss(requestUpdateEvent.getData().getNss());
        correccionNSS.setOrigen(OrigenCapturaCDAEnum.RESPONSABLE.getClave());
        correccionNSS.setObservaciones(requestUpdateEvent.getData().getObservacion());
        List<DocumentoProbatorio> documentosProbatorios
                = TransformerRegistroUtils.voToModelDocumentoProbatorio(requestUpdateEvent.getData().getDocumentosProbatorios());
        correccionNSS.setDocumentosProbatorios(documentosProbatorios);
        return correccionNSS;
    }
    
    private void actualizarTramiteStep1(List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> listaNuevosDoctosFront, List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> listdocumentos,TramiteCorreccionCurp tramitecurp){
      
      if (listdocumentos != null && !listdocumentos.isEmpty()) {
          for (mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio doctoFront : listaNuevosDoctosFront) {              
              if (tramitecurp.getDocumentosProbatorios() != null && !tramitecurp.getDocumentosProbatorios().isEmpty()) {
                  for (DocumentoProbatorio doctoBD : tramitecurp.getDocumentosProbatorios() ) {                      
                      if (doctoBD != null && doctoFront != null
                              && doctoBD.getBovedaDocId().equals(doctoFront.getIdDocBoveda())) {
                          listaNuevosDoctosFront.add(doctoFront);
                      }
                  }
              }
          }
      }
      log.error("Se guardaran del front");
      for (mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio next : listaNuevosDoctosFront ) {          
          log.debug(DOCTO + next.getCveIdDocumento());
          log.debug(BOVEDA_DOC_ID + next.getIdDocBoveda());
          log.debug(NOM_NOMBRE_DOCUMENTO + next.getNombre());
      }
    }
    
    private void actualizarTramiteStep2(List<DocumentoProbatorio> listaDoctosEliminados, List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> eliminaLista,TramiteCorreccionCurp tramitecurp){
      if (eliminaLista != null && !eliminaLista.isEmpty()) {
                for (Iterator iterator = eliminaLista.iterator(); iterator.hasNext();) {
                    mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio doctoFrontEliminar = (mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio) iterator.next();

                    if (tramitecurp.getDocumentosProbatorios() != null && !tramitecurp.getDocumentosProbatorios().isEmpty()) {
                        for (Iterator iteratorDoctosBD = tramitecurp.getDocumentosProbatorios().iterator(); iteratorDoctosBD.hasNext();) {
                            DocumentoProbatorio doctoBD = (DocumentoProbatorio) iteratorDoctosBD.next();
                            if (doctoBD != null && doctoFrontEliminar != null
                                    && doctoBD.getBovedaDocId().equals(doctoFrontEliminar.getIdDocBoveda())) {
                                iteratorDoctosBD.remove();
                                listaDoctosEliminados.add(doctoBD);
                            }
                        }
                    }
                }
            }
    }

    private void actualizarTramite(TramiteCorreccionCurp tramitecurp, List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> listdocumentos, List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> eliminaLista) throws AgregarNssException {
        try {
            log.error("Revisando los documentos que vienen del front");
            for (Iterator iterator = listdocumentos.iterator(); iterator.hasNext();) {
                mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio next = (mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio) iterator.next();
                log.debug(DOCTO + next.getCveIdDocumento());
                log.debug(BOVEDA_DOC_ID + next.getIdDocBoveda());
                log.debug(NOM_NOMBRE_DOCUMENTO + next.getNombre());
            }

            log.error("Revisando los documentos que ya se tienen en BD");
            for (Iterator iterator = tramitecurp.getDocumentosProbatorios().iterator(); iterator.hasNext();) {
                DocumentoProbatorio next = (DocumentoProbatorio) iterator.next();
                log.debug(DOCTO + next.getIdDocumentoProbatorio());
                log.debug(BOVEDA_DOC_ID + next.getBovedaDocId());
                log.debug(NOM_NOMBRE_DOCUMENTO + next.getNomNombreDocumento());
            }

            //Se revisan los documentos que vienen del front para dejar solo aquellos que se deben insertar
            List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> listaNuevosDoctosFront = new ArrayList<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio>();
            actualizarTramiteStep1(listaNuevosDoctosFront,listdocumentos,tramitecurp);

            List<DocumentoProbatorio> listaNuevosDoctos = registroCorreccionCurpUtil.ensamblaDocumentos(listaNuevosDoctosFront);

            //Se revisan los documentos que vienen del front para eliminación 
            List<DocumentoProbatorio> listaDoctosEliminados = new ArrayList<DocumentoProbatorio>();
            actualizarTramiteStep2(listaDoctosEliminados,eliminaLista,tramitecurp);

            log.error("Se eliminaran del front");
            for (Iterator iterator = listaDoctosEliminados.iterator(); iterator.hasNext();) {
                mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio next = (mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio) iterator.next();
                log.debug(DOCTO + next.getCveIdDocumento());
                log.debug(BOVEDA_DOC_ID + next.getIdDocBoveda());
                log.debug(NOM_NOMBRE_DOCUMENTO + next.getNombre());
            }

            log.error("Revisando los documentos que finales que ya tiene el tramite en BD");
            for (Iterator iterator = tramitecurp.getDocumentosProbatorios().iterator(); iterator.hasNext();) {
                DocumentoProbatorio next = (DocumentoProbatorio) iterator.next();
                log.debug(DOCTO + next.getIdDocumentoProbatorio());
                log.debug(BOVEDA_DOC_ID + next.getBovedaDocId());
                log.debug(NOM_NOMBRE_DOCUMENTO + next.getNomNombreDocumento());
            }

            agregarNssBusiness.actualizarDocumentosNss(tramitecurp, listaNuevosDoctos, listaDoctosEliminados);
        } catch (DocumentoProbatorioException e) {
            log.error("Error al agregar los documentos ", e);
        }

    }

    private Usuario obtenerResponsableSolicitud(String curp)
            throws AgregarNssException {
        try {
            return responsablesDelegacionBusiness
                    .recuperaUsuarioEsquemaSeguridadByCURP(curp);
        } catch (ClienteWebserviceResponsablesSubdelegacionException e1) {
            log.error("Error en obtenerResponsableSolicitud", e1);
            System.out.println("Error en obtenerResponsableSolicitud " + e1);
            throw new AgregarNssException(e1);
        }
    }

    private Solicitud obtenerSolicitud(String folio) throws AgregarNssException {
        try {
            return solicitudBusiness
                    .consultarPorFolioSolicitud(folio);
        } catch (SolicitudNoEncontradaException e) {
            log.error("CDA Error al obtener la solicitud", e);
            System.out.println("CDA Error al procesar los documentos " + e.getMessage());
            throw new AgregarNssException(e);
        }
    }

}
