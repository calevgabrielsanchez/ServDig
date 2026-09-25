package mx.gob.imss.ctirss.delta.cobranza.model.cfdi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.model.Comprobante;
import mx.gob.imss.ctirss.delta.cobranza.model.ObjectFactory;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.KeyLoaderEnumeration;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.KeyLoaderFactory;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Utilerias;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

public final class CFDv33 {
	
  private static final Logger LOG;
		
  static{
	LOG = LoggerFactory.getLogger(CFDv33.class);
  }
	
  private static final String XSLT = "/xslt/cadenaoriginal_3_3.xslt";
  private final Map<String, String> localPrefixes = Maps.newHashMap(PREFIXES);
  private TransformerFactory tf;
  private Comprobante document;

  
  private static final String[] XSD = new String[] {
      "/xsd/v32/cfdv33.xsd"
     //"/xsd/v3/TimbreFiscalDigital.xsd"
  };

  private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  private static final String BASE_CONTEXT = "mx.gob.imss.ctirss.delta.cobranza.model";
  private final static Joiner JOINER = Joiner.on(':');
  private final JAXBContext context;

  public static final ImmutableMap<String, String> PREFIXES = 
         ImmutableMap.of("http://www.w3.org/2001/XMLSchema-instance","xsi", 
                         "http://www.sat.gob.mx/cfd/3", "cfdi",
                         "http://www.sat.gob.mx/nomina","nomina");

  
  public CFDv33(InputStream in, String... contexts) throws Exception {
    this.context = getContext(contexts);
    this.document = load(in);
  }

  public CFDv33(Comprobante comprobante, String... contexts) throws Exception {
    this.context = getContext(contexts);
    this.document = copy(comprobante);
  }

  public CFDv33(String... contexts) throws Exception {
    this.context = getContext(contexts);
    this.document = null;
  }
  
  
  /**
   * @param 
   * @param 
   *
   * @throws 
   */
  public void sellar(PrivateKey key, X509Certificate cert) throws ErrorEnGeneracionXMLPagoException {
	try {
		cert.checkValidity();
	    byte[] bytes = cert.getEncoded();
	    Base64 b64 = new Base64(-1);
	    String certStr = b64.encodeToString(bytes);
	    document.setCertificado(certStr);
	    BigInteger bi = cert.getSerialNumber();
	    document.setNoCertificado(new String(bi.toByteArray()));
	    String signature = getSignature(key);    
	    document.setSello(signature);    
	} catch(Exception ex) {
		LOG.error("Error al sellar el xml [" + ex.getMessage() + "]");
		System.out.println("Error al sellar el xml [" + ex.getMessage() + "]");
		ex.printStackTrace();
		throw new ErrorEnGeneracionXMLPagoException("Error al sellar el xml [" + ex.getMessage() + "]", ex.getCause());
	}
  }
  
  /**
   * @param PrivateKey
   * @param X509Certificate
   *
   * @throws Exception
   */
  public void sellarComprobante(PrivateKey key, X509Certificate cert) throws ErrorEnGeneracionXMLPagoException{
    sellar(key, cert);
  }
  
  /**
   * @param 
   * @param 
   *
   * @throws 
   */
  public void validar() throws Exception {
    validar(null);
  }

  
  /**
   * @param ErrorHandler
   * @param 
   *
   * @throws Exception
   */
  public void validar(ErrorHandler handler) throws Exception{
    SchemaFactory sf =
    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Source[] schemas = new Source[XSD.length];
    for (int i = 0; i < XSD.length; i++) {
      schemas[i] = new StreamSource(getClass().getResourceAsStream(XSD[i]));
    }
    Schema schema = sf.newSchema(schemas);
    Validator validator = schema.newValidator();
    if (handler != null) {
      validator.setErrorHandler(handler);
    }
    validator.validate(new JAXBSource(context, document));
  }

