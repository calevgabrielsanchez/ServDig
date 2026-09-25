/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.service.business;

import mx.gob.imss.ctirss.delta.cobranza.dto.ComprobanteDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.PagoReferenciadoDTO;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionComprobanteException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnServicioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaServicioTimbradoCFDI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lucio Duran Silva
 *
 */
public class GeneracionXMLPagoServiceBusiness {
	
    private static final Logger LOG;
	
	static {
		LOG = LoggerFactory.getLogger(GeneracionXMLPagoServiceBusiness.class);
	}
	
	/**
	* Metodo que crea los componentes que forman parte del comprobante fiscal a partir de los datos ingresados
	* @param PagoReferenciadoDTO datos ingresados para crear el comprobante fiscal 
	* @return ComprobanteDTO objeto creado con los componentes del comprobante fiscal
	* @throws Exception
	*/
    public static ComprobanteDTO generaComprobantePago(PagoReferenciadoDTO pagoReferenciadoDTO) throws ErrorEnGeneracionComprobanteException {
    	LOG.info("########## GENERA EL COMPROBANTE FISCAL DE LOS PAGOS REFERENCIADOS ##########");
    	ComprobanteDTO comprobantePago = null;
    	try {
    		comprobantePago = ComprobanteFiscalPagoServiceBusiness.generaComprobante(pagoReferenciadoDTO);
    	} catch (ErrorEnGeneracionComprobanteException ex) {
    		LOG.error("########## ERROR AL GENERAR EL COMPROBANTE FISCAL DE PAGOS REFERENCIADOS ########## [" + ex.getMessage() +"]");
    		ex.printStackTrace();
	    }

       return comprobantePago;
    }
    
   /**
	* Metodo que genera el XML con el comprobante creado
	* @param ComprobanteDTO
	* @return
	* @throws ErrorEnGeneracionXMLPagoException
	*/
    public static String generaComprobantePagoXML(ComprobanteDTO comprobante) throws ErrorEnGeneracionXMLPagoException{
    	LOG.info("########## SE GENERA EL XML CORRESPONDIENTE AL COMPROBANTE FISCAL DE LOS PAGOS REFERENCIADOS ##########");
    	String comprobanteXML = null;
        try {
        	ComprobanteFiscalXMLPagoServiceBusiness comprobanteFiscalXMLPago = new ComprobanteFiscalXMLPagoServiceBusiness();
        	comprobanteXML = comprobanteFiscalXMLPago.generaComprobanteXML(comprobante);
        } catch (ErrorEnGeneracionXMLPagoException error) {
        	LOG.error("########## ERROR EN LA GENERACION DEL COMPONENTE XML ########## [" + error.getMessage() +"]");
        	error.printStackTrace();
        }
        return comprobanteXML;
    }
    
    
    /**
	* Metodo que hace la peticion al servicio timbrar del SAT
	* @param String
	* @return
	* @throws 
	*/
    public static RespuestaServicioTimbradoCFDI timbraComprobanteFiscal(String comprobanteXML) throws ErrorEnServicioTimbradoException {
    	RespuestaServicioTimbradoCFDI respuestaServicioTimbrado = null;
    	ComprobanteFiscalTimbradoSeviceBusiness timbrarCFDI = new ComprobanteFiscalTimbradoSeviceBusiness();
    	try {
    		respuestaServicioTimbrado = timbrarCFDI.procesaTimbrarRespStream(comprobanteXML);
    	} catch (ErrorEnServicioTimbradoException ex) {
    		LOG.error("########## ERROR EN EL TIMBRADO DEL XML ########## [" + ex.getMessage() +"]");
			ex.printStackTrace();
    	}
    	return respuestaServicioTimbrado;
    }
    
   /**
   	* Metodo que inicia la ejecucion para la creacion del comprobante fiscal
   	* @param PagoReferenciadoDTO
   	* @return String
   	* @throws ErrorEnGeneracionXMLPagoException,ErrorEnGeneracionComprobanteException
   	*/
    public static RespuestaServicioTimbradoCFDI procesaComprobanteFiscalXMLTimbrado(PagoReferenciadoDTO pagoReferenciadoDTO) 
    		throws ErrorEnServicioTimbradoException, ErrorEnGeneracionComprobanteException, ErrorEnGeneracionXMLPagoException {
    	
    	ComprobanteDTO comprobante = null;
    	String comprobanteXML = null;
    	RespuestaServicioTimbradoCFDI respuestaServicioTimbrado = null;
    	
    	comprobante = GeneracionXMLPagoServiceBusiness.generaComprobantePago(pagoReferenciadoDTO);
    	
    	if (comprobante != null) {
    		comprobanteXML = GeneracionXMLPagoServiceBusiness.generaComprobantePagoXML(comprobante);
    		LOG.info("########## XML GENERADO PARA TIMBRAR ########## \n" + comprobanteXML);
    	}
        
    	if (comprobanteXML != null) {
    		try {
    			respuestaServicioTimbrado = GeneracionXMLPagoServiceBusiness.timbraComprobanteFiscal(comprobanteXML);
    		} catch(Exception ex) {
    			ex.printStackTrace();
    		}
    		if (respuestaServicioTimbrado != null) {
    			if (respuestaServicioTimbrado.getXmlTimbrado() != null) {
        			LOG.info("########## XML TIMBRADO ########## \n" + respuestaServicioTimbrado.getXmlTimbrado());
    			}
        		if (respuestaServicioTimbrado.getAcuseRespuestaTimbrado() != null) {
        			LOG.info("########## CODIGO ESTATUS "+respuestaServicioTimbrado.getAcuseRespuestaTimbrado().getCodeStatus()+" ##########");
    			}
			}
    	}
    	return respuestaServicioTimbrado;
    }
}
