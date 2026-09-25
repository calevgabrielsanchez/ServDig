package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;

public class UtilRTT {

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
}
