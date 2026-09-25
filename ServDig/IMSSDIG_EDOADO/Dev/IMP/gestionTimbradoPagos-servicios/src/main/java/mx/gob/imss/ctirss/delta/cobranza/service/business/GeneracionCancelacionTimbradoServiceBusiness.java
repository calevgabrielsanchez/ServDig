package mx.gob.imss.ctirss.delta.cobranza.service.business;


import java.io.FileInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.CancelacionType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.FoliosType;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.KeyLoaderEnumeration;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.KeyLoaderFactory;
import mx.gob.imss.ctirss.delta.cobranza.security.timbrado.ResourceBoundleConfigurationCSD;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Constantes;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.JaxbUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.xml.security.c14n.Canonicalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class GeneracionCancelacionTimbradoServiceBusiness {

	private static final Logger LOG;
	
	
	
	 @SuppressWarnings("rawtypes")
		public static final Class[] MARSHALLING_CLASSES;
		private static final JaxbUtil JAXB_UTIL;
		
		
		
	
	static {
	   LOG = LoggerFactory.getLogger(ComprobanteFiscalTimbradoSeviceBusiness.class);
	   MARSHALLING_CLASSES = new Class[] { CancelacionType.class};
		JAXB_UTIL = new JaxbUtil(MARSHALLING_CLASSES);
		
		org.apache.xml.security.Init.init();
	}

//	private static final String xpath  = "not(/*/*[local-name()='Signature'])";
	
	private static final String xpath  = "not(ancestor-or-self::*[local-name()='Signature'])";
	
   /**
   	* Metodo inicia la ejecucion para la generacion del XML e invocacion del serivio de cancelacion
   	* @param PagoReferenciadoDTO
   	* @return String
   	* @throws Exception 
   	*/		
    public static String procesaXMLCancelacionTimbrado(RegistroCFDI[] registros) throws Exception{
    	  Document documentoCancelacion = null;	
    	  String XMLCancelacionFirmado = null;
    	  try{
//    		  documentoCancelacion = generarXMLCancelacion(listaUUID);
    		  
    		  
    		  
    		  documentoCancelacion =  generaXMLCancelacionDeFolios(registros);
    		  
    		  
	    	  if(documentoCancelacion != null){
	    		  XMLCancelacionFirmado = firmarDocumentoCancelacion(documentoCancelacion);
	    		  
	    	  }
	    	  
	    	  if(XMLCancelacionFirmado != null){
	    		  CancelacionTimbradoServiceBusiness servicioCancelacionFolios = new CancelacionTimbradoServiceBusiness();
	    		  servicioCancelacionFolios.procesaCancelacionTimbrado(XMLCancelacionFirmado);
	    	  }
    	  }catch(Exception ex){
    		   LOG.info("Error en procesaXMLCancelacionTimbrado ::" + ex.getMessage());
    		   throw new ErrorEnGeneracionXMLPagoException("Error en la creacion del documento XML de cancelacion de folios [" + ex.getMessage() + "]" , ex.getCause());
    		  
    	  }
	    	  
    	  
    	return "";
     }
    
    
    /**
     * @param String
     * @param 
     * @return ServTimbradoCanStub
     * @throws 
     */	
    public static Document generarXMLCancelacion(List<String> listFoliosTimbradosCancelar) throws ErrorEnGeneracionXMLPagoException{

    	LOG.info("Inicia la generacion del XML cancelacion ");
        
        Document doc = null; 
    
        try {

              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
              Date date = new Date(System.currentTimeMillis()); 
              String fecha = sdf.format(new Date()); 
              
              //Crea los elementos del XML de cancelacion
              DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
              DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
              doc = docBuilder.newDocument();
                      
              Element rootElement = doc.createElement(Constantes.ELEMENTO_CANCELACION);              
              
              Attr attrXsi = doc.createAttribute(Constantes.ELEMENTO_CANCELACION_XMLNS_XSI);
              attrXsi.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSI_URL);
              rootElement.setAttributeNode(attrXsi);
              
              Attr attrXsd = doc.createAttribute(Constantes.ELEMENTO_CANCELACION_XMLNS_XSD);
              attrXsd.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSD_URI);
              rootElement.setAttributeNode(attrXsd);

              Attr attrFecha = doc.createAttribute(Constantes.ELEMENTO_CANCELACION_FECHA);
              
              
              //TODO: MODIFICAR ESTA FECHA ES DE PRUEBAS.
              attrFecha.setValue("2014-08-05T16:13:41");
              
              
              rootElement.setAttributeNode(attrFecha);
              
              Attr attrRfc = doc.createAttribute(Constantes.ELEMENTO_CANCELACION_RFC_EMISOR);
              attrRfc.setValue(Constantes.RFC_IMSS);
              rootElement.setAttributeNode(attrRfc);

              Attr attrXmlns = doc.createAttribute(Constantes.ELEMENTO_CANCELACION_XMLNS);
              attrXmlns.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_URI);
              rootElement.setAttributeNode(attrXmlns);

              doc.appendChild(rootElement);
              
              LOG.info("Agrega folios al documento " + listFoliosTimbradosCancelar.size());
              
              for(int i = 0; i < listFoliosTimbradosCancelar.size(); i++){

		            Element folios = doc.createElement(Constantes.ELEMENTO_CANCELACION_FOLIOS);
		            rootElement.appendChild(folios);
		
		            Element firstname = doc.createElement(Constantes.ELEMENTO_CANCELACION_UUID);
		            firstname.appendChild(doc.createTextNode(listFoliosTimbradosCancelar.get(i)));
		            folios.appendChild(firstname);
              }
              
              TransformerFactory transformerFactory = TransformerFactory.newInstance();
              Transformer transformer = transformerFactory.newTransformer();
              
              DOMSource source = new DOMSource(doc);              
              StreamResult result = new StreamResult(System.out);
            
              transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
              transformer.setOutputProperty(OutputKeys.INDENT, "no");
              transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
              transformer.setOutputProperty(OutputKeys.METHOD, "xml");
              transformer.transform(source, result);
           
           }catch(TransformerConfigurationException tce){
        	   LOG.info("Error en la creacion del documento XML de cancelacion de folios ::" + tce.getMessage());
        	   throw new ErrorEnGeneracionXMLPagoException("Error en la creacion del documento XML de cancelacion de folios sin firma [" + tce.getMessage() + "]" , tce.getCause());
           }catch(TransformerException te){
        	   LOG.info("Error en la creacion del documento XML de cancelacion de folios ::" + te.getMessage());
        	   throw new ErrorEnGeneracionXMLPagoException("Error en la creacion del documento XML de cancelacion de folios sin firma [" + te.getMessage() + "]" , te.getCause());
           }catch(ParserConfigurationException pce){              
              LOG.info("Error en la creacion del documento XML de cancelacion de folios ::" + pce.getMessage());
              throw new ErrorEnGeneracionXMLPagoException("Error en la creacion del documento XML de cancelacion de folios sin firma [" + pce.getMessage() + "]" , pce.getCause());
           }
        
        LOG.info("Crea XML con los folios a cancelar sin firma " + toStringXML(doc));
        
        return doc;
    }

    
    
    
    public static Document generaXMLCancelacionDeFolios(RegistroCFDI[] registro) throws Exception {
    	LOG.info("Inicia la generacion del XML con folios");
        CancelacionType cancelacionType = new CancelacionType();
        List<FoliosType> listFolios = new ArrayList<FoliosType>();
        
        /**FOLIOS DE PRUEBA*/
        FoliosType folio = new FoliosType();            
        //folio.setUUID("C728C984-B871-46CE-BBD3-6267CE09F42D"); 
        //folio.setUUID("D4E89438-40B2-484F-9872-049C0E9875F2");
        //folio.setUUID("0D0AFBC7-EB1F-410C-946D-F257AFE3B8BC");
        folio.setUUID("AA97B177-9383-4934-8543-0F91A7A02836");
        //folio.setUUID("3B9D3F1A-1B53-4D2B-A21C-B148166E8D43");
//        folio.setUUID("6DB5A9A0-8A41-4481-AEA4-FBFFADBC96DA");
        
        Date date = new GregorianCalendar().getTime();
        LOG.info("Agrega FOLIOS ");
        listFolios.add(folio);        
        cancelacionType.setFolios(listFolios);
        LOG.info("Agrega Atributos ");
        cancelacionType.setaNameXmlnsXsi("http://www.w3.org/2001/XMLSchema-instance");        
        cancelacionType.setNameSpaceXsd("http://www.w3.org/2001/XMLSchema");
        cancelacionType.setNameSpacexmlns("http://cancelacfd.sat.gob.mx");
        
        
        
        
        Calendar c = Calendar.getInstance();
//        c.set(Calendar.DATE, 5);
//        c.set(Calendar.HOUR_OF_DAY, 16);
//        c.set(Calendar.MINUTE, 13);
//        c.set(Calendar.SECOND, 41);
        
        
        c.set(Calendar.DATE, 30);
      c.set(Calendar.HOUR_OF_DAY, 14);
      c.set(Calendar.MINUTE, 14);
      c.set(Calendar.SECOND, 40);
      c.set(Calendar.YEAR,2012);
      c.set(Calendar.MONTH, Calendar.SEPTEMBER);
        
        System.out.println("Fecha Dummy " + c.getTime());
        
        cancelacionType.setFecha(c.getTime());
        cancelacionType.setRfcEmisor("AAA010101AAA");      
        
        String xml = JAXB_UTIL.objectToXml(cancelacionType);
        
        
        LOG.debug("XML DE Cancelacion generad::::" + xml);
       
        Document document = convertStringToDocument(xml);
        return document;
   }
   
    
    
    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder builder; 
        try 
        { 
            builder = factory.newDocumentBuilder(); 
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return null;
    }
    
	/**
	 * @param String
	 * @param 
	 * @return ServTimbradoCanStub
	 * @throws 
	 */	       
    public static String firmarDocumentoCancelacion (Document documentXMLFolios) throws Exception {
    	
    	
		LOG.info("Inicia firmado del documento XML de cancelacion");
		
    	ResourceBundle resourcebundleCSD = ResourceBoundleConfigurationCSD.getResourceBundle();
    	String XMLCancelacionFolios = null;
		try{
			
			
			Base64 base64 = new Base64();
			
			System.out.println("Generando el digest value del xml");
			String data = toStringXML(documentXMLFolios);
			MessageDigest md;
	        md = MessageDigest.getInstance("SHA");
	        byte[] sha1hash;
	       
	        md.update(data.getBytes(), 0, data.length());
	        sha1hash = md.digest();
	        String base64Sha1OfNonXml = new String(base64.encode(sha1hash));
	        System.out.println("Digest base64NonXMLCano:" + base64Sha1OfNonXml);
			
			
			
	        
	        
	        Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
	        byte canonXmlBytes[] = canon.canonicalize(data.getBytes());
	        String canonXmlString = new String(canonXmlBytes);
	        
	        System.out.println("XML Canonizado en GeneracionCancelacionTimbradoServiceBusiness::" + canonXmlString);
	        
	        md.update(canonXmlString.getBytes(), 0, canonXmlString.length());
	        sha1hash = md.digest();
	        String base64Sha1OfCanonicalXml = new String(base64.encode(sha1hash));
	        System.out.println("Digest:" + base64Sha1OfCanonicalXml);
			
			final XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
			
		        List<Transform> transforms = new ArrayList<Transform>() {{
		             add(fac.newTransform(
		                Transform.ENVELOPED,
		                (TransformParameterSpec) null
		            )
		            );
		        }};
					
				    Reference ref = fac.newReference
	                ("", fac.newDigestMethod(DigestMethod.SHA1, null),
	                        transforms,
	                        null, null);
					
				    // Create the SignedInfo.
			        SignedInfo si = fac.newSignedInfo
			                (fac.newCanonicalizationMethod
			                        (CanonicalizationMethod.INCLUSIVE,
			                                (C14NMethodParameterSpec) null),
			                        fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
			                        Collections.singletonList(ref));
				    
				    
			
			LOG.info("Carga CSD para la firma del documento");
			PrivateKey key = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER,		        
			                                                 new FileInputStream(ResourceBoundleConfigurationCSD.getArchivoLlaveDigital(resourcebundleCSD)),
			                                                                     ResourceBoundleConfigurationCSD.getpasswordLlaveDigital(resourcebundleCSD)).getKey();
			
			X509Certificate cert = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, 
					                                               new FileInputStream(ResourceBoundleConfigurationCSD.getArchivoCertificadoDigital(resourcebundleCSD))).getKey();
			LOG.info("Cargo Certificado para firma del documento");      
			
			
			
		    KeyInfoFactory kif = fac.getKeyInfoFactory();
		    
		    
		    
		    X509IssuerSerial issuerx509 = kif.newX509IssuerSerial(cert.getIssuerDN().getName(), cert.getSerialNumber());
	        List x509Content = new ArrayList();
	       // x509Content.add(issuerx509);
	        x509Content.add(cert);
	        
	        X509Data xd = kif.newX509Data(x509Content);
	        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
			
			
			
