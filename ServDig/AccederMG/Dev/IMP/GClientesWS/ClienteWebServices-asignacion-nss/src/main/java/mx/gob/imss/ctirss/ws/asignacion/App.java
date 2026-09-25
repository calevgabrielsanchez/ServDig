package mx.gob.imss.ctirss.ws.asignacion;

import mx.gob.imss.ctirss.ws.asignacion.implementacion.AsignacionNSSBean;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.InicioLlamadaTransService;
import mx.gob.imss.ctirss.ws.asignacion.implementacion.InicioLlamadaTransServiceService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        InicioLlamadaTransServiceService service = new InicioLlamadaTransServiceService();
        InicioLlamadaTransService port = service.getInicioLlamadaTransServicePort();
        port.ejecutarAlta(new AsignacionNSSBean(), "FACV092", "JUN13");
        
        
    }
}
