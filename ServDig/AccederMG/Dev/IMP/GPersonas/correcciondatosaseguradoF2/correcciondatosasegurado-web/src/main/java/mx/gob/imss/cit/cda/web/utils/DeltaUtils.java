package mx.gob.imss.cit.cda.web.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class DeltaUtils {

    private static final String FORMATO_FECHA_COMPLETO = "dd/MM/yyyy kk:mm:ss";

    private final Logger log = LoggerFactory.getLogger(DeltaUtils.class);

    public Integer generaDigitoVerificador(String nss) {
        int suma = 0;
        int resultado = 0;
        for (int i = nss.length() - 1; i >= 1; i--) {
            if (i % 2 == 0) {
                int multiplicacion = (Integer.parseInt(nss.charAt(i - 1) + "")) * 2;
                if (multiplicacion > 9) {
                    suma = suma + ((multiplicacion - 10) + 1);
                } else {
                    suma = suma + multiplicacion;
                }
            } else {
                suma = suma + (Integer.parseInt(nss.charAt(i - 1) + ""));
            }
        }
        int modulo = suma % 10;
        if (modulo == 0) {
            resultado = 0;
        } else if (modulo < 10) {
            resultado = 10 - modulo;
        }
        return resultado;
    }

    public String convertirDateToString(Date fecha) {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String fechaString = df.format(fecha);

        return fechaString;

    }

    public Date convertirStringToDateMask(String date, String mask) {
        DateFormat df = new SimpleDateFormat(
                StringUtils.isNotBlank(mask) ? mask : FORMATO_FECHA_COMPLETO);
        Date dateAux = null;
        try {
            dateAux = df.parse(date);
        } catch (ParseException e) {
            log.error("Ocurrio un error con el parseo {}", e);
            dateAux = new Date();
        }
        return dateAux;
    }

    public Date convertirStringTodateCompleto(String date) {

        DateFormat df = new SimpleDateFormat(FORMATO_FECHA_COMPLETO);
        Date dateAux = null;
        try {
            dateAux = df.parse(date);
        } catch (ParseException e) {
            log.error("Ocurrio un error con el parseo {}", e);
            dateAux = new Date();
        }
        return dateAux;

    }

    public Integer diasLaborablesEntreFechas(Date fechaInicio, Date fechaFin) {
        Calendar calendarInicio = Calendar.getInstance();
        calendarInicio.setTime(fechaInicio);
        calendarInicio.add(Calendar.DAY_OF_MONTH, 1);

        Calendar calendarFin = Calendar.getInstance();
        calendarFin.setTime(fechaFin);

        int workDays = 0;
        if (calendarInicio.get(Calendar.DAY_OF_YEAR) == calendarFin
                .get(Calendar.DAY_OF_YEAR)) {
            return 0;
        }

        while (calendarInicio.get(Calendar.DAY_OF_YEAR) < calendarFin
                .get(Calendar.DAY_OF_YEAR)) {
            calendarInicio.add(Calendar.DAY_OF_MONTH, 1);
            if (calendarInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                    && calendarInicio.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        }

        return workDays;
    }

    public String convertirDateToStringCompleto(Date fecha) {

        DateFormat df = new SimpleDateFormat(FORMATO_FECHA_COMPLETO);

        String fechaString = df.format(fecha);

        return fechaString;

    }

    public String cambiarFormatoFecha(String formatoInicio, String fecha,
            String formatoFinal) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(formatoInicio);
        Date fechaFormat = sdf.parse(fecha);
        sdf.applyPattern(formatoFinal);
        return sdf.format(fechaFormat);

    }

    public static boolean validarInformacionDomicilio(Object object) {
        boolean informacionDomicilio = false;
        if (object instanceof List<?>) {
            informacionDomicilio = !((List<?>) object).isEmpty();
        }
        return informacionDomicilio;
    }

}
