/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.PeriodoUtil;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaBeneficiarioMigradoLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ParametrosEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.VigenciaIvroServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroConstants;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroDateUtils;
//import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.EntityManager;
//import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.PersistenceContext;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.EstadoSeguroIvro;
import mx.gob.imss.digital.modelo.seguros.PeriodoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "vigenciaIvroServiceLocal", mappedName = "vigenciaIvroServiceLocal")
public class VigenciaIvroServiceEntity implements VigenciaIvroServiceLocal {

	/**
	 * LOGGER de la clase
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VigenciaIvroServiceLocal.class);
	/**
	 * Servicio para el manejo de beneficios
	 */
	@EJB(name = "beneficioRissServiceBusiness", mappedName = "beneficioRissServiceBusiness")
	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusinessRemote;
	@EJB
	private SujetoObligadoServiceBusinessRemote registroPatronalService;

	/**
	 * Servicio de consulta ivro
	 */
	@EJB
	private ConsultaSeguroIvroLocal consultaSeguroIvro;
	/**
	 * servicio de consulta e parametros
	 */
	@EJB
	private ParametrosEntityLocal parametrosEntity;

	/**
	 * Consulta a los beneficiarios si no hay ramite implica que fueron migrados
	 */
	@EJB
	private ConsultaBeneficiarioMigradoLocal consultaBeneficiarioMigradoEntity;
	
	
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager em;

	/**
	 * Lista de modalidades que aplican dbeneficios
	 */
	private static final ModalidadEnum[] APLICA_BENEFICIOS = {
			ModalidadEnum.TREINTAYCINCO, ModalidadEnum.CUARENTAYTRES,
			ModalidadEnum.CUARENTAYCUATRO };
	/**
	 * Dia limite de pago para una persona con beneficio
	 */
	private static final int DIA_BENEFICIO = 25;
	private static final int DIA_RECARGO = 17;
	/**
	 * Hora maxima de para poder pedir seguro si es el ultimo dia valido
	 */
	private static final int HORA_MAXIMA_RENOVACION = 23;
	private static final int DIAS_RENOVAVION_EXTAPORENEA_SSF = 45;
	/**
	 * Obtiene un periodo valido, para generar una cotizacion de seguro, además
	 * de indicar si el periodo es nuevo o se trata de una renovacion
	 * 
	 * - Si el trabajador tiene beneficio su fecha limite es el 25 del mes
	 * anterior al inicio de periodo, en caso contrario el el ultimo dia habil
	 * del mes anterior a iniciar el seguro - Si se trata de una renovacion la
	 * fecha limite puede estar dentro del periodo del seguro - Si no es
	 * renovacion y la fecha limite de pago es menor a la fecha actual el
	 * periodo se mueve un mes mas
	 * 
	 * @param persona
	 *            <code>Persona</code> Persona fisica a la cual se verifican sus
	 *            vigencias
	 * @return el objeto <code>PeriodoSeguro</code> que contiene las vechas de
	 *         inicio y fin del periodo a cotizar asi como si se trata de una
	 *         renovacion.
	 * @throws IvroException
	 *             ivro exception
	 */
	public PeriodoSeguro obtenPeriodoSeguroIndividual(Fisica persona)
			throws IvroException {
		Calendar fechaInicial = Calendar.getInstance();
		SeguroIvro anterior = consultaSeguroIvro.getUltimoSeguro(persona);
		PeriodoSeguro periodo = new PeriodoSeguro();
		periodo.setRenovacion(false);



		if(anterior != null){
			anterior = isPeriodoRenovacionTipo(anterior);
		}

		if(anterior!=null){
		    if(anterior.getEnRenovacion()!=null){
		        LOGGER.debug("El seguro anterior esta en renovacion: "+anterior.getEnRenovacion());
            }
            if(anterior.getExtemporanea()!=null){
		        LOGGER.debug("El seguro anterior esta en renovacion extemporanea: "+anterior.getExtemporanea());
            }
            if(anterior.getEstadoSeguro()!=null){
                LOGGER.debug("El seguro anterior esta en Estado: "+anterior.getEstadoSeguro().getDescripcion());
            }
        }

		if (anterior != null && validarSeguroRenovacion(anterior) && anterior.getEnRenovacion() && !anterior.getExtemporanea()) {
			LOGGER.info("Se calcula fecha inicial como Oportuna");

			fechaInicial = getFechaInicialNueva(persona);
			fechaInicial = getFechaInicialRenovacion(anterior.getFechaFin());
            LOGGER.info("fecha inicial : "+fechaInicial);
			periodo.setRenovacion(true);
            LOGGER.info("renovacion : true");

		} else {
			LOGGER.info("Se calcula fecha inicial como Extemporanea");
			fechaInicial = getFechaInicialNueva(persona);
            LOGGER.info("fecha inicial : "+fechaInicial);
		}

		Calendar fechaFinal = getFechaFinalPeriodo(fechaInicial);
		periodo.setFechaIncial(fechaInicial.getTime());
		periodo.setFechaFinal(fechaFinal.getTime());
		return periodo;
	}

