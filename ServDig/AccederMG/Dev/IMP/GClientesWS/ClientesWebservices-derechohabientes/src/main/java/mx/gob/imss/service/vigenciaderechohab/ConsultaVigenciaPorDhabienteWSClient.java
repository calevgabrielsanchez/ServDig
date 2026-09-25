package mx.gob.imss.service.vigenciaderechohab;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;

public class ConsultaVigenciaPorDhabienteWSClient {

	private final String FECHA_INVALIDA = "01/01/3000";
	private String socketTimeoutException = "Ocurrió un error de comunicación al intentar recuperar la cabeza de grupo familiar";
	
	
	public GrupoFamiliar getGrupoFamiliarPorDerechohabiente(
			Long idAsignacionNSS, Integer cveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException{
		try{

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date fecha = null;
			
			WSConsVigXDHab_Service service = new WSConsVigXDHab_Service();
			WSConsVigXDHab ws = service.getWSConsVigXDHabPort();
			
			MessageWSConsVigXDHab message = new MessageWSConsVigXDHab();
			message.setCveIdAsignacionNss(idAsignacionNSS.intValue());
			message.setCveIdPersonaIntegrante(cveEstadoDerechohabiente);
			
			RespuestaWSConsVigXDHab respuesta = ws.obtieneVigXDHab(message);
			GrupoFamiliar cgf = null;
			if (respuesta != null
					&& respuesta.getCodigoError() ==  0){
				cgf = new GrupoFamiliar();
				
				AsignacionNSS asignacionNSS = new AsignacionNSS();
				asignacionNSS.setIdAsignacionNSS(idAsignacionNSS);
				
				cgf.setAsignacionNSS(asignacionNSS);
				
				InfoConsVigXDHab itemRespuesta = respuesta.getInfoConsVigXDHab();
				
				EstadoDerechohabiente estado = new EstadoDerechohabiente();
				estado.setIdEstadoDerechohabiente(itemRespuesta.getCveEstadoDerechohabiente().getValue().longValue());
				cgf.setEstadoDerechohabiente(estado);
				
				SubEstadoDerechohabiente sub = new SubEstadoDerechohabiente();
				sub.setIdSubEstadoDerechohabiente( itemRespuesta.getCveSubestadoDerechohabiente().getValue().longValue() );
				cgf.setSubEstadoDerechohabiente(sub);
				
				Derechohabiente dhab = new Derechohabiente();
				dhab.setIdPersona( itemRespuesta.getCveIdPersona().getValue().longValue() );
				
				cgf.setDerechohabiente(dhab);
				
				if(itemRespuesta.getCveIdCalidadParentesco().getValue() != null){
					Parentesco p = new Parentesco();
					p.setIdParentesco(itemRespuesta.getCveIdCalidadParentesco().getValue().longValue() );
					cgf.setParentesco(p);	
				}				
				
				if(itemRespuesta.getFecFinVigencia() != null && itemRespuesta.getFecFinVigencia().getValue() != null && !FECHA_INVALIDA.equals(itemRespuesta.getFecFinVigencia().getValue().toString()) ){
					fecha = formatter.parse( itemRespuesta.getFecFinVigencia().getValue().toString() );
					cgf.setFechaFinVigencia( fecha );
				}
			
				if(itemRespuesta.getFecInicioVigencia() != null && itemRespuesta.getFecInicioVigencia().getValue() != null ){
					fecha = formatter.parse( itemRespuesta.getFecInicioVigencia().getValue().toString() );
					cgf.setFechaInicioVigencia( fecha );
				}
				
				if(itemRespuesta.getAgregadoMedico() != null && itemRespuesta.getAgregadoMedico().getValue() != null){
					cgf.setAgregadoMedico(itemRespuesta.getAgregadoMedico().getValue());
				}
				
			}
			else{
				throw new DerechohabientesWebSserviceException( respuesta.getCodigoError() + ", " + respuesta.getMensajeError() );
			}
			
			return cgf;
		} catch (Exception e) {
			e.printStackTrace();
			
			if(e.getCause() instanceof SocketTimeoutException){
				throw new DerechohabientesWebSserviceException(socketTimeoutException);
			}
			
			if( e instanceof com.sun.xml.ws.client.ClientTransportException ){
				throw new DerechohabientesWebSserviceException("Ocurrio un error en el WebService de Vigencia al obtener la información del la Cabeza de Grupo Familiar por derechohabiente");
			}
			
			throw new DerechohabientesWebSserviceException(e.getMessage());
		}
	}

}
