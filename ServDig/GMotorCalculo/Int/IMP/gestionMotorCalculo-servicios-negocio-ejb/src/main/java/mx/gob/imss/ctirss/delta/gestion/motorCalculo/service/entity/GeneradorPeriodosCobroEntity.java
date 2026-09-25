/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.GeneradorPeriodosCobroLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.PeriodoCalculoCuota;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.digital.modelo.cobranza.MovimientoEmpleado;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para generar los periodos de pagos de un trabajador dado un rango de calculo, esto tomando en cuenta
 * que los periodos maximos de pagos son de 2 meses y estos son ficales es decir 
 * Ene-Feb, Mar-Abr, May-Jun, etc. 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "generadorPeriodosCobroEntity", mappedName = "generadorPeriodosCobroEntity")
public class GeneradorPeriodosCobroEntity implements GeneradorPeriodosCobroLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(GeneradorPeriodosCobroEntity.class);
    /**
     * Arreglo con los mese en que inicia un periodo de calculo
     */
    private static final Integer[] INICIO_PERIODOS = {Calendar.JANUARY, Calendar.MARCH,
            Calendar.MAY, Calendar.JULY, Calendar.SEPTEMBER, Calendar.NOVEMBER};

    /**
     * Genera la lista de periodos a calcular para un empleado, esto debido a que en un rango de fechas a 
     * cobrar se deben subdividir en periodos de 2 meses fiscales
     * @param valores los datos del empleado
     * @param beneficio El beneficio asociado al calculo
     * @return La lista de periodos a calcular con sus respectivos valores
     */
    @Override
    public List<PeriodoCalculoCuota> generaPeriodosCalculo(ValoresCalculoEmpleado valores,
            Beneficio beneficio) {
        // PRimero generamos os periodos basicos (que son periodos de 2 meses
        // mientras las fechas de calculo esten dentro de los rangos)
        List<PeriodoCalculoCuota> periodos;
		if (valores.getModalidad() == ModalidadEnum.CUARENTA.getId()) {
			periodos = creaPeriodosBasicosMensuales(valores);
		} else if (valores.getModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
			periodos = creaPeriodosBasicosAnuales(valores);
		} else {
			periodos = creaPeriodosBasicos(valores);
		}
        
        List<MovimientoEmpleado> movimientos = new ArrayList<MovimientoEmpleado>();
        if(valores.getEmpleado().getMovimientos() != null) {
            movimientos = Arrays.asList(valores.getEmpleado().getMovimientos());
        }
        periodos = agregaMovimientosYDescuentos(periodos, movimientos, beneficio);
        return periodos;
    }

    /**
     * Construye la lista de periodos basicos que contendra el motor de calculo
     * siempre debe existir por lo menos un periodo de calculo
     * 
     * @param valores Los valores completos e calculo, sobre los cuales se partiran los subperiodos
     * @return la lista de periodos a calcular
     */
    private List<PeriodoCalculoCuota> creaPeriodosBasicos(ValoresCalculoEmpleado valores) {
        List<PeriodoCalculoCuota> periodos = new ArrayList<PeriodoCalculoCuota>();

        Calendar inicio = valores.getFechaInicioCalculo();
        Calendar fin = valores.getFechaFinCalculo();
        int mesIniValores = inicio.get(Calendar.MONTH);
        int mesInicio = ArrayUtils.contains(INICIO_PERIODOS, mesIniValores) ? mesIniValores
                : mesIniValores - 1;
        int anioInicio = inicio.get(Calendar.YEAR);

        // generamos el primer periodo, ese siempre esta presente
        int orden = 1;
        Calendar fechaFinPeriodo = PeriodoUtil.getUltimoDia(mesInicio + 1, anioInicio);
        PeriodoCalculoCuota periodoInicial = creaPeriodoBasico(inicio, fechaFinPeriodo, orden);
        periodos.add(periodoInicial);


		if (!DateUtils.isSameDay(fin, periodoInicial.getFechaFinal())) {
			boolean ultimo = false;
			do {
				PeriodoCalculoCuota periodoSig = creaPeriodoSiguiente(
						periodoInicial, fin);
				ultimo = DateUtils.isSameDay(fin, periodoSig.getFechaFinal());
				LOGGER.debug("Fecha fin  {}  fecha final periodo {}",
						fin.getTime(), periodoSig.getFechaFinal().getTime());
				periodos.add(periodoSig);
				periodoInicial = periodoSig;
			} while (!ultimo);
		}

        return periodos;
    }

	private List<PeriodoCalculoCuota> creaPeriodosBasicosMensuales(
			ValoresCalculoEmpleado valores) {
		List<PeriodoCalculoCuota> periodos = new ArrayList<PeriodoCalculoCuota>();

		Calendar inicio = valores.getFechaInicioCalculo();
		Calendar fin = valores.getFechaFinCalculo();

		int mesInicio = inicio.get(Calendar.MONTH);
		int anioInicio = inicio.get(Calendar.YEAR);

		// generamos el primer periodo, ese siempre esta presente
		int orden = 1;
		Calendar fechaFinPeriodo = PeriodoUtil.getUltimoDia(mesInicio, anioInicio);
		PeriodoCalculoCuota periodoInicial = creaPeriodoBasico(inicio, fechaFinPeriodo, orden);
		periodos.add(periodoInicial);

		if (!DateUtils.isSameDay(fin, periodoInicial.getFechaFinal())) {
			boolean ultimo = false;
			do {
				PeriodoCalculoCuota periodoSig = creaPeriodoSiguienteMensual(periodoInicial, fin);
				ultimo = DateUtils.isSameDay(fin, periodoSig.getFechaFinal());
				LOGGER.debug("Fecha fin  {}  fecha final periodo {}", fin.getTime(), periodoSig.getFechaFinal().getTime());
				periodos.add(periodoSig);
				periodoInicial = periodoSig;
			} while (!ultimo);
		}

		return periodos;
	}
	
	private List<PeriodoCalculoCuota> creaPeriodosBasicosAnuales(
			ValoresCalculoEmpleado valores) {
		List<PeriodoCalculoCuota> periodos = new ArrayList<PeriodoCalculoCuota>();

		Calendar inicio = valores.getFechaInicioCalculo();
		Calendar fin = valores.getFechaFinCalculo();

		int mesInicio = inicio.get(Calendar.MONTH);
		int anioInicio = inicio.get(Calendar.YEAR);

		// generamos el primer periodo, ese siempre esta presente
		int orden = 1;
		Calendar fechaFinPeriodo = PeriodoUtil.getUltimoDia(mesInicio - 1, anioInicio + 1);
		PeriodoCalculoCuota periodoInicial = creaPeriodoBasico(inicio, fechaFinPeriodo, orden);
		LOGGER.debug("Primer Fecha fin  {}  fecha final periodo  {}", fin.getTime(), periodoInicial.getFechaFinal().getTime());
		periodos.add(periodoInicial);

		if (!DateUtils.isSameDay(fin, periodoInicial.getFechaFinal())) {
			boolean ultimo = false;
			do {
				PeriodoCalculoCuota periodoSig = creaPeriodoSiguienteAnual(periodoInicial, fin);
				ultimo = DateUtils.isSameDay(fin, periodoSig.getFechaFinal());
				LOGGER.debug("Fecha fin  {}  fecha final periodo {}", fin.getTime(), periodoSig.getFechaFinal().getTime());
				periodos.add(periodoSig);
				periodoInicial = periodoSig;
			} while (!ultimo);
		}

		return periodos;
	}
	
	private PeriodoCalculoCuota creaPeriodoSiguienteMensual(PeriodoCalculoCuota periodoAnterior,
            Calendar fechaFin) {
        int orden = periodoAnterior.getOrden() + 1;

        Calendar fechaIniPeriodo = PeriodoUtil.truncaFecha(periodoAnterior.getFechaFinal());
        fechaIniPeriodo.add(Calendar.DATE, 1);

        Calendar fechaFinPeriodo = PeriodoUtil.getUltimoDia(
                fechaIniPeriodo.get(Calendar.MONTH), fechaIniPeriodo.get(Calendar.YEAR));
        fechaFinPeriodo = fechaFin.after(fechaFinPeriodo) ? fechaFinPeriodo 
                : PeriodoUtil.truncaFecha(fechaFin);

        return creaPeriodoBasico(fechaIniPeriodo, fechaFinPeriodo, orden);
    }
	
	private PeriodoCalculoCuota creaPeriodoSiguienteAnual(PeriodoCalculoCuota periodoAnterior,
            Calendar fechaFin) {
        int orden = periodoAnterior.getOrden() + 1;

        Calendar fechaIniPeriodo = PeriodoUtil.truncaFecha(periodoAnterior.getFechaFinal());
        fechaIniPeriodo.add(Calendar.DATE, 1);

        Calendar fechaFinPeriodo = PeriodoUtil.getUltimoDia(
                fechaIniPeriodo.get(Calendar.MONTH) - 1, fechaIniPeriodo.get(Calendar.YEAR) + 1);
        fechaFinPeriodo = fechaFin.after(fechaFinPeriodo) ? fechaFinPeriodo 
                : PeriodoUtil.truncaFecha(fechaFin);

        return creaPeriodoBasico(fechaIniPeriodo, fechaFinPeriodo, orden);
    }

    /**
     * Genera un objeto <code>PeriodoCalculoCuota</code> que representa el
     * siguiente periodo al recibido como parametro
     * 
     * @param periodoAnterior periodo base , para generar el nuevo periodo
     * @param fechaFin fecha final del periodo nuevo
     * @return el nuevo periodo generado
     */
    private PeriodoCalculoCuota creaPeriodoSiguiente(PeriodoCalculoCuota periodoAnterior,
            Calendar fechaFin) {
        int orden = periodoAnterior.getOrden() + 1;

        Calendar fechaIniPeriodo = PeriodoUtil.truncaFecha(periodoAnterior.getFechaFinal());
        fechaIniPeriodo.add(Calendar.DATE, 1);

        Calendar fechaFinPeriodo = PeriodoUtil.getUltimoDia(
                fechaIniPeriodo.get(Calendar.MONTH) + 1, fechaIniPeriodo.get(Calendar.YEAR));
        fechaFinPeriodo = fechaFin.after(fechaFinPeriodo) ? fechaFinPeriodo 
                : PeriodoUtil.truncaFecha(fechaFin);

        return creaPeriodoBasico(fechaIniPeriodo, fechaFinPeriodo, orden);
    }
    
    /**
     * Crea un objeto de tipo <code>PeriodoCalculoCuota</code> con los datos
     * recibidos
     * 
     * @param fechaIniPeriodo fecha de inicio de periodo de calculo
     * @param fechaFinPeriodo fecha final del periodo de calculo
     * @param orden el numero de ordenacion para el periodo
     * @return El periodo nuevo generado
     */
    private PeriodoCalculoCuota creaPeriodoBasico(Calendar fechaIniPeriodo,
            Calendar fechaFinPeriodo, int orden) {
        PeriodoCalculoCuota periodo = new PeriodoCalculoCuota();
        periodo.setFechaInicial(PeriodoUtil.truncaFecha(fechaIniPeriodo));
        periodo.setFechaFinal(PeriodoUtil.truncaFecha(fechaFinPeriodo));
        periodo.setOrden(orden);
        return periodo;
    }
 
    /**
     * Agrega la lista de movimientos y descuentos que se aplicaran en un
     * periodo de tiempo
     * 
     * @param periodos El periodo de tiempo en el cual se buscan los movimientos y descuentos
     * @param movimientos lista de movimientos que aplican en todo el periodo de calculo
     * @param beneficio el beneficio que aplica para todo el period de calculo
     * @return la lista de periodos de calculo con sus movimientos y descuentos correspondientes
     */
    private List<PeriodoCalculoCuota> agregaMovimientosYDescuentos(
            List<PeriodoCalculoCuota> periodos, List<MovimientoEmpleado> movimientos,
            Beneficio beneficio) {

        List<PeriodoCalculoCuota> periodosMov = new ArrayList<PeriodoCalculoCuota>();

        for (PeriodoCalculoCuota periodo : periodos) {
            periodo.setMovimientos(getMovimientosPeriodo(periodo, movimientos));
            periodo.setDescuentos(getDescuentosPeriodo(periodo, beneficio));
            periodosMov.add(periodo);
        }
        return periodosMov;
    }

    /**
     * Obtiene una lista de movmientos que afecten un periodo, es decir los
     * movimientos que pertenescan al periodo de calculo
     * 
     * @param periodo Rango de fechas en las cuales se buscan los movimientos de un empleado
     * @param movimientos lista de movimientos de un empleado, en el rango completo de calculo
     * @return la lista de movimientos que aplican en el periodo de calculo
     */
    private List<MovimientoEmpleado> getMovimientosPeriodo(PeriodoCalculoCuota periodo,
            List<MovimientoEmpleado> movimientos) {

        List<MovimientoEmpleado> movimientosAfectan = new ArrayList<MovimientoEmpleado>();
        if (movimientos != null) {
            for (MovimientoEmpleado mov : movimientos) {
                if (PeriodoUtil.isFechaContenida(periodo.getFechaInicial(),
                        periodo.getFechaFinal(), mov.getFecha())) {
                    movimientosAfectan.add(mov);
                }
            }
        }

        return movimientosAfectan;
    }

    /**
     * Obtiene la lista de descuentos de un beneficio
     * @param periodo Rango de fechas en las cuales hay que buscar los descuentos
     * @param beneficio Entidad que contiene la lista de descuentos por fecha
     * @return la lista de descuentos que aplican en el periodo de tiempo
     */
    private List<DescuentoBeneficio> getDescuentosPeriodo(PeriodoCalculoCuota periodo,
            Beneficio beneficio) {
        List<DescuentoBeneficio> descuentos = new ArrayList<DescuentoBeneficio>();
        if (beneficio != null && beneficio.getListaDescuentosBeneficio() != null) {
            for (DescuentoBeneficio descuento : beneficio.getListaDescuentosBeneficio()) {
                //PeriodoUtil.truncaFecha(fecha)
                Calendar desIni = PeriodoUtil.truncaFecha(descuento.getFecInicio());
                Calendar desFin = PeriodoUtil.truncaFecha(descuento.getFechaFin());
                
                Calendar periodoIni = PeriodoUtil.truncaFecha(periodo.getFechaInicial());
                Calendar periodoFin = PeriodoUtil.truncaFecha(periodo.getFechaFinal());

                if (PeriodoUtil.isFechaContenida(periodoIni, periodoFin, desIni.getTime())
                        || PeriodoUtil.isFechaContenida(periodoIni, periodoFin, desFin.getTime())
                        || PeriodoUtil.isFechaContenida(desIni, desFin, periodoIni.getTime())) {
                    descuentos.add(descuento);
                }
            }
        }
        return descuentos;
    }

}
