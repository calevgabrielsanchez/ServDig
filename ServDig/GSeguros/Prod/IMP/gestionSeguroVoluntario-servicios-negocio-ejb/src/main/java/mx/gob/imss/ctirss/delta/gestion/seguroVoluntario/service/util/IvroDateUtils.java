/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Clase utilitaria para el manejo de fechas
 * @author NOVUTECK1
 *
 */
public abstract class IvroDateUtils {
    
    /**
     * Dias inahbiles por fin de semana
     */
    private static final int[] DIAS_INHABILES = {Calendar.SATURDAY, Calendar.SUNDAY};

    /**
     * Regresa la fecha que sea un dia habil mas proximo al recibido habiles de
     * lunes a viernes
     * 
     * @param fecha
     *            FEcha a validar o mover hasta que no se encuentre dentro de
     *            los dias inhabiles
     * @param diasFeriados
     *            lista de dias feriados por la ley
     * @return la fecha inmediata habial a la recibida (puede ser la misma)
     */
    public static final Date getFechaHabil(Calendar fecha, List<Date> diasFeriados) {
        Calendar nueva = (Calendar) fecha.clone();
        Date nuevaD = nueva.getTime();
        if (ArrayUtils.contains(DIAS_INHABILES, nueva.get(Calendar.DAY_OF_WEEK))
                || diaContenido(nueva.getTime(), diasFeriados)) {
            nueva.add(Calendar.DATE, -1);
            nuevaD = getFechaHabil(nueva, diasFeriados);
        }
        return nuevaD;
    }
    
    /**
     * Verifica si una fecha esta contenida dentro de una lista de dias
     * 
     * @param fecha
     *            LA fecha a verificar si se encuentra dentro de los dias
     *            feriados
     * @param diasFeriados
     *            lista con los dias feriados
     * @return true si la fecha es un dia feriado
     */
    private static boolean diaContenido(Date fecha, List<Date> diasFeriados) {
        boolean diaContenido = false;
        for (Date dia : diasFeriados) {
            if (DateUtils.isSameDay(fecha, dia)) {
                diaContenido = true;
                break;
            }
        }
        return diaContenido;
    }

    /**
     * Agrega o resta dias habiles a una fecha dependiendo los dias feriados recibidos.
     * @param fecha
     * @param diasFeriados
     * @param numeroDias
     * @return Devuelve la fecha resultante de los dias habiles agregados o disminuidos 
     */
    public static final Date agregaDiasHabiles(Calendar fecha, List<Date> diasFeriados,int numeroDias){
        Calendar nueva = (Calendar) fecha.clone();
        Integer dias = 0;
        if (numeroDias >= 0) {
            while (dias < numeroDias) {
                nueva.add(Calendar.DATE, 1);
                if (!ArrayUtils.contains(DIAS_INHABILES, nueva.get(Calendar.DAY_OF_WEEK))
                        || !diaContenido(nueva.getTime(), diasFeriados)) {
                    dias++;
                }
            }
        }else{
            while (dias > numeroDias) {
                nueva.add(Calendar.DATE, -1);
                if (!ArrayUtils.contains(DIAS_INHABILES, nueva.get(Calendar.DAY_OF_WEEK))
                        || !diaContenido(nueva.getTime(), diasFeriados)) {
                    dias--;
                }
            }
        }
        return nueva.getTime();
    }
    
}
