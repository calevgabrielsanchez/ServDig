/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.business;

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
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.ParametrosEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.SuaServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces.GeneradorDatosPatronLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces.GeneradorDatosTrabajadorLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.interfaces.GeneradorSumarioYValidacionLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.SUAServiceUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.SuaUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.DatosValidacion;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model.PeriodoSUA;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.EmpleadoCuota;
import mx.gob.imss.digital.modelo.cobranza.Patron;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.cobranza.RegistroValidacion;
import mx.gob.imss.digital.modelo.cobranza.SUAPago;
import mx.gob.imss.digital.modelo.cobranza.SumarioPatronal;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para generar los datos de un archivo sua a partir de los datos de su calculo
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "suaServiceBusiness", mappedName = "suaServiceBusiness")
public class SuaServiceBusiness implements SuaServiceRemote {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SuaServiceBusiness.class);
    /**
     * Version del sua
     */
    public static final String CLAVE_VERSION_SUA = "VERSION_SUA";

    public static final Integer JORNADA_REDUCIDA_BIMESTRAL = 5;

    public static final Integer JORNADA_REDUCIDA_ANUAL = 3;

    /**
     * Servicio para obtener los sujetos obligados
     */
    @EJB(name = "sujetoObligadoServiceBusiness", mappedName = "sujetoObligadoServiceBusiness")
    private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
    /**
     * Servicoi para la consulta de personas fisicas
     */
    @EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

    /**
     * Servicio para la generacion de los datos del patron
     */
    @EJB
    private GeneradorDatosPatronLocal generadorDatosPatron;
    /**
     * Servicio para la generacion de los datos de un trabajador
     */
    @EJB
    private GeneradorDatosTrabajadorLocal generadorDatosTrabajador;
    /**
     * Servicio para la generacion del sumario y registro de validacion
     */
    @EJB
    private GeneradorSumarioYValidacionLocal generadorSumarioYValidacion;
    /**
     * Servicio para obtener los parametros necesarios
     */
    @EJB
    private ParametrosEntityLocal parametrosEntity;
    
    /**
     * MApa de paeronas fisicas ya buscadas en la misma generacion, esto con el
     * fin de no volver a buscar en base un empleado que ya esta en memoria
     */
    private Map<String, Fisica> personasBuscadas = new HashMap<String, Fisica>();

    /**
     * Servicio para buscar y completar los datos de un patron y sus empleado, y
     * asi poder generar los archivos sua y generar sus pagos
     * @param datosCalculo Los datos de calculo de un empleado o empleados, a partir de estos datos se obtiene los
     * faltantes para generar los archivos SUA
     * @return todos los datos necesarios para generar un archivo SUA
     * @throws SUAException Por si ocurre un error en la ejecucion del servicio
     */
    public SUAPago[] generaDatosSua(CalculoCuota datosCalculo, String nrp35)
            throws SUAException {
        LOGGER.debug("Invocando servicio para generar datos SUA");       
        
        personasBuscadas = new HashMap<String, Fisica>();        
        List<PeriodoSUA> periodosGenerados = generaDatosSUAPorPeriodo(datosCalculo, nrp35);
        return generaRegistrosSUA(datosCalculo, periodosGenerados, datosCalculo.getFechaFinCalculo());       

    }
    
    /**
     * GEnera los registros de trabajador y patron por cada periodo sua indicado
     * @param datosCalculo Los datos que se reciben en el llamado del servicio
     * @return la lista de periodos sua generados para el patron y sus tabajadores
     * @throws SUAException Errores al generar los periodos sua
     */
    private List<PeriodoSUA> generaDatosSUAPorPeriodo(CalculoCuota datosCalculo, String nrp35) 
            throws SUAException {
        SujetoObligado patronSujeto = getSujetoObligado(datosCalculo.getNumeroRegistroPatronal());
        List<PeriodoSUA> periodosGenerados = new ArrayList<PeriodoSUA>();        
        for (EmpleadoCuota empleado : datosCalculo.getEmpleados()) {
            Fisica persona = getPerosnaFisica(empleado.getNumeroSeguridadSocial());
            for (PeriodoCuota periodo : empleado.getPeriodos()) {
                PeriodoSUA periodoSUA = SUAServiceUtil.obtenPeriodoSUA(periodosGenerados, periodo);
                // agregamos el registro del patron si no se ha agregado en otra
                // iteracion                
                if (periodoSUA.getPatron() == null) {
                    periodoSUA.setPatron(generadorDatosPatron.generaPatron(datosCalculo,
                            patronSujeto, periodo, empleado, nrp35));
                }
                if (periodoSUA.getTrabajadores() == null) {
                    periodoSUA.setTrabajadores(new ArrayList<Trabajador>());
                }
                // Agregamos el trabajador SUA
                Trabajador trabajador = generadorDatosTrabajador.generaTrabajador(empleado,
                        persona, periodo, datosCalculo.getModalidad());
                trabajador.setMunicipio(periodoSUA.getPatron().getMunicipio());
                //Se calcula la jornadaPorSemanaReducida
                boolean beneficio = datosCalculo.getConBeneficio() != null
                        && datosCalculo.getConBeneficio().booleanValue();
                boolean recargos = datosCalculo.getConRecargos() != null
                        && datosCalculo.getConRecargos().booleanValue();

                LOGGER.info("+++ beneficio: "+beneficio+ " recargos: "+recargos);
                trabajador.setJornadaPorSemanaReducida(beneficio || recargos ? JORNADA_REDUCIDA_BIMESTRAL : JORNADA_REDUCIDA_ANUAL);

                periodoSUA.getTrabajadores().add(trabajador);
                // Agregamos los factores de recargos yactualizaciones
                periodoSUA.setFactorActualizacion(periodo.getFactorActualizacion());
                periodoSUA.setFactorRecargo(periodo.getFactorRecargo());
                // Sumamos los montos totales de los trabajadores por periodo 
                periodoSUA.setMonto(periodoSUA.getMonto().add(periodo.getTotal()));
                //Agregamos el periodo a la lista                
                periodosGenerados = SUAServiceUtil.agregaPeriodo(periodosGenerados, periodoSUA);
            }
        }
        return periodosGenerados;
    }
    
    /**
     * GEnera los registros sua a partir de los periodos generados, esto es se les agrega las sumatorias y 
     * registros de validacion a sus respectivos patrones y trabajadores
     * @param datosCalculo los datos que llegan al servicio
     * @param periodosGenerados los periodos a generar sus registros
     * @return la lista de registros suas generados
     * @throws SUAException error al generar los registros de validacion
     */
    private SUAPago[] generaRegistrosSUA(CalculoCuota datosCalculo, List<PeriodoSUA> periodosGenerados, Calendar fechaReferencia) 
            throws SUAException {
        List<Date> diasFeriados = parametrosEntity.getDiasFeriados();
        List<SUAPago> suas = new ArrayList<SUAPago>();
        for (PeriodoSUA periodoSUA : periodosGenerados) {
                          
            List<Trabajador> trabajadores = periodoSUA.getTrabajadores();                
            SumarioPatronal sumario = generadorSumarioYValidacion.generaSumario(periodoSUA);
            boolean beneficio = datosCalculo.getConBeneficio() != null ? datosCalculo.getConBeneficio().booleanValue() 
                    : false;
            boolean recargo = datosCalculo.getConRecargos() != null ? datosCalculo.getConRecargos().booleanValue() 
                    : false; 
            DatosValidacion datos = new DatosValidacion();
            datos.setConbeneficio(beneficio);
            datos.setDiasFeriados(diasFeriados);
            datos.setRenovacion(datosCalculo.getRenovacion());
			if (datosCalculo.getModalidad() == ModalidadEnum.CUARENTA.getId()) {
				datos.setFechaInicio(periodoSUA.getFechaInicio());
			} else {
				datos.setFechaInicio(beneficio || recargo
						? periodoSUA.getFechaInicio() : datosCalculo.getFechaInicioCalculo());
			}
			datos.setFechaReferencia(fechaReferencia);
            datos.setModalidad(datosCalculo.getModalidad());
            datos.setVersionSUA(datosCalculo.getVersionSUA());
            datos.setSumario(sumario);
            datos.setTrabajadores(trabajadores);
            datos.setAplicaRecargoPorFechaBaja(datosCalculo.getAplicaRecargoPorFechaBaja());

            RegistroValidacion validacion = generadorSumarioYValidacion.generaRegistroValidacion(datos);
            
            SUAPago suapago = new SUAPago();            
            Patron patron = periodoSUA.getPatron();
            patron.setDiasCotizadosPeriodoConIncidencias(getDiasCotizados(trabajadores));
            patron.setFolioSUA(SuaUtil.generaFolioSUA(periodoSUA));
            
            suapago.setPatron(patron);
            suapago.setTrabajadores(trabajadores.toArray(new Trabajador[trabajadores.size()]));
            suapago.setSumarioPatronal(sumario);
            suapago.setRegistroValidacion(validacion);
            suapago.setFechaInicio(((Calendar) periodoSUA.getFechaInicio()).getTime());
            suapago.setFechaFin(((Calendar) periodoSUA.getFechaFin()).getTime());
            suapago.setMonto(periodoSUA.getMonto());

            suas.add(suapago);                
                    
        }
        return suas.toArray(new SUAPago[suas.size()]);        
    }
    
    /**
     * Obtiene el total de dias cotizados en el periodo de todos los trabajadres
     * @param trabajadores la lista de trabajadores donde se obtendran lod dias cotizados
     * @return el total de dias cotizados
     */
    private int getDiasCotizados(List<Trabajador> trabajadores) {
        // Se calcula el total de dias
        int diasM = 0;
        int diasB = 0;
        for (Trabajador trabajador : trabajadores) {
            diasB = diasB + trabajador.getDiasCotizadosEnElBimestre();
            diasM = diasM + trabajador.getDiasCotizadosEnElMes();
        }
        return diasM != 0 ? diasM : diasB;
    }

    /**
     * Obtiene el Sujeto obligado a partir del registro patronal
     * 
     * @param rp numero de registro patronal
     * @return La representacion de un empleado <code>SujetoObligado</code> 
     * asociado al registro patronal
     * @throws SUAException Si se genera un eeror al buscar al empleador
     */
    private SujetoObligado getSujetoObligado(String rp) throws SUAException {
        // BUscamos el sujeto obligado por su registro patronal
        LOGGER.debug("BUscado al patron {}", new Date());
        SujetoObligado patron = sujetoObligadoServiceBusiness
                .consultarPorNumeroRegistroPatronal(rp);
        LOGGER.debug("Patron encontrado {}", patron);
        if (patron == null) {
            throw new SUAException(SUAConstants.COD_NO_PATRON, SUAConstants.MSG_NO_PATRON);
        }
        LOGGER.debug("Se enontro y se busca su detalle al patron {}", new Date());
        // Agregamos los datos complementarios del sujeto obligado
        patron = sujetoObligadoServiceBusiness.obtenerDetalleRP(patron);
        LOGGER.debug("patron  completo{}", new Date());

        return patron;
    }

    /**
     * Obtiene la persona fisica relacionada a un nss, Si ya se tiene en memoria
     * esa persona se regresa, en caso contrario se busca en BD se guarda en
     * memori ay se regresa
     * 
     * @param nss NUmero de seguridad social del trabajador
     * @return La persona fisica asociada al nss ingresado
     * @throws SUAException Error al no encontrar la persona con el nss indicado
     */
    private Fisica getPerosnaFisica(String nss) throws SUAException {
        try {
            Fisica persona;
            if (personasBuscadas.containsKey(nss)) {
                persona = personasBuscadas.get(nss);
            } else {
                LOGGER.debug("buscando persona {}", new Date());
                persona = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);
                personasBuscadas.put(nss, persona);
                LOGGER.debug("persona encontrada {}", new Date());
            }
            return persona;
        } catch (PersonasNoLocalizadasException e) {
            LOGGER.error("No se encontró un trabajador asociado al NSS {}", nss);
            throw new SUAException(SUAConstants.COD_NO_TRABAJADOR, SUAConstants.MSG_NO_TRABAJADOR,
                    e);
        } catch (NssRelacionadoVariasPersonasException e1) {
            LOGGER.error("Existe más de un trabajador con el NSS {} ", nss, e1);
            throw new SUAException(SUAConstants.COD_NO_TRABAJADOR, SUAConstants.MSG_NO_TRABAJADOR,
                    e1);
        }
    }    

}