	/**
	 * Obtiene el periodo de vigencia para un seguro
	 * 
	 * @param persona
	 *            el patron al cal hay que obtener las fechas de vigencia de un
	 *            seguro nuevo
	 * @return El periodo del seguro nuevo
	 * @throws IvroException
	 *             Errores al obtener el periodo del seguro de un domestico
	 */
	public PeriodoSeguro obtenPeriodoSeguroDomestico(Fisica persona)
			throws IvroException {
		PeriodoSeguro periodo = new PeriodoSeguro();
		Calendar fechaIni = getFechaInicialNueva(persona);
		periodo.setFechaIncial(fechaIni.getTime());
		periodo.setRenovacion(false);
		periodo.setFechaFinal(getFechaFinalPeriodo(fechaIni).getTime());
		return periodo;
	}

	@Override
	public PeriodoSeguro obtenerPeriodoSeguroFamiliar() throws IvroException {
		PeriodoSeguro periodo = new PeriodoSeguro();
		Calendar fechaIni = getFechaInicialNueva();

		periodo.setFechaIncial(fechaIni.getTime());
		periodo.setRenovacion(false);
		periodo.setFechaFinal(getFechaFinalPeriodo(fechaIni).getTime());

		return periodo;
	}

	@Override
	public PeriodoSeguro obtenerPeriodoContinuacionVoluntaria()
			throws IvroException {
		PeriodoSeguro periodo = new PeriodoSeguro();
		Calendar fechaIni = Calendar.getInstance();

		periodo.setFechaIncial(fechaIni.getTime());
		periodo.setRenovacion(false);
		periodo.setFechaFinal(getFechaFinalPeriodoContinuacionVoluntaria(
				fechaIni).getTime());

		return periodo;
	}

	/**
	 * Obtiene el periodo de vigencia para un seguro a renovar
	 * 
	 * @param seguro
	 *            el seguro a renovar
	 * @return El periodo del seguro a renovar
	 * @throws IvroException
	 *             Errores al obtener el periodo de renovacion de un domestico
	 */
	public PeriodoSeguro obtenPeriodoRenovacionDomestico(SeguroIvro seguro)
			throws IvroException {
		if (!isPeriodoRenovacion(seguro)) {
			throw new IvroException(IvroConstants.COD_NO_RENOVACON,
					IvroConstants.MSG_NO_RENOVACON);
		}
		PeriodoSeguro periodo = new PeriodoSeguro();
		SeguroIvro seguroBuscado = consultaSeguroIvro.buscaSeguroPorId(seguro
				.getCveIdSeguroIvro());
		if (seguroBuscado == null) {
			throw new IvroException(IvroConstants.COD_NO_RENOVACON,
					IvroConstants.MSG_NO_RENOVACON);
		}
		Calendar fechaIni = getFechaInicialRenovacion(seguroBuscado
				.getFechaFin());
		periodo.setFechaIncial(fechaIni.getTime());
		periodo.setRenovacion(false);
		periodo.setFechaFinal(getFechaFinalPeriodo(fechaIni).getTime());
		return periodo;
	}

	/**
	 * Obtiene la fecha inicial de un periodo a partir de la fecha final del
	 * periodo del seguro anterior
	 * 
	 * @param fechaFinAnterior
	 *            fecha final del seguro anterior
	 * @return La fecha en que debe iniciar la renovacion de un seguro
	 */
	private Calendar getFechaInicialRenovacion(Date fechaFinAnterior) {
		Calendar fechaInicial = Calendar.getInstance();
		fechaInicial.setTime(fechaFinAnterior);
		fechaInicial.add(Calendar.MONTH, 1);
		fechaInicial.set(Calendar.DAY_OF_MONTH, 1);
		return fechaInicial;
	}

