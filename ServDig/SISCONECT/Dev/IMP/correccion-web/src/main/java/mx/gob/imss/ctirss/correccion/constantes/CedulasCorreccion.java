package mx.gob.imss.ctirss.correccion.constantes;

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
	 * Nombre del archivo XLS correspondiente 320-009-128_Desglose_balanza_compro_auxNominas
	 */
	public static final String CEDULA_A = "CEDULA_A.xlsm";
	/**
	 * Nombre del archivo XLS correspondiente 9320-009-129_COP_pagadas
	 */
	public static final String CEDULA_G = "CEDULA_G.xlsm";
	/**
	 * Nombre del archivo XLS correspondiente 9320-009-130_Prueba_selectiva_de_trabajadores
	 */
	public static final String CEDULA_H = "CEDULA_H.xlsm";
	/**
	 * ¼ Nombre del archivo XLS correspondiente 9320-009-131_Excedentes_de_salarios_topados
	 */
	public static final String CEDULA_I = "CEDULA_I.xlsm";
	/**
	 * Nombre del archivo XLS correspondiente 9320-009-132_Analisis_de_tiempo_extra
	 */
	public static final String CEDULA_O = "CEDULA_O.xlsm";
	/**
	 * Nombre del archivo XLS correspondiente 9320-009-133_Analisis_de_honorarios
	 */
	public static final String CEDULA_Q = "CEDULA_Q.xlsm";
	/**
	 * Nombre del archivo XLS correspondiente a la cedula Detalle Trabajador
	 */
	public static final String CEDULA_DETALLE_TRABAJADORES = "DETALLE_TRABAJADORES.xlsm";
	/**
	 * Nombre del archivo XLS correspondiente a la Detalle de Pagos
	 */
	public static final String CEDULA_COP_PAGADA = "COP_PAGADAS.xlsm";

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
	 * Mensaje de error al momento de descargar cédulas
	 */
	
	public static final String NO_EXISTE_FOLIO_PERIODO = "El Folio ingresado es inválido para el periodo ingresado";
	public static final String NO_EXISTE_PATRONES_PERIODO = "El Folio ingresado no contiene patrones asocioados al periodo";
	public static final String NO_EXISTE_CATEGORIAS_RP = "El Folio ingresado no contiene categorias asocioados al patron";
	public static final String NO_EXISTE_PERCEPCIONES = "El catálogo de percepciones deberá ser ingresado previo a la descarga de esta cédula";
	public static final String NO_EXISTE_GASTOS = "El catálogo de gastos deberá ser ingresado previo a la descarga de esta cédula";
	public static final String NO_EXISTE_MESES = "El catálogo de meses contiene un error favor de avisar al personal adm del IMSS";
	public static final String NO_EXISTE_TRABAJADORES = "No existen trabajadores asociados a la cédula, la cédula no puede ser descargada";
	public static final String NO_EXISTE_TRABAJADORES_DETALLE = "No existen detalle de trabajadores asociados a la cédula, la cédula no puede ser descargada";

}
