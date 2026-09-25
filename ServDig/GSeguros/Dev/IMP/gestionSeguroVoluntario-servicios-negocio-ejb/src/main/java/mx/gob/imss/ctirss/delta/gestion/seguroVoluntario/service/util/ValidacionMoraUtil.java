package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.persistence.cobranza.DitPago;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validaciones específicas para proceso de baja por mora.
 *
 * NO es un EJB, solo métodos estáticos de validación.
 */
public class ValidacionMoraUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidacionMoraUtil.class);

    /**
     * Días máximos entre pagos para considerarlos consecutivos.
     * 45 días permite cubrir meses con diferente cantidad de días.
     */
    private static final int DIAS_MAX_CONSECUTIVOS = 45;

    /**
     * Valida si los pagos vencidos son consecutivos (sin saltos).
     *
     * Dos pagos son consecutivos si la diferencia entre sus fechas límite
     * es menor o igual a DIAS_MAX_CONSECUTIVOS (45 días).
     *
     * @param pagosOrdenados Lista de pagos ordenados por fecha límite
     * @return true si son consecutivos, false si hay saltos
     */
    public static boolean sonPagosConsecutivos(List<DitPago> pagosOrdenados) {

        if (pagosOrdenados == null || pagosOrdenados.size() < 2) {
            return false;
        }

        for (int i = 0; i < pagosOrdenados.size() - 1; i++) {
            DitPago pagoActual = pagosOrdenados.get(i);
            DitPago pagoSiguiente = pagosOrdenados.get(i + 1);

            if (pagoActual.getFecLimitePago() == null || pagoSiguiente.getFecLimitePago() == null) {
                LOGGER.warn("Pago sin fecha límite encontrado");
                return false;
            }

            long diff = Math.abs(
                pagoSiguiente.getFecLimitePago().getTime() -
                pagoActual.getFecLimitePago().getTime()
            );

            long diasDiferencia = diff / (1000 * 60 * 60 * 24);

            if (diasDiferencia > DIAS_MAX_CONSECUTIVOS) {
                LOGGER.info("Pagos no consecutivos detectados. Diferencia: " + diasDiferencia + " días");
                return false;
            }
        }

        return true;
    }

    /**
     * Verifica si un seguro califica para baja por mora.
     *
     * Criterios:
     * - Mínimo 2 pagos vencidos
     * - Pagos deben ser consecutivos
     *
     * @param pagosVencidos Lista de pagos vencidos ordenados
     * @return true si califica, false si no
     */
    public static boolean calificaParaBajaMora(List<DitPago> pagosVencidos) {

        if (pagosVencidos == null || pagosVencidos.size() < 2) {
            LOGGER.debug("No califica: menos de 2 pagos vencidos");
            return false;
        }

        if (!sonPagosConsecutivos(pagosVencidos)) {
            LOGGER.debug("No califica: pagos vencidos no son consecutivos");
            return false;
        }

        return true;
    }

    /**
     * Valida si una fecha está dentro del mes actual.
     *
     * @param fecha Fecha a validar
     * @return true si es del mes actual
     */
    public static boolean esMesActual(Date fecha) {
        if (fecha == null) {
            return false;
        }

        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);

        Calendar calHoy = Calendar.getInstance();

        return calFecha.get(Calendar.YEAR) == calHoy.get(Calendar.YEAR) &&
               calFecha.get(Calendar.MONTH) == calHoy.get(Calendar.MONTH);
    }

    /**
     * Valida si una fecha está dentro de un rango específico.
     *
     * @param fecha Fecha a validar
     * @param fechaDesde Límite inferior (inclusivo)
     * @param fechaHasta Límite superior (inclusivo)
     * @return true si está dentro del rango
     */
    public static boolean estaEnRango(Date fecha, Date fechaDesde, Date fechaHasta) {
        if (fecha == null) {
            return false;
        }

        boolean despuesDeFechaDesde = (fechaDesde == null) ||
                                       fecha.after(fechaDesde) ||
                                       fecha.equals(fechaDesde);

        boolean antesDeFechaHasta = (fechaHasta == null) ||
                                     fecha.before(fechaHasta) ||
                                     fecha.equals(fechaHasta);

        return despuesDeFechaDesde && antesDeFechaHasta;
    }

    /**
     * Cuenta cuántos pagos consecutivos hay desde el inicio de la lista.
     *
     * @param pagosOrdenados Lista de pagos ordenados por fecha
     * @return Cantidad de pagos consecutivos desde el primer pago
     */
    public static int contarPagosConsecutivosDesdeInicio(List<DitPago> pagosOrdenados) {

        if (pagosOrdenados == null || pagosOrdenados.isEmpty()) {
            return 0;
        }

        if (pagosOrdenados.size() == 1) {
            return 1;
        }

        int consecutivos = 1; // Primer pago siempre cuenta

        for (int i = 0; i < pagosOrdenados.size() - 1; i++) {
            DitPago pagoActual = pagosOrdenados.get(i);
            DitPago pagoSiguiente = pagosOrdenados.get(i + 1);

            if (pagoActual.getFecLimitePago() == null || pagoSiguiente.getFecLimitePago() == null) {
                break;
            }

            long diff = Math.abs(
                pagoSiguiente.getFecLimitePago().getTime() -
                pagoActual.getFecLimitePago().getTime()
            );

            long diasDiferencia = diff / (1000 * 60 * 60 * 24);

            if (diasDiferencia > DIAS_MAX_CONSECUTIVOS) {
                break; // Se encontró un salto
            }

            consecutivos++;
        }

        return consecutivos;
    }
}
