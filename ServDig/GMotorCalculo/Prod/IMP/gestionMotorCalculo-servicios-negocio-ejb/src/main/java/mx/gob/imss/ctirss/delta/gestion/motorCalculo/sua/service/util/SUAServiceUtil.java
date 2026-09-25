/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.PeriodoSUA;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase utilitaria para el manejo de lgunas operaciones del servicio sua
 * 
 * @author NOVUTECK1
 * 
 */
public abstract class SUAServiceUtil {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SUAServiceUtil.class);

    /**
     * Agrega un periodo sua a la lista de periodos, si ya existe en la lista lo
     * elimina y agrega el nuevo
     * 
     * @param periodos la lista de periodos a la cual se regara el nuevo elemento
     * @param periodoAgregar El uevo elemento a agregar en la lista de periodos
     * @return la lista de periodos con sus elementos resultantes
     */
    public static final List<PeriodoSUA> agregaPeriodo(List<PeriodoSUA> periodos,
            PeriodoSUA periodoAgregar) {
        List<PeriodoSUA> periodosNuevos = new ArrayList<PeriodoSUA>();

        for (PeriodoSUA periodo : periodos) {
            // Si el periodo a agregar no esta contenido se agrega a la lista
            // nueva, en caso
            // Contrario no se agrega ya que esta se agregara eal final
            if (!(periodoContenido(periodo.getFechaInicio(), periodo.getFechaFin(),
                    periodoAgregar.getFechaInicio(), periodoAgregar.getFechaFin()))) {
//                LOGGER.debug("PEriodo NO contenido");
                periodosNuevos.add(periodo);
            }
        }
        periodosNuevos.add(periodoAgregar);
        return periodosNuevos;
    }

    /**
     * Obtiene el periodo sua que corresponde al periodo que se esta calculando,
     * si no existe este periodo se crea si ya esiste se regresa este objeto para
     * seguirle agregando elementos
     * 
     * @param periodosGenerados Lista de perioso sua ya generados
     * @param periodo Objeto con las fechas de periodo a buscar, esto para saber 
     * si ya existe o se crea uno nuevo
     * @return El periodo sua generado o encontrado
     */
    public static final PeriodoSUA obtenPeriodoSUA(List<PeriodoSUA> periodosGenerados,
            PeriodoCuota periodo) {
        if (periodosGenerados == null) {
            periodosGenerados = new ArrayList<PeriodoSUA>();
        }
        PeriodoSUA periodoSUA = generaPeriodoBasico(periodo);
        for (PeriodoSUA sua : periodosGenerados) {
            if (periodoContenido(sua.getFechaInicio(), sua.getFechaFin(),
                    periodo.getInicioPeriodo(), periodo.getFinPeriodo())) {
                periodoSUA = sua;
                LOGGER.debug("Se encontro un periodo ya generado");
                break;
            }
        }
        return periodoSUA;
    }

    /**
     * GEnera un objeto de periodo sua basico (solo fechas de periodo)
     * 
     * @param periodo Objeto de donde se obtiene las fechas para crear el periodo SUA nuevo
     * @return El periodo sua basico, solo con fechas de periodo
     */
    private static PeriodoSUA generaPeriodoBasico(PeriodoCuota periodo) {
        PeriodoSUA sua = new PeriodoSUA();
        sua.setFechaInicio(periodo.getInicioPeriodo());
        sua.setFechaFin(periodo.getFinPeriodo());
        sua.setMonto(BigDecimal.ZERO);
        return sua;
    }

    /**
     * MEtodo utilitario para validar si las fechas de un  periodo esta contenido dentro de otro
     * @param iniPeriodoContenedor Fecha de inicio del periodo contenedor
     * @param finPeriodoContenedor FEcha final del periodo contenedor
     * @param iniPeriodo FEcha de inicio del periodo a validar 
     * @param finPeriodo fecha final del periodo a validadr
     * @return true si las fechas del periodo se encuentran dentro del rango de fechas del periodo contenedor
     */
    private static boolean periodoContenido(Calendar iniPeriodoContenedor,
            Calendar finPeriodoContenedor, Calendar iniPeriodo, Calendar finPeriodo) {
        long iniContenedor = DateUtils.truncate(iniPeriodoContenedor, Calendar.DATE)
                .getTimeInMillis();
        long finContenedor = DateUtils.truncate(finPeriodoContenedor, Calendar.DATE)
                .getTimeInMillis();

        long inicio = DateUtils.truncate(iniPeriodo, Calendar.DATE).getTimeInMillis();
        long fin = DateUtils.truncate(finPeriodo, Calendar.DATE).getTimeInMillis();

        boolean inicioContenido = iniContenedor <= inicio && inicio <= finContenedor;
        boolean finContenido = iniContenedor <= fin && fin <= finContenedor;

        return inicioContenido && finContenido;
    }

}
