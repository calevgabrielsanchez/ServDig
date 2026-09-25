package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Local;

@Local
public interface PersonaMoralServiceValidateLocal {
    
    /**
     * 111212
     * Este metodo valida que el RFC este bien construido
     * @param rfc
     * @return
     */
    String validarRFC(String rfc);
        
}
