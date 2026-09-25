package mx.gob.imss.ctirss.delta.asegurado.solicitud;

import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.AseguradoServiciosExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.CambioCurpResponse;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.TramiteCambioCurpDTO;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.ValidaRequisitosCambioCurpResponse;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.VerificarCambioCurpResponse;

import org.junit.Test;

public class ServiciosMovilesTest {
	
	@Test
	public void testValidaCambioCurp() {
		
		String curp = "RUFF741117HDFZLR11";//"TEBM871029HPLRLR08";
		String nss = "30987408066";//"48068738524";
		String correo = "RUFF74@hotmail.com";
		
		this.validarRequisitosCambioCurpInterno(curp, nss, correo);
	
	}

	@Test
	public void testFinalizaActualizacionDatos() {
		TramiteCambioCurpDTO datos = new TramiteCambioCurpDTO();
		this.finalizarTramiteActualizacionDatos(datos);
	}
	
	@Test
	public void testFlujoCompletoActualizacionCurp() {
		String curp = "RUFF741117HDFZLR11";//"TEBM871029HPLRLR08";
		String nss = "30987408066";//"48068738524";
		String correo = "RUFF74@hotmail.com";
		
		ValidaRequisitosCambioCurpResponse response = this.validarRequisitosCambioCurpInterno(curp, nss, correo);
		TramiteCambioCurpDTO tramite = response.getTramite();
		this.finalizarTramiteActualizacionDatos(tramite);
	}
	
	private ValidaRequisitosCambioCurpResponse validarRequisitosCambioCurpInterno(String curp, String nss, String correo) {
		
		System.out.println("Empiezan validaciones de requisitos para actualizacion de curp");
		AseguradoServiciosExternosRemote asegurExternosRemote = EJBLocator.getServiciosExternosAseguradoService();
		
		ValidaRequisitosCambioCurpResponse response =asegurExternosRemote.validarCambioCurp(curp, nss, correo);
		TramiteCambioCurpDTO tramite = response.getTramite();
		System.out.println("El codigo de respuesta del servicio es: " + response.getCodigo());
		System.out.println("El mensaje de respuesta del servicio es: " + response.getMensaje());
		System.out.println("El tramite es nulo? " + (tramite == null));
		if(tramite != null) {
			System.out.println("El correo que se envia es: " + tramite.getCorreo());
			System.out.println("EL nombre de la persona es: " + tramite.getNombre());
			System.out.println("La nueva curp es: " + tramite.getCurpNueva());
			System.out.println("La curp anterior es: " + tramite.getCurpAnterior());
			System.out.println("Es necesario actualizar la curp de portal ciudadano: " + tramite.isActualizaPortalCorreo());
			System.out.println("Es necesario actualizar la curp de nss correo: " + tramite.isActualizaAsignacionCorreo());
		}
		System.out.println("Termina validacion de requisitos para actualizacion de curp");
		
		return response;
	}
	
	private void finalizarTramiteActualizacionDatos(TramiteCambioCurpDTO datos) {
		System.out.println("Empieza finalizacion de tramite de actualizacion de curp");
		AseguradoServiciosExternosRemote asegurExternosRemote = EJBLocator.getServiciosExternosAseguradoService();
		CambioCurpResponse response = asegurExternosRemote.finalizaActualizacionCurp(datos);
		System.out.println("El codigo de respuesta del servicio es: " + response.getCodigo());
		System.out.println("El mensaje de respuesta del servicio es: " + response.getMensaje());
	}
	
	@Test
	public void testValidaCurp() {
		String curp = "SAAC820312HDFNLS06";
		String nss = "96968200442";
		String correo = "mtb1_1@hotmail.com";
		
		AseguradoServiciosExternosRemote asegurExternosRemote = EJBLocator.getServiciosExternosAseguradoService();
		VerificarCambioCurpResponse response = asegurExternosRemote.validaCambioDatosCurp(curp, nss, correo);
		System.out.println("El codigo de respuesta del servicio es: " + response.getCodigo());
		System.out.println("El mensaje de respuesta del servicio es: " + response.getMensaje());
		System.out.println("Se ha ralizado correccion curp: " + response.getEstatus());
		System.out.println("La fecha de actualizacion es: " + response.getFechaActualizacion());
	}
}
