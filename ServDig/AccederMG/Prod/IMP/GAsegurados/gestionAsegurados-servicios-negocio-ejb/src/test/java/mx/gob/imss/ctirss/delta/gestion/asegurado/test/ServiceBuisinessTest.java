package mx.gob.imss.ctirss.delta.gestion.asegurado.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAsignarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorCrearFoliosDeSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorGuardarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NumeroDeSeriePorAnioRegistroExisteException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AltaDatosAsignacionNSSType;
import mx.gob.imss.ctirss.delta.model.asegurado.Empleado;
import mx.gob.imss.ctirss.delta.model.asegurado.EmpleadoEmpresa;
import mx.gob.imss.ctirss.delta.model.enums.TipoSerieEnum;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.AseguradoWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.TipoSerie;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceBuisinessTest {

	private static final Logger log = LoggerFactory
			.getLogger(ServiceBuisinessTest.class);
	private ServiceBusinessRemote serviceBusiness;
	private SerieServiceBusinessRemote serieServiceBusiness;
	private SolicitudBusinessRemote solicitudBusiness;
	private PersonaBusinessRemote personaBusiness;
	
	@Before
	public void before() throws NamingException {
		serviceBusiness = EJBLocator.getServiceBusiness();
		serieServiceBusiness = EJBLocator.getSerieServiceBusiness();
		solicitudBusiness = EJBLocator.getSolicitudBusiness();
		personaBusiness = EJBLocator.getPersonaBusiness();
	}

	@Test
	public void generarNssEstudiante() {
		log.debug("Inicia la prueba");

		String nombreUsuario = "SAEM860110HDFNSR01";
		AseguradoWrapper wrapper = null;
		AltaDatosAsignacionNSSType estudiante = null;
		List<AseguradoWrapper> resultado = new ArrayList<AseguradoWrapper>();
		
		InputStream stream = null;

		try {
			stream = new FileInputStream("/home/marco/Documentos/Delta/SIME-SIE/EDUCACION A DISTANCIA_SUR_5.xml");

			JAXBContext jaxbContext = JAXBContext
					.newInstance(EmpleadoEmpresa.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			EmpleadoEmpresa empleadoEmpresa = (EmpleadoEmpresa) jaxbUnmarshaller
					.unmarshal(stream);

			for (Empleado empleado : empleadoEmpresa.getEmpleado()) {
				estudiante = new AltaDatosAsignacionNSSType();
				try {
					BeanUtils.copyProperties(estudiante, empleado);
					estudiante.setSexo(Integer.parseInt(empleado.getSexo()));
					estudiante.setLugarNacimiento(Integer.parseInt(empleado.getLugarNacimiento()));
					wrapper = serviceBusiness.generarNSSEstudiante(estudiante,
							nombreUsuario);
					resultado.add(wrapper);
				} catch (IllegalAccessException e) {
					log.error(e.getMessage());
				} catch (InvocationTargetException e) {
					log.error(e.getMessage());
				} catch (NivelDeAsignacionSerieIndefinidoException e) {
					log.error(e.getMessage());
				} catch (SeriesNoLocalizadasException e) {
					log.error(e.getMessage());
				} catch (ErrorAlActivarSerieException e) {
					log.error(e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (JAXBException e) {
			log.error(e.getMessage());
		}

		for(AseguradoWrapper aux : resultado) {
			log.debug(aux.toString());
		}
		
		log.debug("Finaliza la prueba");
	}

	@Test
	public void testCrearSerie() {

		Long numSerie = 76L;

		AsignacionSerieNSS asignacion = new AsignacionSerieNSS();

		TipoSerie tipoSerie = new TipoSerie();
		tipoSerie.setIdTipoSerie(TipoSerieEnum.HOMONIMIA.getClave());

		Serie serie = null;
		for (int i = 16; i <= 99; i++) {
			serie = new Serie();
			serie.setTipoSerie(tipoSerie);
			serie.setAnioRegistro(i);
			serie.setNumSerie(numSerie);

			asignacion.setSerie(serie);

			// log.debug(serie.toString());

			try {
				serieServiceBusiness.crearSerie(asignacion);
			} catch (NumeroDeSeriePorAnioRegistroExisteException e) {
				log.error(e.getMessage());
			} catch (ErrorGuardarSerieException e) {
				log.error(e.getMessage());
			} catch (ErrorCrearFoliosDeSerieException e) {
				log.error(e.getMessage());
			} catch (ErrorAsignarSerieException e) {
				log.error(e.getSituacion());
				log.error(e.getMessage());
			} catch (EJBException e) {
				log.error(e.getCause().getMessage());
			}
		}
	}

	@Test
	public void generarTramaAsegurado() {

		String[] folios = { "13915319509407777" };
		
		Solicitud solicitud = null;
		TramiteAsegurado tramiteAsegurado = null;
		String xml = null;
		
		for (int i = 0; i < folios.length; i++) {
			solicitud = new Solicitud();
			solicitud.setNoFolioSolicitud(folios[i]);
			try {
				solicitud = this.solicitudBusiness.consultarFolio(solicitud);

				for (Tramite tramite : solicitud.getTramites()) {
					if (tramite instanceof TramiteAsegurado) {
						tramiteAsegurado = (TramiteAsegurado) tramite;
						log.debug("Se encontro tramiteAsegurado con id -> "
								+ tramiteAsegurado.getTramiteId());
					}
				}

				xml = this.serieServiceBusiness.generarXmlMovAsignacionNSS(tramiteAsegurado, true);
				
				log.debug("=============Movimiento=============");
				log.debug(xml);

			} catch (SolicitudNoEncontradaException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	@Test
	public void enviarCorreoPendientes() {
		
		String curpsSinCorreo[] = {"SAEM860110HDFNSR01"}; 
		Fisica fisica = null;
		Fisica fisicaTramite = null;
		List<Fisica> personasEncontradas = null;
		List<Solicitud> solicitudes = null;
		String nss = null;
		List<String> curps = Arrays.asList(curpsSinCorreo);
		boolean correoEnviado = false;
		
		
		for (String curp : curps) {
			correoEnviado = false;
			
			personasEncontradas = this.personaBusiness
					.buscarPersonaFisicaPorCurpEnImss(curp);
			
			if (personasEncontradas != null) { 
				if (personasEncontradas.size() == 1) {
					fisica = personasEncontradas.get(0);
					
					try {
						nss = this.personaBusiness.obtenerNssPersona(fisica.getIdPersona());
						
						fisica.setNss(nss);
						
						solicitudes = this.solicitudBusiness
								.obtenerSolicitudPorPersonaFisica(fisica.getIdPersona());

						if (solicitudes != null && !solicitudes.isEmpty()) {
							for (Solicitud solicitud : solicitudes) {
								if (solicitud.getTipoSolicitud() != null
										&& solicitud.getTipoSolicitud().getIdTipoSolicitud().intValue() == TipoSolicitudEnum.ASIGNACION_NSS.getValor().intValue()
										&& solicitud.getOrigenSolicitud().getIdTipoSolicitud().equals(OrigenSolicitudEnum.INTERNET.getId())) {
									for (Tramite tramite : solicitud.getTramites()) {
										Date fechaTramite = DateUtils.truncate(tramite.getFechaTramite(), Calendar.DATE);
										Date fechaFalla = null;;
										
										try {
											fechaFalla = new SimpleDateFormat("dd/MM/yyyy").parse("21/01/2014");
										} catch (ParseException e) {
										}
										
										if (tramite instanceof TramiteAsegurado 
												&& tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.LOCALIZACION_NSS.getCodigo())
												&& fechaTramite == fechaFalla) {
											TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
											
											fisicaTramite = tramiteAsegurado.getFisica();
											
											if (fisicaTramite.getCorreoElectronico() != null
													&& StringUtils.isNotBlank(fisicaTramite.getCorreoElectronico().getCorreo())) {
//												this.serviceBusiness.enviarCorreoNSS(
//														fisicaTramite,
//														solicitud.getNoFolioSolicitud(),
//														false, solicitud.getSolicitudId());
												log.debug("Se envia correo a -> " + fisicaTramite.getCorreoElectronico().getCorreo());
												
												correoEnviado = true;
												break;
											} else {
												log.error("La persona con CURP " + curp + " no cuenta con mail en el XML");
											}
										} 
									}
									if (correoEnviado) {
										break;
									}
								}
							}
						}
												
					} catch (PersonaConVariosNSSException e) {
						log.error("La persona con curp " + curp + " cuenta con más de un NSS");
					} catch (PersonaSinNSSException e) {
						log.error(e.getMessage());
					}
				} else {
					log.error("Se encontró más de una persona para el curp "
							+ curp + " [" + personasEncontradas.size()
							+ " concidencias]");
				}
			} else {
				log.error("No se encontraron personas para el curp " +  curp);
			}
			
			if (!correoEnviado) {
				log.error("No se envío correo al curp " + curp);
			}
		}
	}
	
	@Test
	public void testProcesarSolicitudAsignacionNSS() {
		
		Long idSolicitud = 9310L;
		
		try {
			serviceBusiness.procesarSolicitudAsignacionNSS(idSolicitud);
			
			log.debug("Finaliza la solicitud!");
		} catch (SolicitudNoEncontradaException e) {
			log.error(e.getMessage());
		} catch (AfectacionDatosPersonaException e) {
			log.error(e.getMessage());
		} catch (PersonaNoEncontradaException e) {
			log.error(e.getMessage());
		} catch (RegistroPersonaFisicaException e) {
			log.error(e.getMessage());
		} catch (NivelDeAsignacionSerieIndefinidoException e) {
			log.error(e.getMessage());
		} catch (SeriesNoLocalizadasException e) {
			log.error(e.getMessage());
		} catch (ErrorAlActivarSerieException e) {
			log.error(e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			log.error(e.getMessage());
		} catch (DomicilioNoValidoException e) {
			log.error(e.getMessage());
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error(e.getMessage());
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error(e.getMessage());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error(e.getMessage());
		} catch (PersonaSinCalificacionesException e) {
			log.error(e.getMessage());
		} catch (SolicitudNoValidaException e) {
			log.error(e.getMessage());
		}
	}
	
	@Test
	public void leerArchivoSIE(){
		
		EJBLocator.getAseguradoBusiness().cargarArchivoSIE("F5410115325_20140409131035_EDUCACION A DISTANCIA_SUR_5.xml");
	}
	
	@Test
	public void obtenerAsignacionNss() {
				
		System.out.println(this.serviceBusiness.obtenerCveAsignacionNss("01004800445"));
	}
}
