package gob.imss.webservice.sat.rfc.implementacion;

import java.util.Date;

import org.apache.log4j.Logger;

import gob.imss.webservice.sat.rfc.cliente.EntradaSAT;
import gob.imss.webservice.sat.rfc.cliente.SalidaSAT;

public class DatosWebserviceRfc  extends Thread {
        private Logger log = Logger.getLogger(DatosWebserviceRfc.class);
        SalidaSAT respuesta = null;
        String sRfc = "";
        boolean bErrorServicio = false;

    	public void run(){
			log.error("WebserviceSatRfc. Thread. Se inicia el thread de consulta. " + new Date());
    		
            EntradaSAT entrada = new EntradaSAT();
            entrada.setRfc(sRfc);

            try{
	        	gob.imss.webservice.sat.rfc.cliente.SATPatronesService service = new gob.imss.webservice.sat.rfc.cliente.SATPatronesService();
	        	gob.imss.webservice.sat.rfc.cliente.SATPatrones port = service.getSATPatronesSoapPort();
    			log.error("WebserviceSatRfc. Thread. El proceso inicia consulta al SAT. " + new Date());
            	respuesta = port.getPatron(entrada);
    			log.error("WebserviceSatRfc. Thread. Se finaliza la consulta satisfactoriamente al SAT. " + new Date());
            }
            catch(Exception e){
    			log.error("WebserviceSatRfc. Thread. Se generó un error al accesar el webservice: ");
    			bErrorServicio = true;
    			e.printStackTrace();
            }
            
			log.error("WebserviceSatRfc. Thread. Fin del thread. " + new Date());
    	}
}
