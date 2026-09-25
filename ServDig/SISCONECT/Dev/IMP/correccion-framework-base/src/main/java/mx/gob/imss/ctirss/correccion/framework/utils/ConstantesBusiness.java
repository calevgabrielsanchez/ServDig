package mx.gob.imss.ctirss.correccion.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Instituto Mexicano del Seguro Social
 *
 *@author Luis Enrique Gonzalez Hernandez - 04/Eenero/2012
 * Clase de utilerias basicas
 */
public class ConstantesBusiness {  
	
	/**Como esta clase contiene puras constantes y metodos estaticos es mejor evitar la instanciacion y la herencia*/
	private ConstantesBusiness(){}
	
	/**Enumeracion que nos sirve para representar los diferentes {@link Number} que se pueden utilizar en este caso SHORT, INTEGER, LONG, FLOAT, DOUBLE, BIGINT, BIGDECIMAL*/
	public static enum NumberTypes{
		SHORT, INTEGER, LONG, FLOAT, DOUBLE, BIGINT, BIGDECIMAL
	};

	/**Enumeracion que representa las operaciones aritmeticas basicas SUMA(+), RESTA(-), MULTIPLICACION(*) y DIVISION(/) */
	public static enum OperationTypes{
		SUMA, RESTA, DIVISION, MULTIPLICACION
	}
	
	/**Enumaeracion para tipos de dats a validar, se podria utilizar commons validatios pero bueno por ahora el modulo no requiere esto*/
	public static enum FielValidationTypes{
		FECHA, NUMERICO, REQUERIDO
	}
	
	public static final String RUTA_ARCHIVO_PROPIEDADES_CORRECCION = "/resources/text/";
	public static final String ARCHIVO_PROPIEDADES = "htmltext.properties";
	public static final int ESTATUS_SOLICITADA = 1;
	public static final int ESTATUS_APROBADA = 2;
	public static final int ESTATUS_RECHAZADO = 3;
	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	public static final String DD_MM_YY = "dd/MM/yy";
	public static final String dd_mm_yyyy = "dd-MM-yyyy";
	public static final int SOLICITUD_CORRECCION_INVITACION = 2;
	public static final int SOLICITUD_CORRECCION_ESPONTANEA = 1;
	public static final int PERIODO_GRACIA_PRORROGA = 10;
	public static final Integer FLUJO_PROMOCION = 2;
	public static final String LISTA_TIPOS_PROMOCION = "Promocion_FLUJO_2";
	public static final String LISTA_ORIGENES = "ORIGENES";
	public static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";
	
	public static final int DATA_TABLE_WITH_OUT_RESULT = 0;
	public static final String NO_ERRROR = "";
	public static final String EMPTY = "";
	public static final Integer EXHORTO_ORDINARIO = new Integer(6);
	public static final int USUARIO_INTERNET=1;
	public static final Integer ROL_CANCELA_PROMOCION=88;
	
	//public static final Long PROMOCION_CANCELADA = new Long(1L);
	/*public static final Long PROMOCION_ABIERTA = new Long(0L);
	
	public static final Long PROMOCION_AUTORIZADA_AVISO_DICT = new Long(2L);
	public static final Long PROMOCION_SOLICITUD_CORRECCION = new Long(3L);
	public static final Long PROMOCION_INVITACION = new Long(4L);*/
	
	public static final String ORIGEN_PROGRAMADO_NIVEL_CENTRAL = "2";
	
	public static final String TIPO_ORDINARIO = "5";
	public static final String TIPO_SALARIO_BASE_COTIZACION="9";
	
	
	public static final Integer SOLICITUD_UN_RP =1;
	public static final Integer SOLICITUD_VARIOS_RP =2;
	
	public static final BigDecimal TIPO_PAGO_RCV = BigDecimal.valueOf(1);
	public static final BigDecimal TIPO_PAGO_COP = BigDecimal.valueOf(2);
	public static final BigDecimal TIPO_PAGO_RCV_Y_COP = BigDecimal.valueOf(3);

	/**
	 * Indicador para el servicio de validacion del registro patronal
	 * PatronesServiceBean.validaRegistroPatronalWS(String, Long)
	 * Con este valor no verifica si el registro patronal
	 * pertenece a la subdelegacion del usuario en sesion.
	 */
	public static final Long NO_VALIDAR_SUBDELEGACION = -3L;
	
