package mx.gob.imss.ctirss.delta.cobranza.security.cfdi;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorKeyException;

import com.google.common.io.ByteStreams;
import lombok.Getter;
import org.apache.commons.ssl.PKCS8Key;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;


public class PrivateKeyLoader implements KeyLoader{

    @Getter
    PrivateKey key;
   /**
    * @param 
    * @param 
    *
    * @throws ErrorKeyException 
    */
    public PrivateKeyLoader(String privateKeyLocation, String keyPassword) {
        this.setPrivateKey(privateKeyLocation, keyPassword);
    }


   /**
    * @param 
    * @param 
    *
    * @throws ErrorKeyException 
    */    
    public PrivateKeyLoader(InputStream privateKeyInputStream, String keyPassword) {
        this.setPrivateKey(privateKeyInputStream, keyPassword);
    }


    /**
     * @param privateKeyLocation    private key located in filesystem
     * @param keyPassword           private key password
     *
     * @throws ErrorKeyException thrown when any security exception occurs
     */
    public void setPrivateKey(String privateKeyLocation, String keyPassword) {

        InputStream privateKeyInputStream = null;

        try {
            privateKeyInputStream = new FileInputStream(privateKeyLocation);
        }catch (FileNotFoundException fnfe){
            throw new ErrorKeyException("La ubicación del archivo de la llave privada es incorrecta", fnfe.getCause());
        }

        this.setPrivateKey(privateKeyInputStream, keyPassword);
    }


    /**
     *
     * @param privateKeyInputStream private key's input stream
     * @param keyPassword           private key password
     *
     * @throws ErrorKeyException thrown when any security exception occurs
     */
    public void setPrivateKey(InputStream privateKeyInputStream, String keyPassword) {

        byte[] privateKeyByte = this.extractProtectedPrivateKey(privateKeyInputStream, keyPassword);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);

        try {
            this.key = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        }catch (GeneralSecurityException gse) {
            throw new ErrorKeyException(
                    "Error al obtener la información del certificado debido a su codificación",
                    gse.getCause());
        }
    }

    /**
     * @param 
     * @param 
     *
     * @throws ErrorKeyException 
     */
    private byte[] extractProtectedPrivateKey(InputStream privateKeyInputStream, String keyPassword) {
        byte[] bytes = null;

        try {
            if(keyPassword == null) {
                ByteStreams.toByteArray(privateKeyInputStream);
            } else {
                bytes = new PKCS8Key(privateKeyInputStream, keyPassword.toCharArray()).getDecryptedBytes();
            }
        } catch (GeneralSecurityException e) {
            throw new ErrorKeyException("La contraseña del certificado no es correcta", e.getCause());
        } catch (IOException ioe){
            throw new ErrorKeyException(ioe.getMessage(), ioe.getCause());
        }

        return bytes;
    }

	public PrivateKey getKey() {
		return key;
	}
}
