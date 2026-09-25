package mx.gob.imss.services;

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
import mx.gob.imss.services.MessageVigGpoFamXlistEst.ListaEstadosDerechohabientes;

public class ConsultaGFPorEstadosWSClient {
	
	private String socketTimeoutException = "Ocurrió un error de comunicación al intentar recuperar la cabeza de grupo familiar";
	
	public List<GrupoFamiliar> getGrupoFamiliarPorEstados(Long idAsignacionNSS,
			List<Integer> listCveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException {
		try {
			List<GrupoFamiliar> items = null;

			ServVigGpoFamXListEstService service = new ServVigGpoFamXListEstService();
			ServVigGpoFamXListEst ws = service.getServVigGpoFamXListEstPort();

			MessageVigGpoFamXlistEst message = new MessageVigGpoFamXlistEst();
			message.setCveIdAsignacionNSS(idAsignacionNSS.intValue());
			ListaEstadosDerechohabientes estados = new ListaEstadosDerechohabientes();
			estados.listaEstados = listCveEstadoDerechohabiente;
			message.setListaEstadosDerechohabientes(estados);

			RespuestaVigGpoFamXListEst respuesta = ws
					.obtieneVigGpoFamXListEst(message);

			if (respuesta != null && respuesta.getCodigoError() == 0) {
				
				if(respuesta.getListaResultado() != null ) {
					List<InfoVigGpoFamXListEstVo> itemsRespuesta = respuesta
							.getListaResultado().getListaRespuesta();
	
					if (itemsRespuesta != null && !itemsRespuesta.isEmpty()) {
						items = new ArrayList<GrupoFamiliar>();
						for (InfoVigGpoFamXListEstVo item : itemsRespuesta) {
	
							GrupoFamiliar cgf = new GrupoFamiliar();
							
							AsignacionNSS asignacionNSS = new AsignacionNSS();
							asignacionNSS.setIdAsignacionNSS(idAsignacionNSS);
							
							cgf.setAsignacionNSS(asignacionNSS);
							
							Parentesco p = new Parentesco();
							p.setIdParentesco( 
									item.getCveIdCalidadParentesco().getValue().longValue() );
							cgf.setParentesco(p);
							
							EstadoDerechohabiente estado = new EstadoDerechohabiente();
							estado.setIdEstadoDerechohabiente(item.getCveIdEstadoDerechohabiente().getValue().longValue());
							cgf.setEstadoDerechohabiente(estado);
							
							SubEstadoDerechohabiente sub = new SubEstadoDerechohabiente();
							sub.setIdSubEstadoDerechohabiente( item.getCveIdSubestadoDerechohabiente().getValue().longValue() );
							cgf.setSubEstadoDerechohabiente(sub);
	
							Derechohabiente dhab = new Derechohabiente();
							dhab.setIdPersona( item.getCveIdPersona().getValue().longValue() );
							
							cgf.setDerechohabiente(dhab);
							items.add(cgf);
						
						}
					}
				}
			}
			else{
				throw new DerechohabientesWebSserviceException( respuesta.getCodigoError() + ", " + respuesta.getMensajeError() );
			}

			return items;
		} catch (Exception e) {
			e.printStackTrace();
			
			if(e.getCause() instanceof SocketTimeoutException){
				throw new DerechohabientesWebSserviceException(socketTimeoutException);
			}
			
			if( e instanceof com.sun.xml.ws.client.ClientTransportException ){
				throw new DerechohabientesWebSserviceException("Ocurrio un error en el WebService de Vigencia al obtener la información del la Cabeza de Grupo Familiar por estados");
			}
			
			throw new DerechohabientesWebSserviceException(e.getMessage());
		}
	}

	public List<GrupoFamiliar> getGrupoFamiliarPorEstadoySubestado(
			Long idAsignacionNSS, Integer cveEstadoDerechohabiente,
			Integer cveSubEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException {

		try {
			List<GrupoFamiliar> items = null;

			ServVigGpoFamXEstSubService service = new ServVigGpoFamXEstSubService();
			ServVigGpoFamXEstSub ws = service.getServVigGpoFamXEstSubPort();

			MessageVigGpoFamXEst message = new MessageVigGpoFamXEst();
			message.setCveIdAsignacionNSS(idAsignacionNSS.intValue());
			message.setCveIdEstadoDerechohabiente(cveEstadoDerechohabiente);
			message.setCveIdSubestadoDerechohabiente(cveSubEstadoDerechohabiente);

			RespuestaVigGpoFamXEstSub respuesta = ws
					.obtieneVigGpoFamXEstSub(message);

			if (respuesta != null && respuesta.getCodigoError() == 0) {
				List<InfPersonaGpoFamiliarVo> itemsRespuesta = respuesta
						.getListaResultado().getLstInfoPersonas();

				if (itemsRespuesta != null && !itemsRespuesta.isEmpty()) {
					items = new ArrayList<GrupoFamiliar>();
					for (InfPersonaGpoFamiliarVo item : itemsRespuesta) {

						GrupoFamiliar cgf = new GrupoFamiliar();
						

						AsignacionNSS asignacionNSS = new AsignacionNSS();
						asignacionNSS.setIdAsignacionNSS(idAsignacionNSS);
						
						cgf.setAsignacionNSS(asignacionNSS);
						
						EstadoDerechohabiente estado = new EstadoDerechohabiente();
						estado.setIdEstadoDerechohabiente(item.getCveIdEstadoDerechohabiente().getValue().longValue());
						cgf.setEstadoDerechohabiente(estado);
						
						SubEstadoDerechohabiente sub = new SubEstadoDerechohabiente();
						sub.setIdSubEstadoDerechohabiente( item.getCveIdSubestadoDerechohabiente().getValue().longValue() );
						cgf.setSubEstadoDerechohabiente(sub);
						

						Derechohabiente dhab = new Derechohabiente();
						dhab.setIdPersona( item.getCveIdPersona().getValue().longValue() );
						
						cgf.setDerechohabiente(dhab);
						
						items.add(cgf);
					
					}
				}
			}else{
				throw new DerechohabientesWebSserviceException( respuesta.getCodigoError() + ", " + respuesta.getMensajeError() );
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			
			if(e.getCause() instanceof SocketTimeoutException){
				throw new DerechohabientesWebSserviceException(socketTimeoutException);
			}
			
			if( e instanceof com.sun.xml.ws.client.ClientTransportException ){
				throw new DerechohabientesWebSserviceException("Ocurrio un error en el WebService de Vigencia al obtener la información del la Cabeza de Grupo Familiar por estado y subestado");
			}
			
			throw new DerechohabientesWebSserviceException(e.getMessage());
		}

	}

	public List<GrupoFamiliar> getGrupoFamiliarPorEstado(Long idAsignacionNSS,
			Integer cveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException {
		try {
			List<GrupoFamiliar> items = null;

			ServVigGpoFamEstService service = new ServVigGpoFamEstService();
			ServVigGpoFamEst ws = service.getServVigGpoFamEstPort();
			Message message = new Message();
			message.setCveIdAsignacionNSS(idAsignacionNSS.intValue());
			message.setCveIdEstadoDerechohabiente(cveEstadoDerechohabiente);

			RespuestaServVigGpoFamEst respuesta = ws
					.obtieneVigenciaGpoFamEst(message);
			

			if (respuesta != null && respuesta.getCodigoError() == 0 && respuesta.getListaResultado() != null) {
				if(respuesta.getListaResultado().getLstInfoPersonas() != null && !respuesta.getListaResultado().getLstInfoPersonas().isEmpty()){
					List<InfoPersonaGpoFamiliarVo> itemsRespuesta = respuesta
							.getListaResultado().getLstInfoPersonas();
	
					if (itemsRespuesta != null && !itemsRespuesta.isEmpty()) {
						items = new ArrayList<GrupoFamiliar>();
						for (InfoPersonaGpoFamiliarVo item : itemsRespuesta) {
							GrupoFamiliar cgf = new GrupoFamiliar();
							cgf.setAsignacionNSS(new AsignacionNSS());
							cgf.getAsignacionNSS().setIdAsignacionNSS(idAsignacionNSS);
							
							Parentesco p = new Parentesco();
							p.setIdParentesco( 
									item.getCveIdCalidadParentesco().getValue().longValue() );
							cgf.setParentesco(p);
							
							EstadoDerechohabiente estado = new EstadoDerechohabiente();
							estado.setIdEstadoDerechohabiente(item.getCveIdEstadoDerechohabiente().getValue().longValue());
							cgf.setEstadoDerechohabiente(estado);
							
							SubEstadoDerechohabiente sub = new SubEstadoDerechohabiente();
							sub.setIdSubEstadoDerechohabiente( item.getCveIdSubestadoDerechohabiente().getValue().longValue() );
							cgf.setSubEstadoDerechohabiente(sub);
							
							cgf.setDerechohabiente(new Derechohabiente());
							cgf.getDerechohabiente().setIdPersona(item.getCveIdPersona().getValue().longValue());
							
							items.add(cgf);
						}
					}
				}
			}
			else{
				throw new DerechohabientesWebSserviceException( respuesta.getCodigoError() + ", " + respuesta.getMensajeError() );
			}

			return items;
		} catch (Exception e) {
			e.printStackTrace();
			
			if(e.getCause() instanceof SocketTimeoutException){
				throw new DerechohabientesWebSserviceException(socketTimeoutException);
			}
			
			if( e instanceof com.sun.xml.ws.client.ClientTransportException ){
				throw new DerechohabientesWebSserviceException("Ocurrio un error en el WebService de Vigencia al obtener la información del la Cabeza de Grupo Familiar por estado");
			}
			
			throw new DerechohabientesWebSserviceException(e.getMessage());
		}

	}

}
