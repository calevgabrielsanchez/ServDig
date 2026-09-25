package mx.gob.imss.cit.cda.web.informacionadicional.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.service.interfaces.InformacionAdicionalRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoSolicitanteEnum;
import mx.gob.imss.cit.cda.web.constants.EnvioCorreoCDAConstants;
import mx.gob.imss.cit.cda.web.informacionadicional.constants.InformacionAdicionalConstants;
import mx.gob.imss.cit.cda.web.informacionadicional.vo.InformacionAdicionalSolicitudVO;
import mx.gob.imss.cit.cda.web.utils.EnvioCorreoUtils;
import mx.gob.imss.cit.cda.web.utils.RegistroCorreccionCurpUtil;
import mx.gob.imss.cit.cda.web.utils.TransformerRegistroUtils;
import mx.gob.imss.cit.cda.web.validator.DocumentosProbatoriosValidator;
import mx.gob.imss.cit.cda.web.validator.NSSValidator;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

@Component
public class InformacionAdicionalHelper {

    private final Logger log = LoggerFactory
            .getLogger(InformacionAdicionalHelper.class);

    @Autowired
    private DocumentosProbatoriosValidator documentosProbatoriosValidator;

    @Autowired
    private NSSValidator nSSValidator;

    @Autowired
    private EnvioCorreoUtils envioCorreoUtil;

