package mx.gob.imss.ctirss.delta.cobranza.model.cfdi;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.CancelacionType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.CanonicalizationMethodType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.DigestMethodType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.KeyInfoType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.ReferenceType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.SignatureMethodType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.SignatureType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.SignedInfoType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.TransformType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.TransformsType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.X509DataType;
import mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion.X509IssuerSerialType;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Constantes;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.JaxbUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.ssl.util.Hex;
import org.apache.xml.security.c14n.Canonicalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public class CFDCancelacion {
	
  private static final Logger LOG;
//  private TransformerFactory tf;
  private CancelacionType document;
  
  private static final String BASE_CONTEXT = "mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion";
  private final static Joiner JOINER = Joiner.on(':');
  private JAXBContext context;
  
  
  @SuppressWarnings("rawtypes")
	public static final Class[] MARSHALLING_CLASSES;
	private static final JaxbUtil JAXB_UTIL;
	
	
	static {
		MARSHALLING_CLASSES = new Class[] { CancelacionType.class , SignedInfoType.class};
		JAXB_UTIL = new JaxbUtil(MARSHALLING_CLASSES);
		org.apache.xml.security.Init.init();
		LOG = LoggerFactory.getLogger(CFDCancelacion.class);
	}

   public CFDCancelacion(String... contexts) throws Exception {
        this.context = getContext(contexts);
        this.document = null;
   }
  
   private static JAXBContext getContext(String[] contexts) throws Exception {
       List<String> ctx = Lists.asList(BASE_CONTEXT, contexts);
      return JAXBContext.newInstance(JOINER.join(ctx));
   }
     
   
   /**
    * @param PrivateKey
    * @param  X509Certificate
    * @return
    * @throws 
    */	 
   public void sellar(PrivateKey key, X509Certificate cert) {
	   byte[] bytes = null;
	   String certStr = null;
	   String signature = null;
	   
        try{
        
            cert.checkValidity();
            String digestValue = generaDigestValues();
            LOG.info("DIGEST VALUES " + digestValue);
            bytes = cert.getEncoded();
            Base64 b64 = new Base64(-1);
            certStr = b64.encodeToString(bytes);
                
            SignatureType signatureType = new SignatureType();
            signatureType.setXmlns(Constantes.ELEMENTO_SIGNATURE_TYPE);
            SignedInfoType signatureInfo = new SignedInfoType();
            
            CanonicalizationMethodType canonicalizationMethodType = new CanonicalizationMethodType();
            SignatureMethodType signatureMethodType = new SignatureMethodType();
            ReferenceType referenceType = new ReferenceType();
            DigestMethodType digestMethodType = new DigestMethodType();
            KeyInfoType keyInfoType = new KeyInfoType();
            X509DataType x509DataType = new X509DataType();
            X509IssuerSerialType x509IssuerSerialType = new X509IssuerSerialType();
            
            TransformsType transformsType = new TransformsType();
            TransformType transformType = new TransformType();
            transformType.setAlgorithm(Constantes.ELEMENTO_TRANSFORMACION);
            transformsType.setTransform(transformType);
            
            digestMethodType.setAlgorithm(Constantes.ELEMENTO_DIGEST_VALUE);
            referenceType.setURI("");
            referenceType.setDigestMethod(digestMethodType);
            referenceType.setTransforms(transformsType);
            referenceType.setDigestValue(digestValue);
            signatureMethodType.setAlgorithm(Constantes.ELEMENTO_SIGNATURE_METHOD);
            
            canonicalizationMethodType.setAlgorithm(Constantes.ELEMENTO_CANONIZACION);
            signatureInfo.setCanonicalizationMethod(canonicalizationMethodType);
            signatureInfo.setSignatureMethod(signatureMethodType);
            signatureInfo.setReference(referenceType);
            
            signatureInfo.setaNameXmlns(Constantes.ELEMENTO_SIGNATURE_INFO_XMLNS);
            signatureInfo.setaNameXmlnsXsi(Constantes.ELEMENTO_SIGNATURE_INFO_XMLNS_XSI);
            signatureInfo.setNameSpaceXsd(Constantes.ELEMENTO_SIGNATURE_INFO_XSD);
            
            signatureType.setSignedInfo(signatureInfo);
            signature = getSignatureSignedInfo(signatureInfo, key);
            
            signatureType.setKeyInfo(keyInfoType);            
            x509DataType.setX509Certificate(certStr);
            keyInfoType.setX509Data(x509DataType);
            x509DataType.setX509IssuerSerial(x509IssuerSerialType);
            x509IssuerSerialType.setX509IssuerName(cert.getIssuerDN().getName());
            x509IssuerSerialType.setX509SerialNumber(cert.getSerialNumber().toString());
            signatureType.setSignatureValue(signature);
            document.setSignature(signatureType);
            LOG.info("CERT GET NAME " + cert.getIssuerDN().getName() + " SERIAL NUMBER " + cert.getSerialNumber());            
        }catch(Exception ex){
            System.out.println("Error al sellar el xml [" + ex.getMessage() + "]");		
            //throw new ErrorEnGeneracionXMLPagoException("Error al sellar el xml [" + ex.getMessage() + "]", ex.getCause());
        }        
  }
  
  /**
   * @param PrivateKey
   * @param  X509Certificate
   * @return
   * @throws 
   */   
   public void sellarComprobante(PrivateKey key, X509Certificate cert){
     sellar(key, cert);
   }
  
   /**
    * @param 
    * @param
    * @return
    * @throws 
    */
   public byte[] getOriginalBytes() throws Exception {
	  String xml = JAXB_UTIL.objectToXml(document);
	  Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
      byte canonXmlBytes[] = canon.canonicalize(xml.getBytes());
      String canonXmlString = new String(canonXmlBytes);
      
//      System.out.println("XML Canonizado CFDCancelacion::" + canonXmlString);
      return canonXmlString.getBytes();
  }
 
  
  /**
   * @param PrivateKey 
   * 
   * @return String
   * @throws 
   */
   public String getSignature(PrivateKey key) throws Exception {	  
        byte[] bytes = getOriginalBytes();
        Signature sig = Signature.getInstance("SHA1withRSA");    
        sig.initSign(key);
        sig.update(	bytes);
        byte[] signed = sig.sign();
       
        return Base64.encodeBase64String(signed);
   } 

   /**
    * @param SignedInfoType 
    * @param PrivateKey
    * @return String
    * @throws 
    */
    public String getSignatureSignedInfo(SignedInfoType signedInfo ,PrivateKey key ) throws Exception {	  
	  
		  String xml = JAXB_UTIL.objectToXml(signedInfo);
//		  LOG.info("SignedInfo:::" + xml);
		  
		  Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
	      byte canonXmlBytes[] = canon.canonicalize(xml.getBytes());
	      String canonXmlString = new String(canonXmlBytes);
	      
//	      LOG.info("XML SignedInfo Canonizado::" + canonXmlString);	  
	      byte[] bytes = canonXmlString.getBytes();
	      MessageDigest dgst= MessageDigest.getInstance("SHA-1");  
	      dgst.update(bytes);
	      
	      byte[] encryptedRaw = dgst.digest();
	      LOG.info("Hex del digest sha1 de signedinfo.. "+Hex.encode(encryptedRaw));
	      Signature sig = Signature.getInstance("SHA1withRSA");    
	      sig.initSign(key);
	      sig.update(bytes);
	      byte[] signed = sig.sign();     
	      LOG.info(" Hexadecimal de lo firmado.. " +Hex.encode(bytes));
	     return Base64.encodeBase64String(signed);
    }
 
   /**
    * @param SignedInfoType 
    * @param PrivateKey
    * @return String
    * @throws 
    */   
    public void setCancelaType(CancelacionType cancelacion) throws Exception {
        this.document = cancelacion;
    }

    /** 
     * @param 
     * @return String
     * @throws 
     */        
    public String generaDigestValues()throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception{
        
	      byte[] dataBytes =  getOriginalBytes();
	      MessageDigest dgst= MessageDigest.getInstance("SHA-1");  
	      dgst.update(dataBytes);
	      byte[] encryptedRaw = dgst.digest();   
	      	      
      return Base64.encodeBase64String(encryptedRaw);        
    }
    
    /** 
     * @param OutputStream
     * @return String
     * @throws 
     */
     public String guardar(OutputStream out) {
        StringBuffer sb = null;
        Marshaller m = null;
        try{

            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);           
            m.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
                  @Override
                  public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {              
                      out.write(ch,start,length);
                  }
              });
            m.setProperty("jaxb.encoding", "Unicode");            
            StringWriter sw = new StringWriter();
            m.marshal(document, sw);            
            sb = sw.getBuffer();
//            LOG.info("Creando archivo " + sb.toString());

            }catch(Exception ex){
                  System.out.println("Error al generar el archivo cancelacion XML [" + ex.getMessage() + "]");
     
         }	      
        return sb.toString();
      }         
}