	/**
	 * Obtiene la fecha de inici de un periodo nuevo, tomando en cuenta las
	 * fechas maximas de contratacion
	 * 
	 * @param persona
	 *            la persona a la cual se obitiene las fechas inicial de
	 *            vigencia
	 * @return la fecha en que debe iniciar el periodo de un seguro
	 */
	private Calendar getFechaInicialNueva(Fisica persona) {
		Calendar fechaInicial = Calendar.getInstance();
		Calendar hoy = Calendar.getInstance();
		long modalidad = persona != null && persona.getIdModalidad() != null ? persona
				.getIdModalidad() : 0l;
		Calendar maximo = getFechaMaximaContratacion(hoy,
				aplicaBeneficio(persona, modalidad, hoy));
		fechaInicial.add(Calendar.MONTH, 1);
		fechaInicial.set(Calendar.DATE, 1);
		if (hoy.after(maximo)) {
			fechaInicial.add(Calendar.MONTH, 1);
		}
		return fechaInicial;
	}

	private Calendar getFechaInicialNueva() {
		Calendar fechaInicial = Calendar.getInstance();
		Calendar hoy = Calendar.getInstance();

		Calendar maximo = getFechaMaximaContratacion(hoy, false);
		fechaInicial.add(Calendar.MONTH, 1);
		fechaInicial.set(Calendar.DATE, 1);

		if (hoy.after(maximo)) {
			fechaInicial.add(Calendar.MONTH, 1);
		}

		return fechaInicial;
	}

	/**
	 * Obtiene la fecha final de un perdiodo de seguro Un año a partir de la
	 * fecha de inicio
	 * 
	 * @param fechaInicio
	 *            la fecha de inicio del periodo
	 * @return la fecha final del periodo
	 */
	private Calendar getFechaFinalPeriodo(Calendar fechaInicio) {
		Calendar fechaFinal = (Calendar) fechaInicio.clone();
		fechaFinal.add(Calendar.YEAR, 1);
		fechaFinal.add(Calendar.DATE, -1);
		return fechaFinal;
	}

	private Calendar getFechaFinalPeriodoContinuacionVoluntaria(
			Calendar fechaInicio) {
		Calendar fechaFinal = (Calendar) fechaInicio.clone();

		fechaFinal.add(Calendar.MONTH, 1);
		fechaFinal.set(Calendar.DAY_OF_MONTH, 1);
		fechaFinal.add(Calendar.DATE, -1);

		return fechaFinal;
	}

