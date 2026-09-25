package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;

public class UtilRTT {
    private static final int PERIODOHISTORIAL = 5;

    public static String obtenerPeriodoHistorial(Date fechaInicio, Date fechaFinal, OrigenSolicitudEnum origen) {
        Locale locMx = new Locale("es", "MX");
        SimpleDateFormat formatInicio;
        if (origen.equals(OrigenSolicitudEnum.VENTANILLA)) {
            formatInicio = new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", locMx);
        } else {
            formatInicio = new SimpleDateFormat("dd' de 'MMMM", locMx);
        }
        SimpleDateFormat formatFin = new SimpleDateFormat("' al 'dd' de 'MMMM' del 'yyyy", locMx);
        
        return formatInicio.format(fechaInicio) + formatFin.format(fechaFinal);
    }

    
    public static Date obtenerFechaInicioHistorial(RiesgoTrabajo riesgo){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(riesgo.getFechaAccidente());
        calendar.set(calendar.get(calendar.YEAR),0, 1);
        return calendar.getTime();
    }
    
    
    public static String obtenerFecha(Date fechaInicio) {
        Locale locMx = new Locale("es", "MX");
        SimpleDateFormat formatInicio;
        formatInicio = new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", locMx);
        return formatInicio.format(fechaInicio);
    }

    public static List<Date> obtenerPeriodoCorrespondiente(OrigenSolicitudEnum origen, Integer periodo) {

        Locale locale = new Locale("es", "MX");
        Calendar fechaActual = Calendar.getInstance(locale);
        Date fechaFin;
        Date fechaInicio;

        if (periodo == null || periodo == 0) {
            if (fechaActual.get(Calendar.MONTH) <= 2) {
                //Si la fecha actual es febrero o enero el periodo es del a?o pasado
                fechaActual.set(fechaActual.get(Calendar.YEAR) - 1, Calendar.DECEMBER, 31, 23, 59, 59);
                fechaFin = fechaActual.getTime();
            } else {
                //El fin de periodo es el ultimo dia del mes pasado
                fechaActual.set(fechaActual.get(Calendar.YEAR), fechaActual.get(Calendar.MONTH) - 1, fechaActual.get(Calendar.DATE));
                fechaActual.set(fechaActual.get(Calendar.YEAR), fechaActual.get(Calendar.MONTH), fechaActual.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                fechaFin = fechaActual.getTime();
            }

            if (origen.equals(OrigenSolicitudEnum.INTERNET)) {
                //Si el origen de la solicitud es internet el inicio de periodo es enero
                //del periodo actual
                fechaActual.set(fechaActual.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
                fechaInicio = fechaActual.getTime();
            } else {
                //Si el origen de la solicitud es ventanilla el inicio de periodo es enero
                //de 5 a?os atras
                fechaActual.set(fechaActual.get(Calendar.YEAR) - PERIODOHISTORIAL, Calendar.JANUARY, 1, 0, 0, 0);
                fechaInicio = fechaActual.getTime();
            }
        } else {
            boolean anioActual = fechaActual.get(Calendar.YEAR) == periodo;
            Integer periodoInicio = periodo;


            if (fechaActual.get(Calendar.MONTH) <= 2 && anioActual) {
                //Si la fecha actual es febrero o enero el periodo es del a?o pasado
                fechaActual.set(fechaActual.get(Calendar.YEAR) - 1, Calendar.DECEMBER, 31, 23, 59, 59);
                periodoInicio = periodo - 1;
                fechaFin = fechaActual.getTime();
            } else {
                //El fin de periodo es el ultimo dia del mes pasado
                fechaActual.set(periodo, Calendar.DECEMBER, 31, 23, 59, 59);
                fechaFin = fechaActual.getTime();
            }
            //Si el origen de la solicitud es ventanilla el inicio de periodo es enero
            //de 5 a?os atras
            fechaActual.set(periodoInicio, Calendar.JANUARY, 1, 0, 0, 0);
            fechaInicio = fechaActual.getTime();
        }

        //Seteamos las fechas a la lista
        List<Date> listaFechas = new ArrayList<Date>();
        listaFechas.add(fechaInicio);
        listaFechas.add(fechaFin);

        return listaFechas;
    }

    /**
     * Obtener las el año de siniestralidad
     *
     * @return
     */
    public static Date obtenerFechaSiniestralidad() {
        Locale locale = new Locale("es", "MX");
        Calendar fechaCalHoy = Calendar.getInstance(locale);
        Calendar fechaCalCorte = Calendar.getInstance(locale);
        Date fechaSiniestralidad = new Date();
        fechaCalCorte.set(fechaCalHoy.get(Calendar.YEAR), Calendar.MARCH, 01);

        if (fechaCalCorte.getTime().after(fechaCalHoy.getTime())){
            fechaCalHoy.set(fechaCalHoy.get(Calendar.YEAR) - 1, fechaCalHoy.get(Calendar.MONTH), fechaCalHoy.get(Calendar.DAY_OF_MONTH));
            fechaSiniestralidad = fechaCalHoy.getTime();
        }

        return fechaSiniestralidad;
    }
}
