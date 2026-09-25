package mx.gob.imss.cit.cda.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.common.business.OperacionesSolicitudBusiness;
import mx.gob.imss.cit.cda.service.correccion.util.CoreccionDatosLocal;
import mx.gob.imss.cit.cda.service.entity.CorreccionDatosAseguradoLocal;
import mx.gob.imss.cit.cda.service.entity.DetalleNssCdaLocal;
import mx.gob.imss.cit.cda.service.entity.DocumentoCdaLocal;
import mx.gob.imss.cit.cda.service.interfaces.InformacionAdicionalRemote;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoDoctoOrigSolicitanteCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "informacionAdicionalBusiness", mappedName = "informacionAdicionalBusiness")
public class InformacionAdicionalBusiness extends OperacionesSolicitudBusiness implements InformacionAdicionalRemote{
    
    
    @EJB(name = "documentoProbatorioServiceBusiness", mappedName = "documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
    
    //nuevo
    @EJB
    private CorreccionDatosAseguradoLocal correccionDatosAseguradoEntity;
    
    @EJB
    private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;
    
    @EJB
    private DocumentoCdaLocal documentoCdaLocal;
    
    @EJB
    private DetalleNssCdaLocal detalleNssCdaLocal;
    
    @EJB
	private CoreccionDatosLocal correccionDatosUtil;
    
    @EJB
	private SolicitudBusinessRemote solicitudBusiness;
    
    //termina


    private final Logger log = LoggerFactory.getLogger(InformacionAdicionalBusiness.class);
    
    @Override
    public Solicitud actualizarCorreccionSolicitud(Solicitud solicitud)
            throws TramiteNoEncontradoException {
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
        estadoSolicitud.setDescripcion(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getDescripcion());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        crearTramitesSolicitarInformacion(solicitud.getTramites());
        log.info("Inicia Guardado de Solicitar informacion");

        try {
            getSolicitudBusiness().actualizarEstados(solicitud);
        } catch (SolicitudNoEncontradaException e) {
            log.debug("Error al cambiar el estado");
        }

        for (Tramite tramite : solicitud.getTramites()) {
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramite;
            getSolicitudBusiness().actualizarXmlTramite(tramiteCda);
            }
        return solicitud;
    }
    
    @Override
    public void guardarDocumentosTramite(List<DocumentoProbatorio> documentos, Long idTramite)
            throws DocumentoProbatorioException, RegistrarDocumentoProbatorioException{
        // no requerimos que los doctos se asocien a la persona por lo tanto
        // se le envia null al metodo
        documentoProbatorioServiceBusinessRemote.procesaDocumentosGD(
        idTramite, null, documentos);
    }
    
    /**
     * Metodo para asignar el estado en espera de informacion a los tramites
     */
    private List<Tramite> crearTramitesSolicitarInformacion (List<Tramite> tramites){
        List<Tramite> lstTramite = new ArrayList<Tramite>();
        
        for (Tramite tramitecda : tramites) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA.getCodigo()));
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA.getCodigo());
            tramite.setEstadoTramite(estadoTramite);
            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
