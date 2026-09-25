package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import mx.gob.imss.ctirss.autopac.modelo.RespuestaCancelacionCfdiDto;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnCancelacionFolioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
import mx.gob.imss.ctirss.delta.cobranza.model.cfdi.CFDCancelacion;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.CancelacionType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.FoliosType;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaCancelacionCfdi;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.TipoCfdi;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.KeyLoaderEnumeration;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.KeyLoaderFactory;
import mx.gob.imss.ctirss.delta.cobranza.security.timbrado.ResourceBoundleConfigurationCSD;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Constantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProcesaCancelacionTimbrado {
	
	private static final Logger LOG;
	static {
		LOG = LoggerFactory.getLogger(ComprobanteFiscalPagoServiceBusiness.class);
	}
	
	/**
    * Metodo que arma xml con los folios a cancelar y formato solicitado por el SAT 
    * @param RegistroCFDI[]
    *  
    * @return RegistroCFDI[]
    * @throws ErrorEnCancelacionFolioTimbradoException 
    */
	public RegistroCFDI[] generaXMLCancelacionDeFolios(RegistroCFDI[] listaFolios) throws ErrorEnCancelacionFolioTimbradoException {
		LOG.info("Inicia la generacion del XML con folios");
		RegistroCFDI[] listaFoliosCancelados = null;
		try{
			CancelacionType cancelacionType = new CancelacionType();
	        List<FoliosType> listFolios = new ArrayList<FoliosType>();
	        Date fechaCancelacion = new GregorianCalendar().getTime();
	        FoliosType folio = null;
	        
	        if (listaFolios.length > 0) {
		        for (int i=0; i < listaFolios.length; i++) {
		        	if (listaFolios[i] != null) {
		        		LOG.info("Procesando registro con clave: " + listaFolios[i].getCveRegistro() + " UUID: " + listaFolios[i].getUuid());
		        		folio = new FoliosType();
		        		folio.setUUID(listaFolios[i].getUuid());
			        	listFolios.add(folio);
		        	}
		        }
	        }   
	        
	        LOG.info("Arma XML con atributos ordenados y folios.");        
	        cancelacionType.setFolios(listFolios);        
	        cancelacionType.setaNameXmlnsXsi(Constantes.ELEMENTO_CANCELACION_XMLNS_XSI_URL);        
	        cancelacionType.setNameSpaceXsd(Constantes.ELEMENTO_CANCELACION_XMLNS_XSD_URI);
	        cancelacionType.setNameSpacexmlns(Constantes.ELEMENTO_CANCELACION_XMLNS_URI);
	        
	        cancelacionType.setFecha(fechaCancelacion);
	        cancelacionType.setRfcEmisor(Constantes.RFC_IMSS);        
	                 
	        listaFoliosCancelados = generaSelloCancelaXML(cancelacionType);
		} catch(Exception ex) {
			LOG.info("Error al crear el archivo XML cancelacion " + ex.getMessage());
			throw new ErrorEnCancelacionFolioTimbradoException("Error al crear el archivo XML cancelacion [" + ex.getMessage() + "]", ex.getCause());
		}
		return listaFoliosCancelados;
	}
	
	/**
	 * Metodo que sella el XML de cancelacion armado 
	 * @param CancelacionType
	 * @param 
	 * @return RegistroCFDI[]
	 * @throws ErrorEnCancelacionFolioTimbradoException
	 */   
	public RegistroCFDI[] generaSelloCancelaXML(CancelacionType cancela) throws ErrorEnCancelacionFolioTimbradoException{
	   LOG.info("Agrega algoritmos y genera sello.");
	   RegistroCFDI[] listaFoliosCancelados = null;
	   try {
		    String archivoLlaveDigital = null;
	        String passwordLlaveDigital = null;
	        String archivoCertificadoDigital = null;
	                
	        ResourceBundle resourceBoundle = ResourceBoundleConfigurationCSD.getResourceBundle();
	        archivoLlaveDigital = ResourceBoundleConfigurationCSD.getArchivoLlaveDigital(resourceBoundle);
	        passwordLlaveDigital = ResourceBoundleConfigurationCSD.getpasswordLlaveDigital(resourceBoundle);
	        archivoCertificadoDigital = ResourceBoundleConfigurationCSD.getArchivoCertificadoDigital(resourceBoundle);
	        
	        PrivateKey key = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER, new FileInputStream(archivoLlaveDigital),passwordLlaveDigital).getKey();
	        X509Certificate cert = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, new FileInputStream(archivoCertificadoDigital)).getKey();
	        
	        CFDCancelacion cdfCancelacion  = new CFDCancelacion("mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion");
	        cdfCancelacion.setCancelaType(cancela);
	        cdfCancelacion.sellar(key, cert);
	        String xmlCancelacion = cdfCancelacion.guardar(System.out);
	        
//	        LOG.info("ARCHIVO CANCELACION GENERADO ::: " + xmlCancelacion);
	        CancelacionTimbradoServiceBusiness cancelaFolios = new CancelacionTimbradoServiceBusiness();
	        listaFoliosCancelados = cancelaFolios.procesaCancelacionTimbrado(xmlCancelacion);

		} catch(Exception ex) {
			ex.printStackTrace();
			LOG.info("Error al sellar el archivo XML cancelacion " + ex.getMessage());
			throw new ErrorEnCancelacionFolioTimbradoException("Error al sellar el archivo XML cancelacion [" + ex.getMessage() + "]", ex.getCause());
		}
	   return listaFoliosCancelados;
    }
   
   /**
    * Metodo que valida la respuesta de los folios cancelados con los enviados 
    * @param CancelacionType
    * @param 
    * @return RegistroCFDI[]
    * @throws ErrorEnCancelacionFolioTimbradoException
    */   
   public RegistroCFDI[] validacionDeCancelacionDeFolios(RegistroCFDI[] foliosParaCancelar, RegistroCFDI[] foliosCancelados) throws ErrorEnCancelacionFolioTimbradoException {
	   LOG.info("Metodo que hace match en las listas sizeFoliosParaCancelar "+ foliosParaCancelar.length  + " sizeFoliosCancelados " + foliosCancelados.length);
	   RegistroCFDI[] matcheoFoliosCancelados = new RegistroCFDI[foliosParaCancelar.length]; 
	   for(int index = 0; index < foliosCancelados.length; index++){
		   if(foliosCancelados[index].getUuid().equals(foliosParaCancelar[index].getUuid())){			  
			   matcheoFoliosCancelados[index].setCveRegistro(foliosParaCancelar[index].getCveRegistro());
			   matcheoFoliosCancelados[index].setEstatus(foliosCancelados[index].getEstatus());
			   matcheoFoliosCancelados[index].setUuid(foliosCancelados[index].getUuid());
		   }		   
	   }
	   LOG.info("matcheo de folios " + matcheoFoliosCancelados.length);
	   return matcheoFoliosCancelados;	  
   }
   
   public RegistroCFDI[] generaXMLCancelacionDeFoliosNuevaImplementacion(RegistroCFDI[] listaFolios) throws ErrorEnCancelacionFolioTimbradoException {
		LOG.info("########## INICIA LA GENERACION DEL XML CON FOLIOS ##########");
		RegistroCFDI[] listaFoliosCancelados = null;
		try {
	        List<FoliosType> listFolios = new ArrayList<FoliosType>();
	        FoliosType folio = null;
	        
	        if (listaFolios.length > 0) {
		        for (int i = 0; i < listaFolios.length; i++) {
		        	if (listaFolios[i] != null) {
		        		LOG.info("########## PROCESANDO REGISTRO CON CLAVE: " + listaFolios[i].getCveRegistro() + " UUID: " + listaFolios[i].getUuid() + "##########");
		        		folio = new FoliosType();
		        		folio.setUUID(listaFolios[i].getUuid());
			        	listFolios.add(folio);
		        	}
		        }
	        }
	        
//	        String xmlDocumento = generarXMLCancelacion(listFolios);	        
	        String xmlDocumento = generarStringXMLCancelacion(listFolios);
	        String xmlCancelacion = firmarDocumentoCancelacion(xmlDocumento);
	        
	        CancelacionTimbradoServiceBusiness cancelaFolios = new CancelacionTimbradoServiceBusiness();
	        listaFoliosCancelados = cancelaFolios.procesaCancelacionTimbrado(xmlCancelacion);
	        
		} catch(Exception ex) {
			LOG.info("########## ERROR AL CREAR EL ARCHIVO XML DE CANCELACION ########## " + ex.getMessage());
			throw new ErrorEnCancelacionFolioTimbradoException("########## ERROR AL CREAR EL ARCHIVO XML DE CANCELACION ########## [" + ex.getMessage() + "]", ex.getCause());
		}
		return listaFoliosCancelados;
   }
   
   public RespuestaCancelacionCfdi solicitarCancelacionCfdiPorUuid(String uuid) {
	   RespuestaCancelacionCfdiDto respuestaCancelacionCfdiDto = null;
	   RespuestaCancelacionCfdi respuestaCancelacionCfdi = new RespuestaCancelacionCfdi();
	   TipoCfdi[] listTipoCfdi = null;
	   List<FoliosType> listFolios = new ArrayList<FoliosType>();
	   FoliosType folio = new FoliosType();;
	   folio.setUUID(uuid);
	   listFolios.add(folio);
	   
	   try {
//		   String xmlDocumento = generarXMLCancelacion(listFolios);
		   String stringXmlDocumento = generarStringXMLCancelacion(listFolios);
		   
		   String xmlCancelacion = firmarDocumentoCancelacion(stringXmlDocumento);
		   
		   CancelacionTimbradoServiceBusiness cancelacionTimbradoServiceBusiness = new CancelacionTimbradoServiceBusiness();
		   respuestaCancelacionCfdiDto = cancelacionTimbradoServiceBusiness.procesaCancelacionCfdi(xmlCancelacion);
		   
		   if (respuestaCancelacionCfdiDto != null) {
			   	LOG.info("############ Objeto Respuesta de la Cancelacion de CFDI: " + respuestaCancelacionCfdiDto.toString()+ " ############");
				
				if (respuestaCancelacionCfdiDto.getListTipoCfdiDto() != null && respuestaCancelacionCfdiDto.getListTipoCfdiDto().length > 0) {
					listTipoCfdi = new TipoCfdi[respuestaCancelacionCfdiDto.getListTipoCfdiDto().length];
					TipoCfdi tipoCfdiDto = null;
					for (int i = 0; i < respuestaCancelacionCfdiDto.getListTipoCfdiDto().length; i++) {
						tipoCfdiDto = new TipoCfdi();
						tipoCfdiDto.setEstatusUuid(respuestaCancelacionCfdiDto.getListTipoCfdiDto()[i].getUuid());
						tipoCfdiDto.setEstatusUuid(respuestaCancelacionCfdiDto.getListTipoCfdiDto()[i].getEstatusUuid());
						tipoCfdiDto.setRespuesta(respuestaCancelacionCfdiDto.getListTipoCfdiDto()[i].getRespuesta());
						tipoCfdiDto.setRfcEmisor(respuestaCancelacionCfdiDto.getListTipoCfdiDto()[i].getRfcEmisor());
						tipoCfdiDto.setRfcReceptor(respuestaCancelacionCfdiDto.getListTipoCfdiDto()[i].getRfcReceptor());
						listTipoCfdi[i] = tipoCfdiDto;
					}
				}
				respuestaCancelacionCfdi.setListTipoCfdi(listTipoCfdi);
				respuestaCancelacionCfdi.setCodEstatus(respuestaCancelacionCfdiDto.getCodEstatus());
				respuestaCancelacionCfdi.setFecha(respuestaCancelacionCfdiDto.getFecha());
				respuestaCancelacionCfdi.setRfcEmisor(respuestaCancelacionCfdiDto.getRfcEmisor());
				respuestaCancelacionCfdi.setCodigoOperacion(respuestaCancelacionCfdiDto.getCodigoOperacion());
				respuestaCancelacionCfdi.setResultadoOperacion(respuestaCancelacionCfdiDto.getResultadoOperacion());
			} else {
				LOG.info("############ No se pudo obtener la cancelacion de CFDI ############");
				respuestaCancelacionCfdi = null;
			}
		   
	   } catch (TransformerConfigurationException ex) {
		   LOG.info("########## ERROR EN LA CONFIGURACION DE LA TRANSFORMACION DEL XML PARA LA CANCELACION DE CFDI ########## " + ex.getMessage());
		   ex.printStackTrace();
	   } catch (TransformerException ex) {
		   LOG.info("########## ERROR EN LA TRANSFORMACION DEL XML PARA LA CANCELACION DE CFDI ########## " + ex.getMessage());
		   ex.printStackTrace();
	   } catch (Exception ex) {
		   LOG.info("########## ERROR EN EL PROCESO DE LA CANCELACION DE CFDI ########## " + ex.getMessage());
		   ex.printStackTrace();
	   }
	   return respuestaCancelacionCfdi;
   }
   
