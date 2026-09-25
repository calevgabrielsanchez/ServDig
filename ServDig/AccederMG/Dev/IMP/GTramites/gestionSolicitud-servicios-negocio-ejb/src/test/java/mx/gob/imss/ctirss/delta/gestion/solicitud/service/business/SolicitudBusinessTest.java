package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FolioCertificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ReporteRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudQueueProducerRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.GenerarFolioCertificacionException;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO Remember profiler aspect
public class SolicitudBusinessTest {

	private static final Logger LOG;
	private static final String SOLICITUD_ID_NOT_NULL_MSG = "El id de la solicitud creada/consultada no debe ser nulo!";
	private static final String SOLICITUD_ID_MSG = "La solicitud que se gener\u00F3 fue la ";
	private transient final SolicitudBusinessRemote solicitudBusiness = EjbLocator
			.getSolicitudBusiness();
	private transient final ServiceBusinessRemote serviceBusinessRemote = EjbLocator
			.getServiceBusiness();
	private transient Long solicitudId;
	
	
	private transient SolicitudQueueProducerRemote x =  EjbLocator.getSolicitudProducerService();
	
	static {
		LOG = LoggerFactory.getLogger(SolicitudBusinessTest.class);
	}

	// TEST FIXTURE:
	@Before
	public void setUp() {
		solicitudId = 1397L;
	}

	@Test
	public void crearSolicitudSujetoObligado()
			throws SolicitudNoValidaException {
		final Solicitud solicitudRespuesta = solicitudBusiness
				.crear(initSolicitudSujetoObligado());
		LOG.debug(SOLICITUD_ID_MSG + solicitudRespuesta.getSolicitudId());
		assertNotNull(SOLICITUD_ID_NOT_NULL_MSG,
				solicitudRespuesta.getSolicitudId());
	}

	private Solicitud initSolicitudSujetoObligado() {

		final TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(1);

		final TramiteSujetoObligado tramite = new TramiteSujetoObligado();
		final SujetoObligado sujetoObligado = new SujetoObligado();
		final Clasificacion clasificacion = new Clasificacion();
		clasificacion.setId(1L);
		sujetoObligado.setClasificacion(clasificacion);

		tramite.setSujetoObligado(sujetoObligado);
		tramite.setTipoTramite(tipoTramite);

		final Solicitud solicitud = new Solicitud();
		solicitud.setSolicitante(new Usuario());
		solicitud.getTramites().add(tramite);
		return solicitud;
	}

	@Test
	public void crearPersonaFisicaSolicitud() throws SolicitudNoValidaException {
		final Solicitud solicitudRespuesta = solicitudBusiness
				.crear(initPersonaFisicaSolicitud());
		LOG.debug(SOLICITUD_ID_MSG + solicitudRespuesta.getSolicitudId());
		assertNotNull(SOLICITUD_ID_NOT_NULL_MSG,
				solicitudRespuesta.getSolicitudId());
	}

	@Test
	public void crearSolicitudPersonaFisicaExistente()
			throws SolicitudNoValidaException {
		final Solicitud solicitud = initPersonaFisicaSolicitud();
		((TramiteFisica) solicitud.getTramites().get(0)).getFisica()
				.setIdPersona(1L);
		final Solicitud solicitudRespuesta = solicitudBusiness.crear(solicitud);
		LOG.debug(SOLICITUD_ID_MSG + solicitudRespuesta.getSolicitudId());
		assertNotNull(SOLICITUD_ID_NOT_NULL_MSG,
				solicitudRespuesta.getSolicitudId());
		final StringBuilder idTramitesStrB = new StringBuilder(
				"Los ids de tramite(s): {");
		for (Tramite tramite : solicitudRespuesta.getTramites()) {
			assertNotNull("El id del tramite no deben ser nulo!",
					tramite.getTramiteId());
			idTramitesStrB.append(tramite.getTramiteId());
			idTramitesStrB.append(",");
		}
		LOG.debug(idTramitesStrB.append("}").toString());
	}

