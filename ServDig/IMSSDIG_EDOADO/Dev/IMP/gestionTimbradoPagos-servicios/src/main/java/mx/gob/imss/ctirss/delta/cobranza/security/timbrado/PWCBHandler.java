package mx.gob.imss.ctirss.delta.cobranza.security.timbrado;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import org.apache.ws.security.WSPasswordCallback;
import javax.security.auth.callback.CallbackHandler;

import javax.security.auth.callback.UnsupportedCallbackException;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Constantes;


public class PWCBHandler extends RampartSecurity implements CallbackHandler{
    
   /**
	* @param
	* @return Policy
	* @throws IOException 
	* @throws UnsupportedCallbackException
	*/	
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    	
        for (int i = 0; i < callbacks.length; i++) {
        	
            // To use the private key to sign messages, we need to provide the private key password
            WSPasswordCallback pwcb = (WSPasswordCallback)callbacks[i];
             //User "importkey"   Pass =  importkey
            if(pwcb.getIdentifier().equals(USER_KEYSTORE) ) {
            	pwcb.setPassword(PASSWORD_KEYSTORE);
                return;
            }
        }
    }
}
