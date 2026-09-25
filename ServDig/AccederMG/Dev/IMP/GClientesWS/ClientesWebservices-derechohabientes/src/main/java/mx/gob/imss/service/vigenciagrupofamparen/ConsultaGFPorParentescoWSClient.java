package mx.gob.imss.service.vigenciagrupofamparen;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;

public class ConsultaGFPorParentescoWSClient {
	
	private String socketTimeoutException = "Ocurrió un error de comunicación al intentar recuperar la cabeza de grupo familiar";
	
	public List<GrupoFamiliar> getGrupoFamiliarPorParentesco(
			Long idAsignacionNSS, Integer cveIdCalidadParentezco)
			throws DerechohabientesWebSserviceException{
		try{
			List<GrupoFamiliar> items = null;
			WSConsVigGpoFamXParen_Service service = new WSConsVigGpoFamXParen_Service();
			WSConsVigGpoFamXParen ws = service.getWSConsVigGpoFamXParenPort();
			
			MessageWSConsVigGpoFamXParen message = new MessageWSConsVigGpoFamXParen();
			message.setCveIdAsignacionNss(idAsignacionNSS.intValue());
			message.setCveIdCalidadParentesco(cveIdCalidadParentezco);
			
			RespuestaWSConsVigGpoFamXParen respuesta = ws.obtieneVigCabGpoFamXParen(message);
			
			if (respuesta != null
					&& respuesta.getCodigoError()== 0){
				List<InfoConsVigGpoFamXParen> itemsRespuesta = respuesta.getListaResultados().getListInfoConsVigGpoFamXParen();
				if( itemsRespuesta != null && !itemsRespuesta.isEmpty() ){
					items = new ArrayList<GrupoFamiliar>();
					
					for (InfoConsVigGpoFamXParen item : itemsRespuesta) {

						GrupoFamiliar cgf = new GrupoFamiliar();
						
						AsignacionNSS asignacionNSS = new AsignacionNSS();
						asignacionNSS.setIdAsignacionNSS(idAsignacionNSS);
						
						cgf.setAsignacionNSS(asignacionNSS);
						
						Parentesco p = new Parentesco();
						p.setIdParentesco( 
								item.getCveIdCalidadParentesco().getValue().longValue() );
						cgf.setParentesco(p);
						
						EstadoDerechohabiente estado = new EstadoDerechohabiente();
						estado.setIdEstadoDerechohabiente(item.getCveEstadoDerechohabiente().getValue().longValue());
						cgf.setEstadoDerechohabiente(estado);
						
						SubEstadoDerechohabiente sub = new SubEstadoDerechohabiente();
						sub.setIdSubEstadoDerechohabiente( item.getCveSubestadoDerechohabiente().getValue().longValue() );
						cgf.setSubEstadoDerechohabiente(sub);
						

						Derechohabiente dhab = new Derechohabiente();
						dhab.setIdPersona( item.getCveIdPersona().getValue().longValue() );
						
						cgf.setDerechohabiente(dhab);
						
						items.add(cgf);
					
					}
				}
			}
			else{
				throw new DerechohabientesWebSserviceException( respuesta.getCodigoError() + ", " + respuesta.getMensajeError() );
			}
			
			return items;
		}
		catch (Exception e) {
			e.printStackTrace();
			
			if(e.getCause() instanceof SocketTimeoutException){
				throw new DerechohabientesWebSserviceException(socketTimeoutException);
			}
			
			if( e instanceof com.sun.xml.ws.client.ClientTransportException ){
				throw new DerechohabientesWebSserviceException("Ocurrio un error en el WebService de Vigencia al obtener la información del la Cabeza de Grupo Familiar por parentesco");
			}
			
			throw new DerechohabientesWebSserviceException(e.getMessage());
		}
	}
}
