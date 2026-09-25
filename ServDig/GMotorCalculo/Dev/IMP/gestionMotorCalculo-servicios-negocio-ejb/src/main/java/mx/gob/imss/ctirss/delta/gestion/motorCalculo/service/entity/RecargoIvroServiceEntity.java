/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Factor;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.ParametrosEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.RecargoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.MotorFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.SuaUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.DatosValidacion;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.MesesEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementacion para el calculo de recargo
 * @author NOVUTECK1
 *
 */
@Stateless(name = "recargoServiceEntity", mappedName = "recargoServiceEntity")
public class RecargoIvroServiceEntity implements RecargoServiceLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RecargoIvroServiceEntity.class);
    
    @EJB
    private CobranzaServiceRemote cobranzaService;
	/**
	 * servicio de consulta e parametros
	 */
	@EJB
	private ParametrosEntityLocal parametrosEntity;
    
    /**
     * Factor para obtener el porcentaje de recargo en decimal
     */
    private static final BigDecimal CIEN = new BigDecimal("100");

    private static final int MAX_REINTENTO_MORA = 60;
    private static final int DIA_RECARGO = 17;

	/**
	 * valor de la rama de la tabla DIC_RAMA para CYV
	 */
	private static final Integer CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ = 9;

	/**
	 * Valor de la aportacion en  la tabla DIC_RAMA de tipo patronal
	 */
	private static final Integer TIPO_APORTACION_PATRONAL = 1;

	/**
	 * Modalidades IVRO
	 */
	private static final ModalidadEnum[] MODALIDADES_SIN_RECARGO = new ModalidadEnum[]{ModalidadEnum.TREINTAYCUATRO,ModalidadEnum.TREINTAYCINCO,ModalidadEnum.CUARENTAYTRES,ModalidadEnum.CUARENTAYCUATRO};

	/**
	 * Num de orden de primer periodo
	 */
	private static final int NUM_ORDEN_PRIMER_PERIODO = 1;

	/**
     * Servicio para obtener los parametros del sistema
     */
    @EJB
    private ParametrosEntityLocal parametrosEntityLocal;

    /**
     * Calcula el recargo de un calculo para un seguro ivro, este es se aplica un porcentaje al costo total
     * y este se divide entre el numero de pagos, dando asi el recargo de cada periodo de pago 
     */
    public CalculoCuota generaRecargos(CalculoCuota calculoCuota) throws SUAException {        
        LOGGER.debug("Calculando los recargos");
        BigDecimal factor = parametrosEntityLocal.getPorcentajeRecargo().divide(CIEN);
        BigDecimal recargototalCot = BigDecimal.ZERO;
        calculoCuota.setConRecargos(true);
        LOGGER.info("mod: "+calculoCuota.getModalidad());
        for (EmpleadoCuota empleado : calculoCuota.getEmpleados()) {
            
            BigDecimal recargototalEmpleado = BigDecimal.ZERO;
			BigDecimal recargoPeriodo;

            for (PeriodoCuota periodo : empleado.getPeriodos()) {

            	//Filtramos para evitar que le genere recargo si es el primer periodo y es mod 35,43,44 y 34 (Domestico)
				if(periodo.getOrden() == NUM_ORDEN_PRIMER_PERIODO && ArrayUtils.contains( MODALIDADES_SIN_RECARGO, ModalidadEnum.fromId(calculoCuota.getModalidad()) )){
					continue;
				}

				periodo.setCuotas(calculaRecargos(periodo.getCuotas(), factor, BigDecimal.ZERO));
				recargoPeriodo = recargoPeriodo(periodo.getCuotas());
				periodo.setFactorRecargo(factor);
				periodo.setCuotaRecargo(recargoPeriodo);
				recargototalEmpleado = recargototalEmpleado.add(recargoPeriodo);
				periodo.setTotal(periodo.getTotal().add(recargoPeriodo));

            }

            empleado.setCuotaRecargo(recargototalEmpleado);
            empleado.setCuotaTotal(empleado.getCuotaTotal().add(recargototalEmpleado));
            recargototalCot = recargototalCot.add(recargototalEmpleado);
        }

        calculoCuota.setCuotaRecargo(recargototalCot);
        calculoCuota.setCuotaTotal(calculoCuota.getCuotaTotal().add(recargototalCot));

        return calculoCuota;
    }

    @Override
	public CalculoCuota generaActualizacionesRecargos(CalculoCuota calculoCuota, BigDecimal porcentajeLey168, BigDecimal actualizacionLey168)
			throws SUAException {
		LOGGER.debug("Calculando los recargos");
		LOGGER.debug("El porcentaje para Ley 168 es: "+porcentajeLey168);
        LOGGER.debug("Actualizacion para Ley 168 es: "+actualizacionLey168);
		BigDecimal recargototalCot = BigDecimal.ZERO;
		BigDecimal actualizacionTotalCot = BigDecimal.ZERO;
		
		Calendar hoy = Calendar.getInstance();
		Calendar mesAnteriorFechaSolicitud = (Calendar) hoy.clone();

		for (EmpleadoCuota empleado : calculoCuota.getEmpleados()) {
			BigDecimal recargototalEmpleado = BigDecimal.ZERO;
			BigDecimal actualizacionTotalEmpleado = BigDecimal.ZERO;

			for (PeriodoCuota periodo : empleado.getPeriodos()) {
				BigDecimal recargoPeriodo = BigDecimal.ZERO;
				Calendar mesAnteriorInicioPeriodo = (Calendar) periodo.getInicioPeriodo().clone();

				/*
				 * Se obtiene el INPC de la tabla INPC de Banxico (tabla
				 * D_COP_FACTOR) por periodo
				 */

				Factor factorMesInicio = obtenerUltimoFactorPorPeriodo(mesAnteriorInicioPeriodo);
				Factor factorMesFin = obtenerUltimoFactorPorPeriodo(mesAnteriorFechaSolicitud);

				LOGGER.info("Mes periodo: "+periodo.getInicioPeriodo().get(Calendar.MONTH));
                LOGGER.info("factorMesInicio : "+factorMesInicio);
                LOGGER.info("factorMesFin : "+factorMesFin);

				if (calculoCuota.getAplicaRecargoPorFechaBaja() != null && calculoCuota.getAplicaRecargoPorFechaBaja()) {
					// INPC del mes anterior al inicio del periodo
					String strInpcMesInicio;
					if (factorMesInicio != null) {
						strInpcMesInicio = factorMesInicio.getInpc();
					} else {
						strInpcMesInicio = null;
					}
	
					BigDecimal inpcMesInicio;
					if (StringUtils.isNotBlank(strInpcMesInicio)) {
						inpcMesInicio = new BigDecimal(strInpcMesInicio);
					} else {
						inpcMesInicio = BigDecimal.ONE;
					}

					// INPC del mes anterior a la fecha de solicitud
					String strInpcMesFin;
					if (factorMesFin != null) {
						strInpcMesFin = factorMesFin.getInpc();
					} else {
						strInpcMesFin = null;
					}

					BigDecimal inpcMesFin;
					if (StringUtils.isNotBlank(strInpcMesFin)) {
						inpcMesFin = new BigDecimal(strInpcMesFin);
					} else {
						inpcMesFin = BigDecimal.ONE;
					}

					// Calculo del factor de Actualizacion
					BigDecimal factorActualizacion = inpcMesFin.divide(inpcMesInicio, 4, RoundingMode.DOWN);

					if ( factorActualizacion.compareTo(BigDecimal.ONE) < 0 ){
						factorActualizacion = BigDecimal.ONE;
					}

					LOGGER.debug("FACTOR DE ACTUALIZACION CALCULADO -> " + factorActualizacion + ": " + inpcMesFin + "/"+ inpcMesInicio);

					periodo.setCuotas(calculaActualizacion(periodo.getCuotas(), factorActualizacion, actualizacionLey168));

					BigDecimal cuotaActualizaciones = actualizacionPeriodo(periodo.getCuotas());

					periodo.setFactorActualizacion(factorActualizacion);
					periodo.setTotal(periodo.getTotal().add(cuotaActualizaciones));

					actualizacionTotalEmpleado = actualizacionTotalEmpleado.add(cuotaActualizaciones);

					LOGGER.debug("IMPORTE ACTUALIZADO -> " + periodo.getTotal());

					calculaRecargosRetroactivos(periodo, calculoCuota.getFechaFinCalculo());
					recargoPeriodo = periodo.getCuotaRecargo();
					
				} else {
					LOGGER.debug("Periodo actual, no se aplican recargos retroactivos");
					String strRecargo;
					if (factorMesInicio != null ) {
                            strRecargo = factorMesInicio.getRecargos();
					} else {
						strRecargo = null;
					}

					LOGGER.info("strRecargo: "+strRecargo);
					
					BigDecimal factorRecargo;
					if (StringUtils.isNotBlank(strRecargo)) {
						// TODO habilitar para leer porcentaje desde tabla de factores
						// factorRecargo = new BigDecimal(strRecargo);
						factorRecargo = parametrosEntityLocal.getPorcentajeRecargoCvro();
					} else {
						factorRecargo = BigDecimal.ZERO;
					}

                    int mesPeriodoInt = periodo.getInicioPeriodo().get(Calendar.MONTH);
                    int mesFinCalculo = calculoCuota.getFechaFinCalculo().get(Calendar.MONTH);
                    LOGGER.info("mesPeriodo: "+mesPeriodoInt +" mesFinCalculo: "+mesFinCalculo);

                    LOGGER.info("factorRecargo1: "+factorRecargo);
                    
                    Calendar today = Calendar.getInstance();
                    Calendar fechaLim =Calendar.getInstance();

                    DatosValidacion datos = new DatosValidacion();
                    datos.setModalidad(ModalidadEnum.CUARENTA.getId());
                    datos.setDiasFeriados(parametrosEntity.getDiasFeriados());
                    Date fechaLimitePago = SuaUtil.getSiguienteFechaHabil(PeriodoUtil.truncaFecha(today), datos);
                    fechaLim.setTime(fechaLimitePago);

                    LOGGER.info("fechaLim: "+fechaLim);
                    
                    int diaActual =  today.get(Calendar.DAY_OF_MONTH);
                    
                    if ((mesPeriodoInt==mesFinCalculo)&&(diaActual<=DIA_RECARGO)){
                    
                    //if ((mesPeriodoInt == mesFinCalculo)&&today.get(Calendar.DAY_OF_MONTH) <=  fechaLim.get(Calendar.DAY_OF_MONTH)) {
                        diaActual =  fechaLim.get(Calendar.DAY_OF_MONTH);
                        factorRecargo = BigDecimal.ZERO;
                        LOGGER.info("se setea factor en cero");
                    }
                    
                    LOGGER.info("factorRecargo2: "+factorRecargo);

                    //Se añade el factor de actualizacion para ley 168
                    if(actualizacionLey168!=null && !actualizacionLey168.equals(BigDecimal.ZERO)){

                        BigDecimal factorActualizacion = BigDecimal.ZERO;
                        LOGGER.info("Se calcula actualizacion para el periodo actual por ley 168: "+actualizacionLey168);

                        periodo.setCuotas(calculaActualizacionLey168(periodo.getCuotas(),actualizacionLey168));
                        BigDecimal cuotaActualizaciones = actualizacionPeriodo(periodo.getCuotas());
                        periodo.setFactorActualizacion(factorActualizacion);
                        periodo.setTotal(periodo.getTotal().add(cuotaActualizaciones));
                        actualizacionTotalEmpleado = actualizacionTotalEmpleado.add(cuotaActualizaciones);
                    }

                    LOGGER.info("dia actual para recargo retroactivo: "+diaActual);

					BigDecimal factorRecargoTotal = factorRecargo.divide(CIEN);
					LOGGER.debug("---> Factor recargo: " + factorRecargoTotal);
					periodo.setCuotas(calculaRecargos(periodo.getCuotas(), factorRecargoTotal,porcentajeLey168));
					recargoPeriodo = recargoPeriodo(periodo.getCuotas());

					periodo.setFactorRecargo(factorRecargoTotal);
					periodo.setCuotaRecargo(recargoPeriodo);
				}

				recargototalEmpleado = recargototalEmpleado.add(recargoPeriodo);
				periodo.setTotal(periodo.getTotal().add(recargoPeriodo));

				LOGGER.debug("Detalle del periodo con actualizaciones y recargos: \n"
						+ ToStringBuilder.reflectionToString(periodo, ToStringStyle.MULTI_LINE_STYLE));
			}

			empleado.setCuotaRecargo(recargototalEmpleado);
			empleado.setCuotaTotal(empleado.getCuotaTotal().add(actualizacionTotalEmpleado).add(recargototalEmpleado));

			recargototalCot = recargototalCot.add(recargototalEmpleado);
			actualizacionTotalCot = actualizacionTotalCot.add(actualizacionTotalEmpleado);
		}

		calculoCuota.setCuotaRecargo(recargototalCot);
		calculoCuota.setCuotaTotal(calculoCuota.getCuotaTotal().add(actualizacionTotalCot).add(recargototalCot));

		return calculoCuota;
	}

	private Factor obtenerUltimoFactorPorPeriodo(Calendar mesFechaPeriodoReferencia) {
		// Se obtiene el INPC del mes solicitado, si no esta publicado, se obtiene el ultimo INPC publicado
		int numReintentos = 0;
		Factor factorMes = null;
		Calendar mesFechaPeriodo = (Calendar) mesFechaPeriodoReferencia.clone();

		DateFormat df = new SimpleDateFormat("yyyyMM");
		do {
            LOGGER.info("mes periodo: "+mesFechaPeriodo.get(Calendar.MONTH)+" reintento: "+numReintentos);
			mesFechaPeriodo.add(Calendar.MONTH, -1);
			String periodoMes = df.format(mesFechaPeriodo.getTime());
			factorMes = cobranzaService.getFactorByPeriodo(periodoMes);

			numReintentos++;
		} while (numReintentos < MAX_REINTENTO_MORA && factorMes == null);

		return factorMes;
	}


    private Factor obtenerFactorPorPeriodo(Calendar mesFechaPeriodoReferencia) {
        // Se obtiene el INPC del mes solicitado, si no esta publicado, se obtiene el ultimo INPC publicado
        int numReintentos = 0;
        Factor factorMes = null;
        Calendar mesFechaPeriodo = (Calendar) mesFechaPeriodoReferencia.clone();

        DateFormat df = new SimpleDateFormat("yyyyMM");
        LOGGER.info("mes periodo1: "+mesFechaPeriodo.get(Calendar.MONTH));
        do {
            mesFechaPeriodo.add(Calendar.MONTH, -1);
            String prueba = df.format(mesFechaPeriodo.getTime());
            LOGGER.info("periodoMes format: "+prueba);

            LOGGER.info("mes periodo: "+mesFechaPeriodo.get(Calendar.MONTH)+" reintento: "+numReintentos);

            int cveMes = mesFechaPeriodo.get(Calendar.MONTH);
            int year =  mesFechaPeriodo.get(Calendar.YEAR);

            String month = MesesEnum.fromId(cveMes).getDescripcion();

            String periodoMes = year+month;
            LOGGER.info("periodoMes concat: "+periodoMes);

            factorMes = cobranzaService.getFactorByPeriodo(periodoMes);

            numReintentos++;
        } while (numReintentos < MAX_REINTENTO_MORA && factorMes == null);

        return factorMes;
    }


	/**
     * Calcula los recargos de las cutas sobre su saldo insoluto, regresando una
     * lista don los tipos de cuotas y su valor de recaro
     * 
     * @param cuotas
     *            la lista de cuotas con saldos insolutos
     * @param factorRecargo
     *            el factor para los recargos
     * @param porcentajeLey168
     *            el porcentaje de actualizacion para ajuste del la ley 168
     * @return la lista con los valores de recargos para cada tipo de cuota
     */
    private RamaCalculo[] calculaRecargos(RamaCalculo[] cuotas, BigDecimal factorRecargo,BigDecimal porcentajeLey168) {
        List<RamaCalculo> recargos = new ArrayList<RamaCalculo>();
        LOGGER.debug("Porcentaje Ley 168: "+porcentajeLey168+ " factorRecargo: "+factorRecargo);
        for (RamaCalculo cuota : cuotas) {
            cuota.setRecargo(MotorFactoryUtil.redondeoCantidades(
                    cuota.getAportacion().multiply(factorRecargo)));

				if((cuota.getIdRama().equals(CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ)&&
						cuota.getIdTipoAportacion().equals(TIPO_APORTACION_PATRONAL)) ){
                    cuota.setRecargo(cuota.getRecargo()!=null?cuota.getRecargo():BigDecimal.ZERO);
                    LOGGER.info("El porcentaje Ley 168 "+porcentajeLey168 + " se agrega para CYV Patronal, antes de suma: " +cuota.getRecargo());
					cuota.setRecargo(cuota.getRecargo().add(porcentajeLey168));
                    LOGGER.info("El porcentaje Ley 168 "+porcentajeLey168 + " se agrega para CYV Patronal, despues de suma: " +cuota.getRecargo());
				}

            recargos.add(cuota);
        }
        
        return recargos.toArray(new RamaCalculo[recargos.size()]);
    }
    
	/**
	 * Calcula los recargos de las cutas sobre su saldo actualizado
	 * <br>[(aportacion + actualizacion) * factorRecargo]
	 * <br>Regresando una lista don los tipos de
	 * cuotas y su valor de recaro
	 * 
	 * @param cuotas
	 *            la lista de cuotas con saldos insolutos
	 * @param factorRecargo
	 *            el factor para los recargos

	 * @return la lista con los valores de recargos para cada tipo de cuota
	 */
    private RamaCalculo[] calculaRecargosSobreSaldoActualizado(RamaCalculo[] cuotas, BigDecimal factorRecargo) {
        List<RamaCalculo> recargos = new ArrayList<RamaCalculo>();
        
        for (RamaCalculo cuota : cuotas) {

			cuota.setRecargo(MotorFactoryUtil.redondeoCantidades((cuota
					.getAportacion().add(cuota.getActualizacion()))
					.multiply(factorRecargo)));

            recargos.add(cuota);
        }
        
        return recargos.toArray(new RamaCalculo[recargos.size()]);
    }
    
	/**
	 * Calcula los recargos retroactivos para la modalidad 40. Se realiza la
	 * suma de los porcentajes de la siguiente forma. Se suman los porcentajes
	 * de forma mensual, si aplica recargos por fecha de solicitud se agrega el
	 * mes actual
	 * 
	 * @param periodo
	 */
	private void calculaRecargosRetroactivos(PeriodoCuota periodo, Calendar fechaFinCalculo) {

		LOGGER.info("se calcula retroactivo : "+periodo.getInicioPeriodo().get(Calendar.MONTH));
		Calendar fechaReferencia = Calendar.getInstance();
		fechaReferencia.set(Calendar.DATE, 2);

		Calendar mesPeriodo = (Calendar) periodo.getInicioPeriodo().clone();
		mesPeriodo.set(Calendar.DATE, 1);

		BigDecimal factorRecargoTotal = BigDecimal.ZERO;
		
		List<BigDecimal> recargosList = new ArrayList<BigDecimal>();

        LOGGER.info("mesPeriodo: "+mesPeriodo.toString());
		LOGGER.info("fechaReferencia: "+fechaReferencia.toString());

		while (mesPeriodo.before(fechaReferencia)) {

            Calendar mesPeriodoConsulta =(Calendar) mesPeriodo.clone();
            LOGGER.info("mesPeriodo while: "+mesPeriodo.toString());
            LOGGER.info("mesPeriodoConsulta: "+mesPeriodoConsulta.toString());
			Factor factorPeriodo = obtenerFactorPorPeriodo(mesPeriodoConsulta);

			String strRecargo;
			if (factorPeriodo != null) {
				strRecargo = factorPeriodo.getRecargos();
			} else {
				strRecargo = null;
			}

			BigDecimal factorRecargo;
			if (StringUtils.isNotBlank(strRecargo)) {
				factorRecargo = new BigDecimal(strRecargo);

				// TODO Deshabilitar para leer porcentaje desde tabla de factores
				if (factorRecargo.equals(BigDecimal.ZERO)) {
					factorRecargo = parametrosEntityLocal.getPorcentajeRecargoCvro();
				}
			} else {
				factorRecargo = BigDecimal.ZERO;
			}

            int mesPeriodoInt = mesPeriodo.get(Calendar.MONTH);
            int mesFinCalculo = fechaFinCalculo.get(Calendar.MONTH);
			LOGGER.info("mesPeriodo: "+mesPeriodoInt +" mesFinCalculo: "+mesFinCalculo);

            Calendar today = Calendar.getInstance();
			Calendar fechaLim =Calendar.getInstance();

            DatosValidacion datos = new DatosValidacion();
            datos.setModalidad(ModalidadEnum.CUARENTA.getId());
            datos.setDiasFeriados(parametrosEntity.getDiasFeriados());
            Date fechaLimitePago = SuaUtil.getSiguienteFechaHabil(PeriodoUtil.truncaFecha(today), datos);

			fechaLim.setTime(fechaLimitePago);

			int diaActual =  today.get(Calendar.DAY_OF_MONTH);
			if (today.get(Calendar.DAY_OF_MONTH) <  fechaLim.get(Calendar.DAY_OF_MONTH)) {
				diaActual =  fechaLim.get(Calendar.DAY_OF_MONTH);
			}

            LOGGER.info("dia actual para recargo retroactivo: "+diaActual);
            if ((mesPeriodoInt==mesFinCalculo)&&(diaActual<=DIA_RECARGO)){
                LOGGER.info("El recargo es cero debido a que es el mismo mes y es menor al dia de recargo");
                factorRecargo = BigDecimal.ZERO;
            }else{
                LOGGER.info("Si se genera recargo");
            }

			factorRecargoTotal = factorRecargoTotal.add(factorRecargo);
			mesPeriodo.add(Calendar.MONTH, 1);
			
			recargosList.add(factorRecargo);
		}

		LOGGER.debug("FACTORES USADOS PARA DETERMINAR FACTOR DE RECARGO FINAL -> "
				+ Arrays.toString(recargosList.toArray()));
		
		LOGGER.debug("FACTOR DE RECARGO CALCULADO -> " + factorRecargoTotal);

		BigDecimal factorRecargo = factorRecargoTotal.divide(CIEN);
		
		LOGGER.debug("FACTOR DE RECARGO -> " + factorRecargo);

		periodo.setCuotas(calculaRecargosSobreSaldoActualizado(periodo.getCuotas(), factorRecargo));
		
		BigDecimal cuotaRecargo = recargoPeriodo(periodo.getCuotas());
		
		periodo.setFactorRecargo(factorRecargo);
		periodo.setCuotaRecargo(cuotaRecargo);
		
		LOGGER.debug("IMPORTE DE RECARGOS -> " + cuotaRecargo);
	}
    
	private boolean aplicaRecargo() {
		boolean aplicaRecargo = false;

		Calendar fechaHoy = Calendar.getInstance();
		Calendar fechaPago = Calendar.getInstance();
		/*
		   Aplicarán recargos y actualizaciones para el mes en curso,
		   cuando la fecha de solicitud a la inscripción inicial o
		   renovación sea POSTERIOR al día 17 de dicho mes.
		*/
    	fechaPago.set(Calendar.DATE, DIA_RECARGO+1);

		Date fechaLimitePago = PeriodoUtil.truncaFecha(fechaPago).getTime();

		LOGGER.debug("Fecha hoy {} ", fechaHoy);
		LOGGER.debug("Fecha pago calculado {}", fechaLimitePago);
		if (fechaLimitePago.before(fechaHoy.getTime())) {
			aplicaRecargo = true;
		}

		LOGGER.debug("aplicaRecargo {}", aplicaRecargo);
		return aplicaRecargo;
	}

	private RamaCalculo[] calculaActualizacion(RamaCalculo[] cuotas,
			BigDecimal factorActualizacion, BigDecimal actualizacionLey168) {
		List<RamaCalculo> actualizaciones = new ArrayList<RamaCalculo>();

        LOGGER.debug("Actualizacion Ley 168: "+actualizacionLey168+ " factorActualizacion: "+factorActualizacion);

		for (RamaCalculo cuota : cuotas) {
			BigDecimal coutaActualizada = MotorFactoryUtil
					.redondeoCantidades(cuota.getAportacion().multiply(
							factorActualizacion));
			cuota.setActualizacion(coutaActualizada.subtract(cuota
					.getAportacion()));

			if((cuota.getIdRama().equals(CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ)&&
					cuota.getIdTipoAportacion().equals(TIPO_APORTACION_PATRONAL)) ){
				cuota.setActualizacion(cuota.getActualizacion()!=null?cuota.getActualizacion():BigDecimal.ZERO);
				LOGGER.info("Actualizacion Ley 168 "+actualizacionLey168 + " se agrega para CYV Patronal, antes de suma: " +cuota.getActualizacion());
				cuota.setActualizacion(cuota.getActualizacion().add(actualizacionLey168));
				LOGGER.info("Actualizacion Ley 168 "+actualizacionLey168 + " se agrega para CYV Patronal, despues de suma: " +cuota.getActualizacion());
			}


			actualizaciones.add(cuota);
		}

		return actualizaciones.toArray(new RamaCalculo[actualizaciones.size()]);
	}

    private BigDecimal recargoPeriodo(RamaCalculo[] cuotas) {
        BigDecimal total = BigDecimal.ZERO;
        for (RamaCalculo cuota : cuotas) {
            total = total.add(cuota.getRecargo());
        }
        return total;
    }
		
	private BigDecimal actualizacionPeriodo(RamaCalculo[] cuotas) {

		BigDecimal total = BigDecimal.ZERO;

		for (RamaCalculo cuota : cuotas) {
			total = total.add(cuota.getActualizacion());
		}

		return total;
	}

    @Override
    public CalculoCuota generaRecargosLey168(CalculoCuota calculoCuota, BigDecimal porcentajeLey168, BigDecimal actualizacionLey168)
            throws SUAException {
        LOGGER.debug("Recargo para Ley 168");
        LOGGER.debug("El porcentaje para Ley 168 es: "+porcentajeLey168);
        BigDecimal recargototalCot = BigDecimal.ZERO;
        BigDecimal actualizacionTotalCot = BigDecimal.ZERO;


        for (EmpleadoCuota empleado : calculoCuota.getEmpleados()) {
            BigDecimal recargototalEmpleado = BigDecimal.ZERO;
            BigDecimal actualizacionTotalEmpleado = BigDecimal.ZERO;

            BigDecimal factorActualizacion = BigDecimal.ZERO;


            for (PeriodoCuota periodo : empleado.getPeriodos()) {
                BigDecimal recargoPeriodo = BigDecimal.ZERO;
                BigDecimal factorRecargoTotal = BigDecimal.ZERO;

                periodo.setCuotas(calculaActualizacionLey168(periodo.getCuotas(), actualizacionLey168));

                BigDecimal cuotaActualizaciones = actualizacionPeriodo(periodo.getCuotas());

                periodo.setFactorActualizacion(factorActualizacion);
                periodo.setTotal(periodo.getTotal().add(cuotaActualizaciones));

                actualizacionTotalEmpleado = actualizacionTotalEmpleado.add(cuotaActualizaciones);

                LOGGER.debug("IMPORTE ACTUALIZADO -> " + periodo.getTotal());



                periodo.setCuotas(calculaRecargos(periodo.getCuotas(), factorRecargoTotal,porcentajeLey168));
                recargoPeriodo = recargoPeriodo(periodo.getCuotas());

                periodo.setFactorRecargo(factorRecargoTotal);
                periodo.setCuotaRecargo(recargoPeriodo);

                recargototalEmpleado = recargototalEmpleado.add(recargoPeriodo);
                periodo.setTotal(periodo.getTotal().add(recargoPeriodo));

                LOGGER.debug("Detalle del periodo con actualizaciones y recargos: \n"
                        + ToStringBuilder.reflectionToString(periodo, ToStringStyle.MULTI_LINE_STYLE));
            }

            empleado.setCuotaRecargo(recargototalEmpleado);
            empleado.setCuotaTotal(empleado.getCuotaTotal().add(actualizacionTotalEmpleado).add(recargototalEmpleado));

            recargototalCot = recargototalCot.add(recargototalEmpleado);
            actualizacionTotalCot = actualizacionTotalCot.add(actualizacionTotalEmpleado);
        }

        calculoCuota.setCuotaRecargo(recargototalCot);
        calculoCuota.setCuotaTotal(calculoCuota.getCuotaTotal().add(actualizacionTotalCot).add(recargototalCot));

        return calculoCuota;
    }

	private RamaCalculo[] calculaActualizacionLey168(RamaCalculo[] cuotas, BigDecimal actualizacionLey168) {
		List<RamaCalculo> actualizaciones = new ArrayList<RamaCalculo>();

		LOGGER.debug("Actualizacion Ley 168: "+actualizacionLey168);

		for (RamaCalculo cuota : cuotas) {

			cuota.setActualizacion(BigDecimal.ZERO);

			if((cuota.getIdRama().equals(CESANTIA_EN_EDAD_AVANZADA_Y_VEJEZ)&&
					cuota.getIdTipoAportacion().equals(TIPO_APORTACION_PATRONAL)) ){
                LOGGER.info("Actualizacion CYV Patronal antes de la suma: " +cuota.getActualizacion());
				cuota.setActualizacion(actualizacionLey168);
				LOGGER.info("Actualizacion Ley 168 "+actualizacionLey168 + " se agrega para CYV Patronal, despues de suma: " +cuota.getActualizacion());
			}

			actualizaciones.add(cuota);
		}

		return actualizaciones.toArray(new RamaCalculo[actualizaciones.size()]);
	}
}
