package mx.gob.imss.ctirss.delta.cobranza.security.cfdi;

import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorKeyException;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.PrivateKeyLoader;
import mx.gob.imss.ctirss.delta.cobranza.security.cfdi.PublicKeyLoader;

import java.io.InputStream;

public class KeyLoaderFactory {
	
   /**
    * @param 
    * @param 
    *
    * @throws ErrorKeyException 
    */
    public final static KeyLoader createInstance(KeyLoaderEnumeration keyLoaderEnumeration, String keyLocation, String ... keyPassword) {
        KeyLoader keyLoader = null;

        if(keyLoaderEnumeration == KeyLoaderEnumeration.PRIVATE_KEY_LOADER) {
            keyLoader = new PrivateKeyLoader(keyLocation, keyPassword == null ? null : keyPassword[0]);
        } else if (keyLoaderEnumeration == KeyLoaderEnumeration.PUBLIC_KEY_LOADER){
            keyLoader = new PublicKeyLoader(keyLocation);
        }

        return keyLoader;
    }

    /**
     * @param 
     * @param 
     *
     * @throws ErrorKeyException 
     */
    public final static KeyLoader createInstance(KeyLoaderEnumeration keyLoaderEnumeration, InputStream keyInputStream, String ... keyPassword) {
        KeyLoader keyLoader = null;
        
        if(keyLoaderEnumeration == KeyLoaderEnumeration.PRIVATE_KEY_LOADER) {
            keyLoader = new PrivateKeyLoader(keyInputStream, keyPassword == null ? null : keyPassword[0]);
        } else if (keyLoaderEnumeration == KeyLoaderEnumeration.PUBLIC_KEY_LOADER){
            keyLoader = new PublicKeyLoader(keyInputStream);
        }

        return keyLoader;
    }
}