	private Solicitud initPersonaFisicaSolicitud() {
		// INICIALIZA LA SOLICITUD

		final AsignacionNSS personaFisica = new AsignacionNSS();
		personaFisica.setCurp("HDJGD7ERE98DFJW");
		personaFisica.setRfc("HDFJ37878733");
		personaFisica.setNombre("CARLOS ALBERTO X");
		personaFisica.setPrimerApellido("GARCIA");
		personaFisica.setSegundoApellido("REYES");
		personaFisica.getLugarNacimiento().setClave("11");
		personaFisica.getLugarNacimiento().setNombre("PUEBLA");
		PersonaCalificacion personaCalificacion = new PersonaCalificacion();
		personaCalificacion.setCalificacion(new Calificacion());
		personaFisica.getPersonaCalificaciones().add(personaCalificacion);
		personaFisica.getPersonaCalificaciones().get(0).getCalificacion()
				.setIdCalificacion(1L);

		// PAIS
		Pais pais = new Pais();
		pais.setIdPais(1);
		personaFisica.setPais(pais);

		// SEXO
		personaFisica.getSexo().setIdSexo(1);

		// DOCUMENTO PROBATORIO - ACTA DE NACIMIENTO
		Nacimiento actaNacimiento = new Nacimiento();
		actaNacimiento.setAnio(1950);
		actaNacimiento.setCrip("4552");
		// XXX campo eliminado
		// actaNacimiento.setDigitalizacion("Digitalizacion");
		actaNacimiento.setFechaExpedicion(new Date());
		actaNacimiento.setFechaSuceso(new Date());
		actaNacimiento.setNoActa("11");
		actaNacimiento.setNoFoja("22");
		actaNacimiento.setNoJuzgado("33");
		actaNacimiento.setNoLibro("44");
		actaNacimiento.setTomo("55");
		Municipio municipioActa = new Municipio();
		municipioActa.setClave("1");
		EntidadFederativa entidadFederativaActa = new EntidadFederativa();
		entidadFederativaActa.setClave("1");
		municipioActa.setEntidadFederativa(entidadFederativaActa);
		actaNacimiento.setMunicipio(municipioActa);
		personaFisica.setActaNacimiento(actaNacimiento);

		// MEDIOS DE CONTACTO - CORREO ELECTRONICO
		TipoMedioContacto tipoCorreoElectronico = new TipoMedioContacto();
		tipoCorreoElectronico.setIdTipoMedioContacto(1L);
		CorreoElectronico correoElectronico = new CorreoElectronico();
		correoElectronico.setCorreo("usuario@usuario.com");
		correoElectronico.setTipoMedioContacto(tipoCorreoElectronico);
		personaFisica.setCorreoElectronico(correoElectronico);

		// MEDIOS DE CONTACTO - TELEFONO FIJO
		TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
		tipoTelefonoFijo.setIdTipoMedioContacto(2L);
		TelefonoFijo telefonoFijo = new TelefonoFijo("5533-3355", "90", "34552");
		telefonoFijo.setTipoMedioContacto(tipoTelefonoFijo);
		personaFisica.setTelefonoFijo(telefonoFijo);

		// MEDIOS DE CONTACTO - TELEFONO MOVIL
		TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
		tipoTelefonoMovil.setIdTipoMedioContacto(3L);
		TelefonoMovil telefonoMovil = new TelefonoMovil();
		telefonoMovil.setNumero("55-1433-5533");
		telefonoMovil.setTipoMedioContacto(tipoTelefonoMovil);
		personaFisica.setTelefonoMovil(telefonoMovil);

		// DOMICILIO
		Domicilio domicilio = new Domicilio();
		personaFisica.getDomicilios().add(domicilio);

		TipoDomicilio tipoDomicilio = new TipoDomicilio();
		tipoDomicilio.setClave(1);
		domicilio.setTipoDomicilio(tipoDomicilio);

		// VIALIDADES
		TipoVialidad tipoVialidad = new TipoVialidad();
		tipoVialidad.setClave(1);
		Vialidad vialidad = new Vialidad();
		vialidad.setClave(1);
		vialidad.setTipoVialidad(tipoVialidad);
		vialidad.setNombre("MiHouse");
		domicilio.setVialidadPrimaria(vialidad);
		domicilio.setVialidadReferenciaPosterior(vialidad);
		domicilio.setVialidadReferenciaPrimaria(vialidad);
		domicilio.setVialidadReferenciaSecundaria(vialidad);

		domicilio.setLongitud(BigDecimal.valueOf(2L));
		domicilio.setNumExterior1(101);
		domicilio.setNumExterior2(102);
		domicilio.setNumExteriorAlf("B");
		domicilio.setNumInterior(2);
		domicilio.setNumInteriorAlf("1");

		TipoAmbito ambito = new TipoAmbito();
		ambito.setClave(1L);
		domicilio.setAmbito(ambito);

		// CODIGO POSTAL
		CodigoPostal codigoPostal = new CodigoPostal();
		// codigoPostal.setCodigoPostal(6500);
		domicilio.setCodigoPostal(codigoPostal);

		// CONFIGURACION DE ASENTAMIENTO
		EntidadFederativa entidadFederativa = new EntidadFederativa();
		entidadFederativa.setClave("9");
		entidadFederativa.setNombre("DISTRITO FEDERAL");

		Municipio municipio = new Municipio();
		municipio.setClave("15");
		municipio.setEntidadFederativa(entidadFederativa);
		municipio.setNombre("CUAUHTÉMOC");

		Localidad localidad = new Localidad();
		localidad.setClave("1");
		localidad.setMunicipio(municipio);
		localidad.setNombre("CUAUHTÉMOC");

		Asentamiento asentamiento = new Asentamiento();
		asentamiento.setClave("10");
		asentamiento.setNombre("CUAUHTÉMOC");
		asentamiento.setLocalidad(localidad);
		asentamiento.setCodigoPostal(codigoPostal);
		domicilio.setAsentamiento(asentamiento);

		personaFisica.getDomicilios().add(domicilio);

		// PERSONA VALIDADA POR IMSS
		personaFisica.getPersonaCalificaciones().get(0).getCalificacion()
				.setIdCalificacion(1L); // TODO
										// cambiarlo
										// por
		personaFisica.setNssStr("This is my nss value");
		// EstadoPersonaEnum.EDO_NEW.getCodigo()

		final TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.REGISTRO_DE_PERSONA
				.getCodigo());
		tipoTramite.setDescripcion(TipoTramiteEnum.REGISTRO_DE_PERSONA
				.toString()); // TODO
								// To
								// change
								// enumeration
								// to
								// support
								// description...

