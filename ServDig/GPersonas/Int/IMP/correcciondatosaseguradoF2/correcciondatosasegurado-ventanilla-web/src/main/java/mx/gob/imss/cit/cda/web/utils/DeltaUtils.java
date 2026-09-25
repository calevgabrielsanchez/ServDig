package mx.gob.imss.cit.cda.web.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DeltaUtils {

    private static final String FORMATO_FECHA_COMPLETO = "dd/MM/yyyy kk:mm:ss";

    private static final String FORMATO_FECHA = "dd/MM/yyyy";

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

    public String convertirDateToStringMask(Date fecha, String mask) {

        DateFormat df = new SimpleDateFormat(
                StringUtils.isNotBlank(mask) ? mask : FORMATO_FECHA_COMPLETO);

        String fechaString = df.format(fecha != null ? fecha : new Date());

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
        }
        return dateAux;
    }

    public Date convertirStringTodate(String date) {
        Date dateFormat = convertirStringToDateMask(date,
                FORMATO_FECHA_COMPLETO);
        return dateFormat == null ? convertirStringToDateMask(date,
                FORMATO_FECHA) : dateFormat;
    }

    public static boolean validarDocumentosBeneficiario(Object object) {
        boolean documentosBeneficiario = false;
        if (object instanceof List<?>) {
            for (DocumentoProbatorio documentoProbatorio : (List<DocumentoProbatorio>) object) {
                documentosBeneficiario = documentoProbatorio
                        .getNomNombreDocumento().startsWith("CDA_PI_");
            }
        }

        return documentosBeneficiario;
    }

    public static boolean validarInformacionDomicilio(Object object) {
        boolean informacionDomicilio = false;
        if (object instanceof List<?>) {
            informacionDomicilio = !((List<?>) object).isEmpty();
        }
        return informacionDomicilio;
    }

    public String convertirDateToStringCompleto(Date fecha) {

        DateFormat df = new SimpleDateFormat(FORMATO_FECHA_COMPLETO);

        String fechaString = df.format(fecha);

        return fechaString;

    }

    public String fechaSolicitudBandeja(Date fecha) {

        Calendar fechaCalendario = Calendar.getInstance();
        fechaCalendario.setTime(fecha);

        int dia = fechaCalendario.get(Calendar.DAY_OF_MONTH);
        int mes = fechaCalendario.get(Calendar.MONTH) + 1;
        int anio = fechaCalendario.get(Calendar.YEAR);

        String fechaSolicitudBandeja = String.format("%02d", dia) + "/"
                + String.format("%02d", mes) + "/" + Integer.toString(anio);

        return fechaSolicitudBandeja;
    }

    public String cambiarFormatoFecha(String formatoInicio, String fecha,
            String formatoFinal) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(formatoInicio);
        Date fechaFormat = sdf.parse(fecha);
        sdf.applyPattern(formatoFinal);
        return sdf.format(fechaFormat);

    }
}