	/**
	 * Tipo de pagos usados para el seguimiento de la correccion , 
	 * cedula de Recepcian.
	 * 1. PAGOS POR AUTODETERMINACION
	 */
	public  static final Integer TIPO_PAGO_RECEPCION_AUTODETERMINACION= new Integer(1);
	/**
	 * Tipo de pagos usados para el seguimiento de la correccion , 
	 * Cedula de validacian
	 * 2. PAGOS POR REVISION
	 */
	public  static final Integer TIPO_PAGO_REVISION= new Integer(2);
	 
	
	/**Formato de fecha que se utiliza por default en el sistema SICONET DIA/MES/AaO ej. 30/01/2099, 
	 * en este caso todas las cadenas que tengan este formato y sean validas podran ser convertidas a
	 * un objeto {@link Date}*/
	private static DateFormat dateFormat = new SimpleDateFormat(ConstantesBusiness.DD_MM_YYYY);
	
	/**
	 * Estatus para la revision de la cedula
	 * 
	 */
	public static final int ESTATUS_REV_AUTO_REV = 1;
	public static final int ESTATUS_REV_RECHAZADA = 0;
	public static final int ESTATUS_REV_AUTORIZADA = 2;
	public static final int ESTATUS_CORR_CONC_RAZONABLE_=12;
	
	//Correccian concluida con parametro razonable
	/**
	 * Estatus para la validacion de la cedula
	 * 
	 */
	public static final int ESTATUS_REV_VAL_FINALIZADO_AUDITOR=1;
	public static final int ESTATUS_REV_VAL_RECHAZADO_SUPERV=0;
	public static final int ESTATUS_REV_VAL_AUTORIZADO_SUPERV=2;
	
	
	public static final int NIVEL_SECCION_A_CEDULA_VALIDACION=1;
	public static final int NIVEL_SECCION_B_CEDULA_VALIDACION=2;
	
	
	/**
	 * Claves referentes a los tipos de roles de usuario
	 * 
	 * 
	 */
	public static final int ROL_AUDITOR=2;
	
	
	public static final int JEFE_OF_CORRECCION=4;
	public static final int ROL_JEFE_OFICINA_CORRECCION_Y_DICTAMEN=6;
	public static final int ROL_JEFE_DEPARTAMENTO_AUDITORIA_A_PATRONES=7;
	public static final int ROL_SUPERVISOR_DELEG_AUDIT_PATRON=11;	
	public static final int ROL_SUPERVISOR_OFICINA_CORRECCION=10;
	public static final int ROL_USER_INTERNET = 5;

	//Invitacion
	
	public static final int TIPO_CORRECCION_INVITACION_CI=11;
	public static final int TIPO_CORRECCION_INVITACION_CCI=10;
	
	
	public static final Long TIPO_INVITACION_CI=4l;
	public static final Long TIPO_INVITACION_CCI=2l;
	
	
	
	public static final long ESTATUS_SIN_OPERACION=5;
	public static final long ESTATUS_EN_PROCESO=6;
	/**
	 * Constante para la clave del indicador de
	 * vigencia del funcionario 
	 */		
	public static final int INDICADOR_FUNCIONARIOVIGENTE=1;
	
	/**
	 * Metodo que convierte un objeto {@link Date} a una cadena aplicando un patron de formato.
	 * @param fecha a convertir
	 * @param pattern ej. dd/MM/yyyy
	 * @return cadena que representa la fecha.
	 */
	public static String dateToStringFormat(Date fecha, String pattern){
		if(fecha == null)
			return null;
		
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String stringDate = formatter.format(fecha);
		return stringDate;
	}
	
