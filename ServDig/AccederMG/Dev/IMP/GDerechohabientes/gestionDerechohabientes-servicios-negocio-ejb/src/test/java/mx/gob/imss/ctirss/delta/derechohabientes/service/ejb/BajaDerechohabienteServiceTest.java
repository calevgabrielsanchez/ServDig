package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechoabientePensionesRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.junit.Test;

public class BajaDerechohabienteServiceTest {

	@Test
	public void testCandidatosBajaAdministrativa() {
		BajaDerechohabienteServiceRemote ejb = EjbLocator.getBajaDerechohabienteService();
		Long idAsignacionNss = 19378833L;
		Usuario usuario = new Usuario();
		usuario.setPerfilUsuario(new PerfilUsuario());
		usuario.getPerfilUsuario().setIdPerfilUsuario(PerfilesEnum.AUTORIZADOR.getId());
		usuario.setIdUmf(440L);
		usuario.setCveIdSubdelegacion(137L);
		
		try {
			List<GrupoFamiliar> candidatos = ejb.findGrupoFamiliarBajaAdministrativa(idAsignacionNss, usuario);
			
			if(candidatos != null && !candidatos.isEmpty()) {
				for(GrupoFamiliar candidato: candidatos) {
					System.out.println("Se encontro al candidato: " + candidato.getDerechohabiente().getNombreCompleto() +
							" en la umf " + candidato.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto() + 
							" con parentesco " + candidato.getParentesco().getDescripcion());
				}
			}
			
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testBajaPensiones() {
		BajaDerechoabientePensionesRemote ejb = EjbLocator.getBajaDerechohabientePensionesService();
		
		try {
			Solicitud solicitud = ejb.finalizarSolicitudBaja(9077485L, 9012508L, 1L, "MTBA", "observacioens", new Date());
			System.out.println("La solicitud creada es : " + solicitud.getNoFolioSolicitud());
		} catch (DerechohabientesBusinessException e) {
			System.out.println("El codigo de error es: " + e.getSituacion());
			System.out.println("El mensaje de error es: " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindBajasPorDerechohabiente() {
		BajaDerechoabientePensionesRemote ejb = EjbLocator.getBajaDerechohabientePensionesService();
		
		try {
			List<Long> bajasActivas= ejb.findBajasActivasBeneficiario(54385146L, 2028L);
			//retornamos error indicando que se encontro una baja activa para el candidato
			if(bajasActivas != null && !bajasActivas.isEmpty()) {
				String error = "La persona ya cuenta con una baja activa (";
				for(Long baja: bajasActivas) {
					error += " "+baja+" ";
				}
				error += ")";
				
				System.out.println(error);
			}
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testValidaBajaDerechohabiente() {
		BajaDerechoabientePensionesRemote ejb = EjbLocator.getBajaDerechohabientePensionesService();
		
		try {
			GrupoFamiliar gp = ejb.validarBajaDerechohabientes(9077485L, 9012508L, 1L);
			System.out.println("VAMOS A LA PLAYA " + gp.toString());
			//retornamos error indicando que se encontro una baja activa para el candidato
			
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
