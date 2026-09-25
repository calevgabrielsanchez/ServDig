/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.entity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces.GeneradorSumarioYValidacionLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.SuaUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.VersionSUAIvroEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.DatosValidacion;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.PeriodoSUA;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesTipoMovimiento;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.digital.modelo.cobranza.Movimiento;
import mx.gob.imss.digital.modelo.cobranza.RegistroValidacion;
import mx.gob.imss.digital.modelo.cobranza.SumarioPatronal;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;

import org.apache.commons.lang.StringUtils;

/**
 * Servicio para la generacion del registro de validacion y sumario de un
 * archivo SUA
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "generadorSumarioYValidacionEntity", mappedName = "generadorSumarioYValidacionEntity")
public class GeneradorSumarioYValidacionEntity implements GeneradorSumarioYValidacionLocal {

    /**
     * tamano en bytes de cara regitro contenido en el archivo
     */
    private static final int SIZE_REGISTRO = 295;
    /**
     * Numero de registros fijos en el archivo sual (Patron, Sumario y
     * Validacion)
     */
    private static final int REGISTROS_FIJOS = 3;
    /**
     * Factor de reversion
     */
    private static final String FACTOR_REVERSION = "   ";
    /**
     * Día del mes en que se deben hacer los pagos
     */
    private static final int DIA_PAGO = 25;
    /**
     * Dia del mes por defecto en el que se debe situar la fecha limite de pago para la modalidad 40 CVRO
     */
    private static final int DIA_PAGO_MOD_40 = 17;
    /**
     * Numero maximo de movimientos por cada registro en el archivo sua
     */
    private static final int MAX_MOVS_REGISTRO = 7;
    /**
     * VAlor default de un checksum, este se sobreescribira cuando se obtenga el archivo
     * y se aplique el agoritmo correspondiente para crearlo
     */
    private static final long CHECKSUM_DEF = 1234567891234567890L;

    /**
     * GEnera el registro del Sumario para un archivo SUA a partir de los datos del trabajador, sus cuotas y  
     * movimientos
     * @param periodoSua el objeto que contiene los trabajadores y factores deactualizacion para el sumario
     * @return El registro con la sumatoria de cuotas en el archivo SUA
     */
    @Override
    public SumarioPatronal generaSumario(PeriodoSUA periodoSua) {
        SumarioPatronal sumario = calculaSumatorias(periodoSua.getTrabajadores());
        sumario.setTasaDeActualizacionEmpleada(SuaUtil.getValorNoNulo(periodoSua.getFactorActualizacion()));
        sumario.setTasaDeRecargosEmpleada(SuaUtil.getValorNoNulo(periodoSua.getFactorRecargo()));
        agregaValoresSumarioDefault(sumario);
        return sumario;
    }

    /**
     * Calcula la sumatario de las cuotas de trabajador para el regisro
     * 
     * @param trabajadores la lista de trabajadores y sus cuotas cobradas
     * @return El registro del sumario, co la sumatroria de los cobros a ls trabajadores
     */
    private SumarioPatronal calculaSumatorias(List<Trabajador> trabajadores) {
        SumarioPatronal sumario = new SumarioPatronal();
        
        
        for (Trabajador trabajador : trabajadores) {

            sumario.setCuotaFijaDeEnfermedadYMaternidad(sumario
                    .getCuotaFijaDeEnfermedadYMaternidad().add(
                            trabajador.getCuotaFijaDeEnfermedadYMaternidad()));
            sumario.setCuotaExcedenteDeEnfermedadYMaternidad(sumario
                    .getCuotaExcedenteDeEnfermedadYMaternidad().add(
                            trabajador.getCuotaExcedenteDeEnfermedadYMaternidad()));
            sumario.setPrestacionesEnDineroDeEnfermedadYMaternidad(sumario
                    .getPrestacionesEnDineroDeEnfermedadYMaternidad().add(
                            trabajador.getPrestacionesEnDineroDeEnfermedadYMaternidad()));
            sumario.setGastosMedicosPensionadosArt25(sumario.getGastosMedicosPensionadosArt25()
                    .add(trabajador.getGastosMedicosPensionadosArt25()));
            sumario.setRiesgosDeTrabajo(sumario.getRiesgosDeTrabajo().add(
                    trabajador.getRiesgosDeTrabajo()));
            sumario.setInvalidezYVida(sumario.getInvalidezYVida().add(
                    trabajador.getInvalidezYVida()));
            sumario.setGuarderiaYPrestacionesSociales(sumario.getGuarderiaYPrestacionesSociales()
                    .add(trabajador.getGuarderiaYPrestacionesSociales()));
            sumario.setRetiro(sumario.getRetiro().add(trabajador.getRetiro()));
            sumario.setCesantiaYVejez(sumario.getCesantiaYVejez()
                    .add(trabajador.getCesantiaYVejezCuotaPatronal())
                    .add(trabajador.getCesantiaYVejezCuotaTrabajador()));
            sumario.setAportacionesVoluntarias(sumario.getAportacionesVoluntarias().add(
                    trabajador.getAportacionVoluntaria()));
            sumario.setAportacionPatronalINFONAVITParaCuentaIndividual(sumario
                    .getAportacionPatronalINFONAVITParaCuentaIndividual().add(
                            trabajador.getAportacionPatronalINFONAVIT()));
            sumario.setAportacionPatronalINFONAVITParaAmortizacionDeCreditos(sumario
                    .getAportacionPatronalINFONAVITParaAmortizacionDeCreditos().add(
                            trabajador.getAportacionPatronalINFONAVIT()));
            sumario.setAmortizacionDeCreditoINFONAVIT(sumario.getAmortizacionDeCreditoINFONAVIT()
                    .add(trabajador.getAmortizacionDeCreditoINFONAVIT()));

            for (Movimiento mov : trabajador.getMovimientos()) {
                if (mov.getTipoDeMovimientoOIncidencia() == ClavesTipoMovimiento.APORTACION) {
                    sumario.setAportacionesComplementarias(sumario.getAportacionesComplementarias()
                            .add(mov.getSalarioDiarioIntegrado()));
                }
            }
            
            sumario.setActualizacionDeLosCuatroSegurosIMSS(sumario.getActualizacionDeLosCuatroSegurosIMSS().add(
                    trabajador.getActualizacionCuatroSegurosIMSS()));
            sumario.setRecargosDeLosCuatroSegurosIMSS(sumario.getRecargosDeLosCuatroSegurosIMSS().add(
                    trabajador.getRecargosCuatroSegurosIMSS()));
            
            sumario.setActualizacionDeRetiroCesantiaYVejez(sumario.getActualizacionDeRetiroCesantiaYVejez().add(
                    trabajador.getActualizacionCesantiaYVejez()));
            sumario.setActualizacionDeRetiroCesantiaYVejez(sumario.getActualizacionDeRetiroCesantiaYVejez().add(
                    trabajador.getActualizacionRetiro()));
            sumario.setRecargosDeRetiroCesantiaYVejez(sumario.getRecargosDeRetiroCesantiaYVejez().add(
                    trabajador.getRecargosCesantiaYVejez()));
            sumario.setRecargosDeRetiroCesantiaYVejez(sumario.getRecargosDeRetiroCesantiaYVejez().add(
                    trabajador.getRecargosRetiro()));          
            
        }

        sumario.setSubtotalDeCuatroSegurosIMSS(sumario
                .getCuotaFijaDeEnfermedadYMaternidad()
                .add(sumario.getCuotaExcedenteDeEnfermedadYMaternidad()
                        .add(sumario.getPrestacionesEnDineroDeEnfermedadYMaternidad()
                                .add(sumario.getGastosMedicosPensionadosArt25()
                                        .add(sumario.getRiesgosDeTrabajo()
                                                .add(sumario.getInvalidezYVida()
                                                        .add(sumario.getGuarderiaYPrestacionesSociales())))))));

        sumario.setSubtotalRetiroCesantiaYVejez(sumario.getRetiro()
                .add(sumario.getCesantiaYVejez()));
        
        return sumario;
    }

    /**
     * Agrega los valores no calculables del sumario
     * 
     * @param sumario Registro al cual se agregaran los valores default
     */
    private void agregaValoresSumarioDefault(SumarioPatronal sumario) {
        sumario.setFiller(SuaUtil.FILLER);
        sumario.setFolioDeRequerimiento(SuaUtil.FILLER);        

        // Valores de infonavit y fundemex no los maneja el IMSS
        sumario.setMultasINFONAVIT(BigDecimal.ZERO);
        sumario.setDonativoFUNDEMEX(BigDecimal.ZERO);
        sumario.setActualizacionDeAportacionPatronalYAmortizacionDeCreditoINFONAVIT(BigDecimal.ZERO);
        sumario.setRecargosDeAportacionPatronalYAmortizacionDeCreditoINFONAVIT(BigDecimal.ZERO);
        sumario.setAportacionPatronalINFONAVITParaAmortizacionDeCreditos(BigDecimal.ZERO);
        sumario.setAportacionPatronalINFONAVITParaCuentaIndividual(BigDecimal.ZERO);
    }

    /**
     * GEnera el registro de validacion para un archivo SUA
     * @param datos Objeto que contiene la informacion para generar un registro de validacion,
     * trabajadores, fechaInicio, sumario, diasFeriados
     * @return El regitro con los datos de validacion para un archivo SUA
     * @throws SUAException Errores generados por no cumplir las condiciones al
     *  generar el registro de validacion
     */
    @Override
    public RegistroValidacion generaRegistroValidacion(DatosValidacion datos) throws SUAException {
        List<Trabajador> trabajadores = datos.getTrabajadores();
        SumarioPatronal sumario = datos.getSumario();

        RegistroValidacion validacion = new RegistroValidacion();

        validacion.setSumaDeTasasDeRecargosYActualizacionQueSeAplico(sumario
                .getTasaDeActualizacionEmpleada().add(sumario.getTasaDeRecargosEmpleada()));

        validacion.setTamanioDelArchivoEnBytes(sizeFile(trabajadores));

        validacion.setTotalADepositarEnCuentaIMSS4RSS(sumario.getSubtotalDeCuatroSegurosIMSS().add(
                sumario.getActualizacionDeLosCuatroSegurosIMSS().add(
                        sumario.getRecargosDeLosCuatroSegurosIMSS())));
        validacion.setTotalADepositarEnCuentaIMSSRCV(sumario.getSubtotalRetiroCesantiaYVejez().add(
                sumario.getActualizacionDeRetiroCesantiaYVejez().add(
                        sumario.getRecargosDeRetiroCesantiaYVejez().add(
                                sumario.getAportacionesVoluntarias().add(
                                        sumario.getAportacionesVoluntarias())))));
        validacion.setTotalADepositarINFONAVITAportacionPatronal(sumario
                .getAportacionPatronalINFONAVITParaCuentaIndividual());
        validacion
                .setTotalADepositarINFONAVITAmortizacionCredito(sumario
                        .getAportacionPatronalINFONAVITParaAmortizacionDeCreditos()
                        .add(sumario
                                .getAmortizacionDeCreditoINFONAVIT()
                                .add(sumario
                                        .getActualizacionDeAportacionPatronalYAmortizacionDeCreditoINFONAVIT()
                                        .add(sumario
                                                .getRecargosDeAportacionPatronalYAmortizacionDeCreditoINFONAVIT()
                                                .add(sumario.getMultasINFONAVIT().add(
                                                        sumario.getDonativoFUNDEMEX()))))));

        agregaValoresValidacionDefault(validacion);
        validacion.setNumeroDeDiscos(1);                
        validacion.setFechaLimiteDePago(getFechaPago(datos));
        validacion.setCheckSum(CHECKSUM_DEF);
        validacion.setVersionSUA(getVersionSUA(datos.getVersionSUA(), datos.getModalidad()));

        return validacion;
    }
    
    /**
     * Obtiene la fecha de pago para el servicio SUA
     * @param datos Datos necesarios para generar la fecha de pago:
     * - fechaInicio FEcha de inicio del periodo a pagar
     * - conBeneficio bandera que indica si es con beneficio el pago
     * - diasFeriados lista de dias feriados para poder obtener la fecha
     * - renovacion, indica si el pariodo de pago es con renovacion
     * @return la fecha en la que se realiza el pago 
     */
	private Date getFechaPago(DatosValidacion datos) {
		// PAra IVRO la fecha de pago es el 25 antes del mes de inicio del
		// periodo si tiene beneficio, en caso contrario es el ultimo dia habil
		// del mes anterior
		// a entrar en vigor su seguro
		Calendar fechaPago;
		Date fechaLimitePago;

		if (datos.getModalidad() == ModalidadEnum.CUARENTA.getId()) {
			fechaPago = (Calendar) datos.getFechaInicio().clone();
			fechaPago.set(Calendar.DATE, DIA_PAGO_MOD_40);

			fechaLimitePago = SuaUtil.getSiguienteFechaHabil(PeriodoUtil.truncaFecha(fechaPago), datos);
		} else {
			fechaPago = getMesPago(datos.getFechaInicio(), datos.getRenovacion());

			if (datos.isConbeneficio()) {
				fechaPago.set(Calendar.DATE, DIA_PAGO);
			}

			if (datos.getModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
                fechaLimitePago = SuaUtil.getFechaHabil(PeriodoUtil.truncaFecha(fechaPago),
                        datos.getDiasFeriados());
			} else {
				fechaLimitePago = SuaUtil.getFechaHabil(PeriodoUtil.truncaFecha(fechaPago),
						datos.getDiasFeriados());
			}
		}

		if (datos.getModalidad() == ModalidadEnum.CUARENTA.getId()) {
			Calendar fechaReferencia = (Calendar) datos.getFechaReferencia().clone();
			fechaReferencia.set(Calendar.DATE, 1);

			if (datos.getAplicaRecargoPorFechaBaja() != null
					&& datos.getAplicaRecargoPorFechaBaja()) {
				Calendar fechaSolicitud = Calendar.getInstance();
				if (fechaLimitePago.before(fechaReferencia.getTime())) {
					fechaPago = (Calendar) fechaReferencia.clone();
					fechaPago.set(Calendar.DATE, DIA_PAGO_MOD_40);

					if (fechaPago.before(fechaSolicitud)) {
						fechaLimitePago = SuaUtil.getSiguienteFechaHabil(PeriodoUtil.truncaFecha(fechaSolicitud), datos);
					} else {
						fechaLimitePago = SuaUtil.getSiguienteFechaHabil(PeriodoUtil.truncaFecha(fechaPago), datos);
					}
				} else if (fechaPago.before(fechaSolicitud)) {
					fechaLimitePago = SuaUtil.getSiguienteFechaHabil(PeriodoUtil.truncaFecha(fechaSolicitud), datos);
				} else {
					fechaLimitePago = SuaUtil.getSiguienteFechaHabil(PeriodoUtil.truncaFecha(fechaPago), datos);
				}
			} else if (fechaLimitePago.before(datos.getFechaInicio().getTime())) {
				fechaLimitePago = SuaUtil.getSiguienteFechaHabil(datos.getFechaInicio(), datos);
			}
		}

		return fechaLimitePago;
	}
    
    /**
     * GEnera la fecha de fin de mes a realizar el pago, esto es si es una contratacion nueva
     * se genera la fecha con el mes anterior al inicio del periodo, 
     * en caso de ser una renovacion se verifica si el inicio es el mes actual se toma como fecha 
     * de pago , en caso contrario es el mes anterior al inicio del periodo
     * @param fechaInicio la fecha de inicio del periodo
     * @param renovacion indica si el pago es renovacion 
     * @return la fecha a final de mes en donde se debe generar el pago
     */
    private Calendar getMesPago(Calendar fechaInicio, Boolean renovacion) {
        Calendar fechaPago = (Calendar) fechaInicio.clone();
        fechaPago.set(Calendar.DAY_OF_MONTH, 1);
        fechaPago.add(Calendar.DATE, -1);
        return fechaPago;
    }

    /***
     * Obtiene el tama�o del archivo SUA a generar
     * 
     * @param trabajadores lista de trabajadore spresentes en el archivo SUA
     * @return El tama�o del archivo que se generara
     */
    private int sizeFile(List<Trabajador> trabajadores) {
        int numTrabajadores = trabajadores.size();
        int numMovs = 0;
        for (Trabajador trabajador : trabajadores) {
            numMovs = numMovs + trabajador.getMovimientos().length;
        }
        int numRegMovs = numMovs % MAX_MOVS_REGISTRO == 0 ? numMovs / MAX_MOVS_REGISTRO 
                : (numMovs / MAX_MOVS_REGISTRO) + 1;

        // El tamano del archivo final es igual al numero de registros por el
        // tamano de cada registro
        return ((REGISTROS_FIJOS + numTrabajadores + numRegMovs) * SIZE_REGISTRO);
    }
    
    /**
     * Servicio utilitario que obtiene la version SUA del archivo, esta puede ser enviada por el 
     * servicio que la pide, en caso contrario se toma la regla default de IVRO para obtenerlo
     * @param version version sua enviada en la peticion del servicio
     * @param modalidad la modalidad a la que pertenece el patron
     * @return LA version SUA para generar el archivo
     * @throws SUAException Error al no recibir ninguna version y no poder generarse con las reglas default
     */
    private String getVersionSUA(String version, long modalidad) throws SUAException {
        if (StringUtils.trimToNull(version) == null) {
            VersionSUAIvroEnum versionE = VersionSUAIvroEnum.fromId(modalidad);
            if (versionE == null) {
                throw new SUAException(SUAConstants.COD_VERSION_SUA, SUAConstants.MSG_VERSION_SUA);
            }
            return versionE.getVersion();
        }
        return version;
    }

    /**
     * Agrega los valores no calculables del registro Varios de estos valores
     * son default para el IVRO si se requieren agregar para otras modalidades
     * hay que agregarlos en la entrada del servicio y agregarlos en esta
     * asignacion
     * 
     * @param validacion registro al cual se agregan los valores default
     */
    private void agregaValoresValidacionDefault(RegistroValidacion validacion) {

        validacion.setFiller(SuaUtil.FILLER);
        validacion.setFactorDeAusentismo(BigDecimal.ZERO);

        // Los facores de reversion no son manejados por el IMSS por lo cual se
        // van en ceros
        validacion.setFactorDeReversionCF(FACTOR_REVERSION);
        validacion.setFactorDeReversionExcPatronal(FACTOR_REVERSION);
        validacion.setFactorDeReversionExcObrero(FACTOR_REVERSION);
        validacion.setFactorDeReversionPDPatronal(FACTOR_REVERSION);
        validacion.setFactorDeReversionPDObrero(FACTOR_REVERSION);
        validacion.setFactorDeReversionGMPPatronal(FACTOR_REVERSION);
        validacion.setFactorDeReversionGMPObrero(FACTOR_REVERSION);
        validacion.setFactorDeReversionRT(FACTOR_REVERSION);
        validacion.setFactorDeReversionIVPatronal(FACTOR_REVERSION);
        validacion.setFactorDeReversionIVObrero(FACTOR_REVERSION);
        validacion.setFactorDeReversionGPS(FACTOR_REVERSION);
        agregaCuotasReversion(validacion);
    }

    /**
     * Agrega los valores de cuota en ceros por que sn para calculos de
     * reversion los cuales el imss no maneja
     * 
     * @param validacion registro al cual se agregan los valores default
     */
    private void agregaCuotasReversion(RegistroValidacion validacion) {
        validacion.setCuotaFija(BigDecimal.ZERO);
        validacion.setCuotaExcedentePatronal(BigDecimal.ZERO);
        validacion.setPrestacionesDineroPatronal(BigDecimal.ZERO);
        validacion.setGastosMedicosPensionadosPatronal(BigDecimal.ZERO);
        validacion.setRiesgosDeTrabajo(BigDecimal.ZERO);
        validacion.setInvalidezYVidaPatronal(BigDecimal.ZERO);
        validacion.setGuarderiaYPrestacionesSociales(BigDecimal.ZERO);

        validacion.setCuotaExcedenteObrero(BigDecimal.ZERO);
        validacion.setPrestacionesDineroObrero(BigDecimal.ZERO);
        validacion.setGastosMedicosPensionadosObrero(BigDecimal.ZERO);
        validacion.setInvalidezYVidaObrero(BigDecimal.ZERO);
        
    }
    
}
