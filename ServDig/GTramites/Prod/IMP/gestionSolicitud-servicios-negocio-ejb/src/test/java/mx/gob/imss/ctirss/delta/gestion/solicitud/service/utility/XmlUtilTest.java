package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.util.Date;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtilTest {

	private static final Logger LOG;
	

	static {
		LOG = LoggerFactory.getLogger(XmlUtilTest.class);
	}

	@Test
	public void jaxbFisicaTest() {

		// Persona
		final Fisica personaFisica = new Fisica();
		personaFisica.setIdPersona(1L);
		personaFisica.setCurp("HDJGD7ERE98DFJW");
		personaFisica.setRfc("HDFJ37878733");
		personaFisica.setNombre("CARLOS ALBERTO X");
		personaFisica.setPrimerApellido("GARCIA");
		personaFisica.setSegundoApellido("REYES");
//		personaFisica.getLugarNacimiento().setClave("11");
//		personaFisica.getLugarNacimiento().setNombre("PUEBLA");
//		PersonaCalificacion personaCalificacion = new PersonaCalificacion();
//		personaCalificacion.setCalificacion(new Calificacion());
//        personaFisica.getPersonaCalificaciones().add(personaCalificacion);
//		personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(1L);

		// PAIS
//		Pais pais = new Pais();
//		pais.setIdPais(1);
//		personaFisica.setPais(pais);

		// SEXO
//		personaFisica.getSexo().setIdSexo(1);

		// DOCUMENTO PROBATORIO - ACTA DE NACIMIENTO
		Nacimiento actaNacimiento = new Nacimiento();
		actaNacimiento.setAnio(1950);
		actaNacimiento.setCrip("4552");
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

		 //MEDIOS DE CONTACTO - CORREO ELECTRONICO
		 TipoMedioContacto tipoCorreoElectronico = new TipoMedioContacto();
		 tipoCorreoElectronico.setIdTipoMedioContacto(1L);
		 CorreoElectronico correoElectronico = new CorreoElectronico();
		 correoElectronico.setCorreo("usuario@usuario.com");
		 correoElectronico.setTipoMedioContacto(tipoCorreoElectronico);
		 personaFisica.setCorreoElectronico(correoElectronico);
		
		 //MEDIOS DE CONTACTO - TELEFONO FIJO
		 TipoMedioContacto tipoTelefonoFijo = new TipoMedioContacto();
		 tipoTelefonoFijo.setIdTipoMedioContacto(2L);
		 TelefonoFijo telefonoFijo = new TelefonoFijo("5533-3355", "90",
		 "34552");
		 telefonoFijo.setTipoMedioContacto(tipoTelefonoFijo);
		 personaFisica.setTelefonoFijo(telefonoFijo);
		
		 //MEDIOS DE CONTACTO - TELEFONO MOVIL
		 TipoMedioContacto tipoTelefonoMovil = new TipoMedioContacto();
		 tipoTelefonoMovil.setIdTipoMedioContacto(3L);
		 TelefonoMovil telefonoMovil = new TelefonoMovil();
		 telefonoMovil.setNumero("55-1433-5533");
		 telefonoMovil.setTipoMedioContacto(tipoTelefonoMovil);
		 personaFisica.setTelefonoMovil(telefonoMovil);
		
//		 //DOMICILIO
//		 Domicilio domicilio = new Domicilio();
//		 personaFisica.getDomicilios().add(domicilio);
//		
//		 TipoDomicilio tipoDomicilio = new TipoDomicilio();
//		 tipoDomicilio.setClave(1);
//		 domicilio.setTipoDomicilio(tipoDomicilio);
//		
//		 // VIALIDADES
//		 TipoVialidad tipoVialidad = new TipoVialidad();
//		 tipoVialidad.setClave(1);
//		 Vialidad vialidad = new Vialidad();
//		 vialidad.setClave(1);
//		 vialidad.setTipoVialidad(tipoVialidad);
//		 vialidad.setNombre("MiHouse");
//		 domicilio.setVialidadPrimaria(vialidad);
//		 domicilio.setVialidadReferenciaPosterior(vialidad);
//		 domicilio.setVialidadReferenciaPrimaria(vialidad);
//		 domicilio.setVialidadReferenciaSecundaria(vialidad);
//		
//		 domicilio.setLongitud(2);
//		 domicilio.setNumExterior1(101);
//		 domicilio.setNumExterior2(102);
//		 domicilio.setNumExteriorAlf("B");
//		 domicilio.setNumInterior("2");
//		 domicilio.setNumInteriorAlf("1");
//		
//		
//		 TipoAmbito ambito = new TipoAmbito();
//		 ambito.setClave(1L);
//		 domicilio.setAmbito(ambito);
//		
//		 //CODIGO POSTAL
//		 CodigoPostal codigoPostal = new CodigoPostal();
//		 codigoPostal.setCodigoPostal(6500);
//		 domicilio.setCodigoPostal(codigoPostal);
//		
//		 //CONFIGURACION DE ASENTAMIENTO
//		 EntidadFederativa entidadFederativa = new EntidadFederativa();
//		 entidadFederativa.setClave("9");
//		 entidadFederativa.setNombre("DISTRITO FEDERAL");
//		
//		 Municipio municipio = new Municipio();
//		 municipio.setClave("15");
//		 municipio.setEntidadFederativa(entidadFederativa);
//		 municipio.setNombre("CUAUHTÉMOC");
//		
//		 Localidad localidad = new Localidad();
//		 localidad.setClave("1");
//		 localidad.setMunicipio(municipio);
//		 localidad.setNombre("CUAUHTÉMOC");
//		
//		 Asentamiento asentamiento = new Asentamiento();
//		 asentamiento.setClave("10");
//		 asentamiento.setNombre("CUAUHTÉMOC");
//		 asentamiento.setLocalidad(localidad);
//		 asentamiento.setCodigoPostal(codigoPostal);
//		 domicilio.setAsentamiento(asentamiento);
//
//		 personaFisica.getDomicilios().add(domicilio);
//		 
//		// PERSONA VALIDADA POR IMSS
//		personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(1L); // TODO cambiarlo por
//										// EstadoPersonaEnum.EDO_NEW.getCodigo()

		final TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo());
		tipoTramite.setDescripcion(TipoTramiteEnum.REGISTRO_DE_PERSONA.toString()); // TODO To change enumeration to support description...

		final TramiteFisica tramite1 = new TramiteFisica();
		// tramite1.setTramiteId(1L);
		tramite1.setTipoTramite(tipoTramite);
		tramite1.setFisica(personaFisica);
		tramite1.getEstadoTramite().setIdEstadoTramitePersona(1);

		final Fisica persona2 = new Fisica();
		persona2.setCurp("HDJGD7ERE98DFJW");
		persona2.setRfc("HDFJ37878733");
		persona2.setNombre("JUAN PABLO X");
		persona2.setPrimerApellido("NIEVES");
		persona2.setSegundoApellido("TERRAN");
		persona2.getSexo().setIdSexo(1);
		persona2.getLugarNacimiento().setClave("21");
		persona2.getLugarNacimiento().setNombre("QUERETARO");
        PersonaCalificacion personaCalificacion2 = new PersonaCalificacion();
        personaCalificacion2.setCalificacion(new Calificacion());
        persona2.getPersonaCalificaciones().add(personaCalificacion2);
		persona2.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(2L);
		
//		Domicilio domicilio = new Domicilio();
//		domicilio.setClave(23);
//		personaFisica.getDomicilios().add(domicilio);
//		personaFisica.getDomicilios().add(new Domicilio());
		// // Persona Domicilio
		// final PersonaDomicilio personaDomicilio = new PersonaDomicilio();
		// personaDomicilio.setFechaRegistroActualizado(new Date());
		// personaDomicilio.setFechaRegistroAlta(new Date());
		// personaDomicilio.setFechaRegistroBaja(new Date());
		// // Domicilio
		// final Domicilio domicilio = new Domicilio();
		// domicilio.setClave(1);
		// // Codigo postal
		// final CodigoPostal codigoPostal = new CodigoPostal();
		// codigoPostal.setCodigoPostal(123456);
		// domicilio.setCodigoPostal(codigoPostal);
		// domicilio.setNumExterior1(12);
		// domicilio.setNumExteriorAlf("A-12");
		//
		// personaDomicilio.setDomicilio(domicilio);

		// final List<PersonaDomicilio> pesonaDomicilios = new
		// ArrayList<PersonaDomicilio>();
		// pesonaDomicilios.add(personaDomicilio);

		// XXX este campo no existe en clase 'nueva':
		// personaF.setPersonaDomicilio(pesonaDomicilios);

		// final Nacimiento actaNacimiento = new Nacimiento();
		// actaNacimiento.setAnio(Integer.valueOf(2000));
		// actaNacimiento.setIdDocumentoProbatorio(23);

		((Fisica) personaFisica).setNombre("Juan");
		((Fisica) personaFisica).setPrimerApellido("Perez");

		// Solicitud
//		final Solicitud solicitud = new Solicitud();
		final EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setDescripcion("Solicitud Activa");
		estadoSolicitud.setIdEstadoSolicitud(2);
//		solicitud.setEstadoSolicitud(estadoSolicitud);
//		solicitud.setFechaSolicitud(new Date());
//		solicitud.setSolicitudId(1L);
//		solicitud.setNoFolioSolicitud("123456");
//		solicitud.setObservacion("Observaciones");

		// Tramite
		final Tramite tramite = new TramiteFisica();
		//		final TramiteFisica tramite = new TramiteFisica(null);
		// tramite.setSolicitud(solicitud);
		((TramiteFisica) tramite).setFisica((Fisica) personaFisica);

//		tramite.setFechaTramite(new Date());
//		tramite.setObservacion("observacion");
//		tramite.setResultado(Boolean.TRUE);
//		tramite.setTramiteId(23L);
		
		// Prueba Objeto a xml
		final String xml = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(tramite);
		LOG.trace("\n" + xml);

		// Prueba XML a Object
		final Tramite tramite11 = (Tramite) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(xml);
		LOG.debug("Tramite 1: " + tramite11);

	}

    @Test
    public void jaxbSujetoObligadoTest() {
        final TramiteSujetoObligado tramite = new TramiteSujetoObligado();
        final SujetoObligado sujetoObligado = new SujetoObligado();
        sujetoObligado.setCveIdSujetoObligado(3L);
        tramite.setSujetoObligado(sujetoObligado);
        final String xml = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(tramite);
        LOG.trace("\n" + xml);

    }
    
    @Test
    public void jaxbMoralTest() {
        final Tramite tramite = new TramiteMoral();
        final String xml = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(tramite);
        LOG.trace("\n" + xml);
    }
    
    @Test
    public void jaxbAseguradoTest() {
        final TramiteAsegurado tramite = new TramiteAsegurado();
        final AsignacionNSS fisica = new AsignacionNSS();
        fisica.setNssStr("value test nss");
        fisica.setIdPersona(23L);
        tramite.setFisica(fisica);
        final String xml = mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(tramite);
        LOG.trace("\n" + xml);
    }
    
}
