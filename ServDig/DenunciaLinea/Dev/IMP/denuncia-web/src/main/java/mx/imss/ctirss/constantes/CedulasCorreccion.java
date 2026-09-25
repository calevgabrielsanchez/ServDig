package mx.imss.ctirss.constantes;

import java.io.File;

/**
 * Consntates para la identificar los archivos de las cedulas.
 * @author Marco A Nieto Plett
 *
 */
public abstract class CedulasCorreccion {

	/**
	 * Ruta en donde se encuentran todas las cedulas de la correccion
	 */
	public static final String RUTA_CEDULAS = "resources"+File.separator+"archivos"+File.separator+"cedulasConstruccion"+File.separator;

	/**
	 * Nombre del archivo XLS correspondiente a la cedula A
	 */
	public static final String CEDULA_A = "9320-009-128_Desglose_balanza_compro_auxNominas.xls";
	/**
	 * Nombre del archivo XLS correspondiente a la cedula G
	 */
	public static final String CEDULA_G = "9320-009-129_COP_pagadas.xls";
	/**
	 * Nombre del archivo XLS correspondiente a la cedula H
	 */
	public static final String CEDULA_H = "9320-009-130_Prueba_selectiva_de_trabajadores.xls";
	/**
	 * � Nombre del archivo XLS correspondiente a la cedula I
	 */
	public static final String CEDULA_I = "9320-009-131_Excedentes_de_salarios_topados.xls";
	/**
	 * Nombre del archivo XLS correspondiente a la cedula O
	 */
	public static final String CEDULA_O = "9320-009-132_Analisis_de_tiempo_extra.xls";
	/**
	 * Nombre del archivo XLS correspondiente a la cedula Q
	 */
	public static final String CEDULA_Q = "9320-009-133_Analisis_de_honorarios.xls";
	/**
	 * Nombre del archivo XLS correspondiente a la cedula Detalle Trabajador
	 */
	public static final String CEDULA_DETALLE_TRABAJADORES = "Trabajadores_Cedulas.xls";
	/**
	 * Nombre del archivo XLS correspondiente a la cedula Detalle Trabajador
	 */
	public static final String CEDULA_COP_PAGADA = "Cop_Pagadas.xls";

	/**
	 * ID de la cedula que se desea descargar
	 */
	public static final int ID_CEDULA_A = 1;
	/**
	 * ID de la cedula que se desea descargar
	 */
	public static final int ID_CEDULA_G = 2;
	/**
	 * ID de la cedula que se desea descargar
	 */
	public static final int ID_CEDULA_H = 3;
	/**
	 * ID de la cedula que se desea descargar
	 */
	public static final int ID_CEDULA_I = 4;
	/**
	 * ID de la cedula que se desea descargar
	 */
	public static final int ID_CEDULA_O = 5;
	/**
	 * ID de la cedula que se desea descargar
	 */
	public static final int ID_CEDULA_Q = 6;

	/**
	 * ID XLS detalle de trabajadores
	 */
	public static final int ID_DETALLE_TRABAJADORES = 7;
	
	/**
	 * ID XLS COPS PAGADAS
	 */
	public static final int ID_COP_PAGADA = 8;
	
	/**
	 * Nombre de la hoja (libro) de control de la cedula A
	 */
	public static final String CONTROL_SHEET_CEDULA = "Prep";

	/**
	 * Posicion en donde se encuentra las elementos de seguridad del archivo XLS
	 * correspondiente, aplica a todas las cedulas
	 */
	public static final String CELDA_CONTROL_SEGURIDAD_COLUMNA = "A";
	public static final Integer CELDA_CONTROL_SEGURIDAD_FILAL = 1;
	
	public static final String DT_HOJA_CONTROL = "TRABAJADORES-CEDULAS";
	public static final String COP_HOJA_CONTROL = "COP pagadas";
	
	/**
	 * Mensaje de error al momento de descargar c�dulas
	 */
	
	public static final String NO_EXISTE_FOLIO_PERIODO = "El Folio ingresado es inv�lido para el periodo ingresado";
	public static final String NO_EXISTE_PATRONES_PERIODO = "El Folio ingresado no contiene patrones asocioados al periodo";
	public static final String NO_EXISTE_PERCEPCIONES = "El cat�logo de percepciones deber� ser ingresado previo a la descarga de esta c�dula";
	public static final String NO_EXISTE_GASTOS = "El cat�logo de gastos deber� ser ingresado previo a la descarga de esta c�dula";
	public static final String NO_EXISTE_MESES = "El cat�logo de meses contiene un error favor de avisar al personal adm del IMSS";
	public static final String NO_EXISTE_TRABAJADORES = "No existen trabajadores asociados a la c�dula, la c�dula no puede ser descargada";
	public static final String NO_EXISTE_TRABAJADORES_DETALLE = "No existen detalle de trabajadores asociados a la c�dula, la c�dula no puede ser descargada";

}
