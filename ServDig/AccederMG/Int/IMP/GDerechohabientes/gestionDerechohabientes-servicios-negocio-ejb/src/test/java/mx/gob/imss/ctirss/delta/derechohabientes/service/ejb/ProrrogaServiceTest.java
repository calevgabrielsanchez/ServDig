package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import java.util.List;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ProrrogaServiceRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

public class ProrrogaServiceTest {

	@Test
	public void testCandidatosProrroga() {
		ProrrogaServiceRemote prorrogaService = EjbLocator.getProrrogaService();
		
		
		Long tipoProrroga = 1L;
		Long idAsignacioNSS = 2L;
		Boolean saltaSolicitudes = false;
		
		GrupoFamiliar grupoFamiliar = null;
		CabezaGrupoFamiliar cabeza = new CabezaGrupoFamiliar();
		Usuario usuario = null;
		
		try {
			List<GrupoFamiliar> candidatos = prorrogaService.getCandidatosProrroga(cabeza, grupoFamiliar, tipoProrroga, idAsignacioNSS, usuario, saltaSolicitudes);
			
			if(candidatos != null && !candidatos.isEmpty()) {
				for(GrupoFamiliar candidato: candidatos) {
					System.out.println("Se encontro al candidato: " + candidato.getDerechohabiente().getNombreCompleto() +
							" en la umf " + candidato.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto() + 
							" con parentesco " + candidato.getParentesco().getDescripcion());
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
