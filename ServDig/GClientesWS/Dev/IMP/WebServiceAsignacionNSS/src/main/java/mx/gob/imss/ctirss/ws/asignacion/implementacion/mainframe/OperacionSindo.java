 
 package mx.gob.imss.ctirss.ws.asignacion.implementacion.mainframe;

import java.util.ResourceBundle;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.CanaseBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.bean.ResponseMainFrameBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.exception.SindoException;

import org.apache.log4j.Logger;


/**
 * @author juancho
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OperacionSindo {
	
	private static Logger log = Logger.getLogger(OperacionSindo.class);
	
	
	private  String ciz_cics = null;
    private  String ip_nodo =null;
    private  int puerto=0;
    private ResourceBundle resourceBundle;
    private TransaccionMainFrame connection1;
    private int tiempoRespuesta;
    private boolean activaDebug;

    
    public OperacionSindo(String usuario, String password){
        try{
    	resourceBundle = ResourceBundle.getBundle("imssTrabajadoresConfig");
        ip_nodo = resourceBundle.getString("pathCanaseMainFrame");
        puerto = Integer.parseInt(resourceBundle.getString("puertoCanaseMainFrame"));
        ciz_cics = resourceBundle.getString("cizCanaseMainFrame");
        activaDebug = Boolean.valueOf(resourceBundle.getString("debugTransaccionMainFrame")).booleanValue();
        tiempoRespuesta = Integer.parseInt(resourceBundle.getString("tiempoEsperaMainFrame"));
    	connection1= new TransaccionMainFrame(ip_nodo,puerto,usuario,password,ciz_cics, activaDebug, tiempoRespuesta);
        }catch(Exception e){
         log.debug("error en la constructor" , e);   
        }
    }
    
    
	public ResponseMainFrameBean transaccion (CanaseBean beanCanase) throws SindoException, Exception {
		log.debug("entre a hacer la transaccion");
		try {
           ResponseMainFrameBean response = connection1.getTransaction(beanCanase);
            log.debug("##### Response regresado " + response.getStrResultado());
			//log.debug("regrese de sendtoqueue");
            if(response.getErrorNumber() == 5 || response.getErrorNumber() == 6 ){
                log.debug("error al tratar de leer el mensaje de respuesta ");

                throw new Exception(response.getErrorMessage());
            }
              return response;
        } catch(SindoException e){
            log.debug("ERROR en la transaccion con CANASE" + e);
            log.debug("ERROR en la transaccion con CANASE" + e.getMessage());
            log.debug("ERROR en la transaccion con CANASE" + e.getCodigoError());
			throw e;
		}
    }
	
	
}