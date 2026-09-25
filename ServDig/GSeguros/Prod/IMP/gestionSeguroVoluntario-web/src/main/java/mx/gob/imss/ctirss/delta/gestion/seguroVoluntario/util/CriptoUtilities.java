package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.security.Security;

import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class CriptoUtilities {
	
	private static final Logger LOGGER = LoggerFactory
            .getLogger(SeguroCvroUtil.class);
	
	static {
        // Registrar Bouncy Castle de forma dinámica en tu código
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }
 
    private static final String SECRET_KEY = "OdMlJGz5EoR1PgjOrptqI19lPwnS3up4";
    private static final int IV_LENGTH_BYTES = 16;
    


    // true para encriptar, false para desencriptar
    
 
    public static String cifrar(String data) throws Exception {
        if (data == null) return null;
        
        byte[] input = data.getBytes("UTF-8");
        
        AESEngine engine = new AESEngine();
        CBCBlockCipher cbc = new CBCBlockCipher(engine);
        PaddedBufferedBlockCipher cipher1 = new PaddedBufferedBlockCipher(cbc);
        
        byte[] keyBytes = new byte[32]; // Tu llave AES-256
        byte[] ivBytes = new byte[16];  // Tu Vector de Inicialización

        KeyParameter keyParam = new KeyParameter(keyBytes);
        ParametersWithIV params = new ParametersWithIV(keyParam, ivBytes);
        
        cipher1.init(true, params);
        byte[] output = new byte[cipher1.getOutputSize(input.length)];
        int tam = cipher1.processBytes(input, 0, input.length, output, 0);
        int tamFinal = cipher1.doFinal(output, tam);
        
        byte[] resultadoReal = new byte[tam + tamFinal];
        System.arraycopy(output, 0, resultadoReal, 0, resultadoReal.length);
        
        
        return encodeBase64UrlSafeNoPadding(resultadoReal);
        
    }
    
    public static String getIdFromUrl(String idSeguroCifrado)  throws Exception {
    	String idDescifrado = "";
    	
    	String parametroLimpio = URLDecoder.decode(idSeguroCifrado, "UTF-8");
    	
    	System.out.println("Desde getIdFromUrl");
    	System.out.println(parametroLimpio);
    	parametroLimpio=parametroLimpio.replace(" ", "+");
    	
    	String[] partes = parametroLimpio.split("\\|\\|", 2);
    	
    	if(partes.length < 2) {
	    	try {
	    		idDescifrado= CriptoUtilities.descifrar(parametroLimpio);
	    		LOGGER.info("idSegurodescifrado:"+idDescifrado);
	    	}catch(Exception e) {
	    		LOGGER.error("error al descifrar "+e);
	    	}
    	}else {
    		idDescifrado =partes[1];
    	}
    	
    	return idDescifrado;
    	
    }
 
    public static String descifrar(String textoCifradoBase64) throws Exception {
        if (textoCifradoBase64 == null) return null;
        
        //byte[] input = Base64.decode(textoCifradoBase64.getBytes("UTF-8"));
        
        byte[] input = decodeBase64UrlSafe(textoCifradoBase64);

        // 2. Configurar exactamente el mismo motor de Bouncy Castle
        AESEngine engine = new AESEngine();
        CBCBlockCipher cbc = new CBCBlockCipher(engine);
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cbc);
        
        byte[] keyBytes = new byte[32]; // Tu llave AES-256
        byte[] ivBytes = new byte[16];  // Tu Vector de Inicialización

        KeyParameter keyParam = new KeyParameter(keyBytes);
        ParametersWithIV params = new ParametersWithIV(keyParam, ivBytes);
        

        // CRUCIAL: Inicializar en modo DESENCRIPTACIÓN pasando "false"
        cipher.init(false, params); 

        byte[] output = new byte[cipher.getOutputSize(input.length)];
        
        int tam = cipher.processBytes(input, 0, input.length, output, 0);
        
        int tamFinal = cipher.doFinal(output, tam);
        
        byte[] textoOriginalBytes = new byte[tam + tamFinal];
        System.arraycopy(output, 0, textoOriginalBytes, 0, textoOriginalBytes.length);
        
        return new String(textoOriginalBytes, "UTF-8");
    }
 

 

    private static String encodeBase64UrlSafeNoPadding(byte[] src) {
        String base64 = javax.xml.bind.DatatypeConverter.printBase64Binary(src);
        return base64.replace('+', '-').replace('/', '_').replace("=", "");
    }
 
    private static byte[] decodeBase64UrlSafe(String src) {
        String base64 = src.replace('-', '+').replace('_', '/');
        int missingPadding = base64.length() % 4;
        if (missingPadding == 2) base64 += "==";
        else if (missingPadding == 3) base64 += "=";
        return javax.xml.bind.DatatypeConverter.parseBase64Binary(base64);
    }
}