package mx.gob.imss.ctirss.delta.gestion.asegurado.test;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.AseguradoServiciosExternosRemote;

import org.junit.Test;

public class ConsultaServiciosExternosTest{
	
//	@Autowired  AseguradoServiciosExternosRemote test;
	@Test
	public void serviciosExternosAsegurados() throws SolicitudNssCorreoException{
//		String curp="";
//		String correo="";
//		long cveIdTipoSolicitud=15;
		
		AseguradoServiciosExternosRemote aser = EJBLocator.getServiciosExternosAseguradoService();
		
		//test.validarDerechoAConsultaNSS("bolp820510hdfmgb04", "pablo.bombela@imss.com", 15l);

		try {
			int valor = aser.validarDerechoAConsultaNSS("DUSL821218HDFRLC09", "lucio.duran.silva@icloud.com");
			System.out.println("Servicio Ejecutado" + valor);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
						
	}
	
//	public void main (String args []){
//		this.serviciosExternosAsegurados();
//		System.out.println("Servicio Ejecutado");
//		
//	}


}