	/**
	 * Indica si un seguro se encuentra en fechas de renovacion. Un seguro puede
	 * ser renovado a partir del primer dia del mes en que vence y hasta el
	 * ultimo dia valido (ultimo del mes para personas sin eneficio y 25 para
	 * personas con beneficio) del siguiente mes a su vencimiento
	 * 
	 * @param seguro
	 *            a la cual se verfica la vigencia
	 * @return true en caso de estar en periodo de renovacion
	 * @throws IvroException
	 *             Error de datos incorretos
	 */
	public Boolean isPeriodoRenovacion(SeguroIvro seguro) throws IvroException {
		if (seguro.getCveIdSeguroIvro() != null
				&& seguro.getCveIdSeguroIvro() > 0l) {
			seguro = consultaSeguroIvro.buscaSeguroPorId(seguro
					.getCveIdSeguroIvro());
		}

		boolean seguroValido = validarSeguroRenovacion(seguro);

		if (!seguroValido) {
			return seguroValido;
		}

		Date hoy = new Date();

		// EstadoSeguroIvro estado = seguro.getEstadoSeguro();
		// if (!(estado.getIdEstadoSeguro() ==
		// EstadoSeguroIvroEnum.ACTIVO.getId() || estado
		// .getIdEstadoSeguro() == EstadoSeguroIvroEnum.CONCLUIDO.getId())) {
		// return false;
		// }
		// Date fechaSeguro = seguro.getFechaFin();
		//
		// if (fechaSeguro == null) {
		// throw new IvroException(IvroConstants.COD_NO_FECHA_VENCIMIENTO,
		// IvroConstants.MSG_NO_FECHA_VENCIMIENTO);
		// }
		Date fechaIniRenov = obtenerfechaInicioDeRenovacion(seguro);
		// Calendar fechaIniRenovaion = Calendar.getInstance();
		// fechaIniRenovaion.setTime(seguro.getFechaFin());
		// fechaIniRenovaion.set(Calendar.DAY_OF_MONTH, 1);

		// la final para personas sin beneficios es el ultimo dia del mes

		Date fechaFinrenov = obtenerFechaFinRenovacion(seguro);
		// Calendar fechaFinRenovaion = Calendar.getInstance();
		// fechaFinRenovaion.setTime(seguro.getFechaFin());
		// fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, 1);
		// fechaFinRenovaion.add(Calendar.MONTH, 2);
		// fechaFinRenovaion.set(Calendar.DATE, -1);
		// Fisica persona = null;
		// if (seguro.getTramite() != null &&
		// seguro.getTramite().getBeneficiarios() != null) {
		// persona = seguro.getTramite().getBeneficiarios()[0];
		// } else {
		// // Revisamos si es parte de los seguros migrados
		// persona =
		// consultaBeneficiarioMigradoEntity.buscaBeneficiarioSeguro(seguro);
		// }
		// // para seguros con modalidades que pueden aplicar beneficios y
		// tengan
		// // asociado a su beneficiario
		// // los buscamos y si apica se mueve la fecha al 25 del mes ,
		// if (aplicaBeneficio(persona, seguro.getModalidad().getIdModalidad(),
		// Calendar.getInstance())) {
		// fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, DIA_BENEFICIO);
		// }

		// La siguiente linea se incorporó en el método
		// obtenerfechaInicioDeRenovacion
		// fechaIniRenovaion = DateUtils.truncate(fechaIniRenovaion,
		// Calendar.DATE);
		// Esto se integro en la función obtenerFechaFinRenovacion(seguro)
		// fechaFinRenovaion = DateUtils.truncate(fechaFinRenovaion,
		// Calendar.DATE);
		// fechaFinRenovaion.set(Calendar.HOUR_OF_DAY, HORA_MAXIMA_RENOVACION);
		// Date fechaFinrenov = IvroDateUtils.getFechaHabil(fechaFinRenovaion,
		// parametrosEntity.getDiasFeriados());

		// return (hoy.after(fechaIniRenovaion.getTime()) &&
		// hoy.before(fechaFinrenov))
		// || DateUtils.isSameDay(hoy, fechaIniRenovaion.getTime())
		// || DateUtils.isSameDay(hoy, fechaFinrenov);

		boolean esRenovacion = (hoy.after(fechaIniRenov) && hoy
				.before(fechaFinrenov))
				|| DateUtils.isSameDay(hoy, fechaIniRenov)
				|| DateUtils.isSameDay(hoy, fechaFinrenov);

		boolean esExtemporanea = false;

		if (esRenovacion) {
			Calendar calFechaFinRenovacion = Calendar.getInstance();
			calFechaFinRenovacion.setTime(fechaFinrenov);

			Calendar calFechaHoy = Calendar.getInstance();
			if (calFechaHoy.get(Calendar.MONTH) == calFechaFinRenovacion
					.get(Calendar.MONTH)) {
				esExtemporanea = true;
			}
		} 
		seguro.setExtemporanea(esExtemporanea);
		seguro.setEnRenovacion(esRenovacion);

		return esRenovacion;
	}
                       