    @Autowired
    @Qualifier("solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;

    @Autowired
    @Qualifier("informacionAdicionalBusiness")
    private InformacionAdicionalRemote informacionAdicionalBusiness;

    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

    @Autowired
    @Qualifier("envioCorreoElectronicoBusiness")
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;
    
    @Autowired
    private RegistroCorreccionCurpUtil registroCorreccionCurpUtil;

    /**
     * Obtiene los documentos probatorios de asegurado, nss y beneficiario(si es
     * que tiene) para agregarlos a el objeto de InformacionAdicionalSolicitudVO
     * construido con base en la solicitud
     * 
     * @param solicitud
     *            Solicitud que se contiene datos de la solicitud usada para e
     *            tramite
     * @return InformacionAdicionalSolicitudVO Objeto que se usa en vista para
     *         mostrar los objetos de la soicitud
     */
    public InformacionAdicionalSolicitudVO getSolicitudByInfoAdicional(
            Solicitud solicitud) {
        InformacionAdicionalSolicitudVO informacionAdicionalSolicitud = new InformacionAdicionalSolicitudVO();
        informacionAdicionalSolicitud
                .setAsociadosNSS(getListNSSAsociadas(solicitud));
        getTipoSolicitante(informacionAdicionalSolicitud, solicitud);
        return informacionAdicionalSolicitud;
    }

    /**
     * Valida que informacion adicional registrada cumpla con las reglas de
     * validacion
     * 
     * @param informacionAdicionalSolicitudVO
     *            Objeto a validar que vene de la vista
     * @param errors
     *            Errores presentados en las validaciones
     * @param labelReject
     *            Objeto al cual esta relacionado el mensaje de error en la
     *            vista
     * @return Cuando es exitoso un un errors vacio
     */
    public Errors validarInformacionAdicional(
            InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO,
            Errors errors, boolean isActivoAseg, boolean isActivoBenf,
            boolean isActivoNSS) {

        log.warn("---CDA--- validarInformacionAdicional helper");
        if (informacionAdicionalSolicitudVO != null) {

            errors = validaAsegurado(
                    informacionAdicionalSolicitudVO,
                    errors,
                    InformacionAdicionalConstants.FIELD_ERRORS_DOCUMENTO_PROBATORIO_ASEGURADO_LIST,
                    isActivoAseg);

            errors = validaBeneficiarios(
                    informacionAdicionalSolicitudVO,
                    errors,
                    InformacionAdicionalConstants.FIELD_ERRORS_DOCUMENTO_PROBATORIO_BENEFICIARIO_LIST,
                    isActivoBenf);

            errors = validaNSS(informacionAdicionalSolicitudVO.getListNSS(),
                    errors,
                    InformacionAdicionalConstants.FIELD_ERRORS_LIST_NSS,
                    isActivoNSS);
        }
        return errors;
    }
    
    
    private String getGuardarDocumentosAdicionalesStep1(Solicitud solicitud, TramiteCorreccionCurp tramite, TramiteCorreccionCurp tramiteRegistro, InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO) throws SolicitudNoEncontradaException, TramiteNoEncontradoException{
      String refCurp;
      if (solicitud.getTramites() != null) {
             tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
             Solicitud solRegistro=solicitudBusiness.consultarPorIdTramite(solicitud.getTramites().get(0).getTramiteId());
             tramiteRegistro=(TramiteCorreccionCurp) solRegistro.getTramites().get(0);
            
         }
         refCurp = tramite.getPersonaRENAPO().getCurp();
     
//        log.debug("primer tramite principal tiene documentos probatorios ",
//                tramite.getDocumentosProbatorios().size());
        
        /**
         * Se valida la lista de asegurado
         */
        if (informacionAdicionalSolicitudVO
                .getDocumentoProbatorioAseguradoList() != null) {
        	if( tramite.getAsegurado()==null){
        		Fisica aseg =new Fisica();
        		tramite.setAsegurado(aseg);
        	}
        	if(tramite.getAsegurado().getDocumentosProbatorios()==null){
        		List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> doc=new ArrayList<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio>(); 
        		tramite.setDocumentosProbatorios(doc);
        	}
        	
              tramite.getAsegurado().getDocumentosProbatorios()
                      .addAll(construyeDocumentosProbatorioAdicionales(informacionAdicionalSolicitudVO
                              .getDocumentoProbatorioAseguradoList()));
            solicitudBusiness.actualizarXmlTramite(tramite);
            List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> documentosLista = registroCorreccionCurpUtil
                    .ensamblaDocumentos(informacionAdicionalSolicitudVO
                            .getDocumentoProbatorioAseguradoList());
            try {
//              informacionAdicionalBusiness.guardarDocumentosTramite(documentosLista, tramite.getTramiteId());
              informacionAdicionalBusiness.almacenarDatosAsegurado(tramite,refCurp,tramiteRegistro);
          }catch (DocumentoProbatorioException e) {
              log.debug("Error al agregar los documentos asegurado {}",e);
          }
        }
        log.debug("guardado de nss");
        return refCurp;
    }
    
    private void getGuardarDocumentosAdicionalesStep2(TramiteCorreccionCurp tramite, InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO, TramiteCorreccionCurp tramiteRegistro, String refCurp) throws TramiteNoEncontradoException{
      Beneficiario beneficiario = null;
      Fisica representante = null;
      log.debug("getDocumentoProbatorioBeneficiarioList no es vacio");
      if (tramite.getBeneficiario() != null ){
          beneficiario = tramite.getBeneficiario();
      }
      if(tramite.getRepresentante()!=null) {
          representante= tramite.getRepresentante();
      }

      if(beneficiario!=null){
          log.debug("Los documnetos no son nullos");
          log.debug("lista de doc de beneficiario {}",informacionAdicionalSolicitudVO.getDocumentoProbatorioBeneficiarioList());
          if(beneficiario.getDocumentosProbatorios()==null){
            List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> docBenf=new ArrayList<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio>(); 
            beneficiario.setDocumentosProbatorios(docBenf);
          }
          beneficiario.getDocumentosProbatorios().addAll((construyeDocumentosProbatorioAdicionales(informacionAdicionalSolicitudVO.getDocumentoProbatorioBeneficiarioList())));
          tramite.setBeneficiario(beneficiario);//.set(0, beneficiario);
      }else{
        if(representante.getDocumentosProbatorios()!=null){
          log.debug("Los documnetos no son nullos");
          log.debug("lista de doc de beneficiario {}",informacionAdicionalSolicitudVO.getDocumentoProbatorioBeneficiarioList());
          if(representante.getDocumentosProbatorios()==null){
            List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> docBenf=new ArrayList<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio>(); 
            representante.setDocumentosProbatorios(docBenf);
          }
          representante.getDocumentosProbatorios().addAll((construyeDocumentosProbatorioAdicionales(informacionAdicionalSolicitudVO.getDocumentoProbatorioBeneficiarioList())));
          tramite.setRepresentante(representante);
        }
      }
      solicitudBusiness.actualizarXmlTramite(tramite);
      try {
        informacionAdicionalBusiness.almacenarDatosBeneficiario(tramite,refCurp,tramiteRegistro);
      } catch (DocumentoProbatorioException e1) {     
        log.error("Error al agregar los documentos beneficiario {}",e1);
      }
      List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> documentosTransformados = registroCorreccionCurpUtil.ensamblaDocumentos(informacionAdicionalSolicitudVO.getDocumentoProbatorioBeneficiarioList());
      try {
        informacionAdicionalBusiness.guardarDocumentosTramite(documentosTransformados, tramite.getTramiteId());
      }catch (DocumentoProbatorioException e) {
        log.error("Error al agregar los documentos beneficiario {}",e);
      }catch (RegistrarDocumentoProbatorioException e) {
        log.error("Error al agregar los documentos beneficiario {}",e);
      }            
    }
    
    private void getGuardarDocumentosAdicionalesStep3(InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO,
      TramiteCorreccionCurp tramite, String refCurp,TramiteCorreccionCurp tramiteRegistro,Solicitud solicitud ) throws TramiteNoEncontradoException{
    
    List<NSSVO> listNsss = informacionAdicionalSolicitudVO.getListNSS();
        if (listNsss != null && !listNsss.isEmpty()) {
            Iterator<NSSVO> iterator = listNsss.iterator();
            
            
            while (iterator.hasNext()) {
                NSSVO nss = iterator.next();
                /**
                 * Se valida la lista de de documentos de nss
                 */ 
                if(nss.getDocumentoProbatorioList()!=null && !nss.getDocumentoProbatorioList().isEmpty()){
                    agregarDocumentosNSSTramite(tramite, nss, refCurp,tramiteRegistro,solicitud.getTramites().get(0).getTramiteId());
                }
                
            }
        }
    }

    /**
     * Actualiza la informacion de la solicitud y sus tramites
     * 
     * @param solicitud
     * @param informacionAdicionalSolicitudVO
     * @throws TramiteNoEncontradoException
     * @throws IllegalArgumentException
     * @throws SolicitudNoEncontradaException 
     */
    public Solicitud getGuardarDocumentosAdicionales(Solicitud solicitud,
            InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO,String usuario)
            throws TramiteNoEncontradoException, IllegalArgumentException, SolicitudNoEncontradaException {
    	String refCurp ;
        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        TramiteCorreccionCurp tramiteRegistro=new TramiteCorreccionCurp();
       
      refCurp = getGuardarDocumentosAdicionalesStep1(solicitud, tramite, tramiteRegistro, informacionAdicionalSolicitudVO );   
        
        /**
         * Se valida la lista el beneficiario
         */
        if(informacionAdicionalSolicitudVO.getDocumentoProbatorioBeneficiarioList()!= null){
        	 getGuardarDocumentosAdicionalesStep2(tramite, informacionAdicionalSolicitudVO, tramiteRegistro, refCurp);
        }
              
        /**
         * Se valida la lista de nss
         */ 
        getGuardarDocumentosAdicionalesStep3(informacionAdicionalSolicitudVO,
              tramite, refCurp, tramiteRegistro, solicitud);
        
        /**
         * Actualizar bitacora de estados y envio de correo
         */
//  envioCorreoUtil.contenidoCorreoResponsableInformacionAdiconal(solicitud, responsable, observaciones);
        
//      List<Tramite> tramiteAdicional=new ArrayList<Tramite>();
//      tramiteAdicional.add(tramite);
//       	solicitud.setTramites(tramiteAdicional);
      
       	solicitud = informacionAdicionalBusiness.crearSolicitudInformacionAdicional(
   				solicitud, usuario, "RESPONSABLE");
       	solicitudBusiness.actualizarXmlTramite(solicitud.getTramites().get(0));
       	solicitudBusiness.actualizarTramites(solicitud);
       	return solicitud;
//      	solicitudBusiness.actualizarTramites(solicitud);
//			informacionAdicionalBusiness.actualizarCorreccionSolicitud(solicitud);

        
    }
    

    /**
     * Metodo que transforma una lista de DocumentosProbatorios de tipo VO a
     * MODEL con la bandera de informacion adicional
     * 
     * @param documentos
     * @return
     */
    public List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> construyeDocumentosProbatorioAdicionales(
            List<mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio> documentos) {
        List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> listDocumentos = new ArrayList<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio>();
        if (documentos != null && !documentos.isEmpty()) {
            for (DocumentoProbatorio d : documentos) {
                mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio datoDocumentoProbatorio = TransformerRegistroUtils
                        .voToModelDocumentoProbatorio(d);
                datoDocumentoProbatorio.setInformacionAdicional(Boolean.TRUE);
                listDocumentos.add(datoDocumentoProbatorio);
            }
        }else{
            log.debug("Lista de documentos vacia ");
        }
        return listDocumentos;
    }

    /**
     * Agrega los documentos adicionales a un NSS ya asociado a la solicitud
     * 
     * @param tramite2
     *            Solicitud en sesion que se modificara
     * @param nss
     *            Objeto que tiene los nuevos documentos probatorios
     * @param tramiteRegistro 
     * @param string 
     * @return La solicitud ya actualizada
     * @throws IllegalArgumentException
     * @throws TramiteNoEncontradoException
     */
    public void agregarDocumentosNSSTramite(TramiteCorreccionCurp tramitecurp, NSSVO nss,String refCurp, TramiteCorreccionCurp tramiteRegistro, Long idTramite)
            throws TramiteNoEncontradoException, IllegalArgumentException {
        String nssTramite = nss.getNSS();

        List<String> listNss = new ArrayList<String>();
        listNss.add(nssTramite);
        
            
//            log.debug("Entra al nss : {} ",tramitecurp.getListaNSS().get(0));
        for(CorreccionNSS correccionNSS:tramitecurp.getListaNssCorreccion()){
            if (correccionNSS.getNss().equals(nss.getNSS())) {
                log.debug("Compara el nss con el del tramite : {} ",tramitecurp.getListaNssCorreccion().get(0));
               correccionNSS.getDocumentosProbatorios().addAll(
                        construyeDocumentosProbatorioAdicionales(nss
                                .getDocumentoProbatorioList()));
              
                solicitudBusiness.actualizarXmlTramite(tramitecurp);
                log.debug("Se actualizo el xml ");
                List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> documentosLista = registroCorreccionCurpUtil.ensamblaDocumentos(nss.getDocumentoProbatorioList());
                try {
                    log.debug("Guardamos en el tramite id :  {}",tramitecurp.getTramiteId());
                    
                    
                    try {
						informacionAdicionalBusiness.almacenarDatosDetalleNss(tramitecurp,correccionNSS,refCurp, tramiteRegistro,idTramite);
					} catch (RegistrarDocumentoProbatorioException e) {
						// TODO Auto-generated catch block
						 log.error("Error al agregar los documentos beneficiario {}",e);
					}
//                    informacionAdicionalBusiness.guardarDocumentosTramite(documentosLista, tramitecurp.getTramiteId());
                } catch (DocumentoProbatorioException e) {
                    log.debug("Error al agregar los documentos nss {}",nssTramite);
                    log.debug("SolicitudNoValidaException {}",e);
                }
                
            }
    }
        
    }

    /**
     * Validacion la documentacion probatoria del asegurado
     * 
     * @param informacionAdicionalSolicitudVO
     * @param errors
     * @param labelReject
     * @return
     */
    private Errors validaAsegurado(
            InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO,
            Errors errors, String labelReject, boolean isActiva) {
        /* NI02 del caso de uso AgregarInformacionAdicional */
        if (isActiva) {
            if (informacionAdicionalSolicitudVO
                    .getDocumentoProbatorioAseguradoList() == null
                    || informacionAdicionalSolicitudVO
                            .getDocumentoProbatorioAseguradoList().isEmpty()) {
                log.warn("---CDA--- Errores de captura documentoProbatorioList error");
                errors.rejectValue(labelReject,
                        "field.documentoProbatorio.listaVacia");
            }
        }
        if (informacionAdicionalSolicitudVO
                .getDocumentoProbatorioAseguradoList() != null
                && !informacionAdicionalSolicitudVO
                        .getDocumentoProbatorioAseguradoList().isEmpty()) {
            documentosProbatoriosValidator.validate(
                    informacionAdicionalSolicitudVO
                            .getDocumentoProbatorioAseguradoList(), errors,
                    informacionAdicionalSolicitudVO.isDefuncion(), labelReject);
        }
        return errors;
    }

    /**
     * Validacion de documento probatorio de benefeficiario
     * 
     * @param informacionAdicionalSolicitudVO
     * @param errors
     * @param labelReject
     * @return
     */
    private Errors validaBeneficiarios(
            InformacionAdicionalSolicitudVO informacionAdicionalSolicitudVO,
            Errors errors, String labelReject, boolean isActiva) {
        /* Valida si el panel de beneficiario esta activo */
        if (isActiva) {
            /* Valida documentos esten vacios o nulos */
            if (informacionAdicionalSolicitudVO
                    .getDocumentoProbatorioBeneficiarioList() == null
                    || informacionAdicionalSolicitudVO
                            .getDocumentoProbatorioBeneficiarioList().isEmpty()) {
                log.warn("---CDA--- Errores de captura documentoProbatorioList error");
                errors.rejectValue(labelReject,
                        "field.documentoProbatorio.listaVacia");
            }
        }
        /* Valida documento los tipos de documentos en la lista */
        if (informacionAdicionalSolicitudVO
                .getDocumentoProbatorioBeneficiarioList() != null
                && !informacionAdicionalSolicitudVO
                        .getDocumentoProbatorioBeneficiarioList().isEmpty()) {
            documentosProbatoriosValidator.validateBeneficiario(
                    informacionAdicionalSolicitudVO
                            .getDocumentoProbatorioBeneficiarioList(), errors,
                    informacionAdicionalSolicitudVO.getTipoSolicitante(),
                    informacionAdicionalSolicitudVO.getTipoBeneficiario(),
                    labelReject);
        }
        return errors;
    }

    /**
     * Validacin los documentos probatorios de nss
     * 
     * @param nssvo
     * @param listaDocumentoProbNSS
     * @param errors
     * @param labelReject
     * @return
     */
    public Errors validarNssDocumentos(NSSVO nssvo,
            List<DocumentoProbatorio> listaDocumentoProbNSS, Errors errors,
            String labelReject) {
        log.debug("nss de validacion : ", nssvo);
        log.debug("documentos de validacion : ", listaDocumentoProbNSS);
        if (nssvo != null && nssvo.getNSS() != null) {
            nSSValidator.validateAsociado(nssvo, errors);
            if (!errors.hasErrors() && !errors.hasFieldErrors("NSS")) {
                errors = new BindException(
                        new InformacionAdicionalSolicitudVO(), "model");
                if (listaDocumentoProbNSS != null
                        && !listaDocumentoProbNSS.isEmpty()) {
                    documentosProbatoriosValidator.validateDocumentosNSS(
                            listaDocumentoProbNSS, errors, labelReject);
                } else {
                    log.debug("listaDocumentosNssTemp esta vacia");
                    errors.rejectValue("listaDocumentosNssTemp",
                            "field.documentoProbatorio.listaVacia");
                }
            }
        } else {
            errors.rejectValue("NSS", "field.required");
        }

        return errors;
    }

    private Errors validaNSS(List<NSSVO> listaNssvos, Errors errors,
            String labelReject, boolean isActiva) {
        if ((listaNssvos == null || listaNssvos.isEmpty()) && isActiva) {
            log.warn("---CDA--- Errores de captura");
            errors.rejectValue(labelReject, "field.NSS.listaVacia");
        }
        return errors;
    }

    private List<String> getListNSSAsociadas(Solicitud solicitud) {
        List<String> listNssAsociado = new ArrayList<String>();
        for (Tramite t : solicitud.getTramites()) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) t;
            if (tramite.getListaNssCorreccion() != null
                    && !tramite.getListaNssCorreccion().isEmpty()) {
            	for(CorreccionNSS correccionNSS:tramite.getListaNssCorreccion()){
            		if(!listNssAsociado.contains(correccionNSS.getNss())){
            			listNssAsociado.add(correccionNSS.getNss());
            		}
            	}
            }
        }
        return !listNssAsociado.isEmpty() ? listNssAsociado : null;
    }

    private void getTipoSolicitante(
            InformacionAdicionalSolicitudVO infoAdicional, Solicitud solicitud) {
    	 TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
    	 tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
        log.debug("Obtiene el tipo de solicitante");
        if (tramite.getBeneficiario() != null) {
            Beneficiario beneficiario = tramite.getBeneficiario();
            if (beneficiario.getTipoBeneficiario() != null) {
                if (beneficiario.getTipoBeneficiario()
                        .equals(TipoSolicitanteEnum.CONCUBINO.getId())
                        || beneficiario
                                .getTipoBeneficiario()                                
                                .equals(TipoSolicitanteEnum.DESCENDIENTE
                                        .getId())
                        || beneficiario
                                .getTipoBeneficiario()
                                .equals(TipoSolicitanteEnum.CONYUGE
                                        .getId())
                        || beneficiario
                                .getTipoBeneficiario()
                                .equals(TipoSolicitanteEnum.PADRES
                                        .getId())) {

                    infoAdicional
                            .setTipoSolicitante(TipoSolicitanteEnum.BENEFICIARIO
                                    .getDescripcion());
                    infoAdicional.setTipoBeneficiario(TipoSolicitanteEnum.getTipoSolicitudEnumById(beneficiario.getTipoBeneficiario()).getDescripcion());
                } else {
                    infoAdicional.setTipoSolicitante(!beneficiario.getTipoBeneficiario()
                            .toString().equals("") ? beneficiario
                            .getTipoBeneficiario().toString() : "");
                }
                infoAdicional
                        .setDefuncion((beneficiario.getTipoPersona() !=null && beneficiario.getTipoPersona().getChecked() != null) ? beneficiario
                                .getTipoPersona().getChecked() : false);
                infoAdicional.setCurp(beneficiario.getCurp());
            }
        } else if(tramite.getRepresentante() != null){

            Fisica fisica = tramite.getRepresentante();
                    infoAdicional
                            .setTipoSolicitante(TipoSolicitanteEnum.REPRESENTANTE_LEGAL
                                    .getDescripcion());
                    infoAdicional.setTipoSolicitante(TipoSolicitanteEnum.REPRESENTANTE_LEGAL
                            .getDescripcion());
                 
//                infoAdicional
//                        .setDefuncion(fisica.getTipoPersona().getChecked() != null ? fisica
//                                .getTipoPersona().getChecked() : false);
                infoAdicional.setCurp(fisica.getCurp());
            
        
        }else {
            log.debug("El tipo de solicitante es asegurado");
            infoAdicional
                    .setTipoSolicitante(TipoSolicitanteEnum.ASEGURADO_PENSIONADO
                            .getDescripcion());
        }
    }

    public void cambiaEstadoSolicitud(Solicitud solicitud, String observaciones) {
        try {
            informacionAdicionalBusiness
                    .actualizarCorreccionSolicitud(solicitud);
            log.debug("Cambia el estado de la solicitud a espera de informacion ");
        } catch (SolicitudNoValidaException e) {
            log.error("Error al cambiar el estado de la solicitud {}",e);

        } catch (TramiteNoEncontradoException e) {
            log.error("Error al cambiar el estado de la solicitud {}",e);

        }
    }

    private void enviarCorreoCorreccionDatosPorCurp(Solicitud solicitud,
            byte[] adjuntos, String curpResponsable, String observaciones) {
        CorreoElectronicoDTO correoElectronicoDTO = new CorreoElectronicoDTO();

        mx.gob.imss.ctirss.delta.model.Usuario responsable;
        try {
            responsable = responsablesDelegacionBusiness
                    .recuperaUsuarioEsquemaSeguridadByCURP(curpResponsable);
            if(responsable.getFisica() != null && responsable.getFisica().getCorreoElectronico() != null){
                correoElectronicoDTO.setCorreoPara(new String[1]);
                correoElectronicoDTO.getCorreoPara()[0] = responsable.getFisica()
                        .getCorreoElectronico().getCorreo();
                correoElectronicoDTO
                        .setAsunto(StringEscapeUtils
                                .unescapeHtml(EnvioCorreoCDAConstants.ASUNTO_CORRECCION_DATOS));
                correoElectronicoDTO.setCuerpoCorreo(envioCorreoUtil
                        .contenidoCorreoResponsableInformacionAdiconal(solicitud,
                                responsable,observaciones));
                log.debug("---CDA--- Contenido Correo Derechohabiente: "
                        + (correoElectronicoDTO.getCuerpoCorreo()));
                if (adjuntos != null) {
                    Map<String, byte[]> adjuntosMap = new HashMap<String, byte[]>();
                    adjuntosMap.put("Documento.pdf", adjuntos);
                    correoElectronicoDTO.setAdjuntos(adjuntosMap);
                }
    
                try {
                    envioCorreoElectronicoBusinessRemote.enviarCorreo(
                            correoElectronicoDTO,
                            EnvioCorreoCDAConstants.MAIL_PROPERTIES_ADRESS);
                } catch (Exception e) {
                    log.error("---CDA--- Error al enviar correo ", e);
                }
            }else{
                log.error(
                        "---CDA--- Error al obtener la informacion del responsable no se envia el correo");
            }
        } catch (ClienteWebserviceResponsablesSubdelegacionException e1) {
            log.error(
                    "---CDA--- Error al obtener la informacion del responsable ",
                    e1);
        }
    }
    
 

}
