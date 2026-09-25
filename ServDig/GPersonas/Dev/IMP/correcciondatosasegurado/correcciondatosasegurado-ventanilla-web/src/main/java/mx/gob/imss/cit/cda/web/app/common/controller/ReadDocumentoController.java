package mx.gob.imss.cit.cda.web.app.common.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Documento;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author antonio
 */
@Controller
public class ReadDocumentoController extends AbstractReadController<Documento, byte[]> {

	@Autowired
	@Qualifier(BeansConstants.READ_DOCUMENTO_HELPER)
	ReadHelper<Documento, byte[]> service;
	
	private final Logger log = LoggerFactory.getLogger(ReadDocumentoController.class);

	@Override
	public ReadHelper<Documento, byte[]> getHelper() {
		return service;
	}

	@RequestMapping(RequestMappingConstants.READ_DOCUMENTO)		
	public Object load(@PathVariable String idPersona,
          @PathVariable String folio,
          @PathVariable String extension,
          @PathVariable String nombreArchivo,
          @PathVariable String idDocBoveda,
          HttpServletRequest request, HttpServletResponse response){
	    
		printData(idPersona, folio, extension, nombreArchivo, idDocBoveda);
		Documento input = llenaDocumento(idPersona, folio, extension, nombreArchivo, idDocBoveda);
			
	        
	    ReadEvent<byte[]> readEvent = getHelper().requestEvent(new RequestReadEvent<Documento>(UUID.randomUUID(), input, getUserProfile(request)));
	    
	    try{
	      if( readEvent.isEntityFound() && readEvent.getData() != null ){
	        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(input.getNombreArchivo(), "UTF-8"));
	        response.setContentLength((int) readEvent.getData().length );
	        response.getOutputStream().write( readEvent.getData() );      
	
	        return null;
	      }else{
	    	  Map<String, Object> error = setErrores(readEvent);
	    	  return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE, error);
	      }
	    }catch( IOException ioe){
	    	Map<String, Object> errores = new HashMap<String, Object>();
	  	  	errores.put("errorBoveda", ioe.getMessage());
	  	  	errores.put("errorNegocio", MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002.getCodigo()));
	    	return new ModelAndView(RequestMappingConstants.VIEW_RECURSO_NO_DISPONIBLE,errores);
	    }
	}
	
	private Map<String, Object> setErrores( ReadEvent<byte[]> readEvent){
		Map<String, Object> errores = new HashMap<String, Object>();
  	  errores.put("errorBoveda", readEvent.getMensajeExcepcion());
  	  errores.put("errorNegocio", readEvent.getMensajeNegocio());
  	  return errores;

	}
	        
    private void printData(String idPersona,
          String folio,
          String extension,
          String nombreArchivo,
          String idDocBoveda){
	    
	    log.debug("---CDA--- El idPersona es: " + idPersona);
	    log.debug("---CDA--- El Folio es: " + folio);
	    log.debug("---CDA--- La extension es: " + extension);
	    log.debug("---CDA--- El nombreArchivo es: " + nombreArchivo);
	    log.debug("---CDA--- El idDocBoveda es: " + idDocBoveda);    
	} 
    
    private Documento llenaDocumento (String idPersona,
             String folio,
             String extension,
             String nombreArchivo,
             String idDocBoveda){
          
      Documento documento = new Documento();
      documento.setNombreArchivo(nombreArchivo);
      documento.setFolio(folio);
      documento.setExtension(extension);
      documento.setIdPersona(idPersona);
      documento.setIdDocBoveda(idDocBoveda);
      
      return documento;
	
    }

}
