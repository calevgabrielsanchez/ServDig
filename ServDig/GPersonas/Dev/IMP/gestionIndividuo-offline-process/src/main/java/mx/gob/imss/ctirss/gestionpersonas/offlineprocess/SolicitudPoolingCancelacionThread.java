/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.gestionpersonas.offlineprocess;

public class SolicitudPoolingCancelacionThread extends Thread {

	public boolean bTerminaHilo = false;
	public boolean bEjecutaProcesoOffline = false;
	public boolean bDebug = false;

	public SolicitudPoolingCancelacionThread() {
		super("SolicitudPoolingCancelacionThread");
	}

	@Override
	public void run() {
		try {
			boolean bPrimeraVez = true;
			while (!bTerminaHilo) {

				if (bPrimeraVez) {
					//SI ES LA PRIMERA VEZ QUE SE EJECUTA ESE THREAD ESPERAMOS 15 SEGUNDOS HASTA QUE TERMINE
					//DE LEVANTAR EL SERVIDOR DE APLICACIONES Y ESTEN DISPONIBLES LOS RECURSOS DEL SISTEMA
					Thread.sleep(15000);
					bPrimeraVez = false;
				}
				
				if (bEjecutaProcesoOffline){
					if (bDebug){
						System.out.println("SolicitudPoolingCancelacionThread. Se ejecuta la busqueda de solicitudes a cancelar en base de datos");	
					}
					EjbLocator.getSolicitudPersonaBusiness().cancelaSolicitudes();					
				}

				try {
					//sleep(120000);
					sleep(10000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("SolicitudPoolingCancelacionThread. SE FINALIZA THREAD SolicitudPoolingCancelacionThread");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}