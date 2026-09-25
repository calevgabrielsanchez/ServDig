package mx.gob.imss.ctirss.delta.cobranza.security.cfdi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorKeyException;
import lombok.Getter;


public class PublicKeyLoader implements KeyLoader {

    @Getter
    X509Certificate key;

   /**
    * @param 
    * @param 
    *
    * @throws ErrorKeyException 
    */    
    public PublicKeyLoader(String certificateLocation) {
        try {
            this.setX509Certificate(new FileInputStream(certificateLocation));
        } catch (FileNotFoundException fnfe) {
            throw new ErrorKeyException("La ubicación del archivo de la llave pública es incorrecta", fnfe.getCause());
        }
    }

    
   /**
    * @param 
    * @param 
    *
    * @throws ErrorKeyException 
    */
    public PublicKeyLoader(InputStream crtInputStream) {
        this.setX509Certificate(crtInputStream);
    }


    /**
     * @param 
     * @param 
     *
     * @throws ErrorKeyException
     */

    public void setX509Certificate(InputStream crtInputStream) {
        try {
            this.key = (X509Certificate)
                    CertificateFactory.getInstance("X.509").generateCertificate(crtInputStream);
        } catch (CertificateException e) {
            throw new ErrorKeyException("Error al obtener el certificado x.509. La codificación puede ser incorrecta.", e.getCause());
        }
    }


	public X509Certificate getKey() {
		return key;
	}


	public void setKey(X509Certificate key) {
		this.key = key;
	}
        
}
