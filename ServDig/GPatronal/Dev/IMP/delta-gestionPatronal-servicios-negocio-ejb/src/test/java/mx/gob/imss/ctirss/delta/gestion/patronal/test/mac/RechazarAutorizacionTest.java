package mx.gob.imss.ctirss.delta.gestion.patronal.test.mac;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

public class RechazarAutorizacionTest {

	@Test
	public void testRechazarAutorizacionDictamen(){
		System.out.println("::: Inicio");
		try {
			AnalisisServiceBusinessRemote ejb = EJBLocator.getAnalisisServiceBusiness();
			ClasificacionDTO dto = getDTODictamen();
			AnalisisClasificacionEmpresas analisisClasificacionEmpresas = ejb.rechazarAutorizacion(dto, new Long("103246"), true);
			System.out.println(analisisClasificacionEmpresas.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}			
		System.out.println("::: Fin");		
	}
	
	public static ClasificacionDTO getDTODictamen() {
		
		final Usuario usuario = new Usuario();
		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion("Jefe de Oficina Del");
		pu.setIdPerfilUsuario(3L);

		usuario.setPassword("prueba");
		usuario.setUsuario("CASA700420MCLMNL07");
		usuario.setCveIdUsuario("CASA700420MCLMNL07");
		usuario.setCveIdSubdelegacion(16L);
		UsuarioFuncionario uf = new UsuarioFuncionario();
		uf.setDelegacion(new Delegacion());
		uf.getDelegacion().setId(5L);
		uf.setSubdelegacion(new Subdelegacion());
		uf.getSubdelegacion().setId(16L);
		uf.setUsuario(usuario);
		usuario.setUsuarioFuncionario(uf);
		
		final ClasificacionDTO clasificacionDTO = new ClasificacionDTO();
		clasificacionDTO.setCveIdSolicitud("795190571");
		clasificacionDTO.setCveIdAnalisis("1253074");
		clasificacionDTO.setIdEstatus("6");
		clasificacionDTO.setRegPatronal("A301129510");
		clasificacionDTO.setTipoPersona("2");
		clasificacionDTO.setCveIdDelegacion("5");
		clasificacionDTO.setCveIdSubdelegacion("16");
		clasificacionDTO.setCveIdFraccionAct("235");
		clasificacionDTO.setPrimaSRTAct("2.5984");
		clasificacionDTO.setCveIdFraccionPro("153");
		clasificacionDTO.setPrimaSRTPro("4.65325");
		clasificacionDTO.setCveIdFraccionAnt("235");
		clasificacionDTO.setPrimaSRTAnt("2.5984");
		clasificacionDTO.setTTramite("1");
		clasificacionDTO.setCveNumSubdelegacion("12");
		clasificacionDTO.setComentarios("Rechazo de Autorización");
		
		clasificacionDTO.setUsuario(usuario);
		
		return clasificacionDTO;
	}	

}
