/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.util;

/**
 * Clase con valores constantes y utilitarios para el motor de calculo 
 * y la generacion de archivo SUA
 * @author NOVUTECK1
 *
 */
public abstract class SUAConstants {

	/**
	 * Name Sapace para los schemas que maneja el motor de calculo y archivo sua
	 */
	public static final String SUA_NAMESSPACE = "http://mx.gob.imss.ctirss.delta.motorcalc/sua";

	/**
	 * Codigo de error si no se encuentra un patron 
	 */
	public static final String COD_NO_PATRON = "S100";
	/**
	 * mensaje de error si no se encuentra un patron 
	 */
	public static final String MSG_NO_PATRON = 
	        "No se encontró un patrón asociado con el número de registro patronal.";
	/**
	 * Codigo de error si no se encuentra un trabajador 
	 */
	public static final String COD_NO_TRABAJADOR = "S101";
	/**
	 * mensaje de error si no se encuentra un trabajador 
	 */
	public static final String MSG_NO_TRABAJADOR = 
			"No se encontró un trabajador asociado con el número de seguridad social. ";
	
	/**
     * Codigo de error si la fecha final del periodo es menor a la inicial 
     */
    public static final String COD_FECHA = "S102";
    /**
     * mensaje de error si la fecha final del periodo es menor a la inicial 
     */
    public static final String MSG_FECHA = 
            "La fecha final del periodo a calcular no puede ser menor a la fecha inicial";
    
    /**
     * Codigo de error si no se encentra registrado el salario en BD 
     */
    public static final String COD_NO_SALARIO = "S103";
    /**
     * mensaje de error si no se encentra registrado el salario en BD
     */
    public static final String MSG_NO_SALARIO = 
            "No se encuentra el salario mínimo para generar los cálculos";
    
    /**
     * Codigo de error si existe mas e un salario para realizar los calculos en la fecha indicada 
     */
    public static final String COD_MULTIPLE_SALARIO = "S104";
    /**
     * mensaje de error si existe mas e un salario para realizar los calculos en la fecha indicada
     */
    public static final String MSG_MULTIPLE_SALARIO = 
            "Existe más de un salario mínimo para realizar los cálculos";
    
    /**
     * Codigo de error si la url recibida es nula o vacia 
     */
    public static final String COD_NO_URL = "S105";
    /**
     * mensaje de error si la url recibida es nula o vacia
     */
    public static final String MSG_NO_URL = "La URL del servicio no puede ser vacía.";
    /**
     * Codigo de error si la url recibida se encuentra mal formada 
     */
    public static final String COD_MAL_URL = "S106";
    /**
     * mensaje de error si la url recibida se encuentra mal formada
     */
    public static final String MSG_MAL_URL = "La URL del servicio se encuentra mal formada.";
    /**
     * Codigo de error si el ws de proceso sua tiene errores 
     */
    public static final String COD_WS_PROCESO = "S107";
    /**
     * Codigo de error si no se encuentra la version sua
     */
    public static final String COD_VERSION_SUA = "S222";
    /**
     * Mensaje de error si no se encuentra la version SUA
     */
    public static final String MSG_VERSION_SUA = "La versión SUA no fue recibida y " +
    		"no se puede generar con los defaults IVRO";
    /**
     * Codigo de error que indica cuando se quiere calcular un recargo, pero no se envia la 
     * fecha del periodo en el cual se debe calcular
     */
    public static final String COD_FECHA_RECARGO = "S233";
    /**
     * Mensaje de error que indica cuando se quiere calcular un recargo, pero no se envia la 
     * fecha del periodo en el cual se debe calcular
     */
    public static final String MSG_FECHA_RECARGO = "Sin el periodo a pagar no se pueden calcular recargos";
    /**
     * Codigo de error que indica cuando hay errores el a serilizacion con jaxb
     */
    public static final String COD_JAXB = "S333";
    /**
     * Mensaje de error que indica cuando hay errores en la serializacion de objetos con jaxb
     */
    public static final String MSG_JAXB = "Errores al generar los detalles";
    
    /**
     * Codigo de error que indica cuando no se recibio el numero de registro patronal y es requerido
     */
    public static final String COD_NRP_NULL = "S234";
    /**
     * Mensaje de error que indica cuando cuando no se recibio el numero de registro patronal y es requerido
     */
    public static final String MSG_NRP_NULL = "El número de registro patronal es requerido.";
    /**
     * Codigo de error que indica cuando la cotizacion es nula para generar la compra
     */
    public static final String COD_COTIZACION_NOT_FOUND = "S235";
    /**
     * Mensaje de error que indica cuando la cotizacion es nula para generar la compra
     */
    public static final String MSG_COTIZACION_NOT_FOUND = "No existe cotización asociada al identificador.";
    
    /**
     * Codigo de error que indica cuando no se encuentra una compra
     */
    public static final String COD_COMPRA_NOT_FOUND = "S236";
    /**
     * Mensaje de error que indica cuando la compra no es encontrada
     */
    public static final String MSG_COMPRA_NOT_FOUND = "No existe compra asociada al identificador.";
    
    /**
     * Codigo de error que indica cuando no se encuentra un pago
     */
    public static final String COD_PAGO_NOT_FOUND = "S237";
    /**
     * Mensaje de error que indica cuando no se encuentra el pago
     */
    public static final String MSG_PAGO_NOT_FOUND = "No existe pago asociada al identificador.";
    
    /**
     * Codigo de error que indica cuando no se encuentra un pago para ser actualizado
     */
    public static final String COD_PAGO_NO_UPDATE = "S238";
    /**
     * Mensaje de error que indica cuando no se encuentra un pago para ser actualizado
     */
    public static final String MSG_PAGO_NO_UPDATE = "No existe identificador asociada al pago.";
    
    /**
     * Codigo de error que indica cuando no hay localidad asociada al patron
     */
    public static final String COD_NO_LOCALIDAD = "S239";
    /**
     * Mensaje de error que indica cuando no no hay localidad asociada al patron
     */
    public static final String MSG_NO_LOCALIDAD = "No existe información del municipio para el NRP asociado.";
    
    /**
     * Codigo de error que indica cuando no hay datos de calculo
     */
    public static final String COD_NO_DATOS_CALCULO = "S240";
    /**
     * Mensaje de error que indica cuando no no hay datos de calculo
     */
    public static final String MSG_NO_DATOS_CALCULO = "No puede ser nulo los datos del calculo.";
    
    /**
     * Codigo de error que indica cuando no hay datos de calculo
     */
    public static final String COD_NO_UMA = "S241";
    
    /**
     * mensaje de error si no se encentra registrado el salario UMA en BD
     */
    public static final String MSG_NO_UMA = 
            "No se encuentra el salario UMA para generar los cálculos";
    
    /**
     * Codigo de error que indica cuando no hay datos de calculo
     */
    public static final String COD_MULTIPLE_UMA = "S242";
    
    /**
     * mensaje de error si existe mas e un salario para realizar los calculos en la fecha indicada
     */
    public static final String MSG_MULTIPLE_UMA = 
            "Existe más de un salario UMA para realizar los cálculos";
}
