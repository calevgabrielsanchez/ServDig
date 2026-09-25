package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;



import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCotizacion;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitPago;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.bajas.DitBajaMoraPagoVencido;
import mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilidad para cálculos y lógica de baja por mora.
 *
 * NO es un EJB, solo métodos estáticos para procesamiento de datos.
 * Reutilizable desde SeguroIvroServiceEntity y otros servicios.
 */
public class BajaMoraUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BajaMoraUtil.class);

    /**
     * Obtiene fecha de corte para procesamiento de mora (día 17 del mes).
     *
     * @param fecha Fecha base (null = mes actual)
     * @return Fecha con día 17, hora 23:59:59
     */
    public static Date obtenerFechaCorte17DelMes(Date fecha) {
        if (fecha == null) {
            fecha = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.DAY_OF_MONTH, 17);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    /**
     * Filtra pagos en estado POR_PAGAR con fecha límite vencida.
     *
     * @param todosPagos Lista completa de pagos del seguro
     * @param fechaCorte Fecha límite (pagos vencidos antes de esta fecha)
     * @return Lista de pagos vencidos ordenados por fecha
     */
    public static List<DitPago> filtrarPagosVencidos(List todosPagos, Date fechaCorte) {

        List<DitPago> vencidos = new ArrayList<DitPago>();
        Date hoy = new Date();

        if (todosPagos == null || todosPagos.isEmpty()) {
            return vencidos;
        }

        for (int i = 0; i < todosPagos.size(); i++) {
            DitPago pago = (DitPago) todosPagos.get(i);

            // Solo pagos POR_PAGAR (estado 1)
            if (pago.getDicEstadoPago().getCveIdEstadoPago() != EstadoPagoEnum.POR_PAGAR.getId()) {
                continue;
            }

            // Con fecha límite definida
            if (pago.getFecLimitePago() == null) {
                continue;
            }

            // Vencidos (fecha límite < hoy)
            if (!pago.getFecLimitePago().before(hoy)) {
                continue;
            }

            // Dentro del rango de corte
            if (pago.getFecLimitePago().after(fechaCorte)) {
                continue;
            }

            vencidos.add(pago);
        }

        // Ordenar por fecha límite
        Collections.sort(vencidos, new Comparator() {
            public int compare(Object o1, Object o2) {
                DitPago p1 = (DitPago) o1;
                DitPago p2 = (DitPago) o2;
                return p1.getFecLimitePago().compareTo(p2.getFecLimitePago());
            }
        });

        return vencidos;
    }

    /**
     * Filtra pagos ya marcados como VENCIDOS (estado 3).
     *
     * @param todosPagos Lista completa de pagos del seguro
     * @param fechaCorte Fecha límite (opcional, null = todos)
     * @return Lista de pagos vencidos ordenados por fecha
     */
    public static List<DitPago> obtenerPagosYaVencidos(List todosPagos, Date fechaCorte) {

        List<DitPago> vencidos = new ArrayList<DitPago>();

        if (todosPagos == null || todosPagos.isEmpty()) {
            return vencidos;
        }

        for (int i = 0; i < todosPagos.size(); i++) {
            DitPago pago = (DitPago) todosPagos.get(i);

            // Solo pagos VENCIDOS (estado 3)
            if (pago.getDicEstadoPago().getCveIdEstadoPago() != EstadoPagoEnum.VENCIDO.getId()) {
                continue;
            }

            // Con fecha límite definida
            if (pago.getFecLimitePago() == null) {
                continue;
            }

            // Si hay fecha de corte, filtrar
            if (fechaCorte != null && pago.getFecLimitePago().after(fechaCorte)) {
                continue;
            }

            vencidos.add(pago);
        }

        // Ordenar por fecha límite
        Collections.sort(vencidos, new Comparator() {
            public int compare(Object o1, Object o2) {
                DitPago p1 = (DitPago) o1;
                DitPago p2 = (DitPago) o2;
                return p1.getFecLimitePago().compareTo(p2.getFecLimitePago());
            }
        });

        return vencidos;
    }
    
    
    
    public static List<DitBajaMoraPagoVencido> crearListMoraPagoVencidoBitacora(DetalleMoraInfo bajaDtalles){
    	List<DitBajaMoraPagoVencido> pagosVencidosBitacora = new ArrayList<DitBajaMoraPagoVencido>(); 
    	
    	for (int i = 0; i < bajaDtalles.getPagosVencidos().size(); i++) {
    		PagoVencido pagoVencido = bajaDtalles.getPagosVencidos().get(i);
    		DitBajaMoraPagoVencido pagoVencidoBitacora = new DitBajaMoraPagoVencido();
    		pagoVencidoBitacora.setCveIdDetalle(bajaDtalles.getCveIdDetalle());
    		pagoVencidoBitacora.setNumMesMora(i + 1);
    		pagoVencidoBitacora.setTxtLineaDeCaptura(pagoVencido.getLineaCaptura());    		
    		pagoVencidoBitacora.setNumMonto(pagoVencido.getMonto());
    		pagoVencidoBitacora.setFecInicioPeriodo(pagoVencido.getFecIniPeriodo());
    		pagoVencidoBitacora.setFecFinPeriodo(pagoVencido.getFecFinPeriodo());
    		pagoVencidoBitacora.setNumRecargos(BigDecimal.ZERO);
    		pagosVencidosBitacora.add(pagoVencidoBitacora);
    		LOGGER.debug("PagoVencidoBitacora: " + pagoVencido.toString());
    	}
    	return pagosVencidosBitacora;
    	
    }
    
    
	/**
     * Construye cadena CSV de períodos vencidos (formato: YYYY-MM,YYYY-MM,...).
     *
     * @param pagosVencidos Lista de pagos vencidos ordenados
     * @return String CSV de períodos (ej: "2025-01,2025-02,2025-03")
     */
    public static String construirPeriodosCsv(List<DitPago> pagosVencidos) {

        if (pagosVencidos == null || pagosVencidos.isEmpty()) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        StringBuilder csv = new StringBuilder();

        for (int i = 0; i < pagosVencidos.size(); i++) {
            if (i > 0) {
                csv.append(",");
            }
            csv.append(sdf.format(pagosVencidos.get(i).getFecLimitePago()));
        }

        return csv.toString();
    }

    /**
     * Calcula el monto total adeudado basado en pagos vencidos.
     *
     * @param compra Compra asociada al seguro
     * @param cantidadPagosVencidos Número de pagos vencidos
     * @return Monto adeudado (monto por pago * cantidad vencidos)
     */
    public static BigDecimal calcularMontoAdeudo(DitCompra compra, int cantidadPagosVencidos) {

        BigDecimal montoTotal = BigDecimal.ZERO;

        if (compra == null || cantidadPagosVencidos <= 0) {
            return montoTotal;
        }

        try {
            DitCotizacion cotizacion = compra.getDitCotizacion();
            if (cotizacion == null || cotizacion.getNumTotal() == null) {
                LOGGER.warn("No hay cotización disponible para compra: " + compra.getCveIdCompra());
                return montoTotal;
            }

            BigDecimal montoTotalCotizacion = cotizacion.getNumTotal();
            int totalPagos = compra.getDitPagos() != null ? compra.getDitPagos().size() : 0;

            if (totalPagos == 0) {
                LOGGER.warn("No hay pagos en compra: " + compra.getCveIdCompra());
                return montoTotal;
            }

            // Monto por pago = Total cotización / Total de pagos
            BigDecimal montoPorPago = montoTotalCotizacion.divide(
                new BigDecimal(totalPagos),
                2,
                RoundingMode.HALF_UP
            );

            // Monto adeudado = Monto por pago * Pagos vencidos
            montoTotal = montoPorPago.multiply(new BigDecimal(cantidadPagosVencidos));

            LOGGER.debug("Cálculo mora - Cotización: " + montoTotalCotizacion +
                         ", Pagos totales: " + totalPagos +
                         ", Monto por pago: " + montoPorPago +
                         ", Pagos vencidos: " + cantidadPagosVencidos +
                         ", Adeudo: " + montoTotal);

        } catch (Exception e) {
            LOGGER.error("Error calculando monto adeudo: " + e.getMessage(), e);
        }

        return montoTotal;
    }

    /**
     * Obtiene la fecha del último pago PAGADO (estado 2).
     *
     * @param todosPagos Lista completa de pagos
     * @return Fecha del último pago, null si no hay pagos pagados
     */
    public static Date obtenerFechaUltimoPagoPagado(List todosPagos) {

        Date fecUltimoPago = null;

        if (todosPagos == null || todosPagos.isEmpty()) {
            return null;
        }

        for (int i = 0; i < todosPagos.size(); i++) {
            DitPago pago = (DitPago) todosPagos.get(i);

            // Solo pagos PAGADOS (estado 2)
            if (pago.getDicEstadoPago().getCveIdEstadoPago() != EstadoPagoEnum.PAGADO.getId()) {
                continue;
            }

            if (pago.getFecPago() == null) {
                continue;
            }

            // Obtener el más reciente
            if (fecUltimoPago == null || pago.getFecPago().after(fecUltimoPago)) {
                fecUltimoPago = pago.getFecPago();
            }
        }

        return fecUltimoPago;
    }

    /**
     * Calcula fecha de corte con meses hacia atrás.
     * Útil para búsquedas históricas.
     *
     * @param mesesAtras Cantidad de meses a retroceder (ej: 10)
     * @return Fecha resultante
     */
    public static Date calcularFechaCorteConMeses(int mesesAtras) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -mesesAtras);
        return cal.getTime();
    }

}
