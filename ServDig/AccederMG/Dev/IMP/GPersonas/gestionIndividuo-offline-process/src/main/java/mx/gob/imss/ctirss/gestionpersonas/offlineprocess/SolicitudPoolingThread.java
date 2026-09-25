/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.gestionpersonas.offlineprocess;

public class SolicitudPoolingThread extends Thread {

	public boolean bTerminaHilo = false;
	public boolean bEjecutaProcesoOffline = true;
	public boolean bDebug = false;

	public SolicitudPoolingThread() {
		super("TramitePoolingThread");
	}

	@Override
	public void run() {
		try {
			boolean bPrimeraVez = true;
			while (!bTerminaHilo) {

				if (bPrimeraVez) {
					//SI ES LA PRIMERA VEZ QUE SE EJECUTA ESE THREAD ESPERAMOS 10 SEGUNDOS HASTA QUE TERMINE
					//DE LEVANTAR EL SERVIDOR DE APLICACIONES Y ESTEN DISPONIBLES LOS RECURSOS DEL SISTEMA
					Thread.sleep(15000);
					bPrimeraVez = false;
				}
				
				if (bEjecutaProcesoOffline){
					if (bDebug){
						System.out.println("SolicitudPoolerServlet. Se ejecuta la busqueda de solicitudes en base de datos");	
					}
					EjbLocator.getSolicitudPersonaBusiness().procesarSolicitudesRegistradas();					
				}

				try {
					sleep(4000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("TramitePoolingThread. SE FINALIZA THREAD TramitePoolingThread");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}