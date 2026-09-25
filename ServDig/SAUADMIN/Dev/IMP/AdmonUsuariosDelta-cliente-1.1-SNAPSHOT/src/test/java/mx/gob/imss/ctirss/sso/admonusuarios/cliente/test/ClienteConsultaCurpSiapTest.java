package mx.gob.imss.ctirss.sso.admonusuarios.cliente.test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.sso.admonusuarios.siap.cliente.ClienteConsultaCurpSiap;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;

public class ClienteConsultaCurpSiapTest {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteConsultaCurpSiap.class);
	
	@Test
	public void testClienteConsultaCurpSiap() {
		ClienteConsultaCurpSiap clienteConsultaCurpSiap = new ClienteConsultaCurpSiap();
		UsuarioNominaResponse usuarioNominaResponse = null;
		
//		String curp = "MOAJ760806HDFRLN06";
//		String curp = "OOVR840920HDFRLC03";
		String curp = "JARR631024MDFMDS03";
//		String curp = "VAGE740913HCMLTL02";
		
		String matricula = "";
		
		try {
			log.info("############ TEST CONSULTA CURP SIAP ############");
			usuarioNominaResponse = clienteConsultaCurpSiap.invocarServicioConsultaCurpSiap(curp, matricula);
			
			if (usuarioNominaResponse != null) {
				log.info("############ LA CURP [{}] SI SE ENCUENTRA EN SIAP ############", usuarioNominaResponse.getCurp());
				log.info("############ LA PERSONA TIENE LA MATRICULA [{}] EN SIAP ############", usuarioNominaResponse.getMatricula());
			} else {
				log.info("############ LA CURP NO SE ENCUENTRA EN SIAP ############");
			}
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

}
