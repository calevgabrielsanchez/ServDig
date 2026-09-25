package mx.gob.imss.ctirss.sso.admonusuarios.cliente.test;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.sso.admonusuarios.ttds.cliente.ClienteConsultaCurpTTDS;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo.UsuarioTTD;

public class ClienteConsultaCurpTTDSTest {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteConsultaCurpTTDS.class);
	
	@Test
	public void testClienteConsultaCurpSiap() {
		ClienteConsultaCurpTTDS clienteConsultaCurpSiap = new ClienteConsultaCurpTTDS();
		UsuarioTTD usuarioNominaResponse = null;
		
//		String curp = "MOAJ760806HDFRLN06";
//		String curp = "OOVR840920HDFRLC03";
		String curp = "JARR631024MDFMDS03";
//		String curp = "VAGE740913HCMLTL02";
		
		String matricula = "";
		
		try {
			log.info("############ TEST CONSULTA CURP SIAP ############");
			usuarioNominaResponse = clienteConsultaCurpSiap.invocarServicioConsultaCurpTTDS(curp, matricula);
			
			if (usuarioNominaResponse != null) {
				log.info("############ LA CURP [{}] SI SE ENCUENTRA EN TTDS ############", usuarioNominaResponse.getCurp());
				log.info("############ LA PERSONA TIENE LA MATRICULA [{}] EN TTDS ############", usuarioNominaResponse.getMatricula());
			} else {
				log.info("############ LA CURP NO SE ENCUENTRA EN TTDS ############");
			}
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

}
