/**
 * Permite controlar los elemenentos visuales que involucran
 * a la cédula I [Excedentes Topados]
 */
package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo;

import mx.gob.imss.ctirss.correccion.constantes.ConstantesConsultaEC;

/**
 * Objeto visual el cual nos apoya al momento de consultar
 * las cédulas elaboradas por el patrón, en este caso
 * particular la "Cédula I" [Excedentes Topados].
 * 
 * @see ConsultaEstudioCorreccionVO
 * @author Marco Antonio Nieto Plett
 * @version 1.0.0
 *
 */
public class CedulaIVO {
	
	/**
	 * Registro patronal asociado al asegurado
	 */
	private String registroPatronal;
	
	/**
	 * Descripción del mes mostrado
	 */
	private String txMes;
	
	/**
	 * Descripción de la remuneración mostrada
	 */
	private String txRemuneracion;
	
	/**
	 * Importe decimal (2) de la remuneración mostrada
	 */
	private String impRemuneracion;
	
	/**
	 * Indica si la remuneración integra o no.
	 * 1.- Si Integra
	 * 2.- No Integra
	 * @see ConstantesConsultaEC
	 */
	private String integra;
	
	/**
	 * Nombre del asegurado que se muestra
	 */
	private String nombreAsegurado;
	
	/**
	 * Apellido Paterno del asegurado que se muestra
	 */
	private String apPatAsegurado;
	
	/**
	 * Apellido Materno del asegurado que se muestra
	 */
	private String apMatAsegurado;
	
	/**
	 * Días devengados del asegurado
	 */
	private String diasDevengados;

	/**
	 * Total de las percepciones que si
	 * integran
	 */
	private String totalSiIntegra;
	
	/**
	 * Total de las percepciones que no
	 * integran
	 */
	private String totalNoIntegrar;
	
	private Float salarioMinimo;

	/**
	 * Constructor por defecto
	 */
	public CedulaIVO(){}
	
	/**
	 * Constructor el cual recibe un arreglo 
	 * tipo Obj[] y este es parceado llenando
	 * así los atributos del objeto.
	 * 
	 * @param obj
	 */
	public CedulaIVO(Object[] obj){
		int i=0;
		
		setRegistroPatronal(String.valueOf(obj[i++]));
		setTxMes(String.valueOf(obj[i++]));
		setTxRemuneracion(String.valueOf(obj[i++]));
		setImpRemuneracion(String.valueOf(obj[i++]));
		setTotalSiIntegra(String.valueOf(obj[i++]));
		setTotalNoIntegrar(String.valueOf(obj[i++]));
		setIntegra(String.valueOf(obj[i++]));
		setNombreAsegurado(String.valueOf(obj[i++]));
		setApPatAsegurado(String.valueOf(obj[i++]));
		setApMatAsegurado(String.valueOf(obj[i++]));
		setDiasDevengados(String.valueOf(obj[i++]));
		
		
	}
	/**
	 * Otorga el Registro Patronal (RP) asociado al segurado a 10 
	 * posiciones.
	 * 
	 * @return RP 10 posiciones
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	
	/**
	 * Permite ingrsar el Registro Patronal (RP) asociado al 
	 * asegurado a 10 posiciones.
	 * 
	 * @param registroPatronal
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	
	/**
	 * Otorga la descripción del mes (Enero-Diciembre)
	 * 
	 * @return descripción del mes
	 */
	public String getTxMes() {
		return txMes;
	}
	
	/**
	 * Permite ingresar la descripción del mes
	 * Usando notación Enero-Diciembre
	 * @param txMes
	 */
	public void setTxMes(String txMes) {
		this.txMes = txMes;
	}
	
	/**
	 * Otorga la descripción asociada
	 * a la remuneración
	 * 
	 * @return descripcion de la remuneración
	 */
	public String getTxRemuneracion() {
		return txRemuneracion;
	}
	
	/**
	 * Permite ingresar la descripción de la 
	 * remuneración.
	 * 
	 * @param txRemuneracion
	 */
	public void setTxRemuneracion(String txRemuneracion) {
		this.txRemuneracion = txRemuneracion;
	}
	
	/**
	 * Otorga la cantidad monetaria asociada
	 * a la remuneración, esta contiene decimales.
	 * 
	 * @return cantidad
	 */
	public String getImpRemuneracion() {
		return impRemuneracion;
	}
	
	/**
	 * Permite obtener el importe asociado a la 
	 * remuneración.
	 * 
	 * @param impRemuneracion
	 */
	public void setImpRemuneracion(String impRemuneracion) {
		this.impRemuneracion = impRemuneracion;
	}
	
