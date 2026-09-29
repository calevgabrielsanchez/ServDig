/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.CancelarBeneficioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.HistorialUltimoSeguroCotizadoDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.UltimoTrabajoModalidad40DTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.*;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model.DatosMovSeguro;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaCancelacionBeneficio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.MotivoCancelacionBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.BdtutUltimoTrabajo;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.sindo.MovimientoTrabajadorSindo;
import mx.gob.imss.digital.modelo.sindo.MovimientosTrabajadorSindo;

import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.ws.estatusvigencia.individual.implementacion.EstatusVigenciaIndividual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;


/**
 * Implementacion de los servicios de seguros, altas, vencimientos y
 * cancelaciones por riss
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "seguroIvroServiceBusiness", mappedName = "seguroIvroServiceBusiness")
public class SeguroIvroServiceBusiness implements SeguroIvroServiceRemote {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeguroIvroServiceBusiness.class);
    /**
     *
     */
    public static final String FORMAT_DATE_GUINMEDIO_DD_MM_YYYY = "dd-MM-yyyy";

	/**
     * Bean con los sevicios sobre los seguros
     */
    @EJB
    private SeguroIvroServiceLocal seguroIvroServiceLocal;
    /**
     * Servicio para generar los movimientos de alta o baja
     */
    @EJB
    private GeneradorMovimientoLocal generadorMovimientoLocal;
    
    @EJB
    private ConsultaSeguroIvroLocal consultaSeguroIvro;
    
    @EJB
    private CotizacionServiceRemote cotizacionService;
    
    @EJB
    private CompraServiceLocal compraService;

	@EJB
	private SolicitudServiceRemote solicitudServiceRemote;

	@EJB
	private ValidaVigenciaRemote validaVigenciaRemote;

	@EJB
	private RuleServiceBusinessRemote ruleServiceBusinessRemote;

    @Autowired
    private CancelarBeneficioServiceBusinessRemote cancelarBeneficioServiceBusiness;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceRemote
     * #activaSeguro(mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra)
     */
    @Override
    public MovimientosTrabajadorSindo activaSeguro(ActualizacionCompra comprasPagadas) throws IvroException {
        List<DatosMovSeguro> datos = seguroIvroServiceLocal.activaSeguro(comprasPagadas);
        seguroIvroServiceLocal.desactivarSegurosRechazados(datos);
        MovimientosTrabajadorSindo movimientos = new MovimientosTrabajadorSindo();
        movimientos.setMovimientoTrabajadorSindo(new MovimientoTrabajadorSindo[0]);
		movimientos.setMovimientoTrabajadorSindoFuturo(new MovimientoTrabajadorSindo[0]);
        return movimientos;
    }
    
    @Override
	public MovimientosTrabajadorSindo generaMovimientosAlta(ActualizacionCompra comprasPagadas) throws IvroException {
		List<DatosMovSeguro> datos = seguroIvroServiceLocal.generaMovActivaSeguro(comprasPagadas);
		LOGGER.info("Cantidad de datos: "+datos.size());
		MovimientosTrabajadorSindo movimientos = generadorMovimientoLocal.generaMovimientosAlta(datos);
        return movimientos;
	}

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceRemote
     * #venceSeguro(mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra)
     */
    @Override
    public MovimientoTrabajadorSindo[] venceSeguro(ActualizacionCompra comprasVencidas) throws IvroException {
        List<DatosMovSeguro> datos = seguroIvroServiceLocal.venceSeguro(comprasVencidas);
        return new MovimientoTrabajadorSindo[0];
    }

	@Override
	public MovimientoTrabajadorSindo[] generaMovimientosBaja(ActualizacionCompra comprasVencidas) throws IvroException {
		List<DatosMovSeguro> datos = seguroIvroServiceLocal.generaMovVencSeguro(comprasVencidas);
		LOGGER.info("datos: "+datos.size());
		List<MovimientoTrabajadorSindo> movimientos = generadorMovimientoLocal.generaMovimientosBaja(datos);
		return movimientos.toArray(new MovimientoTrabajadorSindo[movimientos.size()]);
	}

	/*
     * (non-Javadoc)
     *
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceRemote#bajaDeSeguroPorReingresoRO(mx.gob.imss.digital.modelo.seguros.SeguroIvro)
     */
    @Override
    public MovimientoTrabajadorSindo[] bajaDeSeguroPorReingresoRO(SeguroIvro seguroIvro) throws IvroException {
        List<DatosMovSeguro> datos = seguroIvroServiceLocal.bajaDeSeguroPorReingresoRO(seguroIvro);
        return new MovimientoTrabajadorSindo[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceRemote#vencimientoSeguroVigencia()
     */
    @Override
    public MovimientoTrabajadorSindo[] vencimientoSeguroVigencia() throws IvroException {
        //Se inhibe el vencimientoSeguroVigencia para el proceso Bach
        //List<DatosMovSeguro> datos = seguroIvroServiceLocal.concluirSegurosVigencia();
        List<DatosMovSeguro> datos = new ArrayList<DatosMovSeguro>();
        return new MovimientoTrabajadorSindo[0];
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceRemote
     * #cancelaSeguroRiss(mx.gob.imss.digital.modelo.persona.Persona)
     */
    @Override
    public Persona cancelaSeguroRiss(Persona persona) throws IvroException {
        return seguroIvroServiceLocal.cancelaSeguroRiss(persona);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceRemote
     * #activaSeguros(mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra)
     */
    @Override
    public SeguroIvro[] activaSeguros(ActualizacionCompra comprasPagadas) {
        List<SeguroIvro> seguros = seguroIvroServiceLocal.activaSeguros(comprasPagadas);
        return seguros.toArray(new SeguroIvro[seguros.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceRemote
     * #venceSeguros(mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra)
     */
    @Override
    public SeguroIvro[] venceSeguros(ActualizacionCompra comprasVencidas) {
        List<SeguroIvro> seguros = seguroIvroServiceLocal.venceSeguros(comprasVencidas);
        try{
            for(SeguroIvro temp:seguros){
                TramiteSeguroIvro tramite = temp.getTramite();
                SimpleDateFormat formato=new SimpleDateFormat(FORMAT_DATE_GUINMEDIO_DD_MM_YYYY);
                LOGGER.info("RFC: "+temp.getTitular().getRfc());
                LOGGER.info("NRP: "+tramite.getNrpFisica());
                LOGGER.info("NSS: "+temp.getTitular().getNss());
                LOGGER.info("Fecha:"+formato.format(new Date()));
                LOGGER.info("motivo:"+ MotivoCancelacionBeneficioEnum.POR_ADEUDO_IMSS.getClave());
                RespuestaCancelacionBeneficio response = cancelarBeneficioServiceBusiness
                        .cancelarBeneficio(temp.getTitular().getRfc(),
                                tramite.getNrpFisica(),
                                temp.getTitular().getNss(),
                                formato.format(new Date()),
                                MotivoCancelacionBeneficioEnum.POR_ADEUDO_IMSS.getClave());
                LOGGER.info("Respuesta cancelacion:   "+response.getDescripcion());
                LOGGER.info("Error:   "+response.getClaveError());
                LOGGER.info("Exito:   "+response.getExito());
            }

        }catch(Exception e){

        }
        return seguros.toArray(new SeguroIvro[seguros.size()]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceRemote#vencimientosSeguroVigencia()
     */
    @Override
    public SeguroIvro[] concluirSeguroVigencia() {
        List<SeguroIvro> seguros = seguroIvroServiceLocal.concluirSeguroVigencia();
        return seguros.toArray(new SeguroIvro[seguros.size()]);
    }

    @Override
	public void asociaPagoAutomaticoSeguro(SeguroIvro seguro) {
    	try {

    		/*
			 * Se busca la compra recien creada, el OSB manda el id de la nueva compra
			 * en el atributo compra, no es la compra relacionada al seguro CVRO
			 */
			Compra compraNueva = this.compraService.findCompraById(seguro.getCompra().getIdCompra());
			
			SeguroIvro seguroMod40 = this.consultaSeguroIvro.buscaSeguroPorId(seguro
					.getCveIdSeguroIvro());
			
			this.compraService.cambiarPagosDeCompra(seguroMod40.getCompra(), compraNueva);
			
			Cotizacion cotizacionOriginal = this.cotizacionService
					.findCotizacion(seguroMod40.getCompra().getIdCotizacion());
			Cotizacion cotizacionNueva = this.cotizacionService
					.findCotizacion(compraNueva.getIdCotizacion());

			BigDecimal nuevoSalario = cotizacionNueva.getDetalle()
					.getEmpleados()[0].getSalario();
			
			/*
			 * Se toman siempre las primeras posiciones, ya que para este tipo
			 * de seguro la cotizacion siempre se tiene solo a una persona con
			 * un perido
			 */
			PeriodoCuota periodosOriginales[] = cotizacionOriginal.getDetalle()
					.getEmpleados()[0].getPeriodos();
			
			int numPeriodos = periodosOriginales.length;
			
			if (numPeriodos == 1) {
				/*
				 * Se settea el salario de la cotizacion original al primer
				 * periodo para mantener el salario con el que se cotiza la
				 * primera vez el seguro CVRO y no perderlo cuando se vayan
				 * agregando los periodos nuevos
				 */
				PeriodoCuota primerPeriodo = periodosOriginales[0];
				primerPeriodo.setSalarioPeriodo(cotizacionOriginal.getDetalle()
						.getEmpleados()[0].getSalario());
			}
			
			PeriodoCuota periodoNuevo = cotizacionNueva.getDetalle()
					.getEmpleados()[0].getPeriodos()[0];
			periodoNuevo.setSalarioPeriodo(nuevoSalario);
			periodoNuevo.setOrden(numPeriodos + 1);
			
			/* 
			 * Se agrega el nuevo periodo a la cotizacion original
			 * con la finalidad de mantener el historico
			 */
			List<PeriodoCuota> periodosTmp = new ArrayList<PeriodoCuota>(Arrays.<PeriodoCuota>asList(periodosOriginales));
			periodosTmp.add(periodoNuevo);
			
			periodosOriginales = periodosTmp.toArray(new PeriodoCuota[periodosTmp.size()]);
			
			/*
			 * Siempre se settea el salario de la cotizacion nueva a la
			 * cotizacion original para que en el mes siguiente se tome del,
			 * mismo lugar y no se tenga que buscar a lo largo de los periodos
			 */
			cotizacionOriginal.getDetalle().getEmpleados()[0].setSalario(nuevoSalario);
			cotizacionOriginal.getDetalle().getEmpleados()[0].setPeriodos(periodosOriginales);
			
			this.cotizacionService.actualizaCotizacion(cotizacionOriginal);

			Long cveIdSeguro = seguro.getCveIdSeguroIvro();
			Long cveIdCompraAnt = compraNueva.getIdCompra();
			Long cveIdCompraNva = seguroMod40.getCompra().getIdCompra();
            this.consultaSeguroIvro.guardaHistSeguroCompra(cveIdCompraAnt,cveIdCompraNva,cveIdSeguro);

		} catch (SUAException e) {
			LOGGER.error(e.getMessage());
		}
	}
    
    @Override
    public MovimientoTrabajadorSindo[] bajaMensualSindoSeguroCvro() throws IvroException {

        //Se inhibe la funcionalidad de busqueda de seguros baja mensual para el proceso bach
        //List<DatosMovSeguro> datos = seguroIvroServiceLocal.obtenerSegurosCvroBajaMensual();
    	List<DatosMovSeguro> datos = new ArrayList<DatosMovSeguro>();
        
        List<MovimientoTrabajadorSindo> movimientos = generadorMovimientoLocal.generaMovimientosBaja(datos);

		// Se actualiza fecha de movimiento al ultimo dia del mes anterior
		for (MovimientoTrabajadorSindo movimiento : movimientos) {
			Calendar fechaMovimiento = Calendar.getInstance();
			fechaMovimiento.set(Calendar.DATE, 1);
			fechaMovimiento.add(Calendar.DATE, -1);

			movimiento.setfMovto(fechaMovimiento.getTime());
		}

        return movimientos.toArray(new MovimientoTrabajadorSindo[movimientos.size()]);
    }
    
    /**
	 * Servicio que vence los seguros asociados a las compras vencidas recibidas de la modalidad 40
	 * 
	 * @param comprasVencidas
	 */
	public void venceSegurosPorMoraMod40(ActualizacionCompra comprasVencidas){
		seguroIvroServiceLocal.venceSegurosPorMoraMod40(comprasVencidas);
	}
	
	@Override
	public Pago validarGeneracionNuevoPeriodoPagoCvro(long idSeguro) {

		LOGGER.debug("Se inicia la validacion para checar el periodo del ultimo pago para el seguro CVRO "
				+ idSeguro);

		Pago pago;
		
		try {
			pago = this.consultaSeguroIvro.buscaUltimoPagoSeguro(idSeguro);

			// Se checa si el pago obtenido es para el periodo actual de calculo
			Date inicioPeriodo = pago.getFechaInicioPeriodo();

			Calendar periodo = Calendar.getInstance();
			periodo.setTime(inicioPeriodo);
			periodo.set(Calendar.DATE, 1);

			inicioPeriodo = periodo.getTime();

			Date fechaActual = new Date();

			if (fechaActual.after(inicioPeriodo)
					&& fechaActual.before(pago.getFechaFinPeriodo())) {
				LOGGER.debug("El seguro CVRO " + idSeguro
						+ " YA cuenta con un pago para le periodo");

				pago.setErrorFormGeneral("100|El seguro CVRO ya cuenta con un pago para el periodo");
			} else {
				LOGGER.debug("El seguro CVRO " + idSeguro
						+ " NO cuenta con un pago para le periodo");
			}
		} catch (SUAException e) {
			LOGGER.error(e.getMessage());

			pago = new Pago();
			pago.setErrorFormGeneral("200|Error al obtener el ultimo pago para el seguro "
					+ idSeguro);
		}

		LOGGER.debug("Se finalizo la validacion para checar el periodo del ultimo pago para el seguro CVRO "
				+ idSeguro);
		
		return pago;
	}

	/*
     * (non-Javadoc)
     *
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceRemote
     * #confirmaPagosDeSeguro(mx.gob.imss.digital.modelo.seguros.SeguroIvro)
     */
	@Override
	public SeguroIvro confirmaPagosDeSeguro(SeguroIvro seguro){
		Pago pagoAnterior = new Pago();

		List<Pago> pagos = new ArrayList<Pago>(Arrays.asList(seguro.getCompra().getPagos()));
		boolean pasoPrimerPago = false;

		VigenciaTrabajdor vigenciaTrabajdor = null;
		RespuestaValidacionTrabajador respuestaValidacionTrabajador = null;
		boolean imprimible;

		try {

			for (Pago pago : pagos) {
				if(pasoPrimerPago && pagoAnterior != null){

					//Si no es el primer pago modificamos que solo sea imprimible si,
					// es un pago por pagar
					// su pago anterior ya fue pagado
					// su seguro esta activo
					imprimible = pago.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.POR_PAGAR.getId() &&
							pagoAnterior.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.PAGADO.getId() &&
							seguro.getEstadoSeguro().getIdEstadoSeguro() == EstadoSeguroIvroEnum.ACTIVO.getId();


					if(imprimible){
						// Si paso las primeras evaluaciones revisamos si sigue sin estar dado de alta en alguna modalidad incompatible

						// Revisamos si no se ha consultado su vigencia antes para invocar el servicio solo una vez
						if(vigenciaTrabajdor == null && respuestaValidacionTrabajador == null){
							long idAsignacionNss = solicitudServiceRemote.obtenerIdAsignacionNssPorNss(seguro.getTramite().getBeneficiarios()[0].getNss().substring(0,10));
							vigenciaTrabajdor = EstatusVigenciaIndividual.consultaVigenciaSeguroIndividual(String.valueOf(idAsignacionNss));
							vigenciaTrabajdor.setModalidadSolicitada(seguro.getModalidad().getNumModalidad());
							respuestaValidacionTrabajador = validaVigenciaRemote.validaContinuarVigenciaTrabajador(vigenciaTrabajdor);
						}

						imprimible = respuestaValidacionTrabajador.getValido();
					}

					if(imprimible && seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId()){

						//Si pasa las fases anteiores y ademas es modalidad 35 evaluamos si aun es un patron y ademas tiene un nrp vigente
						String rfc = ruleServiceBusinessRemote.obtenerNrpPrimaRiesgoMayorPersonaFisica(seguro.getTramite().getBeneficiarios()[0].getRfc());
						if(rfc != null && !rfc.isEmpty() && rfc.length() == 11){
							imprimible = true;
						}else {
							imprimible = false;
						}

					}

					pago.setImprimible(imprimible);
				}else {
					/*Si es el primer pago solo evaluamos si
						-El pago esta por pagar
					*/
					imprimible = pago.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.POR_PAGAR.getId();
					pago.setImprimible(imprimible);
					pasoPrimerPago = true;
				}
				pagoAnterior = pago;

			}

			seguro.getCompra().setPagos(pagos.toArray(new Pago[pagos.size()]));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return seguro;
	}

	@Override
	public String obtenZonaSalarialOriginal(String cveEnt, String cveMun) {
		return seguroIvroServiceLocal.obtenZonaSalarialOriginal(cveEnt,cveMun);
	}

	@Override
	public Fisica findPersonabyPago(Long cveIdPago) throws IvroException{
		try {
			return compraService.findPersonabyPago(cveIdPago);
		}catch (Exception e ){
			throw new IvroException(e.getMessage());
		}
	}

	@Override
    public boolean insertaCancelacionCuestionario(Beneficiario beneficiarioCancelar, String numSolicitud) {
        return seguroIvroServiceLocal.insertaCancelacionCuestionario(beneficiarioCancelar,numSolicitud);
    }

    @Override
    public Long recuperaIdAsignacionPorNSS(String nss) {
        return seguroIvroServiceLocal.recuperaIdAsignacionPorNSS(nss);
    }

    public boolean verificaBeneficiarioConEnfermedad(Long idAsignacion){
        return seguroIvroServiceLocal.verificaBeneficiarioConEnfermedad(idAsignacion);
    }
	
    // ================================================================
    // MÉTODOS AGREGADOS PARA BAJAS POR REINGRESO RO
    // ================================================================

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long procesarArchivoSindoBajas(byte[] contenidoArchivo,
                                          String nombreArchivo,
                                          String usuario) throws IvroException {
        return seguroIvroServiceLocal.procesarArchivoSindoBajas(contenidoArchivo, nombreArchivo, usuario);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long cargarArchivoSoloStaging(byte[] contenidoArchivo,
                                         String nombreArchivo,
                                         String usuario) throws IvroException {
        return seguroIvroServiceLocal.cargarArchivoSoloStaging(contenidoArchivo, nombreArchivo, usuario);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int reprocesarLote(Long idLote,
                              boolean soloErrores,
                              String usuario) throws IvroException {
        return seguroIvroServiceLocal.reprocesarLote(idLote, soloErrores, usuario);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int procesarPorRangoLineas(Long idLote,
                                      int lineaInicio,
                                      int lineaFin,
                                      String usuario) throws IvroException {
        return seguroIvroServiceLocal.procesarPorRangoLineas(idLote, lineaInicio, lineaFin, usuario);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleReingresoRODTO obtenerDetalleReingresoParaJSP(Long idSeguroIvro) {
        return seguroIvroServiceLocal.obtenerDetalleReingresoParaJSP(idSeguroIvro);
    }
    
	/**
	 * Ejecuta proceso automático de baja por mora. Busca seguros con pagos vencidos
	 * y ejecuta baja masiva.
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long ejecutarProcesoMoraAutomatico(String usuarioOperador) throws Exception {
		return seguroIvroServiceLocal.ejecutarProcesoMoraAutomatico(usuarioOperador);
	}

	/**
	 * Ejecuta baja por mora para un seguro específico. Incluye actualización de
	 * estados y registro de auditoría.
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean ejecutarBajaPorMora(Long cveIdSeguroIvro, Long cveIdLote, String usuarioOperador) {
		return seguroIvroServiceLocal.ejecutarBajaPorMora(cveIdSeguroIvro, cveIdLote, usuarioOperador);
	}

	/**
	 * Obtiene detalle de baja por mora para mostrar en JSP.
	 */
	@Override
	public mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleMoraDTO obtenerDetalleMoraParaJSP(
			Long idSeguroIvro) {
		return seguroIvroServiceLocal.obtenerDetalleMoraParaJSP(idSeguroIvro);
	}
	
	/**
	 * Obtiene detalle de baja por mora para mostrar en JSP.
	 */
	@Override
	public mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleBajaExpresaDTO obtenerDetalleBajaExpresaParaJSP(
			Long idSeguroIvro) {
		return seguroIvroServiceLocal.obtenerDetalleBajaExpresaParaJSP(idSeguroIvro);
	}

	/**
	 * Actualiza solo el estado del seguro a BAJA_POR_MORA. No crea registros de
	 * auditoría.
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean actualizarEstadoSeguroMora(Long cveIdSeguroIvro) {
		return seguroIvroServiceLocal.actualizarEstadoSeguroMora(cveIdSeguroIvro);
	}

	/**
	 * Crea solo registros de bitácora para seguros ya dados de baja.
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long registrarBitacoraBajaMora(Long cveIdSeguroIvro, Long cveIdLote, String usuarioOperador) {
		return seguroIvroServiceLocal.registrarBitacoraBajaMora(cveIdSeguroIvro, cveIdLote, usuarioOperador);
	}

	/**
	 * Completa bitácora para seguros con estado BAJA_POR_MORA sin registro. Útil
	 * para migración de bajas ejecutadas por procesos legados.
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long completarBitacoraBajasMoraExistentes(Date fechaInicio, Date fechaFin, String usuarioOperador) {
		return seguroIvroServiceLocal.completarBitacoraBajasMoraExistentes(fechaInicio, fechaFin, usuarioOperador);
	}

	/**
	 * Obtiene el tipo de baja actual del seguro. Retorna MORA, REINGRESO_RO,
	 * EXPRESA o null si no está en baja.
	 */
	@Override
	public String obtenerTipoBajaActual(Long idSeguroIvro) {
		return seguroIvroServiceLocal.obtenerTipoBajaActual(idSeguroIvro);
	}

	/**
	 *
	 * Delega a SeguroIvroServiceEntity.solicitarBajaExpresa()
	 *
	 * @param cveIdSeguroIvro ID del seguro
	 * @param motivo          Motivo de baja (opcional)
	 * @param usuario         Usuario autenticado
	 * @param ipSolicitud     IP del cliente
	 * @return ID de la solicitud creada
	 * @throws Exception Si validaciones fallan
	 */
	@Override
	public Long solicitarBajaExpresa(Long cveIdSeguroIvro, String motivo, String usuario, String ipSolicitud)
			throws Exception {
		return seguroIvroServiceLocal.solicitarBajaExpresa(cveIdSeguroIvro, motivo, usuario, ipSolicitud);
	}

	/**
	 *
	 * Delega a SeguroIvroServiceEntity.confirmarBajaExpresa()
	 *
	 * @param token          Token UUID del link del correo
	 * @param ipConfirmacion IP del cliente
	 * @return true si baja exitosa
	 * @throws Exception Si token inválido/expirado
	 */
	@Override
	public boolean confirmarBajaExpresa(String token, String ipConfirmacion) throws Exception {
		return seguroIvroServiceLocal.confirmarBajaExpresa(token, ipConfirmacion);
	}

	/**
	 * Ejecuta baja por MORA para un grupo específico de seguros
	 *
	 * @param idsSeguro       Lista de IDs de seguros a dar de baja
	 * @param usuarioOperador Usuario que ejecuta la operación
	 * @return ID del lote creado
	 * @throws Exception Si ocurre error
	 */
	@Override
	public Long ejecutarBajaPorMoraLista(List<Long> idsSeguro, String usuarioOperador) throws Exception {
		return seguroIvroServiceLocal.ejecutarBajaPorMoraLista(idsSeguro, usuarioOperador);
	}
	@Override
	  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	  public Integer marcarPagosVencidosPorFecha(Date fechaCorte, String usuarioOperador) {
	      return seguroIvroServiceLocal.marcarPagosVencidosPorFecha(fechaCorte, usuarioOperador);
	  }
	 
	  @Override
	  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	  public Integer marcarPagosVencidosSeguro(Long cveIdSeguroIvro, Date fechaCorte, String usuarioOperador) {
	      return seguroIvroServiceLocal.marcarPagosVencidosSeguro(cveIdSeguroIvro, fechaCorte, usuarioOperador);
	  }
	 
	  @Override
	  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	  public Long darBajaPorMoraHastaFecha(Date fechaCorte, String usuarioOperador) {
	      return seguroIvroServiceLocal.darBajaPorMoraHastaFecha(fechaCorte, usuarioOperador);
	  }
	 
	  @Override
	  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	  public Boolean darBajaPorMoraSeguro(Long cveIdSeguroIvro, String usuarioOperador) {
	      return seguroIvroServiceLocal.darBajaPorMoraSeguro(cveIdSeguroIvro, usuarioOperador);
	  }
	 
	  @Override
	  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	  public Long completarBitacorasBajaMoraDesde(Date fechaDesde, Date dummy, String usuarioOperador) {
	      return seguroIvroServiceLocal.completarBitacorasBajaMoraDesde(fechaDesde, dummy, usuarioOperador);
	  }
	  
	  @Override
	    public void guardarHistorialUltimoSeguroModalidad40(UltimoTrabajoModalidad40DTO ultimoTrabajo)
	            throws IvroException {
	        try {
	            if (ultimoTrabajo == null) {
	                throw new IllegalArgumentException("Los datos del ultimo trabajo son obligatorios");
	            }
	            if (ultimoTrabajo.getCveNss() == null || !ultimoTrabajo.getCveNss().trim().matches("[0-9]{11}")) {
	                throw new IllegalArgumentException("El NSS debe tener 11 digitos");
	            }
	            if (ultimoTrabajo.getCveModalidad() == null || ultimoTrabajo.getCveModalidad() < 0
	                    || ultimoTrabajo.getCveModalidad() > 99) {
	                throw new IllegalArgumentException("Modalidad de ultimo trabajo invalida");
	            }
	            if (ultimoTrabajo.getFechaUltimoTrabajo() == null
	                    || !ultimoTrabajo.getFechaUltimoTrabajo().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
	                throw new IllegalArgumentException("Fecha de ultimo trabajo invalida");
	            }
	            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	            formato.setLenient(false);
	            Date fecha = formato.parse(ultimoTrabajo.getFechaUltimoTrabajo());
	            if (ultimoTrabajo.getSalarioUltimoTrabajo() == null
	                    || Float.isNaN(ultimoTrabajo.getSalarioUltimoTrabajo())
	                    || Float.isInfinite(ultimoTrabajo.getSalarioUltimoTrabajo())
	                    || ultimoTrabajo.getSalarioUltimoTrabajo() < 0) {
	                throw new IllegalArgumentException("Salario de ultimo trabajo invalido");
	            }
	            if (ultimoTrabajo.getSemanasCotizadas() != null
	                    && (ultimoTrabajo.getSemanasCotizadas() < 0 || ultimoTrabajo.getSemanasCotizadas() > 999)) {
	                throw new IllegalArgumentException("Semanas en RO de los ultimos cinco anios invalidas");
	            }
	            Calendar calendario = Calendar.getInstance();
	            calendario.setTime(fecha);

	            HistorialUltimoSeguroCotizadoDTO historial = new HistorialUltimoSeguroCotizadoDTO();
	            historial.setCveCurp(ultimoTrabajo.getCveCurp());
	            historial.setCveModalidad(ultimoTrabajo.getCveModalidad());
	            historial.setCveMunicipioImss(null);
	            historial.setCveNss(ultimoTrabajo.getCveNss().trim());
	            historial.setRefRegistroPatronal(ultimoTrabajo.getRefRegistroPatronal());
	            historial.setCveRfcAsegurado(ultimoTrabajo.getCveRfcAsegurado());
	            historial.setCveUsuarioAlta("SERVICIOS DIGITALES");
	            if ("02".equals(ultimoTrabajo.getTipoMovObligatorio())) {
	                historial.setFecBajaUltimoTrabajo(fecha);
	            }
	            historial.setFecConsulta(new Date());
	            historial.setIndPension(ultimoTrabajo.getIndPension());
	            historial.setIndTrabajadorImss(ultimoTrabajo.getIndTrabajadorImss());
	            historial.setNomAsegurado(ultimoTrabajo.getNomAsegurado());
	            historial.setNumAnioUltimoTrabajo(calendario.get(Calendar.YEAR));
	            historial.setNumMesUltimoTrabajo(calendario.get(Calendar.MONTH) + 1);
	            historial.setSalarioUltimoTrabajo(new BigDecimal(Float.toString(ultimoTrabajo.getSalarioUltimoTrabajo())));
	            historial.setNumSemanasRoUlt5anios(ultimoTrabajo.getSemanasCotizadas());
	            historial.setStpAlta(new Date());

	            seguroIvroServiceLocal.guardarHistorialUltimoSeguro(historial);
	        } catch (Exception e) {
	            throw new IvroException("No fue posible guardar el historial de modalidad 40: " + e.getMessage());
	        }
	    }

	    @Override
	    public boolean actualizarHistorialUltimoSeguroModalidad40(String cveNss, String cveEntInegi,
	            String cveMunInegi) throws IvroException {
	        return seguroIvroServiceLocal.actualizarHistorialUltimoSeguroModalidad40(cveNss, cveEntInegi, cveMunInegi);
	    }

	    @Override
	    public UltimoTrabajoModalidad40DTO getUltimoTrabajoPorNss(String cveNss) throws IvroException {
	    	BdtutUltimoTrabajo respuesta = seguroIvroServiceLocal.getUltimoTrabajoPorNss(cveNss);
	    	UltimoTrabajoModalidad40DTO response = convertirDto(respuesta);
	    	
	    	return response;
	    }
	    
	    private UltimoTrabajoModalidad40DTO convertirDto(BdtutUltimoTrabajo entity) {
	    	if (entity == null) {
	    		return null;
	    	}
	    	UltimoTrabajoModalidad40DTO dto = new UltimoTrabajoModalidad40DTO();
	    	dto.setCveCurp(entity.getCveCurp());
	    	dto.setCveModalidad(entity.getCveModalidad());
	    	dto.setCveNss(entity.getCveNss());
	    	dto.setCveRfcAsegurado(entity.getCveRfcAsegurado());
	    	dto.setNomAsegurado(entity.getNomAsegurado());
	    	dto.setRefRegistroPatronal(entity.getRefRegistroPatronal());
	    	dto.setIndPension(entity.getIndPension());
	    	dto.setIndTrabajadorImss(entity.getIndTrabajadorImss());
	    	dto.setSemanasCotizadas(entity.getNumSemanasRoUlt5anios());
	    	dto.setCveMunicipioImss(entity.getCveIdMunicipioImss());
	    	if (entity.getNumSalarioUltimoTrabajo() != null) {
	    		dto.setSalarioUltimoTrabajo(
	    				entity.getNumSalarioUltimoTrabajo().floatValue()
	    				);
	    	}
	    	if (entity.getFecBajaUltimoTrabajo() != null) {
	    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    		dto.setFechaUltimoTrabajo(
	    				sdf.format(entity.getFecBajaUltimoTrabajo())
	    				);
	    	}
	    	// No existe equivalente en el entity
	    	dto.setTipoMovObligatorio(null);
	    	return dto;
	    }
}