		final TramiteFisica tramite1 = new TramiteFisica();
		// tramite1.setTramiteId(1L);
		tramite1.setTipoTramite(tipoTramite);
		tramite1.setFisica(personaFisica);
		tramite1.getEstadoTramite().setIdEstadoTramitePersona(1);

		// final Fisica persona2 = new Fisica();
		// persona2.setCurp("HDJGD7ERE98DFJW");
		// persona2.setRfc("HDFJ37878733");
		// persona2.setNombre("JUAN PABLO X");
		// persona2.setPrimerApellido("NIEVES");
		// persona2.setSegundoApellido("TERRAN");
		// persona2.getSexo().setIdSexo(1);
		// persona2.getLugarNacimiento().setClave("21");
		// persona2.getLugarNacimiento().setNombre("QUERETARO");
		// PersonaCalificacion personaCalificacion2 = new PersonaCalificacion();
		// personaCalificacion2.setCalificacion(new Calificacion());
		// persona2.getPersonaCalificaciones().add(personaCalificacion2);
		// persona2.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(2L);

		// final Tramite tramite2 = new Tramite();
		// tramite2.setTramiteId(2L);
		// tramite2.setTipoTramite(tipoTramite);
		// tramite2.setFisica(persona2);
		// tramite2.getEstadoTramite().setIdEstadoTramitePersona(1);

		// AGREGA EL TRAMITE A LA SOLICITUD
		final Solicitud solicitud = new Solicitud();
		final TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(1L);
		solicitud.setTipoSolicitud(tipoSolicitud);
		solicitud.setSolicitante(new Usuario());
		solicitud.getTramites().add(tramite1);
		// solicitud.getTramite().add(tramite2);

