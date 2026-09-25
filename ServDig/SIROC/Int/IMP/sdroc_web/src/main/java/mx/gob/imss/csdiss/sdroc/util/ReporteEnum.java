/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.util;

/**
 * @author francisco.rodriguez
 *
 */
public enum ReporteEnum {
	
	//TIPO REPORTE
	ACUSE(17,"ACU"),
	REGISTRO(18, ""),

	//REGISTRO DE OBRAS
	REGISTRO_OBRA(1, "REGISTRO DE OBRA"),
	REGISTRO_OBRA_ACUSE(2, "ACUSE DE RECIBO-REGISTRO DE OBRA"),
	AVISO_UBICACION_OBRA(3, "AVISO DE UBICACION DE OBRA"),
	AVISO_UBICACION_OBRA_ACUSE(4, "ACUSE DE RECIBO-AVISO DE UBICACION DE OBRA"),
	
	//INCIDENCIAS
	ACTUALIZACION(5, "ACTUALIZACION"),
	ACTUALIZACION_ACUSE(6, "ACUSE DE RECIBO-ACTUALIZACION"),
	REPORTE_BIMESTRAL(7, "REPORTE BIMESTRAL"),
	REPORTE_BIMESTRAL_ACUSE(8, "ACUSE DE RECIBO-REPORTE BIMESTRAL"),
	SUSPENSION(9, "SUSPENSION"),
	SUSPENSION_ACUSE(10, "ACUSE DE RECIBO-SUSPENSIONL"),
	REANUDACION(11, "REANUDACION"),
	REANUDACION_ACUSE(12, "ACUSE DE RECIBO-REANUDACION"),
	CANCELACION(13, "CANCELACION"),
	CANCELACION_ACUSE(14, "ACUSE DE RECIBO-CANCELACION"),
	TERMINACION(15, "TERMINACION"),
	TERMINACION_ACUSE(16, "ACUSE DE RECIBO-TERMINACION"),
	
	//RESUMEN DE OBRA
	REUMEN_OBRA(17, "RESUMEN DE OBRA"),
	
	REGISTRO_PATRONAL(18, "REGISTRO PATRONAL")
    ;

	private int valor;
	private String nombre;

    /**
     * Constructor para la clase Reporte.
     * 
     * @param clave
     */
	ReporteEnum(int valor, String nombre) {
        this.valor = valor;
        this.nombre = nombre;
    }

    /**
     * Retorna el valor del Enumerado.
     * 
     * @return
     */
    public int getValor() {
        return valor;
    }
	
    /**
     * Retorna el nombre del reporte
     * @return
     */
    public String getNombre(){
    	return nombre;
    }
}
