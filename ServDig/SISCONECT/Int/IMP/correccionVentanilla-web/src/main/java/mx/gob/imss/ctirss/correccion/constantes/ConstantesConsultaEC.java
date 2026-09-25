/**
 * Contiene todas las constantes utilizadas
 * en el flujo de las consultas del estudio
 * de corrección.
 */
package mx.gob.imss.ctirss.correccion.constantes;

/**
 * Constantes de control para el estudio
 * de corrección.
 * @author Marco Antonio Nieto Plett
 * @version 1.1.0
 *
 */
public abstract class ConstantesConsultaEC {
	
	/**
	 * Constante utilizada para poder ingresar y obtener el modelo
	 * del estudio de corrección al momento de consultar 
	 * algún dato dentro de la JSP.
	 */
	public static final String MODEL_CONSULTAS_ESTUIDO_CORRECCION ="CEC";
	
	/**
	 * Utilizado en la Cédula I, indica que la 
	 * percepción integra al salario
	 */
	public static final String INTEGRA ="1";
	
	/**
	 * Utilizado en la Cédula I, indica que la 
	 * percepción NO integra al salario
	 */
	public static final String NO_INTEGRA ="2";
	
	/**64.75 59.82
	 * Indica 25 salarios mínimos
	 */
	public static final String VSMGDF ="1495.50";
	
	public static final double SALARIO_MINIMO = 64.75;

}
