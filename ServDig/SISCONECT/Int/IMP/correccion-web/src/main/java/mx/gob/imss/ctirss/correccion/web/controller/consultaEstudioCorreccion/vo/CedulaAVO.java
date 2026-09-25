/**
 * Permite controlar los elemenentos visuales que involucran
 * a la cédula A [Balanza de Comprobación]
 */

package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo;

import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;

/**
 * Objeto visual el cual nos apoya al momento de consultar
 * las cédulas elaboradas por el patrón, en este caso
 * particular la "Cédula A" [Balanza de Comprobación].
 * 
 * @see ConsultaEstudioCorreccionVO
 * @author Marco Antonio Nieto Plett
 * @version 1.0.1
 *
 */
public class CedulaAVO {
	
	/**
	 * Indica el registro patronal que se está
	 * utilizando.
	 */
	private String registroPatronal;
	
	/**
	 * Descripción de la percepción utilizada
	 * dentro de la elaboración de la cédula A.
	 */
	private String txRemuneracion;
	
	/**
	 * Descripción del concepto tipo gasto
	 * utilizado dentro de la elaboración
	 * de la cédula A.
	 */
	private String txGastos;
	
	/**
	 * Importe asociado al gasto o remuneración
	 */
    private String imRemuneracion;
    
    /**
     * Importe asociado al gasto o remuneracion
     * desde el punto de vista del auxiliar
     * de nómina de la empresa, este es 
     * atributo es opcional.
     */
    private String imAuxiliarNomina;
    
    /**
     * Indica si el salario integra o no integra.
     * 
     * S .- Si integra
     * N .- No integra
     */
    private String inIntegraSalario;
    
    /**
     * Constructor por defecto del objeto
     * @author Marco Antonio Nieto Plett
     */
    public CedulaAVO(){}
    
    
    /**
     * Permite inicializar el objeto a través
     * de una consulta genérica SQL Ansi, en 
     * donde se le pasará un Obj tipo Object
     * y el constructor desdoblará la información.
     * 
     * @param obj
     * @see ConsultasEstudioCorreccion
     * @author Marco Antonio Nieto Plett
     */
    public CedulaAVO(Object[] obj){
    	int i = 0;
    	setRegistroPatronal(String.valueOf(obj[i++]));
    	setTxRemuneracion(String.valueOf(obj[i++]));
    	setTxGastos(String.valueOf(obj[i++]));
    	setImRemuneracion(String.valueOf(obj[i++]));
    	setImAuxiliarNomina(String.valueOf(obj[i++]));
    	setInIntegraSalario(String.valueOf(obj[i++]));
    }
    
    /**
     * Otorga la descripción asignada a la percepción. 
     * @return Descripción de la percepción.
     * @author Marco Antonio Nieto Plett
     */
	public String getTxRemuneracion() {
		return txRemuneracion;
	}
	
	/**
	 * Permite ingresar la descripción de la percepción 
	 * @param txRemuneracion
	 * @author Marco Antonio Nieto Plett
	 */
	public void setTxRemuneracion(String txRemuneracion) {
		this.txRemuneracion = txRemuneracion;
	}
	
	/**
	 * Permite obtener la descripción del gasto asociado
	 * al registro.
	 * @return Descripción del Gasto
	 * @author Marco Antonio Nieto Plett
	 */
	public String getTxGastos() {
		return txGastos;
	}
	
	/**
	 * Permite ingresar la descripción del gasto asociado
	 * al registro
	 * @param txGastos
	 * @author Marco Antonio Nieto Plett
	 */
	public void setTxGastos(String txGastos) {
		this.txGastos = txGastos;
	}
	
	/**
	 * Permite ingresar la remuneración en pesos asociada
	 * al gasto o a la remuneración.
	 * @return Cantidad asociada al registro de gasto o
	 *         remuneración
	 * @author Marco Antonio Nieto Plett
	 */
	public String getImRemuneracion() {
		return imRemuneracion;
	}
	
	/**
	 * Permite ingresar la remuneración en pesos asociada
	 * al gasto o a la remuneración
	 * @param imRemuneracion
	 * @author Marco Antonio Nieto Plett
	 */
	public void setImRemuneracion(String imRemuneracion) {
		this.imRemuneracion = imRemuneracion;
	}
	
	/**
	 * Permite obtener la cantidad registrada en el
	 * auxiliar de nómina del patrón en caso de que
	 * este sea requerido.
	 * @return Cantidad asociada al auxiliar de nómina
	 * @author Marco Antonio Nieto Plett
	 */
	public String getImAuxiliarNomina() {
		return imAuxiliarNomina;
	}
	
	/**
	 * Permite ingresar la cantidad registrada en el
	 * auxiliar de nómina del patrón en caso de que
	 * este sea requerido.
	 * @param imAuxiliarNomina
	 * @author Marco Antonio Nieto Plett
	 */
	public void setImAuxiliarNomina(String imAuxiliarNomina) {
		this.imAuxiliarNomina = imAuxiliarNomina;
	}
	
	/**
	 * Permite obtener un indicador el cual describe
	 * si la percepción y/o gasto integran para el salario
	 * o no.
	 * S- Si integra
	 * N- No integra
	 * 
	 * @return estatus de la percepción y/o gasto 
	 *         en la prespectiva de integración
	 * @author Marco Antonio Nieto Plett        
	 */
	public String getInIntegraSalario() {
		return inIntegraSalario;
	}
	
	/**
	 * Permite ingresar un indicador el cual describe
	 * si la percepción y/o gasto integran para el salario
	 * o no.
	 * S- Si integra
	 * N- No integra

	 * @param inIntegraSalario
	 * @author Marco Antonio Nieto Plett
	 */
	public void setInIntegraSalario(String inIntegraSalario) {
		this.inIntegraSalario = inIntegraSalario;
	}

	/**
	 * Permite obtener el registro patronal con el cual
	 * se desea trabajar a 11 posiciones.
	 * 
	 * @return RP
	 * @author Marco Antonio Nieto Plett
	 */

	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * Permite ingresar el registro patronal
	 * con el cual se va a trabajar a 11 
	 * posiciones.
	 * 
	 * @param registroPatronal
	 * @author Marco Antonio Nieto Plett
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
    
    

}