		LOG.debug("solicitud: " + solicitud);
		return solicitud;
	}

	@Test
	public void crearSolicitudMoralTest() throws SolicitudNoValidaException {
		final Solicitud solicitud = initSolicitudMoral();
		((TramiteMoral) solicitud.getTramites().get(0)).getMoral()
				.setIdPersona(1L);
		final Solicitud solicitudRespuesta = solicitudBusiness.crear(solicitud);
		LOG.debug(SOLICITUD_ID_MSG + solicitudRespuesta.getSolicitudId());
		assertNotNull(SOLICITUD_ID_NOT_NULL_MSG,
				solicitudRespuesta.getSolicitudId());
		final StringBuilder idTramitesStrB = new StringBuilder(
				"Los ids de tramite(s): {");
		for (Tramite tramite : solicitudRespuesta.getTramites()) {
			assertNotNull("El id del tramite no deben ser nulo!",
					tramite.getTramiteId());
			idTramitesStrB.append(tramite.getTramiteId());
			idTramitesStrB.append(",");
		}
		LOG.debug(idTramitesStrB.append("}").toString());
	}

	private Solicitud initSolicitudMoral() {
		final Nacimiento actaNacimiento = new Nacimiento();
		actaNacimiento.setAnio(2000);
		actaNacimiento.setNoLibro("noLibro");

		final Moral moral = new Moral();
		moral.setActaNacimiento(actaNacimiento);

		final TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(2);

		final TramiteMoral tramiteMoral = new TramiteMoral();
		tramiteMoral.setMoral(moral);
		tramiteMoral.setTipoTramite(tipoTramite);

		final Solicitud solicitud = new Solicitud();
		solicitud.getTramites().add(tramiteMoral);
		return solicitud;
	}

	@Test
	public void actualizarEstadosSolicitud()
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

		final EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(1);

		final Tramite tramite = new Tramite();
		tramite.setTramiteId(211L);
		tramite.setEstadoTramite(estadoTramite);

		final Solicitud solicitud = new Solicitud(676L);
		solicitud.setEstadoSolicitud(new EstadoSolicitud(
				EstadoSolicitudEnum.REGISTRADA.getCodigo()));
		solicitud.getTramites().add(tramite);

		solicitudBusiness.actualizarEstados(solicitud);
		// TODO assert de que todo lo demas esta igual y solo el estado ha
		// cambiado...

	}

	@Test
	public void consultarSolicitud() throws SolicitudNoEncontradaException {
		final Solicitud solicitudRespuesta = solicitudBusiness
				.consultar(new Solicitud(solicitudId));
		LOG.debug("solicitudRespuesta: " + solicitudRespuesta);
		LOG.debug("num tramites: " + solicitudRespuesta.getTramites().size());
		assertNotNull(SOLICITUD_ID_NOT_NULL_MSG,
				solicitudRespuesta.getSolicitudId());
		for (Tramite tramite : solicitudRespuesta.getTramites()) {
			if (tramite != null) {
				LOG.trace("Tramite id = {}", tramite.getTramiteId());
				if (tramite.getEstadoTramite() != null) {
					LOG.trace("Estado id: {} descr: {}", tramite
							.getEstadoTramite().getIdEstadoTramitePersona(),
							tramite.getEstadoTramite().getDescripcion());
				}
			}
		}
	}

	@Test
	public void concluirTest() throws SolicitudNoValidaException {
		// serviceBusinessRemote.concluir(solicitudBusiness.crear(initSolicitudAsegurado()));
	}

	private Solicitud initSolicitudAsegurado() {

		final TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(1);

		final TramiteAsegurado tramite = new TramiteAsegurado();

		AsignacionNSS fisica = new AsignacionNSS();
		fisica.setNssStr("nss value");
		fisica.setNombre("nombre");
		final PersonaCalificacion pCalif = new PersonaCalificacion();
		final Calificacion calificacion = new Calificacion();
		calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_IMSS
				.getCodigo().longValue());
		pCalif.setCalificacion(calificacion);
		fisica.getPersonaCalificaciones().add(pCalif);
		tramite.setFisica(fisica);
		tramite.setTipoTramite(tipoTramite);

		final Solicitud solicitud = new Solicitud();
		solicitud.setSolicitante(new Usuario());
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(1L);
		solicitud.setTipoSolicitud(tipoSolicitud);
		solicitud.getTramites().add(tramite);
		return solicitud;
	}

	@Test
	public void notificarError() {
		solicitudBusiness.reportarErrorProcesamiento(0L, "1379442947757621",
				"Error comment");
	}

	@Test
	public void actualizaMensajeNotificacion() {
		solicitudBusiness.actualizarMensajeNotificacion("13776426367696661",
				"Error");
	}

	@Test
	public void testObtenerCifrasSolicitudProcesoOk() {
		solicitudBusiness.obtenerCifrasSolicitudProceso();
//		if (listDitSolicitudes!=null && !listDitSolicitudes.isEmpty()) {
//			System.err.println("total solicitudes :"
//					+ listDitSolicitudes.size());
//		for(int x=0;x<listDitSolicitudes.size();x++){
//			ObtCifrasSolicitudProceso descripcion=listDitSolicitudes.get(x);
//			System.out.println( descripcion.getNoSolicitudes()+ "" +descripcion.getTipoSolicitud());
//			
//		}
//		
//		}
		
	}
	
