/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizadorEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CuotaServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.DicFactorModalidadRamaEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.FactorCostosEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.GeneradorPeriodosCobroLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MotorBeneficiosBusinessLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.MotorCalculoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.RecargoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.SalarioCalculoServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.MotorFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model.ValoresCalculoEmpleado;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;
import mx.gob.imss.digital.modelo.persona.Parentesco;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * implementacion de los servicio para el calculo de cuotas
 * @author NOVUTECK1
 *
 */
@Stateless(name = "cuotaServiceEntity", mappedName = "cuotaServiceEntity")
public class CuotaServiceEntity implements CuotaServiceLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CuotaServiceEntity.class);

    /**
     * Servicio para obtener las ramas de un trabajador por modalidad
     */
    @EJB
    private DicFactorModalidadRamaEntityLocal dicFactorModalidadRamaEntityLocal;
    /**
     * Servicio para el manejo de beneficios
     */
    @EJB
    private MotorBeneficiosBusinessLocal motorBeneficiosBusinessLocal;
    /**
     * Servicio para generar los periodos a calcular las cuotas
     */
    @EJB
    private GeneradorPeriodosCobroLocal generadorPeriodosCobroLocal;
    /**
     * Servicio para obtener los salarios con los que se calculan las cuotas
     */
    @EJB
    private SalarioCalculoServiceLocal salarioCalculoServiceLocal;
    /**
     * Servicio para el calculo de cuotas
     */
    @EJB
    private MotorCalculoServiceLocal motorCalculoServiceLocal;
    /**
     * Ejb para la persistencia de la cotizacion
     */
    @EJB
    private CotizadorEntityLocal cotizadorEntity;
    /**
     * Servicoi para la consulta de personas fisicas
     */
    @EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
    
    @EJB(name = "personaBusiness", mappedName = "personaBusiness")
    private PersonaBusinessRemote personaBusiness;
    
    /**
     * Servicio para el calculo de recargo
     */
    @EJB
    private RecargoServiceLocal recargoServiceLocal;

    @EJB
    private FactorCostosEntityLocal factorCostosEntity;

    /**
     * Variable que contendra las ramas de calculo por modalidad, esto por si
     * tenemos varios empleados con la misma modalidad no se realicen nuevamente
     * las consultas y si tienen diferentes modalidades se puedan calcular
     * correctamente
     */
    private Map<Long, List<RamaCalculo>> ramasBuscadas;

    /**
     * Genera los calculos ed las cuotas a pagar por un empleado en un periodo de cobro
     * @param datosCalculoCuota DAtos a partir de los cuales se generan los calculos de cuotas
     * @return EL resultado de los calculos de cobreo para un empleado
     * @throws SUAException Error al generar los calculos de cuotas
     */    
    public Cotizacion generaCotizacion(DatosCalculoCuota datosCalculoCuota)
            throws SUAException {
        LOGGER.debug("Datos calculo {}", ReflectionToStringBuilder.toString(datosCalculoCuota));
        // normalizamos fechas
        datosCalculoCuota.setFechaInicioCalculo(
                PeriodoUtil.normalizaFecha(datosCalculoCuota.getFechaInicioCalculo()));
        datosCalculoCuota.setFechaFinCalculo(
                PeriodoUtil.normalizaFecha(datosCalculoCuota.getFechaFinCalculo()));
        // validamos que las fechas del periodo sean las correctas
        validaFecha(datosCalculoCuota.getFechaInicioCalculo(),
                datosCalculoCuota.getFechaFinCalculo());

        // Copia de datos que tiene que ir en la salida sin ninguna modificacion
        CalculoCuota calculo = MotorFactoryUtil.copiaValoresCuota(datosCalculoCuota);
        // calculamos las cuotas por cada empleado
        ramasBuscadas = new HashMap<Long, List<RamaCalculo>>();
        BigDecimal total = BigDecimal.ZERO;
        List<EmpleadoCuota> empleadosCalculo = new ArrayList<EmpleadoCuota>();
        for (DatosEmpleado empleado : datosCalculoCuota.getEmpleados()) {
            // validamos que los trabajadores existan
            String nombre = "";
            String curp = "";
            Integer edad = -1;
            try {
                Fisica fisica = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(
                        empleado.getNumeroSeguridadSocial());
                nombre = generaNombre(fisica.getNombre(), fisica.getPrimerApellido(), fisica.getSegundoApellido());
                curp = fisica.getCurp();
                edad = personaBusiness.obtenerEdadPersona(fisica.getIdPersona());
                empleado.setEdad(edad);
            } catch (PersonasNoLocalizadasException e) {
                throw new SUAException(SUAConstants.COD_NO_TRABAJADOR, SUAConstants.MSG_NO_TRABAJADOR);
            } catch (NssRelacionadoVariasPersonasException e) {
                throw new SUAException(SUAConstants.COD_NO_TRABAJADOR, SUAConstants.MSG_NO_TRABAJADOR);
            }

            ValoresCalculoEmpleado valores = MotorFactoryUtil
                    .copiaValoresCuotaEmpleado(datosCalculoCuota);
            valores.setEmpleado(empleado);
            EmpleadoCuota cuota = calculaCuotaEmpleado(valores);

            empleadosCalculo.add(cuota);
            cuota.setNombreTrabajador(nombre);
            cuota.setCurp(curp);
            cuota.setEdad(edad);

            if (empleado.getParentesco() != null) {
                cuota.setParentesco(new Parentesco());
                cuota.getParentesco().setIdParentesco(empleado.getParentesco());
            }
            cuota.setAplicaCuestionario(empleado.getAplicaCuestionario());
            cuota.setInscripcion(empleado.getInscripcion());
            cuota.setIndividual(empleado.getIndividual());
            calculo.setConBeneficio(cuota.getConBeneficio() || calculo.getConBeneficio());
            total = total.add(cuota.getCuotaTotal());
        }

        //Cambio para ajuste de la ley 168: se calcula el recargo
        BigDecimal recargoLey168 = BigDecimal.ZERO;
        BigDecimal actualizacionLey168 = BigDecimal.ZERO;
        try{
            recargoLey168 = salarioCalculoServiceLocal.recuperaPorcentajeSeguroLey168(datosCalculoCuota.getEmpleados()[0].getNumeroSeguridadSocial());
            actualizacionLey168 = salarioCalculoServiceLocal.recuperaActualizacionSeguroLey168(datosCalculoCuota.getEmpleados()[0].getNumeroSeguridadSocial());

            if(recargoLey168!=null && !recargoLey168.equals(BigDecimal.ZERO)){

                LOGGER.info("El porcentaje al ajuste ley 168 es: "+recargoLey168);

            }else{
                LOGGER.info("El porcentaje al ajuste ley 168 es cero o null");
            }

        }catch (Exception e){
            LOGGER.error("Hubo un error en el codigo para ajustar ley 168: "+e);
            e.printStackTrace();
        }
        //Fin del cambio ley 168


        //Se reestablece la zona salarial original
        String zonaSalarialOriginal = recuperaZonaSalarialOriginal(datosCalculoCuota.getZonaSalarial(),datosCalculoCuota.getModalidad(), new Date());
        calculo.setZonaSalarial(zonaSalarialOriginal);
        datosCalculoCuota.setZonaSalarial(zonaSalarialOriginal);

        calculo.setEmpleados(empleadosCalculo.toArray(new EmpleadoCuota[empleadosCalculo.size()]));
        calculo.setCuotaTotal(total);
        calculo.setConcepto(datosCalculoCuota.getConcepto());

        if (datosCalculoCuota.getRecargos() != null && datosCalculoCuota.getRecargos()) {
            if (datosCalculoCuota.getModalidad() != ModalidadEnum.CUARENTA.getId()) {
                calculo = generaRecargos(calculo);
            } else {

                calculo = generaActualizacionesRecargos(calculo,recargoLey168,actualizacionLey168);
            }

        }else{

            if (datosCalculoCuota.getModalidad() == ModalidadEnum.CUARENTA.getId()) {
                LOGGER.info("No se generan recargos de forma normal");
                if (recargoLey168!=null && !recargoLey168.equals(BigDecimal.ZERO)){
                    LOGGER.info("El recargo del porcentaje Ley168 se aplica aunque no se genere recargo del periodo");
                    calculo = generaRecargosLey168(calculo,recargoLey168,actualizacionLey168);
                }else{
                    LOGGER.debug("No se genera recargo normal ni por ley 168");
                }
            }

        }

        return cotizadorEntity.generaYGuardaCotizacion(calculo);
    }
    
    /**
     * Concatena los nombres de una persona, 
     * @param nombre el nombre de la persona
     * @param primApellido el primer apellido de la persona
     * @param segApellido el segundo apellido de la persona
     * @return el nombre concatenado  (Nombre PrimerApellido SegundoApellido)
     */
    private String generaNombre(String nombre, String primApellido, String segApellido) {
        StringBuilder nombreCompleto = new StringBuilder(StringUtils.trimToEmpty(nombre));
        nombreCompleto.append(" ")
        .append(StringUtils.trimToEmpty(primApellido))
        .append(" ").append(StringUtils.trimToEmpty(segApellido));
        return nombreCompleto.toString();
    }

    /**
     * Metodo para el calculo las cuotas a pagar por un empleado,
     * 
     * @param valores los valores necesarios para generar las cuotas
     * @return las cuotas calculadas para un empleado
     * @throws SUAException Por si genera un error en los calculos del empleado
     */
    private EmpleadoCuota calculaCuotaEmpleado(ValoresCalculoEmpleado valores) throws SUAException {
        // Buscamos el beneficio
        Beneficio beneficio = motorBeneficiosBusinessLocal.buscarBeneficioTrabajador(valores);


        valores.setUltimoSalarioCotizado(valores.getEmpleado().getSalario());
        BigDecimal ultSalCot = valores.getUltimoSalarioCotizado();
        LOGGER.info("ultimoSalarioCotizado {}",ultSalCot);
        // Agregamos los valores de los salarios para calcular las cuotas
        LOGGER.debug("Agregando salarios");
        valores = salarioCalculoServiceLocal.agregaSalariosCalculo(valores);
        // Dividimos el rango de calculo en periodos de calculo
        LOGGER.debug("Generando periodos");
        valores.setPeriodos(generadorPeriodosCobroLocal.generaPeriodosCalculo(valores, beneficio));
        LOGGER.debug("Buscando ramas");
        // Agregamos las ramas de las cuotas a calcular
        long modalidad = valores.getModalidad();
		if (modalidad == ModalidadEnum.TREINTAYTRES.getId()) {
			valores.setCuotas(factorCostosEntity.buscarCostoSeguro(valores.getEmpleado().getEdad(), valores.getEmpleado().getParentesco()));
		} else {
			valores.setCuotas(getRamaCalculoEmpleado(modalidad, valores.getNumeroRegistroPatronal()));
		}
        // Calculamos las cuota del empleado en los periodos de tiempo
        LOGGER.debug("Calculando cuotas");
        EmpleadoCuota cuota = motorCalculoServiceLocal.calculaCuota(valores);
        cuota.setConBeneficio(beneficio != null ? true : false);
        return cuota;
    }

    /**
     * Metodo utilitario que verifica su dada una modalidad ya se ha buscado
     * antes sus cuotas, si es asi regresa estas de lo contrario las busca y
     * guarda esas cuotas para calculos posteriores
     * 
     * @param modalidad la modalidad a la que pertenece el empleador
     * @param registroPatronal el numero de identificacion de un empleador
     * @return la lista de cuotas que seben cobrar a un trabajador por el seguro
     * @throws SUAException Error al buscar las ramas de calculo del empleado
     * 
     */
    private List<RamaCalculo> getRamaCalculoEmpleado(long modalidad, String registroPatronal) throws SUAException {
        List<RamaCalculo> ramas = ramasBuscadas.get(Long.valueOf(modalidad));
        if (ramas == null) {
            ramas = dicFactorModalidadRamaEntityLocal.buscarRamasCalculo(modalidad, registroPatronal);
            ramasBuscadas.put(Long.valueOf(modalidad), ramas);
        }
        return ramas;
    }

    /**
     * Verifica que la fecha final se mayor o igual a al fecha inicial
     * 
     * @param fechaIni Fecha inicial del periodo de pago
     * @param fechaFin fecha final del periodo de pago
     * @throws SUAException Error generado si las fechas no cumplen lo esperado
     */
    private void validaFecha(Calendar fechaIni, Calendar fechaFin) throws SUAException {        
        Calendar ini = PeriodoUtil.truncaFecha(fechaIni);
        Calendar fin = PeriodoUtil.truncaFecha(fechaFin);
        if (fin.before(ini)) {
            throw new SUAException(SUAConstants.COD_FECHA, SUAConstants.MSG_FECHA);
        }
    }
    
    /**
     * genera los recargos de una cotizacion 
     * @param calculos los calculos a generar recargos
     * @return el calculo con recargos
     * @throws SUAException errores al generar los recargos
     */
    private CalculoCuota generaRecargos(CalculoCuota calculos) throws SUAException {
        calculos = recargoServiceLocal.generaRecargos(calculos);
        return calculos;
    }

	private CalculoCuota generaActualizacionesRecargos(CalculoCuota calculos,BigDecimal porcentajeLey168, BigDecimal actualizacionLey168)
			throws SUAException {
		calculos = recargoServiceLocal.generaActualizacionesRecargos(calculos,porcentajeLey168,actualizacionLey168);
		return calculos;
	}

    private CalculoCuota generaRecargosLey168(CalculoCuota calculos,BigDecimal porcentajeLey168,BigDecimal actualizacionLey168)
            throws SUAException {
        calculos = recargoServiceLocal.generaRecargosLey168(calculos,porcentajeLey168,actualizacionLey168);
        return calculos;
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
