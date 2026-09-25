package personas;

import java.util.Date;

import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;

import org.junit.Test;

import test.EjbLocator;

public class SolicitudTest {

	@Test
	public void altaSolicitud() throws SolicitudNoEncontradaException, JAXBException {
		System.out.println("testSolicitudTramiteRegistroPersona. Inicio " + new Date());

		final SolicitudPersonaBusinessRemote service = EjbLocator.getSolicitudPersonaBusiness();
		final Solicitud solicitudRespuesta = service.alta(solicitudFisica());

		System.out.println("La solicitud que se gener� fue la: " + solicitudRespuesta.getIdSolicitud());
		System.out.println("testSolicitudTramiteRegistroPersona. Fin " + new Date());
	}

	@Test
	public void procesarSolicitudNueva() throws SolicitudNoEncontradaException, JAXBException {
		System.out.println("procesarSolicitudNueva. Inicio " + new Date());

		SolicitudPersonaBusinessRemote service = EjbLocator.getSolicitudPersonaBusiness();
		//Solicitud solicitudRespuesta = service.procesarSolicitudNueva(solicitudMoral());
		Solicitud solicitudRespuesta = null;
		try {
			solicitudRespuesta = service.procesarSolicitudNueva(solicitudFisica());
		} catch (SolicitudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("La solicitud que se gener� fue la: " + solicitudRespuesta.getIdSolicitud());
		System.out.println("procesarSolicitudNueva. Fin " + new Date());
	}

	@Test
	public void procesarSolicitudYaRegistrada() throws SolicitudNoEncontradaException, JAXBException {
		System.out.println("procesarSolicitudYaRegistrada. Inicio " + new Date());

		SolicitudPersonaBusinessRemote service = EjbLocator.getSolicitudPersonaBusiness();
		Solicitud solicitudRespuesta = service.getSolicitud(2015L);

		System.out.println("procesarSolicitudYaRegistrada. La soliticitud encontrada en BD es: \n" + solicitudRespuesta);
		try {
			service.procesarTramiteSolicitud(solicitudRespuesta);
		} catch (SolicitudException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("procesarSolicitudYaRegistrada. Fin " + new Date());
	}

	@Test
	public void getSolicitud() throws SolicitudNoEncontradaException, JAXBException {
		System.out.println("getSolicitud. Inicio " + new Date());

		final SolicitudPersonaBusinessRemote service = EjbLocator.getSolicitudPersonaBusiness();
		final Solicitud solicitudRespuesta = service.getSolicitud(20101L);

		System.out.println("La solicitud encontrada es: " + solicitudRespuesta);
		System.out.println("getSolicitud. Fin " + new Date());
	}

	private Solicitud solicitudMoral() {
		
		// INICIALIZA LA SOLICITUD
		final Moral persona = new Moral();
//		personas.DatosPersonas.agregaPersonaMoral(persona);
//		personas.DatosPersonas.agregarMediosContacto(persona);
//		personas.DatosPersonas.agregarDomicilio(persona);
//		
		// AGREGA EL TRAMITE A LA SOLICITUD
		final Solicitud solicitud = new Solicitud();
//		personas.DatosPersonas.agregaTramitePersona(solicitud, persona);
		return solicitud;
	}
	
	private Solicitud solicitudFisica() {
		
		// INICIALIZA LA SOLICITUD
		final Fisica personaFisica = new Fisica();
//		personas.DatosPersonas.agregaPersonaFisica1(personaFisica);
//		personas.DatosPersonas.agregaDocumentosProbatorios(personaFisica);
//		personas.DatosPersonas.agregarMediosContacto(personaFisica);
//		personas.DatosPersonas.agregarDomicilio(personaFisica);

		final Fisica personaFisica2 = new Fisica();
//		personas.DatosPersonas.agregaPersonaFisica2(personaFisica2);
//		personas.DatosPersonas.agregaDocumentosProbatorios(personaFisica2);
//		personas.DatosPersonas.agregarMediosContacto(personaFisica2);
//		personas.DatosPersonas.agregarDomicilio(personaFisica2);
		
		// AGREGA EL TRAMITE A LA SOLICITUD
		final Solicitud solicitud = new Solicitud();
//		personas.DatosPersonas.agregaTramitePersona(solicitud, personaFisica);
		//personas.DatosPersonas.agregaTramitePersona(solicitud, personaFisica2);
		return solicitud;
	}
}