	/**
	 * Metodo que convierte un {@link StackTraceElement} a cadena, en algunas veces el Stack Trace es demasiado grande entonces solo tomaremos
	 * el 25% de la lineas cuando el Stack Trace sea mayor a 7,000 lineas.
	 * @param ex
	 * @return cadena
	 */
	public static String fromStackTraceToString(Throwable ex){
		String stackStr = "";
		try{
			int factor = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			ex.printStackTrace(ps);
			stackStr = baos.toString();
			if(stackStr.length() > 7000){
				factor = (int)(stackStr.length() * 0.25f);
				stackStr = stackStr.substring(0, factor);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return stackStr;
	}
	
	/**
	 * Metodo que convierte cualquier {@link Number} a una cadena
	 * @param valor Numero a convertir en cadena
	 * @param type Tipo de numero a convertir, este tipo esta representado por la enumeracion {@link NumberTypes}
	 * @return Cadena o ZERO si es null el numero a convertir en cadena
	 */
	public static String numberToString(Number valor, NumberTypes type){
		String strVal = "";
		
		if(valor == null){
			return "0";
		}
		
		switch (type) {
		case SHORT:
			strVal = Short.toString((Short) valor);
			break;
		case INTEGER:
			strVal = Integer.toString((Integer) valor);
			break;
		case LONG:
			strVal = Long.toString((Long) valor);
			break;
		case FLOAT:
			strVal = Float.toString((Float) valor);
			break;
		case DOUBLE:
			strVal = Double.toString((Double) valor);
			break;
		case BIGINT:
			strVal = ((BigInteger)valor).toString();
			break;
		case BIGDECIMAL:
			strVal = ((BigDecimal)valor).toString();
			break;
		default:
			strVal = "0";
			break;
		}
		
		return strVal;
	}
	
	/**
	 * Metodo que hace un operacion aritmetica sobre un grupo de {@link BigDecimal}, para poder saber que operacion
	 * se va aplicar es necesario utilizar la enumeracion {@link OperationTypes}.
	 * @param operation Tipo de operacion (+, -, *, /) a aplicar sobre el arreglo de {@link BigDecimal}
	 * @param bigDecimals Datos numericos
	 * @return Una cadena que representa la SUMA, RESTA, MULTIPLICACION a DIVISION del grupo de {@link BigDecimal}
	 */
	public static String numberToString(OperationTypes operation, BigDecimal... bigDecimals){
		BigDecimal result = new BigDecimal("0.0");
		for(BigDecimal big : bigDecimals){
			if(big != null){
				switch (operation) {
					case SUMA:
						result = result.add(big);
						break;
					case RESTA:
						result = result.subtract(big);
						break;
					case MULTIPLICACION:
						result = result.multiply(big);
						break;
					case DIVISION:
						result = result.divide(big);
						break;
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * <p>Convierte una cadena a un Date, la cadena tiene que tener un formato valido para poder ser parseada a una Fecha.</p>
	 * @param date Cadena que representa a una fecha 
	 * @return fecha objeto java.util.Date
	 */
	public static Date stringToDate(String date){
		Date fecha = null;
		try {
			fecha = ConstantesBusiness.dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fecha;
	}
	
	/**
     * <p>Verifica si dos fechas son corrrespondientes en el mismo dia se ignora el tiempo</p>
     * @param date1 fecha 1
     * @param date2 fecha 2
     * @return true si las dos fechas tienen el mismo dia.
     * @throws IllegalArgumentException si cualquier fecha es <code>null</code>
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }
    
    /**
     * <p>Verifica si dos fechas son corrrespondientes en el mismo dia</p>
     * @param cal1 Calendario 1
     * @param cal2 Calendario 2
     * @return true si las dos fechas tienen el mismo dia.
     * @throws IllegalArgumentException si cualquier fecha es <code>null</code>
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * 
     * @param valCell
     * @return
     */
	public static boolean isSpace(String valCell) {
		boolean isSpace = false;
		
		if(valCell == null){
			return false;
		}
		
		char[] chars = valCell.toCharArray();
		
		if(chars.length <= 0){
			return true;
		}
		
		for(char caracter :  chars){
			if(Character.isSpaceChar(caracter)){
				isSpace = true;
			}else if(Character.isLetterOrDigit(caracter)){
				isSpace = false;
			}
		}
		return isSpace;
	}
	
	public static boolean isNumber(String str) {
		int dot = 0; 
		if (str == null || str.length() == 0)
			return false;

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
			if(str.charAt(i) == '.'){
				dot++;
			}
		}
		
		if(dot > 1){
			return false;
		}
		
		return true;
	}
	
	public static boolean isZero(String str){
		Integer dato = Integer.parseInt(str);
		if(dato.intValue() == 0){
			return true;
		}else{
			return false;
		}
	}

}