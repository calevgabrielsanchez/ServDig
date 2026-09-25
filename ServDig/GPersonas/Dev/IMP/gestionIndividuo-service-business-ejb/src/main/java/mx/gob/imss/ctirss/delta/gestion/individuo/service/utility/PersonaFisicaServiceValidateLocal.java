package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Local
public interface PersonaFisicaServiceValidateLocal {

    /**
     * 111212
     * Este metodo valida que la CURP este bien construida
     * @param curp
     * @return
     */
    String validarCURP(String curp);
    
    /**
     * 111212
     * Este metodo valida que el RFC este bien construido
     * @param rfc
     * @return
     */
    String validarRFC(String rfc);
    
    /**
     * 111212
     * Este metodo valida que los datos basicos esten bien construidos
     * @param fisica
     * @return
     */
    List<String> validarDatosBasicos(Fisica fisica);
    
}
