import java.util.Date;
import java.util.List;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.webservice.renapo.curp.implementacion.ClienteWebserviceResponsablesSubdelegacion;
import mx.gob.imss.webservice.renapo.curp.utility.RolesResponsablesSubdelegacionEnum;

public class ConsultaResponsablesTest {

	@Test
	public void consultaResponsablesThread() {
		System.out.println("consulta. Inicio. " + new Date());

		try {
			ClienteWebserviceResponsablesSubdelegacion clienteWebserviceResponsablesSubdelegacion = new ClienteWebserviceResponsablesSubdelegacion();
			List<Fisica> respuesta = clienteWebserviceResponsablesSubdelegacion
					.buscarResponsables(6, 21, RolesResponsablesSubdelegacionEnum.AUTORIZADOR.getClave(), 14);

			if (respuesta != null) {
				for (Fisica fisica : respuesta) {
					System.out.println("Nombre " + fisica.getNombre());
					System.out.println("Primer Apellido "
							+ fisica.getPrimerApellido());
					System.out.println("Segundo Apellido "
							+ fisica.getSegundoApellido());
					System.out.println("CURP " + fisica.getCurp());
					System.out.println("Correo Electronico "
							+ fisica.getCorreoElectronico().getCorreo());
				}

			}
		} catch (ClienteWebserviceResponsablesSubdelegacionException ws) {
			System.out.println("Se generó un error en el webservice");
		} catch (Exception e) {
			System.out
					.println("\n\n Se generó un errorno identificado en cliente\n\n");
			e.printStackTrace();
		}

		System.out.println("consulta. Final. " + new Date());
	}

}
