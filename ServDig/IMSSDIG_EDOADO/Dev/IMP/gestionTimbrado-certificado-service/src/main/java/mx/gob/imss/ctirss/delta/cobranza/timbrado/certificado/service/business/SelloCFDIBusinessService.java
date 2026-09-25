/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.service.business;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.exception.ErrorSelloCFDIException;
import mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.model.SelloCFDI;
import mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.security.URIResolverImpl;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lucio Duran Silva.
 * 
 */
public class SelloCFDIBusinessService  {

	private static String path = "/Users/vanderluk/Downloads/CFDI/ProyectosCFDI/factura-electronica-master/resources/certs/keystore.jks";

	private static String password = "CTDIVCONT.2012";

	private static String alias = "ctdivcont.2012";

	// cadenaoriginal_3_2
//	private static  String XSLT = "/xslt/cadenaoriginal_3_2.xslt";
		
	// cadenaoriginal_3_3
	private static  String XSLT = "/xslt/cadenaoriginal_3_3.xslt";

	private static TransformerFactory tf;
	
	static Properties properties = null;
	
	static InputStream input = null;
	
	
	private static final Logger LOG;
	
	
	static{
		
		
		LOG = LoggerFactory.getLogger(SelloCFDIBusinessService.class);
		
		
		
		try {
			
			
			LOG.info("Inicializando las variables ...");
			
			
			SelloCFDIBusinessService.properties= new Properties(); 
			//input = new FileInputStream();
			
			 
			
			//SelloCFDIBusinessService.class.getResourceAsStream("sellado.properties");
			//ClassLoader.class.getResourceAsStream("/sellado.properties")
			SelloCFDIBusinessService.properties.load(SelloCFDIBusinessService.class.getResourceAsStream("/sellado.properties"));
			
			SelloCFDIBusinessService.path = SelloCFDIBusinessService.properties.getProperty("sellado.keystore");
			SelloCFDIBusinessService.alias = SelloCFDIBusinessService.properties.getProperty("sellado.alias");
			SelloCFDIBusinessService.password = SelloCFDIBusinessService.properties.getProperty("sellado.pwd");
			SelloCFDIBusinessService.XSLT = SelloCFDIBusinessService.properties.getProperty("sellado.xslt");
			
			
			LOG.info("Inicializando properties de sellado.....");
			
			LOG.info("Path:::" + SelloCFDIBusinessService.path);
			LOG.info("Alias"+SelloCFDIBusinessService.alias);
			LOG.info("XSTL resources.."+SelloCFDIBusinessService.XSLT);
			
			
			System.out.println(SelloCFDIBusinessService.XSLT);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.service.business
	 * .ISelloCFDIBusinessService#generarSelloCFDI()
	 */
	
	public static  SelloCFDI generarSelloCFDI(String xml) throws ErrorSelloCFDIException {
		// TODO Auto-generated method stub
		SelloCFDI selloCFDI = new SelloCFDI();
		try {

			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(new FileInputStream(path), password.toCharArray());
			KeyStore.PrivateKeyEntry keyEnt = (KeyStore.PrivateKeyEntry) keyStore
					.getEntry(alias, new KeyStore.PasswordProtection(password.toCharArray()));

			X509Certificate cert = (X509Certificate) keyEnt.getCertificate();

			
			
			byte[] byteArray = SelloCFDIBusinessService.getOriginalBytes(xml);
			
			String sello = SelloCFDIBusinessService.getSignature(keyEnt.getPrivateKey(), byteArray);
			String noCertificado = ""+cert.getSerialNumber();
			String certificado = SelloCFDIBusinessService.getCertificadoBase64(cert);
			String cadenaOriginal = new String(byteArray);
			
			
			
			
			selloCFDI.setCertificado(certificado);
			selloCFDI.setNoCertificado(noCertificado);
			selloCFDI.setSello(sello);
			selloCFDI.setCadenaOriginal(cadenaOriginal);
			
		} catch (KeyStoreException e) {
			throw new ErrorSelloCFDIException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new ErrorSelloCFDIException(e);
		} catch (CertificateException e) {
			throw new ErrorSelloCFDIException(e);
		} catch (FileNotFoundException e) {
			throw new ErrorSelloCFDIException(e);
		} catch (IOException e) {
			throw new ErrorSelloCFDIException(e);
		} catch (UnrecoverableKeyException e) {
			throw new ErrorSelloCFDIException(e);
		} catch (UnrecoverableEntryException e) {
			throw new ErrorSelloCFDIException(e);
		} catch (Exception e) {
			throw new ErrorSelloCFDIException(e);
		}
		
		System.out.println("Selllo generado .:::" + selloCFDI);

		return selloCFDI;
	}
	
	private static String getCertificadoBase64(X509Certificate cert){
		byte[] bytes = null;
		String certStr = null;
		
		try {
			bytes = cert.getEncoded();
		    Base64 b64 = new Base64(-1);
		    certStr = b64.encodeToString(bytes);
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		}
	
	    return certStr;
	}

	/**
	 * @param
	 * @param PrivateKey
	 * @return String
	 * @throws
	 */
	private static String getSignature(PrivateKey key, byte[] bytes) throws Exception {

		Signature sig = Signature.getInstance("SHA1withRSA");
		sig.initSign(key);
		sig.update(bytes);
		byte[] signed = sig.sign();

		Base64 b64 = new Base64(-1);
		return b64.encodeToString(signed);
	}

	/**
	 * @param
	 * @param
	 * 
	 * @throws
	 */
	private static byte[] getOriginalBytes(String xml) throws Exception {

		
		System.out.println("Parseando ::::" + xml);
		
		StringReader reader = new StringReader(xml);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Result out = new StreamResult(baos);
		TransformerFactory factory = SelloCFDIBusinessService.tf;
		if (factory == null) {
			factory = TransformerFactory.newInstance();
			factory.setURIResolver(new URIResolverImpl());
		}
		
		
		
		
		InputStream ioXSLT = SelloCFDIBusinessService.class.getResourceAsStream(XSLT);
		
		//InputStream ioXSLT = ClassLoader.class.getResourceAsStream(XSLT);
		
		Transformer transformer = factory.newTransformer(new StreamSource(
				ioXSLT));

		transformer.transform(new javax.xml.transform.stream.StreamSource(
				reader ), out);
		return baos.toByteArray();
	}

}