        public SeguroIvro isPeriodoRenovacionTipo(SeguroIvro seguro) throws IvroException {
		if (seguro.getCveIdSeguroIvro() != null
				&& seguro.getCveIdSeguroIvro() > 0l) {
			seguro = consultaSeguroIvro.buscaSeguroPorId(seguro
					.getCveIdSeguroIvro());
		}

		Date hoy = new Date();

		Date fechaIniRenov = obtenerfechaInicioDeRenovacion(seguro);
		Date fechaFinRenovOportuna = obtenerFechaFinRenovacionIvro(seguro);
                Calendar fechaFinRenovExtemporanea =  Calendar.getInstance();
                fechaFinRenovExtemporanea.setTime(fechaFinRenovOportuna);
                fechaFinRenovExtemporanea.add(Calendar.MONTH, 1);
                
                        
                LOGGER.debug("fechaIniRenov:" + fechaIniRenov);
                LOGGER.debug("fechaFinRenovOportuna:" + fechaFinRenovOportuna);
                LOGGER.debug("hoy:" + hoy);
                LOGGER.debug("fechaFinRenovExtemporanea:" + fechaFinRenovExtemporanea.getTime());
                
		boolean esRenovacion = (hoy.after(fechaIniRenov) && hoy
				.before(fechaFinRenovExtemporanea.getTime()))
				|| DateUtils.isSameDay(hoy, fechaIniRenov)
				|| DateUtils.isSameDay(hoy, fechaFinRenovExtemporanea.getTime());

		boolean esExtemporanea = false;

		if (esRenovacion) {
		    esExtemporanea  = this.esRenovacionExtemporanea(fechaFinRenovOportuna);

        }
		seguro.setExtemporanea(esExtemporanea);
		seguro.setEnRenovacion(esRenovacion);
                
                LOGGER.debug("esRenovacion:" + esRenovacion);
                LOGGER.debug("esExtemporanea:" + esExtemporanea);

		return seguro;
	}
        
