package mx.gob.imss.ctirss.delta.cobranza.security.timbrado;

import java.util.Properties;



import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnServicioTimbradoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.rampart.policy.model.CryptoConfig;
import org.apache.rampart.policy.model.RampartConfig;

public class PolicyRampart extends RampartSecurity{
	
   private static final Logger LOG;
	
   static {
	 LOG = LoggerFactory.getLogger(PolicyRampart.class);
   }

  /**
   * @param
   * @param 
   * @return Policy
   * @throws Exception
   */
   public static Policy configuraPropiedadesRampart() throws ErrorEnServicioTimbradoException{
       
        CryptoConfig sigCrypto = new CryptoConfig();	
        RampartConfig rampartConfig = new RampartConfig();
        rampartConfig.setUser(USER_KEYSTORE);
        rampartConfig.setPwCbClass("mx.gob.imss.ctirss.delta.cobranza.security.timbrado.PWCBHandler");
                
        sigCrypto.setProvider("org.apache.ws.security.components.crypto.Merlin");
        sigCrypto.setCacheEnabled(true);	        
        
        Properties props = new Properties();
        props.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", TYPE_KEYSTORE);
        props.setProperty("org.apache.ws.security.crypto.merlin.file", PATH_FILE_KEYSTORE);
        props.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", PASSWORD_KEYSTORE);
        
        sigCrypto.setProp(props);
        rampartConfig.setSigCryptoConfig(sigCrypto);
        Policy policy = new Policy();
        policy.addAssertion(rampartConfig);
        
        for(PolicyComponent policyAsse:policy.getAssertions()){
        	///LOG.error("ASSERCION " + policyAsse.getType());	            
        }
        
        return policy;
     }
}