  /**
   * @param 
   * @param 
   *
   * @throws 
   */
  public void verificar() throws Exception {
    String certStr = document.getCertificado();
    Base64 b64 = new Base64();
    byte[] cbs = b64.decode(certStr);

    X509Certificate cert = KeyLoaderFactory.createInstance(
            KeyLoaderEnumeration.PUBLIC_KEY_LOADER,
            new ByteArrayInputStream(cbs)
    ).getKey();

    String sigStr = document.getSello();
    byte[] signature = b64.decode(sigStr); 
    byte[] bytes = getOriginalBytes();
    Signature sig = Signature.getInstance("SHA256withRSA");
    sig.initVerify(cert);
    sig.update(bytes);
    boolean bool = sig.verify(signature);
    if (!bool) {
      throw new Exception("Invalid signature");
    }
  }

  /**
   * @param OutputStream
   * @param String
   *
   * @throws Exception
   */
  public String guardar(OutputStream out) throws ErrorEnGeneracionXMLPagoException{
 
	  StringBuffer sb = null;
	  Marshaller m = null;
	  
	  try {
		  m = context.createMarshaller();
		  m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapperImpl(localPrefixes));
		  m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		  m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		  m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3  " + "http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
		  m.setProperty(CharacterEscapeHandler.class.getName(), new CharacterEscapeHandler() {
		          @Override
		          public void escape(char[] ch, int start, int length, boolean isAttVal, Writer out) throws IOException {              
		              out.write(ch,start,length);
		          }
		      });
		    
		  m.setProperty("jaxb.encoding", "Unicode");
		  byte[] xmlHeaderBytes = XML_HEADER.getBytes("UTF8");
		    
		  StringWriter sw = new StringWriter();
		  sw.append(Utilerias.convierteBytesString(xmlHeaderBytes));
		  m.marshal(document, sw);
		   
		  sb = sw.getBuffer();
	  
	  } catch(Exception ex) {
		  LOG.error("Error al guardar el comprobante fiscal [" + ex.getMessage() + "]");
		  System.out.println("Error al guardar el comprobante fiscal [" + ex.getMessage() + "]");
		  ex.printStackTrace();
		  throw new ErrorEnGeneracionXMLPagoException("Error al guardar el comprobante fiscal [" + ex.getMessage() + "]", ex.getCause());
	  }
    return sb.toString();
  }
  
  /**
   * @param 
   * @param 
   * @return String
   * @throws 
   */
  public String getCadenaOriginal() throws Exception {
    byte[] bytes = getOriginalBytes();
    return new String(bytes, "UTF8");
  }

  /**
   * @param 
   * @param InputStream
   * @return Comprobante
   * @throws 
   */
  public static Comprobante newComprobante(InputStream in) throws Exception {
    return load(in);
  }

  /**
   * @param 
   * @param 
   *
   * @throws 
   */
  public byte[] getOriginalBytes() throws Exception {
	    ClassLoader cl = ObjectFactory.class.getClassLoader();
	    
//	    JAXBSource in = new JAXBSource(context, document);
	    
	    String stringXml = this.guardar(System.out);
	    stringXml = Utilerias.validaCarcateresEspeciales(stringXml);
	    Source xmlInput = new StreamSource(new StringReader(stringXml));
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();    
	    Result out = new StreamResult(baos);    
	    TransformerFactory factory = tf;
	    
	    if (factory == null) {
	      factory = TransformerFactory.newInstance();      
	      factory.setURIResolver(new URIResolverImpl());      
	    }       
	    //System.out.println(" XLST->" + cl.getResourceAsStream(XSLT) + " " + getClass());
	   
	    Transformer transformer = factory
	    .newTransformer(new StreamSource(getClass().getResourceAsStream(XSLT)));	    
//	    transformer.transform(in, out);
	    transformer.transform(xmlInput, out);
	 
	    if (baos != null) {
	    	LOG.info("Cadena Original:::" + new String(baos.toByteArray()));
	    }
	    return baos.toByteArray();
  }
    
  /**
   * @param 
   * @param PrivateKey
   * @return String
   * @throws 
   */
  public String getSignature(PrivateKey key) throws Exception {
	  
    byte[] bytes = getOriginalBytes();
//    version 3.2
//    Signature sig = Signature.getInstance("SHA1withRSA");
//    version 3.3
    Signature sig = Signature.getInstance("SHA256withRSA");
    sig.initSign(key);
    sig.update(bytes);
    byte[] signed = sig.sign();
    Base64 b64 = new Base64(-1);
    return b64.encodeToString(signed);
  }

  /**
   * @param 
   * @param 
   * @return ComprobanteBase
   * @throws 
   */
  public ComprobanteBase getComprobante() throws Exception {
    return new CFDv33ComprobanteBase(doGetComprobante());
  }
  
  /**
   * @param Comprobante
   * @param 
   *
   * @throws 
   */
  public void setComprobante(Comprobante comprobante) throws Exception {
    this.document = comprobante;
  }
  
  /**
   * @param 
   * @param 
   *
   * @throws 
   */
  public Comprobante doGetComprobante() throws Exception {
    return copy(document);
  }

  /**
   * @param 
   * @param 
   *
   * @throws 
   */
  private Comprobante copy(Comprobante comprobante) throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    DocumentBuilder db = dbf.newDocumentBuilder(); 
    Document doc = db.newDocument();
    Marshaller m = context.createMarshaller();
    m.marshal(comprobante, doc);
    Unmarshaller u = context.createUnmarshaller();
    return (Comprobante) u.unmarshal(doc);
  }
  
  /**
   * @param 
   * @param 
   *
   * @throws 
   */
  public Comprobante getComprobanteXML(InputStream in)throws Exception{
       Unmarshaller u = context.createUnmarshaller();
       Comprobante comprobante = (Comprobante)u.unmarshal(in);
     return comprobante;
  }

 
  public static final class CFDv33ComprobanteBase implements ComprobanteBase {

    private final Comprobante document;
  
    /**
     * @param 
     * @param 
     *
     * @throws 
     */
    
    public CFDv33ComprobanteBase(Comprobante document) {
      this.document = document;
    }
    
    
    /**
     * @param 
     * @param 
     *
     * @throws 
     */    
    public boolean hasComplemento() {
      return document.getComplemento() != null;
    } 
    
    
    /**
     * @param 
     * @param 
     *
     * @throws 
     */    
    public List<Object> getComplementoGetAny() {
      return document.getComplemento().getAny();
    }    
    
    /**
     * @param 
     * @param 
     *
     * @throws 
     */    
    public String getSello() {
      return document.getSello();
    }
    
    /**
     * @param 
     * @param 
     *
     * @throws 
    */    
    public void setComplemento(Element element) {
      ObjectFactory of = new ObjectFactory();
      Comprobante.Complemento comp = of.createComprobanteComplemento();
      List<Object> list = comp.getAny(); 
      list.add(element);
      document.setComplemento(comp);
    }
    /**
     * @param 
     * @param 
     *
     * @throws 
     */      
    public Object getComprobante() {
      return document;
    }
  }

  /**
   * @param 
   * @param 
   *
   * @throws 
   */    
  private static JAXBContext getContext(String[] contexts) throws Exception {
       List<String> ctx = Lists.asList(BASE_CONTEXT, contexts);
    return JAXBContext.newInstance(JOINER.join(ctx));
  }

  
  /**
   * @param 
   * @param 
   *
   * @throws 
   */   
  private static Comprobante load(InputStream source, String... contexts) throws Exception {
	 JAXBContext context = getContext(contexts);
	 try {
	      Unmarshaller u = context.createUnmarshaller();
	      return (Comprobante) u.unmarshal(source);
	     } finally {
	        source.close();
	     }
  }
  
  
  /**
   * @param 
   * @param 
   *
   * @throws 
   */  
  public static void dump(String title, byte[] bytes, PrintStream out) {
	  out.printf("%s: ", title);
	  for (byte b : bytes) {
	      out.printf("%02x ", b & 0xff);
	  }
	  out.println();
  }
  
}