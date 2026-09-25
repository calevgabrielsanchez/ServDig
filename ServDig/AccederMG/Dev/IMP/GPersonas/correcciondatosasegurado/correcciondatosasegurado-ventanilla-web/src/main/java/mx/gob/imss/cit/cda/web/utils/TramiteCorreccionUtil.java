/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author aldair.vidal
 */
@Component
public class TramiteCorreccionUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TramiteCorreccionUtil.class);
    
    @Autowired
    private RegistroCorreccionCurpUtil registroCorreccionCurpUtil;
    
    @Autowired
    @Qualifier("solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;
    
    private final String TIPOSOLICITANTE_BENEFICIARIO = "Beneficiario";
    private final String TIPOSOLICITANTE_REPRESENTANTE = "Representante";
    private final String TIPOSOLICITANTE_ASEGURADO = "Asegurado";

    
    public Solicitud actualizarXmlDocumentosAseg(Solicitud solicitud,DatosHistoriaLaboralVO datosHistoriaLaboral) throws TramiteNoEncontradoException, IllegalArgumentException{
        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        if(solicitud.getTramites() != null){
            tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
        }
        tramite.setDocumentosProbatorios(registroCorreccionCurpUtil.cargarDatosTramiteDocumentAsegurado(tramite,datosHistoriaLaboral));
        tramite.setPersonas(getInteresadoTramite(datosHistoriaLaboral));
        tramite.setChecked(datosHistoriaLaboral.isDefuncion());
        solicitudBusiness.actualizarXmlTramite(tramite);
        return solicitud;		
    }
    
    public Solicitud crearTramiteNss(Solicitud solicitud,DatosHistoriaLaboralVO datosHistoriaLaboral) throws SolicitudNoValidaException{
            if(solicitud.getTramites() != null){
                List<NSSVO> listNsss = verificaNuevosNss(solicitud,datosHistoriaLaboral.getNSSList());
                for(int i=0;i<listNsss.size();i++){
                    NSSVO nss=listNsss.get(i);
                    if(i!=0){
                        solicitud = crearTramiteNuevo(solicitud,nss);
                        LOGGER.debug("---CDA--- tramites en solicitud {}",solicitud.getTramites().size());
                    }else{
                        TramiteCorreccionCurp tramiteInicial= (TramiteCorreccionCurp)solicitud.getTramites().get(0);
                        if(tramiteInicial.getListaNSS() == null){
                            nss.getDocumentoProbatorioList().addAll(datosHistoriaLaboral.getDocumentoProbatorioList());
                            tramiteInicial = registroCorreccionCurpUtil.cargarDatosTramiteNuevo(tramiteInicial,nss);
                            try {
                                solicitudBusiness.actualizarXmlTramite(tramiteInicial);
                            } catch (TramiteNoEncontradoException e) {
//                                LOGGER.error("---CDA--- No se encontro el Tramite {}",e);
                            } catch (IllegalArgumentException e) {
//                                LOGGER.error("---CDA--- Ocurrio un error al actualizar el tramite {}",e);
                            }
                        }
                    }
                }  
            }

        return solicitud;		
    }
    
    public DatosHistoriaLaboralVO getPrecargaNssList(Solicitud solicitud, DatosHistoriaLaboralVO datosHistoriaLaboralVO){
        List<NSSVO> listNss = new ArrayList<NSSVO>();
        Long idDocumento=TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId();
        for(Tramite t:solicitud.getTramites()){
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) t;
            if(tramite.getListaNSS()!=null && !tramite.getListaNSS().isEmpty()){
                NSSVO nss = new NSSVO();
                nss.setNSS(tramite.getListaNSS().get(0));
                if(tramite.getDocumentosProbatorios()!=null && !tramite.getDocumentosProbatorios().isEmpty()){
                    List<DocumentoProbatorio> documentoProbatorioList =new ArrayList<DocumentoProbatorio>();
//                    LOGGER.debug("---CDA--- iddocumentos "+idDocumento);
                    for(mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentosprobatorio:tramite.getDocumentosProbatorios()){
//                        LOGGER.debug("---CDA--- documentos probatorios por tramite "+documentosprobatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
                        if(documentosprobatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio()==idDocumento.intValue()){
//                            LOGGER.debug("---CDA--- documentos probatorios por tramite "+tramite.getTramiteId()+" documento "+documentosprobatorio.getDocumentoPorTipo());
                            DocumentoProbatorio documento = new DocumentoProbatorio();
                            documento.setIdDocBoveda(documentosprobatorio.getBovedaDocId());
                            documento.setNombre(documentosprobatorio.getNomNombreDocumento());
                            documento.setCveIdDocumento(documentosprobatorio.getDocumentoPorTipo().getDocumento().getCveIdDocumento());
                            documento.setDesDocumento(documentosprobatorio.getDocumentoPorTipo().getDocumento().getDesDocumento());
                            documento.setTipoDocumento(documentosprobatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
                            documento.setIdDocumentoPorTipo(documentosprobatorio.getDocumentoPorTipo().getIdDocumentoPorTipo());
                            documentoProbatorioList.add(documento);
                        }
                    }
                    nss.setDocumentoProbatorioList(documentoProbatorioList);
                }else{
//                    LOGGER.error("---CDA--- No tiene documentos probatorios");
                }
                listNss.add(nss);
            }
        }
        if(listNss.size()>0){
            datosHistoriaLaboralVO.setNSSList(listNss);
        }
        return datosHistoriaLaboralVO;
    }
    
    public Solicitud cancelarNssTramiteXml(Solicitud solicitud,String nss){
//        LOGGER.debug("---CDA--- Inicia cancelarNssTramiteXml");
        for(int i =0; i<solicitud.getTramites().size(); i++){  
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(i);
            if(tramite.getListaNSS()!=null){
                String nssTramite=tramite.getListaNSS().get(0);
                if(nss.equals(nssTramite) ){
                    if(i!=0){
//                        LOGGER.debug("---CDA--- Se actualiza el tramite a cancelado");
                        tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CANCELADO.getCodigo());
                        try {
                            solicitudBusiness.cancelarTramite(tramite);
    //                        solicitudBusiness.actualizarTramites(solicitud);
                        } catch (TramiteNoEncontradoException e) {
//                            LOGGER.error("---CDA--- No se encontro el Tramite {}",e);
                        } catch (IllegalArgumentException e) {
//                            LOGGER.error("---CDA--- Ocurrio un error al actualizar el tramite {}",e);
                        }
                    }else{
                        editaPrimerTramite(solicitud, nss);
                    }
                }
            }else{
//                LOGGER.error("---CDA--- No se encontro el Tramite");
            }
        }
        return solicitud;		
    }
    
    public TramiteCorreccionCurp cancelarTramiteXml(TramiteCorreccionCurp tramite){
//        LOGGER.debug("---CDA--- Se actualiza el tramite a cancelado");
        tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CANCELADO.getCodigo());
        try {
            solicitudBusiness.cancelarTramite(tramite);
        } catch (TramiteNoEncontradoException e) {
//            LOGGER.error("---CDA--- No se encontro el Tramite {}",e);
        } catch (IllegalArgumentException e) {
//            LOGGER.error("---CDA--- Ocurrio un error al actualizar el tramite {}",e);
        }
        return tramite;		
    }
    
    public Solicitud editaPrimerTramite(Solicitud solicitud,String nss){
        TramiteCorreccionCurp tramiteCurp = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
//        LOGGER.debug("---CDA--- Se elimina el nss del tramite ");
        tramiteCurp.setListaNSS(null);
        Iterator<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> iterador=tramiteCurp.getDocumentosProbatorios().iterator();
//        LOGGER.debug("---CDA--- Se remueven los documentos probatorios que no son del tipo asegurado");
        while(iterador.hasNext()){
            if(iterador.next().getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio()==TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()){
                iterador.remove();
            }
        }
        try {
            if(solicitud.getTramites().size()>1){
//               LOGGER.debug("---CDA--- Se toma el nss y documnetos del 2 tramite y se pasa al primero");
               TramiteCorreccionCurp tramite2 = (TramiteCorreccionCurp) solicitud.getTramites().get(1);
               tramiteCurp.setListaNSS(new ArrayList<String>());
               tramiteCurp.getListaNSS().add(tramite2.getListaNSS().get(0));
               tramiteCurp.getDocumentosProbatorios().addAll(tramite2.getDocumentosProbatorios());
//               LOGGER.debug("---CDA--- Se cancela el segundo tramite");
               cancelarTramiteXml(tramite2);
            }
            solicitudBusiness.actualizarXmlTramite(tramiteCurp);
        } catch (TramiteNoEncontradoException ex) {
            java.util.logging.Logger.getLogger(TramiteCorreccionUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger(TramiteCorreccionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return solicitud;		
    }
    
    
    
    private Solicitud crearTramiteNuevo(Solicitud solicitud,NSSVO nss){
        try {
            TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
            tramite.setListaNSS(new ArrayList<String>());
            tramite.getListaNSS().add(nss.getNSS());
            solicitud = solicitudBusiness
                    .asociarTramiteSolicitudPorEnum(solicitud
                            , tramite, mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO
                            , EstadoTramiteEnum.INICIADO);
            for(int i=0 ;i<solicitud.getTramites().size(); i++){
                TramiteCorreccionCurp tramiteAsociado=(TramiteCorreccionCurp) solicitud.getTramites().get(i);
                if(tramiteAsociado.getTramiteId() == null){
//                    LOGGER.debug("---CDA--- Tramite {}",tramiteAsociado);
                    tramiteAsociado = registroCorreccionCurpUtil.cargarDatosTramiteNuevo(tramiteAsociado,nss);
                    solicitud.getTramites().set(i, solicitudBusiness.crearTramiteASolicitud(tramiteAsociado, solicitud.getSolicitudId()));
                }
            }
        } catch (SolicitudNoValidaException ex) {
//            LOGGER.error("---CDA--- Ocurrio un error general {}",ex);
        }
        return solicitud;
    }
    
    /**
     * Valida que nss son nuevos y no se encuentran en un tramite existente
     * @param solicitud
     * @param listNss 
     */
    private List<NSSVO> verificaNuevosNss(Solicitud solicitud, List<NSSVO> listNss){
        List<NSSVO> nuevosNss= new ArrayList<NSSVO>();
        for(NSSVO nss:listNss){
            Boolean bandera = Boolean.FALSE;
          
           for(Tramite tramites : solicitud.getTramites()){
                TramiteCorreccionCurp tramiteAsociado=(TramiteCorreccionCurp) tramites;
                if(tramiteAsociado.getListaNSS()!=null && tramiteAsociado.getListaNSS().isEmpty()){
                    if(nss.getNSS().equals(tramiteAsociado.getListaNSS().get(0)) 
                            && tramites.getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.CANCELADO.getCodigo())){
//                        LOGGER.debug("---CDA--- verificaNuevosNss  el NSS {0} ya se encuentra en el tramite {1}",nss,tramites.getTramiteId());
                        bandera = Boolean.TRUE;
                        break;
                    }
                }
            }
           if(!bandera){
             nuevosNss.add(nss);
           }
        }
        return nuevosNss;
    }
    
    private List<Fisica> getInteresadoTramite(DatosHistoriaLaboralVO documenHistoriaLaboralVO){
        List<Fisica> listFisica=new ArrayList();
        Fisica persona = new Fisica();
        persona.setChecked(documenHistoriaLaboralVO.isDefuncion());
        persona.setTipoPersona(new TipoPersona());
        persona.getTipoPersona().setDescripcion(documenHistoriaLaboralVO.getTipoSolicitante());
        listFisica.add(persona);
        return listFisica;
    }
    
    public Solicitud actualizarXmlDocumentosBeneficiarioRepresentante(Solicitud solicitud,DatosHistoriaLaboralVO datosBeneficiario,Fisica persona) throws TramiteNoEncontradoException, IllegalArgumentException{
        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        if(solicitud.getTramites() != null){
            tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
        }
        persona.setTipoPersona(new TipoPersona());
        if(datosBeneficiario.getTipoSolicitante().equals(TIPOSOLICITANTE_BENEFICIARIO)){
            persona.getTipoPersona().setDescripcion(datosBeneficiario.getTipoBeneficiario());
            persona.setChecked(datosBeneficiario.isDefuncion());
        }else if(datosBeneficiario.getTipoSolicitante().equals(TIPOSOLICITANTE_REPRESENTANTE)){
            persona.getTipoPersona().setDescripcion(datosBeneficiario.getTipoSolicitante());
        }
        persona.setDocumentosProbatorios(registroCorreccionCurpUtil.cargarDatosTramiteDocumentBeneficiario(tramite,datosBeneficiario));
        persona.setDocumentosProbatorios(registroCorreccionCurpUtil.cargarDatosTramiteDocumentBeneficiario(tramite,datosBeneficiario));
        tramite.getPersonas().set(0, persona);
        solicitudBusiness.actualizarXmlTramite(tramite);
        return solicitud;		
    }
    
    
}
