/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.DatosValidacion;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.PeriodoSUA;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoIssfEnum;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase utilitaria con operacion comunes en la generacion de un SUA
 *
 * @author NOVUTECK1
 *
 */
public abstract class SuaUtil {

    private final static int TIPO_TRABAJADOR = 1;
    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SuaUtil.class);
    /**
     * Filler de los documentos
     */
    public static final String FILLER = " ";
    /**
     * Dias inahbiles por fin de semana
     */
    private static final int[] DIAS_INHABILES = {Calendar.SATURDAY, Calendar.SUNDAY};
    /**
     * Dia del mes por defecto en el que se debe situar la fecha limite de pago
     * para la modalidad 40 CVRO
     */
    private static final int DIA_PAGO_MOD_40 = 17;
    /**
     * Indice a pertir el cual se toma el npr, esto para generar el folio sua
     */
    private static final int INDEX_NPR = 3;
    /**
     * Formato de fechas
     */
    private static final String DATE_FORMAT = "yyyyMMdd";
    /**
     * DAte format
     */
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT);

    /**
     * Valor de control para el folio
     */
    private static final BigDecimal CONTROL_FOLIO = new BigDecimal(1000000);
    /**
     * Valor de control para el folio maximo
     */
    private static final int FOLIO_MAX = 100000;

    /**
     * Verifica si una cadena es nula regresa vacio, si no es nula regresa el
     * valor recibido
     *
     * @param cadena valor a verificar que no sea nulo
     * @return vacio si la cadena es nula
     */
    public static final String getCadenaNoNula(String cadena) {
        return cadena == null ? "" : cadena;
    }

    /**
     * Obtiene la clave de una entidad a partir de una cadena convirtiendola en
     * un int
     *
     * @param clave la clave a convertir en int
     * @return el valor numerico de la clave
     */
    public static final int getClaveEntidad(String clave) {
        int claveInt = 0;
        try {
            claveInt = Integer.valueOf(clave);
        } catch (NumberFormatException e) {
            LOGGER.info("La clave de la entidad no es numerica   es = {}", clave);
        }
        return claveInt;
    }

    /**
     * Obtiene el numero de dias en un periodo de tiempo
     *
     * @param fechaIni fecha inicial del periodo
     * @param fechaFinal fecha final del periodo
     * @return el numero de dias entre las fechas ingresadas
     */
    public static final int getDiasPeriodo(Date fechaIni, Date fechaFinal) {
        Calendar calFechaIni = PeriodoUtil.truncaFecha(fechaIni);
        Calendar calFechaFinal = PeriodoUtil.truncaFecha(fechaFinal);

        long milisegFechaIni = calFechaIni.getTimeInMillis();
        long milisegFechaFin = calFechaFinal.getTimeInMillis();

        TimeZone timezoneFechaIni = calFechaIni.getTimeZone();
        TimeZone timezoneFechaFin = calFechaFinal.getTimeZone();

        int offsetFechaIni = timezoneFechaIni.getOffset(milisegFechaIni);
        int offsetFechaFin = timezoneFechaFin.getOffset(milisegFechaFin);

        LOGGER.debug("Fecha Ini {} -> {}", milisegFechaIni, calFechaIni);
        LOGGER.debug("Fecha Ini Offset {} -> {}", offsetFechaIni, milisegFechaIni - offsetFechaIni);
        LOGGER.debug("Fecha Fin {} -> {}", milisegFechaFin, calFechaFinal);
        LOGGER.debug("Fecha Fin Offset {} -> {}", offsetFechaFin, milisegFechaFin - offsetFechaFin);
        LOGGER.debug("Diferencia ms {}", milisegFechaFin - milisegFechaIni);

        long difMilisegundos = (milisegFechaFin + offsetFechaFin)
                - (milisegFechaIni + offsetFechaIni);
        LOGGER.debug("Diferencia CALCULADA ms {}", difMilisegundos);

        BigDecimal diferenciaMilisegundos = new BigDecimal(difMilisegundos);
        BigDecimal resultado = diferenciaMilisegundos.divide(new BigDecimal(
                DateUtils.MILLIS_PER_DAY), 0, RoundingMode.CEILING);

        resultado = resultado.add(BigDecimal.ONE);
        LOGGER.debug("Dias del periodo {}", resultado.intValue());
        return resultado.intValue();
    }

    /**
     * GEnera el valor del folio SUA
     *
     * @param periodoSUA Los datos para generar el folio
     * @return un folio random para el archivo SUA
     */
    public static final int generaFolioSUA(PeriodoSUA periodoSUA) {
        BigDecimal valor = new BigDecimal(periodoSUA.getPatron()
                .getRegistroPatronalIMSS().substring(INDEX_NPR));
        for (Trabajador trabajador : periodoSUA.getTrabajadores()) {
            valor = valor.add(new BigDecimal(trabajador.getNssTrabajador()));
        }
        valor = valor.add(new BigDecimal(FORMAT.format(periodoSUA.getFechaInicio().getTime())));
        valor = valor.add(new BigDecimal(FORMAT.format(periodoSUA.getFechaFin().getTime())));
        valor = valor.add(new BigDecimal(FORMAT.format(Calendar.getInstance().getTime())));
        valor = valor.divide(CONTROL_FOLIO);
        int folio = valor.remainder(BigDecimal.ONE).multiply(CONTROL_FOLIO).intValue();
        folio = folio < FOLIO_MAX ? (folio + FOLIO_MAX) : folio;
        return folio;
    }

    /**
     * Dada una cantidad numerica se codifica bajo su algoritmo de codificacion
     * BaseSua62
     *
     * @param valor la cantidad a ser codificada
     * @return la cantidad codificada en BaseSua62
     */
    public static final String codificaValores(BigDecimal valor) {
        BigDecimal cantidad = valor != null ? valor : BigDecimal.ZERO;
        return BaseSUA62.codifica(cantidad);
    }

    /**
     * Regresa la fecha que sea un dia habil mas proximo al recibido habiles de
     * lunes a viernes
     *
     * @param fecha FEcha a validar o mover hasta que no se encuentre dentro de
     * los dias inhabiles
     * @param diasFeriados lista de dias feriados por la ley
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
     * Regresa la fecha que sea un dia habil mas proximo al recibido, habiles de
     * lunes a viernes, en le caso de modalidad 40 CVRO considera la regla de
     * que si es viernes y es día DIA_PAGO_MOD_40(17 por defecto) se busca el
     * dia habil siguiente
     *
     * @param fecha Fecha a validar o mover hasta que no se encuentre dentro de
     * los dias inhabiles
     * @param datos diasFeriados: lista de dias feriados por la ley modalidad:
     * modalidad de la que se aquiere
     * @return la fecha inmediata habial a la recibida (puede ser la misma)
     */
    public static final Date getSiguienteFechaHabil(Calendar fecha, DatosValidacion datos) {
        Calendar nueva = (Calendar) fecha.clone();
        Date nuevaD = nueva.getTime();
        if (ArrayUtils.contains(DIAS_INHABILES, nueva.get(Calendar.DAY_OF_WEEK))
                || diaContenido(nueva.getTime(), datos.getDiasFeriados())
                || (datos.getModalidad() == ModalidadEnum.CUARENTA.getId()
                && nueva.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
            nueva.add(Calendar.DATE, 1);
            nuevaD = getSiguienteFechaHabil(nueva, datos);
        }
        return nuevaD;
    }

    /**
     * Verifica si una fecha esta contenida dentro de una lista de dias
     *
     * @param fecha LA fecha a verificar si se encuentra dentro de los dias
     * feriados
     * @param diasFeriados lista con los dias feriados
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
     * Obiene el valor de un bigdecima, si este es nulo el valor regresado es
     * cero
     *
     * @param valor el objeto a evaluar
     * @return el valor del objeo ingresado
     */
    public static final BigDecimal getValorNoNulo(BigDecimal valor) {
        return valor != null ? valor : BigDecimal.ZERO;
    }

    /**
     * Obiene el valor de un bigdecima, si este es nulo el valor regresado es
     * cero
     *
     * @param valor el objeto a evaluar
     * @return el valor del objeo ingresado
     */
    public static final Integer getValorNoNulo(Integer valor) {
        return valor != null && valor != 0 ? valor : null;
    }

    /**
     *
     * @param empleado
     * @param idModalidad
     * @return
     */
    public static int obtenTipoTrabajdor(EmpleadoCuota empleado, Integer idModalidad) {
        switch (idModalidad) {
            case 15:
                return empleado.getIndividual() ? 7 : ParentescoIssfEnum.obternerEnumById(empleado.getParentesco().getIdParentesco().intValue()).getTipoDeTrabajador();
            default:
                return TIPO_TRABAJADOR;
        }
    }

}
