/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

/**
 * Clase para las contantes a utilizar en IVRO
 * @author NOVUTECK1
 *
 */
public abstract class IvroConstants {
    
    /**
     * Codigo de error si no se encuentra fechas del seguro
     */
    public static final String COD_NO_FECHA_VENCIMIENTO = "IV100";
    /**
     * mensaje de error si no se encuentra fechas del seuro 
     */
    public static final String MSG_NO_FECHA_VENCIMIENTO = "El seguro no cuenta con fecha de vencimiento.";
    
    /**
     * Codigo de error si el seguro no se enuentra en periodo de renovacion
     */
    public static final String COD_NO_RENOVACON = "IV101";
    /**
     * mensaje de error si no se encuentra en periodo de renovacion 
     */
    public static final String MSG_NO_RENOVACON = "El seguro no se encuentra en periodo de renovación.";
    
    /**
     * Codigo de error si el seguro no se enuentra asociado a una compra
     */
    public static final String COD_NO_SEGURO_COMPRA = "IV102";
    /**
     * mensaje de error si no se encuentra asociado a una compra
     */
    public static final String MSG_NO_SEGURO_COMPRA = "El seguro no se encuentra asociado a una compra.";
    
    /**
     * Codigo de error si no se encuentra el patron asociado
     */
    public static final String COD_NO_PATRON = "IV103";
    /**
     * mensaje de error si no se encuentra el patron asociado
     */
    public static final String MSG_NO_PATRON = "El registro patronal no se encuentra.";
    
    /**
     * Codigo de error si no se encuentra el trabajador asociado
     */
    public static final String COD_NO_TRABAJADOR = "IV103";
    /**
     * mensaje de error si no se encuentra el trabajdor asociado
     */
    public static final String MSG_NO_TRABAJADOR = "El asegurado no fue encontrado.";
    
    /**
     * Codigo de error si el tramite no cuenta con
     */
    public static final String COD_TRAM_SIN_MODALIDAD = "IV104";
    /**
     * mensaje de error si el tramite no cuenta con
     */
    public static final String MSG_TRAM_SIN_MODALIDAD = "El trámite no tiene una modalidad asociada.";
    
    /**
     * Codigo de error si el tramite no cuenta con
     */
    public static final String COD_TRAM_SIN_COMPRA = "IV105";
    /**
     * mensaje de error si el tramite no cuenta con
     */
    public static final String MSG_TRAM_SIN_COMPRA = "El trámite no tiene una compra asociada.";
    /**
     * Codigo de error si el tramite no cuenta con
     */
    public static final String COD_TRAM_SIN_PERSONA = "IV106";
    /**
     * mensaje de error si el tramite no cuenta con
     */
    public static final String MSG_TRAM_SIN_PERSONA = "El trámite no tiene una persona asociada.";    

    /**
     * Codigo de error si el seguro no cuenta con cotizacion
     */
    public static final String COD_SEG_SIN_COTIZACION = "IV105";
    /**
     * mensaje de error si el seguro no cuenta con
     */
    public static final String MSG_SEG_SIN_COTIZACION = "El seguro no tiene una cotización asociada.";
}
