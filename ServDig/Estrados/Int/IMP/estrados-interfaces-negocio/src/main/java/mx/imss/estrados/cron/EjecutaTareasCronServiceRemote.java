package mx.imss.estrados.cron;

import java.util.Date;

import javax.ejb.Remote;

@Remote
public interface EjecutaTareasCronServiceRemote {

	public void modificaNotificacionesPublicadas();
	
	public void modificaNotificacionesRegistradas();
	
	Integer modificaNotificacionesPublicadasPorFecha(Integer idEstatus, Integer idCambio, Date FechaRegistro);
}
