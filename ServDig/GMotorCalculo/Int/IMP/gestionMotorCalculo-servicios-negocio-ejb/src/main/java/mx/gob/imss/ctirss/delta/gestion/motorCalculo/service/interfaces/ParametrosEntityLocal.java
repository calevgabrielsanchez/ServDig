
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

/**
 * Interfaz que definira los servicio de consulta de parametros, factores, etc.. que le sirvan a IVRO
 * @author NOVUTECK1
 *
 */
@Local
public interface ParametrosEntityLocal {

    /**
     * Obtieen la lista de dias feriados
     * 
     * @return la lista de dias feriados
     */
    List<Date> getDiasFeriados();
    
    /**
     * Obtiene el numero de dias posteriores a su vencimiento es vlido
     * @return El numero de dias validos despues de un vencimiento es valido
     */
    Integer getDiasVEncimiento();
    
    /**
     * Obtiene el porcentaje de recargo a cobrar en un seguro ivro
     * @return porcentaje a cobrar de recargo
     */
    BigDecimal getPorcentajeRecargo();
    
    BigDecimal getPorcentajeRecargoCvro();
}
