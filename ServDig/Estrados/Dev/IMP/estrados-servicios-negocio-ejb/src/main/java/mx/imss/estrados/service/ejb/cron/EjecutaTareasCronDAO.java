package mx.imss.estrados.service.ejb.cron;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.imss.estrados.entity.NeeCatProceso;
import mx.imss.estrados.entity.NeeCatTipodocumento;
import mx.imss.estrados.entity.NeeDocumentosAdjuntos;
import mx.imss.estrados.entity.NeeNotificaciones;
import mx.imss.estrados.entity.SsoVwUsuario;

@Local
public interface EjecutaTareasCronDAO {
	
	public List<NeeNotificaciones> consultaNotificacionesAModificar(Integer idStatus);
	
	public List<NeeNotificaciones> consultaNotificacionesARetirar(Integer idStatus);
	
	public NeeDocumentosAdjuntos consultaDoctoAdjunto(long idNotificacion, int idTipoAdjunto);
	
	public SsoVwUsuario consultarDatosUsuario(String curp);
	
	public NeeCatTipodocumento consultaTipoDocumento(Integer idDocumento);
	
	public NeeCatProceso consultaProceso(Integer idProceso);
	
	List<NeeNotificaciones> consultaNotificacionesAModificarPorFecha(Integer idStatus, Date fecha);
	
	public List<Date> obtenerFechasConNotificacionesPendientes(Integer idStatus);
	
}