        private static final boolean esRenovacionExtemporanea(Date fechaFin) {
        boolean extemporanea= false;
        // Si no hay seguros activamos la accion de compra
        if (fechaFin  != null) {
            
    		Calendar hoy = Calendar.getInstance();
    		hoy.setTime(new Date());

    		Calendar fechaFinRenovaion = Calendar.getInstance();
    		Calendar fechaFinSeguro = Calendar.getInstance();
    		fechaFinRenovaion.setTime(fechaFin);
    		fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, 1);
    		fechaFinRenovaion.add(Calendar.MONTH, 1);
    		fechaFinRenovaion.set(Calendar.DATE, +31);
            
    		fechaFinSeguro.setTime(fechaFin);

            extemporanea = (hoy.after(fechaFinSeguro) && hoy.before(fechaFinRenovaion));

            LOGGER.info("Hoy: " + hoy.getTime() +
                    "\nFecha fin seguro: " + fechaFinSeguro.getTime() +
                    "\nFecha Fin Renov: " + fechaFinRenovaion.getTime() +
                    "\nEs extemporanea: "+extemporanea);
        }
        return extemporanea;
    }

	/**
	 * Indica si la renovación es extemporanea
	 * 
	 * @param seguro
	 * @return
	 * @throws IvroException
	 */
	public Boolean isRenovacionExtemporanea(SeguroIvro seguro)
			throws IvroException {
		if (seguro.getCveIdSeguroIvro() != null
				&& seguro.getCveIdSeguroIvro() > 0l) {
			seguro = consultaSeguroIvro.buscaSeguroPorId(seguro
					.getCveIdSeguroIvro());
		}

		Date hoy = new Date();

		Date fechaIniRenov = obtenerfechaInicioDeRenovacion(seguro);
		Date fechaFinrenov = obtenerFechaFinRenovacion(seguro);
		boolean esRenovacion = (hoy.after(fechaIniRenov) && hoy
				.before(fechaFinrenov))
				|| DateUtils.isSameDay(hoy, fechaIniRenov)
				|| DateUtils.isSameDay(hoy, fechaFinrenov);

		boolean esExtemporanea = false;

		if (esRenovacion) {
			Calendar calFechaFinRenovacion = Calendar.getInstance();
			calFechaFinRenovacion.setTime(fechaFinrenov);

			Calendar calFechaHoy = Calendar.getInstance();

            LOGGER.info("FechaHoy: "+calFechaHoy.getTime());
            LOGGER.info("calFechaFinRenovacion: "+calFechaFinRenovacion.getTime());

			if (calFechaHoy.get(Calendar.MONTH) == calFechaFinRenovacion
					.get(Calendar.MONTH)) {
				esExtemporanea = true;
			}
		}

		return esExtemporanea;
	}


	/**
	 * Verifica si el seguro esta en estado válido para renovación y si cuenta
	 * con la información necesaria para realizarla.
	 * 
	 * @param seguro
	 * @return
	 * @throws IvroException
	 */
	private boolean validarSeguroRenovacion(SeguroIvro seguro)
			throws IvroException {
		EstadoSeguroIvro estado = seguro.getEstadoSeguro();
		if (!(estado.getIdEstadoSeguro() == EstadoSeguroIvroEnum.ACTIVO.getId() || estado
				.getIdEstadoSeguro() == EstadoSeguroIvroEnum.CONCLUIDO.getId())) {
			return false;
		}
		Date fechaSeguro = seguro.getFechaFin();
		if (fechaSeguro == null) {
			throw new IvroException(IvroConstants.COD_NO_FECHA_VENCIMIENTO,
					IvroConstants.MSG_NO_FECHA_VENCIMIENTO);
		}
		return true;
	}

	/**
	 * Calcula la fecha de inicio de renovación en base a la fecha de fin del
	 * seguro vigente
	 * 
	 * @return Calendar
	 */
	private Date obtenerfechaInicioDeRenovacion(SeguroIvro seguro) {
		Calendar fechaIniRenovaion = Calendar.getInstance();
		fechaIniRenovaion.setTime(seguro.getFechaFin());

        //no es el primer dia, son 30 dias naturales antes del ultimo
		fechaIniRenovaion.add(Calendar.DAY_OF_YEAR, -29);
		fechaIniRenovaion = DateUtils
				.truncate(fechaIniRenovaion, Calendar.DATE);
		return fechaIniRenovaion.getTime();
	}
   
	/**
	 * Calcula la fecha de fin de renovación en base a la fecha de fin de seguro
	 * y a la condición de si es o no una persona que cuenta con beneficio RISS
	 * y días feriados
	 * 
	 * @param seguro
	 *            Seguro vigente
	 * @return Calendar
	 */
	private Date obtenerFechaFinRenovacion(SeguroIvro seguro) {
		Calendar fechaFinRenovaion = Calendar.getInstance();
		fechaFinRenovaion.setTime(seguro.getFechaFin());
		fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, 1);
		fechaFinRenovaion.add(Calendar.MONTH, 2);
                
		Fisica persona = null;
		if (seguro.getTramite() != null
				&& seguro.getTramite().getBeneficiarios() != null) {
			persona = seguro.getTramite().getBeneficiarios()[0];
		} else {
			// Revisamos si es parte de los seguros migrados
			persona = consultaBeneficiarioMigradoEntity
					.buscaBeneficiarioSeguro(seguro);
		}
		// para seguros con modalidades que pueden aplicar beneficios y tengan
		// asociado a su beneficiario
		// los buscamos y si apica se mueve la fecha al 25 del mes ,
		if (aplicaBeneficio(persona, seguro.getModalidad().getIdModalidad(),
				Calendar.getInstance())) {
			fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, DIA_BENEFICIO);
		}

		fechaFinRenovaion = DateUtils
				.truncate(fechaFinRenovaion, Calendar.DATE);
		fechaFinRenovaion.set(Calendar.HOUR_OF_DAY, HORA_MAXIMA_RENOVACION);
		Date fechaFinrenov = IvroDateUtils.getFechaHabil(fechaFinRenovaion,
				parametrosEntity.getDiasFeriados());

		return fechaFinrenov;
	}

	/**
	 * Obtiene la fecha maxima de contratacion de un seguro
	 * 
	 * @param fecha
	 *            la fecha actual a obtener su maximo
	 * @param conBeneficio
	 *            indica si la persona tiene beneficio
	 * @return la fecha limite de pago
	 */
	private Calendar getFechaMaximaContratacion(Calendar fecha,
			boolean conBeneficio) {
		Calendar max = (Calendar) fecha.clone();
		max = DateUtils.truncate(max, Calendar.DATE);
		if (!conBeneficio) {
			max.set(Calendar.DAY_OF_MONTH, 1);
			max.add(Calendar.MONTH, 1);
			max.add(Calendar.DATE, -1);
		} else {
			max.set(Calendar.DAY_OF_MONTH, DIA_BENEFICIO);
		}
		max.add(Calendar.HOUR_OF_DAY, HORA_MAXIMA_RENOVACION);
		Date maximo = IvroDateUtils.getFechaHabil(max,
				parametrosEntity.getDiasFeriados());
		max.setTime(maximo);
		return max;
	}
        
        /**
	 * Calcula la fecha de fin de renovación para IVRO en base a la fecha de fin de seguro
	 * y a la condición de si es o no una persona que cuenta con beneficio RISS
	 * y días feriados
	 * 
	 * @param seguro
	 *            Seguro vigente
	 * @return Calendar
	 */
	public Date obtenerFechaFinRenovacionIvro(SeguroIvro seguro) {
		Calendar fechaFinRenovaion = Calendar.getInstance();
		fechaFinRenovaion.setTime(seguro.getFechaFin());
                
		Fisica persona = null;
		if (seguro.getTramite() != null
				&& seguro.getTramite().getBeneficiarios() != null) {
			persona = seguro.getTramite().getBeneficiarios()[0];
		} else {
			// Revisamos si es parte de los seguros migrados
			persona = consultaBeneficiarioMigradoEntity
					.buscaBeneficiarioSeguro(seguro);
		}
		// para seguros con modalidades que pueden aplicar beneficios y tengan
		// asociado a su beneficiario
		// los buscamos y si apica se mueve la fecha al 25 del mes ,
		if (aplicaBeneficio(persona, seguro.getModalidad().getIdModalidad(),
				Calendar.getInstance())) {
			fechaFinRenovaion.set(Calendar.DAY_OF_MONTH, DIA_BENEFICIO);
		}

		fechaFinRenovaion = DateUtils
				.truncate(fechaFinRenovaion, Calendar.DATE);
		fechaFinRenovaion.set(Calendar.HOUR_OF_DAY, HORA_MAXIMA_RENOVACION);
		Date fechaFinrenov = IvroDateUtils.getFechaHabil(fechaFinRenovaion,
				parametrosEntity.getDiasFeriados());

		return fechaFinrenov;
	}

	/**
	 * VErifica so una persona cuenta con un beneficio a partir de su nss,
	 * modalidad y fecha de inicio
	 * 
	 * @param idModalidad
	 *            identificador de la modalidad de la persona
	 * @param persona
	 *            la persona a buscar su beneficio
	 * @param fechaInicio
	 *            fecha de inicio del periodo a buscar su beneficio (Dao que
	 *            estamos hablando de ivro la feha final de consulta es un año
	 *            posterior al actual)
	 * @return true si la persona aplica beneficio
	 */
	private boolean aplicaBeneficio(Fisica persona, long idModalidad,
			Calendar fechaInicio) {
		ModalidadEnum modalidadEnum = ModalidadEnum.fromId(idModalidad);
		if (ArrayUtils.contains(APLICA_BENEFICIOS, modalidadEnum)
				&& persona != null) {
			Calendar fechaFin = Calendar.getInstance();
			fechaFin.add(Calendar.YEAR, 1);
			try {
				Beneficio beneficio = beneficioRissServiceBusinessRemote
						.obtenerBeneficioPorNSS(persona.getNss(),
								fechaInicio.getTime(), fechaFin.getTime());
				if (beneficio != null
						&& beneficio.getEstadoBeneficio() != null
						&& beneficio.getEstadoBeneficio()
								.getIdEstadoBeneficio()
								.equals(EstadoBeneficioEnum.ACTIVO.getClave())) {
					return true;
				}
			} catch (BeneficioRissException e) {
				LOGGER.debug("Sin beneficios");
			}

		}
		return false;
	}

	@Override
	public Boolean aplicaRecargo(Long idModalidad) {
		boolean aplicaRecargo = false;

		if (idModalidad == ModalidadEnum.CUARENTA.getId()) {
			Calendar fechaHoy = Calendar.getInstance();

			Calendar fechaPago = Calendar.getInstance();
			fechaPago.set(Calendar.DATE, DIA_RECARGO+1);

            Date fechaPagoHabil = IvroDateUtils.getFechaHabil(fechaPago,
                    new ArrayList<Date>());

            Date fechaLimitePago = new Date();

			if(fechaPagoHabil.after(fechaPago.getTime())){
                fechaLimitePago = PeriodoUtil.truncaFecha(fechaPagoHabil).getTime();
            }else{
                fechaLimitePago = PeriodoUtil.truncaFecha(fechaPago).getTime();
            }

			LOGGER.debug("Fecha hoy {} ", fechaHoy);
			LOGGER.debug("Fecha pago calculado {}", fechaLimitePago);
			if (fechaLimitePago.before(fechaHoy.getTime())) {
				aplicaRecargo = true;
			}
		}

		LOGGER.debug("aplicaRecargo {}", aplicaRecargo);
		return aplicaRecargo;
	}
	
	/**
	 * Verifica si el seguro esta en periodo de renovacion
	 * 
	 * @param seguro
	 * @return
	 * @throws IvroException
	 */
	public Boolean isPeriodoRenovacionSSF(SeguroIvro seguro) throws IvroException{
		Date hoy = new Date();
		Date fechaIniRenov = obtenerfechaInicioDeRenovacion(seguro);
	    Date fechaFinrenov = obtenerFechaFinRenovacionSSF(seguro);
	    return (hoy.after(fechaIniRenov) && hoy
				.before(fechaFinrenov))
				|| DateUtils.isSameDay(hoy, fechaIniRenov)
				|| DateUtils.isSameDay(hoy, fechaFinrenov);
	}
	
	/**
	 * Obtiene la fecha final del periodo de renovacion
	 * 
	 * @param seguro
	 * @return
	 */
	public Date obtenerFechaFinRenovacionSSF(SeguroIvro seguro){
                Calendar tiempoRenovacionExtemporanea=Calendar.getInstance();
		tiempoRenovacionExtemporanea.setTime(seguro.getFechaFin());
                return IvroDateUtils.agregaDiasHabiles(tiempoRenovacionExtemporanea, parametrosEntity.getDiasFeriados(), DIAS_RENOVAVION_EXTAPORENEA_SSF);
	}

	/**
	 * Verifica si es una renovacion Extemporanea
	 * 
	 * @param seguro
	 * @return
	 * @throws IvroException
 */
        @Override
	public Boolean isRenovacionExtemporaneaSSF(SeguroIvro seguro) throws IvroException {
		Date hoy = new Date();
		Date fechaFinrenov = seguro.getFechaFin();
                Calendar calendarFechaFin = Calendar.getInstance();
                calendarFechaFin.setTime(fechaFinrenov);
                calendarFechaFin.add(Calendar.DAY_OF_YEAR, 1);
                calendarFechaFin = DateUtils
				.truncate(calendarFechaFin, Calendar.DATE);
		return hoy.after(calendarFechaFin.getTime());
	}
	
    @Override
    public AsignacionNssIvro obtenerAsignacionNss(Long idPersona) throws NoResultException, IvroException {
        AsignacionNssIvro asignacionNssIvro = new AsignacionNssIvro();
        try {
            DitAsignacionNss asignacionNss;
            Query query = em.createNamedQuery("buscaAsignacionXpersona", DitAsignacionNss.class);
            query.setParameter("idPersona", idPersona);
            asignacionNss = (DitAsignacionNss) query.getSingleResult();
            asignacionNssIvro.setCveIdAsignacionNss(asignacionNss.getCveIdAsignacionNss());
            if (asignacionNss.getFecRegistroBaja() != null) {
                asignacionNssIvro.setFecRegistroBaja(asignacionNss.getFecRegistroBaja());
            }
        } catch (NoResultException e) {
            asignacionNssIvro = null;
        } catch (Exception e1) {
            LOGGER.error("Error obtenerAsignacionNss", e1);
            asignacionNssIvro = null;
        }
        return asignacionNssIvro;
    }

    @Override
    public AsignacionNssIvro obtenerAsignacionNss(String numNss) throws NoResultException, IvroException {
        AsignacionNssIvro asignacionNssIvro = new AsignacionNssIvro();
        try {
            DitAsignacionNss asignacionNss;
            Query query = em.createNamedQuery("buscaAsignacionXnssSinPersona", DitAsignacionNss.class);
            query.setParameter("numNss", numNss);
            asignacionNss = (DitAsignacionNss) query.getSingleResult();
            asignacionNssIvro.setCveIdAsignacionNss(asignacionNss.getCveIdAsignacionNss());
            if (asignacionNss.getFecRegistroBaja() != null) {
                asignacionNssIvro.setFecRegistroBaja(asignacionNss.getFecRegistroBaja());
            }
        } catch (NoResultException e) {
            asignacionNssIvro = null;
        } catch (Exception e1) {
            LOGGER.error("Error obtenerAsignacionNss", e1);
            asignacionNssIvro = null;
        }
        return asignacionNssIvro;
    }
}
