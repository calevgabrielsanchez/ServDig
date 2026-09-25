package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;

import org.junit.Test;

public class CorreccionDerechohabienteServiceTest {
	
	@Test
	public void testTramitesAbiertos() {
		CorreccionDerechohabienteServiceRemote ejb = EjbLocator.getCorreccionDerechohabienteService();
		AsignacionNSS nss = new AsignacionNSS();
		nss.setIdPersona(46700759L);
		
		List<Long> idsPersona = new ArrayList<Long>();
		idsPersona.add(nss.getIdPersona());
		
		try {
			List<Tramite> tramites= ejb.findTramitesAbiertos(nss, idsPersona);
			
			if(tramites == null) {
				System.out.println("No se encontraron tramites abiertos para la persona " + idsPersona.get(0));
			} else {
				System.out.print("Encontre " + tramites.size() + " tramites");
			}
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Test
	public void testCandidatosCambioClinica() {
		CorreccionDerechohabienteServiceRemote ejb = EjbLocator.getCorreccionDerechohabienteService();
		Usuario usuario = new Usuario();
		usuario.setIdUmf(440L);
		usuario.setPerfilUsuario(new PerfilUsuario());
		usuario.getPerfilUsuario().setIdPerfilUsuario(1L);
		
		AsignacionNSS nss = new AsignacionNSS(62571803L);
		nss.setNss("89806300821");
		nss.setNssStr("89806300821");
		/*
		try {
			List<GrupoFamiliar> lista = ejb.findGrupoFamiliarCambioUmf(nss, usuario, false, false);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	}
	
	@Test
	public void testCircunscripcionForanea() {
		CorreccionDerechohabienteServiceRemote ejb = EjbLocator.getCorreccionDerechohabienteService();
		Usuario usuario = new Usuario();
		usuario.setIdUmf(440L);
		usuario.setPerfilUsuario(new PerfilUsuario());
		usuario.getPerfilUsuario().setIdPerfilUsuario(1L);
		
		AsignacionNSS nss = new AsignacionNSS(62571803L);
		nss.setNss("89806300821");
		nss.setNssStr("89806300821");
		
		try {
			TramiteCircunscripcionForanea tramite = ejb.getCircunscripcionForanea( 249959974L, nss, true);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
