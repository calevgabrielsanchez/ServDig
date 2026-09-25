package mx.gob.imss.ctirss.delta.asegurado.solicitud;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

import org.junit.Test;

public class SolicitudTest {
	
    @Test
    public void altaSolicitud() throws SolicitudNoValidaException {
		System.out.println("altaSolicitud. Inicio " + new Date());
    	
        final Solicitud solicitud = EJBLocator.getSolicitudBusiness().crear(initSolicitud());
		System.out.println("La solicitud que se genero fue la: " + solicitud.getSolicitudId());
        assertNotNull("El id de la solicitud recien ingresada no deber ser nulo", solicitud.getSolicitudId());
		System.out.println("altaSolicitud. Final " + new Date());
    }

    private Solicitud initSolicitud() {
        // INICIALIZA LA SOLICITUD
        final AsignacionNSS personaFisica = new AsignacionNSS();
        personaFisica.setNssStr("mine value");
        DatosPersonas.agregaPersonaFisica1(personaFisica);
        DatosPersonas.agregaDocumentosProbatoriosCurp(personaFisica);
        DatosPersonas.agregarMediosContacto(personaFisica);
        DatosPersonas.agregarDomicilio(personaFisica);

        final AsignacionNSS personaFisica2 = new AsignacionNSS();
        DatosPersonas.agregaPersonaFisica2(personaFisica2);
        DatosPersonas.agregaDocumentosProbatoriosCurp(personaFisica2);
        //DatosPersonas.agregaDocumentosProbatorios(personaFisica2);
        DatosPersonas.agregarMediosContacto(personaFisica2);
        DatosPersonas.agregarDomicilio(personaFisica2);

        // AGREGA EL TRAMITE A LA SOLICITUD
        final Solicitud solicitud = new Solicitud();
        final TipoSolicitud tipoSolicitud = new TipoSolicitud();
        tipoSolicitud.setIdTipoSolicitud(1L);
        solicitud.setTipoSolicitud(tipoSolicitud);

        DatosPersonas.agregaTramitePersonaFisica(solicitud, personaFisica);
        DatosPersonas.agregaTramitePersonaFisica(solicitud, personaFisica2);
        return solicitud;
    }

    @Test
    public void procesarNuevaSolicitud() throws SolicitudNoEncontradaException, JAXBException, SolicitudNoValidaException {
		System.out.println("procesarNuevaSolcitud. Inicio " + new Date());
        final Solicitud solicitud = EJBLocator.getSolicitudBusiness().crear(initSolicitud());
		System.out.println("La solicitud que se genero fue la: " + solicitud.getSolicitudId());

		System.out.println("Se recupera la solicitud desde la base de datos ");
        final Solicitud solicitudTest = EJBLocator.getSolicitudBusiness().consultar(solicitud);
        System.out.println("Se procesa la solicitud recuperada ");
		try {
			EJBLocator.getServiceBusiness().procesarTramiteSolicitud(solicitudTest);
		} catch (Exception e) {
			System.out.println("error" + e);
		}
		System.out.println("procesarNuevaSolcitud. Final " + new Date());
    }    
    
    @Test
    public void procesarSolicitudYaRegistrada() throws SolicitudNoEncontradaException, JAXBException, SolicitudNoValidaException {
    	System.out.println("procesarSolicitudYaRegistrada. Inicio " + new Date());
        final Solicitud solicitudTest = EJBLocator.getSolicitudBusiness().consultar(new Solicitud(1864L));
        System.out.println("Solicitud encontrada " + solicitudTest);
        try {
        	EJBLocator.getServiceBusiness().procesarTramiteSolicitud(solicitudTest);
        } catch (Exception e) {
			System.out.println("error" + e);
		}
        System.out.println("procesarSolicitudYaRegistrada. Final " + new Date());
    }
    
    @Test
    public void testConsultar() throws SolicitudNoEncontradaException {
        final Solicitud solicitudFound = EJBLocator.getSolicitudBusiness().consultar(new Solicitud(1869L));
        assertNotNull("No debe ser nula la solicitud buscada", solicitudFound);
        System.out.println("La solicitud encontrada en BD es: " + solicitudFound);
        for (Tramite tramite : solicitudFound.getTramites()) {
			System.out.println("tramite: " + tramite);
			if(tramite instanceof TramiteAsegurado) {
				System.out.println("nss: " + ((TramiteAsegurado)tramite).getFisica().getNssStr());
			}
		}
	}
}