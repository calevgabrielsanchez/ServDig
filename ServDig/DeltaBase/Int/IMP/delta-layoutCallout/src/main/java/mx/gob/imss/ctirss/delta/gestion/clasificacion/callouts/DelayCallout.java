package mx.gob.imss.ctirss.delta.gestion.clasificacion.callouts;

public class DelayCallout {
	public static void esperar(int tiempo) {
		try {
			Thread.sleep(tiempo);
		} catch (InterruptedException e) {
			System.out.println("Error al ejecutar hilo de espera");
		}
	}
}
