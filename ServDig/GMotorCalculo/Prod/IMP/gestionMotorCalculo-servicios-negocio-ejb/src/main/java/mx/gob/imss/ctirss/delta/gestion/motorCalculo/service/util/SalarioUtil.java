/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;

import org.apache.commons.lang.ArrayUtils;

/**
 * Clase utilitaria para el manejo de los salarios y sus reglas
 * 
 * @author NOVUTECK1
 * 
 */
public abstract class SalarioUtil {

    /**
     * Numero de salarios maximos con los que se puede calcular
     */
    private static final Integer MAXIMOS_SALARIOS = 25;
    /**
     * NUmero minimo de salarios en caso de ser excedente
     */
    private static final Integer MIN_SALARIOS_EXCEDENTE = 3;
    
    /**
     * Modalidades que aplican para un solo salario minimo
     */
    private static final ModalidadEnum[] IVRO_UN_SALARIO = {ModalidadEnum.TREINTAYCINCO,
            ModalidadEnum.CUARENTAYTRES, ModalidadEnum.CUARENTAYCUATRO };

    /**
     * Obtiene el salario para calcular las cuotas excedentes Hasta el momento
     * se tiene las siguientes reglas 
     * 1.- Para la modalidad 34 se calcula si el salario del empleado es mayor a 3 salarios minimos no integrados de la zona (A o B)
     * 2.- Solo se puede calcular con un salario maximo de 25 minimos
     * 3.- Cualquier otro caso se calcula con zero, es decir, para las modalidades 35,43 y 44.
     * 
     * @param salarioEmpl Salario registrado para el empleado
     * @param salarioMin salario minimo del empleado
     * @param modalidad Modalida a la que pertenece el empleado
     * @return El valor del salario para el calculo de cuotas excedentes
     */
    public static final BigDecimal getSalarioExcedente(BigDecimal salarioEmpl,
            BigDecimal salarioMin, ModalidadEnum modalidad) {
        BigDecimal salario = BigDecimal.ZERO;
        BigDecimal salarioMaximo = salarioMin.multiply(new BigDecimal(MAXIMOS_SALARIOS));
        BigDecimal salarioMinCuota = salarioMin.multiply(new BigDecimal(MIN_SALARIOS_EXCEDENTE));
        
        if (ModalidadEnum.TREINTAYCUATRO.equals(modalidad)
                && salarioEmpl.doubleValue() > (salarioMinCuota).doubleValue()) {
            salario = salarioEmpl.doubleValue() > salarioMaximo.doubleValue() ? salarioMaximo
                    : salarioEmpl;
            // el salario excedente es (el salario real o max 25 sal. minimos) menos tres salarios minimos
            salario = salario.subtract(salarioMinCuota);
        }
        return salario;
    }

    /**
     * Obtiene el salario con el cual se calculan las cuoas del motor() este
     * aplica para los calculos que tengan algun otra excepcion)
     * 
     * Reglas : 
     * 1.- Para las modalidades IVRO (35, 43 y 44) debe ser un salario minimo 
     * 2.- Para el resto de modalidades el salario es el registrado, con
     * un tope de 25 salarios minimos
     * 
     * @param salarioEmpl SAlario registrado por el empleado
     * @param salarioMin Salario minimo que le corresponde al empleado
     * @param modalidad Modalidad del trabajador
     * @return El valor de l salario con el cual se calcula la cuota
     */
    public static final BigDecimal getSalarioCuotas(BigDecimal salarioEmpl, BigDecimal salarioMin,
            ModalidadEnum modalidad) {
        if (ArrayUtils.contains(IVRO_UN_SALARIO, modalidad)) {
            return salarioMin;
        }
        BigDecimal salarioMnimoCalculo = salarioEmpl.doubleValue() >= salarioMin.doubleValue() 
                ? salarioEmpl : salarioMin;
        BigDecimal salarioMaximo = salarioMin.multiply(new BigDecimal(MAXIMOS_SALARIOS));
        return salarioMnimoCalculo.doubleValue() > salarioMaximo.doubleValue() ? salarioMaximo
                : salarioMnimoCalculo;
    }
    
    
    /**
     * Obtiene el salario con el cual se calculan las cuoas del motor() este
     * aplica para los calculos que tengan algun otra excepcion)
     * 
     * Reglas : 
     * 1.- Para las modalidades IVRO (35, 43 y 44) debe ser un salario minimo 
     * 2.- Para el resto de modalidades el salario es el registrado, con
     * un tope de 25 salarios minimos
     * 
     * Se aplica la siguiente regla (RN-020-00-15):
     * El salario para cuotas distintas a cuota fija y excedente es el salario real del empleado
     * (con tope de hasta 25 veces el SMGVDF y no menor a un salario diario integrado de la zona que pertenezca el patrón) 
     * 
     * 
     * @param salarioEmpl SAlario registrado por el empleado
     * @param salarioMin Salario minimo que le corresponde al empleado
     * @param modalidad Modalidad del trabajador
     * @return El valor de l salario con el cual se calcula la cuota
     */
    public static final BigDecimal getSalarioCuotasDomestico(BigDecimal salarioEmpl, BigDecimal salarioMinDF, BigDecimal salarioMinIntegradoZona) {

    	BigDecimal salarioMinimoCalculo = salarioEmpl.doubleValue() >= salarioMinIntegradoZona.doubleValue() 
                ? salarioEmpl : salarioMinIntegradoZona;
    	
        BigDecimal salarioMaximo = salarioMinDF.multiply(new BigDecimal(MAXIMOS_SALARIOS));
        return salarioMinimoCalculo.doubleValue() > salarioMaximo.doubleValue() ? salarioMaximo
                : salarioMinimoCalculo;
    }
    
    public static final BigDecimal getSalarioCuotasCvro(BigDecimal salarioEmpl, BigDecimal salarioMinDF, BigDecimal salarioMinIntegradoZona) {

    	BigDecimal salarioMinimoCalculo = salarioEmpl.doubleValue() >= salarioMinIntegradoZona.doubleValue() 
                ? salarioEmpl : salarioMinIntegradoZona;

        BigDecimal salarioMaximo = salarioMinDF.multiply(new BigDecimal(MAXIMOS_SALARIOS));
        return salarioMinimoCalculo.doubleValue() > salarioMaximo.doubleValue() ? salarioMaximo
                : salarioMinimoCalculo;
    }
}
