/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MotorCalculoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.SalarioCalculoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.MotorFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.RamaCalculoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.PeriodoCalculoCuota;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesRama;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesTipoMovimiento;
import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.MovimientoEmpleado;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serivicio para el calculo de cuotas de un trabajador
 *
 * @author NOVUTECK1
 *
 */
@Stateless(name = "motorCalculoServiceEntity", mappedName = "motorCalculoServiceEntity")
public class MotorCalculoServiceEntity implements MotorCalculoServiceLocal {
    @EJB
    private SalarioCalculoServiceLocal salarioCalculoServiceLocal;

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MotorCalculoServiceEntity.class);
    /**
     * Liste de movimientos que implican no calcular cuota del empleado
     */
    public static final int[] MOVIMIENTOS_NO_CALCULO = {ClavesTipoMovimiento.AUSENTISMO,
            ClavesTipoMovimiento.INCAPACIDAD };

    /**
     * Factor por el acual hay que dividir los percentaje para poder trabajarlos
     * en los calculos de cuotas
     */
    private static final BigDecimal FACTOR_PORCENTAJE = new BigDecimal(100);

    /**
     * valor de la rama de la tabla DIC_RAMA para CYV
     */
    private static final Integer CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ = 9;

    /**
     * Valor de la aportacion en  la tabla DIC_RAMA de tipo patronal
     */
    private static final Integer TIPO_APORTACION_PATRONAL = 1;

    /**
     * Calculamos loa valores a pagar por un empleado a partir de sus periodos
     * de pago descuentos, movimientos y salarios
     *
     * @param valores
     *            Entidad con la informacion necesaria para el calculo de cuotas
     * @return Las cuotas generadas para un trabajador
     * @throws SUAException
     */
    @Override
    public EmpleadoCuota calculaCuota(ValoresCalculoEmpleado valores) throws SUAException {
        LOGGER.debug("Calculando cuota de empleado");
        // Copiamos los valores basicos del empleado
        EmpleadoCuota empleadoCuota = new EmpleadoCuota();
        empleadoCuota.setNumeroSeguridadSocial(valores.getEmpleado().getNumeroSeguridadSocial());


        String zonaSalarialOriginal = null;

        BigDecimal salarioBaseCotizacion;
        if (valores.getModalidad() == ModalidadEnum.CUARENTA.getId()) {
            salarioBaseCotizacion = valores.getEmpleado().getSalario();
        } else{
            salarioBaseCotizacion = valores.getSalarioCalculo();
        }

        empleadoCuota.setSalario(salarioBaseCotizacion);
        LOGGER.info("salario empleadoCuota.getSalario: {}",empleadoCuota.getSalario());
        // calculamos las cuotas del empleado por cada periodo que tenga
        BigDecimal cuotaTotal = BigDecimal.ZERO;
        List<PeriodoCuota> periodos = new ArrayList<PeriodoCuota>();
        for (PeriodoCalculoCuota periodoCalculo : valores.getPeriodos()) {
            long modalidad = valores.getModalidad();

            // Se recalculan valores de salario por periodo ()
            if (modalidad == ModalidadEnum.CUARENTA.getId()) {

                LOGGER.info("Fecha Fin calculo {} , fechaInicial calculo {}", valores.getFechaFinCalculo(), periodoCalculo.getFechaInicial());
                boolean validaSalarioMod40 = salarioCalculoServiceLocal.validaSalarioMod40(valores.getFechaFinCalculo(), periodoCalculo.getFechaInicial());
                LOGGER.info("validaSalarioMod40: " + validaSalarioMod40);
                if (validaSalarioMod40) {
                    LOGGER.info("Entra a salario de Empleado: " + valores.getEmpleado().getSalario());
                    valores.getEmpleado().setSalario(empleadoCuota.getSalario());
                } else {
                    LOGGER.info("Entra a salario de Empleado: " + valores.getEmpleado().getSalario());
                    valores.getEmpleado().setSalario(valores.getUltimoSalarioCotizado());
                }
                LOGGER.info("valores.getEmpleado().getSalario: " + valores.getEmpleado().getSalario());
                Calendar fechaInicioCalculo = valores.getFechaInicioCalculo();
                valores.setFechaInicioCalculo(periodoCalculo.getFechaInicial());
                valores = salarioCalculoServiceLocal.agregaSalariosCalculo(valores);
                valores.setFechaInicioCalculo(fechaInicioCalculo);


                Date fechaInicialPeriodo = periodoCalculo.getFechaInicial().getTime();
                Date fechaFinalPeriodo = periodoCalculo.getFechaFinal().getTime();
                //Se reestablece la zona salarial original para calcular el valor del factor de cesantia y vejez - patronal - CVRO
                zonaSalarialOriginal = recuperaZonaSalarialOriginal(valores.getZonaSalarial(), valores.getModalidad(), fechaInicialPeriodo);

                BigDecimal factorCYVPatronalCVRO = salarioCalculoServiceLocal.getFactorCYVPatronalCVRO(zonaSalarialOriginal, valores.getSalarioCalculo(), fechaInicialPeriodo, fechaFinalPeriodo);

                if (factorCYVPatronalCVRO == null || factorCYVPatronalCVRO.equals(BigDecimal.ZERO)){
                    LOGGER.error("ERROR: No se encontró el factor de cesantía y vejez de tipo patronal no encontrado.");
                    throw new SUAException("ERROR: No se encontró el factor de cesantía y vejez de tipo patronal no encontrado.");
                }

                List<RamaCalculo> ramaCalculos =  valores.getCuotas();
                for(RamaCalculo calculo:ramaCalculos){
                    //Se evalua si es modalidad 40 (CVRO), rama 9 (CESANTIA EN EDAD AVANZADA Y VEJEZ) y de tipo de aportarcion 1 (PATRONAL)
                    if((calculo.getIdRama().equals(CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ)&&
                            calculo.getIdTipoAportacion().equals(TIPO_APORTACION_PATRONAL))){
                        LOGGER.info("Mod40, rama: "+calculo.getNombre()+ " aportacion: "+calculo.getDesTipoAportacion()+ " factorCalculado: "+factorCYVPatronalCVRO);
                        //Se coloca el factor de acuerdo a la tabla nueva
                        //Se actualiza el factor en el objeto rama
                        calculo.setFactorCalculo(factorCYVPatronalCVRO);
                    }
                }

            }

            PeriodoCuota periodo = calculaPeriodoCuota(periodoCalculo, valores);

            //Cambio para ajuste de la ley 168

            try{
                BigDecimal ajusteCYVPatronal = salarioCalculoServiceLocal.verificaSeguroLey168(valores.getEmpleado().getNumeroSeguridadSocial());

                if(ajusteCYVPatronal!=null && !ajusteCYVPatronal.equals(BigDecimal.ZERO)){

                    if(periodo.getCuotas()!=null){

                        RamaCalculo[] ramas = periodo.getCuotas();

                        List<RamaCalculo> ramaCalculos = Arrays.asList(ramas);
                        for(RamaCalculo calculo:ramaCalculos){
                            //Se evalua si es modalidad 40 (CVRO), rama 9 (CESANTIA EN EDAD AVANZADA Y VEJEZ) y de tipo de aportarcion 1 (PATRONAL)
                            if((calculo.getIdRama().equals(CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ)&&
                                    calculo.getIdTipoAportacion().equals(TIPO_APORTACION_PATRONAL))){
                                LOGGER.info("Mod40, rama: "+calculo.getNombre()+ " aportacion: "+calculo.getDesTipoAportacion()+ " ajusteCYVPatronal: "+ajusteCYVPatronal);
                                LOGGER.info("Antes del calculo: Aportacion : "+calculo.getAportacion()+" TotalPeriodo: "+periodo.getTotal());
                                calculo.setAportacion(calculo.getAportacion().add(ajusteCYVPatronal));
                                periodo.setTotal(periodo.getTotal().add(ajusteCYVPatronal));
                                LOGGER.info("Después del calculo: Aportacion : "+calculo.getAportacion()+" TotalPeriodo: "+periodo.getTotal()+ " ajuste: "+ajusteCYVPatronal);
                            }
                        }

                    }else{
                        LOGGER.info("periodo.getCuotas() null");
                    }
                }else{
                    LOGGER.info("ajusteCYVPatronal cero, no aplica recargo");
                }


            }catch (Exception e){
                LOGGER.error("Hubo un error en el codigo para ajustar ley 168: "+e);
                e.printStackTrace();
            }
            //Fin del cambio ley 168



            cuotaTotal = cuotaTotal.add(periodo.getTotal());
            periodos.add(periodo);
        }
        empleadoCuota.setPeriodos(periodos.toArray(new PeriodoCuota[periodos.size()]));

        empleadoCuota.setCuotaTotal(cuotaTotal);
        return empleadoCuota;
    }

    /**
     * Calcula las cuotas de un empleado por un periodo de tiempo dado. Tomando
     * en cuenta sus movimientos y beneficios que apliquen
     *
     * @param periodo
     *            PEriodo de pago a ser calculado
     * @param valores
     *            valores para generar los cobros
     * @return Las cuotas a cobrar en un periodo de tiempo
     */
    private PeriodoCuota calculaPeriodoCuota(PeriodoCalculoCuota periodo,
                                             ValoresCalculoEmpleado valores) {
        // Generamos el periodo nuevo a regresar con los calculos
        PeriodoCuota periodoCalculado = new PeriodoCuota();
        periodoCalculado.setSalarioPeriodo(valores.getSalarioCalculo());
        LOGGER.info("salario periodoCalculado.getSalarioPeriodo: {}",periodoCalculado.getSalarioPeriodo());
        periodoCalculado.setInicioPeriodo(periodo.getFechaInicial());
        periodoCalculado.setFinPeriodo(periodo.getFechaFinal());
        periodoCalculado.setMovimientos(periodo.getMovimientos().toArray(
                new MovimientoEmpleado[periodo.getMovimientos().size()]));
        periodoCalculado.setOrden(periodo.getOrden());

        // inicializamos los valores a cero
        // dado las reglas de redondeo, se rodenda cada vez que se acabe un mes
        // en el calculo
        List<RamaCalculo> cuotas = inicializaCalculoCero(valores.getCuotas());
        List<RamaCalculo> cuotasCalculadas = inicializaCalculoCero(valores.getCuotas());

        if (valores.getModalidad() != ModalidadEnum.TREINTAYTRES.getId()) {
            Calendar inicio = (Calendar) periodo.getFechaInicial().clone();
            Calendar fin = (Calendar) periodo.getFechaFinal().clone();

            // Calculamos la cuota por cada dia del periodo
            int mes = inicio.get(Calendar.MONTH);
            while (inicio.before(fin) || DateUtils.isSameDay(inicio, fin)) {

                if (mes != inicio.get(Calendar.MONTH)) {
                    mes = inicio.get(Calendar.MONTH);

                    redondeaCuotas(cuotas);
                    calcultaDescuento(cuotas,cuotas);
                    truncaDescuento(cuotas);
                    restarDescuentosAportacion(cuotas, cuotas);
                    sumaCuotas(cuotasCalculadas, cuotas);
                    cuotas = inicializaCalculoCero(valores.getCuotas());
                }

                cuotas = calculoDiario(inicio, cuotas, periodo, valores);
                inicio.add(Calendar.DATE, 1);
            }
        } else {
            int numeroPeriodos =  valores.getPeriodos().size();
            LOGGER.debug("------------->numeroPeriodos: " + numeroPeriodos);
            cuotas = obtenerAportacionesParciales(valores.getCuotas(), numeroPeriodos);
        }

        redondeaCuotas(cuotas);
        calcultaDescuento(cuotas,cuotas);
        truncaDescuento(cuotas);
        restarDescuentosAportacion(cuotas, cuotas);
        sumaCuotas(cuotasCalculadas, cuotas);

        periodoCalculado.setCuotas(cuotasCalculadas.toArray(new RamaCalculo[cuotasCalculadas.size()]));
        periodoCalculado.setTotal(getTotal(cuotasCalculadas));
        return periodoCalculado;
    }


    /**
     * Genera el calculo de las cuotas correspondientes a un dia especifico
     * tomando en cuenta si el dia es valido para calcular (recordemos que si un
     * trabajador se ausenta o incapacita no generan cobros)
     *
     * @param fecha
     *            la fecha para generar el calculo de las cuotas
     * @param calculos
     *            lista de cuotas a cobrar
     * @param periodo
     *            entidad con los valores de movimientos y descuentos que pueden
     *            aplican al calculo
     * @param valores
     *            Objeto con los valores del salario del empleado
     * @return lalista de cuotas calculadas
     */
    private List<RamaCalculo> calculoDiario(Calendar fecha, List<RamaCalculo> calculos,
                                            PeriodoCalculoCuota periodo, ValoresCalculoEmpleado valores) {

        // Si es un dia con un movimiento que no se calcula no hacemos nada
        if (!diaValidoCalculo(fecha, periodo.getMovimientos())) {
            return calculos;
        }
        // Obtenemos el descuento para el dia a calcular
        BigDecimal descuento = getDescuento(fecha, periodo.getDescuentos());
        // CAlculamos las cuotas para el dia indicado
        List<RamaCalculo> calculosNuevos = new ArrayList<RamaCalculo>();
        for (RamaCalculo calculo : calculos) {
            BigDecimal salario = BigDecimal.ZERO;
            // Obtenemos el salario dependiendo el tipo de rama a calcular
            switch (calculo.getIdRama()) {
                case ClavesRama.CUOTA_FIJA:

                    if (valores.getModalidad() == ModalidadEnum.TREINTAYCUATRO.getId() ||
                            valores.getModalidad() == ModalidadEnum.TREINTAYCINCO.getId() ||
                            valores.getModalidad() == ModalidadEnum.CUARENTAYTRES.getId() ||
                            valores.getModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId()) {

                        salario = valores.getSalarioCuotaFijaUma();
                    } else {
                        salario = valores.getSalarioCuotaFija();
                    }
                    break;
                case ClavesRama.EXCEDENTE:

                    if (valores.getModalidad() == ModalidadEnum.TREINTAYCUATRO.getId() ||
                            valores.getModalidad() == ModalidadEnum.TREINTAYCINCO.getId() ||
                            valores.getModalidad() == ModalidadEnum.CUARENTAYTRES.getId() ||
                            valores.getModalidad() == ModalidadEnum.CUARENTAYCUATRO.getId()) {

                        salario = valores.getSalarioExedenteUma();
                    } else {
                        salario = valores.getSalarioExedente();
                    }

                    break;
                default:
                    salario = valores.getSalarioCalculo();
                    break;
            }
            // sumamos el calculo del dia a lo que ya trae la cuota

            LOGGER.info("salario calculoDiario: {}",salario);

            BigDecimal aportacionDiaria = calculaSinDescuento(salario, calculo.getFactorCalculo());

            calculo.setAportacion(calculo.getAportacion().add(aportacionDiaria));
            calculo.setDescuento(descuento);
            calculosNuevos.add(calculo);
        }
        return calculosNuevos;
    }

    /**
     * GEnera el calculo de un pago dado el salario, el factor de cobro y el
     * descuento -- (Salario * factor) - descuento
     *
     * @param salario
     *            Cantidad para generar el calculo
     * @param factor
     *            el factor de cobro para la cuota (Este valor es porcentaje)
     * @param descuento
     *            El porcentaje de descuento que aplica al cobro
     * @return El total a cobrar dadas las cantidades
     */
    private BigDecimal calcula(BigDecimal salario, BigDecimal factor, BigDecimal descuento) {
        BigDecimal calculo = BigDecimal.ZERO;
        BigDecimal factorFinal = factor.divide(FACTOR_PORCENTAJE);
        // Calculamos la cuota
        calculo = salario.multiply(factorFinal);
        // calculamos el descuento sobre la cuota
        BigDecimal descuentoFinal = calculo.multiply(descuento.divide(FACTOR_PORCENTAJE));
        // Le quitamos el descuento a la cuota
        calculo = calculo.subtract(descuentoFinal);
        return calculo;
    }

    private BigDecimal calculaSinDescuento(BigDecimal salario, BigDecimal factor) {
        BigDecimal calculo = BigDecimal.ZERO;
        BigDecimal factorFinal = factor.divide(FACTOR_PORCENTAJE);
        // Calculamos la cuota
        calculo = salario.multiply(factorFinal);
        return calculo;
    }

    private BigDecimal calculaDescuento(BigDecimal calculo, BigDecimal descuento){
//         calculamos el descuento sobre la cuota
        BigDecimal descuentoFinal = calculo.multiply(descuento.divide(FACTOR_PORCENTAJE));
        return descuentoFinal;
    }

    /**
     * Inicializa una lista de ramas a cero
     *
     * @param ramas
     *            lal ista de ramas a inicializarce en ceros
     * @return una lista de ramas inicializada en ceros
     */
    private List<RamaCalculo> inicializaCalculoCero(List<RamaCalculo> ramas) {
        List<RamaCalculo> calculos = new ArrayList<RamaCalculo>();
        for (RamaCalculo rama : ramas) {
            calculos.add(MotorFactoryUtil.generaCalculoCero(rama));
        }
        return calculos;
    }

    private List<RamaCalculo> obtenerAportacionesParciales(
            List<RamaCalculo> cuotas, int numeroPeriodos) {
        List<RamaCalculo> calculos = new ArrayList<RamaCalculo>();

        for (RamaCalculo rama : cuotas) {
            calculos.add(MotorFactoryUtil.generaCalculoAportacionParcial(rama, numeroPeriodos));
        }

        return calculos;
    }

    /**
     * Valida que dada una fecha sea valida para generar su calculo LAs fechas
     * validas son aquellas que no se encuentren en movimientos de de ausentismo
     * o incapacidad
     *
     * @param fecha
     *            FEcha a verificar si es valida para el calculo
     * @param movimientos
     *            la lista de movimientos en los cu
     *            ales se verifican los
     *            ausentismos o incapacidades
     * @return true si es un dia valido para realizarse el calculo
     */
    private boolean diaValidoCalculo(Calendar fecha, List<MovimientoEmpleado> movimientos) {
        boolean valido = true;
        for (MovimientoEmpleado mov : movimientos) {
            Calendar fechaIni = Calendar.getInstance();
            fechaIni.setTime(mov.getFecha());
            Calendar fechaFin = (Calendar) fechaIni.clone();
            fechaFin.add(Calendar.DATE, mov.getDias());
            if (ArrayUtils.contains(MOVIMIENTOS_NO_CALCULO, mov.getTipoMovimiento())
                    && PeriodoUtil.isFechaContenida(fechaIni, fechaFin, fecha.getTime())) {
                valido = false;
                break;
            }
        }
        return valido;
    }

    /**
     * Dada una lista de descuentos, regresa el descuento que aplique para la
     * fecha indicada o cero si no hay descuentos.
     *
     * @param fecha
     *            la fecha en la cual se verifica la exisencia de un descuento
     * @param descuentos
     *            la lista de descuentos que pueden aplicar
     * @return La cantidad que aplicara para el descuento
     */
    private BigDecimal getDescuento(Calendar fecha, List<DescuentoBeneficio> descuentos) {
        BigDecimal descuento = BigDecimal.ZERO;
        if (descuentos != null) {
            for (DescuentoBeneficio dec : descuentos) {
                Calendar fechaIni = Calendar.getInstance();
                fechaIni.setTime(dec.getFecInicio());
                Calendar fechaFin = Calendar.getInstance();
                fechaFin.setTime(dec.getFechaFin());
                if (PeriodoUtil.isFechaContenida(fechaIni, fechaFin, fecha.getTime())) {
                    descuento = dec.getPorcentajeDescuento();
                    break;
                }
            }
        }
        return descuento;
    }

    /**
     * Obtiene el total de los cobros da una lista de cuotas
     *
     * @param cuotas
     *            lista de cuotas cobradas en un periodo
     * @return La sumatoria de las cuotas en la lista
     */
    private BigDecimal getTotal(List<RamaCalculo> cuotas) {
        BigDecimal total = BigDecimal.ZERO;
        for (RamaCalculo cuota : cuotas) {
            total = total.add(cuota.getAportacion());
        }
        return total;
    }

    /**
     * redondea las cantidades calculadas en las cuotas dadas las reglas IMSS
     *
     * @param cuotas
     *            las cuota a ser redondeadas
     */
    private void redondeaCuotas(List<RamaCalculo> cuotas) {
        for (RamaCalculo cuota : cuotas) {
            cuota.setAportacion(MotorFactoryUtil.redondeoCantidades(cuota.getAportacion()));
        }
    }

    /**
     *
     * Suma las cuotas de calculos de diferentes periodos de tiempo
     *
     * @param cuotasCalculadas
     *            las cuotas que guardaran el valor calculado
     * @param cuotasNuevas
     *            los valores de las cuotas a calcular
     */
    private void sumaCuotas(List<RamaCalculo> cuotasCalculadas, List<RamaCalculo> cuotasNuevas) {
        for (RamaCalculo cuotaC : cuotasCalculadas) {
            for (RamaCalculo cuotaN : cuotasNuevas) {
                if (RamaCalculoUtil.mismaCuota(cuotaC, cuotaN)) {
                    cuotaC.setAportacion(cuotaC.getAportacion().add(cuotaN.getAportacion()));
                }
            }
        }
    }

    /**
     * trunca los descuentos calculados en las cuotas dadas las reglas IMSS
     *
     * @param cuotas
     *            las cuota a ser redondeadas
     */
    private void truncaDescuento(List<RamaCalculo> cuotas) {
        for (RamaCalculo cuota : cuotas) {
            cuota.setDescuento(cuota.getDescuento().setScale(2, RoundingMode.DOWN));
        }
    }

    /**
     *
     * Suma los descuentos de calculos de diferentes periodos de tiempo
     *
     * @param cuotasCalculadas
     *            las cuotas que guardaran el valor calculado
     * @param cuotasNuevas
     *            los valores de las cuotas a calcular
     */
    private void sumarDescuentos(List<RamaCalculo> cuotasCalculadas, List<RamaCalculo> cuotasNuevas) {
        for (RamaCalculo cuotaC : cuotasCalculadas) {
            for (RamaCalculo cuotaN : cuotasNuevas) {
                if (RamaCalculoUtil.mismaCuota(cuotaC, cuotaN)) {
                    cuotaC.setDescuento(cuotaC.getDescuento().add(cuotaN.getDescuento()));
                }
            }
        }
    }

    /**
     *
     * Resta los descuentos de calculos de diferentes periodos de tiempo a las aportaciones
     *
     * @param cuotasCalculadas
     *            las cuotas que guardaran el valor calculado
     * @param cuotasNuevas
     *            los valores de las cuotas a calcular
     */
    private void restarDescuentosAportacion(List<RamaCalculo> cuotasCalculadas, List<RamaCalculo> cuotasNuevas) {
        for (RamaCalculo cuotaC : cuotasCalculadas) {
            for (RamaCalculo cuotaN : cuotasNuevas) {
                if (RamaCalculoUtil.mismaCuota(cuotaC, cuotaN)) {
                    cuotaC.setAportacion(cuotaC.getAportacion().subtract(cuotaN.getDescuento()));
                }
            }
        }
    }


    /**
     *
     * Resta los descuentos de calculos de diferentes periodos de tiempo a las aportaciones
     *
     * @param cuotasCalculadas
     *            las cuotas que guardaran el valor calculado
     * @param cuotasNuevas
     *            los valores de las cuotas a calcular
     */
    private void calcultaDescuento(List<RamaCalculo> cuotasCalculadas, List<RamaCalculo> cuotasNuevas) {
        for (RamaCalculo cuotaC : cuotasCalculadas) {
            for (RamaCalculo cuotaN: cuotasNuevas) {
                if (RamaCalculoUtil.mismaCuota(cuotaC, cuotaN)) {
                    BigDecimal descuento = cuotaN.getDescuento()!=null? cuotaN.getDescuento() : new BigDecimal(0);

                    BigDecimal descuentoFinal = cuotaN.getAportacion().multiply(descuento.divide(FACTOR_PORCENTAJE));
                    cuotaC.setDescuento(descuentoFinal);
                }
            }
        }
    }

    /**
     * Traduce la zona salaria del par ClaveEntidad - ClaveMunicipio a su zona correspondiente tipo 'A' o 'B' etc.
     * @param zonaSalarial
     * @param modalidad
     * @return
     */
    private String recuperaZonaSalarialOriginal(String zonaSalarial,Long modalidad, Date fechaConsulta){

        try {
            if (zonaSalarial != null) {
                LOGGER.info("LA ZONA SALARIA ES: " + zonaSalarial);
                String  zonaSalarialOriginal = salarioCalculoServiceLocal.obtenZonaSalarialOriginal(zonaSalarial, ModalidadEnum.fromId(modalidad),fechaConsulta);
                if(!zonaSalarialOriginal.trim().toUpperCase().equals("D")){
                    zonaSalarialOriginal="A";
                }
                LOGGER.info("La zona salarial original es: "+zonaSalarialOriginal +" y se setea en Calculo");
                return zonaSalarialOriginal;
            } else {
                LOGGER.info("NO se pudo recuperar la zona Salarial");
            }
        } catch (Exception suae) {
            LOGGER.error("Ocurrio un error al obtener el salario minimo: ",suae);

        }

        return null;
    }
}
