package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import mx.gob.imss.ctirss.autopac.cancelacion.ClienteAutoPacCancelacion;
import mx.gob.imss.ctirss.autopac.cancelacion.cfdi.ClienteAutoPacCancelacionCfdi;
import mx.gob.imss.ctirss.autopac.modelo.RespuestaCancelacionCfdiDto;
//import mx.gob.imss.ctirss.autopac.modelo.RespuestaCancelacionDto;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnCancelacionFolioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
import mx.gob.imss.ctirss.delta.cobranza.security.timbrado.RampartSecurity;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Constantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CancelacionTimbradoServiceBusiness extends RampartSecurity{

	private static final Logger LOG;

	static {
		   LOG = LoggerFactory.getLogger(CancelacionTimbradoServiceBusiness.class);
		}
	
   /** 
    * @param String
    * @param 
    * @return RegistroCFDI[]
    * @throws ErrorEnCancelacionFolioTimbradoException
    */
	public RegistroCFDI[] procesaCancelacionTimbrado(String cancelacionTimbrado) throws ErrorEnCancelacionFolioTimbradoException {
//		LOG.info("########## XML DE CANCELACION GENERADO ########## "+cancelacionTimbrado);
//		RegistroCFDI[] listaFoliosCancelados = null;
//		RespuestaCancelacionDto respuestaCancelacionDto = null;
//		try {
//			ClienteAutoPacCancelacion clienteAutoPacCancelacion = new ClienteAutoPacCancelacion();
//			
//			LOG.info("########## INVOCANDO EL CLIENTE DEL SERVICIO DE AUTOPAC PARA LA CANCELACION ##########");
//			respuestaCancelacionDto = clienteAutoPacCancelacion.invocarServicioAutoPacCancelacion(cancelacionTimbrado, Constantes.RFC_IMSS);
//			LOG.info("########## YA SE INVOCO EL CLIENTE DEL SERVICIO DE AUTOPAC PARA LA CANCELACION ##########");
//			
//			if (respuestaCancelacionDto != null && respuestaCancelacionDto.getRespuestaCancelacion() != null && 
//					respuestaCancelacionDto.getRespuestaCancelacion().getAcuseCancelacion() != null) {
//				LOG.info("########## RESPUESTA DEL SERVICIO DE CANCELACION ########## " + respuestaCancelacionDto.getRespuestaCancelacion().getAcuseCancelacion());
//				listaFoliosCancelados = obtieneAcuseRespuestaCancelacion(respuestaCancelacionDto.getRespuestaCancelacion().getAcuseCancelacion());
//			    LOG.info("########## TAMAÑO DE LA LISTA DE LOS FOLIOS CANCELADOS ########## " + listaFoliosCancelados.length);
//			}
//		} catch (MalformedURLException ex) {
//			ex.printStackTrace();
//			LOG.info("########## ERROR EN LA CANCELACION DE FOLIOS ########## " + ex.getMessage());
//			throw new ErrorEnCancelacionFolioTimbradoException("Error en el servicio cancelacion folios [" + ex.getMessage() + "]" , ex.getCause());
//		} catch (RemoteException ex) {
//			ex.printStackTrace();
//			LOG.info("########## ERROR EN LA CANCELACION DE FOLIOS ##########  " + ex.getMessage());
//			throw new ErrorEnCancelacionFolioTimbradoException("Error en el servicio cancelacion folios [" + ex.getMessage() + "]" , ex.getCause());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			LOG.info("########## ERROR EN LA CANCELACION DE FOLIOS ########## " + ex.getMessage());
//			throw new ErrorEnCancelacionFolioTimbradoException("Error en el servicio cancelacion folios [" + ex.getMessage() + "]" , ex.getCause());
//		}
//		return listaFoliosCancelados;
		return null;
	}
	
	
	public RespuestaCancelacionCfdiDto procesaCancelacionCfdi(String xmlCancelacion) throws ErrorEnCancelacionFolioTimbradoException {
		LOG.info("########## XML DE CANCELACION DE CFDI GENERADO: "+xmlCancelacion + " ##########" );
		RespuestaCancelacionCfdiDto respuestaCancelacionCfdiDto = null;
		try {
			ClienteAutoPacCancelacionCfdi clienteAutoPacCancelacionCfdi = new ClienteAutoPacCancelacionCfdi();
			
			LOG.info("########## INVOCANDO EL CLIENTE DEL SERVICIO DE AUTOPAC PARA LA CANCELACION DE CFDI ##########");
		
			respuestaCancelacionCfdiDto = clienteAutoPacCancelacionCfdi.invocarServicioAutoPacCancelacion(xmlCancelacion);
		
			LOG.info("########## YA SE INVOCO EL CLIENTE DEL SERVICIO DE AUTOPAC PARA LA CANCELACION DE CFDI ##########");
			
//			if (respuestaCancelacionCfdiDto != null && respuestaCancelacionCfdiDto.getListTipoCfdiDto() != null && respuestaCancelacionCfdiDto.getListTipoCfdiDto().length > 0) {
//				LOG.info("########## RESPUESTA DEL SERVICIO DE CANCELACION DE CFDI: " + respuestaCancelacionCfdiDto.getListTipoCfdiDto()[0].getEstatusUuid() + " ##########");
//			} else {
//				LOG.info("########## EL RESULTADO DE LA CANCELACION DEL XML NO FUE CORRECTA ##########");
//				respuestaCancelacionCfdiDto = new RespuestaCancelacionCfdiDto();
//				respuestaCancelacionCfdiDto.setCodEstatus("000");
//				respuestaCancelacionCfdiDto.setResultadoOperacion("Ocurrio un error en la cancelacion de cfdi");
//			}
		} catch (MalformedURLException eX) {
			respuestaCancelacionCfdiDto = new RespuestaCancelacionCfdiDto();
			respuestaCancelacionCfdiDto.setCodEstatus("001");
			respuestaCancelacionCfdiDto.setResultadoOperacion("Ocurrio un error en la cancelacion de cfdi");
			eX.printStackTrace();
		} catch (RemoteException eX) {
			respuestaCancelacionCfdiDto = new RespuestaCancelacionCfdiDto();
			respuestaCancelacionCfdiDto.setCodEstatus("002");
			respuestaCancelacionCfdiDto.setResultadoOperacion("Ocurrio un error en la cancelacion de cfdi");
			eX.printStackTrace();
		}
		return respuestaCancelacionCfdiDto;
	}
	

   /** 
     * @param String
     * @param 
     * @return RegistroCFDI[]
     * @throws ErrorEnCancelacionFolioTimbradoException
     */
	
	public RegistroCFDI[] obtieneAcuseRespuestaCancelacion(String acuseCancelacion)throws ErrorEnCancelacionFolioTimbradoException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        RegistroCFDI[] listaFoliosCancelados = null;
        RegistroCFDI registroCFDI = null;
        try {
        	
        	byte[] strdec = org.apache.soap.encoding.soapenc.Base64.decode(new String(acuseCancelacion));
        	String xmlDecodificado = new String(strdec, "UTF-8");
        	DocumentBuilder docBuilder = factory.newDocumentBuilder();
        	Document doc = docBuilder.parse(new ByteArrayInputStream(xmlDecodificado.getBytes("UTF-8")));
            
            Element channelNode = (Element) doc.getElementsByTagName("Acuse").item(0);
            String CodEstatus = channelNode.getAttribute("CodEstatus");
            CodEstatus = CodEstatus != null ? CodEstatus : "";
            LOG.info("Codigo Estatus en caso de error en proceso cancelacion:: " + CodEstatus);
            
            NodeList nodoFolios = doc.getElementsByTagName("Folios");
            int numFolios = nodoFolios.getLength();
            LOG.info("Numero total folios " + numFolios);
            listaFoliosCancelados = new RegistroCFDI[numFolios];
            for(int i = 0; i < numFolios; i++){
            	registroCFDI = new RegistroCFDI();
            	
                Element sectionFolios =(Element)nodoFolios.item(i);
                NodeList nodeUUID = sectionFolios.getElementsByTagName("UUID");
                Element nodeUUIDElement = (Element)nodeUUID.item(0);
                
                NodeList uuid = nodeUUIDElement.getChildNodes();
                String uuidValue = ((Node)uuid.item(0)).getNodeValue().trim();
                registroCFDI.setUuid(uuidValue);
                LOG.info("FOLIO : " + ((Node)uuid.item(0)).getNodeValue().trim());
                
                NodeList nodeEstatusUUID = sectionFolios.getElementsByTagName("EstatusUUID");
                Element nodeUUIDEstatusElement = (Element)nodeEstatusUUID.item(0);
                NodeList uuidEstatus = nodeUUIDEstatusElement.getChildNodes();
                
                String uuidEstatusValue = ((Node)uuidEstatus.item(0)).getNodeValue().trim();
                LOG.info("ESTATUS : " + uuidEstatusValue);
                
                if(uuidEstatusValue.equals(Constantes.CODIGO_RESPUESTA_CANCELACION_EXITOSO)){                	
                	uuidEstatusValue = Constantes.CODIGO_RESPUESTA_CANCELACION_EN_ODI;
                }
                registroCFDI.setEstatus(uuidEstatusValue);
                
	        	listaFoliosCancelados[i] = registroCFDI;	        	
            }

        }catch(ParserConfigurationException pe){
        	pe.printStackTrace();
			LOG.info("Error en la obtencion respuesta cancelacion " + pe.getMessage());
			throw new ErrorEnCancelacionFolioTimbradoException("Error en el servicio cancelacion folios [" + pe.getMessage() + "]" , pe.getCause());

        }catch(SAXException sax){
        	sax.printStackTrace();
			LOG.info("Error en la obtencion respuesta cancelacion " + sax.getMessage());
			throw new ErrorEnCancelacionFolioTimbradoException("Error en el servicio cancelacion folios [" + sax.getMessage() + "]" , sax.getCause());

        }catch(IOException ioex){
        	ioex.printStackTrace();
			LOG.info("Error en la obtencion respuesta cancelacion " + ioex.getMessage());
			throw new ErrorEnCancelacionFolioTimbradoException("Error en el servicio cancelacion folios [" + ioex.getMessage() + "]" , ioex.getCause());
        }
		return listaFoliosCancelados; 
	}
}
