/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 
 * Clase utilitaria para el manejo de fechas y operaciones comunes a los
 * periodos de calculo
 * 
 * @author NOVUTECK1
 * 
 */
public abstract class PeriodoUtil {

    /**
     * Obtiene una fecha con el ultimo dia del mes enviado
     * 
     * @param month
     *            Mes para genera la fecha
     * @param year
     *            año en el que se genera la fecha
     * @return LA fecha generada con el ultimo dia del mes recibido
     */
    public static final Calendar getUltimoDia(int month, int year) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(year, month, 1);
        fecha.add(Calendar.MONTH, 1);
        fecha.add(Calendar.DATE, -1);
        return fecha;
    }

    /**
     * Metodo utilitario para valida que una fecha este contenida en un rango de
     * fechas
     * 
     * @param fechaIni
     *            FEcha inicial del rango contenedor
     * @param fechaFin
     *            Fecha final del rango contenedor
     * @param fechaValidar
     *            FEcha a validar si se encuentra dento del rango de fechas
     * @return true si la fecha se encuentra dentro del rango de fechas
     *         contenedoras
     */
    public static final boolean isFechaContenida(Calendar fechaIni, Calendar fechaFin,
            Date fechaValidar) {

        Calendar fechaInicial = truncaFecha(fechaIni.getTime());
        Calendar fechaFinal = truncaFecha(fechaFin.getTime());
        Calendar fechaVal = truncaFecha(fechaValidar);

        return fechaVal.getTimeInMillis() >= fechaInicial.getTimeInMillis()
                && fechaVal.getTimeInMillis() <= fechaFinal.getTimeInMillis();
    }

    /**
     * Metodo privado que trunca una fecha, para solo obtener la fecha sin horas
     * , normalizandola en la misma zona horaria
     * 
     * @param fecha
     *            la fecha a truncar,
     * @return la fecha truncada y normalizada
     */
    public static final Calendar truncaFecha(Date fecha) {
        Calendar fechaT = Calendar.getInstance();
        fechaT.setTime((Date) fecha.clone());
        fechaT = normalizaFecha(fechaT);
        return DateUtils.truncate(fechaT, Calendar.DATE);
    }

    /**
     * Pone las fechas de un calendar a las cero horas
     * 
     * @param fecha
     *            la fecha a truncar
     * @return la fecha truncada
     */
    public static final Calendar truncaFecha(Calendar fecha) {
        return truncaFecha(((Calendar) fecha.clone()).getTime());
    }

    /**
     * Normaliza una fecha a la zona horaria default
     * 
     * @param fecha la fecha a normalizar
     * @return Regresa un nuevo Calendar con la fecha normalizada
     */
    public static final Calendar normalizaFecha(Calendar fecha) {
        Calendar fechaN = Calendar.getInstance();
        fechaN.set(Calendar.DAY_OF_MONTH, fecha.get(Calendar.DAY_OF_MONTH));
        fechaN.set(Calendar.MONTH, fecha.get(Calendar.MONTH));
        fechaN.set(Calendar.YEAR, fecha.get(Calendar.YEAR));
        return fechaN;
    }

    /**
     * Obtiene el numero de meses que transcurren entre dos fechas
     * 
     * @param fechaIni
     *            la fecha inicial del periodo de tiempo
     * @param fechaFin
     *            la final del periodo de tiempo
     * @return el numero de meses que transcurre en el periodo de tiempo
     *         indicado por las fechas.
     */
    public static final int numMeses(Calendar fechaIni, Calendar fechaFin) {
        Calendar fechaControl = (Calendar) fechaIni.clone();
        int meses = 1;
        while (fechaControl.get(Calendar.MONTH) != fechaFin.get(Calendar.MONTH)) {
            fechaControl.add(Calendar.MONTH, 1);
            meses++;
        }
        return meses;
    }

}
