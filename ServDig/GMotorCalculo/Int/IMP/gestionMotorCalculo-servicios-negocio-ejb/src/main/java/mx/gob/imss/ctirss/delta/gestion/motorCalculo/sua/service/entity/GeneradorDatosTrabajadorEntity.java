/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces.GeneradorDatosTrabajadorLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.SuaUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesRama;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesTipoAportacion;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesTipoMovimiento;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.Movimiento;
import mx.gob.imss.digital.modelo.cobranza.MovimientoEmpleado;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servico para generar trabajadores a artir de los datos de sus cuotas
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "generadorDatosTrabajadorEntity", mappedName = "generadorDatosTrabajadorEntity")
public class GeneradorDatosTrabajadorEntity implements GeneradorDatosTrabajadorLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneradorDatosTrabajadorEntity.class);
    /**
     * RFC default para los trabajadores
     */
    private static final String RFC_DEFAULT = "AAAA010101";
    /**
     * Tipo de trabajador
     */
    private static final int TIPO_TRABAJADOR = 1;
    /**
     * Valor para indicar una jornada completa de trabajo
     */
    private static final int JORNADA_COMPLETA = 0;
    /**
     * Caracter vacio
     */
    private static final String VACIO = "";

    /**
     * Genera el registro de un TRabajador para el archivo sua a partir de la
     * persona fisica, las cuotas y el periodo de cobro
     * 
     * @param empleado
     *            Cuotas que genero el empleado en el periodo de cobro
     * @param persona
     *            LA persona fisica de la cual se obtiene la informacion de un
     *            trabajador
     * @param periodo
     *            El periodo de cobro para el trabajador
     * @return Regresa el registro genrado para el archivo sua con los datos de
     *         un trabaador, sus cuotas y movimientos
     * @throws SUAException
     *             Si ocurren error en la generacion de los datos de un
     *             trabajador
     */
    @Override
    public Trabajador generaTrabajador(EmpleadoCuota empleado, Fisica persona, PeriodoCuota periodo, Long idModalidad)
            throws SUAException {

        Trabajador trabajador = generaDatosTrabajador(persona);
        trabajador.setUltimoSalarioDiarioIntegradoDelPeriodo(periodo.getSalarioPeriodo());
        trabajador.setTipoDeTrabajador(SuaUtil.obtenTipoTrabajdor(empleado,idModalidad.intValue()));
        trabajador.setMovimientos(generaMovimientosTrabajador(periodo, empleado.getNumeroSeguridadSocial()));
        trabajador.setNumeroDeMovimientosEnElPeriodo(trabajador.getMovimientos().length);
        agregaCuotasTrabajador(trabajador, periodo.getCuotas());
        agregaValoresCodificados(trabajador);
        agregaDiasCotizados(trabajador, periodo, idModalidad);
        agregaRecargosYActualizaciones(trabajador, periodo);
        return trabajador;
    }

    /**
     * Genera un movimiento default, esto es para los periodos donde los
     * trabajadores no tienen movimientos generados se pone el default de carga
     * inicial, por cuestiones del layout del archivo.
     * 
     * @param nss
     *            Numero de seguridad social del trabajador asociado al
     *            movimiento
     * @param periodo
     *            el periodo en que se genera el movimiento
     * @return el movimiento default para el trabajador
     */
    private Movimiento getMovimientoDefault(String nss, PeriodoCuota periodo) {
        Movimiento movimiento = new Movimiento();
        movimiento.setNssTrabajador(nss);
        movimiento.setDiasDeIncidencia(BigDecimal.ZERO.intValue());
        movimiento.setFechaDeMovimientoOIncidencia(periodo.getInicioPeriodo().getTime());
        movimiento.setFolioDeIncapacidad(VACIO);
        movimiento.setSalarioDiarioIntegrado(periodo.getSalarioPeriodo());
        movimiento.setTipoDeMovimientoOIncidencia(ClavesTipoMovimiento.CARGA_INICIAL);
        return movimiento;
    }

    /**
     * Genera un trabajador a partir de ls datos de una persona fisica
     * 
     * @param persona
     *            La representacion del trabajador
     * @return El registro de un trabajador con sus datos correspondientes
     */
    private Trabajador generaDatosTrabajador(Fisica persona) {
        Trabajador trabajador = new Trabajador();
        agregaValoresDefault(trabajador);
        trabajador.setNssTrabajador(persona.getNss());
        trabajador.setRfcDelTrabajador(StringUtils.isEmpty(persona.getRfc()) ? RFC_DEFAULT
                : persona.getRfc());
        trabajador.setClaveUnicaDelRegistroDePoblacion(persona.getCurp());
        trabajador.setNombreTrabajador(persona.getNombre());
        trabajador.setApellidoPaternoTrabajador(persona.getPrimerApellido());
        trabajador.setApellidoMaternoTrabajador(persona.getSegundoApellido());

        return trabajador;
    }

    /**
     * MEtodo que agrega los valores default para un IVRO, cuando se agregen
     * nuevas modalidades estos valores deberia irse agregando al entrada del
     * servicio y copiarlos al trabajador
     * 
     * @param trabajador
     *            El regisro del trabajador al cual se agregaran los datos no
     *            calculados
     */
    private void agregaValoresDefault(Trabajador trabajador) {
        trabajador.setTipoDeTrabajador(TIPO_TRABAJADOR);
//        trabajador.setJornadaPorSemanaReducida(JORNADA_COMPLETA);
        // Los valores de Infonavit no son manejados por el IMMS y se vana CERO
        trabajador.setNumeroDeCreditoINFONAVIT(0);
        trabajador.setFechaDeInicioDelDescuentoDeCreditoINFONAVIT(new Date());
        trabajador.setAmortizacionDeCreditoINFONAVIT(BigDecimal.ZERO);
        trabajador.setAportacionVoluntaria(BigDecimal.ZERO);
        trabajador.setAportacionPatronalINFONAVIT(BigDecimal.ZERO);

    }

    /**
     * MEtodo que genera la copia de los valores de MovimientoEmpleado a
     * movimiento, Que es la entidad a utilizar en el SUA
     * 
     * @param periodo
     *            periodo en el que se genera el movimiento
     * @param nss
     *            numero de identificacion para el trabajador
     * @return la lista de movimientos que se deben mostrar en el archivo SUA
     */
    private Movimiento[] generaMovimientosTrabajador(
            PeriodoCuota periodo, String nss) {
        List<Movimiento> movimientos = new ArrayList<Movimiento>();
        // Si no hay movimientos en el periodo se agrega uno default
        if (periodo.getMovimientos() == null || periodo.getMovimientos().length == 0) {
            Movimiento movimiento = getMovimientoDefault(nss, periodo);
            movimientos.add(movimiento);
        } else {
            for (MovimientoEmpleado mov : periodo.getMovimientos()) {
                Movimiento movimiento = new Movimiento();
                movimiento.setNssTrabajador(nss);
                movimiento.setDiasDeIncidencia(mov.getDias());
                movimiento.setFechaDeMovimientoOIncidencia(mov.getFecha());
                movimiento.setFolioDeIncapacidad(mov.getFolio());
                movimiento.setSalarioDiarioIntegrado(mov.getSalario());
                movimiento.setTipoDeMovimientoOIncidencia(mov.getTipoMovimiento());
                movimientos.add(movimiento);
            }
        }
        return movimientos.toArray(new Movimiento[movimientos.size()]);
    }

    /**
     * Metodo que agrega los valores a las cuotas del trabajador a parti de la
     * lista de cuotas regresadas
     * 
     * @param trabajador
     *            El registro del trabajador al cual se le agregaran las cuotas
     * @param cuotas
     *            las cuotas a ser agregadas al registro del trabajador
     */
    private void agregaCuotasTrabajador(Trabajador trabajador, RamaCalculo[] cuotas) {
        LOGGER.debug("Agregando cuotas al trabajador");
        for (RamaCalculo rama : cuotas) {
            BigDecimal aportacion = rama.getAportacion();
            switch (rama.getIdRama()) {
            case ClavesRama.CUOTA_FIJA:
                trabajador.setCuotaFijaDeEnfermedadYMaternidad(aportacion.add(trabajador
                        .getCuotaFijaDeEnfermedadYMaternidad()));
                break;
            case ClavesRama.RIESGOS_TRABAJO:
                trabajador.setRiesgosDeTrabajo(aportacion.add(trabajador.getRiesgosDeTrabajo()));
                break;
            case ClavesRama.GUARDERIAS_PRESTACIONES_SOCIALES:
                trabajador.setGuarderiaYPrestacionesSociales(aportacion.add(trabajador
                        .getGuarderiaYPrestacionesSociales()));
                break;
            case ClavesRama.RETIRO:
                trabajador.setRetiro(aportacion.add(trabajador.getRetiro()));
                break;
            case ClavesRama.EXCEDENTE:
                trabajador.setCuotaExcedenteDeEnfermedadYMaternidad(aportacion.add(trabajador
                        .getCuotaExcedenteDeEnfermedadYMaternidad()));
                break;
            case ClavesRama.PRESTACIONES_DINERO:
                trabajador.setPrestacionesEnDineroDeEnfermedadYMaternidad(aportacion.add(trabajador
                        .getPrestacionesEnDineroDeEnfermedadYMaternidad()));
                break;

            case ClavesRama.INVALIDEZ_Y_VIDA:
                trabajador.setInvalidezYVida(aportacion.add(trabajador.getInvalidezYVida()));
                break;
            case ClavesRama.GASTOS_MEDICOS_PENSIONADOS:
                trabajador.setGastosMedicosPensionadosArt25(aportacion.add(trabajador
                        .getGastosMedicosPensionadosArt25()));
                break;

            }
            agregaCuotaPorTipo(rama, trabajador);

        }
    }

    /**
     * Agrega la cuota por tipo de aoprtacion (Casos especiales)
     * 
     * @param rama
     *            la rama de calculo que se agregara en caso de ser una cuota
     *            especial Estas son las que aparecen separadas para atron y
     *            obrero
     * @param trabajador
     *            el registro al cual se le agrega la cuota
     */
    private void agregaCuotaPorTipo(RamaCalculo rama, Trabajador trabajador) {
        BigDecimal aportacion = rama.getAportacion();
        switch (rama.getIdTipoAportacion()) {
        case ClavesTipoAportacion.OBRERA:
            switch (rama.getIdRama()) {
            case ClavesRama.EXCEDENTE:
                trabajador.setCuotaExcedenteCuotaObreraN(aportacion);
                break;
            case ClavesRama.PRESTACIONES_DINERO:
                trabajador.setPrestacionesEnDineroCuotaObreraN(aportacion);
                break;
            case ClavesRama.GASTOS_MEDICOS_PENSIONADOS:
                trabajador.setGastosMedicosPensionadosCuotaObreraN(aportacion);
                break;
            case ClavesRama.INVALIDEZ_Y_VIDA:
                trabajador.setInvalidezYVidaCuotaObreraN(aportacion);
                break;
            case ClavesRama.CESANTIA_EDAD_AVANZADA:
                trabajador.setCesantiaYVejezCuotaObreraN(aportacion);
                trabajador.setCesantiaYVejezCuotaTrabajador(aportacion);
                break;
            }
            break;
        case ClavesTipoAportacion.PATRONAL:
            switch (rama.getIdRama()) {
            case ClavesRama.CESANTIA_EDAD_AVANZADA:
                trabajador.setCesantiaYVejezCuotaPatronal(aportacion);
                break;
            }
            break;
        }
    }

    /**
     * Agrega os dias cotizados en el periodo, dias de incapacidad y dias de
     * ausentismo
     * 
     * @param trabajador
     *            El registro al que se le agregaran los dias cotizados en el
     *            periodo
     * @param periodo
     *            EL periodo que contiene la informacion de fechas y movimientos
     *            que afecten los dias cotizados
     */
    private void agregaDiasCotizados(Trabajador trabajador, PeriodoCuota periodo, Long idModalidad) {
        int diasIncapacidad = 0;
        int diasAusentismo = 0;
        if(periodo.getMovimientos() != null) {
            for (MovimientoEmpleado movimiento : periodo.getMovimientos()) {
                if (ClavesTipoMovimiento.AUSENTISMO == movimiento.getTipoMovimiento()) {
                    diasAusentismo = diasAusentismo + movimiento.getDias();
                } else if (ClavesTipoMovimiento.INCAPACIDAD == movimiento.getTipoMovimiento()) {
                    diasIncapacidad = diasIncapacidad + movimiento.getDias();
                }
            }
        }        
        int diasPeriodo = SuaUtil.getDiasPeriodo(periodo.getInicioPeriodo().getTime(), periodo
                .getFinPeriodo().getTime());
        int diasCotizados = diasPeriodo - (diasAusentismo + diasIncapacidad);
        
        trabajador.setDiasDeIncapacidadEnElBimestre(diasIncapacidad);
        trabajador.setDiasDeAusentismoEnElBimestre(diasAusentismo);
        // Dado que el IVRO se genera por bimestre los valores de mes son iguales al mes
		if (idModalidad == ModalidadEnum.TREINTAYTRES.getId()) {
			trabajador.setDiasCotizadosEnElMes(0);
			trabajador.setDiasCotizadosEnElBimestre(0);
		} else {
			trabajador.setDiasCotizadosEnElMes(diasCotizados);
			trabajador.setDiasCotizadosEnElBimestre(diasCotizados);
		}
        
        trabajador.setDiasDeIncapacidadEnElMes(diasIncapacidad);
        trabajador.setDiasDeAusentismoEnElMes(diasAusentismo);
    }

    /**
     * Agrega los valores que se tiene que codificar
     * 
     * @param trabajador
     *            registro del cual se obtiene los valors de cuota, se codifican
     *            y se agregan al mismo registro
     */
    private void agregaValoresCodificados(Trabajador trabajador) {

        trabajador.setPrestacionesEnDineroCuotaObrera(SuaUtil.codificaValores(trabajador
                .getPrestacionesEnDineroCuotaObreraN()));
        trabajador.setGastosMedicosPensionadosCuotaObrera(SuaUtil.codificaValores(trabajador
                .getGastosMedicosPensionadosCuotaObreraN()));
        trabajador.setInvalidezYVidaCuotaObrera(SuaUtil.codificaValores(trabajador
                .getInvalidezYVidaCuotaObreraN()));
        trabajador.setCesantiaYVejezCuotaObrera(SuaUtil.codificaValores(trabajador
                .getCesantiaYVejezCuotaObreraN()));
        trabajador.setCuotaExcedenteCuotaObrera(SuaUtil.codificaValores(trabajador
                .getCuotaExcedenteCuotaObreraN()));
    }

    /**
     * Agrega los valores de recargos y actualizaciones de los trabajadores
     * @param trabajador el trabajador al cual se agregan los valores de racargos y actualizaciones
     * @param periodo el periodo de donde se obtienen los valores de recargos
     */
    private void agregaRecargosYActualizaciones(Trabajador trabajador, PeriodoCuota periodo) {
        trabajador.setActualizacionYRecargosDeRetiro(BigDecimal.ZERO);
        trabajador.setActualizacionYRecargosDeCesantiaYVejez(BigDecimal.ZERO);
        trabajador.setActualizacionYRecargosDeLosCuatroSegurosIMSS(BigDecimal.ZERO);
        for (RamaCalculo rama : periodo.getCuotas()) {
            
            BigDecimal actualizacion  = SuaUtil.getValorNoNulo(rama.getActualizacion());
            BigDecimal recargo = SuaUtil.getValorNoNulo(rama.getRecargo());
            BigDecimal aportacion = actualizacion.add(recargo);

            if (rama.getIdRama().equals(ClavesRama.CESANTIA_EDAD_AVANZADA)) {
                trabajador.setActualizacionYRecargosDeCesantiaYVejez(
                        trabajador.getActualizacionYRecargosDeCesantiaYVejez().add(aportacion));
                trabajador.setActualizacionCesantiaYVejez(
                        trabajador.getActualizacionCesantiaYVejez().add(actualizacion));
                trabajador.setRecargosCesantiaYVejez(
                        trabajador.getRecargosCesantiaYVejez().add(recargo));
            } else if (rama.getIdRama().equals(ClavesRama.RETIRO)) {
                trabajador.setActualizacionYRecargosDeRetiro(
                        trabajador.getActualizacionYRecargosDeRetiro().add(aportacion));
                trabajador.setActualizacionRetiro(
                        trabajador.getActualizacionRetiro().add(actualizacion));
                trabajador.setRecargosRetiro(
                        trabajador.getRecargosRetiro().add(recargo));
            } else {
                trabajador.setActualizacionYRecargosDeLosCuatroSegurosIMSS(trabajador
                        .getActualizacionYRecargosDeLosCuatroSegurosIMSS().add(aportacion));
                trabajador.setActualizacionCuatroSegurosIMSS(
                        trabajador.getActualizacionCuatroSegurosIMSS().add(actualizacion));
                trabajador.setRecargosCuatroSegurosIMSS(
                        trabajador.getRecargosCuatroSegurosIMSS().add(recargo));
            }
        }
    }

}
