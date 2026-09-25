package mx.gob.imss.consulta.tramites.business.implementacion;

import javax.ejb.Stateless;

import mx.gob.imss.consulta.tramites.business.FinalizaSolicitudWSClientRemote;
import mx.gob.imss.consulta.tramites.service.Message;
import mx.gob.imss.consulta.tramites.service.RespuestaWS;
import mx.gob.imss.consulta.tramites.service.ServActualizaFuentesBDTU;
import mx.gob.imss.consulta.tramites.service.ServActualizaFuentesBDTUService;
import mx.gob.imss.consulta.tramites.business.Mensaje;
import mx.gob.imss.consulta.tramites.business.Respuesta;


@Stateless(name="finalizaSolicitudWSClientService", mappedName="finalizaSolicitudWSClientService")
public class FinalizaSolicitudWSClientService implements
		FinalizaSolicitudWSClientRemote {

	@Override
	public Respuesta finalizarSolicitud(Mensaje mensaje) throws Exception {
		ServActualizaFuentesBDTUService service = new ServActualizaFuentesBDTUService();
		ServActualizaFuentesBDTU serv = service.getServActualizaFuentesBDTUPort();
		return transformarRespuestaWS(serv.actualizaMovimientosRecientesBDTU(transformarMensaje(mensaje)));
	}
	
	private Message transformarMensaje(Mensaje mensaje){
		Message message = new Message();
		message.setIdentificadorFuente(mensaje.getIdentificadorFuente());
		message.setIdentificadorOperacion(mensaje.getIdentificadorOperacion());
		message.setRegistroAInsertar(mensaje.getRegistroAInsertar());
		return message;
	}
	
	private Respuesta transformarRespuestaWS(RespuestaWS respuestaWS){
		Respuesta respuesta = new Respuesta();
		respuesta.setCodigoError(respuestaWS.getCodigoError());
		respuesta.setMensajeError(respuestaWS.getMensajeError());
		return respuesta;
	}

}