	/**
	 * Indica si la remuneración integra o no integra.
	 * 1.- Si Integra
	 * 2.- No Integra
	 * 
	 * @see ConstantesConsultaEC
	 * @return 1 si integra, 2 si no integra
	 */
	public String getIntegra() {
		return integra;
	}
	
	/**
	 * Permite indicar si la remuneración 
	 * integra o no integra.
	 * 1.- Si Integra
	 * 2.- No Integra
	 * @see ConstantesConsultaEC
	 * @param integra
	 */
	public void setIntegra(String integra) {
		this.integra = integra;
	}
	
	/**
	 * Otorga el Nombre del asegurado
	 * @return nombre
	 */
	public String getNombreAsegurado() {
		return nombreAsegurado;
	}
	
	/**
	 * Permite ingresar el nombre del asegurado
	 * @param nombreAsegurado
	 */
	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}
	
	/**
	 * Otorga el nombre del asegurado
	 * @return
	 */
	public String getApPatAsegurado() {
		return apPatAsegurado;
	}
	
	/**
	 * Permite ingresar el apellido paterno
	 * del asegurado
	 * @param apPatAsegurado
	 */
	public void setApPatAsegurado(String apPatAsegurado) {
		this.apPatAsegurado = apPatAsegurado;
	}
	
	/**
	 * Otorga el apellido materno del asegurado
	 * @return
	 */
	public String getApMatAsegurado() {
		return apMatAsegurado;
	}
	
	/**
	 * Permite ingresar el apellido materno
	 * del asegurado.
	 * @param apMatAsegurado
	 */
	public void setApMatAsegurado(String apMatAsegurado) {
		this.apMatAsegurado = apMatAsegurado;
	}
	
	/**
	 * Indica en cantidad los dias devengados
	 * por el asegurado
	 * @return
	 */
	public String getDiasDevengados() {
		return diasDevengados;
	}
	
	/**
	 * Permite ingresar los dias devengados del
	 * asegurado
	 * @param diasDevengados
	 */
	public void setDiasDevengados(String diasDevengados) {
		this.diasDevengados = diasDevengados;
	}

	/**
	 * Otorga el total de las percepciones que
	 * si integran al salario del asegurado
	 * @return
	 */
	public String getTotalSiIntegra() {
		return totalSiIntegra;
	}

	/**
	 * Permite ingresar el total de las percepciones
	 * que integran en el salario del asegurado
	 * @param totalSiIntegra
	 */
	public void setTotalSiIntegra(String totalSiIntegra) {
		this.totalSiIntegra = totalSiIntegra;
	}

	/**
	 * Otorga el total que NO integra de todas las
	 * percepciones asociadas al salario del asegurado
	 * @return
	 */
	public String getTotalNoIntegrar() {
		return totalNoIntegrar;
	}
	
	/**
	 * Permite ingresar el total de las percepciones
	 * que NO integran dentro del salario del 
	 * asegurado
	 * @param totalNoIntegrar
	 */
	public void setTotalNoIntegrar(String totalNoIntegrar) {
		this.totalNoIntegrar = totalNoIntegrar;
	}

	/**
	 * Otorga la suma de las percepciones que 
	 * integran + las que no integran.
	 * 
	 * @see totalSiIntegrar
	 * @see totalNoIntegrar
	 * @return
	 */
	public String getPercepcionDelPeriodo() {
		return String.valueOf((Double.parseDouble(getTotalSiIntegra())+
				Double.parseDouble(getTotalNoIntegrar())));
	}

	/**
	 * Otorga un valor el cual es calculado
	 * 25 veces el salario mínimo
	 * @return
	 */
	public String getPercepcionExcenta() {
		double percepcionExcenta =  (getSalarioMinimo()* 25 * Double.parseDouble(this.diasDevengados)) + Double.parseDouble(this.getTotalNoIntegrar());
		return String.valueOf(percepcionExcenta);
	}

	
	public void setSalarioMinimo(Float salario){
		this.salarioMinimo = salario;
	}

	public Float getSalarioMinimo(){
		return salarioMinimo;
	}
	
	
	/**
	 * Otorga el valor positivo o negativo
	 * en caso de que exista un excedente será
	 * positivo.
	 * 
	 * @return
	 */
	public String getExcedentesFiniquitos() {
		double resta = Double.parseDouble(getPercepcionDelPeriodo())
				- Double.parseDouble(getPercepcionExcenta());
				if(resta<0){
					resta = 0;
				}
		return (String.valueOf(resta));
	}

	
	
	

}
