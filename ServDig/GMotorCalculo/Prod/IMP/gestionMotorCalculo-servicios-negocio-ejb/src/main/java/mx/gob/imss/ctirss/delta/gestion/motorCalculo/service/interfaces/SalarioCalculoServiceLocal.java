/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;

/**
 * Servicio encargado de obtener los salarios sobre los cuales se realizara el calculo de cuotas
 * @author NOVUTECK1
 *
 */
@Local
public interface SalarioCalculoServiceLocal {

    /**
     * Servicio que agregara los valores de los salarios sobre los cuales se va a realizar 
     * el calculo de cuotas
     * @param valores los datos para poder consultar los salarios correspondientes
     * @return los datos con los salarios para los calculos agregados
     * @throws SUAException POr si ocurren errores en la obtencion de los salarios
     */
    ValoresCalculoEmpleado agregaSalariosCalculo(ValoresCalculoEmpleado valores) throws SUAException;
    
    /**
     * Obtiene la UMA a una fecha determinada
     * @param fecha
     * @return BigDecimal UMA asociado a la fecha
     */
    BigDecimal getUma(Date fecha) throws SUAException;

    String obtenZonaSalarialOriginal(String zonaSalarial, ModalidadEnum modalidad, Date fechaConsulta);

    public boolean validaSalarioMod40(Calendar fechaFinCalculo, Calendar fechaInicialPeriodo);

    /**
     * Obtiene el factor de la rama CESANTÏA EN EDAD AVANZADA Y VEJEZ, tipo PATRONAL para la modalidad CVRO en una fecha determinada
     * @param zonaSalarial
     * @return BigDecimal del factor correspondiente
     */
    BigDecimal getFactorCYVPatronalCVRO(String zonaSalarial, BigDecimal salarioEmpleado, Date fechaInicioPeriodo, Date fechaFinPeriodo);

    /**
     * Verifica si el seguro requiere un ajuste por la ley 168 mediante el idCotizacion
     * @param nss
     * @return
     */
    BigDecimal verificaSeguroLey168(String nss);

    /**
     * Verifica si el seguro requiere un porcentaje al ajuste por la ley 168 mediante el nss
     * @param nss
     * @return
     */
    BigDecimal recuperaPorcentajeSeguroLey168(String nss);

    /**
     * Verifica si el seguro requiere un porcentaje de actualización al ajuste por la ley 168 mediante el nss
     * @param nss
     * @return
     */
    BigDecimal recuperaActualizacionSeguroLey168(String nss);
}