//            tramite.getObservacionesSubdelegacion().add(crearObservacionTramiteSolicitarInfo (requestUpdateEvent, estadoTramite));
            lstTramite.add(tramite);
        }
        
        return lstTramite;
        
    }
    
    private void registrarDocumentosAsegurado(TramiteCorreccionCurp tramite,TramiteCorreccionCurp tramiteRegistro, 
            Long idTramiteCorreccionDatosAseg)
                throws DocumentoProbatorioException {
            if (tramite.getAsegurado()!= null
                    && tramite.getAsegurado().getDocumentosProbatorios() != null) {
                
              
                log.debug(
                            "---CDA--- doctos a registrar del asegurado {} ",
                            tramite.getAsegurado().getDocumentosProbatorios().size());
                List<DocumentoProbatorio> docPersist=new ArrayList<DocumentoProbatorio>();
                for (int i=0;tramite.getAsegurado().getDocumentosProbatorios().size()>i;i++) {
                	 int cont=0;
                	for (int j=0;tramiteRegistro.getAsegurado().getDocumentosProbatorios().size()>j;j++) {
 	            	   if(tramite.getAsegurado().getDocumentosProbatorios().get(i).getBovedaDocId().equals(tramite.getAsegurado().getDocumentosProbatorios().get(j).getBovedaDocId())){
 	            		   cont=cont+1;
 	            	   }
             	   }
                	if(cont == 0){
                		docPersist.add(tramite.getAsegurado().getDocumentosProbatorios().get(i));
	            	   }
                }
            	   
            	   for (mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentoProbatorio : docPersist) {
	                    documentoProbatorio = documentoProbatorioServiceBusinessRemote.registraDocumento(documentoProbatorio);
	                    documentoCdaLocal.guardarDocumentacionTramite(tramite.getTramiteId(), documentoProbatorio);
		                    log.info("---CDA--- ingresa el detalle de docto de asegurado CDA ");
		                    documentoCdaLocal.guardarDoctoAseg(tramite.getTramiteId(), documentoProbatorio,
		                            idTramiteCorreccionDatosAseg, 
		                            TipoDoctoOrigSolicitanteCDAEnum.ASEGURADO.getClave(),true);
	                    
	                }
                }
            
        }
    
    
    public void almacenarDatosAsegurado(TramiteCorreccionCurp tramite,String refCurp,TramiteCorreccionCurp tramiteRegistro) throws DocumentoProbatorioException{
    	  TramiteCorreccionCurp correccionDatosAseg = correccionDatosAseguradoEntity
                  .almacenarTramiteCDA(tramite.getTramiteId(), refCurp);
   	   
   	   registrarDocumentosAsegurado(tramite,tramiteRegistro, 
                  correccionDatosAseg.getIdTramiteCorreccionDatosAseg());
    }
    
    public void almacenarDatosBeneficiario(TramiteCorreccionCurp tramite,String refCurp,TramiteCorreccionCurp tramiteRegistro) throws DocumentoProbatorioException{
  	  TramiteCorreccionCurp correccionDatosAseg = correccionDatosAseguradoEntity
                .almacenarTramiteCDA(tramite.getTramiteId(), refCurp);
  	  
  	 registrarDocumentosBeneficiario(tramite,tramiteRegistro, correccionDatosAseg.getIdTramiteCorreccionDatosAseg());
 	   
  }
    
    private void registrarDocumentosBeneficiarioStep01(TramiteCorreccionCurp tramite,TramiteCorreccionCurp tramiteRegistro,
            Long idTramiteCorreccionDatosAseg) throws DocumentoProbatorioException{
      if (tramite.getRepresentante()!= null
                 && tramite.getRepresentante().getDocumentosProbatorios() != null) {
             
           
             log.debug(
                         "---CDA--- doctos a registrar del asegurado {} ",
                         tramite.getRepresentante().getDocumentosProbatorios().size());
             
             List<DocumentoProbatorio> docPersist=new ArrayList<DocumentoProbatorio>();
             for (int i=0;tramite.getRepresentante().getDocumentosProbatorios().size()>i;i++) {
             	 int cont=0;
             	for (int j=0;tramiteRegistro.getRepresentante().getDocumentosProbatorios().size()>j;j++) {
 	            	   if(tramite.getRepresentante().getDocumentosProbatorios().get(i).getBovedaDocId().equals(tramite.getRepresentante().getDocumentosProbatorios().get(j).getBovedaDocId())){
 	            		   cont=cont+1;
 	            	   }
          	   }
             	if(cont == 0){
             		docPersist.add(tramite.getRepresentante().getDocumentosProbatorios().get(i));
             	   }
             }
         	   
         	   registrarDocumentosBeneficiarioStep02(docPersist,
                     idTramiteCorreccionDatosAseg, tramite);
         }
    }
    
    private void registrarDocumentosBeneficiarioStep02(List<DocumentoProbatorio> docPersist, Long idTramiteCorreccionDatosAseg,
            TramiteCorreccionCurp tramite ) throws DocumentoProbatorioException{
    
      for (mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentoProbatorio
              : docPersist) {
        documentoProbatorio = documentoProbatorioServiceBusinessRemote.
                registraDocumento(documentoProbatorio);
        if (documentoProbatorio.getInformacionAdicional() == true) {
        		documentoCdaLocal.guardarDocumentacionTramite(tramite.getTramiteId(), documentoProbatorio);
				log.info("---CDA--- ingresa el detalle de docto de beneficiario CDA ");
				documentoCdaLocal.guardarDoctoAseg(tramite.getTramiteId(),
						documentoProbatorio, idTramiteCorreccionDatosAseg,
						TipoDoctoOrigSolicitanteCDAEnum.REPRESENTANTE_LEGAL
								.getClave(), true);
        }
      }
    
    }
    
    private void registrarDocumentosBeneficiarioStep03(TramiteCorreccionCurp tramite, TramiteCorreccionCurp tramiteRegistro,
            Long idTramiteCorreccionDatosAseg, List<DocumentoProbatorio> docPersist) throws DocumentoProbatorioException{
      for (mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentoProbatorio : docPersist) {
            	if(!tramiteRegistro.getBeneficiario().getDocumentosProbatorios().contains(documentoProbatorio)){  
                documentoProbatorio = documentoProbatorioServiceBusinessRemote.registraDocumento(documentoProbatorio);
                documentoCdaLocal.guardarDocumentacionTramite(tramite.getTramiteId(), documentoProbatorio);
                log.info("---CDA--- ingresa el detalle de docto de beneficiario CDA ");
                documentoCdaLocal.guardarDoctoAseg(tramite.getTramiteId(), documentoProbatorio,
                        idTramiteCorreccionDatosAseg, 
                        TipoDoctoOrigSolicitanteCDAEnum.BENEFICIARIO.getClave(),true);
                }
            }
    }
    
    private void registrarDocumentosBeneficiario(TramiteCorreccionCurp tramite,TramiteCorreccionCurp tramiteRegistro,
            Long idTramiteCorreccionDatosAseg)
            throws DocumentoProbatorioException {
         if (tramite.getBeneficiario()!= null
                && tramite.getBeneficiario().getDocumentosProbatorios() != null) {
            
          
            log.debug(
                        "---CDA--- doctos a registrar del asegurado {} ",
                        tramite.getBeneficiario().getDocumentosProbatorios().size());
            List<DocumentoProbatorio> docPersist=new ArrayList<DocumentoProbatorio>();
            for (int i=0;tramite.getBeneficiario().getDocumentosProbatorios().size()>i;i++) {
            	 int cont=0;
            	for (int j=0;tramiteRegistro.getBeneficiario().getDocumentosProbatorios().size()>j;j++) {
	            	   if(tramite.getBeneficiario().getDocumentosProbatorios().get(i).getBovedaDocId().equals(tramite.getBeneficiario().getDocumentosProbatorios().get(j).getBovedaDocId())){
	            		   cont=cont+1;
	            	   }
         	   }
            	if(cont == 0){
            		docPersist.add(tramite.getBeneficiario().getDocumentosProbatorios().get(i));
            	   }
            }
        	   
        	   registrarDocumentosBeneficiarioStep03(tramite, tramiteRegistro,
                     idTramiteCorreccionDatosAseg, docPersist);
        }
         
         else {
         
          registrarDocumentosBeneficiarioStep01(tramite, tramiteRegistro,
                  idTramiteCorreccionDatosAseg);
         
         }
         
    }
    
    public void almacenarDatosDetalleNss(TramiteCorreccionCurp tramite,CorreccionNSS correccionNSS, String refCurp, TramiteCorreccionCurp tramiteRegistro, Long idTramite) throws DocumentoProbatorioException, TramiteNoEncontradoException, RegistrarDocumentoProbatorioException{
    	
//    	TramiteCorreccionCurp correccionDatosAseg = correccionDatosAseguradoEntity
//                 .almacenarTramiteCDA(tramite.getTramiteId(), refCurp);
//    	  
//               DitDetalleNss ditDetalleNss =  correccionDatosAseguradoEntity.bloquearNSS(
//                  correccionNSS.getNss(),
//                  tramite.getIdTramiteCorreccionDatosAseg(),  OrigenCapturaCDAEnum.SOLICITUD.getClave());
    	
    	
    	
              log.info("---CDA--- Se bloque el nss {}",correccionNSS.getNss());
              for (DocumentoProbatorio DocumentoProbatorioAgregado:correccionNSS.getDocumentosProbatorios()) {    

            	  
            	  for (CorreccionNSS CorreccionNssRegistro:tramiteRegistro.getListaNssCorreccion()) {
            		  if (correccionNSS.getNss().equals(CorreccionNssRegistro.getNss())) {
            			  int cont=0;
            			  for(DocumentoProbatorio DocumentoProbatorioRegistro :CorreccionNssRegistro.getDocumentosProbatorios()) {
            				  if(DocumentoProbatorioAgregado.getBovedaDocId().equals(DocumentoProbatorioRegistro.getBovedaDocId())){
            					  cont=cont+1;
            				  }
            				  
            			  }
            			  if(cont == 0){
            				  DitDetalleNss ditDetalleNss =correccionDatosAseguradoEntity.obtenerDetalleNss(idTramite, correccionNSS.getNss());
                    		  registrarDocumentosDetalleNss(tramite.getTramiteId(), ditDetalleNss,
                    				  DocumentoProbatorioAgregado);
                    		  }
            			  
            		  }
            	  }
            	  
            	  

              }
          	
    	  
   	   
    }
    
    
    private void registrarDocumentosDetalleNss( Long idTramite,
            DitDetalleNss ditDetalleNss, DocumentoProbatorio documentoProbatorio ) 
            throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException {

    	documentoProbatorio = documentoProbatorioServiceBusinessRemote.registraDocumento(documentoProbatorio);

    	log.info("---CDA--- ingresa el detalle de docto de CDA ");
    	documentoCdaLocal.guardarDoctoNss(idTramite, documentoProbatorio,
    			ditDetalleNss.getCorreccionDatosAsegurado().getCveIdCorreccionDatosAsegurado(),
    			ditDetalleNss.getCveDetalleNss(),true);


    	log.info("---CDA--- se finalizo la carga de archivos ");
    }
    
    
    public Solicitud crearSolicitudInformacionAdicional(Solicitud solicitud, String usuario,String responsable) throws SolicitudNoEncontradaException, TramiteNoEncontradoException{
    	/**
    	 * ACTUALIZANDO TRAMITE
    	 */
    	solicitud = correccionDatosUtil.crearSolicitudInformacionAdicional(
    			solicitud, usuario, responsable);

//    	solicitudBusiness.actualizarXmlTramite(solicitud.getTramites().get(0));
//			solicitudBusiness.actualizarTramites(solicitud);
		 return solicitud;
    }

}