//   public String generarXMLCancelacion(List<FoliosType> listFolios) throws TransformerConfigurationException, TransformerException {
//       Document doc = null;
//       StringWriter writer = new StringWriter();
//       try {
//           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//           String fecha = sdf.format(new Date()); 
//
//           DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//           DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//            // root elements
//           doc = docBuilder.newDocument();
//           
//           Element rootElement = doc.createElement("CancelaCFD");
//		   
//		   Attr attrXmlns = doc.createAttribute("xmlns");
//		   attrXmlns.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_URI);
//		   rootElement.setAttributeNode(attrXmlns);
//	       Attr attrXsd = doc.createAttribute("xmlns:xsd");
//	       attrXsd.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSD_URI);
//	       rootElement.setAttributeNode(attrXsd);
//		   Attr attrXsi = doc.createAttribute("xmlns:xsi");
//		   attrXsi.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSI_URL);
//		   rootElement.setAttributeNode(attrXsi);
//		   
//		   doc.appendChild(rootElement);
//
//           Element cancelacionElement = doc.createElement("Cancelacion");
////           Attr attrXsi = doc.createAttribute("xmlns:xsi");
////           attrXsi.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSI_URL);
////           rootElement.setAttributeNode(attrXsi);
////           Attr attrXsd = doc.createAttribute("xmlns:xsd");
////           attrXsd.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSD_URI);
////           rootElement.setAttributeNode(attrXsd);
//           Attr attrFecha = doc.createAttribute("Fecha");
//           attrFecha.setValue(fecha);
//           cancelacionElement.setAttributeNode(attrFecha);
//           Attr attrRfc = doc.createAttribute("RfcEmisor");
//           attrRfc.setValue(Constantes.RFC_IMSS);
//           cancelacionElement.setAttributeNode(attrRfc);
////           Attr attrXmlns = doc.createAttribute("xmlns");
////           attrXmlns.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_URI);
////           rootElement.setAttributeNode(attrXmlns);
//           
//           rootElement.appendChild(cancelacionElement);
//
//           for(int i = 0; i < listFolios.size(); i++){
//             // Folios elements
//             Element folios = doc.createElement("Folios");
//             cancelacionElement.appendChild(folios);
//             String cgtTimbradoNominaVo = (String)listFolios.get(i).getUUID();
//             Element firstname = doc.createElement("UUID");
//             firstname.appendChild(doc.createTextNode(cgtTimbradoNominaVo));
//             folios.appendChild(firstname);
//           }
//           
//           TransformerFactory transformerFactory = TransformerFactory.newInstance();
//           Transformer transformer = transformerFactory.newTransformer();
//           transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//           transformer.setOutputProperty(OutputKeys.INDENT, "no");
//           
//           StreamResult resultString = new StreamResult(writer);
//           transformer.transform(new DOMSource(doc), resultString);
//       } catch (Exception ex) {
//    	   LOG.info("Error al generar el documento xml de los cancelados " + ex.getMessage());
//    	   ex.printStackTrace();
//       }
//       return writer.toString();
//   }
   
   public String generarStringXMLCancelacion(List<FoliosType> listFolios) throws TransformerConfigurationException, TransformerException {
	   Document doc = null;
       StringWriter writer = new StringWriter();
       try {
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
           String fecha = sdf.format(new Date()); 

           DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // root elements
           doc = docBuilder.newDocument();

           Element rootElement = doc.createElement("Cancelacion");
           Attr attrXsi = doc.createAttribute("xmlns:xsi");
           attrXsi.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSI_URL);
           rootElement.setAttributeNode(attrXsi);
           Attr attrXsd = doc.createAttribute("xmlns:xsd");
           attrXsd.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSD_URI);
           rootElement.setAttributeNode(attrXsd);
           Attr attrFecha = doc.createAttribute("Fecha");
           attrFecha.setValue(fecha);
           rootElement.setAttributeNode(attrFecha);
           Attr attrRfc = doc.createAttribute("RfcEmisor");
           attrRfc.setValue(Constantes.RFC_IMSS);
           rootElement.setAttributeNode(attrRfc);
           Attr attrXmlns = doc.createAttribute("xmlns");
           attrXmlns.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_URI);
           rootElement.setAttributeNode(attrXmlns);

           doc.appendChild(rootElement);

           for(int i = 0; i < listFolios.size(); i++){
             // Folios elements
             Element folios = doc.createElement("Folios");
             rootElement.appendChild(folios);
             String cgtTimbradoNominaVo = (String)listFolios.get(i).getUUID();
             Element firstname = doc.createElement("UUID");
             firstname.appendChild(doc.createTextNode(cgtTimbradoNominaVo));
             folios.appendChild(firstname);
           }
           
           TransformerFactory transformerFactory = TransformerFactory.newInstance();
           Transformer transformer = transformerFactory.newTransformer();
           transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
           transformer.setOutputProperty(OutputKeys.INDENT, "no");
           
           StreamResult resultString = new StreamResult(writer);
           transformer.transform(new DOMSource(doc), resultString);
       } catch (Exception ex) {
    	   LOG.info("Error al generar el documento xml de los cancelados " + ex.getMessage());
    	   ex.printStackTrace();
       }
       return writer.toString();
   }
   
   @SuppressWarnings({ "rawtypes", "unchecked" })
   public String firmarDocumentoCancelacion(String documentXMLCancelados) throws Exception {
       // Create a DOM XMLSignatureFactory that will be used to generate the enveloped signature
       XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
       // Create a Reference to the enveloped document (in this case we are signing the whole document, 
       //so a URI of "" signifies that) and also specify the SHA1 digest algorithm and the ENVELOPED Transform.        
       Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
            Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)),null, null);
       // Create the SignedInfo
       SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
             (C14NMethodParameterSpec) null),fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),Collections.singletonList(ref));
       
       String archivoLlaveDigital = null;
       String passwordLlaveDigital = null;
       String archivoCertificadoDigital = null;
               
       ResourceBundle resourceBoundle = ResourceBoundleConfigurationCSD.getResourceBundle();
       archivoLlaveDigital = ResourceBoundleConfigurationCSD.getArchivoLlaveDigital(resourceBoundle);
       passwordLlaveDigital = ResourceBoundleConfigurationCSD.getpasswordLlaveDigital(resourceBoundle);
       archivoCertificadoDigital = ResourceBoundleConfigurationCSD.getArchivoCertificadoDigital(resourceBoundle);
       
       PrivateKey key = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER, new FileInputStream(archivoLlaveDigital),passwordLlaveDigital).getKey();
       X509Certificate cert = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, new FileInputStream(archivoCertificadoDigital)).getKey();
              
       KeyInfoFactory kif = fac.getKeyInfoFactory();
       
       X509IssuerSerial issuerx509 = kif.newX509IssuerSerial(cert.getIssuerDN().getName(), cert.getSerialNumber());

       List certs = new ArrayList();
       certs.add(issuerx509);
       certs.add(cert);

       X509Data datax509 = kif.newX509Data(certs);

       // Create a KeyInfo and add the KeyValue to it
       KeyInfo ki = kif.newKeyInfo(Collections.singletonList(datax509));

       // Instantiate the document to be signed
       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
       dbf.setNamespaceAware(true);
       
       Document documentCancelacion = generaDocumetoXML(documentXMLCancelados);
       
       // Create a DOMSignContext and specify the DSA PrivateKey and
       // location of the resulting XMLSignature's parent element
       
       DOMSignContext dsc = new DOMSignContext(key, documentCancelacion.getDocumentElement());

       // Create the XMLSignature (but don't sign it yet)
       XMLSignature signature = fac.newXMLSignature(si, ki);

       // Marshal, generate (and sign) the enveloped signature
       signature.sign(dsc);
       
       TransformerFactory transformerFactory = TransformerFactory.newInstance();
       Transformer transformer = transformerFactory.newTransformer();
       transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
       transformer.setOutputProperty(OutputKeys.INDENT, "no");
       
