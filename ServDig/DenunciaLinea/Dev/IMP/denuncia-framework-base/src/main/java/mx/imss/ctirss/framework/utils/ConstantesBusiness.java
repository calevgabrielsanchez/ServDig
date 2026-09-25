package mx.imss.ctirss.framework.utils;

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
 * @author Luis Enrique Gonzalez Hernandez - 04/Eenero/2012
 * 
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
	
	public static final String RUTA_ARCHIVO_PROPIEDADES_CORRECCION = "resources/text/";
	public static final String ARCHIVO_PROPIEDADES = "htmltext.properties";
	public static final int ESTATUS_SOLICITADA = 1;
	public static final int ESTATUS_APROBADA = 2;
	public static final int ESTATUS_RECHAZADO = 3;
	public static final String DD_MM_YYYY = "dd-MM-yyyy";	
	public static final String FORMATO_FECHA_FOLIO="ddMMyyHHmm";
	public static final String DD_MM_YYYY_DIAG = "dd/MM/yyyy";
	public static final String dd_MM_yyyy_HH_mm_ss = "dd/MM/yyyy HH:mm:ss";
	public static final int SOLICITUD_CORRECCION_INVITACION = 2;
	public static final int SOLICITUD_CORRECCION_ESPONTANEA = 1;
	public static final int PERIODO_GRACIA_PRORROGA = 10;
	public static final Integer FLUJO_PROMOCION = 2;
	public static final String LISTA_TIPOS_PROMOCION = "Promocion_FLUJO_2";
	public static final String LISTA_ORIGENES = "ORIGENES";
	public static final String CONTENT_TYPE_XLS = "application/vnd.ms-excel";
	public static final String PREFIJO_FOLIOS_DENUNCIA = "DEN";
	public static final String PREFIJO_FOLIOS_SUB_DENUNCIA = "S";
	public static final String MOTIVOS_DENUNCIA_NO_AFILIADO = "1";
	public static final String MOTIVOS_DENUNCIA_AFILIADO_POST = "2";
	public static final String MOTIVOS_DENUNCIA_AFILIADO_SAL_MENOR = "3";
	public static final String MOTIVOS_DENUNCIA_SIN_AVISO_BAJA = "4";
	public static final String MOTIVOS_DENUNCIA_MULTIPLES = "5";
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public final static long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
	public static final int DATA_TABLE_WITH_OUT_RESULT = 0;
	public static final String NO_ERRROR = "";
	public static final String EMPTY = "";
	public static final Integer EXHORTO_ORDINARIO = new Integer(6);
	
	public static final Long PROMOCION_ABIERTA = new Long(0L);
	public static final Long PROMOCION_FINALIZADA = new Long(1L);
	
	
	//Estatus de las denuncias
	public static final Long ESTATUS_EN_ELABORACION = new Long(1L);
	public static final Long ENVIADA_PARA_RATIFICACION = new Long(2L);
	public static final Long EXPIRADA_POR_NO_ENVIO = new Long(3L);
	public static final Long ESTATUS_RATIFICADA = new Long(4L);	
	public static final Long NO_RATIFICADA = new Long(5L);
	
	public static final int TRABAJADOR = 1;
	public static final int BENEFICIARIO = 2;
	public static final int REPRESENTANTE = 3;
	
	public static final int SU_PATRON_NO_LO_AFILIO_AL_SEGURO_SOCIAL = 1;
	public static final int SU_PATRON_LO_AFILIO_CON_FECHA_POSTERIOR_AL_INGRESO = 2;
	public static final int SU_PATRON_LO_AFILIO_CON_UN_SALARIO_INFERIOR_AL_REA = 3;
	public static final int EL_PATRON_SE_NIEGA_A_PRESENTAR_EL_AVISO_DE_BAJA_DE = 4;
	
	
	public static final Long TIPO_DE_PERIODO_PAGO = 1l;
	public static final Long TIPO_DE_COMPROBANTE_PAGO = 2l;
	public static final Long TIPO_DE_FORMA_PAGO = 3l;
	public static final Long TIPO_FORMA_PAGO_OTRO = 17L;


	public static final int CONCEPTO_TIPO_DE_PERIODO_PAGO = 1;
	public static final int CONCEPTO_TIPO_DE_COMPROBANTE_PAGO = 2;
	public static final int CONCEPTO_TIPO_DE_FORMA_PAGO = 3;
	
	
	public static final int TIPO_USUARIO_DENUNCIA=1;
	public static final int TIPO_USUARIO_SUB=2;
	/**Formato de fecha que se utiliza por default en el sistema SICONET DIA/MES/A�O ej. 30/01/2099, 
	 * en este caso todas las cadenas que tengan este formato y sean validas podran ser convertidas a
	 * un objeto {@link Date}*/
	private static DateFormat dateFormat = new SimpleDateFormat(ConstantesBusiness.DD_MM_YYYY);
	
	public static String EXTENSIONES_ARCHIVOS="exe,bat,doc,xls,bin,jar,bat,mp3,com,dll";
	
	public static int TAM_ARCHIVO=1024*1000;
	
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
	 * @return Una cadena que representa la SUMA, RESTA, MULTIPLICACION � DIVISION del grupo de {@link BigDecimal}
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