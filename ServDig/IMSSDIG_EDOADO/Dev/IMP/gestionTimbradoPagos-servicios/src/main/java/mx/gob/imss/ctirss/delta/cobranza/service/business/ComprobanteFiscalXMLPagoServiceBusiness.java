package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.io.FileInputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import mx.gob.imss.ctirss.delta.cobranza.dto.ComprobanteDTO;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.model.Comprobante;
import mx.gob.imss.ctirss.delta.cobranza.model.ObjectFactory;
import mx.gob.imss.ctirss.delta.cobranza.model.cfdi.CFDv33;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.KeyLoaderEnumeration;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.KeyLoaderFactory;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.KeySecurity;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Utilerias;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ComprobanteFiscalXMLPagoServiceBusiness extends KeySecurity{

   private static final Logger LOG;
	
	static {
		LOG = LoggerFactory.getLogger(ComprobanteFiscalXMLPagoServiceBusiness.class);
	}
	
	
   /**
    * @param ComprobanteDTO
    * @param 
    * @return String
    * @throws ErrorEnGeneracionXMLPagoException 
    */
    public String generaComprobanteXML(ComprobanteDTO comprobanteDTO) throws ErrorEnGeneracionXMLPagoException {
    	LOG.info("########## GENERACION DEL COMPROBANTE XML : KEY " +  PATH_FILE_KEY_DIGITAL  + " PASS:" + PASSWORD_KEY_DIGITAL + " CER: " + PATH_FILE_CERT_DIGITAL +" ##########");
    	
    	Comprobante comprobante = null;
    	String comprobanteFiscalGenerado = null;
    	
    	try {
	    	PrivateKey key = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER, new FileInputStream(PATH_FILE_KEY_DIGITAL), PASSWORD_KEY_DIGITAL).getKey();
	    	
	    	X509Certificate cert = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, new FileInputStream(PATH_FILE_CERT_DIGITAL)).getKey();

	        CFDv33 cfd = new CFDv33("mx.gob.imss.ctirss.delta.cobranza.model");
	        comprobante = armaComprobanteXML(comprobanteDTO);        
	        cfd.setComprobante(comprobante);
	        cfd.sellarComprobante(key, cert);
	        
	        comprobanteFiscalGenerado = cfd.guardar(System.out);
	        comprobanteFiscalGenerado = Utilerias.validaCarcateresEspeciales(comprobanteFiscalGenerado);
    	} catch(ErrorEnGeneracionXMLPagoException errorGeneraXMl) {
    		LOG.info("########## ERROR AL GENERAR EL COMPROBANTE XML ########## [" + errorGeneraXMl.getMessage() + "]");
    		errorGeneraXMl.printStackTrace();
    		throw new ErrorEnGeneracionXMLPagoException("########## ERROR AL GENERAR EL COMPROBANTE XML ########## [" + errorGeneraXMl.getMessage() + "]", errorGeneraXMl.getCause());
    	} catch (Exception ex) {
    		LOG.info("########## ERROR AL GENERAR EL COMPROBANTE XML ########## [" + ex.getMessage() + "]");
    		ex.printStackTrace();
    		throw new ErrorEnGeneracionXMLPagoException("########## ERROR AL GENERAR EL COMPROBANTE XML ########## [" + ex.getMessage() + "]", ex.getCause());
    	}
       return comprobanteFiscalGenerado;
    }

    
   /**
    * @param comprobanteDTO 
    * @param 
    * @return Comprobante
    * @throws ErrorEnGeneracionXMLPagoException
    */
    public Comprobante armaComprobanteXML(ComprobanteDTO comprobanteDTO) throws ErrorEnGeneracionXMLPagoException {        
        
            ObjectFactory of = new ObjectFactory();
            Comprobante comprobante = of.createComprobante();
        
            comprobante.setVersion(comprobanteDTO.getVersion());
            comprobante.setFolio(comprobanteDTO.getFolio());
            comprobante.setFecha(comprobanteDTO.getFecha());
            comprobante.setFormaDePago(comprobanteDTO.getFormaDePago());
            comprobante.setSubTotal(comprobanteDTO.getSubTotal());
            comprobante.setTotal(comprobanteDTO.getTotal());
            comprobante.setTipoDeComprobante(comprobanteDTO.getTipoDeComprobante());
            comprobante.setMetodoDePago(comprobanteDTO.getMetodoDePago());
            comprobante.setLugarExpedicion(comprobanteDTO.getLugarExpedicion());
            comprobante.setMoneda(comprobanteDTO.getMoneda());
            comprobante.setSerie(comprobanteDTO.getSerie());
            comprobante.setEmisor(ComprobanteFiscalXMLPlantillaServiceBusiness.getEmisor(comprobante.getEmisor(), comprobanteDTO.getEmisor(), of));
            comprobante.setReceptor(ComprobanteFiscalXMLPlantillaServiceBusiness.getReceptor(comprobante.getReceptor(), comprobanteDTO.getReceptor(), of));        
            comprobante.setConceptos(ComprobanteFiscalXMLPlantillaServiceBusiness.getConceptos(comprobante.getConceptos(), comprobanteDTO.getConceptos(), of));
            comprobante.setImpuestos(ComprobanteFiscalXMLPlantillaServiceBusiness.getImpuestos(comprobante.getImpuestos(), comprobanteDTO.getImpuestos(), of));
            comprobante.setCfdiRelacionados(ComprobanteFiscalXMLPlantillaServiceBusiness.getCfdiRelacionados(comprobante.getCfdiRelacionados(), comprobanteDTO.getCfdiRelacionados(), of));
	         
	        return comprobante;
    }

}
