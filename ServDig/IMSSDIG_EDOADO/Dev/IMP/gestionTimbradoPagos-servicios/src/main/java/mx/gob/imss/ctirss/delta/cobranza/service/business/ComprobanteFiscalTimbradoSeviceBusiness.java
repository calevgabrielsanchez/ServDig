package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Iterator;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.autopac.modelo.RespuestaTimbrado;
import mx.gob.imss.ctirss.autopac.timbrado.ClienteAutoPacTimbrado;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnServicioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.AcuseRespuestaServicio;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaServicioTimbradoCFDI;
import mx.gob.imss.ctirss.delta.cobranza.security.timbrado.RampartSecurity;

public class ComprobanteFiscalTimbradoSeviceBusiness extends RampartSecurity {
	
	private static final Logger LOG;
		
	static {
	   LOG = LoggerFactory.getLogger(ComprobanteFiscalTimbradoSeviceBusiness.class);
	}
	
   /**
    * @param String
    * @param 
    * @return String
    * @throws ErrorEnServicioTimbradoException
    */
	public RespuestaServicioTimbradoCFDI procesaTimbrarRespStream(String comprobanteXML) throws ErrorEnServicioTimbradoException {
		String pRfc = IMSS_RFC;
        ClienteAutoPacTimbrado clienteAutopac = new ClienteAutoPacTimbrado();
        RespuestaServicioTimbradoCFDI acuseRespuestaTimbrado = new RespuestaServicioTimbradoCFDI();
	    try {
	    	LOG.info("########## INVOCANDO EL CLIENTE DEL SERVICIO DE AUTOPAC PARA EL TIMBRADO ##########");
	        RespuestaTimbrado respuestaTimbrado = clienteAutopac.invocarServicioAutoPacTimbrado(comprobanteXML.getBytes(), pRfc);
	        LOG.info("########## YA SE INVOCO EL CLIENTE DEL SERVICIO DE AUTOPAC PARA EL TIMBRADO ##########");
	        
	        AcuseRespuestaServicio acuseTimbrado = new AcuseRespuestaServicio();
	        acuseTimbrado.setCodeStatus(respuestaTimbrado.getAcuseRespuestaServicioHolder().value.getCodeStatus());
	        acuseTimbrado.setDescriptionError(respuestaTimbrado.getAcuseRespuestaServicioHolder().value.getDescriptionError());
	        acuseRespuestaTimbrado.setAcuseRespuestaTimbrado(acuseTimbrado);
	        
	        if (respuestaTimbrado.getRespuesta() != null && respuestaTimbrado.getRespuesta().getArchivoTimbrado() != null && respuestaTimbrado.getRespuesta().getArchivoTimbrado().length > 0) {
	        	System.out.println("Antes del decode Base64: " +respuestaTimbrado.getRespuesta().getArchivoTimbrado());
	        	System.out.println("Antes del decode Base64 con new String: "+new String(respuestaTimbrado.getRespuesta().getArchivoTimbrado()));
	        	byte[] strdec = org.apache.soap.encoding.soapenc.Base64.decode(new String(respuestaTimbrado.getRespuesta().getArchivoTimbrado()));
	        	System.out.println("Despues del decode Base64 sin UTF-8: " +strdec.toString());
	        	String xmlDecodificado = new String(strdec, "UTF-8");
	        	System.out.println("Despues del decode Base64 y con : " +xmlDecodificado);
//	        	xmlDecodificado = xmlDecodificado.replace("xmlns:ns2=\"http://www.sat.gob.mx/TimbreFiscalDigital\"", "xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\"");
	        	acuseRespuestaTimbrado.setXmlTimbrado(xmlDecodificado);
	        }
		} catch (RemoteException rmi) {
			LOG.error("########## ERROR EN EL TIMBRADO DEL XML ########## " + rmi.getMessage());
			rmi.printStackTrace();
		   	throw new ErrorEnServicioTimbradoException("########## ERROR EN EL TIMBRADO DEL XML ########## ", rmi.getCause());
		} catch (IOException ioe) {
			LOG.error("########## ERROR EN EL TIMBRADO DEL XML ##########" + ioe.getMessage());
			ioe.printStackTrace();
			throw new ErrorEnServicioTimbradoException("########## ERROR EN EL TIMBRADO DEL XML ##########", ioe.getCause());
		} catch (Exception ex) {
			LOG.error("########## ERROR EN EL TIMBRADO DEL XML ##########" + ex.getMessage());
			ex.printStackTrace();
			throw new ErrorEnServicioTimbradoException("########## ERROR EN EL TIMBRADO DEL XML ##########", ex.getCause());
		}
	    return acuseRespuestaTimbrado;
	  }
	
   /**
    * @param String
    * @param 
    * @return String
    * @throws 
    */
	 public AcuseRespuestaServicio obtieneAcuseRespuestaTimbrado(SOAPHeader soapHeader) throws ErrorEnServicioTimbradoException {
		   LOG.info("REPUESTA HEADERS:: " + soapHeader.toString());
		   AcuseRespuestaServicio acuseRespuestaServicio = new AcuseRespuestaServicio();
		        Iterator it = soapHeader.getChildElements();
		           while(it.hasNext()){	             
		             OMElement element =(OMElement)it.next();              
		             Iterator itChild = element.getChildElements();
		             
		                 while(itChild.hasNext()){
		                        OMElement elementChild =(OMElement)itChild.next(); 
		                        
		                        if(elementChild.getQName().getLocalPart().equals("CodeStatus")){
		                            acuseRespuestaServicio.setCodeStatus(Integer.parseInt(elementChild.getText()));
		                            LOG.info("Estatus Codigo Respuesta:: " + elementChild.getText());
		                        }
		                        
		                        if(elementChild.getQName().getLocalPart().equals("DescriptionError")){
		                            acuseRespuestaServicio.setDescriptionError(elementChild.getText());
		                            LOG.info("Descripcion Respuesta: "+elementChild.getText());
		                        }
		                        		                        
		                        if(elementChild.getQName().getLocalPart().equals("IncidenciaValidaciones")){			                        	
		                        	Iterator itChildResp = elementChild.getChildElements();		                        	
		   		                     while(itChildResp.hasNext()){
		   		                    	OMElement elementChildRes =(OMElement)itChildResp.next();
		   		                    	
		   		                    	if(elementChildRes.getQName().getLocalPart().equals("IncidenciaValidacion")){		   		                    		
		   		                    		Iterator itChildRespDescriptionIncidenciaValidacion = elementChildRes.getChildElements();
		   		                    		
				   		                     while(itChildRespDescriptionIncidenciaValidacion.hasNext()){
				   		                    	OMElement elementChildResDescription =(OMElement)itChildRespDescriptionIncidenciaValidacion.next();
				   		                    	LOG.info("INCIDENCIA DE VALIDACION  " + elementChildResDescription.getQName().getLocalPart() + " :: " + elementChildResDescription.getText());				   		                    	
				   		                     }
		   		                    	}
		   		                 	}
		                        }
		                    }	            
		          }
		    
		    return acuseRespuestaServicio;
	  }
}
	