//	public String armarCorreo(){
//		
//		
//		
//	}

	
	
	

    @Test
    public void generarReporteRiss() {
    
    	ReporteRissServiceBusinessRemote ejb = EjbLocator.getReporteRissService();
    	
//    	ejb.obtenerTxtMovimientosRiss();
    	
    	ejb.generarExcelMovimientosRiss(true, false, false);
    	
    }
    
    @Test
    public void generarFolioCertificacion() {
    	
    	FolioCertificacionServiceBusinessRemote ejb = EjbLocator.getFolioCertificacionServiceBusiness();
    	
    	try {
			String folio = ejb.obtenerFolio();
			LOG.debug(folio);
		} catch (GenerarFolioCertificacionException e) {
			LOG.error(e.getMessage());
		}
    }
    
    @Test
    public void guardarSolicitudNumeroResolucion() {
    	
    	FolioCertificacionServiceBusinessRemote ejb = EjbLocator.getFolioCertificacionServiceBusiness();
    	
    	ejb.guardarRelacionSolicitudFolioCertificacion(728995L, "90000001");
    }
    
    @Test
    public void encolarSolicitudes(){
    	List<String> folios = new ArrayList<String>();

    	folios.add("14356788123533569750");
    	folios.add("14356788218873569751");
    	folios.add("14356788255133569752");
    	folios.add("14356788301983569753");
    	folios.add("14356788388783569754");
    	folios.add("14356788453263569755");
    	folios.add("14356788491993569756");
    	folios.add("14356788515273569757");
    	folios.add("14356788540043569758");
    	folios.add("14356788563983569759");
    	folios.add("14356788587253569760");
    	folios.add("14356788612943569761");
    	folios.add("14356788642503569762");
    	folios.add("14356788667413569763");
    	folios.add("14356788696123569764");
    	folios.add("14356788718613569765");
    	folios.add("14356788745583569766");
    	folios.add("14356788773523569767");
    	folios.add("14356788796213569768");
    	folios.add("14356788819333569769");
    	folios.add("14356788844303569770");
    	folios.add("14356788867323569771");
    	folios.add("14356788887613569772");
    	folios.add("14356788915613569773");
    	folios.add("14356788939483569774");
    	folios.add("14356788961533569775");
    	folios.add("14356788986753569776");
    	folios.add("14356789009193569777");
    	folios.add("14356789033523569778");
    	folios.add("14356789084063569779");
    	folios.add("14356789105393569780");
    	folios.add("14356789125393569781");
    	folios.add("14356789149783569782");
    	folios.add("14356789173263569783");
    	folios.add("14356789194943569784");
    	folios.add("14356789219743569785");
    	folios.add("14356789244993569786");
    	folios.add("14356789267063569787");
    	folios.add("14356789290763569788");
    	folios.add("14356789312373569789");
    	folios.add("14356789332783569790");
    	folios.add("14356789359783569791");
    	folios.add("14356789382283569792");
    	folios.add("14356789403793569793");
    	folios.add("14356789428173569794");
    	folios.add("14356789452543569795");
    	folios.add("14356789473723569796");
    	folios.add("14356789499683569797");
    	folios.add("14356789522403569798");
    	folios.add("14356789542923569799");
    	folios.add("14356789568103569800");
    	folios.add("14356789589563569801");
    	folios.add("14356789611173569802");
    	folios.add("14356789655623569803");
    	folios.add("14356789677333569804");
    	folios.add("14356789698993569805");
    	folios.add("14356789721543569806");
    	folios.add("14356789741263569807");
    	folios.add("14356789762303569808");
    	folios.add("14356789782273569809");
    	folios.add("14356789801923569810");
    	folios.add("14356789820913569811");
    	folios.add("14356789840643569812");
    	folios.add("14356789860203569813");
    	folios.add("14356789881213569814");
    	folios.add("14356789902243569815");
    	folios.add("14356789920683569816");
    	folios.add("14356789940783569817");
    	folios.add("14356789962033569818");
    	folios.add("14356789981213569819");
    	folios.add("14356790000993569820");
    	folios.add("14356790021753569821");
    	folios.add("14356790039673569822");
    	folios.add("14356790058263569823");
    	folios.add("14356790078423569824");
    	folios.add("14356790100013569825");
    	folios.add("14356790122983569826");
    	folios.add("14356790142693569827");
    	folios.add("14356790161023569828");
    	folios.add("14356790179583569829");
    	folios.add("14356790211483569830");
    	folios.add("14356790230843569831");
    	folios.add("14356790250023569832");
    	folios.add("14356790268933569833");
    	folios.add("14356790286073569834");
    	folios.add("14356790306123569835");
    	folios.add("14356790325993569836");
    	folios.add("14356790348783569837");
    	folios.add("14356790369353569838");
    	folios.add("14356790390213569839");
    	folios.add("14356790408683569840");
    	folios.add("14356790426723569841");
    	folios.add("14356790448593569842");
    	folios.add("14356790470953569843");
    	folios.add("14356790492033569844");
    	folios.add("14356790512783569845");
    	folios.add("14356790535753569846");
    	folios.add("14356790564753569847");
    	folios.add("14356790590443569848");
    	folios.add("14356790611473569849");
    	folios.add("14356790630183569850");
    	folios.add("14356790653543569851");
    	folios.add("14356790673683569852");
    	folios.add("14356790698233569853");
    	folios.add("14356790722743569854");
    	folios.add("14356790747793569855");
    	folios.add("14356790766203569856");
    	folios.add("14356790817473569857");
    	folios.add("14356790839773569858");
    	folios.add("14356790862253569859");
    	folios.add("14356790890013569860");
    	folios.add("14356790909853569861");
    	folios.add("14356790928553569862");
    	folios.add("14356790951403569863");
    	folios.add("14356790973793569864");
    	folios.add("14356790993493569865");
    	folios.add("14356791015943569866");
    	folios.add("14356791037013569867");
    	folios.add("14356791055543569868");
    	folios.add("14356791074313569869");
    	folios.add("14356791093633569870");
    	folios.add("14356791113153569871");
    	folios.add("14356791138693569872");
    	folios.add("14356791158133569873");
    	folios.add("14356791180363569874");
    	folios.add("14356791210273569875");
    	folios.add("14356791230123569876");
    	folios.add("14356791249603569877");
    	folios.add("14356791274843569878");
    	folios.add("14356791301633569879");
    	folios.add("14356791322443569880");
    	folios.add("14356791369933569881");
    	folios.add("14356791390503569882");
    	folios.add("14356791413053569883");
    	folios.add("14356791435103569884");
    	folios.add("14356791458253569885");
    	folios.add("14356791478223569886");
    	folios.add("14356791500813569887");
    	folios.add("14356791525463569888");
    	folios.add("14356791544193569889");
    	folios.add("14356791568933569890");
    	folios.add("14356791592833569891");
    	folios.add("14356791613503569892");
    	folios.add("14356791640393569893");
    	folios.add("14356791664063569894");
    	folios.add("14356791685453569895");
    	folios.add("14356791711473569896");
    	folios.add("14356791736793569897");
    	folios.add("14356791755493569898");
    	folios.add("14356791777973569899");
    	folios.add("14356791800123569900");
    	folios.add("14356791821973569901");
    	folios.add("14356791843753569902");
    	folios.add("14356791866313569903");
    	folios.add("14356791889923569904");
    	folios.add("14356791914023569905");
    	folios.add("14356791937183569906");
    	folios.add("14356791959233569907");
    	folios.add("14356791982863569908");
    	folios.add("14356792007763569909");
    	folios.add("14356792027143569910");
    	folios.add("14356792055423569911");
    	folios.add("14356792081793569912");
    	folios.add("14356792101933569913");
    	folios.add("14356792144403569914");
    	folios.add("14356792168363569915");
    	folios.add("14356792187513569916");
    	folios.add("14356792210923569917");
    	folios.add("14356792236493569918");
    	folios.add("14356792258053569919");
    	folios.add("14356792280453569920");
    	folios.add("14356792303303569921");
    	folios.add("14356792321493569922");
    	folios.add("14356792343083569923");
    	folios.add("14356792365633569924");
    	folios.add("14356792386483569925");
    	folios.add("14356792408683569926");
    	folios.add("14356792433283569927");
    	folios.add("14356792455383569928");
    	folios.add("14356792484283569929");
    	folios.add("14356792509323569930");
    	folios.add("14356792532213569931");
    	folios.add("14356792554653569932");
    	folios.add("14356792578773569933");
    	folios.add("14356792601393569934");
    	folios.add("14356792623633569935");
    	folios.add("14356792649483569936");
    	folios.add("14356792671133569937");
    	folios.add("14356792703423569938");
    	folios.add("14356792725883569939");
    	folios.add("14356792749563569940");
    	folios.add("14356792802023569941");
    	folios.add("14356792828163569942");
    	folios.add("14356792850743569943");
    	folios.add("14356792874833569944");
    	folios.add("14356792899443569945");
    	folios.add("14356792929393569946");
    	folios.add("14356792956453569947");
    	folios.add("14356792982533569948");
    	folios.add("14356793008603569949");
    	folios.add("14356793034603569950");
    	folios.add("14356793062783569951");
    	folios.add("14356793084513569952");
    	folios.add("14356793109533569953");
    	folios.add("14356793133413569954");
    	folios.add("14356793154173569955");
    	folios.add("14356793178223569956");
    	folios.add("14356793204613569957");
    	folios.add("14356793229443569958");
    	folios.add("14356793253353569959");
    	folios.add("14356793281393569960");
    	folios.add("14356793305543569961");
    	folios.add("14356793340393569962");
    	folios.add("14356793365603569963");
    	folios.add("14356793388533569964");
    	folios.add("14356793414383569965");
    	folios.add("14356793453253569966");
    	folios.add("14356793477733569967");
    	folios.add("14356793508523569968");
    	folios.add("14356793560863569969");
    	folios.add("14356793585273569970");
    	folios.add("14356793614143569971");
    	folios.add("14356793642753569972");
    	folios.add("14356793663473569973");
    	folios.add("14356793687643569974");
    	folios.add("14356793716653569975");
    	folios.add("14356793739773569976");
    	folios.add("14356793761993569977");
    	folios.add("14356793789283569978");
    	folios.add("14356793809043569979");
    	folios.add("14356793835643569980");
    	folios.add("14356793865633569981");
    	folios.add("14356793890023569982");
    	folios.add("14356793915313569983");
    	folios.add("14356793943433569984");
    	folios.add("14356793962123569985");
    	folios.add("14356793988713569986");
    	folios.add("14356794014373569987");
    	folios.add("14356794035193569988");
    	folios.add("14356794061713569989");
    	folios.add("14356794087433569990");
    	folios.add("14356794107203569991");
    	folios.add("14356794130743569992");
    	folios.add("14356794155823569993");
    	folios.add("14356794176433569994");
    	folios.add("14356794204293569995");
    	folios.add("14356794233133569996");
    	folios.add("14356794254893569997");
    	folios.add("14356794280133569998");
    	folios.add("14356794308133569999");
    	folios.add("14356794334453570000");
    	folios.add("14356794356033570001");
    	folios.add("14356794382273570002");
    	folios.add("14356794405653570003");
    	folios.add("14356794428013570004");
    	folios.add("14356794455313570005");
    	folios.add("14356794475613570006");
    	folios.add("14356794497883570007");
    	folios.add("14356794530513570008");
    	folios.add("14356794549093570009");
    	folios.add("14356794574613570010");
    	folios.add("14356794601443570011");
    	folios.add("14356794622213570012");
    	folios.add("14356794645643570013");
    	folios.add("14356794672623570014");
    	folios.add("14356794691653570015");
    	folios.add("14356794714273570016");
    	folios.add("14356794741933570017");
    	folios.add("14356794760943570018");
    	folios.add("14356794783283570019");
    	folios.add("14356794810773570020");
    	folios.add("14356794831673570021");
    	folios.add("14356794854533570022");
    	folios.add("14356794881383570023");
    	folios.add("14356794903863570024");
    	folios.add("14356794927453570025");
    	folios.add("14356794959563570026");
    	folios.add("14356794980203570027");
    	folios.add("14356795003563570028");
    	folios.add("14356795029213570029");
    	folios.add("14356795053103570030");
    	folios.add("14356795077203570031");
    	folios.add("14356795104043570032");
    	folios.add("14356795126283570033");
    	folios.add("14356795151553570034");
    	folios.add("14356795180773570035");
    	folios.add("14356795202793570036");
    	folios.add("14356795226623570037");
    	folios.add("14356795255093570038");
    	folios.add("14356795274923570039");
    	folios.add("14356795356383570040");
    	folios.add("14356795385873570041");
    	folios.add("14356795406993570042");
    	folios.add("14356795432063570043");
    	folios.add("14356795458263570044");
    	folios.add("14356795477113570045");
    	folios.add("14356795497833570046");
    	folios.add("14356795525223570047");
    	folios.add("14356795544393570048");
    	folios.add("14356795566283570049");
    	folios.add("14356795595003570050");
    	folios.add("14356795616103570051");
    	folios.add("14356795637853570052");
    	folios.add("14356795664593570053");
    	folios.add("14356795686833570054");
    	folios.add("14356795718533570055");
    	folios.add("14356795748683570056");
    	folios.add("14356795769713570057");
    	folios.add("14356795793413570058");
    	folios.add("14356795820613570059");
    	folios.add("14356795839503570060");
    	folios.add("14356795861593570061");
    	folios.add("14356795888923570062");
    	folios.add("14356795907523570063");
    	folios.add("14356795932553570064");
    	folios.add("14356795959493570065");
    	folios.add("14356795978373570066");
    	folios.add("14356796000303570067");
    	folios.add("14356796027403570068");
    	folios.add("14356796049093570069");
    	folios.add("14356796073743570070");
    	folios.add("14356796102613570071");
    	folios.add("14356796124823570072");
    	folios.add("14356796152863570073");
    	folios.add("14356796181613570074");
    	folios.add("14356796203253570075");
    	folios.add("14356796226453570076");
    	folios.add("14356796256603570077");
    	folios.add("14356796276513570078");
    	folios.add("14356796300723570079");
    	folios.add("14356796329703570080");
    	folios.add("14356796356563570081");
    	folios.add("14356796381343570082");
    	folios.add("14356796410493570083");
    	folios.add("14356796430533570084");
    	folios.add("14356796459373570085");
    	folios.add("14356796499443570086");
    	folios.add("14356796522093570087");
    	folios.add("14356796547163570088");
    	folios.add("14356796578593570089");
    	folios.add("14356796605303570090");
    	folios.add("14356796638653570091");
    	folios.add("14356796665273570092");
    	folios.add("14356796684003570093");
    	folios.add("14356796706413570094");
    	folios.add("14356796731983570095");
    	folios.add("14356796813083570096");
    	folios.add("14356796861273570097");
    	folios.add("14356796885603570098");
    	folios.add("14356796906753570099");
    	folios.add("14356796931583570100");
    	folios.add("14356796958353570101");
    	folios.add("14356796978213570102");
    	folios.add("14356797023003570103");
    	folios.add("14356797050893570104");
    	folios.add("14356797073773570105");
    	folios.add("14356797101733570106");
    	folios.add("14356797126463570107");
    	folios.add("14356797145023570108");
    	folios.add("14356797184733570109");
    	folios.add("14356797210833570110");
    	folios.add("14356797232433570111");
    	folios.add("14356797259643570112");
    	folios.add("14356797288533570113");
    	folios.add("14356797308153570114");
    	folios.add("14356797330403570115");
    	folios.add("14356797361243570116");
    	folios.add("14356797379923570117");
    	folios.add("14356797418223570118");
    	folios.add("14356797450173570119");
    	folios.add("14356797469283570120");
    	folios.add("14356797493623570121");
    	folios.add("14356797523083570122");
    	folios.add("14356797542813570123");
    	folios.add("14356797566393570124");
    	folios.add("14356797593523570125");
    	folios.add("14356797612973570126");
    	folios.add("14356797638903570127");
    	folios.add("14356797670563570128");
    	folios.add("14356797693583570129");
    	folios.add("14356797715083570130");
    	folios.add("14356797745073570131");
    	folios.add("14356797764743570132");
    	folios.add("14356797789353570133");
    	folios.add("14356797817503570134");
    	folios.add("14356797839943570135");
    	folios.add("14356797879633570136");
    	folios.add("14356797917043570137");
    	folios.add("14356797935513570138");
    	folios.add("14356797958363570139");
    	folios.add("14356797982983570140");
    	folios.add("14356798003743570141");
    	folios.add("14356798035393570142");
    	folios.add("14356798060933570143");
    	folios.add("14356798080643570144");
    	folios.add("14356798129143570145");
    	folios.add("14356798155123570146");
    	folios.add("14356798174433570147");
    	folios.add("14356798196233570148");
    	folios.add("14356798224373570149");
    	folios.add("14356798244883570150");
    	folios.add("14356798271433570151");
    	folios.add("14356798305543570152");
    	folios.add("14356798328353570153");
    	folios.add("14356798349603570154");
    	folios.add("14356798376683570155");
    	folios.add("14356798396083570156");
    	folios.add("14356798418063570157");
    	folios.add("14356798452523570158");
    	folios.add("14356798473883570159");
    	folios.add("14356798498523570160");
    	folios.add("14356798527333570161");
    	folios.add("14356798552303570162");
    	folios.add("14356798619723570163");
    	folios.add("14356798647693570164");
    	folios.add("14356798666743570165");
    	folios.add("14356798688773570166");
    	folios.add("14356798716103570167");
    	folios.add("14356798735933570168");
    	folios.add("14356798757483570169");
    	folios.add("14356798783533570170");
    	folios.add("14356798803123570171");
    	folios.add("14356798827283570172");
    	folios.add("14356798852403570173");
    	folios.add("14356798872613570174");
    	folios.add("14356798896243570175");
    	folios.add("14356798923803570177");
    	folios.add("14356798942123570178");
    	folios.add("14356798970223570179");
    	folios.add("14356798999253570180");
    	folios.add("14356799018703570181");
    	folios.add("14356799043373570182");
    	folios.add("14356799071793570183");
    	folios.add("14356799094343570184");
    	folios.add("14356799118263570185");
    	folios.add("14356799146533570186");
    	folios.add("14356799168093570187");
    	folios.add("14356799191403570188");
    	folios.add("14356799223503570189");
    	folios.add("14356799242943570190");
    	folios.add("14356799265093570191");
    	folios.add("14356799292563570192");
    	folios.add("14356799314953570193");
    	folios.add("14356799342723570194");
    	folios.add("14356799368993570195");
    	folios.add("14356799388363570196");
    	folios.add("14356799409433570197");
    	folios.add("14356799467423570198");
    	folios.add("14356799488513570199");
    	folios.add("14356799515813570200");
    	folios.add("14356799539943570201");
    	folios.add("14356799560153570202");
    	folios.add("14356799589383570203");
    	folios.add("14356799614093570204");
    	folios.add("14356799634553570205");
    	folios.add("14356799655553570206");
    	folios.add("14356799678013570207");
    	folios.add("14356799698453570208");
    	folios.add("14356799725123570209");
    	folios.add("14356799746523570210");
    	folios.add("14356799765963570211");
    	folios.add("14356799813453570212");
    	folios.add("14356799836003570213");
    	folios.add("14356799856143570214");
    	folios.add("14356799885863570215");
    	folios.add("14356799910373570216");
    	folios.add("14356799928583570217");
    	folios.add("14356799950423570218");
    	folios.add("14356799971213570219");
    	folios.add("14356799989443570220");
    	folios.add("14356800013883570221");
    	folios.add("14356800037103570222");
    	folios.add("14356800056283570223");
    	folios.add("14356800078383570224");
    	folios.add("14356800109183570225");
    	folios.add("14356800130583570226");
    	folios.add("14356800158633570227");
    	folios.add("14356800184773570228");
    	folios.add("14356800216243570229");
    	folios.add("14356800243543570230");
    	folios.add("14356800279103570231");
    	folios.add("14356800310163570232");
    	folios.add("14356800335753570233");
    	folios.add("14356800361473570234");
    	folios.add("14356800380523570235");
    	folios.add("14356800404433570236");
    	folios.add("14356800430273570237");
    	folios.add("14356800451143570238");
    	folios.add("14356800471913570239");
    	folios.add("14356800502083570240");
    	folios.add("14356800521013570241");
    	folios.add("14356800575643570242");
    	folios.add("14356800610443570243");
    	folios.add("14356800633643570244");
    	folios.add("14356800655803570245");
    	folios.add("14356800677553570246");
    	folios.add("14356800700973570247");
    	folios.add("14356800724703570248");
    	folios.add("14356800744113570249");
    	folios.add("14356800764683570250");


    	
    	for(String folio :folios){
    		
    		x.encolarSolicitudAConcluir(folio);
    		
    	}
    	
    }
    
}