//       //Se eliminan namespace
//       documentCancelacion.getDocumentElement().removeAttribute("xmlns");
//       documentCancelacion.getDocumentElement().removeAttribute("xmlns:xsd");
//       documentCancelacion.getDocumentElement().removeAttribute("xmlns:xsi");
//       
//       //Se crea el nuevo documento para tener como root element CancelaCFD y poder agregar el elemento cancelacion a este documento
//       Document documentCancelaCFD = null;
//       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//       documentCancelaCFD = docBuilder.newDocument();
//       
//       Element rootElement = documentCancelaCFD.createElement("CancelaCFD");
//	   Attr attrXmlns = documentCancelaCFD.createAttribute("xmlns");
//	   attrXmlns.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_URI);
//	   rootElement.setAttributeNode(attrXmlns);
//       Attr attrXsd = documentCancelaCFD.createAttribute("xmlns:xsd");
//       attrXsd.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSD_URI);
//       rootElement.setAttributeNode(attrXsd);
//	   Attr attrXsi = documentCancelaCFD.createAttribute("xmlns:xsi");
//	   attrXsi.setValue(Constantes.ELEMENTO_CANCELACION_XMLNS_XSI_URL);
//	   rootElement.setAttributeNode(attrXsi);
//	   documentCancelaCFD.appendChild(rootElement);
	   
	   //Se importa el elemento Cancelacion a el nuevo documento con root element CancelaCFD
//	   rootElement.appendChild(documentCancelaCFD.importNode(documentCancelacion.getDocumentElement(), true));
	   
	   StringWriter writer = new StringWriter();
       StreamResult resultString = new StreamResult(writer);
//       transformer.transform(new DOMSource(documentCancelaCFD), resultString);
       transformer.transform(new DOMSource(documentCancelacion), resultString);
       
       return writer.toString();
       
   }
   
   public Document generaDocumetoXML(String documentXMLCancelados) {
	   try {
		   DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		   documentBuilderFactory.setNamespaceAware(true);
		   DocumentBuilder documentBuilder;
		   documentBuilder = documentBuilderFactory.newDocumentBuilder();
		   return documentBuilder.parse(new ByteArrayInputStream(documentXMLCancelados.getBytes()));
	   } catch (Exception ex) {
		   LOG.info("Error al parsear el XML Sting en un Document" + ex.getMessage());
		   ex.printStackTrace();
		   return null;
	   }
   }
   
}
