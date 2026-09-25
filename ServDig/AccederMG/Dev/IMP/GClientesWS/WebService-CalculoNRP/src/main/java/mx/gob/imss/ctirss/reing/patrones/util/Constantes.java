/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: Constantes.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.util
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.reing.patrones.util;


public final class Constantes {
	
	public static final Integer TIPO_PERSONA_MORAL = new Integer(1);
	public static final Integer TIPO_PERSONA_FISICA = new Integer(2);	
	
	public static final String MODALIDAD_32 = "32";
	public static final String ERROR_MODALIDAD_32 = "LA MODALIDAD DEL PATRON NO PUEDE SER 32 FAVOR DE VERIFICAR";
	
	public static final String ERROR_PATRON_ENCONTRADO_SSPA="LOS DATOS DEL PATRON YA EXISTEN EN EL SISTEMA FAVOR DE VERIFICARLOS";
	public static final String ERROR_PATRON_ENCONTRADO_SSPA_DIFERENTE_ACTIVIDAD="LOS DATOS DEL PATRON EXISTEN EN EL SISTEMA CON DIFERENTE ACTIVIDAD";	
	public static final String ERROR_PATRON_ENCONTRADO_RPC="EL PATRON TIENE ASIGNADO UN REGISTRO PATRONAL DE ESA CLASE";
	
	public static final Integer RPC = 1;
}