//			KeyInfoFactory kif = fac.getKeyInfoFactory();		
//			X509IssuerSerial issuerx509 = kif.newX509IssuerSerial(cert.getIssuerDN().getName(), cert.getSerialNumber());
//			
//			List certs = new ArrayList();
//			certs.add(issuerx509);
//			certs.add(cert);
//			
//			X509Data datax509 = kif.newX509Data(certs);		
//			KeyInfo ki = kif.newKeyInfo(Collections.singletonList(datax509));
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			LOG.info("Agrega firma en el documento");
			
			DOMSignContext dsc = new DOMSignContext(key, documentXMLFolios.getDocumentElement());
			XMLSignature signature = fac.newXMLSignature(si, ki);
			signature.sign(dsc);
			
//			  XPathExpression xpathExpression = XPathFactory.newInstance().newXPath().compile(xpath);
//		        String data = xpathExpression.evaluate(new InputSource(new StringReader(toStringXML(doc))));
//		        System.out.println("Xpath: " + data);
//			
//		        
//		        MessageDigest md;
//		        md = MessageDigest.getInstance("SHA");
//		        byte[] sha1hash;
//		        md.update(data.getBytes(), 0, data.length());
//		        sha1hash = md.digest();
//		        String base64Sha1OfCanonicalXml = new String(base64.encode(sha1hash));
//		        System.out.println("Digest:   " + base64Sha1OfCanonicalXml);
		        
			
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			TransformerFactory tf = TransformerFactory.newInstance();        
//			Transformer trans = tf.newTransformer();
//			  
//			StreamResult streamResult = new StreamResult(baos);		
//			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//			trans.setOutputProperty(OutputKeys.INDENT, "no");
//			trans.transform(new DOMSource(doc), streamResult);        
//			
//			
//			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			XMLCancelacionFolios = toStringXML(documentXMLFolios);
			LOG.info("Documento de cancelacion armado::" + XMLCancelacionFolios+"---");
		}catch(Exception ex){
			LOG.info("Error en la generacion firma del XML cancelacion ::" + ex.getMessage());
 		   throw new ErrorEnGeneracionXMLPagoException("Error en la generacion firma del XML cancelacion [" + ex.getMessage() + "]" , ex.getCause());
		}
		              
		return XMLCancelacionFolios;
	}
    
    
    
    


public static String toStringXML(Document doc) {
    try {
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//        transformer.setOutputProperty(OutputKeys.INDENT, "no");
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    } catch (Exception ex) {
        throw new RuntimeException("Error converting to String", ex);
    }
}


    
}
